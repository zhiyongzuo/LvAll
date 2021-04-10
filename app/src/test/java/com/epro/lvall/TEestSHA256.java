package com.epro.lvall;

import com.blankj.utilcode.util.EncryptUtils;

import org.junit.Test;

/**
 * @author zzy
 * @date 2020/11/6
 */
public class TEestSHA256 {

    @Test
    public void test() {
        System.out.println(EncryptUtils.encryptSHA256ToString("apiKeytest_api_keyeggspamfoobartimestamp1514736000000test_api_secret"));
    }
}
