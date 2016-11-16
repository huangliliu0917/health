package com.huotu.health.common;

/**
 * 公共参数获取
 * Created by lst on 2016/1/8.
 */
public class PublicParameterHolder {
    private static final ThreadLocal<PublicParameterModel> holder = new ThreadLocal<>();

    public static PublicParameterModel get() {
        return holder.get();
    }

    public static void set(PublicParameterModel model) {
        holder.set(model);
    }





}
