package com.administrator.administrator.eataway.utils;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/8/8.
 * 用于解决Popwindow在Android 7.0以上无法正常显示的问题
 */

public class PopWindowUtil {
    public static void showFilterWindow(Context context, PopupWindow popupWindow, View showView, int xoff, int yoff) {
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
