package com.epro.recyclerview;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author zzy
 * @date 2019/12/22
 */
public class QuickMultipleEntity implements MultiItemEntity {
    public static final int TEXT = 1;
    public static final int IMG = 2;

    private String text;
    private int itemType;

    public QuickMultipleEntity(int itemType, String text) {
        this.text = text;
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
