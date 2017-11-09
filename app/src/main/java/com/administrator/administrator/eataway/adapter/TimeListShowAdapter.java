package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.ShopTimeConfigActivity;
import com.administrator.administrator.eataway.activity.TimeListActivity;
import com.administrator.administrator.eataway.bean.ShopTimeBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by local123 on 2017/11/3.
 */

public class TimeListShowAdapter extends RecyclerView.Adapter<TimeListShowAdapter.ViewHolder> {
    private Context context;
    private ShopTimeBean bean;

    public TimeListShowAdapter(Context context) {
        this.context = context;
    }

    public void setData(ShopTimeBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public TimeListShowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_list_show, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TimeListShowAdapter.ViewHolder holder, final int position) {
        holder.setWeek(position);
        holder.tvMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShopTimeConfigActivity.class);
                i.putExtra("date", position + 1);
                i.putExtra("openAndclose", holder.getData(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bean != null) {
            return 7;
        }
        return 0;
    }


     class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_label1)
        TextView tvLabel1;
        @Bind(R.id.tv_monday)
        TextView tvMonday;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setWeek(int position) {
            if (position == 0) {
                tvLabel1.setText(R.string.xing_qi_yi);
                if (!"".equals(bean.getMsg().getMonday())) {
                    tvMonday.setText(bean.getMsg().getMonday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }else if (position == 1) {
                tvLabel1.setText(R.string.xing_qi_er);
                if (!"".equals(bean.getMsg().getTuesday())) {
                    tvMonday.setText(bean.getMsg().getTuesday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }else if (position == 2) {
                tvLabel1.setText(R.string.xing_qi_san);
                if (!"".equals(bean.getMsg().getWednesday())) {
                    tvMonday.setText(bean.getMsg().getWednesday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }else if (position == 3) {
                tvLabel1.setText(R.string.xing_qi_si);
                if (!"".equals(bean.getMsg().getThursday())) {
                    tvMonday.setText(bean.getMsg().getThursday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }else if (position == 4) {
                tvLabel1.setText(R.string.xing_qi_wu);
                if (!"".equals(bean.getMsg().getFriday())) {
                    tvMonday.setText(bean.getMsg().getFriday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }else if (position == 5) {
                tvLabel1.setText(R.string.xing_qi_liu);
                if (!"".equals(bean.getMsg().getSaturday())) {
                    tvMonday.setText(bean.getMsg().getSaturday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }else if (position == 6) {
                tvLabel1.setText(R.string.xing_qi_qi);
                if (!"".equals(bean.getMsg().getSunday())) {
                    tvMonday.setText(bean.getMsg().getSunday());
                }else {
                    tvMonday.setText(R.string.qing_she_zhi_ying_ye_shi_jian);
                }
            }
        }

        public String getData(int position) {
            switch (position) {
                case 0:
                    return bean.getMsg().getMonday();
                case 1:
                    return bean.getMsg().getTuesday();
                case 2:
                    return bean.getMsg().getWednesday();
                case 3:
                    return bean.getMsg().getThursday();
                case 4:
                    return bean.getMsg().getFriday();
                case 5:
                    return bean.getMsg().getSaturday();
                case 6:
                    return bean.getMsg().getSunday();
                default:
                    return "";
            }
        }
    }
}
