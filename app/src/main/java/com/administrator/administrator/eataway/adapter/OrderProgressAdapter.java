package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.LoginActivity;
import com.administrator.administrator.eataway.bean.YorderListBean;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/14.
 */

public class OrderProgressAdapter extends RecyclerView.Adapter<OrderProgressAdapter.OrderHolder> {
    private Context context;
    private YorderListBean bean;

    private OptionsPickerView pvOptions;
    private List<String> items = Arrays.asList("15min", "20min", "25min", "30min", "35min",
            "40min", "45min", "50min", "55min", "60min", "65min", "70min", "75min", "80min",
            "85min", "90min", "95min");

    public OrderProgressAdapter(Context context, YorderListBean bean) {
        this.context = context;
        this.bean = bean;
    }
    public void addBean(YorderListBean.MsgBean addbean){
        bean.getMsg().add(addbean);
        notifyDataSetChanged();
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public OrderProgressAdapter.OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_recylerview_order_page_in_progress, null);
        OrderHolder holder = new OrderHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final OrderProgressAdapter.OrderHolder holder, final int position) {
        initOrderList(holder, position);
        holder.tvInProgressOrderNum.setText(bean.getMsg().get(position).getOrderid());
        holder.tvInProgressOrderTimeInfo.setText(bean.getMsg().get(position).getAddtime());
        if (MyApplication.isEnglish) {
            if (bean.getMsg().get(position).getSex().equals("0"))
                holder.tvInProgressClientName.setText("Mr."+bean.getMsg().get(position).getName());
            else
                holder.tvInProgressClientName.setText("Miss."+bean.getMsg().get(position).getName());
        }else {
            if (bean.getMsg().get(position).getSex().equals("0"))
                holder.tvInProgressClientName.setText(bean.getMsg().get(position).getName() + "先生");
            else
                holder.tvInProgressClientName.setText(bean.getMsg().get(position).getName() + "女士");
        }
        holder.tvInProgressRemarksInfo.setText(bean.getMsg().get(position).getBeizhu());
        holder.tvInProgressLocation.setText(bean.getMsg().get(position).getAddress());
        holder.tvInProgressDistance.setText(bean.getMsg().get(position).getJuli() + "km");
        holder.tvInProgressPhone.setText(bean.getMsg().get(position).getPhone());
        holder.tvInProgressDeliveryFee.setText("$" + bean.getMsg().get(position).getMoney());
        holder.tvInProgressTotal.setText("$" + bean.getMsg().get(position).getAllprice());
        holder.tvInProgressDeliveryTimeInfo.setText(bean.getMsg().get(position).getCometime());
        holder.tvInProgressId.setText(bean.getMsg().get(position).getTodaynums());
        holder.tvInProgressRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTuiDan(bean.getMsg().get(position).getOrderid(), holder, position);
            }
        });
        if (bean.getMsg().get(position).getState().equals("2")){
            holder.btnInProgress.setText(R.string.que_ren_song_da);
        }else {
            holder.btnInProgress.setText(context.getString(R.string.jie_dan));
        }
        holder.btnInProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getMsg().get(position).getState().equals("1")) {
                    setJieDan(bean.getMsg().get(position).getOrderid(), holder, position);
                } else if (bean.getMsg().get(position).getState().equals("2")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle(R.string.que_ren_song_da_ma);
                    dialog.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setSongDa(bean.getMsg().get(position).getOrderid(), holder, position);
                        }
                    });
                    dialog.setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create().show();
                }
            }
        });
    }

    private void setTuiDan(final String id, final OrderHolder holder, final int index) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.tui_dan_title);
        dialog.setMessage(R.string.tui_dan_content);
        dialog.setPositiveButton(R.string.Refund_my, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HttpUtils httpUtils=new HttpUtils(Contants.URL_BACKORDER) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject js = new JSONObject(response);
                            int status = js.getInt("status");
                            if (status == 1) {
//                                holder.tvInProgressRefund.setVisibility(View.GONE);
//                                holder.btnInProgress.setVisibility(View.GONE);
//                                holder.tvInProgress.setVisibility(View.VISIBLE);
//                                bean.getMsg().get(index).setState("3");
                                bean.getMsg().remove(index);
                                notifyItemRemoved(index);
                                notifyItemRangeChanged(index,bean.getMsg().size());
                            } else if (status == 9) {
                                Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                MyApplication.saveLogin(null);
                                context.startActivity(new Intent(context, LoginActivity.class));
                            } else if (status == 3) {
                                Toast.makeText(context, R.string.chang_shi_jian_wei_cao_zuo_gai, Toast.LENGTH_SHORT).show();
                                bean.getMsg().remove(index);
                                notifyItemRemoved(index);
                                notifyItemRangeChanged(index,bean.getMsg().size());
                            }
                            else {
                                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                httpUtils.addParam("orderid", id).addParams("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken());
                httpUtils.clicent();
            }
        });
        dialog.setNegativeButton(R.string.text_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    private void setSongDa(String id, final OrderHolder holder, final int index) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_SONGDA) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject js = new JSONObject(response);
                    int status = js.getInt("status");
                    if (status == 1) {
//                        holder.tvInProgressRefund.setVisibility(View.GONE);
//                        holder.btnInProgress.setVisibility(View.GONE);
//                        holder.tvInProgress.setVisibility(View.VISIBLE);
                        bean.getMsg().remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, bean.getMsg().size());
                    } else if (status == 9) {
                        Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Login login = MyApplication.getLogin();
        if (login != null) {
            httpUtils.addParam("orderid", id).addParams("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken());
            httpUtils.clicent();
        }
    }

    private void setJieDan(final String id, final OrderHolder holder, final int index) {
        HttpUtils httpUtils = new HttpUtils(Contants.URL_QUEREN) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject js = new JSONObject(response);
                    int status = js.getInt("status");
                    if (status == 1) {
                        holder.btnInProgress.setText(R.string.que_ren_song_da);
                        bean.getMsg().get(index).setState("2");
                    } else if (status == 9) {
                        Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else if (status == 3) {
                        Toast.makeText(context, R.string.chang_shi_jian_wei_cao_zuo_gai, Toast.LENGTH_SHORT).show();
                        bean.getMsg().remove(index);
                        notifyItemRemoved(index);
                        notifyDataSetChanged();
//                        notifyItemRangeChanged(index,bean.getMsg().size());
                    } else {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("orderid", id).addParams("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken());
        httpUtils.clicent();
        //条件选择器
//        if (items != null && items.size() > 0) {
//            pvOptions = new  OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
//                    //返回的分别是三个级别的选中位置
//                    String tx = items.get(options1);
//                    HttpUtils httpUtils = new HttpUtils(Contants.URL_QUEREN) {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//                            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//                            try {
//                                JSONObject js = new JSONObject(response);
//                                int status = js.getInt("status");
//                                if (status == 1) {
//                                    holder.btnInProgress.setText(R.string.que_ren_song_da);
//                                    bean.getMsg().get(index).setState("2");
//                                } else if (status == 9) {
//                                    Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
//                                    MyApplication.saveLogin(null);
//                                    context.startActivity(new Intent(context, LoginActivity.class));
//                                } else if (status == 3) {
//                                    Toast.makeText(context, R.string.chang_shi_jian_wei_cao_zuo_gai, Toast.LENGTH_SHORT).show();
//                                    bean.getMsg().remove(index);
//                                    notifyItemRemoved(index);
//                                    notifyDataSetChanged();
////                        notifyItemRangeChanged(index,bean.getMsg().size());
//                                } else {
//                                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    };
//                    httpUtils.addParam("orderid", id).addParams("shopid", MyApplication.getLogin().getShopId()).addParams("token", MyApplication.getLogin().getToken())
//                            .addParams("aftertime", tx);
//                    httpUtils.clicent();
//                }
//            }).setTitleText("请选择送达所需时间")
//                    .setSubmitText(context.getString(R.string.text_ok))
//                    .setCancelText(context.getString(R.string.text_cancel))
//                    .build();
//            pvOptions.setPicker(items);
//            pvOptions.show();
//        }
    }

    @Override
    public int getItemCount() {
        if (bean == null || bean.getMsg().size() == 0) {
            return 0;
        }
        return bean.getMsg().size();
    }

    private void initOrderList(OrderHolder holder, int position) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        OrderDishListAdapter adapter = new OrderDishListAdapter(context, bean.getMsg().get(position).getGoods());
        holder.rvInProgressDishname.setLayoutManager(manager);
        holder.rvInProgressDishname.setAdapter(adapter);
    }


    class OrderHolder extends RecyclerView.ViewHolder {
        public OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Bind(R.id.rv_in_progress_dishname)
        RecyclerView rvInProgressDishname;
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
        @Bind(R.id.tv_in_progress_total)
        TextView tvInProgressTotal;
        @Bind(R.id.tv_in_progress_refund)
        TextView tvInProgressRefund;
        @Bind(R.id.btn_in_progress)
        Button btnInProgress;
        @Bind(R.id.tv_in_progress)
        TextView tvInProgress;
        @Bind(R.id.tv_in_progress_id)
        TextView tvInProgressId;
    }
}
