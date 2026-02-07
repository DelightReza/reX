package org.telegram.p023ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import java.util.HashSet;
import java.util.Iterator;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.AttachableDrawable;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class VectorAvatarThumbDrawable extends Drawable implements AnimatedEmojiSpan.InvalidateHolder, AttachableDrawable, NotificationCenter.NotificationCenterDelegate {
    AnimatedEmojiDrawable animatedEmojiDrawable;
    final int currentAccount;
    ImageReceiver currentParent;
    public final GradientTools gradientTools;
    ImageReceiver imageReceiver;
    boolean imageSeted;
    boolean isPremium;
    HashSet parents;
    float roundRadius;
    TLRPC.TL_videoSizeStickerMarkup sizeStickerMarkup;
    ImageReceiver stickerPreloadImageReceiver;
    private final int type;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override // org.telegram.p023ui.Components.AttachableDrawable
    public /* synthetic */ void setParent(View view) {
        AttachableDrawable.CC.$default$setParent(this, view);
    }

    public VectorAvatarThumbDrawable(TLRPC.VideoSize videoSize, boolean z, int i) {
        GradientTools gradientTools = new GradientTools();
        this.gradientTools = gradientTools;
        this.parents = new HashSet();
        this.stickerPreloadImageReceiver = new ImageReceiver();
        this.currentAccount = UserConfig.selectedAccount;
        this.type = i;
        this.isPremium = z;
        gradientTools.setColors(ColorUtils.setAlphaComponent(((Integer) videoSize.background_colors.get(0)).intValue(), 255), videoSize.background_colors.size() > 1 ? ColorUtils.setAlphaComponent(((Integer) videoSize.background_colors.get(1)).intValue(), 255) : 0, videoSize.background_colors.size() > 2 ? ColorUtils.setAlphaComponent(((Integer) videoSize.background_colors.get(2)).intValue(), 255) : 0, videoSize.background_colors.size() > 3 ? ColorUtils.setAlphaComponent(((Integer) videoSize.background_colors.get(3)).intValue(), 255) : 0);
        if (videoSize instanceof TLRPC.TL_videoSizeEmojiMarkup) {
            AnimatedEmojiDrawable animatedEmojiDrawable = new AnimatedEmojiDrawable((i == 1 && z) ? 7 : i == 2 ? 15 : 8, UserConfig.selectedAccount, ((TLRPC.TL_videoSizeEmojiMarkup) videoSize).emoji_id);
            this.animatedEmojiDrawable = animatedEmojiDrawable;
            animatedEmojiDrawable.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
            return;
        }
        if (videoSize instanceof TLRPC.TL_videoSizeStickerMarkup) {
            this.sizeStickerMarkup = (TLRPC.TL_videoSizeStickerMarkup) videoSize;
            ImageReceiver imageReceiver = new ImageReceiver() { // from class: org.telegram.ui.Components.VectorAvatarThumbDrawable.1
                @Override // org.telegram.messenger.ImageReceiver
                public void invalidate() {
                    VectorAvatarThumbDrawable.this.invalidate();
                }
            };
            this.imageReceiver = imageReceiver;
            imageReceiver.setInvalidateAll(true);
            if (i == 1) {
                this.imageReceiver.setAutoRepeatCount(2);
            }
            setImage();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setImage() {
        /*
            r15 = this;
            int r0 = r15.currentAccount
            org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
            org.telegram.tgnet.TLRPC$TL_videoSizeStickerMarkup r1 = r15.sizeStickerMarkup
            org.telegram.tgnet.TLRPC$InputStickerSet r1 = r1.stickerset
            r2 = 0
            org.telegram.tgnet.TLRPC$TL_messages_stickerSet r0 = r0.getStickerSet(r1, r2)
            if (r0 == 0) goto L8f
            r1 = 1
            r15.imageSeted = r1
        L14:
            java.util.ArrayList r3 = r0.documents
            int r3 = r3.size()
            if (r2 >= r3) goto L8f
            java.util.ArrayList r3 = r0.documents
            java.lang.Object r3 = r3.get(r2)
            org.telegram.tgnet.TLRPC$Document r3 = (org.telegram.tgnet.TLRPC.Document) r3
            long r3 = r3.f1579id
            org.telegram.tgnet.TLRPC$TL_videoSizeStickerMarkup r5 = r15.sizeStickerMarkup
            long r5 = r5.sticker_id
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L8c
            java.util.ArrayList r0 = r0.documents
            java.lang.Object r0 = r0.get(r2)
            r11 = r0
            org.telegram.tgnet.TLRPC$Document r11 = (org.telegram.tgnet.TLRPC.Document) r11
            boolean r0 = r15.isPremium
            java.lang.String r2 = "50_50_firstframe"
            if (r0 == 0) goto L47
            int r0 = r15.type
            if (r0 != r1) goto L47
            java.lang.String r0 = "50_50"
        L43:
            r4 = r0
            r6 = r2
            r0 = r11
            goto L52
        L47:
            int r0 = r15.type
            r1 = 2
            if (r0 != r1) goto L4f
            java.lang.String r0 = "100_100"
            goto L43
        L4f:
            r0 = 0
            r6 = r0
            r4 = r2
        L52:
            int r1 = org.telegram.p023ui.ActionBar.Theme.key_windowBackgroundWhiteGrayIcon
            r2 = 1045220557(0x3e4ccccd, float:0.2)
            org.telegram.messenger.SvgHelper$SvgDrawable r9 = org.telegram.messenger.DocumentObject.getSvgThumb(r11, r1, r2)
            org.telegram.messenger.ImageReceiver r2 = r15.imageReceiver
            org.telegram.messenger.ImageLocation r3 = org.telegram.messenger.ImageLocation.getForDocument(r11)
            org.telegram.messenger.ImageLocation r5 = org.telegram.messenger.ImageLocation.getForDocument(r0)
            java.lang.String r12 = "tgs"
            r14 = 0
            r7 = 0
            r8 = 0
            r13 = r11
            r10 = 0
            r2.setImage(r3, r4, r5, r6, r7, r8, r9, r10, r12, r13, r14)
            int r0 = r15.type
            r1 = 3
            if (r0 != r1) goto L8f
            org.telegram.messenger.ImageReceiver r2 = r15.stickerPreloadImageReceiver
            org.telegram.messenger.ImageLocation r3 = org.telegram.messenger.ImageLocation.getForDocument(r13)
            java.lang.String r10 = "tgs"
            r12 = 0
            java.lang.String r4 = "100_100"
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r11 = r13
            r2.setImage(r3, r4, r5, r6, r7, r8, r10, r11, r12)
            return
        L8c:
            int r2 = r2 + 1
            goto L14
        L8f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.VectorAvatarThumbDrawable.setImage():void");
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.gradientTools.setBounds(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom);
        if (this.currentParent != null) {
            this.roundRadius = r0.getRoundRadius()[0];
        }
        float f = this.roundRadius;
        if (f == 0.0f) {
            canvas.drawRect(getBounds(), this.gradientTools.paint);
        } else {
            GradientTools gradientTools = this.gradientTools;
            canvas.drawRoundRect(gradientTools.bounds, f, f, gradientTools.paint);
        }
        int iCenterX = getBounds().centerX();
        int iCenterY = getBounds().centerY();
        int iWidth = ((int) (getBounds().width() * 0.7f)) >> 1;
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable != null) {
            if (animatedEmojiDrawable.getImageReceiver() != null) {
                this.animatedEmojiDrawable.getImageReceiver().setRoundRadius((int) (iWidth * 2 * 0.13f));
            }
            this.animatedEmojiDrawable.setBounds(iCenterX - iWidth, iCenterY - iWidth, iCenterX + iWidth, iCenterY + iWidth);
            this.animatedEmojiDrawable.draw(canvas);
        }
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            float f2 = iWidth * 2;
            imageReceiver.setRoundRadius((int) (0.13f * f2));
            this.imageReceiver.setImageCoords(iCenterX - iWidth, iCenterY - iWidth, f2, f2);
            this.imageReceiver.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.gradientTools.paint.setAlpha(i);
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.setAlpha(i);
        }
    }

    @Override // org.telegram.p023ui.Components.AttachableDrawable
    public void onAttachedToWindow(ImageReceiver imageReceiver) {
        if (imageReceiver == null) {
            return;
        }
        this.roundRadius = imageReceiver.getRoundRadius()[0];
        if (this.parents.isEmpty()) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.addView(this);
            }
            ImageReceiver imageReceiver2 = this.imageReceiver;
            if (imageReceiver2 != null) {
                imageReceiver2.onAttachedToWindow();
            }
            ImageReceiver imageReceiver3 = this.stickerPreloadImageReceiver;
            if (imageReceiver3 != null) {
                imageReceiver3.onAttachedToWindow();
            }
        }
        this.parents.add(imageReceiver);
        if (this.sizeStickerMarkup != null) {
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
        }
    }

    @Override // org.telegram.p023ui.Components.AttachableDrawable
    public void onDetachedFromWindow(ImageReceiver imageReceiver) {
        this.parents.remove(imageReceiver);
        if (this.parents.isEmpty()) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.removeView(this);
            }
            ImageReceiver imageReceiver2 = this.imageReceiver;
            if (imageReceiver2 != null) {
                imageReceiver2.onDetachedFromWindow();
            }
            ImageReceiver imageReceiver3 = this.stickerPreloadImageReceiver;
            if (imageReceiver3 != null) {
                imageReceiver3.onDetachedFromWindow();
            }
        }
        if (this.sizeStickerMarkup != null) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupStickersDidLoad);
        }
    }

    @Override // org.telegram.ui.Components.AnimatedEmojiSpan.InvalidateHolder
    public void invalidate() {
        Iterator it = this.parents.iterator();
        while (it.hasNext()) {
            ((ImageReceiver) it.next()).invalidate();
        }
    }

    public boolean equals(Object obj) {
        TLRPC.TL_videoSizeStickerMarkup tL_videoSizeStickerMarkup;
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VectorAvatarThumbDrawable vectorAvatarThumbDrawable = (VectorAvatarThumbDrawable) obj;
            if (this.type == vectorAvatarThumbDrawable.type) {
                GradientTools gradientTools = this.gradientTools;
                int i = gradientTools.color1;
                GradientTools gradientTools2 = vectorAvatarThumbDrawable.gradientTools;
                if (i == gradientTools2.color1 && gradientTools.color2 == gradientTools2.color2 && gradientTools.color3 == gradientTools2.color3 && gradientTools.color4 == gradientTools2.color4) {
                    AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
                    if (animatedEmojiDrawable != null && vectorAvatarThumbDrawable.animatedEmojiDrawable != null) {
                        return animatedEmojiDrawable.getDocumentId() == vectorAvatarThumbDrawable.animatedEmojiDrawable.getDocumentId();
                    }
                    TLRPC.TL_videoSizeStickerMarkup tL_videoSizeStickerMarkup2 = this.sizeStickerMarkup;
                    if (tL_videoSizeStickerMarkup2 != null && (tL_videoSizeStickerMarkup = vectorAvatarThumbDrawable.sizeStickerMarkup) != null && tL_videoSizeStickerMarkup2.stickerset.f1596id == tL_videoSizeStickerMarkup.stickerset.f1596id && tL_videoSizeStickerMarkup2.sticker_id == tL_videoSizeStickerMarkup.sticker_id) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i != NotificationCenter.groupStickersDidLoad || this.imageSeted) {
            return;
        }
        setImage();
    }

    public void setParent(ImageReceiver imageReceiver) {
        this.currentParent = imageReceiver;
    }
}
