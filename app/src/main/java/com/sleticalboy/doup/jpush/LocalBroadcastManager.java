package com.sleticalboy.doup.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 2017/4/14.
 * </pre>
 *
 * @author efan
 */
public final class LocalBroadcastManager {

    private static final String TAG = "JIGUANG-Example";

    private static final boolean DEBUG = false;
    private final Context mAppContext;
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private final Handler mHandler;
    private static final Object mLock = new Object();
    private static LocalBroadcastManager mInstance;

    public static LocalBroadcastManager getInstance(Context context) {
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            return mInstance;
        }
    }

    private LocalBroadcastManager(Context context) {
        mAppContext = context;
        mHandler = new Handler(context.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    LocalBroadcastManager.this.executePendingBroadcasts();
                } else {
                    super.handleMessage(msg);
                }
            }
        };
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        synchronized (mReceivers) {
            ReceiverRecord entry = new ReceiverRecord(filter, receiver);
            ArrayList<IntentFilter> filters = (ArrayList<IntentFilter>) mReceivers.get(receiver);
            if (filters == null) {
                filters = new ArrayList<>(1);
                mReceivers.put(receiver, filters);
            }

            filters.add(filter);

            for (int i = 0; i < filter.countActions(); ++i) {
                String action = filter.getAction(i);
                ArrayList<ReceiverRecord> entries = (ArrayList<ReceiverRecord>) mActions.get(action);
                if (entries == null) {
                    entries = new ArrayList<>(1);
                    mActions.put(action, entries);
                }
                entries.add(entry);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        synchronized (mReceivers) {
            ArrayList<IntentFilter> filters = (ArrayList<IntentFilter>) mReceivers.remove(receiver);
            if (filters != null) {
                for (int i = 0; i < filters.size(); ++i) {
                    IntentFilter filter = (IntentFilter) filters.get(i);
                    for (int j = 0; j < filter.countActions(); ++j) {
                        String action = filter.getAction(j);
                        ArrayList<ReceiverRecord> receivers = (ArrayList<ReceiverRecord>) mActions.get(action);
                        if (receivers != null) {
                            for (int k = 0; k < receivers.size(); ++k) {
                                if (((ReceiverRecord) receivers.get(k)).receiver == receiver) {
                                    receivers.remove(k);
                                    --k;
                                }
                            }
                            if (receivers.size() <= 0) {
                                mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean sendBroadcast(Intent intent) {
        synchronized (mReceivers) {
            String action = intent.getAction();
            String type = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean debug = (intent.getFlags() & Intent.FLAG_DEBUG_LOG_RESOLUTION) != 0;
            if (debug) {
                Log.v("LocalBroadcastManager", "Resolving type " + type + " scheme " + scheme + " of intent " + intent);
            }

            ArrayList<ReceiverRecord> entries = mActions.get(intent.getAction());
            if (entries != null) {
                if (debug) {
                    Log.v("LocalBroadcastManager", "Action list: " + entries);
                }

                ArrayList<ReceiverRecord> receivers = null;

                int i;
                for (i = 0; i < entries.size(); ++i) {
                    ReceiverRecord receiver = (ReceiverRecord) entries.get(i);
                    if (debug) {
                        Log.v("LocalBroadcastManager", "Matching against filter " + receiver.filter);
                    }

                    if (receiver.broadcasting) {
                        if (debug) {
                            Log.v("LocalBroadcastManager", "  Filter\'s target already added");
                        }
                    } else {
                        int match = receiver.filter.match(action, type, scheme, data, categories, "LocalBroadcastManager");
                        if (match >= 0) {
                            if (debug) {
                                Log.v("LocalBroadcastManager", "  Filter matched!  match=0x" + Integer.toHexString(match));
                            }

                            if (receivers == null) {
                                receivers = new ArrayList<>();
                            }

                            receivers.add(receiver);
                            receiver.broadcasting = true;
                        } else if (debug) {
                            String reason;
                            switch (match) {
                                case -4:
                                    reason = "category";
                                    break;
                                case -3:
                                    reason = "action";
                                    break;
                                case -2:
                                    reason = "data";
                                    break;
                                case -1:
                                    reason = "type";
                                    break;
                                default:
                                    reason = "unknown reason";
                            }

                            Log.v("LocalBroadcastManager", "  Filter did not match: " + reason);
                        }
                    }
                }

                if (receivers != null) {
                    for (i = 0; i < receivers.size(); ++i) {
                        ((ReceiverRecord) receivers.get(i)).broadcasting = false;
                    }

                    this.mPendingBroadcasts.add(new BroadcastRecord(intent, receivers));
                    if (!this.mHandler.hasMessages(1)) {
                        this.mHandler.sendEmptyMessage(1);
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }

    }

    private void executePendingBroadcasts() {
        while (true) {
            BroadcastRecord[] brs = null;
            synchronized (this.mReceivers) {
                int br = this.mPendingBroadcasts.size();
                if (br <= 0) {
                    return;
                }

                brs = new BroadcastRecord[br];
                this.mPendingBroadcasts.toArray(brs);
                this.mPendingBroadcasts.clear();
            }

            for (BroadcastRecord br : brs) {
                for (int j = 0; j < br.receivers.size(); ++j) {
                    ((ReceiverRecord) br.receivers.get(j)).receiver.onReceive(this.mAppContext, br.intent);
                }
            }
        }
    }

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    private static class ReceiverRecord {
        final IntentFilter filter;
        final BroadcastReceiver receiver;
        boolean broadcasting;

        ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
            filter = _filter;
            receiver = _receiver;
        }

        public String toString() {
            return "Receiver{" +
                    receiver +
                    " filter=" +
                    filter +
                    "}";
        }
    }
}