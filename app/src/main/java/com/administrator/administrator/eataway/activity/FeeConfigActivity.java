package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.ui.CustomFormatDialog;
import com.administrator.administrator.eataway.ui.CustomFormatDialog;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.BottomBar;
import com.administrator.administrator.eataway.view.TopBar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class FeeConfigActivity extends BaseActivity {
    @Bind(R.id.tp_fee_config)
    TopBar tpFeeConfig;
    @Bind(R.id.tv_fee_config_maxprice_start)
    TextView tvFeeConfigMaxpriceStart;
    @Bind(R.id.tv_fee_config_maxprice_info)
    TextView tvFeeConfigMaxpriceInfo;
    @Bind(R.id.tv_fee_config_maxprice_end)
    TextView tvFeeConfigMaxpriceEnd;
    @Bind(R.id.rl_activity_fee_config_minprice)
    RelativeLayout rlActivityFeeConfigMaxprice;
    @Bind(R.id.tv_fee_config_mindistance_start)
    TextView tvFeeConfigMindistanceStart;
    @Bind(R.id.tv_fee_config_mindistance_info)
    TextView tvFeeConfigMindistanceInfo;
    @Bind(R.id.tv_fee_config_mindistance_end)
    TextView tvFeeConfigMindistanceEnd;
    @Bind(R.id.rl_activity_fee_config_maxdistance)
    RelativeLayout rlActivityFeeConfigMindistance;
    @Bind(R.id.tv_fee_config_lkprice_start)
    TextView tvFeeConfigLkpriceStart;
    @Bind(R.id.tv_fee_config_lkprice_info)
    TextView tvFeeConfigLkpriceInfo;
    @Bind(R.id.rl_activity_fee_config_lkprice)
    RelativeLayout rlActivityFeeConfigLkprice;
    @Bind(R.id.bb_fee_config)
    BottomBar bbFeeConfig;
    @Bind(R.id.tv_fee_config_mindistance_price)
    TextView tvFeeConfigMindistancePrice;

    private CustomFormatDialog dialog1;
    private HttpUtils httpUtils;

    private Login login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fee_config;
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

    @Override
    protected void onResume() {
        super.onResume();
        login = MyApplication.getLogin();
        if (login != null) {
            if (MyApplication.getLogin().getMaxPrice() < 0) {
                rlActivityFeeConfigMaxprice.setVisibility(View.GONE);
            } else {
                rlActivityFeeConfigMaxprice.setVisibility(View.VISIBLE);
                tvFeeConfigMaxpriceInfo.setText("$" + login.getMaxPrice());
            }
            if (MyApplication.getLogin().getMaxLong() < 0) {
                rlActivityFeeConfigMindistance.setVisibility(View.GONE);
            } else {
                rlActivityFeeConfigMindistance.setVisibility(View.VISIBLE);
                tvFeeConfigMindistanceInfo.setText(login.getMaxLong() + "km");
                tvFeeConfigMindistancePrice.setText("$" + login.getMaxMoney());
            }
            tvFeeConfigLkpriceInfo.setText("$" + login.getLkMoney());
        }
    }

    private void initTopBar() {
        tpFeeConfig.setTbCenterTv(getString(R.string.pei_song_fei), R.color.color_white);
        tpFeeConfig.setTbLeftIv(R.drawable.img_icon_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bbFeeConfig.setCenText(R.string.tian_jia_pei_song_gui_ze, R.color.color_blue);
    }

    private void deleteRule(String url, final String param) {
        dialog1.dismiss();
        httpUtils = new HttpUtils(url) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(R.string.please_check_your_network_connection, FeeConfigActivity.this);
//                Toast.makeText(FeeConfigActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        Toast.makeText(FeeConfigActivity.this, R.string.shan_chu_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(FeeConfigActivity.this, R.string.shan_chu_cheng_gong, Toast.LENGTH_SHORT).show();
                        Login login = MyApplication.getLogin();
                        if (param.equals("maxprice")) {
                            login.setMaxPrice(-1);
                            rlActivityFeeConfigMaxprice.setVisibility(View.GONE);
                        } else if (param.equals("maxlong")) {
                            login.setMaxLong(-1);
                            login.setMaxMoney(0);
                            rlActivityFeeConfigMindistance.setVisibility(View.GONE);
                        }
                        MyApplication.saveLogin(login);
                    } else if (status == 9) {
                        Toast.makeText(FeeConfigActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.clicent();
    }

    private void changeRule(String url, final String param, final String content) {
        dialog1.dismiss();
        httpUtils = new HttpUtils(url) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(R.string.please_check_your_network_connection, FeeConfigActivity.this);
//                Toast.makeText(FeeConfigActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 0) {
                        Toast.makeText(FeeConfigActivity.this, R.string.xiu_gai_shi_bai, Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        Toast.makeText(FeeConfigActivity.this, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                        Login login = MyApplication.getLogin();
                        if (param.equals("maxprice")) {
                            login.setMaxPrice(Double.parseDouble(content));
                            tvFeeConfigMaxpriceInfo.setText("$" + content);
                        } else if (param.equals("maxlong")) {
                            login.setMaxLong(Double.parseDouble(content));
                            tvFeeConfigMindistanceInfo.setText(content + "km");
                        } else if (param.equals("maxmoney")) {  //新增
                            login.setMaxMoney(Double.parseDouble(content));
                            tvFeeConfigMindistancePrice.setText("$" + content);
                        }else {
                            login.setLkMoney(Double.parseDouble(content));
                            tvFeeConfigLkpriceInfo.setText("$" + content);
                        }
                        MyApplication.saveLogin(login);
                    } else if (status == 9) {
                        Toast.makeText(FeeConfigActivity.this, R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        if (param.equals("maxprice")) {
            httpUtils.addParam("maxprice",  Double.parseDouble(content) + "");
        } else if (param.equals("maxlong")) {
            httpUtils.addParam("maxlong", Double.parseDouble(content) + "");
        }else if (param.equals("maxmoney")) {
            httpUtils.addParam("maxmoney", Double.parseDouble(content) + "");
        }
        httpUtils.addParam(param, content);
        httpUtils.clicent();
    }

    @OnClick({R.id.rl_activity_fee_config_minprice, R.id.tv_fee_config_mindistance_info,
            R.id.rl_activity_fee_config_lkprice, R.id.bb_fee_config, R.id.tv_fee_config_mindistance_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fee_config_mindistance_price:  //新增：多少钱
                dialog1 = new CustomFormatDialog(this, R.style.MyDialog, CustomFormatDialog.DELETE);
                dialog1.setEditText(0);
                dialog1.setDialogOnClickListener(new CustomFormatDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(int type, String et) {
                        if (type == CustomFormatDialog.DELETE) {  //删除规则
                            deleteRule(Contants.URL_EDITMAXDISTANCE, "maxlong");
                        } else if (type == CustomFormatDialog.OK) {    //修改规则
                            if (!"".equals(et) && Double.parseDouble(et) != login.getMaxMoney()){
                                changeRule(Contants.URL_EDITMAXMONEY, "maxmoney", et);
                            }else {
                                dialog1.dismiss();
                                ToastUtils.showToast(R.string.xiu_gai_cheng_gong, FeeConfigActivity.this);
                            }
                        }

                    }
                });
                dialog1.show();
                break;
            case R.id.rl_activity_fee_config_minprice:
                dialog1 = new CustomFormatDialog(this, R.style.MyDialog, CustomFormatDialog.DELETE);
                dialog1.setEditText(0);
                dialog1.setDialogOnClickListener(new CustomFormatDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(int type, String et) {
                        if (type == CustomFormatDialog.DELETE) {  //删除规则
                            deleteRule(Contants.URL_BACKMINPRICE, "maxprice");
                        } else if (type == CustomFormatDialog.OK) {    //修改规则
                            if (!"".equals(et) && Double.parseDouble(et) != login.getMaxPrice()){
                                changeRule(Contants.URL_EDITMINPRICE, "maxprice", et);
                            }else {
                                dialog1.dismiss();
                                ToastUtils.showToast(R.string.xiu_gai_cheng_gong, FeeConfigActivity.this);
                            }
                        }

                    }
                });
                dialog1.show();
                break;
            case R.id.tv_fee_config_mindistance_info:
                dialog1 = new CustomFormatDialog(this, R.style.MyDialog, CustomFormatDialog.DELETE);
                dialog1.setEditText(1);
                dialog1.setDialogOnClickListener(new CustomFormatDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(int type, String et) {
                        if (type == CustomFormatDialog.DELETE) {  //删除规则
                            deleteRule(Contants.URL_BACKMAXDISTANCE, "maxlong");
                        } else if (type == CustomFormatDialog.OK) {    //修改规则
                            if (!"".equals(et) && Double.parseDouble(et) != login.getMaxLong()){
                                changeRule(Contants.URL_EDITMAXDISTANCE, "maxlong", et);
                            }else {
                                dialog1.dismiss();
                                ToastUtils.showToast(R.string.xiu_gai_cheng_gong, FeeConfigActivity.this);
                            }
                        }
                    }
                });
                dialog1.show();
                break;
            case R.id.rl_activity_fee_config_lkprice:
                dialog1 = new CustomFormatDialog(this, R.style.MyDialog, CustomFormatDialog.CANCLE);
                dialog1.setDialogOnClickListener(new CustomFormatDialog.DialogOnClickListener() {
                    @Override
                    public void onClick(int type, String et) {
                        if (type == CustomFormatDialog.CANCLE) {  //删除规则
                            dialog1.dismiss();
                        } else if (type == CustomFormatDialog.OK) {    //修改规则
                            if (!"".equals(et) && Double.parseDouble(et) != login.getLkMoney()){
                                changeRule(Contants.URL_EDITLKPRICE, "lkmoney", et);
                            }else {
                                dialog1.dismiss();
                                ToastUtils.showToast(R.string.xiu_gai_cheng_gong, FeeConfigActivity.this);
                            }
                        }
                    }
                });
                dialog1.show();
                break;
            case R.id.bb_fee_config:
                goToActivity(FeeAddActivity.class);
                break;
        }
    }
}
