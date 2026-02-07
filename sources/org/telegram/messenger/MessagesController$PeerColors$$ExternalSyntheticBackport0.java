package org.telegram.messenger;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class MessagesController$PeerColors$$ExternalSyntheticBackport0 {
    /* renamed from: m */
    public static /* synthetic */ int m1181m(String str, int i) throws NumberFormatException {
        if (str.length() > 1 && str.charAt(0) == '+') {
            str = str.substring(1);
        }
        long j = Long.parseLong(str, i);
        if ((4294967295L & j) == j) {
            return (int) j;
        }
        throw new NumberFormatException("Input " + str + " in base " + i + " is not in the range of an unsigned integer");
    }
}
