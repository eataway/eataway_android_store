package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.adapter.TimeListShowAdapter;
import com.administrator.administrator.eataway.bean.ShopTimeBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import okhttp3.Call;

public class TimeListShowActivity extends BaseActivity {
    @Bind(R.id.tp_time_list_show)
    TopBar tpTimeListShow;
    @Bind(R.id.rv_time_list_show)
    RecyclerView rvTimeListShow;

    private TimeListShowAdapter adapter;
    private Login login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_time_list_show;
    }

    @Override
    protected void initDate() {
        tpTimeListShow.setTbCenterTv(R.string.ying_ye_shi_jian, R.color.color_white);
        tpTimeListShow.setTbLeftIv(R.drawable.img_icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpTimeListShow.setTbRightIv(R.drawable.img_icon_modify_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //总体营业时间修改
                startActivity(new Intent(TimeListShowActivity.this, ShopTimeConfigActivity.class).putExtra("date", 0));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog();
        getTimeData();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTimeListShow.setLayoutManager(manager);
        adapter = new TimeListShowAdapter(this);
        rvTimeListShow.setAdapter(adapter);
    }

    private void getTimeData() {
        login = MyApplication.getLogin();
        if (login != null) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_GET_BUSSINESS_TIME) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    hidDialog();
                    ToastUtils.showToast(R.string.please_check_your_network_connection, TimeListShowActivity.this);
                }

                @Override
                public void onResponse(String response, int id) {
                    hidDialog();
                    try {
                        JSONObject o = new JSONObject(response);
                        int status = o.getInt("status");
                        if (status == 1) {
                            ShopTimeBean bean = new Gson().fromJson(response, ShopTimeBean.class);
                            adapter.setData(bean);
                        }else if (status == 0){

                        }else {
                            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, TimeListShowActivity.this);
                            MyApplication.saveLogin(null);
                            goToActivity(LoginActivity.class);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("shopid", login.getShopId());
            httpUtils.addParam("token", login.getToken());
            httpUtils.clicent();
        }else {
            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, TimeListShowActivity.this);
            goToActivity(LoginActivity.class);
            finish();
        }
    }
}
