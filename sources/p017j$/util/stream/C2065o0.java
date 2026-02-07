package p017j$.util.stream;

import java.util.function.Predicate;

/* renamed from: j$.util.stream.o0 */
/* loaded from: classes2.dex */
public final class C2065o0 extends AbstractC2085s0 {

    /* renamed from: c */
    public final /* synthetic */ EnumC2090t0 f1302c;

    /* renamed from: d */
    public final /* synthetic */ Predicate f1303d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2065o0(EnumC2090t0 enumC2090t0, Predicate predicate) {
        super(enumC2090t0);
        this.f1302c = enumC2090t0;
        this.f1303d = predicate;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        if (this.f1335a) {
            return;
        }
        boolean zTest = this.f1303d.test(obj);
        EnumC2090t0 enumC2090t0 = this.f1302c;
        if (zTest == enumC2090t0.f1342a) {
            this.f1335a = true;
            this.f1336b = enumC2090t0.f1343b;
        }
    }
}
