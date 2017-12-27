package com.sleticalboy.doup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.eye.VideoBean;

import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class VideoPlayActivity extends AppCompatActivity {

    public static final String VIDEO = "video";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
    }

    public static void startAction(Context context, VideoBean videoBean) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra(VIDEO, videoBean);
        context.startActivity(intent);
    }
}
