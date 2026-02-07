package com.exteragram.messenger.utils.p011ui;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.color.MaterialColors;
import java.io.File;
import java.util.LinkedHashMap;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.p023ui.ActionBar.Theme;
import p017j$.util.Map;

/* loaded from: classes.dex */
public abstract class MonetUtils {
    private static int harmonizeColor;
    private static final LinkedHashMap ids;
    private static final OverlayChangeReceiver overlayChangeReceiver;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ids = linkedHashMap;
        overlayChangeReceiver = new OverlayChangeReceiver();
        harmonizeColor = -1;
        linkedHashMap.put("mBlack", Integer.valueOf(C2369R.color.black));
        linkedHashMap.put("mWhite", Integer.valueOf(C2369R.color.white));
        linkedHashMap.put("mRed200", Integer.valueOf(C2369R.color.mRed200));
        linkedHashMap.put("mRed500", Integer.valueOf(C2369R.color.mRed500));
        linkedHashMap.put("mRed800", Integer.valueOf(C2369R.color.mRed800));
        linkedHashMap.put("mGreen200", Integer.valueOf(C2369R.color.mGreen200));
        linkedHashMap.put("mGreen500", Integer.valueOf(C2369R.color.mGreen500));
        linkedHashMap.put("mGreen800", Integer.valueOf(C2369R.color.mGreen800));
        linkedHashMap.put("a1_10", Integer.valueOf(R.color.system_accent1_10));
        linkedHashMap.put("a1_50", Integer.valueOf(R.color.system_accent1_50));
        linkedHashMap.put("a1_100", Integer.valueOf(R.color.system_accent1_100));
        linkedHashMap.put("a1_200", Integer.valueOf(R.color.system_accent1_200));
        linkedHashMap.put("a1_300", Integer.valueOf(R.color.system_accent1_300));
        linkedHashMap.put("a1_400", Integer.valueOf(R.color.system_accent1_400));
        linkedHashMap.put("a1_500", Integer.valueOf(R.color.system_accent1_500));
        linkedHashMap.put("a1_600", Integer.valueOf(R.color.system_accent1_600));
        linkedHashMap.put("a1_700", Integer.valueOf(R.color.system_accent1_700));
        linkedHashMap.put("a1_800", Integer.valueOf(R.color.system_accent1_800));
        linkedHashMap.put("a1_900", Integer.valueOf(R.color.system_accent1_900));
        linkedHashMap.put("a2_10", Integer.valueOf(R.color.system_accent2_10));
        linkedHashMap.put("a2_50", Integer.valueOf(R.color.system_accent2_50));
        linkedHashMap.put("a2_100", Integer.valueOf(R.color.system_accent2_100));
        linkedHashMap.put("a2_200", Integer.valueOf(R.color.system_accent2_200));
        linkedHashMap.put("a2_300", Integer.valueOf(R.color.system_accent2_300));
        linkedHashMap.put("a2_400", Integer.valueOf(R.color.system_accent2_400));
        linkedHashMap.put("a2_500", Integer.valueOf(R.color.system_accent2_500));
        linkedHashMap.put("a2_600", Integer.valueOf(R.color.system_accent2_600));
        linkedHashMap.put("a2_700", Integer.valueOf(R.color.system_accent2_700));
        linkedHashMap.put("a2_800", Integer.valueOf(R.color.system_accent2_800));
        linkedHashMap.put("a2_900", Integer.valueOf(R.color.system_accent2_900));
        linkedHashMap.put("a3_10", Integer.valueOf(R.color.system_accent3_10));
        linkedHashMap.put("a3_50", Integer.valueOf(R.color.system_accent3_50));
        linkedHashMap.put("a3_100", Integer.valueOf(R.color.system_accent3_100));
        linkedHashMap.put("a3_200", Integer.valueOf(R.color.system_accent3_200));
        linkedHashMap.put("a3_300", Integer.valueOf(R.color.system_accent3_300));
        linkedHashMap.put("a3_400", Integer.valueOf(R.color.system_accent3_400));
        linkedHashMap.put("a3_500", Integer.valueOf(R.color.system_accent3_500));
        linkedHashMap.put("a3_600", Integer.valueOf(R.color.system_accent3_600));
        linkedHashMap.put("a3_700", Integer.valueOf(R.color.system_accent3_700));
        linkedHashMap.put("a3_800", Integer.valueOf(R.color.system_accent3_800));
        linkedHashMap.put("a3_900", Integer.valueOf(R.color.system_accent3_900));
        linkedHashMap.put("n1_10", Integer.valueOf(R.color.system_neutral1_10));
        linkedHashMap.put("n1_50", Integer.valueOf(R.color.system_neutral1_50));
        linkedHashMap.put("n1_100", Integer.valueOf(R.color.system_neutral1_100));
        linkedHashMap.put("n1_200", Integer.valueOf(R.color.system_neutral1_200));
        linkedHashMap.put("n1_300", Integer.valueOf(R.color.system_neutral1_300));
        linkedHashMap.put("n1_400", Integer.valueOf(R.color.system_neutral1_400));
        linkedHashMap.put("n1_500", Integer.valueOf(R.color.system_neutral1_500));
        linkedHashMap.put("n1_600", Integer.valueOf(R.color.system_neutral1_600));
        linkedHashMap.put("n1_700", Integer.valueOf(R.color.system_neutral1_700));
        linkedHashMap.put("n1_800", Integer.valueOf(R.color.system_neutral1_800));
        linkedHashMap.put("n1_900", Integer.valueOf(R.color.system_neutral1_900));
        linkedHashMap.put("n2_10", Integer.valueOf(R.color.system_neutral2_10));
        linkedHashMap.put("n2_50", Integer.valueOf(R.color.system_neutral2_50));
        linkedHashMap.put("n2_100", Integer.valueOf(R.color.system_neutral2_100));
        linkedHashMap.put("n2_200", Integer.valueOf(R.color.system_neutral2_200));
        linkedHashMap.put("n2_300", Integer.valueOf(R.color.system_neutral2_300));
        linkedHashMap.put("n2_400", Integer.valueOf(R.color.system_neutral2_400));
        linkedHashMap.put("n2_500", Integer.valueOf(R.color.system_neutral2_500));
        linkedHashMap.put("n2_600", Integer.valueOf(R.color.system_neutral2_600));
        linkedHashMap.put("n2_700", Integer.valueOf(R.color.system_neutral2_700));
        linkedHashMap.put("n2_800", Integer.valueOf(R.color.system_neutral2_800));
        linkedHashMap.put("n2_900", Integer.valueOf(R.color.system_neutral2_900));
        harmonizeColor = getColor("a1_600");
    }

    public static int harmonize(int i) {
        return MaterialColors.harmonize(i, harmonizeColor);
    }

    public static int getColor(String str) throws NumberFormatException {
        int i;
        int i2;
        int i3;
        try {
            if (str.matches(".*\\(.*\\).*")) {
                i = 100;
                i2 = 100;
                i3 = 100;
                for (String str2 : str.substring(str.indexOf("(") + 1, str.indexOf(")")).split(",")) {
                    String[] strArrSplit = str2.split("=");
                    if (strArrSplit.length == 2) {
                        String strTrim = strArrSplit[0].trim();
                        String strTrim2 = strArrSplit[1].trim();
                        if (strTrim.equalsIgnoreCase("a")) {
                            i = Integer.parseInt(strTrim2);
                        } else if (strTrim.equalsIgnoreCase("s")) {
                            i2 = Integer.parseInt(strTrim2);
                        } else if (strTrim.equalsIgnoreCase("l")) {
                            i3 = Integer.parseInt(strTrim2);
                        }
                    }
                }
                str = str.substring(0, str.indexOf("(")).trim();
            } else {
                i = 100;
                i2 = 100;
                i3 = 100;
            }
            int color = ApplicationLoader.applicationContext.getColor(((Integer) Map.EL.getOrDefault(ids, str, 0)).intValue());
            if (i2 != 100) {
                color = ColorUtils.blendARGB(-1, color, i2 / 100.0f);
            }
            if (i3 != 100) {
                color = ColorUtils.blendARGB(-16777216, color, i3 / 100.0f);
            }
            if (i != 100) {
                color = ColorUtils.setAlphaComponent(color, (int) (i * 2.55f));
            }
            if (!str.startsWith("mR") && !str.startsWith("mG")) {
                return color;
            }
            return harmonize(color);
        } catch (Exception e) {
            FileLog.m1160e(e);
            return 0;
        }
    }

    public static void registerReceiver(Context context) {
        overlayChangeReceiver.register(context);
    }

    public static void unregisterReceiver(Context context) {
        try {
            overlayChangeReceiver.unregister(context);
        } catch (Exception unused) {
        }
    }

    private static class OverlayChangeReceiver extends BroadcastReceiver {
        private OverlayChangeReceiver() {
        }

        public void register(Context context) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("android", 0);
            context.registerReceiver(this, intentFilter);
        }

        public void unregister(Context context) {
            context.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.OVERLAY_CHANGED".equals(intent.getAction()) && Theme.isCurrentThemeMonet()) {
                StringBuilder sb = new StringBuilder();
                sb.append("monet_");
                sb.append(Theme.getActiveTheme().isDark() ? "dark" : "light");
                sb.append(".attheme");
                File file = new File(ApplicationLoader.getFilesDirFixed(), sb.toString());
                if (file.exists()) {
                    file.delete();
                }
                Theme.applyTheme(Theme.getActiveTheme());
            }
        }
    }
}
