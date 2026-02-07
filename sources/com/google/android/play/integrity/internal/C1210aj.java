package com.google.android.play.integrity.internal;

/* renamed from: com.google.android.play.integrity.internal.aj */
/* loaded from: classes4.dex */
public final class C1210aj implements InterfaceC1209ai {

    /* renamed from: a */
    private static final C1210aj f354a = new C1210aj(null);

    /* renamed from: b */
    private final Object f355b;

    private C1210aj(Object obj) {
        this.f355b = obj;
    }

    /* renamed from: b */
    public static InterfaceC1209ai m404b(Object obj) {
        if (obj != null) {
            return new C1210aj(obj);
        }
        throw new NullPointerException("instance cannot be null");
    }

    @Override // com.google.android.play.integrity.internal.InterfaceC1212al
    /* renamed from: a */
    public final Object mo344a() {
        return this.f355b;
    }
}
