package com.jam.mymvpdemo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.jam.mymvpdemo.MyApplication;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Utils {
    private static final String TAG = "Utils";


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        L.d("statusBarHeight" + statusBarHeight);
        return statusBarHeight;
    }



    /**
     * 从短信字符窜提取验证码
     *
     * @param body      短信内容
     * @param YZMLENGTH 验证码的长度 一般6位或者4位
     * @return 接取出来的验证码
     */
    public static String getyzm(String body, int YZMLENGTH) {
        // 首先([a-zA-Z0-9]{YZMLENGTH})是得到一个连续的六位数字字母组合
        // (?<![a-zA-Z0-9])负向断言([0-9]{YZMLENGTH})前面不能有数字
        // (?![a-zA-Z0-9])断言([0-9]{YZMLENGTH})后面不能有数字出现
        Pattern p = Pattern.compile("(?<![a-zA-Z0-9])([a-zA-Z0-9]{" + YZMLENGTH + "})(?![a-zA-Z0-9])");
        Matcher m = p.matcher(body);
        if (m.find()) {
            return m.group(0);
        }
        return "";
    }

    /**
     * 从字符串中截取连续6位数字 用于从短信中获取动态密码
     *
     * @param str 短信内容
     * @return 截取得到的6位动态密码
     */
    public static String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
        Matcher m                       = continuousNumberPattern.matcher(str);
        String  dynamicPassword         = "";
        while (m.find()) {
            if (m.group().length() == 6) {
                System.out.print(m.group());
                dynamicPassword = m.group();
            }
        }

        return dynamicPassword;
    }

    /**
     * 验证有效的身份证号码
     */
    public static boolean isCardNo(String cardNo) {
        String  str = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        Pattern p   = Pattern.compile(str);
        Matcher m   = p.matcher(cardNo);
        return m.matches();
    }


    // 过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String  regEx = "[`~!@#$%^&*()+=|{}''\\[\\]<>/?~@#￥%……& amp;*（）——+|{}【】‘”“’]";
        Pattern p     = Pattern.compile(regEx);
        Matcher m     = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isPersonId(String cardNo) {
        String regx  = "[0-9]{17}x";
        String reg1  = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return cardNo.matches(regx) || cardNo.matches(reg1) || cardNo.matches(regex);
    }

    // 验证邮箱
    public static boolean isValidEmail(String str) {
        if (str == null || str.length() == 0)
            return false;
        return str.matches("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
    }

    /**
     * 验证有效的电话
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        // String string = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String  string = "^1[3-8]\\d{9}$";
        Pattern p      = Pattern.compile(string);
        Matcher m      = p.matcher(mobiles);
        return m.matches();
    }

    //可以带+86,0086
    public static boolean isMobileNumber(String mobiles) {
        // String string = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String  string = "^((\\+86)?|(0086)?)1[3-8]\\d{9}$";
        Pattern p      = Pattern.compile(string);
        Matcher m      = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isPayPwd(String pwd) {
        String  string = "^[0-9]\\d{5}$";
        Pattern p      = Pattern.compile(string);
        Matcher m      = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 模糊手机号
     */
    public static String garblePhoneNum(String phone) {
        if (isEmpty(phone))
            return "";
        if (phone.length() < 11)
            return phone;
        return phone.substring(0, 3) + "******" + phone.substring(9);
    }

    /**
     * str为空或者空字符串则返回true。否则返回false
     */
    public static boolean isEmpty(String string) {
        return null == string || "".equals(string.trim()) || "null".equals(string.trim());
    }

    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            ExpManager.sendException(e);
            return false;
        }
    }

    /**
     * 判断应用是否在后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager       am    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLockScreen(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean         flag             = mKeyguardManager.inKeyguardRestrictedInputMode();
        return flag;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo    info    = manager.getPackageInfo(context.getPackageName(), 0);
            String         version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            ExpManager.sendException(e);
            return "0.0";
        }
    }

    /**
     * 获取版本编号
     *
     * @return 当前应用的版本编号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo    info    = manager.getPackageInfo(context.getPackageName(), 0);
            int            code    = info.versionCode;
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            ExpManager.sendException(e);
            return 0;
        }
    }

    /**
     * 获取MD5值
     *
     * @param content
     * @return
     */
    public static String getMD5(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            ExpManager.sendException(e);
        }
        return "";
    }

    /**
     * 获取hash字符串
     *
     * @param digest
     * @return
     */
    private static String getHashString(MessageDigest digest) {
        StringBuilder builder = new StringBuilder();
        for (byte b : digest.digest()) {
            builder.append(Integer.toHexString((b >> 4) & 0xf));
            builder.append(Integer.toHexString(b & 0xf));
        }
        return builder.toString();
    }

    public static void showProgressView(ImageView imageProgress) {
        try {
            if (imageProgress == null) {
                return;
            }
            imageProgress.setVisibility(View.VISIBLE);
            AnimationDrawable drawable = (AnimationDrawable) imageProgress.getDrawable();
            drawable.start();
        } catch (Exception e) {
            e.printStackTrace();
            ExpManager.sendException(e);
        }
    }

    public static SpannableString processColor(String str, String filter) {
        if (str == null) {
            str = "";
        }
        SpannableString span = new SpannableString(str);
        if (filter != null && filter.length() > 0) {
            int start = str.indexOf(filter);
            if (start >= 0) {
                CharacterStyle style = new ForegroundColorSpan(0xffCD3436);
                span.setSpan(style, start, start + filter.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return span;
    }

    /**
     * 获取网络状态
     *
     * @return
     */
    public static int getConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         info                = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            if ("WIFI".equals(name)) {
                return 1;
            } else {
                return 2;
            }
        } else {
            // 没有可用网络
            return 0;
        }
    }

    public static String getConnectivityName() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         info                = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();

            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int networkType = info.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        name = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        name = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        name = "4G";
                        break;
                }
            }
            return name;
        } else {
            // 没有可用网络
            return "";
        }
    }

    public static String getIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (isIP(hostAddress)) {
                            return hostAddress;
                        } else {
                            return "127:0:0:1";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExpManager.sendException(e);
        }
        return "127:0:0:1";
    }

    public static void dismassProgressView(ImageView imageProgress) {
        try {
            if (imageProgress == null) {
                return;
            }
            imageProgress.setVisibility(View.GONE);
            AnimationDrawable drawable = (AnimationDrawable) imageProgress.getDrawable();
            drawable.stop();
        } catch (Exception e) {
            e.printStackTrace();
            ExpManager.sendException(e);
        }
    }

    /**
     * 检查网络状态
     */
    public static boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo         info                = connectivityManager.getActiveNetworkInfo();
        // 没有可用网络
        return info != null && info.isAvailable();
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        if (!isCanUseSim(context)) {
            return "";
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String phoneNumber = tm.getLine1Number();// 获取本机号码 有些手机这里会出异常
            if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.contains("+86")) {
                String replace = phoneNumber.replace("+86", "");
                return replace;
            }
            return phoneNumber;
        } catch (Throwable e) {
            return "";
        }

    }

    private static boolean isCanUseSim(Context context) {
        boolean ret = true;
        try {
            TelephonyManager mgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            ret = TelephonyManager.SIM_STATE_ABSENT != mgr.getSimState();
            //ret = TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
            ExpManager.sendException(e);
        }

        return ret;
    }

    /**
     * 获取mac地址
     *
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        String      mac  = "null";
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo    info = wifi.getConnectionInfo();
        mac = info.getMacAddress();
        return mac;
    }

    /**
     * 获取imei （所有的设备都可以返回一个 TelephonyManager.getDeviceId()）
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager tm   = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String           imei = tm.getDeviceId();
        if (imei == null) {
            // android pad
            imei = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
        return imei;
    }

    /**
     * 获取android id （所有添加有谷歌账户的设备可以返回一个 ANDROID_ID）
     *
     * @param context
     * @return
     */
    public static String getAndriodId(Context context) {
        String android_id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return android_id;
    }

    /**
     * 获取Sim序列号 所有的GSM设备可以返回一个TelephonyManager.getSimSerialNumber() 所有的CDMA 设备对于
     * getSimSerialNumber() 却返回一个空值
     *
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    public static String getVendor() {
        return Build.BRAND;
    }

    /**
     * 获取SDK版本
     *
     * @return
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static List<String> getAllProcessName(Context context) {
        List<String>                list                = new ArrayList<String>();
        int                         pid                 = android.os.Process.myPid();
        ActivityManager             mActivityManager    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningAppProcesses = mActivityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : runningAppProcesses) {
            list.add(appProcess.processName);
        }
        return list;
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            ExpManager.sendException(e);
        }

        return resultData;
    }

    public static int getWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
    }

    /**
     * 获取验证码
     */
    public static String getsmsyzm() {
        try {
            Uri      uri           = Uri.parse("content://sms/inbox");
            String[] projection    = new String[]{"address", "person", "body"};
            String   selection     = " address='106902816618' or address='1069028123456'";
            String[] selectionArgs = new String[]{};
            String   sortOrder     = "date desc";
            Cursor   cur           = MyApplication.getInstance().getContentResolver().query(uri, projection, null, null, sortOrder);
            if (cur != null && cur.getCount() > 0) {
                cur.moveToFirst();
                String body = cur.getString(cur.getColumnIndex("body")).replaceAll("\n", " ");
                cur.close();
                if (!isEmpty(body) && (body.contains("德家助手") || body.contains("快钱支付"))) {
                    return getyzm(body, 6);
                } else {
                    return "";
                }
            }
            if (cur != null) {
                cur.close();
            }
            return "";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            sendExceptionString(e, "from Utils.getsmsyzm()");
            e.printStackTrace();
            return "";

        }

    }

    /**
     * @param source
     * @param decimalNumber 精确到几位小数
     * @return
     */
    public static String formatNumber(double source, int decimalNumber) {
        if (decimalNumber <= 0) {
            return "" + (int) source;
        } else {
            StringBuilder sb = new StringBuilder("#0.");
            for (int i = 1; i <= decimalNumber; i++) {
                sb.append("0");
            }
            DecimalFormat df  = new DecimalFormat(sb.toString());
            String        str = df.format(source);
            return str;
        }

    }


    /**
     * 保存有效的小数
     *
     * @param source
     * @param decimalNumber 保存几位的有效数字
     * @return
     */
    public static String formatMaximumFraction(double source, int decimalNumber) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(decimalNumber);
        return df.format(source);
    }

    /**
     * 输出异常信息
     */
    public static void sendExceptionString(Throwable throwable, String des) {
        String msg = "try catch--->\n";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter  pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            pw.flush();
            msg += sw.toString();
            msg += "/n--->" + des;
            // UMengUtilProxy.reportException(msg);
            pw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ExpManager.sendException(e);
            // UMengUtilProxy.reportException(e);
        }

    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager  wm         = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager  wm         = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    //    /**
    //     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
    //     */
    //    public static int dipTopx(Context context, float dpValue) {
    //        final float scale = context.getResources().getDisplayMetrics().density;
    //        return (int) (dpValue * scale + 0.5f);
    //    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxTodip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static AnimationSet getAnimationSet() {
        // 动画集合
        AnimationSet set = new AnimationSet(false);
        // 旋转
        // RotateAnimation rotate = new RotateAnimation(0, 360,
        // Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // rotate.setDuration(2000);
        // rotate.setFillAfter(true);
        // 缩放
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);
        // 透明
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);
        // 动画添加进集合
        // set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);
        return set;
    }

    // source = "颜色:红色;香味:奶香;净含量:30ml";
    // return 红色 奶香 30ml

    public static String formatAttr(String source) {
        if (source != null) {
            String[]      attrs = source.split(";");
            StringBuilder sb    = new StringBuilder();
            for (String string : attrs) {
                String[] keyValue = string.split(":");
                if (keyValue != null && keyValue.length > 1) {
                    if (sb.length() > 0)
                        sb.append("   ");// 加几个空格
                    sb.append(keyValue[1]);
                }
            }
            return sb.toString();
        }
        return source;
    }


    /**
     * 获取硬盘缓存的路径地址。
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取运营商
     *
     * @return
     */
    public static String getOperatorName() {
        TelephonyManager tm       = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String           operator = tm.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                // operatorName="中国移动";
                return "CMCC";
            } else if (operator.equals("46001")) {
                // operatorName="中国联通";
                return "CUCC";
            } else if (operator.equals("46003")) {
                // operatorName="中国电信";
                return "CTC";
            }
        }
        return "OTHER";
    }

    public static boolean checkValidContext(String content) {
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            L.d("checkValidContext", c + " = " + (int) c);
            if ((int) c == 55357) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 用户是否同意录音权限
     *
     * @return true 同意 false 拒绝
     */
    public static boolean isVoicePermission() {

        try {
            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState = record.getRecordingState();
            if (recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                return false;
            }
            record.release();
            return true;
        } catch (Exception e) {
            ExpManager.sendException(e);
            return false;
        }

    }
}
