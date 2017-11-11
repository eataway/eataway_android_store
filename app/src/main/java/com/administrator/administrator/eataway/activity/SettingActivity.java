package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class SettingActivity extends BaseActivity {
    @Bind(R.id.tp_activity_setting)
    TopBar tpActivitySetting;
    @Bind(R.id.rl_activity_setting_about_us)
    RelativeLayout rlActivitySettingAboutUs;
    @Bind(R.id.btn_activity_setting_sign_out)
    Button btnActivitySettingSignOut;
//    @Bind(R.id.tv_yuyan)
//    TextView tvYuyan;
//    @Bind(R.id.rl_activity_setting_yuyan)
//    RelativeLayout rlActivitySettingYuyan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        if (MyApplication.getLogin() == null) {
            btnActivitySettingSignOut.setVisibility(View.GONE);
        } else {
            btnActivitySettingSignOut.setVisibility(View.VISIBLE);
        }
    }

    private void initTopBar() {
        tpActivitySetting.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpActivitySetting.setTbCenterTv(R.string.xi_tong_she_zhi, R.color.color_white);
    }

    @OnClick({R.id.rl_activity_setting_about_us, R.id.btn_activity_setting_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_setting_about_us:
                goToActivity(AboutUsActivity.class);
                break;
            case R.id.btn_activity_setting_sign_out:
                showDialog();
                HttpUtils httpUtils = new HttpUtils(Contants.URL_LOGOUT) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hidDialog();
                        ToastUtils.showToast(R.string.please_check_your_network_connection,SettingActivity.this);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hidDialog();
                        try {
                            JSONObject o = new JSONObject(response);
                            int status = o.getInt("status");
                            if (status == 0) {
                                Toast.makeText(SettingActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            }else if (status == 9) {
                                Toast.makeText(SettingActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                                goToActivity(LoginActivity.class);
                                finish();
                            }else if (status == 1) {
                                Toast.makeText(SettingActivity.this, R.string.ting_zhi_ying_ye, Toast.LENGTH_SHORT).show();
//                                JPushInterface.deleteAlias(SettingActivity.this, Integer.parseInt(MyApplication.getLogin().getShopId()));
                                MyApplication.saveLogin(null);
//                                goToActivity(LoginActivity.class);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
                httpUtils.addParam("token", MyApplication.getLogin().getToken());
                httpUtils.clicent();
                break;
        }
    }

//    @OnClick(R.id.rl_activity_setting_yuyan)
//    public void onClick() {
//        if (MyApplication.isEnglish) {
//            tvYuyan.setText("中文");
//            BaseActivity.setLanguage(false, this);
//            MyApplication.isEnglish = false;
//            Intent i = new Intent(SettingActivity.this, SettingActivity.class);
//            startActivity(i);
//            finish();
//        }else {
//            tvYuyan.setText("English");
//            BaseActivity.setLanguage(true, this);
//            MyApplication.isEnglish = true;
//            Intent i = new Intent(SettingActivity.this, SettingActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }
}
