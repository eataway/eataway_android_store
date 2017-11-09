package com.administrator.administrator.eataway.activity;

import android.content.Intent;
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
import okhttp3.Call;

public class ReplyActivity extends BaseActivity {
    @Bind(R.id.tp_reply)
    TopBar tpReply;
    @Bind(R.id.et_activity_reply)
    EditText etActivityReply;
    @Bind(R.id.btn_reply_publication)
    Button btnReplyPublication;
    private String eid;
    private String userid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reply;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        eid = getIntent().getStringExtra("eid");
        userid = getIntent().getStringExtra("userid");
    }

    private void initTopBar() {
        tpReply.setTbCenterTv(R.string.hui_fu, R.color.color_white);
        tpReply.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnReplyPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReplyPublication.setClickable(false);
                btnReplyPublication.setBackgroundResource(R.drawable.shape_gray_btn);
                if (etActivityReply.getText()!=null&&!etActivityReply.getText().toString().trim().equals("")){
                    HttpUtils h=new HttpUtils(Contants.URL_BACKPINGJIA) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showToast(R.string.please_check_your_network_connection,mContext);
//                            Toast.makeText(mContext, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            btnReplyPublication.setBackgroundResource(R.drawable.shape_blue);
                            btnReplyPublication.setClickable(true);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            btnReplyPublication.setBackgroundResource(R.drawable.shape_blue);
                            btnReplyPublication.setClickable(true);
                            try {
                                JSONObject json=new JSONObject(response);
                                int status = json.getInt("status");
                                if (status == 1) {
                                    Toast.makeText(mContext, R.string.hui_fu_cheng_gong, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (status == 9) {
                                    Toast.makeText(ReplyActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                    MyApplication.saveLogin(null);
                                    startActivity(new Intent(ReplyActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(ReplyActivity.this, R.string.hui_fu_shi_bai, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    h.addParam("eid",eid).addParams("userid",userid).addParams("shopid", MyApplication.getLogin().getShopId())
                            .addParams("token", MyApplication.getLogin().getToken()).addParams("backpingjia",etActivityReply.getText().toString().trim());
                    h.clicent();
                }else {
                    Toast.makeText(ReplyActivity.this, R.string.nei_rong_bu_neng_wei_kong, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
