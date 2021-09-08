package com.epro.image;


import android.media.ImageReader;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /**
     * 测试图片类型
     */
    @Test
    public void testImageType() {
        try {
            InputStream inputStream = new FileInputStream("E:\\ap2\\LvAll\\image\\src\\main\\res\\drawable" + "\\pikacu.gif");
            ImageIO imageIO
            inputStream.read();
            ImageReader imageReader = new ImageReader(inputStream.read());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}