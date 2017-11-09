package com.administrator.administrator.eataway.activity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.administrator.eataway.MyApplication;
import com.administrator.administrator.eataway.R;
import com.administrator.administrator.eataway.comm.BaseActivity;
import com.administrator.administrator.eataway.fragment.CommentPage;
import com.administrator.administrator.eataway.fragment.MenuPage;
import com.administrator.administrator.eataway.fragment.OrderPage;
import com.administrator.administrator.eataway.fragment.PersonPage;
import com.administrator.administrator.eataway.utils.ToastUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity {
    @Bind(R.id.iv_activity_main_order)
    ImageView ivActivityMainOrder;
    @Bind(R.id.tv_activity_main_order)
    TextView tvActivityMainOrder;
    @Bind(R.id.rl_activity_main_order)
    RelativeLayout rlActivityMainOrder;
    @Bind(R.id.iv_activity_main_menu)
    ImageView ivActivityMainMenu;
    @Bind(R.id.tv_activity_main_activity_menu)
    TextView tvActivityMainActivityMenu;
    @Bind(R.id.rl_activity_main_menu)
    RelativeLayout rlActivityMainMenu;
    @Bind(R.id.iv_activity_main_comment)
    ImageView ivActivityMainComment;
    @Bind(R.id.tv_activity_main_activity_comment)
    TextView tvActivityMainActivityComment;
    @Bind(R.id.rl_activity_main_comment)
    RelativeLayout rlActivityMainComment;
    @Bind(R.id.iv_activity_main_person)
    ImageView ivActivityMainPerson;
    @Bind(R.id.tv_activity_main_activity_person)
    TextView tvActivityMainActivityPerson;
    @Bind(R.id.rl_activity_main_person)
    RelativeLayout rlActivityMainPerson;
    @Bind(R.id.ll_activity_main_tab)
    LinearLayout llActivityMainTab;
    @Bind(R.id.fl_activity_main_content)
    FrameLayout flActivityMainContent;

    private int preSel = 0;
    private SupportFragment fragment;
    public static OrderPage orderPage;
    private MenuPage menuPage;
    private CommentPage commentPage;
    private PersonPage personPage;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    public static MainActivity mainActivity=null;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setFragement(0);
        mainActivity=this;
    }

    /**
     * 判断app是否处于前台
     * @param context
     * @return
     */
    public static boolean isAppForeground(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList==null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName()) &&
                    processInfo.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
    }

    public void setFragement(int page) {
        clearPreSelect(preSel);
        transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.hide(fragment);
        }
        switch (page) {
            case 0:
                if (orderPage == null) {
                    orderPage = new OrderPage();
                }
                fragment = orderPage;
                tvActivityMainOrder.setTextColor(getResources().getColor(R.color.color_blue));
                ivActivityMainOrder.setBackgroundResource(R.mipmap.tab_icon_order_sel);
                break;
            case 1:
                if (menuPage == null) {
                    menuPage = new MenuPage();
                }
                menuPage.initData();
                fragment = menuPage;
                tvActivityMainActivityMenu.setTextColor(getResources().getColor(R.color.color_blue));
                ivActivityMainMenu.setBackgroundResource(R.mipmap.tab_icon_menu_sel);
                break;
            case 2:
                if (commentPage == null) {
                    commentPage = new CommentPage();
                }
                fragment = commentPage;
                tvActivityMainActivityComment.setTextColor(getResources().getColor(R.color.color_blue));
                ivActivityMainComment.setBackgroundResource(R.mipmap.tab_icon_comment_sel);
                break;
            case 3:
                if (personPage == null) {
                    personPage = new PersonPage();
                }
                fragment = personPage;
                tvActivityMainActivityPerson.setTextColor(getResources().getColor(R.color.color_blue));
                ivActivityMainPerson.setBackgroundResource(R.mipmap.tab_icon_person_sel);
                break;
        }
        if (!fragment.isAdded()) {
                transaction.add(R.id.fl_activity_main_content, fragment);
        }else {
            transaction.show(fragment);
        }
        transaction.commit();
        preSel = page;
    }

    private void clearPreSelect(int preSel) {
        switch (preSel) {
            case 0:
                tvActivityMainOrder.setTextColor(getResources().getColor(R.color.text_gray));
                ivActivityMainOrder.setBackgroundResource(R.mipmap.tab_icon_order);
                break;
            case 1:
                tvActivityMainActivityMenu.setTextColor(getResources().getColor(R.color.text_gray));
                ivActivityMainMenu.setBackgroundResource(R.mipmap.tab_icon_menu);
                break;
            case 2:
                tvActivityMainActivityComment.setTextColor(getResources().getColor(R.color.text_gray));
                ivActivityMainComment.setBackgroundResource(R.mipmap.tab_icon_comment);
                break;
            case 3:
                tvActivityMainActivityPerson.setTextColor(getResources().getColor(R.color.text_gray));
                ivActivityMainPerson.setBackgroundResource(R.mipmap.tab_icon_person);
                break;
        }
    }

    @OnClick({R.id.rl_activity_main_order, R.id.rl_activity_main_menu, R.id.rl_activity_main_comment, R.id.rl_activity_main_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_main_order:
                setFragement(0);
                break;
            case R.id.rl_activity_main_menu:
                setFragement(1);
                break;
            case R.id.rl_activity_main_comment:
                setFragement(2);
                break;
            case R.id.rl_activity_main_person:
                setFragement(3);
                break;
        }
    }

    private long exitTime = 0;

    /**
     * 再按一次退出程序（间隔2秒之后再按返回才会提示）
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (MyApplication.isEnglish) {
                    Toast.makeText(getApplicationContext(), "Press again to exit the program",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                }
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
