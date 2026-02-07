package org.telegram.p023ui.Components.Premium.boosts.cells.msg;

import android.R;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.StateSet;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.ChatMessageCell;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.Premium.boosts.BoostDialogs;
import org.telegram.p023ui.Components.StaticLayoutEx;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class GiveawayMessageCell {
    private static final Map monthsToEmoticon;
    private int additionPrizeHeight;
    private StaticLayout additionPrizeLayout;
    private AvatarDrawable[] avatarDrawables;
    private ImageReceiver[] avatarImageReceivers;
    private boolean[] avatarVisible;
    private int bottomHeight;
    private StaticLayout bottomLayout;
    private Paint chatBgPaint;
    private RectF chatRect;
    private TextPaint chatTextPaint;
    private float[] chatTitleWidths;
    private CharSequence[] chatTitles;
    private TLRPC.Chat[] chats;
    private Rect[] clickRect;
    private Paint clipRectPaint;
    private Rect containerRect;
    private RectF countRect;
    private Paint counterBgPaint;
    private Drawable counterIcon;
    private TextPaint counterStarsTextPaint;
    private String counterStr;
    private Rect counterTextBounds;
    private TextPaint counterTextPaint;
    private int countriesHeight;
    private StaticLayout countriesLayout;
    private TextPaint countriesTextPaint;
    private int diffTextWidth;
    private ImageReceiver giftReceiver;
    private boolean isStars;
    private Paint lineDividerPaint;
    private MessageObject messageObject;
    private boolean[] needNewRow;
    private final ChatMessageCell parentView;
    private int[] pressedState;
    private Paint saveLayerPaint;
    private int selectorColor;
    private Drawable selectorDrawable;
    private String textDivider;
    private TextPaint textDividerPaint;
    private float textDividerWidth;
    private TextPaint textPaint;
    private int titleHeight;
    private StaticLayout titleLayout;
    private int topHeight;
    private StaticLayout topLayout;
    private int measuredHeight = 0;
    private int measuredWidth = 0;
    private int pressedPos = -1;
    private boolean isButtonPressed = false;
    private boolean isContainerPressed = false;

    static {
        HashMap map = new HashMap();
        monthsToEmoticon = map;
        map.put(1, "1⃣");
        map.put(3, "2⃣");
        map.put(6, "3⃣");
        map.put(12, "4⃣");
        map.put(24, "5⃣");
    }

    public GiveawayMessageCell(ChatMessageCell chatMessageCell) {
        this.parentView = chatMessageCell;
    }

    private void init() {
        if (this.counterTextPaint != null) {
            return;
        }
        this.counterTextPaint = new TextPaint(1);
        this.counterStarsTextPaint = new TextPaint(1);
        this.chatTextPaint = new TextPaint(1);
        this.textPaint = new TextPaint(1);
        this.textDividerPaint = new TextPaint(1);
        this.lineDividerPaint = new Paint(1);
        this.countriesTextPaint = new TextPaint(1);
        this.counterBgPaint = new Paint(1);
        this.chatBgPaint = new Paint(1);
        this.saveLayerPaint = new Paint();
        this.clipRectPaint = new Paint();
        this.countRect = new RectF();
        this.chatRect = new RectF();
        this.counterTextBounds = new Rect();
        this.containerRect = new Rect();
        this.pressedState = new int[]{R.attr.state_enabled, R.attr.state_pressed};
        this.chatTitles = new CharSequence[10];
        this.chats = new TLRPC.Chat[10];
        this.chatTitleWidths = new float[10];
        this.needNewRow = new boolean[10];
        this.clickRect = new Rect[10];
        ImageReceiver imageReceiver = new ImageReceiver(this.parentView);
        this.giftReceiver = imageReceiver;
        imageReceiver.setAllowLoadingOnAttachedOnly(true);
        Paint paint = this.clipRectPaint;
        PorterDuff.Mode mode = PorterDuff.Mode.DST_OUT;
        paint.setXfermode(new PorterDuffXfermode(mode));
        this.counterTextPaint.setTypeface(AndroidUtilities.bold());
        this.counterTextPaint.setXfermode(new PorterDuffXfermode(mode));
        this.counterTextPaint.setTextSize(AndroidUtilities.m1146dp(12.0f));
        TextPaint textPaint = this.counterTextPaint;
        Paint.Align align = Paint.Align.CENTER;
        textPaint.setTextAlign(align);
        this.counterStarsTextPaint.setTypeface(AndroidUtilities.bold());
        this.counterStarsTextPaint.setTextSize(AndroidUtilities.m1146dp(12.0f));
        this.counterStarsTextPaint.setTextAlign(align);
        this.counterStarsTextPaint.setColor(-1);
        this.chatTextPaint.setTypeface(AndroidUtilities.bold());
        this.chatTextPaint.setTextSize(AndroidUtilities.m1146dp(13.0f));
        this.countriesTextPaint.setTextSize(AndroidUtilities.m1146dp(13.0f));
        this.textPaint.setTextSize(AndroidUtilities.m1146dp(14.0f));
        this.textDividerPaint.setTextSize(AndroidUtilities.m1146dp(14.0f));
        this.textDividerPaint.setTextAlign(align);
    }

    public boolean checkMotionEvent(MotionEvent motionEvent) {
        MessageObject messageObject = this.messageObject;
        if (messageObject != null && messageObject.isGiveaway()) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0) {
                int i = 0;
                while (true) {
                    Rect[] rectArr = this.clickRect;
                    if (i < rectArr.length) {
                        if (rectArr[i].contains(x, y)) {
                            this.pressedPos = i;
                            this.selectorDrawable.setHotspot(x, y);
                            this.isButtonPressed = true;
                            setButtonPressed(true);
                            return true;
                        }
                        i++;
                    } else if (this.containerRect.contains(x, y)) {
                        this.isContainerPressed = true;
                        return true;
                    }
                }
            } else if (motionEvent.getAction() == 1) {
                if (this.isButtonPressed) {
                    if (this.parentView.getDelegate() != null) {
                        this.parentView.getDelegate().didPressGiveawayChatButton(this.parentView, this.pressedPos);
                    }
                    this.parentView.playSoundEffect(0);
                    setButtonPressed(false);
                    this.isButtonPressed = false;
                }
                if (this.isContainerPressed) {
                    this.isContainerPressed = false;
                    BoostDialogs.showBulletinAbout(this.messageObject);
                }
            } else if (motionEvent.getAction() != 2 && motionEvent.getAction() == 3) {
                if (this.isButtonPressed) {
                    setButtonPressed(false);
                }
                this.isButtonPressed = false;
                this.isContainerPressed = false;
            }
        }
        return false;
    }

    public void setButtonPressed(boolean z) {
        Drawable drawable;
        MessageObject messageObject = this.messageObject;
        if (messageObject == null || !messageObject.isGiveaway() || (drawable = this.selectorDrawable) == null) {
            return;
        }
        if (z) {
            drawable.setCallback(new Drawable.Callback() { // from class: org.telegram.ui.Components.Premium.boosts.cells.msg.GiveawayMessageCell.1
                @Override // android.graphics.drawable.Drawable.Callback
                public void invalidateDrawable(Drawable drawable2) {
                    GiveawayMessageCell.this.parentView.invalidate();
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void scheduleDrawable(Drawable drawable2, Runnable runnable, long j) {
                    GiveawayMessageCell.this.parentView.invalidate();
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
                    GiveawayMessageCell.this.parentView.invalidate();
                }
            });
            this.selectorDrawable.setState(this.pressedState);
            this.parentView.invalidate();
        } else {
            drawable.setState(StateSet.NOTHING);
            this.parentView.invalidate();
        }
    }

    public void setMessageContent(MessageObject messageObject, int i, int i2) {
        int iM1146dp;
        float f;
        this.messageObject = null;
        this.titleLayout = null;
        this.additionPrizeLayout = null;
        this.topLayout = null;
        this.bottomLayout = null;
        this.countriesLayout = null;
        this.measuredHeight = 0;
        this.measuredWidth = 0;
        this.additionPrizeHeight = 0;
        this.textDividerWidth = 0.0f;
        if (messageObject.isGiveaway()) {
            this.messageObject = messageObject;
            init();
            createImages();
            setGiftImage(messageObject);
            TLRPC.TL_messageMediaGiveaway tL_messageMediaGiveaway = (TLRPC.TL_messageMediaGiveaway) messageObject.messageOwner.media;
            this.isStars = (tL_messageMediaGiveaway.flags & 32) != 0;
            checkArraysLimits(tL_messageMediaGiveaway.channels.size());
            int iM1146dp2 = AndroidUtilities.m1146dp(148.0f);
            if (AndroidUtilities.isTablet()) {
                iM1146dp = AndroidUtilities.getMinTabletSide() - AndroidUtilities.m1146dp(80.0f);
            } else {
                iM1146dp = i - AndroidUtilities.m1146dp(80.0f);
            }
            int i3 = iM1146dp;
            MessagesController messagesController = MessagesController.getInstance(UserConfig.selectedAccount);
            boolean zIsForwarded = messageObject.isForwarded();
            TLRPC.Message message = messageObject.messageOwner;
            boolean zIsChannelAndNotMegaGroup = ChatObject.isChannelAndNotMegaGroup(messagesController.getChat(Long.valueOf(-MessageObject.getPeerId(zIsForwarded ? message.fwd_from.from_id : message.peer_id))));
            SpannableStringBuilder spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.BoostingGiveawayPrizes));
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableStringBuilderReplaceTags);
            spannableStringBuilder.setSpan(new RelativeSizeSpan(1.05f), 0, spannableStringBuilderReplaceTags.length(), 33);
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
            if (this.isStars) {
                spannableStringBuilder2.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("BoostingStarsGiveawayMsgInfoPlural1", (int) tL_messageMediaGiveaway.stars)));
                spannableStringBuilder2.append((CharSequence) "\n");
                spannableStringBuilder2.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralString("BoostingStarsGiveawayMsgInfoPlural2", tL_messageMediaGiveaway.quantity, new Object[0])));
            } else {
                spannableStringBuilder2.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("BoostingGiveawayMsgInfoPlural1", tL_messageMediaGiveaway.quantity)));
                spannableStringBuilder2.append((CharSequence) "\n");
                spannableStringBuilder2.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralString("BoostingGiveawayMsgInfoPlural2", tL_messageMediaGiveaway.quantity, LocaleController.formatPluralString("BoldMonths", tL_messageMediaGiveaway.months, new Object[0]))));
            }
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
            spannableStringBuilder3.append((CharSequence) spannableStringBuilder2);
            spannableStringBuilder3.append((CharSequence) "\n\n");
            spannableStringBuilder3.setSpan(new RelativeSizeSpan(0.4f), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 33);
            SpannableStringBuilder spannableStringBuilderReplaceTags2 = AndroidUtilities.replaceTags(LocaleController.getString("BoostingGiveawayMsgParticipants", C2369R.string.BoostingGiveawayMsgParticipants));
            spannableStringBuilder3.append((CharSequence) spannableStringBuilderReplaceTags2);
            spannableStringBuilder3.setSpan(new RelativeSizeSpan(1.05f), spannableStringBuilder2.length() + 2, spannableStringBuilder2.length() + 2 + spannableStringBuilderReplaceTags2.length(), 33);
            spannableStringBuilder3.append((CharSequence) "\n");
            if (tL_messageMediaGiveaway.only_new_subscribers) {
                spannableStringBuilder3.append((CharSequence) LocaleController.formatPluralString(zIsChannelAndNotMegaGroup ? "BoostingGiveawayMsgNewSubsPlural" : "BoostingGiveawayMsgNewSubsGroupPlural", tL_messageMediaGiveaway.channels.size(), new Object[0]));
            } else {
                spannableStringBuilder3.append((CharSequence) LocaleController.formatPluralString(zIsChannelAndNotMegaGroup ? "BoostingGiveawayMsgAllSubsPlural" : "BoostingGiveawayMsgAllSubsGroupPlural", tL_messageMediaGiveaway.channels.size(), new Object[0]));
            }
            SpannableStringBuilder spannableStringBuilderReplaceTags3 = AndroidUtilities.replaceTags(LocaleController.getString("BoostingWinnersDate", C2369R.string.BoostingWinnersDate));
            SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(spannableStringBuilderReplaceTags3);
            spannableStringBuilder4.setSpan(new RelativeSizeSpan(1.05f), 0, spannableStringBuilderReplaceTags3.length(), 33);
            Date date = new Date(tL_messageMediaGiveaway.until_date * 1000);
            String str = LocaleController.getInstance().getFormatterGiveawayCard().format(date);
            String str2 = LocaleController.getInstance().getFormatterDay().format(date);
            spannableStringBuilder4.append((CharSequence) "\n");
            spannableStringBuilder4.append((CharSequence) LocaleController.formatString("formatDateAtTime", C2369R.string.formatDateAtTime, str, str2));
            TextPaint textPaint = this.textPaint;
            Layout.Alignment alignment = Layout.Alignment.ALIGN_CENTER;
            float fM1146dp = AndroidUtilities.m1146dp(2.0f);
            TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
            this.titleLayout = StaticLayoutEx.createStaticLayout(spannableStringBuilder, textPaint, i3, alignment, 1.0f, fM1146dp, false, truncateAt, i3, 10);
            this.topLayout = StaticLayoutEx.createStaticLayout(spannableStringBuilder3, this.textPaint, i3, alignment, 1.0f, AndroidUtilities.m1146dp(2.0f), false, truncateAt, i3, 10);
            this.bottomLayout = StaticLayoutEx.createStaticLayout(spannableStringBuilder4, this.textPaint, i3, alignment, 1.0f, AndroidUtilities.m1146dp(3.0f), false, truncateAt, i3, 10);
            int iM1146dp3 = 0;
            for (int i4 = 0; i4 < this.titleLayout.getLineCount(); i4++) {
                iM1146dp3 = (int) Math.max(iM1146dp3, Math.ceil(this.titleLayout.getLineWidth(i4)));
            }
            for (int i5 = 0; i5 < this.topLayout.getLineCount(); i5++) {
                iM1146dp3 = (int) Math.max(iM1146dp3, Math.ceil(this.topLayout.getLineWidth(i5)));
            }
            for (int i6 = 0; i6 < this.bottomLayout.getLineCount(); i6++) {
                iM1146dp3 = (int) Math.max(iM1146dp3, Math.ceil(this.bottomLayout.getLineWidth(i6)));
            }
            if (iM1146dp3 < AndroidUtilities.m1146dp(180.0f)) {
                iM1146dp3 = AndroidUtilities.m1146dp(180.0f);
            }
            int i7 = iM1146dp3;
            String str3 = tL_messageMediaGiveaway.prize_description;
            if (str3 != null && !str3.isEmpty()) {
                StaticLayout staticLayoutCreateStaticLayout = StaticLayoutEx.createStaticLayout(Emoji.replaceEmoji(AndroidUtilities.replaceTags(LocaleController.formatPluralString("BoostingGiveawayMsgPrizes", tL_messageMediaGiveaway.quantity, tL_messageMediaGiveaway.prize_description)), this.countriesTextPaint.getFontMetricsInt(), false), this.textPaint, i7, Layout.Alignment.ALIGN_CENTER, 1.0f, AndroidUtilities.m1146dp(2.0f), false, TextUtils.TruncateAt.END, i7, 20);
                this.additionPrizeLayout = staticLayoutCreateStaticLayout;
                this.additionPrizeHeight = staticLayoutCreateStaticLayout.getLineBottom(staticLayoutCreateStaticLayout.getLineCount() - 1) + AndroidUtilities.m1146dp(22.0f);
                String string = LocaleController.getString(C2369R.string.BoostingGiveawayMsgWithDivider);
                this.textDivider = string;
                this.textDividerWidth = this.textDividerPaint.measureText(string, 0, string.length());
            }
            if (tL_messageMediaGiveaway.countries_iso2.size() > 0) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = tL_messageMediaGiveaway.countries_iso2;
                int size = arrayList2.size();
                int i8 = 0;
                while (i8 < size) {
                    Object obj = arrayList2.get(i8);
                    i8++;
                    String str4 = (String) obj;
                    String displayCountry = new Locale("", str4).getDisplayCountry(Locale.getDefault());
                    String languageFlag = LocaleController.getLanguageFlag(str4);
                    SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder();
                    if (languageFlag != null) {
                        spannableStringBuilder5.append((CharSequence) languageFlag).append((CharSequence) " ");
                    }
                    spannableStringBuilder5.append((CharSequence) displayCountry);
                    arrayList.add(spannableStringBuilder5);
                }
                f = 2.0f;
                if (!arrayList.isEmpty()) {
                    this.countriesLayout = StaticLayoutEx.createStaticLayout(Emoji.replaceEmoji(AndroidUtilities.replaceTags(LocaleController.formatString("BoostingGiveAwayFromCountries", C2369R.string.BoostingGiveAwayFromCountries, TextUtils.join(", ", arrayList))), this.countriesTextPaint.getFontMetricsInt(), false), this.countriesTextPaint, i7, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false, TextUtils.TruncateAt.END, i7, 10);
                }
            } else {
                f = 2.0f;
            }
            int iMax = Math.max(i2, Math.min(i7 + AndroidUtilities.m1146dp(38.0f), i3));
            this.diffTextWidth = iMax - i3;
            float f2 = iMax;
            float f3 = iM1146dp2;
            float f4 = f3 / f;
            this.giftReceiver.setImageCoords((f2 / f) - f4, AndroidUtilities.m1146dp(42.0f) - f4, f3, f3);
            StaticLayout staticLayout = this.titleLayout;
            int lineBottom = staticLayout.getLineBottom(staticLayout.getLineCount() - 1) + AndroidUtilities.m1146dp(5.0f);
            this.titleHeight = lineBottom;
            int i9 = lineBottom + this.additionPrizeHeight;
            StaticLayout staticLayout2 = this.topLayout;
            this.topHeight = i9 + staticLayout2.getLineBottom(staticLayout2.getLineCount() - 1);
            StaticLayout staticLayout3 = this.bottomLayout;
            this.bottomHeight = staticLayout3.getLineBottom(staticLayout3.getLineCount() - 1);
            StaticLayout staticLayout4 = this.countriesLayout;
            int lineBottom2 = staticLayout4 != null ? staticLayout4.getLineBottom(staticLayout4.getLineCount() - 1) + AndroidUtilities.m1146dp(12.0f) : 0;
            this.countriesHeight = lineBottom2;
            int i10 = this.measuredHeight + this.topHeight + lineBottom2 + this.bottomHeight;
            this.measuredHeight = i10;
            this.measuredHeight = i10 + AndroidUtilities.m1146dp(128.0f);
            this.measuredWidth = iMax;
            if (this.isStars) {
                if (this.counterIcon == null) {
                    this.counterIcon = ApplicationLoader.applicationContext.getResources().getDrawable(C2369R.drawable.filled_giveaway_stars).mutate();
                }
                this.counterStr = LocaleController.formatNumber((int) tL_messageMediaGiveaway.stars, ',');
            } else {
                this.counterIcon = null;
                this.counterStr = "x" + tL_messageMediaGiveaway.quantity;
            }
            TextPaint textPaint2 = this.counterTextPaint;
            String str5 = this.counterStr;
            textPaint2.getTextBounds(str5, 0, str5.length(), this.counterTextBounds);
            if (tL_messageMediaGiveaway.stars != 0) {
                this.counterTextBounds.right += AndroidUtilities.m1146dp(20.0f);
            }
            Arrays.fill(this.avatarVisible, false);
            this.measuredHeight += AndroidUtilities.m1146dp(30.0f);
            ArrayList arrayList3 = new ArrayList(tL_messageMediaGiveaway.channels.size());
            ArrayList arrayList4 = tL_messageMediaGiveaway.channels;
            int size2 = arrayList4.size();
            int i11 = 0;
            while (i11 < size2) {
                Object obj2 = arrayList4.get(i11);
                i11++;
                Long l = (Long) obj2;
                if (MessagesController.getInstance(UserConfig.selectedAccount).getChat(l) != null) {
                    arrayList3.add(l);
                }
            }
            float f5 = 0.0f;
            for (int i12 = 0; i12 < arrayList3.size(); i12++) {
                Long l2 = (Long) arrayList3.get(i12);
                long jLongValue = l2.longValue();
                TLRPC.Chat chat = MessagesController.getInstance(UserConfig.selectedAccount).getChat(l2);
                if (chat != null) {
                    this.avatarVisible[i12] = true;
                    this.chats[i12] = chat;
                    this.chatTitles[i12] = TextUtils.ellipsize(Emoji.replaceEmoji(chat.title, this.chatTextPaint.getFontMetricsInt(), false), this.chatTextPaint, 0.8f * f2, TextUtils.TruncateAt.END);
                    float[] fArr = this.chatTitleWidths;
                    TextPaint textPaint3 = this.chatTextPaint;
                    CharSequence charSequence = this.chatTitles[i12];
                    fArr[i12] = textPaint3.measureText(charSequence, 0, charSequence.length());
                    float fM1146dp2 = this.chatTitleWidths[i12] + AndroidUtilities.m1146dp(40.0f);
                    f5 += fM1146dp2;
                    if (i12 > 0) {
                        boolean[] zArr = this.needNewRow;
                        boolean z = f5 > 0.9f * f2;
                        zArr[i12] = z;
                        if (z) {
                            this.measuredHeight += AndroidUtilities.m1146dp(30.0f);
                            f5 = fM1146dp2;
                        }
                    } else {
                        this.needNewRow[i12] = false;
                    }
                    this.avatarDrawables[i12].setInfo(chat);
                    this.avatarImageReceivers[i12].setForUserOrChat(chat, this.avatarDrawables[i12]);
                    this.avatarImageReceivers[i12].setImageCoords(0.0f, 0.0f, AndroidUtilities.m1146dp(24.0f), AndroidUtilities.m1146dp(24.0f));
                } else {
                    this.chats[i12] = null;
                    this.avatarVisible[i12] = false;
                    this.chatTitles[i12] = "";
                    this.needNewRow[i12] = false;
                    this.chatTitleWidths[i12] = AndroidUtilities.m1146dp(20.0f);
                    this.avatarDrawables[i12].setInfo(jLongValue, "", "");
                }
            }
        }
    }

    private int getChatColor(TLRPC.Chat chat, Theme.ResourcesProvider resourcesProvider) {
        if (this.messageObject.isOutOwner()) {
            return Theme.getColor(Theme.key_chat_outPreviewInstantText, resourcesProvider);
        }
        int colorId = ChatObject.getColorId(chat);
        if (colorId < 7) {
            return Theme.getColor(Theme.keys_avatar_nameInMessage[colorId], resourcesProvider);
        }
        MessagesController.PeerColors peerColors = MessagesController.getInstance(UserConfig.selectedAccount).peerColors;
        MessagesController.PeerColor color = peerColors == null ? null : peerColors.getColor(colorId);
        if (color != null) {
            return color.getColor(0, resourcesProvider);
        }
        return Theme.getColor(Theme.keys_avatar_nameInMessage[0], resourcesProvider);
    }

    public void draw(Canvas canvas, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
        float f;
        boolean[] zArr;
        int i3;
        Canvas canvas2 = canvas;
        MessageObject messageObject = this.messageObject;
        if (messageObject == null || !messageObject.isGiveaway()) {
            return;
        }
        if (this.selectorDrawable == null) {
            int color = Theme.getColor(Theme.key_listSelector);
            this.selectorColor = color;
            Drawable drawableCreateRadSelectorDrawable = Theme.createRadSelectorDrawable(color, 12, 12);
            this.selectorDrawable = drawableCreateRadSelectorDrawable;
            drawableCreateRadSelectorDrawable.setCallback(this.parentView);
        }
        this.textPaint.setColor(Theme.chat_msgTextPaint.getColor());
        this.textDividerPaint.setColor(Theme.multAlpha(Theme.chat_msgTextPaint.getColor(), 0.45f));
        this.lineDividerPaint.setColor(Theme.multAlpha(Theme.chat_msgTextPaint.getColor(), 0.15f));
        this.countriesTextPaint.setColor(Theme.chat_msgTextPaint.getColor());
        if (this.messageObject.isOutOwner()) {
            TextPaint textPaint = this.chatTextPaint;
            int i4 = Theme.key_chat_outPreviewInstantText;
            textPaint.setColor(Theme.getColor(i4, resourcesProvider));
            this.counterBgPaint.setColor(Theme.getColor(i4, resourcesProvider));
            this.chatBgPaint.setColor(Theme.getColor(Theme.key_chat_outReplyLine, resourcesProvider));
        } else {
            TextPaint textPaint2 = this.chatTextPaint;
            int i5 = Theme.key_chat_inPreviewInstantText;
            textPaint2.setColor(Theme.getColor(i5, resourcesProvider));
            this.counterBgPaint.setColor(Theme.getColor(i5, resourcesProvider));
            this.chatBgPaint.setColor(Theme.getColor(Theme.key_chat_inReplyLine, resourcesProvider));
        }
        if (this.isStars) {
            this.counterBgPaint.setColor(Theme.getColor(Theme.key_starsGradient1, resourcesProvider));
        }
        canvas2.save();
        int iM1146dp = i2 - AndroidUtilities.m1146dp(4.0f);
        canvas2.translate(iM1146dp, i);
        this.containerRect.set(iM1146dp, i, getMeasuredWidth() + iM1146dp, getMeasuredHeight() + i);
        canvas2.saveLayer(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.saveLayerPaint, 31);
        this.giftReceiver.draw(canvas2);
        float measuredWidth = getMeasuredWidth() / 2.0f;
        float fM1146dp = AndroidUtilities.m1146dp(106.0f);
        int iWidth = this.counterTextBounds.width() + AndroidUtilities.m1146dp(12.0f);
        int iHeight = this.counterTextBounds.height() + AndroidUtilities.m1146dp(10.0f);
        this.countRect.set(measuredWidth - ((AndroidUtilities.m1146dp(2.0f) + iWidth) / 2.0f), fM1146dp - ((AndroidUtilities.m1146dp(2.0f) + iHeight) / 2.0f), ((iWidth + AndroidUtilities.m1146dp(2.0f)) / 2.0f) + measuredWidth, ((iHeight + AndroidUtilities.m1146dp(2.0f)) / 2.0f) + fM1146dp);
        canvas2.drawRoundRect(this.countRect, AndroidUtilities.m1146dp(11.0f), AndroidUtilities.m1146dp(11.0f), this.clipRectPaint);
        float f2 = iWidth / 2.0f;
        float f3 = iHeight / 2.0f;
        this.countRect.set(measuredWidth - f2, fM1146dp - f3, f2 + measuredWidth, fM1146dp + f3);
        canvas2.drawRoundRect(this.countRect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), this.counterBgPaint);
        Drawable drawable = this.counterIcon;
        if (drawable != null) {
            drawable.setBounds(((int) this.countRect.left) + AndroidUtilities.m1146dp(5.0f), ((int) this.countRect.centerY()) - AndroidUtilities.m1146dp(6.96f), ((int) this.countRect.left) + AndroidUtilities.m1146dp(21.24f), ((int) this.countRect.centerY()) + AndroidUtilities.m1146dp(6.96f));
            this.counterIcon.draw(canvas2);
        }
        canvas2.drawText(this.counterStr, this.countRect.centerX() + AndroidUtilities.m1146dp(this.isStars ? 8.0f : 0.0f), this.countRect.centerY() + AndroidUtilities.m1146dp(4.0f), this.isStars ? this.counterStarsTextPaint : this.counterTextPaint);
        canvas2.restore();
        canvas2.translate(0.0f, AndroidUtilities.m1146dp(128.0f));
        int iM1146dp2 = i + AndroidUtilities.m1146dp(128.0f);
        canvas2.save();
        canvas2.translate(this.diffTextWidth / 2.0f, 0.0f);
        this.titleLayout.draw(canvas2);
        canvas2.translate(0.0f, this.titleHeight);
        if (this.additionPrizeLayout != null) {
            canvas2.restore();
            canvas2.save();
            float fM1146dp2 = (this.titleHeight + this.additionPrizeHeight) - AndroidUtilities.m1146dp(6.0f);
            float f4 = this.measuredWidth / 2.0f;
            canvas2.drawText(this.textDivider, f4, fM1146dp2, this.textDividerPaint);
            f = 16.0f;
            canvas2.drawLine(AndroidUtilities.m1146dp(17.0f), fM1146dp2 - AndroidUtilities.m1146dp(4.0f), (f4 - (this.textDividerWidth / 2.0f)) - AndroidUtilities.m1146dp(6.0f), fM1146dp2 - AndroidUtilities.m1146dp(4.0f), this.lineDividerPaint);
            canvas2 = canvas;
            canvas2.drawLine(f4 + (this.textDividerWidth / 2.0f) + AndroidUtilities.m1146dp(6.0f), fM1146dp2 - AndroidUtilities.m1146dp(4.0f), this.measuredWidth - AndroidUtilities.m1146dp(16.0f), fM1146dp2 - AndroidUtilities.m1146dp(4.0f), this.lineDividerPaint);
            canvas2.translate((this.measuredWidth - this.additionPrizeLayout.getWidth()) / 2.0f, this.titleHeight);
            this.additionPrizeLayout.draw(canvas2);
            canvas2.restore();
            canvas2.save();
            canvas2.translate(this.diffTextWidth / 2.0f, this.additionPrizeHeight + this.titleHeight);
        } else {
            f = 16.0f;
        }
        this.topLayout.draw(canvas2);
        canvas2.restore();
        canvas2.translate(0.0f, this.topHeight + AndroidUtilities.m1146dp(6.0f));
        int i6 = 0;
        int iM1146dp3 = iM1146dp2 + this.topHeight + AndroidUtilities.m1146dp(6.0f);
        int i7 = 0;
        while (true) {
            boolean[] zArr2 = this.avatarVisible;
            if (i6 >= zArr2.length) {
                break;
            }
            if (zArr2[i6]) {
                canvas2.save();
                int i8 = i6;
                float fM1146dp3 = 0.0f;
                do {
                    fM1146dp3 += this.chatTitleWidths[i8] + AndroidUtilities.m1146dp(40.0f);
                    i8++;
                    zArr = this.avatarVisible;
                    if (i8 >= zArr.length || this.needNewRow[i8]) {
                        break;
                    }
                } while (zArr[i8]);
                float f5 = measuredWidth - (fM1146dp3 / 2.0f);
                canvas2.translate(f5, 0.0f);
                int iWidth2 = ((int) f5) + iM1146dp;
                int i9 = i6;
                while (true) {
                    int chatColor = getChatColor(this.chats[i9], resourcesProvider);
                    int i10 = this.pressedPos;
                    i3 = (i10 < 0 || i10 != i9) ? i7 : chatColor;
                    this.chatTextPaint.setColor(chatColor);
                    this.chatBgPaint.setColor(chatColor);
                    this.chatBgPaint.setAlpha(25);
                    this.avatarImageReceivers[i9].draw(canvas2);
                    CharSequence charSequence = this.chatTitles[i9];
                    int i11 = iWidth2;
                    canvas2.drawText(charSequence, 0, charSequence.length(), AndroidUtilities.m1146dp(30.0f), AndroidUtilities.m1146dp(f), this.chatTextPaint);
                    this.chatRect.set(0.0f, 0.0f, this.chatTitleWidths[i9] + AndroidUtilities.m1146dp(40.0f), AndroidUtilities.m1146dp(24.0f));
                    canvas2.drawRoundRect(this.chatRect, AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(12.0f), this.chatBgPaint);
                    float f6 = i11;
                    this.clickRect[i9].set(i11, iM1146dp3, (int) (this.chatRect.width() + f6), AndroidUtilities.m1146dp(24.0f) + iM1146dp3);
                    canvas2.translate(this.chatRect.width() + AndroidUtilities.m1146dp(6.0f), 0.0f);
                    iWidth2 = (int) (f6 + this.chatRect.width() + AndroidUtilities.m1146dp(6.0f));
                    i9++;
                    boolean[] zArr3 = this.avatarVisible;
                    if (i9 >= zArr3.length || this.needNewRow[i9] || !zArr3[i9]) {
                        break;
                    } else {
                        i7 = i3;
                    }
                }
                canvas2.restore();
                canvas2.translate(0.0f, AndroidUtilities.m1146dp(30.0f));
                iM1146dp3 += AndroidUtilities.m1146dp(30.0f);
                i6 = i9;
                i7 = i3;
            } else {
                i6++;
            }
        }
        if (this.countriesLayout != null) {
            canvas2.save();
            canvas2.translate((this.measuredWidth - this.countriesLayout.getWidth()) / 2.0f, AndroidUtilities.m1146dp(4.0f));
            this.countriesLayout.draw(canvas2);
            canvas2.restore();
            canvas2.translate(0.0f, this.countriesHeight);
        }
        canvas2.translate(0.0f, AndroidUtilities.m1146dp(6.0f));
        canvas2.save();
        canvas2.translate(this.diffTextWidth / 2.0f, 0.0f);
        this.bottomLayout.draw(canvas2);
        canvas2.restore();
        canvas2.restore();
        if (this.pressedPos >= 0) {
            int iMultAlpha = Theme.multAlpha(i7, Theme.isCurrentThemeDark() ? 0.12f : 0.1f);
            if (this.selectorColor != iMultAlpha) {
                Drawable drawable2 = this.selectorDrawable;
                this.selectorColor = iMultAlpha;
                Theme.setSelectorDrawableColor(drawable2, iMultAlpha, true);
            }
            this.selectorDrawable.setBounds(this.clickRect[this.pressedPos]);
            this.selectorDrawable.setCallback(this.parentView);
        }
    }

    public void onDetachedFromWindow() {
        ImageReceiver imageReceiver = this.giftReceiver;
        if (imageReceiver != null) {
            imageReceiver.onDetachedFromWindow();
        }
        ImageReceiver[] imageReceiverArr = this.avatarImageReceivers;
        if (imageReceiverArr != null) {
            for (ImageReceiver imageReceiver2 : imageReceiverArr) {
                imageReceiver2.onDetachedFromWindow();
            }
        }
    }

    public void onAttachedToWindow() {
        ImageReceiver imageReceiver = this.giftReceiver;
        if (imageReceiver != null) {
            imageReceiver.onAttachedToWindow();
        }
        ImageReceiver[] imageReceiverArr = this.avatarImageReceivers;
        if (imageReceiverArr != null) {
            for (ImageReceiver imageReceiver2 : imageReceiverArr) {
                imageReceiver2.onAttachedToWindow();
            }
        }
    }

    public int getMeasuredHeight() {
        return this.measuredHeight;
    }

    public int getMeasuredWidth() {
        return this.measuredWidth;
    }

    private void createImages() {
        if (this.avatarImageReceivers != null) {
            return;
        }
        this.avatarImageReceivers = new ImageReceiver[10];
        this.avatarDrawables = new AvatarDrawable[10];
        this.avatarVisible = new boolean[10];
        int i = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.avatarImageReceivers;
            if (i >= imageReceiverArr.length) {
                return;
            }
            imageReceiverArr[i] = new ImageReceiver(this.parentView);
            this.avatarImageReceivers[i].setAllowLoadingOnAttachedOnly(true);
            this.avatarImageReceivers[i].setRoundRadius(AndroidUtilities.m1146dp(12.0f));
            this.avatarDrawables[i] = new AvatarDrawable();
            this.avatarDrawables[i].setTextSize(AndroidUtilities.m1146dp(18.0f));
            this.clickRect[i] = new Rect();
            i++;
        }
    }

    private void checkArraysLimits(int i) {
        ImageReceiver[] imageReceiverArr = this.avatarImageReceivers;
        if (imageReceiverArr.length < i) {
            int length = imageReceiverArr.length;
            this.avatarImageReceivers = (ImageReceiver[]) Arrays.copyOf(imageReceiverArr, i);
            this.avatarDrawables = (AvatarDrawable[]) Arrays.copyOf(this.avatarDrawables, i);
            this.avatarVisible = Arrays.copyOf(this.avatarVisible, i);
            this.chatTitles = (CharSequence[]) Arrays.copyOf(this.chatTitles, i);
            this.chatTitleWidths = Arrays.copyOf(this.chatTitleWidths, i);
            this.needNewRow = Arrays.copyOf(this.needNewRow, i);
            this.clickRect = (Rect[]) Arrays.copyOf(this.clickRect, i);
            this.chats = (TLRPC.Chat[]) Arrays.copyOf(this.chats, i);
            for (int i2 = length - 1; i2 < i; i2++) {
                this.avatarImageReceivers[i2] = new ImageReceiver(this.parentView);
                this.avatarImageReceivers[i2].setAllowLoadingOnAttachedOnly(true);
                this.avatarImageReceivers[i2].setRoundRadius(AndroidUtilities.m1146dp(12.0f));
                this.avatarDrawables[i2] = new AvatarDrawable();
                this.avatarDrawables[i2].setTextSize(AndroidUtilities.m1146dp(18.0f));
                this.clickRect[i2] = new Rect();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a7 A[LOOP:0: B:12:0x004b->B:31:0x00a7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00aa A[EDGE_INSN: B:50:0x00aa->B:32:0x00aa BREAK  A[LOOP:0: B:12:0x004b->B:31:0x00a7], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setGiftImage(org.telegram.messenger.MessageObject r20) {
        /*
            Method dump skipped, instructions count: 248
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Premium.boosts.cells.msg.GiveawayMessageCell.setGiftImage(org.telegram.messenger.MessageObject):void");
    }
}
