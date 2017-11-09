package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProgressActivity extends BaseActivity {

    @Bind(R.id.pb_newsdetails)
    ProgressBar pbNewsdetails;
    @Bind(R.id.rl_newsdetails)
    RelativeLayout rlNewsdetails;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
