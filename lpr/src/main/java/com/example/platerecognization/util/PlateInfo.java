package com.example.platerecognization.util;

public class PlateInfo {
    public String[] color = new String[] {"蓝牌", "黄牌单层","白牌单层", "绿牌新能源", "黑牌港澳", "香港单层", "香港双层", "澳门单层", "澳门双层", "大陆双层", "未知"};

    private float confidence;

    private int errorCode;

    private int height;

    private String plateName;

    private int plateType;

    public String[] warningInfo = new String[] { ", ", ", "};

    private int width;

    private int x;

    private int y;

    public PlateInfo(int paramInt) {
        this.errorCode = paramInt;
    }

    public PlateInfo(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat) {
        this.plateName = paramString;
        this.plateType = paramInt1;
        this.x = paramInt2;
        this.y = paramInt3;
        this.width = paramInt4;
        this.height = paramInt5;
        this.confidence = paramFloat;
        this.errorCode = -1;
    }

    public float getConfidence() {
        return this.confidence;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public int getHeight() {
        return this.height;
    }

    public String getPlateName() {
        return this.plateName;
    }

    public int getPlateType() {
        return this.plateType;
    }

    public String getWarningInfo() {
        int i = this.errorCode;
        return (i != -1) ? this.warningInfo[i] : "";
    }

    public int getWidth() {
        return this.width;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setConfidence(float paramFloat) {
        this.confidence = paramFloat;
    }

    public void setErrorCode(int paramInt) {
        this.errorCode = paramInt;
    }

    public void setHeight(int paramInt) {
        this.height = paramInt;
    }

    public void setPlateName(String paramString) {
        this.plateName = paramString;
    }

    public void setPlateType(int paramInt) {
        this.plateType = paramInt;
    }

    public void setWidth(int paramInt) {
        this.width = paramInt;
    }

    public void setX(int paramInt) {
        this.x = paramInt;
    }

    public void setY(int paramInt) {
        this.y = paramInt;
    }
}
