package com.epro.behavior.behaviordemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epro.behavior.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zzy
 * @date 2021/9/6
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private List<String> dataList;
    private Activity mActivity;

    public TestAdapter(List<String> dataList, Activity mActivity) {
        this.dataList = dataList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_text_view, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        holder.tvItem.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder{
        private TextView tvItem;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
