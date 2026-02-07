package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.Set;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BubbleCounterPath;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.FilterCreateActivity;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class DrawerActionCell extends FrameLayout {
    private boolean currentError;
    private int currentId;
    private BackupImageView imageView;
    private RectF rect;
    private TextView textView;

    public DrawerActionCell(Context context) {
        super(context);
        this.rect = new RectF();
        BackupImageView backupImageView = new BackupImageView(context);
        this.imageView = backupImageView;
        backupImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_menuItemIcon), PorterDuff.Mode.SRC_IN));
        this.imageView.getImageReceiver().setFileLoadingPriority(3);
        TextView textView = new TextView(context);
        this.textView = textView;
        textView.setTextColor(Theme.getColor(Theme.key_chats_menuItemText));
        this.textView.setTextSize(1, 15.0f);
        this.textView.setTypeface(AndroidUtilities.bold());
        this.textView.setGravity(19);
        addView(this.imageView, LayoutHelper.createFrame(24, 24.0f, 51, 19.0f, 12.0f, 0.0f, 0.0f));
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, 51, 72.0f, 0.0f, 16.0f, 0.0f));
        setWillNotDraw(false);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        boolean z;
        int archiveUnreadCount;
        super.onDraw(canvas);
        boolean z2 = this.currentError;
        if (z2 || this.currentId != 8) {
            z = z2;
        } else {
            Set<String> set = MessagesController.getInstance(UserConfig.selectedAccount).pendingSuggestions;
            z = set.contains("VALIDATE_PHONE_NUMBER") || set.contains("VALIDATE_PASSWORD");
        }
        if (z) {
            int iM1146dp = AndroidUtilities.m1146dp(12.5f);
            this.rect.set(((getMeasuredWidth() - AndroidUtilities.m1146dp(9.0f)) - AndroidUtilities.m1146dp(25.0f)) - AndroidUtilities.m1146dp(5.5f), iM1146dp, r9 + r8 + AndroidUtilities.m1146dp(14.0f), iM1146dp + AndroidUtilities.m1146dp(23.0f));
            Theme.chat_docBackPaint.setColor(Theme.getColor(z2 ? Theme.key_text_RedBold : Theme.key_chats_archiveBackground));
            RectF rectF = this.rect;
            float f = AndroidUtilities.density;
            canvas.drawRoundRect(rectF, f * 11.5f, f * 11.5f, Theme.chat_docBackPaint);
            float intrinsicWidth = Theme.dialogs_errorDrawable.getIntrinsicWidth() / 2;
            float intrinsicHeight = Theme.dialogs_errorDrawable.getIntrinsicHeight() / 2;
            Theme.dialogs_errorDrawable.setBounds((int) (this.rect.centerX() - intrinsicWidth), (int) (this.rect.centerY() - intrinsicHeight), (int) (this.rect.centerX() + intrinsicWidth), (int) (this.rect.centerY() + intrinsicHeight));
            Theme.dialogs_errorDrawable.draw(canvas);
        }
        if (this.currentId != 14 || (archiveUnreadCount = MessagesStorage.getInstance(UserConfig.selectedAccount).getArchiveUnreadCount()) <= 0) {
            return;
        }
        String strValueOf = String.valueOf(archiveUnreadCount);
        int iM1146dp2 = AndroidUtilities.m1146dp(12.5f);
        int iCeil = (int) Math.ceil(Theme.dialogs_countTextPaint.measureText(strValueOf));
        this.rect.set(((getMeasuredWidth() - Math.max(AndroidUtilities.m1146dp(10.0f), iCeil)) - AndroidUtilities.m1146dp(25.0f)) - AndroidUtilities.m1146dp(5.5f), iM1146dp2, r9 + r8 + AndroidUtilities.m1146dp(14.0f), AndroidUtilities.m1146dp(23.0f) + iM1146dp2);
        RectF rectF2 = new RectF();
        Path path = new Path();
        if (!rectF2.equals(this.rect)) {
            rectF2.set(this.rect);
            BubbleCounterPath.addBubbleRect(path, rectF2, AndroidUtilities.m1146dp(11.5f));
        }
        canvas.drawPath(path, Theme.dialogs_countGrayPaint);
        RectF rectF3 = this.rect;
        canvas.drawText(strValueOf, rectF3.left + ((rectF3.width() - iCeil) / 2.0f), iM1146dp2 + AndroidUtilities.m1146dp(16.0f), Theme.dialogs_countTextPaint);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(48.0f), TLObject.FLAG_30));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.textView.setTextColor(Theme.getColor(Theme.key_chats_menuItemText));
    }

    public void setError(boolean z) {
        this.currentError = z;
        invalidate();
    }

    public void setTextAndIcon(int i, CharSequence charSequence, int i2) {
        this.currentId = i;
        try {
            this.textView.setText(charSequence);
            this.imageView.setImageResource(i2);
        } catch (Throwable th) {
            FileLog.m1160e(th);
        }
    }

    public void updateTextAndIcon(String str, int i) {
        try {
            this.textView.setText(str);
            this.imageView.setImageResource(i, Theme.getColor(Theme.key_chats_menuItemIcon));
        } catch (Throwable th) {
            FileLog.m1160e(th);
        }
    }

    public BackupImageView getImageView() {
        return this.imageView;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Button");
        accessibilityNodeInfo.addAction(16);
        accessibilityNodeInfo.addAction(32);
        accessibilityNodeInfo.setText(this.textView.getText());
        accessibilityNodeInfo.setClassName(TextView.class.getName());
    }

    public void setBot(TLRPC.TL_attachMenuBot tL_attachMenuBot) {
        this.currentId = (int) tL_attachMenuBot.bot_id;
        try {
            if (tL_attachMenuBot.side_menu_disclaimer_needed) {
                this.textView.setText(applyNewSpan(tL_attachMenuBot.short_name));
            } else {
                this.textView.setText(tL_attachMenuBot.short_name);
            }
            TLRPC.TL_attachMenuBotIcon sideAttachMenuBotIcon = MediaDataController.getSideAttachMenuBotIcon(tL_attachMenuBot);
            if (this.currentId == 1985737506) {
                this.imageView.setImageResource(C2369R.drawable.menu_wallet);
                return;
            }
            if (sideAttachMenuBotIcon != null) {
                TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(sideAttachMenuBotIcon.icon.thumbs, 72);
                Drawable svgThumb = DocumentObject.getSvgThumb(sideAttachMenuBotIcon.icon.thumbs, Theme.key_emptyListPlaceholder, 0.2f);
                BackupImageView backupImageView = this.imageView;
                ImageLocation forDocument = ImageLocation.getForDocument(sideAttachMenuBotIcon.icon);
                ImageLocation forDocument2 = ImageLocation.getForDocument(closestPhotoSizeWithSize, sideAttachMenuBotIcon.icon);
                if (svgThumb == null) {
                    svgThumb = getContext().getResources().getDrawable(C2369R.drawable.msg_bot).mutate();
                }
                backupImageView.setImage(forDocument, "24_24", forDocument2, "24_24", svgThumb, tL_attachMenuBot);
                return;
            }
            this.imageView.setImageResource(C2369R.drawable.msg_bot);
        } catch (Throwable th) {
            FileLog.m1160e(th);
        }
    }

    public static CharSequence applyNewSpan(String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
        spannableStringBuilder.append((CharSequence) "  d");
        FilterCreateActivity.NewSpan newSpan = new FilterCreateActivity.NewSpan(10.0f);
        newSpan.setColor(Theme.getColor(Theme.key_premiumGradient1));
        spannableStringBuilder.setSpan(newSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 0);
        return spannableStringBuilder;
    }
}
