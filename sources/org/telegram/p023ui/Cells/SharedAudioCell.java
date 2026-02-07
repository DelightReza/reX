package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.CheckBox2;
import org.telegram.p023ui.Components.DotDividerSpan;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RadialProgress2;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.FilteredSearchView;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class SharedAudioCell extends FrameLayout implements DownloadController.FileDownloadProgressListener, NotificationCenter.NotificationCenterDelegate {
    private int TAG;
    private boolean buttonPressed;
    private int buttonState;
    private int buttonX;
    private int buttonY;
    private StaticLayout captionLayout;
    AnimatedEmojiSpan.EmojiGroupedSpans captionLayoutEmojis;
    private float captionLayoutLeft;
    private float captionLayoutWidth;
    private TextPaint captionTextPaint;
    private int captionY;
    private CheckBox2 checkBox;
    private boolean checkForButtonPress;
    private int currentAccount;
    private MessageObject currentMessageObject;
    private StaticLayout dateLayout;
    private int dateLayoutX;
    private TextPaint description2TextPaint;
    private StaticLayout descriptionLayout;
    AnimatedEmojiSpan.EmojiGroupedSpans descriptionLayoutEmojis;
    private float descriptionLayoutLeft;
    private float descriptionLayoutWidth;
    private int descriptionY;
    private SpannableStringBuilder dotSpan;
    float enterAlpha;
    FlickerLoadingView globalGradientView;
    private int hasMiniProgress;
    private boolean miniButtonPressed;
    private int miniButtonState;
    private boolean needDivider;
    private RadialProgress2 radialProgress;
    private final Theme.ResourcesProvider resourcesProvider;
    boolean showName;
    float showNameProgress;
    boolean showReorderIcon;
    float showReorderIconProgress;
    private StaticLayout titleLayout;
    AnimatedEmojiSpan.EmojiGroupedSpans titleLayoutEmojis;
    private float titleLayoutLeft;
    private float titleLayoutWidth;
    TextPaint titlePaint;
    private int titleY;
    private int viewType;

    protected boolean needPlayMessage(MessageObject messageObject) {
        return false;
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressUpload(String str, long j, long j2, boolean z) {
    }

    public SharedAudioCell(Context context) {
        this(context, 0, null);
    }

    public SharedAudioCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        this(context, 0, resourcesProvider);
    }

    public SharedAudioCell(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.titleY = AndroidUtilities.m1146dp(9.0f);
        this.descriptionY = AndroidUtilities.m1146dp(29.0f);
        this.captionY = AndroidUtilities.m1146dp(29.0f);
        this.currentAccount = UserConfig.selectedAccount;
        this.showName = true;
        this.showNameProgress = 0.0f;
        this.enterAlpha = 1.0f;
        this.resourcesProvider = resourcesProvider;
        this.viewType = i;
        setFocusable(true);
        setImportantForAccessibility(1);
        RadialProgress2 radialProgress2 = new RadialProgress2(this, resourcesProvider);
        this.radialProgress = radialProgress2;
        radialProgress2.setColorKeys(Theme.key_chat_inLoader, Theme.key_chat_inLoaderSelected, Theme.key_chat_inMediaIcon, Theme.key_chat_inMediaIconSelected);
        this.TAG = DownloadController.getInstance(this.currentAccount).generateObserverTag();
        setWillNotDraw(false);
        CheckBox2 checkBox2 = new CheckBox2(context, 22, resourcesProvider);
        this.checkBox = checkBox2;
        checkBox2.setVisibility(4);
        this.checkBox.setColor(-1, Theme.key_windowBackgroundWhite, Theme.key_checkboxCheck);
        this.checkBox.setDrawUnchecked(false);
        this.checkBox.setDrawBackgroundAsArc(3);
        CheckBox2 checkBox22 = this.checkBox;
        boolean z = LocaleController.isRTL;
        addView(checkBox22, LayoutHelper.createFrame(24, 24.0f, (z ? 5 : 3) | 48, z ? 0.0f : 38.1f, 32.1f, z ? 6.0f : 0.0f, 0.0f));
        if (i == 1) {
            TextPaint textPaint = new TextPaint(1);
            this.description2TextPaint = textPaint;
            textPaint.setTextSize(AndroidUtilities.m1146dp(13.0f));
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(".");
            this.dotSpan = spannableStringBuilder;
            spannableStringBuilder.setSpan(new DotDividerSpan(), 0, 1, 0);
        }
        TextPaint textPaint2 = new TextPaint(1);
        this.captionTextPaint = textPaint2;
        textPaint2.setTextSize(AndroidUtilities.m1146dp(13.0f));
        if (resourcesProvider != null) {
            TextPaint textPaint3 = new TextPaint(1);
            this.titlePaint = textPaint3;
            textPaint3.setTypeface(AndroidUtilities.bold());
            this.titlePaint.setTextSize(AndroidUtilities.m1146dp(15.0f));
            this.titlePaint.setColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int iM1146dp;
        CharSequence charSequenceReplace;
        this.descriptionLayout = null;
        this.titleLayout = null;
        this.captionLayout = null;
        int size = (View.MeasureSpec.getSize(i) - AndroidUtilities.m1146dp(AndroidUtilities.leftBaseline)) - AndroidUtilities.m1146dp(28.0f);
        if (this.viewType == 1) {
            String strStringForMessageListDate = LocaleController.stringForMessageListDate(this.currentMessageObject.messageOwner.date);
            int iCeil = (int) Math.ceil(this.description2TextPaint.measureText(strStringForMessageListDate));
            this.dateLayout = ChatMessageCell.generateStaticLayout(strStringForMessageListDate, this.description2TextPaint, iCeil, iCeil, 0, 1);
            this.dateLayoutX = ((size - iCeil) - AndroidUtilities.m1146dp(8.0f)) + AndroidUtilities.m1146dp(20.0f);
            iM1146dp = iCeil + AndroidUtilities.m1146dp(12.0f);
        } else {
            iM1146dp = 0;
        }
        try {
            if (this.viewType == 1 && (this.currentMessageObject.isVoice() || this.currentMessageObject.isRoundVideo())) {
                charSequenceReplace = FilteredSearchView.createFromInfoString(this.currentMessageObject, 1);
            } else {
                charSequenceReplace = this.currentMessageObject.getMusicTitle().replace('\n', ' ');
            }
            CharSequence charSequenceHighlightText = AndroidUtilities.highlightText(charSequenceReplace, this.currentMessageObject.highlightedWords, this.resourcesProvider);
            if (charSequenceHighlightText != null) {
                charSequenceReplace = charSequenceHighlightText;
            }
            TextPaint textPaint = this.titlePaint;
            if (textPaint == null) {
                textPaint = Theme.chat_contextResult_titleTextPaint;
            }
            TextPaint textPaint2 = textPaint;
            StaticLayout staticLayout = new StaticLayout(TextUtils.ellipsize(charSequenceReplace, textPaint2, size - iM1146dp, TextUtils.TruncateAt.END), textPaint2, (AndroidUtilities.m1146dp(4.0f) + size) - iM1146dp, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.titleLayout = staticLayout;
            this.titleLayoutLeft = staticLayout.getLineCount() > 0 ? this.titleLayout.getLineLeft(0) : 0.0f;
            this.titleLayoutWidth = this.titleLayout.getLineCount() > 0 ? this.titleLayout.getLineWidth(0) : 0.0f;
            this.titleLayoutEmojis = AnimatedEmojiSpan.update(0, this, this.titleLayoutEmojis, this.titleLayout);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        String str = "";
        if (this.currentMessageObject.hasHighlightedWords()) {
            CharSequence charSequenceHighlightText2 = AndroidUtilities.highlightText(TextUtils.isEmpty(this.currentMessageObject.messageOwner.message) ? "" : Emoji.replaceEmoji(this.currentMessageObject.messageOwner.message.replace("\n", " ").replaceAll(" +", " ").trim(), Theme.chat_msgTextPaint.getFontMetricsInt(), false), this.currentMessageObject.highlightedWords, this.resourcesProvider);
            if (charSequenceHighlightText2 != null) {
                StaticLayout staticLayout2 = new StaticLayout(TextUtils.ellipsize(AndroidUtilities.ellipsizeCenterEnd(charSequenceHighlightText2, this.currentMessageObject.highlightedWords.get(0), size, this.captionTextPaint, Opcodes.IXOR), this.captionTextPaint, size, TextUtils.TruncateAt.END), this.captionTextPaint, size + AndroidUtilities.m1146dp(4.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                this.captionLayout = staticLayout2;
                this.captionLayoutLeft = staticLayout2.getLineCount() > 0 ? this.captionLayout.getLineLeft(0) : 0.0f;
                this.captionLayoutWidth = this.captionLayout.getLineCount() > 0 ? this.captionLayout.getLineWidth(0) : 0.0f;
            }
            this.captionLayoutEmojis = AnimatedEmojiSpan.update(0, this, this.captionLayoutEmojis, this.captionLayout);
        }
        try {
            if (this.currentMessageObject.isVoice() || this.currentMessageObject.isRoundVideo()) {
                String str2 = AndroidUtilities.formatDuration((int) this.currentMessageObject.getDuration(), false) + " • " + this.currentMessageObject.getMusicAuthor().replace('\n', ' ');
                TextPaint textPaint3 = this.viewType == 1 ? this.description2TextPaint : Theme.chat_contextResult_descriptionTextPaint;
                this.descriptionLayout = new StaticLayout(TextUtils.ellipsize(str2, textPaint3, size, TextUtils.TruncateAt.END), textPaint3, size + AndroidUtilities.m1146dp(4.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            } else {
                CharSequence charSequenceReplace2 = this.currentMessageObject.getMusicAuthor().replace('\n', ' ');
                CharSequence charSequenceHighlightText3 = AndroidUtilities.highlightText(charSequenceReplace2, this.currentMessageObject.highlightedWords, this.resourcesProvider);
                if (charSequenceHighlightText3 != null) {
                    charSequenceReplace2 = charSequenceHighlightText3;
                }
                if (this.viewType == 1) {
                    charSequenceReplace2 = new SpannableStringBuilder(charSequenceReplace2).append(' ').append((CharSequence) this.dotSpan).append(' ').append(FilteredSearchView.createFromInfoString(this.currentMessageObject, 1));
                }
                TextPaint textPaint4 = this.viewType == 1 ? this.description2TextPaint : Theme.chat_contextResult_descriptionTextPaint;
                if (this.currentMessageObject.getDuration() > 0.0d) {
                    str = " • " + LocaleController.formatShortDuration((int) Math.ceil(this.currentMessageObject.getDuration()));
                }
                this.descriptionLayout = new StaticLayout(SpannableStringBuilder.valueOf(TextUtils.ellipsize(charSequenceReplace2, textPaint4, size - textPaint4.measureText(str), TextUtils.TruncateAt.END)).append((CharSequence) str), textPaint4, size + AndroidUtilities.m1146dp(4.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            this.descriptionLayoutLeft = this.descriptionLayout.getLineCount() > 0 ? this.descriptionLayout.getLineLeft(0) : 0.0f;
            this.descriptionLayoutWidth = this.descriptionLayout.getLineCount() > 0 ? this.descriptionLayout.getLineWidth(0) : 0.0f;
            this.descriptionLayoutEmojis = AnimatedEmojiSpan.update(0, this, this.descriptionLayoutEmojis, this.descriptionLayout);
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.m1146dp(56.0f) + (this.captionLayout != null ? AndroidUtilities.m1146dp(18.0f) : 0) + (this.needDivider ? 1 : 0));
        int size2 = LocaleController.isRTL ? (View.MeasureSpec.getSize(i) - AndroidUtilities.m1146dp(8.0f)) - AndroidUtilities.m1146dp(52.0f) : AndroidUtilities.m1146dp(8.0f);
        RadialProgress2 radialProgress2 = this.radialProgress;
        int iM1146dp2 = AndroidUtilities.m1146dp(4.0f) + size2;
        this.buttonX = iM1146dp2;
        int iM1146dp3 = AndroidUtilities.m1146dp(6.0f);
        this.buttonY = iM1146dp3;
        radialProgress2.setProgressRect(iM1146dp2, iM1146dp3, size2 + AndroidUtilities.m1146dp(48.0f), AndroidUtilities.m1146dp(50.0f));
        measureChildWithMargins(this.checkBox, i, 0, i2, 0);
        if (this.captionLayout != null) {
            this.captionY = AndroidUtilities.m1146dp(29.0f);
            this.descriptionY = AndroidUtilities.m1146dp(29.0f) + AndroidUtilities.m1146dp(18.0f);
        } else {
            this.descriptionY = AndroidUtilities.m1146dp(29.0f);
        }
    }

    public void setMessageObject(MessageObject messageObject, boolean z) {
        this.needDivider = z;
        this.currentMessageObject = messageObject;
        TLRPC.Document document = messageObject.getDocument();
        TLRPC.PhotoSize closestPhotoSizeWithSize = document != null ? FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 360) : null;
        if ((closestPhotoSizeWithSize instanceof TLRPC.TL_photoSize) || (closestPhotoSizeWithSize instanceof TLRPC.TL_photoSizeProgressive)) {
            this.radialProgress.setImageOverlay(closestPhotoSizeWithSize, document, messageObject);
        } else {
            String artworkUrl = messageObject.getArtworkUrl(true);
            if (!TextUtils.isEmpty(artworkUrl)) {
                this.radialProgress.setImageOverlay(artworkUrl);
            } else {
                this.radialProgress.setImageOverlay(null, null, null);
            }
        }
        updateButtonState(false, false);
        requestLayout();
    }

    public void setChecked(boolean z, boolean z2) {
        if (this.checkBox.getVisibility() != 0) {
            this.checkBox.setVisibility(0);
        }
        this.checkBox.setChecked(z, z2);
    }

    public void setCheckForButtonPress(boolean z) {
        this.checkForButtonPress = z;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.radialProgress.onAttachedToWindow();
        updateButtonState(false, false);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.messagePlayingDidStart);
        this.titleLayoutEmojis = AnimatedEmojiSpan.update(0, this, this.titleLayoutEmojis, this.titleLayout);
        this.descriptionLayoutEmojis = AnimatedEmojiSpan.update(0, this, this.descriptionLayoutEmojis, this.descriptionLayout);
        this.captionLayoutEmojis = AnimatedEmojiSpan.update(0, this, this.captionLayoutEmojis, this.captionLayout);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
        this.radialProgress.onDetachedFromWindow();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.messagePlayingDidStart);
        AnimatedEmojiSpan.release(this, this.titleLayoutEmojis);
        AnimatedEmojiSpan.release(this, this.descriptionLayoutEmojis);
        AnimatedEmojiSpan.release(this, this.captionLayoutEmojis);
    }

    public MessageObject getMessage() {
        return this.currentMessageObject;
    }

    public void initStreamingIcons() {
        this.radialProgress.initMiniIcons();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return onTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkAudioMotionEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            float r0 = r9.getX()
            int r0 = (int) r0
            float r1 = r9.getY()
            int r1 = (int) r1
            r2 = 1108344832(0x42100000, float:36.0)
            int r2 = org.telegram.messenger.AndroidUtilities.m1146dp(r2)
            int r3 = r8.miniButtonState
            r4 = 1
            r5 = 0
            if (r3 < 0) goto L32
            r3 = 1104674816(0x41d80000, float:27.0)
            int r3 = org.telegram.messenger.AndroidUtilities.m1146dp(r3)
            int r6 = r8.buttonX
            int r7 = r6 + r3
            if (r0 < r7) goto L32
            int r6 = r6 + r3
            int r6 = r6 + r2
            if (r0 > r6) goto L32
            int r6 = r8.buttonY
            int r7 = r6 + r3
            if (r1 < r7) goto L32
            int r6 = r6 + r3
            int r6 = r6 + r2
            if (r1 > r6) goto L32
            r2 = 1
            goto L33
        L32:
            r2 = 0
        L33:
            int r3 = r9.getAction()
            if (r3 != 0) goto L68
            if (r2 == 0) goto L48
            r8.miniButtonPressed = r4
            org.telegram.ui.Components.RadialProgress2 r9 = r8.radialProgress
            r9.setPressed(r4, r4)
            r8.invalidate()
        L45:
            r9 = 1
            goto Lb6
        L48:
            boolean r9 = r8.checkForButtonPress
            if (r9 == 0) goto Lb5
            org.telegram.ui.Components.RadialProgress2 r9 = r8.radialProgress
            android.graphics.RectF r9 = r9.getProgressRect()
            float r0 = (float) r0
            float r1 = (float) r1
            boolean r9 = r9.contains(r0, r1)
            if (r9 == 0) goto Lb5
            r8.requestDisallowInterceptTouchEvent(r4)
            r8.buttonPressed = r4
            org.telegram.ui.Components.RadialProgress2 r9 = r8.radialProgress
            r9.setPressed(r4, r5)
            r8.invalidate()
            goto L45
        L68:
            int r0 = r9.getAction()
            if (r0 != r4) goto L91
            boolean r9 = r8.miniButtonPressed
            if (r9 == 0) goto L7e
            r8.miniButtonPressed = r5
            r8.playSoundEffect(r5)
            r8.didPressedMiniButton(r4)
            r8.invalidate()
            goto L8d
        L7e:
            boolean r9 = r8.buttonPressed
            if (r9 == 0) goto L8d
            r8.buttonPressed = r5
            r8.playSoundEffect(r5)
            r8.didPressedButton()
            r8.invalidate()
        L8d:
            r8.requestDisallowInterceptTouchEvent(r5)
            goto Lb5
        L91:
            int r0 = r9.getAction()
            r1 = 3
            if (r0 != r1) goto La3
            r8.requestDisallowInterceptTouchEvent(r5)
            r8.miniButtonPressed = r5
            r8.buttonPressed = r5
            r8.invalidate()
            goto Lb5
        La3:
            int r9 = r9.getAction()
            r0 = 2
            if (r9 != r0) goto Lb5
            if (r2 != 0) goto Lb5
            boolean r9 = r8.miniButtonPressed
            if (r9 == 0) goto Lb5
            r8.miniButtonPressed = r5
            r8.invalidate()
        Lb5:
            r9 = 0
        Lb6:
            org.telegram.ui.Components.RadialProgress2 r0 = r8.radialProgress
            boolean r1 = r8.miniButtonPressed
            r0.setPressed(r1, r4)
            if (r9 != 0) goto Lc5
            boolean r9 = r8.buttonPressed
            if (r9 == 0) goto Lc4
            goto Lc5
        Lc4:
            return r5
        Lc5:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.SharedAudioCell.checkAudioMotionEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.currentMessageObject == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean zCheckAudioMotionEvent = checkAudioMotionEvent(motionEvent);
        if (motionEvent.getAction() != 3) {
            return zCheckAudioMotionEvent;
        }
        this.miniButtonPressed = false;
        this.buttonPressed = false;
        this.radialProgress.setPressed(false, false);
        this.radialProgress.setPressed(this.miniButtonPressed, true);
        return false;
    }

    private void didPressedMiniButton(boolean z) {
        int i = this.miniButtonState;
        if (i == 0) {
            this.miniButtonState = 1;
            this.radialProgress.setProgress(0.0f, false);
            FileLoader.getInstance(this.currentAccount).loadFile(this.currentMessageObject.getDocument(), this.currentMessageObject, 1, 0);
            this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
            invalidate();
            return;
        }
        if (i == 1) {
            if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                MediaController.getInstance().cleanupPlayer(true, true);
            }
            this.miniButtonState = 0;
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.currentMessageObject.getDocument());
            this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
            invalidate();
        }
    }

    public void didPressedButton() {
        int i = this.buttonState;
        if (i == 0) {
            if (this.miniButtonState == 0) {
                this.currentMessageObject.putInDownloadsStore = true;
                FileLoader.getInstance(this.currentAccount).loadFile(this.currentMessageObject.getDocument(), this.currentMessageObject, 1, 0);
            }
            if (needPlayMessage(this.currentMessageObject)) {
                if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                    this.miniButtonState = 1;
                    this.radialProgress.setProgress(0.0f, false);
                    this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                }
                this.buttonState = 1;
                this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                invalidate();
                return;
            }
            return;
        }
        if (i == 1) {
            if (MediaController.getInstance().lambda$startAudioAgain$7(this.currentMessageObject)) {
                this.buttonState = 0;
                this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                invalidate();
                return;
            }
            return;
        }
        if (i == 2) {
            this.radialProgress.setProgress(0.0f, false);
            this.currentMessageObject.putInDownloadsStore = true;
            FileLoader.getInstance(this.currentAccount).loadFile(this.currentMessageObject.getDocument(), this.currentMessageObject, 1, 0);
            this.buttonState = 4;
            this.radialProgress.setIcon(getIconForCurrentState(), false, true);
            invalidate();
            return;
        }
        if (i == 4) {
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.currentMessageObject.getDocument());
            this.buttonState = 2;
            this.radialProgress.setIcon(getIconForCurrentState(), false, true);
            invalidate();
        }
    }

    private int getMiniIconForCurrentState() {
        int i = this.miniButtonState;
        if (i < 0) {
            return 4;
        }
        return i == 0 ? 2 : 3;
    }

    private int getIconForCurrentState() {
        int i = this.buttonState;
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        return i == 4 ? 3 : 0;
    }

    public void updateButtonState(boolean z, boolean z2) {
        String fileName = this.currentMessageObject.getFileName();
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        boolean z3 = messageObject.attachPathExists || messageObject.mediaExists;
        if (SharedConfig.streamMedia && messageObject.isMusic() && ((int) this.currentMessageObject.getDialogId()) != 0) {
            this.hasMiniProgress = z3 ? 1 : 2;
            z3 = true;
        } else {
            this.hasMiniProgress = 0;
            this.miniButtonState = -1;
        }
        if (this.hasMiniProgress == 0) {
            if (z3) {
                DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
                boolean zIsPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
                if (!zIsPlayingMessage || (zIsPlayingMessage && MediaController.getInstance().isMessagePaused())) {
                    this.buttonState = 0;
                } else {
                    this.buttonState = 1;
                }
                this.radialProgress.setProgress(1.0f, z2);
                this.radialProgress.setIcon(getIconForCurrentState(), z, z2);
                invalidate();
                return;
            }
            DownloadController.getInstance(this.currentAccount).addLoadingFileObserver(fileName, this.currentMessageObject, this);
            if (!FileLoader.getInstance(this.currentAccount).isLoadingFile(fileName)) {
                this.buttonState = 2;
                this.radialProgress.setProgress(0.0f, z2);
            } else {
                this.buttonState = 4;
                Float fileProgress = ImageLoader.getInstance().getFileProgress(fileName);
                if (fileProgress != null) {
                    this.radialProgress.setProgress(fileProgress.floatValue(), z2);
                } else {
                    this.radialProgress.setProgress(0.0f, z2);
                }
            }
            this.radialProgress.setIcon(getIconForCurrentState(), z, z2);
            invalidate();
            return;
        }
        this.radialProgress.setMiniProgressBackgroundColor(getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outLoader : Theme.key_chat_inLoader));
        boolean zIsPlayingMessage2 = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
        if (!zIsPlayingMessage2 || (zIsPlayingMessage2 && MediaController.getInstance().isMessagePaused())) {
            this.buttonState = 0;
        } else {
            this.buttonState = 1;
        }
        this.radialProgress.setIcon(getIconForCurrentState(), z, z2);
        if (this.hasMiniProgress == 1) {
            DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
            this.miniButtonState = -1;
            this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), z, z2);
            return;
        }
        DownloadController.getInstance(this.currentAccount).addLoadingFileObserver(fileName, this.currentMessageObject, this);
        if (!FileLoader.getInstance(this.currentAccount).isLoadingFile(fileName)) {
            this.miniButtonState = 0;
            this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), z, z2);
            return;
        }
        this.miniButtonState = 1;
        this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), z, z2);
        Float fileProgress2 = ImageLoader.getInstance().getFileProgress(fileName);
        if (fileProgress2 != null) {
            this.radialProgress.setProgress(fileProgress2.floatValue(), z2);
        } else {
            this.radialProgress.setProgress(0.0f, z2);
        }
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onFailedDownload(String str, boolean z) {
        updateButtonState(true, z);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onSuccessDownload(String str) {
        this.radialProgress.setProgress(1.0f, true);
        updateButtonState(false, true);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressDownload(String str, long j, long j2) {
        this.radialProgress.setProgress(Math.min(1.0f, j / j2), true);
        if (this.hasMiniProgress != 0) {
            if (this.miniButtonState != 1) {
                updateButtonState(false, true);
            }
        } else if (this.buttonState != 4) {
            updateButtonState(false, true);
        }
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public int getObserverTag() {
        return this.TAG;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setEnabled(true);
        if (this.currentMessageObject.isMusic()) {
            accessibilityNodeInfo.setText(LocaleController.formatString("AccDescrMusicInfo", C2369R.string.AccDescrMusicInfo, this.currentMessageObject.getMusicAuthor(), this.currentMessageObject.getMusicTitle()));
        } else if (this.titleLayout != null && this.descriptionLayout != null) {
            accessibilityNodeInfo.setText(((Object) this.titleLayout.getText()) + ", " + ((Object) this.descriptionLayout.getText()));
        }
        if (this.checkBox.isChecked()) {
            accessibilityNodeInfo.setCheckable(true);
            accessibilityNodeInfo.setChecked(true);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        updateButtonState(false, true);
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void setGlobalGradientView(FlickerLoadingView flickerLoadingView) {
        this.globalGradientView = flickerLoadingView;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void dispatchDraw(android.graphics.Canvas r20) {
        /*
            r19 = this;
            r0 = r19
            boolean r1 = r0.showName
            r2 = 0
            r3 = 1037726734(0x3dda740e, float:0.10666667)
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L19
            float r5 = r0.showNameProgress
            int r6 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r6 == 0) goto L19
            float r5 = r5 + r3
            r0.showNameProgress = r5
            r0.invalidate()
            goto L27
        L19:
            if (r1 != 0) goto L27
            float r1 = r0.showNameProgress
            int r5 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r5 == 0) goto L27
            float r1 = r1 - r3
            r0.showNameProgress = r1
            r0.invalidate()
        L27:
            float r1 = r0.showNameProgress
            float r1 = org.telegram.messenger.Utilities.clamp(r1, r4, r2)
            r0.showNameProgress = r1
            float r1 = r0.enterAlpha
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 == 0) goto L94
            org.telegram.ui.Components.FlickerLoadingView r1 = r0.globalGradientView
            if (r1 == 0) goto L94
            int r1 = r0.getMeasuredWidth()
            float r8 = (float) r1
            int r1 = r0.getMeasuredHeight()
            float r9 = (float) r1
            float r1 = r0.enterAlpha
            float r4 = r4 - r1
            r1 = 1132396544(0x437f0000, float:255.0)
            float r4 = r4 * r1
            int r10 = (int) r4
            r11 = 31
            r6 = 0
            r7 = 0
            r5 = r20
            r5.saveLayerAlpha(r6, r7, r8, r9, r10, r11)
            org.telegram.ui.Components.FlickerLoadingView r2 = r0.globalGradientView
            r3 = 4
            r2.setViewType(r3)
            org.telegram.ui.Components.FlickerLoadingView r2 = r0.globalGradientView
            r2.updateColors()
            org.telegram.ui.Components.FlickerLoadingView r2 = r0.globalGradientView
            r2.updateGradient()
            org.telegram.ui.Components.FlickerLoadingView r2 = r0.globalGradientView
            r2.draw(r5)
            r5.restore()
            int r2 = r0.getMeasuredWidth()
            float r15 = (float) r2
            int r2 = r0.getMeasuredHeight()
            float r2 = (float) r2
            float r3 = r0.enterAlpha
            float r3 = r3 * r1
            int r1 = (int) r3
            r18 = 31
            r13 = 0
            r14 = 0
            r17 = r1
            r16 = r2
            r12 = r5
            r12.saveLayerAlpha(r13, r14, r15, r16, r17, r18)
            r19.drawInternal(r20)
            super.dispatchDraw(r20)
            r19.drawReorder(r20)
            r20.restore()
            return
        L94:
            r19.drawInternal(r20)
            r19.drawReorder(r20)
            super.dispatchDraw(r20)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.SharedAudioCell.dispatchDraw(android.graphics.Canvas):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawReorder(android.graphics.Canvas r8) {
        /*
            r7 = this;
            boolean r0 = r7.showReorderIcon
            r1 = 0
            if (r0 != 0) goto Ld
            float r2 = r7.showReorderIconProgress
            int r2 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r2 == 0) goto Lc
            goto Ld
        Lc:
            return
        Ld:
            r2 = 1037726734(0x3dda740e, float:0.10666667)
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r0 == 0) goto L21
            float r4 = r7.showReorderIconProgress
            int r5 = (r4 > r3 ? 1 : (r4 == r3 ? 0 : -1))
            if (r5 == 0) goto L21
            float r4 = r4 + r2
            r7.showReorderIconProgress = r4
            r7.invalidate()
            goto L2f
        L21:
            if (r0 != 0) goto L2f
            float r0 = r7.showReorderIconProgress
            int r4 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r4 == 0) goto L2f
            float r0 = r0 - r2
            r7.showReorderIconProgress = r0
            r7.invalidate()
        L2f:
            float r0 = r7.showReorderIconProgress
            float r0 = org.telegram.messenger.Utilities.clamp(r0, r3, r1)
            r7.showReorderIconProgress = r0
            int r0 = r7.getMeasuredWidth()
            r1 = 1094713344(0x41400000, float:12.0)
            int r1 = org.telegram.messenger.AndroidUtilities.m1146dp(r1)
            int r0 = r0 - r1
            android.graphics.drawable.Drawable r1 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            int r1 = r1.getIntrinsicWidth()
            int r0 = r0 - r1
            int r1 = r7.getMeasuredHeight()
            android.graphics.drawable.Drawable r2 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            int r2 = r2.getIntrinsicHeight()
            int r1 = r1 - r2
            int r1 = r1 >> 1
            r8.save()
            float r2 = r7.showReorderIconProgress
            float r3 = (float) r0
            android.graphics.drawable.Drawable r4 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            int r4 = r4.getIntrinsicWidth()
            float r4 = (float) r4
            r5 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r5
            float r3 = r3 + r4
            float r4 = (float) r1
            android.graphics.drawable.Drawable r6 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            int r6 = r6.getIntrinsicHeight()
            float r6 = (float) r6
            float r6 = r6 / r5
            float r4 = r4 + r6
            r8.scale(r2, r2, r3, r4)
            android.graphics.drawable.Drawable r2 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            int r3 = r2.getIntrinsicWidth()
            int r3 = r3 + r0
            android.graphics.drawable.Drawable r4 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            int r4 = r4.getIntrinsicHeight()
            int r4 = r4 + r1
            r2.setBounds(r0, r1, r3, r4)
            android.graphics.drawable.Drawable r0 = org.telegram.p023ui.ActionBar.Theme.dialogs_reorderDrawable
            r0.draw(r8)
            r8.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.SharedAudioCell.drawReorder(android.graphics.Canvas):void");
    }

    private void drawInternal(Canvas canvas) {
        StaticLayout staticLayout;
        if (this.viewType == 1) {
            this.description2TextPaint.setColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText3));
        }
        int width = 0;
        if (this.dateLayout != null) {
            canvas.save();
            canvas.translate(AndroidUtilities.m1146dp(LocaleController.isRTL ? 24.0f : AndroidUtilities.leftBaseline) + (LocaleController.isRTL ? 0 : this.dateLayoutX), this.titleY);
            this.dateLayout.draw(canvas);
            canvas.restore();
        }
        if (this.titleLayout != null) {
            int alpha = Theme.chat_contextResult_titleTextPaint.getAlpha();
            float f = this.showNameProgress;
            if (f != 1.0f) {
                Theme.chat_contextResult_titleTextPaint.setAlpha((int) (alpha * f));
            }
            canvas.save();
            int iM1146dp = AndroidUtilities.m1146dp(LocaleController.isRTL ? 24.0f : AndroidUtilities.leftBaseline);
            if (LocaleController.isRTL && (staticLayout = this.dateLayout) != null) {
                width = staticLayout.getWidth() + AndroidUtilities.m1146dp(LocaleController.isRTL ? 12.0f : 4.0f);
            }
            canvas.translate(((iM1146dp + width) + (LocaleController.isRTL ? this.titleLayout.getWidth() - this.titleLayoutWidth : 0.0f)) - this.titleLayoutLeft, this.titleY);
            this.titleLayout.draw(canvas);
            AnimatedEmojiSpan.drawAnimatedEmojis(canvas, this.titleLayout, this.titleLayoutEmojis, 0.0f, null, 0.0f, 0.0f, 0.0f, 1.0f);
            canvas.restore();
            if (this.showNameProgress != 1.0f) {
                Theme.chat_contextResult_titleTextPaint.setAlpha(alpha);
            }
        }
        if (this.captionLayout != null) {
            this.captionTextPaint.setColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            canvas.save();
            canvas.translate((AndroidUtilities.m1146dp(LocaleController.isRTL ? 24.0f : AndroidUtilities.leftBaseline) + (LocaleController.isRTL ? this.captionLayout.getWidth() - this.captionLayoutWidth : 0.0f)) - this.captionLayoutLeft, this.captionY);
            this.captionLayout.draw(canvas);
            canvas.restore();
        }
        if (this.descriptionLayout != null) {
            Theme.chat_contextResult_descriptionTextPaint.setColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText2));
            int alpha2 = Theme.chat_contextResult_descriptionTextPaint.getAlpha();
            float f2 = this.showNameProgress;
            if (f2 != 1.0f) {
                Theme.chat_contextResult_descriptionTextPaint.setAlpha((int) (alpha2 * f2));
            }
            canvas.save();
            canvas.translate((AndroidUtilities.m1146dp(LocaleController.isRTL ? 24.0f : AndroidUtilities.leftBaseline) + (LocaleController.isRTL ? this.descriptionLayout.getWidth() - this.descriptionLayoutWidth : 0.0f)) - this.descriptionLayoutLeft, this.descriptionY);
            this.descriptionLayout.draw(canvas);
            AnimatedEmojiSpan.drawAnimatedEmojis(canvas, this.descriptionLayout, this.descriptionLayoutEmojis, 0.0f, null, 0.0f, 0.0f, 0.0f, 1.0f);
            canvas.restore();
            if (this.showNameProgress != 1.0f) {
                Theme.chat_contextResult_descriptionTextPaint.setAlpha(alpha2);
            }
        }
        this.radialProgress.setProgressColor(getThemedColor(this.buttonPressed ? Theme.key_chat_inAudioSelectedProgress : Theme.key_chat_inAudioProgress));
        this.radialProgress.setOverlayImageAlpha(this.showNameProgress);
        this.radialProgress.draw(canvas);
        if (this.needDivider) {
            if (LocaleController.isRTL) {
                canvas.drawLine(0.0f, getHeight() - 1, (getWidth() - AndroidUtilities.m1146dp(72.0f)) - getPaddingRight(), getHeight() - 1, Theme.getThemePaint("paintDivider", this.resourcesProvider));
            } else {
                canvas.drawLine(AndroidUtilities.m1146dp(72.0f), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, Theme.getThemePaint("paintDivider", this.resourcesProvider));
            }
        }
    }

    public void setEnterAnimationAlpha(float f) {
        if (this.enterAlpha != f) {
            this.enterAlpha = f;
            invalidate();
        }
    }

    public void showReorderIcon(boolean z, boolean z2) {
        if (this.showReorderIcon == z) {
            return;
        }
        this.showReorderIcon = z;
        if (!z2) {
            this.showReorderIconProgress = z ? 1.0f : 0.0f;
        }
        invalidate();
    }

    public void showName(boolean z, boolean z2) {
        if (!z2) {
            this.showNameProgress = z ? 1.0f : 0.0f;
        }
        if (this.showName == z) {
            return;
        }
        this.showName = z;
        invalidate();
    }

    public static final class Factory extends UItem.UItemFactory {
        static {
            UItem.UItemFactory.setup(new Factory());
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public SharedAudioCell createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
            SharedAudioCell sharedAudioCell = new SharedAudioCell(context, resourcesProvider);
            sharedAudioCell.setPadding(AndroidUtilities.m1146dp(12.0f), 0, AndroidUtilities.m1146dp(12.0f), 0);
            return sharedAudioCell;
        }

        @Override // org.telegram.ui.Components.UItem.UItemFactory
        public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
            SharedAudioCell sharedAudioCell = (SharedAudioCell) view;
            sharedAudioCell.setMessageObject((MessageObject) uItem.object, z);
            sharedAudioCell.setChecked(uItem.checked, false);
        }

        /* renamed from: as */
        public static UItem m1249as(MessageObject messageObject) {
            UItem uItemOfFactory = UItem.ofFactory(Factory.class);
            uItemOfFactory.object = messageObject;
            return uItemOfFactory;
        }
    }
}
