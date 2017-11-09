package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.view.TopBar;

import butterknife.Bind;
import butterknife.OnClick;

public class TimeListActivity extends BaseActivity {
    @Bind(R.id.tb_time_list)
    TopBar tbTimeList;
    @Bind(R.id.tv_monday)
    TextView tvMonday;
    @Bind(R.id.tv_tuesday)
    TextView tvTuesday;
    @Bind(R.id.tv_wednesday)
    TextView tvWednesday;
    @Bind(R.id.tv_thurday)
    TextView tvThurday;
    @Bind(R.id.tv_friday)
    TextView tvFriday;
    @Bind(R.id.tv_saturday)
    TextView tvSaturday;
    @Bind(R.id.tv_sunday)
    TextView tvSunday;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_time_list;
    }

    @Override
    protected void initDate() {
        initTopBar();
    }

    private void initTopBar() {
        tbTimeList.setTbCenterTv(R.string.ying_ye_shi_jian, R.color.color_white);
        tbTimeList.setTbLeftIv(R.drawable.img_icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_all, R.id.tv_monday, R.id.tv_tuesday, R.id.tv_wednesday, R.id.tv_thurday, R.id.tv_friday, R.id.tv_saturday, R.id.tv_sunday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all:

                break;
            case R.id.tv_monday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 1));
                break;
            case R.id.tv_tuesday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 2));
                break;
            case R.id.tv_wednesday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 3));
                break;
            case R.id.tv_thurday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 4));
                break;
            case R.id.tv_friday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 5));
                break;
            case R.id.tv_saturday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 6));
                break;
            case R.id.tv_sunday:
                startActivity(new Intent(TimeListActivity.this, ShopTimeConfigActivity.class).putExtra("date", 7));
                break;
        }
    }
}
