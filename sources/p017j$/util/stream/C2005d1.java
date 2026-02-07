package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.d1 */
/* loaded from: classes2.dex */
public final class C2005d1 extends C1958U2 implements InterfaceC1867C0, InterfaceC2105w0 {
    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo975l((Integer) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2105w0, p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1867C0 build() {
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1887G0 build() {
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1084Q(this, j, j2);
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

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        AbstractC2106w1.m1070C(this, num);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
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
        AbstractC2106w1.m1078K(this, (Integer[]) objArr, i);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, p017j$.util.stream.InterfaceC1882F0
    /* renamed from: c */
    public final void mo952c(int i, Object obj) {
        super.mo952c(i, (int[]) obj);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, p017j$.util.stream.InterfaceC1882F0
    /* renamed from: d */
    public final void mo953d(Object obj) {
        super.mo953d((IntConsumer) obj);
    }

    @Override // p017j$.util.stream.C1958U2, p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
    public final Spliterator spliterator() {
        return super.spliterator();
    }

    @Override // p017j$.util.stream.C1958U2, p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
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
        return (int[]) super.mo951b();
    }
}
