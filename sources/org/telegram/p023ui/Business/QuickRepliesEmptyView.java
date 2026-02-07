package org.telegram.p023ui.Business;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.RLottieImageView;

/* loaded from: classes5.dex */
public class QuickRepliesEmptyView extends LinearLayout {
    private TextView descriptionView;
    private TextView descriptionView2;
    public RLottieImageView imageView;
    private final Theme.ResourcesProvider resourcesProvider;
    private TextView titleView;

    private class DotTextView extends TextView {
        public DotTextView(Context context) {
            super(context);
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (getPaddingLeft() > 0) {
                canvas.drawCircle((getPaddingLeft() - AndroidUtilities.m1146dp(2.5f)) / 2.0f, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(2.5f), getPaint());
            }
            super.dispatchDraw(canvas);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x01cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public QuickRepliesEmptyView(android.content.Context r8, int r9, long r10, long r12, java.lang.String r14, org.telegram.ui.ActionBar.Theme.ResourcesProvider r15) {
        /*
            Method dump skipped, instructions count: 481
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Business.QuickRepliesEmptyView.<init>(android.content.Context, int, long, long, java.lang.String, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    private void updateColors() {
        TextView textView = this.titleView;
        int i = Theme.key_chat_serviceText;
        textView.setTextColor(getThemedColor(i));
        this.descriptionView.setTextColor(getThemedColor(i));
        TextView textView2 = this.descriptionView2;
        if (textView2 != null) {
            textView2.setTextColor(getThemedColor(i));
        }
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }
}
