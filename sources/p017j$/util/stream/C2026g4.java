package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.Predicate;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.g4 */
/* loaded from: classes2.dex */
public final class C2026g4 extends AbstractC2032h4 implements Consumer {

    /* renamed from: e */
    public final Predicate f1251e;

    /* renamed from: f */
    public Object f1252f;

    /* renamed from: g */
    public final /* synthetic */ int f1253g;

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2026g4(Spliterator spliterator, Predicate predicate, int i) {
        super(spliterator);
        this.f1253g = i;
        this.f1251e = predicate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2026g4(Spliterator spliterator, C2026g4 c2026g4, int i) {
        super(spliterator, c2026g4);
        this.f1253g = i;
        this.f1251e = c2026g4.f1251e;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f1260d = (this.f1260d + 1) & 63;
        this.f1252f = obj;
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        boolean zTryAdvance;
        boolean zTest;
        switch (this.f1253g) {
            case 0:
                boolean z = this.f1259c;
                Spliterator spliterator = this.f1257a;
                if (z) {
                    boolean z2 = false;
                    this.f1259c = false;
                    while (true) {
                        zTryAdvance = spliterator.tryAdvance(this);
                        if (zTryAdvance && m1041a() && this.f1251e.test(this.f1252f)) {
                            z2 = true;
                        }
                    }
                    if (!zTryAdvance) {
                        return zTryAdvance;
                    }
                    if (z2) {
                        this.f1258b.set(true);
                    }
                    consumer.accept(this.f1252f);
                    return zTryAdvance;
                }
                return spliterator.tryAdvance(consumer);
            default:
                if (this.f1259c && m1041a() && this.f1257a.tryAdvance(this)) {
                    zTest = this.f1251e.test(this.f1252f);
                    if (zTest) {
                        consumer.accept(this.f1252f);
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
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public Spliterator trySplit() {
        switch (this.f1253g) {
            case 1:
                if (!this.f1258b.get()) {
                    break;
                }
                break;
        }
        return super.trySplit();
    }

    @Override // p017j$.util.stream.AbstractC2032h4
    /* renamed from: b */
    public final Spliterator mo1031b(Spliterator spliterator) {
        switch (this.f1253g) {
            case 0:
                return new C2026g4(spliterator, this, 0);
            default:
                return new C2026g4(spliterator, this, 1);
        }
    }
}
