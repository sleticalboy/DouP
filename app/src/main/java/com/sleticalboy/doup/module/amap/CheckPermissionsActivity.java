package com.sleticalboy.doup.module.amap;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.KeyEvent;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 继承了Activity，实现Android6.0的运行时权限检测
 * 需要进行运行时权限检测的Activity可以继承这个类
 *
 * @author hongming.wang
 * @author sleticalboy
 * @创建时间：2016年5月27日 下午3:01:31
 * @项目名称： DouP
 * @文件名称：PermissionsChecker.java
 * @类型名称：PermissionsChecker
 * @修改时间：2018年01月18日01:42:11
 * @since 2.5.0
 */
public abstract class CheckPermissionsActivity extends BaseActivity {
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
            if (isNeedCheck) {
                checkPermissions();
            }
        }
    }

    /**
     * @since 2.5.0
     */
    private void checkPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
//                List<String> needRequestPermissionList = findDeniedPermissions(permissions);
//                if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
//                    String[] array = needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]);
//                    Method method = getClass().getMethod("requestPermissions", new Class[]{String[].class,
//                            Integer.class});
//                    method.invoke(this, array, PERMISSON_REQUESTCODE);
//                }
                RxPermissions rxPermissions = new RxPermissions(this);
                if (!rxPermissions.isGranted(needPermissions[0])
                        || !rxPermissions.isGranted(needPermissions[1])
                        || !rxPermissions.isGranted(needPermissions[2])
                        || !rxPermissions.isGranted(needPermissions[3])
                        || !rxPermissions.isGranted(needPermissions[4])) {
                    rxPermissions.request(needPermissions)
                            .subscribe(granted -> {
                                if (granted) {
                                    // Do other things
                                } else {
                                    // Do something
                                }
                            });
                } else {
                    // Do other things
                }
            } else {
                // Do other things
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod =
                            getClass().getMethod("shouldShowRequestPermissionRationale", String.class);
                    if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                        needRequestPermissionList.add(perm);
                    }
                }
            } catch (Throwable e) {

            }
        }
        return needRequestPermissionList;
    }

    /**
     * 检测是否所有的权限都已经授权
     *
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
