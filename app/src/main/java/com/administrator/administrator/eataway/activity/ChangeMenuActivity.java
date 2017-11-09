package com.administrator.administrator.eataway.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.adapter.MenuChangeAdapter;
import com.administrator.administrator.eataway.adapter.MenuListAdapter;
import com.administrator.administrator.eataway.bean.MenuListBean;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.comm.Login;
import com.administrator.administrator.eataway.utils.Contants;
import com.administrator.administrator.eataway.utils.HttpUtils;
import com.administrator.administrator.eataway.utils.ToastUtils;
import com.administrator.administrator.eataway.view.TopBar;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import butterknife.Bind;
import okhttp3.Call;

public class ChangeMenuActivity extends BaseActivity {

    @Bind(R.id.tb_activity_menu_change)
    TopBar tbActivityMenuChange;
    @Bind(R.id.rv_menu_change)
    SwipeMenuRecyclerView rvMenuChange;

    private MenuListBean bean;
    private MenuListBean preBean;
    private Login login;

    private MenuChangeAdapter adapter;
    private int fromPosition ,toPosition;
    private boolean isMove = false; //判断是否调用了移动了item
    private boolean isOver = true;//判断上次的修改是否成功
    private String self, up, end;
    private OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            fromPosition = srcHolder.getAdapterPosition();
            toPosition = targetHolder.getAdapterPosition();

            Log.i("====", fromPosition + " " +toPosition);
            // Item被拖拽时，交换数据，并更新adapter。
            if (bean != null && bean.getMsg().size() > 1) {
                if (toPosition == bean.getMsg().size()-1) {
                    up = bean.getMsg().get(toPosition).getCid();
                    end = "0";
                }else if (toPosition == 0) {
                    up = "-1";
                    end = bean.getMsg().get(toPosition).getCid();
                }else {
                    if (fromPosition > toPosition) {
                        //从下往上拖动
                        up = bean.getMsg().get(toPosition).getUp();
                        end = bean.getMsg().get(toPosition).getCid();
                    }else if (fromPosition < toPosition) {
                        //从上往下拖动
                        up = bean.getMsg().get(toPosition).getCid();
                        end = bean.getMsg().get(toPosition).getEnd();
                    }else {
                        //没有变化，不会调用这个方法
                    }
                }
                self = bean.getMsg().get(fromPosition).getCid();
                isMove = true;
                Collections.swap(bean.getMsg(), fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
//                mDataList.remove(position);
            adapter.notifyItemRemoved(position);
        }
    };
    private OnItemStateChangedListener mStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {
                // 状态：正在拖拽。
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
                // 状态：滑动删除。
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                // 状态：手指松开。
                Log.i("====", up + " " +self + " "+ end);
                if (isMove && isOver) {
                    isOver = false;
                    showDialog();
                    changeMenuList(up, self, end);
                    isMove = false;
                }else if (!isOver) {
                    ToastUtils.showToast(R.string.xiu_gai_shi_bai_qing_shua_xin, mContext);
                    finish();
                }
                Log.i("====", "isMove:" + isMove + " " + "isOver:" + isOver);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_menu;
    }

    @Override
    protected void initDate() {
        tbActivityMenuChange.setTbCenterTv(R.string.cai_dan_shun_xu, R.color.color_white);
        tbActivityMenuChange.setTbLeftIv(R.drawable.img_icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tbActivityMenuChange.setTbRightTv(R.string.text_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login = MyApplication.getLogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        showDialog();
        getData();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMenuChange.setLayoutManager(manager);
        adapter = new MenuChangeAdapter(this);
        rvMenuChange.setAdapter(adapter);
        //新增
        rvMenuChange.setLongPressDragEnabled(true);
        rvMenuChange.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        rvMenuChange.setOnItemStateChangedListener(mStateChangedListener);
    }

    /**
     * 修改菜单顺序
     * @param up 修改后菜单的上条数据id
     * @param self 修改的菜单id
     * @param end 修改后菜单的下条数据id
     */
    private void changeMenuList(String up, String self, String end) {
        if (login != null) {
            HttpUtils httpUtils = new HttpUtils(Contants.URL_CHANGE_MENU_LIST) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i("xiugaishunxu", e.getMessage());
                    hidDialog();
                    bean = preBean;
                    adapter.setData(bean.getMsg());
                    ToastUtils.showToast(R.string.please_check_your_network_connection, ChangeMenuActivity.this);
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.i("xiugaishunxu", response);
                    try {
                        JSONObject o = new JSONObject(response);
                        int status = o.getInt("status");
                        if (status == 1) {
//                            ToastUtils.showToast(R.string.xiu_gai_cheng_gong, ChangeMenuActivity.this);
                            getData();
                        }else if (status == 0) {
                            hidDialog();
                            ToastUtils.showToast(R.string.xiu_gai_shi_bai, ChangeMenuActivity.this);
                            bean = preBean;
                            adapter.setData(bean.getMsg());
                            //修改失败不要让changeMenuList方法再掉用
                        }else {
                            hidDialog();
                            MyApplication.saveLogin(null);
                            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, ChangeMenuActivity.this);
                            goToActivity(LoginActivity.class);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("shopid", login.getShopId());
            httpUtils.addParam("token", login.getToken());
            httpUtils.addParam("up", up);
            httpUtils.addParam("self", self);
            httpUtils.addParam("end", end);
            httpUtils.clicent();
        }else {
            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, ChangeMenuActivity.this);
            goToActivity(LoginActivity.class);
            finish();
        }
    }

    private void getData() {
        if (login != null) {
            HttpUtils httpUtils=new HttpUtils(Contants.URL_CATEGORY) {
                @Override
                public void onError(Call call, Exception e, int id) {
                    hidDialog();
                    ToastUtils.showToast(R.string.please_check_your_network_connection, ChangeMenuActivity.this);
                }

                @Override
                public void onResponse(String response, int id) {
                    hidDialog();
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if (status==1){
                            bean = new Gson().fromJson(response,MenuListBean.class);
                            preBean = bean;
                            adapter.setData(bean.getMsg());
                            isOver = true;
                        }else if (status == 0) {
                            ToastUtils.showToast(R.string.please_check_your_network_connection, ChangeMenuActivity.this);
                        }else if (status == 9) {
                            MyApplication.saveLogin(null);
                            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, ChangeMenuActivity.this);
                            goToActivity(LoginActivity.class);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            httpUtils.addParam("shopid",login.getShopId()).addParams("token",login.getToken());
            httpUtils.clicent();
        }else {
            ToastUtils.showToast(R.string.token_yan_zheng_shi_bai, ChangeMenuActivity.this);
            goToActivity(LoginActivity.class);
            finish();
        }
    }
}
