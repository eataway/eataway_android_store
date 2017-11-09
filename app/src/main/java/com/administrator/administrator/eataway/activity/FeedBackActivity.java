package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import okhttp3.Call;

public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.tp_activity_feed_back)
    TopBar tpActivityFeedBack;
    @Bind(R.id.et_activity_feed_back)
    EditText etActivityFeedBack;
    @Bind(R.id.btn_activity_feed_back)
    Button btnActivityFeedBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
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

    private void initTopBar() {
        tpActivityFeedBack.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpActivityFeedBack.setTbCenterTv(R.string.yi_jian_fan_kui, R.color.color_white);
    }

    @OnClick(R.id.btn_activity_feed_back)
    public void onClick() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_SHOP_BACK) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(R.string.please_check_your_network_connection,FeedBackActivity.this);
//                Toast.makeText(FeedBackActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        Toast.makeText(FeedBackActivity.this, R.string.Give_FeedBack_unsuccessfully, Toast.LENGTH_SHORT).show();
                    }else if (status == 1){
                        Toast.makeText(FeedBackActivity.this, R.string.thank_you_for_comments, Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (status == 9) {
                        Toast.makeText(FeedBackActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("backmessage", etActivityFeedBack.getText().toString().trim());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.clicent();
        finish();
    }
}
