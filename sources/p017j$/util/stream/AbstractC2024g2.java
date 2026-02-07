package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Objects;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.g2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2024g2 implements InterfaceC2047k2 {

    /* renamed from: a */
    public final InterfaceC2062n2 f1249a;

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
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        mo934v((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        AbstractC2106w1.m1068A(this, d);
    }

    public AbstractC2024g2(InterfaceC2062n2 interfaceC2062n2) {
        this.f1249a = (InterfaceC2062n2) Objects.requireNonNull(interfaceC2062n2);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        this.f1249a.mo931h(j);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public void end() {
        this.f1249a.end();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public boolean mo932m() {
        return this.f1249a.mo932m();
    }
}
