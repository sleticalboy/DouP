package com.sleticalboy.doup.jpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.sleticalboy.doup.jpush.activity.IndexActivity;
import com.sleticalboy.doup.jpush.JPushUtil;
import com.sleticalboy.doup.jpush.LocalBroadcastManager;
import com.sleticalboy.doup.jpush.activity.TestActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            String action = intent.getAction();
            Log.d(TAG, "[JPushReceiver] onReceive - " + action + ", extras: " + printBundle(bundle));

            // 接收Registration Id
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(action)) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
                // 向服务器发送用户的 register id

                // 接收到推送下来的自定义消息:
                // 比如有重大更新需要用户升级应用等
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(action)) {
                String customMsg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                Log.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + customMsg);
                processCustomMessage(context, bundle);

                // 接收到推送下来的通知
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action)) {
                Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
                // 接收到通知后，响应用户的点击行为打开 Activity
                Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context, TestActivity.class);
                if (bundle != null) {
                    i.putExtras(bundle);
                }
                // 在新的任务栈中打开 activity
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(action)) {
                String richPushCallback = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.d(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + richPushCallback);
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(action)) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[JPushReceiver]" + action + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[JPushReceiver] Unhandled intent - " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印所有的 intent extra 数据
     */
    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:").append(key).append(", value: [").append(myKey).append(" - ")
                                .append(json.optString(myKey)).append("]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getString(key));
            }
        }
        return sb.toString();
    }

    // 向 IndexActivity 发送自定义消息

    /**
     * send msg to IndexActivity
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        if (IndexActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(IndexActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(IndexActivity.KEY_MESSAGE, message);
            if (!JPushUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(IndexActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
