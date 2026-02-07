package org.telegram.p023ui.Business;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Business.QuickRepliesController;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Cells.TextCheckCell;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CircularProgressDrawable;
import org.telegram.p023ui.Components.CrossfadeDrawable;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;

/* loaded from: classes5.dex */
public class AwayMessagesActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private int currentScheduleCustomEnd;
    private int currentScheduleCustomStart;
    public TL_account.TL_businessAwayMessage currentValue;
    public int currentValueScheduleType;
    private ActionBarMenuItem doneButton;
    private CrossfadeDrawable doneButtonDrawable;
    public boolean enabled;
    public boolean exclude;
    private boolean hasHours;
    private UniversalRecyclerView listView;
    public boolean offline_only;
    private BusinessRecipientsHelper recipientsHelper;
    public int schedule;
    public int scheduleCustomEnd;
    public int scheduleCustomStart;
    private int shiftDp = -4;
    private boolean valueSet;

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString(C2369R.string.BusinessAway));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.Business.AwayMessagesActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    if (AwayMessagesActivity.this.onBackPressed()) {
                        AwayMessagesActivity.this.lambda$onBackPressed$371();
                    }
                } else if (i == 1) {
                    AwayMessagesActivity.this.processDone();
                }
            }
        });
        Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.ic_ab_done).mutate();
        int i = Theme.key_actionBarDefaultIcon;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i), PorterDuff.Mode.MULTIPLY));
        this.doneButtonDrawable = new CrossfadeDrawable(drawableMutate, new CircularProgressDrawable(Theme.getColor(i)));
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, this.doneButtonDrawable, AndroidUtilities.m1146dp(56.0f), LocaleController.getString(C2369R.string.Done));
        checkDone(false);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        BusinessRecipientsHelper businessRecipientsHelper = new BusinessRecipientsHelper(this, new Runnable() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$0();
            }
        });
        this.recipientsHelper = businessRecipientsHelper;
        businessRecipientsHelper.setExclude(this.exclude);
        BusinessRecipientsHelper businessRecipientsHelper2 = this.recipientsHelper;
        if (businessRecipientsHelper2 != null) {
            TL_account.TL_businessAwayMessage tL_businessAwayMessage = this.currentValue;
            businessRecipientsHelper2.setValue(tL_businessAwayMessage == null ? null : tL_businessAwayMessage.recipients);
        }
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, null);
        this.listView = universalRecyclerView;
        universalRecyclerView.adapter.setUseSectionStyle(true);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        setValue();
        this.fragmentView = frameLayout;
        return frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0() {
        this.listView.adapter.update(true);
        checkDone(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setValue() {
        /*
            r4 = this;
            boolean r0 = r4.valueSet
            if (r0 == 0) goto L5
            return
        L5:
            org.telegram.messenger.UserConfig r0 = r4.getUserConfig()
            long r0 = r0.getClientUserId()
            org.telegram.messenger.MessagesController r2 = r4.getMessagesController()
            org.telegram.tgnet.TLRPC$UserFull r0 = r2.getUserFull(r0)
            r1 = 1
            if (r0 != 0) goto L2c
            org.telegram.messenger.MessagesController r0 = r4.getMessagesController()
            org.telegram.messenger.UserConfig r2 = r4.getUserConfig()
            org.telegram.tgnet.TLRPC$User r2 = r2.getCurrentUser()
            int r3 = r4.getClassGuid()
            r0.loadUserInfo(r2, r1, r3)
            return
        L2c:
            org.telegram.tgnet.tl.TL_account$TL_businessAwayMessage r2 = r0.business_away_message
            r4.currentValue = r2
            org.telegram.tgnet.tl.TL_account$TL_businessWorkHours r0 = r0.business_work_hours
            r3 = 0
            if (r0 == 0) goto L37
            r0 = 1
            goto L38
        L37:
            r0 = 0
        L38:
            r4.hasHours = r0
            if (r2 == 0) goto L3e
            r0 = 1
            goto L3f
        L3e:
            r0 = 0
        L3f:
            r4.enabled = r0
            if (r2 == 0) goto L48
            org.telegram.tgnet.tl.TL_account$TL_businessRecipients r0 = r2.recipients
            boolean r0 = r0.exclude_selected
            goto L49
        L48:
            r0 = 1
        L49:
            r4.exclude = r0
            if (r2 == 0) goto L50
            boolean r0 = r2.offline_only
            goto L51
        L50:
            r0 = 1
        L51:
            r4.offline_only = r0
            org.telegram.ui.Business.BusinessRecipientsHelper r0 = r4.recipientsHelper
            if (r0 == 0) goto L60
            if (r2 != 0) goto L5b
            r2 = 0
            goto L5d
        L5b:
            org.telegram.tgnet.tl.TL_account$TL_businessRecipients r2 = r2.recipients
        L5d:
            r0.setValue(r2)
        L60:
            org.telegram.tgnet.tl.TL_account$TL_businessAwayMessage r0 = r4.currentValue
            if (r0 == 0) goto L81
            org.telegram.tgnet.tl.TL_account$BusinessAwayMessageSchedule r0 = r0.schedule
            boolean r2 = r0 instanceof org.telegram.tgnet.tl.TL_account.TL_businessAwayMessageScheduleCustom
            if (r2 == 0) goto L81
            r2 = 2
            r4.currentValueScheduleType = r2
            r4.schedule = r2
            r2 = r0
            org.telegram.tgnet.tl.TL_account$TL_businessAwayMessageScheduleCustom r2 = (org.telegram.tgnet.tl.TL_account.TL_businessAwayMessageScheduleCustom) r2
            int r2 = r2.start_date
            r4.currentScheduleCustomStart = r2
            r4.scheduleCustomStart = r2
            org.telegram.tgnet.tl.TL_account$TL_businessAwayMessageScheduleCustom r0 = (org.telegram.tgnet.tl.TL_account.TL_businessAwayMessageScheduleCustom) r0
            int r0 = r0.end_date
            r4.currentScheduleCustomEnd = r0
            r4.scheduleCustomEnd = r0
            goto Lb9
        L81:
            org.telegram.tgnet.ConnectionsManager r0 = r4.getConnectionsManager()
            int r0 = r0.getCurrentTime()
            r4.scheduleCustomStart = r0
            org.telegram.tgnet.ConnectionsManager r0 = r4.getConnectionsManager()
            int r0 = r0.getCurrentTime()
            r2 = 86400(0x15180, float:1.21072E-40)
            int r0 = r0 + r2
            r4.scheduleCustomEnd = r0
            org.telegram.tgnet.tl.TL_account$TL_businessAwayMessage r0 = r4.currentValue
            if (r0 == 0) goto La8
            org.telegram.tgnet.tl.TL_account$BusinessAwayMessageSchedule r2 = r0.schedule
            boolean r2 = r2 instanceof org.telegram.tgnet.tl.TL_account.TL_businessAwayMessageScheduleAlways
            if (r2 == 0) goto La8
            r4.currentValueScheduleType = r3
            r4.schedule = r3
            goto Lb9
        La8:
            if (r0 == 0) goto Lb5
            org.telegram.tgnet.tl.TL_account$BusinessAwayMessageSchedule r0 = r0.schedule
            boolean r0 = r0 instanceof org.telegram.tgnet.tl.TL_account.TL_businessAwayMessageScheduleOutsideWorkHours
            if (r0 == 0) goto Lb5
            r4.currentValueScheduleType = r1
            r4.schedule = r1
            goto Lb9
        Lb5:
            r4.currentValueScheduleType = r3
            r4.schedule = r3
        Lb9:
            org.telegram.ui.Components.UniversalRecyclerView r0 = r4.listView
            if (r0 == 0) goto Lc4
            org.telegram.ui.Components.UniversalAdapter r0 = r0.adapter
            if (r0 == 0) goto Lc4
            r0.update(r1)
        Lc4:
            r4.checkDone(r1)
            r4.valueSet = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Business.AwayMessagesActivity.setValue():void");
    }

    public boolean hasChanges() {
        if (!this.valueSet) {
            return false;
        }
        boolean z = this.enabled;
        TL_account.TL_businessAwayMessage tL_businessAwayMessage = this.currentValue;
        if (z != (tL_businessAwayMessage != null)) {
            return true;
        }
        if (z && tL_businessAwayMessage != null) {
            if (tL_businessAwayMessage.recipients.exclude_selected != this.exclude) {
                return true;
            }
            BusinessRecipientsHelper businessRecipientsHelper = this.recipientsHelper;
            if (businessRecipientsHelper != null && businessRecipientsHelper.hasChanges()) {
                return true;
            }
            int i = this.currentValueScheduleType;
            int i2 = this.schedule;
            if (i != i2 || this.currentValue.offline_only != this.offline_only) {
                return true;
            }
            if (i2 == 2 && (this.currentScheduleCustomStart != this.scheduleCustomStart || this.currentScheduleCustomEnd != this.scheduleCustomEnd)) {
                return true;
            }
        }
        return false;
    }

    private void checkDone(boolean z) {
        if (this.doneButton == null) {
            return;
        }
        boolean zHasChanges = hasChanges();
        this.doneButton.setEnabled(zHasChanges);
        if (z) {
            this.doneButton.animate().alpha(zHasChanges ? 1.0f : 0.0f).scaleX(zHasChanges ? 1.0f : 0.0f).scaleY(zHasChanges ? 1.0f : 0.0f).setDuration(180L).start();
            return;
        }
        this.doneButton.setAlpha(zHasChanges ? 1.0f : 0.0f);
        this.doneButton.setScaleX(zHasChanges ? 1.0f : 0.0f);
        this.doneButton.setScaleY(zHasChanges ? 1.0f : 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDone() {
        if (this.doneButtonDrawable.getProgress() > 0.0f) {
            return;
        }
        if (!hasChanges()) {
            lambda$onBackPressed$371();
            return;
        }
        QuickRepliesController.QuickReply quickReplyFindReply = QuickRepliesController.getInstance(this.currentAccount).findReply("away");
        boolean z = this.enabled;
        if (z && quickReplyFindReply == null) {
            BotWebViewVibrationEffect.APP_ERROR.vibrate();
            View viewFindViewByItemId = this.listView.findViewByItemId(2);
            int i = -this.shiftDp;
            this.shiftDp = i;
            AndroidUtilities.shakeViewSpring(viewFindViewByItemId, i);
            UniversalRecyclerView universalRecyclerView = this.listView;
            universalRecyclerView.smoothScrollToPosition(universalRecyclerView.findPositionByItemId(2));
            return;
        }
        if (!z || this.recipientsHelper.validate(this.listView)) {
            this.doneButtonDrawable.animateToProgress(1.0f);
            TLRPC.UserFull userFull = getMessagesController().getUserFull(getUserConfig().getClientUserId());
            TL_account.updateBusinessAwayMessage updatebusinessawaymessage = new TL_account.updateBusinessAwayMessage();
            if (this.enabled) {
                TL_account.TL_inputBusinessAwayMessage tL_inputBusinessAwayMessage = new TL_account.TL_inputBusinessAwayMessage();
                updatebusinessawaymessage.message = tL_inputBusinessAwayMessage;
                tL_inputBusinessAwayMessage.offline_only = this.offline_only;
                tL_inputBusinessAwayMessage.shortcut_id = quickReplyFindReply.f1797id;
                tL_inputBusinessAwayMessage.recipients = this.recipientsHelper.getInputValue();
                int i2 = this.schedule;
                if (i2 == 0) {
                    updatebusinessawaymessage.message.schedule = new TL_account.TL_businessAwayMessageScheduleAlways();
                } else if (i2 == 1) {
                    updatebusinessawaymessage.message.schedule = new TL_account.TL_businessAwayMessageScheduleOutsideWorkHours();
                } else if (i2 == 2) {
                    TL_account.TL_businessAwayMessageScheduleCustom tL_businessAwayMessageScheduleCustom = new TL_account.TL_businessAwayMessageScheduleCustom();
                    tL_businessAwayMessageScheduleCustom.start_date = this.scheduleCustomStart;
                    tL_businessAwayMessageScheduleCustom.end_date = this.scheduleCustomEnd;
                    updatebusinessawaymessage.message.schedule = tL_businessAwayMessageScheduleCustom;
                }
                updatebusinessawaymessage.flags |= 1;
                if (userFull != null) {
                    userFull.flags2 |= 8;
                    TL_account.TL_businessAwayMessage tL_businessAwayMessage = new TL_account.TL_businessAwayMessage();
                    userFull.business_away_message = tL_businessAwayMessage;
                    tL_businessAwayMessage.offline_only = this.offline_only;
                    tL_businessAwayMessage.shortcut_id = quickReplyFindReply.f1797id;
                    tL_businessAwayMessage.recipients = this.recipientsHelper.getValue();
                    userFull.business_away_message.schedule = updatebusinessawaymessage.message.schedule;
                }
            } else if (userFull != null) {
                userFull.flags2 &= -9;
                userFull.business_away_message = null;
            }
            getConnectionsManager().sendRequest(updatebusinessawaymessage, new RequestDelegate() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda5
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$processDone$2(tLObject, tL_error);
                }
            });
            getMessagesStorage().updateUserInfo(userFull, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDone$2(final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDone$1(tL_error, tLObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDone$1(TLRPC.TL_error tL_error, TLObject tLObject) {
        if (tL_error != null) {
            this.doneButtonDrawable.animateToProgress(0.0f);
            BulletinFactory.showError(tL_error);
        } else if (tLObject instanceof TLRPC.TL_boolFalse) {
            this.doneButtonDrawable.animateToProgress(0.0f);
            BulletinFactory.m1267of(this).createErrorBulletin(LocaleController.getString(C2369R.string.UnknownError)).show();
        } else {
            lambda$onBackPressed$371();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (hasChanges()) {
            if (!this.enabled) {
                processDone();
                return false;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString(C2369R.string.UnsavedChanges));
            builder.setMessage(LocaleController.getString(C2369R.string.BusinessAwayUnsavedChanges));
            builder.setPositiveButton(LocaleController.getString(C2369R.string.ApplyTheme), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda3
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$onBackPressed$3(alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.PassportDiscard), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda4
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$onBackPressed$4(alertDialog, i);
                }
            });
            showDialog(builder.create());
            return false;
        }
        return super.onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackPressed$3(AlertDialog alertDialog, int i) {
        processDone();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBackPressed$4(AlertDialog alertDialog, int i) {
        lambda$onBackPressed$371();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asTopView(LocaleController.getString(C2369R.string.BusinessAwayInfo), "RestrictedEmoji", "💤"));
        arrayList.add(UItem.asCheck(1, LocaleController.getString(C2369R.string.BusinessAwaySend)).setChecked(this.enabled));
        arrayList.add(UItem.asShadow(null));
        if (this.enabled) {
            QuickRepliesController.QuickReply quickReplyFindReply = QuickRepliesController.getInstance(this.currentAccount).findReply("away");
            if (quickReplyFindReply != null) {
                arrayList.add(UItem.asLargeQuickReply(quickReplyFindReply));
            } else {
                arrayList.add(UItem.asButton(2, C2369R.drawable.msg2_chats_add, LocaleController.getString(C2369R.string.BusinessAwayCreate)).accent());
            }
            arrayList.add(UItem.asShadow(null));
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.BusinessAwaySchedule)));
            arrayList.add(UItem.asRadio(3, LocaleController.getString(C2369R.string.BusinessAwayScheduleAlways)).setChecked(this.schedule == 0));
            if (this.hasHours) {
                arrayList.add(UItem.asRadio(4, LocaleController.getString(C2369R.string.BusinessAwayScheduleOutsideHours)).setChecked(this.schedule == 1));
            }
            arrayList.add(UItem.asRadio(5, LocaleController.getString(C2369R.string.BusinessAwayScheduleCustom)).setChecked(this.schedule == 2));
            if (this.schedule == 2) {
                arrayList.add(UItem.asShadow(null));
                arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.BusinessAwaySchedule)));
                arrayList.add(UItem.asButton(8, LocaleController.getString(C2369R.string.BusinessAwayScheduleCustomStart), LocaleController.formatShortDateTime(this.scheduleCustomStart)));
                arrayList.add(UItem.asButton(9, LocaleController.getString(C2369R.string.BusinessAwayScheduleCustomEnd), LocaleController.formatShortDateTime(this.scheduleCustomEnd)));
            }
            arrayList.add(UItem.asShadow(null));
            arrayList.add(UItem.asCheck(10, LocaleController.getString(C2369R.string.BusinessAwayOnlyOffline)).setChecked(this.offline_only));
            arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.BusinessAwayOnlyOfflineInfo)));
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.BusinessRecipients)));
            arrayList.add(UItem.asRadio(6, LocaleController.getString(C2369R.string.BusinessChatsAllPrivateExcept)).setChecked(this.exclude));
            arrayList.add(UItem.asRadio(7, LocaleController.getString(C2369R.string.BusinessChatsOnlySelected)).setChecked(true ^ this.exclude));
            arrayList.add(UItem.asShadow(null));
            this.recipientsHelper.fillItems(arrayList);
            arrayList.add(UItem.asShadow(null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(UItem uItem, final View view, int i, float f, float f2) {
        if (this.recipientsHelper.onClick(uItem)) {
            return;
        }
        int i2 = uItem.f2017id;
        if (i2 == 2 || uItem.viewType == 17) {
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", getUserConfig().getClientUserId());
            bundle.putInt("chatMode", 5);
            bundle.putString("quick_reply", "away");
            presentFragment(new ChatActivity(bundle));
            return;
        }
        if (i2 == 1) {
            this.enabled = !this.enabled;
            this.listView.adapter.update(true);
            checkDone(true);
            return;
        }
        if (i2 == 6) {
            BusinessRecipientsHelper businessRecipientsHelper = this.recipientsHelper;
            this.exclude = true;
            businessRecipientsHelper.setExclude(true);
            this.listView.adapter.update(true);
            checkDone(true);
            return;
        }
        if (i2 == 7) {
            BusinessRecipientsHelper businessRecipientsHelper2 = this.recipientsHelper;
            this.exclude = false;
            businessRecipientsHelper2.setExclude(false);
            this.listView.adapter.update(true);
            checkDone(true);
            return;
        }
        if (i2 == 3) {
            this.schedule = 0;
            this.listView.adapter.update(true);
            checkDone(true);
            return;
        }
        if (i2 == 4) {
            this.schedule = 1;
            this.listView.adapter.update(true);
            checkDone(true);
            return;
        }
        if (i2 == 5) {
            this.schedule = 2;
            this.listView.adapter.update(true);
            checkDone(true);
        } else {
            if (i2 == 8) {
                AlertsCreator.createDatePickerDialog(getContext(), LocaleController.getString(C2369R.string.BusinessAwayScheduleCustomStartTitle), LocaleController.getString(C2369R.string.BusinessAwayScheduleCustomSetButton), this.scheduleCustomStart, new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda6
                    @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
                    public final void didSelectDate(boolean z, int i3, int i4) {
                        this.f$0.lambda$onClick$5(view, z, i3, i4);
                    }
                });
                return;
            }
            if (i2 == 9) {
                AlertsCreator.createDatePickerDialog(getContext(), LocaleController.getString(C2369R.string.BusinessAwayScheduleCustomEndTitle), LocaleController.getString(C2369R.string.BusinessAwayScheduleCustomSetButton), this.scheduleCustomEnd, new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.Business.AwayMessagesActivity$$ExternalSyntheticLambda7
                    @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
                    public final void didSelectDate(boolean z, int i3, int i4) {
                        this.f$0.lambda$onClick$6(view, z, i3, i4);
                    }
                });
            } else if (i2 == 10) {
                boolean z = !this.offline_only;
                this.offline_only = z;
                ((TextCheckCell) view).setChecked(z);
                checkDone(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5(View view, boolean z, int i, int i2) {
        this.scheduleCustomStart = i;
        ((TextCell) view).setValue(LocaleController.formatShortDateTime(i), true);
        checkDone(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(View view, boolean z, int i, int i2) {
        this.scheduleCustomEnd = i;
        ((TextCell) view).setValue(LocaleController.formatShortDateTime(i), true);
        checkDone(true);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        UniversalAdapter universalAdapter;
        if (i == NotificationCenter.quickRepliesUpdated) {
            UniversalRecyclerView universalRecyclerView = this.listView;
            if (universalRecyclerView != null && (universalAdapter = universalRecyclerView.adapter) != null) {
                universalAdapter.update(true);
            }
            checkDone(true);
            return;
        }
        if (i == NotificationCenter.userInfoDidLoad) {
            setValue();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.quickRepliesUpdated);
        getNotificationCenter().addObserver(this, NotificationCenter.userInfoDidLoad);
        QuickRepliesController.getInstance(this.currentAccount).load();
        setValue();
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        getNotificationCenter().removeObserver(this, NotificationCenter.quickRepliesUpdated);
        getNotificationCenter().removeObserver(this, NotificationCenter.userInfoDidLoad);
        super.onFragmentDestroy();
    }
}
