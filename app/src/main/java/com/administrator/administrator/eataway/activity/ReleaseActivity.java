package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ReleaseActivity extends BaseActivity implements OnSendMessageHandler {

    @Bind(R.id.tp_release)
    TopBar tpRelease;
    @Bind(R.id.et_activity_release_usernum)
    EditText etActivityReleaseUsernum;
    @Bind(R.id.iv_activity_release_code)
    ImageView ivActivityReleaseCode;
    @Bind(R.id.et_activity_release_code)
    EditText etActivityReleaseCode;
    @Bind(R.id.et_activity_release_password)
    EditText etActivityReleasePassword;
    @Bind(R.id.et_activity_release_confirm)
    EditText etActivityReleaseConfirm;
    @Bind(R.id.tv_activity_release_click_release)
    TextView tvActivityReleaseClickRelease;
    @Bind(R.id.btn_activity_release_release)
    Button btnActivityReleaseRelease;
    @Bind(R.id.cb_activity_release_agree)
    CheckBox cbActivityReleaseAgree;
    @Bind(R.id.ll_activity_language)
    LinearLayout llActivityLanguage;
    @Bind(R.id.btn_activity_release_code)
    Button btnActivityReleaseCode;
    @Bind(R.id.tv_agree)
    TextView tvAgree;

    private EventHandler eventHandler;
    private TimeCount time;
    private HttpUtils httpUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release;
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
                            Toast.makeText(ReleaseActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Release();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time = new TimeCount(60000, 1000);
                                time.start();
                                Toast.makeText(ReleaseActivity.this, R.string.get_the_captcha_successfully, Toast.LENGTH_SHORT).show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(ProtocolAcitvity.class);
            }
        });

        cbActivityReleaseAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnActivityReleaseRelease.setBackgroundResource(R.drawable.shape_blue);
                    btnActivityReleaseRelease.setClickable(true);
                } else {
                    btnActivityReleaseRelease.setBackgroundResource(R.drawable.shape_gray_btn);
                    btnActivityReleaseRelease.setClickable(false);
                }
            }
        });
        tvActivityReleaseClickRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /**
     * 辅助函数
     */
    private void initTopBar() {
        tpRelease.setBackGround(R.color.color_gray);
        tpRelease.setTbCenterTv(R.string.release, R.color.text_black);
        tpRelease.setTbLeftIv(R.drawable.img_back_black, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //发送验证码
    private void initMssage() {
        Log.i("mysend", "send");
        SMSSDK.getVerificationCode("61", etActivityReleaseUsernum.getText().toString().trim(), this);
    }

    //验证验证码
    private void checkCode() {
        SMSSDK.submitVerificationCode("61", etActivityReleaseUsernum.getText().toString().trim(), etActivityReleaseCode.getText().toString().trim());
    }

    private void Release() {
        httpUtils = new HttpUtils(Contants.URL_RELEASE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(R.string.please_check_your_network_connection,ReleaseActivity.this);
//                Toast.makeText(ReleaseActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 1) {
                        String userid = object.getString("shopid");
                        String token = object.getString("token");
                        Login login = new Login();
                        login.setShopId(userid);
                        login.setToken(token);
                        MyApplication.saveLogin(login);
                        Toast.makeText(ReleaseActivity.this, R.string.release_successful, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.putExtra("number", etActivityReleaseUsernum.getText().toString().trim());
                        setResult(1, i);
                        finish();
                    } else if (status == 0) {
                        Toast.makeText(ReleaseActivity.this, R.string.release_failed, Toast.LENGTH_SHORT).show();
                    } else if (status == 2) {
                        Toast.makeText(ReleaseActivity.this, R.string.The_phone_number_has_been_registered, Toast.LENGTH_SHORT).show();
                    } else if (status == 4) {
                        Toast.makeText(ReleaseActivity.this, R.string.The_phone_number_cannot_empty, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ReleaseActivity.this, R.string.Format_error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    btnActivityReleaseRelease.setClickable(true);
                    btnActivityReleaseRelease.setBackgroundResource(R.drawable.shape_blue);
                }
            }
        };
        httpUtils.addParam("phone", etActivityReleaseUsernum.getText().toString().trim());
        httpUtils.addParam("password", CipherUtils.md5(etActivityReleasePassword.getText().toString().trim() + "EatAway"));
        httpUtils.clicent();
    }

    private void checkPhone() {
        httpUtils = new HttpUtils(Contants.URL_VERL_PHONE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(ReleaseActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if ("3".equals(status)) {
                        initMssage();
                    } else if ("2".equals(status)) {
                        Toast.makeText(ReleaseActivity.this, R.string.The_phone_number_has_been_registered, Toast.LENGTH_SHORT).show();
                    } else if ("4".equals(status)) {
                        Toast.makeText(ReleaseActivity.this, R.string.The_phone_number_cannot_empty, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ReleaseActivity.this, R.string.Format_error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("phone", etActivityReleaseUsernum.getText().toString().trim());
        httpUtils.clicent();
    }

    @OnClick(R.id.ll_activity_language)
    public void onClick() {
        if (MyApplication.isEnglish) {
            BaseActivity.setLanguage(false, this);
            MyApplication.isEnglish = false;
            Intent i = new Intent(ReleaseActivity.this, ReleaseActivity.class);
            startActivity(i);
            finish();
        } else {
            BaseActivity.setLanguage(true, this);
            MyApplication.isEnglish = true;
            Intent i = new Intent(ReleaseActivity.this, ReleaseActivity.class);
            startActivity(i);
            finish();
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
            btnActivityReleaseCode.setBackgroundResource(R.drawable.shape_gray_half_corner_sel);
            btnActivityReleaseCode.setClickable(false);
            btnActivityReleaseCode.setText(getResources().getText(R.string.chong_xin_fa_song) + "(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            btnActivityReleaseCode.setText(getResources().getText(R.string.get_verification_code));
            btnActivityReleaseCode.setClickable(true);
            btnActivityReleaseCode.setBackgroundResource(R.drawable.shape_gray_half_corner_default);
        }
    }

    @Override
    public boolean onSendMessage(String s, String s1) {
        return false;
    }

    @OnClick({R.id.btn_activity_release_code, R.id.btn_activity_release_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_release_code:
                checkPhone();
                break;
            case R.id.btn_activity_release_release:
                if (etActivityReleaseUsernum.getText().length() == 0 || etActivityReleaseCode.getText().length() == 0 ||
                        etActivityReleaseConfirm.getText().length() == 0 || etActivityReleasePassword.getText().length() == 0) {
                    Toast.makeText(this, R.string.zhu_ce_xin_xi_bu_neng_wei_kong, Toast.LENGTH_SHORT).show();
                } else if (etActivityReleasePassword.getText().length() < 6) {
                    Toast.makeText(this, R.string.mi_ma_chang_du, Toast.LENGTH_SHORT).show();
                    btnActivityReleaseRelease.setClickable(true);
                    btnActivityReleaseRelease.setBackgroundResource(R.drawable.shape_blue);
                } else if (!etActivityReleasePassword.getText().toString().equals(etActivityReleaseConfirm.getText().toString())){
                    Toast.makeText(this, R.string.the_password_input_is_inconsistent_twice, Toast.LENGTH_SHORT).show();
                    btnActivityReleaseRelease.setClickable(true);
                    btnActivityReleaseRelease.setBackgroundResource(R.drawable.shape_blue);
                }else {
                    btnActivityReleaseRelease.setClickable(false);
                    btnActivityReleaseRelease.setBackgroundResource(R.drawable.shape_gray_btn);
                    checkCode();
                }
                break;
        }
    }
}
