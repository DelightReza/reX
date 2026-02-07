package org.telegram.p023ui.Components;

import android.app.Activity;
import android.content.Context;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.PremiumPreviewFragment;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public class StickerSetBulletinLayout extends Bulletin.TwoLineLayout {
    public StickerSetBulletinLayout(Context context, TLObject tLObject, int i, TLRPC.Document document, Theme.ResourcesProvider resourcesProvider) {
        this(context, tLObject, 1, i, document, resourcesProvider);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024 A[PHI: r4
      0x0024: PHI (r4v2 org.telegram.tgnet.TLRPC$StickerSet) = 
      (r4v1 org.telegram.tgnet.TLRPC$StickerSet)
      (r4v19 org.telegram.tgnet.TLRPC$StickerSet)
      (r4v19 org.telegram.tgnet.TLRPC$StickerSet)
     binds: [B:17:0x003c, B:5:0x0015, B:7:0x001b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public StickerSetBulletinLayout(final android.content.Context r14, org.telegram.tgnet.TLObject r15, int r16, int r17, org.telegram.tgnet.TLRPC.Document r18, org.telegram.ui.ActionBar.Theme.ResourcesProvider r19) {
        /*
            Method dump skipped, instructions count: 984
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.StickerSetBulletinLayout.<init>(android.content.Context, org.telegram.tgnet.TLObject, int, int, org.telegram.tgnet.TLRPC$Document, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ void $r8$lambda$H3iEL8c72YVk4MXdEAWXkJoksi0(Context context) {
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity instanceof LaunchActivity) {
            ((LaunchActivity) activityFindActivity).lambda$runLinkRequest$107(new PremiumPreviewFragment(LimitReachedBottomSheet.limitTypeToServerString(10)));
        }
    }

    /* renamed from: $r8$lambda$57QwHCV-IjZ3fyt7ykEQYpLyG_I, reason: not valid java name */
    public static /* synthetic */ void m11709$r8$lambda$57QwHCVIjZ3fyt7ykEQYpLyG_I(Context context) {
        Activity activityFindActivity = AndroidUtilities.findActivity(context);
        if (activityFindActivity instanceof LaunchActivity) {
            ((LaunchActivity) activityFindActivity).lambda$runLinkRequest$107(new PremiumPreviewFragment(LimitReachedBottomSheet.limitTypeToServerString(9)));
        }
    }
}
