package com.administrator.administrator.eataway.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.yanzhenjie.loading.LoadingView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * Created by local123 on 2017/11/6.
 */

public class LoadMoreView extends LinearLayout implements SwipeMenuRecyclerView.LoadMoreView, View.OnClickListener{

    private SwipeMenuRecyclerView.LoadMoreListener listener;

    private TextView textView;
    private LoadingView mLoadingView;


    public LoadMoreView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
        setVisibility(GONE);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int minHeight = (int) (displayMetrics.density * 60 + 0.5);
        setMinimumHeight(minHeight);

        textView = new TextView(context);
        LayoutParams tvLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(tvLayoutParams);
        textView.setPadding(0, 10, 0, 10);
        this.addView(textView);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onLoadMore();
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore) {

    }

    @Override
    public void onWaitToLoadMore(SwipeMenuRecyclerView.LoadMoreListener loadMoreListener) {

    }

    @Override
    public void onLoadError(int errorCode, String errorMessage) {

    }
}
