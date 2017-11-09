package com.administrator.administrator.eataway.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.administrator.administrator.eataway.MyApplication;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ContactUtils {
    private static String PHONE_NUMBER_EN = "0403537867";
    private static String PHONE_NUMBER_CH = "0452451156";
    private static Intent dialIntent = null;

    public static void ContactUS (Context context){
        if (MyApplication.isEnglish){
            dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PHONE_NUMBER_EN));
        }else {
            dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PHONE_NUMBER_CH));
        }
        context.startActivity(dialIntent);
    }

    public static void ContactUS(Context context, String phoneNumber) {
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(dialIntent);
    }

}
