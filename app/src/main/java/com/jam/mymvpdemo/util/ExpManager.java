package com.jam.mymvpdemo.util;

/**
 * Created by liyp on 2016/9/12 把捕获到的 Exception 传到 Bugtags 后台
 */
public class ExpManager {
    public static void sendException(final Exception e){
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                Bugtags.sendException(e);
//            }
//        });
    }
}
