package org.telegram.p023ui.Components;

/* loaded from: classes6.dex */
public abstract /* synthetic */ class ChatActivityEnterView$$ExternalSyntheticBackport0 {
    /* renamed from: m */
    public static /* synthetic */ String m1269m(String str) {
        int length = str.length();
        int iCharCount = 0;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (!Character.isWhitespace(iCodePointAt)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        while (length > iCharCount) {
            int iCodePointBefore = Character.codePointBefore(str, length);
            if (!Character.isWhitespace(iCodePointBefore)) {
                break;
            }
            length -= Character.charCount(iCodePointBefore);
        }
        return str.substring(iCharCount, length);
    }
}
