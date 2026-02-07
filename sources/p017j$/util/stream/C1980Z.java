package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.Z */
/* loaded from: classes2.dex */
public final class C1980Z extends AbstractC1998c0 {
    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream
    public final IntStream sequential() {
        this.f1162h.f1172r = false;
        return this;
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream
    public final IntStream parallel() {
        this.f1162h.f1172r = true;
        return this;
    }

    @Override // p017j$.util.stream.AbstractC1998c0, p017j$.util.stream.IntStream
    public final void forEach(IntConsumer intConsumer) {
        if (this.f1162h.f1172r) {
            super.forEach(intConsumer);
        } else {
            AbstractC1998c0.m1032I0(m1012G0()).forEachRemaining(intConsumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC1998c0, p017j$.util.stream.IntStream
    public final void forEachOrdered(IntConsumer intConsumer) {
        if (this.f1162h.f1172r) {
            super.forEachOrdered(intConsumer);
        } else {
            AbstractC1998c0.m1032I0(m1012G0()).forEachRemaining(intConsumer);
        }
    }

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C2089t(this, EnumC1995b3.f1200r, 1);
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
