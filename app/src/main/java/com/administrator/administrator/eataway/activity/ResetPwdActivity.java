package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.utils.CipherUtils;
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

public class ResetPwdActivity extends BaseActivity implements OnSendMessageHandler {
    @Bind(R.id.tp_reset)
    TopBar tpReset;
    @Bind(R.id.et_activity_reset_usernum)
    EditText etActivityResetUsernum;
    @Bind(R.id.iv_activity_reset_code)
    ImageView ivActivityResetCode;
    @Bind(R.id.et_activity_reset_code)
    EditText etActivityResetCode;
    @Bind(R.id.btn_activity_reset_code)
    Button btnActivityResetCode;
    @Bind(R.id.et_activity_reset_password)
    EditText etActivityResetPassword;
    @Bind(R.id.et_activity_reset_confirm)
    EditText etActivityResetConfirm;
    @Bind(R.id.btn_activity_reset_reset)
    Button btnActivityResetReset;
    private EventHandler eventHandler;
    private TimeCount time;
    private HttpUtils httpUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void initDate() {
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(final int event, int result, final Object data) {
                if (data instanceof Throwable) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Throwable throwable = (Throwable) data;
                            String msg = throwable.getMessage();
                            Toast.makeText(ResetPwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Reset();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time = new TimeCount(60000, 1000);
                                time.start();
                                Toast.makeText(ResetPwdActivity.this, R.string.get_the_captcha_successfully, Toast.LENGTH_SHORT).show();
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
        tpReset.setTbCenterTv(R.string.chong_zhi_mi_ma, R.color.color_white);
        tpReset.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //发送验证码
    private void initMssage() {
        Log.i("mysend", "send");
        SMSSDK.getVerificationCode("61", etActivityResetUsernum.getText().toString().trim(), this);
    }

    //验证验证码
    private void checkCode() {
        SMSSDK.submitVerificationCode("61", etActivityResetUsernum.getText().toString().trim(), etActivityResetCode.getText().toString().trim());
    }

    private void Reset() {
        httpUtils = new HttpUtils(Contants.URL_RESETPWD) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(R.string.please_check_your_network_connection,ResetPwdActivity.this);
//                Toast.makeText(ResetPwdActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 1) {
                        Toast.makeText(ResetPwdActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.putExtra("number", etActivityResetUsernum.getText().toString().trim());
                        setResult(1, i);
                        finish();
                    } else if (status == 0) {
                        Toast.makeText(ResetPwdActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 2) {
                        Toast.makeText(ResetPwdActivity.this, R.string.gai_shou_ji_wei_zhu_ce, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("phone", etActivityResetUsernum.getText().toString().trim());
        httpUtils.addParam("password", CipherUtils.md5(etActivityResetPassword.getText().toString().trim() + "EatAway"));
        httpUtils.clicent();
    }

    private void checkPhone() {
        httpUtils = new HttpUtils(Contants.URL_VERL_PHONE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(ResetPwdActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if ("3".equals(status)) {
                        initMssage();
                    } else if ("2".equals(status)) {
                        Toast.makeText(ResetPwdActivity.this, R.string.The_phone_number_has_been_registered, Toast.LENGTH_SHORT).show();
                    } else if ("4".equals(status)) {
                        Toast.makeText(ResetPwdActivity.this, R.string.The_phone_number_cannot_empty, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResetPwdActivity.this, R.string.Format_error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("phone", etActivityResetUsernum.getText().toString().trim());
        httpUtils.clicent();
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
            btnActivityResetCode.setBackgroundResource(R.drawable.shape_gray_half_corner_sel);
            btnActivityResetCode.setClickable(false);
            btnActivityResetCode.setText(getResources().getText(R.string.chong_xin_fa_song) + "(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            btnActivityResetCode.setText(getResources().getText(R.string.get_verification_code));
            btnActivityResetCode.setClickable(true);
            btnActivityResetCode.setBackgroundResource(R.drawable.shape_gray_half_corner_default);
        }
    }

    @OnClick({R.id.btn_activity_reset_reset, R.id.btn_activity_reset_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_reset_reset:
                if (etActivityResetPassword.getText().length() == 0 || etActivityResetConfirm.getText().length() == 0
                        || etActivityResetCode.getText().length() == 0 || etActivityResetUsernum.getText().length() == 0) {
                    Toast.makeText(this, R.string.zhu_ce_xin_xi_bu_neng_wei_kong, Toast.LENGTH_SHORT).show();
                } else if (etActivityResetPassword.getText().length()<6) {
                    Toast.makeText(this, R.string.mi_ma_chang_du, Toast.LENGTH_SHORT).show();
                } else if (!etActivityResetConfirm.getText().toString().equals(etActivityResetPassword.getText().toString())) {
                    Toast.makeText(this, R.string.the_password_input_is_inconsistent_twice, Toast.LENGTH_SHORT).show();
                } else {
                    checkCode();
                }
                break;
            case R.id.btn_activity_reset_code:
                initMssage();
                break;
        }
    }
}
