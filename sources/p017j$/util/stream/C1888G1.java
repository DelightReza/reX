package p017j$.util.stream;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.G1 */
/* loaded from: classes2.dex */
public final class C1888G1 extends AbstractC1947S1 implements InterfaceC1942R1 {

    /* renamed from: b */
    public final /* synthetic */ Object f1007b;

    /* renamed from: c */
    public final /* synthetic */ BiFunction f1008c;

    /* renamed from: d */
    public final /* synthetic */ BinaryOperator f1009d;

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
        this.f1120a = this.f1009d.apply(this.f1120a, ((C1888G1) interfaceC1942R1).f1120a);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1120a = this.f1007b;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        this.f1120a = this.f1008c.apply(this.f1120a, obj);
    }

    public C1888G1(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        this.f1007b = obj;
        this.f1008c = biFunction;
        this.f1009d = binaryOperator;
    }
}
