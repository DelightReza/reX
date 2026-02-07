package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.x */
/* loaded from: classes2.dex */
public final class C2109x extends AbstractC1856A {
    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream
    public final InterfaceC1871D sequential() {
        this.f1162h.f1172r = false;
        return this;
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream
    public final InterfaceC1871D parallel() {
        this.f1162h.f1172r = true;
        return this;
    }

    @Override // p017j$.util.stream.AbstractC1856A, p017j$.util.stream.InterfaceC1871D
    public final void forEach(DoubleConsumer doubleConsumer) {
        if (this.f1162h.f1172r) {
            super.forEach(doubleConsumer);
        } else {
            AbstractC1856A.m915I0(m1012G0()).forEachRemaining(doubleConsumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC1856A, p017j$.util.stream.InterfaceC1871D
    public final void forEachOrdered(DoubleConsumer doubleConsumer) {
        if (this.f1162h.f1172r) {
            super.forEachOrdered(doubleConsumer);
        } else {
            AbstractC1856A.m915I0(m1012G0()).forEachRemaining(doubleConsumer);
        }
    }

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C2084s(this, EnumC1995b3.f1200r, 1);
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return spliterator();
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: D0 */
    public final boolean mo1007D0() {
        throw new UnsupportedOperationException();
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        throw new UnsupportedOperationException();
    }
}
