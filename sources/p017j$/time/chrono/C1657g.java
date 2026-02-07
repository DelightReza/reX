package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1743q;
import p017j$.util.Objects;

/* renamed from: j$.time.chrono.g */
/* loaded from: classes2.dex */
public final class C1657g implements InterfaceC1743q, Serializable {

    /* renamed from: e */
    public static final /* synthetic */ int f484e = 0;
    private static final long serialVersionUID = 57387258289L;

    /* renamed from: a */
    public final InterfaceC1661k f485a;

    /* renamed from: b */
    public final int f486b;

    /* renamed from: c */
    public final int f487c;

    /* renamed from: d */
    public final int f488d;

    static {
        AbstractC1636a.m495N(new Object[]{EnumC1728b.YEARS, EnumC1728b.MONTHS, EnumC1728b.DAYS});
    }

    public C1657g(InterfaceC1661k interfaceC1661k, int i, int i2, int i3) {
        Objects.requireNonNull(interfaceC1661k, "chrono");
        this.f485a = interfaceC1661k;
        this.f486b = i;
        this.f487c = i2;
        this.f488d = i3;
    }

    public final String toString() {
        if (this.f486b == 0 && this.f487c == 0 && this.f488d == 0) {
            return this.f485a.toString() + " P0D";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.f485a.toString());
        sb.append(" P");
        int i = this.f486b;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.f487c;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.f488d;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }

    @Override // p017j$.time.temporal.InterfaceC1743q
    /* renamed from: i */
    public final InterfaceC1739m mo550i(InterfaceC1739m interfaceC1739m) {
        Objects.requireNonNull(interfaceC1739m, "temporal");
        InterfaceC1661k interfaceC1661k = (InterfaceC1661k) interfaceC1739m.mo547t(AbstractC1745s.f691b);
        if (interfaceC1661k == null || this.f485a.equals(interfaceC1661k)) {
            if (this.f487c != 0) {
                C1748v c1748vMo660q = this.f485a.mo660q(EnumC1727a.MONTH_OF_YEAR);
                long j = (c1748vMo660q.f697a == c1748vMo660q.f698b && c1748vMo660q.f699c == c1748vMo660q.f700d && c1748vMo660q.m823d()) ? (c1748vMo660q.f700d - c1748vMo660q.f697a) + 1 : -1L;
                if (j > 0) {
                    interfaceC1739m = interfaceC1739m.mo557d((this.f486b * j) + this.f487c, EnumC1728b.MONTHS);
                } else {
                    int i = this.f486b;
                    if (i != 0) {
                        interfaceC1739m = interfaceC1739m.mo557d(i, EnumC1728b.YEARS);
                    }
                    interfaceC1739m = interfaceC1739m.mo557d(this.f487c, EnumC1728b.MONTHS);
                }
            } else {
                int i2 = this.f486b;
                if (i2 != 0) {
                    interfaceC1739m = interfaceC1739m.mo557d(i2, EnumC1728b.YEARS);
                }
            }
            int i3 = this.f488d;
            return i3 != 0 ? interfaceC1739m.mo557d(i3, EnumC1728b.DAYS) : interfaceC1739m;
        }
        throw new C1640b("Chronology mismatch, expected: " + this.f485a.getId() + ", actual: " + interfaceC1661k.getId());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1657g) {
            C1657g c1657g = (C1657g) obj;
            if (this.f486b == c1657g.f486b && this.f487c == c1657g.f487c && this.f488d == c1657g.f488d && this.f485a.equals(c1657g.f485a)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (Integer.rotateLeft(this.f488d, 16) + (Integer.rotateLeft(this.f487c, 8) + this.f486b)) ^ this.f485a.hashCode();
    }

    public Object writeReplace() {
        return new C1645D((byte) 9, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
