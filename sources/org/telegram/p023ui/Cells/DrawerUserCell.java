package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.badges.BadgesController;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.GroupCreateCheckBox;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Premium.PremiumGradient;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class DrawerUserCell extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private int accountNumber;
    private final AvatarDrawable avatarDrawable;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable botVerification;
    private final GroupCreateCheckBox checkBox;
    private final BackupImageView imageView;
    private final RectF rect;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable status;
    private final SimpleTextView textView;

    public DrawerUserCell(Context context) {
        super(context);
        this.rect = new RectF();
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        this.avatarDrawable = avatarDrawable;
        avatarDrawable.setTextSize(AndroidUtilities.m1146dp(20.0f));
        BackupImageView backupImageView = new BackupImageView(context);
        this.imageView = backupImageView;
        backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(36.0f));
        addView(backupImageView, LayoutHelper.createFrame(36, 36.0f, 51, 14.0f, 6.0f, 0.0f, 0.0f));
        SimpleTextView simpleTextView = new SimpleTextView(context);
        this.textView = simpleTextView;
        simpleTextView.setPadding(0, AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f));
        simpleTextView.setTextColor(Theme.getColor(Theme.key_chats_menuItemText));
        simpleTextView.setTextSize(15);
        simpleTextView.setTypeface(AndroidUtilities.bold());
        simpleTextView.setMaxLines(1);
        simpleTextView.setGravity(19);
        simpleTextView.setEllipsizeByGradient(24);
        addView(simpleTextView, LayoutHelper.createFrame(-1, -2.0f, 19, 72.0f, 0.0f, 14.0f, 0.0f));
        this.botVerification = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(simpleTextView, AndroidUtilities.m1146dp(18.0f));
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(simpleTextView, AndroidUtilities.m1146dp(20.0f));
        this.status = swapAnimatedEmojiDrawable;
        simpleTextView.setRightDrawable(swapAnimatedEmojiDrawable);
        GroupCreateCheckBox groupCreateCheckBox = new GroupCreateCheckBox(context);
        this.checkBox = groupCreateCheckBox;
        groupCreateCheckBox.setChecked(true, false);
        groupCreateCheckBox.setCheckScale(0.9f);
        groupCreateCheckBox.setInnerRadDiff(AndroidUtilities.m1146dp(1.5f));
        groupCreateCheckBox.setColorKeysOverrides(Theme.key_chats_unreadCounterText, Theme.key_chats_unreadCounter, Theme.key_chats_menuBackground);
        addView(groupCreateCheckBox, LayoutHelper.createFrame(18, 18.0f, 51, 37.0f, 27.0f, 0.0f, 0.0f));
        setWillNotDraw(false);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(48.0f), TLObject.FLAG_30));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.textView.setTextColor(Theme.getColor(Theme.key_chats_menuItemText));
        this.status.attach();
        this.botVerification.attach();
        for (int i = 0; i < 16; i++) {
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.updateInterfaces);
        }
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.status.detach();
        this.botVerification.detach();
        for (int i = 0; i < 16; i++) {
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.updateInterfaces);
        }
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        if (this.textView.getRightDrawable() instanceof AnimatedEmojiDrawable.WrapSizeDrawable) {
            Drawable drawable = ((AnimatedEmojiDrawable.WrapSizeDrawable) this.textView.getRightDrawable()).getDrawable();
            if (drawable instanceof AnimatedEmojiDrawable) {
                ((AnimatedEmojiDrawable) drawable).removeView(this.textView);
            }
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            int i3 = this.accountNumber;
            if (i2 == i3) {
                setAccount(i3);
                return;
            }
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            this.textView.invalidate();
        } else {
            if (i != NotificationCenter.updateInterfaces || (((Integer) objArr[0]).intValue() & MessagesController.UPDATE_MASK_EMOJI_STATUS) <= 0) {
                return;
            }
            setAccount(this.accountNumber);
        }
    }

    public void setAccount(int i) {
        this.accountNumber = i;
        TLRPC.User currentUser = UserConfig.getInstance(i).getCurrentUser();
        if (currentUser == null) {
            return;
        }
        this.avatarDrawable.setInfo(i, currentUser);
        CharSequence name = ContactsController.formatName(currentUser.first_name, currentUser.last_name);
        try {
            name = Emoji.replaceEmoji(name, this.textView.getPaint().getFontMetricsInt(), false);
        } catch (Exception unused) {
        }
        this.textView.setText(name);
        Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(currentUser);
        BadgeDTO badge = BadgesController.INSTANCE.getBadge(currentUser);
        if (emojiStatusDocumentId != null) {
            this.textView.setDrawablePadding(AndroidUtilities.m1146dp(4.0f));
            this.status.set(emojiStatusDocumentId.longValue(), true);
            this.status.setParticles(DialogObject.isEmojiStatusCollectible(currentUser.emoji_status), true);
            this.textView.setRightDrawableOutside(true);
        } else if (badge != null) {
            this.textView.setDrawablePadding(AndroidUtilities.m1146dp(2.0f));
            this.status.set(badge.getDocumentId(), true);
            this.status.setParticles(true, true);
            this.textView.setRightDrawableOutside(true);
        } else if (MessagesController.getInstance(i).isPremiumUser(currentUser)) {
            this.textView.setDrawablePadding(AndroidUtilities.m1146dp(6.0f));
            this.status.set(PremiumGradient.getInstance().premiumStarDrawableMini, true);
            this.status.setParticles(false, true);
            this.textView.setRightDrawableOutside(true);
        } else {
            this.status.set((Drawable) null, true);
            this.status.setParticles(false, true);
            this.textView.setRightDrawableOutside(false);
        }
        long botVerificationIcon = DialogObject.getBotVerificationIcon(currentUser);
        if (botVerificationIcon == 0 || ConnectionsManager.getInstance(i).isTestBackend() != ConnectionsManager.getInstance(UserConfig.selectedAccount).isTestBackend()) {
            this.botVerification.set((Drawable) null, false);
            this.textView.setLeftDrawable((Drawable) null);
        } else {
            this.botVerification.set(botVerificationIcon, false);
            this.botVerification.setColor(Integer.valueOf(Theme.getColor(Theme.key_featuredStickers_addButton)));
            this.textView.setLeftDrawable(this.botVerification);
        }
        this.status.setColor(Integer.valueOf(Theme.getColor(Theme.key_chats_verifiedBackground)));
        this.imageView.getImageReceiver().setCurrentAccount(i);
        this.imageView.setForUserOrChat(currentUser, this.avatarDrawable);
        this.checkBox.setVisibility(i != UserConfig.selectedAccount ? 4 : 0);
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (UserConfig.getActivatedAccountsCount() <= 1 || !NotificationsController.getInstance(this.accountNumber).showBadgeNumber) {
            this.textView.setRightPadding(0);
            return;
        }
        int mainUnreadCount = MessagesStorage.getInstance(this.accountNumber).getMainUnreadCount();
        if (mainUnreadCount <= 0) {
            this.textView.setRightPadding(0);
            return;
        }
        String str = String.format("%d", Integer.valueOf(mainUnreadCount));
        int iM1146dp = AndroidUtilities.m1146dp(12.5f);
        int iCeil = (int) Math.ceil(Theme.dialogs_countTextPaint.measureText(str));
        int iMax = Math.max(AndroidUtilities.m1146dp(10.0f), iCeil);
        this.rect.set(((getMeasuredWidth() - iMax) - AndroidUtilities.m1146dp(25.0f)) - AndroidUtilities.m1146dp(5.5f), iM1146dp, r4 + iMax + AndroidUtilities.m1146dp(14.0f), AndroidUtilities.m1146dp(23.0f) + iM1146dp);
        RectF rectF = this.rect;
        float f = AndroidUtilities.density;
        canvas.drawRoundRect(rectF, f * 11.5f, f * 11.5f, Theme.dialogs_countPaint);
        RectF rectF2 = this.rect;
        canvas.drawText(str, rectF2.left + ((rectF2.width() - iCeil) / 2.0f), iM1146dp + AndroidUtilities.m1146dp(16.0f), Theme.dialogs_countTextPaint);
        this.textView.setRightPadding(iMax + AndroidUtilities.m1146dp(26.0f));
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(16);
    }
}
