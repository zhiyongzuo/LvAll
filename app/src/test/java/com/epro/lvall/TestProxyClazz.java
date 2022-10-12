package com.epro.lvall;

import org.jetbrains.annotations.TestOnly;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.UUID;

public class TestProxyClazz {

    interface TestInterface{
        String test01();
        String test02(String test01, String test02);
    }

    static class TestProxy implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch(method.getName()) {
                case "tet01":
                    return "invoke tet01====>" + UUID.randomUUID().toString();
                case "test01":
                    return "invoke test01===>" + UUID.randomUUID().toString();
                case "test02":
                    return "invoke test02====>" + Arrays.asList(args);
            }
            return null;
        }
    }

    static class TestFactory{
        public static <T> T newInstances(Class<T> clazz) {
            InvocationHandler h = new TestProxy();
            return (T) Proxy.newProxyInstance(TestFactory.class.getClassLoader(), new Class[]{clazz}, h);
        }
    }

    @Test
    public void tet01() {
        InvocationHandler h = new TestProxy();
        TestInterface obj = (TestInterface)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{TestInterface.class}, h);
        String res = obj.test01();
        //invoke test01===>406ccc62-9c70-4d97-bde1-6e45f2022de2
        System.out.println(res);
    }

    @Test
    public void tet02() {
        TestInterface t = TestFactory.newInstances(TestInterface.class);
        String res = t.test02("111", "222");
        //invoke test02====>[111, 222]
        System.out.println(res);
    }

}


