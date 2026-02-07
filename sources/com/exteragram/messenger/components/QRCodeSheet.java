package com.exteragram.messenger.components;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.RectF;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.utils.system.SystemUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import org.mvel2.DataTypes;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LinkifyPort;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.DialogCell;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.StickerImageView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class QRCodeSheet extends BottomSheet {
    private final int TEXT_TYPE_AUTH_TOKEN;
    private final int TEXT_TYPE_LINK;
    private final int TEXT_TYPE_PHONE;
    private final int TEXT_TYPE_TEXT;
    private final int TEXT_TYPE_WIFI;
    private final BaseFragment fragment;
    private String password;
    private String ssid;
    private String wifiAuthType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [android.view.View, android.view.ViewGroup, android.widget.FrameLayout] */
    /* JADX WARN: Type inference failed for: r12v3, types: [android.view.View, android.view.ViewGroup, android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.view.View, android.widget.ScrollView] */
    /* JADX WARN: Type inference failed for: r30v0, types: [com.exteragram.messenger.components.QRCodeSheet, org.telegram.ui.ActionBar.BottomSheet] */
    /* JADX WARN: Type inference failed for: r6v13, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v6, types: [android.text.Spanned] */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.lang.String] */
    public QRCodeSheet(final BaseFragment baseFragment, String str) {
        final int i;
        SpannableStringBuilder spannableStringBuilderAppend;
        CharSequence textWithIcon;
        String str2;
        super(baseFragment.getParentActivity(), false, baseFragment.getResourceProvider());
        final String strEnsureUrlHasHttps = str;
        this.TEXT_TYPE_LINK = 0;
        this.TEXT_TYPE_TEXT = 1;
        this.TEXT_TYPE_AUTH_TOKEN = 2;
        this.TEXT_TYPE_PHONE = 3;
        this.TEXT_TYPE_WIFI = 4;
        this.wifiAuthType = "WPA";
        this.fragment = baseFragment;
        if (strEnsureUrlHasHttps.startsWith("tg://login?token=")) {
            ?? string = LocaleController.getString(C2369R.string.Cancel);
            textWithIcon = LocaleController.getString(C2369R.string.Allow);
            spannableStringBuilderAppend = string;
            i = 2;
        } else if (LinkifyPort.WEB_URL.matcher(strEnsureUrlHasHttps).matches() || strEnsureUrlHasHttps.startsWith("tel:")) {
            i = strEnsureUrlHasHttps.startsWith("tel:") ? 3 : 0;
            spannableStringBuilderAppend = new SpannableStringBuilder(LocaleController.getString(C2369R.string.Open)).append((CharSequence) ".");
            int length = spannableStringBuilderAppend.length();
            spannableStringBuilderAppend.setSpan(new ColoredImageSpan(ContextCompat.getDrawable(baseFragment.getParentActivity(), C2369R.drawable.msg_mini_topicarrow)), length - 1, length, 0);
            textWithIcon = getTextWithIcon("share");
        } else if (strEnsureUrlHasHttps.startsWith("WIFI:")) {
            parseWifiInfo(strEnsureUrlHasHttps);
            ?? string2 = LocaleController.getString(C2369R.string.WifiConnect);
            textWithIcon = getTextWithIcon("share");
            spannableStringBuilderAppend = string2;
            i = 4;
        } else {
            ?? textWithIcon2 = getTextWithIcon("copy");
            textWithIcon = getTextWithIcon("share");
            spannableStringBuilderAppend = textWithIcon2;
            i = 1;
        }
        final Activity parentActivity = baseFragment.getParentActivity();
        fixNavigationBar();
        ?? frameLayout = new FrameLayout(parentActivity);
        ?? linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        frameLayout.addView(linearLayout);
        linearLayout.addView(new View(baseFragment.getParentActivity()) { // from class: com.exteragram.messenger.components.QRCodeSheet.1
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                int measuredWidth = getMeasuredWidth();
                RectF rectF = new RectF();
                rectF.set((getMeasuredWidth() - measuredWidth) / 2.0f, 0.0f, (getMeasuredWidth() + measuredWidth) / 2.0f, AndroidUtilities.m1146dp(4.0f));
                Theme.dialogs_onlineCirclePaint.setColor(QRCodeSheet.this.getThemedColor(Theme.key_sheet_scrollUp));
                canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(2.0f), AndroidUtilities.m1146dp(2.0f), Theme.dialogs_onlineCirclePaint);
            }
        }, LayoutHelper.createLinear(36, 4, 1, 18, 2, 18, 0));
        if (i == 2) {
            StickerImageView stickerImageView = new StickerImageView(parentActivity, this.currentAccount);
            stickerImageView.setStickerPackName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME);
            stickerImageView.setStickerNum(6);
            stickerImageView.getImageReceiver().setAutoRepeat(1);
            stickerImageView.getImageReceiver().setAutoRepeatCount(1);
            linearLayout.addView(stickerImageView, LayoutHelper.createLinear(Opcodes.D2F, Opcodes.D2F, 1, 0, 20, 0, 10));
        } else {
            ImageView imageView = new ImageView(parentActivity);
            ScaleStateListAnimator.apply(imageView, 0.03f, 1.2f);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.exteragram.messenger.components.QRCodeSheet.2
                @Override // android.view.ViewOutlineProvider
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight(), AndroidUtilities.m1146dp(12.0f));
                }
            });
            imageView.setClipToOutline(true);
            final Bitmap bitmapCreateQR = createQR(strEnsureUrlHasHttps);
            imageView.setImageBitmap(bitmapCreateQR);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IOException {
                    this.f$0.lambda$new$0(bitmapCreateQR, parentActivity, view);
                }
            });
            linearLayout.addView(imageView, LayoutHelper.createLinear(DataTypes.EMPTY, DataTypes.EMPTY, 1, 18, 20, 18, 10));
        }
        TextView textView = new TextView(parentActivity);
        ScaleStateListAnimator.apply(textView, 0.02f, 1.5f);
        textView.setGravity(1);
        textView.setTextSize(1, 14.0f);
        textView.setPadding(AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(4.0f));
        int i2 = Theme.key_windowBackgroundWhiteGrayText;
        textView.setTextColor(getThemedColor(i2));
        if (i == 4 && !TextUtils.isEmpty(this.ssid)) {
            String str3 = this.ssid;
            if (TextUtils.isEmpty(this.password)) {
                str2 = "";
            } else {
                str2 = ", Password: " + this.password;
            }
            textView.setText(MessageFormat.format("SSID: {0}{1}", str3, str2));
        } else {
            textView.setText(i == 2 ? LocaleController.getString(C2369R.string.AreYouSureToLogin) : strEnsureUrlHasHttps);
        }
        strEnsureUrlHasHttps = i == 0 ? LocaleUtils.ensureUrlHasHttps(strEnsureUrlHasHttps) : strEnsureUrlHasHttps;
        if (i != 2) {
            textView.setBackground(Theme.createSelectorDrawable(Theme.multAlpha(getThemedColor(i2), Theme.isCurrentThemeDark() ? 0.2f : 0.15f), 7, AndroidUtilities.m1146dp(8.0f)));
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$1(strEnsureUrlHasHttps, view);
                }
            });
        }
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 1, 21, 2, 21, 8));
        LinearLayout linearLayout2 = new LinearLayout(parentActivity);
        linearLayout2.setOrientation(0);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, 48, 16.0f, 15.0f, 16.0f, 4.0f));
        TextView textView2 = new TextView(parentActivity);
        ScaleStateListAnimator.apply(textView2, 0.02f, 1.5f);
        textView2.setGravity(17);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setText(spannableStringBuilderAppend);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$2(i, baseFragment, strEnsureUrlHasHttps, view);
            }
        });
        if (i == 2) {
            textView2.setTextColor(getThemedColor(Theme.key_featuredStickers_addButton));
            int iM1146dp = AndroidUtilities.m1146dp(8.0f);
            int i3 = Theme.key_windowBackgroundWhiteValueText;
            textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(iM1146dp, Theme.multAlpha(getThemedColor(i3), Theme.isCurrentThemeDark() ? 0.3f : 0.15f), ColorUtils.setAlphaComponent(Theme.multAlpha(getThemedColor(i3), Theme.isCurrentThemeDark() ? 0.3f : 0.15f), Opcodes.ISHL)));
        } else {
            textView2.setTextColor(getThemedColor(Theme.key_featuredStickers_buttonText));
            textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), getThemedColor(Theme.key_featuredStickers_addButton), ColorUtils.setAlphaComponent(getThemedColor(Theme.key_windowBackgroundWhite), Opcodes.ISHL)));
        }
        linearLayout2.addView(textView2, LayoutHelper.createLinear(0, -1, 1.0f));
        linearLayout2.addView(new View(parentActivity), LayoutHelper.createLinear(0, -1, 0.06f));
        TextView textView3 = new TextView(parentActivity);
        ScaleStateListAnimator.apply(textView3, 0.02f, 1.5f);
        textView3.setGravity(17);
        textView3.setTextSize(1, 14.0f);
        textView3.setTypeface(AndroidUtilities.bold());
        textView3.setText(textWithIcon);
        textView3.setTextColor(getThemedColor(Theme.key_featuredStickers_buttonText));
        textView3.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), getThemedColor(Theme.key_featuredStickers_addButton), ColorUtils.setAlphaComponent(getThemedColor(Theme.key_windowBackgroundWhite), Opcodes.ISHL)));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$6(i, strEnsureUrlHasHttps, baseFragment, view);
            }
        });
        linearLayout2.addView(textView3, LayoutHelper.createLinear(0, -1, 1.0f));
        ?? scrollView = new ScrollView(parentActivity);
        scrollView.addView(frameLayout);
        setCustomView(scrollView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Bitmap bitmap, Activity activity, View view) throws IOException {
        if (bitmap != null) {
            copyQR(bitmap, activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(String str, View view) {
        if (AndroidUtilities.addToClipboard(str)) {
            showCopyBulletin(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$new$2(int r1, org.telegram.p023ui.ActionBar.BaseFragment r2, java.lang.String r3, android.view.View r4) {
        /*
            r0 = this;
            r4 = 2
            if (r1 != r4) goto L7
            r0.lambda$new$0()
            return
        L7:
            if (r1 == 0) goto L29
            r4 = 1
            if (r1 == r4) goto L1e
            r4 = 3
            if (r1 == r4) goto L29
            r2 = 4
            if (r1 == r2) goto L13
            goto L34
        L13:
            com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda7 r1 = new com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda7
            r1.<init>()
            r2 = 750(0x2ee, double:3.705E-321)
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r1, r2)
            goto L34
        L1e:
            boolean r1 = org.telegram.messenger.AndroidUtilities.addToClipboard(r3)
            if (r1 == 0) goto L34
            r1 = 0
            r0.showCopyBulletin(r1)
            goto L34
        L29:
            android.app.Activity r1 = r2.getParentActivity()
            android.net.Uri r2 = android.net.Uri.parse(r3)
            org.telegram.messenger.browser.Browser.openUrl(r1, r2)
        L34:
            r0.lambda$new$0()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.components.QRCodeSheet.lambda$new$2(int, org.telegram.ui.ActionBar.BaseFragment, java.lang.String, android.view.View):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(int i, final String str, final BaseFragment baseFragment, View view) {
        if (i == 2) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$5(str, baseFragment);
                }
            }, 750L);
        } else {
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", str);
                baseFragment.startActivityForResult(Intent.createChooser(intent, LocaleController.getString(C2369R.string.QrCode)), 500);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        lambda$new$0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(String str, final BaseFragment baseFragment) {
        try {
            byte[] bArrDecode = Base64.decode(str.substring(17).replaceAll("/", "_").replaceAll("\\+", "-"), 8);
            TLRPC.TL_auth_acceptLoginToken tL_auth_acceptLoginToken = new TLRPC.TL_auth_acceptLoginToken();
            tL_auth_acceptLoginToken.token = bArrDecode;
            ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tL_auth_acceptLoginToken, new RequestDelegate() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda10
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$new$3(tLObject, tL_error);
                }
            });
        } catch (Exception e) {
            FileLog.m1159e("Failed to pass qr code auth", e);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.showSimpleAlert(baseFragment, LocaleController.getString(C2369R.string.AuthAnotherClient), LocaleController.getString(C2369R.string.ErrorOccurred));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(TLObject tLObject, TLRPC.TL_error tL_error) {
        lambda$new$0();
    }

    private Spanned getTextWithIcon(String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) "..").setSpan(new ColoredImageSpan(ContextCompat.getDrawable(this.fragment.getParentActivity(), str.equals("copy") ? C2369R.drawable.msg_copy_filled : C2369R.drawable.msg_share_filled)), 0, 1, 0);
        spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.m1146dp(4.0f)), 1, 2, 0);
        spannableStringBuilder.append((CharSequence) LocaleController.getString(str.equals("copy") ? C2369R.string.LinkActionCopy : C2369R.string.LinkActionShare));
        return spannableStringBuilder;
    }

    private Bitmap createQR(String str) {
        try {
            HashMap map = new HashMap();
            map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            map.put(EncodeHintType.MARGIN, 0);
            return new QRCodeWriter().encode(str, 768, 768, map, null, 1.0f, -1, -16777216, false);
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    private void copyQR(Bitmap bitmap, Activity activity) throws IOException {
        try {
            File file = new File(activity.getExternalFilesDir(null), "qr_code.jpg");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);
            SystemUtils.addFileToClipboard(file, new Runnable() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$copyQR$7();
                }
            });
        } catch (IOException e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$copyQR$7() {
        BulletinFactory.m1266of(getContainer(), null).createCopyBulletin(LocaleController.getString(C2369R.string.PhotoCopied), this.resourcesProvider).show();
    }

    private void parseWifiInfo(String str) {
        for (String str2 : str.substring(str.indexOf(":") + 1).split("(?<!\\\\);")) {
            if (str2.startsWith("S:")) {
                this.ssid = unescapeWifiString(str2.substring(2));
            } else if (str2.startsWith("P:")) {
                this.password = unescapeWifiString(str2.substring(2));
            } else if (str2.startsWith("T:")) {
                this.wifiAuthType = str2.substring(2);
            }
        }
    }

    private String unescapeWifiString(String str) {
        return str.replace("\\\\", "\\").replace("\\;", ";").replace("\\:", ":").replace("\\,", ",").replace("\\\"", "\"");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectToWifi() {
        if (TextUtils.isEmpty(this.ssid)) {
            showErrorBulletin(LocaleController.getString(C2369R.string.WifiFailed));
            return;
        }
        WifiManager wifiManager = (WifiManager) ApplicationLoader.applicationContext.getSystemService("wifi");
        if (wifiManager == null) {
            showErrorBulletin(LocaleController.getString(C2369R.string.WifiFailed));
            return;
        }
        if (!wifiManager.isWifiEnabled()) {
            if (Build.VERSION.SDK_INT >= 29) {
                showErrorBulletin(LocaleController.getString(C2369R.string.WifiDisabled));
                this.fragment.startActivityForResult(new Intent("android.settings.panel.action.WIFI"), 501);
                return;
            }
            showErrorBulletin(LocaleController.getString(C2369R.string.WifiDisabled));
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            connectWifiModern(wifiManager);
        } else {
            connectWifiLegacy(wifiManager);
        }
    }

    private void connectWifiModern(WifiManager wifiManager) {
        WifiNetworkSuggestion.Builder isAppInteractionRequired = QRCodeSheet$$ExternalSyntheticApiModelOutline0.m194m().setSsid(this.ssid).setIsAppInteractionRequired(true);
        if (!TextUtils.isEmpty(this.password)) {
            if ("WPA".equalsIgnoreCase(this.wifiAuthType)) {
                isAppInteractionRequired.setWpa2Passphrase(this.password);
            } else if (Build.VERSION.SDK_INT >= 30 && "SAE".equalsIgnoreCase(this.wifiAuthType)) {
                isAppInteractionRequired.setWpa3Passphrase(this.password);
            }
        }
        if (wifiManager.addNetworkSuggestions(Collections.singletonList(isAppInteractionRequired.build())) == 0) {
            this.fragment.getParentActivity().startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        } else {
            showErrorBulletin(LocaleController.getString(C2369R.string.WifiFailed));
        }
    }

    private void connectWifiLegacy(WifiManager wifiManager) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", this.ssid);
        boolean z = TextUtils.isEmpty(this.wifiAuthType) || "nopass".equalsIgnoreCase(this.wifiAuthType);
        if (!TextUtils.isEmpty(this.password) && !z) {
            if ("WPA".equalsIgnoreCase(this.wifiAuthType)) {
                wifiConfiguration.preSharedKey = String.format("\"%s\"", this.password);
            } else if ("WEP".equalsIgnoreCase(this.wifiAuthType)) {
                wifiConfiguration.wepKeys[0] = String.format("\"%s\"", this.password);
                wifiConfiguration.wepTxKeyIndex = 0;
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedGroupCiphers.set(0);
            }
        } else {
            wifiConfiguration.allowedKeyManagement.set(0);
        }
        int iAddNetwork = wifiManager.addNetwork(wifiConfiguration);
        if (iAddNetwork != -1) {
            if (wifiManager.enableNetwork(iAddNetwork, true)) {
                wifiManager.reconnect();
                BulletinFactory.m1267of(this.fragment).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.getString(C2369R.string.WifiSuccess)).show();
                return;
            } else {
                showErrorBulletin(LocaleController.getString(C2369R.string.WifiFailed));
                return;
            }
        }
        showErrorBulletin(LocaleController.getString(C2369R.string.WifiFailed));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showErrorBulletin$8(String str) {
        BulletinFactory.m1267of(this.fragment).createErrorBulletin(str).show();
    }

    private void showErrorBulletin(final String str) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showErrorBulletin$8(str);
            }
        });
    }

    private void showCopyBulletin(final boolean z) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.components.QRCodeSheet$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showCopyBulletin$9(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCopyBulletin$9(boolean z) {
        (z ? BulletinFactory.m1266of(getContainer(), null) : BulletinFactory.m1267of(this.fragment)).createCopyBulletin(LocaleController.formatString("TextCopied", C2369R.string.TextCopied, new Object[0])).show();
    }
}
