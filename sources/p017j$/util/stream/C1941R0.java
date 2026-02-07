package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.R0 */
/* loaded from: classes2.dex */
public final class C1941R0 extends AbstractC1897I0 {
    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        if (j == 0 && j2 == this.f1025c) {
            return this;
        }
        long jCount = this.f1023a.count();
        if (j >= jCount) {
            return this.f1024b.mo956e(j - jCount, j2 - jCount, intFunction);
        }
        if (j2 > jCount) {
            return AbstractC2106w1.m1092Y(EnumC2001c3.REFERENCE, this.f1023a.mo956e(j, jCount, intFunction), this.f1024b.mo956e(0L, j2 - jCount, intFunction));
        }
        return this.f1023a.mo956e(j, j2, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        return new C2035i1(this);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final void mo957f(Object[] objArr, int i) {
        Objects.requireNonNull(objArr);
        InterfaceC1887G0 interfaceC1887G0 = this.f1023a;
        interfaceC1887G0.mo957f(objArr, i);
        this.f1024b.mo957f(objArr, i + ((int) interfaceC1887G0.count()));
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final Object[] mo958g(IntFunction intFunction) {
        long j = this.f1025c;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) j);
        mo957f(objArr, 0);
        return objArr;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final void forEach(Consumer consumer) {
        this.f1023a.forEach(consumer);
        this.f1024b.forEach(consumer);
    }

    public final String toString() {
        long j = this.f1025c;
        return j < 32 ? String.format("ConcNode[%s.%s]", this.f1023a, this.f1024b) : String.format("ConcNode[size=%d]", Long.valueOf(j));
    }
}
