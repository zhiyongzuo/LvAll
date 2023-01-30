package com.zeusee.main.lpr.jni;

import android.util.Log;

import com.example.platerecognization.util.PlateInfo;

import java.util.ArrayList;
import java.util.List;

public class PlateRecognition {
    public List<PlateInfo> ques;

    static {
        System.loadLibrary("hyperlpr");
    }

    private PlateRecognition() {}

    //    java.lang.UnsatisfiedLinkError: No implementation found for void com.zeusee.main.lpr.jni.PlateRecognition.InitPlateRecognizer(java.lang.String)
    //    (tried Java_com_example_platerecognization_util_PlateRecognition_InitPlateRecognizer
    //       and Java_com_example_platerecognization_util_PlateRecognition_InitPlateRecognizer__Ljava_lang_String_2)
    private static native void InitPlateRecognizer(String paramString);

    private static native String RunRecognitionAsRaw(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, float paramFloat, int paramInt4, int paramInt5, int paramInt6);

    private static native String RunRecognitionByPath(String paramString, float paramFloat, int paramInt1, int paramInt2, int paramInt3);

    private static native String RunRecognitionWithBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4, int paramInt5);

    public static final PlateRecognition getInstance() {
        return PlateRecognizerHolder.INSTANCE;
    }

    private PlateInfo getMaxPlateInfo(List<PlateInfo> paramList) {
        PlateInfo plateInfo = paramList.get(0);
        int i = 1;
        while (i < paramList.size()) {
            PlateInfo plateInfo1 = plateInfo;
            if (((PlateInfo)paramList.get(i)).getConfidence() > plateInfo.getConfidence())
                plateInfo1 = paramList.get(i);
            i++;
            plateInfo = plateInfo1;
        }
        return plateInfo;
    }

    private static List<PlateInfo> parserPlate(String paramString) {
        ArrayList<PlateInfo> arrayList = new ArrayList();
        if (paramString == null)
            return arrayList;
        if (paramString.indexOf("sessionError") != -1)
            arrayList.add(new PlateInfo(Integer.parseInt(paramString.split("/")[1])));
        String[] arrayOfString = paramString.split("/");
        int i = 0;
        while (i < arrayOfString.length) {
            String[] arrayOfString1 = arrayOfString[i].split(",");
            if (arrayOfString1.length == 1)
                return arrayList;
            if (arrayOfString1.length == 7) {
                arrayList.add(new PlateInfo(arrayOfString1[0].trim(), Integer.parseInt(arrayOfString1[1]), Integer.parseInt(arrayOfString1[2]), Integer.parseInt(arrayOfString1[3]), Integer.parseInt(arrayOfString1[4]), Integer.parseInt(arrayOfString1[5]), Float.parseFloat(arrayOfString1[6])));
                i++;
                continue;
            }
            throw new ArrayIndexOutOfBoundsException("");
        }
        return arrayList;
    }

    private static native void releasePlateRecognizer();

    public List<PlateInfo> RunRecognitionWithBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
        List<PlateInfo> list = parserPlate(RunRecognitionWithBytes(paramArrayOfbyte, paramInt1, paramInt2, 0.8F, paramInt3, paramInt4, paramInt5));
        if (list.size() == 0) {
            this.ques = new ArrayList<PlateInfo>();
            this.ques.clear();
            return list;
        }
        PlateInfo plateInfo = getMaxPlateInfo(list);
        if (this.ques.size() < 2) {
            this.ques.add(plateInfo);
            return list;
        }
        this.ques.remove(0);
        this.ques.add(plateInfo);
        return list;
    }

    public PlateInfo getBestConfidenceInfo() {
        Log.d("debug_d", String.valueOf(this.ques.size()));
        return (this.ques.size() >= 2) ? getMaxPlateInfo(this.ques) : new PlateInfo(0);
    }

    public void initPlateRecognition(String paramString) {
        InitPlateRecognizer(paramString);
        this.ques = new ArrayList<PlateInfo>();
    }

    public List<PlateInfo> plateRecognitionByPath(String paramString, int paramInt1, int paramInt2, int paramInt3) {
        paramString = RunRecognitionByPath(paramString, 0.8F, paramInt1, paramInt2, paramInt3);
        Log.d("concat_res", paramString);
        List<PlateInfo> list = parserPlate(paramString);
        if (list.size() == 0) {
            this.ques.clear();
            return list;
        }
        PlateInfo plateInfo = getMaxPlateInfo(list);
        if (this.ques.size() < 2) {
            this.ques.add(plateInfo);
            return list;
        }
        this.ques.remove(0);
        this.ques.add(plateInfo);
        return list;
    }

    public List<PlateInfo> plateRecognitionWithRaw(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4, int paramInt5) {
        List<PlateInfo> list = parserPlate(RunRecognitionAsRaw(paramArrayOfbyte, paramInt1, paramInt2, 90, 0.8F, paramInt3, paramInt4, paramInt5));
        if (list.size() == 0) {
            this.ques = new ArrayList<PlateInfo>();
            this.ques.clear();
            return list;
        }
        PlateInfo plateInfo = getMaxPlateInfo(list);
        if (this.ques.size() < 2) {
            this.ques.add(plateInfo);
            return list;
        }
        this.ques.remove(0);
        this.ques.add(plateInfo);
        return list;
    }

    public void releaseJni() {
        releasePlateRecognizer();
    }

    private static class PlateRecognizerHolder {
        private static final PlateRecognition INSTANCE = new PlateRecognition();
    }
}
