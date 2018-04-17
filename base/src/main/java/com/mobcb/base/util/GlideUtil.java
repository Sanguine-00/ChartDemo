package com.mobcb.base.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class GlideUtil {

    /**
     * 加载图片(默认加载动画)
     *
     * @param context     上下文，可以是Application Context
     * @param path        加载的图片地址
     * @param imageView
     * @param placeHolder 图片加载完成前，需要显示的图片的Drawable ID
     * @param errorImage  图片加载出错时，需要显示的错误图片的 Drawable （这里可以显示默认图片）
     */
    public static void loadImage(Context context, Object path, ImageView imageView, int placeHolder, int errorImage) {
        Glide.with(context)
                .load(path)
                .into(imageView);
    }

    /**
     * 加载图片(没有placeHolder，errorImage)
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .into(imageView);
    }

    public static void loadImage(Fragment fragment, Object path, ImageView imageView) {
        Glide.with(fragment)
                .load(path)
                .into(imageView);
    }

    /**
     * 加载缩略图
     *
     * @param context
     * @param path
     * @param imageView
     * @param sizeMultiplier 与原图相比，是原图的多少倍
     */
    public static void loadThumbnail(Context context, Object path, ImageView imageView, float sizeMultiplier) {
        Glide.with(context)
                .load(path)
                .thumbnail(sizeMultiplier)
                .into(imageView);
    }

    /**
     * 清楚Glide缓存
     *
     * @param context
     */
    public static void clearCache(Context context) {
        Glide.get(context).clearMemory();//清除缓存
        Glide.get(context).clearDiskCache();//清除磁盘缓存
    }
}
