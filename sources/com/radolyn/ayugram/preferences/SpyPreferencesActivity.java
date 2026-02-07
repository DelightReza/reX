package com.radolyn.ayugram.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuConstants;
import com.radolyn.ayugram.AyuInfra;
import com.radolyn.ayugram.database.AyuData;
import com.radolyn.ayugram.preferences.utils.ClearBottomSheet;
import com.radolyn.ayugram.preferences.utils.DatabaseImportExportBottomSheet;
import com.radolyn.ayugram.utils.android.AndroidPickerUtils;
import com.radolyn.ayugram.utils.android.StorageUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.HeaderCell;
import org.telegram.p023ui.Cells.MaxFileSizeCell;
import org.telegram.p023ui.Cells.TextCheckBoxCell;
import org.telegram.p023ui.Cells.TextCheckCell;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes.dex */
public class SpyPreferencesActivity extends BasePreferencesActivity {
    private HintView2 attachmentsHint;
    private long attachmentsSize;
    private long initialAttachmentsMaxSize;
    private long totalDeviceSize = -1;

    private enum ItemId {
        HEADER_ESSENTIALS,
        SAVE_DELETED,
        SAVE_HISTORY,
        SAVE_FOR_BOTS,
        HEADER_READ,
        SAVE_READ_DATE,
        READ_DESC,
        HEADER_LOCAL_ONLINE,
        SAVE_LOCAL_ONLINE,
        LOCAL_ONLINE_DESC,
        HEADER_MEDIA,
        SAVE_MEDIA,
        SAVE_PATH,
        HEADER_MAXSIZE,
        MAXSIZE_SLIDE,
        MAXSIZE_DESC,
        EXPORT_DB,
        IMPORT_DB,
        CLEAR;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        View viewCreateView = super.createView(context);
        HintView2 rounding = new HintView2(getContext(), 1).setMultilineText(true).setTextAlign(Layout.Alignment.ALIGN_NORMAL).setDuration(-1L).setHideByTouch(true).useScale(true).setCloseButton(false).setRounding(8.0f);
        this.attachmentsHint = rounding;
        rounding.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.MessageSavingSaveMediaHintPopup)));
        HintView2 hintView2 = this.attachmentsHint;
        hintView2.setMaxWidthPx(HintView2.cutInFancyHalf(hintView2.getText(), this.attachmentsHint.getTextPaint()));
        ((FrameLayout) viewCreateView).addView(this.attachmentsHint, LayoutHelper.createFrame(-1, 120.0f, 55, 16.0f, 0.0f, 0.0f, 0.0f));
        if (!AyuConfig.sawSaveAttachmentsAlert) {
            this.listView.addOnLayoutChangeListener(new ViewOnLayoutChangeListenerC14851(viewCreateView));
        }
        this.listView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (SpyPreferencesActivity.this.attachmentsHint == null || !SpyPreferencesActivity.this.attachmentsHint.isShown()) {
                    return;
                }
                SpyPreferencesActivity.this.attachmentsHint.setTranslationY(Math.max(0.0f, SpyPreferencesActivity.this.attachmentsHint.getTranslationY() - i2));
                SpyPreferencesActivity.this.hideAttachmentHint();
            }
        });
        calculateAttachmentsSize();
        return viewCreateView;
    }

    /* renamed from: com.radolyn.ayugram.preferences.SpyPreferencesActivity$1 */
    /* loaded from: classes4.dex */
    class ViewOnLayoutChangeListenerC14851 implements View.OnLayoutChangeListener {
        final /* synthetic */ View val$fragmentView;

        ViewOnLayoutChangeListenerC14851(View view) {
            this.val$fragmentView = view;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            ((BasePreferencesActivity) SpyPreferencesActivity.this).listView.removeOnLayoutChangeListener(this);
            final TextCheckCell textCheckCell = (TextCheckCell) ((BasePreferencesActivity) SpyPreferencesActivity.this).listView.findViewByItemId(ItemId.SAVE_MEDIA.getId());
            final View view2 = this.val$fragmentView;
            textCheckCell.post(new Runnable() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLayoutChange$0(textCheckCell, view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLayoutChange$0(TextCheckCell textCheckCell, View view) {
            int[] iArr = new int[2];
            textCheckCell.getValueTextView().getLocationInWindow(iArr);
            if (AndroidUtilities.isTablet()) {
                ViewGroup view2 = SpyPreferencesActivity.this.getParentLayout().getView();
                iArr[0] = iArr[0] + ((int) (view2.getX() + view2.getPaddingLeft()));
                iArr[1] = iArr[1] - ((int) (view2.getY() + view2.getPaddingTop()));
            }
            SpyPreferencesActivity.this.attachmentsHint.setTranslationY((iArr[1] + r8.getHeight()) - view.getTop());
            SpyPreferencesActivity.this.attachmentsHint.setJointPx(0.0f, (r8.getWidth() / 2.0f) - r8.getPaddingLeft());
            SpyPreferencesActivity.this.attachmentsHint.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateAttachmentsSize() {
        this.attachmentsSize = 0L;
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$calculateAttachmentsSize$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$calculateAttachmentsSize$0() {
        this.attachmentsSize = Utilities.getDirSize(AyuConfig.getSavePathJava().getAbsolutePath(), 0, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAttachmentHint() {
        if (this.attachmentsHint.shown()) {
            this.attachmentsHint.hide();
            SharedPreferences.Editor editor = AyuConfig.editor;
            boolean z = !AyuConfig.sawSaveAttachmentsAlert;
            AyuConfig.sawSaveAttachmentsAlert = z;
            editor.putBoolean("sawSaveAttachmentsAlert", z).apply();
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        hideAttachmentHint();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        this.initialAttachmentsMaxSize = AyuConfig.saveMediaMaxCacheSize;
        ArrayList<File> rootDirs = AndroidUtilities.getRootDirs();
        int i = 0;
        File file = rootDirs.get(0);
        file.getAbsolutePath();
        if (!TextUtils.isEmpty(SharedConfig.storageCacheDir)) {
            int size = rootDirs.size();
            while (true) {
                if (i < size) {
                    File file2 = rootDirs.get(i);
                    if (file2.getAbsolutePath().startsWith(SharedConfig.storageCacheDir)) {
                        file = file2;
                        break;
                    }
                    i++;
                }
            }
        }
        try {
            StatFs statFs = new StatFs(file.getPath());
            this.totalDeviceSize = statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() throws IOException {
        if (this.initialAttachmentsMaxSize != AyuConfig.saveMediaMaxCacheSize) {
            AyuConfig.preferences.edit().putInt("last_attachments_tidyup", 0).apply();
            AyuData.tidyUpAttachments();
        }
        super.onFragmentDestroy();
        hideAttachmentHint();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.SpyEssentialsHeader)));
        arrayList.add(UItem.asCheck(ItemId.SAVE_DELETED.getId(), LocaleController.getString(C2369R.string.SaveDeletedMessages)).setChecked(AyuConfig.saveDeletedMessages).setSearchable(this).setLinkAlias("saveDeletedMessages", this));
        arrayList.add(UItem.asCheck(ItemId.SAVE_HISTORY.getId(), LocaleController.getString(C2369R.string.SaveMessagesHistory)).setChecked(AyuConfig.saveMessagesHistory).setSearchable(this).setLinkAlias("saveMessagesHistory", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asCheck(ItemId.SAVE_FOR_BOTS.getId(), LocaleController.getString(C2369R.string.MessageSavingSaveForBots)).setChecked(AyuConfig.saveForBots).setSearchable(this).setLinkAlias("saveForBots", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asCheck(ItemId.SAVE_READ_DATE.getId(), LocaleController.getString(C2369R.string.SpySaveReadMarks)).setChecked(AyuConfig.saveReadDate).setSearchable(this).setLinkAlias("saveReadDate", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.SpySaveReadMarksDescription)));
        arrayList.add(UItem.asCheck(ItemId.SAVE_LOCAL_ONLINE.getId(), LocaleController.getString(C2369R.string.SpySaveLocalOnline)).setChecked(AyuConfig.saveLocalOnline).setSearchable(this).setLinkAlias("saveLocalOnline", this));
        arrayList.add(UItem.asShadow(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.SpySaveLocalOnlineDescription))));
        arrayList.add(UItem.asCheck(ItemId.SAVE_MEDIA.getId(), LocaleController.getString(C2369R.string.MessageSavingSaveMedia), LocaleController.getString(C2369R.string.MessageSavingSaveMediaHint), false).setChecked(AyuConfig.saveMedia).setSearchable(this).setLinkAlias("saveAttachments", this));
        arrayList.add(UItem.asButton(ItemId.SAVE_PATH.getId(), LocaleController.getString(C2369R.string.MessageSavingSavePath), AyuConfig.getSavePathFolder()));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.AttachmentsFolderMaxSizeHeader)));
        arrayList.add(UItem.asSlideView(ItemId.MAXSIZE_SLIDE.getId(), getMaxSizeOptionsLabels(), getInitialMaxSizeIndex(), new Utilities.Callback() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$fillItems$1((Integer) obj);
            }
        }).setText(LocaleController.getString(C2369R.string.AttachmentsFolderMaxSizeHeader)).setSearchable(this).setLinkAlias("saveMediaMaxCacheSize", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.AttachmentsFolderMaxSizeDescription)));
        arrayList.add(UItem.asButton(ItemId.EXPORT_DB.getId(), C2369R.drawable.msg_unarchive, LocaleController.getString(C2369R.string.ExportDatabaseButton)).setSearchable(this).setLinkAlias("exportDatabase", this));
        arrayList.add(UItem.asButton(ItemId.IMPORT_DB.getId(), C2369R.drawable.msg_archive, LocaleController.getString(C2369R.string.ImportDatabaseButton)).setSearchable(this).setLinkAlias("importDatabase", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asButton(ItemId.CLEAR.getId(), C2369R.drawable.msg_clear, LocaleController.getString(C2369R.string.Clear)).setSearchable(this).setLinkAlias("clearDatabase", this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$1(Integer num) {
        int i = getMaxSizeOptionsValues()[num.intValue()];
        AyuConfig.saveMediaMaxCacheSize = i;
        AyuConfig.editor.putInt("saveMediaMaxCacheSize", i).apply();
    }

    private String[] getMaxSizeOptionsLabels() {
        ArrayList options = getOptions();
        String[] strArr = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            if (((Integer) options.get(i)).intValue() == AyuConstants.MAX_CACHE_SIZE_300_MB) {
                strArr[i] = "300 MB";
            } else if (((Integer) options.get(i)).intValue() == Integer.MAX_VALUE) {
                strArr[i] = LocaleController.getString(C2369R.string.NoLimit);
            } else {
                strArr[i] = options.get(i) + " GB";
            }
        }
        return strArr;
    }

    private int[] getMaxSizeOptionsValues() {
        ArrayList options = getOptions();
        int[] iArr = new int[options.size()];
        for (int i = 0; i < options.size(); i++) {
            iArr[i] = ((Integer) options.get(i)).intValue();
        }
        return iArr;
    }

    private int getInitialMaxSizeIndex() {
        int iIndexOf = getOptions().indexOf(Integer.valueOf(AyuConfig.saveMediaMaxCacheSize));
        return iIndexOf < 0 ? r0.size() - 1 : iIndexOf;
    }

    private ArrayList getOptions() {
        float f = ((int) ((this.totalDeviceSize / 1024) / 1024)) / 1000.0f;
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(AyuConstants.MAX_CACHE_SIZE_300_MB));
        arrayList.add(1);
        arrayList.add(2);
        if (f > 5.0f) {
            arrayList.add(5);
        }
        if (f > 16.0f) {
            arrayList.add(16);
        }
        arrayList.add(Integer.valueOf(ConnectionsManager.DEFAULT_DATACENTER_ID));
        return arrayList;
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) {
            return;
        }
        if (uItem.f2017id == ItemId.SAVE_DELETED.getId()) {
            SharedPreferences.Editor editor = AyuConfig.editor;
            boolean z = !AyuConfig.saveDeletedMessages;
            AyuConfig.saveDeletedMessages = z;
            editor.putBoolean("saveDeletedMessages", z).apply();
        } else if (uItem.f2017id == ItemId.SAVE_HISTORY.getId()) {
            SharedPreferences.Editor editor2 = AyuConfig.editor;
            boolean z2 = !AyuConfig.saveMessagesHistory;
            AyuConfig.saveMessagesHistory = z2;
            editor2.putBoolean("saveMessagesHistory", z2).apply();
        } else if (uItem.f2017id == ItemId.SAVE_FOR_BOTS.getId()) {
            SharedPreferences.Editor editor3 = AyuConfig.editor;
            boolean z3 = !AyuConfig.saveForBots;
            AyuConfig.saveForBots = z3;
            editor3.putBoolean("saveForBots", z3).apply();
        } else if (uItem.f2017id == ItemId.SAVE_READ_DATE.getId()) {
            SharedPreferences.Editor editor4 = AyuConfig.editor;
            boolean z4 = !AyuConfig.saveReadDate;
            AyuConfig.saveReadDate = z4;
            editor4.putBoolean("saveReadDate", z4).apply();
        } else if (uItem.f2017id == ItemId.SAVE_LOCAL_ONLINE.getId()) {
            SharedPreferences.Editor editor5 = AyuConfig.editor;
            boolean z5 = !AyuConfig.saveLocalOnline;
            AyuConfig.saveLocalOnline = z5;
            editor5.putBoolean("saveLocalOnline", z5).apply();
        } else if (uItem.f2017id == ItemId.SAVE_MEDIA.getId()) {
            TextCheckCell textCheckCell = (TextCheckCell) view;
            if ((LocaleController.isRTL && f <= AndroidUtilities.m1146dp(76.0f)) || (!LocaleController.isRTL && f >= view.getMeasuredWidth() - AndroidUtilities.m1146dp(76.0f))) {
                SharedPreferences.Editor editor6 = AyuConfig.editor;
                boolean z6 = !AyuConfig.saveMedia;
                AyuConfig.saveMedia = z6;
                editor6.putBoolean("saveMedia", z6).apply();
                textCheckCell.setChecked(AyuConfig.saveMedia);
            } else if (AyuConfig.saveMedia) {
                showMediaSavingBottomSheet();
            }
        } else if (uItem.f2017id == ItemId.SAVE_PATH.getId()) {
            if (!StorageUtils.ensureHasPermissions(getParentActivity())) {
                return;
            }
            Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
            intent.addCategory("android.intent.category.DEFAULT");
            startActivityForResult(Intent.createChooser(intent, "Choose directory"), 6969);
        } else {
            int i2 = uItem.f2017id;
            ItemId itemId = ItemId.EXPORT_DB;
            if (i2 == itemId.getId() || uItem.f2017id == ItemId.IMPORT_DB.getId()) {
                new DatabaseImportExportBottomSheet(this, uItem.f2017id == itemId.getId()).showIfPossible();
            } else if (uItem.f2017id == ItemId.CLEAR.getId()) {
                showClearDialog();
            }
        }
        this.listView.adapter.update(true);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        super.onActivityResultFragment(i, i2, intent);
        if (i == 6969 && i2 == -1) {
            try {
                Uri data = intent.getData();
                AyuConfig.editor.putString("savePath", AndroidPickerUtils.getPath(getParentActivity(), DocumentsContract.buildDocumentUriUsingTree(data, DocumentsContract.getTreeDocumentId(data)))).apply();
                AyuInfra.initializeAttachmentsFolder();
                calculateAttachmentsSize();
                this.listView.adapter.update(true);
            } catch (Exception e) {
                if (ExteraConfig.useGoogleCrashlytics) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                }
            }
        }
    }

    private void showMediaSavingBottomSheet() {
        if (getParentActivity() == null) {
            return;
        }
        hideAttachmentHint();
        final BottomSheet.Builder builder = new BottomSheet.Builder(getParentActivity());
        builder.setApplyTopPadding(false);
        builder.setApplyBottomPadding(false);
        LinearLayout linearLayout = new LinearLayout(getParentActivity());
        linearLayout.setOrientation(1);
        builder.setCustomView(linearLayout);
        HeaderCell headerCell = new HeaderCell(getParentActivity(), Theme.key_dialogTextBlue2, 21, 15, false);
        headerCell.setText(LocaleController.getString(C2369R.string.MessageSavingSaveMedia).toUpperCase());
        linearLayout.addView(headerCell, LayoutHelper.createFrame(-1, -2.0f));
        final TextCheckBoxCell[] textCheckBoxCellArr = new TextCheckBoxCell[5];
        for (int i = 0; i < 5; i++) {
            final TextCheckBoxCell textCheckBoxCell = new TextCheckBoxCell(getParentActivity(), true, false);
            textCheckBoxCellArr[i] = textCheckBoxCell;
            if (i == 0) {
                textCheckBoxCell.setTextAndCheck(LocaleController.getString(C2369R.string.MessageSavingSaveMediaInPrivateChats), AyuConfig.saveMediaInPrivateChats, true);
            } else if (i == 1) {
                textCheckBoxCell.setTextAndCheck(LocaleController.getString(C2369R.string.MessageSavingSaveMediaInPublicChannels), AyuConfig.saveMediaInPublicChannels, true);
            } else if (i == 2) {
                textCheckBoxCell.setTextAndCheck(LocaleController.getString(C2369R.string.MessageSavingSaveMediaInPrivateChannels), AyuConfig.saveMediaInPrivateChannels, true);
            } else if (i == 3) {
                textCheckBoxCell.setTextAndCheck(LocaleController.getString(C2369R.string.MessageSavingSaveMediaInPublicGroups), AyuConfig.saveMediaInPublicGroups, true);
            } else {
                textCheckBoxCell.setTextAndCheck(LocaleController.getString(C2369R.string.MessageSavingSaveMediaInPrivateGroups), AyuConfig.saveMediaInPrivateGroups, false);
            }
            textCheckBoxCellArr[i].setBackgroundDrawable(Theme.getSelectorDrawable(false));
            textCheckBoxCellArr[i].setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SpyPreferencesActivity.$r8$lambda$picj2v5_OwVBId8YQJ2ztw3957c(textCheckBoxCell, view);
                }
            });
            linearLayout.addView(textCheckBoxCellArr[i], LayoutHelper.createFrame(-1, 50.0f));
        }
        if (ExteraConfig.dividerType != 0) {
            View view = new View(getParentActivity());
            view.setBackgroundColor(Theme.getColor(Theme.key_divider));
            linearLayout.addView(view, new LinearLayout.LayoutParams(-1, 1));
        }
        final MaxFileSizeCell[] maxFileSizeCellArr = new MaxFileSizeCell[2];
        for (int i2 = 0; i2 < 2; i2++) {
            MaxFileSizeCell maxFileSizeCell = new MaxFileSizeCell(getParentActivity());
            maxFileSizeCellArr[i2] = maxFileSizeCell;
            if (i2 == 0) {
                maxFileSizeCell.setText(LocaleController.getString(C2369R.string.MaximumMediaSizeCellular));
                maxFileSizeCell.setSize(AyuConfig.saveMediaOnCellularDataLimit);
            } else {
                maxFileSizeCell.setText(LocaleController.getString(C2369R.string.MaximumMediaSizeWiFi));
                maxFileSizeCell.setSize(AyuConfig.saveMediaOnWiFiLimit);
            }
            linearLayout.addView(maxFileSizeCellArr[i2], LayoutHelper.createFrame(-1, 50.0f));
        }
        FrameLayout frameLayout = new FrameLayout(getParentActivity());
        frameLayout.setPadding(AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(8.0f));
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, 52));
        TextView textView = new TextView(getParentActivity());
        textView.setTextSize(1, 14.0f);
        int i3 = Theme.key_dialogTextBlue2;
        textView.setTextColor(Theme.getColor(i3));
        textView.setGravity(17);
        textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        textView.setText(LocaleController.getString(C2369R.string.Cancel).toUpperCase());
        textView.setPadding(AndroidUtilities.m1146dp(10.0f), 0, AndroidUtilities.m1146dp(10.0f), 0);
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, 36, 51));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                builder.getDismissRunnable().run();
            }
        });
        TextView textView2 = new TextView(getParentActivity());
        textView2.setTextSize(1, 14.0f);
        textView2.setTextColor(Theme.getColor(i3));
        textView2.setGravity(17);
        textView2.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        textView2.setText(LocaleController.getString(C2369R.string.Save).toUpperCase());
        textView2.setPadding(AndroidUtilities.m1146dp(10.0f), 0, AndroidUtilities.m1146dp(10.0f), 0);
        frameLayout.addView(textView2, LayoutHelper.createFrame(-2, 36, 53));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                SpyPreferencesActivity.$r8$lambda$FGz2TikJpmtcHVNmTRtFU0Y_6P4(textCheckBoxCellArr, maxFileSizeCellArr, builder, view2);
            }
        });
        showDialog(builder.create());
    }

    public static /* synthetic */ void $r8$lambda$picj2v5_OwVBId8YQJ2ztw3957c(TextCheckBoxCell textCheckBoxCell, View view) {
        if (view.isEnabled()) {
            textCheckBoxCell.setChecked(!textCheckBoxCell.isChecked());
        }
    }

    public static /* synthetic */ void $r8$lambda$FGz2TikJpmtcHVNmTRtFU0Y_6P4(TextCheckBoxCell[] textCheckBoxCellArr, MaxFileSizeCell[] maxFileSizeCellArr, BottomSheet.Builder builder, View view) {
        AyuConfig.saveMediaInPrivateChats = textCheckBoxCellArr[0].isChecked();
        AyuConfig.saveMediaInPublicChannels = textCheckBoxCellArr[1].isChecked();
        AyuConfig.saveMediaInPrivateChannels = textCheckBoxCellArr[2].isChecked();
        AyuConfig.saveMediaInPublicGroups = textCheckBoxCellArr[3].isChecked();
        AyuConfig.saveMediaInPrivateGroups = textCheckBoxCellArr[4].isChecked();
        AyuConfig.saveMediaOnCellularDataLimit = maxFileSizeCellArr[0].getSize();
        AyuConfig.saveMediaOnWiFiLimit = maxFileSizeCellArr[1].getSize();
        AyuConfig.editor.putBoolean("saveMediaInPrivateChats", AyuConfig.saveMediaInPrivateChats).apply();
        AyuConfig.editor.putBoolean("saveMediaInPublicChannels", AyuConfig.saveMediaInPublicChannels).apply();
        AyuConfig.editor.putBoolean("saveMediaInPrivateChannels", AyuConfig.saveMediaInPrivateChannels).apply();
        AyuConfig.editor.putBoolean("saveMediaInPublicGroups", AyuConfig.saveMediaInPublicGroups).apply();
        AyuConfig.editor.putBoolean("saveMediaInPrivateGroups", AyuConfig.saveMediaInPrivateGroups).apply();
        AyuConfig.editor.putLong("saveMediaOnCellularDataLimit", AyuConfig.saveMediaOnCellularDataLimit).apply();
        AyuConfig.editor.putLong("saveMediaOnWiFiLimit", AyuConfig.saveMediaOnWiFiLimit).apply();
        builder.getDismissRunnable().run();
    }

    private void showClearDialog() {
        new ClearBottomSheet(this, this.attachmentsSize, new Runnable() { // from class: com.radolyn.ayugram.preferences.SpyPreferencesActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.calculateAttachmentsSize();
            }
        }).show();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.CategorySpy);
    }
}
