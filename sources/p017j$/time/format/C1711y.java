package p017j$.time.format;

import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.ZoneId;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.Objects;

/* renamed from: j$.time.format.y */
/* loaded from: classes2.dex */
public final class C1711y {

    /* renamed from: a */
    public final InterfaceC1740n f634a;

    /* renamed from: b */
    public final DateTimeFormatter f635b;

    /* renamed from: c */
    public int f636c;

    public C1711y(InterfaceC1740n interfaceC1740n, DateTimeFormatter dateTimeFormatter) {
        InterfaceC1661k interfaceC1661k = dateTimeFormatter.f553e;
        if (interfaceC1661k != null) {
            InterfaceC1661k interfaceC1661k2 = (InterfaceC1661k) interfaceC1740n.mo547t(AbstractC1745s.f691b);
            ZoneId zoneId = (ZoneId) interfaceC1740n.mo547t(AbstractC1745s.f690a);
            InterfaceC1652b interfaceC1652bMo651A = null;
            interfaceC1661k = Objects.equals(interfaceC1661k, interfaceC1661k2) ? null : interfaceC1661k;
            Objects.equals(null, zoneId);
            if (interfaceC1661k != null) {
                InterfaceC1661k interfaceC1661k3 = interfaceC1661k != null ? interfaceC1661k : interfaceC1661k2;
                if (interfaceC1661k != null) {
                    if (interfaceC1740n.mo543e(EnumC1727a.EPOCH_DAY)) {
                        interfaceC1652bMo651A = interfaceC1661k3.mo651A(interfaceC1740n);
                    } else if (interfaceC1661k != C1668r.f512c || interfaceC1661k2 != null) {
                        for (EnumC1727a enumC1727a : EnumC1727a.values()) {
                            if (enumC1727a.isDateBased() && interfaceC1740n.mo543e(enumC1727a)) {
                                throw new C1640b("Unable to apply override chronology '" + interfaceC1661k + "' because the temporal object being formatted contains date fields but does not represent a whole date: " + interfaceC1740n);
                            }
                        }
                    }
                }
                interfaceC1740n = new C1710x(interfaceC1652bMo651A, interfaceC1740n, interfaceC1661k3, zoneId);
            }
        }
        this.f634a = interfaceC1740n;
        this.f635b = dateTimeFormatter;
    }

    /* renamed from: b */
    public final Object m768b(C1678e c1678e) {
        InterfaceC1740n interfaceC1740n = this.f634a;
        Object objMo547t = interfaceC1740n.mo547t(c1678e);
        if (objMo547t != null || this.f636c != 0) {
            return objMo547t;
        }
        throw new C1640b("Unable to extract " + c1678e + " from temporal " + interfaceC1740n);
    }

    /* renamed from: a */
    public final Long m767a(InterfaceC1744r interfaceC1744r) {
        int i = this.f636c;
        InterfaceC1740n interfaceC1740n = this.f634a;
        if (i <= 0 || interfaceC1740n.mo543e(interfaceC1744r)) {
            return Long.valueOf(interfaceC1740n.mo542D(interfaceC1744r));
        }
        return null;
    }

    public final String toString() {
        return this.f634a.toString();
    }
}
