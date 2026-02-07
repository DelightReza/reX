package org.telegram.p023ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Keep;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import me.vkryl.android.animator.ListAnimator;
import me.vkryl.android.animator.ReplaceAnimator;
import me.vkryl.core.lambda.Destroyable;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.voip.GroupCallMessage;
import org.telegram.messenger.voip.GroupCallMessagesController;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.p023ui.ActionBar.ActionBarMenu;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.ActionBarMenuSlider;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.AudioPlayerAlert;
import org.telegram.p023ui.Components.SharingLocationsAlert;
import org.telegram.p023ui.Components.conference.message.GroupCallMessageCell;
import org.telegram.p023ui.Components.voip.CellFlickerDrawable;
import org.telegram.p023ui.Components.voip.VoIPHelper;
import org.telegram.p023ui.DialogsActivity;
import org.telegram.p023ui.GroupCallActivity;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.LocationActivity;
import org.telegram.p023ui.Stories.LivePlayer;
import org.telegram.p023ui.Stories.StoryViewer;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_phone;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes3.dex */
public class FragmentContextView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate, VoIPService.StateListener, GroupCallMessagesController.CallMessageListener {
    private static final float[] speeds = {0.5f, 1.0f, 1.2f, 1.5f, 1.7f, 2.0f};
    private final int account;
    private FragmentContextView additionalContextView;
    private AnimatorSet animatorSet;
    private View applyingView;
    private AvatarsImageView avatars;
    private final ReplaceAnimator callMessagesAnimator;
    private ChatActivityInterface chatActivity;
    private boolean checkCallAfterAnimation;
    private boolean checkImportAfterAnimation;
    private boolean checkLiveStoryAfterAnimation;
    private final Runnable checkLocationRunnable;
    private boolean checkPlayerAfterAnimation;
    private ImageView closeButton;
    float collapseProgress;
    boolean collapseTransition;
    private CoverContainer coverContainer;
    private String currentFile;
    private int currentProgress;
    private int currentStyle;
    private FragmentContextViewDelegate delegate;
    private View divider;
    boolean drawOverlay;

    /* renamed from: dx */
    private float f1882dx;

    /* renamed from: dy */
    private float f1883dy;
    float extraHeight;
    private boolean firstLocationsLoaded;
    private float firstX;
    private float firstY;
    private boolean flickOnAttach;
    private BaseFragment fragment;
    private FrameLayout frameLayout;
    private Paint gradientPaint;
    private TextPaint gradientTextPaint;
    private int gradientWidth;
    private int groupCallMessageCounter;
    private FrameLayout groupCallMessagesContainer;
    private RLottieImageView importingImageView;
    private final boolean isLocation;
    private boolean isMusic;
    private boolean isMuted;
    private final boolean isSideMenued;
    private TextView joinButton;
    private CellFlickerDrawable joinButtonFlicker;
    private int joinButtonWidth;
    private int lastLocationSharingCount;
    private MessageObject lastMessageObject;
    private long lastPlaybackClick;
    private String lastString;
    private float leftMargin;
    private LinearGradient linearGradient;
    private Matrix matrix;
    float micAmplitude;
    private RLottieImageView muteButton;
    private RLottieDrawable muteDrawable;
    private RLottieImageView nextButton;
    private AnimationNotificationsLocker notificationsLocker;
    private AnimationNotificationsLocker notificationsLocker2;
    private ButtonBounce notifyButtonBounce;
    private boolean notifyButtonEnabled;
    private final AnimatedTextView.AnimatedTextDrawable notifyText;
    private ImageView playButton;
    private PlayPauseDrawable playPauseDrawable;
    private ActionBarMenuItem playbackSpeedButton;
    private RLottieImageView prevButton;
    private final Theme.ResourcesProvider resourcesProvider;
    private boolean scheduleRunnableScheduled;
    private float secondX;
    private float secondY;
    private View selector;
    private FrameLayout silentButton;
    private ImageView silentButtonImage;
    private boolean slidingSpeed;
    float speakerAmplitude;
    private HintView speedHintView;
    private SpeedIconDrawable speedIcon;
    private ActionBarMenuItem.Item[] speedItems;
    private ActionBarMenuSlider.SpeedSlider speedSlider;
    private AudioPlayerAlert.ClippingTextViewSwitcher subtitleTextView;
    private boolean supportsCalls;
    private AudioPlayerAlert.ClippingTextViewSwitcher titleTextView;
    private int toggleGroupCallStartSubscriptionReqId;
    protected float topPadding;
    private final Runnable updateScheduleTimeRunnable;
    private boolean visible;
    boolean wasDraw;
    private boolean willBeNotified;

    /* loaded from: classes6.dex */
    public interface FragmentContextViewDelegate {
        void onAnimation(boolean z, boolean z2);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onCameraFirstFrameAvailable() {
        VoIPService.StateListener.CC.$default$onCameraFirstFrameAvailable(this);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onCameraSwitch(boolean z) {
        VoIPService.StateListener.CC.$default$onCameraSwitch(this, z);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onMediaStateUpdated(int i, int i2) {
        VoIPService.StateListener.CC.$default$onMediaStateUpdated(this, i, i2);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onScreenOnChange(boolean z) {
        VoIPService.StateListener.CC.$default$onScreenOnChange(this, z);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onSignalBarsCountChanged(int i) {
        VoIPService.StateListener.CC.$default$onSignalBarsCountChanged(this, i);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public /* synthetic */ void onVideoAvailableChange(boolean z) {
        VoIPService.StateListener.CC.$default$onVideoAvailableChange(this, z);
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onAudioSettingsChanged() {
        boolean z = VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().isMicMute();
        if (this.isMuted != z) {
            this.isMuted = z;
            this.muteDrawable.setCustomEndFrame(z ? 15 : 29);
            RLottieDrawable rLottieDrawable = this.muteDrawable;
            rLottieDrawable.setCurrentFrame(rLottieDrawable.getCustomEndFrame() - 1, false, true);
            this.muteButton.invalidate();
            Theme.getFragmentContextViewWavesDrawable().updateState(this.visible);
        }
        if (this.isMuted) {
            this.micAmplitude = 0.0f;
            Theme.getFragmentContextViewWavesDrawable().setAmplitude(0.0f);
        }
    }

    public FragmentContextView(Context context, BaseFragment baseFragment, boolean z) {
        this(context, baseFragment, null, z, null);
    }

    public FragmentContextView(Context context, BaseFragment baseFragment, boolean z, Theme.ResourcesProvider resourcesProvider) {
        this(context, baseFragment, null, z, resourcesProvider);
    }

    public FragmentContextView(Context context, BaseFragment baseFragment, View view, boolean z, Theme.ResourcesProvider resourcesProvider) {
        this(context, baseFragment, view, z, resourcesProvider, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FragmentContextView(Context context, BaseFragment baseFragment, View view, boolean z, Theme.ResourcesProvider resourcesProvider, boolean z2) {
        super(context);
        this.speedItems = new ActionBarMenuItem.Item[6];
        this.currentProgress = -1;
        this.currentStyle = -1;
        this.supportsCalls = true;
        this.notifyText = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
        this.updateScheduleTimeRunnable = new Runnable() { // from class: org.telegram.ui.Components.FragmentContextView.1
            @Override // java.lang.Runnable
            public void run() {
                String fullDuration;
                if (FragmentContextView.this.gradientTextPaint == null || !(FragmentContextView.this.fragment instanceof ChatActivity)) {
                    FragmentContextView.this.scheduleRunnableScheduled = false;
                    return;
                }
                ChatObject.Call groupCall = FragmentContextView.this.chatActivity.getGroupCall();
                if (groupCall == null || !groupCall.isScheduled()) {
                    FragmentContextView.this.notifyButtonEnabled = false;
                    FragmentContextView.this.scheduleRunnableScheduled = false;
                    return;
                }
                int currentTime = FragmentContextView.this.fragment.getConnectionsManager().getCurrentTime();
                int i = groupCall.call.schedule_date;
                int i2 = i - currentTime;
                if (i2 >= 86400) {
                    fullDuration = LocaleController.formatPluralString("Days", Math.round(i2 / 86400.0f), new Object[0]);
                } else {
                    fullDuration = AndroidUtilities.formatFullDuration(i - currentTime);
                }
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = FragmentContextView.this.notifyText;
                if (!FragmentContextView.this.willBeNotified) {
                    fullDuration = LocaleController.getString(C2369R.string.VoipChatNotify);
                }
                animatedTextDrawable.setText(fullDuration, true);
                AndroidUtilities.runOnUIThread(FragmentContextView.this.updateScheduleTimeRunnable, 1000L);
                FragmentContextView.this.frameLayout.invalidate();
            }
        };
        this.account = UserConfig.selectedAccount;
        this.lastLocationSharingCount = -1;
        this.checkLocationRunnable = new Runnable() { // from class: org.telegram.ui.Components.FragmentContextView.2
            @Override // java.lang.Runnable
            public void run() {
                FragmentContextView.this.checkLocationString();
                AndroidUtilities.runOnUIThread(FragmentContextView.this.checkLocationRunnable, 1000L);
            }
        };
        this.notificationsLocker = new AnimationNotificationsLocker();
        this.notificationsLocker2 = new AnimationNotificationsLocker(new int[]{NotificationCenter.messagesDidLoad});
        this.toggleGroupCallStartSubscriptionReqId = 0;
        this.callMessagesAnimator = new ReplaceAnimator(new ReplaceAnimator.Callback() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda16
            @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
            public /* synthetic */ boolean hasChanges(ReplaceAnimator replaceAnimator) {
                return ReplaceAnimator.Callback.CC.$default$hasChanges(this, replaceAnimator);
            }

            @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
            public /* synthetic */ boolean onApplyMetadataAnimation(ReplaceAnimator replaceAnimator, float f) {
                return ReplaceAnimator.Callback.CC.$default$onApplyMetadataAnimation(this, replaceAnimator, f);
            }

            @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
            public /* synthetic */ void onFinishMetadataAnimation(ReplaceAnimator replaceAnimator, boolean z3) {
                ReplaceAnimator.Callback.CC.$default$onFinishMetadataAnimation(this, replaceAnimator, z3);
            }

            @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
            public /* synthetic */ void onForceApplyChanges(ReplaceAnimator replaceAnimator) {
                ReplaceAnimator.Callback.CC.$default$onForceApplyChanges(this, replaceAnimator);
            }

            @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
            public final void onItemChanged(ReplaceAnimator replaceAnimator) {
                this.f$0.onItemChanged(replaceAnimator);
            }

            @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
            public /* synthetic */ void onPrepareMetadataAnimation(ReplaceAnimator replaceAnimator) {
                ReplaceAnimator.Callback.CC.$default$onPrepareMetadataAnimation(this, replaceAnimator);
            }
        }, CubicBezierInterpolator.EASE_OUT_QUINT, 450L);
        this.groupCallMessageCounter = 0;
        this.resourcesProvider = resourcesProvider;
        this.isSideMenued = z2;
        this.fragment = baseFragment;
        if (baseFragment instanceof ChatActivityInterface) {
            this.chatActivity = (ChatActivityInterface) baseFragment;
        }
        this.applyingView = view;
        this.visible = true;
        this.isLocation = z;
        if (view == null) {
            ((ViewGroup) baseFragment.getFragmentView()).setClipToPadding(false);
        }
        setTag(1);
    }

    public void setSupportsCalls(boolean z) {
        this.supportsCalls = z;
    }

    public void setDelegate(FragmentContextViewDelegate fragmentContextViewDelegate) {
        this.delegate = fragmentContextViewDelegate;
    }

    private void checkCreateView() {
        if (this.frameLayout != null) {
            return;
        }
        final Context context = getContext();
        BlurredFrameLayout blurredFrameLayout = new BlurredFrameLayout(context, this.fragment.getFragmentView() instanceof SizeNotifierFrameLayout ? (SizeNotifierFrameLayout) this.fragment.getFragmentView() : null) { // from class: org.telegram.ui.Components.FragmentContextView.3
            private final RectF notifyButtonRect = new RectF();

            @Override // android.view.View
            public void invalidate() {
                super.invalidate();
                if (FragmentContextView.this.avatars == null || FragmentContextView.this.avatars.getVisibility() != 0) {
                    return;
                }
                FragmentContextView.this.avatars.invalidate();
            }

            @Override // org.telegram.p023ui.Components.BlurredFrameLayout, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                float f;
                super.dispatchDraw(canvas);
                if (FragmentContextView.this.currentStyle == 4 && FragmentContextView.this.notifyButtonEnabled) {
                    int iCeil = ((int) Math.ceil(FragmentContextView.this.notifyText.getCurrentWidth())) + AndroidUtilities.m1146dp(24.0f);
                    if (iCeil != FragmentContextView.this.gradientWidth) {
                        FragmentContextView.this.linearGradient = new LinearGradient(0.0f, 0.0f, iCeil * 1.7f, 0.0f, new int[]{-10187532, -7575089, -2860679, -2860679}, new float[]{0.0f, 0.294f, 0.588f, 1.0f}, Shader.TileMode.CLAMP);
                        FragmentContextView.this.gradientPaint.setShader(FragmentContextView.this.linearGradient);
                        FragmentContextView.this.gradientWidth = iCeil;
                    }
                    ChatObject.Call groupCall = FragmentContextView.this.chatActivity.getGroupCall();
                    if (FragmentContextView.this.fragment == null || groupCall == null || !groupCall.isScheduled()) {
                        f = 0.0f;
                    } else {
                        long currentTimeMillis = (groupCall.call.schedule_date * 1000) - FragmentContextView.this.fragment.getConnectionsManager().getCurrentTimeMillis();
                        f = currentTimeMillis >= 0 ? currentTimeMillis < 5000 ? 1.0f - (currentTimeMillis / 5000.0f) : 0.0f : 1.0f;
                        if (currentTimeMillis < 6000) {
                            invalidate();
                        }
                    }
                    FragmentContextView.this.matrix.reset();
                    FragmentContextView.this.matrix.postTranslate((-FragmentContextView.this.gradientWidth) * 0.7f * f, 0.0f);
                    FragmentContextView.this.linearGradient.setLocalMatrix(FragmentContextView.this.matrix);
                    int measuredWidth = (getMeasuredWidth() - iCeil) - AndroidUtilities.m1146dp(10.0f);
                    float f2 = measuredWidth;
                    float fM1146dp = AndroidUtilities.m1146dp(10.0f);
                    this.notifyButtonRect.set(f2, fM1146dp, measuredWidth + iCeil, r2 + AndroidUtilities.m1146dp(28.0f));
                    canvas.save();
                    float scale = FragmentContextView.this.notifyButtonBounce.getScale(0.1f);
                    canvas.scale(scale, scale, this.notifyButtonRect.centerX(), this.notifyButtonRect.centerY());
                    canvas.translate(f2, fM1146dp);
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, iCeil, AndroidUtilities.m1146dp(28.0f));
                    canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), FragmentContextView.this.gradientPaint);
                    canvas.translate(AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(6.0f));
                    FragmentContextView.this.notifyText.setBounds(0, 0, AndroidUtilities.displaySize.x, AndroidUtilities.m1146dp(16.0f));
                    FragmentContextView.this.notifyText.draw(canvas);
                    canvas.restore();
                }
                canvas.drawLine(0.0f, AndroidUtilities.m1146dp(FragmentContextView.this.getStyleHeight()) - (2.0f / AndroidUtilities.density), getMeasuredWidth(), AndroidUtilities.m1146dp(FragmentContextView.this.getStyleHeight()) - (2.0f / AndroidUtilities.density), Theme.dividerPaint);
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (FragmentContextView.this.currentStyle == 4 && FragmentContextView.this.notifyButtonEnabled && FragmentContextView.this.notifyButtonBounce != null) {
                    boolean zContains = this.notifyButtonRect.contains(motionEvent.getX(), motionEvent.getY());
                    if (motionEvent.getAction() == 0) {
                        FragmentContextView.this.notifyButtonBounce.setPressed(zContains);
                    } else if (motionEvent.getAction() == 2) {
                        if (!zContains) {
                            FragmentContextView.this.notifyButtonBounce.setPressed(false);
                        }
                    } else if (motionEvent.getAction() == 1) {
                        if (zContains) {
                            FragmentContextView.this.toggleScheduledNotify();
                        }
                        FragmentContextView.this.notifyButtonBounce.setPressed(false);
                    } else if (motionEvent.getAction() == 3) {
                        FragmentContextView.this.notifyButtonBounce.setPressed(false);
                    }
                } else if (FragmentContextView.this.notifyButtonBounce != null) {
                    FragmentContextView.this.notifyButtonBounce.setPressed(false);
                }
                return (FragmentContextView.this.notifyButtonBounce != null && FragmentContextView.this.notifyButtonBounce.isPressed()) || super.dispatchTouchEvent(motionEvent);
            }

            @Override // android.view.View
            protected boolean verifyDrawable(Drawable drawable) {
                return drawable == FragmentContextView.this.notifyText || super.verifyDrawable(drawable);
            }
        };
        this.frameLayout = blurredFrameLayout;
        this.notifyButtonBounce = new ButtonBounce(blurredFrameLayout);
        this.notifyText.setOverrideFullWidth(AndroidUtilities.displaySize.x);
        this.notifyText.setScaleProperty(0.4f);
        this.notifyText.setCallback(this.frameLayout);
        this.notifyText.setTextColor(-1);
        this.notifyText.setTextSize(AndroidUtilities.m1146dp(14.0f));
        this.notifyText.setTypeface(AndroidUtilities.bold());
        addView(this.frameLayout, LayoutHelper.createFrame(-1, 36.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        View view = new View(context);
        this.selector = view;
        this.frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f));
        int i = Theme.key_inappPlayerPlayPause;
        int themedColor = getThemedColor(i);
        int themedColor2 = getThemedColor(Theme.key_chat_topPanelLine);
        View view2 = new View(context);
        this.divider = view2;
        view2.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1146dp(6.0f), ColorUtils.setAlphaComponent(themedColor2, (int) ((Color.alpha(themedColor2) / 255.0f) * 76.0f))));
        addView(this.divider, LayoutHelper.createFrame(2, 32.0f, 51, 8.0f, 8.0f, 0.0f, 8.0f));
        CoverContainer coverContainer = new CoverContainer(context);
        this.coverContainer = coverContainer;
        addView(coverContainer, LayoutHelper.createFrame(36, 36.0f, 51, 6.0f, 6.0f, 0.0f, 6.0f));
        ImageView imageView = new ImageView(context);
        this.playButton = imageView;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView.setScaleType(scaleType);
        ImageView imageView2 = this.playButton;
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        imageView2.setColorFilter(new PorterDuffColorFilter(themedColor, mode));
        ImageView imageView3 = this.playButton;
        PlayPauseDrawable playPauseDrawable = new PlayPauseDrawable(14);
        this.playPauseDrawable = playPauseDrawable;
        imageView3.setImageDrawable(playPauseDrawable);
        int i2 = themedColor & 436207615;
        this.playButton.setBackground(Theme.createSelectorDrawable(i2, 1, AndroidUtilities.m1146dp(14.0f)));
        addView(this.playButton, LayoutHelper.createFrame(36, 36, 51));
        this.playButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$0(view3);
            }
        });
        RLottieImageView rLottieImageView = new RLottieImageView(context);
        this.nextButton = rLottieImageView;
        rLottieImageView.setScaleType(scaleType);
        this.nextButton.setAnimation(C2369R.raw.player_prev, 14, 14);
        this.nextButton.setLayerColor("Triangle 3.**", themedColor);
        this.nextButton.setLayerColor("Triangle 4.**", themedColor);
        this.nextButton.setLayerColor("Rectangle 4.**", themedColor);
        this.nextButton.setRotation(180.0f);
        this.nextButton.setBackground(Theme.createSelectorDrawable(i2, 1, AndroidUtilities.m1146dp(14.0f)));
        addView(this.nextButton, LayoutHelper.createFrame(36, 36, 51));
        this.nextButton.setContentDescription(LocaleController.getString(C2369R.string.Next));
        this.nextButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$1(view3);
            }
        });
        RLottieImageView rLottieImageView2 = new RLottieImageView(context);
        this.prevButton = rLottieImageView2;
        rLottieImageView2.setScaleType(scaleType);
        this.prevButton.setAnimation(C2369R.raw.player_prev, 14, 14);
        this.prevButton.setLayerColor("Triangle 3.**", themedColor);
        this.prevButton.setLayerColor("Triangle 4.**", themedColor);
        this.prevButton.setLayerColor("Rectangle 4.**", themedColor);
        this.prevButton.setBackground(Theme.createSelectorDrawable(i2, 1, AndroidUtilities.m1146dp(14.0f)));
        addView(this.prevButton, LayoutHelper.createFrame(36, 36, 51));
        this.prevButton.setContentDescription(LocaleController.getString(C2369R.string.Back));
        this.prevButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$2(view3);
            }
        });
        RLottieImageView rLottieImageView3 = new RLottieImageView(context);
        this.importingImageView = rLottieImageView3;
        rLottieImageView3.setScaleType(scaleType);
        this.importingImageView.setAutoRepeat(true);
        this.importingImageView.setAnimation(C2369R.raw.import_progress, 30, 30);
        this.importingImageView.setBackground(Theme.createCircleDrawable(AndroidUtilities.m1146dp(22.0f), getThemedColor(i)));
        addView(this.importingImageView, LayoutHelper.createFrame(22, 22.0f, 51, 7.0f, 7.0f, 0.0f, 0.0f));
        AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher = new AudioPlayerAlert.ClippingTextViewSwitcher(context) { // from class: org.telegram.ui.Components.FragmentContextView.4
            @Override // org.telegram.ui.Components.AudioPlayerAlert.ClippingTextViewSwitcher
            protected TextView createTextView() {
                TextView textView = new TextView(context);
                textView.setMaxLines(1);
                textView.setLines(1);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setTypeface(AndroidUtilities.bold());
                textView.setTextSize(1, 15.0f);
                textView.setGravity(19);
                if (FragmentContextView.this.currentStyle == 2) {
                    textView.setTypeface(Typeface.DEFAULT);
                    return textView;
                }
                if (FragmentContextView.this.currentStyle == 4 || FragmentContextView.this.currentStyle == 0) {
                    textView.setGravity(51);
                    FragmentContextView fragmentContextView = FragmentContextView.this;
                    textView.setTextColor(fragmentContextView.getThemedColor(fragmentContextView.currentStyle == 0 ? Theme.key_player_actionBarTitle : Theme.key_inappPlayerPerformer));
                    return textView;
                }
                if (FragmentContextView.this.currentStyle != 1 && FragmentContextView.this.currentStyle != 3) {
                    return textView;
                }
                textView.setTextColor(FragmentContextView.this.getThemedColor(Theme.key_returnToCallText));
                textView.setTextSize(1, 14.0f);
                return textView;
            }
        };
        this.titleTextView = clippingTextViewSwitcher;
        addView(clippingTextViewSwitcher, LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, 0.0f, (this.isSideMenued ? 64 : 0) + 36, 0.0f));
        AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher2 = new AudioPlayerAlert.ClippingTextViewSwitcher(context) { // from class: org.telegram.ui.Components.FragmentContextView.5
            @Override // org.telegram.ui.Components.AudioPlayerAlert.ClippingTextViewSwitcher
            protected TextView createTextView() {
                TextView textView = new TextView(context);
                textView.setMaxLines(1);
                textView.setLines(1);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setGravity(3);
                textView.setTextSize(1, 14.0f);
                textView.setTextColor(FragmentContextView.this.getThemedColor(Theme.key_inappPlayerClose));
                return textView;
            }
        };
        this.subtitleTextView = clippingTextViewSwitcher2;
        addView(clippingTextViewSwitcher2, LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, 10.0f, (this.isSideMenued ? 64 : 0) + 36, 0.0f));
        CellFlickerDrawable cellFlickerDrawable = new CellFlickerDrawable();
        this.joinButtonFlicker = cellFlickerDrawable;
        cellFlickerDrawable.setProgress(1.0f);
        this.joinButtonFlicker.repeatEnabled = false;
        TextView textView = new TextView(context) { // from class: org.telegram.ui.Components.FragmentContextView.6
            @Override // android.view.View
            public void draw(Canvas canvas) {
                super.draw(canvas);
                int iM1146dp = AndroidUtilities.m1146dp(1.0f);
                RectF rectF = AndroidUtilities.rectTmp;
                float f = iM1146dp;
                rectF.set(f, f, getWidth() - iM1146dp, getHeight() - iM1146dp);
                FragmentContextView.this.joinButtonFlicker.draw(canvas, rectF, AndroidUtilities.m1146dp(16.0f), this);
            }

            @Override // android.view.View
            protected void onSizeChanged(int i3, int i4, int i5, int i6) {
                super.onSizeChanged(i3, i4, i5, i6);
                FragmentContextView.this.joinButtonFlicker.setParentWidth(getWidth());
            }

            @Override // android.widget.TextView, android.view.View
            protected void onMeasure(int i3, int i4) {
                super.onMeasure(i3, i4);
                updateJoinButtonWidth(getMeasuredWidth());
            }

            @Override // android.view.View
            public void setVisibility(int i3) {
                super.setVisibility(i3);
                if (i3 != 0) {
                    updateJoinButtonWidth(0);
                    FragmentContextView.this.joinButtonWidth = 0;
                }
            }

            private void updateJoinButtonWidth(int i3) {
                if (FragmentContextView.this.joinButtonWidth != i3) {
                    FragmentContextView.this.titleTextView.setPadding(FragmentContextView.this.titleTextView.getPaddingLeft(), FragmentContextView.this.titleTextView.getPaddingTop(), (FragmentContextView.this.titleTextView.getPaddingRight() - FragmentContextView.this.joinButtonWidth) + i3, FragmentContextView.this.titleTextView.getPaddingBottom());
                    FragmentContextView.this.joinButtonWidth = i3;
                }
            }
        };
        this.joinButton = textView;
        textView.setText(LocaleController.getString(C2369R.string.VoipChatJoin));
        this.joinButton.setTextColor(getThemedColor(Theme.key_featuredStickers_buttonText));
        this.joinButton.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(16.0f), getThemedColor(Theme.key_featuredStickers_addButton), getThemedColor(Theme.key_featuredStickers_addButtonPressed)));
        this.joinButton.setTextSize(1, 14.0f);
        this.joinButton.setTypeface(AndroidUtilities.bold());
        this.joinButton.setGravity(17);
        this.joinButton.setPadding(AndroidUtilities.m1146dp(14.0f), 0, AndroidUtilities.m1146dp(14.0f), 0);
        addView(this.joinButton, LayoutHelper.createFrame(-2, 28.0f, 53, 0.0f, 10.0f, 14.0f, 0.0f));
        this.joinButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$3(view3);
            }
        });
        if (this.flickOnAttach) {
            startJoinFlickerAnimation();
        }
        this.silentButton = new FrameLayout(context);
        ImageView imageView4 = new ImageView(context);
        this.silentButtonImage = imageView4;
        imageView4.setImageResource(C2369R.drawable.msg_mute);
        ImageView imageView5 = this.silentButtonImage;
        int i3 = Theme.key_inappPlayerClose;
        imageView5.setColorFilter(new PorterDuffColorFilter(getThemedColor(i3), mode));
        this.silentButton.addView(this.silentButtonImage, LayoutHelper.createFrame(20, 20, 17));
        this.silentButton.setBackground(Theme.createSelectorDrawable(getThemedColor(i3) & 436207615, 1, AndroidUtilities.m1146dp(14.0f)));
        this.silentButton.setContentDescription(LocaleController.getString(C2369R.string.Unmute));
        this.silentButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                MediaController.getInstance().updateSilent(false);
            }
        });
        this.silentButton.setVisibility(8);
        addView(this.silentButton, LayoutHelper.createFrame(36, 36.0f, 53, 0.0f, 0.0f, 36.0f, 0.0f));
        if (!this.isLocation) {
            createPlaybackSpeedButton();
        }
        AvatarsImageView avatarsImageView = new AvatarsImageView(context, false);
        this.avatars = avatarsImageView;
        avatarsImageView.setAvatarsTextSize(AndroidUtilities.m1146dp(21.0f));
        this.avatars.setDelegate(new Runnable() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkCreateView$5();
            }
        });
        this.avatars.setVisibility(8);
        addView(this.avatars, LayoutHelper.createFrame(108, 36, 51));
        this.muteDrawable = new RLottieDrawable(C2369R.raw.voice_muted, "" + C2369R.raw.voice_muted, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(20.0f), true, null);
        C38947 c38947 = new C38947(context);
        this.muteButton = c38947;
        c38947.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_returnToCallText), mode));
        this.muteButton.setBackground(Theme.createSelectorDrawable(getThemedColor(i3) & 436207615, 1, AndroidUtilities.m1146dp(14.0f)));
        this.muteButton.setAnimation(this.muteDrawable);
        this.muteButton.setScaleType(scaleType);
        this.muteButton.setVisibility(8);
        addView(this.muteButton, LayoutHelper.createFrame(36, 36.0f, 53, 0.0f, 0.0f, 2.0f, 0.0f));
        this.muteButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$6(view3);
            }
        });
        ImageView imageView6 = new ImageView(context);
        this.closeButton = imageView6;
        imageView6.setImageResource(C2369R.drawable.miniplayer_close);
        this.closeButton.setColorFilter(new PorterDuffColorFilter(getThemedColor(i3), mode));
        this.closeButton.setBackground(Theme.createSelectorDrawable(getThemedColor(i3) & 436207615, 1, AndroidUtilities.m1146dp(14.0f)));
        this.closeButton.setScaleType(scaleType);
        addView(this.closeButton, LayoutHelper.createFrame(36, 36.0f, 53, 0.0f, 0.0f, 2.0f, 0.0f));
        this.closeButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$8(view3);
            }
        });
        setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda14
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view3) {
                return this.f$0.lambda$checkCreateView$9(view3);
            }
        });
        setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda15
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                return this.f$0.lambda$checkCreateView$10(view3, motionEvent);
            }
        });
        FrameLayout frameLayout = new FrameLayout(getContext()) { // from class: org.telegram.ui.Components.FragmentContextView.8
            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                return false;
            }
        };
        this.groupCallMessagesContainer = frameLayout;
        addView(frameLayout, LayoutHelper.createFrame(-1, -2.0f, 48, 96.0f, 3.0f, 96.0f, 0.0f));
        setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$checkCreateView$12(view3);
            }
        });
        setLeftMargin(this.leftMargin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$0(View view) {
        if (this.currentStyle == 0) {
            if (MediaController.getInstance().isMessagePaused()) {
                MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
            } else {
                MediaController.getInstance().lambda$startAudioAgain$7(MediaController.getInstance().getPlayingMessageObject());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$1(View view) {
        if (this.currentStyle == 0) {
            MediaController.getInstance().playNextMessage();
            this.nextButton.setProgress(0.0f);
            this.nextButton.playAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$2(View view) {
        if (this.currentStyle == 0) {
            MediaController.getInstance().playPreviousMessage();
            this.prevButton.setProgress(0.0f);
            this.prevButton.playAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$3(View view) {
        callOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$5() {
        updateAvatars(true);
    }

    /* renamed from: org.telegram.ui.Components.FragmentContextView$7 */
    /* loaded from: classes6.dex */
    class C38947 extends RLottieImageView {
        private final Runnable pressRunnable;
        boolean pressed;
        boolean scheduled;
        private final Runnable toggleMicRunnable;

        C38947(Context context) {
            super(context);
            this.toggleMicRunnable = new Runnable() { // from class: org.telegram.ui.Components.FragmentContextView$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$$0();
                }
            };
            this.pressRunnable = new Runnable() { // from class: org.telegram.ui.Components.FragmentContextView$7$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$$1();
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$0() {
            if (VoIPService.getSharedInstance() == null) {
                return;
            }
            VoIPService.getSharedInstance().setMicMute(false, true, false);
            if (FragmentContextView.this.muteDrawable.setCustomEndFrame(FragmentContextView.this.isMuted ? 15 : 29)) {
                if (FragmentContextView.this.isMuted) {
                    FragmentContextView.this.muteDrawable.setCurrentFrame(0);
                } else {
                    FragmentContextView.this.muteDrawable.setCurrentFrame(14);
                }
            }
            FragmentContextView.this.muteButton.playAnimation();
            Theme.getFragmentContextViewWavesDrawable().updateState(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$1() {
            if (!this.scheduled || VoIPService.getSharedInstance() == null) {
                return;
            }
            this.scheduled = false;
            this.pressed = true;
            FragmentContextView.this.isMuted = false;
            AndroidUtilities.runOnUIThread(this.toggleMicRunnable, 90L);
            try {
                FragmentContextView.this.muteButton.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (FragmentContextView.this.currentStyle == 3 || FragmentContextView.this.currentStyle == 1) {
                VoIPService sharedInstance = VoIPService.getSharedInstance();
                if (sharedInstance == null) {
                    AndroidUtilities.cancelRunOnUIThread(this.pressRunnable);
                    AndroidUtilities.cancelRunOnUIThread(this.toggleMicRunnable);
                    this.scheduled = false;
                    this.pressed = false;
                    return true;
                }
                if (motionEvent.getAction() == 0 && sharedInstance.isMicMute()) {
                    AndroidUtilities.runOnUIThread(this.pressRunnable, 300L);
                    this.scheduled = true;
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    AndroidUtilities.cancelRunOnUIThread(this.toggleMicRunnable);
                    if (this.scheduled) {
                        AndroidUtilities.cancelRunOnUIThread(this.pressRunnable);
                        this.scheduled = false;
                    } else if (this.pressed) {
                        FragmentContextView.this.isMuted = true;
                        if (FragmentContextView.this.muteDrawable.setCustomEndFrame(15)) {
                            if (FragmentContextView.this.isMuted) {
                                FragmentContextView.this.muteDrawable.setCurrentFrame(0);
                            } else {
                                FragmentContextView.this.muteDrawable.setCurrentFrame(14);
                            }
                        }
                        FragmentContextView.this.muteButton.playAnimation();
                        if (VoIPService.getSharedInstance() != null) {
                            VoIPService.getSharedInstance().setMicMute(true, true, false);
                            try {
                                FragmentContextView.this.muteButton.performHapticFeedback(3, 2);
                            } catch (Exception unused) {
                            }
                        }
                        this.pressed = false;
                        Theme.getFragmentContextViewWavesDrawable().updateState(true);
                        MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0);
                        super.onTouchEvent(motionEventObtain);
                        motionEventObtain.recycle();
                        return true;
                    }
                }
            }
            return super.onTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(Button.class.getName());
            accessibilityNodeInfo.setText(LocaleController.getString(FragmentContextView.this.isMuted ? C2369R.string.VoipUnmute : C2369R.string.VoipMute));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$6(View view) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null) {
            return;
        }
        ChatObject.Call call = sharedInstance.groupCall;
        if (call != null) {
            TLRPC.Chat chat = sharedInstance.getChat();
            TLRPC.GroupCallParticipant groupCallParticipant = (TLRPC.GroupCallParticipant) call.participants.get(sharedInstance.getSelfId());
            if (groupCallParticipant != null && !groupCallParticipant.can_self_unmute && groupCallParticipant.muted && !ChatObject.canManageCalls(chat)) {
                return;
            }
        }
        boolean z = !sharedInstance.isMicMute();
        this.isMuted = z;
        sharedInstance.setMicMute(z, false, true);
        if (this.muteDrawable.setCustomEndFrame(this.isMuted ? 15 : 29)) {
            if (this.isMuted) {
                this.muteDrawable.setCurrentFrame(0);
            } else {
                this.muteDrawable.setCurrentFrame(14);
            }
        }
        this.muteButton.playAnimation();
        Theme.getFragmentContextViewWavesDrawable().updateState(true);
        try {
            this.muteButton.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$8(View view) {
        if (this.currentStyle == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.fragment.getParentActivity(), this.resourcesProvider);
            builder.setTitle(LocaleController.getString(C2369R.string.StopLiveLocationAlertToTitle));
            if (this.fragment instanceof DialogsActivity) {
                builder.setMessage(LocaleController.getString(C2369R.string.StopLiveLocationAlertAllText));
            } else {
                TLRPC.Chat currentChat = this.chatActivity.getCurrentChat();
                TLRPC.User currentUser = this.chatActivity.getCurrentUser();
                if (currentChat != null) {
                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("StopLiveLocationAlertToGroupText", C2369R.string.StopLiveLocationAlertToGroupText, currentChat.title)));
                } else if (currentUser != null) {
                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("StopLiveLocationAlertToUserText", C2369R.string.StopLiveLocationAlertToUserText, UserObject.getFirstName(currentUser))));
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.AreYouSure));
                }
            }
            builder.setPositiveButton(LocaleController.getString(C2369R.string.Stop), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda18
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$checkCreateView$7(alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            builder.show();
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(getThemedColor(Theme.key_text_RedBold));
                return;
            }
            return;
        }
        MediaController.getInstance().cleanupPlayer(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$7(AlertDialog alertDialog, int i) {
        BaseFragment baseFragment = this.fragment;
        if (!(baseFragment instanceof DialogsActivity)) {
            LocationController.getInstance(baseFragment.getCurrentAccount()).removeSharingLocation(this.chatActivity.getDialogId());
            return;
        }
        for (int i2 = 0; i2 < 16; i2++) {
            LocationController.getInstance(i2).removeAllLocationSharings();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkCreateView$9(View view) {
        if (this.currentStyle != 0) {
            return false;
        }
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        BaseFragment baseFragment = this.fragment;
        if (baseFragment == null || playingMessageObject == null) {
            return false;
        }
        if (playingMessageObject.getDialogId() == (baseFragment instanceof ChatActivity ? ((ChatActivity) baseFragment).getDialogId() : 0L)) {
            ((ChatActivity) this.fragment).scrollToMessageId(playingMessageObject.getId(), 0, false, 0, true, 0);
            return true;
        }
        long dialogId = playingMessageObject.getDialogId();
        Bundle bundle = new Bundle();
        if (DialogObject.isEncryptedDialog(dialogId)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(dialogId));
        } else if (DialogObject.isUserDialog(dialogId)) {
            bundle.putLong("user_id", dialogId);
        } else {
            bundle.putLong("chat_id", -dialogId);
        }
        bundle.putInt("message_id", playingMessageObject.getId());
        this.fragment.presentFragment(new ChatActivity(bundle), this.fragment instanceof ChatActivity);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkCreateView$10(View view, MotionEvent motionEvent) {
        if (this.currentStyle != 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.firstX = motionEvent.getRawX();
            this.firstY = motionEvent.getRawY();
            return false;
        }
        if (action != 1) {
            return false;
        }
        this.secondX = motionEvent.getRawX();
        this.secondY = motionEvent.getRawY();
        this.f1882dx = Math.abs(this.firstX - this.secondX);
        float fAbs = Math.abs(this.firstY - this.secondY);
        this.f1883dy = fAbs;
        if (this.firstY <= this.secondY || fAbs <= AndroidUtilities.m1146dp(getStyleHeight()) || this.f1882dx >= this.f1883dy) {
            return false;
        }
        MediaController.getInstance().cleanupPlayer(true, true);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$12(View view) {
        ChatObject.Call groupCall;
        long dialogId;
        TL_stories.StoryItem storyItemFindStory;
        int i = this.currentStyle;
        if (i == 6) {
            LivePlayer livePlayer = LivePlayer.recording;
            if (livePlayer == null) {
                return;
            }
            int i2 = livePlayer.currentAccount;
            if (i2 != UserConfig.selectedAccount) {
                LaunchActivity launchActivity = LaunchActivity.instance;
                if (launchActivity == null) {
                    return;
                } else {
                    launchActivity.switchToAccount(i2, true);
                }
            }
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            if (safeLastFragment == null || (storyItemFindStory = MessagesController.getInstance(livePlayer.currentAccount).getStoriesController().findStory(livePlayer.dialogId, livePlayer.storyId)) == null) {
                return;
            }
            storyItemFindStory.dialogId = livePlayer.dialogId;
            safeLastFragment.getOrCreateStoryViewer(livePlayer.currentAccount).open(livePlayer.currentAccount, getContext(), storyItemFindStory, (StoryViewer.PlaceProvider) null);
            return;
        }
        if (i == 0) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (this.fragment == null || playingMessageObject == null) {
                return;
            }
            if ((playingMessageObject.isMusic() || isPlayingVoice()) && !isPlayingRoundVideo()) {
                if (getContext() instanceof LaunchActivity) {
                    this.fragment.showDialog(new AudioPlayerAlert(getContext(), this.resourcesProvider));
                    return;
                }
                return;
            }
            ChatActivityInterface chatActivityInterface = this.chatActivity;
            if (playingMessageObject.getDialogId() == (chatActivityInterface != null ? chatActivityInterface.getDialogId() : 0L)) {
                this.chatActivity.scrollToMessageId(playingMessageObject.getId(), 0, false, 0, true, 0);
                return;
            }
            long dialogId2 = playingMessageObject.getDialogId();
            Bundle bundle = new Bundle();
            if (DialogObject.isEncryptedDialog(dialogId2)) {
                bundle.putInt("enc_id", DialogObject.getEncryptedChatId(dialogId2));
            } else if (DialogObject.isUserDialog(dialogId2)) {
                bundle.putLong("user_id", dialogId2);
            } else {
                bundle.putLong("chat_id", -dialogId2);
            }
            bundle.putInt("message_id", playingMessageObject.getId());
            this.fragment.presentFragment(new ChatActivity(bundle), this.fragment instanceof ChatActivity);
            return;
        }
        if (i == 1) {
            getContext().startActivity(new Intent(getContext(), (Class<?>) LaunchActivity.class).setAction("voip"));
            return;
        }
        if (i == 2) {
            int currentAccount = UserConfig.selectedAccount;
            ChatActivityInterface chatActivityInterface2 = this.chatActivity;
            if (chatActivityInterface2 != null) {
                dialogId = chatActivityInterface2.getDialogId();
                currentAccount = this.fragment.getCurrentAccount();
            } else if (LocationController.getLocationsCount() == 1) {
                for (int i3 = 0; i3 < 16; i3++) {
                    if (!LocationController.getInstance(i3).sharingLocationsUI.isEmpty()) {
                        LocationController.SharingLocationInfo sharingLocationInfo = LocationController.getInstance(i3).sharingLocationsUI.get(0);
                        dialogId = sharingLocationInfo.did;
                        currentAccount = sharingLocationInfo.messageObject.currentAccount;
                        break;
                    }
                }
                dialogId = 0;
            } else {
                dialogId = 0;
            }
            if (dialogId != 0) {
                openSharingLocation(LocationController.getInstance(currentAccount).getSharingLocationInfo(dialogId));
                return;
            } else {
                this.fragment.showDialog(new SharingLocationsAlert(getContext(), new SharingLocationsAlert.SharingLocationsAlertDelegate() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda19
                    @Override // org.telegram.ui.Components.SharingLocationsAlert.SharingLocationsAlertDelegate
                    public final void didSelectLocation(LocationController.SharingLocationInfo sharingLocationInfo2) {
                        this.f$0.openSharingLocation(sharingLocationInfo2);
                    }
                }, this.resourcesProvider));
                return;
            }
        }
        if (i == 3) {
            if (VoIPService.getSharedInstance() == null || !(getContext() instanceof LaunchActivity)) {
                return;
            }
            GroupCallActivity.create((LaunchActivity) getContext(), AccountInstance.getInstance(VoIPService.getSharedInstance().getAccount()), null, null, false, null);
            return;
        }
        if (i == 4) {
            if (this.fragment.getParentActivity() == null || (groupCall = this.chatActivity.getGroupCall()) == null) {
                return;
            }
            TLRPC.Chat chat = this.fragment.getMessagesController().getChat(Long.valueOf(groupCall.chatId));
            TLRPC.GroupCall groupCall2 = groupCall.call;
            Boolean boolValueOf = Boolean.valueOf((groupCall2 == null || groupCall2.rtmp_stream) ? false : true);
            Activity parentActivity = this.fragment.getParentActivity();
            BaseFragment baseFragment = this.fragment;
            VoIPHelper.startCall(chat, null, null, false, boolValueOf, parentActivity, baseFragment, baseFragment.getAccountInstance());
            return;
        }
        if (i != 5 || this.fragment.getSendMessagesHelper().getImportingHistory(((ChatActivity) this.fragment).getDialogId()) == null) {
            return;
        }
        ImportingAlert importingAlert = new ImportingAlert(getContext(), null, (ChatActivity) this.fragment, this.resourcesProvider);
        importingAlert.setOnHideListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda20
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$checkCreateView$11(dialogInterface);
            }
        });
        this.fragment.showDialog(importingAlert);
        checkImport(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkCreateView$11(DialogInterface dialogInterface) {
        checkImport(false);
    }

    private void createPlaybackSpeedButton() {
        if (this.playbackSpeedButton != null) {
            return;
        }
        ActionBarMenuItem actionBarMenuItem = new ActionBarMenuItem(getContext(), (ActionBarMenu) null, 0, getThemedColor(Theme.key_dialogTextBlack), this.resourcesProvider);
        this.playbackSpeedButton = actionBarMenuItem;
        actionBarMenuItem.setAdditionalYOffset(AndroidUtilities.m1146dp(30.0f));
        this.playbackSpeedButton.setLongClickEnabled(false);
        this.playbackSpeedButton.setVisibility(8);
        this.playbackSpeedButton.setTag(null);
        this.playbackSpeedButton.setShowSubmenuByMove(false);
        this.playbackSpeedButton.setContentDescription(LocaleController.getString(C2369R.string.AccDescrPlayerSpeed));
        this.playbackSpeedButton.setDelegate(new ActionBarMenuItem.ActionBarMenuItemDelegate() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemDelegate
            public final void onItemClick(int i) {
                this.f$0.lambda$createPlaybackSpeedButton$13(i);
            }
        });
        ActionBarMenuItem actionBarMenuItem2 = this.playbackSpeedButton;
        SpeedIconDrawable speedIconDrawable = new SpeedIconDrawable(true);
        this.speedIcon = speedIconDrawable;
        actionBarMenuItem2.setIcon(speedIconDrawable);
        final float[] fArr = {1.0f, 1.5f, 2.0f};
        ActionBarMenuSlider.SpeedSlider speedSlider = new ActionBarMenuSlider.SpeedSlider(getContext(), this.resourcesProvider);
        this.speedSlider = speedSlider;
        speedSlider.setRoundRadiusDp(6.0f);
        this.speedSlider.setDrawShadow(true);
        this.speedSlider.setOnValueChange(new Utilities.Callback2() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$createPlaybackSpeedButton$14((Float) obj, (Boolean) obj2);
            }
        });
        this.speedItems[0] = this.playbackSpeedButton.lazilyAddSubItem(0, C2369R.drawable.msg_speed_slow, LocaleController.getString(C2369R.string.SpeedSlow));
        this.speedItems[1] = this.playbackSpeedButton.lazilyAddSubItem(1, C2369R.drawable.msg_speed_normal, LocaleController.getString(C2369R.string.SpeedNormal));
        this.speedItems[2] = this.playbackSpeedButton.lazilyAddSubItem(2, C2369R.drawable.msg_speed_medium, LocaleController.getString(C2369R.string.SpeedMedium));
        this.speedItems[3] = this.playbackSpeedButton.lazilyAddSubItem(3, C2369R.drawable.msg_speed_fast, LocaleController.getString(C2369R.string.SpeedFast));
        this.speedItems[4] = this.playbackSpeedButton.lazilyAddSubItem(4, C2369R.drawable.msg_speed_veryfast, LocaleController.getString(C2369R.string.SpeedVeryFast));
        this.speedItems[5] = this.playbackSpeedButton.lazilyAddSubItem(5, C2369R.drawable.msg_speed_superfast, LocaleController.getString(C2369R.string.SpeedSuperFast));
        if (AndroidUtilities.density >= 3.0f) {
            this.playbackSpeedButton.setPadding(0, 1, 0, 0);
        }
        this.playbackSpeedButton.setAdditionalXOffset(AndroidUtilities.m1146dp(8.0f));
        addView(this.playbackSpeedButton, LayoutHelper.createFrame(36, 36.0f, 53, 0.0f, 0.0f, 36.0f, 0.0f));
        this.playbackSpeedButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createPlaybackSpeedButton$15(fArr, view);
            }
        });
        this.playbackSpeedButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$createPlaybackSpeedButton$17(view);
            }
        });
        updatePlaybackButton(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPlaybackSpeedButton$13(int i) {
        if (i >= 0) {
            float[] fArr = speeds;
            if (i >= fArr.length) {
                return;
            }
            float playbackSpeed = MediaController.getInstance().getPlaybackSpeed(this.isMusic);
            float f = fArr[i];
            MediaController.getInstance().setPlaybackSpeed(this.isMusic, f);
            if (playbackSpeed != f) {
                playbackSpeedChanged(false, playbackSpeed, f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPlaybackSpeedButton$14(Float f, Boolean bool) {
        this.slidingSpeed = !bool.booleanValue();
        MediaController.getInstance().setPlaybackSpeed(this.isMusic, this.speedSlider.getSpeed(f.floatValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPlaybackSpeedButton$15(float[] fArr, View view) {
        float playbackSpeed = MediaController.getInstance().getPlaybackSpeed(this.isMusic);
        int i = 0;
        while (true) {
            if (i >= fArr.length) {
                i = -1;
                break;
            } else if (playbackSpeed - 0.1f <= fArr[i]) {
                break;
            } else {
                i++;
            }
        }
        int i2 = i + 1;
        float f = fArr[i2 < fArr.length ? i2 : 0];
        MediaController.getInstance().setPlaybackSpeed(this.isMusic, f);
        playbackSpeedChanged(true, playbackSpeed, f);
        checkSpeedHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createPlaybackSpeedButton$17(View view) {
        final float playbackSpeed = MediaController.getInstance().getPlaybackSpeed(this.isMusic);
        this.speedSlider.setSpeed(playbackSpeed, false);
        ActionBarMenuSlider.SpeedSlider speedSlider = this.speedSlider;
        int i = Theme.key_actionBarDefaultSubmenuBackground;
        speedSlider.setBackgroundColor(Theme.getColor(i, this.resourcesProvider));
        this.speedSlider.invalidateBlur(this.fragment instanceof ChatActivity);
        this.playbackSpeedButton.redrawPopup(Theme.getColor(i));
        this.playbackSpeedButton.updateColor();
        updatePlaybackButton(false);
        this.playbackSpeedButton.setDimMenu(0.3f);
        this.playbackSpeedButton.toggleSubMenu(this.speedSlider, null);
        this.playbackSpeedButton.setOnMenuDismiss(new Utilities.Callback() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda17
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createPlaybackSpeedButton$16(playbackSpeed, (Boolean) obj);
            }
        });
        MessagesController.getGlobalNotificationsSettings().edit().putInt("speedhint", -15).apply();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPlaybackSpeedButton$16(float f, Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        playbackSpeedChanged(false, f, MediaController.getInstance().getPlaybackSpeed(this.isMusic));
    }

    private void checkSpeedHint() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastPlaybackClick > 300) {
            int i = MessagesController.getGlobalNotificationsSettings().getInt("speedhint", 0) + 1;
            if (i > 2) {
                i = -10;
            }
            MessagesController.getGlobalNotificationsSettings().edit().putInt("speedhint", i).apply();
            if (i >= 0) {
                showSpeedHint();
            }
        }
        this.lastPlaybackClick = jCurrentTimeMillis;
    }

    private void showSpeedHint() {
        if (this.fragment == null || !(getParent() instanceof ViewGroup)) {
            return;
        }
        HintView hintView = new HintView(getContext(), 6, true) { // from class: org.telegram.ui.Components.FragmentContextView.9
            @Override // android.view.View
            public void setVisibility(int i) {
                super.setVisibility(i);
                if (i != 0) {
                    try {
                        ((ViewGroup) getParent()).removeView(this);
                    } catch (Exception unused) {
                    }
                }
            }
        };
        this.speedHintView = hintView;
        hintView.setExtraTranslationY(AndroidUtilities.m1146dp(-12.0f));
        this.speedHintView.setText(LocaleController.getString(C2369R.string.SpeedHint));
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
        marginLayoutParams.rightMargin = AndroidUtilities.m1146dp(3.0f);
        ((ViewGroup) getParent()).addView(this.speedHintView, marginLayoutParams);
        this.speedHintView.showForView(this.playbackSpeedButton, true);
    }

    public void onPanTranslationUpdate(float f) {
        HintView hintView = this.speedHintView;
        if (hintView != null) {
            hintView.setExtraTranslationY(AndroidUtilities.m1146dp(72.0f) + f);
        }
    }

    private void updatePlaybackButton(boolean z) {
        if (this.speedIcon == null) {
            return;
        }
        float playbackSpeed = MediaController.getInstance().getPlaybackSpeed(this.isMusic);
        this.speedIcon.setValue(playbackSpeed, z);
        updateColors();
        boolean z2 = this.slidingSpeed;
        this.slidingSpeed = false;
        for (int i = 0; i < this.speedItems.length; i++) {
            if (!z2 && Math.abs(playbackSpeed - speeds[i]) < 0.05f) {
                ActionBarMenuItem.Item item = this.speedItems[i];
                int i2 = Theme.key_featuredStickers_addButtonPressed;
                item.setColors(getThemedColor(i2), getThemedColor(i2));
            } else {
                ActionBarMenuItem.Item item2 = this.speedItems[i];
                int i3 = Theme.key_actionBarDefaultSubmenuItem;
                item2.setColors(getThemedColor(i3), getThemedColor(i3));
            }
        }
        this.speedSlider.setSpeed(playbackSpeed, z);
    }

    public void updateColors() {
        int themedColor = getThemedColor(!equals(MediaController.getInstance().getPlaybackSpeed(this.isMusic), 1.0f) ? Theme.key_featuredStickers_addButtonPressed : Theme.key_inappPlayerClose);
        SpeedIconDrawable speedIconDrawable = this.speedIcon;
        if (speedIconDrawable != null) {
            speedIconDrawable.setColor(themedColor);
        }
        ActionBarMenuItem actionBarMenuItem = this.playbackSpeedButton;
        if (actionBarMenuItem != null) {
            actionBarMenuItem.setBackground(Theme.createSelectorDrawable(themedColor & 436207615, 1, AndroidUtilities.m1146dp(14.0f)));
        }
    }

    public void setAdditionalContextView(FragmentContextView fragmentContextView) {
        this.additionalContextView = fragmentContextView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openSharingLocation(final LocationController.SharingLocationInfo sharingLocationInfo) {
        if (sharingLocationInfo == null || !(this.fragment.getParentActivity() instanceof LaunchActivity)) {
            return;
        }
        LaunchActivity launchActivity = (LaunchActivity) this.fragment.getParentActivity();
        launchActivity.switchToAccount(sharingLocationInfo.messageObject.currentAccount, true);
        LocationActivity locationActivity = new LocationActivity(2);
        locationActivity.setMessageObject(sharingLocationInfo.messageObject);
        final long dialogId = sharingLocationInfo.messageObject.getDialogId();
        locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda21
            @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
            public final void didSelectLocation(TLRPC.MessageMedia messageMedia, int i, boolean z, int i2, long j) {
                SendMessagesHelper.getInstance(sharingLocationInfo.messageObject.currentAccount).sendMessage(SendMessagesHelper.SendMessageParams.m1195of(messageMedia, dialogId, (MessageObject) null, (MessageObject) null, (TLRPC.ReplyMarkup) null, (HashMap<String, String>) null, z, i2, 0));
            }
        });
        launchActivity.lambda$runLinkRequest$107(locationActivity);
    }

    @Keep
    public float getTopPadding() {
        return this.topPadding;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void checkVisibility() {
        /*
            r5 = this;
            boolean r0 = r5.isLocation
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L28
            org.telegram.ui.ActionBar.BaseFragment r0 = r5.fragment
            boolean r3 = r0 instanceof org.telegram.p023ui.DialogsActivity
            if (r3 == 0) goto L14
            int r0 = org.telegram.messenger.LocationController.getLocationsCount()
            if (r0 == 0) goto L9c
            goto L9d
        L14:
            int r0 = r0.getCurrentAccount()
            org.telegram.messenger.LocationController r0 = org.telegram.messenger.LocationController.getInstance(r0)
            org.telegram.ui.Components.ChatActivityInterface r1 = r5.chatActivity
            long r3 = r1.getDialogId()
            boolean r1 = r0.isSharingLocation(r3)
            goto L9d
        L28:
            org.telegram.messenger.voip.VoIPService r0 = org.telegram.messenger.voip.VoIPService.getSharedInstance()
            if (r0 == 0) goto L48
            org.telegram.messenger.voip.VoIPService r0 = org.telegram.messenger.voip.VoIPService.getSharedInstance()
            boolean r0 = r0.isHangingUp()
            if (r0 != 0) goto L48
            org.telegram.messenger.voip.VoIPService r0 = org.telegram.messenger.voip.VoIPService.getSharedInstance()
            int r0 = r0.getCallState()
            r3 = 15
            if (r0 == r3) goto L48
            r5.startJoinFlickerAnimation()
            goto L9d
        L48:
            org.telegram.ui.Components.ChatActivityInterface r0 = r5.chatActivity
            if (r0 == 0) goto L65
            org.telegram.ui.ActionBar.BaseFragment r0 = r5.fragment
            org.telegram.messenger.SendMessagesHelper r0 = r0.getSendMessagesHelper()
            org.telegram.ui.Components.ChatActivityInterface r3 = r5.chatActivity
            long r3 = r3.getDialogId()
            org.telegram.messenger.SendMessagesHelper$ImportingHistory r0 = r0.getImportingHistory(r3)
            if (r0 == 0) goto L65
            boolean r0 = r5.isPlayingVoice()
            if (r0 != 0) goto L65
            goto L9d
        L65:
            org.telegram.ui.Components.ChatActivityInterface r0 = r5.chatActivity
            if (r0 == 0) goto L8b
            org.telegram.messenger.ChatObject$Call r0 = r0.getGroupCall()
            if (r0 == 0) goto L8b
            org.telegram.ui.Components.ChatActivityInterface r0 = r5.chatActivity
            org.telegram.messenger.ChatObject$Call r0 = r0.getGroupCall()
            boolean r0 = r0.shouldShowPanel()
            if (r0 == 0) goto L8b
            boolean r0 = org.telegram.p023ui.Components.GroupCallPip.isShowing()
            if (r0 != 0) goto L8b
            boolean r0 = r5.isPlayingVoice()
            if (r0 != 0) goto L8b
            r5.startJoinFlickerAnimation()
            goto L9d
        L8b:
            org.telegram.messenger.MediaController r0 = org.telegram.messenger.MediaController.getInstance()
            org.telegram.messenger.MessageObject r0 = r0.getPlayingMessageObject()
            if (r0 == 0) goto L9c
            int r0 = r0.getId()
            if (r0 == 0) goto L9c
            goto L9d
        L9c:
            r1 = 0
        L9d:
            if (r1 == 0) goto La2
            r5.checkCreateView()
        La2:
            if (r1 == 0) goto La5
            goto La7
        La5:
            r2 = 8
        La7:
            r5.setVisibility(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.FragmentContextView.checkVisibility():void");
    }

    @Keep
    public void setTopPadding(float f) {
        this.topPadding = f;
        if (this.fragment == null || getParent() == null) {
            return;
        }
        View fragmentView = this.applyingView;
        if (fragmentView == null) {
            fragmentView = this.fragment.getFragmentView();
        }
        FragmentContextView fragmentContextView = this.additionalContextView;
        int iM1146dp = (fragmentContextView == null || fragmentContextView.getVisibility() != 0 || this.additionalContextView.getParent() == null) ? 0 : AndroidUtilities.m1146dp(this.additionalContextView.getStyleHeight());
        if (fragmentView == null || getParent() == null) {
            return;
        }
        fragmentView.setPadding(0, ((int) (getVisibility() == 0 ? this.topPadding : 0.0f)) + iM1146dp, 0, 0);
    }

    private boolean equals(float f, float f2) {
        return Math.abs(f - f2) < 0.05f;
    }

    private void playbackSpeedChanged(boolean z, float f, float f2) {
        String string;
        int i;
        if (equals(f, f2)) {
            return;
        }
        if (Math.abs(f2 - 1.0f) < 0.05f) {
            if (f < f2) {
                return;
            }
            string = LocaleController.getString(C2369R.string.AudioSpeedNormal);
            if (Math.abs(f - 2.0f) < 0.05f) {
                i = C2369R.raw.speed_2to1;
            } else if (f2 < f) {
                i = C2369R.raw.speed_slow;
            } else {
                i = C2369R.raw.speed_fast;
            }
        } else if (z && equals(f2, 1.5f) && equals(f, 1.0f)) {
            string = LocaleController.formatString("AudioSpeedCustom", C2369R.string.AudioSpeedCustom, SpeedIconDrawable.formatNumber(f2));
            i = C2369R.raw.speed_1to15;
        } else if (!z || !equals(f2, 2.0f) || !equals(f, 1.5f)) {
            string = LocaleController.formatString("AudioSpeedCustom", C2369R.string.AudioSpeedCustom, SpeedIconDrawable.formatNumber(f2));
            i = f2 < 1.0f ? C2369R.raw.speed_slow : C2369R.raw.speed_fast;
        } else {
            string = LocaleController.getString(C2369R.string.AudioSpeedFast);
            i = C2369R.raw.speed_15to2;
        }
        BulletinFactory.m1267of(this.fragment).createSimpleBulletin(i, string).show();
    }

    private void updateStyle(int i) {
        if (this.currentStyle == i) {
            return;
        }
        checkCreateView();
        int i2 = this.currentStyle;
        if (i2 == 3 || i2 == 1) {
            Theme.getFragmentContextViewWavesDrawable().removeParent(this);
            if (VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().unregisterStateListener(this);
            }
            ReplaceAnimator replaceAnimator = this.callMessagesAnimator;
            if (replaceAnimator != null) {
                replaceAnimator.replace(null, true);
            }
        }
        this.currentStyle = i;
        this.frameLayout.setWillNotDraw(i != 4);
        if (i != 4) {
            this.notifyButtonEnabled = false;
        }
        AvatarsImageView avatarsImageView = this.avatars;
        if (avatarsImageView != null) {
            avatarsImageView.setStyle(this.currentStyle);
            this.avatars.setLayoutParams(LayoutHelper.createFrame(108, getStyleHeight(), 51));
        }
        this.frameLayout.setLayoutParams(LayoutHelper.createFrame(-1, getStyleHeight(), 51, 0.0f, 0.0f, 0.0f, 0.0f));
        float f = this.topPadding;
        if (f > 0.0f && f != AndroidUtilities.dp2(getStyleHeight())) {
            updatePaddings();
            setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
        }
        if (i == 6) {
            this.selector.setBackground(Theme.getSelectorDrawable(false));
            this.frameLayout.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{getThemedColor(Theme.key_stories_circle_live1), getThemedColor(Theme.key_stories_circle_live2)}));
            this.frameLayout.setTag(null);
            this.subtitleTextView.setVisibility(8);
            this.joinButton.setVisibility(8);
            this.closeButton.setVisibility(8);
            this.playButton.setVisibility(8);
            this.muteButton.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.prevButton.setVisibility(8);
            this.coverContainer.setVisibility(8);
            this.divider.setVisibility(8);
            ActionBarMenuItem actionBarMenuItem = this.playbackSpeedButton;
            if (actionBarMenuItem != null) {
                actionBarMenuItem.setVisibility(8);
                this.playbackSpeedButton.setTag(null);
            }
            this.importingImageView.setVisibility(8);
            this.importingImageView.stopAnimation();
            this.avatars.setVisibility(8);
            this.titleTextView.setTag(Integer.valueOf(Theme.key_returnToCallText));
            int i3 = 0;
            while (i3 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher = this.titleTextView;
                TextView textView = i3 == 0 ? clippingTextViewSwitcher.getTextView() : clippingTextViewSwitcher.getNextTextView();
                if (textView != null) {
                    textView.setGravity(17);
                    textView.setTextColor(getThemedColor(Theme.key_returnToCallText));
                    textView.setTypeface(AndroidUtilities.bold());
                    textView.setTextSize(1, 15.0f);
                }
                i3++;
            }
            this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, -1.0f, this.isSideMenued ? 64 : 0, 0.0f));
            return;
        }
        if (i == 5) {
            this.selector.setBackground(Theme.getSelectorDrawable(false));
            FrameLayout frameLayout = this.frameLayout;
            int i4 = Theme.key_inappPlayerBackground;
            frameLayout.setBackgroundColor(getThemedColor(i4));
            this.frameLayout.setTag(Integer.valueOf(i4));
            int i5 = 0;
            while (i5 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher2 = this.titleTextView;
                TextView textView2 = i5 == 0 ? clippingTextViewSwitcher2.getTextView() : clippingTextViewSwitcher2.getNextTextView();
                if (textView2 != null) {
                    textView2.setGravity(19);
                    textView2.setTextColor(getThemedColor(Theme.key_inappPlayerTitle));
                    textView2.setTypeface(Typeface.DEFAULT);
                    textView2.setTextSize(1, 15.0f);
                }
                i5++;
            }
            this.titleTextView.setTag(Integer.valueOf(Theme.key_inappPlayerTitle));
            this.subtitleTextView.setVisibility(8);
            this.joinButton.setVisibility(8);
            this.closeButton.setVisibility(8);
            this.playButton.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.prevButton.setVisibility(8);
            this.coverContainer.setVisibility(8);
            this.divider.setVisibility(8);
            this.muteButton.setVisibility(8);
            this.avatars.setVisibility(8);
            this.importingImageView.setVisibility(0);
            this.importingImageView.playAnimation();
            this.closeButton.setContentDescription(LocaleController.getString(C2369R.string.AccDescrClosePlayer));
            ActionBarMenuItem actionBarMenuItem2 = this.playbackSpeedButton;
            if (actionBarMenuItem2 != null) {
                actionBarMenuItem2.setVisibility(8);
                this.playbackSpeedButton.setTag(null);
            }
            this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 35.0f, 0.0f, (this.isSideMenued ? 64 : 0) + 36, 0.0f));
            return;
        }
        if (i == 0 || i == 2) {
            this.selector.setBackground(Theme.getSelectorDrawable(false));
            FrameLayout frameLayout2 = this.frameLayout;
            int i6 = Theme.key_inappPlayerBackground;
            frameLayout2.setBackgroundColor(getThemedColor(i6));
            this.frameLayout.setTag(Integer.valueOf(i6));
            this.joinButton.setVisibility(8);
            this.closeButton.setVisibility(0);
            this.muteButton.setVisibility(8);
            this.importingImageView.setVisibility(8);
            this.importingImageView.stopAnimation();
            this.avatars.setVisibility(8);
            this.playButton.setVisibility(0);
            this.nextButton.setVisibility(0);
            this.prevButton.setVisibility(0);
            this.coverContainer.setVisibility(0);
            this.divider.setVisibility(0);
            this.subtitleTextView.setVisibility(0);
            int i7 = 0;
            while (i7 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher3 = this.titleTextView;
                TextView textView3 = i7 == 0 ? clippingTextViewSwitcher3.getTextView() : clippingTextViewSwitcher3.getNextTextView();
                if (textView3 != null) {
                    textView3.setGravity(i == 0 ? 48 : 19);
                    textView3.setTextColor(getThemedColor(Theme.key_player_actionBarTitle));
                    textView3.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                    textView3.setTextSize(1, 14.0f);
                }
                i7++;
            }
            this.titleTextView.setPadding(0, 0, 0, 0);
            this.subtitleTextView.setPadding(0, 0, 0, 0);
            this.titleTextView.setTag(Integer.valueOf(Theme.key_player_actionBarTitle));
            this.closeButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 53, 0.0f, 0.0f, 2.0f, 0.0f));
            if (i == 6) {
                this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 51, 8.0f, 0.0f, 0.0f, 0.0f));
                this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 36.0f, 51, 51.0f, 0.0f, (this.isSideMenued ? 64 : 0) + 36, 0.0f));
                this.closeButton.setVisibility(8);
                return;
            }
            if (i == 0) {
                this.coverContainer.setLayoutParams(LayoutHelper.createFrame(32, 32.0f, 51, 17.0f, 8.0f, 0.0f, 8.0f));
                this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 36.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
                createPlaybackSpeedButton();
                ActionBarMenuItem actionBarMenuItem3 = this.playbackSpeedButton;
                if (actionBarMenuItem3 != null) {
                    actionBarMenuItem3.setVisibility(0);
                    this.playbackSpeedButton.setTag(1);
                }
                this.closeButton.setContentDescription(LocaleController.getString(C2369R.string.AccDescrClosePlayer));
                return;
            }
            this.divider.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.prevButton.setVisibility(8);
            this.subtitleTextView.setVisibility(8);
            this.closeButton.setVisibility(0);
            this.playButton.setLayoutParams(LayoutHelper.createFrame(32, 36.0f, 51, 17.0f, 0.0f, 0.0f, 0.0f));
            this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-2, 36.0f, 51, 55.0f, 0.0f, 38.0f, 0.0f));
            this.closeButton.setContentDescription(LocaleController.getString(C2369R.string.AccDescrStopLiveLocation));
            return;
        }
        if (i == 4) {
            this.selector.setBackground(Theme.getSelectorDrawable(false));
            FrameLayout frameLayout3 = this.frameLayout;
            int i8 = Theme.key_inappPlayerBackground;
            frameLayout3.setBackgroundColor(getThemedColor(i8));
            this.frameLayout.setTag(Integer.valueOf(i8));
            this.muteButton.setVisibility(8);
            this.subtitleTextView.setVisibility(0);
            this.coverContainer.setVisibility(8);
            this.divider.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.prevButton.setVisibility(8);
            int i9 = 0;
            while (i9 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher4 = this.titleTextView;
                TextView textView4 = i9 == 0 ? clippingTextViewSwitcher4.getTextView() : clippingTextViewSwitcher4.getNextTextView();
                if (textView4 != null) {
                    textView4.setGravity(51);
                    textView4.setTextColor(getThemedColor(Theme.key_inappPlayerPerformer));
                    textView4.setTypeface(AndroidUtilities.bold());
                    textView4.setTextSize(1, 15.0f);
                }
                i9++;
            }
            this.titleTextView.setTag(Integer.valueOf(Theme.key_inappPlayerPerformer));
            this.titleTextView.setPadding(0, 0, this.joinButtonWidth, 0);
            this.importingImageView.setVisibility(8);
            this.importingImageView.stopAnimation();
            ChatActivityInterface chatActivityInterface = this.chatActivity;
            this.avatars.setVisibility(!((chatActivityInterface == null || chatActivityInterface.getGroupCall() == null || this.chatActivity.getGroupCall().call == null || !this.chatActivity.getGroupCall().call.rtmp_stream) ? false : true) ? 0 : 8);
            if (this.avatars.getVisibility() != 8) {
                updateAvatars(false);
            } else {
                this.titleTextView.setTranslationX(-AndroidUtilities.m1146dp(36.0f));
                this.subtitleTextView.setTranslationX(-AndroidUtilities.m1146dp(36.0f));
            }
            this.closeButton.setVisibility(8);
            this.playButton.setVisibility(8);
            ActionBarMenuItem actionBarMenuItem4 = this.playbackSpeedButton;
            if (actionBarMenuItem4 != null) {
                actionBarMenuItem4.setVisibility(8);
                this.playbackSpeedButton.setTag(null);
                return;
            }
            return;
        }
        if (i == 1 || i == 3) {
            this.selector.setBackground(null);
            updateCallTitle();
            boolean zHasRtmpStream = VoIPService.hasRtmpStream();
            this.avatars.setVisibility(!zHasRtmpStream ? 0 : 8);
            if (i == 3 && VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().registerStateListener(this);
            }
            if (this.avatars.getVisibility() != 8) {
                updateAvatars(false);
            } else {
                this.titleTextView.setTranslationX(0.0f);
                this.subtitleTextView.setTranslationX(0.0f);
            }
            this.muteButton.setVisibility(!zHasRtmpStream ? 0 : 8);
            boolean z = VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().isMicMute();
            this.isMuted = z;
            this.muteDrawable.setCustomEndFrame(z ? 15 : 29);
            RLottieDrawable rLottieDrawable = this.muteDrawable;
            rLottieDrawable.setCurrentFrame(rLottieDrawable.getCustomEndFrame() - 1, false, true);
            this.muteButton.invalidate();
            this.coverContainer.setVisibility(8);
            this.divider.setVisibility(8);
            this.nextButton.setVisibility(8);
            this.prevButton.setVisibility(8);
            this.frameLayout.setBackground(null);
            this.frameLayout.setBackgroundColor(0);
            this.importingImageView.setVisibility(8);
            this.importingImageView.stopAnimation();
            Theme.getFragmentContextViewWavesDrawable().addParent(this);
            invalidate();
            int i10 = 0;
            while (i10 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher5 = this.titleTextView;
                TextView textView5 = i10 == 0 ? clippingTextViewSwitcher5.getTextView() : clippingTextViewSwitcher5.getNextTextView();
                if (textView5 != null) {
                    textView5.setGravity(19);
                    textView5.setTextColor(getThemedColor(Theme.key_returnToCallText));
                    textView5.setTypeface(AndroidUtilities.bold());
                    textView5.setTextSize(1, 14.0f);
                }
                i10++;
            }
            this.titleTextView.setTag(Integer.valueOf(Theme.key_returnToCallText));
            this.closeButton.setVisibility(8);
            this.playButton.setVisibility(8);
            this.subtitleTextView.setVisibility(8);
            this.joinButton.setVisibility(8);
            this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 0.0f, this.isSideMenued ? 64 : 0, 0.0f));
            this.titleTextView.setPadding(AndroidUtilities.m1146dp(88.0f), 0, AndroidUtilities.m1146dp(88.0f) + this.joinButtonWidth, 0);
            ActionBarMenuItem actionBarMenuItem5 = this.playbackSpeedButton;
            if (actionBarMenuItem5 != null) {
                actionBarMenuItem5.setVisibility(8);
                this.playbackSpeedButton.setTag(null);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimatorSet animatorSet = this.animatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.animatorSet = null;
        }
        if (this.scheduleRunnableScheduled) {
            AndroidUtilities.cancelRunOnUIThread(this.updateScheduleTimeRunnable);
            this.scheduleRunnableScheduled = false;
        }
        this.visible = false;
        this.notificationsLocker.unlock();
        this.topPadding = 0.0f;
        if (this.isLocation) {
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.liveLocationsChanged);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.liveLocationsCacheChanged);
        } else {
            for (int i = 0; i < 16; i++) {
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.messagePlayingDidReset);
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.messagePlayingDidStart);
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.groupCallUpdated);
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.groupCallTypingsUpdated);
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.historyImportProgressChanged);
                NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.liveStoryUpdated);
                GroupCallMessagesController.getInstance(i).unsubscribeFromCallMessages(0L, this);
            }
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.messagePlayingSpeedChanged);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didStartedCall);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didEndCall);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.webRtcSpeakerAmplitudeEvent);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.webRtcMicAmplitudeEvent);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.groupCallVisibilityChanged);
        }
        int i2 = this.currentStyle;
        if (i2 == 3 || i2 == 1) {
            Theme.getFragmentContextViewWavesDrawable().removeParent(this);
        }
        if (VoIPService.getSharedInstance() != null) {
            VoIPService.getSharedInstance().unregisterStateListener(this);
        }
        this.wasDraw = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isLocation) {
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.liveLocationsChanged);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.liveLocationsCacheChanged);
            FragmentContextView fragmentContextView = this.additionalContextView;
            if (fragmentContextView != null) {
                fragmentContextView.checkVisibility();
            }
            checkLiveLocation(true);
        } else {
            for (int i = 0; i < 16; i++) {
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.messagePlayingDidReset);
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.messagePlayingPlayStateChanged);
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.messagePlayingDidStart);
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.groupCallUpdated);
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.groupCallTypingsUpdated);
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.historyImportProgressChanged);
                NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.liveStoryUpdated);
                GroupCallMessagesController.getInstance(i).subscribeToCallMessages(0L, this);
            }
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.messagePlayingSpeedChanged);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didStartedCall);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didEndCall);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.webRtcSpeakerAmplitudeEvent);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.webRtcMicAmplitudeEvent);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.groupCallVisibilityChanged);
            FragmentContextView fragmentContextView2 = this.additionalContextView;
            if (fragmentContextView2 != null) {
                fragmentContextView2.checkVisibility();
            }
            if (LivePlayer.recording != null) {
                checkLiveStory(true);
            } else if (VoIPService.getSharedInstance() != null && !VoIPService.getSharedInstance().isHangingUp() && VoIPService.getSharedInstance().getCallState() != 15 && !GroupCallPip.isShowing()) {
                checkCall(true);
            } else if (this.chatActivity != null && this.fragment.getSendMessagesHelper().getImportingHistory(this.chatActivity.getDialogId()) != null && !isPlayingVoice()) {
                checkImport(true);
            } else {
                ChatActivityInterface chatActivityInterface = this.chatActivity;
                if (chatActivityInterface != null && chatActivityInterface.getGroupCall() != null && this.chatActivity.getGroupCall().shouldShowPanel() && !GroupCallPip.isShowing() && !isPlayingVoice()) {
                    checkCall(true);
                } else {
                    checkCall(true);
                    checkPlayer(true);
                    updatePlaybackButton(false);
                }
            }
        }
        int i2 = this.currentStyle;
        if (i2 == 3 || i2 == 1) {
            Theme.getFragmentContextViewWavesDrawable().addParent(this);
            if (VoIPService.getSharedInstance() != null) {
                VoIPService.getSharedInstance().registerStateListener(this);
            }
            boolean z = VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().isMicMute();
            if (this.isMuted != z && this.muteButton != null) {
                this.isMuted = z;
                this.muteDrawable.setCustomEndFrame(z ? 15 : 29);
                RLottieDrawable rLottieDrawable = this.muteDrawable;
                rLottieDrawable.setCurrentFrame(rLottieDrawable.getCustomEndFrame() - 1, false, true);
                this.muteButton.invalidate();
            }
        } else if (i2 == 4 && !this.scheduleRunnableScheduled) {
            this.scheduleRunnableScheduled = true;
            this.updateScheduleTimeRunnable.run();
        }
        if (this.visible && this.topPadding == 0.0f) {
            updatePaddings();
            setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
        }
        this.speakerAmplitude = 0.0f;
        this.micAmplitude = 0.0f;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, AndroidUtilities.dp2(getStyleHeight() + 2));
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        VoIPService sharedInstance;
        TLRPC.GroupCallParticipant groupCallParticipant;
        if (i == NotificationCenter.liveLocationsChanged) {
            checkLiveLocation(false);
            return;
        }
        if (i == NotificationCenter.liveStoryUpdated) {
            checkLiveStory(false);
            return;
        }
        if (i == NotificationCenter.liveLocationsCacheChanged) {
            if (this.chatActivity != null) {
                if (this.chatActivity.getDialogId() == ((Long) objArr[0]).longValue()) {
                    checkLocationString();
                    return;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.messagePlayingDidStart || i == NotificationCenter.messagePlayingPlayStateChanged || i == NotificationCenter.messagePlayingDidReset || i == NotificationCenter.didEndCall) {
            int i3 = this.currentStyle;
            if (i3 == 1 || i3 == 3 || i3 == 4) {
                checkCall(false);
            } else if (i3 == 0 && i != NotificationCenter.messagePlayingDidReset) {
                updateButtonsVisibility(true);
                MessageObject messageObject = this.lastMessageObject;
                if (messageObject != null) {
                    updateCover(messageObject);
                }
            }
            checkPlayer(false);
            return;
        }
        if (i == NotificationCenter.fileLoaded) {
            if (((String) objArr[0]).equals(this.currentFile)) {
                checkPlayer(false);
                return;
            }
            return;
        }
        int i4 = NotificationCenter.didStartedCall;
        if (i == i4 || i == NotificationCenter.groupCallUpdated || i == NotificationCenter.groupCallVisibilityChanged) {
            checkCall(false);
            if (this.currentStyle != 3 || (sharedInstance = VoIPService.getSharedInstance()) == null || sharedInstance.groupCall == null) {
                return;
            }
            if (i == i4) {
                sharedInstance.registerStateListener(this);
            }
            int callState = sharedInstance.getCallState();
            if (callState == 1 || callState == 2 || callState == 6 || callState == 5 || this.muteButton == null || (groupCallParticipant = (TLRPC.GroupCallParticipant) sharedInstance.groupCall.participants.get(sharedInstance.getSelfId())) == null || groupCallParticipant.can_self_unmute || !groupCallParticipant.muted || ChatObject.canManageCalls(sharedInstance.getChat())) {
                return;
            }
            sharedInstance.setMicMute(true, false, false);
            long jUptimeMillis = SystemClock.uptimeMillis();
            this.muteButton.dispatchTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
            return;
        }
        if (i == NotificationCenter.groupCallTypingsUpdated) {
            checkCreateView();
            if (this.visible && this.currentStyle == 4) {
                ChatObject.Call groupCall = this.chatActivity.getGroupCall();
                if (groupCall != null && this.subtitleTextView != null) {
                    if (groupCall.isScheduled()) {
                        this.subtitleTextView.setText(LocaleController.formatStartsTime(groupCall.call.schedule_date, 4), false);
                    } else {
                        TLRPC.GroupCall groupCall2 = groupCall.call;
                        int i5 = groupCall2.participants_count;
                        if (i5 == 0) {
                            this.subtitleTextView.setText(LocaleController.getString(groupCall2.rtmp_stream ? C2369R.string.ViewersWatchingNobody : C2369R.string.MembersTalkingNobody), false);
                        } else {
                            this.subtitleTextView.setText(LocaleController.formatPluralString(groupCall2.rtmp_stream ? "ViewersWatching" : "Participants", i5, new Object[0]), false);
                        }
                    }
                }
                updateAvatars(true);
                return;
            }
            return;
        }
        if (i == NotificationCenter.historyImportProgressChanged) {
            int i6 = this.currentStyle;
            if (i6 == 1 || i6 == 3 || i6 == 4) {
                checkCall(false);
            }
            checkImport(false);
            return;
        }
        if (i == NotificationCenter.messagePlayingSpeedChanged) {
            updatePlaybackButton(true);
            return;
        }
        if (i == NotificationCenter.webRtcMicAmplitudeEvent) {
            if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().isMicMute()) {
                this.micAmplitude = 0.0f;
            } else {
                this.micAmplitude = Math.min(8500.0f, ((Float) objArr[0]).floatValue() * 4000.0f) / 8500.0f;
            }
            if (VoIPService.getSharedInstance() != null) {
                Theme.getFragmentContextViewWavesDrawable().setAmplitude(Math.max(this.speakerAmplitude, this.micAmplitude));
                return;
            }
            return;
        }
        if (i == NotificationCenter.webRtcSpeakerAmplitudeEvent) {
            checkCreateView();
            this.speakerAmplitude = Math.max(0.0f, Math.min((((Float) objArr[0]).floatValue() * 15.0f) / 80.0f, 1.0f));
            if (VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().isMicMute()) {
                this.micAmplitude = 0.0f;
            }
            if (VoIPService.getSharedInstance() != null) {
                Theme.getFragmentContextViewWavesDrawable().setAmplitude(Math.max(this.speakerAmplitude, this.micAmplitude));
            }
            this.avatars.invalidate();
        }
    }

    public int getStyleHeight() {
        int i = this.currentStyle;
        return (i == 0 || i == 4) ? 48 : 36;
    }

    public boolean isCallTypeVisible() {
        int i = this.currentStyle;
        return (i == 1 || i == 3) && this.visible;
    }

    private void checkLiveLocation(boolean z) {
        boolean zIsSharingLocation;
        String pluralString;
        String string;
        View fragmentView = this.fragment.getFragmentView();
        if (!z && fragmentView != null && (fragmentView.getParent() == null || ((View) fragmentView.getParent()).getVisibility() != 0)) {
            z = true;
        }
        BaseFragment baseFragment = this.fragment;
        if (baseFragment instanceof DialogsActivity) {
            zIsSharingLocation = LocationController.getLocationsCount() != 0;
        } else {
            zIsSharingLocation = LocationController.getInstance(baseFragment.getCurrentAccount()).isSharingLocation(this.chatActivity.getDialogId());
        }
        if (!zIsSharingLocation) {
            this.lastLocationSharingCount = -1;
            AndroidUtilities.cancelRunOnUIThread(this.checkLocationRunnable);
            if (this.visible) {
                this.visible = false;
                if (z) {
                    if (getVisibility() != 8) {
                        setVisibility(8);
                    }
                    setTopPadding(0.0f);
                    return;
                }
                AnimatorSet animatorSet = this.animatorSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                    this.animatorSet = null;
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.animatorSet = animatorSet2;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this, "topPadding", 0.0f));
                this.animatorSet.setDuration(200L);
                this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.10
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                            return;
                        }
                        FragmentContextView.this.setVisibility(8);
                        FragmentContextView.this.animatorSet = null;
                    }
                });
                this.animatorSet.start();
                return;
            }
            return;
        }
        checkCreateView();
        updateStyle(2);
        this.playButton.setImageDrawable(new ShareLocationDrawable(getContext(), 1));
        if (z && this.topPadding == 0.0f) {
            setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
        }
        if (!this.visible) {
            if (!z) {
                AnimatorSet animatorSet3 = this.animatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.cancel();
                    this.animatorSet = null;
                }
                AnimatorSet animatorSet4 = new AnimatorSet();
                this.animatorSet = animatorSet4;
                animatorSet4.playTogether(ObjectAnimator.ofFloat(this, "topPadding", AndroidUtilities.dp2(getStyleHeight())));
                this.animatorSet.setDuration(200L);
                this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.11
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                            return;
                        }
                        FragmentContextView.this.animatorSet = null;
                    }
                });
                this.animatorSet.start();
            }
            this.visible = true;
            setVisibility(0);
        }
        if (this.fragment instanceof DialogsActivity) {
            String string2 = LocaleController.getString(C2369R.string.LiveLocationContext);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < 16; i++) {
                arrayList.addAll(LocationController.getInstance(i).sharingLocationsUI);
            }
            if (arrayList.size() == 1) {
                LocationController.SharingLocationInfo sharingLocationInfo = (LocationController.SharingLocationInfo) arrayList.get(0);
                long dialogId = sharingLocationInfo.messageObject.getDialogId();
                if (DialogObject.isUserDialog(dialogId)) {
                    pluralString = UserObject.getFirstName(MessagesController.getInstance(sharingLocationInfo.messageObject.currentAccount).getUser(Long.valueOf(dialogId)));
                    string = LocaleController.getString(C2369R.string.AttachLiveLocationIsSharing);
                } else {
                    TLRPC.Chat chat = MessagesController.getInstance(sharingLocationInfo.messageObject.currentAccount).getChat(Long.valueOf(-dialogId));
                    if (chat != null) {
                        pluralString = chat.title;
                    } else {
                        pluralString = "";
                    }
                    string = LocaleController.getString(C2369R.string.AttachLiveLocationIsSharingChat);
                }
            } else {
                pluralString = LocaleController.formatPluralString("Chats", arrayList.size(), new Object[0]);
                string = LocaleController.getString(C2369R.string.AttachLiveLocationIsSharingChats);
            }
            String str = String.format(string, string2, pluralString);
            int iIndexOf = str.indexOf(string2);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            int i2 = 0;
            while (i2 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher = this.titleTextView;
                TextView textView = i2 == 0 ? clippingTextViewSwitcher.getTextView() : clippingTextViewSwitcher.getNextTextView();
                if (textView != null) {
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                }
                i2++;
            }
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold(), 0, getThemedColor(Theme.key_inappPlayerPerformer)), iIndexOf, string2.length() + iIndexOf, 18);
            this.titleTextView.setText(spannableStringBuilder, false);
            return;
        }
        this.checkLocationRunnable.run();
        checkLocationString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLocationString() {
        int i;
        String str;
        if (this.chatActivity == null || this.titleTextView == null) {
            return;
        }
        checkCreateView();
        long dialogId = this.chatActivity.getDialogId();
        int currentAccount = this.fragment.getCurrentAccount();
        ArrayList arrayList = (ArrayList) LocationController.getInstance(currentAccount).locationsCache.get(dialogId);
        if (!this.firstLocationsLoaded) {
            LocationController.getInstance(currentAccount).loadLiveLocations(dialogId);
            this.firstLocationsLoaded = true;
        }
        TLRPC.User user = null;
        if (arrayList != null) {
            long clientUserId = UserConfig.getInstance(currentAccount).getClientUserId();
            int currentTime = ConnectionsManager.getInstance(currentAccount).getCurrentTime();
            i = 0;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                TLRPC.Message message = (TLRPC.Message) arrayList.get(i2);
                TLRPC.MessageMedia messageMedia = message.media;
                if (messageMedia != null && message.date + messageMedia.period > currentTime) {
                    long fromChatId = MessageObject.getFromChatId(message);
                    if (user == null && fromChatId != clientUserId) {
                        user = MessagesController.getInstance(currentAccount).getUser(Long.valueOf(fromChatId));
                    }
                    i++;
                }
            }
        } else {
            i = 0;
        }
        if (this.lastLocationSharingCount == i) {
            return;
        }
        this.lastLocationSharingCount = i;
        String string = LocaleController.getString(C2369R.string.LiveLocationContext);
        if (i == 0) {
            str = string;
        } else {
            int i3 = i - 1;
            if (LocationController.getInstance(currentAccount).isSharingLocation(dialogId)) {
                if (i3 == 0) {
                    str = String.format("%1$s - %2$s", string, LocaleController.getString(C2369R.string.ChatYourSelfName));
                } else if (i3 == 1 && user != null) {
                    str = String.format("%1$s - %2$s", string, LocaleController.formatString("SharingYouAndOtherName", C2369R.string.SharingYouAndOtherName, UserObject.getFirstName(user)));
                } else {
                    str = String.format("%1$s - %2$s %3$s", string, LocaleController.getString(C2369R.string.ChatYourSelfName), LocaleController.formatPluralString("AndOther", i3, new Object[0]));
                }
            } else if (i3 != 0) {
                str = String.format("%1$s - %2$s %3$s", string, UserObject.getFirstName(user), LocaleController.formatPluralString("AndOther", i3, new Object[0]));
            } else {
                str = String.format("%1$s - %2$s", string, UserObject.getFirstName(user));
            }
        }
        if (str.equals(this.lastString)) {
            return;
        }
        this.lastString = str;
        int iIndexOf = str.indexOf(string);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        int i4 = 0;
        while (i4 < 2) {
            AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher = this.titleTextView;
            TextView textView = i4 == 0 ? clippingTextViewSwitcher.getTextView() : clippingTextViewSwitcher.getNextTextView();
            if (textView != null) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
            }
            i4++;
        }
        if (iIndexOf >= 0) {
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold(), 0, getThemedColor(Theme.key_inappPlayerPerformer)), iIndexOf, string.length() + iIndexOf, 18);
        }
        this.titleTextView.setText(spannableStringBuilder, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPlayer(boolean z) {
        if (this.visible) {
            int i = this.currentStyle;
            if (i == 1 || i == 3) {
                return;
            }
            if ((i == 4 || i == 5) && !isPlayingVoice()) {
                return;
            }
        }
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        View fragmentView = this.fragment.getFragmentView();
        boolean z2 = (z || fragmentView == null || (fragmentView.getParent() != null && ((View) fragmentView.getParent()).getVisibility() == 0)) ? z : true;
        boolean z3 = this.visible;
        if (playingMessageObject == null || playingMessageObject.getId() == 0 || playingMessageObject.isVideo()) {
            this.lastMessageObject = null;
            boolean z4 = (!this.supportsCalls || VoIPService.getSharedInstance() == null || VoIPService.getSharedInstance().isHangingUp() || VoIPService.getSharedInstance().getCallState() == 15 || GroupCallPip.isShowing()) ? false : true;
            if (!isPlayingVoice() && !z4 && this.chatActivity != null && !GroupCallPip.isShowing()) {
                ChatObject.Call groupCall = this.chatActivity.getGroupCall();
                z4 = groupCall != null && groupCall.shouldShowPanel();
            }
            if (z4) {
                checkCall(false);
                return;
            }
            if (this.visible) {
                ActionBarMenuItem actionBarMenuItem = this.playbackSpeedButton;
                if (actionBarMenuItem != null && actionBarMenuItem.isSubMenuShowing()) {
                    this.playbackSpeedButton.toggleSubMenu();
                }
                this.visible = false;
                if (z2) {
                    if (getVisibility() != 8) {
                        setVisibility(8);
                    }
                    setTopPadding(0.0f);
                    return;
                }
                AnimatorSet animatorSet = this.animatorSet;
                if (animatorSet != null) {
                    animatorSet.cancel();
                    this.animatorSet = null;
                }
                this.notificationsLocker.lock();
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.animatorSet = animatorSet2;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this, "topPadding", 0.0f));
                this.animatorSet.setDuration(200L);
                FragmentContextViewDelegate fragmentContextViewDelegate = this.delegate;
                if (fragmentContextViewDelegate != null) {
                    fragmentContextViewDelegate.onAnimation(true, false);
                }
                this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.12
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FragmentContextView.this.notificationsLocker.unlock();
                        if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                            return;
                        }
                        FragmentContextView.this.setVisibility(8);
                        if (FragmentContextView.this.delegate != null) {
                            FragmentContextView.this.delegate.onAnimation(false, false);
                        }
                        FragmentContextView.this.animatorSet = null;
                        if (FragmentContextView.this.checkLiveStoryAfterAnimation) {
                            FragmentContextView.this.checkLiveStory(false);
                        } else if (FragmentContextView.this.checkCallAfterAnimation) {
                            FragmentContextView.this.checkCall(false);
                        } else if (FragmentContextView.this.checkPlayerAfterAnimation) {
                            FragmentContextView.this.checkPlayer(false);
                        } else if (FragmentContextView.this.checkImportAfterAnimation) {
                            FragmentContextView.this.checkImport(false);
                        }
                        FragmentContextView.this.checkLiveStoryAfterAnimation = false;
                        FragmentContextView.this.checkCallAfterAnimation = false;
                        FragmentContextView.this.checkPlayerAfterAnimation = false;
                        FragmentContextView.this.checkImportAfterAnimation = false;
                    }
                });
                this.animatorSet.start();
                return;
            }
            setVisibility(8);
            return;
        }
        checkCreateView();
        int i2 = this.currentStyle;
        if (i2 != 0 && this.animatorSet != null && !z2) {
            this.checkPlayerAfterAnimation = true;
            return;
        }
        updateStyle(0);
        updateCover(playingMessageObject);
        if (z2 && this.topPadding == 0.0f) {
            updatePaddings();
            setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
            FragmentContextViewDelegate fragmentContextViewDelegate2 = this.delegate;
            if (fragmentContextViewDelegate2 != null) {
                fragmentContextViewDelegate2.onAnimation(true, true);
                this.delegate.onAnimation(false, true);
            }
        }
        if (!this.visible) {
            if (!z2) {
                AnimatorSet animatorSet3 = this.animatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.cancel();
                    this.animatorSet = null;
                }
                this.notificationsLocker.lock();
                this.animatorSet = new AnimatorSet();
                FragmentContextView fragmentContextView = this.additionalContextView;
                if (fragmentContextView != null && fragmentContextView.getVisibility() == 0) {
                    ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.m1146dp(getStyleHeight() + this.additionalContextView.getStyleHeight());
                } else {
                    ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.m1146dp(getStyleHeight());
                }
                FragmentContextViewDelegate fragmentContextViewDelegate3 = this.delegate;
                if (fragmentContextViewDelegate3 != null) {
                    fragmentContextViewDelegate3.onAnimation(true, true);
                }
                this.animatorSet.playTogether(ObjectAnimator.ofFloat(this, "topPadding", AndroidUtilities.dp2(getStyleHeight())));
                this.animatorSet.setDuration(200L);
                this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.13
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FragmentContextView.this.notificationsLocker.unlock();
                        if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                            return;
                        }
                        if (FragmentContextView.this.delegate != null) {
                            FragmentContextView.this.delegate.onAnimation(false, true);
                        }
                        FragmentContextView.this.animatorSet = null;
                        if (FragmentContextView.this.checkLiveStoryAfterAnimation) {
                            FragmentContextView.this.checkLiveStory(false);
                        } else if (FragmentContextView.this.checkCallAfterAnimation) {
                            FragmentContextView.this.checkCall(false);
                        } else if (FragmentContextView.this.checkPlayerAfterAnimation) {
                            FragmentContextView.this.checkPlayer(false);
                        } else if (FragmentContextView.this.checkImportAfterAnimation) {
                            FragmentContextView.this.checkImport(false);
                        }
                        FragmentContextView.this.checkLiveStoryAfterAnimation = false;
                        FragmentContextView.this.checkCallAfterAnimation = false;
                        FragmentContextView.this.checkPlayerAfterAnimation = false;
                        FragmentContextView.this.checkImportAfterAnimation = false;
                    }
                });
                this.animatorSet.start();
            }
            this.visible = true;
            setVisibility(0);
        }
        if (MediaController.getInstance().isMessagePaused()) {
            this.playPauseDrawable.setPause(false, !z2);
            this.playButton.setContentDescription(LocaleController.getString(C2369R.string.AccActionPlay));
        } else {
            this.playPauseDrawable.setPause(true, !z2);
            this.playButton.setContentDescription(LocaleController.getString(C2369R.string.AccActionPause));
        }
        updateButtonsVisibility(!z2);
        if (this.lastMessageObject == playingMessageObject && i2 == 0) {
            return;
        }
        this.lastMessageObject = playingMessageObject;
        if (playingMessageObject.isVoice() || this.lastMessageObject.isRoundVideo()) {
            this.isMusic = false;
            ActionBarMenuItem actionBarMenuItem2 = this.playbackSpeedButton;
            if (actionBarMenuItem2 != null) {
                actionBarMenuItem2.setVisibility(0);
                this.playbackSpeedButton.setAlpha(1.0f);
                this.playbackSpeedButton.setEnabled(true);
            }
            int i3 = 0;
            while (i3 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher = this.titleTextView;
                TextView textView = i3 == 0 ? clippingTextViewSwitcher.getTextView() : clippingTextViewSwitcher.getNextTextView();
                if (textView != null) {
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                }
                i3++;
            }
            updatePlaybackButton(false);
        } else {
            this.isMusic = true;
            if (this.playbackSpeedButton != null) {
                if (playingMessageObject.getDuration() >= 600.0d) {
                    this.playbackSpeedButton.setVisibility(0);
                    this.playbackSpeedButton.setAlpha(1.0f);
                    this.playbackSpeedButton.setEnabled(true);
                    updatePlaybackButton(false);
                } else {
                    this.playbackSpeedButton.setVisibility(8);
                    this.playbackSpeedButton.setAlpha(0.0f);
                    this.playbackSpeedButton.setEnabled(false);
                }
            }
            int i4 = 0;
            while (i4 < 2) {
                AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher2 = this.titleTextView;
                TextView textView2 = i4 == 0 ? clippingTextViewSwitcher2.getTextView() : clippingTextViewSwitcher2.getNextTextView();
                if (textView2 != null) {
                    textView2.setEllipsize(TextUtils.TruncateAt.END);
                }
                i4++;
            }
        }
        boolean z5 = this.playbackSpeedButton.getVisibility() != 8;
        this.titleTextView.setText(this.isMusic ? playingMessageObject.getMusicTitle() : playingMessageObject.getMusicAuthor(), !z2 && z3 && this.isMusic);
        this.subtitleTextView.setText(this.isMusic ? playingMessageObject.getMusicAuthor() : playingMessageObject.getMusicTitle(), !z2 && z3 && this.isMusic);
        this.coverContainer.setVisibility(this.isMusic ? 0 : 8);
        this.nextButton.setVisibility((MediaController.getInstance().isPaused() || !this.isMusic) ? 8 : 0);
        this.prevButton.setVisibility((MediaController.getInstance().isPaused() || !this.isMusic) ? 8 : 0);
        this.closeButton.setVisibility((MediaController.getInstance().isPaused() || !this.isMusic) ? 0 : 8);
        AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher3 = this.titleTextView;
        boolean z6 = this.isMusic;
        float f = z6 ? 55.0f : 18.0f;
        int i5 = Opcodes.I2C;
        clippingTextViewSwitcher3.setLayoutParams(LayoutHelper.createFrame(-1, 20.0f, 51, f, 4.0f, (z6 ? z5 ? Opcodes.I2C : 110 : this.joinButtonWidth + 110) + (this.isSideMenued ? 64 : 0), 0.0f));
        AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher4 = this.subtitleTextView;
        boolean z7 = this.isMusic;
        float f2 = z7 ? 55.0f : 18.0f;
        if (!z7 || !z5) {
            i5 = 110;
        }
        clippingTextViewSwitcher4.setLayoutParams(LayoutHelper.createFrame(-1, 20.0f, 51, f2, 24.0f, i5 + (this.isSideMenued ? 64 : 0), 0.0f));
        this.closeButton.setLayoutParams(LayoutHelper.createFrame(36, 48.0f, 53, 0.0f, 0.0f, 2.0f, 0.0f));
        this.playbackSpeedButton.setLayoutParams(LayoutHelper.createFrame(36, 48.0f, 53, 0.0f, 0.0f, this.isMusic ? 110.0f : 74.0f, 0.0f));
        this.nextButton.setLayoutParams(LayoutHelper.createFrame(36, 48.0f, 53, 0.0f, 0.0f, this.isMusic ? 2.0f : 74.0f, 0.0f));
        this.playButton.setLayoutParams(LayoutHelper.createFrame(36, 48.0f, 53, 0.0f, 0.0f, 38.0f, 0.0f));
        this.prevButton.setLayoutParams(LayoutHelper.createFrame(36, 48.0f, 53, 0.0f, 0.0f, 74.0f, 0.0f));
        updateCover(playingMessageObject);
        updateStyle(0);
    }

    private ImageLocation getArtworkThumbImageLocation(MessageObject messageObject) {
        TLRPC.Document document = messageObject.getDocument();
        TLRPC.PhotoSize closestPhotoSizeWithSize = document != null ? FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 360) : null;
        if (!(closestPhotoSizeWithSize instanceof TLRPC.TL_photoSize) && !(closestPhotoSizeWithSize instanceof TLRPC.TL_photoSizeProgressive)) {
            closestPhotoSizeWithSize = null;
        }
        if (closestPhotoSizeWithSize != null) {
            return ImageLocation.getForDocument(closestPhotoSizeWithSize, document);
        }
        String artworkUrl = messageObject.getArtworkUrl(true);
        if (artworkUrl != null) {
            return ImageLocation.getForPath(artworkUrl);
        }
        return null;
    }

    public void checkImport(boolean z) {
        int i;
        if (this.chatActivity != null) {
            if (this.visible && ((i = this.currentStyle) == 1 || i == 3)) {
                return;
            }
            checkCreateView();
            SendMessagesHelper.ImportingHistory importingHistory = this.fragment.getSendMessagesHelper().getImportingHistory(this.chatActivity.getDialogId());
            View fragmentView = this.fragment.getFragmentView();
            if (!z && fragmentView != null && (fragmentView.getParent() == null || ((View) fragmentView.getParent()).getVisibility() != 0)) {
                z = true;
            }
            Dialog visibleDialog = this.fragment.getVisibleDialog();
            if ((isPlayingVoice() || this.chatActivity.shouldShowImport() || ((visibleDialog instanceof ImportingAlert) && !((ImportingAlert) visibleDialog).isDismissed())) && importingHistory != null) {
                importingHistory = null;
            }
            if (importingHistory == null) {
                if (this.visible && ((z && this.currentStyle == -1) || this.currentStyle == 5)) {
                    this.visible = false;
                    if (z) {
                        if (getVisibility() != 8) {
                            setVisibility(8);
                        }
                        setTopPadding(0.0f);
                        return;
                    }
                    AnimatorSet animatorSet = this.animatorSet;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                        this.animatorSet = null;
                    }
                    this.notificationsLocker.lock();
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.animatorSet = animatorSet2;
                    animatorSet2.playTogether(ObjectAnimator.ofFloat(this, "topPadding", 0.0f));
                    this.animatorSet.setDuration(220L);
                    this.animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.14
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            FragmentContextView.this.notificationsLocker.unlock();
                            if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                                return;
                            }
                            FragmentContextView.this.setVisibility(8);
                            FragmentContextView.this.animatorSet = null;
                            if (FragmentContextView.this.checkLiveStoryAfterAnimation) {
                                FragmentContextView.this.checkLiveStory(false);
                            } else if (FragmentContextView.this.checkCallAfterAnimation) {
                                FragmentContextView.this.checkCall(false);
                            } else if (FragmentContextView.this.checkPlayerAfterAnimation) {
                                FragmentContextView.this.checkPlayer(false);
                            } else if (FragmentContextView.this.checkImportAfterAnimation) {
                                FragmentContextView.this.checkImport(false);
                            }
                            FragmentContextView.this.checkLiveStoryAfterAnimation = false;
                            FragmentContextView.this.checkCallAfterAnimation = false;
                            FragmentContextView.this.checkPlayerAfterAnimation = false;
                            FragmentContextView.this.checkImportAfterAnimation = false;
                        }
                    });
                    this.animatorSet.start();
                    return;
                }
                int i2 = this.currentStyle;
                if (i2 == -1 || i2 == 5) {
                    this.visible = false;
                    setVisibility(8);
                    return;
                }
                return;
            }
            if (this.currentStyle != 5 && this.animatorSet != null && !z) {
                this.checkImportAfterAnimation = true;
                return;
            }
            updateStyle(5);
            if (z && this.topPadding == 0.0f) {
                updatePaddings();
                setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
                FragmentContextViewDelegate fragmentContextViewDelegate = this.delegate;
                if (fragmentContextViewDelegate != null) {
                    fragmentContextViewDelegate.onAnimation(true, true);
                    this.delegate.onAnimation(false, true);
                }
            }
            if (!this.visible) {
                if (!z) {
                    AnimatorSet animatorSet3 = this.animatorSet;
                    if (animatorSet3 != null) {
                        animatorSet3.cancel();
                        this.animatorSet = null;
                    }
                    this.notificationsLocker.lock();
                    this.animatorSet = new AnimatorSet();
                    FragmentContextView fragmentContextView = this.additionalContextView;
                    if (fragmentContextView != null && fragmentContextView.getVisibility() == 0) {
                        ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.m1146dp(getStyleHeight() + this.additionalContextView.getStyleHeight());
                    } else {
                        ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.m1146dp(getStyleHeight());
                    }
                    FragmentContextViewDelegate fragmentContextViewDelegate2 = this.delegate;
                    if (fragmentContextViewDelegate2 != null) {
                        fragmentContextViewDelegate2.onAnimation(true, true);
                    }
                    this.animatorSet.playTogether(ObjectAnimator.ofFloat(this, "topPadding", AndroidUtilities.dp2(getStyleHeight())));
                    this.animatorSet.setDuration(200L);
                    this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.15
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            FragmentContextView.this.notificationsLocker.unlock();
                            if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                                return;
                            }
                            if (FragmentContextView.this.delegate != null) {
                                FragmentContextView.this.delegate.onAnimation(false, true);
                            }
                            FragmentContextView.this.animatorSet = null;
                            if (FragmentContextView.this.checkLiveStoryAfterAnimation) {
                                FragmentContextView.this.checkLiveStory(false);
                            } else if (FragmentContextView.this.checkCallAfterAnimation) {
                                FragmentContextView.this.checkCall(false);
                            } else if (FragmentContextView.this.checkPlayerAfterAnimation) {
                                FragmentContextView.this.checkPlayer(false);
                            } else if (FragmentContextView.this.checkImportAfterAnimation) {
                                FragmentContextView.this.checkImport(false);
                            }
                            FragmentContextView.this.checkLiveStoryAfterAnimation = false;
                            FragmentContextView.this.checkCallAfterAnimation = false;
                            FragmentContextView.this.checkPlayerAfterAnimation = false;
                            FragmentContextView.this.checkImportAfterAnimation = false;
                        }
                    });
                    this.animatorSet.start();
                }
                this.visible = true;
                setVisibility(0);
            }
            int i3 = this.currentProgress;
            int i4 = importingHistory.uploadProgress;
            if (i3 != i4) {
                this.currentProgress = i4;
                this.titleTextView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ImportUploading", C2369R.string.ImportUploading, Integer.valueOf(i4))), false);
            }
        }
    }

    private boolean isPlayingVoice() {
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        return playingMessageObject != null && playingMessageObject.isVoice();
    }

    private boolean isPlayingRoundVideo() {
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        return playingMessageObject != null && playingMessageObject.isRoundVideo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLiveStory(boolean z) {
        View fragmentView = this.fragment.getFragmentView();
        if (!z && fragmentView != null && (fragmentView.getParent() == null || ((View) fragmentView.getParent()).getVisibility() != 0)) {
            z = true;
        }
        if (LivePlayer.recording == null) {
            boolean z2 = this.visible;
            if (z2 && ((z && this.currentStyle == -1) || this.currentStyle == 6)) {
                this.visible = false;
                if (z) {
                    if (getVisibility() != 8) {
                        setVisibility(8);
                    }
                    setTopPadding(0.0f);
                } else {
                    AnimatorSet animatorSet = this.animatorSet;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                        this.animatorSet = null;
                    }
                    this.notificationsLocker.lock();
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.animatorSet = animatorSet2;
                    animatorSet2.playTogether(ObjectAnimator.ofFloat(this, "topPadding", 0.0f));
                    this.animatorSet.setDuration(220L);
                    this.animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.16
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            FragmentContextView.this.notificationsLocker.unlock();
                            if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                                return;
                            }
                            FragmentContextView.this.setVisibility(8);
                            FragmentContextView.this.animatorSet = null;
                            if (FragmentContextView.this.checkLiveStoryAfterAnimation) {
                                FragmentContextView.this.checkLiveStory(false);
                            } else if (FragmentContextView.this.checkCallAfterAnimation) {
                                FragmentContextView.this.checkCall(false);
                            } else if (FragmentContextView.this.checkPlayerAfterAnimation) {
                                FragmentContextView.this.checkPlayer(false);
                            } else if (FragmentContextView.this.checkImportAfterAnimation) {
                                FragmentContextView.this.checkImport(false);
                            }
                            FragmentContextView.this.checkLiveStoryAfterAnimation = false;
                            FragmentContextView.this.checkCallAfterAnimation = false;
                            FragmentContextView.this.checkPlayerAfterAnimation = false;
                            FragmentContextView.this.checkImportAfterAnimation = false;
                        }
                    });
                    this.animatorSet.start();
                }
            } else if (z2 && this.currentStyle == -1) {
                this.visible = false;
                setVisibility(8);
            }
        } else {
            checkCreateView();
            int i = this.currentStyle;
            if (6 != i && this.animatorSet != null && !z) {
                this.checkLiveStoryAfterAnimation = true;
                return;
            }
            if (6 != i && this.visible && !z) {
                AnimatorSet animatorSet3 = this.animatorSet;
                if (animatorSet3 != null) {
                    animatorSet3.cancel();
                    this.animatorSet = null;
                }
                this.notificationsLocker.lock();
                AnimatorSet animatorSet4 = new AnimatorSet();
                this.animatorSet = animatorSet4;
                animatorSet4.playTogether(ObjectAnimator.ofFloat(this, "topPadding", 0.0f));
                this.animatorSet.setDuration(220L);
                this.animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.17
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        FragmentContextView.this.notificationsLocker.unlock();
                        if (FragmentContextView.this.animatorSet == null || !FragmentContextView.this.animatorSet.equals(animator)) {
                            return;
                        }
                        FragmentContextView.this.visible = false;
                        FragmentContextView.this.animatorSet = null;
                        FragmentContextView.this.checkLiveStory(false);
                    }
                });
                this.animatorSet.start();
                return;
            }
            updateStyle(6);
            if (!this.visible) {
                if (!z) {
                    AnimatorSet animatorSet5 = this.animatorSet;
                    if (animatorSet5 != null) {
                        animatorSet5.cancel();
                        this.animatorSet = null;
                    }
                    this.animatorSet = new AnimatorSet();
                    FragmentContextView fragmentContextView = this.additionalContextView;
                    if (fragmentContextView != null && fragmentContextView.getVisibility() == 0) {
                        ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.m1146dp(getStyleHeight() + this.additionalContextView.getStyleHeight());
                    } else {
                        ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = -AndroidUtilities.m1146dp(getStyleHeight());
                    }
                    this.notificationsLocker2.lock();
                    this.animatorSet.playTogether(ObjectAnimator.ofFloat(this, "topPadding", AndroidUtilities.dp2(getStyleHeight())));
                    this.animatorSet.setDuration(220L);
                    this.animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.FragmentContextView.18
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            FragmentContextView.this.notificationsLocker2.unlock();
                            if (FragmentContextView.this.animatorSet != null && FragmentContextView.this.animatorSet.equals(animator)) {
                                FragmentContextView.this.animatorSet = null;
                            }
                            if (FragmentContextView.this.checkLiveStoryAfterAnimation) {
                                FragmentContextView.this.checkLiveStory(false);
                            } else if (FragmentContextView.this.checkCallAfterAnimation) {
                                FragmentContextView.this.checkCall(false);
                            } else if (FragmentContextView.this.checkPlayerAfterAnimation) {
                                FragmentContextView.this.checkPlayer(false);
                            } else if (FragmentContextView.this.checkImportAfterAnimation) {
                                FragmentContextView.this.checkImport(false);
                            }
                            FragmentContextView.this.checkLiveStoryAfterAnimation = false;
                            FragmentContextView.this.checkCallAfterAnimation = false;
                            FragmentContextView.this.checkPlayerAfterAnimation = false;
                            FragmentContextView.this.checkImportAfterAnimation = false;
                            FragmentContextView.this.startJoinFlickerAnimation();
                        }
                    });
                    this.animatorSet.start();
                } else {
                    updatePaddings();
                    setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
                    startJoinFlickerAnimation();
                }
                this.visible = true;
                setVisibility(0);
            } else {
                updatePaddings();
                setTopPadding(AndroidUtilities.dp2(getStyleHeight()));
                setVisibility(0);
            }
        }
        LivePlayer livePlayer = LivePlayer.recording;
        if (livePlayer == null || this.currentStyle != 6) {
            return;
        }
        this.titleTextView.setText(LocaleController.formatPluralStringComma("LiveStoryTopPanelWatching", livePlayer.getWatchersCount()));
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void checkCall(boolean r17) {
        /*
            Method dump skipped, instructions count: 937
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.FragmentContextView.checkCall(boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startJoinFlickerAnimation() {
        CellFlickerDrawable cellFlickerDrawable = this.joinButtonFlicker;
        if (cellFlickerDrawable != null && cellFlickerDrawable.getProgress() >= 1.0f) {
            this.flickOnAttach = false;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.FragmentContextView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$startJoinFlickerAnimation$19();
                }
            }, 150L);
        } else {
            this.flickOnAttach = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startJoinFlickerAnimation$19() {
        this.joinButtonFlicker.setProgress(0.0f);
        this.joinButton.invalidate();
    }

    private void updateAvatars(boolean z) {
        ChatObject.Call groupCall;
        int account;
        TLRPC.User user;
        int currentAccount;
        ValueAnimator valueAnimator;
        checkCreateView();
        if (!z && (valueAnimator = this.avatars.avatarsDrawable.transitionProgressAnimator) != null) {
            valueAnimator.cancel();
            this.avatars.avatarsDrawable.transitionProgressAnimator = null;
        }
        AvatarsImageView avatarsImageView = this.avatars;
        if (avatarsImageView.avatarsDrawable.transitionProgressAnimator == null) {
            if (this.currentStyle == 4) {
                ChatActivityInterface chatActivityInterface = this.chatActivity;
                if (chatActivityInterface != null) {
                    groupCall = chatActivityInterface.getGroupCall();
                    currentAccount = this.fragment.getCurrentAccount();
                } else {
                    currentAccount = this.account;
                    groupCall = null;
                }
                account = currentAccount;
                user = null;
            } else if (VoIPService.getSharedInstance() != null) {
                groupCall = VoIPService.getSharedInstance().groupCall;
                user = this.chatActivity != null ? null : VoIPService.getSharedInstance().getUser();
                account = VoIPService.getSharedInstance().getAccount();
            } else {
                groupCall = null;
                account = this.account;
                user = null;
            }
            if (groupCall != null) {
                int size = groupCall.sortedParticipants.size();
                for (int i = 0; i < 3; i++) {
                    if (i < size) {
                        this.avatars.setObject(i, account, groupCall.sortedParticipants.get(i));
                    } else {
                        this.avatars.setObject(i, account, null);
                    }
                }
            } else if (user != null) {
                this.avatars.setObject(0, account, user);
                for (int i2 = 1; i2 < 3; i2++) {
                    this.avatars.setObject(i2, account, null);
                }
            } else {
                for (int i3 = 0; i3 < 3; i3++) {
                    this.avatars.setObject(i3, account, null);
                }
            }
            this.avatars.commitTransition(z);
            if (this.currentStyle != 4 || groupCall == null) {
                return;
            }
            int iMin = groupCall.call.rtmp_stream ? 0 : Math.min(3, groupCall.sortedParticipants.size());
            int i4 = iMin == 0 ? 10 : ((iMin - 1) * 24) + 52;
            if (z) {
                int i5 = ((FrameLayout.LayoutParams) this.titleTextView.getLayoutParams()).leftMargin;
                if (AndroidUtilities.m1146dp(i4) != i5) {
                    float translationX = (this.titleTextView.getTranslationX() + i5) - AndroidUtilities.m1146dp(r5);
                    this.titleTextView.setTranslationX(translationX);
                    this.subtitleTextView.setTranslationX(translationX);
                    ViewPropertyAnimator duration = this.titleTextView.animate().translationX(0.0f).setDuration(220L);
                    CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
                    duration.setInterpolator(cubicBezierInterpolator);
                    this.subtitleTextView.animate().translationX(0.0f).setDuration(220L).setInterpolator(cubicBezierInterpolator);
                }
            } else {
                this.titleTextView.animate().cancel();
                this.subtitleTextView.animate().cancel();
                this.titleTextView.setTranslationX(0.0f);
                this.subtitleTextView.setTranslationX(0.0f);
            }
            float f = i4;
            this.titleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 20.0f, 51, f, 5.0f, (this.isSideMenued ? 64 : 0) + (groupCall.isScheduled() ? 90 : 36), 0.0f));
            this.subtitleTextView.setLayoutParams(LayoutHelper.createFrame(-1, 20.0f, 51, f, 25.0f, (this.isSideMenued ? 64 : 0) + (groupCall.isScheduled() ? 90 : 36), 0.0f));
            return;
        }
        avatarsImageView.updateAfterTransitionEnd();
    }

    public void setCollapseTransition(boolean z, float f, float f2) {
        this.collapseTransition = z;
        this.extraHeight = f;
        this.collapseProgress = f2;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        Canvas canvas2;
        boolean z;
        if (this.frameLayout == null) {
            return;
        }
        if (!this.drawOverlay || getVisibility() == 0) {
            int i = this.currentStyle;
            if (i == 3 || i == 1) {
                Theme.getFragmentContextViewWavesDrawable().updateState(this.wasDraw);
                float fM1146dp = this.topPadding / AndroidUtilities.m1146dp(getStyleHeight());
                if (this.collapseTransition) {
                    Theme.getFragmentContextViewWavesDrawable().draw(0.0f, (AndroidUtilities.m1146dp(getStyleHeight()) - this.topPadding) + this.extraHeight, getMeasuredWidth(), getMeasuredHeight() - AndroidUtilities.m1146dp(2.0f), canvas, null, Math.min(fM1146dp, 1.0f - this.collapseProgress));
                } else {
                    Theme.getFragmentContextViewWavesDrawable().draw(0.0f, AndroidUtilities.m1146dp(getStyleHeight()) - this.topPadding, getMeasuredWidth(), getMeasuredHeight() - AndroidUtilities.m1146dp(2.0f), canvas, this, fM1146dp);
                }
                float fM1146dp2 = AndroidUtilities.m1146dp(getStyleHeight()) - this.topPadding;
                if (this.collapseTransition) {
                    fM1146dp2 += this.extraHeight;
                }
                if (fM1146dp2 > getMeasuredHeight()) {
                    return;
                }
                canvas.save();
                canvas2 = canvas;
                canvas2.clipRect(0.0f, fM1146dp2, getMeasuredWidth(), getMeasuredHeight());
                invalidate();
                z = true;
            } else {
                z = false;
                canvas2 = canvas;
            }
            super.dispatchDraw(canvas);
            if (z) {
                canvas2.restore();
            }
            this.wasDraw = true;
        }
    }

    public void setDrawOverlay(boolean z) {
        this.drawOverlay = z;
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        int i = this.currentStyle;
        if ((i == 3 || i == 1) && getParent() != null) {
            ((View) getParent()).invalidate();
        }
    }

    public boolean isCallStyle() {
        int i = this.currentStyle;
        return i == 3 || i == 1;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        updatePaddings();
        setTopPadding(this.topPadding);
        if (i == 8) {
            this.wasDraw = false;
        }
    }

    private void updatePaddings() {
        int iM1146dp = getVisibility() == 0 ? 0 - AndroidUtilities.m1146dp(getStyleHeight()) : 0;
        FragmentContextView fragmentContextView = this.additionalContextView;
        if (fragmentContextView != null && fragmentContextView.getVisibility() == 0) {
            int iM1146dp2 = iM1146dp - AndroidUtilities.m1146dp(this.additionalContextView.getStyleHeight());
            ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = iM1146dp2;
            ((FrameLayout.LayoutParams) this.additionalContextView.getLayoutParams()).topMargin = iM1146dp2;
            return;
        }
        ((FrameLayout.LayoutParams) getLayoutParams()).topMargin = iM1146dp;
    }

    @Override // org.telegram.messenger.voip.VoIPService.StateListener
    public void onStateChanged(int i) {
        updateCallTitle();
    }

    public void updateCover(MessageObject messageObject) {
        BackupImageView imageView = this.coverContainer.getImageView();
        AudioInfo audioInfo = MediaController.getInstance().getAudioInfo();
        if (audioInfo != null && audioInfo.getCover() != null) {
            imageView.setImageBitmap(audioInfo.getCover());
            this.currentFile = null;
        } else {
            this.currentFile = FileLoader.getAttachFileName(messageObject.getDocument());
            String artworkUrl = messageObject.getArtworkUrl(false);
            ImageLocation artworkThumbImageLocation = getArtworkThumbImageLocation(messageObject);
            if (!TextUtils.isEmpty(artworkUrl)) {
                imageView.setImage(ImageLocation.getForPath(artworkUrl), (String) null, artworkThumbImageLocation, (String) null, (String) null, 0L, 1, messageObject);
            } else if (artworkThumbImageLocation != null) {
                imageView.setImage((ImageLocation) null, (String) null, artworkThumbImageLocation, (String) null, (String) null, 0L, 1, messageObject);
            } else {
                imageView.setImageResource(C2369R.drawable.nocover, Theme.getColor(Theme.key_player_button));
            }
        }
        imageView.invalidate();
    }

    /* loaded from: classes6.dex */
    private static class CoverContainer extends FrameLayout {
        private final BackupImageView imageView;

        public CoverContainer(Context context) {
            super(context);
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            backupImageView.setClipToOutline(true);
            backupImageView.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.FragmentContextView.CoverContainer.1
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), AndroidUtilities.m1146dp(4.0f));
                }
            });
            backupImageView.setRoundRadius(AndroidUtilities.m1146dp(4.0f));
            addView(backupImageView, LayoutHelper.createFrame(-1, -1.0f));
        }

        public final BackupImageView getImageView() {
            return this.imageView;
        }
    }

    private void updateCallTitle() {
        ChatObject.Call call;
        checkCreateView();
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance != null) {
            int i = this.currentStyle;
            if (i == 1 || i == 3) {
                int callState = sharedInstance.getCallState();
                if (!sharedInstance.isSwitchingStream() && (callState == 1 || callState == 2 || callState == 6 || callState == 5)) {
                    this.titleTextView.setText(LocaleController.getString(C2369R.string.VoipGroupConnecting), false);
                    return;
                }
                if (sharedInstance.isConference() && (call = sharedInstance.groupCall) != null) {
                    if (call.sortedParticipants.size() <= 1) {
                        this.titleTextView.setText(LocaleController.getString(C2369R.string.ConferenceChat), false);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < Math.min(3, sharedInstance.groupCall.sortedParticipants.size()); i2++) {
                        if (i2 > 0) {
                            sb.append(", ");
                        }
                        sb.append(DialogObject.getShortName(sharedInstance.getAccount(), DialogObject.getPeerDialogId(sharedInstance.groupCall.sortedParticipants.get(i2).peer)));
                    }
                    if (sharedInstance.groupCall.sortedParticipants.size() > 3) {
                        sb.append(" ");
                        sb.append(LocaleController.formatPluralString("AndOther", sharedInstance.groupCall.sortedParticipants.size() - 3, new Object[0]));
                    }
                    this.titleTextView.setText(sb.toString(), false);
                    return;
                }
                if (sharedInstance.getChat() != null) {
                    if (!TextUtils.isEmpty(sharedInstance.groupCall.call.title)) {
                        this.titleTextView.setText(sharedInstance.groupCall.call.title, false);
                        return;
                    }
                    ChatActivityInterface chatActivityInterface = this.chatActivity;
                    if (chatActivityInterface != null && chatActivityInterface.getCurrentChat() != null && this.chatActivity.getCurrentChat().f1571id == sharedInstance.getChat().f1571id) {
                        TLRPC.Chat currentChat = this.chatActivity.getCurrentChat();
                        if (VoIPService.hasRtmpStream()) {
                            this.titleTextView.setText(LocaleController.getString(C2369R.string.VoipChannelViewVoiceChat), false);
                            return;
                        } else if (ChatObject.isChannelOrGiga(currentChat)) {
                            this.titleTextView.setText(LocaleController.getString(C2369R.string.VoipChannelViewVoiceChat), false);
                            return;
                        } else {
                            this.titleTextView.setText(LocaleController.getString(C2369R.string.VoipGroupViewVoiceChat), false);
                            return;
                        }
                    }
                    this.titleTextView.setText(sharedInstance.getChat().title, false);
                    return;
                }
                if (sharedInstance.getUser() != null) {
                    TLRPC.User user = sharedInstance.getUser();
                    ChatActivityInterface chatActivityInterface2 = this.chatActivity;
                    if (chatActivityInterface2 != null && chatActivityInterface2.getCurrentUser() != null && this.chatActivity.getCurrentUser().f1734id == user.f1734id) {
                        this.titleTextView.setText(LocaleController.getString(C2369R.string.ReturnToCall));
                    } else {
                        this.titleTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                    }
                }
            }
        }
    }

    private int getTitleTextColor() {
        int i = this.currentStyle;
        if (i == 4) {
            return getThemedColor(Theme.key_inappPlayerPerformer);
        }
        if (i == 1 || i == 3) {
            return getThemedColor(Theme.key_returnToCallText);
        }
        return getThemedColor(Theme.key_inappPlayerTitle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void updateButtonsVisibility(boolean z) {
        boolean zIsPaused = MediaController.getInstance().isPaused();
        ImageView imageView = this.closeButton;
        if (imageView != null) {
            AndroidUtilities.updateViewVisibilityAnimated(imageView, zIsPaused || isPlayingVoice() || isPlayingRoundVideo(), 0.5f, z);
        }
        if (this.nextButton != null && !isPlayingVoice() && !isPlayingRoundVideo()) {
            AndroidUtilities.updateViewVisibilityAnimated(this.nextButton, !zIsPaused, 0.5f, z);
        }
        if (this.prevButton != null && !isPlayingVoice() && !isPlayingRoundVideo()) {
            AndroidUtilities.updateViewVisibilityAnimated(this.prevButton, !zIsPaused, 0.5f, z);
        }
        ActionBarMenuItem actionBarMenuItem = this.playbackSpeedButton;
        if (actionBarMenuItem == null || actionBarMenuItem.getVisibility() != 0) {
            return;
        }
        if (!isPlayingVoice() && !isPlayingRoundVideo()) {
            this.playbackSpeedButton.animate().setDuration(250L).translationX(AndroidUtilities.m1146dp(zIsPaused ? 36.0f : 0.0f)).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
        } else {
            this.playbackSpeedButton.animate().cancel();
            this.playbackSpeedButton.setTranslationX(0.0f);
        }
    }

    public void toggleScheduledNotify() {
        ChatActivityInterface chatActivityInterface;
        ChatObject.Call groupCall;
        if (this.fragment == null || (chatActivityInterface = this.chatActivity) == null || (groupCall = chatActivityInterface.getGroupCall()) == null || groupCall.call == null) {
            return;
        }
        if (this.toggleGroupCallStartSubscriptionReqId != 0) {
            this.fragment.getConnectionsManager().cancelRequest(this.toggleGroupCallStartSubscriptionReqId, true);
            this.toggleGroupCallStartSubscriptionReqId = 0;
        }
        TL_phone.toggleGroupCallStartSubscription togglegroupcallstartsubscription = new TL_phone.toggleGroupCallStartSubscription();
        togglegroupcallstartsubscription.call = groupCall.getInputGroupCall();
        TLRPC.GroupCall groupCall2 = groupCall.call;
        boolean z = true ^ this.willBeNotified;
        this.willBeNotified = z;
        groupCall2.schedule_start_subscribed = z;
        togglegroupcallstartsubscription.subscribed = z;
        this.toggleGroupCallStartSubscriptionReqId = this.fragment.getConnectionsManager().sendRequest(togglegroupcallstartsubscription, null);
        if (this.scheduleRunnableScheduled) {
            AndroidUtilities.cancelRunOnUIThread(this.updateScheduleTimeRunnable);
            this.scheduleRunnableScheduled = false;
        }
        this.updateScheduleTimeRunnable.run();
        BulletinFactory bulletinFactoryM1267of = BulletinFactory.m1267of(this.fragment);
        boolean z2 = this.willBeNotified;
        bulletinFactoryM1267of.createSimpleBulletin(z2 ? C2369R.raw.silent_unmute : C2369R.raw.silent_mute, LocaleController.getString(z2 ? C2369R.string.LiveStreamWillNotify : C2369R.string.LiveStreamWillNotNotify)).show();
    }

    public void setLeftMargin(float f) {
        if (this.frameLayout == null) {
            this.leftMargin = f;
            return;
        }
        RLottieImageView rLottieImageView = this.importingImageView;
        if (rLottieImageView != null) {
            rLottieImageView.setTranslationX(f);
        }
        AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher = this.titleTextView;
        if (clippingTextViewSwitcher != null) {
            clippingTextViewSwitcher.setTranslationX(f);
        }
        AudioPlayerAlert.ClippingTextViewSwitcher clippingTextViewSwitcher2 = this.subtitleTextView;
        if (clippingTextViewSwitcher2 != null) {
            clippingTextViewSwitcher2.setTranslationX(f);
        }
        CoverContainer coverContainer = this.coverContainer;
        if (coverContainer != null) {
            coverContainer.setTranslationX(f);
        }
        View view = this.divider;
        if (view != null) {
            view.setTranslationX(f);
        }
        AvatarsImageView avatarsImageView = this.avatars;
        if (avatarsImageView != null) {
            avatarsImageView.setTranslationX(f);
        }
    }

    /* loaded from: classes6.dex */
    private static class CallMessageItem implements Destroyable {
        private final GroupCallMessageCell cell;
        private final ViewGroup parent;

        public CallMessageItem(ViewGroup viewGroup, GroupCallMessage groupCallMessage) {
            GroupCallMessageCell groupCallMessageCell = new GroupCallMessageCell(viewGroup.getContext());
            this.cell = groupCallMessageCell;
            groupCallMessageCell.setBackgroundColor(ColorUtils.setAlphaComponent(-16777216, 34));
            groupCallMessageCell.setSingleLine();
            groupCallMessageCell.set(groupCallMessage);
            groupCallMessageCell.setAlpha(0.0f);
            this.parent = viewGroup;
            viewGroup.addView(groupCallMessageCell);
        }

        @Override // me.vkryl.core.lambda.Destroyable
        public void performDestroy() {
            this.parent.removeView(this.cell);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onItemChanged(ReplaceAnimator replaceAnimator) {
        float totalVisibility = 1.0f - this.callMessagesAnimator.getMetadata().getTotalVisibility();
        this.titleTextView.setAlpha(totalVisibility);
        this.titleTextView.setScaleX(AndroidUtilities.lerp(0.7f, 1.0f, totalVisibility));
        this.titleTextView.setScaleY(AndroidUtilities.lerp(0.7f, 1.0f, totalVisibility));
        Iterator it = this.callMessagesAnimator.iterator();
        while (it.hasNext()) {
            ListAnimator.Entry entry = (ListAnimator.Entry) it.next();
            float fLerp = AndroidUtilities.lerp(0.7f, 1.0f, entry.getVisibility());
            ((CallMessageItem) entry.item).cell.setAlpha(entry.getVisibility());
            ((CallMessageItem) entry.item).cell.setScaleX(fLerp);
            ((CallMessageItem) entry.item).cell.setScaleY(fLerp);
        }
    }

    @Override // org.telegram.messenger.voip.GroupCallMessagesController.CallMessageListener
    public void onNewGroupCallMessage(long j, GroupCallMessage groupCallMessage) {
        if (this.groupCallMessagesContainer == null) {
            return;
        }
        int i = this.currentStyle;
        if ((i == 1 || i == 3) && VoIPService.getSharedInstance() != null && VoIPService.getSharedInstance().getGroupCallID() == j) {
            this.groupCallMessageCounter++;
            if (groupCallMessage.isOut()) {
                return;
            }
            this.callMessagesAnimator.replace(new CallMessageItem(this.groupCallMessagesContainer, groupCallMessage), true);
        }
    }

    @Override // org.telegram.messenger.voip.GroupCallMessagesController.CallMessageListener
    public void onPopGroupCallMessage() {
        int i = this.groupCallMessageCounter;
        if (i > 0) {
            int i2 = i - 1;
            this.groupCallMessageCounter = i2;
            if (i2 == 0) {
                this.callMessagesAnimator.replace(null, true);
            }
        }
    }
}
