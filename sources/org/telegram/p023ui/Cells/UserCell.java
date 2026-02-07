package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.exteragram.messenger.ExteraConfig;
import com.radolyn.ayugram.controllers.AyuFilterController;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.CheckBox2;
import org.telegram.p023ui.Components.CheckBoxSquare;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.NotificationsSettingsActivity;
import org.telegram.p023ui.Stories.StoriesListPlaceProvider;
import org.telegram.p023ui.Stories.StoriesUtilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class UserCell extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private TextView addButton;
    private TextView adminTextView;
    protected AvatarDrawable avatarDrawable;
    public BackupImageView avatarImageView;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable botVerification;
    private CheckBox2 checkBox;
    private ImageView checkBox3;
    private CheckBoxSquare checkBoxBig;
    private ImageView closeView;
    private int currentAccount;
    private int currentDrawable;
    private int currentId;
    private CharSequence currentName;
    private Object currentObject;
    private CharSequence currentStatus;
    protected long dialogId;
    protected boolean disableFilter;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatus;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatus2;
    private TLRPC.EncryptedChat encryptedChat;
    private ImageView imageView;
    private TLRPC.FileLocation lastAvatar;
    private String lastName;
    private int lastStatus;
    private boolean mutual;
    private final ImageView mutualContactView;
    protected SimpleTextView nameTextView;
    public boolean needDivider;
    private Drawable premiumDrawable;
    protected Theme.ResourcesProvider resourcesProvider;
    private boolean selfAsSavedMessages;
    private int statusColor;
    private int statusOnlineColor;
    protected SimpleTextView statusTextView;
    private boolean storiable;
    public StoriesUtilities.AvatarStoryParams storyParams;

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public void openStory(long j, Runnable runnable) {
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment != null) {
            lastFragment.getOrCreateStoryViewer().doOnAnimationReady(runnable);
            lastFragment.getOrCreateStoryViewer().open(getContext(), j, StoriesListPlaceProvider.m1334of((RecyclerListView) getParent()));
        }
    }

    public UserCell(Context context, int i, int i2, boolean z) {
        this(context, i, i2, z, false, null);
    }

    public UserCell(Context context, int i, int i2, boolean z, Theme.ResourcesProvider resourcesProvider) {
        this(context, i, i2, z, false, resourcesProvider);
    }

    public UserCell(Context context, int i, int i2, boolean z, boolean z2) {
        this(context, i, i2, z, z2, null);
    }

    public UserCell(Context context, int i, int i2, boolean z, boolean z2, Theme.ResourcesProvider resourcesProvider) {
        int iCeil;
        int i3;
        float f;
        super(context);
        this.currentAccount = UserConfig.selectedAccount;
        this.storyParams = new StoriesUtilities.AvatarStoryParams(false) { // from class: org.telegram.ui.Cells.UserCell.1
            @Override // org.telegram.ui.Stories.StoriesUtilities.AvatarStoryParams
            public void openStory(long j, Runnable runnable) {
                UserCell.this.openStory(j, runnable);
            }
        };
        this.disableFilter = false;
        this.resourcesProvider = resourcesProvider;
        if (z2) {
            TextView textView = new TextView(context);
            this.addButton = textView;
            textView.setGravity(17);
            this.addButton.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText, resourcesProvider));
            this.addButton.setTextSize(1, 14.0f);
            this.addButton.setTypeface(AndroidUtilities.bold());
            this.addButton.setBackgroundDrawable(Theme.AdaptiveRipple.filledRectByKey(Theme.key_featuredStickers_addButton, 4.0f));
            this.addButton.setText(LocaleController.getString(C2369R.string.Add));
            this.addButton.setPadding(AndroidUtilities.m1146dp(17.0f), 0, AndroidUtilities.m1146dp(17.0f), 0);
            View view = this.addButton;
            boolean z3 = LocaleController.isRTL;
            addView(view, LayoutHelper.createFrame(-2, 28.0f, (z3 ? 3 : 5) | 48, z3 ? 14.0f : 0.0f, 15.0f, z3 ? 0.0f : 14.0f, 0.0f));
            iCeil = (int) Math.ceil((this.addButton.getPaint().measureText(this.addButton.getText().toString()) + AndroidUtilities.m1146dp(48.0f)) / AndroidUtilities.density);
        } else {
            iCeil = 0;
        }
        this.statusColor = Theme.getColor(Theme.key_windowBackgroundWhiteGrayText, resourcesProvider);
        this.statusOnlineColor = Theme.getColor(Theme.key_windowBackgroundWhiteBlueText, resourcesProvider);
        this.avatarDrawable = new AvatarDrawable();
        BackupImageView backupImageView = new BackupImageView(context) { // from class: org.telegram.ui.Cells.UserCell.2
            @Override // org.telegram.p023ui.Components.BackupImageView, android.view.View
            protected void onDraw(Canvas canvas) {
                if (UserCell.this.storiable) {
                    UserCell.this.storyParams.originalAvatarRect.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
                    UserCell userCell = UserCell.this;
                    StoriesUtilities.drawAvatarWithStory(userCell.dialogId, canvas, this.imageReceiver, userCell.storyParams);
                    return;
                }
                super.onDraw(canvas);
            }

            @Override // android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (UserCell.this.storyParams.checkOnTouchEvent(motionEvent, this)) {
                    return true;
                }
                return super.onTouchEvent(motionEvent);
            }
        };
        this.avatarImageView = backupImageView;
        backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(46.0f));
        View view2 = this.avatarImageView;
        boolean z4 = LocaleController.isRTL;
        addView(view2, LayoutHelper.createFrame(46, 46.0f, (z4 ? 5 : 3) | 48, z4 ? 0.0f : i + 7, 6.0f, z4 ? i + 7 : 0.0f, 0.0f));
        setClipChildren(false);
        SimpleTextView simpleTextView = new SimpleTextView(context);
        this.nameTextView = simpleTextView;
        simpleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
        this.nameTextView.setTypeface(AndroidUtilities.bold());
        this.nameTextView.setTextSize(16);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        View view3 = this.nameTextView;
        boolean z5 = LocaleController.isRTL;
        int i4 = (z5 ? 5 : 3) | 48;
        if (z5) {
            i3 = (i2 == 2 ? 18 : 0) + 28 + iCeil;
        } else {
            i3 = i + 64;
        }
        float f2 = i3;
        if (z5) {
            f = i + 64;
        } else {
            f = (i2 != 2 ? 0 : 18) + 28 + iCeil;
        }
        addView(view3, LayoutHelper.createFrame(-1, 20.0f, i4, f2, 10.0f, f, 0.0f));
        this.botVerification = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this.nameTextView, AndroidUtilities.m1146dp(20.0f));
        this.emojiStatus = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this.nameTextView, AndroidUtilities.m1146dp(20.0f));
        this.emojiStatus2 = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this.nameTextView, AndroidUtilities.m1146dp(20.0f));
        SimpleTextView simpleTextView2 = new SimpleTextView(context);
        this.statusTextView = simpleTextView2;
        simpleTextView2.setTextSize(15);
        this.statusTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_REGULAR));
        this.statusTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        View view4 = this.statusTextView;
        boolean z6 = LocaleController.isRTL;
        addView(view4, LayoutHelper.createFrame(-1, 20.0f, (z6 ? 5 : 3) | 48, z6 ? iCeil + 28 : i + 64, 32.0f, z6 ? i + 64 : iCeil + 28, 0.0f));
        ImageView imageView = new ImageView(context);
        this.imageView = imageView;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView.setScaleType(scaleType);
        ImageView imageView2 = this.imageView;
        int i5 = Theme.key_windowBackgroundWhiteGrayIcon;
        int color = Theme.getColor(i5, resourcesProvider);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        imageView2.setColorFilter(new PorterDuffColorFilter(color, mode));
        this.imageView.setVisibility(8);
        View view5 = this.imageView;
        boolean z7 = LocaleController.isRTL;
        addView(view5, LayoutHelper.createFrame(-2, -2.0f, (z7 ? 5 : 3) | 16, z7 ? 0.0f : 16.0f, 0.0f, z7 ? 16.0f : 0.0f, 0.0f));
        if (i2 == 2) {
            CheckBoxSquare checkBoxSquare = new CheckBoxSquare(context, false);
            this.checkBoxBig = checkBoxSquare;
            boolean z8 = LocaleController.isRTL;
            addView(checkBoxSquare, LayoutHelper.createFrame(18, 18.0f, (z8 ? 3 : 5) | 16, z8 ? 19.0f : 0.0f, 0.0f, z8 ? 0.0f : 19.0f, 0.0f));
        } else if (i2 == 1) {
            CheckBox2 checkBox2 = new CheckBox2(context, 21, resourcesProvider);
            this.checkBox = checkBox2;
            checkBox2.setDrawUnchecked(false);
            this.checkBox.setDrawBackgroundAsArc(3);
            this.checkBox.setColor(-1, Theme.key_windowBackgroundWhite, Theme.key_checkboxCheck);
            View view6 = this.checkBox;
            boolean z9 = LocaleController.isRTL;
            addView(view6, LayoutHelper.createFrame(24, 24.0f, (z9 ? 5 : 3) | 48, z9 ? 0.0f : i + 37, 36.0f, z9 ? i + 37 : 0.0f, 0.0f));
        } else if (i2 == 3) {
            ImageView imageView3 = new ImageView(context);
            this.checkBox3 = imageView3;
            imageView3.setScaleType(scaleType);
            this.checkBox3.setImageResource(C2369R.drawable.account_check);
            this.checkBox3.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider), mode));
            this.checkBox3.setVisibility(8);
            View view7 = this.checkBox3;
            boolean z10 = LocaleController.isRTL;
            addView(view7, LayoutHelper.createFrame(24, 24.0f, (z10 ? 3 : 5) | 16, z10 ? i + 10 : 0.0f, 0.0f, z10 ? 0.0f : i + 10, 0.0f));
        }
        if (z) {
            TextView textView2 = new TextView(context);
            this.adminTextView = textView2;
            textView2.setTextSize(1, 14.0f);
            this.adminTextView.setTextColor(Theme.getColor(Theme.key_profile_creatorIcon, resourcesProvider));
            View view8 = this.adminTextView;
            boolean z11 = LocaleController.isRTL;
            addView(view8, LayoutHelper.createFrame(-2, -2.0f, (z11 ? 3 : 5) | 48, z11 ? 23.0f : 0.0f, 10.0f, z11 ? 0.0f : 23.0f, 0.0f));
        }
        ImageView imageView4 = new ImageView(context);
        this.mutualContactView = imageView4;
        imageView4.setImageResource(C2369R.drawable.msg_switch);
        imageView4.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_player_actionBarSelector)));
        imageView4.setScaleType(scaleType);
        imageView4.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i5), mode));
        imageView4.setVisibility(8);
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Cells.UserCell$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view9) {
                NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 1, LocaleController.getString(C2369R.string.MutualContactInfo));
            }
        });
        boolean z12 = LocaleController.isRTL;
        addView(imageView4, LayoutHelper.createFrame(40, 40.0f, (z12 ? 3 : 5) | 16, z12 ? 10.0f : 0.0f, 0.0f, z12 ? 0.0f : 10.0f, 0.0f));
        setFocusable(true);
    }

    public void setAvatarPadding(int i) {
        int i2;
        float f;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.avatarImageView.getLayoutParams();
        layoutParams.leftMargin = AndroidUtilities.m1146dp(LocaleController.isRTL ? 0.0f : i + 7);
        layoutParams.rightMargin = AndroidUtilities.m1146dp(LocaleController.isRTL ? i + 7 : 0.0f);
        this.avatarImageView.setLayoutParams(layoutParams);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.nameTextView.getLayoutParams();
        if (LocaleController.isRTL) {
            i2 = (this.checkBoxBig != null ? 18 : 0) + 28;
        } else {
            i2 = i + 64;
        }
        layoutParams2.leftMargin = AndroidUtilities.m1146dp(i2);
        if (LocaleController.isRTL) {
            f = i + 64;
        } else {
            f = (this.checkBoxBig != null ? 18 : 0) + 28;
        }
        layoutParams2.rightMargin = AndroidUtilities.m1146dp(f);
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.statusTextView.getLayoutParams();
        layoutParams3.leftMargin = AndroidUtilities.m1146dp(LocaleController.isRTL ? 28.0f : i + 64);
        layoutParams3.rightMargin = AndroidUtilities.m1146dp(LocaleController.isRTL ? i + 64 : 28.0f);
        CheckBox2 checkBox2 = this.checkBox;
        if (checkBox2 != null) {
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) checkBox2.getLayoutParams();
            layoutParams4.leftMargin = AndroidUtilities.m1146dp(LocaleController.isRTL ? 0.0f : i + 37);
            layoutParams4.rightMargin = AndroidUtilities.m1146dp(LocaleController.isRTL ? i + 37 : 0.0f);
        }
    }

    public void setAddButtonVisible(boolean z) {
        TextView textView = this.addButton;
        if (textView == null) {
            return;
        }
        textView.setVisibility(z ? 0 : 8);
    }

    public void setAdminRole(String str) {
        TextView textView = this.adminTextView;
        if (textView == null) {
            return;
        }
        textView.setVisibility(str != null ? 0 : 8);
        this.adminTextView.setText(str);
        if (str != null) {
            CharSequence text = this.adminTextView.getText();
            setRightPadding((int) Math.ceil(this.adminTextView.getPaint().measureText(text, 0, text.length())), true, false);
        } else {
            setRightPadding(0, true, false);
        }
    }

    public void setRightPadding(int i, boolean z, boolean z2) {
        if (i > 0) {
            i += AndroidUtilities.m1146dp(6.0f);
        }
        if (z) {
            SimpleTextView simpleTextView = this.nameTextView;
            boolean z3 = LocaleController.isRTL;
            simpleTextView.setPadding(z3 ? i : 0, 0, !z3 ? i : 0, 0);
        }
        if (z2) {
            SimpleTextView simpleTextView2 = this.statusTextView;
            boolean z4 = LocaleController.isRTL;
            int i2 = z4 ? i : 0;
            if (z4) {
                i = 0;
            }
            simpleTextView2.setPadding(i2, 0, i, 0);
        }
    }

    public CharSequence getName() {
        return this.nameTextView.getText();
    }

    public void setData(Object obj, CharSequence charSequence, CharSequence charSequence2, int i) {
        setData(obj, null, charSequence, charSequence2, i, false);
    }

    public void setData(Object obj, CharSequence charSequence, CharSequence charSequence2, int i, boolean z) {
        setData(obj, null, charSequence, charSequence2, i, z);
    }

    public void setData(Object obj, TLRPC.EncryptedChat encryptedChat, CharSequence charSequence, CharSequence charSequence2, int i, boolean z) {
        if (obj == null && charSequence == null && charSequence2 == null) {
            this.currentStatus = null;
            this.currentName = null;
            this.storiable = false;
            this.currentObject = null;
            this.nameTextView.setText("");
            this.statusTextView.setText("");
            this.avatarImageView.setImageDrawable(null);
            return;
        }
        this.encryptedChat = encryptedChat;
        this.currentStatus = charSequence2;
        if (charSequence != null) {
            try {
                SimpleTextView simpleTextView = this.nameTextView;
                if (simpleTextView != null) {
                    charSequence = Emoji.replaceEmoji(charSequence, simpleTextView.getPaint().getFontMetricsInt(), false);
                }
            } catch (Exception unused) {
            }
        }
        this.currentName = charSequence;
        this.storiable = !(obj instanceof String);
        this.currentObject = obj;
        this.currentDrawable = i;
        this.needDivider = z;
        if (obj instanceof TLRPC.User) {
            TLRPC.User user = (TLRPC.User) obj;
            if (!this.disableFilter && AyuFilterController.getInstance(this.currentAccount).isBlocked(user.f1734id)) {
                this.currentObject = "AyuGram";
            }
        }
        setWillNotDraw(!this.needDivider);
        update(0);
    }

    public Object getCurrentObject() {
        return this.currentObject;
    }

    public void setException(NotificationsSettingsActivity.NotificationException notificationException, CharSequence charSequence, boolean z) {
        String string;
        TLRPC.User user;
        if (notificationException.story) {
            int i = notificationException.notify;
            if (i <= 0 && notificationException.auto) {
                string = LocaleController.getString(C2369R.string.NotificationEnabledAutomatically);
            } else if (i <= 0) {
                string = LocaleController.getString(C2369R.string.NotificationEnabled);
            } else {
                string = LocaleController.getString(C2369R.string.NotificationDisabled);
            }
        } else {
            boolean z2 = notificationException.hasCustom;
            int i2 = notificationException.notify;
            int i3 = notificationException.muteUntil;
            boolean z3 = true;
            if (i2 != 3 || i3 == Integer.MAX_VALUE) {
                if (i2 != 0 && i2 != 1) {
                    z3 = false;
                }
                if (z3 && z2) {
                    string = LocaleController.getString(C2369R.string.NotificationsCustom);
                } else {
                    string = LocaleController.getString(z3 ? C2369R.string.NotificationsUnmuted : C2369R.string.NotificationsMuted);
                }
            } else {
                int currentTime = i3 - ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
                if (currentTime <= 0) {
                    string = z2 ? LocaleController.getString(C2369R.string.NotificationsCustom) : LocaleController.getString(C2369R.string.NotificationsUnmuted);
                } else {
                    string = currentTime < 3600 ? LocaleController.formatString("WillUnmuteIn", C2369R.string.WillUnmuteIn, LocaleController.formatPluralString("Minutes", currentTime / 60, new Object[0])) : currentTime < 86400 ? LocaleController.formatString("WillUnmuteIn", C2369R.string.WillUnmuteIn, LocaleController.formatPluralString("Hours", (int) Math.ceil((currentTime / 60.0f) / 60.0f), new Object[0])) : currentTime < 31536000 ? LocaleController.formatString("WillUnmuteIn", C2369R.string.WillUnmuteIn, LocaleController.formatPluralString("Days", (int) Math.ceil(((currentTime / 60.0f) / 60.0f) / 24.0f), new Object[0])) : null;
                }
            }
            if (string == null) {
                string = LocaleController.getString(C2369R.string.NotificationsOff);
            }
            if (notificationException.auto) {
                string = string + ", Auto";
            }
        }
        String str = string;
        if (DialogObject.isEncryptedDialog(notificationException.did)) {
            TLRPC.EncryptedChat encryptedChat = MessagesController.getInstance(this.currentAccount).getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(notificationException.did)));
            if (encryptedChat == null || (user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(encryptedChat.user_id))) == null) {
                return;
            }
            setData(user, encryptedChat, charSequence, str, 0, false);
            return;
        }
        if (DialogObject.isUserDialog(notificationException.did)) {
            TLRPC.User user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(notificationException.did));
            if (user2 != null) {
                setData(user2, null, charSequence, str, 0, z);
                return;
            }
            return;
        }
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-notificationException.did));
        if (chat != null) {
            setData(chat, null, charSequence, str, 0, z);
        }
    }

    public void setNameTypeface(Typeface typeface) {
        this.nameTextView.setTypeface(typeface);
    }

    public void setCurrentId(int i) {
        this.currentId = i;
    }

    public void setChecked(boolean z, boolean z2) {
        CheckBox2 checkBox2 = this.checkBox;
        if (checkBox2 != null) {
            if (checkBox2.getVisibility() != 0) {
                this.checkBox.setVisibility(0);
            }
            this.checkBox.setChecked(z, z2);
            return;
        }
        CheckBoxSquare checkBoxSquare = this.checkBoxBig;
        if (checkBoxSquare != null) {
            if (checkBoxSquare.getVisibility() != 0) {
                this.checkBoxBig.setVisibility(0);
            }
            this.checkBoxBig.setChecked(z, z2);
        } else {
            ImageView imageView = this.checkBox3;
            if (imageView != null) {
                imageView.setVisibility(z ? 0 : 8);
            }
        }
    }

    public void setCheckDisabled(boolean z) {
        CheckBoxSquare checkBoxSquare = this.checkBoxBig;
        if (checkBoxSquare != null) {
            checkBoxSquare.setDisabled(z);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(58.0f) + (this.needDivider ? 1 : 0), TLObject.FLAG_30));
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        CheckBoxSquare checkBoxSquare = this.checkBoxBig;
        if (checkBoxSquare != null) {
            checkBoxSquare.invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:147:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0353  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x03e8  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x04b4  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x04c3  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x04f5  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0511  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0522  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:278:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void update(int r20) {
        /*
            Method dump skipped, instructions count: 1424
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.UserCell.update(int):void");
    }

    public void setSelfAsSavedMessages(boolean z) {
        this.selfAsSavedMessages = z;
    }

    public void setMutual(boolean z) {
        this.mutual = z;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(68.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(68.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        CheckBoxSquare checkBoxSquare = this.checkBoxBig;
        if (checkBoxSquare != null && checkBoxSquare.getVisibility() == 0) {
            accessibilityNodeInfo.setCheckable(true);
            accessibilityNodeInfo.setChecked(this.checkBoxBig.isChecked());
            accessibilityNodeInfo.setClassName("android.widget.CheckBox");
            return;
        }
        CheckBox2 checkBox2 = this.checkBox;
        if (checkBox2 == null || checkBox2.getVisibility() != 0) {
            return;
        }
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.checkBox.isChecked());
        accessibilityNodeInfo.setClassName("android.widget.CheckBox");
    }

    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.emojiLoaded) {
            this.nameTextView.invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        this.emojiStatus.attach();
        this.emojiStatus2.attach();
        this.botVerification.attach();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        this.emojiStatus.detach();
        this.emojiStatus2.detach();
        this.botVerification.detach();
        this.storyParams.onDetachFromWindow();
    }

    public long getDialogId() {
        return this.dialogId;
    }

    public void setFromUItem(int i, UItem uItem, boolean z) {
        CharSequence string;
        CharSequence string2;
        Object obj = uItem.chatType;
        if (obj != null) {
            setData(obj, uItem.text, null, 0, z);
            return;
        }
        long j = uItem.dialogId;
        if (j > 0) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
            if (user != null) {
                if (user.bot) {
                    string2 = LocaleController.getString(C2369R.string.Bot);
                } else if (user.contact) {
                    string2 = LocaleController.getString(C2369R.string.FilterContact);
                } else {
                    string2 = LocaleController.getString(C2369R.string.FilterNonContact);
                }
                setData(user, null, string2, 0, z);
                return;
            }
            return;
        }
        TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
        if (chat != null) {
            if (chat.participants_count != 0) {
                if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                    string = LocaleController.formatPluralStringComma("Subscribers", chat.participants_count);
                } else {
                    string = LocaleController.formatPluralStringComma("Members", chat.participants_count);
                }
            } else if (!ChatObject.isPublic(chat)) {
                if (ChatObject.isChannel(chat) && !chat.megagroup) {
                    string = LocaleController.getString(C2369R.string.ChannelPrivate);
                } else {
                    string = LocaleController.getString(C2369R.string.MegaPrivate);
                }
            } else if (ChatObject.isChannel(chat) && !chat.megagroup) {
                string = LocaleController.getString(C2369R.string.ChannelPublic);
            } else {
                string = LocaleController.getString(C2369R.string.MegaPublic);
            }
            setData(chat, null, string, 0, z);
        }
    }

    public void setCloseIcon(View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            ImageView imageView = this.closeView;
            if (imageView != null) {
                removeView(imageView);
                this.closeView = null;
                return;
            }
            return;
        }
        if (this.closeView == null) {
            ImageView imageView2 = new ImageView(getContext());
            this.closeView = imageView2;
            imageView2.setScaleType(ImageView.ScaleType.CENTER);
            ScaleStateListAnimator.apply(this.closeView);
            this.closeView.setImageResource(C2369R.drawable.ic_close_white);
            this.closeView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, this.resourcesProvider), PorterDuff.Mode.SRC_IN));
            this.closeView.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector, this.resourcesProvider), 5));
            ImageView imageView3 = this.closeView;
            boolean z = LocaleController.isRTL;
            addView(imageView3, LayoutHelper.createFrame(30, 30.0f, (z ? 3 : 5) | 16, z ? 14.0f : 0.0f, 0.0f, z ? 0.0f : 14.0f, 0.0f));
        }
        this.closeView.setOnClickListener(onClickListener);
    }
}
