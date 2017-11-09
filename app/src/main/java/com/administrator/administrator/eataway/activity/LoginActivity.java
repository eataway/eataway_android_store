package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.CipherUtils;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_activity_login_usernumber)
    EditText etActivityLoginUserunmber;
    @Bind(R.id.et_activity_login_password)
    EditText etActivityLoginPassword;
    @Bind(R.id.tv_activity_login_forget_password)
    TextView tvActivityLoginForgetPassword;
    @Bind(R.id.tv_activity_login_click_release)
    TextView tvActivityLoginClickRelease;
    @Bind(R.id.btn_activity_login_login)
    Button btnActivityLoginLogin;
    @Bind(R.id.ll_activity_language)
    LinearLayout llActivityLanguage;
    @Bind(R.id.tp_login)
    TopBar tpLogin;

    private HttpUtils httpUtils;
    private String tag = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
//        Intent i = getIntent();
//        tag = i.getStringExtra("tag");
    }

    private void initTopBar() {
        tpLogin.setBackGround(R.color.color_gray);
        tpLogin.setTbCenterTv(R.string.login, R.color.text_black);
        tpLogin.setTbLeftIv(R.drawable.img_back_black, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Login() {
        httpUtils = new HttpUtils(Contants.URL_LOGIN) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(LoginActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                btnActivityLoginLogin.setClickable(true);
                btnActivityLoginLogin.setBackgroundResource(R.drawable.shape_blue);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 1) {
                        String shopid = object.getString("shopid");
                        String token = object.getString("token");
                        Login login = new Login();
                        login.setShopId(shopid);
                        login.setToken(token);
                        MyApplication.saveLogin(login);
                        JPushInterface.setAlias(LoginActivity.this, Integer.parseInt(MyApplication.getLogin().getShopId()),MyApplication.getLogin().getShopId());//getLogin().getShopName()
                        Toast.makeText(LoginActivity.this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                        goToActivity(MainActivity.class);
                        finish();
                    } else if (status == 0) {
                        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    } else if (status == 9) {
                        Toast.makeText(LoginActivity.this, R.string.The_phone_number_cannot_empty, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.Format_error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if(btnActivityLoginLogin != null) {
                        btnActivityLoginLogin.setClickable(true);
                        btnActivityLoginLogin.setBackgroundResource(R.drawable.shape_blue);
                    }
                }
            }
        };
        httpUtils.addParam("phone", etActivityLoginUserunmber.getText().toString().trim());
        httpUtils.addParam("password", CipherUtils.md5(etActivityLoginPassword.getText().toString().trim() + "EatAway"));
        httpUtils.clicent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 1) {
                etActivityLoginUserunmber.setText(data.getStringExtra("number"));
            }
        }
    }

    @OnClick({R.id.tv_activity_login_forget_password, R.id.tv_activity_login_click_release, R.id.btn_activity_login_login, R.id.ll_activity_language})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_login_forget_password:
                startActivityForResult(new Intent(LoginActivity.this, ResetPwdActivity.class), 0);
                break;
            case R.id.tv_activity_login_click_release:
                startActivityForResult(new Intent(LoginActivity.this, ReleaseActivity.class), 0);
                break;
            case R.id.btn_activity_login_login:
                btnActivityLoginLogin.setClickable(false);
                btnActivityLoginLogin.setBackgroundResource(R.drawable.shape_gray_btn);
                if (etActivityLoginUserunmber.getText().length()==0 || etActivityLoginPassword.getText().length()==0){
                    Toast.makeText(this, R.string.deng_lu_xin_xi_bu_neng_wei_kong, Toast.LENGTH_SHORT).show();
                    btnActivityLoginLogin.setClickable(true);
                    btnActivityLoginLogin.setBackgroundResource(R.drawable.shape_blue);
                }else {
                    Login();
                }
                break;
            case R.id.ll_activity_language:
                if (MyApplication.isEnglish) {
                    BaseActivity.setLanguage(false, this);
                    MyApplication.isEnglish = false;
                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    BaseActivity.setLanguage(true, this);
                    MyApplication.isEnglish = true;
                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
        }
    }
}
