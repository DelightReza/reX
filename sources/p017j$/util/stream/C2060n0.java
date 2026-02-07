package p017j$.util.stream;

import java.util.function.Supplier;

/* renamed from: j$.util.stream.n0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2060n0 implements Supplier {

    /* renamed from: a */
    public final /* synthetic */ int f1297a;

    /* renamed from: b */
    public final /* synthetic */ EnumC2090t0 f1298b;

    public /* synthetic */ C2060n0(EnumC2090t0 enumC2090t0, int i) {
        this.f1297a = i;
        this.f1298b = enumC2090t0;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.f1297a) {
            case 0:
                return new C2075q0(this.f1298b);
            default:
                return new C2080r0(this.f1298b);
        }
    }
}
