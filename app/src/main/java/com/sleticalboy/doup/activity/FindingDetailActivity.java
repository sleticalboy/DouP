package com.sleticalboy.doup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sleticalboy.doup.R;

import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */

public class FindingDetailActivity extends AppCompatActivity {

    private static final String TAG = "FindingDetailActivity";
    public static final String NAME = "name";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_detail);
        ButterKnife.bind(this);
    }

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, FindingDetailActivity.class);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }
}
