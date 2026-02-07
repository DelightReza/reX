package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Objects;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.i2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2036i2 implements InterfaceC2057m2 {

    /* renamed from: a */
    public final InterfaceC2062n2 f1262a;

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
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        mo988u((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        AbstractC2106w1.m1072E(this, l);
    }

    public AbstractC2036i2(InterfaceC2062n2 interfaceC2062n2) {
        this.f1262a = (InterfaceC2062n2) Objects.requireNonNull(interfaceC2062n2);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        this.f1262a.mo931h(j);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public void end() {
        this.f1262a.end();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public boolean mo932m() {
        return this.f1262a.mo932m();
    }
}
