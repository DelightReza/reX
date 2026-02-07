package p017j$.util.stream;

import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.i0 */
/* loaded from: classes2.dex */
public abstract class AbstractC2034i0 extends AbstractC2045k0 {
    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: D0 */
    public final boolean mo1007D0() {
        return true;
    }

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

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C2094u(this, EnumC1995b3.f1200r, 4);
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return spliterator();
    }
}
