package p017j$.util;

import java.security.PrivilegedAction;

/* renamed from: j$.util.r0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1853r0 implements PrivilegedAction {

    /* renamed from: a */
    public final /* synthetic */ int f958a;

    @Override // java.security.PrivilegedAction
    public final Object run() {
        switch (this.f958a) {
            case 0:
                return Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire"));
            case 1:
                return Boolean.valueOf(Boolean.getBoolean("java.util.secureRandomSeed"));
            default:
                return Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire"));
        }
    }
}
