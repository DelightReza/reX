package p017j$.util.stream;

/* renamed from: j$.util.stream.d2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2006d2 extends AbstractC2018f2 {
    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: D0 */
    public final boolean mo1007D0() {
        return true;
    }

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream unordered() {
        return !EnumC1995b3.ORDERED.m1030n(this.f1167m) ? this : new C1988a2(this, EnumC1995b3.f1200r);
    }
}
