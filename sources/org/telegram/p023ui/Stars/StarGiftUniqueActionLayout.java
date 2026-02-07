package org.telegram.p023ui.Stars;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MessageObject;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.ChatActionCell;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.ButtonBounce;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Gifts.GiftSheet;
import org.telegram.p023ui.Stars.StarsReactionsSheet;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stars;

/* loaded from: classes3.dex */
public class StarGiftUniqueActionLayout {
    TLRPC.TL_messageActionStarGiftUnique action;
    private boolean attached;
    private TL_stars.starGiftAttributeBackdrop backdrop;
    private final ButtonBounce bounce;
    private final ButtonBounce buttonBounce;
    private float buttonHeight;
    private Text buttonText;
    private float buttonY;
    private final int currentAccount;
    MessageObject currentMessageObject;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emoji;
    private RadialGradient gradient;
    private int gradientRadius;
    int height;
    public final ImageReceiver imageReceiver;
    private TL_stars.starGiftAttributeModel model;
    private float nameWidth;
    private TL_stars.starGiftAttributePattern pattern;
    public boolean repost;
    private final Theme.ResourcesProvider resourcesProvider;
    private final GiftSheet.RibbonDrawable ribbon;
    private Text subtitle;
    private float subtitleY;
    private Text title;
    private float titleY;
    private float valueWidth;
    private final ChatActionCell view;
    int width;
    private final Paint backgroundPaint = new Paint(1);
    private final Matrix matrix = new Matrix();
    private final RectF backgroundRect = new RectF();
    private final Path backgroundPath = new Path();
    private final ArrayList table = new ArrayList();
    private final RectF buttonRect = new RectF();
    private final Path buttonPath = new Path();
    private final Paint buttonBackgroundPaint = new Paint();
    private final StarsReactionsSheet.Particles buttonParticles = new StarsReactionsSheet.Particles(1, 25);

    /* loaded from: classes6.dex */
    private static final class Row {
        public final Text name;
        public final Text value;

        /* renamed from: y */
        public final float f2056y;

        public Row(float f, CharSequence charSequence, CharSequence charSequence2) {
            this.name = new Text(charSequence, 12.0f);
            this.value = new Text(charSequence2, 12.0f, AndroidUtilities.bold());
            this.f2056y = f + (getHeight() / 2.0f);
        }

        public float getHeight() {
            return Math.max(this.name.getHeight(), this.value.getHeight());
        }
    }

    public StarGiftUniqueActionLayout(int i, ChatActionCell chatActionCell, Theme.ResourcesProvider resourcesProvider) {
        this.currentAccount = i;
        this.view = chatActionCell;
        this.resourcesProvider = resourcesProvider;
        this.ribbon = new GiftSheet.RibbonDrawable(chatActionCell, 1.0f);
        this.buttonBounce = new ButtonBounce(chatActionCell);
        this.bounce = new ButtonBounce(chatActionCell);
        this.imageReceiver = new ImageReceiver(chatActionCell);
        this.emoji = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(chatActionCell, AndroidUtilities.m1146dp(28.0f));
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void set(org.telegram.messenger.MessageObject r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 948
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Stars.StarGiftUniqueActionLayout.set(org.telegram.messenger.MessageObject, boolean):void");
    }

    public boolean has() {
        return this.action != null;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void attach() {
        this.attached = true;
        if (this.action != null) {
            this.imageReceiver.onAttachedToWindow();
            this.emoji.attach();
        }
    }

    public void detach() {
        this.attached = false;
        this.imageReceiver.onDetachedFromWindow();
        this.emoji.detach();
    }

    public void draw(Canvas canvas) {
        float width = getWidth() / 2.0f;
        this.backgroundRect.set(0.0f, 0.0f, getWidth(), getHeight());
        int iWidth = ((int) (this.backgroundRect.width() + this.backgroundRect.height())) / 2;
        if (this.backdrop != null && (this.gradient == null || this.gradientRadius != iWidth)) {
            this.gradientRadius = iWidth;
            float f = iWidth;
            TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop = this.backdrop;
            this.gradient = new RadialGradient(0.0f, 0.0f, f, new int[]{stargiftattributebackdrop.center_color | (-16777216), stargiftattributebackdrop.edge_color | (-16777216)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP);
        }
        if (this.gradient != null) {
            this.matrix.reset();
            this.matrix.postTranslate(width, width);
            this.gradient.setLocalMatrix(this.matrix);
            this.backgroundPaint.setShader(this.gradient);
        }
        this.backgroundPath.rewind();
        this.backgroundPath.addRoundRect(this.backgroundRect, AndroidUtilities.m1146dp(14.0f), AndroidUtilities.m1146dp(14.0f), Path.Direction.CW);
        canvas.save();
        float scale = this.bounce.getScale(0.0125f);
        canvas.scale(scale, scale, this.backgroundRect.centerX(), this.backgroundRect.centerY());
        canvas.save();
        canvas.clipPath(this.backgroundPath);
        canvas.drawPaint(this.backgroundPaint);
        canvas.save();
        canvas.translate(width, AndroidUtilities.m1146dp(65.0f));
        TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop2 = this.backdrop;
        if (stargiftattributebackdrop2 != null) {
            this.emoji.setColor(Integer.valueOf(stargiftattributebackdrop2.pattern_color | (-16777216)));
        }
        StarGiftPatterns.drawPattern(canvas, 1, this.emoji, this.backgroundRect.width(), this.backgroundRect.height(), 1.0f, 1.1f);
        canvas.restore();
        this.imageReceiver.setImageCoords(width - (AndroidUtilities.m1146dp(110.0f) / 2.0f), AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(110.0f), AndroidUtilities.m1146dp(110.0f));
        this.imageReceiver.draw(canvas);
        int iMultAlpha = Theme.multAlpha(-1, 0.6f);
        TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop3 = this.backdrop;
        if (stargiftattributebackdrop3 != null) {
            iMultAlpha = stargiftattributebackdrop3.text_color | (-16777216);
        }
        int i = iMultAlpha;
        this.title.ellipsize(getWidth() - AndroidUtilities.m1146dp(12.0f));
        Text text = this.title;
        text.draw(canvas, width - (text.getCurrentWidth() / 2.0f), this.titleY, -1, 1.0f);
        this.subtitle.ellipsize(getWidth() - AndroidUtilities.m1146dp(12.0f));
        Text text2 = this.subtitle;
        int i2 = i;
        text2.draw(canvas, width - (text2.getCurrentWidth() / 2.0f), this.subtitleY, i2, 1.0f);
        float fM1146dp = this.nameWidth + AndroidUtilities.m1146dp(9.0f) + this.valueWidth;
        ArrayList arrayList = this.table;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            int i4 = i3 + 1;
            Row row = (Row) arrayList.get(i3);
            Text text3 = row.name;
            float f2 = width - (fM1146dp / 2.0f);
            text3.draw(canvas, (f2 + this.nameWidth) - text3.getCurrentWidth(), row.f2056y, i2, 1.0f);
            row.value.draw(canvas, f2 + this.nameWidth + AndroidUtilities.m1146dp(9.0f), row.f2056y, -1, 1.0f);
            i3 = i4;
            i2 = i2;
        }
        int i5 = i2;
        if (!this.repost) {
            this.buttonRect.set(width - ((this.buttonText.getCurrentWidth() + AndroidUtilities.m1146dp(30.0f)) / 2.0f), this.buttonY, width + ((this.buttonText.getCurrentWidth() + AndroidUtilities.m1146dp(30.0f)) / 2.0f), this.buttonY + this.buttonHeight);
            this.buttonPath.rewind();
            Path path = this.buttonPath;
            RectF rectF = this.buttonRect;
            float f3 = this.buttonHeight;
            path.addRoundRect(rectF, f3 / 2.0f, f3 / 2.0f, Path.Direction.CW);
            this.buttonBackgroundPaint.setColor(Theme.multAlpha(-16777216, 0.13f));
            float scale2 = this.buttonBounce.getScale(0.075f);
            canvas.scale(scale2, scale2, this.buttonRect.centerX(), this.buttonRect.centerY());
            canvas.drawPath(this.buttonPath, this.buttonBackgroundPaint);
            canvas.restore();
            this.ribbon.setBounds(((int) this.backgroundRect.right) - AndroidUtilities.m1146dp(46.67f), ((int) this.backgroundRect.top) - AndroidUtilities.m1146dp(1.33f), ((int) this.backgroundRect.right) + AndroidUtilities.m1146dp(1.33f), ((int) this.backgroundRect.top) + AndroidUtilities.m1146dp(46.67f));
            this.ribbon.setTextColor(i5);
            this.ribbon.draw(canvas);
        }
        canvas.restore();
    }

    public void drawOutbounds(Canvas canvas) {
        if (this.repost) {
            return;
        }
        canvas.save();
        float scale = this.bounce.getScale(0.0125f);
        canvas.scale(scale, scale, this.backgroundRect.centerX(), this.backgroundRect.centerY());
        float scale2 = this.buttonBounce.getScale(0.075f);
        canvas.scale(scale2, scale2, this.buttonRect.centerX(), this.buttonRect.centerY());
        canvas.clipPath(this.buttonPath);
        this.buttonParticles.setBounds(this.buttonRect);
        this.buttonParticles.process();
        this.buttonParticles.draw(canvas, Theme.multAlpha(-1, 0.7f));
        this.buttonText.draw(canvas, this.buttonRect.left + AndroidUtilities.m1146dp(15.0f), this.buttonRect.centerY(), -1, 1.0f);
        canvas.restore();
        this.view.invalidateOutbounds();
    }

    public boolean onTouchEvent(float f, float f2, MotionEvent motionEvent) {
        boolean zContains = this.buttonRect.contains(motionEvent.getX() - f, motionEvent.getY() - f2);
        boolean zContains2 = this.backgroundRect.contains(motionEvent.getX() - f, motionEvent.getY() - f2);
        if (motionEvent.getAction() == 0) {
            this.bounce.setPressed(zContains2 && !zContains);
            this.buttonBounce.setPressed(zContains);
        } else if (motionEvent.getAction() == 2) {
            if (this.buttonBounce.isPressed() && !zContains) {
                this.buttonBounce.setPressed(false);
            } else if (this.bounce.isPressed() && !zContains2) {
                this.bounce.setPressed(false);
            }
        } else {
            if (motionEvent.getAction() == 1 && (this.buttonBounce.isPressed() || this.bounce.isPressed())) {
                new StarGiftSheet(this.view.getContext(), this.currentAccount, this.currentMessageObject.getDialogId(), this.resourcesProvider).set(this.currentMessageObject).show();
                this.buttonBounce.setPressed(false);
                this.bounce.setPressed(false);
                return true;
            }
            if (motionEvent.getAction() == 3 && (this.buttonBounce.isPressed() || this.bounce.isPressed())) {
                this.buttonBounce.setPressed(false);
                this.bounce.setPressed(false);
                return true;
            }
        }
        return this.buttonBounce.isPressed() || this.bounce.isPressed();
    }
}
