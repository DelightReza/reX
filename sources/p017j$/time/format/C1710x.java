package p017j$.time.format;

import p017j$.time.C1678e;
import p017j$.time.ZoneId;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1744r;

/* renamed from: j$.time.format.x */
/* loaded from: classes2.dex */
public final class C1710x implements InterfaceC1740n {

    /* renamed from: a */
    public final /* synthetic */ InterfaceC1652b f630a;

    /* renamed from: b */
    public final /* synthetic */ InterfaceC1740n f631b;

    /* renamed from: c */
    public final /* synthetic */ InterfaceC1661k f632c;

    /* renamed from: d */
    public final /* synthetic */ ZoneId f633d;

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    public C1710x(InterfaceC1652b interfaceC1652b, InterfaceC1740n interfaceC1740n, InterfaceC1661k interfaceC1661k, ZoneId zoneId) {
        this.f630a = interfaceC1652b;
        this.f631b = interfaceC1740n;
        this.f632c = interfaceC1661k;
        this.f633d = zoneId;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        InterfaceC1652b interfaceC1652b = this.f630a;
        if (interfaceC1652b != null && interfaceC1744r.isDateBased()) {
            return interfaceC1652b.mo543e(interfaceC1744r);
        }
        return this.f631b.mo543e(interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        InterfaceC1652b interfaceC1652b = this.f630a;
        if (interfaceC1652b != null && interfaceC1744r.isDateBased()) {
            return interfaceC1652b.mo545k(interfaceC1744r);
        }
        return this.f631b.mo545k(interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        InterfaceC1652b interfaceC1652b = this.f630a;
        if (interfaceC1652b != null && interfaceC1744r.isDateBased()) {
            return interfaceC1652b.mo542D(interfaceC1744r);
        }
        return this.f631b.mo542D(interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f691b) {
            return this.f632c;
        }
        if (c1678e == AbstractC1745s.f690a) {
            return this.f633d;
        }
        if (c1678e == AbstractC1745s.f692c) {
            return this.f631b.mo547t(c1678e);
        }
        return c1678e.m704g(this);
    }

    public final String toString() {
        String str;
        String str2 = "";
        InterfaceC1661k interfaceC1661k = this.f632c;
        if (interfaceC1661k != null) {
            str = " with chronology " + interfaceC1661k;
        } else {
            str = "";
        }
        ZoneId zoneId = this.f633d;
        if (zoneId != null) {
            str2 = " with zone " + zoneId;
        }
        return this.f631b + str + str2;
    }
}
