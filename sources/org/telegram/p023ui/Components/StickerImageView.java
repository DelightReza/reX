package org.telegram.p023ui.Components;

import android.content.Context;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.NotificationCenter;

/* loaded from: classes3.dex */
public class StickerImageView extends BackupImageView implements NotificationCenter.NotificationCenterDelegate {
    int currentAccount;
    int stickerNum;
    String stickerPackName;

    public StickerImageView(Context context, int i) {
        super(context);
        this.stickerPackName = AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME;
        this.currentAccount = i;
    }

    public void setStickerNum(int i) {
        if (this.stickerNum != i) {
            this.stickerNum = i;
            setSticker();
        }
    }

    public void setStickerPackName(String str) {
        this.stickerPackName = str;
    }

    @Override // org.telegram.p023ui.Components.BackupImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSticker();
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.diceStickersDidLoad);
    }

    @Override // org.telegram.p023ui.Components.BackupImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.diceStickersDidLoad);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.diceStickersDidLoad) {
            if (this.stickerPackName.equals((String) objArr[0])) {
                setSticker();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setSticker() {
        /*
            r7 = this;
            int r0 = r7.currentAccount
            org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
            java.lang.String r1 = r7.stickerPackName
            org.telegram.tgnet.TLRPC$TL_messages_stickerSet r0 = r0.getStickerSetByName(r1)
            if (r0 != 0) goto L1a
            int r0 = r7.currentAccount
            org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
            java.lang.String r1 = r7.stickerPackName
            org.telegram.tgnet.TLRPC$TL_messages_stickerSet r0 = r0.getStickerSetByEmojiOrName(r1)
        L1a:
            r6 = r0
            r0 = 0
            if (r6 == 0) goto L31
            java.util.ArrayList r1 = r6.documents
            int r1 = r1.size()
            int r2 = r7.stickerNum
            if (r1 <= r2) goto L31
            java.util.ArrayList r1 = r6.documents
            java.lang.Object r1 = r1.get(r2)
            org.telegram.tgnet.TLRPC$Document r1 = (org.telegram.tgnet.TLRPC.Document) r1
            goto L32
        L31:
            r1 = r0
        L32:
            if (r1 == 0) goto L3f
            java.util.ArrayList<org.telegram.tgnet.TLRPC$PhotoSize> r0 = r1.thumbs
            int r2 = org.telegram.p023ui.ActionBar.Theme.key_emptyListPlaceholder
            r3 = 1045220557(0x3e4ccccd, float:0.2)
            org.telegram.messenger.SvgHelper$SvgDrawable r0 = org.telegram.messenger.DocumentObject.getSvgThumb(r0, r2, r3)
        L3f:
            r5 = r0
            if (r5 == 0) goto L47
            r0 = 512(0x200, float:7.17E-43)
            r5.overrideWidthAndHeight(r0, r0)
        L47:
            if (r1 == 0) goto L57
            org.telegram.messenger.ImageLocation r2 = org.telegram.messenger.ImageLocation.getForDocument(r1)
            java.lang.String r4 = "tgs"
            java.lang.String r3 = "130_130"
            r1 = r7
            r1.setImage(r2, r3, r4, r5, r6)
            return
        L57:
            r1 = r7
            org.telegram.messenger.ImageReceiver r0 = r1.imageReceiver
            r0.clearImage()
            int r0 = r1.currentAccount
            org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
            java.lang.String r2 = r1.stickerPackName
            r3 = 0
            if (r6 != 0) goto L6a
            r4 = 1
            goto L6b
        L6a:
            r4 = 0
        L6b:
            r0.loadStickersByEmojiOrName(r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.StickerImageView.setSticker():void");
    }
}
