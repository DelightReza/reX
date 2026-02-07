package org.telegram.p023ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import java.util.HashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.CanvasButton;
import org.telegram.p023ui.Components.CheckBoxBase;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.Shaker;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Components.spoilers.SpoilerEffect;
import org.telegram.p023ui.Components.spoilers.SpoilerEffect2;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.p023ui.Stories.recorder.DominantColors;
import org.telegram.p023ui.Stories.recorder.StoryPrivacyBottomSheet;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes3.dex */
public class SharedPhotoVideoCell2 extends FrameLayout {
    static boolean lastAutoDownload;
    static long lastUpdateDownloadSettingsTime;
    private final AnimatedFloat animatedProgress;
    private final AnimatedFloat animatedReordering;
    ValueAnimator animator;
    private boolean attached;
    private Text authorText;
    public ImageReceiver blurImageReceiver;
    private final RectF bounds;
    CanvasButton canvasButton;
    private boolean check2;
    CheckBoxBase checkBoxBase;
    float checkBoxProgress;
    private Path clipPath;
    float crossfadeProgress;
    float crossfadeToColumnsCount;
    SharedPhotoVideoCell2 crossfadeView;
    int currentAccount;
    MessageObject currentMessageObject;
    int currentParentColumnsCount;
    boolean drawVideoIcon;
    boolean drawViews;
    FlickerLoadingView globalGradientView;
    private Drawable gradientDrawable;
    private boolean gradientDrawableLoading;
    float highlightProgress;
    float imageAlpha;
    public ImageReceiver imageReceiver;
    public int imageReceiverColor;
    public ImageReceiver imageReceiverFullSize;
    float imageScale;
    public boolean isFirst;
    public boolean isLast;
    public boolean isSearchingHashtag;
    public boolean isStory;
    public boolean isStoryPinned;
    public boolean isStoryUploading;
    private SpoilerEffect mediaSpoilerEffect;
    private SpoilerEffect2 mediaSpoilerEffect2;
    private Path path;
    private Bitmap privacyBitmap;
    private Paint privacyPaint;
    private int privacyType;
    private final Paint progressPaint;
    private final Path rectPath;
    private boolean reorder;
    private boolean reordering;
    private final Paint scrimPaint;
    private Text sensitiveText;
    private Text sensitiveTextShort;
    private Text sensitiveTextShort2;
    private Shaker shaker;
    SharedResources sharedResources;
    boolean showVideoLayout;
    private float spoilerMaxRadius;
    private float spoilerRevealProgress;
    private float spoilerRevealX;
    private float spoilerRevealY;
    public int storyId;
    private int style;
    StaticLayout videoInfoLayot;
    String videoText;
    AnimatedFloat viewsAlpha;
    AnimatedTextView.AnimatedTextDrawable viewsText;

    /* renamed from: onCheckBoxPressed, reason: merged with bridge method [inline-methods] */
    public void lambda$setStyle$1() {
    }

    public void setCheck2() {
        this.check2 = true;
    }

    public void setReorder(boolean z) {
        this.reorder = z;
        invalidate();
    }

    public SharedPhotoVideoCell2(Context context, SharedResources sharedResources, int i) {
        super(context);
        this.imageReceiverColor = 0;
        this.imageReceiverFullSize = new ImageReceiver();
        this.imageReceiver = new ImageReceiver();
        this.blurImageReceiver = new ImageReceiver();
        this.imageAlpha = 1.0f;
        this.imageScale = 1.0f;
        this.drawVideoIcon = true;
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.viewsAlpha = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.viewsText = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.path = new Path();
        this.rectPath = new Path();
        this.style = 0;
        this.scrimPaint = new Paint(1);
        this.progressPaint = new Paint(1);
        this.animatedProgress = new AnimatedFloat(this, 0L, 200L, cubicBezierInterpolator);
        this.bounds = new RectF();
        this.animatedReordering = new AnimatedFloat(this, 0L, 320L, cubicBezierInterpolator);
        this.sharedResources = sharedResources;
        this.currentAccount = i;
        setChecked(false, false);
        this.imageReceiver.setParentView(this);
        this.imageReceiverFullSize.setParentView(this);
        this.blurImageReceiver.setParentView(this);
        this.imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
                this.f$0.lambda$new$0(imageReceiver, z, z2, z3);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void didSetImageBitmap(int i2, String str, Drawable drawable) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$didSetImageBitmap(this, i2, str, drawable);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void onAnimationReady(ImageReceiver imageReceiver) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$onAnimationReady(this, imageReceiver);
            }
        });
        this.viewsText.setCallback(this);
        this.viewsText.setTextSize(AndroidUtilities.m1146dp(12.0f));
        this.viewsText.setTextColor(Theme.isCurrentThemeMonet() ? Theme.getColor(Theme.key_chat_mediaTimeText) : -1);
        this.viewsText.setTypeface(AndroidUtilities.bold());
        this.viewsText.setOverrideFullWidth(AndroidUtilities.displaySize.x);
        setWillNotDraw(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        MessageObject messageObject;
        if (z && !z2 && (messageObject = this.currentMessageObject) != null && messageObject.hasMediaSpoilers() && this.imageReceiver.getBitmap() != null) {
            if (this.blurImageReceiver.getBitmap() != null) {
                this.blurImageReceiver.getBitmap().recycle();
            }
            this.blurImageReceiver.setImageBitmap(Utilities.stackBlurBitmapMax(this.imageReceiver.getBitmap()));
        }
        if (!z || z2 || !this.check2 || this.imageReceiver.getBitmap() == null) {
            return;
        }
        int dominantColor = AndroidUtilities.getDominantColor(this.imageReceiver.getBitmap());
        this.imageReceiverColor = dominantColor;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (checkBoxBase != null) {
            checkBoxBase.setBackgroundColor(Theme.blendOver(dominantColor, Theme.multAlpha(-1, 0.25f)));
        }
    }

    public void setStyle(int i) {
        if (this.style == i) {
            return;
        }
        this.style = i;
        if (i == 1) {
            CheckBoxBase checkBoxBase = new CheckBoxBase(this, 21, null);
            this.checkBoxBase = checkBoxBase;
            checkBoxBase.setColor(-1, Theme.key_sharedMedia_photoPlaceholder, Theme.key_checkboxCheck);
            this.checkBoxBase.setDrawUnchecked(true);
            this.checkBoxBase.setBackgroundType(0);
            this.checkBoxBase.setBounds(0, 0, AndroidUtilities.m1146dp(24.0f), AndroidUtilities.m1146dp(24.0f));
            if (this.attached) {
                this.checkBoxBase.onAttachedToWindow();
            }
            CanvasButton canvasButton = new CanvasButton(this);
            this.canvasButton = canvasButton;
            canvasButton.setDelegate(new Runnable() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setStyle$1();
                }
            });
        }
    }

    private TLRPC.MessageMedia getStoryMedia(MessageObject messageObject) {
        TL_stories.StoryItem storyItem;
        if (messageObject == null || (storyItem = messageObject.storyItem) == null) {
            return null;
        }
        return storyItem.media;
    }

    private boolean mediaEqual(TLRPC.MessageMedia messageMedia, TLRPC.MessageMedia messageMedia2) {
        TLRPC.Photo photo;
        if (messageMedia == null && messageMedia2 == null) {
            return true;
        }
        if (messageMedia != null && messageMedia2 != null) {
            TLRPC.Document document = messageMedia.document;
            if (document != null) {
                TLRPC.Document document2 = messageMedia2.document;
                return document2 != null && document2.f1579id == document.f1579id;
            }
            TLRPC.Photo photo2 = messageMedia.photo;
            if (photo2 != null && (photo = messageMedia2.photo) != null && photo.f1603id == photo2.f1603id) {
                return true;
            }
        }
        return false;
    }

    private int getPrivacyType(MessageObject messageObject) {
        TL_stories.StoryItem storyItem;
        if (this.isStoryPinned) {
            return 100;
        }
        if (!this.isStory || messageObject == null || (storyItem = messageObject.storyItem) == null) {
            return -1;
        }
        if (storyItem.parsedPrivacy == null) {
            storyItem.parsedPrivacy = new StoryPrivacyBottomSheet.StoryPrivacy(this.currentAccount, storyItem.privacy);
        }
        int i = messageObject.storyItem.parsedPrivacy.type;
        if (i == 2 || i == 1 || i == 3) {
            return i;
        }
        return -1;
    }

    public void setMessageObject(MessageObject messageObject, int i) {
        setMessageObject(messageObject, i, false);
    }

    public void initFullSizeReceiver() {
        setMessageObject(this.currentMessageObject, this.currentParentColumnsCount, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:199:0x03e6  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x042c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setMessageObject(org.telegram.messenger.MessageObject r21, int r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 1139
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.SharedPhotoVideoCell2.setMessageObject(org.telegram.messenger.MessageObject, int, boolean):void");
    }

    private void setPrivacyType(int i) {
        int i2;
        if (this.privacyType == i) {
            return;
        }
        this.privacyType = i;
        this.privacyBitmap = null;
        if (i == 1) {
            i2 = C2369R.drawable.msg_stories_closefriends;
        } else if (i == 2) {
            i2 = C2369R.drawable.msg_folders_private;
        } else if (i != 3) {
            i2 = i != 100 ? 0 : C2369R.drawable.msg_pin_mini;
        } else {
            i2 = C2369R.drawable.msg_folders_groups;
        }
        if (i2 != 0) {
            this.privacyBitmap = this.sharedResources.getPrivacyBitmap(getContext(), i2);
        }
        invalidate();
    }

    private boolean canAutoDownload(MessageObject messageObject) {
        if (System.currentTimeMillis() - lastUpdateDownloadSettingsTime > 5000) {
            lastUpdateDownloadSettingsTime = System.currentTimeMillis();
            lastAutoDownload = DownloadController.getInstance(this.currentAccount).canDownloadMedia(messageObject);
        }
        return lastAutoDownload;
    }

    public void setVideoText(String str, boolean z) {
        StaticLayout staticLayout;
        this.videoText = str;
        boolean z2 = str != null;
        this.showVideoLayout = z2;
        if (z2 && (staticLayout = this.videoInfoLayot) != null && !staticLayout.getText().toString().equals(str)) {
            this.videoInfoLayot = null;
        }
        this.drawVideoIcon = z;
    }

    private float getPadding() {
        float fDpf2;
        float fDpf22;
        float f;
        if (this.crossfadeProgress != 0.0f) {
            float f2 = this.crossfadeToColumnsCount;
            if (f2 == 9.0f || this.currentParentColumnsCount == 9) {
                if (f2 == 9.0f) {
                    fDpf2 = AndroidUtilities.dpf2(0.5f) * this.crossfadeProgress;
                    fDpf22 = AndroidUtilities.dpf2(1.0f);
                    f = this.crossfadeProgress;
                } else {
                    fDpf2 = AndroidUtilities.dpf2(1.0f) * this.crossfadeProgress;
                    fDpf22 = AndroidUtilities.dpf2(0.5f);
                    f = this.crossfadeProgress;
                }
                return fDpf2 + (fDpf22 * (1.0f - f));
            }
        }
        return this.currentParentColumnsCount == 9 ? AndroidUtilities.dpf2(0.5f) : AndroidUtilities.dpf2(1.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawImpl(canvas, false, 1.0f, 1.0f, 1.0f);
    }

    private void drawImpl(Canvas canvas, boolean z, float f, float f2, float f3) {
        float f4;
        float f5;
        boolean z2;
        float f6;
        Canvas canvas2;
        float f7;
        float f8;
        float f9;
        float f10;
        float fM1146dp;
        float fM1146dp2;
        int i;
        FlickerLoadingView flickerLoadingView;
        float padding = getPadding() * f;
        boolean z3 = this.isStory;
        float f11 = (z3 && this.isFirst) ? 0.0f : padding;
        float f12 = (z3 && this.isLast) ? 0.0f : padding;
        float f13 = this.animatedReordering.set(this.reordering);
        float measuredWidth = ((getMeasuredWidth() - f11) - f12) * this.imageScale;
        float f14 = padding * 2.0f;
        float measuredHeight = (getMeasuredHeight() - f14) * this.imageScale;
        ImageReceiver imageReceiver = z ? this.imageReceiverFullSize : this.imageReceiver;
        imageReceiver.setAlpha(f2);
        if (this.crossfadeProgress > 0.5f && this.crossfadeToColumnsCount != 9.0f && this.currentParentColumnsCount != 9) {
            float f15 = f * 2.0f;
            measuredWidth -= f15;
            measuredHeight -= f15;
        }
        float f16 = measuredHeight;
        if ((this.currentMessageObject != null || this.style == 1) && imageReceiver.hasBitmapImage() && imageReceiver.getCurrentAlpha() == 1.0f && this.imageAlpha == 1.0f) {
            f4 = measuredWidth;
            f5 = 1.0f;
            z2 = true;
            f6 = 2.0f;
        } else {
            if (getParent() == null || (flickerLoadingView = this.globalGradientView) == null) {
                f4 = measuredWidth;
                f5 = 1.0f;
                z2 = true;
                f6 = 2.0f;
            } else {
                flickerLoadingView.setParentSize(((View) getParent()).getMeasuredWidth(), getMeasuredHeight(), -getX());
                this.globalGradientView.updateColors();
                this.globalGradientView.updateGradient();
                float f17 = (this.crossfadeProgress <= 0.5f || this.crossfadeToColumnsCount == 9.0f || this.currentParentColumnsCount == 9) ? 0.0f : 1.0f;
                float f18 = f11 + f17;
                float f19 = f17 + padding;
                f4 = measuredWidth;
                f5 = 1.0f;
                z2 = true;
                f6 = 2.0f;
                canvas.drawRect(f18, f19, f18 + measuredWidth, f19 + f16, this.globalGradientView.getPaint());
            }
            invalidate();
        }
        float f20 = this.imageAlpha;
        if (f20 != f5) {
            canvas.saveLayerAlpha(0.0f, 0.0f, f11 + f12 + f4, f14 + f16, (int) (f20 * 255.0f), 31);
        } else {
            canvas.save();
        }
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (((checkBoxBase == null || !checkBoxBase.isChecked()) && !PhotoViewer.isShowingImage(this.currentMessageObject)) || this.check2) {
            canvas2 = canvas;
            f7 = padding;
            f8 = f11;
            f9 = f16;
        } else {
            canvas2 = canvas;
            f7 = padding;
            f8 = f11;
            f9 = f16;
            canvas2.drawRect(f8, f7, (f11 + f4) - f12, f9, this.sharedResources.backgroundPaint);
        }
        if (this.isStory && this.currentParentColumnsCount == z2) {
            float height = getHeight() * 0.72f;
            Drawable drawable = this.gradientDrawable;
            if (drawable == null) {
                if (!this.gradientDrawableLoading && imageReceiver.getBitmap() != null) {
                    this.gradientDrawableLoading = z2;
                    DominantColors.getColors(false, imageReceiver.getBitmap(), Theme.isCurrentThemeDark(), new Utilities.Callback() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda2
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.lambda$drawImpl$2((int[]) obj);
                        }
                    });
                }
            } else {
                drawable.setBounds(0, 0, getWidth(), getHeight());
                this.gradientDrawable.draw(canvas2);
            }
            imageReceiver.setImageCoords((f4 - height) / f6, 0.0f, height, getHeight());
        } else if (this.checkBoxProgress > 0.0f) {
            float fM1146dp3 = AndroidUtilities.m1146dp(this.check2 ? 7.0f : 10.0f) * this.checkBoxProgress;
            float f21 = f8 + fM1146dp3;
            float f22 = f7 + fM1146dp3;
            float f23 = fM1146dp3 * f6;
            float f24 = f4 - f23;
            float f25 = f9 - f23;
            imageReceiver.setImageCoords(f21, f22, f24, f25);
            this.blurImageReceiver.setImageCoords(f21, f22, f24, f25);
        } else {
            float f26 = (this.crossfadeProgress <= 0.5f || this.crossfadeToColumnsCount == 9.0f || this.currentParentColumnsCount == 9) ? 0.0f : 1.0f;
            float f27 = f8 + f26;
            float f28 = f7 + f26;
            imageReceiver.setImageCoords(f27, f28, f4, f9);
            this.blurImageReceiver.setImageCoords(f27, f28, f4, f9);
        }
        if (this.check2) {
            imageReceiver.setRoundRadius(AndroidUtilities.lerp(0, AndroidUtilities.m1146dp(8.0f), this.checkBoxProgress));
            canvas2.save();
            if (this.reorder || this.reordering) {
                canvas2.translate(imageReceiver.getCenterX(), imageReceiver.getCenterY());
                if (this.shaker == null) {
                    this.shaker = new Shaker(this);
                }
                this.shaker.concat(canvas2, Math.max(this.checkBoxProgress, f13));
                float f29 = f5 - (f13 * 0.075f);
                canvas2.scale(f29, f29);
                canvas2.translate(-imageReceiver.getCenterX(), -imageReceiver.getCenterY());
            }
        }
        if (PhotoViewer.isShowingImage(this.currentMessageObject)) {
            f10 = 0.075f;
        } else {
            imageReceiver.draw(canvas2);
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject == null || !messageObject.hasMediaSpoilers() || this.currentMessageObject.isMediaSpoilersRevealedInSharedMedia) {
                f10 = 0.075f;
            } else {
                canvas2.save();
                canvas2.clipRect(f8, f7, (f8 + f4) - f12, f7 + f9);
                if (this.spoilerRevealProgress != 0.0f) {
                    this.path.rewind();
                    this.path.addCircle(this.spoilerRevealX, this.spoilerRevealY, this.spoilerMaxRadius * this.spoilerRevealProgress, Path.Direction.CW);
                    canvas2.clipPath(this.path, Region.Op.DIFFERENCE);
                }
                this.blurImageReceiver.draw(canvas2);
                if (this.mediaSpoilerEffect2 != null) {
                    canvas2.clipRect(imageReceiver.getImageX(), imageReceiver.getImageY(), imageReceiver.getImageX2(), imageReceiver.getImageY2());
                    this.mediaSpoilerEffect2.draw(canvas2, this, (int) imageReceiver.getImageWidth(), (int) imageReceiver.getImageHeight());
                } else {
                    if (this.mediaSpoilerEffect == null) {
                        this.mediaSpoilerEffect = new SpoilerEffect();
                    }
                    this.mediaSpoilerEffect.setColor(ColorUtils.setAlphaComponent(-1, (int) (Color.alpha(-1) * 0.325f)));
                    this.mediaSpoilerEffect.setBounds((int) imageReceiver.getImageX(), (int) imageReceiver.getImageY(), (int) imageReceiver.getImageX2(), (int) imageReceiver.getImageY2());
                    this.mediaSpoilerEffect.draw(canvas2);
                }
                canvas2.restore();
                if (this.currentMessageObject.isSensitive()) {
                    if (this.sensitiveText == null) {
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("x " + LocaleController.getString(C2369R.string.MessageSensitiveContent));
                        spannableStringBuilder.setSpan(new ColoredImageSpan(C2369R.drawable.filled_sensitive), 0, 1, 33);
                        this.sensitiveText = new Text(spannableStringBuilder, 14.0f, AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                    }
                    Text text = this.sensitiveText;
                    int i2 = 13;
                    if (f4 < (AndroidUtilities.m1146dp(13) * 2) + text.getCurrentWidth()) {
                        if (this.sensitiveTextShort == null) {
                            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder("x " + LocaleController.getString(C2369R.string.MessageSensitiveContentShort));
                            spannableStringBuilder2.setSpan(new ColoredImageSpan(C2369R.drawable.filled_sensitive), 0, 1, 33);
                            this.sensitiveTextShort = new Text(spannableStringBuilder2, 14.0f, AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                        }
                        text = this.sensitiveTextShort;
                    }
                    if (f4 < (AndroidUtilities.m1146dp(26) * 2) + text.getCurrentWidth()) {
                        if (this.sensitiveTextShort2 == null) {
                            this.sensitiveTextShort2 = new Text(new SpannableStringBuilder(LocaleController.getString(C2369R.string.MessageSensitiveContentShort)), 13.0f, AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                        }
                        text = this.sensitiveTextShort2;
                        i2 = 10;
                        i = 28;
                    } else {
                        i = 32;
                    }
                    float imageX = imageReceiver.getImageX() + (imageReceiver.getImageWidth() / f6);
                    float imageY = imageReceiver.getImageY() + (imageReceiver.getImageHeight() / f6);
                    float currentWidth = text.getCurrentWidth() + AndroidUtilities.m1146dp(i2 + i2);
                    float fM1146dp4 = AndroidUtilities.m1146dp(i) / f6;
                    f10 = 0.075f;
                    float fLerp = AndroidUtilities.lerp(0.8f, 1.0f, f5 - this.spoilerRevealProgress);
                    RectF rectF = AndroidUtilities.rectTmp;
                    float f30 = currentWidth / f6;
                    float f31 = f30 * fLerp;
                    float f32 = fM1146dp4 * fLerp;
                    Text text2 = text;
                    rectF.set(imageX - f31, imageY - f32, f31 + imageX, imageY + f32);
                    this.rectPath.reset();
                    this.rectPath.addRoundRect(rectF, fM1146dp4, fM1146dp4, Path.Direction.CW);
                    canvas2.save();
                    canvas2.clipPath(this.rectPath);
                    float alpha = this.blurImageReceiver.getAlpha();
                    this.blurImageReceiver.setAlpha((1.0f - this.spoilerRevealProgress) * alpha);
                    this.blurImageReceiver.draw(canvas2);
                    this.blurImageReceiver.setAlpha(alpha);
                    canvas2.restore();
                    Paint themePaint = Theme.getThemePaint("paintChatTimeBackground");
                    int alpha2 = themePaint.getAlpha();
                    themePaint.setAlpha((int) (alpha2 * (1.0f - this.spoilerRevealProgress) * 0.35f));
                    canvas2.drawRoundRect(rectF, fM1146dp4, fM1146dp4, themePaint);
                    themePaint.setAlpha(alpha2);
                    canvas2.save();
                    canvas2.scale(fLerp, fLerp, imageX, imageY);
                    Canvas canvas3 = canvas2;
                    text2.draw(canvas3, (imageX - f30) + AndroidUtilities.m1146dp(i2), imageY, -1, 1.0f - this.spoilerRevealProgress);
                    canvas2 = canvas3;
                    canvas2.restore();
                } else {
                    f10 = 0.075f;
                }
                invalidate();
            }
            if (!this.isSearchingHashtag) {
                float f33 = this.highlightProgress;
                if (f33 > 0.0f) {
                    this.sharedResources.highlightPaint.setColor(ColorUtils.setAlphaComponent(-16777216, (int) (f33 * 0.5f * 255.0f)));
                    canvas2.drawRect(imageReceiver.getDrawRegion(), this.sharedResources.highlightPaint);
                }
            }
        }
        if (this.isStoryUploading) {
            this.scrimPaint.setColor(805306368);
            canvas2.drawRect(imageReceiver.getDrawRegion(), this.scrimPaint);
            this.progressPaint.setStyle(Paint.Style.STROKE);
            this.progressPaint.setColor(-1);
            this.progressPaint.setStrokeWidth(AndroidUtilities.m1146dp(3.0f));
            this.progressPaint.setStrokeJoin(Paint.Join.ROUND);
            this.progressPaint.setStrokeCap(Paint.Cap.ROUND);
            float fM1146dp5 = AndroidUtilities.m1146dp(18.0f);
            RectF rectF2 = AndroidUtilities.rectTmp;
            rectF2.set(imageReceiver.getCenterX() - fM1146dp5, imageReceiver.getCenterY() - fM1146dp5, imageReceiver.getCenterX() + fM1146dp5, imageReceiver.getCenterY() + fM1146dp5);
            float fCurrentTimeMillis = ((System.currentTimeMillis() % 1500) / 1500.0f) * 360.0f;
            AnimatedFloat animatedFloat = this.animatedProgress;
            MessageObject messageObject2 = this.currentMessageObject;
            canvas2.drawArc(rectF2, fCurrentTimeMillis, 360.0f * animatedFloat.set(AndroidUtilities.lerp(0.15f, 0.95f, messageObject2 != null ? messageObject2.getProgress() : 0.0f)), false, this.progressPaint);
            invalidate();
        }
        this.bounds.set(imageReceiver.getImageX(), imageReceiver.getImageY(), imageReceiver.getImageX2(), imageReceiver.getImageY2());
        drawDuration(canvas2, this.bounds, f3);
        drawViews(canvas2, this.bounds, f3);
        if (!this.isSearchingHashtag) {
            drawPrivacy(canvas2, this.bounds, f3);
        } else {
            drawAuthor(canvas2, this.bounds, f3);
        }
        if (this.check2) {
            canvas2.restore();
        }
        CheckBoxBase checkBoxBase2 = this.checkBoxBase;
        if (checkBoxBase2 != null && (this.style == 1 || checkBoxBase2.getProgress() != 0.0f)) {
            canvas2.save();
            if (this.check2 && (this.reorder || this.reordering)) {
                canvas2.translate(imageReceiver.getCenterX(), imageReceiver.getCenterY());
                if (this.shaker == null) {
                    this.shaker = new Shaker(this);
                }
                this.shaker.concat(canvas2, Math.max(this.checkBoxProgress, f13) * 0.5f);
                float f34 = 1.0f - (f13 * f10);
                canvas2.scale(f34, f34);
                canvas2.translate(-imageReceiver.getCenterX(), -imageReceiver.getCenterY());
            }
            if (this.style == 1) {
                fM1146dp = ((AndroidUtilities.m1146dp(f6) + f4) - AndroidUtilities.m1146dp(25.0f)) - AndroidUtilities.m1146dp(4.0f);
                fM1146dp2 = AndroidUtilities.m1146dp(4.0f);
            } else if (this.check2) {
                fM1146dp = (AndroidUtilities.m1146dp(f6) + f4) - AndroidUtilities.m1146dp((this.checkBoxProgress * 5.0f) + 22.0f);
                fM1146dp2 = AndroidUtilities.m1146dp(-2.0f) + (AndroidUtilities.m1146dp(5.0f) * this.checkBoxProgress);
            } else {
                fM1146dp = (AndroidUtilities.m1146dp(f6) + f4) - AndroidUtilities.m1146dp(25.0f);
                fM1146dp2 = 0.0f;
            }
            canvas2.translate(fM1146dp, fM1146dp2);
            this.checkBoxBase.draw(canvas2);
            if (this.canvasButton != null) {
                RectF rectF3 = AndroidUtilities.rectTmp;
                rectF3.set(fM1146dp, fM1146dp2, this.checkBoxBase.bounds.width() + fM1146dp, this.checkBoxBase.bounds.height() + fM1146dp2);
                this.canvasButton.setRect(rectF3);
            }
            canvas2.restore();
        }
        canvas2.restore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$drawImpl$2(int[] iArr) {
        if (this.gradientDrawableLoading) {
            this.gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, iArr);
            invalidate();
            this.gradientDrawableLoading = false;
        }
    }

    public void customDraw(View view, Canvas canvas, float f, float f2, float f3) {
        canvas.save();
        if (this.clipPath == null) {
            this.clipPath = new Path();
        }
        this.clipPath.rewind();
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(0.0f, 0.0f, f, f2);
        float fM1146dp = AndroidUtilities.m1146dp(12.0f) * f3;
        this.clipPath.addRoundRect(rectF, fM1146dp, fM1146dp, Path.Direction.CW);
        this.clipPath.close();
        canvas.clipPath(this.clipPath);
        canvas.scale(f / getWidth(), f2 / getHeight());
        boolean zHasImageLoaded = this.imageReceiverFullSize.hasImageLoaded();
        if (!zHasImageLoaded || f3 < 1.0f) {
            float f4 = 1.0f - f3;
            drawImpl(canvas, false, f4, 1.0f, f4);
        }
        if (zHasImageLoaded && f3 > 0.0f) {
            drawImpl(canvas, true, 1.0f - f3, f3, 0.0f);
        }
        canvas.restore();
    }

    public void drawDuration(Canvas canvas, RectF rectF, float f) {
        String str;
        float fPow = f;
        if (this.showVideoLayout) {
            ImageReceiver imageReceiver = this.imageReceiver;
            if (imageReceiver == null || imageReceiver.getVisible()) {
                float fWidth = rectF.width() + (AndroidUtilities.m1146dp(20.0f) * this.checkBoxProgress);
                float fWidth2 = rectF.width() / fWidth;
                if (fPow < 1.0f) {
                    fPow = (float) Math.pow(fPow, 8.0d);
                }
                canvas.save();
                canvas.translate(rectF.left, rectF.top);
                canvas.scale(fWidth2, fWidth2, 0.0f, rectF.height());
                canvas.clipRect(0.0f, 0.0f, rectF.width(), rectF.height());
                int i = this.currentParentColumnsCount;
                if (i != 9 && this.videoInfoLayot == null && (str = this.videoText) != null) {
                    this.videoInfoLayot = new StaticLayout(this.videoText, this.sharedResources.textPaint, (int) Math.ceil(this.sharedResources.textPaint.measureText(str)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                } else if ((i >= 9 || this.videoText == null) && this.videoInfoLayot != null) {
                    this.videoInfoLayot = null;
                }
                boolean zViewsOnLeft = viewsOnLeft(fWidth);
                int iM1146dp = AndroidUtilities.m1146dp(8.0f);
                StaticLayout staticLayout = this.videoInfoLayot;
                int width = iM1146dp + (staticLayout != null ? staticLayout.getWidth() : 0) + (this.drawVideoIcon ? AndroidUtilities.m1146dp(10.0f) : 0);
                canvas.translate(AndroidUtilities.m1146dp(5.0f), (((AndroidUtilities.m1146dp(1.0f) + rectF.height()) - AndroidUtilities.m1146dp(17.0f)) - AndroidUtilities.m1146dp(4.0f)) - (zViewsOnLeft ? AndroidUtilities.m1146dp(22.0f) : 0));
                RectF rectF2 = AndroidUtilities.rectTmp;
                rectF2.set(0.0f, 0.0f, width, AndroidUtilities.m1146dp(17.0f));
                int alpha = Theme.chat_timeBackgroundPaint.getAlpha();
                Theme.chat_timeBackgroundPaint.setAlpha((int) (alpha * fPow));
                canvas.drawRoundRect(rectF2, AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), Theme.chat_timeBackgroundPaint);
                Theme.chat_timeBackgroundPaint.setAlpha(alpha);
                if (this.drawVideoIcon) {
                    canvas.save();
                    canvas.translate(this.videoInfoLayot == null ? AndroidUtilities.m1146dp(5.0f) : AndroidUtilities.m1146dp(4.0f), (AndroidUtilities.m1146dp(17.0f) - this.sharedResources.playDrawable.getIntrinsicHeight()) / 2.0f);
                    if (Theme.isCurrentThemeMonet()) {
                        this.sharedResources.playDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_mediaTimeText), PorterDuff.Mode.MULTIPLY));
                    } else {
                        this.sharedResources.playDrawable.setAlpha((int) (this.imageAlpha * 255.0f * fPow));
                    }
                    this.sharedResources.playDrawable.draw(canvas);
                    canvas.restore();
                }
                if (this.videoInfoLayot != null) {
                    canvas.translate(AndroidUtilities.m1146dp((this.drawVideoIcon ? 10 : 0) + 4), (AndroidUtilities.m1146dp(17.0f) - this.videoInfoLayot.getHeight()) / 2.0f);
                    int alpha2 = this.sharedResources.textPaint.getAlpha();
                    this.sharedResources.textPaint.setAlpha((int) (alpha2 * fPow));
                    this.videoInfoLayot.draw(canvas);
                    this.sharedResources.textPaint.setAlpha(alpha2);
                }
                canvas.restore();
            }
        }
    }

    public void updateViews() {
        MessageObject messageObject;
        TL_stories.StoryItem storyItem;
        TL_stories.StoryViews storyViews;
        if (this.isStory && (messageObject = this.currentMessageObject) != null && (storyItem = messageObject.storyItem) != null && (storyViews = storyItem.views) != null) {
            int i = storyViews.views_count;
            this.drawViews = i > 0;
            this.viewsText.setText(AndroidUtilities.formatWholeNumber(i, 0), true);
        } else {
            this.drawViews = false;
            this.viewsText.setText("", false);
        }
    }

    public boolean viewsOnLeft(float f) {
        int width;
        if (this.isStory && this.currentParentColumnsCount < 5) {
            int iM1146dp = AndroidUtilities.m1146dp(26.0f) + ((int) this.viewsText.getCurrentWidth());
            if (this.showVideoLayout) {
                int iM1146dp2 = AndroidUtilities.m1146dp(8.0f);
                StaticLayout staticLayout = this.videoInfoLayot;
                width = iM1146dp2 + (staticLayout != null ? staticLayout.getWidth() : 0) + (this.drawVideoIcon ? AndroidUtilities.m1146dp(10.0f) : 0);
            } else {
                width = 0;
            }
            if (iM1146dp + ((iM1146dp <= 0 || width <= 0) ? 0 : AndroidUtilities.m1146dp(8.0f)) + width > f) {
                return true;
            }
        }
        return false;
    }

    public void drawPrivacy(Canvas canvas, RectF rectF, float f) {
        Bitmap bitmap;
        if (!this.isStory || (bitmap = this.privacyBitmap) == null || bitmap.isRecycled()) {
            return;
        }
        int iM1146dp = AndroidUtilities.m1146dp((rectF.width() / (rectF.width() + (AndroidUtilities.m1146dp(20.0f) * this.checkBoxProgress))) * 17.33f);
        canvas.save();
        float f2 = iM1146dp;
        canvas.translate((rectF.right - f2) - AndroidUtilities.m1146dp(5.66f), rectF.top + AndroidUtilities.m1146dp(5.66f));
        if (this.privacyPaint == null) {
            this.privacyPaint = new Paint(3);
        }
        this.privacyPaint.setAlpha((int) (f * 255.0f));
        RectF rectF2 = AndroidUtilities.rectTmp;
        rectF2.set(0.0f, 0.0f, f2, f2);
        canvas.drawBitmap(this.privacyBitmap, (Rect) null, rectF2, this.privacyPaint);
        canvas.restore();
    }

    public void drawAuthor(Canvas canvas, RectF rectF, float f) {
        if (this.isStory) {
            ImageReceiver imageReceiver = this.imageReceiver;
            if ((imageReceiver == null || imageReceiver.getVisible()) && this.isSearchingHashtag && this.authorText != null) {
                this.authorText.ellipsize((int) (rectF.width() - (2.0f * r0))).setVerticalClipPadding(AndroidUtilities.m1146dp(14.0f)).setShadow(0.4f * f).draw(canvas, rectF.left + AndroidUtilities.m1146dp(5.33f), rectF.top + AndroidUtilities.m1146dp(this.currentParentColumnsCount <= 2 ? 15.0f : 11.33f), Theme.multAlpha(-1, f), 1.0f);
            }
        }
    }

    public void drawViews(Canvas canvas, RectF rectF, float f) {
        if (this.isStory) {
            ImageReceiver imageReceiver = this.imageReceiver;
            if ((imageReceiver == null || imageReceiver.getVisible()) && this.currentParentColumnsCount < 5) {
                float fWidth = rectF.width() + (AndroidUtilities.m1146dp(20.0f) * this.checkBoxProgress);
                float fWidth2 = rectF.width() / fWidth;
                boolean zViewsOnLeft = viewsOnLeft(fWidth);
                float f2 = this.viewsAlpha.set(this.drawViews);
                float fPow = f * f2;
                if (fPow < 1.0f) {
                    fPow = (float) Math.pow(fPow, 8.0d);
                }
                if (f2 <= 0.0f) {
                    return;
                }
                canvas.save();
                canvas.translate(rectF.left, rectF.top);
                canvas.scale(fWidth2, fWidth2, zViewsOnLeft ? 0.0f : rectF.width(), rectF.height());
                canvas.clipRect(0.0f, 0.0f, rectF.width(), rectF.height());
                float fM1146dp = AndroidUtilities.m1146dp(26.0f) + this.viewsText.getCurrentWidth();
                canvas.translate(zViewsOnLeft ? AndroidUtilities.m1146dp(5.0f) : (rectF.width() - AndroidUtilities.m1146dp(5.0f)) - fM1146dp, ((AndroidUtilities.m1146dp(1.0f) + rectF.height()) - AndroidUtilities.m1146dp(17.0f)) - AndroidUtilities.m1146dp(4.0f));
                RectF rectF2 = AndroidUtilities.rectTmp;
                rectF2.set(0.0f, 0.0f, fM1146dp, AndroidUtilities.m1146dp(17.0f));
                int alpha = Theme.chat_timeBackgroundPaint.getAlpha();
                Theme.chat_timeBackgroundPaint.setAlpha((int) (alpha * fPow));
                canvas.drawRoundRect(rectF2, AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), Theme.chat_timeBackgroundPaint);
                Theme.chat_timeBackgroundPaint.setAlpha(alpha);
                canvas.save();
                canvas.translate(AndroidUtilities.m1146dp(3.0f), (AndroidUtilities.m1146dp(17.0f) - this.sharedResources.viewDrawable.getBounds().height()) / 2.0f);
                if (Theme.isCurrentThemeMonet()) {
                    this.sharedResources.viewDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_mediaTimeText), PorterDuff.Mode.MULTIPLY));
                } else {
                    this.sharedResources.viewDrawable.setAlpha((int) (this.imageAlpha * 255.0f * fPow));
                }
                this.sharedResources.viewDrawable.draw(canvas);
                canvas.restore();
                canvas.translate(AndroidUtilities.m1146dp(22.0f), 0.0f);
                this.viewsText.setBounds(0, 0, (int) fM1146dp, AndroidUtilities.m1146dp(17.0f));
                this.viewsText.setAlpha((int) (fPow * 255.0f));
                this.viewsText.draw(canvas);
                canvas.restore();
            }
        }
    }

    public boolean canRevealSpoiler() {
        MessageObject messageObject = this.currentMessageObject;
        return messageObject != null && messageObject.hasMediaSpoilers() && this.spoilerRevealProgress == 0.0f && !this.currentMessageObject.isMediaSpoilersRevealedInSharedMedia;
    }

    public void startRevealMedia(float f, float f2) {
        this.spoilerRevealX = f;
        this.spoilerRevealY = f2;
        this.spoilerMaxRadius = (float) Math.sqrt(Math.pow(getWidth(), 2.0d) + Math.pow(getHeight(), 2.0d));
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration((long) MathUtils.clamp(this.spoilerMaxRadius * 0.3f, 250.0f, 550.0f));
        duration.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startRevealMedia$3(valueAnimator);
            }
        });
        duration.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SharedPhotoVideoCell2 sharedPhotoVideoCell2 = SharedPhotoVideoCell2.this;
                sharedPhotoVideoCell2.currentMessageObject.isMediaSpoilersRevealedInSharedMedia = true;
                sharedPhotoVideoCell2.invalidate();
            }
        });
        duration.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRevealMedia$3(ValueAnimator valueAnimator) {
        this.spoilerRevealProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attached = true;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (checkBoxBase != null) {
            checkBoxBase.onAttachedToWindow();
        }
        if (this.currentMessageObject != null) {
            this.imageReceiver.onAttachedToWindow();
            this.imageReceiverFullSize.onAttachedToWindow();
            this.blurImageReceiver.onAttachedToWindow();
        }
        SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
        if (spoilerEffect2 != null) {
            if (spoilerEffect2.destroyed) {
                this.mediaSpoilerEffect2 = SpoilerEffect2.getInstance(this);
            } else {
                spoilerEffect2.attach(this);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attached = false;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if (checkBoxBase != null) {
            checkBoxBase.onDetachedFromWindow();
        }
        if (this.currentMessageObject != null) {
            this.imageReceiver.onDetachedFromWindow();
            this.imageReceiverFullSize.onDetachedFromWindow();
            this.blurImageReceiver.onDetachedFromWindow();
        }
        SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
        if (spoilerEffect2 != null) {
            spoilerEffect2.detach(this);
        }
    }

    public void setGradientView(FlickerLoadingView flickerLoadingView) {
        this.globalGradientView = flickerLoadingView;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        boolean z = this.isStory;
        int i3 = z ? (int) (size * 1.25f) : size;
        if (z && this.currentParentColumnsCount == 1) {
            i3 /= 2;
        }
        setMeasuredDimension(size, i3);
        updateSpoilers2();
    }

    private void updateSpoilers2() {
        if (getMeasuredHeight() <= 0 || getMeasuredWidth() <= 0) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.hasMediaSpoilers() && SpoilerEffect2.supports()) {
            if (this.mediaSpoilerEffect2 == null) {
                this.mediaSpoilerEffect2 = SpoilerEffect2.getInstance(this);
            }
        } else {
            SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
            if (spoilerEffect2 != null) {
                spoilerEffect2.detach(this);
                this.mediaSpoilerEffect2 = null;
            }
        }
    }

    public int getMessageId() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null) {
            return messageObject.getId();
        }
        return 0;
    }

    public MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public void setImageAlpha(float f, boolean z) {
        if (this.imageAlpha != f) {
            this.imageAlpha = f;
            if (z) {
                invalidate();
            }
        }
    }

    public void setImageScale(float f, boolean z) {
        if (this.imageScale != f) {
            this.imageScale = f;
            if (z) {
                invalidate();
            }
        }
    }

    public void setCrossfadeView(SharedPhotoVideoCell2 sharedPhotoVideoCell2, float f, int i) {
        this.crossfadeView = sharedPhotoVideoCell2;
        this.crossfadeProgress = f;
        this.crossfadeToColumnsCount = i;
    }

    public void drawCrossafadeImage(Canvas canvas) {
        if (this.crossfadeView != null) {
            canvas.save();
            canvas.translate(getX(), getY());
            this.crossfadeView.setImageScale(((getMeasuredWidth() - AndroidUtilities.m1146dp(2.0f)) * this.imageScale) / (this.crossfadeView.getMeasuredWidth() - AndroidUtilities.m1146dp(2.0f)), false);
            this.crossfadeView.draw(canvas);
            canvas.restore();
        }
    }

    public View getCrossfadeView() {
        return this.crossfadeView;
    }

    public void setChecked(final boolean z, boolean z2) {
        int i;
        CheckBoxBase checkBoxBase = this.checkBoxBase;
        if ((checkBoxBase != null && checkBoxBase.isChecked()) == z) {
            return;
        }
        if (this.checkBoxBase == null) {
            CheckBoxBase checkBoxBase2 = new CheckBoxBase(this, 21, null);
            this.checkBoxBase = checkBoxBase2;
            checkBoxBase2.setColor(-1, Theme.key_sharedMedia_photoPlaceholder, Theme.key_checkboxCheck);
            if (this.check2 && (i = this.imageReceiverColor) != 0) {
                this.checkBoxBase.setBackgroundColor(Theme.blendOver(i, Theme.multAlpha(-1, 0.25f)));
            }
            this.checkBoxBase.setDrawUnchecked(false);
            this.checkBoxBase.setBackgroundType(1);
            this.checkBoxBase.setBounds(0, 0, AndroidUtilities.m1146dp(24.0f), AndroidUtilities.m1146dp(24.0f));
            if (this.attached) {
                this.checkBoxBase.onAttachedToWindow();
            }
        }
        this.checkBoxBase.setChecked(z, z2);
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            this.animator = null;
            valueAnimator.cancel();
        }
        if (z2) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.checkBoxProgress, z ? 1.0f : 0.0f);
            this.animator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    SharedPhotoVideoCell2.this.checkBoxProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    SharedPhotoVideoCell2.this.invalidate();
                }
            });
            this.animator.setDuration(200L);
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.SharedPhotoVideoCell2.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ValueAnimator valueAnimator2 = SharedPhotoVideoCell2.this.animator;
                    if (valueAnimator2 == null || !valueAnimator2.equals(animator)) {
                        return;
                    }
                    SharedPhotoVideoCell2 sharedPhotoVideoCell2 = SharedPhotoVideoCell2.this;
                    sharedPhotoVideoCell2.checkBoxProgress = z ? 1.0f : 0.0f;
                    sharedPhotoVideoCell2.animator = null;
                }
            });
            this.animator.start();
        } else {
            this.checkBoxProgress = z ? 1.0f : 0.0f;
        }
        invalidate();
    }

    public void setHighlightProgress(float f) {
        if (this.highlightProgress != f) {
            this.highlightProgress = f;
            invalidate();
        }
    }

    public int getStyle() {
        return this.style;
    }

    public static class SharedResources {
        Drawable playDrawable;
        Drawable viewDrawable;
        TextPaint textPaint = new TextPaint(1);
        private Paint backgroundPaint = new Paint();
        Paint highlightPaint = new Paint();
        SparseArray imageFilters = new SparseArray();
        private final HashMap privacyBitmaps = new HashMap();

        public SharedResources(Context context, Theme.ResourcesProvider resourcesProvider) {
            this.textPaint.setTextSize(AndroidUtilities.m1146dp(12.0f));
            this.textPaint.setColor(Theme.isCurrentThemeMonet() ? Theme.getColor(Theme.key_chat_mediaTimeText) : -1);
            this.textPaint.setTypeface(AndroidUtilities.bold());
            Drawable drawableMutate = ContextCompat.getDrawable(context, C2369R.drawable.play_mini_video).mutate();
            this.playDrawable = drawableMutate;
            drawableMutate.setBounds(0, 0, drawableMutate.getIntrinsicWidth(), this.playDrawable.getIntrinsicHeight());
            Drawable drawableMutate2 = ContextCompat.getDrawable(context, C2369R.drawable.filled_views).mutate();
            this.viewDrawable = drawableMutate2;
            drawableMutate2.setBounds(0, 0, (int) (drawableMutate2.getIntrinsicWidth() * 0.7f), (int) (this.viewDrawable.getIntrinsicHeight() * 0.7f));
            this.backgroundPaint.setColor(Theme.getColor(Theme.key_sharedMedia_photoPlaceholder, resourcesProvider));
        }

        public String getFilterString(int i) {
            String str = (String) this.imageFilters.get(i);
            if (str != null) {
                return str;
            }
            String str2 = i + "_" + i + "_isc";
            this.imageFilters.put(i, str2);
            return str2;
        }

        public Bitmap getPrivacyBitmap(Context context, int i) {
            Bitmap bitmap = (Bitmap) this.privacyBitmaps.get(Integer.valueOf(i));
            if (bitmap != null) {
                return bitmap;
            }
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(context.getResources(), i);
            int width = bitmapDecodeResource.getWidth();
            int height = bitmapDecodeResource.getHeight();
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint(3);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            paint.setColorFilter(new PorterDuffColorFilter(-10461088, mode));
            canvas.drawBitmap(bitmapDecodeResource, 0.0f, 0.0f, paint);
            Utilities.stackBlurBitmap(bitmapCreateBitmap, AndroidUtilities.m1146dp(1.0f));
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight(), config);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint);
            paint.setColorFilter(new PorterDuffColorFilter(-1, mode));
            canvas2.drawBitmap(bitmapDecodeResource, 0.0f, 0.0f, paint);
            bitmapCreateBitmap.recycle();
            bitmapDecodeResource.recycle();
            this.privacyBitmaps.put(Integer.valueOf(i), bitmapCreateBitmap2);
            return bitmapCreateBitmap2;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CanvasButton canvasButton = this.canvasButton;
        if (canvasButton == null || !canvasButton.checkTouchEvent(motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return this.viewsText == drawable || super.verifyDrawable(drawable);
    }

    public void setReordering(boolean z, boolean z2) {
        if (this.reordering == z) {
            return;
        }
        this.reordering = z;
        if (!z2) {
            this.animatedReordering.force(z);
        }
        invalidate();
    }
}
