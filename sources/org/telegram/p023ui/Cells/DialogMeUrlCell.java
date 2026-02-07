package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.text.StaticLayout;
import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class DialogMeUrlCell extends BaseCell {
    private AvatarDrawable avatarDrawable;
    private ImageReceiver avatarImage;
    private int avatarTop;
    private int currentAccount;
    private boolean drawNameLock;
    private boolean drawVerified;
    private boolean isSelected;
    private StaticLayout messageLayout;
    private int messageLeft;
    private int messageTop;
    private StaticLayout nameLayout;
    private int nameLeft;
    private int nameLockLeft;
    private int nameLockTop;
    private int nameMuteLeft;
    private TLRPC.RecentMeUrl recentMeUrl;
    public boolean useSeparator;

    @Override // org.telegram.p023ui.Cells.BaseCell, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public DialogMeUrlCell(Context context) {
        super(context);
        this.avatarImage = new ImageReceiver(this);
        this.avatarDrawable = new AvatarDrawable();
        this.messageTop = AndroidUtilities.m1146dp(40.0f);
        this.avatarTop = AndroidUtilities.m1146dp(10.0f);
        this.currentAccount = UserConfig.selectedAccount;
        Theme.createDialogsResources(context);
        this.avatarImage.setRoundRadius(ExteraConfig.getAvatarCorners(52.0f));
    }

    public void setRecentMeUrl(TLRPC.RecentMeUrl recentMeUrl) {
        this.recentMeUrl = recentMeUrl;
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.avatarImage.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.avatarImage.onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.m1146dp(72.0f) + (this.useSeparator ? 1 : 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            buildLayout();
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(22:0|2|(3:4|(1:6)(1:7)|8)(2:9|(4:11|(1:13)(1:14)|(3:16|(2:18|(1:20)(1:21))|22)|23)(2:24|(3:26|(1:28)(1:29)|30)(2:31|(6:33|(1:35)(1:36)|37|(1:39)(1:40)|41|(1:43)(1:44))(2:45|(3:47|(1:49)(1:50)|51)(1:52)))))|53|(1:55)|56|(1:58)(1:60)|59|61|(1:63)|64|(2:66|(1:68))|69|(3:143|70|71)|(2:145|72)|79|(3:81|(1:83)(1:84)|85)(3:86|(1:88)(1:89)|90)|91|141|92|96|(4:98|(4:102|(1:104)|105|(2:107|(1:109)))|110|(1:149)(2:116|(2:118|151)(1:150)))(4:119|(4:123|(2:125|(1:127))|128|(1:130))|131|(1:154)(2:137|(2:139|140)(1:155)))) */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0338, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0339, code lost:
    
        org.telegram.messenger.FileLog.m1160e(r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:119:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0340  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void buildLayout() {
        /*
            Method dump skipped, instructions count: 1057
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DialogMeUrlCell.buildLayout():void");
    }

    public void setDialogSelected(boolean z) {
        if (this.isSelected != z) {
            invalidate();
        }
        this.isSelected = z;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        if (this.isSelected) {
            canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), Theme.dialogs_tabletSeletedPaint);
            canvas2 = canvas;
        } else {
            canvas2 = canvas;
        }
        if (this.drawNameLock) {
            BaseCell.setDrawableBounds(Theme.dialogs_lockDrawable, this.nameLockLeft, this.nameLockTop);
            Theme.dialogs_lockDrawable.draw(canvas2);
        }
        if (this.nameLayout != null) {
            canvas2.save();
            canvas2.translate(this.nameLeft, AndroidUtilities.m1146dp(13.0f));
            this.nameLayout.draw(canvas2);
            canvas2.restore();
        }
        if (this.messageLayout != null) {
            canvas2.save();
            canvas2.translate(this.messageLeft, this.messageTop);
            try {
                this.messageLayout.draw(canvas2);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
            canvas2.restore();
        }
        if (this.drawVerified) {
            BaseCell.setDrawableBounds(Theme.dialogs_verifiedDrawable, this.nameMuteLeft, AndroidUtilities.m1146dp(16.5f));
            BaseCell.setDrawableBounds(Theme.dialogs_verifiedCheckDrawable, this.nameMuteLeft, AndroidUtilities.m1146dp(16.5f));
            Theme.dialogs_verifiedDrawable.draw(canvas2);
            Theme.dialogs_verifiedCheckDrawable.draw(canvas2);
        }
        if (this.useSeparator) {
            if (LocaleController.isRTL) {
                canvas2.drawLine(0.0f, getMeasuredHeight() - 1, getMeasuredWidth() - AndroidUtilities.m1146dp(AndroidUtilities.leftBaseline), getMeasuredHeight() - 1, Theme.dividerPaint);
            } else {
                canvas2.drawLine(AndroidUtilities.m1146dp(AndroidUtilities.leftBaseline), getMeasuredHeight() - 1, getMeasuredWidth(), getMeasuredHeight() - 1, Theme.dividerPaint);
            }
        }
        this.avatarImage.draw(canvas2);
    }
}
