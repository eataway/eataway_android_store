package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.bean.MenuListBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by local123 on 2017/10/26.
 */

public class MenuChangeAdapter extends RecyclerView.Adapter<MenuChangeAdapter.ViewHolder> {
    private Context context;
    private List<MenuListBean.MsgBean> mDatas;

    public MenuChangeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MenuListBean.MsgBean> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public void addData(List<MenuListBean.MsgBean> data) {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public MenuChangeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_menu_change, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuChangeAdapter.ViewHolder holder, int position) {
        holder.tvMenuChange.setText(mDatas.get(position).getCname());
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_menu_change)
        TextView tvMenuChange;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
