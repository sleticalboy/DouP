package com.sleticalboy.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android Studio.
 * Date: 10/29/17.
 *
 * @author sleticalboy
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final String LOG_DIR = File.separator + "DouPCrashLog" + File.separator;

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static final CrashHandler CRASH_HANDLER = new CrashHandler();

    private WeakReference<Context> mReference;

    //用来存储设备信息和异常信息
    private final Map<String, Object> infoMap = new HashMap<>();
    //用于格式化日期,作为日志文件名的一部分
//    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private final DateFormat formatter = DateFormat.getInstance();

    private String mFileName;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return CRASH_HANDLER;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mReference = new WeakReference<>(context);
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置 CrashHandler 为该程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            SystemClock.sleep(3000);
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) return false;
        //收集设备参数信息
        collectDeviceInfo(mReference.get());

        //使用Toast来显示异常信息
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(mReference.get(), "很抱歉,程序出现异常,即将退出.");
            }
        });
        //保存日志文件
        mFileName = saveCatchInfo2File(ex);
        return mFileName != null;
    }

    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infoMap.put("versionName", versionName);
                infoMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occurred when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infoMap.put(field.getName(), field.get(null));
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occurred when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCatchInfo2File(Throwable ex) {
        StringBuilder buffer = new StringBuilder();
        for (final String key : infoMap.keySet()) {
            buffer.append(key).append('=').append(infoMap.get(key)).append('\n');
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        buffer.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            // /storage/emulated/0
            String externalDir = Environment.getExternalStorageDirectory().getPath();
            Log.d(TAG, "saveCatchInfo2File: " + externalDir);
            String fileDir = externalDir + LOG_DIR;
            Log.d(TAG, "saveCatchInfo2File: " + fileDir);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(fileDir);
                if (!dir.exists() && dir.mkdirs()) {
                    Log.d(TAG, "create " + dir);
                }
                FileOutputStream fos = new FileOutputStream(fileDir + fileName);
                fos.write(buffer.toString().getBytes());
                //发送给开发人员
                sendCrashLog2PM(fileDir + fileName);
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occurred while writing file...", e);
        }
        return null;
    }

    /**
     * 将捕获的导致崩溃的错误信息发送给开发人员
     * <p>
     * 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
     */
    private void sendCrashLog2PM(String fileName) {
        if (!new File(fileName).exists()) {
            Toast.makeText(mReference.get(), "日志文件不存在！", Toast.LENGTH_SHORT).show();
            return;
        }
        FileInputStream fis = null;
        BufferedReader reader = null;
        String s;
        try {
            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
            while (true) {
                s = reader.readLine();
                if (s == null) break;
                // 由于目前尚未确定以何种方式发送，所以先打出log日志。
                Log.i("info", s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // 关闭流
            try {
                if (reader != null) reader.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadCrash2Server() {
        // TODO: 1/9/18 将崩溃文件发送到服务器
        File file = new File(mFileName);
        // upload file to server
        Log.d(TAG, file.getAbsolutePath());
    }

    /**
     * 监听 app 崩溃
     */
    public interface OnCrashListener {
        /**
         * Called when the App crashing
         */
        void onCrash();
    }
}
