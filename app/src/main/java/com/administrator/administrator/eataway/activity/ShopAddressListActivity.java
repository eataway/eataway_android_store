package com.administrator.administrator.eataway.activity;

import android.os.Bundle;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;

import butterknife.ButterKnife;

public class ShopAddressListActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_address;
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
