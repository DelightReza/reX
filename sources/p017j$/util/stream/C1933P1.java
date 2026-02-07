package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.P1 */
/* loaded from: classes2.dex */
public final class C1933P1 implements InterfaceC1942R1, InterfaceC2057m2 {

    /* renamed from: a */
    public long f1088a;

    /* renamed from: b */
    public final /* synthetic */ long f1089b;

    /* renamed from: c */
    public final /* synthetic */ LongBinaryOperator f1090c;

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

    public C1933P1(long j, LongBinaryOperator longBinaryOperator) {
        this.f1089b = j;
        this.f1090c = longBinaryOperator;
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        accept(((C1933P1) interfaceC1942R1).f1088a);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1088a = this.f1089b;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        this.f1088a = this.f1090c.applyAsLong(this.f1088a, j);
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.f1088a);
    }
}
