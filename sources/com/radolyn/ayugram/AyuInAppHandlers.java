package com.radolyn.ayugram;

import android.content.Intent;
import android.net.Uri;
import com.exteragram.messenger.badges.BadgesController;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.radolyn.ayugram.AyuInAppHandlers;
import com.radolyn.ayugram.utils.android.AudioOutputManager;
import com.radolyn.ayugram.utils.android.AyuVendorUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.Cells.BaseCell;
import org.telegram.p023ui.Cells.ChatMessageCell;
import org.telegram.p023ui.Cells.EmptyCell;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes4.dex */
public class AyuInAppHandlers {
    private static final HashSet<String> dangerous = new HashSet<>();
    private static final HashMap<String, Integer> jumpscares = new HashMap<>();
    private static final String jumpscaresChannel;

    static {
        ArrayList stringListConfigValue = RemoteUtils.getStringListConfigValue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311744815595046L), AyuConstants.DEFAULT_JUMPSCARES_KEYS);
        ArrayList stringListConfigValue2 = RemoteUtils.getStringListConfigValue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311800650169894L), AyuConstants.DEFAULT_JUMPSCARES_VALUES);
        for (int i = 0; i < stringListConfigValue.size(); i++) {
            try {
                jumpscares.put((String) stringListConfigValue.get(i), Integer.valueOf((String) stringListConfigValue2.get(i)));
            } catch (Exception unused) {
            }
        }
        jumpscaresChannel = RemoteUtils.getStringConfigValue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311856484744742L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311938089123366L));
        Iterator<String> it = jumpscares.keySet().iterator();
        while (it.hasNext()) {
            dangerous.add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312002513632806L) + it.next());
        }
        HashSet<String> hashSet = dangerous;
        hashSet.add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312041168338470L));
        hashSet.add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312101297880614L));
    }

    public static void handleAyu(BaseFragment baseFragment) {
        if (baseFragment == null) {
            return;
        }
        BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.SecretMessageTecno)).show();
    }

    public static void handleXiaomi(BaseFragment baseFragment) {
        if (baseFragment == null) {
            return;
        }
        if (AyuVendorUtils.isMIUI() || AyuVendorUtils.isHyperOS()) {
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.SecretMessageXiaomiFailure)).show();
            Intent intent = new Intent(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310224397172262L));
            intent.setData(Uri.parse(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310348951223846L)));
            intent.addFlags(268435456);
            ApplicationLoader.applicationContext.startActivity(intent);
            return;
        }
        if (AyuVendorUtils.isXiaomi()) {
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.SecretMessageXiaomiWarning)).show();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuInAppHandlers$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AyuUtils.killApplication(LaunchActivity.instance);
                }
            }, 5000L);
        } else {
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.SecretMessageXiaomiSuccess)).show();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x018f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void handleAyuPath(java.lang.String r9, org.telegram.p023ui.ActionBar.BaseFragment r10) {
        /*
            Method dump skipped, instructions count: 605
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.AyuInAppHandlers.handleAyuPath(java.lang.String, org.telegram.ui.ActionBar.BaseFragment):void");
    }

    /* renamed from: com.radolyn.ayugram.AyuInAppHandlers$1 */
    class C14591 extends Browser.Progress {
        final /* synthetic */ boolean val$isUnclosableFinal;
        final /* synthetic */ AudioOutputManager val$manager;
        final /* synthetic */ Integer val$messageId;

        C14591(boolean z, AudioOutputManager audioOutputManager, Integer num) {
            this.val$isUnclosableFinal = z;
            this.val$manager = audioOutputManager;
            this.val$messageId = num;
        }

        @Override // org.telegram.messenger.browser.Browser.Progress
        public void end(boolean z) {
            final boolean z2 = this.val$isUnclosableFinal;
            final AudioOutputManager audioOutputManager = this.val$manager;
            final Integer num = this.val$messageId;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuInAppHandlers$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AyuInAppHandlers.C14591.$r8$lambda$AO6OFP7R2Wdr8CVWpuOhK47yJIg(z2, audioOutputManager, num);
                }
            }, 700L);
        }

        public static /* synthetic */ void $r8$lambda$AO6OFP7R2Wdr8CVWpuOhK47yJIg(boolean z, final AudioOutputManager audioOutputManager, Integer num) {
            if (z) {
                try {
                    audioOutputManager.increaseVolume();
                } catch (Exception unused) {
                }
            }
            try {
                final ChatActivity chatActivity = (ChatActivity) LaunchActivity.getLastFragment();
                BaseCell baseCellFindMessageCell = chatActivity.findMessageCell(num.intValue(), false);
                if (z) {
                    chatActivity.openPhotoViewerForMessage((ChatMessageCell) baseCellFindMessageCell, baseCellFindMessageCell.getMessageObject2());
                } else {
                    chatActivity.setHighlightMessageId(num.intValue());
                }
                double duration = baseCellFindMessageCell.getMessageObject2().getDuration();
                final AlertDialog alertDialog = new AlertDialog(chatActivity.getContext(), 2);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.setCanCancel(false);
                alertDialog.setContentView(new EmptyCell(chatActivity.getContext()));
                if (z) {
                    alertDialog.show();
                }
                alertDialog.setContentView(new EmptyCell(chatActivity.getContext()));
                if (z) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuInAppHandlers$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AyuInAppHandlers.C14591.m2788$r8$lambda$Fcg81vKGsjLPag8jovkq37aIwA(alertDialog, audioOutputManager, chatActivity);
                        }
                    }, (((int) duration) * 1000) + 500);
                }
            } catch (Exception unused2) {
            }
        }

        /* renamed from: $r8$lambda$Fcg81vKGsjLPag8jovkq37-aIwA, reason: not valid java name */
        public static /* synthetic */ void m2788$r8$lambda$Fcg81vKGsjLPag8jovkq37aIwA(AlertDialog alertDialog, AudioOutputManager audioOutputManager, final ChatActivity chatActivity) {
            try {
                alertDialog.dismiss();
            } catch (Exception unused) {
            }
            try {
                audioOutputManager.decreaseVolume();
            } catch (Exception unused2) {
            }
            try {
                audioOutputManager.afterPlay();
            } catch (Exception unused3) {
            }
            try {
                PhotoViewer.getInstance().closePhoto(true, false);
            } catch (Exception unused4) {
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuInAppHandlers$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    chatActivity.finishFragment(true);
                }
            }, 150L);
        }
    }

    public static void handleNekogram(BaseFragment baseFragment) {
        BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.error, LocaleController.getString(C2369R.string.SecretMessageNekogram) + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311663211216422L)).show();
    }

    public static boolean isDangerous(String str) {
        if (!str.startsWith(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311701865922086L))) {
            return false;
        }
        if (str.contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311727635725862L))) {
            str = str.substring(0, str.indexOf(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019311736225660454L)));
        }
        return dangerous.contains(str.toLowerCase());
    }

    public static boolean canProceedWithDangerous(MessageObject messageObject) {
        if (messageObject.isOutOwner()) {
            return true;
        }
        return canProceedWithDangerous(messageObject.getFromPeerObject());
    }

    public static boolean canProceedWithDangerous(TLObject tLObject) {
        if (tLObject instanceof TLRPC.User) {
            TLRPC.User user = (TLRPC.User) tLObject;
            BadgesController badgesController = BadgesController.INSTANCE;
            return badgesController.isDeveloper(user) || badgesController.isAyuModerator(user);
        }
        if (tLObject instanceof TLRPC.Chat) {
            return BadgesController.INSTANCE.isExtera((TLRPC.Chat) tLObject);
        }
        return false;
    }
}
