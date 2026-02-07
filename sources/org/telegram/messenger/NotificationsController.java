package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.PostProcessor;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import com.exteragram.messenger.utils.AppUtils;
import com.google.android.gms.cast.framework.media.internal.zzo$$ExternalSyntheticApiModelOutline0;
import com.radolyn.ayugram.AyuConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.messenger.utils.tlutils.TlUtils;
import org.telegram.messenger.voip.VoIPGroupNotification;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.Components.spoilers.SpoilerEffect;
import org.telegram.p023ui.PopupNotificationActivity;
import org.telegram.p023ui.Stories.recorder.StoryEntry;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes.dex */
public class NotificationsController extends BaseController {
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    private static volatile NotificationsController[] Instance = null;
    public static String OTHER_NOTIFICATIONS_CHANNEL = null;
    public static final int SETTING_MUTE_2_DAYS = 2;
    public static final int SETTING_MUTE_8_HOURS = 1;
    public static final int SETTING_MUTE_CUSTOM = 5;
    public static final int SETTING_MUTE_FOREVER = 3;
    public static final int SETTING_MUTE_HOUR = 0;
    public static final int SETTING_MUTE_UNMUTE = 4;
    public static final int SETTING_SOUND_OFF = 1;
    public static final int SETTING_SOUND_ON = 0;
    public static final int TYPE_CHANNEL = 2;
    public static final int TYPE_GROUP = 0;
    public static final int TYPE_PRIVATE = 1;
    public static final int TYPE_REACTIONS_MESSAGES = 4;
    public static final int TYPE_REACTIONS_STORIES = 5;
    public static final int TYPE_STORIES = 3;
    protected static AudioManager audioManager;
    private static final Object[] lockObjects;
    private static NotificationManagerCompat notificationManager;
    private static final LongSparseArray sharedPrefCachedKeys;
    private static NotificationManager systemNotificationManager;
    private AlarmManager alarmManager;
    private boolean channelGroupsCreated;
    private Runnable checkStoryPushesRunnable;
    private final ArrayList<MessageObject> delayedPushMessages;
    NotificationsSettingsFacade dialogsNotificationsFacade;
    private final LongSparseArray fcmRandomMessagesDict;
    private Boolean groupsCreated;
    private boolean inChatSoundEnabled;
    private int lastBadgeCount;
    private int lastButtonId;
    public long lastNotificationChannelCreateTime;
    private int lastOnlineFromOtherDevice;
    private long lastSoundOutPlay;
    private long lastSoundPlay;
    private final LongSparseArray lastWearNotifiedMessageId;
    private String launcherClassName;
    private SpoilerEffect mediaSpoilerEffect;
    private Runnable notificationDelayRunnable;
    private PowerManager.WakeLock notificationDelayWakelock;
    private String notificationGroup;
    private int notificationId;
    private boolean notifyCheck;
    private long openedDialogId;
    private final HashSet<Long> openedInBubbleDialogs;
    private long openedTopicId;
    private int personalCount;
    public final ArrayList<MessageObject> popupMessages;
    public ArrayList<MessageObject> popupReplyMessages;
    private final LongSparseArray pushDialogs;
    private final LongSparseArray pushDialogsOverrideMention;
    public final ArrayList<MessageObject> pushMessages;
    private final LongSparseArray pushMessagesDict;
    public boolean showBadgeMessages;
    public boolean showBadgeMuted;
    public boolean showBadgeNumber;
    private final LongSparseArray smartNotificationsDialogs;
    private int soundIn;
    private boolean soundInLoaded;
    private int soundOut;
    private boolean soundOutLoaded;
    private SoundPool soundPool;
    private int soundRecord;
    private boolean soundRecordLoaded;
    char[] spoilerChars;
    private final ArrayList<StoryNotification> storyPushMessages;
    private final LongSparseArray storyPushMessagesDict;
    private int total_unread_count;
    private final LongSparseArray wearNotificationsIds;
    private static final DispatchQueue notificationsQueue = new DispatchQueue("notificationsQueue");
    public static long globalSecretChatId = DialogObject.makeEncryptedDialogId(1);

    /* renamed from: $r8$lambda$LYS4eiu9rRCuQmcgvxsvgAg-duI, reason: not valid java name */
    public static /* synthetic */ void m3916$r8$lambda$LYS4eiu9rRCuQmcgvxsvgAgduI(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static /* synthetic */ void $r8$lambda$XCcar2pyUU3PNWJSRPBNWRsFJM0(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static /* synthetic */ void $r8$lambda$m8WhB6SNQirs3X5XQWhhP5mgO4s(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public void processReadStories() {
    }

    static {
        notificationManager = null;
        systemNotificationManager = null;
        if (Build.VERSION.SDK_INT >= 26 && ApplicationLoader.applicationContext != null) {
            notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
            systemNotificationManager = (NotificationManager) ApplicationLoader.applicationContext.getSystemService("notification");
            checkOtherNotificationsChannel();
        }
        audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        Instance = new NotificationsController[16];
        lockObjects = new Object[16];
        for (int i = 0; i < 16; i++) {
            lockObjects[i] = new Object();
        }
        sharedPrefCachedKeys = new LongSparseArray();
    }

    public static NotificationsController getInstance(int i) {
        NotificationsController notificationsController;
        NotificationsController notificationsController2 = Instance[i];
        if (notificationsController2 != null) {
            return notificationsController2;
        }
        synchronized (lockObjects[i]) {
            try {
                notificationsController = Instance[i];
                if (notificationsController == null) {
                    NotificationsController[] notificationsControllerArr = Instance;
                    NotificationsController notificationsController3 = new NotificationsController(i);
                    notificationsControllerArr[i] = notificationsController3;
                    notificationsController = notificationsController3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return notificationsController;
    }

    public NotificationsController(int i) {
        super(i);
        this.pushMessages = new ArrayList<>();
        this.delayedPushMessages = new ArrayList<>();
        this.pushMessagesDict = new LongSparseArray();
        this.fcmRandomMessagesDict = new LongSparseArray();
        this.smartNotificationsDialogs = new LongSparseArray();
        this.pushDialogs = new LongSparseArray();
        this.wearNotificationsIds = new LongSparseArray();
        this.lastWearNotifiedMessageId = new LongSparseArray();
        this.pushDialogsOverrideMention = new LongSparseArray();
        this.popupMessages = new ArrayList<>();
        this.popupReplyMessages = new ArrayList<>();
        this.openedInBubbleDialogs = new HashSet<>();
        this.storyPushMessages = new ArrayList<>();
        this.storyPushMessagesDict = new LongSparseArray();
        this.openedDialogId = 0L;
        this.openedTopicId = 0L;
        this.lastButtonId = 5000;
        this.total_unread_count = 0;
        this.personalCount = 0;
        this.notifyCheck = false;
        this.lastOnlineFromOtherDevice = 0;
        this.lastBadgeCount = -1;
        this.mediaSpoilerEffect = new SpoilerEffect();
        this.spoilerChars = new char[]{10252, 10338, 10385, 10280, 10277, 10286, 10321};
        this.checkStoryPushesRunnable = new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.checkStoryPushes();
            }
        };
        this.notificationId = this.currentAccount + 1;
        StringBuilder sb = new StringBuilder();
        sb.append("messages");
        int i2 = this.currentAccount;
        sb.append(i2 == 0 ? "" : Integer.valueOf(i2));
        this.notificationGroup = sb.toString();
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        this.inChatSoundEnabled = notificationsSettings.getBoolean("EnableInChatSound", true);
        this.showBadgeNumber = notificationsSettings.getBoolean("badgeNumber", true);
        this.showBadgeMuted = notificationsSettings.getBoolean("badgeNumberMuted", false);
        this.showBadgeMessages = notificationsSettings.getBoolean("badgeNumberMessages", true);
        notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
        systemNotificationManager = (NotificationManager) ApplicationLoader.applicationContext.getSystemService("notification");
        try {
            audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        try {
            this.alarmManager = (AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm");
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
        try {
            PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(1, "telegram:notification_delay_lock");
            this.notificationDelayWakelock = wakeLockNewWakeLock;
            wakeLockNewWakeLock.setReferenceCounted(false);
        } catch (Exception e3) {
            FileLog.m1160e(e3);
        }
        this.notificationDelayRunnable = new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
        this.dialogsNotificationsFacade = new NotificationsSettingsFacade(this.currentAccount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d("delay reached");
        }
        if (!this.delayedPushMessages.isEmpty()) {
            showOrUpdateNotification(true);
            this.delayedPushMessages.clear();
        }
        try {
            if (this.notificationDelayWakelock.isHeld()) {
                this.notificationDelayWakelock.release();
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static void checkOtherNotificationsChannel() {
        SharedPreferences sharedPreferences;
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        if (OTHER_NOTIFICATIONS_CHANNEL == null) {
            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            OTHER_NOTIFICATIONS_CHANNEL = sharedPreferences.getString("OtherKey", "Other3");
        } else {
            sharedPreferences = null;
        }
        NotificationChannel notificationChannel = systemNotificationManager.getNotificationChannel(OTHER_NOTIFICATIONS_CHANNEL);
        if (notificationChannel != null && notificationChannel.getImportance() == 0) {
            try {
                systemNotificationManager.deleteNotificationChannel(OTHER_NOTIFICATIONS_CHANNEL);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
            OTHER_NOTIFICATIONS_CHANNEL = null;
            notificationChannel = null;
        }
        if (OTHER_NOTIFICATIONS_CHANNEL == null) {
            if (sharedPreferences == null) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            }
            OTHER_NOTIFICATIONS_CHANNEL = "Other" + Utilities.random.nextLong();
            sharedPreferences.edit().putString("OtherKey", OTHER_NOTIFICATIONS_CHANNEL).apply();
        }
        if (notificationChannel == null) {
            NotificationsController$$ExternalSyntheticApiModelOutline0.m1183m();
            NotificationChannel notificationChannelM296m = zzo$$ExternalSyntheticApiModelOutline0.m296m(OTHER_NOTIFICATIONS_CHANNEL, "Internal notifications", 3);
            notificationChannelM296m.enableLights(false);
            notificationChannelM296m.enableVibration(false);
            notificationChannelM296m.setSound(null, null);
            try {
                systemNotificationManager.createNotificationChannel(notificationChannelM296m);
            } catch (Exception e2) {
                FileLog.m1160e(e2);
            }
        }
    }

    public static String getSharedPrefKey(long j, long j2) {
        return getSharedPrefKey(j, j2, false);
    }

    public static String getSharedPrefKey(long j, long j2, boolean z) {
        String strValueOf;
        if (z) {
            if (j2 != 0) {
                return String.format(Locale.US, "%d_%d", Long.valueOf(j), Long.valueOf(j2));
            }
            return String.valueOf(j);
        }
        long j3 = (j2 << 12) + j;
        LongSparseArray longSparseArray = sharedPrefCachedKeys;
        int iIndexOfKey = longSparseArray.indexOfKey(j3);
        if (iIndexOfKey >= 0) {
            return (String) longSparseArray.valueAt(iIndexOfKey);
        }
        if (j2 != 0) {
            strValueOf = String.format(Locale.US, "%d_%d", Long.valueOf(j), Long.valueOf(j2));
        } else {
            strValueOf = String.valueOf(j);
        }
        longSparseArray.put(j3, strValueOf);
        return strValueOf;
    }

    public void muteUntil(long j, long j2, int i) {
        long j3;
        if (j != 0) {
            SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
            boolean z = j2 != 0;
            boolean zIsGlobalNotificationsEnabled = getInstance(this.currentAccount).isGlobalNotificationsEnabled(j, false, false);
            String sharedPrefKey = getSharedPrefKey(j, j2);
            if (i != Integer.MAX_VALUE) {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey, 3);
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + sharedPrefKey, getConnectionsManager().getCurrentTime() + i);
                j3 = (((long) i) << 32) | 1;
            } else if (!zIsGlobalNotificationsEnabled && !z) {
                editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey);
                j3 = 0;
            } else {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey, 2);
                j3 = 1L;
            }
            editorEdit.apply();
            if (j2 == 0) {
                getInstance(this.currentAccount).removeNotificationsForDialog(j);
                MessagesStorage.getInstance(this.currentAccount).setDialogFlags(j, j3);
                TLRPC.Dialog dialog = (TLRPC.Dialog) MessagesController.getInstance(this.currentAccount).dialogs_dict.get(j);
                if (dialog != null) {
                    TLRPC.TL_peerNotifySettings tL_peerNotifySettings = new TLRPC.TL_peerNotifySettings();
                    dialog.notify_settings = tL_peerNotifySettings;
                    if (i != Integer.MAX_VALUE || zIsGlobalNotificationsEnabled) {
                        tL_peerNotifySettings.mute_until = i;
                    }
                }
            }
            getInstance(this.currentAccount).updateServerNotificationsSettings(j, j2);
        }
    }

    public void cleanup() {
        this.popupMessages.clear();
        this.popupReplyMessages.clear();
        this.channelGroupsCreated = false;
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$cleanup$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanup$1() {
        this.openedDialogId = 0L;
        this.openedTopicId = 0L;
        this.total_unread_count = 0;
        this.personalCount = 0;
        this.pushMessages.clear();
        this.pushMessagesDict.clear();
        this.fcmRandomMessagesDict.clear();
        this.pushDialogs.clear();
        this.wearNotificationsIds.clear();
        this.lastWearNotifiedMessageId.clear();
        this.openedInBubbleDialogs.clear();
        this.delayedPushMessages.clear();
        this.notifyCheck = false;
        this.lastBadgeCount = 0;
        try {
            if (this.notificationDelayWakelock.isHeld()) {
                this.notificationDelayWakelock.release();
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        dismissNotification();
        setBadge(getTotalAllUnreadCount());
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        editorEdit.clear();
        editorEdit.apply();
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                systemNotificationManager.deleteNotificationChannelGroup("channels" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("groups" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("private" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("stories" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("other" + this.currentAccount);
                String str = this.currentAccount + "channel";
                List<NotificationChannel> notificationChannels = systemNotificationManager.getNotificationChannels();
                int size = notificationChannels.size();
                for (int i = 0; i < size; i++) {
                    String id = NotificationsController$$ExternalSyntheticApiModelOutline3.m1186m(notificationChannels.get(i)).getId();
                    if (id.startsWith(str)) {
                        try {
                            systemNotificationManager.deleteNotificationChannel(id);
                        } catch (Exception e2) {
                            FileLog.m1160e(e2);
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1157d("delete channel cleanup " + id);
                        }
                    }
                }
            } catch (Throwable th) {
                FileLog.m1160e(th);
            }
        }
    }

    public void setInChatSoundEnabled(boolean z) {
        this.inChatSoundEnabled = z;
    }

    public void setOpenedDialogId(final long j, final long j2) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setOpenedDialogId$2(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOpenedDialogId$2(long j, long j2) {
        this.openedDialogId = j;
        this.openedTopicId = j2;
    }

    public void setOpenedInBubble(final long j, final boolean z) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setOpenedInBubble$3(z, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOpenedInBubble$3(boolean z, long j) {
        if (z) {
            this.openedInBubbleDialogs.add(Long.valueOf(j));
        } else {
            this.openedInBubbleDialogs.remove(Long.valueOf(j));
        }
    }

    public void setLastOnlineFromOtherDevice(final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setLastOnlineFromOtherDevice$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLastOnlineFromOtherDevice$4(int i) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d("set last online from other device = " + i);
        }
        this.lastOnlineFromOtherDevice = i;
    }

    public void removeNotificationsForDialog(long j) {
        processReadMessages(null, j, 0, ConnectionsManager.DEFAULT_DATACENTER_ID, false);
        LongSparseIntArray longSparseIntArray = new LongSparseIntArray();
        longSparseIntArray.put(j, 0);
        processDialogsUpdateRead(longSparseIntArray);
    }

    public boolean hasMessagesToReply() {
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            if (!messageObject.isReactionPush) {
                TLRPC.Message message = messageObject.messageOwner;
                if ((!message.mentioned || !(message.action instanceof TLRPC.TL_messageActionPinMessage)) && !DialogObject.isEncryptedDialog(dialogId) && ((messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup()) && dialogId != UserObject.VERIFY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void forceShowPopupForReply() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda56
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$forceShowPopupForReply$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceShowPopupForReply$6() {
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            TLRPC.Message message = messageObject.messageOwner;
            if ((!message.mentioned || !(message.action instanceof TLRPC.TL_messageActionPinMessage)) && !DialogObject.isEncryptedDialog(dialogId) && (messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup())) {
                arrayList.add(0, messageObject);
            }
        }
        if (arrayList.isEmpty() || AndroidUtilities.needShowPasscode() || SharedConfig.isWaitingForPasscodeEnter) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$forceShowPopupForReply$5(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceShowPopupForReply$5(ArrayList arrayList) {
        this.popupReplyMessages = arrayList;
        Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) PopupNotificationActivity.class);
        intent.putExtra("force", true);
        intent.putExtra("currentAccount", this.currentAccount);
        intent.setFlags(268763140);
        ApplicationLoader.applicationContext.startActivity(intent);
        ApplicationLoader.applicationContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public void removeDeletedMessagesFromNotifications(final LongSparseArray longSparseArray, final boolean z) {
        if (!AyuConfig.saveDeletedMessages || z) {
            final ArrayList arrayList = new ArrayList(0);
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda52
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedMessagesFromNotifications$9(longSparseArray, z, arrayList);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$9(LongSparseArray longSparseArray, boolean z, final ArrayList arrayList) {
        Integer num;
        int i;
        Integer num2;
        int i2;
        Integer num3;
        LongSparseArray longSparseArray2 = longSparseArray;
        int i3 = this.total_unread_count;
        getAccountInstance().getNotificationsSettings();
        Integer num4 = 0;
        int i4 = 0;
        while (i4 < longSparseArray2.size()) {
            long jKeyAt = longSparseArray2.keyAt(i4);
            SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(jKeyAt);
            if (sparseArray == null) {
                num = num4;
                i = i4;
            } else {
                ArrayList arrayList2 = (ArrayList) longSparseArray2.get(jKeyAt);
                int size = arrayList2.size();
                int i5 = 0;
                while (i5 < size) {
                    int iIntValue = ((Integer) arrayList2.get(i5)).intValue();
                    MessageObject messageObject = (MessageObject) sparseArray.get(iIntValue);
                    if (messageObject == null) {
                        num2 = num4;
                        i2 = i4;
                    } else if (!messageObject.isStoryReactionPush && (!z || messageObject.isReactionPush)) {
                        num2 = num4;
                        long dialogId = messageObject.getDialogId();
                        Integer num5 = (Integer) this.pushDialogs.get(dialogId);
                        if (num5 == null) {
                            num5 = num2;
                        }
                        int iIntValue2 = num5.intValue() - 1;
                        Integer numValueOf = Integer.valueOf(iIntValue2);
                        if (iIntValue2 <= 0) {
                            this.smartNotificationsDialogs.remove(dialogId);
                            num3 = num2;
                        } else {
                            num3 = numValueOf;
                        }
                        if (num3.equals(num5)) {
                            i2 = i4;
                        } else {
                            i2 = i4;
                            if (getMessagesController().isForum(dialogId)) {
                                int i6 = this.total_unread_count - (num5.intValue() > 0 ? 1 : 0);
                                this.total_unread_count = i6;
                                this.total_unread_count = i6 + (num3.intValue() > 0 ? 1 : 0);
                            } else {
                                int iIntValue3 = this.total_unread_count - num5.intValue();
                                this.total_unread_count = iIntValue3;
                                this.total_unread_count = iIntValue3 + num3.intValue();
                            }
                            this.pushDialogs.put(dialogId, num3);
                        }
                        if (num3.intValue() == 0) {
                            this.pushDialogs.remove(dialogId);
                            this.pushDialogsOverrideMention.remove(dialogId);
                        }
                        sparseArray.remove(iIntValue);
                        this.delayedPushMessages.remove(messageObject);
                        this.pushMessages.remove(messageObject);
                        if (isPersonalMessage(messageObject)) {
                            this.personalCount--;
                        }
                        arrayList.add(messageObject);
                    } else {
                        num2 = num4;
                        i2 = i4;
                    }
                    i5++;
                    num4 = num2;
                    i4 = i2;
                }
                num = num4;
                i = i4;
                if (sparseArray.size() == 0) {
                    this.pushMessagesDict.remove(jKeyAt);
                }
            }
            i4 = i + 1;
            longSparseArray2 = longSparseArray;
            num4 = num;
        }
        if (!arrayList.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedMessagesFromNotifications$7(arrayList);
                }
            });
        }
        if (i3 != this.total_unread_count) {
            if (!this.notifyCheck) {
                this.delayedPushMessages.clear();
                showOrUpdateNotification(this.notifyCheck);
            } else {
                scheduleNotificationDelay(this.lastOnlineFromOtherDevice > getConnectionsManager().getCurrentTime());
            }
            final int size2 = this.pushDialogs.size();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedMessagesFromNotifications$8(size2);
                }
            });
        }
        this.notifyCheck = false;
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$7(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$8(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void removeDeletedHisoryFromNotifications(final LongSparseIntArray longSparseIntArray) {
        if (AyuConfig.saveDeletedMessages) {
            return;
        }
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeDeletedHisoryFromNotifications$12(longSparseIntArray, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$12(LongSparseIntArray longSparseIntArray, final ArrayList arrayList) {
        Integer num;
        int i = this.total_unread_count;
        getAccountInstance().getNotificationsSettings();
        Integer num2 = 0;
        int i2 = 0;
        while (i2 < longSparseIntArray.size()) {
            long jKeyAt = longSparseIntArray.keyAt(i2);
            long j = -jKeyAt;
            long j2 = longSparseIntArray.get(jKeyAt);
            Integer num3 = (Integer) this.pushDialogs.get(j);
            if (num3 == null) {
                num3 = num2;
            }
            Integer numValueOf = num3;
            int i3 = 0;
            while (i3 < this.pushMessages.size()) {
                MessageObject messageObject = this.pushMessages.get(i3);
                if (messageObject.getDialogId() == j) {
                    num = num2;
                    if (messageObject.getId() <= j2) {
                        SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(j);
                        if (sparseArray != null) {
                            sparseArray.remove(messageObject.getId());
                            if (sparseArray.size() == 0) {
                                this.pushMessagesDict.remove(j);
                            }
                        }
                        this.delayedPushMessages.remove(messageObject);
                        this.pushMessages.remove(messageObject);
                        i3--;
                        if (isPersonalMessage(messageObject)) {
                            this.personalCount--;
                        }
                        arrayList.add(messageObject);
                        numValueOf = Integer.valueOf(numValueOf.intValue() - 1);
                    }
                } else {
                    num = num2;
                }
                i3++;
                num2 = num;
            }
            Integer num4 = num2;
            if (numValueOf.intValue() <= 0) {
                this.smartNotificationsDialogs.remove(j);
                numValueOf = num4;
            }
            if (!numValueOf.equals(num3)) {
                if (getMessagesController().isForum(j)) {
                    int i4 = this.total_unread_count - (num3.intValue() > 0 ? 1 : 0);
                    this.total_unread_count = i4;
                    this.total_unread_count = i4 + (numValueOf.intValue() > 0 ? 1 : 0);
                } else {
                    int iIntValue = this.total_unread_count - num3.intValue();
                    this.total_unread_count = iIntValue;
                    this.total_unread_count = iIntValue + numValueOf.intValue();
                }
                this.pushDialogs.put(j, numValueOf);
            }
            if (numValueOf.intValue() == 0) {
                this.pushDialogs.remove(j);
                this.pushDialogsOverrideMention.remove(j);
            }
            i2++;
            num2 = num4;
        }
        if (arrayList.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedHisoryFromNotifications$10(arrayList);
                }
            });
        }
        if (i != this.total_unread_count) {
            if (!this.notifyCheck) {
                this.delayedPushMessages.clear();
                showOrUpdateNotification(this.notifyCheck);
            } else {
                scheduleNotificationDelay(this.lastOnlineFromOtherDevice > getConnectionsManager().getCurrentTime());
            }
            final int size = this.pushDialogs.size();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeDeletedHisoryFromNotifications$11(size);
                }
            });
        }
        this.notifyCheck = false;
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$10(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$11(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void processSeenStoryReactions(long j, final int i) {
        if (j != getUserConfig().getClientUserId()) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processSeenStoryReactions$13(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processSeenStoryReactions$13(int i) {
        int i2 = 0;
        boolean z = false;
        while (i2 < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i2);
            if (messageObject.isStoryReactionPush && Math.abs(messageObject.getId()) == i) {
                this.pushMessages.remove(i2);
                SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(Integer.valueOf(messageObject.getId()));
                getMessagesStorage().deletePushMessages(messageObject.getDialogId(), arrayList);
                i2--;
                z = true;
            }
            i2++;
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processDeleteStory(final long j, final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDeleteStory$14(j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDeleteStory$14(long j, int i) {
        boolean z;
        StoryNotification storyNotification = (StoryNotification) this.storyPushMessagesDict.get(j);
        if (storyNotification != null) {
            storyNotification.dateByIds.remove(Integer.valueOf(i));
            if (storyNotification.dateByIds.isEmpty()) {
                this.storyPushMessagesDict.remove(j);
                this.storyPushMessages.remove(storyNotification);
                getMessagesStorage().deleteStoryPushMessage(j);
                z = true;
            } else {
                getMessagesStorage().putStoryPushMessage(storyNotification);
                z = false;
            }
        } else {
            z = false;
        }
        int i2 = 0;
        while (i2 < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i2);
            if (messageObject != null && messageObject.isLiveStoryPush && messageObject.getId() == i) {
                this.pushMessages.remove(i2);
                i2--;
                SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                z = true;
            }
            i2++;
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processReadStories(final long j, final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processReadStories$15(j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processReadStories$15(long j, int i) {
        boolean z;
        StoryNotification storyNotification = (StoryNotification) this.storyPushMessagesDict.get(j);
        if (storyNotification != null) {
            this.storyPushMessagesDict.remove(j);
            this.storyPushMessages.remove(storyNotification);
            getMessagesStorage().deleteStoryPushMessage(j);
            z = true;
        } else {
            z = false;
        }
        int i2 = 0;
        while (i2 < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i2);
            if (messageObject != null && messageObject.isLiveStoryPush && messageObject.getId() <= i) {
                this.pushMessages.remove(i2);
                i2--;
                SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                z = true;
            }
            i2++;
        }
        if (z) {
            showOrUpdateNotification(false);
            updateStoryPushesRunnable();
        }
    }

    public void processIgnoreStories() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processIgnoreStories$16();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processIgnoreStories$16() {
        boolean zIsEmpty = this.storyPushMessages.isEmpty();
        this.storyPushMessages.clear();
        this.storyPushMessagesDict.clear();
        getMessagesStorage().deleteAllStoryPushMessages();
        if (zIsEmpty) {
            return;
        }
        showOrUpdateNotification(false);
    }

    public void processIgnoreStoryReactions() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processIgnoreStoryReactions$17();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processIgnoreStoryReactions$17() {
        int i = 0;
        boolean z = false;
        while (i < this.pushMessages.size()) {
            MessageObject messageObject = this.pushMessages.get(i);
            if (messageObject != null && messageObject.isStoryReactionPush) {
                this.pushMessages.remove(i);
                i--;
                SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(messageObject.getDialogId());
                if (sparseArray != null) {
                    sparseArray.remove(messageObject.getId());
                }
                if (sparseArray != null && sparseArray.size() <= 0) {
                    this.pushMessagesDict.remove(messageObject.getDialogId());
                }
                z = true;
            }
            i++;
        }
        getMessagesStorage().deleteAllStoryReactionPushMessages();
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processIgnoreStories(final long j) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processIgnoreStories$18(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processIgnoreStories$18(long j) {
        boolean zIsEmpty = this.storyPushMessages.isEmpty();
        this.storyPushMessages.clear();
        this.storyPushMessagesDict.clear();
        getMessagesStorage().deleteStoryPushMessage(j);
        if (zIsEmpty) {
            return;
        }
        showOrUpdateNotification(false);
    }

    public void processReadMessages(final LongSparseIntArray longSparseIntArray, final long j, final int i, final int i2, final boolean z) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda51
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processReadMessages$20(longSparseIntArray, arrayList, j, i2, i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processReadMessages$20(org.telegram.messenger.support.LongSparseIntArray r20, final java.util.ArrayList r21, long r22, int r24, int r25, boolean r26) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processReadMessages$20(org.telegram.messenger.support.LongSparseIntArray, java.util.ArrayList, long, int, int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processReadMessages$19(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int addToPopupMessages(java.util.ArrayList<org.telegram.messenger.MessageObject> r4, org.telegram.messenger.MessageObject r5, long r6, boolean r8, android.content.SharedPreferences r9) {
        /*
            r3 = this;
            boolean r0 = r5.isStoryReactionPush
            r1 = 0
            if (r0 == 0) goto L6
            return r1
        L6:
            boolean r0 = org.telegram.messenger.DialogObject.isEncryptedDialog(r6)
            if (r0 != 0) goto L61
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "custom_"
            r0.append(r2)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            boolean r0 = r9.getBoolean(r0, r1)
            if (r0 == 0) goto L3a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "popup_"
            r0.append(r2)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            int r0 = r9.getInt(r0, r1)
            goto L3b
        L3a:
            r0 = 0
        L3b:
            if (r0 != 0) goto L59
            if (r8 == 0) goto L47
            java.lang.String r6 = "popupChannel"
            int r0 = r9.getInt(r6, r1)
            goto L62
        L47:
            boolean r6 = org.telegram.messenger.DialogObject.isChatDialog(r6)
            if (r6 == 0) goto L51
            java.lang.String r6 = "popupGroup"
            goto L54
        L51:
            java.lang.String r6 = "popupAll"
        L54:
            int r0 = r9.getInt(r6, r1)
            goto L62
        L59:
            r6 = 1
            if (r0 != r6) goto L5e
            r0 = 3
            goto L62
        L5e:
            r6 = 2
            if (r0 != r6) goto L62
        L61:
            r0 = 0
        L62:
            if (r0 == 0) goto L77
            org.telegram.tgnet.TLRPC$Message r6 = r5.messageOwner
            org.telegram.tgnet.TLRPC$Peer r6 = r6.peer_id
            long r6 = r6.channel_id
            r8 = 0
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 == 0) goto L77
            boolean r6 = r5.isSupergroup()
            if (r6 != 0) goto L77
            r0 = 0
        L77:
            if (r0 == 0) goto L7c
            r4.add(r1, r5)
        L7c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.addToPopupMessages(java.util.ArrayList, org.telegram.messenger.MessageObject, long, boolean, android.content.SharedPreferences):int");
    }

    public void processEditedMessages(final LongSparseArray longSparseArray) {
        TLRPC.Message message;
        if (longSparseArray == null || longSparseArray.size() == 0) {
            return;
        }
        for (int i = 0; i < longSparseArray.size(); i++) {
            ArrayList arrayList = (ArrayList) longSparseArray.valueAt(i);
            if (arrayList != null) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    MessageObject messageObject = (MessageObject) arrayList.get(i2);
                    if (messageObject != null && (message = messageObject.messageOwner) != null) {
                        TLRPC.MessageAction messageAction = message.action;
                        if (messageAction instanceof TLRPC.TL_messageActionConferenceCall) {
                            TLRPC.TL_messageActionConferenceCall tL_messageActionConferenceCall = (TLRPC.TL_messageActionConferenceCall) messageAction;
                            if (tL_messageActionConferenceCall.active || tL_messageActionConferenceCall.missed) {
                                VoIPGroupNotification.hide(ApplicationLoader.applicationContext, this.currentAccount, messageObject.getId());
                            }
                        }
                    }
                }
            }
        }
        new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda57
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processEditedMessages$21(longSparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processEditedMessages$21(LongSparseArray longSparseArray) {
        long dialogId;
        int size = longSparseArray.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            longSparseArray.keyAt(i);
            ArrayList arrayList = (ArrayList) longSparseArray.valueAt(i);
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                if (messageObject.isStoryReactionPush) {
                    dialogId = messageObject.getDialogId();
                } else {
                    long j = messageObject.messageOwner.peer_id.channel_id;
                    dialogId = j != 0 ? -j : 0L;
                }
                SparseArray sparseArray = (SparseArray) this.pushMessagesDict.get(dialogId);
                if (sparseArray == null) {
                    break;
                }
                MessageObject messageObject2 = (MessageObject) sparseArray.get(messageObject.getId());
                if (messageObject2 != null && (messageObject2.isReactionPush || messageObject2.isStoryReactionPush)) {
                    messageObject2 = null;
                }
                if (messageObject2 != null) {
                    sparseArray.put(messageObject.getId(), messageObject);
                    int iIndexOf = this.pushMessages.indexOf(messageObject2);
                    if (iIndexOf >= 0) {
                        this.pushMessages.set(iIndexOf, messageObject);
                    }
                    int iIndexOf2 = this.delayedPushMessages.indexOf(messageObject2);
                    if (iIndexOf2 >= 0) {
                        this.delayedPushMessages.set(iIndexOf2, messageObject);
                    }
                    z = true;
                }
            }
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processNewMessages(final ArrayList<MessageObject> arrayList, boolean z, boolean z2, final CountDownLatch countDownLatch) {
        final boolean z3;
        final boolean z4;
        if (BuildVars.LOGS_ENABLED) {
            StringBuilder sb = new StringBuilder();
            sb.append("NotificationsController: processNewMessages msgs.size()=");
            sb.append(arrayList == null ? "null" : Integer.valueOf(arrayList.size()));
            sb.append(" isLast=");
            z3 = z;
            sb.append(z3);
            sb.append(" isFcm=");
            z4 = z2;
            sb.append(z4);
            sb.append(")");
            FileLog.m1157d(sb.toString());
        } else {
            z3 = z;
            z4 = z2;
        }
        if (arrayList != null) {
            int i = 0;
            while (i < arrayList.size()) {
                MessageObject messageObject = arrayList.get(i);
                if (messageObject != null && messageObject.messageOwner != null && !messageObject.isOutOwner()) {
                    TLRPC.MessageAction messageAction = messageObject.messageOwner.action;
                    if (messageAction instanceof TLRPC.TL_messageActionConferenceCall) {
                        TLRPC.TL_messageActionConferenceCall tL_messageActionConferenceCall = (TLRPC.TL_messageActionConferenceCall) messageAction;
                        if (!tL_messageActionConferenceCall.active && !tL_messageActionConferenceCall.missed && getConnectionsManager().getCurrentTime() - messageObject.messageOwner.date < getMessagesController().callRingTimeout / 1000) {
                            HashSet hashSet = new HashSet();
                            hashSet.add(Long.valueOf(messageObject.getDialogId()));
                            ArrayList arrayList2 = tL_messageActionConferenceCall.other_participants;
                            int size = arrayList2.size();
                            int i2 = 0;
                            while (i2 < size) {
                                Object obj = arrayList2.get(i2);
                                i2++;
                                hashSet.add(Long.valueOf(DialogObject.getPeerDialogId((TLRPC.Peer) obj)));
                            }
                            StringBuilder sb2 = new StringBuilder();
                            Iterator it = hashSet.iterator();
                            while (it.hasNext()) {
                                long jLongValue = ((Long) it.next()).longValue();
                                if (sb2.length() > 0) {
                                    sb2.append(", ");
                                }
                                sb2.append(DialogObject.getShortName(this.currentAccount, jLongValue));
                            }
                            VoIPGroupNotification.request(ApplicationLoader.applicationContext, this.currentAccount, messageObject.getDialogId(), sb2.toString(), tL_messageActionConferenceCall.call_id, messageObject.getId(), tL_messageActionConferenceCall.video);
                            arrayList.remove(i);
                            i--;
                        } else {
                            VoIPGroupNotification.hide(ApplicationLoader.applicationContext, this.currentAccount, messageObject.getId());
                        }
                    }
                }
                i++;
            }
        }
        if (!arrayList.isEmpty()) {
            final ArrayList arrayList3 = new ArrayList(0);
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$processNewMessages$25(arrayList, arrayList3, z4, z3, countDownLatch);
                }
            });
        } else if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:103:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processNewMessages$25(java.util.ArrayList r41, final java.util.ArrayList r42, boolean r43, boolean r44, java.util.concurrent.CountDownLatch r45) {
        /*
            Method dump skipped, instructions count: 1437
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processNewMessages$25(java.util.ArrayList, java.util.ArrayList, boolean, boolean, java.util.concurrent.CountDownLatch):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processNewMessages$23(ArrayList arrayList, int i) {
        this.popupMessages.addAll(0, arrayList);
        if (ApplicationLoader.mainInterfacePaused || !ApplicationLoader.isScreenOn) {
            if (i == 3 || ((i == 1 && ApplicationLoader.isScreenOn) || (i == 2 && !ApplicationLoader.isScreenOn))) {
                Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) PopupNotificationActivity.class);
                intent.setFlags(268763140);
                try {
                    ApplicationLoader.applicationContext.startActivity(intent);
                } catch (Throwable unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processNewMessages$24(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    private void appendMessage(MessageObject messageObject) {
        for (int i = 0; i < this.pushMessages.size(); i++) {
            if (this.pushMessages.get(i).getId() == messageObject.getId() && this.pushMessages.get(i).getDialogId() == messageObject.getDialogId() && this.pushMessages.get(i).isStoryPush == messageObject.isStoryPush) {
                return;
            }
        }
        this.pushMessages.add(0, messageObject);
    }

    public int getTotalUnreadCount() {
        return this.total_unread_count;
    }

    public void processDialogsUpdateRead(final LongSparseIntArray longSparseIntArray) {
        final ArrayList arrayList = new ArrayList();
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDialogsUpdateRead$28(longSparseIntArray, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0050 A[PHI: r4
      0x0050: PHI (r4v3 int) = (r4v2 int), (r4v27 int) binds: [B:6:0x002e, B:14:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processDialogsUpdateRead$28(org.telegram.messenger.support.LongSparseIntArray r18, final java.util.ArrayList r19) {
        /*
            Method dump skipped, instructions count: 424
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processDialogsUpdateRead$28(org.telegram.messenger.support.LongSparseIntArray, java.util.ArrayList):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDialogsUpdateRead$26(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDialogsUpdateRead$27(int i) {
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void processLoadedUnreadMessages(final LongSparseArray longSparseArray, final ArrayList<TLRPC.Message> arrayList, final ArrayList<MessageObject> arrayList2, ArrayList<TLRPC.User> arrayList3, ArrayList<TLRPC.Chat> arrayList4, ArrayList<TLRPC.EncryptedChat> arrayList5, final Collection<StoryNotification> collection) {
        getMessagesController().putUsers(arrayList3, true);
        getMessagesController().putChats(arrayList4, true);
        getMessagesController().putEncryptedChats(arrayList5, true);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processLoadedUnreadMessages$31(arrayList, longSparseArray, arrayList2, collection);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processLoadedUnreadMessages$31(java.util.ArrayList r27, androidx.collection.LongSparseArray r28, java.util.ArrayList r29, java.util.Collection r30) {
        /*
            Method dump skipped, instructions count: 900
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processLoadedUnreadMessages$31(java.util.ArrayList, androidx.collection.LongSparseArray, java.util.ArrayList, java.util.Collection):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processLoadedUnreadMessages$30(int i) {
        if (this.total_unread_count == 0) {
            this.popupMessages.clear();
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
        }
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    private int getTotalAllUnreadCount() {
        int size;
        int dialogUnreadCount = 0;
        for (int i = 0; i < 16; i++) {
            if (UserConfig.getInstance(i).isClientActivated() && (SharedConfig.showNotificationsForAllAccounts || UserConfig.selectedAccount == i)) {
                NotificationsController notificationsController = getInstance(i);
                if (notificationsController.showBadgeNumber) {
                    if (notificationsController.showBadgeMessages) {
                        if (notificationsController.showBadgeMuted) {
                            try {
                                ArrayList arrayList = new ArrayList(MessagesController.getInstance(i).allDialogs);
                                int size2 = arrayList.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    TLRPC.Dialog dialog = (TLRPC.Dialog) arrayList.get(i2);
                                    if ((dialog == null || !DialogObject.isChatDialog(dialog.f1577id) || !ChatObject.isNotInChat(getMessagesController().getChat(Long.valueOf(-dialog.f1577id)))) && dialog != null) {
                                        dialogUnreadCount += MessagesController.getInstance(i).getDialogUnreadCount(dialog);
                                    }
                                }
                            } catch (Exception e) {
                                FileLog.m1160e(e);
                            }
                        } else {
                            size = notificationsController.total_unread_count;
                            dialogUnreadCount += size;
                        }
                    } else if (notificationsController.showBadgeMuted) {
                        try {
                            int size3 = MessagesController.getInstance(i).allDialogs.size();
                            for (int i3 = 0; i3 < size3; i3++) {
                                TLRPC.Dialog dialog2 = MessagesController.getInstance(i).allDialogs.get(i3);
                                if ((!DialogObject.isChatDialog(dialog2.f1577id) || !ChatObject.isNotInChat(getMessagesController().getChat(Long.valueOf(-dialog2.f1577id)))) && MessagesController.getInstance(i).getDialogUnreadCount(dialog2) != 0) {
                                    dialogUnreadCount++;
                                }
                            }
                        } catch (Exception e2) {
                            FileLog.m1160e(e2);
                        }
                    } else {
                        size = notificationsController.pushDialogs.size();
                        dialogUnreadCount += size;
                    }
                }
            }
        }
        return dialogUnreadCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBadge$32() {
        setBadge(getTotalAllUnreadCount());
    }

    public void updateBadge() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateBadge$32();
            }
        });
    }

    private void setBadge(int i) {
        if (this.lastBadgeCount == i) {
            return;
        }
        FileLog.m1157d("setBadge " + i);
        this.lastBadgeCount = i;
        NotificationBadge.applyCount(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:154:0x0223, code lost:
    
        if (r12.getBoolean("EnablePreviewChannel", r6) != false) goto L155;
     */
    /* JADX WARN: Removed duplicated region for block: B:157:0x023a  */
    /* JADX WARN: Removed duplicated region for block: B:732:0x1040  */
    /* JADX WARN: Removed duplicated region for block: B:752:0x1096  */
    /* JADX WARN: Removed duplicated region for block: B:753:0x1098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String getShortStringForMessage(org.telegram.messenger.MessageObject r28, java.lang.String[] r29, boolean[] r30) {
        /*
            Method dump skipped, instructions count: 4823
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.getShortStringForMessage(org.telegram.messenger.MessageObject, java.lang.String[], boolean[]):java.lang.String");
    }

    private String replaceSpoilers(MessageObject messageObject) {
        TLRPC.Message message;
        String str;
        if (messageObject == null || (message = messageObject.messageOwner) == null || (str = message.message) == null || message.entities == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        if (messageObject.didSpoilLoginCode()) {
            return sb.toString();
        }
        for (int i = 0; i < messageObject.messageOwner.entities.size(); i++) {
            if (messageObject.messageOwner.entities.get(i) instanceof TLRPC.TL_messageEntitySpoiler) {
                TLRPC.TL_messageEntitySpoiler tL_messageEntitySpoiler = (TLRPC.TL_messageEntitySpoiler) messageObject.messageOwner.entities.get(i);
                for (int i2 = 0; i2 < tL_messageEntitySpoiler.length; i2++) {
                    int i3 = tL_messageEntitySpoiler.offset + i2;
                    char[] cArr = this.spoilerChars;
                    sb.setCharAt(i3, cArr[i2 % cArr.length]);
                }
            }
        }
        return sb.toString();
    }

    private String getStringForMessage(MessageObject messageObject, boolean z, boolean[] zArr, boolean[] zArr2) {
        String str;
        long j;
        String title;
        TLRPC.Chat chat;
        char c;
        char c2;
        char c3;
        boolean z2;
        String string;
        if (AndroidUtilities.needShowPasscode() || SharedConfig.isWaitingForPasscodeEnter) {
            return LocaleController.getString(C2369R.string.YouHaveNewMessage);
        }
        if (messageObject.isStoryPush || messageObject.isStoryMentionPush) {
            return "!" + messageObject.messageOwner.message;
        }
        TLRPC.Message message = messageObject.messageOwner;
        long j2 = message.dialog_id;
        TLRPC.Peer peer = message.peer_id;
        long j3 = peer.chat_id;
        if (j3 == 0) {
            j3 = peer.channel_id;
        }
        long fromChatId = peer.user_id;
        if (zArr2 != null) {
            zArr2[0] = true;
        }
        if (messageObject.getDialogId() == UserObject.VERIFY && messageObject.getForwardedFromId() != null) {
            fromChatId = messageObject.getForwardedFromId().longValue();
            j3 = fromChatId < 0 ? -fromChatId : 0L;
        }
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        boolean z3 = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_CONTENT_PREVIEW + j2, true);
        if (messageObject.isFcmMessage()) {
            if (j3 == 0 && fromChatId != 0) {
                if (!z3 || !notificationsSettings.getBoolean("EnablePreviewAll", true)) {
                    if (zArr2 != null) {
                        zArr2[0] = false;
                    }
                    return LocaleController.formatString(C2369R.string.NotificationMessageNoText, messageObject.localName);
                }
            } else if (j3 != 0 && (!z3 || ((!messageObject.localChannel && !notificationsSettings.getBoolean("EnablePreviewGroup", true)) || (messageObject.localChannel && !notificationsSettings.getBoolean("EnablePreviewChannel", true))))) {
                if (zArr2 != null) {
                    zArr2[0] = false;
                }
                return (messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup()) ? LocaleController.formatString(C2369R.string.NotificationMessageGroupNoText, messageObject.localUserName, messageObject.localName) : LocaleController.formatString(C2369R.string.ChannelMessageNoText, messageObject.localName);
            }
            zArr[0] = true;
            return (String) messageObject.messageText;
        }
        long clientUserId = getUserConfig().getClientUserId();
        if (fromChatId == 0) {
            fromChatId = messageObject.getFromChatId();
            if (fromChatId == 0) {
                fromChatId = -j3;
            }
        } else if (fromChatId == clientUserId) {
            fromChatId = messageObject.getFromChatId();
        }
        if (j2 == 0) {
            if (j3 != 0) {
                j2 = -j3;
            } else if (fromChatId != 0) {
                j2 = fromChatId;
            }
        }
        if (fromChatId > 0) {
            str = null;
            if (!messageObject.messageOwner.from_scheduled) {
                TLRPC.User user = getMessagesController().getUser(Long.valueOf(fromChatId));
                title = user != null ? UserObject.getUserName(user) : null;
            } else if (j2 == clientUserId) {
                title = LocaleController.getString(C2369R.string.MessageScheduledReminderNotification);
            } else {
                title = LocaleController.getString(C2369R.string.NotificationMessageScheduledName);
            }
            j = j2;
        } else {
            str = null;
            j = j2;
            TLRPC.Chat chat2 = getMessagesController().getChat(Long.valueOf(-fromChatId));
            title = chat2 != null ? getTitle(chat2) : null;
        }
        if (title == null) {
            return str;
        }
        if (j3 != 0) {
            chat = getMessagesController().getChat(Long.valueOf(j3));
            if (chat == null) {
                return str;
            }
        } else {
            chat = str;
        }
        if (DialogObject.isEncryptedDialog(j)) {
            return LocaleController.getString(C2369R.string.YouHaveNewMessage);
        }
        if (j3 == 0 && fromChatId != 0) {
            if (!z3 || !notificationsSettings.getBoolean("EnablePreviewAll", true)) {
                if (zArr2 != null) {
                    zArr2[0] = false;
                }
                return LocaleController.formatString(C2369R.string.NotificationMessageNoText, title);
            }
            TLRPC.Message message2 = messageObject.messageOwner;
            if (message2 instanceof TLRPC.TL_messageService) {
                TLRPC.MessageAction messageAction = message2.action;
                if (messageAction instanceof TLRPC.TL_messageActionSetSameChatWallPaper) {
                    return LocaleController.getString(C2369R.string.WallpaperSameNotification);
                }
                if (messageAction instanceof TLRPC.TL_messageActionSetChatWallPaper) {
                    return LocaleController.getString(C2369R.string.WallpaperNotification);
                }
                if (messageAction instanceof TLRPC.TL_messageActionGeoProximityReached) {
                    return messageObject.messageText.toString();
                }
                if (messageAction instanceof TLRPC.TL_messageActionTodoCompletions) {
                    return messageObject.messageText.toString();
                }
                if (messageAction instanceof TLRPC.TL_messageActionTodoAppendTasks) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction instanceof TLRPC.TL_messageActionUserJoined) || (messageAction instanceof TLRPC.TL_messageActionContactSignUp)) {
                    return LocaleController.formatString(C2369R.string.NotificationContactJoined, title);
                }
                if (messageAction instanceof TLRPC.TL_messageActionUserUpdatedPhoto) {
                    return LocaleController.formatString(C2369R.string.NotificationContactNewPhoto, title);
                }
                if (messageAction instanceof TLRPC.TL_messageActionLoginUnknownLocation) {
                    String string2 = LocaleController.formatString(C2369R.string.formatDateAtTime, LocaleController.getInstance().getFormatterYear().format(messageObject.messageOwner.date * 1000), LocaleController.getInstance().getFormatterDay().format(messageObject.messageOwner.date * 1000));
                    int i = C2369R.string.NotificationUnrecognizedDevice;
                    String str2 = getUserConfig().getCurrentUser().first_name;
                    TLRPC.MessageAction messageAction2 = messageObject.messageOwner.action;
                    return LocaleController.formatString(i, str2, string2, messageAction2.title, messageAction2.address);
                }
                if ((messageAction instanceof TLRPC.TL_messageActionGameScore) || (messageAction instanceof TLRPC.TL_messageActionPaymentSent) || (messageAction instanceof TLRPC.TL_messageActionPaymentSentMe)) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction instanceof TLRPC.TL_messageActionStarGift) || (messageAction instanceof TLRPC.TL_messageActionGiftPremium) || (messageAction instanceof TLRPC.TL_messageActionGiftTon)) {
                    return messageObject.messageText.toString();
                }
                if (messageAction instanceof TLRPC.TL_messageActionStarGiftUnique) {
                    return messageObject.messageText.toString();
                }
                if (messageAction instanceof TLRPC.TL_messageActionSuggestBirthday) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction instanceof TLRPC.TL_messageActionPaidMessagesRefunded) || (messageAction instanceof TLRPC.TL_messageActionPaidMessagesPrice)) {
                    return messageObject.messageText.toString();
                }
                if (messageAction instanceof TLRPC.TL_messageActionPhoneCall) {
                    if (messageAction.video) {
                        return LocaleController.getString(C2369R.string.CallMessageVideoIncomingMissed);
                    }
                    return LocaleController.getString(C2369R.string.CallMessageIncomingMissed);
                }
                if (messageAction instanceof TLRPC.TL_messageActionConferenceCall) {
                    if (messageAction.video) {
                        return LocaleController.getString(C2369R.string.CallMessageVideoIncomingConferenceMissed);
                    }
                    return LocaleController.getString(C2369R.string.CallMessageIncomingConferenceMissed);
                }
                if (messageAction instanceof TLRPC.TL_messageActionSetChatTheme) {
                    String themeEmoticonOrGiftTitle = TlUtils.getThemeEmoticonOrGiftTitle(((TLRPC.TL_messageActionSetChatTheme) messageAction).theme);
                    if (!TextUtils.isEmpty(themeEmoticonOrGiftTitle)) {
                        c3 = 0;
                        z2 = true;
                        if (j == clientUserId) {
                            string = LocaleController.formatString(C2369R.string.ChatThemeChangedYou, themeEmoticonOrGiftTitle);
                        } else {
                            string = LocaleController.formatString(C2369R.string.ChatThemeChangedTo, title, themeEmoticonOrGiftTitle);
                        }
                    } else if (j == clientUserId) {
                        c3 = 0;
                        string = LocaleController.formatString(C2369R.string.ChatThemeDisabledYou, new Object[0]);
                        z2 = true;
                    } else {
                        c3 = 0;
                        z2 = true;
                        string = LocaleController.formatString(C2369R.string.ChatThemeDisabled, title, themeEmoticonOrGiftTitle);
                    }
                    zArr[c3] = z2;
                    return string;
                }
            } else {
                if (messageObject.isMediaEmpty()) {
                    if (!z && !TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        String string3 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string3;
                    }
                    return LocaleController.formatString(C2369R.string.NotificationMessageNoText, title);
                }
                TLRPC.Message message3 = messageObject.messageOwner;
                if (message3.media instanceof TLRPC.TL_messageMediaPhoto) {
                    if (z || TextUtils.isEmpty(message3.message)) {
                        return messageObject.messageOwner.media.ttl_seconds != 0 ? LocaleController.formatString(C2369R.string.NotificationMessageSDPhoto, title) : LocaleController.formatString(C2369R.string.NotificationMessagePhoto, title);
                    }
                    String string4 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "🖼 " + messageObject.messageOwner.message);
                    zArr[0] = true;
                    return string4;
                }
                if (messageObject.isVideo()) {
                    if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        return messageObject.messageOwner.media.ttl_seconds != 0 ? LocaleController.formatString(C2369R.string.NotificationMessageSDVideo, title) : LocaleController.formatString(C2369R.string.NotificationMessageVideo, title);
                    }
                    String string5 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "📹 " + messageObject.messageOwner.message);
                    zArr[0] = true;
                    return string5;
                }
                if (messageObject.isGame()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGame, title, messageObject.messageOwner.media.game.title);
                }
                if (messageObject.isVoice()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageAudio, title);
                }
                if (messageObject.isRoundVideo()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageRound, title);
                }
                if (messageObject.isMusic()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageMusic, title);
                }
                TLRPC.MessageMedia messageMedia = messageObject.messageOwner.media;
                if (messageMedia instanceof TLRPC.TL_messageMediaContact) {
                    TLRPC.TL_messageMediaContact tL_messageMediaContact = (TLRPC.TL_messageMediaContact) messageMedia;
                    return LocaleController.formatString(C2369R.string.NotificationMessageContact2, title, ContactsController.formatName(tL_messageMediaContact.first_name, tL_messageMediaContact.last_name));
                }
                if (messageMedia instanceof TLRPC.TL_messageMediaGiveaway) {
                    TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway = (TLRPC.TL_messageMediaGiveaway) messageMedia;
                    return LocaleController.formatString(C2369R.string.NotificationMessageChannelGiveaway, title, Integer.valueOf(tL_messageMediaGiveaway.quantity), Integer.valueOf(tL_messageMediaGiveaway.months));
                }
                if (messageMedia instanceof TLRPC.TL_messageMediaGiveawayResults) {
                    return LocaleController.formatString(C2369R.string.BoostingGiveawayResults, new Object[0]);
                }
                if (messageMedia instanceof TLRPC.TL_messageMediaPoll) {
                    TLRPC.Poll poll = ((TLRPC.TL_messageMediaPoll) messageMedia).poll;
                    return poll.quiz ? LocaleController.formatString(C2369R.string.NotificationMessageQuiz2, title, poll.question.text) : LocaleController.formatString(C2369R.string.NotificationMessagePoll2, title, poll.question.text);
                }
                if (messageMedia instanceof TLRPC.TL_messageMediaToDo) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageTodo2, title, ((TLRPC.TL_messageMediaToDo) messageMedia).todo.title.text);
                }
                if ((messageMedia instanceof TLRPC.TL_messageMediaGeo) || (messageMedia instanceof TLRPC.TL_messageMediaVenue)) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageMap, title);
                }
                if (messageMedia instanceof TLRPC.TL_messageMediaGeoLive) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageLiveLocation, title);
                }
                if (messageMedia instanceof TLRPC.TL_messageMediaDocument) {
                    if (messageObject.isSticker() || messageObject.isAnimatedSticker()) {
                        String stickerEmoji = messageObject.getStickerEmoji();
                        return stickerEmoji != null ? LocaleController.formatString(C2369R.string.NotificationMessageStickerEmoji, title, stickerEmoji) : LocaleController.formatString(C2369R.string.NotificationMessageSticker, title);
                    }
                    if (messageObject.isGif()) {
                        if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                            return LocaleController.formatString(C2369R.string.NotificationMessageGif, title);
                        }
                        String string6 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "🎬 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string6;
                    }
                    if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        return LocaleController.formatString(C2369R.string.NotificationMessageDocument, title);
                    }
                    String string7 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "📎 " + messageObject.messageOwner.message);
                    zArr[0] = true;
                    return string7;
                }
                if (z || TextUtils.isEmpty(messageObject.messageText)) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageNoText, title);
                }
                String string8 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, messageObject.messageText);
                zArr[0] = true;
                return string8;
            }
        } else if (j3 != 0) {
            boolean z4 = ChatObject.isChannel(chat) && !chat.megagroup;
            if (!z3 || ((z4 || !notificationsSettings.getBoolean("EnablePreviewGroup", true)) && !(z4 && notificationsSettings.getBoolean("EnablePreviewChannel", true)))) {
                if (zArr2 != null) {
                    zArr2[0] = false;
                }
                return (!ChatObject.isChannel(chat) || chat.megagroup) ? (messageObject.type == 29 && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPaidMedia)) ? LocaleController.formatPluralString("NotificationMessagePaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject)).stars_amount, title) : LocaleController.formatString(C2369R.string.NotificationMessageGroupNoText, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.ChannelMessageNoText, title);
            }
            TLRPC.Message message4 = messageObject.messageOwner;
            if (message4 instanceof TLRPC.TL_messageService) {
                TLRPC.MessageAction messageAction3 = message4.action;
                if (messageAction3 instanceof TLRPC.TL_messageActionChatAddUser) {
                    long jLongValue = messageAction3.user_id;
                    if (jLongValue == 0 && messageAction3.users.size() == 1) {
                        jLongValue = ((Long) messageObject.messageOwner.action.users.get(0)).longValue();
                    }
                    if (jLongValue != 0) {
                        if (messageObject.messageOwner.peer_id.channel_id != 0 && !chat.megagroup) {
                            return LocaleController.formatString(C2369R.string.ChannelAddedByNotification, title, getTitle(chat));
                        }
                        if (jLongValue == clientUserId) {
                            return LocaleController.formatString(C2369R.string.NotificationInvitedToGroup, title, getTitle(chat));
                        }
                        TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(jLongValue));
                        return user2 == null ? str : fromChatId == user2.f1734id ? chat.megagroup ? LocaleController.formatString(C2369R.string.NotificationGroupAddSelfMega, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.NotificationGroupAddSelf, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.NotificationGroupAddMember, title, getTitle(chat), UserObject.getUserName(user2));
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < messageObject.messageOwner.action.users.size(); i2++) {
                        TLRPC.User user3 = getMessagesController().getUser((Long) messageObject.messageOwner.action.users.get(i2));
                        if (user3 != null) {
                            String userName = UserObject.getUserName(user3);
                            if (sb.length() != 0) {
                                sb.append(", ");
                            }
                            sb.append(userName);
                        }
                    }
                    return LocaleController.formatString(C2369R.string.NotificationGroupAddMember, title, getTitle(chat), sb.toString());
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionGroupCall) {
                    return messageAction3.duration != 0 ? LocaleController.formatString(C2369R.string.NotificationGroupEndedCall, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.NotificationGroupCreatedCall, title, getTitle(chat));
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionGroupCallScheduled) {
                    return messageObject.messageText.toString();
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionInviteToGroupCall) {
                    long jLongValue2 = messageAction3.user_id;
                    if (jLongValue2 == 0 && messageAction3.users.size() == 1) {
                        jLongValue2 = ((Long) messageObject.messageOwner.action.users.get(0)).longValue();
                    }
                    if (jLongValue2 != 0) {
                        if (jLongValue2 == clientUserId) {
                            return LocaleController.formatString(C2369R.string.NotificationGroupInvitedYouToCall, title, getTitle(chat));
                        }
                        TLRPC.User user4 = getMessagesController().getUser(Long.valueOf(jLongValue2));
                        return user4 == null ? str : LocaleController.formatString(C2369R.string.NotificationGroupInvitedToCall, title, getTitle(chat), UserObject.getUserName(user4));
                    }
                    StringBuilder sb2 = new StringBuilder();
                    for (int i3 = 0; i3 < messageObject.messageOwner.action.users.size(); i3++) {
                        TLRPC.User user5 = getMessagesController().getUser((Long) messageObject.messageOwner.action.users.get(i3));
                        if (user5 != null) {
                            String userName2 = UserObject.getUserName(user5);
                            if (sb2.length() != 0) {
                                sb2.append(", ");
                            }
                            sb2.append(userName2);
                        }
                    }
                    return LocaleController.formatString(C2369R.string.NotificationGroupInvitedToCall, title, getTitle(chat), sb2.toString());
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionGiftCode) {
                    TLRPC.TL_messageActionGiftCode tL_messageActionGiftCode = (TLRPC.TL_messageActionGiftCode) messageAction3;
                    TLRPC.Chat chat3 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-DialogObject.getPeerDialogId(tL_messageActionGiftCode.boost_peer)));
                    String title2 = chat3 == null ? str : getTitle(chat3);
                    return title2 == null ? LocaleController.getString(C2369R.string.BoostingReceivedGiftNoName) : LocaleController.formatString(C2369R.string.NotificationMessageGiftCode, title2, LocaleController.formatPluralString("Months", tL_messageActionGiftCode.months, new Object[0]));
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChatJoinedByLink) {
                    return LocaleController.formatString(C2369R.string.NotificationInvitedToGroupByLink, title, getTitle(chat));
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChatEditTitle) {
                    return LocaleController.formatString(C2369R.string.NotificationEditedGroupName, title, messageAction3.title);
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionTodoCompletions) {
                    return messageObject.messageText.toString();
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionTodoAppendTasks) {
                    return messageObject.messageText.toString();
                }
                if ((messageAction3 instanceof TLRPC.TL_messageActionChatEditPhoto) || (messageAction3 instanceof TLRPC.TL_messageActionChatDeletePhoto)) {
                    return (message4.peer_id.channel_id == 0 || chat.megagroup) ? messageObject.isVideoAvatar() ? LocaleController.formatString(C2369R.string.NotificationEditedGroupVideo, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.NotificationEditedGroupPhoto, title, getTitle(chat)) : messageObject.isVideoAvatar() ? LocaleController.formatString(C2369R.string.ChannelVideoEditNotification, getTitle(chat)) : LocaleController.formatString(C2369R.string.ChannelPhotoEditNotification, getTitle(chat));
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChatDeleteUser) {
                    long j4 = messageAction3.user_id;
                    if (j4 == clientUserId) {
                        return LocaleController.formatString(C2369R.string.NotificationGroupKickYou, title, getTitle(chat));
                    }
                    if (j4 == fromChatId) {
                        return LocaleController.formatString(C2369R.string.NotificationGroupLeftMember, title, getTitle(chat));
                    }
                    TLRPC.User user6 = getMessagesController().getUser(Long.valueOf(messageObject.messageOwner.action.user_id));
                    return user6 == null ? str : LocaleController.formatString(C2369R.string.NotificationGroupKickMember, title, getTitle(chat), UserObject.getUserName(user6));
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChatCreate) {
                    return messageObject.messageText.toString();
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChannelCreate) {
                    return messageObject.messageText.toString();
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChatMigrateTo) {
                    return LocaleController.formatString(C2369R.string.ActionMigrateFromGroupNotify, getTitle(chat));
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChannelMigrateFrom) {
                    return LocaleController.formatString(C2369R.string.ActionMigrateFromGroupNotify, messageAction3.title);
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionScreenshotTaken) {
                    return messageObject.messageText.toString();
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionPinMessage) {
                    if (!ChatObject.isChannel(chat) || chat.megagroup) {
                        MessageObject messageObject2 = messageObject.replyMessageObject;
                        if (messageObject2 == null) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedNoText, title, getTitle(chat));
                        }
                        if (messageObject2.isMusic()) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedMusic, title, getTitle(chat));
                        }
                        if (messageObject2.isVideo()) {
                            if (TextUtils.isEmpty(messageObject2.messageOwner.message)) {
                                return LocaleController.formatString(C2369R.string.NotificationActionPinnedVideo, title, getTitle(chat));
                            }
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedText, title, "📹 " + messageObject2.messageOwner.message, getTitle(chat));
                        }
                        if (messageObject2.isGif()) {
                            if (TextUtils.isEmpty(messageObject2.messageOwner.message)) {
                                return LocaleController.formatString(C2369R.string.NotificationActionPinnedGif, title, getTitle(chat));
                            }
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedText, title, "🎬 " + messageObject2.messageOwner.message, getTitle(chat));
                        }
                        if (messageObject2.isVoice()) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedVoice, title, getTitle(chat));
                        }
                        if (messageObject2.isRoundVideo()) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedRound, title, getTitle(chat));
                        }
                        if (messageObject2.isSticker() || messageObject2.isAnimatedSticker()) {
                            String stickerEmoji2 = messageObject2.getStickerEmoji();
                            return stickerEmoji2 != null ? LocaleController.formatString(C2369R.string.NotificationActionPinnedStickerEmoji, title, getTitle(chat), stickerEmoji2) : LocaleController.formatString(C2369R.string.NotificationActionPinnedSticker, title, getTitle(chat));
                        }
                        TLRPC.Message message5 = messageObject2.messageOwner;
                        TLRPC.MessageMedia messageMedia2 = message5.media;
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaDocument) {
                            if (TextUtils.isEmpty(message5.message)) {
                                return LocaleController.formatString(C2369R.string.NotificationActionPinnedFile, title, getTitle(chat));
                            }
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedText, title, "📎 " + messageObject2.messageOwner.message, getTitle(chat));
                        }
                        if ((messageMedia2 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia2 instanceof TLRPC.TL_messageMediaVenue)) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedGeo, title, getTitle(chat));
                        }
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaGeoLive) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedGeoLive, title, getTitle(chat));
                        }
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaContact) {
                            TLRPC.TL_messageMediaContact tL_messageMediaContact2 = (TLRPC.TL_messageMediaContact) messageObject.messageOwner.media;
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedContact2, title, getTitle(chat), ContactsController.formatName(tL_messageMediaContact2.first_name, tL_messageMediaContact2.last_name));
                        }
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaPoll) {
                            TLRPC.TL_messageMediaPoll tL_messageMediaPoll = (TLRPC.TL_messageMediaPoll) messageMedia2;
                            return tL_messageMediaPoll.poll.quiz ? LocaleController.formatString(C2369R.string.NotificationActionPinnedQuiz2, title, getTitle(chat), tL_messageMediaPoll.poll.question.text) : LocaleController.formatString(C2369R.string.NotificationActionPinnedPoll2, title, getTitle(chat), tL_messageMediaPoll.poll.question.text);
                        }
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaToDo) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedTodo2, title, getTitle(chat), ((TLRPC.TL_messageMediaToDo) messageMedia2).todo.title.text);
                        }
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaPhoto) {
                            if (TextUtils.isEmpty(message5.message)) {
                                return LocaleController.formatString(C2369R.string.NotificationActionPinnedPhoto, title, getTitle(chat));
                            }
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedText, title, "🖼 " + messageObject2.messageOwner.message, getTitle(chat));
                        }
                        if (messageMedia2 instanceof TLRPC.TL_messageMediaGame) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedGame, title, getTitle(chat));
                        }
                        CharSequence charSequence = messageObject2.messageText;
                        if (charSequence == null || charSequence.length() <= 0) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedNoText, title, getTitle(chat));
                        }
                        CharSequence string9 = messageObject2.messageText;
                        if (string9.length() > 20) {
                            StringBuilder sb3 = new StringBuilder();
                            c = 0;
                            sb3.append((Object) string9.subSequence(0, 20));
                            sb3.append("...");
                            string9 = sb3.toString();
                        } else {
                            c = 0;
                        }
                        int i4 = C2369R.string.NotificationActionPinnedText;
                        String title3 = getTitle(chat);
                        Object[] objArr = new Object[3];
                        objArr[c] = title;
                        objArr[1] = string9;
                        objArr[2] = title3;
                        return LocaleController.formatString(i4, objArr);
                    }
                    MessageObject messageObject3 = messageObject.replyMessageObject;
                    if (messageObject3 == null) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedNoTextChannel, getTitle(chat));
                    }
                    if (messageObject3.isMusic()) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedMusicChannel, getTitle(chat));
                    }
                    if (messageObject3.isVideo()) {
                        if (TextUtils.isEmpty(messageObject3.messageOwner.message)) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedVideoChannel, getTitle(chat));
                        }
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedTextChannel, getTitle(chat), "📹 " + messageObject3.messageOwner.message);
                    }
                    if (messageObject3.isGif()) {
                        if (TextUtils.isEmpty(messageObject3.messageOwner.message)) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedGifChannel, getTitle(chat));
                        }
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedTextChannel, getTitle(chat), "🎬 " + messageObject3.messageOwner.message);
                    }
                    if (messageObject3.isVoice()) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedVoiceChannel, getTitle(chat));
                    }
                    if (messageObject3.isRoundVideo()) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedRoundChannel, getTitle(chat));
                    }
                    if (messageObject3.isSticker() || messageObject3.isAnimatedSticker()) {
                        String stickerEmoji3 = messageObject3.getStickerEmoji();
                        return stickerEmoji3 != null ? LocaleController.formatString(C2369R.string.NotificationActionPinnedStickerEmojiChannel, getTitle(chat), stickerEmoji3) : LocaleController.formatString(C2369R.string.NotificationActionPinnedStickerChannel, getTitle(chat));
                    }
                    TLRPC.Message message6 = messageObject3.messageOwner;
                    TLRPC.MessageMedia messageMedia3 = message6.media;
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaDocument) {
                        if (TextUtils.isEmpty(message6.message)) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedFileChannel, getTitle(chat));
                        }
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedTextChannel, getTitle(chat), "📎 " + messageObject3.messageOwner.message);
                    }
                    if ((messageMedia3 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia3 instanceof TLRPC.TL_messageMediaVenue)) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedGeoChannel, getTitle(chat));
                    }
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaGeoLive) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedGeoLiveChannel, getTitle(chat));
                    }
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaContact) {
                        TLRPC.TL_messageMediaContact tL_messageMediaContact3 = (TLRPC.TL_messageMediaContact) messageObject.messageOwner.media;
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedContactChannel2, getTitle(chat), ContactsController.formatName(tL_messageMediaContact3.first_name, tL_messageMediaContact3.last_name));
                    }
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaPoll) {
                        TLRPC.TL_messageMediaPoll tL_messageMediaPoll2 = (TLRPC.TL_messageMediaPoll) messageMedia3;
                        return tL_messageMediaPoll2.poll.quiz ? LocaleController.formatString(C2369R.string.NotificationActionPinnedQuizChannel2, getTitle(chat), tL_messageMediaPoll2.poll.question.text) : LocaleController.formatString(C2369R.string.NotificationActionPinnedPollChannel2, getTitle(chat), tL_messageMediaPoll2.poll.question.text);
                    }
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaToDo) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedTodoChannel2, getTitle(chat), ((TLRPC.TL_messageMediaToDo) messageMedia3).todo.title.text);
                    }
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaPhoto) {
                        if (TextUtils.isEmpty(message6.message)) {
                            return LocaleController.formatString(C2369R.string.NotificationActionPinnedPhotoChannel, getTitle(chat));
                        }
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedTextChannel, getTitle(chat), "🖼 " + messageObject3.messageOwner.message);
                    }
                    if (messageMedia3 instanceof TLRPC.TL_messageMediaGame) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedGameChannel, getTitle(chat));
                    }
                    CharSequence charSequence2 = messageObject3.messageText;
                    if (charSequence2 == null || charSequence2.length() <= 0) {
                        return LocaleController.formatString(C2369R.string.NotificationActionPinnedNoTextChannel, getTitle(chat));
                    }
                    CharSequence string10 = messageObject3.messageText;
                    if (string10.length() > 20) {
                        StringBuilder sb4 = new StringBuilder();
                        c2 = 0;
                        sb4.append((Object) string10.subSequence(0, 20));
                        sb4.append("...");
                        string10 = sb4.toString();
                    } else {
                        c2 = 0;
                    }
                    int i5 = C2369R.string.NotificationActionPinnedTextChannel;
                    Object[] objArr2 = new Object[2];
                    objArr2[c2] = getTitle(chat);
                    objArr2[1] = string10;
                    return LocaleController.formatString(i5, objArr2);
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionGameScore) {
                    return messageObject.messageText.toString();
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionSetChatTheme) {
                    String themeEmoticonOrGiftTitle2 = TlUtils.getThemeEmoticonOrGiftTitle(((TLRPC.TL_messageActionSetChatTheme) messageAction3).theme);
                    return TextUtils.isEmpty(themeEmoticonOrGiftTitle2) ? j == clientUserId ? LocaleController.formatString(C2369R.string.ChatThemeDisabledYou, new Object[0]) : LocaleController.formatString("ChatThemeDisabled", C2369R.string.ChatThemeDisabled, title, themeEmoticonOrGiftTitle2) : j == clientUserId ? LocaleController.formatString(C2369R.string.ChatThemeChangedYou, themeEmoticonOrGiftTitle2) : LocaleController.formatString(C2369R.string.ChatThemeChangedTo, title, themeEmoticonOrGiftTitle2);
                }
                if (messageAction3 instanceof TLRPC.TL_messageActionChatJoinedByRequest) {
                    return messageObject.messageText.toString();
                }
            } else {
                if (ChatObject.isChannel(chat) && !chat.megagroup) {
                    if (messageObject.isMediaEmpty()) {
                        if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                            return LocaleController.formatString(C2369R.string.ChannelMessageNoText, title);
                        }
                        String string11 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string11;
                    }
                    if (messageObject.type == 29 && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPaidMedia)) {
                        return LocaleController.formatPluralString("NotificationChannelMessagePaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject)).stars_amount, getTitle(chat));
                    }
                    TLRPC.Message message7 = messageObject.messageOwner;
                    if (message7.media instanceof TLRPC.TL_messageMediaPhoto) {
                        if (z || TextUtils.isEmpty(message7.message)) {
                            return LocaleController.formatString(C2369R.string.ChannelMessagePhoto, title);
                        }
                        String string12 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "🖼 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string12;
                    }
                    if (messageObject.isVideo()) {
                        if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                            return LocaleController.formatString(C2369R.string.ChannelMessageVideo, title);
                        }
                        String string13 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "📹 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string13;
                    }
                    if (messageObject.isVoice()) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageAudio, title);
                    }
                    if (messageObject.isRoundVideo()) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageRound, title);
                    }
                    if (messageObject.isMusic()) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageMusic, title);
                    }
                    TLRPC.MessageMedia messageMedia4 = messageObject.messageOwner.media;
                    if (messageMedia4 instanceof TLRPC.TL_messageMediaContact) {
                        TLRPC.TL_messageMediaContact tL_messageMediaContact4 = (TLRPC.TL_messageMediaContact) messageMedia4;
                        return LocaleController.formatString(C2369R.string.ChannelMessageContact2, title, ContactsController.formatName(tL_messageMediaContact4.first_name, tL_messageMediaContact4.last_name));
                    }
                    if (messageMedia4 instanceof TLRPC.TL_messageMediaPoll) {
                        TLRPC.Poll poll2 = ((TLRPC.TL_messageMediaPoll) messageMedia4).poll;
                        return poll2.quiz ? LocaleController.formatString(C2369R.string.ChannelMessageQuiz2, title, poll2.question.text) : LocaleController.formatString(C2369R.string.ChannelMessagePoll2, title, poll2.question.text);
                    }
                    if (messageMedia4 instanceof TLRPC.TL_messageMediaToDo) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageTodo2, title, ((TLRPC.TL_messageMediaToDo) messageMedia4).todo.title.text);
                    }
                    if (messageMedia4 instanceof TLRPC.TL_messageMediaGiveaway) {
                        TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway2 = (TLRPC.TL_messageMediaGiveaway) messageMedia4;
                        return LocaleController.formatString(C2369R.string.NotificationMessageChannelGiveaway, getTitle(chat), Integer.valueOf(tL_messageMediaGiveaway2.quantity), Integer.valueOf(tL_messageMediaGiveaway2.months));
                    }
                    if ((messageMedia4 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia4 instanceof TLRPC.TL_messageMediaVenue)) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageMap, title);
                    }
                    if (messageMedia4 instanceof TLRPC.TL_messageMediaGeoLive) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageLiveLocation, title);
                    }
                    if (messageMedia4 instanceof TLRPC.TL_messageMediaDocument) {
                        if (messageObject.isSticker() || messageObject.isAnimatedSticker()) {
                            String stickerEmoji4 = messageObject.getStickerEmoji();
                            return stickerEmoji4 != null ? LocaleController.formatString(C2369R.string.ChannelMessageStickerEmoji, title, stickerEmoji4) : LocaleController.formatString(C2369R.string.ChannelMessageSticker, title);
                        }
                        if (messageObject.isGif()) {
                            if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                                return LocaleController.formatString(C2369R.string.ChannelMessageGIF, title);
                            }
                            String string14 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "🎬 " + messageObject.messageOwner.message);
                            zArr[0] = true;
                            return string14;
                        }
                        if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                            return LocaleController.formatString(C2369R.string.ChannelMessageDocument, title);
                        }
                        String string15 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, "📎 " + messageObject.messageOwner.message);
                        zArr[0] = true;
                        return string15;
                    }
                    if (z || TextUtils.isEmpty(messageObject.messageText)) {
                        return LocaleController.formatString(C2369R.string.ChannelMessageNoText, title);
                    }
                    String string16 = LocaleController.formatString(C2369R.string.NotificationMessageText, title, messageObject.messageText);
                    zArr[0] = true;
                    return string16;
                }
                if (messageObject.isMediaEmpty()) {
                    return (z || TextUtils.isEmpty(messageObject.messageOwner.message)) ? LocaleController.formatString(C2369R.string.NotificationMessageGroupNoText, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.NotificationMessageGroupText, title, getTitle(chat), messageObject.messageOwner.message);
                }
                if (messageObject.type == 29 && (MessageObject.getMedia(messageObject) instanceof TLRPC.TL_messageMediaPaidMedia)) {
                    return LocaleController.formatPluralString("NotificationChatMessagePaidMedia", (int) ((TLRPC.TL_messageMediaPaidMedia) MessageObject.getMedia(messageObject)).stars_amount, title, getTitle(chat));
                }
                TLRPC.Message message8 = messageObject.messageOwner;
                if (message8.media instanceof TLRPC.TL_messageMediaPhoto) {
                    if (z || TextUtils.isEmpty(message8.message)) {
                        return LocaleController.formatString(C2369R.string.NotificationMessageGroupPhoto, title, getTitle(chat));
                    }
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupText, title, getTitle(chat), "🖼 " + messageObject.messageOwner.message);
                }
                if (messageObject.isVideo()) {
                    if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        return LocaleController.formatString(C2369R.string.NotificationMessageGroupVideo, title, getTitle(chat));
                    }
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupText, title, getTitle(chat), "📹 " + messageObject.messageOwner.message);
                }
                if (messageObject.isVoice()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupAudio, title, getTitle(chat));
                }
                if (messageObject.isRoundVideo()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupRound, title, getTitle(chat));
                }
                if (messageObject.isMusic()) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupMusic, title, getTitle(chat));
                }
                TLRPC.MessageMedia messageMedia5 = messageObject.messageOwner.media;
                if (messageMedia5 instanceof TLRPC.TL_messageMediaContact) {
                    TLRPC.TL_messageMediaContact tL_messageMediaContact5 = (TLRPC.TL_messageMediaContact) messageMedia5;
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupContact2, title, getTitle(chat), ContactsController.formatName(tL_messageMediaContact5.first_name, tL_messageMediaContact5.last_name));
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaPoll) {
                    TLRPC.TL_messageMediaPoll tL_messageMediaPoll3 = (TLRPC.TL_messageMediaPoll) messageMedia5;
                    return tL_messageMediaPoll3.poll.quiz ? LocaleController.formatString(C2369R.string.NotificationMessageGroupQuiz2, title, getTitle(chat), tL_messageMediaPoll3.poll.question.text) : LocaleController.formatString(C2369R.string.NotificationMessageGroupPoll2, title, getTitle(chat), tL_messageMediaPoll3.poll.question.text);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaToDo) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupTodo2, title, getTitle(chat), ((TLRPC.TL_messageMediaToDo) messageMedia5).todo.title.text);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGame) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupGame, title, getTitle(chat), messageObject.messageOwner.media.game.title);
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGiveaway) {
                    TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway3 = (TLRPC.TL_messageMediaGiveaway) messageMedia5;
                    return LocaleController.formatString(C2369R.string.NotificationMessageChannelGiveaway, getTitle(chat), Integer.valueOf(tL_messageMediaGiveaway3.quantity), Integer.valueOf(tL_messageMediaGiveaway3.months));
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGiveawayResults) {
                    return LocaleController.formatString(C2369R.string.BoostingGiveawayResults, new Object[0]);
                }
                if ((messageMedia5 instanceof TLRPC.TL_messageMediaGeo) || (messageMedia5 instanceof TLRPC.TL_messageMediaVenue)) {
                    return LocaleController.formatString("NotificationMessageGroupMap", C2369R.string.NotificationMessageGroupMap, title, getTitle(chat));
                }
                if (messageMedia5 instanceof TLRPC.TL_messageMediaGeoLive) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupLiveLocation, title, getTitle(chat));
                }
                if (!(messageMedia5 instanceof TLRPC.TL_messageMediaDocument)) {
                    return (z || TextUtils.isEmpty(messageObject.messageText)) ? LocaleController.formatString(C2369R.string.NotificationMessageGroupNoText, title, getTitle(chat)) : LocaleController.formatString(C2369R.string.NotificationMessageGroupText, title, getTitle(chat), messageObject.messageText);
                }
                if (messageObject.isSticker() || messageObject.isAnimatedSticker()) {
                    String stickerEmoji5 = messageObject.getStickerEmoji();
                    return stickerEmoji5 != null ? LocaleController.formatString(C2369R.string.NotificationMessageGroupStickerEmoji, title, getTitle(chat), stickerEmoji5) : LocaleController.formatString(C2369R.string.NotificationMessageGroupSticker, title, getTitle(chat));
                }
                if (messageObject.isGif()) {
                    if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                        return LocaleController.formatString(C2369R.string.NotificationMessageGroupGif, title, getTitle(chat));
                    }
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupText, title, getTitle(chat), "🎬 " + messageObject.messageOwner.message);
                }
                if (z || TextUtils.isEmpty(messageObject.messageOwner.message)) {
                    return LocaleController.formatString(C2369R.string.NotificationMessageGroupDocument, title, getTitle(chat));
                }
                return LocaleController.formatString(C2369R.string.NotificationMessageGroupText, title, getTitle(chat), "📎 " + messageObject.messageOwner.message);
            }
        }
        return str;
    }

    private void scheduleNotificationRepeat() {
        try {
            Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) NotificationRepeat.class);
            intent.putExtra("currentAccount", this.currentAccount);
            PendingIntent service = PendingIntent.getService(ApplicationLoader.applicationContext, 0, intent, 33554432);
            if (getAccountInstance().getNotificationsSettings().getInt("repeat_messages", 60) > 0 && this.personalCount > 0) {
                this.alarmManager.set(2, SystemClock.elapsedRealtime() + (r1 * 60000), service);
            } else {
                this.alarmManager.cancel(service);
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private boolean isPersonalMessage(MessageObject messageObject) {
        TLRPC.MessageAction messageAction;
        TLRPC.Message message = messageObject.messageOwner;
        TLRPC.Peer peer = message.peer_id;
        return (peer != null && peer.chat_id == 0 && peer.channel_id == 0 && ((messageAction = message.action) == null || (messageAction instanceof TLRPC.TL_messageActionEmpty))) || messageObject.isStoryReactionPush;
    }

    private int getNotifyOverride(SharedPreferences sharedPreferences, long j, long j2) {
        int property = this.dialogsNotificationsFacade.getProperty(NotificationsSettingsFacade.PROPERTY_NOTIFY, j, j2, -1);
        if (property != 3 || this.dialogsNotificationsFacade.getProperty(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL, j, j2, 0) < getConnectionsManager().getCurrentTime()) {
            return property;
        }
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNotifications$33() {
        showOrUpdateNotification(false);
    }

    public void showNotifications() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showNotifications$33();
            }
        });
    }

    public void hideNotifications() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hideNotifications$34();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideNotifications$34() {
        notificationManager.cancel(this.notificationId);
        this.lastWearNotifiedMessageId.clear();
        for (int i = 0; i < this.wearNotificationsIds.size(); i++) {
            notificationManager.cancel(((Integer) this.wearNotificationsIds.valueAt(i)).intValue());
        }
        this.wearNotificationsIds.clear();
    }

    private void dismissNotification() {
        FileLog.m1157d("NotificationsController dismissNotification");
        try {
            notificationManager.cancel(this.notificationId);
            this.pushMessages.clear();
            this.pushMessagesDict.clear();
            this.lastWearNotifiedMessageId.clear();
            for (int i = 0; i < this.wearNotificationsIds.size(); i++) {
                if (!this.openedInBubbleDialogs.contains(Long.valueOf(this.wearNotificationsIds.keyAt(i)))) {
                    notificationManager.cancel(((Integer) this.wearNotificationsIds.valueAt(i)).intValue());
                }
            }
            this.wearNotificationsIds.clear();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            });
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0016, code lost:
    
        if (org.telegram.messenger.NotificationsController.audioManager.getRingerMode() == 0) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void playInChatSound() {
        /*
            r7 = this;
            boolean r0 = r7.inChatSoundEnabled
            if (r0 == 0) goto Le
            org.telegram.messenger.MediaController r0 = org.telegram.messenger.MediaController.getInstance()
            boolean r0 = r0.isRecordingAudio()
            if (r0 == 0) goto L10
        Le:
            r1 = r7
            goto L44
        L10:
            android.media.AudioManager r0 = org.telegram.messenger.NotificationsController.audioManager     // Catch: java.lang.Exception -> L19
            int r0 = r0.getRingerMode()     // Catch: java.lang.Exception -> L19
            if (r0 != 0) goto L1d
            goto Le
        L19:
            r0 = move-exception
            org.telegram.messenger.FileLog.m1160e(r0)
        L1d:
            org.telegram.messenger.AccountInstance r0 = r7.getAccountInstance()     // Catch: java.lang.Exception -> L3f
            android.content.SharedPreferences r2 = r0.getNotificationsSettings()     // Catch: java.lang.Exception -> L3f
            long r3 = r7.openedDialogId     // Catch: java.lang.Exception -> L3f
            long r5 = r7.openedTopicId     // Catch: java.lang.Exception -> L3f
            r1 = r7
            int r0 = r1.getNotifyOverride(r2, r3, r5)     // Catch: java.lang.Exception -> L3d
            r2 = 2
            if (r0 != r2) goto L32
            goto L44
        L32:
            org.telegram.messenger.DispatchQueue r0 = org.telegram.messenger.NotificationsController.notificationsQueue     // Catch: java.lang.Exception -> L3d
            org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda17 r2 = new org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda17     // Catch: java.lang.Exception -> L3d
            r2.<init>()     // Catch: java.lang.Exception -> L3d
            r0.postRunnable(r2)     // Catch: java.lang.Exception -> L3d
            goto L44
        L3d:
            r0 = move-exception
            goto L41
        L3f:
            r0 = move-exception
            r1 = r7
        L41:
            org.telegram.messenger.FileLog.m1160e(r0)
        L44:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.playInChatSound():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playInChatSound$37() {
        if (Math.abs(SystemClock.elapsedRealtime() - this.lastSoundPlay) <= 500) {
            return;
        }
        try {
            if (this.soundPool == null) {
                SoundPool soundPool = new SoundPool(3, 1, 0);
                this.soundPool = soundPool;
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda24
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                        NotificationsController.m3907$r8$lambda$5g32taFTWDLau1NcnX0pdVwKU4(soundPool2, i, i2);
                    }
                });
            }
            if (this.soundIn == 0 && !this.soundInLoaded) {
                this.soundInLoaded = true;
                this.soundIn = this.soundPool.load(ApplicationLoader.applicationContext, C2369R.raw.sound_in, 1);
            }
            int i = this.soundIn;
            if (i != 0) {
                try {
                    this.soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
    }

    /* renamed from: $r8$lambda$5g32taFTWDLau1NcnX0pdVwK-U4, reason: not valid java name */
    public static /* synthetic */ void m3907$r8$lambda$5g32taFTWDLau1NcnX0pdVwKU4(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            try {
                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    private void scheduleNotificationDelay(boolean z) {
        try {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1157d("delay notification start, onlineReason = " + z);
            }
            this.notificationDelayWakelock.acquire(10000L);
            DispatchQueue dispatchQueue = notificationsQueue;
            dispatchQueue.cancelRunnable(this.notificationDelayRunnable);
            dispatchQueue.postRunnable(this.notificationDelayRunnable, z ? 3000 : MediaDataController.MAX_STYLE_RUNS_COUNT);
        } catch (Exception e) {
            FileLog.m1160e(e);
            showOrUpdateNotification(this.notifyCheck);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void repeatNotificationMaybe() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$repeatNotificationMaybe$38();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$repeatNotificationMaybe$38() {
        int i = Calendar.getInstance().get(11);
        if (i >= 11 && i <= 22) {
            notificationManager.cancel(this.notificationId);
            showOrUpdateNotification(true);
        } else {
            scheduleNotificationRepeat();
        }
    }

    private boolean isEmptyVibration(long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            return false;
        }
        for (long j : jArr) {
            if (j != 0) {
                return false;
            }
        }
        return true;
    }

    public void deleteNotificationChannel(long j, long j2) {
        deleteNotificationChannel(j, j2, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: deleteNotificationChannelInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$deleteNotificationChannel$39(long j, long j2, int i) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            SharedPreferences.Editor editorEdit = notificationsSettings.edit();
            if (i == 0 || i == -1) {
                String str = "org.telegram.key" + j;
                if (j2 != 0) {
                    str = str + ".topic" + j2;
                }
                String string = notificationsSettings.getString(str, null);
                if (string != null) {
                    editorEdit.remove(str).remove(str + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string);
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1157d("delete channel internal " + string);
                    }
                }
            }
            if (i == 1 || i == -1) {
                String str2 = "org.telegram.keyia" + j;
                String string2 = notificationsSettings.getString(str2, null);
                if (string2 != null) {
                    editorEdit.remove(str2).remove(str2 + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string2);
                    } catch (Exception e2) {
                        FileLog.m1160e(e2);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1157d("delete channel internal " + string2);
                    }
                }
            }
            editorEdit.apply();
        } catch (Exception e3) {
            FileLog.m1160e(e3);
        }
    }

    public void deleteNotificationChannel(final long j, final long j2, final int i) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteNotificationChannel$39(j, j2, i);
            }
        });
    }

    public void deleteNotificationChannelGlobal(int i) {
        deleteNotificationChannelGlobal(i, -1);
    }

    /* renamed from: deleteNotificationChannelGlobalInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$deleteNotificationChannelGlobal$40(int i, int i2) {
        String str;
        String str2;
        String str3;
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            SharedPreferences.Editor editorEdit = notificationsSettings.edit();
            if (i2 == 0 || i2 == -1) {
                if (i == 2) {
                    str = "channels";
                } else if (i == 0) {
                    str = "groups";
                } else if (i == 3) {
                    str = "stories";
                } else if (i == 4 || i == 5) {
                    str = "reactions";
                } else {
                    str = "private";
                }
                String string = notificationsSettings.getString(str, null);
                if (string != null) {
                    editorEdit.remove(str).remove(str + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string);
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1157d("delete channel global internal " + string);
                    }
                }
            }
            if (i2 == 1 || i2 == -1) {
                if (i == 2) {
                    str2 = "channels_ia";
                } else if (i == 0) {
                    str2 = "groups_ia";
                } else if (i == 3) {
                    str2 = "stories_ia";
                } else if (i == 4 || i == 5) {
                    str2 = "reactions_ia";
                } else {
                    str2 = "private_ia";
                }
                String string2 = notificationsSettings.getString(str2, null);
                if (string2 != null) {
                    editorEdit.remove(str2).remove(str2 + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string2);
                    } catch (Exception e2) {
                        FileLog.m1160e(e2);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.m1157d("delete channel global internal " + string2);
                    }
                }
            }
            if (i == 2) {
                str3 = "overwrite_channel";
            } else if (i == 0) {
                str3 = "overwrite_group";
            } else if (i == 3) {
                str3 = "overwrite_stories";
            } else if (i == 4 || i == 5) {
                str3 = "overwrite_reactions";
            } else {
                str3 = "overwrite_private";
            }
            editorEdit.remove(str3);
            editorEdit.apply();
        } catch (Exception e3) {
            FileLog.m1160e(e3);
        }
    }

    public void deleteNotificationChannelGlobal(final int i, final int i2) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteNotificationChannelGlobal$40(i, i2);
            }
        });
    }

    public void deleteAllNotificationChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda42
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteAllNotificationChannels$41();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteAllNotificationChannels$41() {
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            Map<String, ?> all = notificationsSettings.getAll();
            SharedPreferences.Editor editorEdit = notificationsSettings.edit();
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("org.telegram.key")) {
                    if (!key.endsWith("_s")) {
                        String str = (String) entry.getValue();
                        systemNotificationManager.deleteNotificationChannel(str);
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.m1157d("delete all channel " + str);
                        }
                    }
                    editorEdit.remove(key);
                }
            }
            editorEdit.apply();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private boolean unsupportedNotificationShortcut() {
        return Build.VERSION.SDK_INT < 29 || !SharedConfig.chatBubbles;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00c0  */
    @android.annotation.SuppressLint({"RestrictedApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String createNotificationShortcut(androidx.core.app.NotificationCompat.Builder r18, long r19, java.lang.String r21, org.telegram.tgnet.TLRPC.User r22, org.telegram.tgnet.TLRPC.Chat r23, androidx.core.app.Person r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.createNotificationShortcut(androidx.core.app.NotificationCompat$Builder, long, java.lang.String, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, androidx.core.app.Person, boolean):java.lang.String");
    }

    @TargetApi(26)
    protected void ensureGroupsCreated() {
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        if (this.groupsCreated == null) {
            this.groupsCreated = Boolean.valueOf(notificationsSettings.getBoolean("groupsCreated5", false));
        }
        if (!this.groupsCreated.booleanValue()) {
            try {
                String str = this.currentAccount + "channel";
                List<NotificationChannel> notificationChannels = systemNotificationManager.getNotificationChannels();
                int size = notificationChannels.size();
                SharedPreferences.Editor editorEdit = null;
                for (int i = 0; i < size; i++) {
                    NotificationChannel notificationChannelM1186m = NotificationsController$$ExternalSyntheticApiModelOutline3.m1186m(notificationChannels.get(i));
                    String id = notificationChannelM1186m.getId();
                    if (id.startsWith(str)) {
                        int importance = notificationChannelM1186m.getImportance();
                        if (importance != 4 && importance != 5 && !id.contains("_ia_")) {
                            if (id.contains("_channels_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_channel").remove("vibrate_channel").remove("ChannelSoundPath").remove("ChannelSound");
                            } else if (id.contains("_reactions_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_react").remove("vibrate_react").remove("ReactionSoundPath").remove("ReactionSound");
                            } else if (id.contains("_groups_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_group").remove("vibrate_group").remove("GroupSoundPath").remove("GroupSound");
                            } else if (id.contains("_private_")) {
                                if (editorEdit == null) {
                                    editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editorEdit.remove("priority_messages");
                                editorEdit.remove("priority_group").remove("vibrate_messages").remove("GlobalSoundPath").remove("GlobalSound");
                            } else {
                                long jLongValue = Utilities.parseLong(id.substring(9, id.indexOf(95, 9))).longValue();
                                if (jLongValue != 0) {
                                    if (editorEdit == null) {
                                        editorEdit = getAccountInstance().getNotificationsSettings().edit();
                                    }
                                    editorEdit.remove("priority_" + jLongValue).remove("vibrate_" + jLongValue).remove("sound_path_" + jLongValue).remove("sound_" + jLongValue);
                                }
                            }
                        }
                        systemNotificationManager.deleteNotificationChannel(id);
                    }
                }
                if (editorEdit != null) {
                    editorEdit.apply();
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
            notificationsSettings.edit().putBoolean("groupsCreated5", true).apply();
            this.groupsCreated = Boolean.TRUE;
        }
        if (this.channelGroupsCreated) {
            return;
        }
        List<NotificationChannelGroup> notificationChannelGroups = systemNotificationManager.getNotificationChannelGroups();
        String str2 = "channels" + this.currentAccount;
        String str3 = "groups" + this.currentAccount;
        String str4 = "private" + this.currentAccount;
        String str5 = "stories" + this.currentAccount;
        String str6 = "reactions" + this.currentAccount;
        String str7 = "other" + this.currentAccount;
        int size2 = notificationChannelGroups.size();
        String str8 = str7;
        String str9 = str6;
        String str10 = str5;
        String str11 = str4;
        for (int i2 = 0; i2 < size2; i2++) {
            String id2 = NotificationsController$$ExternalSyntheticApiModelOutline4.m1187m(notificationChannelGroups.get(i2)).getId();
            if (str2 != null && str2.equals(id2)) {
                str2 = null;
            } else if (str3 != null && str3.equals(id2)) {
                str3 = null;
            } else if (str10 != null && str10.equals(id2)) {
                str10 = null;
            } else if (str9 != null && str9.equals(id2)) {
                str9 = null;
            } else if (str11 != null && str11.equals(id2)) {
                str11 = null;
            } else if (str8 != null && str8.equals(id2)) {
                str8 = null;
            }
            if (str2 == null && str10 == null && str9 == null && str3 == null && str11 == null && str8 == null) {
                break;
            }
        }
        if (str2 != null || str3 != null || str9 != null || str10 != null || str11 != null || str8 != null) {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId()));
            if (user == null) {
                getUserConfig().getCurrentUser();
            }
            String str12 = user != null ? " (" + ContactsController.formatName(user.first_name, user.last_name) + ")" : "";
            ArrayList arrayList = new ArrayList();
            if (str2 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1185m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1184m(str2, LocaleController.getString(C2369R.string.NotificationsChannels) + str12));
            }
            if (str3 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1185m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1184m(str3, LocaleController.getString(C2369R.string.NotificationsGroups) + str12));
            }
            if (str10 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1185m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1184m(str10, LocaleController.getString(C2369R.string.NotificationsStories) + str12));
            }
            if (str9 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1185m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1184m(str9, LocaleController.getString(C2369R.string.NotificationsReactions) + str12));
            }
            if (str11 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1185m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1184m(str11, LocaleController.getString(C2369R.string.NotificationsPrivateChats) + str12));
            }
            if (str8 != null) {
                NotificationsController$$ExternalSyntheticApiModelOutline2.m1185m();
                arrayList.add(NotificationsController$$ExternalSyntheticApiModelOutline1.m1184m(str8, LocaleController.getString(C2369R.string.NotificationsOther) + str12));
            }
            systemNotificationManager.createNotificationChannelGroups(arrayList);
        }
        this.channelGroupsCreated = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0564 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:268:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x05ad  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x05b1 A[LOOP:1: B:276:0x05ae->B:278:0x05b1, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:281:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x060b  */
    @android.annotation.TargetApi(26)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String validateChannelId(long r29, long r31, java.lang.String r33, long[] r34, int r35, android.net.Uri r36, int r37, boolean r38, boolean r39, boolean r40, int r41) {
        /*
            Method dump skipped, instructions count: 1777
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.validateChannelId(long, long, java.lang.String, long[], int, android.net.Uri, int, boolean, boolean, boolean, int):java.lang.String");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(78:80|(1:82)(1:83)|84|(1:87)|86|88|(1:94)|95|(2:99|(1:101)(1:102))|103|(3:105|(2:111|(1:116)(1:115))(1:109)|110)(1:117)|118|119|(4:644|121|122|123)(2:126|(1:128)(1:129))|(1:(1:137)(1:138))(1:135)|139|(1:146)(1:144)|145|147|(2:(2:150|(1:152))(1:153)|(1:155)(58:156|164|(2:170|(1:172))(1:169)|(4:174|(2:176|(1:178)(1:179))(1:180)|181|(3:187|188|(1:190)(1:191))(1:186))(1:192)|193|194|(3:198|225|(2:227|662)(4:(1:(2:232|(1:234)(1:235))(1:231))(1:236)|237|(1:239)|240))(1:(4:197|198|225|(0)(0))(4:199|(3:201|(2:207|655)(6:208|(1:210)|211|(1:220)(1:(1:215)(2:216|(1:218)(1:219)))|221|654)|222)|653|223))|224|(1:249)(1:248)|(1:271)(50:254|(1:256)(1:257)|(2:262|263)(2:259|(3:261|262|263)(2:264|(2:266|263)(2:267|(1:269)(1:270))))|(2:274|(1:276))(1:277)|278|(49:280|(1:282)(1:283)|284|(1:286)|287|290|(7:294|330|(1:332)(1:334)|333|335|(1:337)(1:338)|339)(1:(1:(4:297|(1:299)(1:301)|300|302)(4:305|(1:307)(1:309)|308|310))(2:311|(48:(1:314)(1:315)|316|(1:318)(2:(1:321)(1:322)|323)|319|324|(1:326)(1:327)|328|304|(1:341)(1:342)|343|(1:349)(1:347)|348|(1:353)(1:352)|(1:357)|(1:363)(1:362)|(6:365|(1:367)|368|(1:370)|371|(1:373)(1:374))(1:375)|(3:637|379|(1:383))|386|(1:388)(1:390)|389|391|392|(1:394)(3:397|398|(2:(2:401|402)(2:403|(1:405))|406)(2:407|(28:409|(2:412|410)|660|413|396|427|454|460|(1:462)|463|(1:470)|639|471|(1:473)|476|(3:478|479|480)(1:483)|484|(1:488)(10:(2:491|(1:493)(3:635|495|(4:497|(1:499)(1:500)|501|(1:503))))|506|(4:510|534|(2:536|530)|537)(1:(3:512|513|(1:515)(1:537))(2:516|(2:531|(1:533)(0))(2:518|(3:520|531|(0)(0))(3:521|(2:523|(1:525))(2:526|(2:528|(1:530)))|537))))|538|(1:597)(2:(3:545|(1:547)(1:548)|549)|(4:577|(1:579)(1:581)|580|(1:583)(3:584|(1:586)(1:(2:591|596)(2:592|(1:594)(1:595)))|587))(3:553|554|(5:556|(1:564)(1:(1:562)(1:563))|(0)(0)|580|(0)(0))(6:565|(1:567)(1:(2:569|(1:576)(2:642|573))(0))|577|(0)(0)|580|(0)(0))))|598|(1:620)(4:606|(4:608|(3:610|(4:612|(1:614)|615|659)(2:616|658)|617)|657|618)|656|619)|(1:628)|629|663)|489|506|(5:508|510|534|(0)|537)(0)|538|(1:597)(0)|598|(2:600|620)(0)|(4:622|624|626|628)|629|663)(24:414|(4:416|(1:(1:419)(2:420|(1:422)))|423|(1:427)(1:(3:433|(22:441|460|(0)|463|(3:466|468|470)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)|442)(2:443|(23:445|(0)(1:453)|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)(1:442))))(2:455|(1:459))|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)))|395|396|427|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)(41:329|(0)(0)|343|(2:345|349)(0)|348|(1:353)(0)|(2:355|357)|(2:359|363)(0)|(0)(0)|(4:377|637|379|(2:381|383))|386|(0)(0)|389|391|392|(0)(0)|395|396|427|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)))|303|304|(0)(0)|343|(0)(0)|348|(0)(0)|(0)|(0)(0)|(0)(0)|(0)|386|(0)(0)|389|391|392|(0)(0)|395|396|427|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)(1:288)|289|287|290|(10:292|294|330|(0)(0)|333|335|(0)(0)|339|303|304)(0)|(0)(0)|343|(0)(0)|348|(0)(0)|(0)|(0)(0)|(0)(0)|(0)|386|(0)(0)|389|391|392|(0)(0)|395|396|427|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663)|272|(0)(0)|278|(0)(0)|289|287|290|(0)(0)|(0)(0)|343|(0)(0)|348|(0)(0)|(0)|(0)(0)|(0)(0)|(0)|386|(0)(0)|389|391|392|(0)(0)|395|396|427|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663))(1:157)|(1:(1:160)(1:161))(1:162)|163|164|(4:166|168|170|(0))(0)|(0)(0)|193|194|(0)(0)|224|(1:249)(0)|(3:251|271|272)(0)|(0)(0)|278|(0)(0)|289|287|290|(0)(0)|(0)(0)|343|(0)(0)|348|(0)(0)|(0)|(0)(0)|(0)(0)|(0)|386|(0)(0)|389|391|392|(0)(0)|395|396|427|454|460|(0)|463|(0)|639|471|(0)|476|(0)(0)|484|(0)(0)|489|506|(0)(0)|538|(0)(0)|598|(0)(0)|(0)|629|663) */
    /* JADX WARN: Code restructure failed: missing block: B:474:0x0b49, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:486:0x0b67, code lost:
    
        org.telegram.messenger.FileLog.m1160e(r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:170:0x03d8 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03e3 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03eb A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x04c0 A[PHI: r14
      0x04c0: PHI (r14v7 int A[IMMUTABLE_TYPE]) = (r14v6 int), (r14v25 int) binds: [B:195:0x04bb, B:197:0x04bf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x059a  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x059c  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x060c  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x06bc  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x06c8 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:277:0x06e9  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x070a A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0768  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0776 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0898 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:334:0x08a5 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:337:0x08cc  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x08ce  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x08d7  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x08da  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x08e2 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:349:0x08ed  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x08f4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x08f8  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x08fb A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0908  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x090f  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0913 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0936  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x093a  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0952  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0958  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x098e A[Catch: Exception -> 0x0056, TRY_ENTER, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x09a5 A[Catch: Exception -> 0x0056, TRY_LEAVE, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:462:0x0af3 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0b02 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:473:0x0b41 A[Catch: all -> 0x0b49, TryCatch #2 {all -> 0x0b49, blocks: (B:471:0x0b25, B:473:0x0b41, B:476:0x0b4b, B:480:0x0b53, B:484:0x0b5b), top: B:639:0x0b25 }] */
    /* JADX WARN: Removed duplicated region for block: B:478:0x0b4f  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x0b5a  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0b6c A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:490:0x0b73  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x0bc6  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x0bc9  */
    /* JADX WARN: Removed duplicated region for block: B:533:0x0c00  */
    /* JADX WARN: Removed duplicated region for block: B:536:0x0c09  */
    /* JADX WARN: Removed duplicated region for block: B:537:0x0c0a  */
    /* JADX WARN: Removed duplicated region for block: B:540:0x0c10 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0cfe A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:579:0x0d09 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:581:0x0d14  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0d19 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:584:0x0d24  */
    /* JADX WARN: Removed duplicated region for block: B:597:0x0d4f A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:600:0x0d62 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:620:0x0e04  */
    /* JADX WARN: Removed duplicated region for block: B:622:0x0e07 A[Catch: Exception -> 0x0056, TryCatch #3 {Exception -> 0x0056, blocks: (B:13:0x002c, B:14:0x0038, B:16:0x0040, B:18:0x0051, B:19:0x0053, B:23:0x005a, B:25:0x0062, B:27:0x0074, B:28:0x0077, B:32:0x0080, B:35:0x0088, B:36:0x009e, B:38:0x00a6, B:39:0x00dc, B:41:0x00fe, B:44:0x0106, B:46:0x010e, B:49:0x0115, B:53:0x0129, B:71:0x01ee, B:73:0x0220, B:75:0x0232, B:78:0x023a, B:80:0x023e, B:82:0x025a, B:84:0x0261, B:88:0x0274, B:92:0x0280, B:94:0x028c, B:95:0x0292, B:97:0x029d, B:99:0x02a3, B:101:0x02af, B:102:0x02bb, B:103:0x02c5, B:105:0x02d5, B:107:0x02e5, B:109:0x02eb, B:118:0x0321, B:123:0x033e, B:133:0x0361, B:135:0x0367, B:139:0x0375, B:141:0x037b, B:147:0x0386, B:150:0x0399, B:164:0x03cc, B:166:0x03d0, B:174:0x03eb, B:176:0x03f2, B:178:0x03fa, B:181:0x0428, B:183:0x0433, B:193:0x04a7, B:199:0x04ca, B:201:0x04ee, B:203:0x0504, B:205:0x0508, B:210:0x0514, B:211:0x051a, B:215:0x0527, B:221:0x056f, B:222:0x0572, B:216:0x053d, B:218:0x0545, B:219:0x0559, B:223:0x057d, B:243:0x05fc, B:254:0x0615, B:256:0x0633, B:259:0x0664, B:261:0x066e, B:264:0x0686, B:266:0x0697, B:274:0x06c8, B:278:0x06eb, B:280:0x070a, B:282:0x0735, B:284:0x0751, B:286:0x0761, B:290:0x0770, B:292:0x0776, B:297:0x0788, B:299:0x079c, B:302:0x07b1, B:343:0x08dc, B:345:0x08e2, B:355:0x08fb, B:357:0x0901, B:365:0x0913, B:368:0x091d, B:371:0x0926, B:385:0x094b, B:391:0x095a, B:394:0x098e, B:460:0x0aa9, B:462:0x0af3, B:463:0x0afa, B:466:0x0b04, B:468:0x0b08, B:470:0x0b0e, B:488:0x0b6c, B:513:0x0bce, B:542:0x0c14, B:551:0x0c52, B:553:0x0c5a, B:556:0x0c62, B:558:0x0c6a, B:562:0x0c75, B:579:0x0d09, B:583:0x0d19, B:598:0x0d5c, B:600:0x0d62, B:602:0x0d66, B:604:0x0d71, B:606:0x0d77, B:608:0x0d81, B:610:0x0d90, B:612:0x0d9e, B:614:0x0dbd, B:615:0x0dc2, B:617:0x0deb, B:618:0x0df4, B:622:0x0e07, B:624:0x0e0d, B:626:0x0e15, B:628:0x0e1b, B:629:0x0e3d, B:586:0x0d27, B:594:0x0d3c, B:596:0x0d48, B:563:0x0c9b, B:564:0x0ca0, B:565:0x0ca3, B:567:0x0cab, B:569:0x0cb4, B:571:0x0cbc, B:575:0x0cf5, B:576:0x0cfe, B:545:0x0c1e, B:547:0x0c26, B:549:0x0c4d, B:597:0x0d4f, B:523:0x0be2, B:528:0x0bef, B:531:0x0bf9, B:534:0x0c02, B:491:0x0b75, B:493:0x0b82, B:486:0x0b67, B:397:0x09a5, B:402:0x09b8, B:406:0x09cb, B:405:0x09c6, B:407:0x09d7, B:409:0x09e9, B:410:0x09f2, B:412:0x09fa, B:413:0x0a09, B:414:0x0a10, B:416:0x0a16, B:419:0x0a23, B:422:0x0a2d, B:423:0x0a30, B:425:0x0a36, B:428:0x0a3f, B:430:0x0a48, B:433:0x0a50, B:435:0x0a56, B:437:0x0a5a, B:439:0x0a62, B:445:0x0a71, B:447:0x0a77, B:449:0x0a7b, B:451:0x0a83, B:455:0x0a8a, B:457:0x0a99, B:459:0x0a9f, B:301:0x07a9, B:305:0x07d6, B:307:0x07ea, B:310:0x07ff, B:309:0x07f7, B:316:0x0835, B:318:0x083d, B:324:0x0857, B:323:0x0851, B:330:0x088c, B:332:0x0898, B:335:0x08ad, B:334:0x08a5, B:283:0x0742, B:267:0x06a3, B:269:0x06a7, B:225:0x058c, B:231:0x05a2, B:237:0x05e5, B:240:0x05eb, B:232:0x05b6, B:234:0x05bc, B:235:0x05d0, B:187:0x043f, B:190:0x044c, B:191:0x0467, B:179:0x0407, B:170:0x03d8, B:172:0x03e3, B:160:0x03b6, B:161:0x03bd, B:162:0x03c4, B:137:0x036c, B:138:0x0371, B:111:0x0301, B:113:0x0307, B:87:0x0271, B:54:0x0137, B:56:0x013d, B:57:0x0143, B:60:0x014d, B:61:0x0157, B:62:0x0169, B:64:0x016f, B:65:0x0186, B:67:0x018d, B:69:0x0195, B:70:0x01c5, B:51:0x011e, B:72:0x020e, B:379:0x093d, B:573:0x0cc6), top: B:641:0x002c, inners: #1, #4 }] */
    /* JADX WARN: Type inference failed for: r14v20 */
    /* JADX WARN: Type inference failed for: r14v21 */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v36 */
    /* JADX WARN: Type inference failed for: r14v37 */
    /* JADX WARN: Type inference failed for: r14v38 */
    /* JADX WARN: Type inference failed for: r5v96, types: [org.telegram.messenger.MessageObject] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void showOrUpdateNotification(boolean r54) {
        /*
            Method dump skipped, instructions count: 3732
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showOrUpdateNotification(boolean):void");
    }

    private int getNotificationColor() {
        return AppUtils.getNotificationColor();
    }

    private boolean isSilentMessage(MessageObject messageObject) {
        return messageObject.messageOwner.silent || messageObject.isReactionPush;
    }

    @SuppressLint({"NewApi"})
    private void setNotificationChannel(Notification notification, NotificationCompat.Builder builder, boolean z) {
        if (z) {
            builder.setChannelId(OTHER_NOTIFICATIONS_CHANNEL);
        } else {
            builder.setChannelId(notification.getChannelId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetNotificationSound(NotificationCompat.Builder builder, long j, long j2, String str, long[] jArr, int i, Uri uri, int i2, boolean z, boolean z2, boolean z3, int i3) {
        FileLog.m1157d("resetNotificationSound");
        Uri uri2 = Settings.System.DEFAULT_RINGTONE_URI;
        if (uri2 == null || uri == null || TextUtils.equals(uri2.toString(), uri.toString())) {
            return;
        }
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        String string = uri2.toString();
        String string2 = LocaleController.getString(C2369R.string.DefaultRingtone);
        if (z) {
            if (i3 == 2) {
                editorEdit.putString("ChannelSound", string2);
            } else if (i3 == 0) {
                editorEdit.putString("GroupSound", string2);
            } else if (i3 == 1) {
                editorEdit.putString("GlobalSound", string2);
            } else if (i3 == 3) {
                editorEdit.putString("StoriesSound", string2);
            } else if (i3 == 4 || i3 == 5) {
                editorEdit.putString("ReactionSound", string2);
            }
            if (i3 == 2) {
                editorEdit.putString("ChannelSoundPath", string);
            } else if (i3 == 0) {
                editorEdit.putString("GroupSoundPath", string);
            } else if (i3 == 1) {
                editorEdit.putString("GlobalSoundPath", string);
            } else if (i3 == 3) {
                editorEdit.putString("StoriesSoundPath", string);
            } else if (i3 == 4 || i3 == 5) {
                editorEdit.putString("ReactionSound", string);
            }
            getNotificationsController().lambda$deleteNotificationChannelGlobal$40(i3, -1);
        } else {
            editorEdit.putString("sound_" + getSharedPrefKey(j, j2), string2);
            editorEdit.putString("sound_path_" + getSharedPrefKey(j, j2), string);
            lambda$deleteNotificationChannel$39(j, j2, -1);
        }
        editorEdit.apply();
        builder.setChannelId(validateChannelId(j, j2, str, jArr, i, uri2, i2, z, z2, z3, i3));
        notificationManager.notify(this.notificationId, builder.build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:140:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x043e  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x04e3  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x050c  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0510  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x051f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x057f  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x07a8  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x07b4  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x07bc  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x07ea  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x085a  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0867  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x0872  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x087f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x089c  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x08b3  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x08ce  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x0a15 A[LOOP:5: B:390:0x0a0d->B:392:0x0a15, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0a42  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0a5a  */
    /* JADX WARN: Removed duplicated region for block: B:401:0x0a5f  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0a70  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0bee  */
    /* JADX WARN: Removed duplicated region for block: B:521:0x0d07  */
    /* JADX WARN: Removed duplicated region for block: B:572:0x0e4a  */
    /* JADX WARN: Removed duplicated region for block: B:574:0x0e4e  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x0e7b  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x0ed2  */
    /* JADX WARN: Removed duplicated region for block: B:590:0x0f03  */
    /* JADX WARN: Removed duplicated region for block: B:599:0x0f26  */
    /* JADX WARN: Removed duplicated region for block: B:621:0x1001  */
    /* JADX WARN: Removed duplicated region for block: B:631:0x1026  */
    /* JADX WARN: Removed duplicated region for block: B:633:0x102a  */
    /* JADX WARN: Removed duplicated region for block: B:638:0x1051  */
    /* JADX WARN: Removed duplicated region for block: B:647:0x1092  */
    /* JADX WARN: Removed duplicated region for block: B:650:0x10b3  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x1112  */
    /* JADX WARN: Removed duplicated region for block: B:657:0x114f  */
    /* JADX WARN: Removed duplicated region for block: B:662:0x1177  */
    /* JADX WARN: Removed duplicated region for block: B:663:0x119a  */
    /* JADX WARN: Removed duplicated region for block: B:666:0x11b7  */
    /* JADX WARN: Removed duplicated region for block: B:671:0x11e0  */
    /* JADX WARN: Removed duplicated region for block: B:674:0x1216  */
    /* JADX WARN: Removed duplicated region for block: B:675:0x121d  */
    /* JADX WARN: Removed duplicated region for block: B:678:0x1257  */
    /* JADX WARN: Removed duplicated region for block: B:681:0x127d A[Catch: Exception -> 0x1285, TryCatch #7 {Exception -> 0x1285, blocks: (B:679:0x125e, B:681:0x127d, B:685:0x1289, B:687:0x128d, B:688:0x1294), top: B:827:0x125e }] */
    /* JADX WARN: Removed duplicated region for block: B:692:0x12a9  */
    /* JADX WARN: Removed duplicated region for block: B:711:0x1308  */
    /* JADX WARN: Removed duplicated region for block: B:713:0x130b  */
    /* JADX WARN: Removed duplicated region for block: B:716:0x134e  */
    /* JADX WARN: Removed duplicated region for block: B:732:0x137b  */
    /* JADX WARN: Removed duplicated region for block: B:735:0x1384  */
    /* JADX WARN: Removed duplicated region for block: B:737:0x1389  */
    /* JADX WARN: Removed duplicated region for block: B:745:0x13a3  */
    /* JADX WARN: Removed duplicated region for block: B:764:0x1449  */
    /* JADX WARN: Removed duplicated region for block: B:767:0x1455  */
    /* JADX WARN: Removed duplicated region for block: B:829:0x0e57 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8, types: [boolean, int] */
    @android.annotation.SuppressLint({"InlinedApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void showExtraNotifications(androidx.core.app.NotificationCompat.Builder r78, java.lang.String r79, long r80, long r82, java.lang.String r84, long[] r85, int r86, android.net.Uri r87, int r88, boolean r89, boolean r90, boolean r91, int r92) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 5699
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showExtraNotifications(androidx.core.app.NotificationCompat$Builder, java.lang.String, long, long, java.lang.String, long[], int, android.net.Uri, int, boolean, boolean, boolean, int):void");
    }

    /* renamed from: org.telegram.messenger.NotificationsController$1NotificationHolder, reason: invalid class name */
    class C1NotificationHolder {
        TLRPC.Chat chat;
        long dialogId;

        /* renamed from: id */
        int f1458id;
        String name;
        NotificationCompat.Builder notification;
        boolean story;
        long topicId;
        TLRPC.User user;
        final /* synthetic */ String val$chatName;
        final /* synthetic */ int val$chatType;
        final /* synthetic */ int val$importance;
        final /* synthetic */ boolean val$isDefault;
        final /* synthetic */ boolean val$isInApp;
        final /* synthetic */ boolean val$isSilent;
        final /* synthetic */ long val$lastTopicId;
        final /* synthetic */ int val$ledColor;
        final /* synthetic */ Uri val$sound;
        final /* synthetic */ long[] val$vibrationPattern;

        C1NotificationHolder(int i, long j, boolean z, long j2, String str, TLRPC.User user, TLRPC.Chat chat, NotificationCompat.Builder builder, long j3, String str2, long[] jArr, int i2, Uri uri, int i3, boolean z2, boolean z3, boolean z4, int i4) {
            this.val$lastTopicId = j3;
            this.val$chatName = str2;
            this.val$vibrationPattern = jArr;
            this.val$ledColor = i2;
            this.val$sound = uri;
            this.val$importance = i3;
            this.val$isDefault = z2;
            this.val$isInApp = z3;
            this.val$isSilent = z4;
            this.val$chatType = i4;
            this.f1458id = i;
            this.name = str;
            this.user = user;
            this.chat = chat;
            this.notification = builder;
            this.dialogId = j;
            this.story = z;
            this.topicId = j2;
        }

        void call() throws IOException {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1161w("show dialog notification with id " + this.f1458id + " " + this.dialogId + " user=" + this.user + " chat=" + this.chat);
            }
            try {
                NotificationsController.notificationManager.notify(this.f1458id, this.notification.build());
            } catch (SecurityException e) {
                FileLog.m1160e(e);
                NotificationsController.this.resetNotificationSound(this.notification, this.dialogId, this.val$lastTopicId, this.val$chatName, this.val$vibrationPattern, this.val$ledColor, this.val$sound, this.val$importance, this.val$isDefault, this.val$isInApp, this.val$isSilent, this.val$chatType);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$AK6bR9J0YH6PDgrGfVgJrr75fFA(Uri uri, File file) {
        try {
            ApplicationLoader.applicationContext.revokeUriPermission(uri, 1);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        if (file != null) {
            try {
                file.delete();
            } catch (Exception e2) {
                FileLog.m1160e(e2);
            }
        }
    }

    private String cutLastName(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf < 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, iIndexOf));
        sb.append(str.endsWith("…") ? "…" : "");
        return sb.toString();
    }

    private Pair<Integer, Boolean> parseStoryPushes(ArrayList<String> arrayList, ArrayList<Object> arrayList2) {
        String userName;
        TLRPC.FileLocation fileLocation;
        int iMin = Math.min(3, this.storyPushMessages.size());
        boolean z = false;
        int size = 0;
        for (int i = 0; i < iMin; i++) {
            StoryNotification storyNotification = this.storyPushMessages.get(i);
            size += storyNotification.dateByIds.size();
            z |= storyNotification.hidden;
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(storyNotification.dialogId));
            if (user == null && (user = getMessagesStorage().getUserSync(storyNotification.dialogId)) != null) {
                getMessagesController().putUser(user, true);
            }
            Object obj = null;
            if (user != null) {
                userName = UserObject.getUserName(user);
                TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
                if (userProfilePhoto != null && (fileLocation = userProfilePhoto.photo_small) != null && fileLocation.volume_id != 0 && fileLocation.local_id != 0) {
                    File pathToAttach = getFileLoader().getPathToAttach(user.photo.photo_small, true);
                    if (!pathToAttach.exists()) {
                        pathToAttach = user.photo.photo_big != null ? getFileLoader().getPathToAttach(user.photo.photo_big, true) : null;
                        if (pathToAttach != null && !pathToAttach.exists()) {
                            pathToAttach = null;
                        }
                    }
                    if (pathToAttach != null) {
                        obj = pathToAttach;
                    }
                }
            } else {
                userName = storyNotification.localName;
                if (userName != null) {
                }
            }
            if (userName.length() > 50) {
                userName = userName.substring(0, 25) + "…";
            }
            arrayList.add(userName);
            if (obj == null && user != null) {
                arrayList2.add(user);
            } else if (obj != null) {
                arrayList2.add(obj);
            }
        }
        if (z) {
            arrayList2.clear();
        }
        return new Pair<>(Integer.valueOf(size), Boolean.valueOf(z));
    }

    public static Person.Builder loadRoundAvatar(File file, Person.Builder builder) {
        if (file != null && Build.VERSION.SDK_INT >= 28) {
            try {
                builder.setIcon(IconCompat.createWithBitmap(ImageDecoder.decodeBitmap(ImageDecoder.createSource(file), new ImageDecoder.OnHeaderDecodedListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda49
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        imageDecoder.setPostProcessor(new PostProcessor() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda16
                            @Override // android.graphics.PostProcessor
                            public final int onPostProcess(Canvas canvas) {
                                return NotificationsController.m3922$r8$lambda$gHVQD6JRxFTlRhYgJq7JQcHxNM(canvas);
                            }
                        });
                    }
                })));
            } catch (Throwable unused) {
            }
        }
        return builder;
    }

    /* renamed from: $r8$lambda$gHVQD6JRxFTlRh-YgJq7JQcHxNM, reason: not valid java name */
    public static /* synthetic */ int m3922$r8$lambda$gHVQD6JRxFTlRhYgJq7JQcHxNM(Canvas canvas) {
        Path path = new Path();
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        int width = canvas.getWidth();
        float f = width / 2;
        path.addRoundRect(0.0f, 0.0f, width, canvas.getHeight(), f, f, Path.Direction.CW);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawPath(path, paint);
        return -3;
    }

    public static Bitmap loadMultipleAvatars(ArrayList<Object> arrayList) {
        int i;
        Bitmap bitmap;
        Paint paint;
        float f;
        float size;
        float size2;
        float f2;
        float f3;
        float f4;
        float f5;
        Object obj;
        ArrayList<Object> arrayList2 = arrayList;
        if (Build.VERSION.SDK_INT < 28 || arrayList2 == null || arrayList2.size() == 0) {
            return null;
        }
        int iM1146dp = AndroidUtilities.m1146dp(64.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iM1146dp, iM1146dp, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Matrix matrix = new Matrix();
        Paint paint2 = new Paint(3);
        Paint paint3 = new Paint(1);
        Rect rect = new Rect();
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        float f6 = arrayList2.size() == 1 ? 1.0f : arrayList2.size() == 2 ? 0.65f : 0.5f;
        int i2 = 0;
        TextPaint textPaint = null;
        while (i2 < arrayList2.size()) {
            float f7 = iM1146dp;
            float f8 = (1.0f - f6) * f7;
            try {
                size = (f8 / arrayList2.size()) * ((arrayList2.size() - 1) - i2);
                size2 = i2 * (f8 / arrayList2.size());
                f2 = f7 * f6;
                f3 = f2 / 2.0f;
                i = iM1146dp;
                f4 = size + f3;
                bitmap = bitmapCreateBitmap;
                f5 = size2 + f3;
                f = f6;
                try {
                    canvas.drawCircle(f4, f5, AndroidUtilities.m1146dp(2.0f) + f3, paint3);
                    obj = arrayList2.get(i2);
                    paint = paint3;
                } catch (Throwable unused) {
                    paint = paint3;
                }
            } catch (Throwable unused2) {
                i = iM1146dp;
                bitmap = bitmapCreateBitmap;
                paint = paint3;
                f = f6;
            }
            if (obj instanceof File) {
                String absolutePath = ((File) arrayList2.get(i2)).getAbsolutePath();
                BitmapFactory.Options options = new BitmapFactory.Options();
                try {
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(absolutePath, options);
                    int i3 = (int) f2;
                    options.inSampleSize = StoryEntry.calculateInSampleSize(options, i3, i3);
                    options.inJustDecodeBounds = false;
                    options.inDither = true;
                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(absolutePath, options);
                    Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                    BitmapShader bitmapShader = new BitmapShader(bitmapDecodeFile, tileMode, tileMode);
                    matrix.reset();
                    matrix.postScale(f2 / bitmapDecodeFile.getWidth(), f2 / bitmapDecodeFile.getHeight());
                    matrix.postTranslate(size, size2);
                    bitmapShader.setLocalMatrix(matrix);
                    paint2.setShader(bitmapShader);
                    canvas.drawCircle(f4, f5, f3, paint2);
                    bitmapDecodeFile.recycle();
                } catch (Throwable unused3) {
                    i2++;
                    arrayList2 = arrayList;
                    iM1146dp = i;
                    bitmapCreateBitmap = bitmap;
                    f6 = f;
                    paint3 = paint;
                }
            } else if (obj instanceof TLRPC.User) {
                TLRPC.User user = (TLRPC.User) obj;
                try {
                    paint2.setShader(new LinearGradient(size, size2, size, size2 + f2, new int[]{Theme.getColor(Theme.keys_avatar_background[AvatarDrawable.getColorIndex(user.f1734id)]), Theme.getColor(Theme.keys_avatar_background2[AvatarDrawable.getColorIndex(user.f1734id)])}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
                    canvas.drawCircle(f4, f5, f3, paint2);
                    if (textPaint == null) {
                        try {
                            try {
                                TextPaint textPaint2 = new TextPaint(1);
                                try {
                                    textPaint2.setTypeface(AndroidUtilities.bold());
                                    textPaint2.setTextSize(f7 * 0.25f);
                                    textPaint2.setColor(-1);
                                    textPaint = textPaint2;
                                } catch (Throwable unused4) {
                                    textPaint = textPaint2;
                                    i2++;
                                    arrayList2 = arrayList;
                                    iM1146dp = i;
                                    bitmapCreateBitmap = bitmap;
                                    f6 = f;
                                    paint3 = paint;
                                }
                            } catch (Throwable unused5) {
                            }
                        } catch (Throwable unused6) {
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    AvatarDrawable.getAvatarSymbols(user.first_name, user.last_name, null, sb);
                    String string = sb.toString();
                    try {
                        textPaint.getTextBounds(string, 0, string.length(), rect);
                        canvas.drawText(string, (f4 - (rect.width() / 2.0f)) - rect.left, (f5 - (rect.height() / 2.0f)) - rect.top, textPaint);
                    } catch (Throwable unused7) {
                    }
                } catch (Throwable unused8) {
                }
            }
            i2++;
            arrayList2 = arrayList;
            iM1146dp = i;
            bitmapCreateBitmap = bitmap;
            f6 = f;
            paint3 = paint;
        }
        return bitmapCreateBitmap;
    }

    public void playOutChatSound() {
        if (!this.inChatSoundEnabled || MediaController.getInstance().isRecordingAudio()) {
            return;
        }
        try {
            if (audioManager.getRingerMode() == 0) {
                return;
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$playOutChatSound$46();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playOutChatSound$46() {
        try {
            if (Math.abs(SystemClock.elapsedRealtime() - this.lastSoundOutPlay) <= 100) {
                return;
            }
            this.lastSoundOutPlay = SystemClock.elapsedRealtime();
            if (this.soundPool == null) {
                SoundPool soundPool = new SoundPool(3, 1, 0);
                this.soundPool = soundPool;
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda5
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                        NotificationsController.m3915$r8$lambda$EFQoapY2vqLiJ9LLZ1Q_nJ8AB0(soundPool2, i, i2);
                    }
                });
            }
            if (this.soundOut == 0 && !this.soundOutLoaded) {
                this.soundOutLoaded = true;
                this.soundOut = this.soundPool.load(ApplicationLoader.applicationContext, C2369R.raw.sound_out, 1);
            }
            int i = this.soundOut;
            if (i != 0) {
                try {
                    this.soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
    }

    /* renamed from: $r8$lambda$EFQoapY2vqL-iJ9LLZ1Q_nJ8AB0, reason: not valid java name */
    public static /* synthetic */ void m3915$r8$lambda$EFQoapY2vqLiJ9LLZ1Q_nJ8AB0(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            try {
                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    public void clearDialogNotificationsSettings(long j, long j2) {
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        String sharedPrefKey = getSharedPrefKey(j, j2);
        editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey).remove(NotificationsSettingsFacade.PROPERTY_CUSTOM + sharedPrefKey);
        getMessagesStorage().setDialogFlags(j, 0L);
        TLRPC.Dialog dialog = (TLRPC.Dialog) getMessagesController().dialogs_dict.get(j);
        if (dialog != null) {
            dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
        }
        editorEdit.apply();
        getNotificationsController().updateServerNotificationsSettings(j, j2, true);
    }

    public void setDialogNotificationsSettings(long j, long j2, int i) {
        SharedPreferences.Editor editorEdit = getAccountInstance().getNotificationsSettings().edit();
        TLRPC.Dialog dialog = (TLRPC.Dialog) MessagesController.getInstance(UserConfig.selectedAccount).dialogs_dict.get(j);
        if (i == 4) {
            if (isGlobalNotificationsEnabled(j, false, false)) {
                editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2));
            } else {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 0);
            }
            getMessagesStorage().setDialogFlags(j, 0L);
            if (dialog != null) {
                dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
            }
        } else {
            int currentTime = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentTime();
            if (i == 0) {
                currentTime += 3600;
            } else if (i == 1) {
                currentTime += 28800;
            } else if (i == 2) {
                currentTime += 172800;
            } else if (i == 3) {
                currentTime = ConnectionsManager.DEFAULT_DATACENTER_ID;
            }
            long j3 = 1;
            if (i == 3) {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 2);
            } else {
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 3);
                editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + getSharedPrefKey(j, j2), currentTime);
                j3 = 1 | (((long) currentTime) << 32);
            }
            getInstance(UserConfig.selectedAccount).removeNotificationsForDialog(j);
            MessagesStorage.getInstance(UserConfig.selectedAccount).setDialogFlags(j, j3);
            if (dialog != null) {
                TLRPC.TL_peerNotifySettings tL_peerNotifySettings = new TLRPC.TL_peerNotifySettings();
                dialog.notify_settings = tL_peerNotifySettings;
                tL_peerNotifySettings.mute_until = currentTime;
            }
        }
        editorEdit.apply();
        updateServerNotificationsSettings(j, j2);
    }

    public void updateServerNotificationsSettings(long j, long j2) {
        updateServerNotificationsSettings(j, j2, true);
    }

    public void updateServerNotificationsSettings(long j, long j2, boolean z) {
        if (z) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        }
        if (DialogObject.isEncryptedDialog(j)) {
            return;
        }
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        TL_account.updateNotifySettings updatenotifysettings = new TL_account.updateNotifySettings();
        updatenotifysettings.settings = new TLRPC.TL_inputPeerNotifySettings();
        String sharedPrefKey = getSharedPrefKey(j, j2);
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings = updatenotifysettings.settings;
        tL_inputPeerNotifySettings.flags |= 1;
        tL_inputPeerNotifySettings.show_previews = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_CONTENT_PREVIEW + sharedPrefKey, true);
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings2 = updatenotifysettings.settings;
        tL_inputPeerNotifySettings2.flags = tL_inputPeerNotifySettings2.flags | 2;
        tL_inputPeerNotifySettings2.silent = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + sharedPrefKey, false);
        if (notificationsSettings.contains(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + sharedPrefKey)) {
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings3 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings3.flags |= 64;
            tL_inputPeerNotifySettings3.stories_muted = !notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + sharedPrefKey, true);
        }
        int i = notificationsSettings.getInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), -1);
        if (i != -1) {
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings4 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings4.flags |= 4;
            if (i == 3) {
                tL_inputPeerNotifySettings4.mute_until = notificationsSettings.getInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + getSharedPrefKey(j, j2), 0);
            } else {
                tL_inputPeerNotifySettings4.mute_until = i == 2 ? ConnectionsManager.DEFAULT_DATACENTER_ID : 0;
            }
        }
        long j3 = notificationsSettings.getLong("sound_document_id_" + getSharedPrefKey(j, j2), 0L);
        String string = notificationsSettings.getString("sound_path_" + getSharedPrefKey(j, j2), null);
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings5 = updatenotifysettings.settings;
        tL_inputPeerNotifySettings5.flags = tL_inputPeerNotifySettings5.flags | 8;
        if (j3 != 0) {
            TLRPC.TL_notificationSoundRingtone tL_notificationSoundRingtone = new TLRPC.TL_notificationSoundRingtone();
            tL_notificationSoundRingtone.f1699id = j3;
            updatenotifysettings.settings.sound = tL_notificationSoundRingtone;
        } else if (string != null) {
            if (string.equalsIgnoreCase("NoSound")) {
                updatenotifysettings.settings.sound = new TLRPC.TL_notificationSoundNone();
            } else {
                TLRPC.TL_notificationSoundLocal tL_notificationSoundLocal = new TLRPC.TL_notificationSoundLocal();
                tL_notificationSoundLocal.title = notificationsSettings.getString("sound_" + getSharedPrefKey(j, j2), null);
                tL_notificationSoundLocal.data = string;
                updatenotifysettings.settings.sound = tL_notificationSoundLocal;
            }
        } else {
            tL_inputPeerNotifySettings5.sound = new TLRPC.TL_notificationSoundDefault();
        }
        if (j2 != 0 && j != getUserConfig().getClientUserId()) {
            TLRPC.TL_inputNotifyForumTopic tL_inputNotifyForumTopic = new TLRPC.TL_inputNotifyForumTopic();
            tL_inputNotifyForumTopic.peer = getMessagesController().getInputPeer(j);
            tL_inputNotifyForumTopic.top_msg_id = (int) j2;
            updatenotifysettings.peer = tL_inputNotifyForumTopic;
        } else {
            TLRPC.TL_inputNotifyPeer tL_inputNotifyPeer = new TLRPC.TL_inputNotifyPeer();
            updatenotifysettings.peer = tL_inputNotifyPeer;
            tL_inputNotifyPeer.peer = getMessagesController().getInputPeer(j);
        }
        getConnectionsManager().sendRequest(updatenotifysettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda54
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                NotificationsController.m3916$r8$lambda$LYS4eiu9rRCuQmcgvxsvgAgduI(tLObject, tL_error);
            }
        });
    }

    public void updateServerNotificationsSettings(int i) {
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        if (i == 4 || i == 5) {
            TL_account.setReactionsNotifySettings setreactionsnotifysettings = new TL_account.setReactionsNotifySettings();
            setreactionsnotifysettings.settings = new TL_account.TL_reactionsNotifySettings();
            if (notificationsSettings.getBoolean("EnableReactionsMessages", true)) {
                setreactionsnotifysettings.settings.flags |= 1;
                if (notificationsSettings.getBoolean("EnableReactionsMessagesContacts", false)) {
                    setreactionsnotifysettings.settings.messages_notify_from = new TL_account.TL_reactionNotificationsFromContacts();
                } else {
                    setreactionsnotifysettings.settings.messages_notify_from = new TL_account.TL_reactionNotificationsFromAll();
                }
            }
            if (notificationsSettings.getBoolean("EnableReactionsStories", true)) {
                setreactionsnotifysettings.settings.flags |= 2;
                if (notificationsSettings.getBoolean("EnableReactionsStoriesContacts", false)) {
                    setreactionsnotifysettings.settings.stories_notify_from = new TL_account.TL_reactionNotificationsFromContacts();
                } else {
                    setreactionsnotifysettings.settings.stories_notify_from = new TL_account.TL_reactionNotificationsFromAll();
                }
            }
            setreactionsnotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnableReactionsPreview", true);
            setreactionsnotifysettings.settings.sound = getInputSound(notificationsSettings, "ReactionSound", "ReactionSoundDocId", "ReactionSoundPath");
            getConnectionsManager().sendRequest(setreactionsnotifysettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda34
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    NotificationsController.$r8$lambda$XCcar2pyUU3PNWJSRPBNWRsFJM0(tLObject, tL_error);
                }
            });
            return;
        }
        TL_account.updateNotifySettings updatenotifysettings = new TL_account.updateNotifySettings();
        TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings = new TLRPC.TL_inputPeerNotifySettings();
        updatenotifysettings.settings = tL_inputPeerNotifySettings;
        tL_inputPeerNotifySettings.flags = 5;
        if (i == 0) {
            updatenotifysettings.peer = new TLRPC.TL_inputNotifyChats();
            updatenotifysettings.settings.mute_until = notificationsSettings.getInt("EnableGroup2", 0);
            updatenotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewGroup", true);
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings2 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings2.flags |= 8;
            tL_inputPeerNotifySettings2.sound = getInputSound(notificationsSettings, "GroupSound", "GroupSoundDocId", "GroupSoundPath");
        } else if (i == 1 || i == 3) {
            updatenotifysettings.peer = new TLRPC.TL_inputNotifyUsers();
            updatenotifysettings.settings.mute_until = notificationsSettings.getInt("EnableAll2", 0);
            updatenotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewAll", true);
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings3 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings3.flags |= 128;
            tL_inputPeerNotifySettings3.stories_hide_sender = notificationsSettings.getBoolean("EnableHideStoriesSenders", false);
            if (notificationsSettings.contains("EnableAllStories")) {
                TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings4 = updatenotifysettings.settings;
                tL_inputPeerNotifySettings4.flags |= 64;
                tL_inputPeerNotifySettings4.stories_muted = !notificationsSettings.getBoolean("EnableAllStories", true);
            }
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings5 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings5.flags |= 8;
            tL_inputPeerNotifySettings5.sound = getInputSound(notificationsSettings, "GlobalSound", "GlobalSoundDocId", "GlobalSoundPath");
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings6 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings6.flags |= 256;
            tL_inputPeerNotifySettings6.stories_sound = getInputSound(notificationsSettings, "StoriesSound", "StoriesSoundDocId", "StoriesSoundPath");
        } else {
            updatenotifysettings.peer = new TLRPC.TL_inputNotifyBroadcasts();
            updatenotifysettings.settings.mute_until = notificationsSettings.getInt("EnableChannel2", 0);
            updatenotifysettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewChannel", true);
            TLRPC.TL_inputPeerNotifySettings tL_inputPeerNotifySettings7 = updatenotifysettings.settings;
            tL_inputPeerNotifySettings7.flags |= 8;
            tL_inputPeerNotifySettings7.sound = getInputSound(notificationsSettings, "ChannelSound", "ChannelSoundDocId", "ChannelSoundPath");
        }
        getConnectionsManager().sendRequest(updatenotifysettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda35
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                NotificationsController.$r8$lambda$m8WhB6SNQirs3X5XQWhhP5mgO4s(tLObject, tL_error);
            }
        });
    }

    private TLRPC.NotificationSound getInputSound(SharedPreferences sharedPreferences, String str, String str2, String str3) {
        long j = sharedPreferences.getLong(str2, 0L);
        String string = sharedPreferences.getString(str3, "NoSound");
        if (j != 0) {
            TLRPC.TL_notificationSoundRingtone tL_notificationSoundRingtone = new TLRPC.TL_notificationSoundRingtone();
            tL_notificationSoundRingtone.f1699id = j;
            return tL_notificationSoundRingtone;
        }
        if (string != null) {
            if (string.equalsIgnoreCase("NoSound")) {
                return new TLRPC.TL_notificationSoundNone();
            }
            TLRPC.TL_notificationSoundLocal tL_notificationSoundLocal = new TLRPC.TL_notificationSoundLocal();
            tL_notificationSoundLocal.title = sharedPreferences.getString(str, null);
            tL_notificationSoundLocal.data = string;
            return tL_notificationSoundLocal;
        }
        return new TLRPC.TL_notificationSoundDefault();
    }

    public boolean isGlobalNotificationsEnabled(long j, boolean z, boolean z2) {
        return isGlobalNotificationsEnabled(j, null, z, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean isGlobalNotificationsEnabled(long r1, java.lang.Boolean r3, boolean r4, boolean r5) {
        /*
            r0 = this;
            if (r4 == 0) goto L4
            r1 = 4
            goto L35
        L4:
            if (r5 == 0) goto L8
            r1 = 5
            goto L35
        L8:
            boolean r4 = org.telegram.messenger.DialogObject.isChatDialog(r1)
            if (r4 == 0) goto L34
            r4 = 0
            r5 = 2
            if (r3 == 0) goto L1c
            boolean r1 = r3.booleanValue()
            if (r1 == 0) goto L1a
        L18:
            r1 = 2
            goto L35
        L1a:
            r1 = 0
            goto L35
        L1c:
            org.telegram.messenger.MessagesController r3 = r0.getMessagesController()
            long r1 = -r1
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            org.telegram.tgnet.TLRPC$Chat r1 = r3.getChat(r1)
            boolean r2 = org.telegram.messenger.ChatObject.isChannel(r1)
            if (r2 == 0) goto L1a
            boolean r1 = r1.megagroup
            if (r1 != 0) goto L1a
            goto L18
        L34:
            r1 = 1
        L35:
            boolean r1 = r0.isGlobalNotificationsEnabled(r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.isGlobalNotificationsEnabled(long, java.lang.Boolean, boolean, boolean):boolean");
    }

    public boolean isGlobalNotificationsEnabled(int i) {
        if (i == 4) {
            return getAccountInstance().getNotificationsSettings().getBoolean("EnableReactionsMessages", true);
        }
        if (i == 5) {
            return getAccountInstance().getNotificationsSettings().getBoolean("EnableReactionsStories", true);
        }
        if (i == 3) {
            return getAccountInstance().getNotificationsSettings().getBoolean("EnableAllStories", true);
        }
        return getAccountInstance().getNotificationsSettings().getInt(getGlobalNotificationsKey(i), 0) < getConnectionsManager().getCurrentTime();
    }

    public void setGlobalNotificationsEnabled(int i, int i2) {
        getAccountInstance().getNotificationsSettings().edit().putInt(getGlobalNotificationsKey(i), i2).apply();
        updateServerNotificationsSettings(i);
        getMessagesStorage().updateMutedDialogsFiltersCounters();
        deleteNotificationChannelGlobal(i);
    }

    public static String getGlobalNotificationsKey(int i) {
        if (i == 0) {
            return "EnableGroup2";
        }
        if (i == 1) {
            return "EnableAll2";
        }
        return "EnableChannel2";
    }

    public void muteDialog(long j, long j2, boolean z) {
        if (z) {
            getInstance(this.currentAccount).muteUntil(j, j2, ConnectionsManager.DEFAULT_DATACENTER_ID);
            return;
        }
        boolean zIsGlobalNotificationsEnabled = getInstance(this.currentAccount).isGlobalNotificationsEnabled(j, false, false);
        boolean z2 = j2 != 0;
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
        if (zIsGlobalNotificationsEnabled && !z2) {
            editorEdit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2));
        } else {
            editorEdit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, j2), 0);
        }
        if (j2 == 0) {
            getMessagesStorage().setDialogFlags(j, 0L);
            TLRPC.Dialog dialog = (TLRPC.Dialog) getMessagesController().dialogs_dict.get(j);
            if (dialog != null) {
                dialog.notify_settings = new TLRPC.TL_peerNotifySettings();
            }
        }
        editorEdit.apply();
        updateServerNotificationsSettings(j, j2);
    }

    public NotificationsSettingsFacade getNotificationsSettingsFacade() {
        return this.dialogsNotificationsFacade;
    }

    public void loadTopicsNotificationsExceptions(final long j, final Consumer<HashSet<Integer>> consumer) {
        getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadTopicsNotificationsExceptions$51(j, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadTopicsNotificationsExceptions$51(long j, final Consumer consumer) {
        final HashSet hashSet = new HashSet();
        Iterator<Map.Entry<String, ?>> it = MessagesController.getNotificationsSettings(this.currentAccount).getAll().entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            if (key.startsWith(NotificationsSettingsFacade.PROPERTY_NOTIFY + j)) {
                Integer num = Utilities.parseInt((CharSequence) key.replace(NotificationsSettingsFacade.PROPERTY_NOTIFY + j, ""));
                int iIntValue = num.intValue();
                if (iIntValue != 0 && getMessagesController().isDialogMuted(j, iIntValue) != getMessagesController().isDialogMuted(j, 0L)) {
                    hashSet.add(num);
                }
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.m3909$r8$lambda$97Gr_9tO_IzJhSW54raGI8wjn0(consumer, hashSet);
            }
        });
    }

    /* renamed from: $r8$lambda$97Gr_9tO_IzJhSW54raG-I8wjn0, reason: not valid java name */
    public static /* synthetic */ void m3909$r8$lambda$97Gr_9tO_IzJhSW54raGI8wjn0(Consumer consumer, HashSet hashSet) {
        if (consumer != null) {
            consumer.m971v(hashSet);
        }
    }

    private static class DialogKey {
        final long dialogId;
        final boolean story;
        final long topicId;

        private DialogKey(long j, long j2, boolean z) {
            this.dialogId = j;
            this.topicId = j2;
            this.story = z;
        }
    }

    public static class StoryNotification {
        public long date;
        final HashMap<Integer, Pair<Long, Long>> dateByIds;
        final long dialogId;
        boolean hidden;
        String localName;

        public StoryNotification(long j, String str, int i, long j2) {
            this(j, str, i, j2, j2 + 86400000);
        }

        public StoryNotification(long j, String str, int i, long j2, long j3) {
            HashMap<Integer, Pair<Long, Long>> map = new HashMap<>();
            this.dateByIds = map;
            this.dialogId = j;
            this.localName = str;
            map.put(Integer.valueOf(i), new Pair<>(Long.valueOf(j2), Long.valueOf(j3)));
            this.date = j2;
        }

        public long getLeastDate() {
            long jLongValue = -1;
            for (Pair<Long, Long> pair : this.dateByIds.values()) {
                if (jLongValue == -1 || jLongValue > ((Long) pair.first).longValue()) {
                    jLongValue = ((Long) pair.first).longValue();
                }
            }
            return jLongValue;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkStoryPushes() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = 0;
        boolean z = false;
        while (i < this.storyPushMessages.size()) {
            StoryNotification storyNotification = this.storyPushMessages.get(i);
            Iterator<Map.Entry<Integer, Pair<Long, Long>>> it = storyNotification.dateByIds.entrySet().iterator();
            while (it.hasNext()) {
                if (jCurrentTimeMillis >= ((Long) it.next().getValue().second).longValue()) {
                    it.remove();
                    z = true;
                }
            }
            if (z) {
                if (storyNotification.dateByIds.isEmpty()) {
                    getMessagesStorage().deleteStoryPushMessage(storyNotification.dialogId);
                    this.storyPushMessages.remove(i);
                    i--;
                } else {
                    getMessagesStorage().putStoryPushMessage(storyNotification);
                }
            }
            i++;
        }
        if (z) {
            showOrUpdateNotification(false);
        }
        updateStoryPushesRunnable();
    }

    private void updateStoryPushesRunnable() {
        long jMin = Long.MAX_VALUE;
        for (int i = 0; i < this.storyPushMessages.size(); i++) {
            Iterator<Pair<Long, Long>> it = this.storyPushMessages.get(i).dateByIds.values().iterator();
            while (it.hasNext()) {
                jMin = Math.min(jMin, ((Long) it.next().second).longValue());
            }
        }
        DispatchQueue dispatchQueue = notificationsQueue;
        dispatchQueue.cancelRunnable(this.checkStoryPushesRunnable);
        long jCurrentTimeMillis = jMin - System.currentTimeMillis();
        if (jMin != Long.MAX_VALUE) {
            dispatchQueue.postRunnable(this.checkStoryPushesRunnable, Math.max(0L, jCurrentTimeMillis));
        }
    }

    private String getTitle(TLRPC.Chat chat) {
        if (chat == null) {
            return null;
        }
        if (chat.monoforum) {
            return ForumUtilities.getMonoForumTitle(this.currentAccount, chat);
        }
        return chat.title;
    }
}
