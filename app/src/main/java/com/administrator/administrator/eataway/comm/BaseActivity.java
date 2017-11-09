package com.administrator.administrator.eataway.comm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.Locale;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * Created by GSD on 2016/12/3 0003.
 * QQ:461842166
 * GitHub:Shengdi-Gu
 */
public abstract class BaseActivity extends SupportActivity {
    //新增：对话框
    protected WaitDialog dialog;

    public Context mContext = this;
    public boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        /** 强制竖屏 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init();
        ButterKnife.bind(this);
        //将当前的activity添加到栈管理器中
        AppManager.getInstance().add(this);
        initDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();
    protected abstract void initDate();

    /** * @param isEnglish true ：点击英文，把中文设置未选中 * false ：点击中文，把英文设置未选中 */
    public static void setLanguage(boolean isEnglish, Activity activity) {
        Configuration configuration = activity.getResources().getConfiguration();
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        activity.getResources().updateConfiguration(configuration, displayMetrics);
    }

    //启动一个新的activity
    public void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);

    }
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);

    }

    //结束当前activity的显示
    public void closeCurrentActivity() {
        AppManager.getInstance().removeCurrent();
    }

    //显示对话框
    public void showDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        if (dialog == null) {
            dialog = new WaitDialog(this);
            dialog.show();
        }
    }

    //隐藏对话框
    public void hidDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //显示popwindow
    public void showFilterWindow(Context context, PopupWindow popupWindow, View showView, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT < 24) {
            //7.0 The following system is used normally
            popupWindow.showAsDropDown(showView, xoff, yoff);
        } else {
            int[] location = new int[2];
            showView.getLocationOnScreen(location);
            int offsetY = location[1] + showView.getHeight();
            if (Build.VERSION.SDK_INT == 25) {
                //【note!】Gets the screen height without the virtual key
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                /*
                 * PopupWindow height for match_parent,
                 * will occupy the entire screen, it needs to do special treatment in Android 7.1
                */
                popupWindow.setHeight(screenHeight - offsetY);
            }
            //Use showAtLocation to display pop-up windows
            popupWindow.showAtLocation(showView, Gravity.NO_GRAVITY, 0, offsetY);
        }
    }
}
