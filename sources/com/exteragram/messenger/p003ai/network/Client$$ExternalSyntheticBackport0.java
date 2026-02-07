package com.exteragram.messenger.p003ai.network;

/* loaded from: classes3.dex */
public abstract /* synthetic */ class Client$$ExternalSyntheticBackport0 {
    /* renamed from: m */
    public static /* synthetic */ String m184m(String str) {
        int length = str.length();
        while (length > 0) {
            int iCodePointBefore = Character.codePointBefore(str, length);
            if (!Character.isWhitespace(iCodePointBefore)) {
                break;
            }
            length -= Character.charCount(iCodePointBefore);
        }
        return str.substring(0, length);
    }
}
