package org.telegram.p023ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.voip.ConferenceCall;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.AvatarsImageView;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.TextHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.Components.voip.VoIPHelper;
import org.telegram.p023ui.Stories.DarkThemeResourceProvider;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_phone;
import p017j$.util.Collection;
import p017j$.util.function.Function$CC;
import p017j$.util.function.Predicate$CC;
import p017j$.util.stream.Collectors;

/* loaded from: classes5.dex */
public abstract class GroupCallSheet {
    public static void show(Context context, int i, long j, String str, Browser.Progress progress) {
        TLRPC.TL_inputGroupCallSlug tL_inputGroupCallSlug = new TLRPC.TL_inputGroupCallSlug();
        tL_inputGroupCallSlug.slug = str;
        show(context, i, j, tL_inputGroupCallSlug, progress);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void show(android.content.Context r9, final int r10, final long r11, final org.telegram.tgnet.TLRPC.InputGroupCall r13, final org.telegram.messenger.browser.Browser.Progress r14) {
        /*
            org.telegram.messenger.voip.VoIPService r0 = org.telegram.messenger.voip.VoIPService.getSharedInstance()
            if (r0 == 0) goto L5b
            org.telegram.messenger.voip.VoIPService r0 = org.telegram.messenger.voip.VoIPService.getSharedInstance()
            org.telegram.messenger.voip.ConferenceCall r0 = r0.conference
            if (r0 == 0) goto L5b
            boolean r1 = r13 instanceof org.telegram.tgnet.TLRPC.TL_inputGroupCall
            if (r1 == 0) goto L2e
            org.telegram.tgnet.TLRPC$GroupCall r1 = r0.groupCall
            if (r1 == 0) goto L1e
            long r2 = r13.f1593id
            long r4 = r1.f1586id
            int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r1 == 0) goto L2c
        L1e:
            org.telegram.tgnet.TLRPC$InputGroupCall r0 = r0.inputGroupCall
            boolean r1 = r0 instanceof org.telegram.tgnet.TLRPC.TL_inputGroupCall
            if (r1 == 0) goto L5b
            long r1 = r13.f1593id
            long r3 = r0.f1593id
            int r0 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r0 != 0) goto L5b
        L2c:
            r4 = r9
            goto L43
        L2e:
            boolean r1 = r13 instanceof org.telegram.tgnet.TLRPC.TL_inputGroupCallSlug
            if (r1 == 0) goto L5b
            org.telegram.tgnet.TLRPC$InputGroupCall r0 = r0.inputGroupCall
            boolean r1 = r0 instanceof org.telegram.tgnet.TLRPC.TL_inputGroupCallSlug
            if (r1 == 0) goto L5b
            java.lang.String r0 = r0.slug
            java.lang.String r1 = r13.slug
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            if (r0 == 0) goto L5b
            goto L2c
        L43:
            org.telegram.ui.LaunchActivity r9 = org.telegram.p023ui.LaunchActivity.instance
            if (r9 == 0) goto L5c
            org.telegram.messenger.voip.VoIPService r10 = org.telegram.messenger.voip.VoIPService.getSharedInstance()
            int r10 = r10.getAccount()
            org.telegram.messenger.AccountInstance r10 = org.telegram.messenger.AccountInstance.getInstance(r10)
            r13 = 0
            r14 = 0
            r11 = 0
            r12 = 0
            org.telegram.p023ui.GroupCallActivity.create(r9, r10, r11, r12, r13, r14)
            return
        L5b:
            r4 = r9
        L5c:
            if (r14 != 0) goto L6b
            org.telegram.ui.ActionBar.AlertDialog r9 = new org.telegram.ui.ActionBar.AlertDialog
            r0 = 3
            r9.<init>(r4, r0)
            r0 = 300(0x12c, double:1.48E-321)
            r9.showDelayed(r0)
        L69:
            r1 = r9
            goto L6d
        L6b:
            r9 = 0
            goto L69
        L6d:
            org.telegram.tgnet.tl.TL_phone$getGroupCall r9 = new org.telegram.tgnet.tl.TL_phone$getGroupCall
            r9.<init>()
            r9.call = r13
            r0 = 10
            r9.limit = r0
            org.telegram.tgnet.ConnectionsManager r8 = org.telegram.tgnet.ConnectionsManager.getInstance(r10)
            org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda0 r0 = new org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda0
            r3 = r10
            r5 = r11
            r7 = r13
            r2 = r14
            r0.<init>()
            int r9 = r8.sendRequest(r9, r0)
            if (r2 == 0) goto L96
            org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda1 r10 = new org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda1
            r10.<init>()
            r2.onCancel(r10)
            r2.init()
        L96:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.GroupCallSheet.show(android.content.Context, int, long, org.telegram.tgnet.TLRPC$InputGroupCall, org.telegram.messenger.browser.Browser$Progress):void");
    }

    public static /* synthetic */ void $r8$lambda$Akf10XNgC6J0t1JRJKCuBgnc3cI(AlertDialog alertDialog, Browser.Progress progress, TLObject tLObject, int i, Context context, long j, TLRPC.InputGroupCall inputGroupCall, TLRPC.TL_error tL_error) {
        BaseFragment safeLastFragment;
        ConferenceCall conferenceCall;
        TLRPC.GroupCall groupCall;
        LaunchActivity launchActivity;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (progress != null) {
            progress.end();
        }
        if (tLObject instanceof TL_phone.groupCall) {
            TL_phone.groupCall groupcall = (TL_phone.groupCall) tLObject;
            MessagesController.getInstance(i).putUsers(groupcall.users, false);
            MessagesController.getInstance(i).putChats(groupcall.chats, false);
            if (VoIPService.getSharedInstance() != null && (conferenceCall = VoIPService.getSharedInstance().conference) != null && (groupCall = conferenceCall.groupCall) != null && groupcall.call.f1586id == groupCall.f1586id && (launchActivity = LaunchActivity.instance) != null) {
                GroupCallActivity.create(launchActivity, AccountInstance.getInstance(VoIPService.getSharedInstance().getAccount()), null, null, false, null);
                return;
            } else {
                show(context, i, j, inputGroupCall, groupcall.call, groupcall.participants);
                return;
            }
        }
        if (tL_error == null || !"GROUPCALL_INVALID".equalsIgnoreCase(tL_error.text)) {
            if (tL_error == null || (safeLastFragment = LaunchActivity.getSafeLastFragment()) == null) {
                return;
            }
            BulletinFactory.m1267of(safeLastFragment).showForError(tL_error);
            return;
        }
        BaseFragment safeLastFragment2 = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment2 != null) {
            BulletinFactory.m1267of(safeLastFragment2).createSimpleBulletin(C2369R.raw.error, LocaleController.getString(C2369R.string.LinkIsNoActive)).show();
        }
    }

    public static void show(final Context context, final int i, final long j, final TLRPC.InputGroupCall inputGroupCall, TLRPC.GroupCall groupCall, ArrayList arrayList) {
        DarkThemeResourceProvider darkThemeResourceProvider = new DarkThemeResourceProvider();
        BottomSheet.Builder builder = new BottomSheet.Builder(context, false, darkThemeResourceProvider);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(AndroidUtilities.m1146dp(14.0f), 0, AndroidUtilities.m1146dp(14.0f), AndroidUtilities.m1146dp(8.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackground(Theme.createCircleDrawable(AndroidUtilities.m1146dp(80.0f), Theme.getColor(Theme.key_featuredStickers_addButton, darkThemeResourceProvider)));
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(C2369R.drawable.filled_calls_users);
        frameLayout.addView(imageView, LayoutHelper.createFrame(56, 56, 17));
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(80, 80, 1, 2, 21, 2, 13));
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        LinkSpanDrawable.LinksTextView linksTextViewMakeLinkTextView = TextHelper.makeLinkTextView(context, 20.0f, i2, true, darkThemeResourceProvider);
        linksTextViewMakeLinkTextView.setText(LocaleController.getString(C2369R.string.GroupCallLinkTitle));
        linksTextViewMakeLinkTextView.setGravity(17);
        linearLayout.addView(linksTextViewMakeLinkTextView, LayoutHelper.createLinear(-1, -2, 1, 2, 0, 2, 4));
        List list = (List) Collection.EL.stream(arrayList).map(new Function() { // from class: org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda3
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(DialogObject.getPeerDialogId(((TLRPC.GroupCallParticipant) obj).peer));
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).filter(new Predicate() { // from class: org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda4
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m1306or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return GroupCallSheet.$r8$lambda$0wXRjKk4tiXvqp2xchk3q6Rg_NQ(i, j, (Long) obj);
            }
        }).collect(Collectors.toList());
        boolean zIsEmpty = list.isEmpty();
        LinkSpanDrawable.LinksTextView linksTextViewMakeLinkTextView2 = TextHelper.makeLinkTextView(context, 14.0f, i2, false, darkThemeResourceProvider);
        linksTextViewMakeLinkTextView2.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.GroupCallLinkText)));
        linksTextViewMakeLinkTextView2.setGravity(17);
        linksTextViewMakeLinkTextView2.setMaxWidth(HintView2.cutInFancyHalf(linksTextViewMakeLinkTextView2.getText(), linksTextViewMakeLinkTextView2.getPaint()));
        linearLayout.addView(linksTextViewMakeLinkTextView2, LayoutHelper.createLinear(-1, -2, 1, 2, 0, 2, 23));
        if (!zIsEmpty) {
            View view = new View(context);
            view.setBackgroundColor(-14012362);
            linearLayout.addView(view, LayoutHelper.createLinear(-1, 0.66f, 7, 0, 0, 0, 0));
            AvatarsImageView avatarsImageView = new AvatarsImageView(context, false);
            avatarsImageView.setCentered(true);
            avatarsImageView.setSize(AndroidUtilities.m1146dp(38.0f));
            int iMin = Math.min(3, list.size());
            avatarsImageView.setCount(iMin);
            for (int i3 = 0; i3 < iMin; i3++) {
                avatarsImageView.setObject(i3, i, MessagesController.getInstance(i).getUser((Long) list.get(i3)));
            }
            avatarsImageView.commitTransition(false);
            linearLayout.addView(avatarsImageView, LayoutHelper.createLinear(-1, 58, 2.0f, 11.0f, 5.0f, 0.0f));
            LinkSpanDrawable.LinksTextView linksTextViewMakeLinkTextView3 = TextHelper.makeLinkTextView(context, 14.0f, Theme.key_windowBackgroundWhiteBlackText, false, darkThemeResourceProvider);
            linksTextViewMakeLinkTextView3.setGravity(17);
            if (list.size() == 1) {
                linksTextViewMakeLinkTextView3.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.GroupCallLinkText2One, DialogObject.getShortName(i, ((Long) list.get(0)).longValue()))));
            } else if (list.size() == 2) {
                linksTextViewMakeLinkTextView3.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.GroupCallLinkText2Two, DialogObject.getShortName(i, ((Long) list.get(0)).longValue()), DialogObject.getShortName(i, ((Long) list.get(1)).longValue()))));
            } else {
                linksTextViewMakeLinkTextView3.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("GroupCallLinkText2Many", arrayList.size() - 2, DialogObject.getShortName(i, ((Long) list.get(0)).longValue()), DialogObject.getShortName(i, ((Long) list.get(1)).longValue()))));
            }
            linksTextViewMakeLinkTextView3.setMaxWidth(HintView2.cutInFancyHalf(linksTextViewMakeLinkTextView3.getText(), linksTextViewMakeLinkTextView3.getPaint()));
            linearLayout.addView(linksTextViewMakeLinkTextView3, LayoutHelper.createLinear(-1, -2, 1, 2, 0, 2, 25));
        }
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, darkThemeResourceProvider);
        buttonWithCounterView.setText(LocaleController.getString(C2369R.string.GroupCallLinkJoin), false);
        linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 2.0f, 0.0f, 2.0f, 0.0f));
        builder.setCustomView(linearLayout);
        final BottomSheet bottomSheetCreate = builder.create();
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.GroupCallSheet$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GroupCallSheet.$r8$lambda$9yhIg6AQhYglMlHULX0n7iIw3OA(bottomSheetCreate, context, i, inputGroupCall, view2);
            }
        });
        bottomSheetCreate.fixNavigationBar();
        bottomSheetCreate.show();
    }

    public static /* synthetic */ boolean $r8$lambda$0wXRjKk4tiXvqp2xchk3q6Rg_NQ(int i, long j, Long l) {
        return (l.longValue() == UserConfig.getInstance(i).getClientUserId() || l.longValue() == j) ? false : true;
    }

    public static /* synthetic */ void $r8$lambda$9yhIg6AQhYglMlHULX0n7iIw3OA(BottomSheet bottomSheet, Context context, int i, TLRPC.InputGroupCall inputGroupCall, View view) {
        bottomSheet.lambda$new$0();
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity == null) {
            return;
        }
        VoIPHelper.joinConference(activityFindActivity, i, inputGroupCall, false, null);
    }

    static class UserView extends FrameLayout {
        private final AvatarDrawable avatarDrawable;
        private final int currentAccount;
        private final BackupImageView imageView;
        private final LinkSpanDrawable.LinksTextView textView;

        public UserView(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.avatarDrawable = new AvatarDrawable();
            this.currentAccount = i;
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            backupImageView.setRoundRadius(AndroidUtilities.m1146dp(28.0f));
            addView(backupImageView, LayoutHelper.createFrame(56, 56.0f, 49, 0.0f, 17.0f, 0.0f, 0.0f));
            LinkSpanDrawable.LinksTextView linksTextViewMakeLinkTextView = TextHelper.makeLinkTextView(context, 12.0f, Theme.key_windowBackgroundWhiteBlackText, false, resourcesProvider);
            this.textView = linksTextViewMakeLinkTextView;
            linksTextViewMakeLinkTextView.setGravity(17);
            addView(linksTextViewMakeLinkTextView, LayoutHelper.createFrame(-2, -2.0f, 49, 6.0f, 77.66f, 6.0f, 0.0f));
        }

        public void set(long j) {
            TLObject userOrChat = MessagesController.getInstance(this.currentAccount).getUserOrChat(j);
            this.avatarDrawable.setInfo(userOrChat);
            this.imageView.setForUserOrChat(userOrChat, this.avatarDrawable);
            this.textView.setText(DialogObject.getName(userOrChat));
            this.textView.setMaxWidth(Math.max(AndroidUtilities.m1146dp(41.0f), HintView2.cutInFancyHalf(this.textView.getText(), this.textView.getPaint())));
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(82.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30));
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public UserView createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new UserView(context, i, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((UserView) view).set(uItem.dialogId);
            }
        }
    }
}
