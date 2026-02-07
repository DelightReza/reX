package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.c4 */
/* loaded from: classes2.dex */
public final class C2002c4 extends AbstractC2014e4 {
    @Override // p017j$.util.stream.AbstractC2032h4
    /* renamed from: b */
    public final Spliterator mo1031b(Spliterator spliterator) {
        return new C2002c4((Spliterator.OfInt) spliterator, this);
    }

    @Override // j$.util.Spliterator.OfInt
    public final boolean tryAdvance(IntConsumer intConsumer) {
        boolean zTryAdvance;
        boolean z = this.f1259c;
        Spliterator spliterator = this.f1257a;
        if (z) {
            boolean z2 = false;
            this.f1259c = false;
            while (true) {
                zTryAdvance = ((Spliterator.OfInt) spliterator).tryAdvance((IntConsumer) this);
                if (!zTryAdvance || !m1041a() || !this.f1237e.test(this.f1238f)) {
                    break;
                }
                z2 = true;
            }
            if (zTryAdvance) {
                if (z2) {
                    this.f1258b.set(true);
                }
                intConsumer.accept(this.f1238f);
            }
            return zTryAdvance;
        }
        return ((Spliterator.OfInt) spliterator).tryAdvance(intConsumer);
    }
}
