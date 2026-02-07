package p017j$.time.temporal;

/* renamed from: j$.time.temporal.p */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1742p implements InterfaceC1741o {

    /* renamed from: a */
    public final /* synthetic */ int f688a;

    /* renamed from: b */
    public final /* synthetic */ int f689b;

    public /* synthetic */ C1742p(int i, int i2) {
        this.f688a = i2;
        this.f689b = i;
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        switch (this.f688a) {
            case 0:
                int iMo544i = interfaceC1739m.mo544i(EnumC1727a.DAY_OF_WEEK);
                int i = this.f689b;
                if (iMo544i == i) {
                    return interfaceC1739m;
                }
                return interfaceC1739m.mo557d(iMo544i - i >= 0 ? 7 - r0 : -r0, EnumC1728b.DAYS);
            default:
                int iMo544i2 = interfaceC1739m.mo544i(EnumC1727a.DAY_OF_WEEK);
                int i2 = this.f689b;
                if (iMo544i2 == i2) {
                    return interfaceC1739m;
                }
                return interfaceC1739m.mo559y(i2 - iMo544i2 >= 0 ? 7 - r1 : -r1, EnumC1728b.DAYS);
        }
    }
}
