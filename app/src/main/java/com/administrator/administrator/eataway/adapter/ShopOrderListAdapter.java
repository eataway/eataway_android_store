package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ShopOrderListAdapter extends RecyclerView.Adapter<ShopOrderListAdapter.ViewHolder> {
    private Context context;

    public ShopOrderListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ShopOrderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_shop_order_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShopOrderListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_order_list_number)
        TextView tvOrderListNumber;
        @Bind(R.id.tv_order_list_price)
        TextView tvOrderListPrice;
        @Bind(R.id.tv_order_list_date)
        TextView tvOrderListDate;
        @Bind(R.id.tv_order_list_type)
        TextView tvOrderListType;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
