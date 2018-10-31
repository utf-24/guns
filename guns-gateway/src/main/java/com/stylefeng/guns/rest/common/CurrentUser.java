package com.stylefeng.guns.rest.common;

/**
 * @author young
 * @description 当前用户信息
 * @date 2018/10/31 23:32
 **/
public class CurrentUser {
    //线程绑定的存储空间
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void saveUserId(String userId){
        threadLocal.set(userId);
    }

    public static String getCurrentUser(){
        return threadLocal.get();
    }
}
