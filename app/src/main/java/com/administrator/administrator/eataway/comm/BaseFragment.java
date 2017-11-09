package com.administrator.administrator.eataway.comm;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.administrator.administrator.eataway.ui.LoadingPage;
import com.administrator.administrator.eataway.utils.UIUtils;

import java.util.Map;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by GSD on 2016/12/5 0005.
 * QQ:461842166
 * GitHub:Shengdi-Gu
 */
public abstract class BaseFragment extends SupportFragment {

    Context context;

    private LoadingPage loadingPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BaseFragment.this, successView);
                initTitle();
                initListener();
                initData(resultState.getContent());
            }

            @Override
            protected Map<String, String> params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }//application : activity.getApplication() /context.getApplicationContext()

        };
        return loadingPage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadingPage();
            }
        },0);
    }

    protected abstract Map<String, String> getParams();

    protected abstract void initTitle();

    protected abstract void initData(String response);

    protected abstract void initListener();

    protected abstract String getUrl();

    public abstract int getLayoutId();

    public void showLoadingPage() {
        loadingPage.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
            popupWindow.showAtLocation(showView, Gravity.NO_GRAVITY, xoff, offsetY);
        }
    }
}
