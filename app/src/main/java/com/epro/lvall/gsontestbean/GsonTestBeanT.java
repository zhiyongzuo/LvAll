package com.epro.lvall.gsontestbean;

/**
 * @author zzy
 * @date 2021/6/25
 */
public class GsonTestBeanT<T>{
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
