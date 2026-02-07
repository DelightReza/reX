package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.LocalDate;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1744r;

/* renamed from: j$.time.chrono.x */
/* loaded from: classes2.dex */
public final class C1674x implements InterfaceC1662l, Serializable {

    /* renamed from: d */
    public static final C1674x f522d;

    /* renamed from: e */
    public static final C1674x[] f523e;
    private static final long serialVersionUID = 1466499369062886794L;

    /* renamed from: a */
    public final transient int f524a;

    /* renamed from: b */
    public final transient LocalDate f525b;

    /* renamed from: c */
    public final transient String f526c;

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final /* synthetic */ long mo542D(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m518n(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final /* synthetic */ boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m520p(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m517m(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final /* synthetic */ Object mo547t(C1678e c1678e) {
        return AbstractC1636a.m524t(this, c1678e);
    }

    static {
        C1674x c1674x = new C1674x(-1, LocalDate.m566of(1868, 1, 1), "Meiji");
        f522d = c1674x;
        f523e = new C1674x[]{c1674x, new C1674x(0, LocalDate.m566of(1912, 7, 30), "Taisho"), new C1674x(1, LocalDate.m566of(1926, 12, 25), "Showa"), new C1674x(2, LocalDate.m566of(1989, 1, 8), "Heisei"), new C1674x(3, LocalDate.m566of(2019, 5, 1), "Reiwa")};
    }

    /* renamed from: l */
    public final C1674x m703l() {
        if (this == f523e[r0.length - 1]) {
            return null;
        }
        return m702m(this.f524a + 1);
    }

    public C1674x(int i, LocalDate localDate, String str) {
        this.f524a = i;
        this.f525b = localDate;
        this.f526c = str;
    }

    /* renamed from: m */
    public static C1674x m702m(int i) {
        int i2 = i + 1;
        if (i2 >= 0) {
            C1674x[] c1674xArr = f523e;
            if (i2 < c1674xArr.length) {
                return c1674xArr[i2];
            }
        }
        throw new C1640b("Invalid era: " + i);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(getValue(), EnumC1727a.ERA);
    }

    /* renamed from: h */
    public static C1674x m701h(LocalDate localDate) {
        if (localDate.m578X(C1673w.f518d)) {
            throw new C1640b("JapaneseDate before Meiji 6 are not supported");
        }
        for (int length = f523e.length - 1; length >= 0; length--) {
            C1674x c1674x = f523e[length];
            if (localDate.compareTo(c1674x.f525b) >= 0) {
                return c1674x;
            }
        }
        return null;
    }

    @Override // p017j$.time.chrono.InterfaceC1662l
    public final int getValue() {
        return this.f524a;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        EnumC1727a enumC1727a = EnumC1727a.ERA;
        if (interfaceC1744r != enumC1727a) {
            return AbstractC1745s.m816d(this, interfaceC1744r);
        }
        return C1671u.f516c.mo660q(enumC1727a);
    }

    public final String toString() {
        return this.f526c;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1645D((byte) 5, this);
    }
}
