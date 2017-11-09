package com.administrator.administrator.eataway.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/17.
 */

public class BottomBar extends LinearLayout {
    @Bind(R.id.tv_bottom_text)
    TextView tvBottomText;
    private Context context;

    public BottomBar(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public BottomBar(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public BottomBar(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.bottombar, this, true);
        ButterKnife.bind(this);
    }

    public void setCenText(int stringRes, int color) {
        tvBottomText.setText(stringRes);
        tvBottomText.setTextColor(getResources().getColor(color));
    }
}
