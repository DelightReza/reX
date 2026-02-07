package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.K1 */
/* loaded from: classes2.dex */
public final class C1908K1 extends AbstractC1947S1 implements InterfaceC1942R1 {

    /* renamed from: b */
    public final /* synthetic */ Supplier f1049b;

    /* renamed from: c */
    public final /* synthetic */ BiConsumer f1050c;

    /* renamed from: d */
    public final /* synthetic */ BiConsumer f1051d;

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

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        this.f1051d.accept(this.f1120a, ((C1908K1) interfaceC1942R1).f1120a);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1120a = this.f1049b.get();
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        this.f1050c.accept(this.f1120a, obj);
    }

    public C1908K1(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        this.f1049b = supplier;
        this.f1050c = biConsumer;
        this.f1051d = biConsumer2;
    }
}
