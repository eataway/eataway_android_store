package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.adapter.ShopIncomeAdapter;
import com.administrator.administrator.eataway.bean.IncomeBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ShopIncomeAcitivity extends BaseActivity {

    @Bind(R.id.rv_shop_income)
    RecyclerView rvShopIncome;
    @Bind(R.id.tp_shop_income)
    TopBar tpShopIncome;
    @Bind(R.id.rl_newsdetails)
    RelativeLayout rlNewsdetails;

    private int page = 1;
    private ShopIncomeAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_income_acitivity;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRecylerView();
        initTopBar();
        if (MyApplication.getLogin() != null) {
            page = 1;
            loadData();
        }else {
            Toast.makeText(mContext, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        showDialog();
        HttpUtils httpUtils = new HttpUtils(Contants.URL_MINGXI) {
            @Override
            public void onError(Call call, Exception e, int id) {
                hidDialog();
                ToastUtils.showToast(R.string.please_check_your_network_connection,ShopIncomeAcitivity.this);
            }

            @Override
            public void onResponse(String response, int id) {
                hidDialog();
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        Toast.makeText(ShopIncomeAcitivity.this, R.string.huo_qu_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 9) {
                        Toast.makeText(ShopIncomeAcitivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                    } else if (status == 1) {
                        IncomeBean bean = new Gson().fromJson(response, IncomeBean.class);
                        if (bean != null && bean.getMsg().size() == 0) {
                            if (page == 1)
                                ToastUtils.showToast(R.string.mei_shou_ru,ShopIncomeAcitivity.this);
//                                Toast.makeText(ShopIncomeAcitivity.this, R.string.mei_shou_ru, Toast.LENGTH_SHORT).show();
                            else
                                ToastUtils.showToast(R.string.mei_you_shu_ju_le,ShopIncomeAcitivity.this);
//                                Toast.makeText(ShopIncomeAcitivity.this, R.string.mei_you_shu_ju_le, Toast.LENGTH_SHORT).show();
                        }
                        if (adapter == null) {
                            adapter = new ShopIncomeAdapter(ShopIncomeAcitivity.this, bean);
                            adapter.setListener(new ShopIncomeAdapter.moreListener() {
                                @Override
                                public void click() {
                                    loadData();
                                }
                            });
                            rvShopIncome.setAdapter(adapter);
                        } else {
                            if (page == 1)
                                adapter.setBean(bean);
                            else
                                adapter.addBean(bean);
                            adapter.notifyDataSetChanged();
                        }
                        page++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken())
                .addParams("page", page + "");
        httpUtils.clicent();
    }

    private void initTopBar() {
        tpShopIncome.setTbCenterTv(R.string.shou_ru_ming_xi, R.color.color_white);
        tpShopIncome.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpShopIncome.setTbRightIv(R.drawable.img_refresh_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    rvShopIncome.setVisibility(View.GONE);
                    rlNewsdetails.setVisibility(View.VISIBLE);
                    page = 1;
                    loadData();
                }
            }
        });
    }

    private void initRecylerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvShopIncome.setLayoutManager(manager);
    }
}
