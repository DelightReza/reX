package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.f4 */
/* loaded from: classes2.dex */
public final class C2020f4 extends AbstractC2032h4 implements LongConsumer, InterfaceC1784Y {

    /* renamed from: e */
    public long f1244e;

    /* renamed from: f */
    public final /* synthetic */ int f1245f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2020f4(Spliterator spliterator, int i) {
        super(spliterator);
        this.f1245f = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2020f4(Spliterator spliterator, AbstractC2032h4 abstractC2032h4, int i) {
        super(spliterator, abstractC2032h4);
        this.f1245f = i;
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m529y(this, consumer);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(LongConsumer longConsumer) {
        while (tryAdvance(longConsumer)) {
        }
    }

    @Override // java.util.function.LongConsumer
    public final void accept(long j) {
        this.f1260d = (this.f1260d + 1) & 63;
        this.f1244e = j;
    }

    @Override // p017j$.util.stream.AbstractC2032h4
    /* renamed from: b */
    public final Spliterator mo1031b(Spliterator spliterator) {
        switch (this.f1245f) {
            case 0:
                return new C2020f4((InterfaceC1784Y) spliterator, this, 0);
            default:
                return new C2020f4((InterfaceC1784Y) spliterator, this, 1);
        }
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ Spliterator trySplit() {
        switch (this.f1245f) {
            case 1:
                return trySplit();
            default:
                return super.trySplit();
        }
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        switch (this.f1245f) {
            case 1:
                return trySplit();
            default:
                return super.trySplit();
        }
    }

    @Override // p017j$.util.InterfaceC1784Y
    public final boolean tryAdvance(LongConsumer longConsumer) {
        switch (this.f1245f) {
            case 0:
                boolean z = this.f1259c;
                Spliterator spliterator = this.f1257a;
                if (z) {
                    this.f1259c = false;
                    boolean zTryAdvance = ((InterfaceC1784Y) spliterator).tryAdvance((LongConsumer) this);
                    if (zTryAdvance && m1041a()) {
                        LongPredicate longPredicate = null;
                        longPredicate.test(this.f1244e);
                        throw null;
                    }
                    if (!zTryAdvance) {
                        return zTryAdvance;
                    }
                    longConsumer.accept(this.f1244e);
                    return zTryAdvance;
                }
                return ((InterfaceC1784Y) spliterator).tryAdvance(longConsumer);
            default:
                if (this.f1259c && m1041a() && ((InterfaceC1784Y) this.f1257a).tryAdvance((LongConsumer) this)) {
                    LongPredicate longPredicate2 = null;
                    longPredicate2.test(this.f1244e);
                    throw null;
                }
                this.f1259c = false;
                return false;
        }
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public InterfaceC1784Y trySplit() {
        switch (this.f1245f) {
            case 1:
                if (this.f1258b.get()) {
                    return null;
                }
                return (InterfaceC1784Y) super.trySplit();
            default:
                return super.trySplit();
        }
    }

    @Override // p017j$.util.InterfaceC1789b0
    public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
        switch (this.f1245f) {
            case 1:
                tryAdvance((LongConsumer) obj);
                return false;
            default:
                return tryAdvance((LongConsumer) obj);
        }
    }
}
