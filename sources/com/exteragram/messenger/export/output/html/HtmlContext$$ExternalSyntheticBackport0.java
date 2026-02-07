package com.exteragram.messenger.export.output.html;

import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class HtmlContext$$ExternalSyntheticBackport0 {
    /* renamed from: m */
    public static /* synthetic */ String m206m(String str, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("count is negative: " + i);
        }
        int length = str.length();
        if (i == 0 || length == 0) {
            return "";
        }
        if (i == 1) {
            return str;
        }
        if (str.length() <= ConnectionsManager.DEFAULT_DATACENTER_ID / i) {
            StringBuilder sb = new StringBuilder(length * i);
            for (int i2 = 0; i2 < i; i2++) {
                sb.append(str);
            }
            return sb.toString();
        }
        throw new OutOfMemoryError("Repeating " + str.length() + " bytes String " + i + " times will produce a String exceeding maximum size.");
    }
}
