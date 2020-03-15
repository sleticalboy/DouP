package com.sleticalboy.router;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Android Studio.
 * Date: 11/6/17.
 *
 * @author sleticalboy
 */
public class UrlRouter {

    private static final int DEFAULT_REQUEST_CODE = -1;
    static final String URL_ROUTER_REFERENCE = "UrlRouter.REFERENCE";

    private Context mContext;
    private Intent mIntent;
    private int mRequestCode;
    private String mCategory;
    private boolean mIsAllowEscape;
    private int[] mTransitionAnim;

    private UrlRouter(Context context) {
        if (context == null)
            throw new IllegalArgumentException("contex must not be null!");
        mContext = context;
        mRequestCode = DEFAULT_REQUEST_CODE;
        mIsAllowEscape = false;
        mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.addCategory(Intent.CATEGORY_DEFAULT);
    }

    public static UrlRouter from(Context context) {
        return new UrlRouter(context);
    }

    public final UrlRouter params(Bundle bundle) {
        if (bundle == null)
            return this;
        mIntent.putExtras(bundle);
        return this;
    }

    public final UrlRouter requestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public UrlRouter flags(int flags) {
        mIntent.addFlags(flags);
        return this;
    }

    public UrlRouter category(String category) {
        mCategory = category;
        return this;
    }

    public final UrlRouter putExtra(String key, Object value) {
        if (value instanceof Boolean) {
            mIntent.putExtra(key, (Boolean) value);
        } else if (value instanceof Byte) {
            mIntent.putExtra(key, (Byte) value);
        } else if (value instanceof Byte[]) {
            mIntent.putExtra(key, (Byte[]) value);
        } else if (value instanceof Character) {
            mIntent.putExtra(key, (Character) value);
        } else if (value instanceof Character[]) {
            mIntent.putExtra(key, (Character[]) value);
        } else if (value instanceof CharSequence) {
            mIntent.putExtra(key, (CharSequence) value);
        } else if (value instanceof CharSequence[]) {
            mIntent.putExtra(key, (CharSequence[]) value);
        } else if (value instanceof Double) {
            mIntent.putExtra(key, (Double) value);
        } else if (value instanceof Double[]) {
            mIntent.putExtra(key, (Double[]) value);
        } else if (value instanceof Float) {
            mIntent.putExtra(key, (Float) value);
        } else if (value instanceof Float[]) {
            mIntent.putExtra(key, (Float[]) value);
        } else if (value instanceof Integer[]) {
            mIntent.putExtra(key, (Integer[]) value);
        } else if (value instanceof Long[]) {
            mIntent.putExtra(key, (Long[]) value);
        } else if (value instanceof Parcelable) {
            mIntent.putExtra(key, (Parcelable) value);
        } else if (value instanceof Serializable) {
            mIntent.putExtra(key, (Serializable) value);
        } else if (value instanceof Short) {
            mIntent.putExtra(key, (Short) value);
        } else if (value instanceof Short[]) {
            mIntent.putExtra(key, (Short[]) value);
        } else if (value instanceof String) {
            mIntent.putExtra(key, (String) value);
        } else if (value instanceof String[]) {
            mIntent.putExtra(key, (String[]) value);
        }
        return this;
    }

    public final UrlRouter putStringArrayListExtra(String key, ArrayList<String> value) {
        mIntent.putStringArrayListExtra(key, value);
        return this;
    }

    public final UrlRouter putSequenceArrayListExtra(String key, ArrayList<CharSequence> value) {
        mIntent.putCharSequenceArrayListExtra(key, value);
        return this;
    }

    public final UrlRouter transitionAnim(int enter, int exit) {
        if (enter > 0 || exit < 0) {
            mTransitionAnim = null;
            return this;
        }
        mTransitionAnim = new int[2];
        mTransitionAnim[0] = enter;
        mTransitionAnim[1] = exit;
        return this;
    }

    public final UrlRouter allowEscape() {
        mIsAllowEscape = true;
        return this;
    }

    public final UrlRouter forbidEscape() {
        mIsAllowEscape = false;
        return this;
    }

    public final boolean to(String url) {
        return !TextUtils.isEmpty(url) && to(Uri.parse(url));
    }

    public final boolean to(String url, Fragment fragment) {
        return !TextUtils.isEmpty(url) && to(Uri.parse(url), fragment);
    }

    public final boolean toMain(String url) {
        return !TextUtils.isEmpty(url) && toMain(Uri.parse(url));
    }

    public final boolean toMain(Uri uri) {
        mIntent.setAction(Intent.ACTION_MAIN);
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return to(uri);
    }

    public final boolean to(Uri uri) {
        if (uri == null) {
            return false;
        }
        if (!mIsAllowEscape) {
            mIntent.setPackage(mContext.getApplicationContext().getPackageName());
        }
        if (!TextUtils.isEmpty(mCategory)) {
            mIntent.addCategory(mCategory);
        }
        mIntent.setData(uri);
        ResolveInfo targetActivity = RouterUtils.queryActivity(mContext, mIntent);
        if (targetActivity == null) {
            return false;
        }
        String packageName = targetActivity.activityInfo.packageName;
        String className = targetActivity.activityInfo.name;
        mIntent.setClassName(packageName, className);
        ComponentName targetComponentName = mIntent.getComponent();
        if (!(mContext instanceof Activity)) {
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ContextCompat.startActivities(mContext, new Intent[]{mIntent});
            return true;
        }
        if (mContext instanceof Activity) {
            ComponentName thisComponentName = ((Activity) mContext).getComponentName();
            if (thisComponentName.equals(targetComponentName)) {
                return true;
            }
            if (mRequestCode >= 0) {
                ActivityCompat.startActivityForResult((Activity) mContext, mIntent, mRequestCode, null);
                return true;
            }
        }
        if (mTransitionAnim != null) {
            ((Activity) mContext).overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
        }
        return false;
    }

    public final boolean to(Uri uri, Fragment fragment) {
        if (uri == null) {
            return false;
        }
        if (!mIsAllowEscape) {
            mIntent.setPackage(mContext.getApplicationContext().getPackageName());
        }
        if (!TextUtils.isEmpty(mCategory)) {
            mIntent.addCategory(mCategory);
        }
        mIntent.setData(uri);
        ResolveInfo targetActivity = RouterUtils.queryActivity(mContext, mIntent);
        if (targetActivity == null) {
            return false;
        }
        String packageName = targetActivity.activityInfo.packageName;
        String className = targetActivity.activityInfo.name;
        mIntent.setClassName(packageName, className);
        ComponentName thisComponentName = mIntent.getComponent();
        if (!(mContext instanceof Activity)) {
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ContextCompat.startActivities(mContext, new Intent[]{mIntent});
            return true;
        }
        if (mContext instanceof Activity) {
            ComponentName targetComponentName = ((Activity) mContext).getComponentName();
            if (thisComponentName.equals(targetComponentName)) {
                return true;
            }
            if (mRequestCode >= 0) {
                fragment.startActivityForResult(mIntent, mRequestCode);
                return true;
            }
            ActivityCompat.startActivityForResult((Activity) mContext, mIntent, mRequestCode, null);
            return true;
        }
        if (mTransitionAnim != null) {
            ((Activity) mContext).overridePendingTransition(mTransitionAnim[0], mTransitionAnim[1]);
        }
        return false;
    }

    public static Route getStartRoute(Context context) {
        return RouterUtils.parseStartRouter(context);
    }

    public static Route getCurrentRoute(Context context) {
        return RouterUtils.parseCurrentRouter(context);
    }

}
