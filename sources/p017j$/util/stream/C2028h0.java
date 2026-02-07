package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.h0 */
/* loaded from: classes2.dex */
public final class C2028h0 extends AbstractC2045k0 {
    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream
    public final LongStream sequential() {
        this.f1162h.f1172r = false;
        return this;
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream
    public final LongStream parallel() {
        this.f1162h.f1172r = true;
        return this;
    }

    @Override // p017j$.util.stream.AbstractC2045k0, p017j$.util.stream.LongStream
    public final void forEach(LongConsumer longConsumer) {
        if (this.f1162h.f1172r) {
            super.forEach(longConsumer);
        } else {
            AbstractC2045k0.m1046I0(m1012G0()).forEachRemaining(longConsumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC2045k0, p017j$.util.stream.LongStream
    public final void forEachOrdered(LongConsumer longConsumer) {
        if (this.f1162h.f1172r) {
            super.forEachOrdered(longConsumer);
        } else {
            AbstractC2045k0.m1046I0(m1012G0()).forEachRemaining(longConsumer);
        }
    }

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C2094u(this, EnumC1995b3.f1200r, 4);
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
