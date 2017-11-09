package com.administrator.administrator.eataway.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ShopAddressConfigActivity extends BaseActivity {
    @Bind(R.id.tp_address_config)
    TopBar tpAddressConfig;
    @Bind(R.id.tv_address_config_location)
    TextView tvAddressConfigLocation;
    @Bind(R.id.rl_address_config_location)
    RelativeLayout rlAddressConfigLocation;
    @Bind(R.id.et_address_config)
    EditText etAddressConfig;
    @Bind(R.id.btn_address_config_ok)
    Button btnAddressConfigOk;

    private Login login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_address_config;
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
        if (login != null && !"".equals(login.getLocation_text())) {
            tvAddressConfigLocation.setText(login.getLocation_text());
        }
    }

    private void initTopBar (){
        tpAddressConfig.setTbCenterTv(R.string.dian_pu_wei_zhi, R.color.color_white);
        tpAddressConfig.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpAddressConfig.setTbRightIv(R.drawable.img_icon_loocation, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.get(ShopAddressConfigActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION, getResources().getString(R.string.Request_location_permission));
                startActivityForResult(new Intent(ShopAddressConfigActivity.this,MapActivity.class),0);
            }
        });
    }

    @OnClick({R.id.rl_address_config_location, R.id.btn_address_config_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address_config_location:
                MyApplication.get(ShopAddressConfigActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION, getResources().getString(R.string.Request_location_permission));
                startActivityForResult(new Intent(ShopAddressConfigActivity.this,MapActivity.class),0);
                break;
            case R.id.btn_address_config_ok:
                HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITADDRESS) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(ShopAddressConfigActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                        ToastUtils.showToast(R.string.please_check_your_network_connection, ShopAddressConfigActivity.this);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject o = new JSONObject(response);
                            int status = o.getInt("status");
                            if (status == 0) {
                                Toast.makeText(ShopAddressConfigActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                            }else if (status == 1) {
                                Toast.makeText(ShopAddressConfigActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (status == 9) {
                                Toast.makeText(ShopAddressConfigActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (login != null) {
                    httpUtils.addParam("shopid", login.getShopId());
                    httpUtils.addParam("detailed_address", etAddressConfig.getText().toString().trim() + " " + login.getLocation_text());
                    httpUtils.addParam("coordinate", login.getLongitude() + "," + login.getLatitude());
                    httpUtils.addParam("token", login.getToken());
                    httpUtils.clicent();
                }
                break;
        }
    }
}
