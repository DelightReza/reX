package p017j$.util.stream;

import java.util.function.IntFunction;

/* renamed from: j$.util.stream.s1 */
/* loaded from: classes2.dex */
public final class C2086s1 extends C1983Z2 implements InterfaceC1887G0, InterfaceC2115y0 {
    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1887G0 build() {
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1086S(this, j, j2, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void end() {
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: i */
    public final /* synthetic */ int mo959i() {
        return 0;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1887G0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final void mo957f(Object[] objArr, int i) {
        long j = i;
        long jCount = count() + j;
        if (jCount > objArr.length || jCount < j) {
            throw new IndexOutOfBoundsException("does not fit");
        }
        if (this.f1214c == 0) {
            System.arraycopy(this.f1155e, 0, objArr, i, this.f1213b);
            return;
        }
        for (int i2 = 0; i2 < this.f1214c; i2++) {
            Object[] objArr2 = this.f1156f[i2];
            System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
            i += this.f1156f[i2].length;
        }
        int i3 = this.f1213b;
        if (i3 > 0) {
            System.arraycopy(this.f1155e, 0, objArr, i, i3);
        }
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final Object[] mo958g(IntFunction intFunction) {
        long jCount = count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) jCount);
        mo957f(objArr, 0);
        return objArr;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        clear();
        m1010j(j);
    }
}
