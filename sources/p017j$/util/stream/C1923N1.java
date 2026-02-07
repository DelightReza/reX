package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import p017j$.util.OptionalInt;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.N1 */
/* loaded from: classes2.dex */
public final class C1923N1 implements InterfaceC1942R1, InterfaceC2052l2 {

    /* renamed from: a */
    public boolean f1070a;

    /* renamed from: b */
    public int f1071b;

    /* renamed from: c */
    public final /* synthetic */ IntBinaryOperator f1072c;

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

    public C1923N1(IntBinaryOperator intBinaryOperator) {
        this.f1072c = intBinaryOperator;
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        C1923N1 c1923n1 = (C1923N1) interfaceC1942R1;
        if (c1923n1.f1070a) {
            return;
        }
        accept(c1923n1.f1071b);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1070a = true;
        this.f1071b = 0;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        if (this.f1070a) {
            this.f1070a = false;
            this.f1071b = i;
        } else {
            this.f1071b = this.f1072c.applyAsInt(this.f1071b, i);
        }
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f1070a ? OptionalInt.f779c : new OptionalInt(this.f1071b);
    }
}
