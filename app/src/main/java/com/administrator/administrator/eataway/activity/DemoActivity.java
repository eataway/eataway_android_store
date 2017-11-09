package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.adapter.MenuListAdapter;
import com.administrator.administrator.eataway.bean.MenuListBean;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DemoActivity extends AppCompatActivity {

    @Bind(R.id.et_demo)
    EditText etDemo;
    @Bind(R.id.rv_demo)
    RecyclerView rvDemo;
    @Bind(R.id.btn_demo)
    Button btnDemo;
    @Bind(R.id.btn_demo2)
    Button btnDemo2;
    private MenuListAdapter adapter;
    private MenuListBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDemo.setLayoutManager(manager);
        adapter = new MenuListAdapter(this);
        rvDemo.setAdapter(adapter);
    }


    private void post() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL + "User/editcategoryceshi") {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
            }
        };
//                Locale.Category
        if (bean != null) {
            int k;
            for (int i = 0; i < bean.getMsg().size(); i++) {
                for (int j = 0; j < 3; j++) {
                    if (j == 0) {
                        httpUtils.addParam("category[" + i + "]" + "[" + j + "]", bean.getMsg().get(i).getCid());
                    } else if (j == 1) {
                        if (i == 0) {
                            httpUtils.addParam("category[" + i + "]" + "[" + j + "]", "-1");
                        } else {
                            k = i - 1;
                            httpUtils.addParam("category[" + i + "]" + "[" + j + "]", bean.getMsg().get(k).getCid());
                        }
                    } else if (j == 2) {
                        if (i == bean.getMsg().size() - 1) {
                            httpUtils.addParam("category[" + i + "]" + "[" + j + "]", "0");
                        } else {
                            k = i + 1;
                            httpUtils.addParam("category[" + i + "]" + "[" + j + "]", bean.getMsg().get(k).getCid());
                        }
                    }
                }
            }
            httpUtils.clicent();
        }
    }

    @OnClick({R.id.btn_demo, R.id.btn_demo2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_demo:
                HttpUtils httpUtils = new HttpUtils(Contants.URL_CATEGORYS) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToast(R.string.please_check_your_network_connection, DemoActivity.this);
//                Toast.makeText(_mActivity, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                bean = new Gson().fromJson(response, MenuListBean.class);
                                adapter.setData(bean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (etDemo.getText().length() != 0) {
                    httpUtils.addParam("shopid", etDemo.getText().toString());
                    httpUtils.clicent();
                }
                break;
            case R.id.btn_demo2:
                if (bean != null) {
                    post();
                }
                break;
        }
    }
}
