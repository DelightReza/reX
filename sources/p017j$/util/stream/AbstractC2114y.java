package p017j$.util.stream;

import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.y */
/* loaded from: classes2.dex */
public abstract class AbstractC2114y extends AbstractC1856A {
    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: D0 */
    public final boolean mo1007D0() {
        return true;
    }

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

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C2084s(this, EnumC1995b3.f1200r, 1);
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* bridge */ /* synthetic */ Spliterator spliterator() {
        return spliterator();
    }
}
