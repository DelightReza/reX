package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.b4 */
/* loaded from: classes2.dex */
public final class C1996b4 extends AbstractC2032h4 implements DoubleConsumer, InterfaceC1779T {

    /* renamed from: e */
    public double f1210e;

    /* renamed from: f */
    public final /* synthetic */ int f1211f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1996b4(Spliterator spliterator, int i) {
        super(spliterator);
        this.f1211f = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1996b4(Spliterator spliterator, AbstractC2032h4 abstractC2032h4, int i) {
        super(spliterator, abstractC2032h4);
        this.f1211f = i;
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m527w(this, consumer);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        while (tryAdvance(doubleConsumer)) {
        }
    }

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f1260d = (this.f1260d + 1) & 63;
        this.f1210e = d;
    }

    @Override // p017j$.util.stream.AbstractC2032h4
    /* renamed from: b */
    public final Spliterator mo1031b(Spliterator spliterator) {
        switch (this.f1211f) {
            case 0:
                return new C1996b4((InterfaceC1779T) spliterator, this, 0);
            default:
                return new C1996b4((InterfaceC1779T) spliterator, this, 1);
        }
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ Spliterator trySplit() {
        switch (this.f1211f) {
            case 1:
                return trySplit();
            default:
                return super.trySplit();
        }
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        switch (this.f1211f) {
            case 1:
                return trySplit();
            default:
                return super.trySplit();
        }
    }

    @Override // p017j$.util.InterfaceC1779T
    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        switch (this.f1211f) {
            case 0:
                boolean z = this.f1259c;
                Spliterator spliterator = this.f1257a;
                if (z) {
                    this.f1259c = false;
                    boolean zTryAdvance = ((InterfaceC1779T) spliterator).tryAdvance((DoubleConsumer) this);
                    if (zTryAdvance && m1041a()) {
                        DoublePredicate doublePredicate = null;
                        doublePredicate.test(this.f1210e);
                        throw null;
                    }
                    if (!zTryAdvance) {
                        return zTryAdvance;
                    }
                    doubleConsumer.accept(this.f1210e);
                    return zTryAdvance;
                }
                return ((InterfaceC1779T) spliterator).tryAdvance(doubleConsumer);
            default:
                if (this.f1259c && m1041a() && ((InterfaceC1779T) this.f1257a).tryAdvance((DoubleConsumer) this)) {
                    DoublePredicate doublePredicate2 = null;
                    doublePredicate2.test(this.f1210e);
                    throw null;
                }
                this.f1259c = false;
                return false;
        }
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public InterfaceC1779T trySplit() {
        switch (this.f1211f) {
            case 1:
                if (this.f1258b.get()) {
                    return null;
                }
                return (InterfaceC1779T) super.trySplit();
            default:
                return super.trySplit();
        }
    }

    @Override // p017j$.util.InterfaceC1789b0
    public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
        switch (this.f1211f) {
            case 1:
                tryAdvance((DoubleConsumer) obj);
                return false;
            default:
                return tryAdvance((DoubleConsumer) obj);
        }
    }
}
