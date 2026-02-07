package p017j$.util.stream;

import java.util.function.Consumer;

/* renamed from: j$.util.stream.c2 */
/* loaded from: classes2.dex */
public final class C2000c2 extends AbstractC2018f2 {
    @Override // p017j$.util.stream.AbstractC2018f2, p017j$.util.stream.Stream
    public final void forEach(Consumer consumer) {
        if (!this.f1162h.f1172r) {
            m1012G0().forEachRemaining(consumer);
        } else {
            super.forEach(consumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC2018f2, p017j$.util.stream.Stream
    public final void forEachOrdered(Consumer consumer) {
        if (!this.f1162h.f1172r) {
            m1012G0().forEachRemaining(consumer);
        } else {
            super.forEachOrdered(consumer);
        }
    }

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C1988a2(this, EnumC1995b3.f1200r);
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
