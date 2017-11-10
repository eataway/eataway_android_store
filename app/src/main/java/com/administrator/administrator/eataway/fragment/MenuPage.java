package com.administrator.administrator.eataway.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.ChangeMenuActivity;
import com.administrator.administrator.eataway.activity.LoginActivity;
import com.administrator.administrator.eataway.activity.MainActivity;
import com.administrator.administrator.eataway.adapter.MenuListAdapter;
import com.administrator.administrator.eataway.bean.MenuListBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.BaseFragment;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MenuPage extends BaseFragment {
    @Bind(R.id.rv_menu_page)
    SwipeMenuRecyclerView rvMenuPage;
    @Bind(R.id.tp_menu_page)
    TopBar tpMenuPage;
    private MenuListAdapter adapter;
    private Login login = MyApplication.getLogin();
    private MenuListBean bean;

    private EditText editText;
    private MyWatcher watcher;

    private PopupWindow popMenu;
    private TextView tvNewMenu, tvMenuOrder;

    private String from ,to;

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
        return R.layout.fragment_menu_page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initPopwindow();
        initTopBar();
        initRecylerView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (login != null) {
            MainActivity.mainActivity.showDialog();
            initData();
        }else {
            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, getContext());
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }

    private void initPopwindow() {
        View contentView = View.inflate(getContext(), R.layout.layout_popwindow,
                null);
        popMenu = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(new BitmapDrawable());
        popMenu.setFocusable(true);
        popMenu.setAnimationStyle(R.style.popwin_anim_style);

        tvNewMenu = (TextView) contentView.findViewById(R.id.tv_pop_new_menu);
        tvMenuOrder = (TextView) contentView.findViewById(R.id.tv_pop_menu_order);
        tvNewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新增菜单
                popMenu.dismiss();
                initEditText();
                final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.xin_zeng_cai_dan);
                builder.setView(editText);
                builder.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String trim = editText.getText().toString().trim();
                        if (trim.equals("")){
                            Toast.makeText(_mActivity, R.string.qing_shu_ru_xin_zeng_jia_de_cai_dan, Toast.LENGTH_SHORT).show();
                        }else {
                            MainActivity.mainActivity.showDialog();
                            addMunu(trim);
                        }
                    }
                }).setNegativeButton(R.string.text_cancel,null).show();
            }
        });
        tvMenuOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMenu.dismiss();
                //菜单顺序
                startActivity(new Intent(getContext(), ChangeMenuActivity.class));
            }
        });
    }

    public void initData() {
        HttpUtils httpUtils=new HttpUtils(Contants.URL_CATEGORY) {
            @Override
            public void onError(Call call, Exception e, int id) {
                MainActivity.mainActivity.hidDialog();
                ToastUtils.showToast(R.string.please_check_your_network_connection,_mActivity);
            }

            @Override
            public void onResponse(String response, int id) {
                MainActivity.mainActivity.hidDialog();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status==1){
                        bean = new Gson().fromJson(response,MenuListBean.class);
                        adapter = new MenuListAdapter(getContext(),bean);
                        if (rvMenuPage != null)
                            rvMenuPage.setAdapter(adapter);
                    }else if (status == 9) {
                        ToastUtils.showToast(R.string.Please_Log_on_again, _mActivity);
                        MyApplication.saveLogin(null);
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid",login.getShopId()).addParams("token",login.getToken());
        httpUtils.clicent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initEditText() {
        editText = new EditText(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(getResources().getDrawable(R.color.tou_ming));
        }
        editText.addTextChangedListener(watcher);
        editText.setHint(R.string.qing_shu_ru_xin_zeng_jia_de_cai_dan);
        editText.setHintTextColor(Color.GRAY);
        editText.setPadding(50,30,50,30);
    }

    private void initTopBar() {
        watcher = new MyWatcher();
        tpMenuPage.setTbCenterTv(R.string.cai_dan_guan_li, R.color.color_white);
//        tpMenuPage.setTbRightIv(R.mipmap.icon_menu, new View.OnClickListener() {
        tpMenuPage.setTbRightIv(R.drawable.img_icon_add_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEditText();
                final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.xin_zeng_cai_dan);
                builder.setView(editText);
                builder.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String trim = editText.getText().toString().trim();
                        if (trim.equals("")){
                            Toast.makeText(_mActivity, R.string.qing_shu_ru_xin_zeng_jia_de_cai_dan, Toast.LENGTH_SHORT).show();
                        }else {
                            MainActivity.mainActivity.showDialog();
                            addMunu(trim);
                        }
                    }
                }).setNegativeButton(R.string.text_cancel,null).show();
//               showFilterWindow(getContext(), popMenu, v, MyApplication.width, 0);
            }
        });
    }
    private void addMunu(final String s){
        HttpUtils httpUtils=new HttpUtils(Contants.URL_ADDMUNU) {
            @Override
            public void onError(Call call, Exception e, int id) {
                MainActivity.mainActivity.hidDialog();
                Toast.makeText(_mActivity, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status==1){
//                        Toast.makeText(_mActivity, R.string.tian_jia_cheng_gong, Toast.LENGTH_SHORT).show();
                        initData();
                    }else if (status==9){
                        MainActivity.mainActivity.hidDialog();
                        Toast.makeText(getContext(), R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }else {
                        MainActivity.mainActivity.hidDialog();
                        ToastUtils.showToast(R.string.please_check_your_network_connection, getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if (login != null) {
            httpUtils.addParam("shopid",login.getShopId()).addParams("token",login.getToken()).addParams("cname",s);
            httpUtils.clicent();
        }else {
            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, getContext());
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }


    private void initRecylerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenuPage.setLayoutManager(manager);
    }

    class MyWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int lines = editText.getLineCount();
            // 限制最大输入行数
            if (lines > 1) {
                String str = s.toString();
                int cursorStart = editText.getSelectionStart();
                int cursorEnd = editText.getSelectionEnd();
                if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
                    str = str.substring(0, cursorStart-1) + str.substring(cursorStart);
                } else {
                    str = str.substring(0, s.length()-1);
                }
                // setText会触发afterTextChanged的递归
                editText.setText(str);
                // setSelection用的索引不能使用str.length()否则会越界
                editText.setSelection(editText.getText().length());
            }
        }
    }
}
