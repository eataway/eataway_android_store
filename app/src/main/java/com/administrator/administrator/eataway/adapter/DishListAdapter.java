package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.DishDetailActivity;
import com.administrator.administrator.eataway.bean.GoodsListBean;
import com.administrator.administrator.eataway.utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/17.
 */

public class DishListAdapter extends RecyclerView.Adapter<DishListAdapter.DishHolder> {
    private Context context;
    private GoodsListBean bean;

    public DishListAdapter(Context context,GoodsListBean bean) {
        this.context = context;
        this.bean=bean;
    }

    @Override
    public DishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_dish_list, null);
        DishHolder holder = new DishHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DishHolder holder, int position) {
        initDishHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (bean==null||bean.getMsg()==null||bean.getMsg().size()==0){
            return 0;
        }
        return bean.getMsg().size();
    }

    private void initDishHolder(DishHolder holder, final int position) {
        holder.tvMenuDetailName.setText(bean.getMsg().get(position).getGoodsname());
        holder.tvMenuDetailPrice.setText("$" + bean.getMsg().get(position).getGoodsprice());
        GlideUtils.load(context,bean.getMsg().get(position).getGoodsphoto(),holder.ivMenuDetailItem, GlideUtils.Shape.Dishpic);
        holder.rlDishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DishDetailActivity.class);
                i.putExtra("id",bean.getMsg().get(position).getGoodsid());
                context.startActivity(i);
            }
        });
        holder.rlDishList.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.width/2-50));
    }


    class DishHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_menu_detail_item)
        ImageView ivMenuDetailItem;
        @Bind(R.id.tv_menu_detail_name)
        TextView tvMenuDetailName;
        @Bind(R.id.tv_menu_detail_price)
        TextView tvMenuDetailPrice;
        @Bind(R.id.rl_dish_list)
        RelativeLayout rlDishList;

        DishHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
