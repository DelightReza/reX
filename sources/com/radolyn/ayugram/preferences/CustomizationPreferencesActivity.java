package com.radolyn.ayugram.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.preferences.DrawerPreferencesActivity;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.p015ui.AlertUtils;
import com.radolyn.ayugram.preferences.components.DeletedMessagePreviewCell;
import com.radolyn.ayugram.utils.AyuMessageUtils;
import com.radolyn.ayugram.utils.fcm.CloudMessagingUtils;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.TextCheckCell;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Switch;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.PeerColorActivity;
import org.telegram.p023ui.Stories.recorder.HintView2;

/* loaded from: classes.dex */
public class CustomizationPreferencesActivity extends BasePreferencesActivity {
    private static final ArrayList deletedMarks = new ArrayList() { // from class: com.radolyn.ayugram.preferences.CustomizationPreferencesActivity.1
        {
            add(LocaleController.getString(C2369R.string.DeletedMarkNothing));
            add(LocaleController.getString(C2369R.string.DeletedMarkTrashBin));
            add(LocaleController.getString(C2369R.string.DeletedMarkCross));
            add(LocaleController.getString(C2369R.string.DeletedMarkEyeCrossed));
        }
    };
    private PeerColorActivity.PeerColorGrid colorPicker;
    private DeletedMessagesCell deletedMessagesCell;
    private HintView2 keepAliveServiceHint;

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        CharSequence charSequenceReplaceSingleTag;
        View viewCreateView = super.createView(context);
        FrameLayout frameLayout = (FrameLayout) viewCreateView;
        this.keepAliveServiceHint = new HintView2(getContext(), 1).setMultilineText(true).setTextAlign(Layout.Alignment.ALIGN_NORMAL).setDuration(-1L).setHideByTouch(true).useScale(true).setCloseButton(false).setRounding(8.0f);
        if (CloudMessagingUtils.spoofingNeeded()) {
            charSequenceReplaceSingleTag = "";
        } else {
            charSequenceReplaceSingleTag = AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.KeepAliveServiceNote), Theme.key_undo_cancelColor, 0, new Runnable() { // from class: com.radolyn.ayugram.preferences.CustomizationPreferencesActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$0();
                }
            });
        }
        this.keepAliveServiceHint.setText(charSequenceReplaceSingleTag);
        HintView2 hintView2 = this.keepAliveServiceHint;
        hintView2.setMaxWidthPx((int) HintView2.measureCorrectly(hintView2.getText(), this.keepAliveServiceHint.getTextPaint()));
        frameLayout.addView(this.keepAliveServiceHint, LayoutHelper.createFrame(-1, 120.0f, 55, 16.0f, 0.0f, 0.0f, 0.0f));
        this.listView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.radolyn.ayugram.preferences.CustomizationPreferencesActivity.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (CustomizationPreferencesActivity.this.keepAliveServiceHint == null || !CustomizationPreferencesActivity.this.keepAliveServiceHint.isShown()) {
                    return;
                }
                CustomizationPreferencesActivity.this.keepAliveServiceHint.setTranslationY(Math.max(0.0f, CustomizationPreferencesActivity.this.keepAliveServiceHint.getTranslationY() - i2));
                CustomizationPreferencesActivity.this.hideKeepAliveServiceHint();
            }
        });
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0() {
        hideKeepAliveServiceHint();
        getMessagesController().openByUserName(RemoteUtils.getStringConfigValue("fcm_channel", "ayugramfcm"), this, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideKeepAliveServiceHint() {
        if (this.keepAliveServiceHint.shown()) {
            this.keepAliveServiceHint.hide();
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        hideKeepAliveServiceHint();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        hideKeepAliveServiceHint();
    }

    private enum ItemId {
        MESSAGES_PREVIEW,
        SIMPLE_QUOTES,
        SEMI_TRANSPARENT,
        DELETED_ICON,
        DELETED_ICON_COLOR,
        HEADER_QOL,
        KEEP_ALIVE,
        LOCAL_PREMIUM,
        DISPLAY_GHOST_STATUS,
        DISABLE_ADS,
        DRAWER_SETTINGS;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.CategoryCustomization)));
        if (this.deletedMessagesCell == null) {
            this.deletedMessagesCell = new DeletedMessagesCell(getContext());
        }
        arrayList.add(UItem.asCustom(ItemId.MESSAGES_PREVIEW.getId(), this.deletedMessagesCell));
        arrayList.add(UItem.asCheck(ItemId.SIMPLE_QUOTES.getId(), LocaleController.getString(C2369R.string.SimpleQuotesAndReplies)).setChecked(AyuConfig.simpleQuotesAndReplies).setSearchable(this).setLinkAlias("disableColorfulReplies", this));
        arrayList.add(UItem.asCheck(ItemId.SEMI_TRANSPARENT.getId(), LocaleController.getString(C2369R.string.SemiTransparentDeletedMessages)).setChecked(AyuConfig.semiTransparentDeletedMessages).setSearchable(this).setLinkAlias("translucentDeletedMessages", this));
        Drawable deletedIconPreviewDrawable = AyuMessageUtils.getDeletedIconPreviewDrawable();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("\u200b");
        if (deletedIconPreviewDrawable != null) {
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(deletedIconPreviewDrawable);
            coloredImageSpan.setOverrideColor(Theme.getColor(Theme.key_chat_inTimeText));
            coloredImageSpan.setTranslateX(-AndroidUtilities.m1146dp(7.0f));
            spannableStringBuilder.setSpan(coloredImageSpan, 0, 1, 33);
        }
        arrayList.add(UItem.asButton(ItemId.DELETED_ICON.getId(), LocaleController.getString(C2369R.string.DeletedMarkText), spannableStringBuilder).setSearchable(this).setLinkAlias("deletedMark", this));
        if (AyuConfig.deletedIcon != 0) {
            if (this.colorPicker == null) {
                this.colorPicker = new PeerColorActivity.PeerColorGrid(getContext(), 10, this.currentAccount, this.resourceProvider);
                int color = Theme.getColor(Theme.key_chat_inTimeText);
                int[] iArr = AyuMessageUtils.deletedColors;
                this.colorPicker.setOverrideColors(new int[]{color, iArr[0], iArr[1], iArr[2], iArr[3], iArr[4], iArr[5], iArr[6]});
                this.colorPicker.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundWhite));
                this.colorPicker.setSelected(AyuConfig.deletedIconColor, false);
                this.colorPicker.setOnColorClick(new Utilities.Callback() { // from class: com.radolyn.ayugram.preferences.CustomizationPreferencesActivity$$ExternalSyntheticLambda2
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$fillItems$1((Integer) obj);
                    }
                });
                this.colorPicker.setDivider(false);
            }
            arrayList.add(UItem.asCustom(ItemId.DELETED_ICON_COLOR.getId(), this.colorPicker).setLinkAlias("deletedMarkColor", this));
        }
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asButtonWithSubtext(ItemId.DRAWER_SETTINGS.getId(), C2369R.drawable.msg_list, LocaleController.getString(C2369R.string.DrawerOptions), LocaleController.getString(C2369R.string.DrawerOptionsInfo), 64, 60).setSearchable(this).setLinkAlias("drawerSettings", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.QoLTogglesHeader)));
        arrayList.add(UItem.asCheck(ItemId.KEEP_ALIVE.getId(), LocaleController.getString(C2369R.string.KeepAliveService)).setChecked(AyuConfig.keepAliveService).setSearchable(this).setLinkAlias("keepAliveService", this));
        arrayList.add(UItem.asCheck(ItemId.LOCAL_PREMIUM.getId(), LocaleController.getString(C2369R.string.LocalPremium)).setChecked(AyuConfig.localPremium).setSearchable(this).setLinkAlias("localPremium", this));
        arrayList.add(UItem.asCheck(ItemId.DISABLE_ADS.getId(), LocaleController.getString(C2369R.string.DisableAds)).setChecked(AyuConfig.disableAds).setSearchable(this).setLinkAlias("disableAds", this));
        arrayList.add(UItem.asCheck(ItemId.DISPLAY_GHOST_STATUS.getId(), LocaleController.getString(C2369R.string.DisplayGhostStatus)).setChecked(AyuConfig.displayGhostStatus).setSearchable(this).setLinkAlias("displayGhostStatus", this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$1(Integer num) {
        this.colorPicker.setSelected(num.intValue(), true);
        SharedPreferences.Editor editor = AyuConfig.editor;
        int iIntValue = num.intValue();
        AyuConfig.deletedIconColor = iIntValue;
        editor.putInt("deletedIconColor", iIntValue).apply();
        AyuMessageUtils.reinitializeIcons();
        this.deletedMessagesCell.invalidateTime();
    }

    private void toggleLocalPremium(View view, float f, float f2) {
        boolean z = AyuConfig.localPremium;
        boolean z2 = !z;
        SharedPreferences.Editor editor = AyuConfig.editor;
        AyuConfig.localPremium = z2;
        editor.putBoolean("localPremium", z2).apply();
        getMessagesController().updatePremium(AyuConfig.localPremium);
        boolean z3 = false;
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.premiumStatusChangedGlobal, new Object[0]);
        getMediaDataController().loadPremiumPromo(false);
        getMediaDataController().loadReactions(false, null);
        if (!z) {
            while (true) {
                if (view == getFragmentView()) {
                    break;
                }
                if (view.getParent() == null) {
                    z3 = true;
                    break;
                } else {
                    f += view.getX();
                    f2 += view.getY();
                    view = (View) view.getParent();
                }
            }
            float top = f2 + getFragmentView().getTop();
            if (AndroidUtilities.isTablet()) {
                ViewGroup view2 = getParentLayout().getView();
                f += view2.getX() + view2.getPaddingLeft();
                top += view2.getY() + view2.getPaddingTop();
            }
            if (!z3) {
                LaunchActivity.makeRipple(f, top, 0.9f);
            }
        }
        if (z || AyuConfig.sawLocalPremiumAlert) {
            return;
        }
        AlertUtils.showLocalPremiumAlert(this);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) {
            return;
        }
        if (uItem.f2017id == ItemId.KEEP_ALIVE.getId()) {
            SharedPreferences.Editor editor = AyuConfig.editor;
            boolean z = !AyuConfig.keepAliveService;
            AyuConfig.keepAliveService = z;
            editor.putBoolean("keepAliveService", z).apply();
            if (!CloudMessagingUtils.spoofingNeeded()) {
                Switch checkBox = ((TextCheckCell) view).getCheckBox();
                int[] iArr = new int[2];
                checkBox.getLocationInWindow(iArr);
                if (AndroidUtilities.isTablet()) {
                    ViewGroup view2 = getParentLayout().getView();
                    iArr[0] = iArr[0] + ((int) (view2.getX() + view2.getPaddingLeft()));
                    iArr[1] = iArr[1] - ((int) (view2.getY() + view2.getPaddingTop()));
                }
                this.keepAliveServiceHint.setTranslationY(((iArr[1] + checkBox.getHeight()) - this.fragmentView.getTop()) + AndroidUtilities.m1146dp(4.0f));
                this.keepAliveServiceHint.setJointPx(0.0f, this.fragmentView.getWidth() - (checkBox.getWidth() * 1.5f));
                this.keepAliveServiceHint.show();
            }
        } else if (uItem.f2017id == ItemId.DISABLE_ADS.getId()) {
            SharedPreferences.Editor editor2 = AyuConfig.editor;
            boolean z2 = !AyuConfig.disableAds;
            AyuConfig.disableAds = z2;
            editor2.putBoolean("disableAds", z2).apply();
        } else if (uItem.f2017id == ItemId.LOCAL_PREMIUM.getId()) {
            toggleLocalPremium(view, f, f2);
        } else if (uItem.f2017id == ItemId.DISPLAY_GHOST_STATUS.getId()) {
            SharedPreferences.Editor editor3 = AyuConfig.editor;
            boolean z3 = !AyuConfig.displayGhostStatus;
            AyuConfig.displayGhostStatus = z3;
            editor3.putBoolean("displayGhostStatus", z3).apply();
            NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
        } else if (uItem.f2017id == ItemId.DELETED_ICON.getId()) {
            PopupUtils.showDialog((CharSequence[]) deletedMarks.toArray(new CharSequence[0]), LocaleController.getString(C2369R.string.DeletedMarkText), AyuConfig.deletedIcon, getContext(), new PopupUtils.OnItemClickListener() { // from class: com.radolyn.ayugram.preferences.CustomizationPreferencesActivity$$ExternalSyntheticLambda0
                @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                public final void onClick(int i2) {
                    this.f$0.lambda$onClick$2(i2);
                }
            });
        } else if (uItem.f2017id == ItemId.SIMPLE_QUOTES.getId()) {
            SharedPreferences.Editor editor4 = AyuConfig.editor;
            boolean z4 = !AyuConfig.simpleQuotesAndReplies;
            AyuConfig.simpleQuotesAndReplies = z4;
            editor4.putBoolean("simpleQuotesAndReplies", z4).apply();
            DeletedMessagesCell deletedMessagesCell = this.deletedMessagesCell;
            if (deletedMessagesCell != null) {
                deletedMessagesCell.invalidate();
            }
        } else if (uItem.f2017id == ItemId.SEMI_TRANSPARENT.getId()) {
            SharedPreferences.Editor editor5 = AyuConfig.editor;
            boolean z5 = !AyuConfig.semiTransparentDeletedMessages;
            AyuConfig.semiTransparentDeletedMessages = z5;
            editor5.putBoolean("semiTransparentDeletedMessages", z5).apply();
            DeletedMessagesCell deletedMessagesCell2 = this.deletedMessagesCell;
            if (deletedMessagesCell2 != null) {
                deletedMessagesCell2.animateSemiTransparency(AyuConfig.semiTransparentDeletedMessages);
            }
        } else if (uItem.f2017id == ItemId.DRAWER_SETTINGS.getId()) {
            presentFragment(new DrawerPreferencesActivity());
        }
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(int i) {
        SharedPreferences.Editor editor = AyuConfig.editor;
        AyuConfig.deletedIcon = i;
        editor.putInt("deletedIcon", i).apply();
        this.parentLayout.rebuildAllFragmentViews(false, false);
        DeletedMessagesCell deletedMessagesCell = this.deletedMessagesCell;
        if (deletedMessagesCell != null) {
            deletedMessagesCell.invalidateTime();
        }
        this.listView.adapter.update(true);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.CategoryCustomization);
    }

    private class DeletedMessagesCell extends FrameLayout {
        private final DeletedMessagePreviewCell messagesCell;

        public DeletedMessagesCell(Context context) {
            super(context);
            setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            setWillNotDraw(false);
            DeletedMessagePreviewCell deletedMessagePreviewCell = new DeletedMessagePreviewCell(context, ((BaseFragment) CustomizationPreferencesActivity.this).parentLayout);
            this.messagesCell = deletedMessagePreviewCell;
            deletedMessagePreviewCell.setImportantForAccessibility(4);
            addView(deletedMessagePreviewCell, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            this.messagesCell.invalidate();
        }

        public void invalidateTime() {
            this.messagesCell.invalidateTime();
        }

        public void animateSemiTransparency(boolean z) {
            this.messagesCell.animateSemiTransparency(z);
        }
    }
}
