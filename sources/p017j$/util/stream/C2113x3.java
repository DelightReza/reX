package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1779T;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.x3 */
/* loaded from: classes2.dex */
public final class C2113x3 extends AbstractC1860A3 implements InterfaceC1779T, DoubleConsumer {

    /* renamed from: f */
    public double f1378f;

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m527w(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1875D3
    /* renamed from: b */
    public final Spliterator mo940b(Spliterator spliterator) {
        return new C2113x3((InterfaceC1779T) spliterator, this);
    }

    @Override // p017j$.util.stream.AbstractC1860A3
    /* renamed from: d */
    public final void mo935d(Object obj) {
        ((DoubleConsumer) obj).accept(this.f1378f);
    }

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f1378f = d;
    }

    @Override // p017j$.util.stream.AbstractC1860A3
    /* renamed from: e */
    public final AbstractC2031h3 mo936e(int i) {
        return new C2013e3(i);
    }
}
