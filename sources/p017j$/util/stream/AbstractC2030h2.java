package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.util.Objects;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.h2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2030h2 implements InterfaceC2052l2 {

    /* renamed from: a */
    public final InterfaceC2062n2 f1255a;

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
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        mo975l((Integer) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        AbstractC2106w1.m1070C(this, num);
    }

    public AbstractC2030h2(InterfaceC2062n2 interfaceC2062n2) {
        this.f1255a = (InterfaceC2062n2) Objects.requireNonNull(interfaceC2062n2);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        this.f1255a.mo931h(j);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public void end() {
        this.f1255a.end();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public boolean mo932m() {
        return this.f1255a.mo932m();
    }
}
