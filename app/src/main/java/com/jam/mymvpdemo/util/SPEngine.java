package com.jam.mymvpdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jam.mymvpdemo.MyApplication;

public class SPEngine {
    // --------------------------  单例  ----------------------------
    private static SPEngine INSTANCE;

    private SPEngine() {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences("commondata", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadInfo();
    }

    public static SPEngine getInstance() {
        if (INSTANCE == null) {
            synchronized (SPEngine.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SPEngine();
                }
            }
        }
        return INSTANCE;
    }
    // ----------------------------------------------------------------

    private SharedPreferences sharedPreferences;
    private Editor            editor;

    private void loadInfo() {

    }

    //退出账号的时候调用
    public void clearUserInfo() {

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        if (userInfo == null) {
            synchronized (SPEngine.class) {
                if (userInfo == null) {
                    userInfo = new UserInfo();
                }
            }
        }
        return userInfo;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static class UserInfo {
        private UserInfo() {
            userInfoSP = MyApplication.getInstance().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            userInfoEditor = userInfoSP.edit();
            load();
        }

        private SharedPreferences userInfoSP;
        private Editor            userInfoEditor;


        private void load() {
        }

        public void clear() {

        }
    }

}
