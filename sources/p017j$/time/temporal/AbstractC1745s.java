package p017j$.time.temporal;

import p017j$.time.AbstractC1641c;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.util.Objects;

/* renamed from: j$.time.temporal.s */
/* loaded from: classes2.dex */
public abstract class AbstractC1745s {

    /* renamed from: a */
    public static final C1678e f690a = new C1678e(3);

    /* renamed from: b */
    public static final C1678e f691b = new C1678e(4);

    /* renamed from: c */
    public static final C1678e f692c = new C1678e(5);

    /* renamed from: d */
    public static final C1678e f693d = new C1678e(6);

    /* renamed from: e */
    public static final C1678e f694e = new C1678e(7);

    /* renamed from: f */
    public static final C1678e f695f = new C1678e(8);

    /* renamed from: g */
    public static final C1678e f696g = new C1678e(9);

    /* renamed from: e */
    public static /* synthetic */ int m817e(int i) {
        int i2 = i % 7;
        if (i2 == 0) {
            return 0;
        }
        return (((i ^ 7) >> 31) | 1) > 0 ? i2 : i2 + 7;
    }

    /* renamed from: d */
    public static C1748v m816d(InterfaceC1740n interfaceC1740n, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            Objects.requireNonNull(interfaceC1744r, "field");
            return interfaceC1744r.mo803j(interfaceC1740n);
        }
        if (interfaceC1740n.mo543e(interfaceC1744r)) {
            return ((EnumC1727a) interfaceC1744r).f671b;
        }
        throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
    }

    /* renamed from: a */
    public static int m813a(InterfaceC1740n interfaceC1740n, InterfaceC1744r interfaceC1744r) {
        C1748v c1748vMo545k = interfaceC1740n.mo545k(interfaceC1744r);
        if (!c1748vMo545k.m823d()) {
            throw new C1747u("Invalid field " + interfaceC1744r + " for get() method, use getLong() instead");
        }
        long jMo542D = interfaceC1740n.mo542D(interfaceC1744r);
        if (c1748vMo545k.m824e(jMo542D)) {
            return (int) jMo542D;
        }
        throw new C1640b("Invalid value for " + interfaceC1744r + " (valid values " + c1748vMo545k + "): " + jMo542D);
    }

    /* renamed from: c */
    public static Object m815c(InterfaceC1740n interfaceC1740n, C1678e c1678e) {
        if (c1678e == f690a || c1678e == f691b || c1678e == f692c) {
            return null;
        }
        return c1678e.m704g(interfaceC1740n);
    }

    /* renamed from: b */
    public static InterfaceC1739m m814b(InterfaceC1739m interfaceC1739m, long j, InterfaceC1746t interfaceC1746t) {
        long j2;
        if (j == Long.MIN_VALUE) {
            interfaceC1739m = interfaceC1739m.mo557d(Long.MAX_VALUE, interfaceC1746t);
            j2 = 1;
        } else {
            j2 = -j;
        }
        return interfaceC1739m.mo557d(j2, interfaceC1746t);
    }
}
