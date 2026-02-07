package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.m1 */
/* loaded from: classes2.dex */
public final class C2056m1 extends C1968W2 implements InterfaceC1877E0, InterfaceC2110x0 {
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

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo988u((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2110x0, p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1877E0 build() {
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1887G0 build() {
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1085R(this, j, j2);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void end() {
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final /* synthetic */ Object[] mo958g(IntFunction intFunction) {
        return AbstractC2106w1.m1076I(this, intFunction);
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

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        AbstractC2106w1.m1072E(this, l);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final /* bridge */ /* synthetic */ InterfaceC1887G0 mo950a(int i) {
        mo950a(i);
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC1882F0, p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1882F0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final /* synthetic */ void mo957f(Object[] objArr, int i) {
        AbstractC2106w1.m1079L(this, (Long[]) objArr, i);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, p017j$.util.stream.InterfaceC1882F0
    /* renamed from: c */
    public final void mo952c(int i, Object obj) {
        super.mo952c(i, (long[]) obj);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, p017j$.util.stream.InterfaceC1882F0
    /* renamed from: d */
    public final void mo953d(Object obj) {
        super.mo953d((LongConsumer) obj);
    }

    @Override // p017j$.util.stream.C1968W2, p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
    public final Spliterator spliterator() {
        return super.spliterator();
    }

    @Override // p017j$.util.stream.C1968W2, p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
    public final InterfaceC1789b0 spliterator() {
        return super.spliterator();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        clear();
        m1005p(j);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, p017j$.util.stream.InterfaceC1882F0
    /* renamed from: b */
    public final Object mo951b() {
        return (long[]) super.mo951b();
    }
}
