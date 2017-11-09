package com.administrator.administrator.eataway.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.adapter.OrderCompletedAdapter;
import com.administrator.administrator.eataway.adapter.OrderProgressAdapter;
import com.administrator.administrator.eataway.bean.MyOrder;
import com.administrator.administrator.eataway.bean.NorderListBean;
import com.administrator.administrator.eataway.bean.YorderListBean;
import com.administrator.administrator.eataway.comm.BaseFragment;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.GoosInfoAdapterUtils;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.NoScrollViewPager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/14.
 */

public class OrderPage extends BaseFragment {
    View ProgressView, CompletedView;
    RecyclerView rvInProgress, rvCompleted;
    @Bind(R.id.iv_order_page_refresh)
    ImageView ivOrderPageRefresh;
    @Bind(R.id.rl_newsdetails)
    RelativeLayout rlNewsdetails;
    @Bind(R.id.rl_order_page_top)
    RelativeLayout rlOrderPageTop;
    private OrderCompletedAdapter orderCompletedAdapter;
    private OrderProgressAdapter orderProgressAdapter;
    List<View> views;
    @Bind(R.id.rb_fragment_order_page_progress)
    RadioButton rbFragmentOrderPageProgress;
    @Bind(R.id.rb_fragment_order_page_completed)
    RadioButton rbFragmentOrderPageCompleted;
    @Bind(R.id.rg_activity_main_top)
    RadioGroup rgActivityMainTop;
    @Bind(R.id.vp_fragment_order_pager)
    NoScrollViewPager vpFragmentOrderPager;
    private YorderListBean bean;
    private YorderListBean yorderListBean;
    private int flag = 0;
    private refreshClickListener listener;
    private int page = 1;

    @Override
    protected Map<String, String> getParams() {
        return null;
    }

    @Override
    protected void initTitle() {
        rbFragmentOrderPageProgress.setChecked(true);
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
        return R.layout.fragment_order_page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initRadioGroup();
        initViewPager(inflater);
        return rootView;
    }

    public void addData(String data) {
//        Toast.makeText(_mActivity, data, Toast.LENGTH_SHORT).show();
//        if (type == 1) {
            YorderListBean.MsgBean addbean = new Gson().fromJson(data, YorderListBean.MsgBean.class);
            NorderListBean bean = new Gson().fromJson(data, NorderListBean.class);
            GoosInfoAdapterUtils.InfoCrop(bean.getGoodsinfo());
            List<MyOrder> mbean = GoosInfoAdapterUtils.getList();
            for (int i = 0; i < mbean.size(); i++) {
                YorderListBean.MsgBean.GoodsBean goodsBean=new YorderListBean.MsgBean.GoodsBean();
                goodsBean.setGoodsname(mbean.get(i).getGoodsName());
                goodsBean.setGoodsprice(mbean.get(i).getGoodsPrice());
                goodsBean.setNum(mbean.get(i).getGoodsNum());
                addbean.getGoods().add(goodsBean);
            }
//        } else {
//            NorderListBean bean = new Gson().fromJson(data, NorderListBean.class);
//            GoosInfoAdapterUtils.InfoCrop(bean.getGoodsinfo());
//            List<MyOrder> mbean = GoosInfoAdapterUtils.getList();
//            YorderListBean.MsgBean addbean = new YorderListBean.MsgBean();
//            addbean.setAddress();
//        }
        orderProgressAdapter.addBean(addbean);

//        rbFragmentOrderPageCompleted.setText(data);
    }

    private void initData() {
        if (MyApplication.getLogin()!=null) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_NORDERLIST) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    refreshComplete();
                    ToastUtils.showToast(R.string.please_check_your_network_connection,_mActivity);
//                    Toast.makeText(_mActivity, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    refreshComplete();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            bean = new Gson().fromJson(response, YorderListBean.class);
                            if (bean.getMsg().size() == 0)
                                ToastUtils.showToast(R.string.dang_qian_wu_ding_dan, _mActivity);
//                                Toast.makeText(_mActivity, R.string.dang_qian_wu_ding_dan, Toast.LENGTH_SHORT).show();
                            orderProgressAdapter = new OrderProgressAdapter(getContext(), bean);
                            rvInProgress.setAdapter(orderProgressAdapter);
                        } else {
                            ToastUtils.showToast(R.string.huo_qu_shi_bai, _mActivity);
//                            Toast.makeText(_mActivity, R.string.huo_qu_shi_bai, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        }else {
            ToastUtils.showToast(R.string.Please_Log_on_again, _mActivity);
//            Toast.makeText(_mActivity, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initRadioGroup() {
        rgActivityMainTop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_fragment_order_page_completed:
                        flag = 1;
                        vpFragmentOrderPager.setCurrentItem(1, false);
                        rbFragmentOrderPageCompleted.setTextColor(getResources().getColor(R.color.color_blue));
                        rbFragmentOrderPageProgress.setTextColor(getResources().getColor(R.color.color_white));
                        break;
                    case R.id.rb_fragment_order_page_progress:
                        flag = 0;
                        vpFragmentOrderPager.setCurrentItem(0, false);
                        rbFragmentOrderPageProgress.setTextColor(getResources().getColor(R.color.color_blue));
                        rbFragmentOrderPageCompleted.setTextColor(getResources().getColor(R.color.color_white));
                        break;
                }
            }
        });
        listener = new refreshClickListener();
        ivOrderPageRefresh.setOnClickListener(listener);
    }

    private void initViewPager(LayoutInflater inflater) {
        vpFragmentOrderPager.setNoScroll(true);
        views = new ArrayList<>();
        ProgressView = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_in_progress, null);
        CompletedView = LayoutInflater.from(getContext()).inflate(R.layout.item_viewpager_completed, null);

        views.add(ProgressView);
        views.add(CompletedView);
        vpFragmentOrderPager.setAdapter(new MyViewPagerAdapter(views));
        vpFragmentOrderPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    initData();
                }else {
                    page = 1;
                    orderCompletedAdapter = null;
                    initData1();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class MyViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public MyViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(View container, int position) {
            return super.instantiateItem(container, position);

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), position);
            if (position == 0) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                rvInProgress = (RecyclerView) ProgressView.findViewById(R.id.rv_item_viewpager_in_progress);
                rvInProgress.setLayoutManager(manager);
                rvInProgress.setItemAnimator(new DefaultItemAnimator());
                if (MyApplication.getLogin() != null) {
                    initData();
                }
            }
            if (position == 1) {
                LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
                manager1.setOrientation(LinearLayoutManager.VERTICAL);
                rvCompleted = (RecyclerView) CompletedView.findViewById(R.id.rv_item_viewpager_completed);
                rvCompleted.setLayoutManager(manager1);
                rvCompleted.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    private int firstVisitItem, totalItemCount, visitableItem, lastItem;
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        firstVisitItem = manager.findFirstVisibleItemPosition();
                        totalItemCount = manager.getItemCount();
                        visitableItem = manager.getChildCount();
                        lastItem = manager.findLastVisibleItemPosition();

                        if (lastItem + 1 == totalItemCount) {
                            initData1();
                        }
                    }
                });
                if (MyApplication.getLogin() != null) {
                    initData1();
                }
            }
            return views.get(position);
        }

    }

    private void initData1() {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_YOUDER_LIST) {
            @Override
            public void onError(Call call, Exception e, int id) {
                refreshComplete();
                ToastUtils.showToast(R.string.please_check_your_network_connection, _mActivity);
//                Toast.makeText(_mActivity, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                refreshComplete();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status == 1) {
                        yorderListBean = new Gson().fromJson(response, YorderListBean.class);
                        if (orderCompletedAdapter == null) {
                            orderCompletedAdapter = new OrderCompletedAdapter(getContext(), yorderListBean.getMsg());
                            rvCompleted.setAdapter(orderCompletedAdapter);
                        }else {
                            orderCompletedAdapter.addBean(yorderListBean.getMsg());
                        }
                        if (yorderListBean.getMsg()!=null && yorderListBean.getMsg().size()>0) {
                            page++;
                        }
                    } else {
                        ToastUtils.showToast(R.string.huo_qu_shi_bai,_mActivity);
//                        Toast.makeText(_mActivity, R.string.huo_qu_shi_bai, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken()).addParams("page", page+"");
        httpUtils.clicent();

    }

    class refreshClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setRefreshing();
            if (flag == 0) {
                initData();
            } else {
                page = 1;
                orderCompletedAdapter = null;
                initData1();
            }
        }
    }

    private void setRefreshing() {
        rlNewsdetails.setVisibility(View.VISIBLE);
        rlNewsdetails.getParent().requestDisallowInterceptTouchEvent(true);
    }

    private void refreshComplete() {
        rlNewsdetails.setVisibility(View.GONE);
        rlNewsdetails.getParent().requestDisallowInterceptTouchEvent(true);
    }
}
