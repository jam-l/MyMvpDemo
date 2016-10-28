/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jam.mymvpdemo.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jam.mymvpdemo.interfaces.PageNames;
import com.jam.mymvpdemo.ui.find.FindActivity;
import com.jam.mymvpdemo.ui.home.MainActivity;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Activities 管理类
 */
public class ActivityUtils {
    private static Map<String, Class> actMap;

    static {
        actMap = new HashMap<>();
        actMap.put(PageNames.KEY_MAIN_PAGE, MainActivity.class);
        actMap.put(PageNames.KEY_FIND_PAGE, FindActivity.class);
    }

    /**
     * 增加一个Fragment到Activity中
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * 开启一个Activity
     *
     * @param context  上下文
     * @param key_page 页面的key
     */
    public static void startActivity(Context context, String key_page) {
        startActivity(context, key_page, null);
    }

    /**
     * 开启一个含有参数的Activity
     *
     * @param context  上下文
     * @param key_page 页面的key
     * @param bundle   参数
     */
    public static void startActivity(Context context, String key_page, Bundle bundle) {
        Class clazz = actMap.get(key_page);
        if (clazz != null) {
            Intent starter = new Intent(context, clazz);
            if (bundle != null) {
                starter.putExtras(bundle);
            }
            context.startActivity(starter);
        }
    }

}
