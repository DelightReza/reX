package org.telegram.p023ui.Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessageSuggestionParams;
import org.telegram.messenger.utils.tlutils.AmountUtils$Amount;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public class SuggestionOffer {
    public int height;
    private final Theme.ResourcesProvider resourcesProvider;
    public ArrayList rows = new ArrayList(2);
    private int rowsInfoX;
    private int rowsTitleX;
    public StaticLayout title;
    private int titleX;
    public int width;

    public static class Row {
        public final Text info;
        public final Text title;

        public Row(Text text, Text text2) {
            this.title = text;
            this.info = text2;
        }

        public int getHeight() {
            return (int) this.title.getHeight();
        }
    }

    public SuggestionOffer(int i, View view, Theme.ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
    }

    public void update(MessageObject messageObject) {
        float f;
        int i;
        char c;
        TLRPC.Message message;
        TLRPC.SuggestedPost suggestedPost = (messageObject == null || (message = messageObject.messageOwner) == null) ? null : message.suggested_post;
        if (suggestedPost == null) {
            return;
        }
        MessageSuggestionParams messageSuggestionParamsM1170of = MessageSuggestionParams.m1170of(suggestedPost);
        TextPaint textPaint = (TextPaint) getThemedPaint("paintChatActionText3");
        this.height = AndroidUtilities.m1146dp(14.0f) * 2;
        this.rows.clear();
        AmountUtils$Amount amountUtils$Amount = messageSuggestionParamsM1170of.amount;
        if (amountUtils$Amount != null && !amountUtils$Amount.isZero()) {
            this.rows.add(new Row(new Text(LocaleController.getString(C2369R.string.SuggestionOfferInfoPrice), textPaint), new Text(LocaleController.bold(messageSuggestionParamsM1170of.amount.formatAsDecimalSpaced()), textPaint)));
        }
        if (suggestedPost.schedule_date > 0) {
            this.rows.add(new Row(new Text(LocaleController.getString(C2369R.string.SuggestionOfferInfoTime), textPaint), new Text(LocaleController.bold(LocaleController.formatDateTime(suggestedPost.schedule_date, true)), textPaint)));
        }
        ArrayList arrayList = this.rows;
        int size = arrayList.size();
        float fMax = 0.0f;
        int i2 = 0;
        float fMax2 = 0.0f;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            Row row = (Row) obj;
            fMax2 = Math.max(fMax2, row.title.getWidth());
            fMax = Math.max(fMax, row.info.getWidth());
            int height = this.height + row.getHeight();
            this.height = height;
            this.height = height + AndroidUtilities.m1146dp(7.0f);
        }
        int iM1146dp = (int) (fMax + fMax2 + AndroidUtilities.m1146dp(11.0f));
        int iMax = Math.max(iM1146dp, AndroidUtilities.m1146dp(160.0f));
        String name = DialogObject.getName(messageObject.getFromChatId());
        int editedSuggestionFlags = messageObject.getEditedSuggestionFlags();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (editedSuggestionFlags == 0) {
            if (messageObject.isOutOwner()) {
                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2369R.string.SuggestionOfferInfoTitleYou));
            } else {
                spannableStringBuilder.append((CharSequence) LocaleController.formatString(C2369R.string.SuggestionOfferInfoTitle, name));
            }
            f = 11.0f;
        } else {
            MessageObject messageObject2 = messageObject.replyMessageObject;
            if (messageObject2 != null) {
                DialogObject.getName(messageObject2.getFromChatId());
            }
            StringBuilder sb = new StringBuilder();
            int i3 = editedSuggestionFlags & 4;
            int i4 = editedSuggestionFlags & 2;
            int i5 = editedSuggestionFlags & 8;
            int i6 = editedSuggestionFlags & 1;
            int i7 = (i3 != 0 ? 1 : 0) + (i4 != 0 ? 1 : 0) + (i5 != 0 ? 1 : 0) + (i6 != 0 ? 1 : 0);
            if (i6 != 0) {
                f = 11.0f;
                updateBuildTitleStep(sb, C2369R.string.SuggestionOfferInfoTitleEditedPrice, i7 == 1);
                i = 1;
            } else {
                f = 11.0f;
                i = 0;
            }
            if (i4 != 0) {
                i++;
                c = 0;
                updateBuildTitleStep(sb, C2369R.string.SuggestionOfferInfoTitleEditedTime, i7 == i);
            } else {
                c = 0;
            }
            if (i3 != 0) {
                i++;
                updateBuildTitleStep(sb, C2369R.string.SuggestionOfferInfoTitleEditedText, i7 == i);
            }
            if (i5 != 0) {
                updateBuildTitleStep(sb, C2369R.string.SuggestionOfferInfoTitleEditedMedia, i7 == i + 1);
            }
            if (messageObject.isOutOwner()) {
                int i8 = C2369R.string.SuggestionOfferInfoTitleEditedFromYou;
                Object[] objArr = new Object[1];
                objArr[c] = sb;
                spannableStringBuilder.append((CharSequence) LocaleController.formatString(i8, objArr));
            } else {
                int i9 = C2369R.string.SuggestionOfferInfoTitleEditedFromX;
                Object[] objArr2 = new Object[2];
                objArr2[c] = name;
                objArr2[1] = sb;
                spannableStringBuilder.append((CharSequence) LocaleController.formatString(i9, objArr2));
            }
        }
        this.title = new StaticLayout(AndroidUtilities.replaceTags(spannableStringBuilder), textPaint, iMax, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        int iMax2 = 0;
        for (int i10 = 0; i10 < this.title.getLineCount(); i10++) {
            iMax2 = (int) Math.max(iMax2, this.title.getLineWidth(i10));
        }
        int height2 = this.height + this.title.getHeight();
        this.height = height2;
        this.height = height2 + AndroidUtilities.m1146dp(5.0f);
        int iMax3 = Math.max(iM1146dp, iMax2) + (AndroidUtilities.m1146dp(24.0f) * 2);
        this.width = iMax3;
        this.titleX = (iMax3 - iMax) / 2;
        this.rowsTitleX = (iMax3 - iM1146dp) / 2;
        this.rowsInfoX = (int) (r1 + AndroidUtilities.m1146dp(f) + fMax2);
    }

    private void updateBuildTitleStep(StringBuilder sb, int i, boolean z) {
        if (sb.length() > 0) {
            if (z) {
                sb.append(' ');
                sb.append(LocaleController.getString(C2369R.string.SuggestionOfferInfoTitleEditedAnd));
                sb.append(' ');
            } else {
                sb.append(", ");
            }
        }
        sb.append(LocaleController.getString(i));
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void draw(Canvas canvas, int i, float f, float f2, float f3, float f4, boolean z) {
        int i2 = (i - this.width) / 2;
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(i2, 0.0f, r13 + i2, this.height);
        canvas.save();
        canvas.translate(f / 2.0f, f2);
        Paint themePaint = Theme.getThemePaint("paintChatActionBackground", this.resourcesProvider);
        int alpha = themePaint.getAlpha();
        themePaint.setAlpha((int) (alpha * f4 * f3));
        canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(15.0f), AndroidUtilities.m1146dp(15.0f), themePaint);
        themePaint.setAlpha(alpha);
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null ? resourcesProvider.hasGradientService() : Theme.hasGradientService()) {
            Paint themePaint2 = Theme.getThemePaint("paintChatActionBackgroundDarken", this.resourcesProvider);
            int alpha2 = themePaint2.getAlpha();
            themePaint2.setAlpha((int) (alpha2 * f4 * f3));
            canvas.drawRect(rectF, themePaint2);
            themePaint2.setAlpha(alpha2);
        }
        int iM1146dp = AndroidUtilities.m1146dp(14.0f);
        if (this.title != null) {
            canvas.save();
            canvas.translate(this.titleX + i2, iM1146dp);
            this.title.draw(canvas);
            canvas.restore();
            iM1146dp += this.title.getHeight() + AndroidUtilities.m1146dp(12.0f);
        }
        ArrayList arrayList = this.rows;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            Row row = (Row) obj;
            float f5 = iM1146dp;
            row.title.draw(canvas, this.rowsTitleX + i2, (row.getHeight() / 2.0f) + f5, 0.85f);
            row.info.draw(canvas, this.rowsInfoX + i2, f5 + (row.getHeight() / 2.0f));
            iM1146dp += row.getHeight() + AndroidUtilities.m1146dp(7.0f);
        }
        canvas.restore();
    }

    protected Paint getThemedPaint(String str) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        Paint paint = resourcesProvider != null ? resourcesProvider.getPaint(str) : null;
        return paint != null ? paint : Theme.getThemePaint(str);
    }
}
