package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.O1 */
/* loaded from: classes2.dex */
public final class C1928O1 extends AbstractC1947S1 implements InterfaceC1942R1, InterfaceC2052l2 {

    /* renamed from: b */
    public final /* synthetic */ Supplier f1078b;

    /* renamed from: c */
    public final /* synthetic */ ObjIntConsumer f1079c;

    /* renamed from: d */
    public final /* synthetic */ C2069p f1080d;

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

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
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

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        this.f1120a = this.f1080d.apply(this.f1120a, ((C1928O1) interfaceC1942R1).f1120a);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1120a = this.f1078b.get();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        this.f1079c.accept(this.f1120a, i);
    }

    public C1928O1(Supplier supplier, ObjIntConsumer objIntConsumer, C2069p c2069p) {
        this.f1078b = supplier;
        this.f1079c = objIntConsumer;
        this.f1080d = c2069p;
    }
}
