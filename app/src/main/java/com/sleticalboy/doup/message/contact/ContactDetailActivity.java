package com.sleticalboy.doup.message.contact;

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
public class ContactDetailActivity extends BaseActivity {

    private static final String NAME = "name";

    @Override
    protected void beforeViews() {
        if (getIntent() != null) {
            Parcelable parcelable = getIntent().getParcelableExtra(NAME);
            Log.d(TAG, "parcelable:" + parcelable);
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
        context.startActivity(new Intent(context, ContactDetailActivity.class).putExtra(NAME, data));
    }
}
