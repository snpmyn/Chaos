package com.chaos.jpush.kit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import timber.log.Timber;

/**
 * @decs: LocalBroadcastManagerKit
 * @author: 郑少鹏
 * @date: 2019/5/31 16:54
 */
public final class LocalBroadcastManagerKit {
    private static final Object M_LOCK = new Object();
    private static LocalBroadcastManagerKit mInstance;
    private final String TAG = this.getClass().getSimpleName();
    private final Context mAppContext;
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap<>();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final Handler mHandler;

    private LocalBroadcastManagerKit(@NotNull Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    LocalBroadcastManagerKit.this.executePendingBroadcasts();
                } else {
                    super.handleMessage(msg);
                }
            }
        };
    }

    public static LocalBroadcastManagerKit getInstance(Context context) {
        synchronized (M_LOCK) {
            if (null == mInstance) {
                mInstance = new LocalBroadcastManagerKit(context.getApplicationContext());
            }
            return mInstance;
        }
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        synchronized (this.mReceivers) {
            ReceiverRecord receiverRecord = new ReceiverRecord(filter, receiver);
            ArrayList<IntentFilter> intentFilters = this.mReceivers.get(receiver);
            if (null == intentFilters) {
                intentFilters = new ArrayList<>(1);
                this.mReceivers.put(receiver, intentFilters);
            }
            intentFilters.add(filter);
            for (int i = 0; i < filter.countActions(); ++i) {
                String action = filter.getAction(i);
                ArrayList<LocalBroadcastManagerKit.ReceiverRecord> receiverRecords = this.mActions.get(action);
                if (null == receiverRecords) {
                    receiverRecords = new ArrayList<>(1);
                    this.mActions.put(action, receiverRecords);
                }
                receiverRecords.add(receiverRecord);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        synchronized (this.mReceivers) {
            ArrayList<IntentFilter> intentFilters = this.mReceivers.remove(receiver);
            if (null != intentFilters) {
                for (int i = 0; i < intentFilters.size(); ++i) {
                    IntentFilter filter = (IntentFilter) intentFilters.get(i);
                    for (int j = 0; j < filter.countActions(); ++j) {
                        String action = filter.getAction(j);
                        ArrayList<LocalBroadcastManagerKit.ReceiverRecord> receiverRecords = this.mActions.get(action);
                        if (null != receiverRecords) {
                            for (int k = 0; k < receiverRecords.size(); ++k) {
                                if (((ReceiverRecord) receiverRecords.get(k)).broadcastReceiver == receiver) {
                                    receiverRecords.remove(k);
                                    --k;
                                }
                            }
                            if (receiverRecords.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean sendBroadcast(@NotNull Intent intent) {
        synchronized (this.mReceivers) {
            String action = intent.getAction();
            String type = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
            Uri data = intent.getData();
            String scheme = intent.getScheme();
            Set<String> categories = intent.getCategories();
            boolean debug = (intent.getFlags() & 8) != 0;
            if (debug) {
                Timber.d("Resolving type %s scheme %s of intent %s", type, scheme, intent);
            }
            ArrayList<LocalBroadcastManagerKit.ReceiverRecord> receiverRecords = this.mActions.get(intent.getAction());
            if (null != receiverRecords) {
                if (debug) {
                    Timber.d("Action list: %s", receiverRecords);
                }
                ArrayList<LocalBroadcastManagerKit.ReceiverRecord> receivers = null;
                int i;
                for (i = 0; i < receiverRecords.size(); ++i) {
                    ReceiverRecord receiver = (ReceiverRecord) receiverRecords.get(i);
                    if (debug) {
                        Timber.d("Matching against filter %s", receiver.intentFilter);
                    }
                    if (receiver.broadcasting) {
                        if (debug) {
                            Timber.d("  Filter's target already added");
                        }
                    } else {
                        int match = receiver.intentFilter.match(action, type, scheme, data, categories, TAG);
                        if (match >= 0) {
                            if (debug) {
                                Timber.d("Filter matched! match=0x %s", Integer.toHexString(match));
                            }
                            if (null == receivers) {
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
                            Timber.d("Filter did not match: %s", reason);
                        }
                    }
                }
                if (null != receivers) {
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
        if (this.sendBroadcast(intent)) {
            this.executePendingBroadcasts();
        }
    }

    private void executePendingBroadcasts() {
        while (true) {
            BroadcastRecord[] broadcastRecords;
            synchronized (this.mReceivers) {
                int br = this.mPendingBroadcasts.size();
                if (br <= 0) {
                    return;
                }
                broadcastRecords = new BroadcastRecord[br];
                this.mPendingBroadcasts.toArray(broadcastRecords);
                this.mPendingBroadcasts.clear();
            }
            for (BroadcastRecord var7 : broadcastRecords) {
                for (int j = 0; j < var7.receiverRecords.size(); ++j) {
                    var7.receiverRecords.get(j).broadcastReceiver.onReceive(this.mAppContext, var7.intent);
                }
            }
        }
    }

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receiverRecords;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> receiverRecords) {
            this.intent = intent;
            this.receiverRecords = receiverRecords;
        }
    }

    private static class ReceiverRecord {
        final IntentFilter intentFilter;
        final BroadcastReceiver broadcastReceiver;
        boolean broadcasting;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.intentFilter = intentFilter;
            this.broadcastReceiver = broadcastReceiver;
        }

        @NonNull
        @Override
        public String toString() {
            return "ReceiverRecord{" +
                    "intentFilter=" + intentFilter +
                    ", broadcastReceiver=" + broadcastReceiver +
                    ", broadcasting=" + broadcasting +
                    '}';
        }
    }
}