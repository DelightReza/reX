package com.google.android.play.integrity.internal;

/* renamed from: com.google.android.play.integrity.internal.ah */
/* loaded from: classes4.dex */
public final class C1208ah implements InterfaceC1212al {

    /* renamed from: a */
    private static final Object f351a = new Object();

    /* renamed from: b */
    private volatile InterfaceC1212al f352b;

    /* renamed from: c */
    private volatile Object f353c = f351a;

    private C1208ah(InterfaceC1212al interfaceC1212al) {
        this.f352b = interfaceC1212al;
    }

    /* renamed from: b */
    public static InterfaceC1212al m403b(InterfaceC1212al interfaceC1212al) {
        return interfaceC1212al instanceof C1208ah ? interfaceC1212al : new C1208ah(interfaceC1212al);
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1212al
    /* renamed from: a */
    public final Object mo344a() {
        Object objMo344a;
        Object obj = this.f353c;
        Object obj2 = f351a;
        if (obj != obj2) {
            return obj;
        }
        synchronized (this) {
            try {
                objMo344a = this.f353c;
                if (objMo344a == obj2) {
                    objMo344a = this.f352b.mo344a();
                    Object obj3 = this.f353c;
                    if (obj3 != obj2 && obj3 != objMo344a) {
                        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + obj3 + " & " + objMo344a + ". This is likely due to a circular dependency.");
                    }
                    this.f353c = objMo344a;
                    this.f352b = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return objMo344a;
    }
}
