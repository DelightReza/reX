package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.time.C1726t;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.F3 */
/* loaded from: classes2.dex */
public final class C1885F3 extends AbstractC2007d3 {
    @Override // p017j$.util.stream.AbstractC2007d3
    /* renamed from: e */
    public final AbstractC2007d3 mo955e(Spliterator spliterator) {
        return new C1885F3(this.f1227b, spliterator, this.f1226a);
    }

    @Override // p017j$.util.stream.AbstractC2007d3
    /* renamed from: d */
    public final void mo954d() {
        C1983Z2 c1983z2 = new C1983Z2();
        this.f1233h = c1983z2;
        Objects.requireNonNull(c1983z2);
        this.f1230e = this.f1227b.mo1018u0(new C1880E3(c1983z2, 0));
        this.f1231f = new C1726t(17, this);
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        Object obj;
        Objects.requireNonNull(consumer);
        boolean zM1035a = m1035a();
        if (!zM1035a) {
            return zM1035a;
        }
        C1983Z2 c1983z2 = (C1983Z2) this.f1233h;
        long j = this.f1232g;
        if (c1983z2.f1214c != 0) {
            if (j >= c1983z2.count()) {
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            for (int i = 0; i <= c1983z2.f1214c; i++) {
                long j2 = c1983z2.f1215d[i];
                Object[] objArr = c1983z2.f1156f[i];
                if (j < objArr.length + j2) {
                    obj = objArr[(int) (j - j2)];
                }
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        if (j < c1983z2.f1213b) {
            obj = c1983z2.f1155e[(int) j];
        } else {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        consumer.m971v(obj);
        return zM1035a;
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        if (this.f1233h == null && !this.f1234i) {
            Objects.requireNonNull(consumer);
            m1037c();
            Objects.requireNonNull(consumer);
            C1880E3 c1880e3 = new C1880E3(consumer, 1);
            this.f1227b.mo1017t0(this.f1229d, c1880e3);
            this.f1234i = true;
            return;
        }
        while (tryAdvance(consumer)) {
        }
    }
}
