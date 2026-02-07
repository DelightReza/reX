package p017j$.util;

import java.security.AccessController;

/* renamed from: j$.util.s0 */
/* loaded from: classes2.dex */
public abstract class AbstractC1855s0 {

    /* renamed from: a */
    public static final boolean f959a = ((Boolean) AccessController.doPrivileged(new C1853r0(0))).booleanValue();

    /* renamed from: a */
    public static void m914a(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}
