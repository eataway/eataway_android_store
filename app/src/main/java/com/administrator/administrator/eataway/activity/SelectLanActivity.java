package com.administrator.administrator.eataway.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLanActivity extends BaseActivity {
    @Bind(R.id.btn_ch)
    TextView btnCh;
    @Bind(R.id.btn_en)
    TextView btnEn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_lan;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ch, R.id.btn_en})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ch:
                SharedPreferences sp = getSharedPreferences("lan",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("isFirst",1);
                editor.commit();
                BaseActivity.setLanguage(false, this);
                MyApplication.isEnglish = false;
                goToActivity(LoginActivity.class);
                break;
            case R.id.btn_en:
                sp = getSharedPreferences("lan",MODE_PRIVATE);
                editor = sp.edit();
                editor.putInt("isFirst",2);
                editor.commit();
                BaseActivity.setLanguage(true, this);
                MyApplication.isEnglish = true;
                goToActivity(LoginActivity.class);
                break;
        }
    }
}
