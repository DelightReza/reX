package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.f3 */
/* loaded from: classes2.dex */
public final class C2019f3 extends AbstractC2031h3 implements IntConsumer {

    /* renamed from: c */
    public final int[] f1243c;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    public C2019f3(int i) {
        this.f1243c = new int[i];
    }

    @Override // p017j$.util.stream.AbstractC2031h3
    /* renamed from: a */
    public final void mo1039a(Object obj, long j) {
        IntConsumer intConsumer = (IntConsumer) obj;
        for (int i = 0; i < j; i++) {
            intConsumer.accept(this.f1243c[i]);
        }
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        int i2 = this.f1256b;
        this.f1256b = i2 + 1;
        this.f1243c[i2] = i;
    }
}
