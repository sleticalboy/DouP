package com.sleticalboy.doup.message.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class PushDetailActivity extends BaseActivity {

    private static final String NAME = "data";

    @Override
    protected void beforeViews() {
        if (getIntent() != null) {
            PushMsgBean pushMsgBean = getIntent().getParcelableExtra(NAME);
            Log.d(TAG, "pushMsgBean:" + pushMsgBean);
        }
    }

    @Override
    protected void initView(final Bundle savedInstanceState) {
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

    public static void actionStart(Context context, Parcelable data) {
        context.startActivity(new Intent(context, PushDetailActivity.class).putExtra(NAME, data));
    }
}
