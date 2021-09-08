package com.epro.recyclerview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2019/10/20
 */
public class SimpleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SimpleAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_tv, item);
    }
}
