package com.epro.lvall.gsontestbean;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;



/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResonsesBodyListFunc<T>{

    Class<T> dataType;

    public HttpResonsesBodyListFunc(Class<T> dataType) {
        this.dataType = dataType;
    }

    public BaseResponse<List<T>> apply(String json) {
        try {
            return fromJsonArray(json, dataType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BaseResponse<List<T>> fromJsonArray(String json, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(BaseResponse.class, new Type[]{listType});
        return new Gson().fromJson(json, type);

        //zzy add 1219 如果有大小写问题可以用fastjson
//        return JSONArray.parseObject(json, type);
    }

    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
