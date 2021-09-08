package com.epro.lvall;

import com.blankj.utilcode.util.GsonUtils;
import com.epro.lvall.gsontestbean.BaseResponse;
import com.epro.lvall.gsontestbean.GsonTestBean;
import com.epro.lvall.gsontestbean.GsonTestBeanSeri;
import com.epro.lvall.gsontestbean.GsonTestBeanT;
import com.epro.lvall.gsontestbean.HttpResonsesBodyListFunc;
import com.epro.lvall.gsontestbean.RealBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author zzy
 * @date 2021/6/25
 */
public class GsonTest<T> {

    @Test
    public void test() {
        String s = "{\"result\":{\"list\":[{\"id\":4.0,\"parent_id\":1,\"name\":\"陶瓷\",\"typeTemplate\":{\"id\":53.01,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"53\",\"hasChildren\":false,\"treeParent\":null,\"uuid\":null},{\"id\":5,\"parent_id\":0,\"name\":\"国画\",\"typeTemplate\":{\"id\":52,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"52\",\"hasChildren\":true,\"treeParent\":null,\"uuid\":null},{\"id\":6,\"parent_id\":0,\"name\":\"金银\",\"typeTemplate\":{\"id\":53,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"53\",\"hasChildren\":true,\"treeParent\":null,\"uuid\":null},{\"id\":11,\"parent_id\":0,\"name\":\"手和\",\"typeTemplate\":{\"id\":52,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"52\",\"hasChildren\":true,\"treeParent\":null,\"uuid\":null}]},\"reason\":\"请求成功\",\"success\":\"1\"}";


        Object object = GsonUtils.fromJson(s, Object.class);
        GsonTestBean gsonTestBean = GsonUtils.fromJson(s, GsonTestBean.class);
        BaseResponse baseResponse = GsonUtils.fromJson(s, BaseResponse.class);
        GsonTestBeanSeri gsonTestBeanSeri = GsonUtils.fromJson(s, GsonTestBeanSeri.class);
        GsonTestBeanT gsonTestBeanT = GsonUtils.fromJson(s, GsonTestBeanT.class);
        //下面打印的是不一致的
        //不知道为什么baseResponse能打印出来数据-------因为泛型T result,     也导致1转成1.0.   如果是具体的String类型，而不是泛型，还是1
//        System.out.println(GsonUtils.toJson(object));//{"result":{"list":[{"id":4.0,"parent_id":0.0,"name":"陶瓷","typeTemplate":{"id":53.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"53","hasChildren":false,"treeParent":null,"uuid":null},{"id":5.0,"parent_id":0.0,"name":"国画","typeTemplate":{"id":52.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"52","hasChildren":true,"treeParent":null,"uuid":null},{"id":6.0,"parent_id":0.0,"name":"金银","typeTemplate":{"id":53.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"53","hasChildren":true,"treeParent":null,"uuid":null},{"id":11.0,"parent_id":0.0,"name":"手和","typeTemplate":{"id":52.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"52","hasChildren":true,"treeParent":null,"uuid":null}]},"reason":"请求成功","success":"1"}
//        System.out.println(((RealBean)object).getSuccess());//java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.epro.lvall.gsontestbean.RealBean
        RealBean realBean = GsonUtils.fromJson(GsonUtils.toJson(object), RealBean.class);
//        System.out.println(realBean.getReason());


//        System.out.println(GsonUtils.toJson(gsonTestBean));//{}
//        System.out.println(GsonUtils.toJson(baseResponse));//{"success":"1","reason":"请求成功","message":null,"result":{"list":[{"id":4.0,"parent_id":0.0,"name":"陶瓷","typeTemplate":{"id":53.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"53","hasChildren":false,"treeParent":null,"uuid":null},{"id":5.0,"parent_id":0.0,"name":"国画","typeTemplate":{"id":52.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"52","hasChildren":true,"treeParent":null,"uuid":null},{"id":6.0,"parent_id":0.0,"name":"金银","typeTemplate":{"id":53.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"53","hasChildren":true,"treeParent":null,"uuid":null},{"id":11.0,"parent_id":0.0,"name":"手和","typeTemplate":{"id":52.0,"name":null,"spec_ids":null,"brand_ids":null,"custom_attribute_items":null,"del":null},"del":null,"type_id":"52","hasChildren":true,"treeParent":null,"uuid":null}]},"msg":null}
//        System.out.println(GsonUtils.toJson(gsonTestBeanSeri));//{}
//        System.out.println(getGson().toJson(gsonTestBeanT));


        String jsonStr = "{\"result\":[{\"id\":4.0,\"parent_id\":1,\"name\":\"陶瓷\",\"typeTemplate\":{\"id\":53.01,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"53\",\"hasChildren\":false,\"treeParent\":null,\"uuid\":null},{\"id\":5,\"parent_id\":0,\"name\":\"国画\",\"typeTemplate\":{\"id\":52,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"52\",\"hasChildren\":true,\"treeParent\":null,\"uuid\":null},{\"id\":6,\"parent_id\":0,\"name\":\"金银\",\"typeTemplate\":{\"id\":53,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"53\",\"hasChildren\":true,\"treeParent\":null,\"uuid\":null},{\"id\":11,\"parent_id\":0,\"name\":\"手和\",\"typeTemplate\":{\"id\":52,\"name\":null,\"spec_ids\":null,\"brand_ids\":null,\"custom_attribute_items\":null,\"del\":null},\"del\":null,\"type_id\":\"52\",\"hasChildren\":true,\"treeParent\":null,\"uuid\":null}],\"reason\":\"请求成功\",\"success\":\"1\"}";
        HttpResonsesBodyListFunc httpResonsesBodyListFunc = new HttpResonsesBodyListFunc<>(RealBean.ResultBean.ListBean.class);
        BaseResponse<List<RealBean.ResultBean.ListBean>> baseResponse1 = httpResonsesBodyListFunc.apply(jsonStr);
        System.out.println(baseResponse1.getData().get(0).getId());//4.0
        System.out.println(baseResponse1.getData().get(0).getParent_id());//1.0
    }

    public static Gson getGson() {
        return new GsonBuilder().
                registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        //4.0会转成 4
                        //5.01会转成5.01
                        if (src == src.longValue())
                            return new JsonPrimitive(src.longValue());
                        return new JsonPrimitive(src);
                    }
                }).create();
    }


    /**
     * 测试prettyPrint
     */
    @Test
    public void test1() {
        String s = "{\"timestamp\":11.00,\"status\":400,\"error\":22.10}";
//        Gson gson = new GsonBuilder().create();//{"timestamp":"11.00","status":"400","error":"22.10"}
        Gson gson = new GsonBuilder().setPrettyPrinting().create();//{"timestamp":"11.00","status":"400","error":"22.10"}

//        GsonTestBean gsonTestBean = gson.fromJson(s, GsonTestBean.class);
//        System.out.println(GsonUtils.toJson(gsonTestBean));

        Type classType = getClass().getGenericSuperclass();
        if (classType instanceof ParameterizedType && ((ParameterizedType) classType).getActualTypeArguments().length > 0) {
            Type type = ((ParameterizedType) classType).getActualTypeArguments()[0];
            Type tvvv = gson.fromJson(s, type);
            System.out.println(GsonUtils.toJson(tvvv));
        }

    }


    /**
     * 测试获取泛型名称
     */
    @Test
    public void testGenericityName() {
        Type classType = getClass().getGenericSuperclass();
        System.out.println("classType=" + classType.toString());
        System.out.println("classType=" + GsonUtils.toJson(classType));
        Type type = ((ParameterizedType)classType).getActualTypeArguments()[0];
        System.out.println("type=" + type.toString());
    }
}
