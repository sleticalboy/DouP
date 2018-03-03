package com.sleticalboy.doup.jpush.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.jpush.JPushUtil;
import com.sleticalboy.doup.jpush.LocalBroadcastManager;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;

public class IndexActivity extends InstrumentedActivity implements OnClickListener {

    private TextView mRegId;
    private EditText msgText;

    public static boolean isForeground = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jpush_activity_main);
        initView();
        // used for receive msg
        registerMessageReceiver();
    }

    private void initView() {
        TextView mImei = findViewById(R.id.tv_imei);
        String udid = JPushUtil.getIMEI(getApplicationContext(), "");
        if (null != udid)
            mImei.setText(String.format("IMEI: %s", udid));

        TextView mAppKey = findViewById(R.id.tv_appkey);
        String appKey = JPushUtil.getAppKey(getApplicationContext());
        if (null == appKey)
            appKey = "AppKey异常";
        mAppKey.setText(String.format("AppKey: %s", appKey));

        mRegId = findViewById(R.id.tv_regId);
        mRegId.setText("RegId:");

        String packageName = getPackageName();
        TextView mPackage = findViewById(R.id.tv_package);
        mPackage.setText(String.format("PackageName: %s", packageName));

        String deviceId = JPushUtil.getDeviceId(getApplicationContext());
        TextView mDeviceId = findViewById(R.id.tv_device_id);
        mDeviceId.setText(String.format("deviceId:%s", deviceId));

        String versionName = JPushUtil.getVersion(getApplicationContext());
        TextView mVersion = findViewById(R.id.tv_version);
        mVersion.setText(String.format("Version: %s", versionName));

        findViewById(R.id.init).setOnClickListener(this);

        findViewById(R.id.stopPush).setOnClickListener(this);

        findViewById(R.id.resumePush).setOnClickListener(this);

        findViewById(R.id.getRegistrationId).setOnClickListener(this);

        findViewById(R.id.setting).setOnClickListener(this);

        msgText = findViewById(R.id.msg_rec);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.init:
                init();
                break;
            case R.id.setting:
                startActivity(new Intent(this, PushSetActivity.class));
                break;
            case R.id.stopPush:
                JPushInterface.stopPush(getApplicationContext());
                break;
            case R.id.resumePush:
                JPushInterface.resumePush(getApplicationContext());
                break;
            case R.id.getRegistrationId:
                String rid = JPushInterface.getRegistrationID(getApplicationContext());
                if (!rid.isEmpty()) {
                    mRegId.setText(String.format("RegId:%s", rid));
                } else {
                    Toast.makeText(this, "Get registration fail, JPush initialize failed!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "weather_layout_title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : ").append(messge).append("\n");
                    if (!JPushUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : ").append(extras).append("\n");
                    }
                    setCustomMsg(showMsg.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setCustomMsg(String msg) {
        if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(View.VISIBLE);
        }
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, IndexActivity.class));
    }
}
