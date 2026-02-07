package org.telegram.p023ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.exteragram.messenger.api.dto.NowPlayingInfoDTO;
import com.exteragram.messenger.api.model.NowPlayingServiceType;
import com.exteragram.messenger.badges.BadgesController;
import com.exteragram.messenger.nowplaying.NowPlayingController;
import com.exteragram.messenger.nowplaying.p008ui.SetupNowPlayingActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Consumer;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Business.LocationActivity;
import org.telegram.p023ui.Business.OpeningHoursActivity;
import org.telegram.p023ui.Cells.EditTextCell;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CircularProgressDrawable;
import org.telegram.p023ui.Components.CrossfadeDrawable;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalFragment;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import p017j$.util.function.Consumer$CC;

/* loaded from: classes5.dex */
public class UserInfoActivity extends UniversalFragment implements NotificationCenter.NotificationCenterDelegate {
    private EditTextCell bioEdit;
    private CharSequence bioInfo;
    private TL_account.TL_birthday birthday;
    private CharSequence birthdayInfo;
    private TLRPC.Chat channel;
    private String currentBio;
    private TL_account.TL_birthday currentBirthday;
    private long currentChannel;
    private String currentFirstName;
    private String currentLastName;
    private ActionBarMenuItem doneButton;
    private CrossfadeDrawable doneButtonDrawable;
    private EditTextCell firstNameEdit;
    private boolean hadHours;
    private boolean hadLocation;
    private EditTextCell lastNameEdit;
    private NowPlayingServiceType nowPlayingService;
    private boolean valueSet;
    private AdminedChannelsFetcher channels = new AdminedChannelsFetcher(this.currentAccount, true);
    private boolean wasSaved = false;
    private int shiftDp = -4;

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        return false;
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected CharSequence getTitle() {
        return LocaleController.getString(C2369R.string.EditProfileInfo);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.userInfoDidLoad);
        getNotificationCenter().addObserver(this, NotificationCenter.privacyRulesUpdated);
        getNotificationCenter().addObserver(this, NotificationCenter.nowPlayingUpdated);
        getContactsController().loadPrivacySettings();
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        getNotificationCenter().removeObserver(this, NotificationCenter.userInfoDidLoad);
        getNotificationCenter().removeObserver(this, NotificationCenter.privacyRulesUpdated);
        getNotificationCenter().removeObserver(this, NotificationCenter.nowPlayingUpdated);
        super.onFragmentDestroy();
        if (this.wasSaved) {
            return;
        }
        processDone(false);
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        int i = -1;
        boolean z = false;
        boolean z2 = false;
        EditTextCell editTextCell = new EditTextCell(context, LocaleController.getString(C2369R.string.EditProfileFirstName), z, z2, i, this.resourceProvider) { // from class: org.telegram.ui.UserInfoActivity.1
            @Override // org.telegram.p023ui.Cells.EditTextCell
            protected void onTextChanged(CharSequence charSequence) {
                super.onTextChanged(charSequence);
                UserInfoActivity.this.checkDone(true);
            }
        };
        this.firstNameEdit = editTextCell;
        int i2 = Theme.key_windowBackgroundWhite;
        editTextCell.setBackgroundColor(getThemedColor(i2));
        this.firstNameEdit.setDivider(true);
        this.firstNameEdit.hideKeyboardOnEnter();
        EditTextCell editTextCell2 = new EditTextCell(context, LocaleController.getString(C2369R.string.EditProfileLastName), z, z2, i, this.resourceProvider) { // from class: org.telegram.ui.UserInfoActivity.2
            @Override // org.telegram.p023ui.Cells.EditTextCell
            protected void onTextChanged(CharSequence charSequence) {
                super.onTextChanged(charSequence);
                UserInfoActivity.this.checkDone(true);
            }
        };
        this.lastNameEdit = editTextCell2;
        editTextCell2.setBackgroundColor(getThemedColor(i2));
        this.lastNameEdit.hideKeyboardOnEnter();
        EditTextCell editTextCell3 = new EditTextCell(context, LocaleController.getString(C2369R.string.EditProfileBioHint), true, z2, getMessagesController().getAboutLimit(), this.resourceProvider) { // from class: org.telegram.ui.UserInfoActivity.3
            @Override // org.telegram.p023ui.Cells.EditTextCell
            protected void onTextChanged(CharSequence charSequence) {
                super.onTextChanged(charSequence);
                UserInfoActivity.this.checkDone(true);
            }
        };
        this.bioEdit = editTextCell3;
        editTextCell3.setBackgroundColor(getThemedColor(i2));
        this.bioEdit.setShowLimitWhenEmpty(true);
        this.bioInfo = AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.EditProfileBioInfo), new Runnable() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$0();
            }
        });
        super.createView(context);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.UserInfoActivity.4
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i3) {
                if (i3 == -1) {
                    if (UserInfoActivity.this.onBackPressed()) {
                        UserInfoActivity.this.lambda$onBackPressed$371();
                    }
                } else if (i3 == 1) {
                    UserInfoActivity.this.processDone(true);
                }
            }
        });
        Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.ic_ab_done).mutate();
        int i3 = Theme.key_actionBarDefaultIcon;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i3), PorterDuff.Mode.MULTIPLY));
        this.doneButtonDrawable = new CrossfadeDrawable(drawableMutate, new CircularProgressDrawable(Theme.getColor(i3)));
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, this.doneButtonDrawable, AndroidUtilities.m1146dp(56.0f), LocaleController.getString(C2369R.string.Done));
        checkDone(false);
        setValue();
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0() {
        presentFragment(new PrivacyControlActivity(9, true));
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        ArrayList<TLRPC.PrivacyRule> privacyRules;
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.EditProfileName)));
        arrayList.add(UItem.asCustom(this.firstNameEdit));
        arrayList.add(UItem.asCustom(this.lastNameEdit));
        arrayList.add(UItem.asShadow(-1, null));
        if (BadgesController.INSTANCE.hasBadge() && this.nowPlayingService != null) {
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.NowPlaying)));
            arrayList.add(UItem.asButton(6, LocaleController.getString(C2369R.string.ScrobblingService), this.nowPlayingService.getDisplayName()));
            arrayList.add(UItem.asShadow(null));
        }
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.EditProfileChannel)));
        String string = LocaleController.getString(C2369R.string.EditProfileChannelTitle);
        TLRPC.Chat chat = this.channel;
        arrayList.add(UItem.asButton(3, string, chat == null ? LocaleController.getString(C2369R.string.EditProfileChannelAdd) : chat.title));
        arrayList.add(UItem.asShadow(-2, null));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.EditProfileBio)));
        arrayList.add(UItem.asCustom(this.bioEdit));
        arrayList.add(UItem.asShadow(this.bioInfo));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.EditProfileBirthday)));
        String string2 = LocaleController.getString(C2369R.string.EditProfileBirthdayText);
        TL_account.TL_birthday tL_birthday = this.birthday;
        arrayList.add(UItem.asButton(1, string2, tL_birthday == null ? LocaleController.getString(C2369R.string.EditProfileBirthdayAdd) : birthdayString(tL_birthday)));
        if (this.birthday != null) {
            arrayList.add(UItem.asButton(2, LocaleController.getString(C2369R.string.EditProfileBirthdayRemove)).red());
        }
        if (!getContactsController().getLoadingPrivacyInfo(11) && (privacyRules = getContactsController().getPrivacyRules(11)) != null && this.birthdayInfo == null) {
            String string3 = LocaleController.getString(C2369R.string.EditProfileBirthdayInfoContacts);
            if (!privacyRules.isEmpty()) {
                int i = 0;
                while (true) {
                    if (i >= privacyRules.size()) {
                        break;
                    }
                    if (privacyRules.get(i) instanceof TLRPC.TL_privacyValueAllowContacts) {
                        string3 = LocaleController.getString(C2369R.string.EditProfileBirthdayInfoContacts);
                        break;
                    }
                    if ((privacyRules.get(i) instanceof TLRPC.TL_privacyValueAllowAll) || (privacyRules.get(i) instanceof TLRPC.TL_privacyValueDisallowAll)) {
                        string3 = LocaleController.getString(C2369R.string.EditProfileBirthdayInfo);
                    }
                    i++;
                }
            }
            this.birthdayInfo = AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag(string3, new Runnable() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$fillItems$1();
                }
            }), true);
        }
        arrayList.add(UItem.asShadow(this.birthdayInfo));
        if (this.hadLocation) {
            arrayList.add(UItem.asButton(4, C2369R.drawable.menu_premium_clock, LocaleController.getString(C2369R.string.EditProfileHours)));
        }
        if (this.hadLocation) {
            arrayList.add(UItem.asButton(5, C2369R.drawable.msg_map, LocaleController.getString(C2369R.string.EditProfileLocation)));
        }
        if (this.hadLocation || this.hadHours) {
            arrayList.add(UItem.asShadow(-3, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$1() {
        presentFragment(new PrivacyControlActivity(11));
    }

    public static String birthdayString(TL_account.TL_birthday tL_birthday) {
        if (tL_birthday == null) {
            return "—";
        }
        if ((tL_birthday.flags & 1) != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, tL_birthday.year);
            calendar.set(2, tL_birthday.month - 1);
            calendar.set(5, tL_birthday.day);
            return LocaleController.getInstance().getFormatterBoostExpired().format(calendar.getTimeInMillis());
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2, tL_birthday.month - 1);
        calendar2.set(5, tL_birthday.day);
        return LocaleController.getInstance().getFormatterDayMonth().format(calendar2.getTimeInMillis());
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 == 1) {
            showDialog(AlertsCreator.createBirthdayPickerDialog(getContext(), LocaleController.getString(C2369R.string.EditProfileBirthdayTitle), LocaleController.getString(C2369R.string.EditProfileBirthdayButton), this.birthday, new Utilities.Callback() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$onClick$2((TL_account.TL_birthday) obj);
                }
            }, null, false, getResourceProvider()).create());
            return;
        }
        if (i2 == 2) {
            this.birthday = null;
            UniversalRecyclerView universalRecyclerView = this.listView;
            if (universalRecyclerView != null) {
                universalRecyclerView.adapter.update(true);
            }
            checkDone(true);
            return;
        }
        if (i2 == 3) {
            AdminedChannelsFetcher adminedChannelsFetcher = this.channels;
            TLRPC.Chat chat = this.channel;
            presentFragment(new ChooseChannelFragment(adminedChannelsFetcher, chat == null ? 0L : chat.f1571id, new Utilities.Callback() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$onClick$3((TLRPC.Chat) obj);
                }
            }));
        } else if (i2 == 5) {
            presentFragment(new LocationActivity());
        } else if (i2 == 6) {
            presentFragment(new SetupNowPlayingActivity());
        } else if (i2 == 4) {
            presentFragment(new OpeningHoursActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(TL_account.TL_birthday tL_birthday) {
        this.birthday = tL_birthday;
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
        checkDone(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3(TLRPC.Chat chat) {
        if (this.channel == chat) {
            return;
        }
        this.channel = chat;
        if (chat != null) {
            BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.getString(C2369R.string.EditProfileChannelSet)).show();
        }
        checkDone(true);
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.userInfoDidLoad) {
            setValue();
            return;
        }
        if (i == NotificationCenter.privacyRulesUpdated || i == NotificationCenter.nowPlayingUpdated) {
            if (i == NotificationCenter.nowPlayingUpdated) {
                Object obj = objArr[0];
                if (obj instanceof NowPlayingServiceType) {
                    this.nowPlayingService = (NowPlayingServiceType) obj;
                }
            }
            UniversalRecyclerView universalRecyclerView = this.listView;
            if (universalRecyclerView != null) {
                universalRecyclerView.adapter.update(true);
            }
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        this.channels.invalidate();
        this.channels.subscribe(new Runnable() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onResume$4();
            }
        });
        this.channels.fetch();
        this.birthdayInfo = null;
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$4() {
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    private void setValue() {
        UniversalAdapter universalAdapter;
        if (this.valueSet) {
            return;
        }
        TLRPC.UserFull userFull = getMessagesController().getUserFull(getUserConfig().getClientUserId());
        if (userFull == null) {
            getMessagesController().loadUserInfo(getUserConfig().getCurrentUser(), true, getClassGuid());
            return;
        }
        TLRPC.User currentUser = userFull.user;
        if (currentUser == null) {
            currentUser = getUserConfig().getCurrentUser();
        }
        if (currentUser == null) {
            return;
        }
        EditTextCell editTextCell = this.firstNameEdit;
        String str = currentUser.first_name;
        this.currentFirstName = str;
        editTextCell.setText(str);
        EditTextCell editTextCell2 = this.lastNameEdit;
        String str2 = currentUser.last_name;
        this.currentLastName = str2;
        editTextCell2.setText(str2);
        EditTextCell editTextCell3 = this.bioEdit;
        String str3 = userFull.about;
        this.currentBio = str3;
        editTextCell3.setText(str3);
        TL_account.TL_birthday tL_birthday = userFull.birthday;
        this.currentBirthday = tL_birthday;
        this.birthday = tL_birthday;
        if ((userFull.flags2 & 64) != 0) {
            this.currentChannel = userFull.personal_channel_id;
            this.channel = getMessagesController().getChat(Long.valueOf(this.currentChannel));
        } else {
            this.currentChannel = 0L;
            this.channel = null;
        }
        this.hadHours = userFull.business_work_hours != null;
        this.hadLocation = userFull.business_location != null;
        NowPlayingController.getNowPlayingInfo(new Consumer() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            /* renamed from: accept */
            public final void m971v(Object obj) {
                this.f$0.lambda$setValue$6((NowPlayingInfoDTO) obj);
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }
        });
        checkDone(true);
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null && (universalAdapter = universalRecyclerView.adapter) != null) {
            universalAdapter.update(true);
        }
        this.valueSet = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setValue$6(final NowPlayingInfoDTO nowPlayingInfoDTO) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setValue$5(nowPlayingInfoDTO);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setValue$5(NowPlayingInfoDTO nowPlayingInfoDTO) {
        this.nowPlayingService = nowPlayingInfoDTO == null ? null : nowPlayingInfoDTO.getServiceType();
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.adapter.update(true);
        }
    }

    public boolean hasChanges() {
        String str = this.currentFirstName;
        if (str == null) {
            str = "";
        }
        if (!TextUtils.equals(str, this.firstNameEdit.getText().toString())) {
            return true;
        }
        String str2 = this.currentLastName;
        if (str2 == null) {
            str2 = "";
        }
        if (!TextUtils.equals(str2, this.lastNameEdit.getText().toString())) {
            return true;
        }
        String str3 = this.currentBio;
        if (!TextUtils.equals(str3 != null ? str3 : "", this.bioEdit.getText().toString()) || !birthdaysEqual(this.currentBirthday, this.birthday)) {
            return true;
        }
        long j = this.currentChannel;
        TLRPC.Chat chat = this.channel;
        return j != (chat != null ? chat.f1571id : 0L);
    }

    public static boolean birthdaysEqual(TL_account.TL_birthday tL_birthday, TL_account.TL_birthday tL_birthday2) {
        return (tL_birthday == null) != (tL_birthday2 != null) && (tL_birthday == null || (tL_birthday.day == tL_birthday2.day && tL_birthday.month == tL_birthday2.month && tL_birthday.year == tL_birthday2.year));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkDone(boolean z) {
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
    public void processDone(boolean z) {
        if (this.doneButtonDrawable.getProgress() <= 0.0f) {
            if (z && TextUtils.isEmpty(this.firstNameEdit.getText())) {
                BotWebViewVibrationEffect.APP_ERROR.vibrate();
                EditTextCell editTextCell = this.firstNameEdit;
                int i = -this.shiftDp;
                this.shiftDp = i;
                AndroidUtilities.shakeViewSpring(editTextCell, i);
                return;
            }
            this.doneButtonDrawable.animateToProgress(1.0f);
            TLRPC.User currentUser = getUserConfig().getCurrentUser();
            final TLRPC.UserFull userFull = getMessagesController().getUserFull(getUserConfig().getClientUserId());
            if (currentUser != null && userFull != null) {
                final ArrayList arrayList = new ArrayList();
                if (!TextUtils.isEmpty(this.firstNameEdit.getText()) && (!TextUtils.equals(this.currentFirstName, this.firstNameEdit.getText().toString()) || !TextUtils.equals(this.currentLastName, this.lastNameEdit.getText().toString()) || !TextUtils.equals(this.currentBio, this.bioEdit.getText().toString()))) {
                    TL_account.updateProfile updateprofile = new TL_account.updateProfile();
                    updateprofile.flags |= 1;
                    String string = this.firstNameEdit.getText().toString();
                    currentUser.first_name = string;
                    updateprofile.first_name = string;
                    updateprofile.flags |= 2;
                    String string2 = this.lastNameEdit.getText().toString();
                    currentUser.last_name = string2;
                    updateprofile.last_name = string2;
                    updateprofile.flags |= 4;
                    String string3 = this.bioEdit.getText().toString();
                    userFull.about = string3;
                    updateprofile.about = string3;
                    userFull.flags = TextUtils.isEmpty(string3) ? userFull.flags & (-3) : userFull.flags | 2;
                    arrayList.add(updateprofile);
                }
                final TL_account.TL_birthday tL_birthday = userFull.birthday;
                if (!birthdaysEqual(this.currentBirthday, this.birthday)) {
                    TL_account.updateBirthday updatebirthday = new TL_account.updateBirthday();
                    TL_account.TL_birthday tL_birthday2 = this.birthday;
                    if (tL_birthday2 != null) {
                        userFull.flags2 |= 32;
                        userFull.birthday = tL_birthday2;
                        updatebirthday.flags |= 1;
                        updatebirthday.birthday = tL_birthday2;
                    } else {
                        userFull.flags2 &= -33;
                        userFull.birthday = null;
                    }
                    arrayList.add(updatebirthday);
                    getMessagesController().invalidateContentSettings();
                    NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.premiumPromoUpdated, new Object[0]);
                }
                long j = this.currentChannel;
                TLRPC.Chat chat = this.channel;
                if (j != (chat != null ? chat.f1571id : 0L)) {
                    TL_account.updatePersonalChannel updatepersonalchannel = new TL_account.updatePersonalChannel();
                    updatepersonalchannel.channel = MessagesController.getInputChannel(this.channel);
                    TLRPC.Chat chat2 = this.channel;
                    if (chat2 != null) {
                        userFull.flags |= 64;
                        long j2 = userFull.personal_channel_id;
                        long j3 = chat2.f1571id;
                        if (j2 != j3) {
                            userFull.personal_channel_message = 0;
                        }
                        userFull.personal_channel_id = j3;
                    } else {
                        userFull.flags &= -65;
                        userFull.personal_channel_message = 0;
                        userFull.personal_channel_id = 0L;
                    }
                    arrayList.add(updatepersonalchannel);
                }
                if (arrayList.isEmpty()) {
                    lambda$onBackPressed$371();
                    return;
                }
                final int[] iArr = {0};
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    final TLObject tLObject = (TLObject) arrayList.get(i2);
                    getConnectionsManager().sendRequest(tLObject, new RequestDelegate() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda6
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject2, TLRPC.TL_error tL_error) {
                            this.f$0.lambda$processDone$8(tLObject, tL_birthday, userFull, iArr, arrayList, tLObject2, tL_error);
                        }
                    }, 1024);
                }
                getMessagesStorage().updateUserInfo(userFull, false);
                getUserConfig().saveConfig(true);
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_NAME));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDone$8(final TLObject tLObject, final TL_account.TL_birthday tL_birthday, final TLRPC.UserFull userFull, final int[] iArr, final ArrayList arrayList, final TLObject tLObject2, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.UserInfoActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processDone$7(tL_error, tLObject, tL_birthday, userFull, tLObject2, iArr, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDone$7(TLRPC.TL_error tL_error, TLObject tLObject, TL_account.TL_birthday tL_birthday, TLRPC.UserFull userFull, TLObject tLObject2, int[] iArr, ArrayList arrayList) {
        String str;
        if (tL_error != null) {
            this.doneButtonDrawable.animateToProgress(0.0f);
            boolean z = tLObject instanceof TL_account.updateBirthday;
            if (z && (str = tL_error.text) != null && str.startsWith("FLOOD_WAIT_")) {
                if (getContext() != null) {
                    showDialog(new AlertDialog.Builder(getContext(), this.resourceProvider).setTitle(LocaleController.getString(C2369R.string.PrivacyBirthdayTooOftenTitle)).setMessage(LocaleController.getString(C2369R.string.PrivacyBirthdayTooOftenMessage)).setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null).create());
                }
            } else {
                BulletinFactory.showError(tL_error);
            }
            if (z) {
                if (tL_birthday != null) {
                    userFull.flags |= 32;
                } else {
                    userFull.flags &= -33;
                }
                userFull.birthday = tL_birthday;
                getMessagesStorage().updateUserInfo(userFull, false);
                return;
            }
            return;
        }
        if (tLObject2 instanceof TLRPC.TL_boolFalse) {
            this.doneButtonDrawable.animateToProgress(0.0f);
            BulletinFactory.m1267of(this).createErrorBulletin(LocaleController.getString(C2369R.string.UnknownError)).show();
            return;
        }
        this.wasSaved = true;
        int i = iArr[0] + 1;
        iArr[0] = i;
        if (i == arrayList.size()) {
            lambda$onBackPressed$371();
        }
    }

    public static class AdminedChannelsFetcher {
        public final int currentAccount;
        public final boolean for_personal;
        public boolean loaded;
        public boolean loading;
        public final ArrayList chats = new ArrayList();
        private ArrayList callbacks = new ArrayList();

        public AdminedChannelsFetcher(int i, boolean z) {
            this.currentAccount = i;
            this.for_personal = z;
        }

        public void invalidate() {
            this.loaded = false;
        }

        public void fetch() {
            if (this.loaded || this.loading) {
                return;
            }
            this.loading = true;
            TLRPC.TL_channels_getAdminedPublicChannels tL_channels_getAdminedPublicChannels = new TLRPC.TL_channels_getAdminedPublicChannels();
            tL_channels_getAdminedPublicChannels.for_personal = this.for_personal;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_getAdminedPublicChannels, new RequestDelegate() { // from class: org.telegram.ui.UserInfoActivity$AdminedChannelsFetcher$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$fetch$1(tLObject, tL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$fetch$1(final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.UserInfoActivity$AdminedChannelsFetcher$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$fetch$0(tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$fetch$0(TLObject tLObject) {
            if (tLObject instanceof TLRPC.messages_Chats) {
                this.chats.clear();
                this.chats.addAll(((TLRPC.messages_Chats) tLObject).chats);
            }
            int i = 0;
            MessagesController.getInstance(this.currentAccount).putChats(this.chats, false);
            this.loading = false;
            this.loaded = true;
            ArrayList arrayList = this.callbacks;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Runnable) obj).run();
            }
            this.callbacks.clear();
        }

        public void subscribe(Runnable runnable) {
            if (this.loaded) {
                runnable.run();
            } else {
                this.callbacks.add(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ChooseChannelFragment extends UniversalFragment {
        private AdminedChannelsFetcher channels;
        private boolean invalidateAfterPause = false;
        private String query;
        private ActionBarMenuItem searchItem;
        private long selectedChannel;
        private Utilities.Callback whenSelected;

        @Override // org.telegram.p023ui.Components.UniversalFragment
        protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
            return false;
        }

        public ChooseChannelFragment(AdminedChannelsFetcher adminedChannelsFetcher, long j, Utilities.Callback callback) {
            this.channels = adminedChannelsFetcher;
            this.selectedChannel = j;
            this.whenSelected = callback;
            adminedChannelsFetcher.subscribe(new Runnable() { // from class: org.telegram.ui.UserInfoActivity$ChooseChannelFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            UniversalRecyclerView universalRecyclerView = this.listView;
            if (universalRecyclerView != null) {
                universalRecyclerView.adapter.update(true);
            }
        }

        @Override // org.telegram.p023ui.Components.UniversalFragment, org.telegram.p023ui.ActionBar.BaseFragment
        public View createView(Context context) {
            ActionBarMenuItem actionBarMenuItemSearchListener = this.actionBar.createMenu().addItem(0, C2369R.drawable.ic_ab_search, getResourceProvider()).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItem.ActionBarMenuItemSearchListener() { // from class: org.telegram.ui.UserInfoActivity.ChooseChannelFragment.1
                @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
                public void onSearchExpand() {
                }

                @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
                public void onSearchCollapse() {
                    ChooseChannelFragment.this.query = null;
                    UniversalRecyclerView universalRecyclerView = ChooseChannelFragment.this.listView;
                    if (universalRecyclerView != null) {
                        universalRecyclerView.adapter.update(true);
                    }
                }

                @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
                public void onTextChanged(EditText editText) {
                    ChooseChannelFragment.this.query = editText.getText().toString();
                    UniversalRecyclerView universalRecyclerView = ChooseChannelFragment.this.listView;
                    if (universalRecyclerView != null) {
                        universalRecyclerView.adapter.update(true);
                    }
                }
            });
            this.searchItem = actionBarMenuItemSearchListener;
            actionBarMenuItemSearchListener.setSearchFieldHint(LocaleController.getString(C2369R.string.Search));
            this.searchItem.setContentDescription(LocaleController.getString(C2369R.string.Search));
            this.searchItem.setVisibility(8);
            super.createView(context);
            return this.fragmentView;
        }

        @Override // org.telegram.p023ui.Components.UniversalFragment
        protected CharSequence getTitle() {
            return LocaleController.getString(C2369R.string.EditProfileChannelTitle);
        }

        @Override // org.telegram.p023ui.Components.UniversalFragment
        protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
            if (TextUtils.isEmpty(this.query)) {
                arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.EditProfileChannelSelect)));
            }
            if (TextUtils.isEmpty(this.query) && this.selectedChannel != 0) {
                arrayList.add(UItem.asButton(1, C2369R.drawable.msg_archive_hide, LocaleController.getString(C2369R.string.EditProfileChannelHide)).accent());
            }
            ArrayList arrayList2 = this.channels.chats;
            int size = arrayList2.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                TLRPC.Chat chat = (TLRPC.Chat) obj;
                if (chat != null && !ChatObject.isMegagroup(chat)) {
                    i++;
                    if (!TextUtils.isEmpty(this.query)) {
                        String lowerCase = this.query.toLowerCase();
                        String strTranslitSafe = AndroidUtilities.translitSafe(lowerCase);
                        String lowerCase2 = chat.title.toLowerCase();
                        String strTranslitSafe2 = AndroidUtilities.translitSafe(lowerCase2);
                        if (!lowerCase2.startsWith(lowerCase)) {
                            if (!lowerCase2.contains(" " + lowerCase) && !strTranslitSafe2.startsWith(strTranslitSafe)) {
                                if (!strTranslitSafe2.contains(" " + strTranslitSafe)) {
                                }
                            }
                        }
                    }
                    arrayList.add(UItem.asFilterChat(true, -chat.f1571id).setChecked(this.selectedChannel == chat.f1571id));
                }
            }
            if (TextUtils.isEmpty(this.query) && i == 0) {
                arrayList.add(UItem.asButton(2, C2369R.drawable.msg_channel_create, LocaleController.getString(C2369R.string.EditProfileChannelStartNew)).accent());
            }
            arrayList.add(UItem.asShadow(null));
            ActionBarMenuItem actionBarMenuItem = this.searchItem;
            if (actionBarMenuItem != null) {
                actionBarMenuItem.setVisibility(i <= 5 ? 8 : 0);
            }
        }

        @Override // org.telegram.p023ui.ActionBar.BaseFragment
        public void onResume() {
            super.onResume();
            if (this.invalidateAfterPause) {
                this.channels.invalidate();
                this.channels.subscribe(new Runnable() { // from class: org.telegram.ui.UserInfoActivity$ChooseChannelFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResume$1();
                    }
                });
                this.invalidateAfterPause = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResume$1() {
            UniversalRecyclerView universalRecyclerView = this.listView;
            if (universalRecyclerView != null) {
                universalRecyclerView.adapter.update(true);
            }
        }

        @Override // org.telegram.p023ui.Components.UniversalFragment
        protected void onClick(UItem uItem, View view, int i, float f, float f2) {
            int i2 = uItem.f2017id;
            if (i2 == 1) {
                this.whenSelected.run(null);
                lambda$onBackPressed$371();
                return;
            }
            if (i2 == 2) {
                this.invalidateAfterPause = true;
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                if (!BuildVars.DEBUG_VERSION && globalMainSettings.getBoolean("channel_intro", false)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("step", 0);
                    presentFragment(new ChannelCreateActivity(bundle));
                    return;
                } else {
                    presentFragment(new ActionIntroActivity(0));
                    globalMainSettings.edit().putBoolean("channel_intro", true).apply();
                    return;
                }
            }
            if (uItem.viewType == 12) {
                lambda$onBackPressed$371();
                this.whenSelected.run(getMessagesController().getChat(Long.valueOf(-uItem.dialogId)));
            }
        }
    }
}
