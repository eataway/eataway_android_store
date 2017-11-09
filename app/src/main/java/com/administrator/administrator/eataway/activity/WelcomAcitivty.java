package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;

import butterknife.ButterKnife;

public class WelcomAcitivty extends BaseActivity {
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcom_acitivty;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("lan",MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.getInt("isFirst",0)==1){  //中文
                    BaseActivity.setLanguage(false, WelcomAcitivty.this);
                    MyApplication.isEnglish = false;
                    if (MyApplication.getLogin() == null) {
                        goToActivity(LoginActivity.class);
                        finish();
                    }else {
                        goToActivity(MainActivity.class);
                        finish();
                    }
                } else if (preferences.getInt("isFirst",0)==2) {    //英文
                    BaseActivity.setLanguage(true, WelcomAcitivty.this);
                    MyApplication.isEnglish = true;
                    if (MyApplication.getLogin() == null) {
                        goToActivity(LoginActivity.class);
                        finish();
                    }else {
                        goToActivity(MainActivity.class);
                        finish();
                    }
                }else {
                    goToActivity(SelectLanActivity.class);
                    finish();
                }
            }
        }, 2000);
    }
}
