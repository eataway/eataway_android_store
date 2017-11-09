package com.administrator.administrator.eataway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.bean.GoodsBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.GlideUtils;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DishDetailActivity extends BaseActivity {
    @Bind(R.id.iv_activity_dish_detail_shop_pic)
    ImageView ivActivityDishDetailShopPic;
    @Bind(R.id.iv_dish_detail_back)
    ImageView ivDishDetailBack;
    @Bind(R.id.iv_dish_detail_modify)
    ImageView ivDishDetailModify;
    @Bind(R.id.rl_dish_detail_title)
    RelativeLayout rlDishDetailTitle;
    @Bind(R.id.tv_activity_dish_detail_sold_num)
    TextView tvActivityDishDetailSoldNum;
    @Bind(R.id.tv_activity_dish_detail_dish_name)
    TextView tvActivityDishDetailDishName;
    @Bind(R.id.tv_activity_dish_detail_dish_price)
    TextView tvActivityDishDetailDishPrice;
    @Bind(R.id.tv_activity_dish_detail_dish_menu)
    TextView tvActivityDishDetailDishMenu;
    @Bind(R.id.tv_dish_detail_description_tag)
    TextView tvDishDetailDescriptionTag;
    @Bind(R.id.btn_activity_dish_detail_delete)
    Button btnActivityDishDetailDelete;
    @Bind(R.id.btn_activity_dish_detail_sold_out)
    Button btnActivityDishDetailSoldOut;
    @Bind(R.id.tv_activity_dish_detail_description)
    TextView tvActivityDishDetailDescription;
    private String id;
    private GoodsBean bean;
    private boolean isSold = false;
    private boolean isFinish =true;
    private Login login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dish_detail;
    }

    @Override
    protected void initDate() {
        login = MyApplication.getLogin();
    }

    private void setData() {
        if ("1".equals(bean.getMsg().getFlag())) {
            isSold = false;
        } else {
            isSold = true;
        }
        tvActivityDishDetailSoldNum.setText(bean.getMsg().getNum() + "");
        tvActivityDishDetailDishName.setText(bean.getMsg().getGoodsname());
        tvActivityDishDetailDescription.setMovementMethod(new ScrollingMovementMethod());
        tvActivityDishDetailDescription.setText(bean.getMsg().getGoodscontent());
        tvActivityDishDetailDishPrice.setText("$" + bean.getMsg().getGoodsprice());
        tvActivityDishDetailDishMenu.setText(bean.getMsg().getFenlei());
        GlideUtils.load(this, bean.getMsg().getGoodsphoto(), ivActivityDishDetailShopPic, GlideUtils.Shape.ShopIcon);
//        ImageLoader imageLoader = new ImageLoader(this);
//        imageLoader.loadImage(bean.getMsg().getGoodsphoto(), R.drawable.img_shop_pic_default, R.drawable.img_shop_pic_default, ivActivityDishDetailShopPic);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageView(this, 0, rlDishDetailTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish =false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (login != null) {
            initDate1();
        }else {
            Toast.makeText(DishDetailActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DishDetailActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void initDate1() {
        showDialog();
        id = getIntent().getStringExtra("id");
        HttpUtils httpUtils = new HttpUtils(Contants.URL_GOODSDETAILS) {
            @Override
            public void onError(Call call, Exception e, int id) {
                hidDialog();
                Toast.makeText(DishDetailActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                hidDialog();
                try {
                    JSONObject js = new JSONObject(response);
                    int status = js.getInt("status");
                    if (status == 1) {
                        bean = new Gson().fromJson(response, GoodsBean.class);
                        if (isFinish) {
                            setData();
                        }
                    } else if (status == 9) {
                        Toast.makeText(DishDetailActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        startActivity(new Intent(DishDetailActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(DishDetailActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        httpUtils.addParam("goodsid", id).addParams("shopid", login.getShopId()).addParams("token", login.getToken());
        httpUtils.clicent();
    }

    @OnClick({R.id.iv_dish_detail_back, R.id.iv_dish_detail_modify, R.id.btn_activity_dish_detail_delete, R.id.btn_activity_dish_detail_sold_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_dish_detail_back:
                finish();
                break;
            case R.id.iv_dish_detail_modify:
                if (bean != null) {
                    Bundle b = new Bundle();
                    b.putString("goodsid", id);
                    b.putString("cid", bean.getMsg().getCid());
                    b.putString("dishpic", bean.getMsg().getGoodsphoto());
                    b.putString("dishname", bean.getMsg().getGoodsname());
                    b.putString("dishprice", bean.getMsg().getGoodsprice());
                    b.putString("dishdescription", bean.getMsg().getGoodscontent());
                    goToActivity(DishChangeActivity.class, b);
                }
                break;
            case R.id.btn_activity_dish_detail_delete:  //删除菜品
                showDialog();
                HttpUtils httpUtils = new HttpUtils(Contants.URL_DELETEGOODS) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hidDialog();
                        Toast.makeText(DishDetailActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hidDialog();
                        try {
                            JSONObject js = new JSONObject(response);
                            int status = js.getInt("status");
                            if (status == 1) {
                                Toast.makeText(DishDetailActivity.this, R.string.shan_chu_cheng_gong, Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (status == 9) {
                                Toast.makeText(DishDetailActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                                startActivity(new Intent(DishDetailActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(DishDetailActivity.this, R.string.shan_chu_shi_bai, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("goodsid", id).addParams("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken());
                httpUtils.clicent();
                break;
            case R.id.btn_activity_dish_detail_sold_out: //菜品下架
                String url = null;
                if (isSold) {
                    url = Contants.URL_EDITFLAGA;
                } else {
                    url = Contants.URL_EDITFLAGB;
                }
                showDialog();
                HttpUtils httpUtils1 = new HttpUtils(url) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hidDialog();
                        Toast.makeText(DishDetailActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hidDialog();
                        try {
                            JSONObject js = new JSONObject(response);
                            int status = js.getInt("status");
                            if (status == 1) {
                                Toast.makeText(DishDetailActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                if (isSold) {
                                    btnActivityDishDetailSoldOut.setText(getString(R.string.yi_shou_wan));
                                    btnActivityDishDetailSoldOut.setBackgroundResource(R.drawable.shape_orange);
                                    isSold = false;
                                } else {
                                    btnActivityDishDetailSoldOut.setBackgroundResource(R.drawable.shape_blue);
                                    btnActivityDishDetailSoldOut.setText(getString(R.string.yi_xia_jia));
                                    isSold = true;
                                }
                            } else if (status == 9) {
                                Toast.makeText(DishDetailActivity.this, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                                startActivity(new Intent(DishDetailActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(DishDetailActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils1.addParam("goodsid", id).addParams("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken());
                httpUtils1.clicent();
                break;
        }
    }
}
