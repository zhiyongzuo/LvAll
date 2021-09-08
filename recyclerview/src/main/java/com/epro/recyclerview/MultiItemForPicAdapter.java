package com.epro.recyclerview;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author zzy
 * @date 2019/10/20
 */
public class MultiItemForPicAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

//    public MultiItemForPicAdapter(@Nullable List<String> data) {
//        super(R.layout.item_rv, data);
//    }



//    @Override
//    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.item_tv, item);
//    }


    public MultiItemForPicAdapter(List<QuickMultipleEntity> data) {
        super(data);
        addItemType(QuickMultipleEntity.TEXT, R.layout.item_rv);
        addItemType(QuickMultipleEntity.IMG, R.layout.item_rv_img);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuickMultipleEntity item) {
        switch (item.getItemType()) {
            case QuickMultipleEntity.TEXT:
                helper.setText(R.id.item_tv, item.getText());
                break;
            case QuickMultipleEntity.IMG:
                break;
            default:
                break;
        }
    }
}
