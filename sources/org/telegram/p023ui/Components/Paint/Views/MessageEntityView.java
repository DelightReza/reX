package org.telegram.p023ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManagerFixed;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.ChatActionCell;
import org.telegram.p023ui.Cells.ChatMessageCell;
import org.telegram.p023ui.Components.BlurringShader;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Paint.Views.EntityView;
import org.telegram.p023ui.Components.Point;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Stories.recorder.PreviewView;
import org.telegram.p023ui.Stories.recorder.StoryEntry;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public abstract class MessageEntityView extends EntityView {
    private final BlurringShader.BlurManager blurManager;
    private boolean clipVideoMessageForBitmap;
    public final FrameLayout container;
    private final SparseIntArray currentColors;
    public boolean firstMeasure;
    private MessageObject.GroupedMessages groupedMessages;
    private boolean isDark;
    public final RecyclerListView listView;
    public final ArrayList messageObjects;
    private Theme.MessageDrawable msgInDrawable;
    private Theme.MessageDrawable msgInDrawableSelected;
    private Theme.MessageDrawable msgMediaInDrawable;
    private Theme.MessageDrawable msgMediaInDrawableSelected;
    private Theme.MessageDrawable msgMediaOutDrawable;
    private Theme.MessageDrawable msgMediaOutDrawableSelected;
    private Theme.MessageDrawable msgOutDrawable;
    private Theme.MessageDrawable msgOutDrawableSelected;
    public final Theme.ResourcesProvider resourcesProvider;
    private TextureView textureView;
    private boolean textureViewActive;
    private boolean usesBackgroundPaint;
    private int videoHeight;
    private int videoWidth;

    public abstract boolean drawForBitmap();

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getBounceScale() {
        return 0.02f;
    }

    public MessageEntityView(final Context context, Point point, float f, float f2, ArrayList arrayList, final BlurringShader.BlurManager blurManager, final boolean z, final PreviewView.TextureViewHolder textureViewHolder) {
        TLRPC.MessageFwdHeader messageFwdHeader;
        TLRPC.Peer peer;
        super(context, point);
        this.messageObjects = new ArrayList();
        this.videoWidth = 1;
        this.videoHeight = 1;
        this.firstMeasure = true;
        this.isDark = Theme.isCurrentThemeDark();
        this.currentColors = new SparseIntArray();
        this.resourcesProvider = new Theme.ResourcesProvider() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.7
            public final Paint chat_actionBackgroundGradientDarkenPaint;
            public final Paint chat_actionBackgroundPaint;
            public final Paint chat_actionBackgroundSelectedPaint;
            public final TextPaint chat_actionTextPaint;
            public final TextPaint chat_actionTextPaint2;
            public final TextPaint chat_botButtonPaint;

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public /* synthetic */ void applyServiceShaderMatrix(int i, int i2, float f3, float f4) {
                Theme.applyServiceShaderMatrix(i, i2, f3, f4);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public /* synthetic */ ColorFilter getAnimatedEmojiColorFilter() {
                return Theme.chat_animatedEmojiTextColorFilter;
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public /* synthetic */ int getColorOrDefault(int i) {
                return getColor(i);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public /* synthetic */ int getCurrentColor(int i) {
                return getColor(i);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public /* synthetic */ boolean hasGradientService() {
                return Theme.ResourcesProvider.CC.$default$hasGradientService(this);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public /* synthetic */ void setAnimatedColor(int i, int i2) {
                Theme.ResourcesProvider.CC.$default$setAnimatedColor(this, i, i2);
            }

            {
                TextPaint textPaint = new TextPaint();
                this.chat_actionTextPaint = textPaint;
                TextPaint textPaint2 = new TextPaint();
                this.chat_actionTextPaint2 = textPaint2;
                TextPaint textPaint3 = new TextPaint();
                this.chat_botButtonPaint = textPaint3;
                this.chat_actionBackgroundPaint = new Paint(3);
                this.chat_actionBackgroundSelectedPaint = new Paint(3);
                Paint paint = new Paint(3);
                this.chat_actionBackgroundGradientDarkenPaint = paint;
                textPaint.setTextSize(AndroidUtilities.m1146dp(Math.max(16, SharedConfig.fontSize) - 2));
                textPaint2.setTextSize(AndroidUtilities.m1146dp(Math.max(16, SharedConfig.fontSize) - 2));
                textPaint3.setTextSize(AndroidUtilities.m1146dp(15.0f));
                textPaint3.setTypeface(AndroidUtilities.bold());
                paint.setColor(352321536);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public int getColor(int i) {
                return MessageEntityView.this.currentColors.get(i, Theme.getColor(i));
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public Paint getPaint(String str) {
                str.getClass();
                switch (str) {
                    case "paintChatActionText2":
                        return this.chat_actionTextPaint2;
                    case "paintChatBotButton":
                        return this.chat_botButtonPaint;
                    case "paintChatActionBackgroundDarken":
                        return this.chat_actionBackgroundGradientDarkenPaint;
                    case "paintChatActionBackgroundSelected":
                        return this.chat_actionBackgroundSelectedPaint;
                    case "paintChatActionText":
                        return this.chat_actionTextPaint;
                    default:
                        return Theme.getThemePaint(str);
                }
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public Drawable getDrawable(String str) {
                if (str.equals("drawableMsgIn")) {
                    if (MessageEntityView.this.msgInDrawable == null) {
                        MessageEntityView.this.msgInDrawable = new Theme.MessageDrawable(0, false, false, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgInDrawable;
                }
                if (str.equals("drawableMsgInSelected")) {
                    if (MessageEntityView.this.msgInDrawableSelected == null) {
                        MessageEntityView.this.msgInDrawableSelected = new Theme.MessageDrawable(0, false, true, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgInDrawableSelected;
                }
                if (str.equals("drawableMsgOut")) {
                    if (MessageEntityView.this.msgOutDrawable == null) {
                        MessageEntityView.this.msgOutDrawable = new Theme.MessageDrawable(0, true, false, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgOutDrawable;
                }
                if (str.equals("drawableMsgOutSelected")) {
                    if (MessageEntityView.this.msgOutDrawableSelected == null) {
                        MessageEntityView.this.msgOutDrawableSelected = new Theme.MessageDrawable(0, true, true, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgOutDrawableSelected;
                }
                if (str.equals("drawableMsgInMedia")) {
                    if (MessageEntityView.this.msgMediaInDrawable == null) {
                        MessageEntityView.this.msgMediaInDrawable = new Theme.MessageDrawable(1, false, false, MessageEntityView.this.resourcesProvider);
                    }
                    MessageEntityView.this.msgMediaInDrawable.invalidateSelf();
                    return MessageEntityView.this.msgMediaInDrawable;
                }
                if (str.equals("drawableMsgInMediaSelected")) {
                    if (MessageEntityView.this.msgMediaInDrawableSelected == null) {
                        MessageEntityView.this.msgMediaInDrawableSelected = new Theme.MessageDrawable(1, false, true, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgMediaInDrawableSelected;
                }
                if (str.equals("drawableMsgOutMedia")) {
                    if (MessageEntityView.this.msgMediaOutDrawable == null) {
                        MessageEntityView.this.msgMediaOutDrawable = new Theme.MessageDrawable(1, true, false, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgMediaOutDrawable;
                }
                if (str.equals("drawableMsgOutMediaSelected")) {
                    if (MessageEntityView.this.msgMediaOutDrawableSelected == null) {
                        MessageEntityView.this.msgMediaOutDrawableSelected = new Theme.MessageDrawable(1, true, true, MessageEntityView.this.resourcesProvider);
                    }
                    return MessageEntityView.this.msgMediaOutDrawableSelected;
                }
                return Theme.getThemeDrawable(str);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public boolean isDark() {
                return MessageEntityView.this.isDark;
            }
        };
        this.blurManager = blurManager;
        setRotation(f);
        setScale(f2);
        for (int i = 0; i < arrayList.size(); i++) {
            MessageObject messageObject = (MessageObject) arrayList.get(i);
            TLRPC.Message message = messageObject.messageOwner;
            int i2 = message.date;
            TLRPC.Message messageCopyMessage = copyMessage(message);
            Boolean boolUseForwardForRepost = StoryEntry.useForwardForRepost(messageObject);
            if (boolUseForwardForRepost != null && boolUseForwardForRepost.booleanValue() && (messageFwdHeader = messageCopyMessage.fwd_from) != null && (peer = messageFwdHeader.from_id) != null) {
                messageCopyMessage.from_id = peer;
                messageCopyMessage.peer_id = peer;
                messageCopyMessage.flags &= -5;
                messageCopyMessage.fwd_from = null;
            }
            messageCopyMessage.voiceTranscriptionOpen = false;
            int i3 = messageObject.currentAccount;
            MessageObject messageObject2 = new MessageObject(i3, messageCopyMessage, messageObject.replyMessageObject, MessagesController.getInstance(i3).getUsers(), MessagesController.getInstance(messageObject.currentAccount).getChats(), null, null, true, true, 0L, true, z, false);
            messageObject2.setType();
            this.messageObjects.add(messageObject2);
        }
        this.groupedMessages = null;
        if (this.messageObjects.size() > 1) {
            MessageObject.GroupedMessages groupedMessages = new MessageObject.GroupedMessages();
            this.groupedMessages = groupedMessages;
            groupedMessages.messages.addAll(this.messageObjects);
            this.groupedMessages.groupId = ((MessageObject) this.messageObjects.get(0)).getGroupId();
            this.groupedMessages.calculate();
        }
        FrameLayout frameLayout = new FrameLayout(context) { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.1
            private final Matrix videoMatrix = new Matrix();
            private final float[] radii = new float[8];
            private final Path clipPath = new Path();

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i4, int i5) {
                int left;
                int boundsRight;
                MessageEntityView.this.listView.measure(i4, View.MeasureSpec.makeMeasureSpec(0, 0));
                if (MessageEntityView.this.textureView != null) {
                    MessageEntityView.this.textureView.measure(View.MeasureSpec.makeMeasureSpec(MessageEntityView.this.listView.getMeasuredWidth(), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(MessageEntityView.this.listView.getMeasuredHeight(), TLObject.FLAG_30));
                }
                int measuredWidth = MessageEntityView.this.listView.getMeasuredWidth();
                int iMax = 0;
                for (int i6 = 0; i6 < MessageEntityView.this.listView.getChildCount(); i6++) {
                    View childAt = MessageEntityView.this.listView.getChildAt(i6);
                    int left2 = childAt.getLeft();
                    int right = childAt.getRight();
                    if (childAt instanceof ChatMessageCell) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        left2 = childAt.getLeft() + chatMessageCell.getBoundsLeft();
                        left = childAt.getLeft();
                        boundsRight = chatMessageCell.getBoundsRight();
                    } else if (childAt instanceof ChatActionCell) {
                        ChatActionCell chatActionCell = (ChatActionCell) childAt;
                        left2 = childAt.getLeft() + chatActionCell.getBoundsLeft();
                        left = childAt.getLeft();
                        boundsRight = chatActionCell.getBoundsRight();
                    } else {
                        measuredWidth = Math.min(left2, measuredWidth);
                        iMax = Math.max(right, iMax);
                    }
                    right = boundsRight + left;
                    measuredWidth = Math.min(left2, measuredWidth);
                    iMax = Math.max(right, iMax);
                }
                setMeasuredDimension(iMax - measuredWidth, MessageEntityView.this.listView.getMeasuredHeight());
            }

            @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z2, int i4, int i5, int i6, int i7) {
                int left;
                int boundsRight;
                int measuredWidth = MessageEntityView.this.listView.getMeasuredWidth();
                int iMax = 0;
                for (int i8 = 0; i8 < MessageEntityView.this.listView.getChildCount(); i8++) {
                    View childAt = MessageEntityView.this.listView.getChildAt(i8);
                    int left2 = childAt.getLeft();
                    int right = childAt.getRight();
                    if (childAt instanceof ChatMessageCell) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        left2 = childAt.getLeft() + chatMessageCell.getBoundsLeft();
                        left = childAt.getLeft();
                        boundsRight = chatMessageCell.getBoundsRight();
                    } else if (childAt instanceof ChatActionCell) {
                        ChatActionCell chatActionCell = (ChatActionCell) childAt;
                        left2 = childAt.getLeft() + chatActionCell.getBoundsLeft();
                        left = childAt.getLeft();
                        boundsRight = chatActionCell.getBoundsRight();
                    } else {
                        measuredWidth = Math.min(left2, measuredWidth);
                        iMax = Math.max(right, iMax);
                    }
                    right = boundsRight + left;
                    measuredWidth = Math.min(left2, measuredWidth);
                    iMax = Math.max(right, iMax);
                }
                RecyclerListView recyclerListView = MessageEntityView.this.listView;
                recyclerListView.layout(-measuredWidth, 0, recyclerListView.getMeasuredWidth() - measuredWidth, MessageEntityView.this.listView.getMeasuredHeight());
                if (MessageEntityView.this.textureView != null) {
                    MessageEntityView.this.textureView.layout(0, 0, getMeasuredWidth(), MessageEntityView.this.listView.getMeasuredHeight());
                }
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                ImageReceiver photoImage;
                if (view == MessageEntityView.this.textureView) {
                    ChatMessageCell cell = MessageEntityView.this.getCell();
                    if (cell == null || (photoImage = cell.getPhotoImage()) == null) {
                        return false;
                    }
                    this.videoMatrix.reset();
                    float fMax = Math.max(photoImage.getImageWidth() / MessageEntityView.this.videoWidth, photoImage.getImageHeight() / MessageEntityView.this.videoHeight);
                    this.videoMatrix.postScale((MessageEntityView.this.videoWidth / MessageEntityView.this.textureView.getWidth()) * fMax, (MessageEntityView.this.videoHeight / MessageEntityView.this.textureView.getHeight()) * fMax);
                    this.videoMatrix.postTranslate(((MessageEntityView.this.listView.getX() + cell.getX()) + photoImage.getCenterX()) - ((MessageEntityView.this.videoWidth * fMax) / 2.0f), ((MessageEntityView.this.listView.getY() + cell.getY()) + photoImage.getCenterY()) - ((MessageEntityView.this.videoHeight * fMax) / 2.0f));
                    MessageEntityView.this.textureView.setTransform(this.videoMatrix);
                    canvas.save();
                    this.clipPath.rewind();
                    AndroidUtilities.rectTmp.set(MessageEntityView.this.listView.getX() + cell.getX() + photoImage.getImageX(), MessageEntityView.this.listView.getY() + cell.getY() + photoImage.getImageY(), MessageEntityView.this.listView.getX() + cell.getX() + photoImage.getImageX2(), MessageEntityView.this.listView.getY() + cell.getY() + photoImage.getImageY2());
                    for (int i4 = 0; i4 < photoImage.getRoundRadius().length; i4++) {
                        int i5 = i4 * 2;
                        this.radii[i5] = photoImage.getRoundRadius()[i4];
                        this.radii[i5 + 1] = photoImage.getRoundRadius()[i4];
                    }
                    this.clipPath.addRoundRect(AndroidUtilities.rectTmp, this.radii, Path.Direction.CW);
                    canvas.clipPath(this.clipPath);
                    boolean zDrawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return zDrawChild;
                }
                return super.drawChild(canvas, view, j);
            }
        };
        this.container = frameLayout;
        addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView = new RecyclerListView(context, this.resourcesProvider) { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.2
            private final ArrayList drawTimeAfter = new ArrayList();
            private final ArrayList drawNamesAfter = new ArrayList();
            private final ArrayList drawCaptionAfter = new ArrayList();
            private final ArrayList drawReactionsAfter = new ArrayList();
            private final ArrayList drawingGroups = new ArrayList(10);

            @Override // org.telegram.p023ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                canvas.save();
                this.selectorRect.setEmpty();
                drawChatBackgroundElements(canvas);
                super.dispatchDraw(canvas);
                drawChatForegroundElements(canvas);
                canvas.restore();
            }

            /* JADX WARN: Removed duplicated region for block: B:59:0x0176  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            private void drawChatForegroundElements(android.graphics.Canvas r18) {
                /*
                    Method dump skipped, instructions count: 531
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.Views.MessageEntityView.C41102.drawChatForegroundElements(android.graphics.Canvas):void");
            }

            /* JADX WARN: Removed duplicated region for block: B:62:0x016f  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            private void drawChatBackgroundElements(android.graphics.Canvas r26) {
                /*
                    Method dump skipped, instructions count: 880
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.Views.MessageEntityView.C41102.drawChatBackgroundElements(android.graphics.Canvas):void");
            }

            /* JADX WARN: Removed duplicated region for block: B:172:0x02dd  */
            /* JADX WARN: Removed duplicated region for block: B:175:0x02e9  */
            /* JADX WARN: Removed duplicated region for block: B:182:0x030c  */
            /* JADX WARN: Removed duplicated region for block: B:190:0x0338  */
            /* JADX WARN: Removed duplicated region for block: B:192:0x0362  */
            /* JADX WARN: Removed duplicated region for block: B:195:0x0373  */
            /* JADX WARN: Removed duplicated region for block: B:198:0x0381  */
            /* JADX WARN: Removed duplicated region for block: B:65:0x00fe  */
            @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public boolean drawChild(android.graphics.Canvas r20, android.view.View r21, long r22) {
                /*
                    Method dump skipped, instructions count: 911
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.Views.MessageEntityView.C41102.drawChild(android.graphics.Canvas, android.view.View, long):boolean");
            }
        };
        this.listView = recyclerListView;
        recyclerListView.setAdapter(new RecyclerListView.SelectionAdapter() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.3
            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                return true;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i4) {
                if (i4 == 1) {
                    return new RecyclerListView.Holder(new ChatActionCell(context, false, MessageEntityView.this.resourcesProvider) { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.3.1
                        public final BlurringShader.StoryBlurDrawer blurDrawer;
                        private final TextPaint textPaint;

                        {
                            this.blurDrawer = new BlurringShader.StoryBlurDrawer(blurManager, this, 10);
                            TextPaint textPaint = new TextPaint(1);
                            this.textPaint = textPaint;
                            textPaint.setTypeface(AndroidUtilities.bold());
                            textPaint.setTextSize(AndroidUtilities.m1146dp(Math.max(16, SharedConfig.fontSize) - 2));
                            textPaint.setColor(-1);
                        }

                        @Override // org.telegram.p023ui.Cells.ChatActionCell
                        protected Paint getThemedPaint(String str) {
                            if ("paintChatActionText".equals(str) || "paintChatActionText2".equals(str)) {
                                return this.textPaint;
                            }
                            if ("paintChatActionBackground".equals(str)) {
                                MessageEntityView.this.usesBackgroundPaint = true;
                                Paint paint = this.blurDrawer.adapt(MessageEntityView.this.isDark).getPaint(1.0f);
                                if (paint != null) {
                                    return paint;
                                }
                            }
                            return super.getThemedPaint(str);
                        }
                    });
                }
                ChatMessageCell chatMessageCell = new ChatMessageCell(context, UserConfig.selectedAccount, false, null, MessageEntityView.this.resourcesProvider) { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.3.2
                    public BlurringShader.StoryBlurDrawer blurDrawer;
                    private final Paint clearPaint;
                    private final RectF dst;
                    private final Rect src;
                    private final float[] radii = new float[8];
                    private final Path clipPath = new Path();

                    @Override // org.telegram.p023ui.Cells.ChatMessageCell, android.view.View
                    public boolean onTouchEvent(MotionEvent motionEvent) {
                        return false;
                    }

                    {
                        this.blurDrawer = new BlurringShader.StoryBlurDrawer(blurManager, this, 10);
                        Paint paint = new Paint();
                        this.clearPaint = paint;
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                        this.src = new Rect();
                        this.dst = new RectF();
                    }

                    @Override // org.telegram.p023ui.Cells.ChatMessageCell, android.view.View
                    protected void onDraw(Canvas canvas) {
                        Canvas canvas2;
                        C41113 c41113 = C41113.this;
                        PreviewView.TextureViewHolder textureViewHolder2 = textureViewHolder;
                        if ((textureViewHolder2 != null && textureViewHolder2.active && textureViewHolder2.textureViewActive) || MessageEntityView.this.clipVideoMessageForBitmap) {
                            canvas2 = canvas;
                            canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), 255, 31);
                        } else {
                            canvas2 = canvas;
                            canvas2.save();
                        }
                        super.onDraw(canvas2);
                        canvas2.restore();
                    }

                    @Override // org.telegram.p023ui.Cells.ChatMessageCell
                    public Paint getThemedPaint(String str) {
                        if ("paintChatActionBackground".equals(str)) {
                            MessageEntityView.this.usesBackgroundPaint = true;
                            Paint paint = this.blurDrawer.getPaint(1.0f);
                            if (paint != null) {
                                return paint;
                            }
                        }
                        return super.getThemedPaint(str);
                    }

                    @Override // org.telegram.p023ui.Cells.ChatMessageCell
                    protected boolean drawPhotoImage(Canvas canvas) {
                        PreviewView.TextureViewHolder textureViewHolder2;
                        ImageReceiver photoImage = getPhotoImage();
                        C41113 c41113 = C41113.this;
                        if (z && photoImage != null && (((textureViewHolder2 = textureViewHolder) != null && textureViewHolder2.active && textureViewHolder2.textureViewActive && MessageEntityView.this.textureViewActive) || MessageEntityView.this.clipVideoMessageForBitmap || (MessageEntityView.this.textureView != null && MessageEntityView.this.drawForBitmap()))) {
                            for (int i5 = 0; i5 < photoImage.getRoundRadius().length; i5++) {
                                int i6 = i5 * 2;
                                this.radii[i6] = photoImage.getRoundRadius()[i5];
                                this.radii[i6 + 1] = photoImage.getRoundRadius()[i5];
                            }
                            RectF rectF = AndroidUtilities.rectTmp;
                            rectF.set(photoImage.getImageX(), photoImage.getImageY(), photoImage.getImageX2(), photoImage.getImageY2());
                            this.clipPath.rewind();
                            this.clipPath.addRoundRect(rectF, this.radii, Path.Direction.CW);
                            if (MessageEntityView.this.textureView != null && MessageEntityView.this.drawForBitmap()) {
                                Bitmap bitmap = MessageEntityView.this.textureView.getBitmap();
                                if (bitmap != null) {
                                    canvas.save();
                                    canvas.clipPath(this.clipPath);
                                    canvas.translate(-getX(), -getY());
                                    float fMax = Math.max(photoImage.getImageWidth() / MessageEntityView.this.videoWidth, photoImage.getImageHeight() / MessageEntityView.this.videoHeight);
                                    canvas.translate(photoImage.getCenterX() - ((MessageEntityView.this.videoWidth * fMax) / 2.0f), photoImage.getCenterY() - ((MessageEntityView.this.videoHeight * fMax) / 2.0f));
                                    canvas.scale((MessageEntityView.this.videoWidth / MessageEntityView.this.textureView.getWidth()) * fMax, (MessageEntityView.this.videoHeight / MessageEntityView.this.textureView.getHeight()) * fMax);
                                    this.src.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                                    this.dst.set(0.0f, 0.0f, MessageEntityView.this.textureView.getWidth(), MessageEntityView.this.textureView.getHeight());
                                    canvas.drawBitmap(bitmap, this.src, this.dst, (Paint) null);
                                    canvas.restore();
                                } else {
                                    return super.drawPhotoImage(canvas);
                                }
                            } else {
                                canvas.drawPath(this.clipPath, this.clearPaint);
                            }
                            return true;
                        }
                        return super.drawPhotoImage(canvas);
                    }
                };
                chatMessageCell.isChat = true;
                return new RecyclerListView.Holder(chatMessageCell);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i4) {
                boolean z2;
                MessageObject.GroupedMessagePosition position;
                MessageObject messageObject3 = (MessageObject) MessageEntityView.this.messageObjects.get((MessageEntityView.this.messageObjects.size() - 1) - i4);
                View view = viewHolder.itemView;
                if (view instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                    if (MessageEntityView.this.groupedMessages == null || (position = MessageEntityView.this.groupedMessages.getPosition(messageObject3)) == null) {
                        z2 = false;
                    } else {
                        z2 = position.minY != 0;
                    }
                    chatMessageCell.setMessageObject(messageObject3, MessageEntityView.this.groupedMessages, MessageEntityView.this.groupedMessages != null, z2, false);
                    return;
                }
                if (view instanceof ChatActionCell) {
                    ((ChatActionCell) view).setMessageObject(messageObject3);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i4) {
                return ((MessageObject) MessageEntityView.this.messageObjects.get((MessageEntityView.this.messageObjects.size() - 1) - i4)).contentType;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return MessageEntityView.this.messageObjects.size();
            }
        });
        GridLayoutManagerFixed gridLayoutManagerFixed = new GridLayoutManagerFixed(context, MediaDataController.MAX_STYLE_RUNS_COUNT, 1, true) { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.4
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManagerFixed
            public boolean shouldLayoutChildFromOpositeSide(View view) {
                if (view instanceof ChatMessageCell) {
                    return !((ChatMessageCell) view).getMessageObject().isOutOwner();
                }
                return false;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManagerFixed
            protected boolean hasSiblingChild(int i4) {
                byte b;
                int size = (MessageEntityView.this.messageObjects.size() - 1) - i4;
                if (MessageEntityView.this.groupedMessages != null && size >= 0 && size < MessageEntityView.this.messageObjects.size()) {
                    MessageObject.GroupedMessagePosition position = MessageEntityView.this.groupedMessages.getPosition((MessageObject) MessageEntityView.this.messageObjects.get(size));
                    if (position != null && position.minX != position.maxX && (b = position.minY) == position.maxY && b != 0) {
                        int size2 = MessageEntityView.this.groupedMessages.posArray.size();
                        for (int i5 = 0; i5 < size2; i5++) {
                            MessageObject.GroupedMessagePosition groupedMessagePosition = MessageEntityView.this.groupedMessages.posArray.get(i5);
                            if (groupedMessagePosition != position) {
                                byte b2 = groupedMessagePosition.minY;
                                byte b3 = position.minY;
                                if (b2 <= b3 && groupedMessagePosition.maxY >= b3) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }
        };
        gridLayoutManagerFixed.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.5
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i4) {
                int size = (MessageEntityView.this.messageObjects.size() - 1) - i4;
                if (MessageEntityView.this.groupedMessages == null || size < 0 || size >= MessageEntityView.this.groupedMessages.messages.size()) {
                    return MediaDataController.MAX_STYLE_RUNS_COUNT;
                }
                MessageObject.GroupedMessagePosition position = MessageEntityView.this.groupedMessages.getPosition(MessageEntityView.this.groupedMessages.messages.get(size));
                return position != null ? position.spanSize : MediaDataController.MAX_STYLE_RUNS_COUNT;
            }
        });
        recyclerListView.setLayoutManager(gridLayoutManagerFixed);
        recyclerListView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView.6
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                ChatMessageCell chatMessageCell;
                MessageObject.GroupedMessages currentMessagesGroup;
                MessageObject.GroupedMessagePosition currentPosition;
                int i4 = 0;
                rect.bottom = 0;
                if (!(view instanceof ChatMessageCell) || (currentMessagesGroup = (chatMessageCell = (ChatMessageCell) view).getCurrentMessagesGroup()) == null || (currentPosition = chatMessageCell.getCurrentPosition()) == null || currentPosition.siblingHeights == null) {
                    return;
                }
                android.graphics.Point point2 = AndroidUtilities.displaySize;
                float fMax = Math.max(point2.x, point2.y) * 0.5f;
                int extraInsetHeight = chatMessageCell.getExtraInsetHeight();
                int i5 = 0;
                while (true) {
                    if (i5 >= currentPosition.siblingHeights.length) {
                        break;
                    }
                    extraInsetHeight += (int) Math.ceil(r3[i5] * fMax);
                    i5++;
                }
                int iRound = extraInsetHeight + ((currentPosition.maxY - currentPosition.minY) * Math.round(AndroidUtilities.density * 7.0f));
                int size = currentMessagesGroup.posArray.size();
                while (true) {
                    if (i4 < size) {
                        MessageObject.GroupedMessagePosition groupedMessagePosition = currentMessagesGroup.posArray.get(i4);
                        byte b = groupedMessagePosition.minY;
                        byte b2 = currentPosition.minY;
                        if (b == b2 && ((groupedMessagePosition.minX != currentPosition.minX || groupedMessagePosition.maxX != currentPosition.maxX || b != b2 || groupedMessagePosition.maxY != currentPosition.maxY) && b == b2)) {
                            iRound -= ((int) Math.ceil(fMax * groupedMessagePosition.f1450ph)) - AndroidUtilities.m1146dp(4.0f);
                            break;
                        }
                        i4++;
                    } else {
                        break;
                    }
                }
                rect.bottom = -iRound;
            }
        });
        frameLayout.addView(recyclerListView, LayoutHelper.createFrame(-1, -1.0f));
        if (textureViewHolder != null && textureViewHolder.active) {
            textureViewHolder.takeTextureView(new Utilities.Callback() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$new$0((TextureView) obj);
                }
            }, new Utilities.Callback2() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$new$2((Integer) obj, (Integer) obj2);
                }
            });
        }
        updatePosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(TextureView textureView) {
        this.textureView = textureView;
        if (textureView != null) {
            this.container.addView(textureView, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(Integer num, Integer num2) {
        this.videoWidth = num.intValue();
        this.videoHeight = num2.intValue();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.Paint.Views.MessageEntityView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1();
            }
        }, 60L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        this.textureViewActive = true;
        invalidateAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ChatMessageCell getCell() {
        if (this.listView == null) {
            return null;
        }
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            if (this.listView.getChildAt(i) instanceof ChatMessageCell) {
                return (ChatMessageCell) this.listView.getChildAt(i);
            }
        }
        return null;
    }

    public float getBubbleBounds(RectF rectF) {
        float y;
        float x;
        float x2;
        float y2;
        float fMin = 2.1474836E9f;
        float fMin2 = 2.1474836E9f;
        float fMax = -2.1474836E9f;
        float fMax2 = -2.1474836E9f;
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof ChatMessageCell) {
                ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                if (chatMessageCell.getMessageObject() != null && chatMessageCell.getMessageObject().isRoundVideo() && chatMessageCell.getPhotoImage() != null) {
                    x = this.container.getX() + chatMessageCell.getX() + chatMessageCell.getPhotoImage().getImageX();
                    x2 = this.container.getX() + chatMessageCell.getX() + chatMessageCell.getPhotoImage().getImageX2();
                    y2 = this.container.getY() + chatMessageCell.getY() + chatMessageCell.getPhotoImage().getImageY();
                    y = this.container.getY() + chatMessageCell.getY() + chatMessageCell.getPhotoImage().getImageY2();
                } else {
                    float x3 = this.container.getX() + childAt.getX() + chatMessageCell.getBackgroundDrawableLeft() + AndroidUtilities.m1146dp(1.0f);
                    if (this.groupedMessages == null) {
                        x3 += AndroidUtilities.m1146dp(8.0f);
                    }
                    float x4 = ((this.container.getX() + childAt.getX()) + chatMessageCell.getBackgroundDrawableRight()) - AndroidUtilities.m1146dp(1.66f);
                    float y3 = this.container.getY() + childAt.getY() + chatMessageCell.getBackgroundDrawableTop() + AndroidUtilities.m1146dp(2.0f);
                    y = ((this.container.getY() + childAt.getY()) + chatMessageCell.getBackgroundDrawableBottom()) - AndroidUtilities.m1146dp(1.0f);
                    x = x3;
                    x2 = x4;
                    y2 = y3;
                }
                fMin = Math.min(Math.min(fMin, x), x2);
                fMax = Math.max(Math.max(fMax, x), x2);
                fMin2 = Math.min(Math.min(fMin2, y2), y);
                fMax2 = Math.max(Math.max(fMax2, y2), y);
            } else if (childAt instanceof ChatActionCell) {
                ChatActionCell chatActionCell = (ChatActionCell) childAt;
                if (chatActionCell.starGiftLayout.has()) {
                    float x5 = this.container.getX() + chatActionCell.getX() + chatActionCell.getBoundsLeft();
                    float x6 = this.container.getX() + chatActionCell.getX() + chatActionCell.getBoundsRight();
                    float y4 = this.container.getY() + chatActionCell.getY();
                    float y5 = this.container.getY() + chatActionCell.getY() + chatActionCell.getMeasuredHeight();
                    fMin = Math.min(Math.min(fMin, x5), x6);
                    fMax = Math.max(Math.max(fMax, x5), x6);
                    fMin2 = Math.min(Math.min(fMin2, y4), y5);
                    fMax2 = Math.max(Math.max(fMax2, y4), y5);
                }
            }
        }
        rectF.set(fMin, fMin2, fMax, fMax2);
        return AndroidUtilities.m1146dp(SharedConfig.bubbleRadius);
    }

    public void invalidateAll() {
        this.listView.invalidate();
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            this.listView.getChildAt(i).invalidate();
        }
    }

    public void prepareToDraw(boolean z) {
        this.clipVideoMessageForBitmap = z;
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof ChatMessageCell) {
                ((ChatMessageCell) childAt).drawingToBitmap = z;
            }
        }
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected void updatePosition() {
        setX(getPositionX() - (getMeasuredWidth() / 2.0f));
        setY(getPositionY() - (getMeasuredHeight() / 2.0f));
        updateSelectionView();
        if (this.usesBackgroundPaint) {
            invalidateAll();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onMeasure(int r5, int r6) {
        /*
            r4 = this;
            android.widget.FrameLayout r0 = r4.container
            r0.measure(r5, r6)
            android.widget.FrameLayout r0 = r4.container
            int r0 = r0.getMeasuredWidth()
            android.widget.FrameLayout r1 = r4.container
            int r1 = r1.getMeasuredHeight()
            r4.setMeasuredDimension(r0, r1)
            r4.updatePosition()
            boolean r0 = r4.firstMeasure
            if (r0 == 0) goto L8b
            java.util.ArrayList r0 = r4.messageObjects
            r1 = 0
            if (r0 == 0) goto L34
            int r0 = r0.size()
            r2 = 1
            if (r0 != r2) goto L34
            java.util.ArrayList r0 = r4.messageObjects
            java.lang.Object r0 = r0.get(r1)
            org.telegram.messenger.MessageObject r0 = (org.telegram.messenger.MessageObject) r0
            int r0 = r0.contentType
            if (r0 != r2) goto L34
            goto L35
        L34:
            r2 = 0
        L35:
            int r5 = android.view.View.MeasureSpec.getSize(r5)
            r0 = 0
            if (r2 == 0) goto L3e
            r3 = 0
            goto L40
        L3e:
            r3 = 1110441984(0x42300000, float:44.0)
        L40:
            int r3 = org.telegram.messenger.AndroidUtilities.m1146dp(r3)
            int r5 = r5 - r3
            int r6 = android.view.View.MeasureSpec.getSize(r6)
            if (r2 == 0) goto L4c
            goto L4e
        L4c:
            r0 = 1128267776(0x43400000, float:192.0)
        L4e:
            int r0 = org.telegram.messenger.AndroidUtilities.m1146dp(r0)
            int r6 = r6 - r0
            int r0 = r4.getMeasuredWidth()
            int r3 = r4.getMeasuredHeight()
            float r5 = (float) r5
            float r0 = (float) r0
            float r5 = r5 / r0
            float r6 = (float) r6
            float r0 = (float) r3
            float r6 = r6 / r0
            float r5 = java.lang.Math.min(r5, r6)
            r6 = 1065353216(0x3f800000, float:1.0)
            int r0 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r0 >= 0) goto L6e
            r4.setScale(r5)
        L6e:
            org.telegram.ui.Components.Point r0 = r4.getPosition()
            if (r2 != 0) goto L86
            float r2 = r0.f1929x
            r3 = 1100480512(0x41980000, float:19.0)
            int r3 = org.telegram.messenger.AndroidUtilities.m1146dp(r3)
            float r3 = (float) r3
            float r5 = java.lang.Math.min(r6, r5)
            float r3 = r3 * r5
            float r2 = r2 - r3
            r0.f1929x = r2
        L86:
            r4.setPosition(r0)
            r4.firstMeasure = r1
        L8b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.Views.MessageEntityView.onMeasure(int, int):void");
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    public org.telegram.p023ui.Components.Rect getSelectionBounds() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return new org.telegram.p023ui.Components.Rect();
        }
        float scaleX = viewGroup.getScaleX();
        return new org.telegram.p023ui.Components.Rect(((getPositionX() * scaleX) - (((getMeasuredWidth() * getScale()) / 2.0f) * scaleX)) - AndroidUtilities.m1146dp(35.5f), ((getPositionY() * scaleX) - (((getMeasuredHeight() * getScale()) / 2.0f) * scaleX)) - AndroidUtilities.m1146dp(35.5f), (getMeasuredWidth() * getScale() * scaleX) + AndroidUtilities.m1146dp(71.0f), (getMeasuredHeight() * getScale() * scaleX) + AndroidUtilities.m1146dp(71.0f));
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected EntityView.SelectionView createSelectionView() {
        return new MessageEntityViewSelectionView(getContext());
    }

    public class MessageEntityViewSelectionView extends EntityView.SelectionView {
        private final Paint clearPaint;
        private Path path;

        public MessageEntityViewSelectionView(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.clearPaint = paint;
            this.path = new Path();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }

        @Override // org.telegram.ui.Components.Paint.Views.EntityView.SelectionView
        protected int pointInsideHandle(float f, float f2) {
            float fM1146dp = AndroidUtilities.m1146dp(1.0f);
            float fM1146dp2 = AndroidUtilities.m1146dp(19.5f);
            float f3 = fM1146dp + fM1146dp2;
            float f4 = f3 * 2.0f;
            float measuredWidth = getMeasuredWidth() - f4;
            float measuredHeight = getMeasuredHeight() - f4;
            float f5 = (measuredHeight / 2.0f) + f3;
            if (f > f3 - fM1146dp2 && f2 > f5 - fM1146dp2 && f < f3 + fM1146dp2 && f2 < f5 + fM1146dp2) {
                return 1;
            }
            float f6 = f3 + measuredWidth;
            if (f <= f6 - fM1146dp2 || f2 <= f5 - fM1146dp2 || f >= f6 + fM1146dp2 || f2 >= f5 + fM1146dp2) {
                return (f <= f3 || f >= measuredWidth || f2 <= f3 || f2 >= measuredHeight) ? 0 : 3;
            }
            return 2;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            Canvas canvas2;
            super.onDraw(canvas);
            int saveCount = canvas.getSaveCount();
            float showAlpha = getShowAlpha();
            if (showAlpha <= 0.0f) {
                return;
            }
            if (showAlpha < 1.0f) {
                int i = (int) (showAlpha * 255.0f);
                canvas2 = canvas;
                canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), i, 31);
            } else {
                canvas2 = canvas;
            }
            float fM1146dp = AndroidUtilities.m1146dp(2.0f);
            float fDpf2 = AndroidUtilities.dpf2(5.66f);
            float fM1146dp2 = fM1146dp + fDpf2 + AndroidUtilities.m1146dp(15.0f);
            float f = fM1146dp2 * 2.0f;
            float measuredWidth = getMeasuredWidth() - f;
            float measuredHeight = getMeasuredHeight() - f;
            RectF rectF = AndroidUtilities.rectTmp;
            float f2 = fM1146dp2 + measuredWidth;
            float f3 = fM1146dp2 + measuredHeight;
            rectF.set(fM1146dp2, fM1146dp2, f2, f3);
            float fM1146dp3 = AndroidUtilities.m1146dp(12.0f);
            float fMin = Math.min(fM1146dp3, measuredWidth / 2.0f);
            float f4 = measuredHeight / 2.0f;
            float fMin2 = Math.min(fM1146dp3, f4);
            this.path.rewind();
            float f5 = fMin * 2.0f;
            float f6 = fM1146dp2 + f5;
            float f7 = 2.0f * fMin2;
            float f8 = fM1146dp2 + f7;
            rectF.set(fM1146dp2, fM1146dp2, f6, f8);
            this.path.arcTo(rectF, 180.0f, 90.0f);
            float f9 = f2 - f5;
            rectF.set(f9, fM1146dp2, f2, f8);
            this.path.arcTo(rectF, 270.0f, 90.0f);
            canvas2.drawPath(this.path, this.paint);
            this.path.rewind();
            float f10 = f3 - f7;
            rectF.set(fM1146dp2, f10, f6, f3);
            this.path.arcTo(rectF, 180.0f, -90.0f);
            rectF.set(f9, f10, f2, f3);
            this.path.arcTo(rectF, 90.0f, -90.0f);
            canvas2.drawPath(this.path, this.paint);
            float f11 = fM1146dp2 + f4;
            canvas2.drawCircle(fM1146dp2, f11, fDpf2, this.dotStrokePaint);
            canvas2.drawCircle(fM1146dp2, f11, (fDpf2 - AndroidUtilities.m1146dp(1.0f)) + 1.0f, this.dotPaint);
            canvas2.drawCircle(f2, f11, fDpf2, this.dotStrokePaint);
            canvas2.drawCircle(f2, f11, (fDpf2 - AndroidUtilities.m1146dp(1.0f)) + 1.0f, this.dotPaint);
            canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), 255, 31);
            float f12 = fM1146dp2 + fMin2;
            float f13 = f3 - fMin2;
            canvas.drawLine(fM1146dp2, f12, fM1146dp2, f13, this.paint);
            canvas.drawLine(f2, f12, f2, f13, this.paint);
            canvas.drawCircle(f2, f11, (AndroidUtilities.m1146dp(1.0f) + fDpf2) - 1.0f, this.clearPaint);
            canvas.drawCircle(fM1146dp2, f11, (fDpf2 + AndroidUtilities.m1146dp(1.0f)) - 1.0f, this.clearPaint);
            canvas.restoreToCount(saveCount);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setupTheme(org.telegram.p023ui.Stories.recorder.StoryEntry r8) {
        /*
            r7 = this;
            if (r8 != 0) goto L8
            android.util.SparseIntArray r8 = r7.currentColors
            r8.clear()
            return
        L8:
            android.content.Context r0 = org.telegram.messenger.ApplicationLoader.applicationContext
            java.lang.String r1 = "themeconfig"
            r2 = 0
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r2)
            java.lang.String r1 = "lastDayTheme"
            java.lang.String r3 = "Blue"
            java.lang.String r1 = r0.getString(r1, r3)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r1)
            if (r4 == 0) goto L29
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r1)
            boolean r4 = r4.isDark()
            if (r4 == 0) goto L2a
        L29:
            r1 = r3
        L2a:
            java.lang.String r4 = "lastDarkTheme"
            java.lang.String r5 = "Dark Blue"
            java.lang.String r0 = r0.getString(r4, r5)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r0)
            if (r4 == 0) goto L42
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r0)
            boolean r4 = r4.isDark()
            if (r4 != 0) goto L43
        L42:
            r0 = r5
        L43:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getActiveTheme()
            boolean r6 = r1.equals(r0)
            if (r6 == 0) goto L66
            boolean r4 = r4.isDark()
            if (r4 != 0) goto L64
            boolean r4 = r1.equals(r5)
            if (r4 != 0) goto L64
            java.lang.String r4 = "Night"
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L62
            goto L64
        L62:
            r3 = r1
            goto L68
        L64:
            r5 = r0
            goto L68
        L66:
            r5 = r0
            goto L62
        L68:
            boolean r8 = r8.isDark
            r7.isDark = r8
            if (r8 == 0) goto L73
            org.telegram.ui.ActionBar.Theme$ThemeInfo r8 = org.telegram.p023ui.ActionBar.Theme.getTheme(r5)
            goto L77
        L73:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r8 = org.telegram.p023ui.ActionBar.Theme.getTheme(r3)
        L77:
            r0 = 1
            java.lang.String[] r0 = new java.lang.String[r0]
            java.lang.String r1 = r8.assetName
            r3 = 0
            if (r1 == 0) goto L84
            android.util.SparseIntArray r0 = org.telegram.p023ui.ActionBar.Theme.getThemeFileValues(r3, r1, r0)
            goto L8f
        L84:
            java.io.File r1 = new java.io.File
            java.lang.String r4 = r8.pathToFile
            r1.<init>(r4)
            android.util.SparseIntArray r0 = org.telegram.p023ui.ActionBar.Theme.getThemeFileValues(r1, r3, r0)
        L8f:
            android.util.SparseIntArray r1 = r7.currentColors
            r1.clear()
            int[] r1 = org.telegram.p023ui.ActionBar.Theme.getDefaultColors()
            if (r1 == 0) goto La8
            r3 = 0
        L9b:
            int r4 = r1.length
            if (r3 >= r4) goto La8
            android.util.SparseIntArray r4 = r7.currentColors
            r5 = r1[r3]
            r4.put(r3, r5)
            int r3 = r3 + 1
            goto L9b
        La8:
            if (r0 == 0) goto Lcc
            r1 = 0
        Lab:
            int r3 = r0.size()
            if (r1 >= r3) goto Lc1
            android.util.SparseIntArray r3 = r7.currentColors
            int r4 = r0.keyAt(r1)
            int r5 = r0.valueAt(r1)
            r3.put(r4, r5)
            int r1 = r1 + 1
            goto Lab
        Lc1:
            org.telegram.ui.ActionBar.Theme$ThemeAccent r8 = r8.getAccent(r2)
            if (r8 == 0) goto Lcc
            android.util.SparseIntArray r1 = r7.currentColors
            r8.fillAccentColors(r0, r1)
        Lcc:
            r7.invalidateAll()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.Views.MessageEntityView.setupTheme(org.telegram.ui.Stories.recorder.StoryEntry):void");
    }

    public TLRPC.Message copyMessage(TLRPC.Message message) {
        TLRPC.Message tL_messageService;
        if (message instanceof TLRPC.TL_message) {
            tL_messageService = new TLRPC.TL_message();
        } else {
            if (!(message instanceof TLRPC.TL_messageService)) {
                return message;
            }
            tL_messageService = new TLRPC.TL_messageService();
        }
        tL_messageService.f1597id = message.f1597id;
        tL_messageService.from_id = message.from_id;
        tL_messageService.peer_id = message.peer_id;
        tL_messageService.date = message.date;
        tL_messageService.expire_date = message.expire_date;
        tL_messageService.action = message.action;
        tL_messageService.message = message.message;
        tL_messageService.media = message.media;
        tL_messageService.flags = message.flags;
        tL_messageService.mentioned = message.mentioned;
        tL_messageService.media_unread = message.media_unread;
        tL_messageService.out = message.out;
        tL_messageService.unread = message.unread;
        tL_messageService.entities = message.entities;
        tL_messageService.via_bot_name = message.via_bot_name;
        tL_messageService.reply_markup = message.reply_markup;
        tL_messageService.views = message.views;
        tL_messageService.forwards = message.forwards;
        tL_messageService.replies = message.replies;
        tL_messageService.edit_date = message.edit_date;
        tL_messageService.silent = message.silent;
        tL_messageService.post = message.post;
        tL_messageService.from_scheduled = message.from_scheduled;
        tL_messageService.legacy = message.legacy;
        tL_messageService.edit_hide = message.edit_hide;
        tL_messageService.pinned = message.pinned;
        tL_messageService.fwd_from = message.fwd_from;
        tL_messageService.via_bot_id = message.via_bot_id;
        tL_messageService.reply_to = message.reply_to;
        tL_messageService.post_author = message.post_author;
        tL_messageService.grouped_id = message.grouped_id;
        tL_messageService.reactions = message.reactions;
        tL_messageService.restriction_reason = message.restriction_reason;
        tL_messageService.ttl_period = message.ttl_period;
        tL_messageService.noforwards = message.noforwards;
        tL_messageService.invert_media = message.invert_media;
        tL_messageService.send_state = message.send_state;
        tL_messageService.fwd_msg_id = message.fwd_msg_id;
        tL_messageService.attachPath = message.attachPath;
        tL_messageService.params = message.params;
        tL_messageService.random_id = message.random_id;
        tL_messageService.local_id = message.local_id;
        tL_messageService.dialog_id = message.dialog_id;
        tL_messageService.ttl = message.ttl;
        tL_messageService.destroyTime = message.destroyTime;
        tL_messageService.destroyTimeMillis = message.destroyTimeMillis;
        tL_messageService.layer = message.layer;
        tL_messageService.seq_in = message.seq_in;
        tL_messageService.seq_out = message.seq_out;
        tL_messageService.with_my_score = message.with_my_score;
        tL_messageService.replyMessage = message.replyMessage;
        tL_messageService.reqId = message.reqId;
        tL_messageService.realId = message.realId;
        tL_messageService.stickerVerified = message.stickerVerified;
        tL_messageService.isThreadMessage = message.isThreadMessage;
        tL_messageService.voiceTranscription = message.voiceTranscription;
        tL_messageService.voiceTranscriptionOpen = message.voiceTranscriptionOpen;
        tL_messageService.voiceTranscriptionRated = message.voiceTranscriptionRated;
        tL_messageService.voiceTranscriptionFinal = message.voiceTranscriptionFinal;
        tL_messageService.voiceTranscriptionForce = message.voiceTranscriptionForce;
        tL_messageService.voiceTranscriptionId = message.voiceTranscriptionId;
        tL_messageService.premiumEffectWasPlayed = message.premiumEffectWasPlayed;
        tL_messageService.originalLanguage = message.originalLanguage;
        tL_messageService.translatedToLanguage = message.translatedToLanguage;
        tL_messageService.translatedText = message.translatedText;
        tL_messageService.replyStory = message.replyStory;
        return tL_messageService;
    }
}
