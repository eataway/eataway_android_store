package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.bean.YorderListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/14.
 */

public class OrderCompletedAdapter extends RecyclerView.Adapter<OrderCompletedAdapter.ViewHolder> {
    private Context context;
    private OrderDishListAdapter adapter;
    private List<YorderListBean.MsgBean> been = new ArrayList<>();
    private YorderListBean bean;

    public OrderCompletedAdapter(Context context, List<YorderListBean.MsgBean> been) {
        this.context = context;
        this.been = been;
    }

    @Override
    public OrderCompletedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_order_page_in_progress, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderCompletedAdapter.ViewHolder holder, int position) {
        initOrderList(holder, position);
        holder.tvInProgressOrderNum.setText(been.get(position).getOrderid());
        holder.tvInProgressOrderTimeInfo.setText(been.get(position).getAddtime());
//        1:tuidanzhong 2:正常 3：已退单
        if ("1".equals(been.get(position).getStatu())){
            holder.tvInProgress.setText(context.getString(R.string.tui_dan_zhong));
        }else if ("3".equals(been.get(position).getStatu())){
            holder.tvInProgress.setText(context.getString(R.string.yi_tui_dan));
        }else {
            holder.tvInProgress.setText(context.getString(R.string.yi_wan_cheng));
        }
        if (MyApplication.isEnglish) {
            if (been.get(position).getSex().equals("0"))
                holder.tvInProgressClientName.setText("Mr."+been.get(position).getName());
            else
                holder.tvInProgressClientName.setText("Miss."+been.get(position).getName());
        }else {
            if (been.get(position).getSex().equals("0"))
                holder.tvInProgressClientName.setText(been.get(position).getName() + "先生");
            else
                holder.tvInProgressClientName.setText(been.get(position).getName() + "女士");
        }
        holder.tvInProgressRemarksInfo.setText(been.get(position).getBeizhu());
        holder.tvInProgressLocation.setText(been.get(position).getAddress());
        holder.tvInProgressDistance.setText(been.get(position).getJuli() + "km");
        holder.tvInProgressPhone.setText(been.get(position).getPhone());
        holder.tvInProgressDeliveryFee.setText("$"+been.get(position).getMoney());
        holder.tvInProgressTotal.setText("$"+been.get(position).getAllprice());
        holder.tvInProgressDeliveryTimeInfo.setText(been.get(position).getCometime());
        holder.tvInProgressId.setText(been.get(position).getTodaynums());
        holder.tvInProgressRefund.setVisibility(View.GONE);
        holder.btnInProgress.setVisibility(View.GONE);
        holder.tvInProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
//        if (bean==null||been.size()==0){
//            return 0;
//        }
//        return been.size();
        if (been!=null) {
            return been.size();
        }
        return 0;
    }
    
    public void addBean(List<YorderListBean.MsgBean> add) {
        if (add != null && add.size()>0) {
            this.been.addAll(add);
        }
        notifyDataSetChanged();
    }

    private void initOrderList(ViewHolder holder, int position) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new OrderDishListAdapter(context, been.get(position).getGoods());
        holder.rvInProgressDishname.setLayoutManager(manager);
        holder.rvInProgressDishname.setAdapter(adapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rv_in_progress_dishname)
        RecyclerView rvInProgressDishname;
        @Bind(R.id.num_info)
        TextView numInfo;
        @Bind(R.id.tv_in_progress_order_num)
        TextView tvInProgressOrderNum;
        @Bind(R.id.tv_in_progress_client_name)
        TextView tvInProgressClientName;
        @Bind(R.id.tv_in_progress_order_time)
        TextView tvInProgressOrderTime;
        @Bind(R.id.tv_in_progress_order_time_info)
        TextView tvInProgressOrderTimeInfo;
        @Bind(R.id.tv_in_progress_delivery_time)
        TextView tvInProgressDeliveryTime;
        @Bind(R.id.tv_in_progress_delivery_time_info)
        TextView tvInProgressDeliveryTimeInfo;
        @Bind(R.id.tv_in_progress_remarks)
        TextView tvInProgressRemarks;
        @Bind(R.id.tv_in_progress_remarks_info)
        TextView tvInProgressRemarksInfo;
        @Bind(R.id.list_line2)
        View listLine2;
        @Bind(R.id.iv_in_progress_location)
        ImageView ivInProgressLocation;
        @Bind(R.id.tv_in_progress_location)
        TextView tvInProgressLocation;
        @Bind(R.id.tv_in_progress_distance)
        TextView tvInProgressDistance;
        @Bind(R.id.iv_in_progress_phone)
        ImageView ivInProgressPhone;
        @Bind(R.id.tv_in_progress_phone)
        TextView tvInProgressPhone;
        @Bind(R.id.tv_in_progress_delivery_fee)
        TextView tvInProgressDeliveryFee;
        @Bind(R.id.list_line3)
        View listLine3;
        @Bind(R.id.tv_in_progress_total)
        TextView tvInProgressTotal;
        @Bind(R.id.tv_in_progress_refund)
        TextView tvInProgressRefund;
        @Bind(R.id.btn_in_progress)
        Button btnInProgress;
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.tv_in_progress)
        TextView tvInProgress;
        @Bind(R.id.tv_in_progress_id)
        TextView tvInProgressId;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
