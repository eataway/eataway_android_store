package com.administrator.administrator.eataway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.LoginActivity;
import com.administrator.administrator.eataway.adapter.CommentListAdapter;
import com.administrator.administrator.eataway.bean.CommentListBean;
import com.administrator.administrator.eataway.comm.BaseFragment;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/14.
 */

public class CommentPage extends BaseFragment {
    private final int MAX_SERVER_INFO = 15;

    @Bind(R.id.tp_comment_page)
    TopBar tpCommentPage;
    @Bind(R.id.rv_comment_list)
    SwipeMenuRecyclerView rvCommentList;
    private Login login= MyApplication.getLogin();
    private CommentListAdapter adapter;
    private int page = 1;
    private CommentListBean bean;
    private int isfrist=0;

    private SwipeMenuRecyclerView.LoadMoreListener listener;
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
        return R.layout.fragment_comment_page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initTopBar();
        initRecylerView();
        initData();
        return rootView;
    }

    private void initData() {
        HttpUtils httpUtils=new HttpUtils(Contants.URL_COMMENT) {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(R.string.please_check_your_network_connection,_mActivity);
            }

            @Override
            public void onResponse(String response, int id) {
                isfrist = 1;
                try {
                    JSONObject js = new JSONObject(response);
                    int status = js.getInt("status");
                    if (status == 1) {
                        bean = new Gson().fromJson(response,CommentListBean.class);
//                        if(bean != null && bean.getMsg().size()==0 && page == 1) {
//                            Toast.makeText(_mActivity, R.string.mei_ping_jia, Toast.LENGTH_SHORT).show();
//                        }
//                        adapter = new CommentListAdapter(getContext(),bean);
//                        if (rvCommentList != null)
//                            rvCommentList.setAdapter(adapter);
                        if (page == 1) {
                            adapter.setData(bean);
                        }else {
                            adapter.addData(bean);
                        }
                        page ++;
                        if (bean.getMsg() == null || bean.getMsg().size() < MAX_SERVER_INFO){
                            rvCommentList.loadMoreFinish(false, false);
                        }else {
                            rvCommentList.loadMoreFinish(false, true);
                        }
                    } else if (status == 9) {
                        Toast.makeText(getContext(), R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), R.string.qing_qiu_shi_bai, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        if (login != null) {
            httpUtils.addParam("shopid",login.getShopId()).addParams("token",login.getToken()).addParams("page",page+"");
            httpUtils.clicent();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (isfrist!=0) {
            page = 1;
            initData();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initTopBar() {
        tpCommentPage.setTbCenterTv(R.string.ping_lun, R.color.color_white);
    }

    private void initRecylerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCommentList.setLayoutManager(manager);

        adapter = new CommentListAdapter(getContext());
        rvCommentList.setAdapter(adapter);

        listener = new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                initData();
            }
        };
        rvCommentList.useDefaultLoadMore();
        rvCommentList.setLoadMoreListener(listener);
    }
}
