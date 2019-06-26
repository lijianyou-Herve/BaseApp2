package com.herve.library.commonlibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by yangyan
 * on 2017/12/29.
 */

public class ImageLoad {

    /**
     * 处理Glide You cannot start a load for a destroyed activity问题
     *
     * @param context
     * @param url               网络图片
     * @param defaultLoadingImg 默认加载图片
     * @param imageView
     */
    public static void loadImg(Context context, String url, int defaultLoadingImg,
            ImageView imageView) {
        try {
            RequestOptions options = new RequestOptions()
                    .placeholder(defaultLoadingImg)
                    .error(defaultLoadingImg)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .priority(Priority.HIGH);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception exception) {
            return;
        }
    }

    /**
     * Glide实现高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImgBlur(Context context, String url,
            int defaultLoadingImg, ImageView imageView) {
        try {
            RequestOptions options = RequestOptions
                    .bitmapTransform(new BlurTransformation(40, 16)).priority(
                            Priority.HIGH)
                    .placeholder(defaultLoadingImg)
                    .error(defaultLoadingImg);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception exception) {
            return;
        }
    }

    public static void loadImgBlur(Context context, String url,
            int defaultLoadingImg, ImageView imageView, int blurRadius,
            int blurSampling) {
        try {
            RequestOptions options = RequestOptions
                    .bitmapTransform(
                            new BlurTransformation(blurRadius, blurSampling)).priority(
                            Priority.HIGH)
                    .placeholder(defaultLoadingImg)
                    .error(defaultLoadingImg);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        } catch (Exception exception) {
            return;
        }
    }

}
