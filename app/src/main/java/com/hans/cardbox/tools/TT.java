package com.hans.cardbox.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hans.cardbox.App;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TT {

    public final static String MNT = "/mnt";

    public final static String PATH = "/sdcard/autoeasy/";

    public final static String CACHE = "cache/";

    public final static String TAG = "TT";

    public final static String KEYLIST =
            "139E53F54A1DB2B0C850F728FD828456DABD1849420BC454F5F3CB147356EF369421899328DB3A48DE2A387C57E96949F7D76E2BBC2DFA8BB24764029AB80199";

    public static ProgressDialog dialog = null;


    /**
     * 读取输入流数�?
     *
     * @param inStream 输入�?
     * @return
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            LG.e(TAG, "file is no exits");
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static String getPath() {
        String filePath;
        if ("9".equals(android.os.Build.VERSION.SDK)) {
            filePath = MNT + PATH;
        } else {
            filePath = PATH;
        }
        return filePath;
    }


    /**
     * 获得当前日期
     *
     * @return
     */
    public static String getOnlyDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }


    /**
     * 保存文件
     *
     * @param filename 文件名称
     * @param content  文件内容
     * @throws Exception
     */
    public static void saveFile1111(String filename, String content, Context context)
            throws Exception {
        // 私有操作模式：所创建的文件只能被本应用访问，其它应用无权限访问该文件,另外内容是以覆盖方式添加到文件中的
        FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        byte[] data = content.getBytes();
        outStream.write(data);
        outStream.close();// javase IO
    }

    public static void saveFile(String filename, String content, Context context) throws Exception {
        try {
            // 私有操作模式：所创建的文件只能被本应用访问，其它应用无权限访问该文件,另外内容是以覆盖方式添加到文件中的
            FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(outStream);
            osw.write(content);
            osw.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     *
     * @param filename 文件名称
     * @return
     * @throws Exception
     */
    public static String readFile(String filename, Context context) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            File file = new File(context.getFilesDir(), filename);
            if (file.exists()) {
                FileInputStream inStream = context.openFileInput(filename);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                inStream.close();
                byte[] data = outStream.toByteArray();
                return new String(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static byte[] readFile(String filename) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            File file = new File(filename);
            if (file.exists()) {
                FileInputStream inStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                inStream.close();
                return outStream.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    // 檢查WIFI是否連接
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }

        return false;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        float rs = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics());
        return (int) rs;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm =
                (WindowManager) App.mApp.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        float rs = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics());
        return (int) (rs);
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check =
                    "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            // Logger.error("验证邮箱地址错误", e);
            flag = false;
        }

        return flag;
    }

    /**
     * @param mobiles
     * @return
     * @Description 判断是不是手机号
     * @date 2015-5-5 下午1:19:49
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
    /*
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通） 3G上网卡 14 4G号段 17 总结起来就是第一位必定为1，第二位必定为3-8，其他位置的可以为0-9
     */

        try {
            String telRegex = "[1]\\d\\d{9}";// "[1]"代表第1位为数字1，"[3-8]"代表第二位可以为3到8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            // String telRegex = "[1][3-8]\\d{9}";//
            // "[1]"代表第1位为数字1，"[3-8]"代表第二位可以为3到8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            // "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"(过时)
            Pattern p = Pattern.compile(telRegex);
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            // Logger.error("验证手机号码错误", e);
            flag = false;
        }
        return flag;
    }

    /***
     * 校验手机号是否11位。
     *
     * @return
     */
    public static boolean checkMobileNolength(String mobiles) {
        boolean flag = false;
        if (mobiles.length() == 11) {
            flag = true;
        }
        return flag;
    }

    /***
     * 校验手机号是否11位。
     *
     * @return
     */
    public static boolean checkIdentifyCodelength(String code) {
        boolean flag = false;
        if (code.length() == 6) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取指定路径的图片
     **/
    public static byte[] getImage(String urlpath) throws Exception {
        URL url = new URL(urlpath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        // 别超过6秒。
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            return readStream(inputStream);
        }
        return null;
    }

    /**
     * 获取屏幕宽度px
     *
     * @param activity
     */
    public static int getDisplayWith(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕宽度高度
     *
     * @param activity
     */
    public static int getDisplayHeight(Activity activity) {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     */
    public static int getDisplayStateHeight(Activity activity) {
        int statusHeight;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        if (Build.VERSION.SDK_INT < 15) {
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusHeight = frame.top;
        } else {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = getResources().getDimensionPixelSize(x);
                statusHeight = sbar;

            } catch (Exception e1) {
                Log.e("fail", "get status bar height fail");
                e1.printStackTrace();
                statusHeight = 0;
            }
        }
        return statusHeight;
    }

    /**
     * @param con 上下文参数
     * @return 屏幕密度
     */
    public static float getScale(Context con) {
        return con.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取字符串长度 汉字算两个字符
     *
     * @param str 要获取长度的字符串
     * @return
     */
    public static int getCount(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int count = 0;
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            count += (chs[i] > 0xff) ? 2 : 1;
        }
        return count;
    }

    /**
     * 得到layoutInflater
     *
     * @return
     */
    public static LayoutInflater getLayoutInflater() {
        return (LayoutInflater) App.mApp.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Flash是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkFlashEnable(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> infoList = pm.getInstalledPackages(PackageManager.GET_SERVICES);
        for (PackageInfo info : infoList) {
            if ("com.adobe.flashplayer".equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean existSDCard() {
        return (android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED));
    }


    /**
     * 关闭输入法
     *
     * @param view
     * @author tanmf@bitauto.com
     */
    public static void hideInputMethod(Context mContext, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftKeyBoard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        IBinder iBinder = activity.getCurrentFocus().getWindowToken();
        if (inputMethodManager == null || iBinder == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(iBinder, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 判断是否点击事件在editText
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right =
                    left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * drawable 转化成bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE
                        ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 获取颜色
     *
     * @param color
     * @return 获取失败返回透明色
     */
    public static int getColor(int color) {
        try {
            return App.mApp.getResources().getColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Color.TRANSPARENT;
    }

    /**
     * 获取Drawable
     *
     * @param id
     * @return 失敗返回null
     */
    public static Drawable getDrawable(int id) {
        try {
            return App.mApp.getResources().getDrawable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据ID获取String
     *
     * @param resid
     * @return 失败返回空字符串
     */
    public static String getString(int resid) {
        try {
            return App.mApp.getResources().getString(resid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取StringArray
     *
     * @param resid
     * @return
     */
    public static String[] getStringArray(int resid) {
        return App.mApp.getResources().getStringArray(resid);
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 获取View的图片
     *
     * @param v
     * @return
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }

    /**
     * 判空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string) || " ".equals(string);
    }

    /**
     * 判断是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 读取Assets文件
     *
     * @param context
     * @param filename
     * @return
     */
    public static String getAssetsFile(Context context, String filename) {
        try {
            return TT.convertStreamToString(context.getResources().getAssets().open(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 判断当前应用是否启动
     * @return boolean
     */
    public static boolean isRunning() {
        boolean running = false;
        ActivityManager am = (ActivityManager) App.mApp.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        String MY_PKG_NAME = App.mApp.getPackageName();

//        for (RunningTaskInfo task : tasks) {
//            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName())) {
//                LG.d("mylog", "task.numActivities is: " + task.numActivities);
//                if (task.numActivities > 1) {
//                    return true;
//                }
//            }
//        }

        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
                    || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                running = true;
                LG.i(TAG, info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="
                        + info.baseActivity.getPackageName());
                break;
            }
        }
        return running;
    }

    /**
     * 判断App是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals(context.getPackageName())) {
            return true;
        }

        return false;
    }

    /**
     * 程序进入后台(按下home键)？？？
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getApplicationContext()
                        .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }




    public static int getCountIgnoreEg(String commnet) {
        return commnet.length();
    }

    /**
     * 导入视图
     * @param id layoutID
     * @return
     * @author hanbo1@yiche.com
     * @date 2014-12-17 下午4:36:28
     */
    public static View inflate(int id) {
        try {
            return LayoutInflater.from(App.mApp).inflate(id, null);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入视图
     * @param id
     * @param container
     * @param attach
     * @return
     */
    public static View inflate(int id, ViewGroup container, boolean attach) {
        return LayoutInflater.from(App.mApp).inflate(id, container, attach);
    }

    /**
     * 获取Resources
     * @return
     */
    public static Resources getResources() {
        return App.mApp.getResources();
    }

    /**
     * 格式化带小数点的数字
     *
     * @param number
     * @param format
     * @return
     * @Description
     * @author wangsb@yiche.com
     * @date 2014-12-23 下午6:15:22
     */
    public static String numberFomat(String number, String format) {

        try {
            if (number == null) number = "";

            String checkExpressions = "^[\\-\\d\\.]+$";
            if (!Pattern.matches(checkExpressions, number)) {
                throw new java.lang.NumberFormatException();
            }
            DecimalFormat df = new DecimalFormat(format);
            return df.format(Double.parseDouble(number));
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 保留一位小数，四舍五入
     *
     * @param scale
     * @return
     */
    public static String translate2point1(float scale) {
        BigDecimal b = new BigDecimal(scale);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String reult = f1 + "";
        if (reult.endsWith(".0")) {
            return (int) f1 + "";
        } else {
            return reult;
        }
    }

    /**
     * 保留一位小数，四舍五入,带0
     *
     * @param scale
     * @return
     */
    public static String translate2point1Zero(float scale) {
        BigDecimal b = new BigDecimal(scale);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        String reult = f1 + "";
        return reult;

    }

    /**
     * 四舍五入保留几位小数
     * @param weishu 保留几位
     * @param scale 数字
     * @return
     */
    public static String translate2point1(int weishu, float scale) {
        BigDecimal b = new BigDecimal(scale);
        float f1 = b.setScale(weishu, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1 + "";
    }


    /**
     * @param context
     * @return
     * @Description 判断手机是否安装了浏览器
     * @author pengf@yiche.com
     * @date 2015-2-5 下午1:29:47
     */
    public static boolean hasBrowser(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://"));

        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        final int size = (list == null) ? 0 : list.size();
        return size > 0;
    }

    /**
     * @param intent
     * @return
     * @Description 检查intent是否可以处理
     * @author sud@yiche.com
     * @date 2015年5月19日 下午5:13:17
     */
    public static boolean checkIntent(Intent intent) {
        return checkIntent(intent, PackageManager.GET_INTENT_FILTERS);
    }

    /**
     * @param intent
     * @return
     * @Description 检查intent是否可以处理
     * @author sud@yiche.com
     * @date 2015年5月19日 下午5:13:17
     */
    public static boolean checkIntent(Intent intent, int flags) {
        PackageManager pm = App.mApp.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, flags);
        final int size = (activities == null) ? 0 : activities.size();
        return size > 0;
    }

    /**
     * MD5加密
     * @param source
     * @return
     */
    public static String getMD5String(String source) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            LG.w("hh", e.getMessage());
        }
        BigInteger bi = new BigInteger(hash).abs();
        int RADIX = 10 + 26; // 10 digits + 26 letters
        return bi.toString(RADIX);
    }


    /**
     * 获取当前应用的meta-kps
     * @return 渠道ID
     * @author sudi@yiche.com
     */
    public static String getMetaData(String metaName) {
        try {
            ApplicationInfo appInfo =
                    App.mApp.getPackageManager().getApplicationInfo(App.mApp.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断字符串是不是数字
     * @param str
     * @return
     * @author wangsb@yiche.com
     * @date 2015-5-5 下午1:21:34
     */
    public static boolean stringIsNum(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");// 同等于"-?\\d+.?\\d+"
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * @param src
     * @return
     * @Description url decode
     * @author sud@yiche.com
     * @date 2015年5月13日 下午2:04:13
     */
    public static String decodeUrl(String src) {
        if (isEmpty(src)) {
            return "";
        }
        return URLDecoder.decode(src);
    }


    /**
     * 格式化数字显示，每三位加逗号 ：1,452,365
     * @param value
     * @return
     */
    public static String formatPrice(double value) {
        return String.format(Locale.getDefault(), "%1$,01d", (long) Math.round(value));
    }
}
