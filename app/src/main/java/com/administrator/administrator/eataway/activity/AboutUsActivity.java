package com.administrator.administrator.eataway.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.view.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {

    private final String VERSION = "v1.0.3";

    @Bind(R.id.tb_about_us)
    TopBar tbAboutUs;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tbAboutUs.setTbCenterTv(R.string.guan_yu_wo_men, R.color.color_white);
        tbAboutUs.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvVersion.setText(getString(R.string.ban_ben_hao) + VERSION);
    }
}
