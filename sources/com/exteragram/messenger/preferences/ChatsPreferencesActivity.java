package com.exteragram.messenger.preferences;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.FrameLayout;
import androidx.camera.core.ImageCapture$$ExternalSyntheticBackport1;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.p003ai.AiController;
import com.exteragram.messenger.p003ai.p004ui.activities.AiPreferencesActivity;
import com.exteragram.messenger.preferences.components.AltSeekbar;
import com.exteragram.messenger.preferences.components.CustomPreferenceCell;
import com.exteragram.messenger.preferences.components.DoubleTapCell;
import com.exteragram.messenger.preferences.components.StickerShapeCell;
import com.exteragram.messenger.preferences.components.StickerSizePreviewCell;
import com.exteragram.messenger.speech.VoiceRecognitionController;
import com.exteragram.messenger.speech.p010ui.LoadingModelView;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import org.mvel2.Operator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.TextCheckCell2;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Stories.recorder.DualCameraView;
import org.telegram.p023ui.ThemeActivity;
import p017j$.util.Collection;
import p017j$.util.Objects;
import p017j$.util.function.Function$CC;
import p017j$.util.function.Predicate$CC;

/* loaded from: classes.dex */
public class ChatsPreferencesActivity extends BasePreferencesActivity {
    private boolean cameraSettingsExpanded;
    private DoubleTapCell doubleTapCell;
    private boolean messageMenuExpanded;
    private boolean pauseOnMinimizeExpanded;
    private boolean quickTransitionsExpanded;
    private boolean replyElementsExpanded;
    private ActionBarMenuItem resetItem;
    private StickerShapeCell stickerShapeCell;
    private StickerSizeCell stickerSizeCell;
    private final CharSequence[] doubleTapActions = {LocaleController.getString(C2369R.string.Disable), LocaleController.getString(C2369R.string.Reactions), LocaleController.getString(C2369R.string.Reply), LocaleController.getString(C2369R.string.Copy), LocaleController.getString(C2369R.string.Forward), LocaleController.getString(C2369R.string.Edit), LocaleController.getString(C2369R.string.Save), LocaleController.getString(C2369R.string.Delete), LocaleController.getString(C2369R.string.TranslateMessage), LocaleController.getString(C2369R.string.ReadUntilMenuText)};
    private final CharSequence[] doubleTapOutActions = {LocaleController.getString(C2369R.string.Disable), LocaleController.getString(C2369R.string.Reactions), LocaleController.getString(C2369R.string.Reply), LocaleController.getString(C2369R.string.Copy), LocaleController.getString(C2369R.string.Forward), LocaleController.getString(C2369R.string.Edit), LocaleController.getString(C2369R.string.Save), LocaleController.getString(C2369R.string.Delete), LocaleController.getString(C2369R.string.TranslateMessage)};
    private final CharSequence[] bottomButton = {LocaleController.getString(C2369R.string.Hide), LocaleUtils.capitalize(LocaleController.getString(C2369R.string.ChannelMute)), LocaleUtils.capitalize(LocaleController.getString(C2369R.string.ChannelDiscuss))};
    private final CharSequence[] videoMessagesCamera = {LocaleController.getString(C2369R.string.VideoMessagesCameraFront), LocaleController.getString(C2369R.string.VideoMessagesCameraRear), LocaleController.getString(C2369R.string.VideoMessagesCameraAsk)};
    private final CharSequence[] doubleTapSeekDuration = {LocaleController.formatPluralString("Seconds", 5, new Object[0]), LocaleController.formatPluralString("Seconds", 10, new Object[0]), LocaleController.formatPluralString("Seconds", 15, new Object[0]), LocaleController.formatPluralString("Seconds", 30, new Object[0])};
    private final CharSequence[] cameraType = {"Camera 1", "Camera 2", "Camera X"};
    private final int[] doubleTapIcons = {C2369R.drawable.msg_block, C2369R.drawable.msg_reactions2, C2369R.drawable.menu_reply, C2369R.drawable.msg_copy, C2369R.drawable.msg_forward, C2369R.drawable.msg_edit, C2369R.drawable.msg_saved, C2369R.drawable.msg_delete, C2369R.drawable.msg_translate, C2369R.drawable.msg_view_file};
    private final List languageCodes = ImageCapture$$ExternalSyntheticBackport1.m42m(new String[]{"none", "en", "es", "zh", "hi", "fa", "fr", "ru", "pt", "de", "ja", "ko", "it", "uk", "gu", "pl", "nl", "tr", "vi", "cs", "uz", "eo", "kk", "tg", "ca"});

    public enum ChatsItem {
        STICKER_SIZE,
        HIDE_STICKER_TIME,
        REPLY_ELEMENTS,
        REPLY_COLORS,
        REPLY_EMOJI,
        REPLY_BACKGROUND,
        STICKER_SHAPE,
        AI,
        CHAT_SETTINGS,
        UNLIMITED_RECENT_STICKERS,
        HIDE_REACTIONS,
        DOUBLE_TAP,
        DOUBLE_TAP_ACTION,
        DOUBLE_TAP_ACTION_OUT_OWNER,
        BOTTOM_BUTTON,
        ADMIN_SHORTCUTS,
        QUICK_TRANSITIONS,
        QUICK_TRANSITION_FOR_CHANNELS,
        QUICK_TRANSITION_FOR_TOPICS,
        HIDE_KEYBOARD_ON_SCROLL,
        ADD_COMMA_AFTER_MENTION,
        HIDE_SEND_AS_PEER,
        HIDE_SHARE_BUTTON,
        REPLACE_EDITED_WITH_ICON,
        MESSAGE_MENU,
        COPY_PHOTO,
        SAVE,
        CLEAR,
        HISTORY,
        REPORT,
        GENERATE,
        DETAILS,
        GROUP_MESSAGE_MENU,
        SPEECH_RECOGNITION_LANGUAGE,
        POST_PROCESSING_WITH_AI,
        CAMERA_TYPE,
        CAMERA_SETTINGS,
        DUAL_CAMERA,
        EXTENDED_FRAMES_PER_SECOND,
        CAMERA_STABILIZATION,
        CAMERA_MIRROR_MODE,
        VIDEO_MESSAGES_CAMERA,
        REMEMBER_LAST_USED_CAMERA,
        STATIC_ZOOM,
        ALWAYS_SEND_IN_HD,
        HIDE_COUNTER,
        HIDE_CAMERA_TILE,
        DOUBLE_TAP_SEEK_DURATION,
        PREFER_ORIGINAL_QUALITY,
        SWIPE_TO_PIP,
        UNMUTE_WITH_VOLUME_BUTTONS,
        PAUSE_ON_MINIMIZE,
        PAUSE_ON_MINIMIZE_VIDEO,
        PAUSE_ON_MINIMIZE_VOICE,
        PAUSE_ON_MINIMIZE_ROUND;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.stickerSizeCell = new StickerSizeCell(context);
        this.stickerShapeCell = new StickerShapeCell(context) { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity.1
            @Override // com.exteragram.messenger.preferences.components.StickerShapeCell
            protected void updateStickerPreview() {
                ((BaseFragment) ChatsPreferencesActivity.this).parentLayout.rebuildFragments(0);
                ChatsPreferencesActivity.this.stickerSizeCell.invalidate();
            }
        };
        this.doubleTapCell = new DoubleTapCell(context);
        View viewCreateView = super.createView(context);
        ActionBarMenuItem actionBarMenuItemAddItem = this.actionBar.createMenu().addItem(0, C2369R.drawable.msg_reset);
        this.resetItem = actionBarMenuItemAddItem;
        actionBarMenuItemAddItem.setContentDescription(LocaleController.getString(C2369R.string.Reset));
        this.resetItem.setVisibility(ExteraConfig.stickerSize == 12.0f ? 8 : 0);
        this.resetItem.setTag(null);
        this.resetItem.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda49
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$1(view);
            }
        });
        this.fragmentView = viewCreateView;
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(View view) {
        AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, false, 0.5f, true);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(ExteraConfig.stickerSize, 12.0f);
        valueAnimatorOfFloat.setDuration(200L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda50
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createView$0(valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.stickerSize = fFloatValue;
        editor.putFloat("stickerSize", fFloatValue).apply();
        this.stickerSizeCell.seekBar.setProgress((fFloatValue - 4.0f) / 16.0f);
        this.stickerSizeCell.invalidate();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.SearchAllChatsShort);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asCustom(ChatsItem.STICKER_SIZE.getId(), this.stickerSizeCell).setLinkAlias("stickerSize", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_STICKER_TIME.getId(), LocaleController.getString(C2369R.string.StickerTime)).setChecked(ExteraConfig.hideStickerTime).setSearchable(this).setLinkAlias("hideStickerTime", this));
        int id = ChatsItem.REPLY_ELEMENTS.getId();
        String string = LocaleController.getString(C2369R.string.RepliesTitle);
        Locale locale = Locale.US;
        arrayList.add(UItem.asExteraExpandableSwitch(id, string, String.format(locale, "%d/%d", Integer.valueOf(getReplyElementsSelectedCount(false)), Integer.valueOf(getReplyElementsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleReplyElementsSwitchClick(view);
            }
        }).setChecked(getReplyElementsSelectedCount(false) > 0).setCollapsed(!this.replyElementsExpanded).setSearchable(this).setLinkAlias("replyElements", this));
        if (this.replyElementsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPLY_COLORS.getId(), LocaleController.getString(C2369R.string.BackgroundColors)).setChecked(ExteraConfig.replyColors).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPLY_EMOJI.getId(), LocaleController.getString(C2369R.string.Emoji)).setChecked(ExteraConfig.replyEmoji).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPLY_BACKGROUND.getId(), LocaleController.getString(C2369R.string.ReplyBackground)).setChecked(ExteraConfig.replyBackground).pad());
        }
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.StickerShape)));
        arrayList.add(UItem.asCustom(ChatsItem.STICKER_SHAPE.getId(), this.stickerShapeCell).setLinkAlias("stickerShape", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asButtonWithSubtext(ChatsItem.AI.getId(), C2369R.drawable.ai_chat, LocaleController.getString(C2369R.string.AIChat), LocaleController.getString(C2369R.string.AIChatInfo), 64, 60).setSearchable(this).setLinkAlias("aiChat", this));
        arrayList.add(UItem.asButtonWithSubtext(ChatsItem.CHAT_SETTINGS.getId(), C2369R.drawable.msg_discussion, LocaleController.getString(C2369R.string.ChatSettings), LocaleController.getString(C2369R.string.ChatSettingsInfo), 64, 60).setLinkAlias("chatSettings", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.StickersName)));
        arrayList.add(UItem.asCheck(ChatsItem.UNLIMITED_RECENT_STICKERS.getId(), LocaleController.getString(C2369R.string.UnlimitedRecentStickers)).setChecked(ExteraConfig.unlimitedRecentStickers).setSearchable(this).setLinkAlias("unlimitedRecentStickers", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_REACTIONS.getId(), LocaleController.getString(C2369R.string.HideReactions)).setChecked(ExteraConfig.hideReactions).setSearchable(this).setLinkAlias("hideReactions", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.HideReactionsInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.DoubleTap)));
        arrayList.add(UItem.asCustom(ChatsItem.DOUBLE_TAP.getId(), this.doubleTapCell));
        arrayList.add(UItem.asButton(ChatsItem.DOUBLE_TAP_ACTION.getId(), LocaleController.getString(C2369R.string.DoubleTapIncoming), this.doubleTapActions[ExteraConfig.doubleTapAction]).setSearchable(this).setLinkAlias("doubleTapIncoming", this));
        arrayList.add(UItem.asButton(ChatsItem.DOUBLE_TAP_ACTION_OUT_OWNER.getId(), LocaleController.getString(C2369R.string.DoubleTapOutgoing), this.doubleTapOutActions[ExteraConfig.doubleTapActionOutOwner]).setSearchable(this).setLinkAlias("doubleTapOutgoing", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.DoubleTapInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Chats)));
        arrayList.add(UItem.asButton(ChatsItem.BOTTOM_BUTTON.getId(), LocaleController.getString(C2369R.string.BottomButton), this.bottomButton[ExteraConfig.bottomButton]).setSearchable(this).setLinkAlias("bottomButton", this));
        arrayList.add(UItem.asCheck(ChatsItem.ADMIN_SHORTCUTS.getId(), LocaleController.getString(C2369R.string.AdminShortcuts)).setChecked(ExteraConfig.quickAdminShortcuts).setSearchable(this).setLinkAlias("adminShortcuts", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.QUICK_TRANSITIONS.getId(), LocaleController.getString(C2369R.string.QuickTransitions), String.format(locale, "%d/%d", Integer.valueOf(getQuickTransitionsSelectedCount(false)), Integer.valueOf(getQuickTransitionsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleQuickTransitionsSwitchClick(view);
            }
        }).setChecked(getQuickTransitionsSelectedCount(false) > 0).setCollapsed(!this.quickTransitionsExpanded).setSearchable(this).setLinkAlias("quickTransitions", this));
        if (this.quickTransitionsExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.QUICK_TRANSITION_FOR_CHANNELS.getId(), LocaleController.getString(C2369R.string.FilterChannels)).setChecked(ExteraConfig.quickTransitionForChannels).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.QUICK_TRANSITION_FOR_TOPICS.getId(), LocaleController.getString(C2369R.string.Topics)).setChecked(ExteraConfig.quickTransitionForTopics).pad());
        }
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_KEYBOARD_ON_SCROLL.getId(), LocaleController.getString(C2369R.string.HideKeyboardOnScroll)).setChecked(ExteraConfig.hideKeyboardOnScroll).setSearchable(this).setLinkAlias("hideKeyboardOnScroll", this));
        arrayList.add(UItem.asCheck(ChatsItem.ADD_COMMA_AFTER_MENTION.getId(), LocaleController.getString(C2369R.string.AddCommaAfterMention)).setChecked(ExteraConfig.addCommaAfterMention).setSearchable(this).setLinkAlias("addCommaAfterMention", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_SEND_AS_PEER.getId(), LocaleController.getString(C2369R.string.HideSendAsPeer)).setChecked(ExteraConfig.hideSendAsPeer).setSearchable(this).setLinkAlias("hideSendAsPeer", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.HideSendAsPeerInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.MessagesChartTitle)));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_SHARE_BUTTON.getId(), LocaleController.formatString(C2369R.string.HideShareButton, LocaleController.getString(C2369R.string.ShareFile))).setChecked(ExteraConfig.hideShareButton).setSearchable(this).setLinkAlias("hideShareButton", this));
        arrayList.add(UItem.asCheck(ChatsItem.REPLACE_EDITED_WITH_ICON.getId(), LocaleController.formatString(C2369R.string.ReplaceEditedWithIcon, LocaleController.getString(C2369R.string.EditedMessage))).setChecked(ExteraConfig.replaceEditedWithIcon).setSearchable(this).setLinkAlias("replaceEditedWithIcon", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.MESSAGE_MENU.getId(), LocaleController.getString(C2369R.string.MessageMenu), String.format(locale, "%d/%d", Integer.valueOf(getMessageMenuSelectedCount(false)), Integer.valueOf(getMessageMenuSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMessageMenuSwitchClick(view);
            }
        }).setChecked(getMessageMenuSelectedCount(false) > 0).setCollapsed(!this.messageMenuExpanded).setSearchable(this).setLinkAlias("messageMenu", this));
        if (this.messageMenuExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.COPY_PHOTO.getId(), LocaleController.getString(C2369R.string.CopyPhoto)).setChecked(ExteraConfig.showCopyPhotoButton).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.SAVE.getId(), LocaleController.getString(C2369R.string.Save)).setChecked(ExteraConfig.showSaveMessageButton).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.CLEAR.getId(), LocaleController.getString(C2369R.string.Clear)).setChecked(ExteraConfig.showClearButton).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.HISTORY.getId(), LocaleController.getString(C2369R.string.MessageHistory)).setChecked(ExteraConfig.showHistoryButton).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.REPORT.getId(), LocaleController.getString(C2369R.string.ReportChat)).setChecked(ExteraConfig.showReportButton).pad());
            if (AiController.canUseAI()) {
                arrayList.add(UItem.asRoundCheckbox(ChatsItem.GENERATE.getId(), LocaleController.getString(C2369R.string.Generate)).setChecked(ExteraConfig.showGenerateButton).pad());
            }
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.DETAILS.getId(), LocaleController.getString(C2369R.string.Details)).setChecked(ExteraConfig.showDetailsButton).pad());
        }
        arrayList.add(UItem.asCheck(ChatsItem.GROUP_MESSAGE_MENU.getId(), LocaleController.getString(C2369R.string.GroupMessageMenu)).setChecked(ExteraConfig.groupMessageMenu).setSearchable(this).setLinkAlias("groupMessageMenu", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.GroupMessageMenuInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.PremiumPreviewVoiceToText)));
        arrayList.add(UItem.asButton(ChatsItem.SPEECH_RECOGNITION_LANGUAGE.getId(), LocaleController.getString(C2369R.string.RecognitionLanguage), TranslatorUtils.getLanguageTitleSystem(ExteraConfig.recognitionLanguage)).setSearchable(this).setLinkAlias("recognitionLanguage", this));
        if (AiController.canUseAI()) {
            arrayList.add(UItem.asCheck(ChatsItem.POST_PROCESSING_WITH_AI.getId(), LocaleController.getString(C2369R.string.PostProcessingWithAi), LocaleController.getString(C2369R.string.PostProcessingWithAiInfo), true).setChecked(ExteraConfig.postprocessingWithAi).setSearchable(this).setLinkAlias("postprocessingWithAi", this));
        }
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.RecognitionInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.VoipCamera)));
        arrayList.add(UItem.asButton(ChatsItem.CAMERA_TYPE.getId(), LocaleController.getString(C2369R.string.CameraType), this.cameraType[ExteraConfig.cameraType]).setSearchable(this).setLinkAlias("cameraType", this));
        if (ExteraConfig.cameraType != 0) {
            arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.CAMERA_SETTINGS.getId(), LocaleController.getString(C2369R.string.ExtendedSettings), String.format(locale, "%d/%d", Integer.valueOf(getCameraSettingsSelectedCount(false)), Integer.valueOf(getCameraSettingsSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.handleCameraSettingsSwitchClick(view);
                }
            }).setChecked(getCameraSettingsSelectedCount(false) > 0).setCollapsed(!this.cameraSettingsExpanded).setSearchable(this).setLinkAlias("cameraSettings", this));
            if (this.cameraSettingsExpanded) {
                if (ExteraConfig.cameraType != 2) {
                    arrayList.add(UItem.asRoundCheckbox(ChatsItem.DUAL_CAMERA.getId(), LocaleController.getString(C2369R.string.SeamlessSwitching)).setChecked(DualCameraView.roundDualAvailableStatic(getContext())).pad());
                }
                arrayList.add(UItem.asRoundCheckbox(ChatsItem.EXTENDED_FRAMES_PER_SECOND.getId(), LocaleController.getString(C2369R.string.ExtendedFramesPerSecond)).setChecked(ExteraConfig.extendedFramesPerSecond).pad());
                arrayList.add(UItem.asRoundCheckbox(ChatsItem.CAMERA_STABILIZATION.getId(), LocaleController.getString(C2369R.string.CameraStabilization)).setChecked(ExteraConfig.cameraStabilization).pad());
                if (ExteraConfig.cameraType != 1) {
                    arrayList.add(UItem.asRoundCheckbox(ChatsItem.CAMERA_MIRROR_MODE.getId(), LocaleController.getString(C2369R.string.CameraMirrorMode)).setChecked(ExteraConfig.cameraMirrorMode).pad());
                }
            }
        }
        arrayList.add(UItem.asButton(ChatsItem.VIDEO_MESSAGES_CAMERA.getId(), LocaleController.getString(C2369R.string.VideoMessagesCamera), this.videoMessagesCamera[ExteraConfig.videoMessagesCamera]).setSearchable(this).setLinkAlias("videoMessagesCamera", this));
        if (ExteraConfig.videoMessagesCamera != 2) {
            arrayList.add(UItem.asCheck(ChatsItem.REMEMBER_LAST_USED_CAMERA.getId(), LocaleController.getString(C2369R.string.RememberLastUsedCamera), LocaleController.getString(C2369R.string.RememberLastUsedCameraInfo), true).setChecked(ExteraConfig.rememberLastUsedCamera).setSearchable(this).setLinkAlias("rememberLastUsedCamera", this));
        }
        arrayList.add(UItem.asCheck(ChatsItem.STATIC_ZOOM.getId(), LocaleController.getString(C2369R.string.StaticZoom)).setChecked(ExteraConfig.staticZoom).setSearchable(this).setLinkAlias("staticZoom", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.StaticZoomInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.AutoDownloadPhotos)));
        arrayList.add(UItem.asCheck(ChatsItem.ALWAYS_SEND_IN_HD.getId(), LocaleController.getString(C2369R.string.AlwaysSendInHD)).setChecked(ExteraConfig.alwaysSendInHD).setSearchable(this).setLinkAlias("alwaysSendInHD", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_COUNTER.getId(), LocaleController.getString(C2369R.string.HidePhotoCounter)).setChecked(ExteraConfig.hidePhotoCounter).setSearchable(this).setLinkAlias("hidePhotoCounter", this));
        arrayList.add(UItem.asCheck(ChatsItem.HIDE_CAMERA_TILE.getId(), LocaleController.getString(C2369R.string.HideCameraTile)).setChecked(ExteraConfig.hideCameraTile).setSearchable(this).setLinkAlias("hideCameraTile", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.HideCameraTileInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.AutoDownloadVideos)));
        arrayList.add(UItem.asButton(ChatsItem.DOUBLE_TAP_SEEK_DURATION.getId(), LocaleController.getString(C2369R.string.DoubleTapSeekDuration), this.doubleTapSeekDuration[ExteraConfig.doubleTapSeekDuration]).setSearchable(this).setLinkAlias("doubleTapSeekDuration", this));
        arrayList.add(UItem.asCheck(ChatsItem.PREFER_ORIGINAL_QUALITY.getId(), LocaleController.getString(C2369R.string.PreferOriginalQuality)).setChecked(ExteraConfig.preferOriginalQuality).setSearchable(this).setLinkAlias("preferOriginalQuality", this));
        arrayList.add(UItem.asCheck(ChatsItem.SWIPE_TO_PIP.getId(), LocaleController.getString(C2369R.string.SwipeToPip)).setChecked(ExteraConfig.swipeToPip).setSearchable(this).setLinkAlias("swipeToPip", this));
        arrayList.add(UItem.asCheck(ChatsItem.UNMUTE_WITH_VOLUME_BUTTONS.getId(), LocaleController.getString(C2369R.string.UnmuteWithVolumeButtons), LocaleController.getString(C2369R.string.UnmuteWithVolumeButtonsInfo), true).setChecked(ExteraConfig.unmuteWithVolumeButtons).setSearchable(this).setLinkAlias("unmuteWithVolumeButtons", this));
        arrayList.add(UItem.asExteraExpandableSwitch(ChatsItem.PAUSE_ON_MINIMIZE.getId(), LocaleController.getString(C2369R.string.PauseOnMinimize), String.format(locale, "%d/%d", Integer.valueOf(getPauseOnMinimizeSelectedCount(false)), Integer.valueOf(getPauseOnMinimizeSelectedCount(true))), new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handlePauseOnMinimizeSwitchClick(view);
            }
        }).setChecked(getPauseOnMinimizeSelectedCount(false) > 0).setCollapsed(!this.pauseOnMinimizeExpanded).setSearchable(this).setLinkAlias("pauseOnMinimize", this));
        if (this.pauseOnMinimizeExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PAUSE_ON_MINIMIZE_VIDEO.getId(), LocaleController.getString(C2369R.string.PauseOnMinimizeVideo)).setChecked(ExteraConfig.pauseOnMinimizeVideo).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PAUSE_ON_MINIMIZE_VOICE.getId(), LocaleController.getString(C2369R.string.Voice)).setChecked(ExteraConfig.pauseOnMinimizeVoice).pad());
            arrayList.add(UItem.asRoundCheckbox(ChatsItem.PAUSE_ON_MINIMIZE_ROUND.getId(), LocaleController.getString(C2369R.string.PauseOnMinimizeRound)).setChecked(ExteraConfig.pauseOnMinimizeRound).pad());
        }
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.PauseOnMinimizeInfo)));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > ChatsItem.values().length) {
            return;
        }
        switch (C08873.f188xf6489a8a[ChatsItem.values()[uItem.f2017id - 1].ordinal()]) {
            case 1:
                toggleBooleanSettingAndRefresh("hideStickerTime", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda5
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideStickerTime = ((Boolean) obj).booleanValue();
                    }
                });
                this.stickerSizeCell.invalidate();
                break;
            case 2:
                handleReplyElementsClick(uItem);
                break;
            case 3:
                toggleBooleanSettingAndRefresh("replyColors", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda16
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.replyColors = ((Boolean) obj).booleanValue();
                    }
                });
                updateReplySettings();
                break;
            case 4:
                toggleBooleanSettingAndRefresh("replyEmoji", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda27
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.replyEmoji = ((Boolean) obj).booleanValue();
                    }
                });
                updateReplySettings();
                break;
            case 5:
                toggleBooleanSettingAndRefresh("replyBackground", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda38
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.replyBackground = ((Boolean) obj).booleanValue();
                    }
                });
                updateReplySettings();
                break;
            case 6:
                presentFragment(new AiPreferencesActivity());
                break;
            case 7:
                presentFragment(new ThemeActivity(0));
                break;
            case 8:
                toggleBooleanSettingAndRefresh("unlimitedRecentStickers", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda43
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.unlimitedRecentStickers = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 9:
                toggleBooleanSettingAndRefresh("hideReactions", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda44
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideReactions = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 10:
                showListDialog(uItem, this.doubleTapActions, this.doubleTapIcons, LocaleController.getString(C2369R.string.DoubleTapIncoming), ExteraConfig.doubleTapAction, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda45
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$8(i3);
                    }
                });
                break;
            case 11:
                showListDialog(uItem, this.doubleTapOutActions, this.doubleTapIcons, LocaleController.getString(C2369R.string.DoubleTapOutgoing), ExteraConfig.doubleTapActionOutOwner, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda46
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$9(i3);
                    }
                });
                break;
            case 12:
                showListDialog(uItem, this.bottomButton, LocaleController.getString(C2369R.string.BottomButton), ExteraConfig.bottomButton, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda47
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$10(i3);
                    }
                });
                break;
            case 13:
                toggleBooleanSettingAndRefresh("quickAdminShortcuts", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda48
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.quickAdminShortcuts = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 14:
                handleQuickTransitionsClick(uItem);
                break;
            case 15:
                toggleBooleanSettingAndRefresh("quickTransitionForChannels", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda6
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.quickTransitionForChannels = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 16:
                toggleBooleanSettingAndRefresh("quickTransitionForTopics", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda7
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.quickTransitionForTopics = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 17:
                toggleBooleanSettingAndRefresh("hideKeyboardOnScroll", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda8
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideKeyboardOnScroll = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 18:
                toggleBooleanSettingAndRefresh("addCommaAfterMention", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda9
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.addCommaAfterMention = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 19:
                toggleBooleanSettingAndRefresh("hideSendAsPeer", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda10
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideSendAsPeer = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 20:
                toggleBooleanSettingAndRefresh("hideShareButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda11
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideShareButton = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 21:
                toggleBooleanSettingAndRefresh("replaceEditedWithIcon", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda12
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.replaceEditedWithIcon = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 22:
                handleMessageMenuClick(uItem);
                break;
            case 23:
                toggleBooleanSettingAndRefresh("showCopyPhotoButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda13
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showCopyPhotoButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 24:
                toggleBooleanSettingAndRefresh("showSaveMessageButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda14
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showSaveMessageButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 25:
                toggleBooleanSettingAndRefresh("showClearButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda15
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showClearButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 26:
                toggleBooleanSettingAndRefresh("showHistoryButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda17
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showHistoryButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 27:
                toggleBooleanSettingAndRefresh("showReportButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda18
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showReportButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 28:
                toggleBooleanSettingAndRefresh("showGenerateButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda19
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showGenerateButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 29:
                toggleBooleanSettingAndRefresh("showDetailsButton", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda20
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.showDetailsButton = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 30:
                toggleBooleanSettingAndRefresh("groupMessageMenu", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda21
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.groupMessageMenu = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 31:
                handleSpeechRecognitionLanguageClick(uItem);
                break;
            case 32:
                toggleBooleanSettingAndRefresh("postProcessingWithAI", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda22
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.postprocessingWithAi = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 33:
                showListDialog(uItem, this.cameraType, LocaleController.getString(C2369R.string.CameraType), ExteraConfig.cameraType, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda23
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$28(i3);
                    }
                });
                break;
            case 34:
                handleCameraSettingsClick(uItem);
                break;
            case Operator.PROJECTION /* 35 */:
                toggleBooleanSettingAndRefresh("rounddual_available", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda24
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        MessagesController.getGlobalMainSettings().edit().putBoolean("rounddual_available", ((Boolean) obj).booleanValue()).apply();
                    }
                });
                break;
            case Operator.CONVERTABLE_TO /* 36 */:
                toggleBooleanSettingAndRefresh("extendedFramesPerSecond", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda25
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.extendedFramesPerSecond = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.END_OF_STMT /* 37 */:
                toggleBooleanSettingAndRefresh("cameraStabilization", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda26
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.cameraStabilization = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.FOREACH /* 38 */:
                toggleBooleanSettingAndRefresh("cameraMirrorMode", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda28
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.cameraMirrorMode = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.f1408IF /* 39 */:
                showListDialog(uItem, this.videoMessagesCamera, LocaleController.getString(C2369R.string.VideoMessagesCamera), ExteraConfig.videoMessagesCamera, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda29
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$33(i3);
                    }
                });
                break;
            case Operator.ELSE /* 40 */:
                toggleBooleanSettingAndRefresh("rememberLastUsedCamera", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda30
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.rememberLastUsedCamera = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.WHILE /* 41 */:
                toggleBooleanSettingAndRefresh("staticZoom", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda31
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.staticZoom = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.UNTIL /* 42 */:
                toggleBooleanSettingAndRefresh("alwaysSendInHD", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda32
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.alwaysSendInHD = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.FOR /* 43 */:
                toggleBooleanSettingAndRefresh("hidePhotoCounter", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda33
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hidePhotoCounter = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.SWITCH /* 44 */:
                toggleBooleanSettingAndRefresh("hideCameraTile", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda34
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideCameraTile = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case Operator.f1407DO /* 45 */:
                showListDialog(uItem, this.doubleTapSeekDuration, LocaleController.getString(C2369R.string.DoubleTapSeekDuration), ExteraConfig.doubleTapSeekDuration, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda35
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$39(i3);
                    }
                });
                break;
            case 46:
                toggleBooleanSettingAndRefresh("preferOriginalQuality", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda36
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.preferOriginalQuality = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 47:
                toggleBooleanSettingAndRefresh("swipeToPip", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda37
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.swipeToPip = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 48:
                toggleBooleanSettingAndRefresh("unmuteWithVolumeButtons", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda39
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.unmuteWithVolumeButtons = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 49:
                handlePauseOnMinimizeClick(uItem);
                break;
            case 50:
                toggleBooleanSettingAndRefresh("pauseOnMinimizeVideo", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda40
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.pauseOnMinimizeVideo = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 51:
                toggleBooleanSettingAndRefresh("pauseOnMinimizeVoice", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda41
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.pauseOnMinimizeVoice = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 52:
                toggleBooleanSettingAndRefresh("pauseOnMinimizeRound", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda42
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.pauseOnMinimizeRound = ((Boolean) obj).booleanValue();
                    }
                });
                break;
        }
    }

    /* renamed from: com.exteragram.messenger.preferences.ChatsPreferencesActivity$3 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C08873 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$preferences$ChatsPreferencesActivity$ChatsItem */
        static final /* synthetic */ int[] f188xf6489a8a;

        static {
            int[] iArr = new int[ChatsItem.values().length];
            f188xf6489a8a = iArr;
            try {
                iArr[ChatsItem.HIDE_STICKER_TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f188xf6489a8a[ChatsItem.REPLY_ELEMENTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f188xf6489a8a[ChatsItem.REPLY_COLORS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f188xf6489a8a[ChatsItem.REPLY_EMOJI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f188xf6489a8a[ChatsItem.REPLY_BACKGROUND.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f188xf6489a8a[ChatsItem.AI.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f188xf6489a8a[ChatsItem.CHAT_SETTINGS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f188xf6489a8a[ChatsItem.UNLIMITED_RECENT_STICKERS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f188xf6489a8a[ChatsItem.HIDE_REACTIONS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f188xf6489a8a[ChatsItem.DOUBLE_TAP_ACTION.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f188xf6489a8a[ChatsItem.DOUBLE_TAP_ACTION_OUT_OWNER.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f188xf6489a8a[ChatsItem.BOTTOM_BUTTON.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f188xf6489a8a[ChatsItem.ADMIN_SHORTCUTS.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f188xf6489a8a[ChatsItem.QUICK_TRANSITIONS.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f188xf6489a8a[ChatsItem.QUICK_TRANSITION_FOR_CHANNELS.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f188xf6489a8a[ChatsItem.QUICK_TRANSITION_FOR_TOPICS.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f188xf6489a8a[ChatsItem.HIDE_KEYBOARD_ON_SCROLL.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f188xf6489a8a[ChatsItem.ADD_COMMA_AFTER_MENTION.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f188xf6489a8a[ChatsItem.HIDE_SEND_AS_PEER.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f188xf6489a8a[ChatsItem.HIDE_SHARE_BUTTON.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f188xf6489a8a[ChatsItem.REPLACE_EDITED_WITH_ICON.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f188xf6489a8a[ChatsItem.MESSAGE_MENU.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f188xf6489a8a[ChatsItem.COPY_PHOTO.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f188xf6489a8a[ChatsItem.SAVE.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f188xf6489a8a[ChatsItem.CLEAR.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f188xf6489a8a[ChatsItem.HISTORY.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f188xf6489a8a[ChatsItem.REPORT.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                f188xf6489a8a[ChatsItem.GENERATE.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f188xf6489a8a[ChatsItem.DETAILS.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                f188xf6489a8a[ChatsItem.GROUP_MESSAGE_MENU.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                f188xf6489a8a[ChatsItem.SPEECH_RECOGNITION_LANGUAGE.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                f188xf6489a8a[ChatsItem.POST_PROCESSING_WITH_AI.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                f188xf6489a8a[ChatsItem.CAMERA_TYPE.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                f188xf6489a8a[ChatsItem.CAMERA_SETTINGS.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                f188xf6489a8a[ChatsItem.DUAL_CAMERA.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                f188xf6489a8a[ChatsItem.EXTENDED_FRAMES_PER_SECOND.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                f188xf6489a8a[ChatsItem.CAMERA_STABILIZATION.ordinal()] = 37;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                f188xf6489a8a[ChatsItem.CAMERA_MIRROR_MODE.ordinal()] = 38;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                f188xf6489a8a[ChatsItem.VIDEO_MESSAGES_CAMERA.ordinal()] = 39;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                f188xf6489a8a[ChatsItem.REMEMBER_LAST_USED_CAMERA.ordinal()] = 40;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                f188xf6489a8a[ChatsItem.STATIC_ZOOM.ordinal()] = 41;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                f188xf6489a8a[ChatsItem.ALWAYS_SEND_IN_HD.ordinal()] = 42;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                f188xf6489a8a[ChatsItem.HIDE_COUNTER.ordinal()] = 43;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                f188xf6489a8a[ChatsItem.HIDE_CAMERA_TILE.ordinal()] = 44;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                f188xf6489a8a[ChatsItem.DOUBLE_TAP_SEEK_DURATION.ordinal()] = 45;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                f188xf6489a8a[ChatsItem.PREFER_ORIGINAL_QUALITY.ordinal()] = 46;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                f188xf6489a8a[ChatsItem.SWIPE_TO_PIP.ordinal()] = 47;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                f188xf6489a8a[ChatsItem.UNMUTE_WITH_VOLUME_BUTTONS.ordinal()] = 48;
            } catch (NoSuchFieldError unused48) {
            }
            try {
                f188xf6489a8a[ChatsItem.PAUSE_ON_MINIMIZE.ordinal()] = 49;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                f188xf6489a8a[ChatsItem.PAUSE_ON_MINIMIZE_VIDEO.ordinal()] = 50;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                f188xf6489a8a[ChatsItem.PAUSE_ON_MINIMIZE_VOICE.ordinal()] = 51;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                f188xf6489a8a[ChatsItem.PAUSE_ON_MINIMIZE_ROUND.ordinal()] = 52;
            } catch (NoSuchFieldError unused52) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$8(int i) {
        ExteraConfig.doubleTapAction = i;
        changeIntSetting("doubleTapAction", i);
        handleDoubleTapActionButtonClick(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$9(int i) {
        ExteraConfig.doubleTapActionOutOwner = i;
        changeIntSetting("doubleTapActionOutOwner", i);
        handleDoubleTapActionButtonClick(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$10(int i) {
        ExteraConfig.bottomButton = i;
        changeIntSetting("bottomButton", i);
        this.parentLayout.rebuildFragments(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$28(int i) {
        ExteraConfig.cameraType = i;
        changeIntSetting("cameraType", i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$33(int i) {
        ExteraConfig.videoMessagesCamera = i;
        changeIntSetting("videoMessagesCamera", i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$39(int i) {
        ExteraConfig.doubleTapSeekDuration = i;
        changeIntSetting("doubleTapSeekDuration", i);
    }

    private void handleReplyElementsClick(UItem uItem) {
        boolean z = !this.replyElementsExpanded;
        this.replyElementsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReplyElementsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1820id);
        boolean z = !uItemFindItemByItemId.checked;
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.replyColors = z;
        SharedPreferences.Editor editorPutBoolean = editor.putBoolean("replyColors", z);
        ExteraConfig.replyEmoji = z;
        SharedPreferences.Editor editorPutBoolean2 = editorPutBoolean.putBoolean("replyEmoji", z);
        ExteraConfig.replyBackground = z;
        editorPutBoolean2.putBoolean("replyBackground", z).apply();
        this.stickerSizeCell.invalidate();
        uItemFindItemByItemId.setChecked(z);
        this.parentLayout.rebuildFragments(0);
        this.listView.adapter.update(true);
    }

    private void updateReplySettings() {
        this.stickerSizeCell.invalidate();
        this.parentLayout.rebuildFragments(0);
    }

    private int getReplyElementsSelectedCount(boolean z) {
        int i = (z || ExteraConfig.replyColors) ? 1 : 0;
        if (z || ExteraConfig.replyEmoji) {
            i++;
        }
        return (z || ExteraConfig.replyBackground) ? i + 1 : i;
    }

    private void handleDoubleTapActionButtonClick(boolean z) {
        this.doubleTapCell.updateIcons(z ? 2 : 1, true);
        this.doubleTapCell.invalidate();
    }

    private void handleMessageMenuClick(UItem uItem) {
        boolean z = !this.messageMenuExpanded;
        this.messageMenuExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessageMenuSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1820id);
        boolean z = !uItemFindItemByItemId.checked;
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.showCopyPhotoButton = z;
        SharedPreferences.Editor editorPutBoolean = editor.putBoolean("showCopyPhotoButton", z);
        ExteraConfig.showClearButton = z;
        SharedPreferences.Editor editorPutBoolean2 = editorPutBoolean.putBoolean("showClearButton", z);
        ExteraConfig.showSaveMessageButton = z;
        SharedPreferences.Editor editorPutBoolean3 = editorPutBoolean2.putBoolean("showSaveMessageButton", z);
        ExteraConfig.showReportButton = z;
        SharedPreferences.Editor editorPutBoolean4 = editorPutBoolean3.putBoolean("showReportButton", z);
        ExteraConfig.showHistoryButton = z;
        SharedPreferences.Editor editorPutBoolean5 = editorPutBoolean4.putBoolean("showHistoryButton", z);
        ExteraConfig.showGenerateButton = z;
        SharedPreferences.Editor editorPutBoolean6 = editorPutBoolean5.putBoolean("showGenerateButton", z);
        ExteraConfig.showDetailsButton = z;
        editorPutBoolean6.putBoolean("showDetailsButton", z).apply();
        uItemFindItemByItemId.setChecked(z);
        this.parentLayout.rebuildFragments(0);
        this.listView.adapter.update(true);
    }

    private int getMessageMenuSelectedCount(boolean z) {
        int i = (z || ExteraConfig.showCopyPhotoButton) ? 1 : 0;
        if (z || ExteraConfig.showClearButton) {
            i++;
        }
        if (z || ExteraConfig.showSaveMessageButton) {
            i++;
        }
        if (z || ExteraConfig.showReportButton) {
            i++;
        }
        if (z || ExteraConfig.showHistoryButton) {
            i++;
        }
        if (AiController.canUseAI() && (z || ExteraConfig.showGenerateButton)) {
            i++;
        }
        return (z || ExteraConfig.showDetailsButton) ? i + 1 : i;
    }

    private void handleQuickTransitionsClick(UItem uItem) {
        boolean z = !this.quickTransitionsExpanded;
        this.quickTransitionsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleQuickTransitionsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1820id);
        boolean z = !uItemFindItemByItemId.checked;
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.quickTransitionForChannels = z;
        SharedPreferences.Editor editorPutBoolean = editor.putBoolean("quickTransitionForChannels", z);
        ExteraConfig.quickTransitionForTopics = z;
        editorPutBoolean.putBoolean("quickTransitionForTopics", z).apply();
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private int getQuickTransitionsSelectedCount(boolean z) {
        int i = (z || ExteraConfig.quickTransitionForChannels) ? 1 : 0;
        return (z || ExteraConfig.quickTransitionForTopics) ? i + 1 : i;
    }

    private void handleCameraSettingsClick(UItem uItem) {
        boolean z = !this.cameraSettingsExpanded;
        this.cameraSettingsExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCameraSettingsSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1820id);
        boolean z = !uItemFindItemByItemId.checked;
        if (ExteraConfig.cameraType != 2) {
            MessagesController.getGlobalMainSettings().edit().putBoolean("rounddual_available", z).apply();
        }
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.extendedFramesPerSecond = z;
        editor.putBoolean("extendedFramesPerSecond", z);
        ExteraConfig.cameraStabilization = z;
        editor.putBoolean("cameraStabilization", z);
        if (ExteraConfig.cameraType != 1) {
            ExteraConfig.cameraMirrorMode = z;
            editor.putBoolean("cameraMirrorMode", z);
        }
        editor.apply();
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private int getCameraSettingsSelectedCount(boolean z) {
        int i = (ExteraConfig.cameraType == 2 || !(z || DualCameraView.roundDualAvailableStatic(getContext()))) ? 0 : 1;
        if (z || ExteraConfig.extendedFramesPerSecond) {
            i++;
        }
        if (z || ExteraConfig.cameraStabilization) {
            i++;
        }
        return ExteraConfig.cameraType != 1 ? (z || ExteraConfig.cameraMirrorMode) ? i + 1 : i : i;
    }

    private void handlePauseOnMinimizeClick(UItem uItem) {
        boolean z = !this.pauseOnMinimizeExpanded;
        this.pauseOnMinimizeExpanded = z;
        uItem.setCollapsed(z);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePauseOnMinimizeSwitchClick(View view) {
        UItem uItemFindItemByItemId = this.listView.findItemByItemId(((TextCheckCell2) view).f1820id);
        boolean z = !uItemFindItemByItemId.checked;
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.pauseOnMinimizeVideo = z;
        SharedPreferences.Editor editorPutBoolean = editor.putBoolean("pauseOnMinimizeVideo", z);
        ExteraConfig.pauseOnMinimizeVoice = z;
        SharedPreferences.Editor editorPutBoolean2 = editorPutBoolean.putBoolean("pauseOnMinimizeVoice", z);
        ExteraConfig.pauseOnMinimizeRound = z;
        editorPutBoolean2.putBoolean("pauseOnMinimizeRound", z).apply();
        uItemFindItemByItemId.setChecked(z);
        this.listView.adapter.update(true);
    }

    private int getPauseOnMinimizeSelectedCount(boolean z) {
        int i = (z || ExteraConfig.pauseOnMinimizeVideo) ? 1 : 0;
        if (z || ExteraConfig.pauseOnMinimizeVoice) {
            i++;
        }
        return (z || ExteraConfig.pauseOnMinimizeRound) ? i + 1 : i;
    }

    public static /* synthetic */ String[] $r8$lambda$DgD1D_eIonfqAC254APBY78eLOk(int i) {
        return new String[i];
    }

    public static /* synthetic */ String $r8$lambda$UioirjPO2Gg_T7ebwlI_M68cx3I(String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(TranslatorUtils.getLanguageTitleSystem(str));
        if (TranslatorUtils.getLanguageDisplayName(str) == null) {
            str2 = "";
        } else {
            str2 = " – " + TranslatorUtils.getLanguageDisplayName(str);
        }
        sb.append(str2);
        return sb.toString();
    }

    private void handleSpeechRecognitionLanguageClick(UItem uItem) {
        showListDialog(uItem, (CharSequence[]) Collection.EL.stream(this.languageCodes).map(new Function() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda51
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ChatsPreferencesActivity.$r8$lambda$UioirjPO2Gg_T7ebwlI_M68cx3I((String) obj);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).toArray(new IntFunction() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda52
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return ChatsPreferencesActivity.$r8$lambda$DgD1D_eIonfqAC254APBY78eLOk(i);
            }
        }), LocaleController.getString(C2369R.string.RecognitionLanguage), this.languageCodes.indexOf(ExteraConfig.recognitionLanguage), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda53
            @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
            public final void onClick(int i) {
                this.f$0.lambda$handleSpeechRecognitionLanguageClick$54(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSpeechRecognitionLanguageClick$54(int i) {
        String str = ExteraConfig.recognitionLanguage;
        final String str2 = (String) this.languageCodes.get(i);
        if (Objects.equals(str, str2)) {
            return;
        }
        final Runnable runnable = new Runnable() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda54
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleSpeechRecognitionLanguageClick$48(str2);
            }
        };
        if (!Objects.equals(str2, "none") && Collection.EL.stream(VoiceRecognitionController.getInstance().listDownloadedModels("vosk")).noneMatch(new Predicate() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda55
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m221or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals(((VoiceRecognitionController.RecognitionModel) obj).getLanguage(), str2);
            }
        })) {
            VoiceRecognitionController.RecognitionModel recognitionModel = (VoiceRecognitionController.RecognitionModel) Collection.EL.stream(VoiceRecognitionController.getInstance().listAvailableModels("vosk")).filter(new Predicate() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda56
                public /* synthetic */ Predicate and(Predicate predicate) {
                    return Predicate$CC.$default$and(this, predicate);
                }

                public /* synthetic */ Predicate negate() {
                    return Predicate$CC.$default$negate(this);
                }

                /* renamed from: or */
                public /* synthetic */ Predicate m222or(Predicate predicate) {
                    return Predicate$CC.$default$or(this, predicate);
                }

                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((VoiceRecognitionController.RecognitionModel) obj).getLanguage().equals(str2);
                }
            }).findFirst().orElse(null);
            if (recognitionModel == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(LocaleController.getString(C2369R.string.MissingLanguageModel));
            builder.setSubtitle(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.ModelDownloadInfo, TranslatorUtils.getLanguageTitleSystem(str2))));
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            builder.setPositiveButton(LocaleController.formatString(C2369R.string.ModelDownload, AndroidUtilities.formatFileSize(recognitionModel.getSize())), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda57
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i2) {
                    this.f$0.lambda$handleSpeechRecognitionLanguageClick$53(str2, runnable, alertDialog, i2);
                }
            });
            showDialog(builder.create());
            return;
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSpeechRecognitionLanguageClick$48(String str) {
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.recognitionLanguage = str;
        editor.putString("recognitionLanguage", str).apply();
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSpeechRecognitionLanguageClick$53(String str, Runnable runnable, AlertDialog alertDialog, int i) {
        dismissCurrentDialog();
        final LoadingModelView loadingModelView = new LoadingModelView(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(loadingModelView);
        final AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setCancelable(false);
        final boolean[] zArr = {false};
        final float[] fArr = {0.0f};
        Runnable runnable2 = new Runnable() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                loadingModelView.setProgress(fArr[0]);
            }
        };
        final long[] jArr = {-1};
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                ChatsPreferencesActivity.$r8$lambda$LuKgXMF2NkFTpY2f8dR9O1vdu1I(zArr, jArr, alertDialogCreate);
            }
        }, 150L);
        VoiceRecognitionController.getInstance().downloadModel("vosk", str, new C08862(fArr, runnable2, zArr, loadingModelView, jArr, alertDialogCreate, runnable));
    }

    public static /* synthetic */ void $r8$lambda$LuKgXMF2NkFTpY2f8dR9O1vdu1I(boolean[] zArr, long[] jArr, AlertDialog alertDialog) {
        if (zArr[0]) {
            return;
        }
        jArr[0] = System.currentTimeMillis();
        alertDialog.show();
    }

    /* renamed from: com.exteragram.messenger.preferences.ChatsPreferencesActivity$2 */
    /* loaded from: classes3.dex */
    class C08862 implements VoiceRecognitionController.DownloadModelCallback {
        final /* synthetic */ AlertDialog val$alert;
        final /* synthetic */ boolean[] val$done;
        final /* synthetic */ LoadingModelView val$loadingModelView;
        final /* synthetic */ Runnable val$onDownloaded;
        final /* synthetic */ float[] val$progressValue;
        final /* synthetic */ long[] val$start;
        final /* synthetic */ Runnable val$updateProgress;

        C08862(float[] fArr, Runnable runnable, boolean[] zArr, LoadingModelView loadingModelView, long[] jArr, AlertDialog alertDialog, Runnable runnable2) {
            this.val$progressValue = fArr;
            this.val$updateProgress = runnable;
            this.val$done = zArr;
            this.val$loadingModelView = loadingModelView;
            this.val$start = jArr;
            this.val$alert = alertDialog;
            this.val$onDownloaded = runnable2;
        }

        @Override // com.exteragram.messenger.speech.VoiceRecognitionController.DownloadModelCallback
        public void onProgress(float f) {
            this.val$progressValue[0] = f;
            AndroidUtilities.cancelRunOnUIThread(this.val$updateProgress);
            AndroidUtilities.runOnUIThread(this.val$updateProgress);
        }

        @Override // com.exteragram.messenger.speech.VoiceRecognitionController.DownloadModelCallback
        public void onCompleted() {
            final boolean[] zArr = this.val$done;
            final LoadingModelView loadingModelView = this.val$loadingModelView;
            final long[] jArr = this.val$start;
            final AlertDialog alertDialog = this.val$alert;
            final Runnable runnable = this.val$onDownloaded;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCompleted$0(zArr, loadingModelView, jArr, alertDialog, runnable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCompleted$0(boolean[] zArr, LoadingModelView loadingModelView, long[] jArr, AlertDialog alertDialog, Runnable runnable) {
            zArr[0] = true;
            loadingModelView.setProgress(1.0f);
            if (jArr[0] > 0) {
                Objects.requireNonNull(alertDialog);
                AndroidUtilities.runOnUIThread(new ChatsPreferencesActivity$2$$ExternalSyntheticLambda2(alertDialog), Math.max(0L, 1000 - (System.currentTimeMillis() - jArr[0])));
            } else {
                alertDialog.dismiss();
            }
            runnable.run();
            BulletinFactory.m1267of(ChatsPreferencesActivity.this).createSuccessBulletin(LocaleController.getString(C2369R.string.ModelDownloaded)).show();
        }

        @Override // com.exteragram.messenger.speech.VoiceRecognitionController.DownloadModelCallback
        public void onError(Exception exc) {
            final AlertDialog alertDialog = this.val$alert;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onError$1(alertDialog);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(AlertDialog alertDialog) {
            alertDialog.dismiss();
            BulletinFactory.m1267of(ChatsPreferencesActivity.this).createErrorBulletin(LocaleController.getString(C2369R.string.ModelError)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StickerSizeCell extends FrameLayout implements CustomPreferenceCell {
        private int lastWidth;
        private final StickerSizePreviewCell messagesCell;
        public final AltSeekbar seekBar;

        public StickerSizeCell(Context context) {
            super(context);
            setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            setWillNotDraw(false);
            AltSeekbar altSeekbar = new AltSeekbar(context, new AltSeekbar.OnDrag() { // from class: com.exteragram.messenger.preferences.ChatsPreferencesActivity$StickerSizeCell$$ExternalSyntheticLambda0
                @Override // com.exteragram.messenger.preferences.components.AltSeekbar.OnDrag
                public final void run(float f) {
                    this.f$0.lambda$new$0(f);
                }
            }, 4, 20, LocaleController.getString(C2369R.string.StickerSize), LocaleController.getString(C2369R.string.StickerSizeLeft), LocaleController.getString(C2369R.string.StickerSizeRight));
            this.seekBar = altSeekbar;
            altSeekbar.setProgress((ExteraConfig.stickerSize - 4.0f) / 16.0f);
            addView(altSeekbar, LayoutHelper.createFrame(-1, -2.0f));
            StickerSizePreviewCell stickerSizePreviewCell = new StickerSizePreviewCell(context, ((BaseFragment) ChatsPreferencesActivity.this).parentLayout);
            this.messagesCell = stickerSizePreviewCell;
            stickerSizePreviewCell.setImportantForAccessibility(4);
            addView(stickerSizePreviewCell, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 112.0f, 0.0f, 0.0f));
            setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(float f) {
            SharedPreferences.Editor editor = ExteraConfig.editor;
            ExteraConfig.stickerSize = f;
            editor.putFloat("stickerSize", f).apply();
            invalidate();
            if (ChatsPreferencesActivity.this.resetItem.getVisibility() != 0) {
                AndroidUtilities.updateViewVisibilityAnimated(ChatsPreferencesActivity.this.resetItem, true, 0.5f, true);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            int size = View.MeasureSpec.getSize(i);
            if (this.lastWidth != size) {
                this.lastWidth = size;
            }
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            this.lastWidth = -1;
            this.messagesCell.invalidate();
            this.seekBar.invalidate();
        }

        @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof StickerSizeCell) {
                StickerSizeCell stickerSizeCell = (StickerSizeCell) obj;
                if (Objects.equals(this.messagesCell, stickerSizeCell.messagesCell) && Objects.equals(this.seekBar, stickerSizeCell.seekBar) && this.lastWidth == stickerSizeCell.lastWidth) {
                    return true;
                }
            }
            return false;
        }
    }
}
