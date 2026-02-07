package org.telegram.p023ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import org.mvel2.MVEL;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.NotificationsSettingsFacade;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BackDrawable;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ActionBar.ThemeDescription;
import org.telegram.p023ui.Adapters.ContactsAdapter;
import org.telegram.p023ui.Adapters.SearchAdapter;
import org.telegram.p023ui.Cells.GraySectionCell;
import org.telegram.p023ui.Cells.LetterSectionCell;
import org.telegram.p023ui.Cells.ProfileSearchCell;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Cells.UserCell;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.EditTextBoldCursor;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.NumberTextView;
import org.telegram.p023ui.Components.RLottieImageView;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.StickerEmptyView;
import org.telegram.p023ui.Stories.StoriesListPlaceProvider;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class ContactsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private boolean allowBots;
    private boolean allowSelf;
    private boolean allowUsernameSearch;
    private int animationIndex;
    private boolean askAboutContacts;
    private BackDrawable backDrawable;
    private AnimatorSet bounceIconAnimator;
    private long channelId;
    private long chatId;
    private boolean checkPermission;
    private boolean createSecretChat;
    private boolean creatingChat;
    private ContactsActivityDelegate delegate;
    private ActionBarMenuItem deleteItem;
    private boolean destroyAfterSelect;
    private boolean disableSections;
    private StickerEmptyView emptyView;
    private RLottieImageView floatingButton;
    private FrameLayout floatingButtonContainer;
    private boolean floatingHidden;
    private AccelerateDecelerateInterpolator floatingInterpolator;
    private LongSparseArray ignoreUsers;
    private String initialSearchString;
    private LinearLayoutManager layoutManager;
    private RecyclerListView listView;
    private ContactsAdapter listViewAdapter;
    private boolean needFinishFragment;
    private boolean needForwardCount;
    private boolean needPhonebook;
    private boolean onlyUsers;
    private AlertDialog permissionDialog;
    private long permissionRequestTime;
    private int prevPosition;
    private int prevTop;
    private boolean resetDelegate;
    private boolean returnAsResult;
    boolean scheduled;
    private boolean scrollUpdated;
    private SearchAdapter searchListViewAdapter;
    private String searchQuery;
    private boolean searchWas;
    private boolean searching;
    private String selectAlertString;
    private LongSparseArray selectedContacts;
    private NumberTextView selectedContactsCountTextView;
    private boolean sortByName;
    Runnable sortContactsRunnable;
    private ActionBarMenuItem sortItem;

    public interface ContactsActivityDelegate {
        void didSelectContact(TLRPC.User user, String str, ContactsActivity contactsActivity);
    }

    public ContactsActivity(Bundle bundle) {
        super(bundle);
        this.floatingInterpolator = new AccelerateDecelerateInterpolator();
        this.allowSelf = true;
        this.allowBots = true;
        this.needForwardCount = true;
        this.needFinishFragment = true;
        this.resetDelegate = true;
        this.selectAlertString = null;
        this.allowUsernameSearch = true;
        this.askAboutContacts = true;
        this.selectedContacts = new LongSparseArray();
        this.checkPermission = true;
        this.animationIndex = -1;
        this.sortContactsRunnable = new Runnable() { // from class: org.telegram.ui.ContactsActivity.14
            @Override // java.lang.Runnable
            public void run() {
                ContactsActivity.this.listViewAdapter.sortOnlineContacts();
                ContactsActivity.this.scheduled = false;
            }
        };
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.contactsDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.storiesUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.encryptedChatCreated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.closeChats);
        this.checkPermission = UserConfig.getInstance(this.currentAccount).syncContacts;
        Bundle bundle = this.arguments;
        if (bundle != null) {
            this.onlyUsers = bundle.getBoolean("onlyUsers", false);
            this.destroyAfterSelect = this.arguments.getBoolean("destroyAfterSelect", false);
            this.returnAsResult = this.arguments.getBoolean("returnAsResult", false);
            this.createSecretChat = this.arguments.getBoolean("createSecretChat", false);
            this.selectAlertString = this.arguments.getString("selectAlertString");
            this.allowUsernameSearch = this.arguments.getBoolean("allowUsernameSearch", true);
            this.needForwardCount = this.arguments.getBoolean("needForwardCount", true);
            this.allowBots = this.arguments.getBoolean("allowBots", true);
            this.allowSelf = this.arguments.getBoolean("allowSelf", true);
            this.channelId = this.arguments.getLong("channelId", 0L);
            this.needFinishFragment = this.arguments.getBoolean("needFinishFragment", true);
            this.chatId = this.arguments.getLong("chat_id", 0L);
            this.disableSections = this.arguments.getBoolean("disableSections", false);
            this.resetDelegate = this.arguments.getBoolean("resetDelegate", false);
        } else {
            this.needPhonebook = true;
        }
        if (!this.createSecretChat && !this.returnAsResult) {
            this.sortByName = SharedConfig.sortContactsByName;
        }
        getContactsController().checkInviteText();
        getContactsController().reloadContactsStatusesMaybe(false);
        MessagesController.getInstance(this.currentAccount).getStoriesController().loadHiddenStories();
        return true;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.contactsDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.storiesUpdated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.encryptedChatCreated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.closeChats);
        this.delegate = null;
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
        getNotificationCenter().onAnimationFinish(this.animationIndex);
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            recyclerListView.setAdapter(null);
            this.listView = null;
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onTransitionAnimationProgress(boolean z, float f) {
        super.onTransitionAnimationProgress(z, f);
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0355  */
    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.view.View createView(android.content.Context r17) {
        /*
            Method dump skipped, instructions count: 887
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.ContactsActivity.createView(android.content.Context):android.view.View");
    }

    /* renamed from: $r8$lambda$-gwuEONy7G8aPHJdHr8aEMJdMMI, reason: not valid java name */
    public static /* synthetic */ boolean m12570$r8$lambda$gwuEONy7G8aPHJdHr8aEMJdMMI(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(int i, View view, int i2, float f, float f2) {
        RecyclerView.Adapter adapter = this.listView.getAdapter();
        SearchAdapter searchAdapter = this.searchListViewAdapter;
        if (adapter == searchAdapter) {
            Object item = searchAdapter.getItem(i2);
            if (!this.selectedContacts.isEmpty() && (view instanceof ProfileSearchCell)) {
                ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
                if (profileSearchCell.getUser() == null || !profileSearchCell.getUser().contact) {
                    return;
                }
                showOrUpdateActionMode(profileSearchCell);
                return;
            }
            if (item instanceof TLRPC.User) {
                TLRPC.User user = (TLRPC.User) item;
                if (this.searchListViewAdapter.isGlobalSearch(i2)) {
                    ArrayList<TLRPC.User> arrayList = new ArrayList<>();
                    arrayList.add(user);
                    getMessagesController().putUsers(arrayList, false);
                    MessagesStorage.getInstance(this.currentAccount).putUsersAndChats(arrayList, null, false, true);
                }
                if (this.returnAsResult) {
                    LongSparseArray longSparseArray = this.ignoreUsers;
                    if (longSparseArray == null || longSparseArray.indexOfKey(user.f1734id) < 0) {
                        didSelectResult(user, true, null);
                        return;
                    }
                    return;
                }
                if (this.createSecretChat) {
                    if (user.f1734id == UserConfig.getInstance(this.currentAccount).getClientUserId()) {
                        return;
                    }
                    this.creatingChat = true;
                    SecretChatHelper.getInstance(this.currentAccount).startSecretChat(getParentActivity(), user);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putLong("user_id", user.f1734id);
                if (getMessagesController().checkCanOpenChat(bundle, this)) {
                    presentFragment(new ChatActivity(bundle), this.needFinishFragment);
                    return;
                }
                return;
            }
            if (item instanceof String) {
                String str = (String) item;
                if (str.equals("section")) {
                    return;
                }
                if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
                    AccountFrozenAlert.show(this.currentAccount);
                    return;
                }
                NewContactBottomSheet newContactBottomSheet = new NewContactBottomSheet(this, getContext());
                newContactBottomSheet.setInitialPhoneNumber(str, true);
                newContactBottomSheet.show();
                return;
            }
            if (item instanceof ContactsController.Contact) {
                ContactsController.Contact contact = (ContactsController.Contact) item;
                AlertsCreator.createContactInviteDialog(this, contact.first_name, contact.last_name, contact.phones.get(0));
                return;
            }
            return;
        }
        int sectionForPosition = this.listViewAdapter.getSectionForPosition(i2);
        int positionInSectionForPosition = this.listViewAdapter.getPositionInSectionForPosition(i2);
        if (positionInSectionForPosition < 0 || sectionForPosition < 0) {
            return;
        }
        if (!this.selectedContacts.isEmpty() && (view instanceof UserCell)) {
            showOrUpdateActionMode((UserCell) view);
            return;
        }
        ContactsAdapter contactsAdapter = this.listViewAdapter;
        boolean z = contactsAdapter.hasStories;
        if (z && sectionForPosition == 1) {
            if (view instanceof UserCell) {
                getOrCreateStoryViewer().open(getContext(), ((UserCell) view).getDialogId(), StoriesListPlaceProvider.m1334of(this.listView));
                return;
            }
            return;
        }
        if (z && sectionForPosition > 1) {
            sectionForPosition--;
        }
        if ((!this.onlyUsers || i != 0) && sectionForPosition == 0) {
            if (this.needPhonebook) {
                if (positionInSectionForPosition == 0) {
                    if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
                        AccountFrozenAlert.show(this.currentAccount);
                        return;
                    } else {
                        presentFragment(new InviteContactsActivity());
                        return;
                    }
                }
                return;
            }
            if (i != 0) {
                if (positionInSectionForPosition == 0) {
                    if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
                        AccountFrozenAlert.show(this.currentAccount);
                        return;
                    }
                    long j = this.chatId;
                    if (j == 0) {
                        j = this.channelId;
                    }
                    presentFragment(new GroupInviteActivity(j));
                    return;
                }
                return;
            }
            if (positionInSectionForPosition == 0) {
                if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
                    AccountFrozenAlert.show(this.currentAccount);
                    return;
                } else {
                    presentFragment(new GroupCreateActivity(new Bundle()), false);
                    return;
                }
            }
            if (positionInSectionForPosition == 1) {
                if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
                    AccountFrozenAlert.show(this.currentAccount);
                    return;
                } else {
                    AndroidUtilities.requestAdjustNothing(getParentActivity(), getClassGuid());
                    new NewContactBottomSheet(this, getContext()) { // from class: org.telegram.ui.ContactsActivity.7
                        @Override // org.telegram.p023ui.ActionBar.BottomSheet
                        public void dismissInternal() {
                            super.dismissInternal();
                            AndroidUtilities.requestAdjustResize(ContactsActivity.this.getParentActivity(), this.classGuid);
                        }
                    }.show();
                    return;
                }
            }
            if (positionInSectionForPosition == 2) {
                if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
                    AccountFrozenAlert.show(this.currentAccount);
                    return;
                }
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                if (!BuildVars.DEBUG_VERSION && globalMainSettings.getBoolean("channel_intro", false)) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("step", 0);
                    presentFragment(new ChannelCreateActivity(bundle2));
                    return;
                } else {
                    presentFragment(new ActionIntroActivity(0));
                    globalMainSettings.edit().putBoolean("channel_intro", true).apply();
                    return;
                }
            }
            return;
        }
        Object item2 = this.listViewAdapter.getItem(contactsAdapter.getSectionForPosition(i2), this.listViewAdapter.getPositionInSectionForPosition(i2));
        if (item2 instanceof TLRPC.User) {
            TLRPC.User user2 = (TLRPC.User) item2;
            if (this.returnAsResult) {
                LongSparseArray longSparseArray2 = this.ignoreUsers;
                if (longSparseArray2 == null || longSparseArray2.indexOfKey(user2.f1734id) < 0) {
                    didSelectResult(user2, true, null);
                    return;
                }
                return;
            }
            if (this.createSecretChat) {
                this.creatingChat = true;
                SecretChatHelper.getInstance(this.currentAccount).startSecretChat(getParentActivity(), user2);
                return;
            }
            Bundle bundle3 = new Bundle();
            bundle3.putLong("user_id", user2.f1734id);
            if (getMessagesController().checkCanOpenChat(bundle3, this)) {
                presentFragment(new ChatActivity(bundle3), this.needFinishFragment);
                return;
            }
            return;
        }
        if (item2 instanceof ContactsController.Contact) {
            ContactsController.Contact contact2 = (ContactsController.Contact) item2;
            final String str2 = !contact2.phones.isEmpty() ? contact2.phones.get(0) : null;
            if (str2 == null || getParentActivity() == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            builder.setMessage(LocaleController.getString(C2369R.string.InviteUser));
            builder.setTitle(LocaleController.getString(C2369R.string.AppName));
            builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda10
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i3) {
                    this.f$0.lambda$createView$1(str2, alertDialog, i3);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            showDialog(builder.create());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(String str, AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts("sms", str, null));
            intent.putExtra("sms_body", ContactsController.getInstance(this.currentAccount).getInviteText(1));
            getParentActivity().startActivityForResult(intent, 500);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* renamed from: org.telegram.ui.ContactsActivity$8 */
    class C49398 implements RecyclerListView.OnItemLongClickListener {
        C49398() {
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
        public boolean onItemClick(View view, int i) {
            if (ContactsActivity.this.listView.getAdapter() == ContactsActivity.this.listViewAdapter) {
                int sectionForPosition = ContactsActivity.this.listViewAdapter.getSectionForPosition(i);
                int positionInSectionForPosition = ContactsActivity.this.listViewAdapter.getPositionInSectionForPosition(i);
                if (Bulletin.getVisibleBulletin() != null) {
                    Bulletin.getVisibleBulletin().hide();
                }
                if (positionInSectionForPosition < 0 || sectionForPosition < 0) {
                    return false;
                }
                if (ContactsActivity.this.listViewAdapter.hasStories && sectionForPosition == 1 && (view instanceof UserCell)) {
                    final long dialogId = ((UserCell) view).getDialogId();
                    final TLRPC.User user = MessagesController.getInstance(((BaseFragment) ContactsActivity.this).currentAccount).getUser(Long.valueOf(dialogId));
                    final String sharedPrefKey = NotificationsController.getSharedPrefKey(dialogId, 0L);
                    boolean zAreStoriesNotMuted = NotificationsCustomSettingsActivity.areStoriesNotMuted(((BaseFragment) ContactsActivity.this).currentAccount, dialogId);
                    ItemOptions itemOptionsAddIf = ItemOptions.makeOptions(ContactsActivity.this, view).setScrimViewBackground(Theme.createRoundRectDrawable(0, 0, Theme.getColor(Theme.key_windowBackgroundWhite))).add(C2369R.drawable.msg_discussion, LocaleController.getString(C2369R.string.SendMessage), new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onItemClick$0(dialogId);
                        }
                    }).add(C2369R.drawable.msg_openprofile, LocaleController.getString(C2369R.string.OpenProfile), new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onItemClick$1(dialogId);
                        }
                    }).addIf(zAreStoriesNotMuted, C2369R.drawable.msg_mute, LocaleController.getString(C2369R.string.NotificationsStoryMute), new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onItemClick$2(sharedPrefKey, dialogId, user);
                        }
                    }).addIf(!zAreStoriesNotMuted, C2369R.drawable.msg_unmute, LocaleController.getString(C2369R.string.NotificationsStoryUnmute), new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onItemClick$3(sharedPrefKey, dialogId, user);
                        }
                    });
                    itemOptionsAddIf.add(C2369R.drawable.msg_viewintopic, LocaleController.getString(C2369R.string.ShowInChats), new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onItemClick$6(dialogId, user);
                        }
                    });
                    itemOptionsAddIf.setGravity(5).show();
                    return true;
                }
            }
            if (!ContactsActivity.this.returnAsResult && !ContactsActivity.this.createSecretChat && (view instanceof UserCell)) {
                ContactsActivity.this.showOrUpdateActionMode((UserCell) view);
                return true;
            }
            if (ContactsActivity.this.returnAsResult || ContactsActivity.this.createSecretChat || !(view instanceof ProfileSearchCell)) {
                return false;
            }
            ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
            if (profileSearchCell.getUser() != null && profileSearchCell.getUser().contact) {
                ContactsActivity.this.showOrUpdateActionMode(profileSearchCell);
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$0(long j) {
            ContactsActivity.this.presentFragment(ChatActivity.m1258of(j));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$1(long j) {
            ContactsActivity.this.presentFragment(ProfileActivity.m1314of(j));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$2(String str, long j, TLRPC.User user) {
            MessagesController.getNotificationsSettings(((BaseFragment) ContactsActivity.this).currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, false).apply();
            ContactsActivity.this.getNotificationsController().updateServerNotificationsSettings(j, 0L);
            String strTrim = user == null ? "" : user.first_name.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            BulletinFactory.m1267of(ContactsActivity.this).createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryMutedHint", C2369R.string.NotificationsStoryMutedHint, strTrim))).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$3(String str, long j, TLRPC.User user) {
            MessagesController.getNotificationsSettings(((BaseFragment) ContactsActivity.this).currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, true).apply();
            ContactsActivity.this.getNotificationsController().updateServerNotificationsSettings(j, 0L);
            String strTrim = user == null ? "" : user.first_name.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            BulletinFactory.m1267of(ContactsActivity.this).createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryUnmutedHint", C2369R.string.NotificationsStoryUnmutedHint, strTrim))).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$6(final long j, TLRPC.User user) {
            ContactsActivity.this.getMessagesController().getStoriesController().toggleHidden(j, false, false, true);
            BulletinFactory.UndoObject undoObject = new BulletinFactory.UndoObject();
            undoObject.onUndo = new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemClick$4(j);
                }
            };
            undoObject.onAction = new Runnable() { // from class: org.telegram.ui.ContactsActivity$8$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemClick$5(j);
                }
            };
            BulletinFactory.global().createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("StoriesMovedToDialogs", C2369R.string.StoriesMovedToDialogs, ContactsController.formatName(user.first_name, null, 20))), null, undoObject).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$4(long j) {
            ContactsActivity.this.getMessagesController().getStoriesController().toggleHidden(j, true, false, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$5(long j) {
            ContactsActivity.this.getMessagesController().getStoriesController().toggleHidden(j, false, true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(View view) {
        AndroidUtilities.requestAdjustNothing(getParentActivity(), getClassGuid());
        if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
            AccountFrozenAlert.show(this.currentAccount);
        } else {
            new NewContactBottomSheet(this, getContext()) { // from class: org.telegram.ui.ContactsActivity.10
                @Override // org.telegram.p023ui.ActionBar.BottomSheet
                public void dismissInternal() {
                    super.dismissInternal();
                    AndroidUtilities.requestAdjustResize(ContactsActivity.this.getParentActivity(), this.classGuid);
                }
            }.show();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public ActionBar createActionBar(Context context) {
        ActionBar actionBarCreateActionBar = super.createActionBar(context);
        actionBarCreateActionBar.setBackground(null);
        actionBarCreateActionBar.setAddToContainer(false);
        return actionBarCreateActionBar;
    }

    public boolean addOrRemoveSelectedContact(UserCell userCell) {
        long dialogId = userCell.getDialogId();
        if (this.selectedContacts.indexOfKey(dialogId) >= 0) {
            this.selectedContacts.remove(dialogId);
            userCell.setChecked(false, true);
            return false;
        }
        if (!(userCell.getCurrentObject() instanceof TLRPC.User)) {
            return false;
        }
        this.selectedContacts.put(dialogId, (TLRPC.User) userCell.getCurrentObject());
        userCell.setChecked(true, true);
        return true;
    }

    public boolean addOrRemoveSelectedContact(ProfileSearchCell profileSearchCell) {
        long dialogId = profileSearchCell.getDialogId();
        if (this.selectedContacts.indexOfKey(dialogId) >= 0) {
            this.selectedContacts.remove(dialogId);
            profileSearchCell.setChecked(false, true);
            return false;
        }
        if (profileSearchCell.getUser() == null) {
            return false;
        }
        this.selectedContacts.put(dialogId, profileSearchCell.getUser());
        profileSearchCell.setChecked(true, true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrUpdateActionMode(Object obj) {
        boolean zAddOrRemoveSelectedContact;
        if (obj instanceof UserCell) {
            zAddOrRemoveSelectedContact = addOrRemoveSelectedContact((UserCell) obj);
        } else if (!(obj instanceof ProfileSearchCell)) {
            return;
        } else {
            zAddOrRemoveSelectedContact = addOrRemoveSelectedContact((ProfileSearchCell) obj);
        }
        boolean z = true;
        if (!this.actionBar.isActionModeShowed()) {
            if (zAddOrRemoveSelectedContact) {
                AndroidUtilities.hideKeyboard(this.fragmentView.findFocus());
                this.actionBar.showActionMode();
                this.backDrawable.setRotation(1.0f, true);
            }
            z = false;
        } else if (this.selectedContacts.isEmpty()) {
            hideActionMode();
            return;
        }
        this.selectedContactsCountTextView.setNumber(this.selectedContacts.size(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideActionMode() {
        this.actionBar.hideActionMode();
        int childCount = this.listView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof UserCell) {
                UserCell userCell = (UserCell) childAt;
                if (this.selectedContacts.indexOfKey(userCell.getDialogId()) >= 0) {
                    userCell.setChecked(false, true);
                }
            } else if (childAt instanceof ProfileSearchCell) {
                ProfileSearchCell profileSearchCell = (ProfileSearchCell) childAt;
                if (this.selectedContacts.indexOfKey(profileSearchCell.getDialogId()) >= 0) {
                    profileSearchCell.setChecked(false, true);
                }
            }
        }
        this.selectedContacts.clear();
        this.backDrawable.setRotation(0.0f, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performSelectedContactsDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), getResourceProvider());
        if (this.selectedContacts.size() == 1) {
            builder.setTitle(LocaleController.getString(C2369R.string.DeleteContactTitle));
            builder.setMessage(LocaleController.getString(C2369R.string.DeleteContactSubtitle));
        } else {
            builder.setTitle(LocaleController.formatPluralString("DeleteContactsTitle", this.selectedContacts.size(), new Object[0]));
            builder.setMessage(LocaleController.getString(C2369R.string.DeleteContactsSubtitle));
        }
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda8
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$performSelectedContactsDelete$4(alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda9
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                alertDialog.dismiss();
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.show();
        alertDialogCreate.redPositive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedContactsDelete$4(AlertDialog alertDialog, int i) {
        ArrayList<TLRPC.User> arrayList = new ArrayList<>(this.selectedContacts.size());
        for (int i2 = 0; i2 < this.selectedContacts.size(); i2++) {
            arrayList.add((TLRPC.User) this.selectedContacts.get(this.selectedContacts.keyAt(i2)));
        }
        getContactsController().deleteContactsUndoable(getContext(), this, arrayList);
        hideActionMode();
    }

    private void didSelectResult(final TLRPC.User user, boolean z, final String str) {
        final EditTextBoldCursor editTextBoldCursor;
        if (z && this.selectAlertString != null) {
            if (getParentActivity() == null) {
                return;
            }
            if (user.bot) {
                if (user.bot_nochats) {
                    try {
                        BulletinFactory.m1267of(this).createErrorBulletin(LocaleController.getString(C2369R.string.BotCantJoinGroups)).show();
                        return;
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                        return;
                    }
                }
                if (this.channelId != 0) {
                    TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(this.channelId));
                    AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                    if (ChatObject.canAddAdmins(chat)) {
                        builder.setTitle(LocaleController.getString(C2369R.string.AddBotAdminAlert));
                        builder.setMessage(LocaleController.getString(C2369R.string.AddBotAsAdmin));
                        builder.setPositiveButton(LocaleController.getString(C2369R.string.AddAsAdmin), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda11
                            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                            public final void onClick(AlertDialog alertDialog, int i) {
                                this.f$0.lambda$didSelectResult$6(user, str, alertDialog, i);
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
                    } else {
                        builder.setMessage(LocaleController.getString(C2369R.string.CantAddBotAsAdmin));
                        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
                    }
                    showDialog(builder.create());
                    return;
                }
            }
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getParentActivity());
            builder2.setTitle(LocaleController.getString(C2369R.string.AppName));
            String stringSimple = LocaleController.formatStringSimple(this.selectAlertString, UserObject.getUserName(user));
            if (user.bot || !this.needForwardCount) {
                editTextBoldCursor = null;
            } else {
                stringSimple = String.format("%s\n\n%s", stringSimple, LocaleController.getString(C2369R.string.AddToTheGroupForwardCount));
                editTextBoldCursor = new EditTextBoldCursor(getParentActivity());
                editTextBoldCursor.setTextSize(1, 18.0f);
                editTextBoldCursor.setText("50");
                editTextBoldCursor.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                editTextBoldCursor.setGravity(17);
                editTextBoldCursor.setInputType(2);
                editTextBoldCursor.setImeOptions(6);
                editTextBoldCursor.setBackgroundDrawable(Theme.createEditTextDrawable(getParentActivity(), true));
                editTextBoldCursor.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.ContactsActivity.12
                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        try {
                            String string = editable.toString();
                            if (string.length() != 0) {
                                int iIntValue = Utilities.parseInt((CharSequence) string).intValue();
                                if (iIntValue < 0) {
                                    editTextBoldCursor.setText(MVEL.VERSION_SUB);
                                    EditText editText = editTextBoldCursor;
                                    editText.setSelection(editText.length());
                                    return;
                                }
                                if (iIntValue > 300) {
                                    editTextBoldCursor.setText("300");
                                    EditText editText2 = editTextBoldCursor;
                                    editText2.setSelection(editText2.length());
                                    return;
                                }
                                if (string.equals("" + iIntValue)) {
                                    return;
                                }
                                editTextBoldCursor.setText("" + iIntValue);
                                EditText editText3 = editTextBoldCursor;
                                editText3.setSelection(editText3.length());
                            }
                        } catch (Exception e2) {
                            FileLog.m1160e(e2);
                        }
                    }
                });
                builder2.setView(editTextBoldCursor);
            }
            builder2.setMessage(stringSimple);
            builder2.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda12
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$didSelectResult$7(user, editTextBoldCursor, alertDialog, i);
                }
            });
            builder2.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            showDialog(builder2.create());
            if (editTextBoldCursor != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) editTextBoldCursor.getLayoutParams();
                if (marginLayoutParams != null) {
                    if (marginLayoutParams instanceof FrameLayout.LayoutParams) {
                        ((FrameLayout.LayoutParams) marginLayoutParams).gravity = 1;
                    }
                    int iM1146dp = AndroidUtilities.m1146dp(24.0f);
                    marginLayoutParams.leftMargin = iM1146dp;
                    marginLayoutParams.rightMargin = iM1146dp;
                    marginLayoutParams.height = AndroidUtilities.m1146dp(36.0f);
                    editTextBoldCursor.setLayoutParams(marginLayoutParams);
                }
                editTextBoldCursor.setSelection(editTextBoldCursor.getText().length());
                return;
            }
            return;
        }
        ContactsActivityDelegate contactsActivityDelegate = this.delegate;
        if (contactsActivityDelegate != null) {
            contactsActivityDelegate.didSelectContact(user, str, this);
            if (this.resetDelegate) {
                this.delegate = null;
            }
        }
        if (this.needFinishFragment) {
            lambda$onBackPressed$371();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$6(TLRPC.User user, String str, AlertDialog alertDialog, int i) {
        ContactsActivityDelegate contactsActivityDelegate = this.delegate;
        if (contactsActivityDelegate != null) {
            contactsActivityDelegate.didSelectContact(user, str, this);
            this.delegate = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$7(TLRPC.User user, EditText editText, AlertDialog alertDialog, int i) {
        didSelectResult(user, false, editText != null ? editText.getText().toString() : MVEL.VERSION_SUB);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (this.actionBar.isActionModeShowed()) {
            hideActionMode();
            return false;
        }
        return super.onBackPressed();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        Activity parentActivity;
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        ContactsAdapter contactsAdapter = this.listViewAdapter;
        if (contactsAdapter != null) {
            contactsAdapter.notifyDataSetChanged();
        }
        if (!this.checkPermission || Build.VERSION.SDK_INT < 23 || (parentActivity = getParentActivity()) == null) {
            return;
        }
        this.checkPermission = false;
        if (parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0) {
            if (parentActivity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                AlertDialog alertDialogCreate = AlertsCreator.createContactsPermissionDialog(parentActivity, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.MessagesStorage.IntCallback
                    public final void run(int i) {
                        this.f$0.lambda$onResume$8(i);
                    }
                }).create();
                this.permissionDialog = alertDialogCreate;
                showDialog(alertDialogCreate);
                return;
            }
            askForPermissons(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$8(int i) {
        this.askAboutContacts = i != 0;
        if (i == 0) {
            return;
        }
        askForPermissons(false);
    }

    protected RecyclerListView getListView() {
        return this.listView;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        FrameLayout frameLayout = this.floatingButtonContainer;
        if (frameLayout != null) {
            frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.telegram.ui.ContactsActivity.13
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ContactsActivity.this.floatingButtonContainer.setTranslationY(ContactsActivity.this.floatingHidden ? AndroidUtilities.m1146dp(100.0f) : 0);
                    ContactsActivity.this.floatingButtonContainer.setClickable(!ContactsActivity.this.floatingHidden);
                    if (ContactsActivity.this.floatingButtonContainer != null) {
                        ContactsActivity.this.floatingButtonContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    protected void onDialogDismiss(Dialog dialog) {
        super.onDialogDismiss(dialog);
        AlertDialog alertDialog = this.permissionDialog;
        if (alertDialog == null || dialog != alertDialog || getParentActivity() == null || !this.askAboutContacts) {
            return;
        }
        askForPermissons(false);
    }

    private void askForPermissons(boolean z) {
        Activity parentActivity = getParentActivity();
        if (parentActivity == null || !UserConfig.getInstance(this.currentAccount).syncContacts || parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") == 0) {
            return;
        }
        if (z && this.askAboutContacts) {
            showDialog(AlertsCreator.createContactsPermissionDialog(parentActivity, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda7
                @Override // org.telegram.messenger.MessagesStorage.IntCallback
                public final void run(int i) {
                    this.f$0.lambda$askForPermissons$9(i);
                }
            }).create());
            return;
        }
        this.permissionRequestTime = SystemClock.elapsedRealtime();
        ArrayList arrayList = new ArrayList();
        arrayList.add("android.permission.READ_CONTACTS");
        arrayList.add("android.permission.WRITE_CONTACTS");
        arrayList.add("android.permission.GET_ACCOUNTS");
        try {
            parentActivity.requestPermissions((String[]) arrayList.toArray(new String[0]), 1);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$askForPermissons$9(int i) {
        this.askAboutContacts = i != 0;
        if (i == 0) {
            return;
        }
        askForPermissons(false);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (iArr.length > i2 && "android.permission.READ_CONTACTS".equals(strArr[i2])) {
                    if (iArr[i2] == 0) {
                        ContactsController.getInstance(this.currentAccount).forceImportContacts();
                        return;
                    }
                    SharedPreferences.Editor editorEdit = MessagesController.getGlobalNotificationsSettings().edit();
                    this.askAboutContacts = false;
                    editorEdit.putBoolean("askAboutContacts", false).apply();
                    if (SystemClock.elapsedRealtime() - this.permissionRequestTime < 200) {
                        try {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", ApplicationLoader.applicationContext.getPackageName(), null));
                            getParentActivity().startActivity(intent);
                            return;
                        } catch (Exception e) {
                            FileLog.m1160e(e);
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        ActionBar actionBar = this.actionBar;
        if (actionBar != null) {
            actionBar.closeSearchField();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.storiesUpdated) {
            ContactsAdapter contactsAdapter = this.listViewAdapter;
            if (contactsAdapter != null) {
                contactsAdapter.setStories(getMessagesController().getStoriesController().getHiddenList(), true);
            }
            MessagesController.getInstance(this.currentAccount).getStoriesController().loadHiddenStories();
            return;
        }
        if (i == NotificationCenter.contactsDidLoad) {
            ContactsAdapter contactsAdapter2 = this.listViewAdapter;
            if (contactsAdapter2 != null) {
                if (!this.sortByName) {
                    contactsAdapter2.setSortType(2, true);
                }
                this.listViewAdapter.notifyDataSetChanged();
            }
            if (this.searchListViewAdapter != null) {
                RecyclerView.Adapter adapter = this.listView.getAdapter();
                SearchAdapter searchAdapter = this.searchListViewAdapter;
                if (adapter == searchAdapter) {
                    searchAdapter.searchDialogs(this.searchQuery);
                    return;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.updateInterfaces) {
            int iIntValue = ((Integer) objArr[0]).intValue();
            if ((MessagesController.UPDATE_MASK_AVATAR & iIntValue) != 0 || (MessagesController.UPDATE_MASK_NAME & iIntValue) != 0 || (MessagesController.UPDATE_MASK_STATUS & iIntValue) != 0) {
                updateVisibleRows(iIntValue);
            }
            if ((iIntValue & MessagesController.UPDATE_MASK_STATUS) == 0 || this.sortByName || this.listViewAdapter == null) {
                return;
            }
            scheduleSort();
            return;
        }
        if (i == NotificationCenter.encryptedChatCreated) {
            if (this.createSecretChat && this.creatingChat) {
                TLRPC.EncryptedChat encryptedChat = (TLRPC.EncryptedChat) objArr[0];
                Bundle bundle = new Bundle();
                bundle.putInt("enc_id", encryptedChat.f1583id);
                NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
                presentFragment(new ChatActivity(bundle), false);
                return;
            }
            return;
        }
        if (i != NotificationCenter.closeChats || this.creatingChat) {
            return;
        }
        removeSelfFromStack(true);
    }

    private void scheduleSort() {
        if (this.scheduled) {
            return;
        }
        this.scheduled = true;
        AndroidUtilities.cancelRunOnUIThread(this.sortContactsRunnable);
        AndroidUtilities.runOnUIThread(this.sortContactsRunnable, 5000L);
    }

    private void updateVisibleRows(int i) {
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            int childCount = recyclerListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.listView.getChildAt(i2);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideFloatingButton(boolean z) {
        if (this.floatingHidden == z) {
            return;
        }
        this.floatingHidden = z;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.floatingButtonContainer, (Property<FrameLayout, Float>) View.TRANSLATION_Y, this.floatingHidden ? AndroidUtilities.m1146dp(100.0f) : 0));
        animatorSet.setDuration(300L);
        animatorSet.setInterpolator(this.floatingInterpolator);
        this.floatingButtonContainer.setClickable(!z);
        animatorSet.start();
    }

    public void setDelegate(ContactsActivityDelegate contactsActivityDelegate) {
        this.delegate = contactsActivityDelegate;
    }

    public void setInitialSearchString(String str) {
        this.initialSearchString = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showItemsAnimated() {
        LinearLayoutManager linearLayoutManager = this.layoutManager;
        final int iFindLastVisibleItemPosition = linearLayoutManager == null ? 0 : linearLayoutManager.findLastVisibleItemPosition();
        this.listView.invalidate();
        this.listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.ContactsActivity.15
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ContactsActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
                int childCount = ContactsActivity.this.listView.getChildCount();
                AnimatorSet animatorSet = new AnimatorSet();
                for (int i = 0; i < childCount; i++) {
                    View childAt = ContactsActivity.this.listView.getChildAt(i);
                    if (ContactsActivity.this.listView.getChildAdapterPosition(childAt) > iFindLastVisibleItemPosition) {
                        childAt.setAlpha(0.0f);
                        int iMin = (int) ((Math.min(ContactsActivity.this.listView.getMeasuredHeight(), Math.max(0, childAt.getTop())) / ContactsActivity.this.listView.getMeasuredHeight()) * 100.0f);
                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(childAt, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f);
                        objectAnimatorOfFloat.setStartDelay(iMin);
                        objectAnimatorOfFloat.setDuration(200L);
                        animatorSet.playTogether(objectAnimatorOfFloat);
                    }
                }
                animatorSet.start();
                return true;
            }
        });
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public AnimatorSet onCustomTransitionAnimation(final boolean z, final Runnable runnable) {
        final ValueAnimator valueAnimatorOfFloat;
        float[] fArr = {0.0f, 1.0f};
        if (z) {
            // fill-array-data instruction
            fArr[0] = 1.0f;
            fArr[1] = 0.0f;
            valueAnimatorOfFloat = ValueAnimator.ofFloat(fArr);
        } else {
            valueAnimatorOfFloat = ValueAnimator.ofFloat(fArr);
        }
        final ViewGroup viewGroup = (ViewGroup) this.fragmentView.getParent();
        BaseFragment baseFragment = this.parentLayout.getFragmentStack().size() > 1 ? (BaseFragment) this.parentLayout.getFragmentStack().get(this.parentLayout.getFragmentStack().size() - 2) : null;
        DialogsActivity dialogsActivity = baseFragment instanceof DialogsActivity ? (DialogsActivity) baseFragment : null;
        if (dialogsActivity == null) {
            return null;
        }
        final boolean z2 = dialogsActivity.storiesEnabled;
        final RLottieImageView floatingButton = dialogsActivity.getFloatingButton();
        final View view = floatingButton.getParent() != null ? (View) floatingButton.getParent() : null;
        if (this.floatingButton != null && (this.floatingButtonContainer == null || view == null || floatingButton.getVisibility() != 0 || Math.abs(view.getTranslationY()) > AndroidUtilities.m1146dp(4.0f) || Math.abs(this.floatingButtonContainer.getTranslationY()) > AndroidUtilities.m1146dp(4.0f))) {
            if (z2) {
                this.floatingButton.setAnimation(C2369R.raw.write_contacts_fab_icon_camera, 56, 56);
            } else {
                this.floatingButton.setAnimation(C2369R.raw.write_contacts_fab_icon, 52, 52);
            }
            return null;
        }
        view.setVisibility(8);
        if (z) {
            viewGroup.setAlpha(0.0f);
        }
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ContactsActivity.$r8$lambda$15dggOJC1MPSrxls8qzQ_bEQRUQ(valueAnimatorOfFloat, viewGroup, valueAnimator);
            }
        });
        FrameLayout frameLayout = this.floatingButtonContainer;
        if (frameLayout != null) {
            ((ViewGroup) this.fragmentView).removeView(frameLayout);
            this.parentLayout.getOverlayContainerView().addView(this.floatingButtonContainer, makeLayoutParamsForFloatingContainer(AndroidUtilities.navigationBarHeight));
        }
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.5f));
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.ContactsActivity.16
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (ContactsActivity.this.floatingButtonContainer != null) {
                    if (ContactsActivity.this.floatingButtonContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) ContactsActivity.this.floatingButtonContainer.getParent()).removeView(ContactsActivity.this.floatingButtonContainer);
                    }
                    ContactsActivity contactsActivity = ContactsActivity.this;
                    ((ViewGroup) contactsActivity.fragmentView).addView(contactsActivity.floatingButtonContainer, ContactsActivity.makeLayoutParamsForFloatingContainer(0));
                    view.setVisibility(0);
                    if (!z) {
                        if (z2) {
                            floatingButton.setAnimation(C2369R.raw.write_contacts_fab_icon_reverse_camera, 56, 56);
                        } else {
                            floatingButton.setAnimation(C2369R.raw.write_contacts_fab_icon_reverse, 52, 52);
                        }
                        floatingButton.getAnimatedDrawable().setCurrentFrame(ContactsActivity.this.floatingButton.getAnimatedDrawable().getCurrentFrame());
                        floatingButton.playAnimation();
                    }
                }
                runnable.run();
            }
        });
        animatorSet.playTogether(valueAnimatorOfFloat);
        final View view2 = view;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCustomTransitionAnimation$11(animatorSet, z2, z, view2);
            }
        }, 50L);
        return animatorSet;
    }

    public static /* synthetic */ void $r8$lambda$15dggOJC1MPSrxls8qzQ_bEQRUQ(ValueAnimator valueAnimator, ViewGroup viewGroup, ValueAnimator valueAnimator2) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        viewGroup.setTranslationX(AndroidUtilities.m1146dp(48.0f) * fFloatValue);
        viewGroup.setAlpha(1.0f - fFloatValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCustomTransitionAnimation$11(AnimatorSet animatorSet, boolean z, boolean z2, final View view) {
        char c;
        if (this.floatingButton == null) {
            return;
        }
        this.animationIndex = getNotificationCenter().setAnimationInProgress(this.animationIndex, new int[]{NotificationCenter.diceStickersDidLoad}, false);
        animatorSet.start();
        if (z) {
            this.floatingButton.setAnimation(z2 ? C2369R.raw.write_contacts_fab_icon_camera : C2369R.raw.write_contacts_fab_icon_reverse_camera, 56, 56);
        } else {
            this.floatingButton.setAnimation(z2 ? C2369R.raw.write_contacts_fab_icon : C2369R.raw.write_contacts_fab_icon_reverse, 52, 52);
        }
        this.floatingButton.playAnimation();
        AnimatorSet animatorSet2 = this.bounceIconAnimator;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        this.bounceIconAnimator = new AnimatorSet();
        float duration = this.floatingButton.getAnimatedDrawable().getDuration();
        long duration2 = 0;
        if (z2) {
            for (int i = 0; i < 6; i++) {
                AnimatorSet animatorSet3 = new AnimatorSet();
                if (i == 0) {
                    RLottieImageView rLottieImageView = this.floatingButton;
                    Property property = View.SCALE_X;
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(rLottieImageView, (Property<RLottieImageView, Float>) property, 1.0f, 0.9f);
                    RLottieImageView rLottieImageView2 = this.floatingButton;
                    c = 0;
                    Property property2 = View.SCALE_Y;
                    animatorSet3.playTogether(objectAnimatorOfFloat, ObjectAnimator.ofFloat(rLottieImageView2, (Property<RLottieImageView, Float>) property2, 1.0f, 0.9f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property, 1.0f, 0.9f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property2, 1.0f, 0.9f));
                    animatorSet3.setDuration((long) (0.12765957f * duration));
                    animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                } else {
                    c = 0;
                    if (i == 1) {
                        RLottieImageView rLottieImageView3 = this.floatingButton;
                        Property property3 = View.SCALE_X;
                        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(rLottieImageView3, (Property<RLottieImageView, Float>) property3, 0.9f, 1.06f);
                        RLottieImageView rLottieImageView4 = this.floatingButton;
                        Property property4 = View.SCALE_Y;
                        animatorSet3.playTogether(objectAnimatorOfFloat2, ObjectAnimator.ofFloat(rLottieImageView4, (Property<RLottieImageView, Float>) property4, 0.9f, 1.06f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property3, 0.9f, 1.06f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property4, 0.9f, 1.06f));
                        animatorSet3.setDuration((long) (0.3617021f * duration));
                        animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                    } else if (i == 2) {
                        RLottieImageView rLottieImageView5 = this.floatingButton;
                        Property property5 = View.SCALE_X;
                        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(rLottieImageView5, (Property<RLottieImageView, Float>) property5, 1.06f, 0.9f);
                        RLottieImageView rLottieImageView6 = this.floatingButton;
                        Property property6 = View.SCALE_Y;
                        animatorSet3.playTogether(objectAnimatorOfFloat3, ObjectAnimator.ofFloat(rLottieImageView6, (Property<RLottieImageView, Float>) property6, 1.06f, 0.9f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property5, 1.06f, 0.9f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property6, 1.06f, 0.9f));
                        animatorSet3.setDuration((long) (0.21276596f * duration));
                        animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                    } else if (i == 3) {
                        RLottieImageView rLottieImageView7 = this.floatingButton;
                        Property property7 = View.SCALE_X;
                        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(rLottieImageView7, (Property<RLottieImageView, Float>) property7, 0.9f, 1.03f);
                        RLottieImageView rLottieImageView8 = this.floatingButton;
                        Property property8 = View.SCALE_Y;
                        animatorSet3.playTogether(objectAnimatorOfFloat4, ObjectAnimator.ofFloat(rLottieImageView8, (Property<RLottieImageView, Float>) property8, 0.9f, 1.03f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property7, 0.9f, 1.03f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property8, 0.9f, 1.03f));
                        animatorSet3.setDuration((long) (duration * 0.10638298f));
                        animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                    } else if (i == 4) {
                        RLottieImageView rLottieImageView9 = this.floatingButton;
                        Property property9 = View.SCALE_X;
                        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(rLottieImageView9, (Property<RLottieImageView, Float>) property9, 1.03f, 0.98f);
                        RLottieImageView rLottieImageView10 = this.floatingButton;
                        Property property10 = View.SCALE_Y;
                        animatorSet3.playTogether(objectAnimatorOfFloat5, ObjectAnimator.ofFloat(rLottieImageView10, (Property<RLottieImageView, Float>) property10, 1.03f, 0.98f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property9, 1.03f, 0.98f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property10, 1.03f, 0.98f));
                        animatorSet3.setDuration((long) (duration * 0.10638298f));
                        animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                    } else {
                        RLottieImageView rLottieImageView11 = this.floatingButton;
                        Property property11 = View.SCALE_X;
                        ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(rLottieImageView11, (Property<RLottieImageView, Float>) property11, 0.98f, 1.0f);
                        RLottieImageView rLottieImageView12 = this.floatingButton;
                        Property property12 = View.SCALE_Y;
                        animatorSet3.playTogether(objectAnimatorOfFloat6, ObjectAnimator.ofFloat(rLottieImageView12, (Property<RLottieImageView, Float>) property12, 0.98f, 1.0f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property11, 0.98f, 1.0f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property12, 0.98f, 1.0f));
                        animatorSet3.setDuration((long) (0.08510638f * duration));
                        animatorSet3.setInterpolator(CubicBezierInterpolator.EASE_IN);
                    }
                }
                animatorSet3.setStartDelay(duration2);
                duration2 += animatorSet3.getDuration();
                AnimatorSet animatorSet4 = this.bounceIconAnimator;
                Animator[] animatorArr = new Animator[1];
                animatorArr[c] = animatorSet3;
                animatorSet4.playTogether(animatorArr);
            }
        } else {
            for (int i2 = 0; i2 < 5; i2++) {
                AnimatorSet animatorSet5 = new AnimatorSet();
                if (i2 == 0) {
                    RLottieImageView rLottieImageView13 = this.floatingButton;
                    Property property13 = View.SCALE_X;
                    ObjectAnimator objectAnimatorOfFloat7 = ObjectAnimator.ofFloat(rLottieImageView13, (Property<RLottieImageView, Float>) property13, 1.0f, 0.9f);
                    RLottieImageView rLottieImageView14 = this.floatingButton;
                    Property property14 = View.SCALE_Y;
                    animatorSet5.playTogether(objectAnimatorOfFloat7, ObjectAnimator.ofFloat(rLottieImageView14, (Property<RLottieImageView, Float>) property14, 1.0f, 0.9f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property13, 1.0f, 0.9f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property14, 1.0f, 0.9f));
                    animatorSet5.setDuration((long) (duration * 0.19444445f));
                    animatorSet5.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                } else if (i2 == 1) {
                    RLottieImageView rLottieImageView15 = this.floatingButton;
                    Property property15 = View.SCALE_X;
                    ObjectAnimator objectAnimatorOfFloat8 = ObjectAnimator.ofFloat(rLottieImageView15, (Property<RLottieImageView, Float>) property15, 0.9f, 1.06f);
                    RLottieImageView rLottieImageView16 = this.floatingButton;
                    Property property16 = View.SCALE_Y;
                    animatorSet5.playTogether(objectAnimatorOfFloat8, ObjectAnimator.ofFloat(rLottieImageView16, (Property<RLottieImageView, Float>) property16, 0.9f, 1.06f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property15, 0.9f, 1.06f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property16, 0.9f, 1.06f));
                    animatorSet5.setDuration((long) (0.22222222f * duration));
                    animatorSet5.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                } else if (i2 == 2) {
                    RLottieImageView rLottieImageView17 = this.floatingButton;
                    Property property17 = View.SCALE_X;
                    ObjectAnimator objectAnimatorOfFloat9 = ObjectAnimator.ofFloat(rLottieImageView17, (Property<RLottieImageView, Float>) property17, 1.06f, 0.92f);
                    RLottieImageView rLottieImageView18 = this.floatingButton;
                    Property property18 = View.SCALE_Y;
                    animatorSet5.playTogether(objectAnimatorOfFloat9, ObjectAnimator.ofFloat(rLottieImageView18, (Property<RLottieImageView, Float>) property18, 1.06f, 0.92f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property17, 1.06f, 0.92f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property18, 1.06f, 0.92f));
                    animatorSet5.setDuration((long) (duration * 0.19444445f));
                    animatorSet5.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                } else if (i2 == 3) {
                    RLottieImageView rLottieImageView19 = this.floatingButton;
                    Property property19 = View.SCALE_X;
                    ObjectAnimator objectAnimatorOfFloat10 = ObjectAnimator.ofFloat(rLottieImageView19, (Property<RLottieImageView, Float>) property19, 0.92f, 1.02f);
                    RLottieImageView rLottieImageView20 = this.floatingButton;
                    Property property20 = View.SCALE_Y;
                    animatorSet5.playTogether(objectAnimatorOfFloat10, ObjectAnimator.ofFloat(rLottieImageView20, (Property<RLottieImageView, Float>) property20, 0.92f, 1.02f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property19, 0.92f, 1.02f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property20, 0.92f, 1.02f));
                    animatorSet5.setDuration((long) (0.25f * duration));
                    animatorSet5.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
                } else {
                    RLottieImageView rLottieImageView21 = this.floatingButton;
                    Property property21 = View.SCALE_X;
                    ObjectAnimator objectAnimatorOfFloat11 = ObjectAnimator.ofFloat(rLottieImageView21, (Property<RLottieImageView, Float>) property21, 1.02f, 1.0f);
                    RLottieImageView rLottieImageView22 = this.floatingButton;
                    Property property22 = View.SCALE_Y;
                    animatorSet5.playTogether(objectAnimatorOfFloat11, ObjectAnimator.ofFloat(rLottieImageView22, (Property<RLottieImageView, Float>) property22, 1.02f, 1.0f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property21, 1.02f, 1.0f), ObjectAnimator.ofFloat(view, (Property<View, Float>) property22, 1.02f, 1.0f));
                    animatorSet5.setDuration((long) (duration * 0.10638298f));
                    animatorSet5.setInterpolator(CubicBezierInterpolator.EASE_IN);
                }
                animatorSet5.setStartDelay(duration2);
                duration2 += animatorSet5.getDuration();
                this.bounceIconAnimator.playTogether(animatorSet5);
            }
        }
        this.bounceIconAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.ContactsActivity.17
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ContactsActivity.this.floatingButton.setScaleX(1.0f);
                ContactsActivity.this.floatingButton.setScaleY(1.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                ContactsActivity.this.bounceIconAnimator = null;
                ContactsActivity.this.getNotificationCenter().onAnimationFinish(ContactsActivity.this.animationIndex);
            }
        });
        this.bounceIconAnimator.start();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        ArrayList arrayList = new ArrayList();
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.ContactsActivity$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.lambda$getThemeDescriptions$12();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        ActionBar actionBar = this.actionBar;
        int i = ThemeDescription.FLAG_BACKGROUND;
        int i2 = Theme.key_actionBarDefault;
        arrayList.add(new ThemeDescription(actionBar, i, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SECTIONS, new Class[]{LetterSectionCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText));
        int i3 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteGrayText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, (Paint[]) null, (Drawable[]) null, themeDescriptionDelegate, Theme.key_windowBackgroundWhiteBlueText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, Theme.avatarDrawables, null, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundBlue));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueText2));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"imageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayIcon));
        arrayList.add(new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_chats_actionIcon));
        arrayList.add(new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_chats_actionBackground));
        arrayList.add(new ThemeDescription(this.floatingButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_chats_actionPressedBackground));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_graySectionText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedCheckDrawable}, null, Theme.key_chats_verifiedCheck));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, null, new Drawable[]{Theme.dialogs_verifiedDrawable}, null, Theme.key_chats_verifiedBackground));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_offlinePaint, null, null, Theme.key_windowBackgroundWhiteGrayText3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, Theme.dialogs_onlinePaint, null, null, Theme.key_windowBackgroundWhiteBlueText3));
        TextPaint[] textPaintArr = Theme.dialogs_namePaint;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, (String[]) null, new Paint[]{textPaintArr[0], textPaintArr[1], Theme.dialogs_searchNamePaint}, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_chats_name));
        TextPaint[] textPaintArr2 = Theme.dialogs_nameEncryptedPaint;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{ProfileSearchCell.class}, (String[]) null, new Paint[]{textPaintArr2[0], textPaintArr2[1], Theme.dialogs_searchNameEncryptedPaint}, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_chats_secretName));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$12() {
        RecyclerListView recyclerListView = this.listView;
        if (recyclerListView != null) {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.listView.getChildAt(i);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(0);
                } else if (childAt instanceof ProfileSearchCell) {
                    ((ProfileSearchCell) childAt).update(0);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ViewGroup.LayoutParams makeLayoutParamsForFloatingContainer(int i) {
        boolean z = LocaleController.isRTL;
        FrameLayout.LayoutParams layoutParamsCreateFrame = LayoutHelper.createFrame(76, 76.0f, (z ? 3 : 5) | 80, z ? 4.0f : 0.0f, 0.0f, z ? 0.0f : 4.0f, 0.0f);
        layoutParamsCreateFrame.bottomMargin = i;
        return layoutParamsCreateFrame;
    }
}
