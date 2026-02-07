package com.yandex.runtime.sensors.internal.wifi;

import android.net.wifi.WifiManager;
import com.yandex.runtime.Runtime;

/* loaded from: classes4.dex */
public class WifiScanner {
    public static boolean activeScan() {
        return ((WifiManager) Runtime.getApplicationContext().getSystemService("wifi")).startScan();
    }
}
