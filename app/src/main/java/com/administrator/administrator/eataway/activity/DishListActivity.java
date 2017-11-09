package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.adapter.DishListAdapter;
import com.administrator.administrator.eataway.bean.GoodsListBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.view.BottomBar;
import com.administrator.administrator.eataway.view.TopBar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DishListActivity extends BaseActivity {
    @Bind(R.id.tp_dish_list)
    TopBar tpDishList;
    @Bind(R.id.rv_menu_detail)
    RecyclerView rvMenuDetail;
    DishListAdapter adapter;
    @Bind(R.id.bb_dish_list)
    BottomBar bbDishList;
    private String MenuName;
    private String id;
    private Login login= MyApplication.getLogin();
    private GoodsListBean bean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dish_list;
    }

    @Override
    protected void initDate() {
        if (id==null) {
            id = getIntent().getStringExtra("id");
        }
        HttpUtils httpUtils=new HttpUtils(Contants.URL_MENUGOODS) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(DishListActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status==1){
                        bean = new Gson().fromJson(response,GoodsListBean.class);
                        adapter = new DishListAdapter(DishListActivity.this,bean);
                        rvMenuDetail.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("page","1").addParams("cid",id).addParams("shopid",login.getShopId()).addParams("token",login.getToken());
        httpUtils.clicent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTopBar();
        initRecylerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    private void initRecylerView() {
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
        rvMenuDetail.setLayoutManager(manager);

    }

    private void initTopBar() {
        Intent i = getIntent();
        MenuName = i.getStringExtra("menuname");
        tpDishList.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tpDishList.setTbCenterTv(MenuName, R.color.color_white);
    }

    @OnClick(R.id.bb_dish_list)
    public void onClick() {
       startActivity(new Intent(this,DishAddActivity.class).putExtra("id",id));
    }
}
