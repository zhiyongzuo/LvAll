package com.epro.recyclerview;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
//    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRecyclerView = findViewById(R.id.recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        mRecyclerView.addItemDecoration(new MyItemDecoration());
//
//        List<String> mList = new ArrayList<>();
//        for (int i=0; i<100; i++) {
//            mList.add(i + " str");
//        }
//        SimpleAdapter simpleAdapter = new SimpleAdapter(mList);
//        mRecyclerView.setAdapter(simpleAdapter);


        mRecyclerView = findViewById(R.id.recycler_view);
//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final StaggeredGridLayoutManager mLinearLayoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //228024296
//        mRecyclerView.addItemDecoration(new MyItemDecoration());

        List<QuickMultipleEntity> mList = new ArrayList<>();
        for (int i=0; i<100; i++) {
            if (i==40) {
                mList.add(new QuickMultipleEntity(QuickMultipleEntity.IMG, i + " str"));
            } else {
                mList.add(new QuickMultipleEntity(QuickMultipleEntity.TEXT, i + " str"));
            }
        }
        final MultiItemForPicAdapter simpleAdapter = new MultiItemForPicAdapter(mList);
        mRecyclerView.setAdapter(simpleAdapter);
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
//                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
//                for (int i = fPos; i <= lPos; i++) {
//                    View view = mLinearLayoutManager.findViewByPosition(i);
//                    AdImageView adImageView = view.findViewById(R.id.item_iv);
//                    if (adImageView!=null && adImageView.getVisibility() == View.VISIBLE) {
//                        adImageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());
//                    }
//                }
//            }
//        });



        //StaggeredGridLayoutManager布局设置间距     非平分，九宫格这样
        final int divider = ConvertUtils.dp2px(10);
        RecyclerView.ItemDecoration gridItemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //不为banner类型
                if (simpleAdapter.getItemViewType(parent.getChildAdapterPosition(view)) != 0) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                    int spanIndex = layoutParams.getSpanIndex();
                    outRect.top = divider;
                    if (spanIndex == 0) {
                        // left
                        outRect.left = divider;
                        outRect.right = divider / 2;
                    } else{
                        outRect.right = divider;
                        outRect.left = divider / 2;
                    }
                }
            }
        };
        mRecyclerView.addItemDecoration(gridItemDecoration);

    }
}
