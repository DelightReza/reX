package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1761A;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.C1 */
/* loaded from: classes2.dex */
public final class C1868C1 implements InterfaceC1942R1, InterfaceC2047k2 {

    /* renamed from: a */
    public boolean f973a;

    /* renamed from: b */
    public double f974b;

    /* renamed from: c */
    public final /* synthetic */ DoubleBinaryOperator f975c;

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

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo934v((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        AbstractC2106w1.m1068A(this, d);
    }

    public C1868C1(DoubleBinaryOperator doubleBinaryOperator) {
        this.f975c = doubleBinaryOperator;
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        C1868C1 c1868c1 = (C1868C1) interfaceC1942R1;
        if (c1868c1.f973a) {
            return;
        }
        accept(c1868c1.f974b);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f973a = true;
        this.f974b = 0.0d;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        if (this.f973a) {
            this.f973a = false;
            this.f974b = d;
        } else {
            this.f974b = this.f975c.applyAsDouble(this.f974b, d);
        }
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f973a ? C1761A.f757c : new C1761A(this.f974b);
    }
}
