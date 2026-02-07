package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1762B;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.Q1 */
/* loaded from: classes2.dex */
public final class C1938Q1 implements InterfaceC1942R1, InterfaceC2057m2 {

    /* renamed from: a */
    public boolean f1096a;

    /* renamed from: b */
    public long f1097b;

    /* renamed from: c */
    public final /* synthetic */ LongBinaryOperator f1098c;

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

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
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

    public C1938Q1(LongBinaryOperator longBinaryOperator) {
        this.f1098c = longBinaryOperator;
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        C1938Q1 c1938q1 = (C1938Q1) interfaceC1942R1;
        if (c1938q1.f1096a) {
            return;
        }
        accept(c1938q1.f1097b);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1096a = true;
        this.f1097b = 0L;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        if (this.f1096a) {
            this.f1096a = false;
            this.f1097b = j;
        } else {
            this.f1097b = this.f1098c.applyAsLong(this.f1097b, j);
        }
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f1096a ? C1762B.f760c : new C1762B(this.f1097b);
    }
}
