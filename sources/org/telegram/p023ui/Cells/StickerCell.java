package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Premium.PremiumLockIconView;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class StickerCell extends FrameLayout {
    private static AccelerateInterpolator interpolator = new AccelerateInterpolator(0.5f);
    private boolean clearsInputField;
    private BackupImageView imageView;
    private boolean isPremiumSticker;
    private long lastUpdateTime;
    private Object parentObject;
    private float premiumAlpha;
    private PremiumLockIconView premiumIconView;
    Theme.ResourcesProvider resourcesProvider;
    private float scale;
    private boolean scaled;
    private boolean showPremiumLock;
    private TLRPC.Document sticker;
    private long time;

    public StickerCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.time = 0L;
        this.premiumAlpha = 1.0f;
        this.resourcesProvider = resourcesProvider;
        BackupImageView backupImageView = new BackupImageView(context);
        this.imageView = backupImageView;
        backupImageView.setAspectFit(true);
        this.imageView.setLayerNum(1);
        addView(this.imageView, LayoutHelper.createFrame(66, 66.0f, 1, 0.0f, 5.0f, 0.0f, 0.0f));
        setFocusable(true);
        PremiumLockIconView premiumLockIconView = new PremiumLockIconView(context, PremiumLockIconView.TYPE_STICKERS_PREMIUM_LOCKED);
        this.premiumIconView = premiumLockIconView;
        premiumLockIconView.setPadding(AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f));
        this.premiumIconView.setImageReceiver(this.imageView.getImageReceiver());
        addView(this.premiumIconView, LayoutHelper.createFrame(24, 24.0f, 81, 0.0f, 0.0f, 0.0f, 0.0f));
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(76.0f) + getPaddingLeft() + getPaddingRight(), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(78.0f), TLObject.FLAG_30));
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        if (this.imageView.getImageReceiver().getPressed() != z) {
            this.imageView.getImageReceiver().setPressed(z ? 1 : 0);
            this.imageView.invalidate();
        }
        super.setPressed(z);
    }

    public void setClearsInputField(boolean z) {
        this.clearsInputField = z;
    }

    public boolean isClearsInputField() {
        return this.clearsInputField;
    }

    public void setSticker(TLRPC.Document document, Object obj) {
        this.parentObject = obj;
        boolean zIsPremiumSticker = MessageObject.isPremiumSticker(document);
        this.isPremiumSticker = zIsPremiumSticker;
        if (zIsPremiumSticker) {
            this.premiumIconView.setColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.premiumIconView.setWaitingImage();
        }
        if (document != null) {
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 90);
            SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(document, Theme.key_windowBackgroundGray, 1.0f, 1.0f, this.resourcesProvider);
            if (MessageObject.canAutoplayAnimatedSticker(document)) {
                if (svgThumb != null) {
                    this.imageView.setImage(ImageLocation.getForDocument(document), "80_80", (String) null, svgThumb, this.parentObject);
                } else if (closestPhotoSizeWithSize != null) {
                    this.imageView.setImage(ImageLocation.getForDocument(document), "80_80", ImageLocation.getForDocument(closestPhotoSizeWithSize, document), (String) null, 0, this.parentObject);
                } else {
                    this.imageView.setImage(ImageLocation.getForDocument(document), "80_80", (String) null, (Drawable) null, this.parentObject);
                }
            } else if (svgThumb == null) {
                this.imageView.setImage(ImageLocation.getForDocument(closestPhotoSizeWithSize, document), (String) null, "webp", (Drawable) null, this.parentObject);
            } else if (closestPhotoSizeWithSize != null) {
                this.imageView.setImage(ImageLocation.getForDocument(closestPhotoSizeWithSize, document), (String) null, "webp", svgThumb, this.parentObject);
            } else {
                this.imageView.setImage(ImageLocation.getForDocument(document), (String) null, "webp", svgThumb, this.parentObject);
            }
        }
        this.sticker = document;
        Drawable background = getBackground();
        if (background != null) {
            background.setAlpha(230);
            background.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_stickersHintPanel), PorterDuff.Mode.MULTIPLY));
        }
        updatePremiumStatus(false);
    }

    public TLRPC.Document getSticker() {
        return this.sticker;
    }

    public Object getParentObject() {
        return this.parentObject;
    }

    public void setScaled(boolean z) {
        this.scaled = z;
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    public boolean showingBitmap() {
        return this.imageView.getImageReceiver().getBitmap() != null;
    }

    public MessageObject.SendAnimationData getSendAnimationData() {
        ImageReceiver imageReceiver = this.imageView.getImageReceiver();
        if (!imageReceiver.hasNotThumb()) {
            return null;
        }
        MessageObject.SendAnimationData sendAnimationData = new MessageObject.SendAnimationData();
        this.imageView.getLocationInWindow(new int[2]);
        sendAnimationData.f1452x = imageReceiver.getCenterX() + r2[0];
        sendAnimationData.f1453y = imageReceiver.getCenterY() + r2[1];
        sendAnimationData.width = imageReceiver.getImageWidth();
        sendAnimationData.height = imageReceiver.getImageHeight();
        return sendAnimationData;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean drawChild(android.graphics.Canvas r5, android.view.View r6, long r7) {
        /*
            r4 = this;
            boolean r5 = super.drawChild(r5, r6, r7)
            org.telegram.ui.Components.BackupImageView r7 = r4.imageView
            if (r6 != r7) goto L64
            boolean r6 = r4.scaled
            r7 = 1065353216(0x3f800000, float:1.0)
            r8 = 1061997773(0x3f4ccccd, float:0.8)
            if (r6 == 0) goto L17
            float r0 = r4.scale
            int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r0 != 0) goto L1f
        L17:
            if (r6 != 0) goto L64
            float r6 = r4.scale
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 == 0) goto L64
        L1f:
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = r4.lastUpdateTime
            long r2 = r0 - r2
            r4.lastUpdateTime = r0
            boolean r6 = r4.scaled
            r0 = 1137180672(0x43c80000, float:400.0)
            if (r6 == 0) goto L41
            float r6 = r4.scale
            int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r1 == 0) goto L41
            float r7 = (float) r2
            float r7 = r7 / r0
            float r6 = r6 - r7
            r4.scale = r6
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 >= 0) goto L4e
            r4.scale = r8
            goto L4e
        L41:
            float r6 = r4.scale
            float r8 = (float) r2
            float r8 = r8 / r0
            float r6 = r6 + r8
            r4.scale = r6
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L4e
            r4.scale = r7
        L4e:
            org.telegram.ui.Components.BackupImageView r6 = r4.imageView
            float r7 = r4.scale
            r6.setScaleX(r7)
            org.telegram.ui.Components.BackupImageView r6 = r4.imageView
            float r7 = r4.scale
            r6.setScaleY(r7)
            org.telegram.ui.Components.BackupImageView r6 = r4.imageView
            r6.invalidate()
            r4.invalidate()
        L64:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.StickerCell.drawChild(android.graphics.Canvas, android.view.View, long):boolean");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.sticker == null) {
            return;
        }
        String str = null;
        for (int i = 0; i < this.sticker.attributes.size(); i++) {
            TLRPC.DocumentAttribute documentAttribute = this.sticker.attributes.get(i);
            if (documentAttribute instanceof TLRPC.TL_documentAttributeSticker) {
                String str2 = documentAttribute.alt;
                str = (str2 == null || str2.length() <= 0) ? null : documentAttribute.alt;
            }
        }
        if (str != null) {
            accessibilityNodeInfo.setText(str + " " + LocaleController.getString(C2369R.string.AttachSticker));
        } else {
            accessibilityNodeInfo.setText(LocaleController.getString(C2369R.string.AttachSticker));
        }
        accessibilityNodeInfo.setEnabled(true);
    }

    private void updatePremiumStatus(boolean z) {
        if (this.isPremiumSticker) {
            this.showPremiumLock = true;
        } else {
            this.showPremiumLock = false;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.premiumIconView.getLayoutParams();
        if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
            int iM1146dp = AndroidUtilities.m1146dp(24.0f);
            layoutParams.width = iM1146dp;
            layoutParams.height = iM1146dp;
            layoutParams.gravity = 81;
            layoutParams.rightMargin = 0;
            layoutParams.bottomMargin = 0;
            this.premiumIconView.setPadding(AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f));
        } else {
            int iM1146dp2 = AndroidUtilities.m1146dp(16.0f);
            layoutParams.width = iM1146dp2;
            layoutParams.height = iM1146dp2;
            layoutParams.gravity = 85;
            layoutParams.bottomMargin = AndroidUtilities.m1146dp(8.0f);
            layoutParams.rightMargin = AndroidUtilities.m1146dp(8.0f);
            this.premiumIconView.setPadding(AndroidUtilities.m1146dp(1.0f), AndroidUtilities.m1146dp(1.0f), AndroidUtilities.m1146dp(1.0f), AndroidUtilities.m1146dp(1.0f));
        }
        this.premiumIconView.setLocked(!UserConfig.getInstance(UserConfig.selectedAccount).isPremium());
        AndroidUtilities.updateViewVisibilityAnimated(this.premiumIconView, this.showPremiumLock, 0.9f, z);
        invalidate();
    }
}
