package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.n1 */
/* loaded from: classes2.dex */
public final class C2061n1 extends AbstractC2081r1 implements InterfaceC2047k2 {

    /* renamed from: h */
    public final double[] f1299h;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo934v((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        AbstractC2106w1.m1068A(this, d);
    }

    public C2061n1(Spliterator spliterator, AbstractC2106w1 abstractC2106w1, double[] dArr) {
        super(spliterator, abstractC2106w1, dArr.length);
        this.f1299h = dArr;
    }

    public C2061n1(C2061n1 c2061n1, Spliterator spliterator, long j, long j2) {
        super(c2061n1, spliterator, j, j2, c2061n1.f1299h.length);
        this.f1299h = c2061n1.f1299h;
    }

    @Override // p017j$.util.stream.AbstractC2081r1
    /* renamed from: a */
    public final AbstractC2081r1 mo1053a(Spliterator spliterator, long j, long j2) {
        return new C2061n1(this, spliterator, j, j2);
    }

    @Override // p017j$.util.stream.AbstractC2081r1, p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        int i = this.f1330f;
        if (i >= this.f1331g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f1330f));
        }
        double[] dArr = this.f1299h;
        this.f1330f = i + 1;
        dArr[i] = d;
    }
}
