package com.epro.lvall;

import android.content.Context;
import android.graphics.Matrix;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.epro.lvall", appContext.getPackageName());

        testRomanToInt();
    }


    /**
     *这里打印全是0，和Matrix构造方法调用native有关吧。。
     * 用了android api 测试用例要写在androidtest里
     */
    @Test
    public void matrixtTest() {
        Matrix matrix = new Matrix();

        LogUtils.e(GsonUtils.toJson(getMatrixFloats(matrix)));//[1.0,0.0,0.0,0.0,1.0,0.0,0.0,0.0,1.0]
        matrix.preTranslate(2.0f, 3.0f);
        matrix.postTranslate(2, 3);
        matrix.postScale(2, 3);
        LogUtils.e(GsonUtils.toJson(getMatrixFloats(matrix)));//[2.0,0.0,8.0,0.0,3.0,18.0,0.0,0.0,1.0]
    }
    private float[] getMatrixFloats(Matrix matrix) {
        float[] floats = new float[9];
        matrix.getValues(floats);
        return floats;
    }


    @Test
    public void testRomanToInt() {
        LogUtils.e("MCMXCIV");
        LogUtils.e(romanToInt("MCMXCIV"));
        System.out.println("MCMXCIV");
        System.out.println(romanToInt("MCMXCIV"));
    }
    public int romanToInt(String s) {
        int returnInt = 0;
        while(s.length()>0) {
            if("M".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 1000;
                s = s.substring(1);
            } else if(s.length()>1 && "CM".equals(s.substring(0, 2))) {
                returnInt += 900;
                s = s.substring(2);
            } else if("D".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 500;
                s = s.substring(1);
            } else if(s.length()>1 && "CD".equals(s.substring(0, 2))) {
                returnInt += 400;
                s = s.substring(2);
            } else if("C".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 100;
                s = s.substring(1);
            } else if(s.length()>1 && "XC".equals(s.substring(0, 2))) {
                returnInt += 90;
                s = s.substring(2);
            } else if("L".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 50;
                s = s.substring(1);
            } else if(s.length()>1 && "XL".equals(s.substring(0, 2))) {
                returnInt += 40;
                s = s.substring(2);
            } else if("X".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 10;
                s = s.substring(1);
            } else if(s.length()>1 && "IX".equals(s.substring(0, 2))) {
                returnInt += 9;
                s = s.substring(2);
            } else if("V".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 5;
                s = s.substring(1);
            } else if(s.length()>1 && "IV".equals(s.substring(0, 2))) {
                returnInt += 4;
                s = s.substring(2);
            }  else if("I".equals(String.valueOf(s.charAt(0)))) {
                returnInt += 1;
                if(s.length()==1) {
                    s = "";
                } else{
                    s = s.substring(1);
                }
            }
        }
        return returnInt;
    }
}
