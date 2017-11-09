package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.MapActivity;
import com.administrator.administrator.eataway.bean.AddressBean;
import com.administrator.administrator.eataway.comm.Login;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/22.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private AddressBean bean;

    public AddressAdapter(Context context, AddressBean bean) {
        this.context = context;
        this.bean = bean;
    }

    public AddressBean getBean() {
        return bean;
    }

    public void setBean(AddressBean bean) {
        this.bean = bean;
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_address_list, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {
        holder.tvBottomAddress.setText(bean.getResults().get(position).getVicinity());
        holder.tvTopAddress.setText(bean.getResults().get(position).getName());
        holder.llMapListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = MyApplication.getLogin();
                login.setLocation_text(bean.getResults().get(position).getVicinity());
                login.setLatitude(bean.getResults().get(position).getGeometry().getLocation().getLat()+"");
                login.setLongitude(bean.getResults().get(position).getGeometry().getLocation().getLng()+"");
                MyApplication.saveLogin(login);
                ((MapActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getResults().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_top_address)
        TextView tvTopAddress;
        @Bind(R.id.tv_bottom_address)
        TextView tvBottomAddress;
        @Bind(R.id.ll_map_list_item)
        LinearLayout llMapListItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
