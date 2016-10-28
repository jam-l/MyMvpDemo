package com.jam.mymvpdemo.util;

import android.os.Environment;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.jam.mymvpdemo.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

public class L {
    private static final String  DEFAULT_TAG = "L.EDU";
    static               boolean logAll      = true;

    public static void e(final String tag, final String msg) {
        if (BuildConfig.DEBUG) {
            if (logAll) {
                Logger.t(tag).e(msg);
            } else {
                Log.e(tag, msg);
            }
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dF("e", tag, msg);
            }
        });
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            if (logAll) {
                Logger.t(DEFAULT_TAG).d(msg);
            } else {
                Log.d(DEFAULT_TAG, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (logAll) {
                Logger.t(tag).d(msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (logAll) {
                Logger.t(tag).i(msg);
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (logAll) {
                Logger.t(tag).w(msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }

    private static void dF(String mylogtype, String tag, String msg) {
        Logger.t(tag).d(msg);
        writeLogtoFile(mylogtype, tag, msg);
    }

    public static void writeToFile(String log) {
        try {
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                //sd卡不可用
                return;
            }
            File file = new File(Environment.getExternalStorageDirectory() + "/Dance/");
            if (!file.exists())
                file.mkdirs();

            file = new File(Environment.getExternalStorageDirectory() + "/Dance/Dance.log");
            if (!file.exists())
                file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file, true);

            Date             curDate     = new Date(System.currentTimeMillis());
            SimpleDateFormat sDateFormat = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
            String           strDate     = sDateFormat.format(curDate);

            fo.write(strDate.getBytes());
            fo.write(log.getBytes());
            fo.write("\r\n".getBytes());

            fo.flush();
            fo.close();
        } catch (Exception e) {
            ExpManager.sendException(e);
        }
    }

    private static String           MYLOG_PATH_SDCARD_DIR = "/sdcard/squaredanceLog/";// 日志文件在sdcard中的路径
    private static String           MYLOGFILEName         = "_Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf              = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile               = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    private static String           SPLIT_STR             = "~"; //文件名分割符

    /**
     * 打开日志文件并写入日志
     **/
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        if (BuildConfig.DEBUG) {
            // return;   //测试代码，测试环境也先将日记写上
        }

        Date   nowtime          = new Date();
        String fileDateStr      = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + " " + mylogtype + "/" + tag + "    " + text;

        File destDir = new File(MYLOG_PATH_SDCARD_DIR);
        if (!destDir.exists()) {
            destDir.mkdirs();//创建日记目录
        }
        //判断文件大小，如果过大写成另外一个文件
        int  i    = 0;
        File file = new File(MYLOG_PATH_SDCARD_DIR, SPLIT_STR + fileDateStr + SPLIT_STR + i + SPLIT_STR + MYLOGFILEName);
        while (file.exists() && file.isFile() && (file.length() >= 1 * 1024 * 1024)) {
            i++;
            file = new File(MYLOG_PATH_SDCARD_DIR, SPLIT_STR + fileDateStr + SPLIT_STR + i + SPLIT_STR + MYLOGFILEName);
        }

        try {
            FileWriter     filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter   = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ExpManager.sendException(e);
        }
    }


}
