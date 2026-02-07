package org.telegram.p023ui.web;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.RadioColorCell;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Cells.TextCheckCell;
import org.telegram.p023ui.Components.CombinedDrawable;
import org.telegram.p023ui.Components.EditTextBoldCursor;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalFragment;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.web.BrowserHistory;
import org.telegram.p023ui.web.WebMetadataCache;
import org.telegram.tgnet.TLObject;

/* loaded from: classes6.dex */
public class WebBrowserSettings extends UniversalFragment implements NotificationCenter.NotificationCenterDelegate {
    private Drawable addIcon;
    private long cacheSize;
    private long cookiesSize;
    private long historySize;
    private Utilities.Callback whenHistoryClicked;

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        return false;
    }

    public WebBrowserSettings(Utilities.Callback callback) {
        this.whenHistoryClicked = callback;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        loadSizes();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.webViewResolved);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.webViewResolved);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        UniversalRecyclerView universalRecyclerView;
        if (i != NotificationCenter.webViewResolved || (universalRecyclerView = this.listView) == null) {
            return;
        }
        universalRecyclerView.adapter.update(true);
    }

    private void loadSizes() {
        if (BrowserHistory.getHistory(new Utilities.Callback() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda11
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$loadSizes$0((ArrayList) obj);
            }
        }) != null) {
            this.historySize = r0.size();
            UniversalRecyclerView universalRecyclerView = this.listView;
            if (universalRecyclerView != null && universalRecyclerView.adapter != null && universalRecyclerView.isAttachedToWindow()) {
                this.listView.adapter.update(true);
            }
        }
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadSizes$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSizes$0(ArrayList arrayList) {
        this.historySize = arrayList.size();
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView == null || universalRecyclerView.adapter == null || !universalRecyclerView.isAttachedToWindow()) {
            return;
        }
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSizes$2() {
        File databasePath = ApplicationLoader.applicationContext.getDatabasePath("webview.db");
        long length = (databasePath == null || !databasePath.exists()) ? 0L : databasePath.length();
        File databasePath2 = ApplicationLoader.applicationContext.getDatabasePath("webviewCache.db");
        if (databasePath2 != null && databasePath2.exists()) {
            length += databasePath2.length();
        }
        File file = new File(ApplicationLoader.applicationContext.getApplicationInfo().dataDir, "app_webview");
        if (file.exists()) {
            length += getDirectorySize(file, Boolean.FALSE);
        }
        File file2 = new File(ApplicationLoader.applicationContext.getApplicationInfo().dataDir, "cache/WebView");
        if (file2.exists()) {
            length += getDirectorySize(file2, null);
        }
        final long j = length;
        File file3 = new File(ApplicationLoader.applicationContext.getApplicationInfo().dataDir, "app_webview");
        final long directorySize = file3.exists() ? getDirectorySize(file3, Boolean.TRUE) : 0L;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadSizes$1(j, directorySize);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSizes$1(long j, long j2) {
        this.cacheSize = j;
        this.cookiesSize = j2;
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView == null || universalRecyclerView.adapter == null || !universalRecyclerView.isAttachedToWindow()) {
            return;
        }
        this.listView.adapter.update(true);
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.poll_add_circle).mutate();
        Drawable drawableMutate2 = context.getResources().getDrawable(C2369R.drawable.poll_add_plus).mutate();
        int themedColor = getThemedColor(Theme.key_switchTrackChecked);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(themedColor, mode));
        drawableMutate2.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_checkboxCheck), mode));
        this.addIcon = new CombinedDrawable(drawableMutate, drawableMutate2) { // from class: org.telegram.ui.web.WebBrowserSettings.1
            @Override // org.telegram.p023ui.Components.CombinedDrawable, android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter colorFilter) {
            }

            {
                this.translateX = AndroidUtilities.m1146dp(2.0f);
            }
        };
        return super.createView(context);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        return super.isLightStatusBar();
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected CharSequence getTitle() {
        return LocaleController.getString(C2369R.string.BrowserSettingsTitle);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00fe  */
    @Override // org.telegram.p023ui.Components.UniversalFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void fillItems(java.util.ArrayList r12, org.telegram.p023ui.Components.UniversalAdapter r13) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 478
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.web.WebBrowserSettings.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    @Override // org.telegram.p023ui.Components.UniversalFragment
    protected void onClick(UItem uItem, final View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 == 12) {
            SharedConfig.toggleBrowserAdaptableColors();
            ((TextCheckCell) view).setChecked(SharedConfig.adaptableColorInBrowser);
            return;
        }
        if (i2 == 13) {
            SharedConfig.toggleLocalInstantView();
            ((TextCheckCell) view).setChecked(SharedConfig.onlyLocalInstantView);
            return;
        }
        if (i2 == 1) {
            SharedConfig.toggleInappBrowser();
            TextCheckCell textCheckCell = (TextCheckCell) view;
            textCheckCell.setChecked(SharedConfig.inappBrowser);
            boolean z = SharedConfig.inappBrowser;
            textCheckCell.setBackgroundColorAnimated(z, Theme.getColor(z ? Theme.key_windowBackgroundChecked : Theme.key_windowBackgroundUnchecked));
            this.listView.adapter.update(true);
            return;
        }
        if (i2 == 10) {
            SharedConfig.toggleCustomTabs(true);
            this.listView.adapter.update(true);
            return;
        }
        if (i2 == 11) {
            SharedConfig.toggleCustomTabs(false);
            this.listView.adapter.update(true);
            return;
        }
        String str = "";
        if (i2 == 2) {
            AlertDialog.Builder title = new AlertDialog.Builder(getContext(), getResourceProvider()).setTitle(LocaleController.getString(C2369R.string.BrowserSettingsCacheClear));
            int i3 = C2369R.string.BrowserSettingsCacheClearText;
            if (this.cacheSize != 0) {
                str = " (" + AndroidUtilities.formatFileSize(this.cacheSize) + ")";
            }
            title.setMessage(LocaleController.formatString(i3, str)).setPositiveButton(LocaleController.getString(C2369R.string.Clear), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i4) {
                    this.f$0.lambda$onClick$3(alertDialog, i4);
                }
            }).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).makeRed(-1).show();
            return;
        }
        if (i2 == 3) {
            AlertDialog.Builder title2 = new AlertDialog.Builder(getContext(), getResourceProvider()).setTitle(LocaleController.getString(C2369R.string.BrowserSettingsCookiesClear));
            int i4 = C2369R.string.BrowserSettingsCookiesClearText;
            if (this.cookiesSize != 0) {
                str = " (" + AndroidUtilities.formatFileSize(this.cookiesSize) + ")";
            }
            title2.setMessage(LocaleController.formatString(i4, str)).setPositiveButton(LocaleController.getString(C2369R.string.Clear), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i5) {
                    this.f$0.lambda$onClick$4(alertDialog, i5);
                }
            }).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).makeRed(-1).show();
            return;
        }
        if (i2 == 7) {
            ArrayList history = BrowserHistory.getHistory();
            int size = history.size();
            long jMin = Long.MAX_VALUE;
            int i5 = 0;
            while (i5 < size) {
                Object obj = history.get(i5);
                i5++;
                jMin = Math.min(jMin, ((BrowserHistory.Entry) obj).time);
            }
            new AlertDialog.Builder(getContext(), getResourceProvider()).setTitle(LocaleController.getString(C2369R.string.BrowserSettingsHistoryClear)).setMessage(LocaleController.formatString(C2369R.string.BrowserSettingsHistoryClearText, LocaleController.formatDateChat(jMin / 1000))).setPositiveButton(LocaleController.getString(C2369R.string.Clear), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda3
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i6) {
                    this.f$0.lambda$onClick$5(alertDialog, i6);
                }
            }).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).makeRed(-1).show();
            return;
        }
        if (i2 == 9) {
            final HistoryFragment[] historyFragmentArr = {null};
            HistoryFragment historyFragment = new HistoryFragment(null, new Utilities.Callback() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj2) {
                    this.f$0.lambda$onClick$6(historyFragmentArr, (BrowserHistory.Entry) obj2);
                }
            });
            historyFragmentArr[0] = historyFragment;
            presentFragment(historyFragment);
            return;
        }
        if (i2 == 5) {
            RestrictedDomainsList.getInstance().restrictedDomains.clear();
            RestrictedDomainsList.getInstance().scheduleSave();
            this.listView.adapter.update(true);
            return;
        }
        if (uItem.instanceOf(WebsiteView.Factory.class)) {
            WebsiteView websiteView = (WebsiteView) view;
            final ArrayList arrayList = websiteView.domains;
            ItemOptions.makeOptions((ViewGroup) this.fragmentView, websiteView).add(C2369R.drawable.menu_delete_old, LocaleController.getString(C2369R.string.Remove), new Runnable() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onClick$7(arrayList);
                }
            }).show();
            return;
        }
        int i6 = uItem.f2017id;
        if (i6 == 6) {
            if (getParentActivity() == null) {
                return;
            }
            final AtomicReference atomicReference = new AtomicReference();
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(1);
            ArrayList searchEngines = SearchEngine.getSearchEngines();
            int size2 = searchEngines.size();
            CharSequence[] charSequenceArr = new CharSequence[size2];
            final int i7 = 0;
            while (i7 < size2) {
                charSequenceArr[i7] = ((SearchEngine) searchEngines.get(i7)).name;
                RadioColorCell radioColorCell = new RadioColorCell(getParentActivity());
                radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
                radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                radioColorCell.setTextAndValue(charSequenceArr[i7], i7 == SharedConfig.searchEngineType);
                radioColorCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), 2));
                linearLayout.addView(radioColorCell);
                radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        WebBrowserSettings.m19727$r8$lambda$j8lKl_im6VnUMjGwoAtcQ4sg9Y(i7, view, atomicReference, view2);
                    }
                });
                i7++;
            }
            AlertDialog alertDialogCreate = new AlertDialog.Builder(getParentActivity()).setTitle(LocaleController.getString(C2369R.string.SearchEngine)).setView(linearLayout).setNegativeButton(LocaleController.getString("Cancel", C2369R.string.Cancel), null).create();
            atomicReference.set(alertDialogCreate);
            showDialog(alertDialogCreate);
            return;
        }
        if (i6 == 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), getResourceProvider());
            builder.setTitle(LocaleController.getString(C2369R.string.BrowserSettingsAddTitle));
            LinearLayout linearLayout2 = new LinearLayout(getContext());
            linearLayout2.setOrientation(1);
            TextView textView = new TextView(getContext());
            int i8 = Theme.key_dialogTextBlack;
            textView.setTextColor(Theme.getColor(i8, getResourceProvider()));
            textView.setTextSize(1, 16.0f);
            textView.setText(LocaleController.getString(C2369R.string.BrowserSettingsAddText));
            linearLayout2.addView(textView, LayoutHelper.createLinear(-1, -2, 24.0f, 5.0f, 24.0f, 12.0f));
            final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(getContext()) { // from class: org.telegram.ui.web.WebBrowserSettings.2
                @Override // org.telegram.p023ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
                protected void onMeasure(int i9, int i10) {
                    super.onMeasure(i9, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(36.0f), TLObject.FLAG_30));
                }
            };
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onClick$11(editTextBoldCursor, alertDialogArr);
                }
            };
            editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.web.WebBrowserSettings.3
                @Override // android.widget.TextView.OnEditorActionListener
                public boolean onEditorAction(TextView textView2, int i9, KeyEvent keyEvent) {
                    if (i9 != 6) {
                        return false;
                    }
                    runnable.run();
                    return true;
                }
            });
            editTextBoldCursor.setTextSize(1, 18.0f);
            editTextBoldCursor.setText("");
            editTextBoldCursor.setTextColor(Theme.getColor(i8, getResourceProvider()));
            editTextBoldCursor.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText, getResourceProvider()));
            editTextBoldCursor.setHintText(LocaleController.getString(C2369R.string.BrowserSettingsAddHint));
            editTextBoldCursor.setSingleLine(true);
            editTextBoldCursor.setFocusable(true);
            editTextBoldCursor.setInputType(16384);
            editTextBoldCursor.setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField, getResourceProvider()), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated, getResourceProvider()), Theme.getColor(Theme.key_text_RedRegular, getResourceProvider()));
            editTextBoldCursor.setImeOptions(6);
            editTextBoldCursor.setBackgroundDrawable(null);
            editTextBoldCursor.setPadding(0, 0, AndroidUtilities.m1146dp(42.0f), 0);
            linearLayout2.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, -2, 24.0f, 0.0f, 24.0f, 10.0f));
            builder.setView(linearLayout2);
            builder.setWidth(AndroidUtilities.m1146dp(292.0f));
            builder.setPositiveButton(LocaleController.getString(C2369R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda8
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i9) {
                    runnable.run();
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda9
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i9) {
                    alertDialog.dismiss();
                }
            });
            AlertDialog alertDialogCreate2 = builder.create();
            final AlertDialog[] alertDialogArr = {alertDialogCreate2};
            alertDialogCreate2.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    AndroidUtilities.hideKeyboard(editTextBoldCursor);
                }
            });
            alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    WebBrowserSettings.$r8$lambda$RTpnujINkXf3xoAr5TqC5WEAaQs(editTextBoldCursor, dialogInterface);
                }
            });
            alertDialogArr[0].setDismissDialogByButtons(false);
            alertDialogArr[0].show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3(AlertDialog alertDialog, int i) {
        ApplicationLoader.applicationContext.deleteDatabase("webview.db");
        ApplicationLoader.applicationContext.deleteDatabase("webviewCache.db");
        WebStorage.getInstance().deleteAllData();
        try {
            WebView webView = new WebView(getContext());
            webView.clearCache(true);
            webView.clearHistory();
            webView.destroy();
        } catch (Exception unused) {
        }
        try {
            File file = new File(ApplicationLoader.applicationContext.getApplicationInfo().dataDir, "app_webview");
            if (file.exists()) {
                deleteDirectory(file, Boolean.FALSE);
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        try {
            File file2 = new File(ApplicationLoader.applicationContext.getApplicationInfo().dataDir, "cache/WebView");
            if (file2.exists()) {
                deleteDirectory(file2, null);
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
        WebMetadataCache.getInstance().clear();
        loadSizes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4(AlertDialog alertDialog, int i) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        cookieManager.flush();
        try {
            File file = new File(ApplicationLoader.applicationContext.getApplicationInfo().dataDir, "app_webview");
            if (file.exists()) {
                deleteDirectory(file, Boolean.TRUE);
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        loadSizes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5(AlertDialog alertDialog, int i) {
        BrowserHistory.clearHistory();
        this.historySize = 0L;
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(HistoryFragment[] historyFragmentArr, BrowserHistory.Entry entry) {
        historyFragmentArr[0].lambda$onBackPressed$371();
        if (this.whenHistoryClicked != null) {
            lambda$onBackPressed$371();
            this.whenHistoryClicked.run(entry);
        } else {
            Browser.openUrl(getContext(), entry.url);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$7(ArrayList arrayList) {
        RestrictedDomainsList.getInstance().setRestricted(false, (String[]) arrayList.toArray(new String[0]));
        this.listView.adapter.update(true);
    }

    /* renamed from: $r8$lambda$j8lKl-_im6VnUMjGwoAtcQ4sg9Y, reason: not valid java name */
    public static /* synthetic */ void m19727$r8$lambda$j8lKl_im6VnUMjGwoAtcQ4sg9Y(int i, View view, AtomicReference atomicReference, View view2) {
        SharedConfig.setSearchEngineType(i);
        ((TextCell) view).setValue(SearchEngine.getCurrent().name, true);
        ((Dialog) atomicReference.get()).dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$11(EditTextBoldCursor editTextBoldCursor, final AlertDialog[] alertDialogArr) {
        String string = editTextBoldCursor.getText().toString();
        Uri uri = Uri.parse(string);
        if (uri == null || uri.getHost() == null) {
            uri = Uri.parse("https://" + string);
        }
        if (uri == null || uri.getHost() == null) {
            AndroidUtilities.shakeView(editTextBoldCursor);
            return;
        }
        final String lowerCase = uri.getHost().toLowerCase();
        if (lowerCase.startsWith("www.")) {
            lowerCase = lowerCase.substring(4);
        }
        RestrictedDomainsList.getInstance().setRestricted(true, lowerCase);
        WebMetadataCache.WebMetadata webMetadata = WebMetadataCache.getInstance().get(lowerCase);
        if (webMetadata != null && !TextUtils.isEmpty(webMetadata.sitename) && webMetadata.favicon != null) {
            AlertDialog alertDialog = alertDialogArr[0];
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            this.listView.adapter.update(true);
            return;
        }
        final AlertDialog alertDialog2 = new AlertDialog(getContext(), 3);
        final Runnable runnable = new Runnable() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onClick$9(alertDialogArr, alertDialog2);
            }
        };
        AndroidUtilities.runOnUIThread(runnable, 5000L);
        alertDialog2.showDelayed(300L);
        WebMetadataCache.retrieveFaviconAndSitename("https://" + string + "/", new Utilities.Callback2() { // from class: org.telegram.ui.web.WebBrowserSettings$$ExternalSyntheticLambda15
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$onClick$10(runnable, alertDialog2, lowerCase, (String) obj, (Bitmap) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$9(AlertDialog[] alertDialogArr, AlertDialog alertDialog) {
        alertDialogArr[0].dismiss();
        alertDialog.dismissUnless(800L);
        this.listView.adapter.update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$10(Runnable runnable, AlertDialog alertDialog, String str, String str2, Bitmap bitmap) {
        AndroidUtilities.cancelRunOnUIThread(runnable);
        alertDialog.dismissUnless(800L);
        if (WebMetadataCache.getInstance().get(str) != null) {
            this.listView.adapter.update(true);
        }
    }

    public static /* synthetic */ void $r8$lambda$RTpnujINkXf3xoAr5TqC5WEAaQs(EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
        editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    public static class WebsiteView extends FrameLayout {
        private ArrayList domains;
        public final ImageView imageView;
        private boolean needDivider;
        public final ImageView optionsView;
        public final TextView subtitleView;
        public final TextView titleView;

        public WebsiteView(Context context) {
            super(context);
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            addView(imageView, LayoutHelper.createFrame(28, 28.0f, 19, 18.0f, 0.0f, 0.0f, 0.0f));
            TextView textView = new TextView(context);
            this.titleView = textView;
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            textView.setTextSize(1, 16.0f);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setMaxLines(1);
            TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
            textView.setEllipsize(truncateAt);
            addView(textView, LayoutHelper.createFrame(-1, -2.0f, 55, 68.0f, 7.0f, 54.0f, 0.0f));
            TextView textView2 = new TextView(context) { // from class: org.telegram.ui.web.WebBrowserSettings.WebsiteView.1
                @Override // android.widget.TextView, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(i, i2);
                    WebsiteView.this.subtitleView.setPivotY(getMeasuredHeight() / 2.0f);
                }
            };
            this.subtitleView = textView2;
            textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
            textView2.setTextSize(1, 13.0f);
            textView2.setMaxLines(1);
            textView2.setEllipsize(truncateAt);
            textView2.setPivotX(0.0f);
            addView(textView2, LayoutHelper.createFrame(-1, -2.0f, 55, 68.0f, 30.0f, 54.0f, 0.0f));
            ImageView imageView2 = new ImageView(context);
            this.optionsView = imageView2;
            imageView2.setScaleType(ImageView.ScaleType.CENTER);
            imageView2.setImageResource(C2369R.drawable.ic_ab_other);
            imageView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayIcon), PorterDuff.Mode.SRC_IN));
            addView(imageView2, LayoutHelper.createFrame(32, 32.0f, 21, 0.0f, 0.0f, 18.0f, 0.0f));
        }

        public void set(CharSequence charSequence, ArrayList arrayList, Bitmap bitmap, boolean z) {
            this.titleView.setText(charSequence);
            StringBuilder sb = new StringBuilder();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                String str = (String) obj;
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(str);
            }
            this.subtitleView.setText(sb);
            if (TextUtils.isEmpty(charSequence)) {
                this.subtitleView.setTranslationY(-AndroidUtilities.m1146dp(14.0f));
                this.subtitleView.setScaleX(1.3f);
                this.subtitleView.setScaleY(1.3f);
            } else {
                this.subtitleView.setTranslationY(0.0f);
                this.subtitleView.setScaleX(1.0f);
                this.subtitleView.setScaleY(1.0f);
            }
            this.domains = arrayList;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = (arrayList.isEmpty() || TextUtils.isEmpty((CharSequence) arrayList.get(0))) ? "" : (String) arrayList.get(0);
            }
            String string = charSequence.toString();
            if (bitmap != null) {
                this.imageView.setImageBitmap(bitmap);
            } else {
                CombinedDrawable combinedDrawable = new CombinedDrawable(Theme.createRoundRectDrawable(AndroidUtilities.m1146dp(6.0f), Theme.multAlpha(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText), 0.1f)), new Drawable(string) { // from class: org.telegram.ui.web.WebBrowserSettings.WebsiteView.2
                    private final Text text;
                    final /* synthetic */ String val$s;

                    @Override // android.graphics.drawable.Drawable
                    public int getOpacity() {
                        return -2;
                    }

                    @Override // android.graphics.drawable.Drawable
                    public void setAlpha(int i2) {
                    }

                    @Override // android.graphics.drawable.Drawable
                    public void setColorFilter(ColorFilter colorFilter) {
                    }

                    {
                        this.val$s = string;
                        this.text = new Text(string.substring(0, !string.isEmpty() ? 1 : 0), 14.0f, AndroidUtilities.bold());
                    }

                    @Override // android.graphics.drawable.Drawable
                    public void draw(Canvas canvas) {
                        this.text.draw(canvas, getBounds().centerX() - (this.text.getCurrentWidth() / 2.0f), getBounds().centerY(), Theme.getColor(Theme.key_windowBackgroundWhiteBlackText), 1.0f);
                    }
                });
                combinedDrawable.setCustomSize(AndroidUtilities.m1146dp(28.0f), AndroidUtilities.m1146dp(28.0f));
                this.imageView.setImageDrawable(combinedDrawable);
            }
            if (this.needDivider != z) {
                invalidate();
            }
            this.needDivider = z;
            setWillNotDraw(!z);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            canvas.drawRect(AndroidUtilities.m1146dp(64.0f), getHeight() - 1, getWidth(), getHeight(), Theme.dividerPaint);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(56.0f), TLObject.FLAG_30));
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public WebsiteView createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new WebsiteView(context);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                WebsiteView websiteView = (WebsiteView) view;
                CharSequence charSequence = uItem.text;
                ArrayList arrayList = (ArrayList) uItem.object2;
                Object obj = uItem.object;
                websiteView.set(charSequence, arrayList, obj instanceof Bitmap ? (Bitmap) obj : null, z);
            }

            /* renamed from: as */
            public static UItem m1365as(ArrayList arrayList, String str, Bitmap bitmap) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.text = str;
                uItemOfFactory.object = bitmap;
                uItemOfFactory.object2 = arrayList;
                return uItemOfFactory;
            }
        }
    }

    private static long getDirectorySize(File file, Boolean bool) {
        long directorySize = 0;
        if (file == null || !file.exists()) {
            return 0L;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    directorySize += getDirectorySize(file2, bool);
                }
                return directorySize;
            }
        } else if (bool == null || bool.booleanValue() == file.getName().startsWith("Cookies")) {
            return file.length();
        }
        return 0L;
    }

    private static boolean deleteDirectory(File file, Boolean bool) {
        boolean z;
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                z = true;
                for (File file2 : fileArrListFiles) {
                    if ((bool == null || bool.booleanValue() == file2.getName().startsWith("Cookies")) && !deleteDirectory(file2, bool)) {
                        z = false;
                    }
                }
            } else {
                z = true;
            }
            if (z) {
                file.delete();
            }
        } else {
            if (bool != null && bool.booleanValue() != file.getName().startsWith("Cookies")) {
                return false;
            }
            file.delete();
        }
        return true;
    }
}
