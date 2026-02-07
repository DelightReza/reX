package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.N0 */
/* loaded from: classes2.dex */
public final class C1922N0 extends AbstractC1937Q0 implements InterfaceC1857A0 {
    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1083P(this, j, j2);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* synthetic */ void forEach(Consumer consumer) {
        AbstractC2106w1.m1080M(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final /* synthetic */ void mo957f(Object[] objArr, int i) {
        AbstractC2106w1.m1077J(this, (Double[]) objArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    public final Object newArray(int i) {
        return new double[i];
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        return new C2011e1(this);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final InterfaceC1789b0 spliterator() {
        return new C2011e1(this);
    }
}
