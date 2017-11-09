package com.administrator.administrator.eataway.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.activity.DishListActivity;
import com.administrator.administrator.eataway.activity.LoginActivity;
import com.administrator.administrator.eataway.bean.MenuListBean;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/15.
 */

public class MenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private MenuListBean bean;
    private Login login= MyApplication.getLogin();

    public MenuListAdapter(Context context) {
        this.context = context;
    }

    public MenuListAdapter(Context context, MenuListBean bean) {
        this.bean = bean;
        this.context = context;
    }

    public void setData(MenuListBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recylervier_menu_page, null);
        MenuHolder holder = new MenuHolder(view);
        holder.rlMenuList = (RelativeLayout) view.findViewById(R.id.rl_menu_list);
        holder.tvMemuPageMenuName = (TextView) view.findViewById(R.id.tv_memu_page_menu_name);
        holder.tvMenuPageMenuNum = (TextView) view.findViewById(R.id.tv_menu_page_menu_num);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        initMenuList((MenuHolder) holder, position);

    }

    @Override
    public int getItemCount() {
        if (bean == null || bean.getMsg() == null || bean.getMsg().size() == 0) {
            return 0;
        }
        return bean.getMsg().size();
    }

    private void initMenuList(MenuHolder holder, final int position) {
         holder.tvMemuPageMenuName.setText(bean.getMsg().get(position).getCname());
         holder.tvMenuPageMenuNum.setText(bean.getMsg().get(position).getNum()+"");

        holder.rlMenuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DishListActivity.class);
                i.putExtra("menuname", bean.getMsg().get(position).getCname());
                i.putExtra("id",bean.getMsg().get(position).getCid());
                context.startActivity(i);
            }
        });
        holder.rlMenuList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] strings = new String[]{context.getResources().getString(R.string.xiu_gai_ming_cheng), context.getResources().getString(R.string.shan_chu_cai_dan)};
                builder.setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            addMenu(position);
                        }else {
                            deletecaTegory(position);
                        }
                    }
                }).show();
                return false;
            }
        });
    }
    private void deletecaTegory(final int po){
        HttpUtils httpUtils=new HttpUtils(Contants.URL_DELETECATEGORY) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if (status==1){
                        Toast.makeText(context, R.string.shan_chu_cheng_gong, Toast.LENGTH_SHORT).show();
                        bean.getMsg().remove(po);
                        notifyDataSetChanged();
                    }else if (status==9){
                        Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                        MyApplication.saveLogin(null);
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }else if (status==2){
                        Toast.makeText(context, R.string.qing_xian_shan_chu_shang_pin, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, R.string.shan_chu_shi_bai, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        httpUtils.addParam("cid",bean.getMsg().get(po).getCid()).addParams("shopid",login.getShopId()).addParams("token",login.getToken());
        httpUtils.clicent();
    }
    private void addMenu(final int po) {
        final EditText editText = new EditText(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(context.getResources().getDrawable(R.color.tou_ming));
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int lines = editText.getLineCount();
                // 限制最大输入行数
                if (lines > 1) {
                    String str = s.toString();
                    int cursorStart = editText.getSelectionStart();
                    int cursorEnd = editText.getSelectionEnd();
                    if (cursorStart == cursorEnd && cursorStart < str.length() && cursorStart >= 1) {
                        str = str.substring(0, cursorStart - 1) + str.substring(cursorStart);
                    } else {
                        str = str.substring(0, s.length() - 1);
                    }
                    // setText会触发afterTextChanged的递归
                    editText.setText(str);
                    // setSelection用的索引不能使用str.length()否则会越界
                    editText.setSelection(editText.getText().length());
                }
            }
        });
        editText.setHint(R.string.qing_shu_ru_xiu_gai_ming_cheng);
        editText.setHintTextColor(Color.GRAY);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.xiu_gai_cai_dan);
        builder.setView(editText);
        editText.setPadding(30, 0, 30, 0);
        builder.setPositiveButton(R.string.text_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String trim = editText.getText().toString().trim();
                if (trim.equals("")) {
                    Toast.makeText(context, R.string.qing_shu_ru_xin_zeng_jia_de_cai_dan, Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtils httpUtils=new HttpUtils(Contants.URL_EDITCATEGORY) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int status = jsonObject.getInt("status");
                                if (status==1){
                                    Toast.makeText(context, R.string.xiu_gai_cheng_gong, Toast.LENGTH_SHORT).show();
                                }else if (status==9){
                                    Toast.makeText(context, R.string.Please_Log_on_again, Toast.LENGTH_SHORT).show();
                                    MyApplication.saveLogin(null);
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    httpUtils.addParam("shopid", login.getShopId()).addParams("token", login.getToken()).addParams("cname",trim).addParams("cid",bean.getMsg().get(po).getCid());
                    httpUtils.clicent();
                }
            }
        }).setNegativeButton(R.string.text_cancel, null).show();
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        public MenuHolder(View itemView) {
            super(itemView);
        }

        RelativeLayout rlMenuList;
        TextView tvMemuPageMenuName;
        TextView tvMenuPageMenuNum;
    }
}
