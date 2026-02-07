package com.radolyn.ayugram.preferences.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuUtils;
import com.radolyn.ayugram.controllers.AyuMessagesController;
import com.radolyn.ayugram.database.AyuData;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.CacheControlActivity;
import org.telegram.p023ui.Cells.CheckBoxCell;
import org.telegram.p023ui.Cells.TextInfoPrivacyCell;
import org.telegram.p023ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CombinedDrawable;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.LaunchActivity;

/* loaded from: classes4.dex */
public class ClearBottomSheet extends BottomSheetWithRecyclerListView {
    private final long attachmentsSize;
    private CacheControlActivity.ClearCacheButton button;
    CheckBoxCell[] checkBoxes;
    private final BaseFragment fragment;
    LinearLayout linearLayout;
    private final Runnable recalculateAttachments;

    @Override // org.telegram.p023ui.Components.BottomSheetWithRecyclerListView, org.telegram.p023ui.ActionBar.BottomSheet
    protected boolean canDismissWithSwipe() {
        return true;
    }

    @Override // org.telegram.p023ui.ActionBar.BottomSheet
    protected boolean canDismissWithTouchOutside() {
        return true;
    }

    private long getCurrentSize() {
        long j = this.checkBoxes[0].isChecked() ? this.attachmentsSize : 0L;
        return this.checkBoxes[1].isChecked() ? j + AyuData.getAyuDatabaseSize() : j;
    }

    private void setButtonSize() {
        if (this.button != null) {
            long currentSize = getCurrentSize();
            this.button.setSize(true, currentSize);
            if (currentSize > 0 || !this.checkBoxes[2].isChecked()) {
                return;
            }
            this.button.setDisabled(false);
        }
    }

    @Override // org.telegram.p023ui.Components.BottomSheetWithRecyclerListView
    protected CharSequence getTitle() {
        return LocaleController.getString(C2369R.string.Clear);
    }

    @Override // org.telegram.p023ui.Components.BottomSheetWithRecyclerListView
    protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView recyclerListView) {
        return new RecyclerListView.SelectionAdapter() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet.1
            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return 1;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i) {
                return i;
            }

            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LinearLayout linearLayout;
                if (i == 0) {
                    linearLayout = ClearBottomSheet.this.linearLayout;
                } else {
                    TextInfoPrivacyCell textInfoPrivacyCell = new TextInfoPrivacyCell(viewGroup.getContext());
                    textInfoPrivacyCell.setFixedSize(12);
                    CombinedDrawable combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawableByKey(viewGroup.getContext(), C2369R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    combinedDrawable.setFullsize(true);
                    textInfoPrivacyCell.setBackgroundDrawable(combinedDrawable);
                    linearLayout = textInfoPrivacyCell;
                }
                return new RecyclerListView.Holder(linearLayout);
            }
        };
    }

    public ClearBottomSheet(BaseFragment baseFragment, long j, Runnable runnable) {
        String string;
        long ayuDatabaseSize;
        String fileSize;
        super(baseFragment, false, false, false, null);
        this.checkBoxes = new CheckBoxCell[3];
        this.fragment = baseFragment;
        this.attachmentsSize = j;
        this.recalculateAttachments = runnable;
        this.allowNestedScroll = false;
        updateTitle();
        setAllowNestedScroll(true);
        this.topPadding = 0.2f;
        Context context = baseFragment.getContext();
        fixNavigationBar();
        setApplyBottomPadding(false);
        LinearLayout linearLayout = new LinearLayout(context);
        this.linearLayout = linearLayout;
        linearLayout.setOrientation(1);
        CheckBoxCell checkBoxCell = null;
        final int i = 0;
        for (int i2 = 3; i < i2; i2 = 3) {
            int i3 = Theme.key_radioBackgroundChecked;
            if (i == 0) {
                string = LocaleController.getString(C2369R.string.AyuAttachments);
                ayuDatabaseSize = j;
            } else if (i == 1) {
                string = LocaleController.getString(C2369R.string.AyuDatabase);
                ayuDatabaseSize = AyuData.getAyuDatabaseSize();
            } else {
                string = LocaleController.getString(C2369R.string.TelegramCacheDatabase);
                ayuDatabaseSize = 0;
            }
            CheckBoxCell checkBoxCell2 = new CheckBoxCell(context, 4, 21, null);
            checkBoxCell2.setTag(Integer.valueOf(i));
            checkBoxCell2.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.linearLayout.addView(checkBoxCell2, LayoutHelper.createLinear(-1, 50));
            if (ayuDatabaseSize == 0) {
                fileSize = i == 2 ? null : "…";
            } else {
                fileSize = AndroidUtilities.formatFileSize(ayuDatabaseSize);
            }
            checkBoxCell2.setText(string, fileSize, true, true);
            checkBoxCell2.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
            checkBoxCell2.setCheckBoxColor(i3, Theme.key_windowBackgroundWhiteGrayIcon, Theme.key_checkboxCheck);
            checkBoxCell2.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(i, view);
                }
            });
            checkBoxCell2.setChecked(AyuConfig.preferences.getBoolean("clearToggled_" + i, false), false);
            this.checkBoxes[i] = checkBoxCell2;
            i++;
            checkBoxCell = checkBoxCell2;
        }
        checkBoxCell.setNeedDivider(false);
        createButton();
        this.linearLayout.addView(this.button, LayoutHelper.createLinear(-1, 72, 80));
        setButtonSize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, View view) {
        CheckBoxCell checkBoxCell = (CheckBoxCell) view;
        checkBoxCell.setChecked(!checkBoxCell.isChecked(), true);
        AyuConfig.editor.putBoolean("clearToggled_" + i, checkBoxCell.isChecked());
        AyuConfig.editor.apply();
        setButtonSize();
    }

    @Override // org.telegram.p023ui.Components.BottomSheetWithRecyclerListView
    public void onViewCreated(FrameLayout frameLayout) {
        super.onViewCreated(frameLayout);
        this.recyclerListView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                ClearBottomSheet clearBottomSheet = ClearBottomSheet.this;
                if (clearBottomSheet.nestedSizeNotifierLayout != null) {
                    clearBottomSheet.setShowShadow(!r2.isPinnedToTop());
                }
            }
        });
        if (this.nestedSizeNotifierLayout != null) {
            createButton();
            frameLayout.addView(this.button, LayoutHelper.createFrame(-1, 72, 80));
        }
    }

    private void createButton() {
        CacheControlActivity.ClearCacheButton clearCacheButton = new CacheControlActivity.ClearCacheButton(getContext());
        this.button = clearCacheButton;
        clearCacheButton.button.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createButton$5(view);
            }
        });
        setButtonSize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createButton$5(View view) {
        lambda$new$0();
        AyuMessagesController.getInstance(UserConfig.selectedAccount).getExecutor().submit(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.lambda$createButton$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createButton$4() throws InterruptedException {
        if (this.checkBoxes[0].isChecked()) {
            clearAyuAttachments();
        }
        if (this.checkBoxes[1].isChecked()) {
            clearAyuDatabase();
        }
        if (this.checkBoxes[2].isChecked()) {
            clearLocalDatabase();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createButton$2();
                }
            });
        }
        if (this.checkBoxes[2].isChecked()) {
            return;
        }
        if (this.checkBoxes[0].isChecked()) {
            this.recalculateAttachments.run();
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createButton$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createButton$2() {
        BulletinFactory.m1267of(this.fragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.UtilityRestartRequired)).show();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AyuUtils.killApplication(LaunchActivity.instance);
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createButton$3() {
        BulletinFactory.m1267of(this.fragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.Done)).show();
    }

    private void clearAyuAttachments() {
        AyuMessagesController.clearAttachments();
    }

    private void clearAyuDatabase() {
        AyuMessagesController.clear();
    }

    private void clearLocalDatabase() throws InterruptedException {
        final MessagesStorage messagesStorage = MessagesStorage.getInstance(UserConfig.selectedAccount);
        messagesStorage.clearLocalDatabase();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        messagesStorage.getStorageQueue().postRunnable(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.ClearBottomSheet$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ClearBottomSheet.$r8$lambda$wF09MXPNUUSsqti710zQK9xXKWc(messagesStorage, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
    }

    public static /* synthetic */ void $r8$lambda$wF09MXPNUUSsqti710zQK9xXKWc(MessagesStorage messagesStorage, CountDownLatch countDownLatch) {
        try {
            messagesStorage.getDatabase().executeFast("DELETE FROM messages_v2").stepThis().dispose();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        try {
            messagesStorage.getDatabase().executeFast("DELETE FROM messages_topics").stepThis().dispose();
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
        try {
            messagesStorage.getDatabase().executeFast("DELETE FROM messages_holes").stepThis().dispose();
        } catch (Exception e3) {
            FileLog.m1160e(e3);
        }
        try {
            messagesStorage.getDatabase().executeFast("DELETE FROM messages_holes_topics").stepThis().dispose();
        } catch (Exception e4) {
            FileLog.m1160e(e4);
        }
        try {
            messagesStorage.getDatabase().executeFast("DELETE FROM dialogs").stepThis().dispose();
        } catch (Exception e5) {
            FileLog.m1160e(e5);
        }
        countDownLatch.countDown();
    }
}
