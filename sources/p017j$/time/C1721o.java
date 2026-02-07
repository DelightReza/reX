package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.util.Objects;

/* renamed from: j$.time.o */
/* loaded from: classes2.dex */
public final class C1721o implements InterfaceC1739m, InterfaceC1741o, Comparable, Serializable {

    /* renamed from: c */
    public static final /* synthetic */ int f656c = 0;
    private static final long serialVersionUID = 7264499704384272492L;

    /* renamed from: a */
    public final C1715i f657a;

    /* renamed from: b */
    public final ZoneOffset f658b;

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        C1721o c1721o = (C1721o) obj;
        if (this.f658b.equals(c1721o.f658b)) {
            return this.f657a.compareTo(c1721o.f657a);
        }
        int iCompare = Long.compare(this.f657a.m781c0() - (this.f658b.getTotalSeconds() * 1000000000), c1721o.f657a.m781c0() - (c1721o.f658b.getTotalSeconds() * 1000000000));
        return iCompare == 0 ? this.f657a.compareTo(c1721o.f657a) : iCompare;
    }

    static {
        C1715i c1715i = C1715i.f640e;
        ZoneOffset zoneOffset = ZoneOffset.f459g;
        c1715i.getClass();
        new C1721o(c1715i, zoneOffset);
        C1715i c1715i2 = C1715i.f641f;
        ZoneOffset zoneOffset2 = ZoneOffset.f458f;
        c1715i2.getClass();
        new C1721o(c1715i2, zoneOffset2);
    }

    public C1721o(C1715i c1715i, ZoneOffset zoneOffset) {
        this.f657a = (C1715i) Objects.requireNonNull(c1715i, "time");
        this.f658b = (ZoneOffset) Objects.requireNonNull(zoneOffset, "offset");
    }

    /* renamed from: R */
    public final C1721o m791R(C1715i c1715i, ZoneOffset zoneOffset) {
        return (this.f657a == c1715i && this.f658b.equals(zoneOffset)) ? this : new C1721o(c1715i, zoneOffset);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? ((EnumC1727a) interfaceC1744r).m801Q() || interfaceC1744r == EnumC1727a.OFFSET_SECONDS : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r != EnumC1727a.OFFSET_SECONDS) {
                C1715i c1715i = this.f657a;
                c1715i.getClass();
                return AbstractC1745s.m816d(c1715i, interfaceC1744r);
            }
            return ((EnumC1727a) interfaceC1744r).f671b;
        }
        return interfaceC1744r.mo803j(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r == EnumC1727a.OFFSET_SECONDS) {
                return this.f658b.getTotalSeconds();
            }
            return this.f657a.mo542D(interfaceC1744r);
        }
        return interfaceC1744r.mo806t(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        localDate.getClass();
        return (C1721o) AbstractC1636a.m505a(localDate, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    public final InterfaceC1739m mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r == EnumC1727a.OFFSET_SECONDS) {
                EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
                return m791R(this.f657a, ZoneOffset.m626W(enumC1727a.f671b.m820a(j, enumC1727a)));
            }
            return m791R(this.f657a.mo556c(j, interfaceC1744r), this.f658b);
        }
        return (C1721o) interfaceC1744r.mo807y(this, j);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: Q, reason: merged with bridge method [inline-methods] */
    public final C1721o mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (interfaceC1746t instanceof EnumC1728b) {
            return m791R(this.f657a.mo557d(j, interfaceC1746t), this.f658b);
        }
        return (C1721o) interfaceC1746t.mo808i(this, j);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f693d || c1678e == AbstractC1745s.f694e) {
            return this.f658b;
        }
        if (((c1678e == AbstractC1745s.f690a) || (c1678e == AbstractC1745s.f691b)) || c1678e == AbstractC1745s.f695f) {
            return null;
        }
        if (c1678e == AbstractC1745s.f696g) {
            return this.f657a;
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.NANOS;
        }
        return c1678e.m704g(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(this.f657a.m781c0(), EnumC1727a.NANO_OF_DAY).mo556c(this.f658b.getTotalSeconds(), EnumC1727a.OFFSET_SECONDS);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1721o) {
            C1721o c1721o = (C1721o) obj;
            if (this.f657a.equals(c1721o.f657a) && this.f658b.equals(c1721o.f658b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f657a.hashCode() ^ this.f658b.f460b;
    }

    public final String toString() {
        return this.f657a.toString() + this.f658b.f461c;
    }

    private Object writeReplace() {
        return new C1722p((byte) 9, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
