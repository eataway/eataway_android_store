package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.bigkoo.pickerview.TimePickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ShopTimeConfigActivity extends BaseActivity {
    @Bind(R.id.tv_shop_time_config_open)
    TextView tvShopTimeConfigOpen;
    @Bind(R.id.tv_activity_shop_time_config_open)
    TextView tvActivityShopTimeConfigOpen;
    @Bind(R.id.rl_activity_shop_time_config_open)
    RelativeLayout rlActivityShopTimeConfigOpen;
    @Bind(R.id.tv_shop_time_config_close)
    TextView tvShopTimeConfigClose;
    @Bind(R.id.tv_activity_shop_time_config_close)
    TextView tvActivityShopTimeConfigClose;
    @Bind(R.id.rl_activity_shop_time_config_close)
    RelativeLayout rlActivityShopTimeConfigClose;
    @Bind(R.id.tp_shop_time_config)
    TopBar tpShopTimeConfig;
    @Bind(R.id.tv_shop_time_config_second_open)
    TextView tvShopTimeConfigSecondOpen;
    @Bind(R.id.tv_activity_shop_time_config_second_open)
    TextView tvActivityShopTimeConfigSecondOpen;
    @Bind(R.id.rl_shop_time_second_open)
    RelativeLayout rlShopTimeSecondOpen;
    @Bind(R.id.tv_shop_time_config_second_close)
    TextView tvShopTimeConfigSecondClose;
    @Bind(R.id.tv_activity_shop_time_config_second_close)
    TextView tvActivityShopTimeConfigSecondClose;
    @Bind(R.id.rl_shop_time_second_close)
    RelativeLayout rlShopTimeSecondClose;
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    private TimePickerView timePickerView;
    private int week;
    private String firstOpen, firstClose, secondOpen, secondClose;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_time_config;
    }

    @Override
    protected void initDate() {
        week = getIntent().getIntExtra("date", -1);
        if (week == 0) {
            tvNotice.setVisibility(View.VISIBLE);
        }else {
            String openAndclose = getIntent().getStringExtra("openAndclose");
            if (!"".equals(openAndclose)) {
                String[] allTime = openAndclose.split(",");
                for (int i=0;i<allTime.length;i++) {
                    String[] time = allTime[i].split("-");
                    for (int j=0;j<time.length;j++) {
                        if (i == 0 && j == 0) {
                            tvActivityShopTimeConfigOpen.setText(time[j]);
                        }else if (i == 0 && j == 1) {
                            tvActivityShopTimeConfigClose.setText(time[j]);
                        }else if (i == 1 && j == 0) {
                            tvActivityShopTimeConfigSecondOpen.setText(time[j]);
                        }else if (i == 1 && j == 1) {
                            tvActivityShopTimeConfigSecondClose.setText(time[j]);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initMessage();
    }

    private void initTopBar() {
        tpShopTimeConfig.setTbCenterTv(R.string.pei_song_shi_jian, R.color.color_white);
        tpShopTimeConfig.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMessage() {
    }

    private String getTime(Date date) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }

    @OnClick({R.id.rl_activity_shop_time_config_open, R.id.rl_activity_shop_time_config_close, R.id.btn_activity_shop_timg_config_ok
            , R.id.rl_shop_time_second_open, R.id.rl_shop_time_second_close})
    public void onClick(View view) {
        switch (view.getId()) {
            //新增：
            case R.id.rl_shop_time_second_open:
                timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvActivityShopTimeConfigSecondOpen.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .build();
                timePickerView.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                timePickerView.show();
                break;
            case R.id.rl_shop_time_second_close:
                timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvActivityShopTimeConfigSecondClose.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .build();
                timePickerView.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                timePickerView.show();
                break;
            case R.id.rl_activity_shop_time_config_open:
                //时间选择器
                timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvActivityShopTimeConfigOpen.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .build();
                timePickerView.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                timePickerView.show();
                break;
            case R.id.rl_activity_shop_time_config_close:
                timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvActivityShopTimeConfigClose.setText(getTime(date));
                    }
                })
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .build();
                timePickerView.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                timePickerView.show();
                break;
            case R.id.btn_activity_shop_timg_config_ok:
                if (tvActivityShopTimeConfigOpen.getText().length() == 0 || tvShopTimeConfigSecondClose.getText().length() == 0 ||
                        (tvActivityShopTimeConfigSecondOpen.getText().length() == 0 && tvActivityShopTimeConfigSecondClose.getText().length() != 0) ||
                        tvActivityShopTimeConfigSecondOpen.getText().length() != 0 && tvActivityShopTimeConfigSecondClose.getText().length() == 0) {
                    ToastUtils.showToast(R.string.qing_tian_xie_wan_zheng_she_zhi_xin_xi, ShopTimeConfigActivity.this);
                    return;
                }
                showDialog();
                Login login = MyApplication.getLogin();
                if (login != null) {
                    HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITTIME) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            hidDialog();
                            Toast.makeText(ShopTimeConfigActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            hidDialog();
                            Log.i("gotime", "onResponse: " + response);
                            try {
                                JSONObject o = new JSONObject(response);
                                int status = o.getInt("status");
                                if (status == 0) {
                                    Toast.makeText(ShopTimeConfigActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                                } else if (status == 1) {
                                    Toast.makeText(ShopTimeConfigActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (status == 9) {
                                    Toast.makeText(ShopTimeConfigActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                                    MyApplication.saveLogin(null);
                                    goToActivity(LoginActivity.class);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
                    httpUtils.addParam("token", MyApplication.getLogin().getToken());
                    if (week != -1) {
                        httpUtils.addParam("week", week + "");
                    }
                    String time = tvActivityShopTimeConfigOpen.getText().toString().trim() + "-" +
                            tvActivityShopTimeConfigClose.getText().toString().trim();
                    if (tvActivityShopTimeConfigSecondOpen.getText().length() != 0) {
                        time = time + "," + tvActivityShopTimeConfigSecondOpen.getText().toString().trim() + "-" +
                                tvActivityShopTimeConfigSecondClose.getText().toString().trim();
                    }
                    Log.i("gotime", "gotime:" + time);
                    httpUtils.addParam("gotime", time);
                    httpUtils.clicent();
                } else {
                    hidDialog();
                    ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, ShopTimeConfigActivity.this);
                    goToActivity(LoginActivity.class);
                    finish();
                }
                break;
        }
    }
}
