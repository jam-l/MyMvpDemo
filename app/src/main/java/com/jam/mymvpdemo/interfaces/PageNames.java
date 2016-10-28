package com.jam.mymvpdemo.interfaces;

/**
 * Title: 所有页面名
 * Description: 用于ActivityUtils类startActivity参数key_page的跳转
 * 作者: JAM
 * 日期: 2016/10/28 11:36
 */

public interface PageNames {
    String KEY_MAIN_PAGE = "主页";
    String KEY_FIND_PAGE = "发现界面";

    interface PageParameters {
        // 发现界面参数
        String FIND_VIDEO_ID = "VIDEO_ID";
    }
}
