package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.bean.YorderListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */

public class OrderDishListAdapter extends RecyclerView.Adapter<OrderDishListAdapter.OrderDishHolder> {
    private Context context;
    private List<YorderListBean.MsgBean.GoodsBean> bean;

    public OrderDishListAdapter(Context context, List<YorderListBean.MsgBean.GoodsBean> bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public OrderDishListAdapter.OrderDishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_progress_dish_info, null);
        OrderDishHolder holder = new OrderDishHolder(view);
        holder.tvDishInfoName = (TextView) view.findViewById(R.id.tv_dish_info_name);
        holder.tvDishInfoNum = (TextView) view.findViewById(R.id.tv_dish_info_num);
        holder.tvDishInfoPrice = (TextView) view.findViewById(R.id.tv_dish_info_price);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderDishListAdapter.OrderDishHolder holder, int position) {
        holder.tvDishInfoName.setText(bean.get(position).getGoodsname());
        holder.tvDishInfoNum.setText("×"+bean.get(position).getNum());
        holder.tvDishInfoPrice.setText("$"+bean.get(position).getGoodsprice());
    }

    @Override
    public int getItemCount() {
        if (bean != null && bean.size()>0)
            return bean.size();
        return 0;
    }

    class OrderDishHolder extends RecyclerView.ViewHolder {
        public OrderDishHolder(View itemView) {
            super(itemView);
        }
        TextView tvDishInfoName;
        TextView tvDishInfoPrice;
        TextView tvDishInfoNum;
    }
}
