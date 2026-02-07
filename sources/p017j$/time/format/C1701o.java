package p017j$.time.format;

import java.util.ArrayList;
import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.LocalDate;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.Objects;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.time.format.o */
/* loaded from: classes2.dex */
public final class C1701o extends C1695i {

    /* renamed from: h */
    public static final LocalDate f598h = LocalDate.m566of(2000, 1, 1);

    /* renamed from: g */
    public final InterfaceC1652b f599g;

    @Override // p017j$.time.format.C1695i
    /* renamed from: b */
    public final boolean mo723b(C1708v c1708v) {
        if (c1708v.f627c) {
            return super.mo723b(c1708v);
        }
        return false;
    }

    public C1701o(InterfaceC1744r interfaceC1744r, int i, int i2, InterfaceC1652b interfaceC1652b, int i3) {
        super(interfaceC1744r, i, i2, EnumC1685F.NOT_NEGATIVE, i3);
        this.f599g = interfaceC1652b;
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: a */
    public final long mo728a(C1711y c1711y, long j) {
        long jAbs = Math.abs(j);
        InterfaceC1652b interfaceC1652b = this.f599g;
        long jMo544i = interfaceC1652b != null ? AbstractC1636a.m492K(c1711y.f634a).mo651A(interfaceC1652b).mo544i(this.f574a) : 0;
        long[] jArr = C1695i.f573f;
        if (j >= jMo544i) {
            long j2 = jArr[this.f575b];
            if (j < jMo544i + j2) {
                return jAbs % j2;
            }
        }
        return jAbs % jArr[this.f576c];
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: c */
    public final int mo729c(C1708v c1708v, long j, int i, int i2) {
        final C1701o c1701o;
        final C1708v c1708v2;
        final long j2;
        final int i3;
        final int i4;
        int iMo544i;
        long j3;
        InterfaceC1652b interfaceC1652b = this.f599g;
        if (interfaceC1652b != null) {
            InterfaceC1661k interfaceC1661k = c1708v.m762c().f542c;
            if (interfaceC1661k == null && (interfaceC1661k = c1708v.f625a.f553e) == null) {
                interfaceC1661k = C1668r.f512c;
            }
            iMo544i = interfaceC1661k.mo651A(interfaceC1652b).mo544i(this.f574a);
            c1701o = this;
            c1708v2 = c1708v;
            j2 = j;
            i3 = i;
            i4 = i2;
            Consumer consumer = new Consumer() { // from class: j$.time.format.n
                public final /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
                }

                @Override // java.util.function.Consumer
                /* renamed from: accept */
                public final void m971v(Object obj) {
                    this.f593a.mo729c(c1708v2, j2, i3, i4);
                }
            };
            if (c1708v2.f629e == null) {
                c1708v2.f629e = new ArrayList();
            }
            c1708v2.f629e.add(consumer);
        } else {
            c1701o = this;
            c1708v2 = c1708v;
            j2 = j;
            i3 = i;
            i4 = i2;
            iMo544i = 0;
        }
        int i5 = i4 - i3;
        int i6 = c1701o.f575b;
        if (i5 != i6 || j2 < 0) {
            j3 = j2;
        } else {
            long j4 = C1695i.f573f[i6];
            long j5 = iMo544i;
            long j6 = j5 - (j5 % j4);
            long j7 = iMo544i > 0 ? j6 + j2 : j6 - j2;
            j3 = j7 < j5 ? j4 + j7 : j7;
        }
        return c1708v2.m765f(c1701o.f574a, j3, i3, i4);
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: d */
    public final C1695i mo724d() {
        if (this.f578e == -1) {
            return this;
        }
        return new C1701o(this.f574a, this.f575b, this.f576c, this.f599g, -1);
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: e */
    public final C1695i mo725e(int i) {
        return new C1701o(this.f574a, this.f575b, this.f576c, this.f599g, this.f578e + i);
    }

    @Override // p017j$.time.format.C1695i
    public final String toString() {
        return "ReducedValue(" + this.f574a + "," + this.f575b + "," + this.f576c + "," + Objects.requireNonNullElse(this.f599g, 0) + ")";
    }
}
