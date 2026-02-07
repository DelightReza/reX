package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserObject;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.CombinedDrawable;
import org.telegram.p023ui.Components.DotDividerSpan;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class SessionCell extends FrameLayout {
    private AvatarDrawable avatarDrawable;
    private int currentAccount;
    private int currentType;
    private TextView detailExTextView;
    private TextView detailTextView;
    FlickerLoadingView globalGradient;
    private BackupImageView imageView;
    LinearLayout linearLayout;
    private TextView nameTextView;
    private boolean needDivider;
    private TextView onlineTextView;
    private BackupImageView placeholderImageView;
    private boolean showStub;
    private AnimatedFloat showStubValue;

    /* JADX WARN: Removed duplicated region for block: B:101:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0266  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public SessionCell(android.content.Context r25, int r26) {
        /*
            Method dump skipped, instructions count: 769
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.SessionCell.<init>(android.content.Context, int):void");
    }

    private void setContentAlpha(float f) {
        TextView textView = this.detailExTextView;
        if (textView != null) {
            textView.setAlpha(f);
        }
        TextView textView2 = this.detailTextView;
        if (textView2 != null) {
            textView2.setAlpha(f);
        }
        TextView textView3 = this.nameTextView;
        if (textView3 != null) {
            textView3.setAlpha(f);
        }
        TextView textView4 = this.onlineTextView;
        if (textView4 != null) {
            textView4.setAlpha(f);
        }
        BackupImageView backupImageView = this.imageView;
        if (backupImageView != null) {
            backupImageView.setAlpha(f);
        }
        BackupImageView backupImageView2 = this.placeholderImageView;
        if (backupImageView2 != null) {
            backupImageView2.setAlpha(1.0f - f);
        }
        LinearLayout linearLayout = this.linearLayout;
        if (linearLayout != null) {
            linearLayout.setAlpha(f);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(this.currentType == 0 ? 70.0f : 90.0f) + (this.needDivider ? 1 : 0), TLObject.FLAG_30));
    }

    public void setSession(TLObject tLObject, boolean z) {
        String firstName;
        String strStringForMessageListDate;
        this.needDivider = z;
        if (tLObject instanceof TLRPC.TL_authorization) {
            TLRPC.TL_authorization tL_authorization = (TLRPC.TL_authorization) tLObject;
            this.imageView.setImageDrawable(createDrawable(42, tL_authorization));
            StringBuilder sb = new StringBuilder();
            if (tL_authorization.device_model.length() != 0) {
                sb.append(tL_authorization.device_model);
            }
            if (sb.length() == 0) {
                if (tL_authorization.platform.length() != 0) {
                    sb.append(tL_authorization.platform);
                }
                if (tL_authorization.system_version.length() != 0) {
                    if (tL_authorization.platform.length() != 0) {
                        sb.append(" ");
                    }
                    sb.append(tL_authorization.system_version);
                }
            }
            this.nameTextView.setText(sb);
            if ((tL_authorization.flags & 1) != 0) {
                setTag(Integer.valueOf(Theme.key_windowBackgroundWhiteValueText));
                strStringForMessageListDate = LocaleController.getString(C2369R.string.Online);
            } else {
                setTag(Integer.valueOf(Theme.key_windowBackgroundWhiteGrayText3));
                strStringForMessageListDate = LocaleController.stringForMessageListDate(tL_authorization.date_active);
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            if (tL_authorization.country.length() != 0) {
                spannableStringBuilder.append((CharSequence) tL_authorization.country);
            }
            if (spannableStringBuilder.length() != 0) {
                DotDividerSpan dotDividerSpan = new DotDividerSpan();
                dotDividerSpan.setTopPadding(AndroidUtilities.m1146dp(1.5f));
                spannableStringBuilder.append((CharSequence) " . ").setSpan(dotDividerSpan, spannableStringBuilder.length() - 2, spannableStringBuilder.length() - 1, 0);
            }
            spannableStringBuilder.append((CharSequence) strStringForMessageListDate);
            this.detailExTextView.setText(spannableStringBuilder);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(tL_authorization.app_name);
            sb2.append(" ");
            sb2.append(tL_authorization.app_version);
            this.detailTextView.setText(sb2);
        } else if (tLObject instanceof TLRPC.TL_webAuthorization) {
            TLRPC.TL_webAuthorization tL_webAuthorization = (TLRPC.TL_webAuthorization) tLObject;
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(tL_webAuthorization.bot_id));
            this.nameTextView.setText(tL_webAuthorization.domain);
            if (user != null) {
                this.avatarDrawable.setInfo(this.currentAccount, user);
                firstName = UserObject.getFirstName(user);
                this.imageView.setForUserOrChat(user, this.avatarDrawable);
            } else {
                firstName = "";
            }
            int i = Theme.key_windowBackgroundWhiteGrayText3;
            setTag(Integer.valueOf(i));
            this.onlineTextView.setText(LocaleController.stringForMessageListDate(tL_webAuthorization.date_active));
            this.onlineTextView.setTextColor(Theme.getColor(i));
            StringBuilder sb3 = new StringBuilder();
            if (tL_webAuthorization.f1729ip.length() != 0) {
                sb3.append(tL_webAuthorization.f1729ip);
            }
            if (tL_webAuthorization.region.length() != 0) {
                if (sb3.length() != 0) {
                    sb3.append(" ");
                }
                sb3.append("— ");
                sb3.append(tL_webAuthorization.region);
            }
            this.detailExTextView.setText(sb3);
            StringBuilder sb4 = new StringBuilder();
            if (!TextUtils.isEmpty(firstName)) {
                sb4.append(firstName);
            }
            if (tL_webAuthorization.browser.length() != 0) {
                if (sb4.length() != 0) {
                    sb4.append(", ");
                }
                sb4.append(tL_webAuthorization.browser);
            }
            if (tL_webAuthorization.platform.length() != 0) {
                if (sb4.length() != 0) {
                    sb4.append(", ");
                }
                sb4.append(tL_webAuthorization.platform);
            }
            this.detailTextView.setText(sb4);
        }
        if (this.showStub) {
            this.showStub = false;
            invalidate();
        }
    }

    public static CombinedDrawable createDrawable(int i, String str) {
        TLRPC.TL_authorization tL_authorization = new TLRPC.TL_authorization();
        tL_authorization.device_model = str;
        tL_authorization.platform = str;
        tL_authorization.app_name = str;
        return createDrawable(i, tL_authorization);
    }

    public static CombinedDrawable createDrawable(int i, TLRPC.TL_authorization tL_authorization) {
        int i2;
        int i3;
        int i4;
        String lowerCase = tL_authorization.platform.toLowerCase();
        if (lowerCase.isEmpty()) {
            lowerCase = tL_authorization.system_version.toLowerCase();
        }
        String lowerCase2 = tL_authorization.device_model.toLowerCase();
        if (lowerCase2.contains("safari")) {
            i2 = C2369R.drawable.device_web_safari;
            i3 = Theme.key_avatar_backgroundPink;
            i4 = Theme.key_avatar_background2Pink;
        } else if (lowerCase2.contains("edge")) {
            i2 = C2369R.drawable.device_web_edge;
            i3 = Theme.key_avatar_backgroundPink;
            i4 = Theme.key_avatar_background2Pink;
        } else if (lowerCase2.contains("chrome")) {
            i2 = C2369R.drawable.device_web_chrome;
            i3 = Theme.key_avatar_backgroundPink;
            i4 = Theme.key_avatar_background2Pink;
        } else if (lowerCase2.contains("opera")) {
            i2 = C2369R.drawable.device_web_opera;
            i3 = Theme.key_avatar_backgroundPink;
            i4 = Theme.key_avatar_background2Pink;
        } else if (lowerCase2.contains("firefox")) {
            i2 = C2369R.drawable.device_web_firefox;
            i3 = Theme.key_avatar_backgroundPink;
            i4 = Theme.key_avatar_background2Pink;
        } else if (lowerCase2.contains("vivaldi")) {
            i2 = C2369R.drawable.device_web_other;
            i3 = Theme.key_avatar_backgroundPink;
            i4 = Theme.key_avatar_background2Pink;
        } else if (lowerCase.contains("ios")) {
            i2 = lowerCase2.contains("ipad") ? C2369R.drawable.device_tablet_ios : C2369R.drawable.device_phone_ios;
            i3 = Theme.key_avatar_backgroundBlue;
            i4 = Theme.key_avatar_background2Blue;
        } else if (lowerCase.contains("windows")) {
            i2 = C2369R.drawable.device_desktop_win;
            i3 = Theme.key_avatar_backgroundCyan;
            i4 = Theme.key_avatar_background2Cyan;
        } else if (lowerCase.contains("macos")) {
            i2 = C2369R.drawable.device_desktop_osx;
            i3 = Theme.key_avatar_backgroundCyan;
            i4 = Theme.key_avatar_background2Cyan;
        } else if (lowerCase.contains("android")) {
            i2 = lowerCase2.contains("tab") ? C2369R.drawable.device_tablet_android : C2369R.drawable.device_phone_android;
            i3 = Theme.key_avatar_backgroundGreen;
            i4 = Theme.key_avatar_background2Green;
        } else {
            if (lowerCase.contains("fragment")) {
                i2 = C2369R.drawable.fragment;
            } else if (lowerCase.equalsIgnoreCase("search")) {
                i2 = C2369R.drawable.msg_search;
                i3 = Theme.key_avatar_backgroundBlue;
                i4 = Theme.key_avatar_background2Blue;
            } else if (lowerCase.contains("anonymous")) {
                i2 = C2369R.drawable.large_hidden;
                i3 = Theme.key_avatar_backgroundBlue;
                i4 = Theme.key_avatar_background2Blue;
            } else if (lowerCase.contains("premiumbot")) {
                i2 = C2369R.drawable.filled_star_plus;
                i3 = Theme.key_color_yellow;
                i4 = Theme.key_color_orange;
            } else if (lowerCase.contains("ads")) {
                i2 = C2369R.drawable.msg_channel;
                i3 = Theme.key_avatar_backgroundPink;
                i4 = Theme.key_avatar_background2Pink;
            } else if (lowerCase.contains("api")) {
                i2 = C2369R.drawable.filled_paid_broadcast;
                i3 = Theme.key_avatar_backgroundGreen;
                i4 = Theme.key_avatar_background2Green;
            } else if (lowerCase.equals("?")) {
                i2 = C2369R.drawable.msg_emoji_question;
            } else if (tL_authorization.app_name.toLowerCase().contains("desktop")) {
                i2 = C2369R.drawable.device_desktop_other;
                i3 = Theme.key_avatar_backgroundCyan;
                i4 = Theme.key_avatar_background2Cyan;
            } else {
                i2 = C2369R.drawable.device_web_other;
                i3 = Theme.key_avatar_backgroundPink;
                i4 = Theme.key_avatar_background2Pink;
            }
            i3 = -1;
            i4 = -1;
        }
        boolean zContains = tL_authorization.app_name.toLowerCase().contains("exteragram");
        Context context = ApplicationLoader.applicationContext;
        if (zContains) {
            i2 = C2369R.drawable.ic_foreground_extera;
        }
        Drawable drawableMutate = ContextCompat.getDrawable(context, i2).mutate();
        if (!zContains) {
            drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_avatar_text), PorterDuff.Mode.SRC_IN));
        }
        float f = i;
        int iM1146dp = AndroidUtilities.m1146dp(f);
        int color = -16777216;
        int color2 = zContains ? ContextCompat.getColor(ApplicationLoader.applicationContext, C2369R.color.ic_background_extera) : i3 == -1 ? -16777216 : Theme.getColor(i3);
        if (zContains) {
            color = ContextCompat.getColor(ApplicationLoader.applicationContext, C2369R.color.ic_background_extera);
        } else if (i4 != -1) {
            color = Theme.getColor(i4);
        }
        CombinedDrawable combinedDrawable = new CombinedDrawable(new CircleGradientDrawable(iM1146dp, color2, color), drawableMutate);
        if (lowerCase.contains("fragment")) {
            combinedDrawable.setIconSize((int) ((drawableMutate.getIntrinsicWidth() / 44.0f) * f), (int) ((drawableMutate.getIntrinsicHeight() / 44.0f) * f));
            return combinedDrawable;
        }
        combinedDrawable.setIconSize(AndroidUtilities.m1146dp(42.0f), AndroidUtilities.m1146dp(42.0f));
        return combinedDrawable;
    }

    public static class CircleGradientDrawable extends Drawable {
        private int colorBottom;
        private int colorTop;
        private Paint paint;
        private int size;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public CircleGradientDrawable(int i, int i2, int i3) {
            this.size = i;
            this.colorTop = i2;
            this.colorBottom = i3;
            Paint paint = new Paint(1);
            this.paint = paint;
            paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, i, new int[]{i2, i3}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP));
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.drawCircle(getBounds().centerX(), getBounds().centerY(), Math.min(getBounds().width(), getBounds().height()) / 2.0f, this.paint);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.paint.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.size;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.size;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float f = this.showStubValue.set(this.showStub ? 1.0f : 0.0f);
        setContentAlpha(1.0f - f);
        if (f > 0.0f && this.globalGradient != null) {
            if (f < 1.0f) {
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                canvas.saveLayerAlpha(rectF, (int) (f * 255.0f), 31);
            }
            this.globalGradient.updateColors();
            this.globalGradient.updateGradient();
            if (getParent() != null) {
                View view = (View) getParent();
                this.globalGradient.setParentSize(view.getMeasuredWidth(), view.getMeasuredHeight(), -getX());
            }
            float top = this.linearLayout.getTop() + this.nameTextView.getTop() + AndroidUtilities.m1146dp(12.0f);
            float x = this.linearLayout.getX();
            RectF rectF2 = AndroidUtilities.rectTmp;
            rectF2.set(x, top - AndroidUtilities.m1146dp(4.0f), (getMeasuredWidth() * 0.2f) + x, top + AndroidUtilities.m1146dp(4.0f));
            canvas.drawRoundRect(rectF2, AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), this.globalGradient.getPaint());
            float top2 = (this.linearLayout.getTop() + this.detailTextView.getTop()) - AndroidUtilities.m1146dp(1.0f);
            float x2 = this.linearLayout.getX();
            rectF2.set(x2, top2 - AndroidUtilities.m1146dp(4.0f), (getMeasuredWidth() * 0.4f) + x2, top2 + AndroidUtilities.m1146dp(4.0f));
            canvas.drawRoundRect(rectF2, AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), this.globalGradient.getPaint());
            float top3 = (this.linearLayout.getTop() + this.detailExTextView.getTop()) - AndroidUtilities.m1146dp(1.0f);
            float x3 = this.linearLayout.getX();
            rectF2.set(x3, top3 - AndroidUtilities.m1146dp(4.0f), (getMeasuredWidth() * 0.3f) + x3, top3 + AndroidUtilities.m1146dp(4.0f));
            canvas.drawRoundRect(rectF2, AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), this.globalGradient.getPaint());
            invalidate();
            if (f < 1.0f) {
                canvas.restore();
            }
        }
        if (this.needDivider) {
            int i = this.currentType == 1 ? 49 : 72;
            canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(i), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(i) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
        }
    }

    public void showStub(FlickerLoadingView flickerLoadingView) {
        this.globalGradient = flickerLoadingView;
        this.showStub = true;
        Drawable drawableMutate = ContextCompat.getDrawable(ApplicationLoader.applicationContext, AndroidUtilities.isTablet() ? C2369R.drawable.device_tablet_android : C2369R.drawable.device_phone_android).mutate();
        drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_avatar_text), PorterDuff.Mode.SRC_IN));
        CombinedDrawable combinedDrawable = new CombinedDrawable(Theme.createCircleDrawable(AndroidUtilities.m1146dp(42.0f), Theme.getColor(Theme.key_avatar_backgroundGreen)), drawableMutate);
        BackupImageView backupImageView = this.placeholderImageView;
        if (backupImageView != null) {
            backupImageView.setImageDrawable(combinedDrawable);
        } else {
            this.imageView.setImageDrawable(combinedDrawable);
        }
        invalidate();
    }
}
