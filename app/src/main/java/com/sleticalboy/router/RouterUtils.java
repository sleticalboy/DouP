package com.sleticalboy.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 11/6/17.
 *
 * @author sleticalboy
 */
public class RouterUtils {

    public static void setReference(Context context, Intent intent) {
        if (context instanceof Activity && intent != null) {
            Route currentRoute = parseCurrentRouter(context);
            intent.putExtra(UrlRouter.URL_ROUTER_REFERENCE, currentRoute);
        }
    }

    public static Route parseStartRouter(Context context) {
        if (context != null && context instanceof Activity) {
            Intent startIntent = ((Activity) context).getIntent();
            if (startIntent.hasExtra(UrlRouter.URL_ROUTER_REFERENCE)) {
                return startIntent.getParcelableExtra(UrlRouter.URL_ROUTER_REFERENCE);
            }
        }
        return null;
    }

    public static Route parseCurrentRouter(Context context) {
        try {
            if (context != null && context instanceof Activity) {
                Route route = Route.newInstance();
                Intent startIntent = ((Activity) context).getIntent();
                Uri data = startIntent.getData();
                if (data == null) {
                    return null;
                } else {
                    route.schema = RouterUtils.getScheme(data);
                    route.host = RouterUtils.getHost(data);
                    route.path = RouterUtils.getPath(data);
                    ResolveInfo resolveInfo = queryActivity(context, startIntent);
                    if (resolveInfo == null) {
                        return  route;
                    } else {
                        route.pkgName = resolveInfo.activityInfo.packageName;
                        route.activityName = resolveInfo.activityInfo.name;
                        return route;
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResolveInfo queryActivity(Context context, Intent startIntent) {
        if (context == null || startIntent == null) {
            return null;
        }
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryBroadcastReceivers(startIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfoList == null || resolveInfoList.size() == 0) {
            return null;
        }
        int size = resolveInfoList.size();
        if (size == 1) {
            return resolveInfoList.get(0);
        }
        String appPackageName = context.getApplicationContext().getPackageName();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = resolveInfoList.get(i);
            String activityName = resolveInfo.activityInfo.name;
            if (TextUtils.isEmpty(activityName)) {
                continue;
            }
            if (activityName.startsWith(appPackageName)) {
                return resolveInfo;
            }
        }
        return resolveInfoList.get(0);
    }


    public static String getScheme(Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    public static String getHost(Uri uri) {
        return uri == null ? null : uri.getHost();
    }

    public static String getPath(Uri uri) {
        return uri == null || TextUtils.isEmpty(uri.getPath())
                ? null
                : uri.getPath().replace("/", "");
    }
}
