package p017j$.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import p017j$.util.Optional;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.H1 */
/* loaded from: classes2.dex */
public final class C1893H1 implements InterfaceC1942R1 {

    /* renamed from: a */
    public boolean f1014a;

    /* renamed from: b */
    public Object f1015b;

    /* renamed from: c */
    public final /* synthetic */ BinaryOperator f1016c;

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

    public C1893H1(BinaryOperator binaryOperator) {
        this.f1016c = binaryOperator;
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        C1893H1 c1893h1 = (C1893H1) interfaceC1942R1;
        if (c1893h1.f1014a) {
            return;
        }
        m971v(c1893h1.f1015b);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1014a = true;
        this.f1015b = null;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        if (this.f1014a) {
            this.f1014a = false;
            this.f1015b = obj;
        } else {
            this.f1015b = this.f1016c.apply(this.f1015b, obj);
        }
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f1014a ? Optional.empty() : Optional.m856of(this.f1015b);
    }
}
