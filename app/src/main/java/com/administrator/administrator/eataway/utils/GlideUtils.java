package com.administrator.administrator.eataway.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.administrator.administrator.eataway.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by GSD on 2016/12/23 0023.
 * QQ:461842166
 * GitHub:Shengdi-Gu
 */

public class GlideUtils {
    public static void load(Context context, String url, ImageView imageView, Shape shape) {
        if (shape == Shape.UserIcon) {
            Glide.with(context).load(url).error(R.drawable.img_user_icon_default).into(imageView);
        } else if (shape == Shape.Dishpic){
            Glide.with(context).load(url).bitmapTransform(new RoundedCornersTransformation(context, 40 ,0)).error(R.drawable.img_user_icon_default).into(imageView);
        }else if (shape == Shape.ShopIcon){
            Glide.with(context).load(url).error(R.drawable.img_shop_icon_default).into(imageView);
        }else if (shape == Shape.ShopPic) {
            Glide.with(context).load(url).error(R.drawable.img_shop_pic_default).into(imageView);
        }
    }

    public enum Shape {
        UserIcon, Dishpic, ShopIcon, ShopPic
    }
}
