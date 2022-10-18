package com.epro.verifycode.hooker;

import android.os.IBinder;
import android.os.IInterface;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class SystemServiceBinderHooker {
    public interface HookCallback {
        void onServiceMethodInvoke(Method method, Object[] args);

        Object onServiceMethodIntercept(Object receiver, Method method, Object[] args) throws Throwable;
    }

    private String mServiceName;
    private String mServiceClassName;
    private HookCallback mHookCallback;

    public SystemServiceBinderHooker(String serviceName, String serviceClassName, HookCallback hookCallback){
        this.mServiceName = serviceName;
        this.mServiceClassName = serviceClassName;
        this.mHookCallback = hookCallback;
    }

    public boolean hook(){
        try {
            // 1. 先获取 origin 的 IBinder 对象
            Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = serviceManagerClass.getDeclaredMethod("getService",String.class);
            getServiceMethod.setAccessible(true);
            final IBinder serviceBinder = (IBinder) getServiceMethod.invoke(null,mServiceName);

            // 2. hook 住 serviceBinder 创建代理对象
            IBinder proxyServiceBinder = (IBinder) Proxy.newProxyInstance(serviceManagerClass.getClassLoader(), new Class<?>[]{IBinder.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (TextUtils.equals(method.getName(), "queryLocalInterface")) {
                        return createServiceProxy(serviceBinder);
                    }
                    return method.invoke(serviceBinder, args);
                }
            });

            // 3. 把代理对象塞到 ServiceManager 中的 sCache
            Field sCacheField = serviceManagerClass.getDeclaredField("sCache");
            sCacheField.setAccessible(true);
            Map<String, IBinder> sCache = (Map<String, IBinder>) sCacheField.get(null);
            sCache.put(mServiceName, proxyServiceBinder);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Object createServiceProxy(IBinder serviceBinder) {
        try {
            // new IWifiManager.Stub.Proxy
            Class<?> serviceProxyClass = Class.forName(mServiceClassName + "$Stub$Proxy");
            Constructor<?> serviceProxyConstructor = serviceProxyClass.getDeclaredConstructor(IBinder.class);
            serviceProxyConstructor.setAccessible(true);
            final Object originServiceProxy = serviceProxyConstructor.newInstance(serviceBinder);

            // hook serviceProxy
            Object serviceProxyHooker = Proxy.newProxyInstance(serviceProxyClass.getClassLoader(), new Class<?>[]{IBinder.class, IInterface.class, Class.forName(mServiceClassName)}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (mHookCallback != null) {
                        mHookCallback.onServiceMethodInvoke(method, args);
                        Object result = mHookCallback.onServiceMethodIntercept(originServiceProxy, method, args);
                        if (result != null) {
                            return result;
                        }
                    }
                    return method.invoke(originServiceProxy, args);
                }
            });
            return serviceProxyHooker;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
