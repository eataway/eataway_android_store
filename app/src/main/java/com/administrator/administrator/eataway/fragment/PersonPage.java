package com.administrator.administrator.eataway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.CertificationActivity;
import com.administrator.administrator.eataway.activity.CertificationUpActivity;
import com.administrator.administrator.eataway.activity.FeedBackActivity;
import com.administrator.administrator.eataway.activity.LoginActivity;
import com.administrator.administrator.eataway.activity.MainActivity;
import com.administrator.administrator.eataway.activity.SettingActivity;
import com.administrator.administrator.eataway.activity.ShopDetailActivity;
import com.administrator.administrator.eataway.activity.ShopIncomeAcitivity;
import com.administrator.administrator.eataway.bean.ShopBean;
import com.administrator.administrator.eataway.bean.UpShopBean;
import com.administrator.administrator.eataway.comm.BaseFragment;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.GlideUtils;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/14.
 */

public class PersonPage extends BaseFragment {
    @Bind(R.id.cimg_person_user_icon)
    CircleImageView cimgPersonUserIcon;
    @Bind(R.id.tv_person_user_name)
    TextView tvPersonUserName;
    @Bind(R.id.tv_person_user_phone)
    TextView tvPersonUserPhone;
    @Bind(R.id.rl_person_user_info)
    RelativeLayout rlPersonUserInfo;
    @Bind(R.id.tv_sales_info)
    TextView tvSalesInfo;
    @Bind(R.id.tv_sales)
    TextView tvSales;
    @Bind(R.id.tv_income_info)
    TextView tvIncomeInfo;
    @Bind(R.id.tv_income)
    TextView tvIncome;
    @Bind(R.id.img_person_open_icon)
    ImageView imgPersonOpenIcon;
    @Bind(R.id.tb_is_open)
    ToggleButton tbIsOpen;
    @Bind(R.id.rl_person_open)
    RelativeLayout rlPersonOpen;
    @Bind(R.id.img_person_income_icon)
    ImageView imgPersonIncomeIcon;
    @Bind(R.id.rl_person_income)
    RelativeLayout rlPersonIncome;
    @Bind(R.id.img_person_certification_icon)
    ImageView imgPersonCertificationIcon;
    @Bind(R.id.rl_person_certification)
    RelativeLayout rlPersonCertification;
    @Bind(R.id.img_person_settings_icon)
    ImageView imgPersonSettingsIcon;
    @Bind(R.id.rl_person_settings)
    RelativeLayout rlPersonSettings;
    @Bind(R.id.img_person_feedback_icon)
    ImageView imgPersonFeedbackIcon;
    @Bind(R.id.rl_person_feedback)
    RelativeLayout rlPersonFeedback;
    private HttpUtils httpUtils;
    private boolean iscertification = false;
    private Intent i;
    private final int OPEN = 1;
    private final int CLOSE = 2;
    Login login = MyApplication.getLogin();

    private boolean isFinsh = true;
    private int currentType = CLOSE;

    @Override
    protected Map<String, String> getParams() {
        return null;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData(String response) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initToggleButton();
        rlPersonUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getLogin() != null) {
                    if (iscertification) {
                        i.setClass(getContext(), ShopDetailActivity.class);
                        startActivity(i);
                    }else {
                        ToastUtils.showToast(R.string.nin_dang_qian_hai_wei_ren_zheng, getContext());
                    }
                } else {
                    i.setClass(getContext(), LoginActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        i = new Intent();
        login = MyApplication.getLogin();
        if (login == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }else {
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        isFinsh = false;
    }

    private void initToggleButton() {
        tbIsOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (currentType == CLOSE) {
                    if (iscertification) {
                        editState(OPEN);
                    } else {
                        Toast.makeText(getContext(), R.string.nin_dang_qian_de_zhang_hao_zheng_zai_yan_zheng_zhong, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    editState(CLOSE);
                }
            }
        });
    }

    private void checkCertification() {
        httpUtils = new HttpUtils(Contants.URL_SHOPMSG) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getContext(), R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject object = new JSONObject(response);
                    int status = object.getInt("status");
                    if (status == 2) {  //未认证
                        iscertification = false;
                        i.setClass(getContext(), CertificationUpActivity.class);
                        startActivity(i);
                    } else if (status == 1) {     //已认证，获取信息
                        iscertification = true;
                        UpShopBean bean = new Gson().fromJson(response, UpShopBean.class);
                        login.setShopName(bean.getMsg().getShopname());
                        login.setHead(bean.getMsg().getShophead());
                        login.setPhone(bean.getMsg().getMobile());
                        login.setCertification(Integer.parseInt(bean.getMsg().getState()));
                        login.setLocation_text(bean.getMsg().getDetailed_address());
                        MyApplication.saveLogin(login);
                        if (MyApplication.getLogin().getCertification() == 3) { //认证中
                            Toast.makeText(getContext(), R.string.nin_dang_qian_de_zhang_hao_zheng_zai_yan_zheng_zhong, Toast.LENGTH_SHORT).show();
                        } else {
                            i.setClass(getContext(), CertificationActivity.class);
                            startActivity(i);
                        }
                    } else if (status == 9) {
                        Toast.makeText(getContext(), R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if (MyApplication.getLogin() != null) {
            httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
            httpUtils.addParam("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        } else {
            Toast.makeText(getContext(), R.string.nin_dang_qian_hai_wei_deng_lu, Toast.LENGTH_SHORT).show();
        }
    }

    private void editState(final int state) {
        MainActivity.mainActivity.showDialog();
        httpUtils = new HttpUtils(Contants.URL_EDITSTATE) {
            @Override
            public void onError(Call call, Exception e, int id) {
                MainActivity.mainActivity.hidDialog();
                ToastUtils.showToast(R.string.please_check_your_network_connection, getContext());
            }

            @Override
            public void onResponse(String response, int id) {
                MainActivity.mainActivity.hidDialog();
                try {
                    JSONObject o = new JSONObject(response);
                    int status = o.getInt("status");
                    if (status == 1) {
                        if (currentType == CLOSE) {
                            currentType = OPEN;
                            tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_on);
                            Toast.makeText(_mActivity, R.string.ying_ye_shi_jian, Toast.LENGTH_SHORT).show();
                        } else {
                            currentType = CLOSE;
                            tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                            Toast.makeText(_mActivity, R.string.ting_zhi_ying_ye, Toast.LENGTH_SHORT).show();
                        }
                    } else if (status == 0) {
//                        ToastUtils.showToast(R.string.please_check_your_network_connection, getContext());
                        initData();
                    } else if (status == 9) {
                        Toast.makeText(getContext(), R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
        httpUtils.addParam("state", state + "");
        httpUtils.addParam("token", MyApplication.getLogin().getToken());
        httpUtils.clicent();
    }

    @OnClick({R.id.rl_person_open, R.id.rl_person_income, R.id.rl_person_certification, R.id.rl_person_settings, R.id.rl_person_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_person_open:
                break;
            case R.id.rl_person_income:
                if (iscertification) {
                    i.setClass(getContext(), ShopIncomeAcitivity.class);
                    startActivity(i);
                }else {
                    ToastUtils.showToast(R.string.nin_dang_qian_hai_wei_ren_zheng, getContext());
                }
                break;
            case R.id.rl_person_certification:
                checkCertification();
                break;
            case R.id.rl_person_settings:
                i.setClass(getContext(), SettingActivity.class);
                startActivity(i);
                break;
            case R.id.rl_person_feedback:
                if (MyApplication.getLogin() != null) {
                    i.setClass(getContext(), FeedBackActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getContext(), R.string.nin_dang_qian_hai_wei_deng_lu, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void initData() {
        if (isFinsh) {
            if (login != null) {
                MainActivity.mainActivity.showDialog();
                httpUtils = new HttpUtils(Contants.URL_SHOPMSG) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MainActivity.mainActivity.hidDialog();
                        ToastUtils.showToast(R.string.please_check_your_network_connection,_mActivity);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MainActivity.mainActivity.hidDialog();
                        try {
                            JSONObject o = new JSONObject(response);
                            int status = o.getInt("status");
                            if (status == 1) {
                                ShopBean bean = new Gson().fromJson(response, ShopBean.class);
                                if (login != null) {
                                    login.setHead(bean.getMsg().getShophead());
                                    login.setShopName(bean.getMsg().getShopname());
                                    login.setAddress(bean.getMsg().getDetailed_address());
                                    login.setPic(bean.getMsg().getShopphoto());
                                    if (bean.getMsg().getState()!=null && !"".equals(bean.getMsg().getState())) {
                                        login.setCertification(Integer.parseInt(bean.getMsg().getState()));
                                    }
                                    if (bean.getMsg().getMaxprice() != null && !"".equals(bean.getMsg().getMaxprice())) {
                                        login.setMaxPrice(Double.parseDouble(bean.getMsg().getMaxprice()));
                                    }
                                    if (bean.getMsg().getMaxlong() != null && !"".equals(bean.getMsg().getMaxlong())) {
                                        login.setMaxLong(Double.parseDouble(bean.getMsg().getMaxlong()));
                                    }
                                    if (bean.getMsg().getMaxmoney() != null && !"".equals(bean.getMsg().getMaxmoney())) {
                                        login.setMaxLong(Double.parseDouble(bean.getMsg().getMaxmoney()));
                                    }
                                    if (bean.getMsg().getLkmoney() != null && !"".equals(bean.getMsg().getLkmoney())) {
                                        login.setMaxLong(Double.parseDouble(bean.getMsg().getLkmoney()));
                                    }
                                    if (bean.getMsg().getLmoney() != null && !"".equals(bean.getMsg().getLmoney())) {
                                        login.setMaxLong(Double.parseDouble(bean.getMsg().getLmoney()));
                                    }
                                    login.setIntroduce(bean.getMsg().getContent());
                                    login.setLocation_text(bean.getMsg().getDetailed_address());
                                    login.setTime(bean.getMsg().getGotime());
                                    if (bean.getMsg().getLongX() != null && !"".equals(bean.getMsg().getLongX())) {
                                        login.setMaxLong(Double.parseDouble(bean.getMsg().getLongX()));
                                    }
                                    MyApplication.saveLogin(login);
                                }
                                if ("2".equals(bean.getMsg().getState())) {
                                    //未营业
                                    iscertification = true;
                                    tvPersonUserName.setText(bean.getMsg().getShopname());
                                    tvPersonUserPhone.setVisibility(View.VISIBLE);
                                    tvPersonUserPhone.setText(bean.getMsg().getMobile());
                                    tvIncomeInfo.setText("$" + bean.getMsg().getAllmoney());
                                    tvSalesInfo.setText(bean.getMsg().getNums() + "");
                                    GlideUtils.load(getContext(), bean.getMsg().getShophead(), cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
//                                    tbIsOpen.setChecked(false);
                                    currentType = CLOSE;
                                    tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                                    tbIsOpen.setClickable(true);
                                } else if ("3".equals(bean.getMsg().getState())) {
                                    iscertification = false;
                                    tvPersonUserName.setText(getResources().getString(R.string.ren_zheng_zhong));
                                    tvPersonUserPhone.setVisibility(View.GONE);
                                    tvIncomeInfo.setText("$0");
                                    tvSalesInfo.setText("0");
                                    GlideUtils.load(getContext(), "", cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
//                                    tbIsOpen.setChecked(false);
                                    currentType = CLOSE;
                                    tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                                    tbIsOpen.setClickable(false);
                                } else if ("1".equals(bean.getMsg().getState())) {
                                    //营业
                                    iscertification = true;
                                    tvPersonUserName.setText(bean.getMsg().getShopname());
                                    tvPersonUserPhone.setVisibility(View.VISIBLE);
                                    tvPersonUserPhone.setText(bean.getMsg().getMobile());
                                    tvSalesInfo.setText(bean.getMsg().getNums() + "");
                                    tvIncomeInfo.setText("$" + bean.getMsg().getAllmoney());
                                    GlideUtils.load(getContext(), bean.getMsg().getShophead(), cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
//                                    tbIsOpen.setChecked(true);
                                    currentType = OPEN;
                                    tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_on);
                                    tbIsOpen.setClickable(true);
                                } else if ("4".equals(bean.getMsg().getState())) {
                                    //退出加盟
                                    iscertification = true;
                                    tvPersonUserName.setText(bean.getMsg().getShopname());
                                    tvPersonUserPhone.setVisibility(View.VISIBLE);
                                    tvPersonUserPhone.setText(bean.getMsg().getMobile());
                                    tvSalesInfo.setText(bean.getMsg().getNums() + "");
                                    tvIncomeInfo.setText("$" + bean.getMsg().getAllmoney());
                                    GlideUtils.load(getContext(), bean.getMsg().getShophead(), cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
//                                    tbIsOpen.setChecked(false);
                                    currentType = CLOSE;
                                    tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                                    tbIsOpen.setClickable(true);
                                }
                            } else if (status == 0) {
                                Toast.makeText(getContext(), R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            } else if (status == 9) {
                                Toast.makeText(getContext(), R.string.token_yan_zheng_shi_bai, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                                GlideUtils.load(getContext(), "", cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
                                tvPersonUserName.setText(getString(R.string.wei_deng_lu));
                                tvPersonUserPhone.setVisibility(View.GONE);
                                currentType = CLOSE;
                                tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                                tbIsOpen.setClickable(false);
                            } else if (status == 2) {    //未认证
                                iscertification = false;
                                currentType = CLOSE;
                                tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                                tbIsOpen.setClickable(false);
                                tvPersonUserName.setText(getResources().getString(R.string.wei_ren_zheng));
                                tvPersonUserPhone.setVisibility(View.GONE);
                                GlideUtils.load(getContext(), "", cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("shopid", MyApplication.getLogin().getShopId());
                httpUtils.addParam("token", MyApplication.getLogin().getToken());
                httpUtils.clicent();
            } else {
                GlideUtils.load(getContext(), "", cimgPersonUserIcon, GlideUtils.Shape.ShopIcon);
                tvPersonUserName.setText(getString(R.string.wei_deng_lu));
                tvPersonUserPhone.setVisibility(View.GONE);
                currentType = CLOSE;
                tbIsOpen.setBackgroundResource(R.drawable.img_switch_icon_off);
                tbIsOpen.setClickable(false);
            }
        }
    }
}
