package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.V0 */
/* loaded from: classes2.dex */
public final class C1961V0 extends AbstractC1981Z0 implements InterfaceC1857A0 {
    @Override // p017j$.util.stream.AbstractC1981Z0, p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1083P(this, j, j2);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* synthetic */ void forEach(Consumer consumer) {
        AbstractC2106w1.m1080M(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1981Z0, p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final /* bridge */ /* synthetic */ InterfaceC1887G0 mo950a(int i) {
        mo950a(i);
        throw null;
    }

    @Override // p017j$.util.stream.AbstractC1981Z0, p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1882F0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final /* synthetic */ void mo957f(Object[] objArr, int i) {
        AbstractC2106w1.m1077J(this, (Double[]) objArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: b */
    public final /* bridge */ /* synthetic */ Object mo951b() {
        return AbstractC2106w1.f1367g;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return Spliterators.f789d;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* bridge */ /* synthetic */ InterfaceC1789b0 spliterator() {
        return Spliterators.f789d;
    }
}
