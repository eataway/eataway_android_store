package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;

public class ShopPhoneConfigActivity extends BaseActivity implements OnSendMessageHandler {
    @Bind(R.id.top_bar_phonechange)
    TopBar topBarPhonechange;
    @Bind(R.id.et_phone_change_number)
    EditText etPhoneChangeNumber;
    @Bind(R.id.et_phone_change_code)
    EditText etPhoneChangeCode;
    @Bind(R.id.btn_send_again)
    Button btnSendAgain;
    @Bind(R.id.btn_phone_change_confim)
    Button btnPhoneChangeConfim;

    private String orignalNumber = "";
    private String submitNumber = "";
    private String submitCode = "";
    private EventHandler eventHandler;

    private long reSendTime = -1;
    private TimeCount time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_phone_config;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void initTopBar() {
        topBarPhonechange.setTbCenterTv(R.string.xiu_gai_shou_ji_hao, R.color.color_white);
        topBarPhonechange.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.btn_send_again, R.id.btn_phone_change_confim})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_again:
                submitNumber = etPhoneChangeNumber.getText().toString().trim();
                HttpUtils httpUtils = new HttpUtils(Contants.URL_VERL_PHONE) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToast(R.string.please_check_your_network_connection,ShopPhoneConfigActivity.this);
//                        Toast.makeText(ShopPhoneConfigActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");
                            if ("3".equals(status)) {
                                initMssage();
                            }else if ("2".equals(status)) {
                                Toast.makeText(ShopPhoneConfigActivity.this, R.string.The_phone_number_has_been_registered, Toast.LENGTH_SHORT).show();
                            }else if ("0".equals(status)) {
                                Toast.makeText(ShopPhoneConfigActivity.this, R.string.validation_fails, Toast.LENGTH_SHORT).show();
                            }else if ("4".equals(status)) {
                                Toast.makeText(ShopPhoneConfigActivity.this, R.string.Enter_empty, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ShopPhoneConfigActivity.this, R.string.Format_error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("phone", submitNumber);
                httpUtils.clicent();
                break;
            case R.id.btn_phone_change_confim:
                submitCode = etPhoneChangeCode.getText().toString().trim();
                checkCode();
                break;
        }
    }

    /**
     * 辅助类
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            reSendTime = millisUntilFinished;
            btnSendAgain.setBackgroundColor(getResources().getColor(R.color.color_gray));
            btnSendAgain.setClickable(false);
            btnSendAgain.setText(getResources().getText(R.string.person_resend) + "(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            btnSendAgain.setText(getResources().getText(R.string.person_resend));
            btnSendAgain.setClickable(true);
            btnSendAgain.setBackgroundColor(getResources().getColor(R.color.color_blue));
        }
    }


    /**
     * 辅助函数
     */
    //发送验证码
    private void initMssage() {
        Log.i("mysend", "send");
        SMSSDK.getVerificationCode("61", submitNumber, this);
    }

    //验证验证码
    private void checkCode() {
        SMSSDK.submitVerificationCode("61", submitNumber, submitCode);
    }

    //更改手机号请求
    private void changePhoneNumber() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_EDITPHONE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(ShopPhoneConfigActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 0) {
                        Toast.makeText(mContext, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(mContext, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                        Login login = MyApplication.getLogin();
                        login.setPhone(submitNumber);
                        MyApplication.saveLogin(login);
                        startActivity(new Intent(ShopPhoneConfigActivity.this, ShopDetailActivity.class));
                        finish();
                    } else if (status == 9) {
                        Toast.makeText(mContext, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        goToActivity(LoginActivity.class);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.addParam("mobile", submitNumber);
        httpUtils.clicent();
    }

    @Override
    protected void initDate() {
        initTopBar();
        orignalNumber = MyApplication.getLogin().getPhone();
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(final int event, int result, final Object data) {
                if (data instanceof Throwable) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Throwable throwable = (Throwable) data;
                            String msg = throwable.getMessage();
                            Toast.makeText(ShopPhoneConfigActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        changePhoneNumber();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (time == null) time = new TimeCount(60000, 1000);
                                time.start();
                                Toast.makeText(ShopPhoneConfigActivity.this, R.string.get_the_captcha_successfully, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public boolean onSendMessage(String s, String s1) {
        return false;
    }
}
