package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.utils.ContactUtils;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class CertificationActivity extends BaseActivity {
    @Bind(R.id.rl_activity_certification_shop_name)
    RelativeLayout rlActivityCertificationShopName;
    @Bind(R.id.rl_activity_certification_shop_type)
    RelativeLayout rlActivityCertificationShopType;
    @Bind(R.id.rl_activity_certification_phone)
    RelativeLayout rlActivityCertificationPhone;
    @Bind(R.id.rl_activity_certification_shop_location)
    RelativeLayout rlActivityCertificationShopLocation;
    @Bind(R.id.btn_activity_certification_sign_out)
    Button btnActivityCertificationSignOut;
    @Bind(R.id.tv_activity_certification_contack)
    TextView tvActivityCertificationContack;
    @Bind(R.id.tp_certification)
    TopBar tpCertification;
    @Bind(R.id.tv_activity_certification_location)
    TextView tvActivityCertificationLocation;
    @Bind(R.id.tv_activity_certification_shop_name)
    TextView tvActivityCertificationShopName;
    @Bind(R.id.tv_activity_certification_type)
    TextView tvActivityCertificationType;
    @Bind(R.id.tv_activity_certification_phone)
    TextView tvActivityCertificationPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initMessage();
    }

    private void initTopBar() {
        tpCertification.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpCertification.setTbCenterTv(R.string.ren_zheng_zhuang_tai, R.color.color_white);
    }

    private void initMessage() {
        if (MyApplication.getLogin().getCertification() == 4) {
            btnActivityCertificationSignOut.setBackgroundResource(R.drawable.shape_gray_btn);
            btnActivityCertificationSignOut.setClickable(false);
            tvActivityCertificationType.setText(R.string.shen_he_zhong);
        }else {
            tvActivityCertificationType.setText(R.string.yi_ren_zheng);
        }
        tvActivityCertificationShopName.setText(MyApplication.getLogin().getShopName());
        tvActivityCertificationPhone.setText(MyApplication.getLogin().getPhone());
        tvActivityCertificationLocation.setText(MyApplication.getLogin().getAddress());
    }

    private void SignOut() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_OUT) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(CertificationActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 1) {
                        Toast.makeText(CertificationActivity.this, R.string.qing_qiu_cheng_gong, Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (status == 0) {
                        Toast.makeText(CertificationActivity.this, R.string.qing_qiu_shi_bai, Toast.LENGTH_SHORT).show();
                    }else if (status == 9) {
                        Toast.makeText(CertificationActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.clicent();
    }

    @OnClick({R.id.btn_activity_certification_sign_out, R.id.tv_activity_certification_contack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_certification_sign_out:
                SignOut();
                break;
            case R.id.tv_activity_certification_contack:
                ContactUtils.ContactUS(CertificationActivity.this);
                break;
        }
    }
}
