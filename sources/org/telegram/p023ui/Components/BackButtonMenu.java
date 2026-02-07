package org.telegram.p023ui.Components;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserObject;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.INavigationLayout;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.BackButtonMenu;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.DialogsActivity;
import org.telegram.p023ui.ProfileActivity;
import org.telegram.p023ui.TopicsFragment;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public abstract class BackButtonMenu {

    /* loaded from: classes6.dex */
    public static class PulledDialog {
        Class activity;
        TLRPC.Chat chat;
        long dialogId;
        int filterId;
        int folderId;
        int stackIndex;
        TLRPC.TL_forumTopic topic;
        TLRPC.User user;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0274 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v0, types: [android.view.View, org.telegram.ui.ActionBar.ActionBarPopupWindow$ActionBarPopupWindowLayout] */
    /* JADX WARN: Type inference failed for: r5v14, types: [android.graphics.drawable.BitmapDrawable] */
    /* JADX WARN: Type inference failed for: r7v2, types: [android.view.View, android.view.ViewGroup, android.widget.FrameLayout] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.telegram.p023ui.ActionBar.ActionBarPopupWindow show(final org.telegram.p023ui.ActionBar.BaseFragment r28, android.view.View r29, long r30, long r32, org.telegram.ui.ActionBar.Theme.ResourcesProvider r34) {
        /*
            Method dump skipped, instructions count: 763
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.BackButtonMenu.show(org.telegram.ui.ActionBar.BaseFragment, android.view.View, long, long, org.telegram.ui.ActionBar.Theme$ResourcesProvider):org.telegram.ui.ActionBar.ActionBarPopupWindow");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ae A[LOOP:1: B:35:0x00aa->B:37:0x00ae, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ba A[EDGE_INSN: B:45:0x00ba->B:38:0x00ba BREAK  A[LOOP:1: B:35:0x00aa->B:37:0x00ae], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static /* synthetic */ void $r8$lambda$LjwJnyIy4aTYeceLn6PSUidUlns(java.util.concurrent.atomic.AtomicReference r4, org.telegram.ui.Components.BackButtonMenu.PulledDialog r5, org.telegram.p023ui.ActionBar.INavigationLayout r6, org.telegram.tgnet.TLRPC.TL_forumTopic r7, org.telegram.p023ui.ActionBar.BaseFragment r8, android.view.View r9) {
        /*
            java.lang.Object r9 = r4.get()
            r0 = 0
            if (r9 == 0) goto L10
            java.lang.Object r4 = r4.getAndSet(r0)
            org.telegram.ui.ActionBar.ActionBarPopupWindow r4 = (org.telegram.p023ui.ActionBar.ActionBarPopupWindow) r4
            r4.dismiss()
        L10:
            int r4 = r5.stackIndex
            if (r4 < 0) goto Lc9
            if (r6 == 0) goto L63
            java.util.List r4 = r6.getFragmentStack()
            if (r4 == 0) goto L63
            int r4 = r5.stackIndex
            java.util.List r9 = r6.getFragmentStack()
            int r9 = r9.size()
            if (r4 < r9) goto L29
            goto L63
        L29:
            java.util.List r4 = r6.getFragmentStack()
            int r9 = r5.stackIndex
            java.lang.Object r4 = r4.get(r9)
            org.telegram.ui.ActionBar.BaseFragment r4 = (org.telegram.p023ui.ActionBar.BaseFragment) r4
            boolean r9 = r4 instanceof org.telegram.p023ui.ChatActivity
            if (r9 == 0) goto L4c
            org.telegram.ui.ChatActivity r4 = (org.telegram.p023ui.ChatActivity) r4
            long r0 = r4.getDialogId()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            long r1 = r4.getTopicId()
            java.lang.Long r4 = java.lang.Long.valueOf(r1)
            goto L64
        L4c:
            boolean r9 = r4 instanceof org.telegram.p023ui.ProfileActivity
            if (r9 == 0) goto L63
            org.telegram.ui.ProfileActivity r4 = (org.telegram.p023ui.ProfileActivity) r4
            long r0 = r4.getDialogId()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            long r1 = r4.getTopicId()
            java.lang.Long r4 = java.lang.Long.valueOf(r1)
            goto L64
        L63:
            r4 = r0
        L64:
            if (r0 == 0) goto L70
            long r0 = r0.longValue()
            long r2 = r5.dialogId
            int r9 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r9 != 0) goto L7f
        L70:
            if (r7 == 0) goto L93
            if (r4 == 0) goto L93
            int r7 = r7.f1631id
            long r0 = (long) r7
            long r2 = r4.longValue()
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L93
        L7f:
            java.util.List r4 = r6.getFragmentStack()
            int r4 = r4.size()
            int r4 = r4 + (-2)
        L89:
            int r7 = r5.stackIndex
            if (r4 <= r7) goto Lc9
            r6.removeFragmentFromStack(r4)
            int r4 = r4 + (-1)
            goto L89
        L93:
            if (r6 == 0) goto Lc9
            java.util.List r4 = r6.getFragmentStack()
            if (r4 == 0) goto Lc9
            java.util.ArrayList r4 = new java.util.ArrayList
            java.util.List r7 = r6.getFragmentStack()
            r4.<init>(r7)
            int r7 = r4.size()
            int r7 = r7 + (-2)
        Laa:
            int r9 = r5.stackIndex
            if (r7 <= r9) goto Lba
            java.lang.Object r9 = r4.get(r7)
            org.telegram.ui.ActionBar.BaseFragment r9 = (org.telegram.p023ui.ActionBar.BaseFragment) r9
            r9.removeSelfFromStack()
            int r7 = r7 + (-1)
            goto Laa
        Lba:
            java.util.List r4 = r6.getFragmentStack()
            int r4 = r4.size()
            if (r9 >= r4) goto Lc9
            r4 = 1
            r6.closeLastFragment(r4)
            return
        Lc9:
            goToPulledDialog(r8, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.BackButtonMenu.$r8$lambda$LjwJnyIy4aTYeceLn6PSUidUlns(java.util.concurrent.atomic.AtomicReference, org.telegram.ui.Components.BackButtonMenu$PulledDialog, org.telegram.ui.ActionBar.INavigationLayout, org.telegram.tgnet.TLRPC$TL_forumTopic, org.telegram.ui.ActionBar.BaseFragment, android.view.View):void");
    }

    private static ArrayList getStackedHistoryForTopic(BaseFragment baseFragment, long j, long j2) {
        INavigationLayout parentLayout;
        int i;
        ArrayList arrayList = new ArrayList();
        if (baseFragment == null || (parentLayout = baseFragment.getParentLayout()) == null) {
            return arrayList;
        }
        List pulledDialogs = parentLayout.getPulledDialogs();
        if (pulledDialogs != null) {
            i = -1;
            for (int i2 = 0; i2 < pulledDialogs.size(); i2++) {
                PulledDialog pulledDialog = (PulledDialog) pulledDialogs.get(i2);
                if (pulledDialog.topic != null && r7.f1631id != j2) {
                    int i3 = pulledDialog.stackIndex;
                    if (i3 >= i) {
                        i = i3;
                    }
                    arrayList.add(pulledDialog);
                }
            }
        } else {
            i = -1;
        }
        if (parentLayout.getFragmentStack().size() > 1 && (parentLayout.getFragmentStack().get(parentLayout.getFragmentStack().size() - 2) instanceof TopicsFragment)) {
            PulledDialog pulledDialog2 = new PulledDialog();
            arrayList.add(pulledDialog2);
            pulledDialog2.stackIndex = i + 1;
            pulledDialog2.activity = DialogsActivity.class;
            PulledDialog pulledDialog3 = new PulledDialog();
            arrayList.add(pulledDialog3);
            pulledDialog3.stackIndex = -1;
            pulledDialog3.activity = TopicsFragment.class;
            pulledDialog3.chat = MessagesController.getInstance(baseFragment.getCurrentAccount()).getChat(Long.valueOf(-j));
        } else {
            PulledDialog pulledDialog4 = new PulledDialog();
            arrayList.add(pulledDialog4);
            pulledDialog4.stackIndex = -1;
            pulledDialog4.activity = TopicsFragment.class;
            pulledDialog4.chat = MessagesController.getInstance(baseFragment.getCurrentAccount()).getChat(Long.valueOf(-j));
        }
        Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.Components.BackButtonMenu$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BackButtonMenu.$r8$lambda$zwJbf49aukFzohFh_mJM0GlNr8Q((BackButtonMenu.PulledDialog) obj, (BackButtonMenu.PulledDialog) obj2);
            }
        });
        return arrayList;
    }

    public static /* synthetic */ int $r8$lambda$zwJbf49aukFzohFh_mJM0GlNr8Q(PulledDialog pulledDialog, PulledDialog pulledDialog2) {
        return pulledDialog2.stackIndex - pulledDialog.stackIndex;
    }

    public static void goToPulledDialog(BaseFragment baseFragment, PulledDialog pulledDialog) {
        BaseFragment baseFragment2;
        if (pulledDialog == null) {
            return;
        }
        Class cls = pulledDialog.activity;
        if (cls == ChatActivity.class) {
            Bundle bundle = new Bundle();
            TLRPC.Chat chat = pulledDialog.chat;
            if (chat != null) {
                bundle.putLong("chat_id", chat.f1571id);
            } else {
                TLRPC.User user = pulledDialog.user;
                if (user != null) {
                    bundle.putLong("user_id", user.f1734id);
                }
            }
            bundle.putInt("dialog_folder_id", pulledDialog.folderId);
            bundle.putInt("dialog_filter_id", pulledDialog.filterId);
            TLRPC.TL_forumTopic tL_forumTopic = pulledDialog.topic;
            if (tL_forumTopic != null) {
                baseFragment2 = baseFragment;
                baseFragment2.presentFragment(ForumUtilities.getChatActivityForTopic(baseFragment2, pulledDialog.chat.f1571id, tL_forumTopic, 0, bundle), true);
            } else {
                baseFragment2 = baseFragment;
                baseFragment2.presentFragment(new ChatActivity(bundle), true);
            }
        } else {
            baseFragment2 = baseFragment;
            if (cls == ProfileActivity.class) {
                Bundle bundle2 = new Bundle();
                bundle2.putLong("dialog_id", pulledDialog.dialogId);
                baseFragment2.presentFragment(new ProfileActivity(bundle2), true);
            }
        }
        if (pulledDialog.activity == TopicsFragment.class) {
            Bundle bundle3 = new Bundle();
            bundle3.putLong("chat_id", pulledDialog.chat.f1571id);
            baseFragment2.presentFragment(new TopicsFragment(bundle3), true);
        }
        if (pulledDialog.activity == DialogsActivity.class) {
            baseFragment2.presentFragment(new DialogsActivity(null), true);
        }
    }

    public static ArrayList getStackedHistoryDialogs(BaseFragment baseFragment, long j) {
        INavigationLayout parentLayout;
        TLRPC.Chat currentChat;
        TLRPC.User currentUser;
        long dialogId;
        Class cls;
        int dialogFilterId;
        int dialogFolderId;
        ArrayList arrayList = new ArrayList();
        if (baseFragment == null || (parentLayout = baseFragment.getParentLayout()) == null) {
            return arrayList;
        }
        List fragmentStack = parentLayout.getFragmentStack();
        List pulledDialogs = parentLayout.getPulledDialogs();
        if (fragmentStack != null) {
            int size = fragmentStack.size();
            for (int i = 0; i < size; i++) {
                BaseFragment baseFragment2 = (BaseFragment) fragmentStack.get(i);
                if (baseFragment2 instanceof ChatActivity) {
                    ChatActivity chatActivity = (ChatActivity) baseFragment2;
                    if (chatActivity.getChatMode() == 0 && !chatActivity.isReport()) {
                        currentChat = chatActivity.getCurrentChat();
                        currentUser = chatActivity.getCurrentUser();
                        dialogId = chatActivity.getDialogId();
                        dialogFolderId = chatActivity.getDialogFolderId();
                        dialogFilterId = chatActivity.getDialogFilterId();
                        cls = ChatActivity.class;
                        if (dialogId == j && (j != 0 || !UserObject.isUserSelf(currentUser))) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= arrayList.size()) {
                                    PulledDialog pulledDialog = new PulledDialog();
                                    pulledDialog.activity = cls;
                                    pulledDialog.stackIndex = i;
                                    pulledDialog.chat = currentChat;
                                    pulledDialog.user = currentUser;
                                    pulledDialog.dialogId = dialogId;
                                    pulledDialog.folderId = dialogFolderId;
                                    pulledDialog.filterId = dialogFilterId;
                                    if (currentChat != null || currentUser != null) {
                                        arrayList.add(pulledDialog);
                                    }
                                } else {
                                    if (((PulledDialog) arrayList.get(i2)).dialogId == dialogId) {
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                    }
                } else if (baseFragment2 instanceof ProfileActivity) {
                    ProfileActivity profileActivity = (ProfileActivity) baseFragment2;
                    currentChat = profileActivity.getCurrentChat();
                    try {
                        currentUser = profileActivity.getUserInfo().user;
                    } catch (Exception unused) {
                        currentUser = null;
                    }
                    dialogId = profileActivity.getDialogId();
                    cls = ProfileActivity.class;
                    dialogFilterId = 0;
                    dialogFolderId = 0;
                    if (dialogId == j) {
                    }
                }
            }
        }
        if (pulledDialogs != null) {
            for (int size2 = pulledDialogs.size() - 1; size2 >= 0; size2--) {
                PulledDialog pulledDialog2 = (PulledDialog) pulledDialogs.get(size2);
                if (pulledDialog2.dialogId != j) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= arrayList.size()) {
                            arrayList.add(pulledDialog2);
                            break;
                        }
                        if (((PulledDialog) arrayList.get(i3)).dialogId == pulledDialog2.dialogId) {
                            break;
                        }
                        i3++;
                    }
                }
            }
        }
        Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.Components.BackButtonMenu$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BackButtonMenu.m7889$r8$lambda$sPUzo9otFwawnC1lMMb0CzYuf8((BackButtonMenu.PulledDialog) obj, (BackButtonMenu.PulledDialog) obj2);
            }
        });
        return arrayList;
    }

    /* renamed from: $r8$lambda$sPUzo9otFwawnC1-lMMb0CzYuf8, reason: not valid java name */
    public static /* synthetic */ int m7889$r8$lambda$sPUzo9otFwawnC1lMMb0CzYuf8(PulledDialog pulledDialog, PulledDialog pulledDialog2) {
        return pulledDialog2.stackIndex - pulledDialog.stackIndex;
    }

    public static void addToPulledDialogs(BaseFragment baseFragment, int i, TLRPC.Chat chat, TLRPC.User user, TLRPC.TL_forumTopic tL_forumTopic, long j, int i2, int i3) {
        INavigationLayout parentLayout;
        TLRPC.TL_forumTopic tL_forumTopic2;
        if ((chat == null && user == null) || baseFragment == null || (parentLayout = baseFragment.getParentLayout()) == null) {
            return;
        }
        if (parentLayout.getPulledDialogs() == null) {
            parentLayout.setPulledDialogs(new ArrayList());
        }
        for (PulledDialog pulledDialog : parentLayout.getPulledDialogs()) {
            if (tL_forumTopic == null && pulledDialog.dialogId == j) {
                return;
            }
            if (tL_forumTopic != null && (tL_forumTopic2 = pulledDialog.topic) != null && tL_forumTopic2.f1631id == tL_forumTopic.f1631id) {
                return;
            }
        }
        PulledDialog pulledDialog2 = new PulledDialog();
        pulledDialog2.activity = ChatActivity.class;
        pulledDialog2.stackIndex = i;
        pulledDialog2.dialogId = j;
        pulledDialog2.filterId = i3;
        pulledDialog2.folderId = i2;
        pulledDialog2.chat = chat;
        pulledDialog2.user = user;
        pulledDialog2.topic = tL_forumTopic;
        parentLayout.getPulledDialogs().add(pulledDialog2);
    }

    public static void clearPulledDialogs(BaseFragment baseFragment, int i) {
        INavigationLayout parentLayout;
        if (baseFragment == null || (parentLayout = baseFragment.getParentLayout()) == null || parentLayout.getPulledDialogs() == null) {
            return;
        }
        int i2 = 0;
        while (i2 < parentLayout.getPulledDialogs().size()) {
            if (((PulledDialog) parentLayout.getPulledDialogs().get(i2)).stackIndex > i) {
                parentLayout.getPulledDialogs().remove(i2);
                i2--;
            }
            i2++;
        }
    }
}
