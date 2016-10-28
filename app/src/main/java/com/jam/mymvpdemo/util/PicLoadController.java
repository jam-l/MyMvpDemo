package com.jam.mymvpdemo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jam.mymvpdemo.MyApplication;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PicLoadController {
    public static <T> RequestManager with(T t) {
        if (t instanceof Fragment) {
            Fragment with = (Fragment) t;
            return Glide.with(with);
        } else if (t instanceof FragmentActivity) {
            FragmentActivity with = (FragmentActivity) t;
            return Glide.with(with);
        } else if (t instanceof Activity) {
            Activity with = (Activity) t;
            return Glide.with(with);
        } else if (t instanceof android.app.Fragment) {
            android.app.Fragment with = (android.app.Fragment) t;
            return Glide.with(with);
        } else if (t instanceof Context) {
            Context with = (Context) t;
            return Glide.with(with);
        } else {
            return Glide.with(MyApplication.getInstance());
        }
    }

    public static <T> void load(T t, String url, ImageView imageView) {
        with(t).load(url).into(imageView);
    }

    public static <T> void loadCircle(T t, String url, ImageView imageView) {
        with(t).load(url).bitmapTransform(new CropCircleTransformation(MyApplication.getInstance())).into(imageView);
    }

    public static <T> void loadRounded(T t, String url, ImageView imageView) {
        with(t).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                int d_2dp = Utils.dp2px(MyApplication.getInstance(), 2);

                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(MyApplication.getInstance().getResources(), resource);
                circularBitmapDrawable.setCornerRadius(d_2dp);
                getView().setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
