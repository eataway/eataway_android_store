package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
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
import com.administrator.administrator.eataway.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class FeeAddActivity extends BaseActivity {
    @Bind(R.id.tp_fee_add)
    TopBar tpFeeAdd;
    @Bind(R.id.tv_fee_add_minprice)
    TextView tvFeeAddMinprice;
    @Bind(R.id.rl_fee_add_minprice)
    RelativeLayout rlFeeAddMinprice;
    @Bind(R.id.tv_fee_add_maxdistance)
    TextView tvFeeAddMaxdistance;
    @Bind(R.id.rl_fee_add_maxdistance)
    RelativeLayout rlFeeAddMaxdistance;

    private Login login;
    private HttpUtils httpUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fee_add;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        login = MyApplication.getLogin();
        if (login != null) {
            if (MyApplication.getLogin().getMaxPrice() < 0) {
                rlFeeAddMinprice.setVisibility(View.VISIBLE);
            }else {
                rlFeeAddMinprice.setVisibility(View.GONE);
            }
            if (MyApplication.getLogin().getMaxLong() < 0) {
                rlFeeAddMaxdistance.setVisibility(View.VISIBLE);
            }else {
                rlFeeAddMaxdistance.setVisibility(View.GONE);
            }
        }
    }

    private void initTopBar() {
        tpFeeAdd.setTbCenterTv(R.string.pei_song_gui_ze, R.color.color_white);
        tpFeeAdd.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changeRule(String url, final String param, final String content) {
        httpUtils = new HttpUtils(url) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(FeeAddActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        Toast.makeText(FeeAddActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                    }else if (status == 1) {
                        Toast.makeText(FeeAddActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                        login = MyApplication.getLogin();
                        if (param.equals("maxprice")) {
                            login.setMaxPrice(Double.parseDouble(content));
                        }else if (param.equals("maxlong")) {
                            login.setMaxLong(Double.parseDouble(content));
                            login.setMaxMoney(Double.parseDouble(content));
                        }else {
                            login.setLkMoney(Double.parseDouble(content));
                        }
                        MyApplication.saveLogin(login);
                    }else if (status == 9) {
                        Toast.makeText(FeeAddActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        if (param.equals("maxlong")) {
            httpUtils.addParam("maxlong", "0");
        }else if (param.equals("maxmoney")) {
            httpUtils.addParam("maxmoney", "0");
        }
        httpUtils.addParam(param, content);
        httpUtils.clicent();
    }

    @OnClick({R.id.tv_fee_add_minprice, R.id.rl_fee_add_minprice,
            R.id.tv_fee_add_maxdistance, R.id.rl_fee_add_maxdistance})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fee_add_minprice:
            case R.id.rl_fee_add_minprice:
                changeRule(Contants.URL_EDITMINPRICE, "maxprice", "0");
                break;
            case R.id.tv_fee_add_maxdistance:
            case R.id.rl_fee_add_maxdistance:
                changeRule(Contants.URL_EDITMAXDISTANCE, "maxlong", "0");
                break;
        }
    }
}
