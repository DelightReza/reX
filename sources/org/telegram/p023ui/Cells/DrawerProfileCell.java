package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.badges.BadgesController;
import com.radolyn.ayugram.AyuConfig;
import java.util.ArrayList;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.p023ui.ActionBar.DrawerLayoutContainer;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Premium.PremiumGradient;
import org.telegram.p023ui.Components.Premium.StarParticlesView;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.RLottieImageView;
import org.telegram.p023ui.Components.Reactions.AnimatedEmojiEffect;
import org.telegram.p023ui.Components.Reactions.HwEmojis;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p023ui.Components.SnowflakesEffect;
import org.telegram.p023ui.ThemeActivity;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public abstract class DrawerProfileCell extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private static RLottieDrawable sunDrawable;
    public static boolean switchingTheme;
    private boolean accountsShown;
    private AnimatedStatusView animatedStatus;
    private ImageView arrowView;
    private BackupImageView avatarImageView;
    private Paint backPaint;
    private Integer currentColor;
    private Integer currentMoonColor;
    private int darkThemeBackgroundColor;
    private RLottieImageView darkThemeView;
    private Rect destRect;
    public boolean drawPremium;
    public float drawPremiumProgress;
    PremiumGradient.PremiumGradientTools gradientTools;
    private int lastAccount;
    private TLRPC.User lastUser;
    private SimpleTextView nameTextView;
    private Paint paint;
    private TextView phoneTextView;
    private Drawable premiumStar;
    private ImageView shadowView;
    private SnowflakesEffect snowflakesEffect;
    private Rect srcRect;
    StarParticlesView.Drawable starParticlesDrawable;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable status;
    private Long statusGiftId;
    private boolean updateRightDrawable;

    protected abstract void onPremiumClick();

    public DrawerProfileCell(Context context, final DrawerLayoutContainer drawerLayoutContainer) {
        super(context);
        this.updateRightDrawable = true;
        this.srcRect = new Rect();
        this.destRect = new Rect();
        this.paint = new Paint();
        this.backPaint = new Paint(1);
        this.lastAccount = -1;
        this.lastUser = null;
        this.premiumStar = null;
        ImageView imageView = new ImageView(context);
        this.shadowView = imageView;
        imageView.setVisibility(4);
        this.shadowView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.shadowView.setImageResource(C2369R.drawable.bottom_shadow);
        addView(this.shadowView, LayoutHelper.createFrame(-1, 70, 83));
        BackupImageView backupImageView = new BackupImageView(context);
        this.avatarImageView = backupImageView;
        backupImageView.getImageReceiver().setRoundRadius(ExteraConfig.getAvatarCorners(64.0f));
        addView(this.avatarImageView, LayoutHelper.createFrame(64, 64.0f, 83, 16.0f, 0.0f, 0.0f, 67.0f));
        SimpleTextView simpleTextView = new SimpleTextView(context) { // from class: org.telegram.ui.Cells.DrawerProfileCell.1
            @Override // org.telegram.p023ui.ActionBar.SimpleTextView, android.view.View
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (DrawerProfileCell.this.updateRightDrawable) {
                    DrawerProfileCell.this.updateRightDrawable = false;
                    DrawerProfileCell.this.getEmojiStatusLocation(AndroidUtilities.rectTmp2);
                    DrawerProfileCell.this.animatedStatus.translate(r0.centerX(), r0.centerY());
                }
            }

            @Override // android.view.View
            public void invalidate() {
                if (HwEmojis.grab(this)) {
                    return;
                }
                super.invalidate();
            }

            @Override // android.view.View
            public void invalidate(int i, int i2, int i3, int i4) {
                if (HwEmojis.grab(this)) {
                    return;
                }
                super.invalidate(i, i2, i3, i4);
            }

            @Override // org.telegram.p023ui.ActionBar.SimpleTextView, android.view.View, android.graphics.drawable.Drawable.Callback
            public void invalidateDrawable(Drawable drawable) {
                if (HwEmojis.grab(this)) {
                    return;
                }
                super.invalidateDrawable(drawable);
            }

            @Override // android.view.View
            public void invalidate(Rect rect) {
                if (HwEmojis.grab(this)) {
                    return;
                }
                super.invalidate(rect);
            }
        };
        this.nameTextView = simpleTextView;
        simpleTextView.setRightDrawableOnClick(new View.OnClickListener() { // from class: org.telegram.ui.Cells.DrawerProfileCell$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        this.nameTextView.setPadding(0, AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f));
        this.nameTextView.setTextSize(15);
        this.nameTextView.setTypeface(AndroidUtilities.bold());
        this.nameTextView.setGravity(19);
        this.nameTextView.setEllipsizeByGradient(true);
        this.nameTextView.setRightDrawableOutside(true);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 83, 16.0f, 0.0f, 52.0f, 28.0f));
        TextView textView = new TextView(context);
        this.phoneTextView = textView;
        textView.setTextSize(1, 13.0f);
        this.phoneTextView.setLines(1);
        this.phoneTextView.setMaxLines(1);
        this.phoneTextView.setSingleLine(true);
        this.phoneTextView.setGravity(3);
        addView(this.phoneTextView, LayoutHelper.createFrame(-1, -2.0f, 83, 16.0f, 0.0f, 52.0f, 9.0f));
        ImageView imageView2 = new ImageView(context);
        this.arrowView = imageView2;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView2.setScaleType(scaleType);
        ImageView imageView3 = this.arrowView;
        int i = Theme.key_chats_menuName;
        int color = Theme.getColor(i);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        imageView3.setColorFilter(new PorterDuffColorFilter(color, mode));
        this.arrowView.setImageResource(C2369R.drawable.msg_expand);
        addView(this.arrowView, LayoutHelper.createFrame(59, 59, 85));
        setArrowState(false);
        boolean z = sunDrawable == null;
        if (z) {
            RLottieDrawable rLottieDrawable = new RLottieDrawable(C2369R.raw.sun_outline, "" + C2369R.raw.sun_outline, AndroidUtilities.m1146dp(28.0f), AndroidUtilities.m1146dp(28.0f), true, null);
            sunDrawable = rLottieDrawable;
            rLottieDrawable.setPlayInDirectionOfCustomEndFrame(true);
            if (Theme.isCurrentThemeDay()) {
                sunDrawable.setCustomEndFrame(0);
                sunDrawable.setCurrentFrame(0);
            } else {
                sunDrawable.setCurrentFrame(35);
                sunDrawable.setCustomEndFrame(36);
            }
        }
        RLottieImageView rLottieImageView = new RLottieImageView(context) { // from class: org.telegram.ui.Cells.DrawerProfileCell.2
            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                if (Theme.isCurrentThemeDark()) {
                    accessibilityNodeInfo.setText(LocaleController.getString(C2369R.string.AccDescrSwitchToDayTheme));
                } else {
                    accessibilityNodeInfo.setText(LocaleController.getString(C2369R.string.AccDescrSwitchToNightTheme));
                }
            }
        };
        this.darkThemeView = rLottieImageView;
        rLottieImageView.setFocusable(true);
        this.darkThemeView.setBackground(Theme.createCircleSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 0, 0));
        sunDrawable.beginApplyLayerColors();
        int color2 = Theme.getColor(i);
        sunDrawable.setLayerColor("Sunny.**", color2);
        sunDrawable.setLayerColor("Path.**", color2);
        sunDrawable.setLayerColor("Path 10.**", color2);
        sunDrawable.setLayerColor("Path 11.**", color2);
        sunDrawable.commitApplyLayerColors();
        sunDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i), mode));
        this.darkThemeView.setScaleType(scaleType);
        this.darkThemeView.setAnimation(sunDrawable);
        RLottieImageView rLottieImageView2 = this.darkThemeView;
        int color3 = Theme.getColor(Theme.key_listSelector);
        this.darkThemeBackgroundColor = color3;
        rLottieImageView2.setBackgroundDrawable(Theme.createSelectorDrawable(color3, 1, AndroidUtilities.m1146dp(17.0f)));
        Theme.setRippleDrawableForceSoftware((RippleDrawable) this.darkThemeView.getBackground());
        if (!z && sunDrawable.getCustomEndFrame() != sunDrawable.getCurrentFrame()) {
            this.darkThemeView.playAnimation();
        }
        this.darkThemeView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Cells.DrawerProfileCell$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$2(drawerLayoutContainer, view);
            }
        });
        this.darkThemeView.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Cells.DrawerProfileCell$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return DrawerProfileCell.m5955$r8$lambda$vmOIi2nxjZpXV9p5UMm36KPVNI(drawerLayoutContainer, view);
            }
        });
        addView(this.darkThemeView, LayoutHelper.createFrame(48, 48.0f, 85, 0.0f, 0.0f, 6.0f, 90.0f));
        if (Theme.canStartHolidayAnimation() || ExteraConfig.forceSnow) {
            SnowflakesEffect snowflakesEffect = new SnowflakesEffect(0);
            this.snowflakesEffect = snowflakesEffect;
            snowflakesEffect.setColorKey(i);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this, AndroidUtilities.m1146dp(20.0f));
        this.status = swapAnimatedEmojiDrawable;
        this.nameTextView.setRightDrawable(swapAnimatedEmojiDrawable);
        AnimatedStatusView animatedStatusView = new AnimatedStatusView(context, 20, 60);
        this.animatedStatus = animatedStatusView;
        addView(animatedStatusView, LayoutHelper.createFrame(20, 20, 51));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        TLRPC.User user = this.lastUser;
        if ((user == null || !user.premium) && !AyuConfig.localPremium) {
            return;
        }
        onPremiumClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$new$2(final org.telegram.p023ui.ActionBar.DrawerLayoutContainer r7, android.view.View r8) {
        /*
            r6 = this;
            boolean r8 = org.telegram.p023ui.Cells.DrawerProfileCell.switchingTheme
            if (r8 == 0) goto L6
            goto Lab
        L6:
            r8 = 1
            org.telegram.p023ui.Cells.DrawerProfileCell.switchingTheme = r8
            android.content.Context r8 = org.telegram.messenger.ApplicationLoader.applicationContext
            java.lang.String r0 = "themeconfig"
            r1 = 0
            android.content.SharedPreferences r8 = r8.getSharedPreferences(r0, r1)
            java.lang.String r0 = "lastDayTheme"
            java.lang.String r2 = "Blue"
            java.lang.String r0 = r8.getString(r0, r2)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p023ui.ActionBar.Theme.getTheme(r0)
            if (r3 == 0) goto L2b
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p023ui.ActionBar.Theme.getTheme(r0)
            boolean r3 = r3.isDark()
            if (r3 == 0) goto L2c
        L2b:
            r0 = r2
        L2c:
            java.lang.String r3 = "lastDarkTheme"
            java.lang.String r4 = "Dark Blue"
            java.lang.String r8 = r8.getString(r3, r4)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p023ui.ActionBar.Theme.getTheme(r8)
            if (r3 == 0) goto L44
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p023ui.ActionBar.Theme.getTheme(r8)
            boolean r3 = r3.isDark()
            if (r3 != 0) goto L45
        L44:
            r8 = r4
        L45:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r3 = org.telegram.p023ui.ActionBar.Theme.getActiveTheme()
            boolean r5 = r0.equals(r8)
            if (r5 == 0) goto L68
            boolean r5 = r3.isDark()
            if (r5 != 0) goto L66
            boolean r5 = r0.equals(r4)
            if (r5 != 0) goto L66
            java.lang.String r5 = "Night"
            boolean r5 = r0.equals(r5)
            if (r5 == 0) goto L64
            goto L66
        L64:
            r2 = r0
            goto L6a
        L66:
            r4 = r8
            goto L6a
        L68:
            r4 = r8
            goto L64
        L6a:
            java.lang.String r8 = r3.getKey()
            boolean r8 = r2.equals(r8)
            if (r8 == 0) goto L80
            org.telegram.ui.ActionBar.Theme$ThemeInfo r0 = org.telegram.p023ui.ActionBar.Theme.getTheme(r4)
            org.telegram.ui.Components.RLottieDrawable r1 = org.telegram.p023ui.Cells.DrawerProfileCell.sunDrawable
            r2 = 36
            r1.setCustomEndFrame(r2)
            goto L89
        L80:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r0 = org.telegram.p023ui.ActionBar.Theme.getTheme(r2)
            org.telegram.ui.Components.RLottieDrawable r2 = org.telegram.p023ui.Cells.DrawerProfileCell.sunDrawable
            r2.setCustomEndFrame(r1)
        L89:
            org.telegram.ui.Components.RLottieImageView r1 = r6.darkThemeView
            r1.playAnimation()
            r6.switchTheme(r0, r8)
            if (r7 == 0) goto Lab
            android.view.ViewParent r8 = r7.getParent()
            boolean r8 = r8 instanceof android.widget.FrameLayout
            if (r8 == 0) goto La2
            android.view.ViewParent r8 = r7.getParent()
            android.widget.FrameLayout r8 = (android.widget.FrameLayout) r8
            goto La3
        La2:
            r8 = 0
        La3:
            org.telegram.ui.Cells.DrawerProfileCell$$ExternalSyntheticLambda3 r0 = new org.telegram.ui.Cells.DrawerProfileCell$$ExternalSyntheticLambda3
            r0.<init>()
            org.telegram.p023ui.ActionBar.Theme.turnOffAutoNight(r8, r0)
        Lab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DrawerProfileCell.lambda$new$2(org.telegram.ui.ActionBar.DrawerLayoutContainer, android.view.View):void");
    }

    public static /* synthetic */ void $r8$lambda$TCxVxJuXglAZO9moA_H3guv1A1k(DrawerLayoutContainer drawerLayoutContainer) {
        drawerLayoutContainer.closeDrawer(false);
        drawerLayoutContainer.presentFragment(new ThemeActivity(1));
    }

    /* renamed from: $r8$lambda$vmOIi2nxjZpXV9p5UM-m36KPVNI, reason: not valid java name */
    public static /* synthetic */ boolean m5955$r8$lambda$vmOIi2nxjZpXV9p5UMm36KPVNI(DrawerLayoutContainer drawerLayoutContainer, View view) {
        if (drawerLayoutContainer == null) {
            return false;
        }
        drawerLayoutContainer.presentFragment(new ThemeActivity(0));
        return true;
    }

    public static class AnimatedStatusView extends View {
        private int animationUniq;
        private ArrayList animations;
        private Integer color;
        private int effectsSize;
        private int renderedEffectsSize;
        private int stateSize;

        /* renamed from: y1 */
        private float f1808y1;

        /* renamed from: y2 */
        private float f1809y2;

        public AnimatedStatusView(Context context, int i, int i2) {
            super(context);
            this.animations = new ArrayList();
            this.stateSize = i;
            this.effectsSize = i2;
            this.renderedEffectsSize = i2;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(Math.max(this.renderedEffectsSize, Math.max(this.stateSize, this.effectsSize))), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(Math.max(this.renderedEffectsSize, Math.max(this.stateSize, this.effectsSize))), TLObject.FLAG_30));
        }

        public void translate(float f, float f2) {
            setTranslationX(f - (getMeasuredWidth() / 2.0f));
            float measuredHeight = f2 - (getMeasuredHeight() / 2.0f);
            this.f1808y1 = measuredHeight;
            setTranslationY(measuredHeight + this.f1809y2);
        }

        public void translateY2(float f) {
            float f2 = this.f1808y1;
            this.f1809y2 = f;
            setTranslationY(f2 + f);
        }

        @Override // android.view.View
        public void dispatchDraw(Canvas canvas) {
            int iM1146dp = AndroidUtilities.m1146dp(this.renderedEffectsSize);
            int iM1146dp2 = AndroidUtilities.m1146dp(this.effectsSize);
            for (int i = 0; i < this.animations.size(); i++) {
                Object obj = this.animations.get(i);
                if (obj instanceof ImageReceiver) {
                    ImageReceiver imageReceiver = (ImageReceiver) obj;
                    float f = iM1146dp2;
                    imageReceiver.setImageCoords((getMeasuredWidth() - iM1146dp2) / 2.0f, (getMeasuredHeight() - iM1146dp2) / 2.0f, f, f);
                    imageReceiver.draw(canvas);
                } else if (obj instanceof AnimatedEmojiEffect) {
                    AnimatedEmojiEffect animatedEmojiEffect = (AnimatedEmojiEffect) obj;
                    animatedEmojiEffect.setBounds((int) ((getMeasuredWidth() - iM1146dp) / 2.0f), (int) ((getMeasuredHeight() - iM1146dp) / 2.0f), (int) ((getMeasuredWidth() + iM1146dp) / 2.0f), (int) ((getMeasuredHeight() + iM1146dp) / 2.0f));
                    animatedEmojiEffect.draw(canvas);
                    if (animatedEmojiEffect.isDone()) {
                        animatedEmojiEffect.removeView(this);
                        this.animations.remove(animatedEmojiEffect);
                    }
                }
            }
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            detach();
        }

        private void detach() {
            if (!this.animations.isEmpty()) {
                ArrayList arrayList = this.animations;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (obj instanceof ImageReceiver) {
                        ((ImageReceiver) obj).onDetachedFromWindow();
                    } else if (obj instanceof AnimatedEmojiEffect) {
                        ((AnimatedEmojiEffect) obj).removeView(this);
                    }
                }
            }
            this.animations.clear();
        }

        public void animateChange(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
            TLRPC.TL_availableReaction tL_availableReaction;
            AnimatedEmojiDrawable animatedEmojiDrawableMake;
            String strFindAnimatedEmojiEmoticon;
            if (visibleReaction == null) {
                detach();
                return;
            }
            TLRPC.Document document = null;
            TLRPC.TL_availableReaction tL_availableReaction2 = visibleReaction.emojicon != null ? MediaDataController.getInstance(UserConfig.selectedAccount).getReactionsMap().get(visibleReaction.emojicon) : null;
            if (tL_availableReaction2 == null) {
                TLRPC.Document documentFindDocument = AnimatedEmojiDrawable.findDocument(UserConfig.selectedAccount, visibleReaction.documentId);
                if (documentFindDocument != null && (strFindAnimatedEmojiEmoticon = MessageObject.findAnimatedEmojiEmoticon(documentFindDocument, null)) != null) {
                    tL_availableReaction2 = MediaDataController.getInstance(UserConfig.selectedAccount).getReactionsMap().get(strFindAnimatedEmojiEmoticon);
                }
                tL_availableReaction = tL_availableReaction2;
                document = documentFindDocument;
            } else {
                tL_availableReaction = tL_availableReaction2;
            }
            if (document == null && tL_availableReaction != null) {
                ImageReceiver imageReceiver = new ImageReceiver();
                imageReceiver.setParentView(this);
                int i = this.animationUniq;
                this.animationUniq = i + 1;
                imageReceiver.setUniqKeyPrefix(Integer.toString(i));
                imageReceiver.setImage(ImageLocation.getForDocument(tL_availableReaction.around_animation), this.effectsSize + "_" + this.effectsSize + "_nolimit", null, "tgs", tL_availableReaction, 1);
                imageReceiver.setAutoRepeat(0);
                imageReceiver.onAttachedToWindow();
                this.animations.add(imageReceiver);
                invalidate();
                return;
            }
            if (document == null) {
                animatedEmojiDrawableMake = AnimatedEmojiDrawable.make(2, UserConfig.selectedAccount, visibleReaction.documentId);
            } else {
                animatedEmojiDrawableMake = AnimatedEmojiDrawable.make(2, UserConfig.selectedAccount, document);
            }
            if (this.color != null) {
                animatedEmojiDrawableMake.setColorFilter(new PorterDuffColorFilter(this.color.intValue(), PorterDuff.Mode.MULTIPLY));
            }
            AnimatedEmojiEffect animatedEmojiEffectCreateFrom = AnimatedEmojiEffect.createFrom(animatedEmojiDrawableMake, false, !animatedEmojiDrawableMake.canOverrideColor());
            animatedEmojiEffectCreateFrom.setView(this);
            this.animations.add(animatedEmojiEffectCreateFrom);
            invalidate();
        }

        public void setColor(int i) {
            this.color = Integer.valueOf(i);
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.MULTIPLY);
            PorterDuffColorFilter porterDuffColorFilter2 = new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN);
            for (int i2 = 0; i2 < this.animations.size(); i2++) {
                Object obj = this.animations.get(i2);
                if (obj instanceof ImageReceiver) {
                    ((ImageReceiver) obj).setColorFilter(porterDuffColorFilter);
                } else if (obj instanceof AnimatedEmojiEffect) {
                    ((AnimatedEmojiEffect) obj).animatedEmojiDrawable.setColorFilter(porterDuffColorFilter2);
                }
            }
        }
    }

    public void animateStateChange(long j) {
        this.animatedStatus.animateChange(ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(Long.valueOf(j)));
        this.updateRightDrawable = true;
    }

    public void getEmojiStatusLocation(Rect rect) {
        if (this.nameTextView.getRightDrawable() == null) {
            rect.set(this.nameTextView.getWidth() - 1, (this.nameTextView.getHeight() / 2) - 1, this.nameTextView.getWidth() + 1, (this.nameTextView.getHeight() / 2) + 1);
            return;
        }
        rect.set(this.nameTextView.getRightDrawable().getBounds());
        rect.offset((int) this.nameTextView.getX(), (int) this.nameTextView.getY());
        this.animatedStatus.translate(rect.centerX(), rect.centerY());
    }

    private void switchTheme(Theme.ThemeInfo themeInfo, boolean z) {
        int[] iArr = new int[2];
        this.darkThemeView.getLocationInWindow(iArr);
        iArr[0] = iArr[0] + (this.darkThemeView.getMeasuredWidth() / 2);
        iArr[1] = iArr[1] + (this.darkThemeView.getMeasuredHeight() / 2);
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.needSetDayNightTheme, themeInfo, Boolean.FALSE, iArr, -1, Boolean.valueOf(z), this.darkThemeView);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.status.attach();
        updateColors();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        for (int i = 0; i < 16; i++) {
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.status.detach();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        for (int i = 0; i < 16; i++) {
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        }
        int i2 = this.lastAccount;
        if (i2 >= 0) {
            NotificationCenter.getInstance(i2).removeObserver(this, NotificationCenter.userEmojiStatusUpdated);
            NotificationCenter.getInstance(this.lastAccount).removeObserver(this, NotificationCenter.updateInterfaces);
            this.lastAccount = -1;
        }
        if (this.nameTextView.getRightDrawable() instanceof AnimatedEmojiDrawable.WrapSizeDrawable) {
            Drawable drawable = ((AnimatedEmojiDrawable.WrapSizeDrawable) this.nameTextView.getRightDrawable()).getDrawable();
            if (drawable instanceof AnimatedEmojiDrawable) {
                ((AnimatedEmojiDrawable) drawable).removeView(this.nameTextView);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(148.0f) + AndroidUtilities.statusBarHeight, TLObject.FLAG_30));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.drawPremium) {
            if (this.starParticlesDrawable == null) {
                StarParticlesView.Drawable drawable = new StarParticlesView.Drawable(15);
                this.starParticlesDrawable = drawable;
                drawable.init();
                StarParticlesView.Drawable drawable2 = this.starParticlesDrawable;
                drawable2.speedScale = 0.8f;
                drawable2.minLifeTime = 3000L;
            }
            this.starParticlesDrawable.rect.set(this.avatarImageView.getLeft(), this.avatarImageView.getTop(), this.avatarImageView.getRight(), this.avatarImageView.getBottom());
            this.starParticlesDrawable.rect.inset(-AndroidUtilities.m1146dp(20.0f), -AndroidUtilities.m1146dp(20.0f));
            this.starParticlesDrawable.resetPositions();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01a3  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onDraw(android.graphics.Canvas r13) throws java.lang.InterruptedException {
        /*
            Method dump skipped, instructions count: 551
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DrawerProfileCell.onDraw(android.graphics.Canvas):void");
    }

    public boolean isInAvatar(float f, float f2) {
        return f >= ((float) this.avatarImageView.getLeft()) && f <= ((float) this.avatarImageView.getRight()) && f2 >= ((float) this.avatarImageView.getTop()) && f2 <= ((float) this.avatarImageView.getBottom());
    }

    public boolean hasAvatar() {
        return this.avatarImageView.getImageReceiver().hasNotThumb();
    }

    public void setAccountsShown(boolean z, boolean z2) {
        if (this.accountsShown == z) {
            return;
        }
        this.accountsShown = z;
        setArrowState(z2);
    }

    public void setUser(TLRPC.User user, boolean z) {
        int i = UserConfig.selectedAccount;
        int i2 = this.lastAccount;
        if (i != i2) {
            if (i2 >= 0) {
                NotificationCenter.getInstance(i2).removeObserver(this, NotificationCenter.userEmojiStatusUpdated);
                NotificationCenter.getInstance(this.lastAccount).removeObserver(this, NotificationCenter.updateInterfaces);
            }
            this.lastAccount = i;
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.userEmojiStatusUpdated);
            this.lastAccount = i;
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.updateInterfaces);
        }
        this.lastUser = user;
        if (user == null) {
            return;
        }
        this.accountsShown = z;
        setArrowState(false);
        CharSequence userName = UserObject.getUserName(user);
        try {
            userName = Emoji.replaceEmoji(userName, this.nameTextView.getPaint().getFontMetricsInt(), false);
        } catch (Exception unused) {
        }
        this.drawPremium = false;
        this.nameTextView.setText(userName);
        this.statusGiftId = null;
        Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(user);
        BadgeDTO badge = BadgesController.INSTANCE.getBadge(user);
        if (emojiStatusDocumentId != null) {
            boolean z2 = user.emoji_status instanceof TLRPC.TL_emojiStatusCollectible;
            this.animatedStatus.animate().alpha(1.0f).setDuration(200L).start();
            this.nameTextView.setDrawablePadding(AndroidUtilities.m1146dp(4.0f));
            this.status.set(emojiStatusDocumentId.longValue(), true);
            if (z2) {
                this.statusGiftId = Long.valueOf(((TLRPC.TL_emojiStatusCollectible) user.emoji_status).collectible_id);
            }
            this.status.setParticles(z2, true);
        } else if (badge != null) {
            this.animatedStatus.animate().alpha(1.0f).setDuration(200L).start();
            this.nameTextView.setDrawablePadding(AndroidUtilities.m1146dp(2.0f));
            this.status.set(badge.getDocumentId(), true);
            this.status.setParticles(false, true);
        } else if (user.premium || AyuConfig.localPremium) {
            this.animatedStatus.animate().alpha(1.0f).setDuration(200L).start();
            this.nameTextView.setDrawablePadding(AndroidUtilities.m1146dp(4.0f));
            if (this.premiumStar == null) {
                this.premiumStar = getResources().getDrawable(C2369R.drawable.msg_premium_liststar).mutate();
            }
            this.premiumStar.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_menuPhoneCats), PorterDuff.Mode.MULTIPLY));
            this.status.set(this.premiumStar, true);
            this.status.setParticles(false, true);
        } else {
            this.animatedStatus.animateChange(null);
            this.animatedStatus.animate().alpha(0.0f).setDuration(200L).start();
            this.status.set((Drawable) null, true);
            this.status.setParticles(false, true);
        }
        this.animatedStatus.setColor(Theme.getColor(Theme.isCurrentThemeDark() ? Theme.key_chats_verifiedBackground : Theme.key_chats_menuPhoneCats));
        this.status.setColor(Integer.valueOf(Theme.getColor(Theme.isCurrentThemeDark() ? Theme.key_chats_verifiedBackground : Theme.key_chats_menuPhoneCats)));
        if (!ExteraConfig.hidePhoneNumber) {
            this.phoneTextView.setText(PhoneFormat.getInstance().format("+" + user.phone));
        } else if (!TextUtils.isEmpty(UserObject.getPublicUsername(user))) {
            this.phoneTextView.setText("@" + UserObject.getPublicUsername(user));
        } else {
            this.phoneTextView.setText(LocaleController.getString("MobileHidden", C2369R.string.MobileHidden));
        }
        AvatarDrawable avatarDrawable = new AvatarDrawable(user);
        avatarDrawable.setColor(Theme.getColor(Theme.key_avatar_backgroundInProfileBlue));
        this.avatarImageView.setForUserOrChat(user, avatarDrawable);
        applyBackground(true);
        this.updateRightDrawable = true;
    }

    public Integer applyBackground(boolean z) {
        Integer num = (Integer) getTag();
        int i = Theme.key_chats_menuTopBackground;
        if (!Theme.hasThemeKey(i) || Theme.getColor(i) == 0) {
            i = Theme.key_chats_menuTopBackgroundCats;
        }
        if (z || num == null || i != num.intValue()) {
            setBackgroundColor(Theme.getColor(i));
            setTag(Integer.valueOf(i));
        }
        return Integer.valueOf(i);
    }

    public void updateColors() {
        SnowflakesEffect snowflakesEffect = this.snowflakesEffect;
        if (snowflakesEffect != null) {
            snowflakesEffect.updateColors();
        }
        AnimatedStatusView animatedStatusView = this.animatedStatus;
        if (animatedStatusView != null) {
            animatedStatusView.setColor(Theme.getColor(Theme.isCurrentThemeDark() ? Theme.key_chats_verifiedBackground : Theme.key_chats_menuPhoneCats));
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.status;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.setColor(Integer.valueOf(Theme.getColor(Theme.isCurrentThemeDark() ? Theme.key_chats_verifiedBackground : Theme.key_chats_menuPhoneCats)));
        }
    }

    private void setArrowState(boolean z) {
        float f = this.accountsShown ? 180.0f : 0.0f;
        if (z) {
            this.arrowView.animate().rotation(f).setDuration(220L).setInterpolator(CubicBezierInterpolator.EASE_OUT).start();
        } else {
            this.arrowView.animate().cancel();
            this.arrowView.setRotation(f);
        }
        this.arrowView.setContentDescription(LocaleController.getString(this.accountsShown ? C2369R.string.AccDescrHideAccounts : C2369R.string.AccDescrShowAccounts));
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.emojiLoaded) {
            this.nameTextView.invalidate();
            return;
        }
        if (i == NotificationCenter.userEmojiStatusUpdated) {
            setUser((TLRPC.User) objArr[0], this.accountsShown);
            return;
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            setUser(UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser(), this.accountsShown);
            return;
        }
        if (i == NotificationCenter.updateInterfaces) {
            int iIntValue = ((Integer) objArr[0]).intValue();
            if ((MessagesController.UPDATE_MASK_NAME & iIntValue) == 0 && (MessagesController.UPDATE_MASK_AVATAR & iIntValue) == 0 && (MessagesController.UPDATE_MASK_STATUS & iIntValue) == 0 && (MessagesController.UPDATE_MASK_PHONE & iIntValue) == 0 && (iIntValue & MessagesController.UPDATE_MASK_EMOJI_STATUS) == 0) {
                return;
            }
            setUser(UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser(), this.accountsShown);
        }
    }

    public AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable getEmojiStatusDrawable() {
        return this.status;
    }

    public Long getEmojiStatusGiftId() {
        return this.statusGiftId;
    }

    public View getEmojiStatusDrawableParent() {
        return this.nameTextView;
    }

    public void updateSunDrawable(boolean z) {
        RLottieDrawable rLottieDrawable = sunDrawable;
        if (rLottieDrawable != null) {
            if (z) {
                rLottieDrawable.setCustomEndFrame(36);
            } else {
                rLottieDrawable.setCustomEndFrame(0);
            }
        }
        RLottieImageView rLottieImageView = this.darkThemeView;
        if (rLottieImageView != null) {
            rLottieImageView.playAnimation();
        }
    }
}
