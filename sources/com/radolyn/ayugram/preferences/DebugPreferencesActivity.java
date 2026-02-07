package com.radolyn.ayugram.preferences;

import android.content.SharedPreferences;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.system.Os;
import android.system.OsConstants;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.View;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.utils.ChatUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuState;
import com.radolyn.ayugram.controllers.AyuMessagesController;
import com.radolyn.ayugram.database.AyuData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.PushListenerController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import p017j$.util.List;
import p017j$.util.Map;

/* loaded from: classes4.dex */
public class DebugPreferencesActivity extends BasePreferencesActivity {
    private final SparseIntArray tidRowMap = new SparseIntArray();
    private final SparseArray threadNames = new SparseArray();
    private final SparseLongArray lastTidCpu = new SparseLongArray();
    private final HashMap tidCpuPercent = new HashMap();
    private double currentRamMb = 0.0d;
    private final long ticksPerSecond = getTicksPerSecond();
    private final Handler statsHandler = new Handler(Looper.getMainLooper());
    private final Runnable statsRunnable = new Runnable() { // from class: com.radolyn.ayugram.preferences.DebugPreferencesActivity.1
        @Override // java.lang.Runnable
        public void run() throws IOException {
            DebugPreferencesActivity.this.refreshStats();
            DebugPreferencesActivity.this.statsHandler.postDelayed(this, 1000L);
        }
    };
    private double averageGetFilePathTime = 0.0d;

    private enum ItemId {
        HEADER_GENERAL,
        FORCE_SHOW_DOWNLOAD,
        PROBE_OTHER_ACCOUNTS,
        DISABLE_HOOK,
        SHADOW_BUTTONS,
        HEADER_DEBUG,
        WAL_MODE,
        SHOW_SCREENSHOT,
        SHADOW_COUNTERS,
        GOOGLE_AVAILABLE,
        FCM_TOKEN,
        PUSH_STATUS,
        PUSHES_RECEIVED,
        GET_FILE_PATH_AVG,
        RESET_ALERTS,
        SHADOW_DEBUG,
        HEADER_STATE,
        DELETED_MESSAGES_COUNT,
        DELETED_DIALOGS_COUNT,
        LAST_SEEN_COUNT,
        LAST_MESSAGES_COUNT,
        SHADOW_STATE,
        HEADER_STATS,
        MEMORY_USAGE;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() throws NumberFormatException, IOException {
        super.onFragmentCreate();
        initThreadRows();
        return true;
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        this.statsHandler.post(this.statsRunnable);
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(false);
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public void onPause() {
        this.statsHandler.removeCallbacks(this.statsRunnable);
        super.onPause();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307093366013478L);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.General)));
        arrayList.add(UItem.asCheck(ItemId.FORCE_SHOW_DOWNLOAD.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307106250915366L)).setChecked(AyuConfig.forceShowDownloadButtons));
        arrayList.add(UItem.asCheck(ItemId.PROBE_OTHER_ACCOUNTS.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307226509999654L)).setChecked(AyuConfig.probeUsingOtherAccounts));
        arrayList.add(UItem.asCheck(ItemId.DISABLE_HOOK.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307368243920422L)).setChecked(AyuConfig.disableHook));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.SettingsDebug)));
        arrayList.add(UItem.asCheck(ItemId.WAL_MODE.getId(), LocaleController.getString(C2369R.string.WALMode)).setChecked(AyuConfig.WALMode));
        arrayList.add(UItem.asCheck(ItemId.SHOW_SCREENSHOT.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307424078495270L)).setChecked(AyuConfig.showScreenshot));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asButton(ItemId.GOOGLE_AVAILABLE.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307509977841190L), Deobfuscator$AyuGram4A$TMessagesProj.getString(PushListenerController.GooglePushListenerServiceProvider.INSTANCE.hasServices() ? -2019307570107383334L : -2019307587287252518L)));
        arrayList.add(UItem.asButton(ItemId.FCM_TOKEN.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307600172154406L), SharedConfig.pushString));
        arrayList.add(UItem.asButton(ItemId.PUSH_STATUS.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307643121827366L), SharedConfig.pushStringStatus));
        arrayList.add(UItem.asButton(ItemId.PUSHES_RECEIVED.getId(), LocaleController.getString(C2369R.string.PushNotificationCount), String.valueOf(AyuState.getFcmCounter())));
        arrayList.add(UItem.asButton(ItemId.GET_FILE_PATH_AVG.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307694661434918L), this.averageGetFilePathTime > 0.0d ? this.averageGetFilePathTime + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307763380911654L) : Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307780560780838L)));
        arrayList.add(UItem.asButton(ItemId.RESET_ALERTS.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307797740650022L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307853575224870L)));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307879345028646L)));
        arrayList.add(UItem.asButton(ItemId.DELETED_MESSAGES_COUNT.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019307943769538086L), String.valueOf(AyuData.getDeletedMessageDao().getDeletedCount())));
        arrayList.add(UItem.asButton(ItemId.DELETED_DIALOGS_COUNT.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308016783982118L), String.valueOf(AyuData.getDeletedDialogDao().getDeletedCount())));
        arrayList.add(UItem.asButton(ItemId.LAST_SEEN_COUNT.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308085503458854L), String.valueOf(AyuData.getSpyDao().getLastSeenCount())));
        int lastMessagesCount = 0;
        for (int i = 0; i < 16; i++) {
            lastMessagesCount += AyuMessagesController.getInstance(i).getLastMessagesCount();
        }
        arrayList.add(UItem.asButton(ItemId.LAST_MESSAGES_COUNT.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308128453131814L), String.valueOf(lastMessagesCount)));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308188582673958L)));
        arrayList.add(UItem.asButton(ItemId.MEMORY_USAGE.getId(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308214352477734L), String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308231532346918L), Double.valueOf(this.currentRamMb))));
        ArrayList arrayList2 = new ArrayList(this.tidCpuPercent.keySet());
        List.EL.sort(arrayList2, new Comparator() { // from class: com.radolyn.ayugram.preferences.DebugPreferencesActivity$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.lambda$fillItems$0((Integer) obj, (Integer) obj2);
            }
        });
        int iMin = Math.min(arrayList2.size(), 20);
        for (int i2 = 0; i2 < iMin; i2++) {
            Integer num = (Integer) arrayList2.get(i2);
            int iIntValue = num.intValue();
            String str = (String) this.threadNames.get(iIntValue, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308265892085286L) + iIntValue);
            Double d = (Double) Map.EL.getOrDefault(this.tidCpuPercent, num, Double.valueOf(0.0d));
            d.doubleValue();
            arrayList.add(UItem.asButton(50000 + i2, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308287366921766L) + str, String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308308841758246L), d)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$fillItems$0(Integer num, Integer num2) {
        HashMap map = this.tidCpuPercent;
        Double dValueOf = Double.valueOf(0.0d);
        return Double.compare(((Double) Map.EL.getOrDefault(map, num2, dValueOf)).doubleValue(), ((Double) Map.EL.getOrDefault(this.tidCpuPercent, num, dValueOf)).doubleValue());
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) {
            return;
        }
        if (uItem.f2017id == ItemId.FORCE_SHOW_DOWNLOAD.getId()) {
            SharedPreferences.Editor editor = AyuConfig.editor;
            String string = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308343201496614L);
            boolean z = !AyuConfig.forceShowDownloadButtons;
            AyuConfig.forceShowDownloadButtons = z;
            editor.putBoolean(string, z).apply();
        } else if (uItem.f2017id == ItemId.PROBE_OTHER_ACCOUNTS.getId()) {
            SharedPreferences.Editor editor2 = AyuConfig.editor;
            String string2 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308450575679014L);
            boolean z2 = !AyuConfig.probeUsingOtherAccounts;
            AyuConfig.probeUsingOtherAccounts = z2;
            editor2.putBoolean(string2, z2).apply();
        } else if (uItem.f2017id == ItemId.DISABLE_HOOK.getId()) {
            SharedPreferences.Editor editor3 = AyuConfig.editor;
            String string3 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308553654894118L);
            boolean z3 = !AyuConfig.disableHook;
            AyuConfig.disableHook = z3;
            editor3.putBoolean(string3, z3).apply();
        } else if (uItem.f2017id == ItemId.WAL_MODE.getId()) {
            SharedPreferences.Editor editor4 = AyuConfig.editor;
            String string4 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308605194501670L);
            boolean z4 = !AyuConfig.WALMode;
            AyuConfig.WALMode = z4;
            editor4.putBoolean(string4, z4).apply();
        } else if (uItem.f2017id == ItemId.SHOW_SCREENSHOT.getId()) {
            SharedPreferences.Editor editor5 = AyuConfig.editor;
            String string5 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308639554240038L);
            boolean z5 = !AyuConfig.showScreenshot;
            AyuConfig.showScreenshot = z5;
            editor5.putBoolean(string5, z5).apply();
        } else if (uItem.f2017id == ItemId.FCM_TOKEN.getId()) {
            AndroidUtilities.addToClipboard(SharedConfig.pushString);
        } else if (uItem.f2017id == ItemId.PUSH_STATUS.getId()) {
            AndroidUtilities.addToClipboard(SharedConfig.pushStringStatus);
        } else if (uItem.f2017id == ItemId.GET_FILE_PATH_AVG.getId()) {
            this.averageGetFilePathTime = 0.0d;
            setAverageGetFilePathTime();
        } else if (uItem.f2017id == ItemId.RESET_ALERTS.getId()) {
            SharedPreferences.Editor editor6 = AyuConfig.editor;
            String string6 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308703978749478L);
            AyuConfig.sawFirstLaunchAlert = false;
            editor6.putBoolean(string6, false).apply();
            SharedPreferences.Editor editor7 = AyuConfig.editor;
            String string7 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308789878095398L);
            AyuConfig.sawExteraChatsAlert = false;
            editor7.putBoolean(string7, false).apply();
            SharedPreferences.Editor editor8 = AyuConfig.editor;
            String string8 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308875777441318L);
            AyuConfig.sawLocalPremiumAlert = false;
            editor8.putBoolean(string8, false).apply();
            SharedPreferences.Editor editor9 = AyuConfig.editor;
            String string9 = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019308965971754534L);
            AyuConfig.sawSaveAttachmentsAlert = false;
            editor9.putBoolean(string9, false).apply();
            BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.contact_check, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309069050969638L)).show();
        }
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    private void setAverageGetFilePathTime() {
        ChatUtils.utilsQueue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.preferences.DebugPreferencesActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setAverageGetFilePathTime$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAverageGetFilePathTime$2() {
        FileLoader fileLoader = FileLoader.getInstance(UserConfig.selectedAccount);
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            fileLoader.getFileDatabase().getPath(5469733571210003142L, 2, 3, true);
        }
        final double dCurrentTimeMillis = (System.currentTimeMillis() - jCurrentTimeMillis) / 100.0d;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.preferences.DebugPreferencesActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setAverageGetFilePathTime$1(dCurrentTimeMillis);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAverageGetFilePathTime$1(double d) {
        this.averageGetFilePathTime = d;
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    private void initThreadRows() throws NumberFormatException, IOException {
        BufferedReader bufferedReader;
        try {
            File[] fileArrListFiles = new File(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309094820773414L)).listFiles();
            if (fileArrListFiles == null) {
                return;
            }
            for (File file : fileArrListFiles) {
                int i = Integer.parseInt(file.getName());
                String strTrim = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309163540250150L) + i;
                try {
                    bufferedReader = new BufferedReader(new FileReader(new File(file, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309185015086630L))));
                } catch (Exception unused) {
                }
                try {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        strTrim = line.trim();
                    }
                    bufferedReader.close();
                    this.threadNames.put(i, strTrim);
                    this.lastTidCpu.put(i, getThreadCpuTicks(i));
                    this.tidCpuPercent.put(Integer.valueOf(i), Double.valueOf(0.0d));
                } catch (Throwable th) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshStats() throws IOException {
        Debug.getMemoryInfo(new Debug.MemoryInfo());
        this.currentRamMb = r0.getTotalPss() / 1024.0d;
        for (int i = 0; i < this.threadNames.size(); i++) {
            int iKeyAt = this.threadNames.keyAt(i);
            long threadCpuTicks = getThreadCpuTicks(iKeyAt);
            long j = threadCpuTicks - this.lastTidCpu.get(iKeyAt, threadCpuTicks);
            this.lastTidCpu.put(iKeyAt, threadCpuTicks);
            long j2 = this.ticksPerSecond;
            this.tidCpuPercent.put(Integer.valueOf(iKeyAt), Double.valueOf(j2 > 0 ? (j * 100.0d) / j2 : 0.0d));
        }
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(false);
        }
    }

    private long getThreadCpuTicks(int i) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309206489923110L) + i + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309279504367142L)));
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    String[] strArrSplit = line.split(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309305274170918L));
                    long j = Long.parseLong(strArrSplit[13]) + Long.parseLong(strArrSplit[14]);
                    bufferedReader.close();
                    return j;
                }
                bufferedReader.close();
                return 0L;
            } finally {
            }
        } catch (Exception unused) {
            return 0L;
        }
    }

    private long getTicksPerSecond() {
        try {
            return Os.sysconf(OsConstants._SC_CLK_TCK);
        } catch (Exception unused) {
            return 100L;
        }
    }
}
