package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.bean.IncomeBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/31.
 */

public class ShopIncomeAdapter extends RecyclerView.Adapter<ShopIncomeAdapter.LocalHolder> {

    private Context context;
    private IncomeBean bean;
    private moreListener listener = null;

    public ShopIncomeAdapter(Context context) {
        this.context = context;
    }

    public moreListener getListener() {
        return listener;
    }

    public void setListener(moreListener listener) {
        this.listener = listener;
    }

    public ShopIncomeAdapter(Context context, IncomeBean bean) {
        this.context = context;
        this.bean = bean;
    }

    public void addBean(IncomeBean bean) {
        for (int i = 0; i < bean.getMsg().size(); i++) {
            this.bean.getMsg().add(bean.getMsg().get(i));
        }
    }

    public void setBean(IncomeBean bean) {
        this.bean = bean;
    }

    @Override
    public LocalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_shop_income, parent, false);
        LocalHolder holder = new LocalHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(LocalHolder holder, int position) {
        if (bean == null || position > bean.getMsg().size() - 1) {
            holder.rlItemShopIncomeAll.setVisibility(View.GONE);
            holder.tvLoadMore.setVisibility(View.VISIBLE);
            holder.tvLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click();
                }
            });
        } else {
            holder.tvLoadMore.setVisibility(View.GONE);
            holder.rlItemShopIncomeAll.setVisibility(View.VISIBLE);
            holder.tvShopIncomeOrderNumber.setText(bean.getMsg().get(position).getOrderid());
            holder.tvActivityShopIncomePrice.setText("+$" + bean.getMsg().get(position).getMoney());
            holder.tvShopIncomeDate.setText(bean.getMsg().get(position).getAddtime());
        }
    }

    @Override
    public int getItemCount() {
        if (bean != null && bean.getMsg().size() > 0) {
            return bean.getMsg().size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class LocalHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_shop_income_order_number)
        TextView tvShopIncomeOrderNumber;
        @Bind(R.id.tv_activity_shop_income_price)
        TextView tvActivityShopIncomePrice;
        @Bind(R.id.tv_shop_income_date)
        TextView tvShopIncomeDate;
        @Bind(R.id.tv_shop_income_order_type)
        TextView tvShopIncomeOrderType;
        @Bind(R.id.rl_item_shop_income_all)
        RelativeLayout rlItemShopIncomeAll;
        @Bind(R.id.tv_load_more)
        TextView tvLoadMore;

        LocalHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface moreListener {
        void click();
    }
}
