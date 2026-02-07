package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class TopicSeparator {
    private final Paint arrowPaint;
    private final Path arrowPath;
    private final ButtonBounce bounce;
    private final View cell;
    private final RectF clickBounds;
    private final int currentAccount;
    public AnimatedEmojiDrawable emojiImage;
    public final ImageReceiver image;
    private Runnable onClickListener;
    private int pathParentWidth;
    private int pathWidth;
    private boolean pathWithCenter;
    private boolean pathWithDots;
    private final Theme.ResourcesProvider resourcesProvider;
    public Text text;
    public long topicId;
    private final boolean withDots;
    public final AvatarDrawable avatarDrawable = new AvatarDrawable();
    private final Path path = new Path();

    public TopicSeparator(int i, View view, Theme.ResourcesProvider resourcesProvider, boolean z) {
        Paint paint = new Paint(1);
        this.arrowPaint = paint;
        Path path = new Path();
        this.arrowPath = path;
        this.clickBounds = new RectF();
        this.currentAccount = i;
        this.cell = view;
        this.resourcesProvider = resourcesProvider;
        this.withDots = z;
        this.bounce = new ButtonBounce(view);
        this.image = new ImageReceiver(view);
        path.rewind();
        path.moveTo(-AndroidUtilities.m1146dp(1.75f), -AndroidUtilities.m1146dp(4.0f));
        path.lineTo(AndroidUtilities.m1146dp(1.75f), 0.0f);
        path.lineTo(-AndroidUtilities.m1146dp(1.75f), AndroidUtilities.m1146dp(4.0f));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setOnClickListener(Runnable runnable) {
        this.onClickListener = runnable;
    }

    public boolean update(MessageObject messageObject) {
        AnimatedEmojiDrawable animatedEmojiDrawable = this.emojiImage;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.removeView(this.cell);
            this.emojiImage = null;
        }
        this.pathWidth = 0;
        this.topicId = 0L;
        if (messageObject == null) {
            this.text = null;
            this.topicId = 0L;
        } else if (ChatObject.isMonoForum(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-messageObject.getDialogId())))) {
            this.image.setRoundRadius(AndroidUtilities.m1146dp(10.0f));
            long monoForumTopicId = messageObject.getMonoForumTopicId();
            TLObject userOrChat = MessagesController.getInstance(this.currentAccount).getUserOrChat(monoForumTopicId);
            this.topicId = monoForumTopicId;
            if (userOrChat == null) {
                this.text = null;
                return false;
            }
            this.avatarDrawable.setInfo(userOrChat);
            this.image.setForUserOrChat(userOrChat, this.avatarDrawable);
            this.text = new Text(DialogObject.getName(userOrChat), 14.0f, AndroidUtilities.bold());
        } else {
            this.image.setRoundRadius(0);
            long topicId = messageObject.getTopicId();
            this.topicId = topicId;
            TLRPC.TL_forumTopic tL_forumTopicFindTopic = MessagesController.getInstance(this.currentAccount).getTopicsController().findTopic(-messageObject.getDialogId(), topicId);
            if (tL_forumTopicFindTopic == null) {
                this.text = null;
                return false;
            }
            if (topicId == 1) {
                this.image.setImageBitmap(ForumUtilities.createGeneralTopicDrawable(this.cell.getContext(), 0.75f, Theme.getColor(Theme.key_actionBarDefaultIcon, this.resourcesProvider), false, false));
            } else if (tL_forumTopicFindTopic.icon_emoji_id != 0) {
                this.emojiImage = new AnimatedEmojiDrawable(0, this.currentAccount, tL_forumTopicFindTopic.icon_emoji_id);
                this.image.onDetachedFromWindow();
                this.emojiImage.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
            } else {
                this.image.setImageBitmap(ForumUtilities.createTopicDrawable(tL_forumTopicFindTopic, false));
            }
            this.text = new Text(tL_forumTopicFindTopic.title, 14.0f, AndroidUtilities.bold());
        }
        return this.text != null;
    }

    public void setText(String str) {
        this.text = new Text(str, 14.0f, AndroidUtilities.bold());
    }

    public void attach() {
        this.image.onAttachedToWindow();
        AnimatedEmojiDrawable animatedEmojiDrawable = this.emojiImage;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.addView(this.cell);
        }
    }

    public void detach() {
        this.image.onDetachedFromWindow();
        AnimatedEmojiDrawable animatedEmojiDrawable = this.emojiImage;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.removeView(this.cell);
        }
    }

    public void draw(Canvas canvas, int i, float f, float f2, float f3, float f4, boolean z) {
        float f5;
        float f6;
        Text text = this.text;
        if (text == null) {
            return;
        }
        text.ellipsize(i - AndroidUtilities.m1146dp(144.66f));
        float fM1146dp = AndroidUtilities.m1146dp(48.66f) + this.text.getWidth();
        float f7 = i;
        float f8 = (f7 - fM1146dp) / 2.0f;
        int i2 = (int) fM1146dp;
        if (this.pathWidth == i2 && this.pathParentWidth == i && this.pathWithCenter == z && this.pathWithDots == this.withDots) {
            f5 = fM1146dp;
            f6 = 2.0f;
        } else {
            this.path.rewind();
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(f8, AndroidUtilities.m1146dp(4.5f), f8 + fM1146dp, AndroidUtilities.m1146dp(28.5f));
            if (z) {
                this.path.addRoundRect(rectF, AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(12.0f), Path.Direction.CW);
            }
            if (this.withDots) {
                float f9 = f7 / 2.0f;
                float fM1146dp2 = f9 - AndroidUtilities.m1146dp(1.833f);
                while (fM1146dp2 > 0.0f) {
                    RectF rectF2 = AndroidUtilities.rectTmp;
                    rectF2.set(fM1146dp2 - AndroidUtilities.m1146dp(3.66f), AndroidUtilities.m1146dp(15.5f), fM1146dp2, AndroidUtilities.m1146dp(17.5f));
                    this.path.addRoundRect(rectF2, AndroidUtilities.m1146dp(1.0f), AndroidUtilities.m1146dp(1.0f), Path.Direction.CW);
                    fM1146dp2 -= AndroidUtilities.m1146dp(8.33f);
                    fM1146dp = fM1146dp;
                }
                f5 = fM1146dp;
                f6 = 2.0f;
                int iM1146dp = AndroidUtilities.m1146dp(1.833f);
                while (true) {
                    f9 += iM1146dp;
                    if (f9 >= f7) {
                        break;
                    }
                    RectF rectF3 = AndroidUtilities.rectTmp;
                    rectF3.set(f9, AndroidUtilities.m1146dp(15.5f), AndroidUtilities.m1146dp(3.66f) + f9, AndroidUtilities.m1146dp(17.5f));
                    this.path.addRoundRect(rectF3, AndroidUtilities.m1146dp(1.0f), AndroidUtilities.m1146dp(1.0f), Path.Direction.CW);
                    iM1146dp = AndroidUtilities.m1146dp(8.33f);
                }
            } else {
                f5 = fM1146dp;
                f6 = 2.0f;
            }
            this.pathWidth = i2;
            this.pathParentWidth = i;
            this.pathWithDots = this.withDots;
            this.pathWithCenter = z;
        }
        canvas.save();
        float f10 = f / f6;
        canvas.translate(f10, f2);
        Paint themePaint = Theme.getThemePaint("paintChatActionBackground", this.resourcesProvider);
        int alpha = themePaint.getAlpha();
        themePaint.setAlpha((int) (alpha * f4 * f3));
        canvas.drawPath(this.path, themePaint);
        themePaint.setAlpha(alpha);
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null ? resourcesProvider.hasGradientService() : Theme.hasGradientService()) {
            Paint themePaint2 = Theme.getThemePaint("paintChatActionBackgroundDarken", this.resourcesProvider);
            int alpha2 = themePaint2.getAlpha();
            themePaint2.setAlpha((int) (alpha2 * f4 * f3));
            canvas.drawPath(this.path, themePaint2);
            themePaint2.setAlpha(alpha2);
        }
        canvas.restore();
        float f11 = f10 + f8;
        float f12 = f11 + f5;
        this.clickBounds.set(f11 - AndroidUtilities.m1146dp(4.0f), f2 - AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f) + f12, AndroidUtilities.m1146dp(32.0f) + f2);
        if (z) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.emojiImage;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.setBounds((int) (AndroidUtilities.m1146dp(2.66f) + f11), (int) (AndroidUtilities.m1146dp(6.5f) + f2), (int) (AndroidUtilities.m1146dp(22.66f) + f11), (int) (AndroidUtilities.m1146dp(26.5f) + f2));
                this.emojiImage.setAlpha((int) (255.0f * f4));
                this.emojiImage.draw(canvas);
            } else {
                this.image.setImageCoords(AndroidUtilities.m1146dp(2.66f) + f11, AndroidUtilities.m1146dp(6.5f) + f2, AndroidUtilities.m1146dp(20.0f), AndroidUtilities.m1146dp(20.0f));
                this.image.setAlpha(f4);
                this.image.draw(canvas);
            }
            int color = Theme.getColor(Theme.key_chat_serviceText, this.resourcesProvider);
            this.text.draw(canvas, f11 + AndroidUtilities.m1146dp(27.66f), AndroidUtilities.m1146dp(16.5f) + f2, color, f4);
            canvas.save();
            canvas.translate(f12 - AndroidUtilities.m1146dp(11.25f), AndroidUtilities.m1146dp(16.5f) + f2);
            this.arrowPaint.setColor(Theme.multAlpha(color, 0.75f * f4));
            this.arrowPaint.setStrokeWidth(AndroidUtilities.m1146dp(1.66f));
            canvas.drawPath(this.arrowPath, this.arrowPaint);
            canvas.restore();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r6, boolean r7) {
        /*
            r5 = this;
            org.telegram.ui.Components.Text r0 = r5.text
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L24
            android.graphics.RectF r0 = r5.clickBounds
            float r3 = r6.getX()
            float r4 = r6.getY()
            if (r7 == 0) goto L19
            android.view.View r7 = r5.cell
            int r7 = r7.getPaddingTop()
            goto L1a
        L19:
            r7 = 0
        L1a:
            float r7 = (float) r7
            float r4 = r4 - r7
            boolean r7 = r0.contains(r3, r4)
            if (r7 == 0) goto L24
            r7 = 1
            goto L25
        L24:
            r7 = 0
        L25:
            int r0 = r6.getAction()
            if (r0 != 0) goto L31
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            r6.setPressed(r7)
            goto L6f
        L31:
            int r0 = r6.getAction()
            r3 = 2
            if (r0 != r3) goto L48
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            boolean r6 = r6.isPressed()
            if (r6 == 0) goto L6f
            if (r7 != 0) goto L6f
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            r6.setPressed(r2)
            goto L6f
        L48:
            int r7 = r6.getAction()
            if (r7 != r1) goto L63
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            boolean r6 = r6.isPressed()
            if (r6 == 0) goto L5d
            java.lang.Runnable r6 = r5.onClickListener
            if (r6 == 0) goto L5d
            r6.run()
        L5d:
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            r6.setPressed(r2)
            goto L6f
        L63:
            int r6 = r6.getAction()
            r7 = 3
            if (r6 != r7) goto L6f
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            r6.setPressed(r2)
        L6f:
            org.telegram.ui.Components.ButtonBounce r6 = r5.bounce
            boolean r6 = r6.isPressed()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.TopicSeparator.onTouchEvent(android.view.MotionEvent, boolean):boolean");
    }

    public static class Cell extends View {
        private Utilities.Callback onClickListener;
        public final TopicSeparator separator;

        public Cell(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            TopicSeparator topicSeparator = new TopicSeparator(i, this, resourcesProvider, false);
            this.separator = topicSeparator;
            topicSeparator.setOnClickListener(new Runnable() { // from class: org.telegram.ui.Components.TopicSeparator$Cell$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            Utilities.Callback callback = this.onClickListener;
            if (callback != null) {
                callback.run(Long.valueOf(this.separator.topicId));
            }
        }

        public void setOnTopicClickListener(Utilities.Callback<Long> callback) {
            this.onClickListener = callback;
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.separator.onTouchEvent(motionEvent, false) || super.onTouchEvent(motionEvent);
        }

        public void set(MessageObject messageObject) {
            this.separator.update(messageObject);
            if (isAttachedToWindow()) {
                this.separator.attach();
            }
        }

        @Override // android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.separator.attach();
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.separator.detach();
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(33.0f), TLObject.FLAG_30));
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            this.separator.draw(canvas, getWidth(), 0.0f, 0.0f, 0.75f, 1.0f, true);
        }
    }
}
