package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.d4 */
/* loaded from: classes2.dex */
public final class C2008d4 extends AbstractC2014e4 {
    @Override // p017j$.util.stream.AbstractC2032h4
    /* renamed from: b */
    public final Spliterator mo1031b(Spliterator spliterator) {
        return new C2008d4((Spliterator.OfInt) spliterator, this);
    }

    @Override // j$.util.Spliterator.OfInt
    public final boolean tryAdvance(IntConsumer intConsumer) {
        boolean zTest;
        if (this.f1259c && m1041a() && ((Spliterator.OfInt) this.f1257a).tryAdvance((IntConsumer) this)) {
            zTest = this.f1237e.test(this.f1238f);
            if (zTest) {
                intConsumer.accept(this.f1238f);
                return true;
            }
        } else {
            zTest = true;
        }
        this.f1259c = false;
        if (!zTest) {
            this.f1258b.set(true);
        }
        return false;
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public final Spliterator.OfInt trySplit() {
        if (this.f1258b.get()) {
            return null;
        }
        return (Spliterator.OfInt) super.trySplit();
    }
}
