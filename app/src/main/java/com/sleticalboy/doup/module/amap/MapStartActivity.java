package com.sleticalboy.doup.module.amap;

import android.content.Context;
import android.content.Intent;

import com.sleticalboy.doup.R;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class MapStartActivity extends CheckPermissionsActivity {

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_map;
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MapStartActivity.class));
    }
}
