package p017j$.util.stream;

import java.util.Deque;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.h1 */
/* loaded from: classes2.dex */
public abstract class AbstractC2029h1 extends AbstractC2041j1 implements InterfaceC1789b0 {
    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(Object obj) {
        InterfaceC1882F0 interfaceC1882F0;
        if (!m1044c()) {
            return false;
        }
        boolean zTryAdvance = ((InterfaceC1789b0) this.f1272d).tryAdvance(obj);
        if (!zTryAdvance) {
            if (this.f1271c == null && (interfaceC1882F0 = (InterfaceC1882F0) AbstractC2041j1.m1042a(this.f1273e)) != null) {
                InterfaceC1789b0 interfaceC1789b0Spliterator = interfaceC1882F0.spliterator();
                this.f1272d = interfaceC1789b0Spliterator;
                return interfaceC1789b0Spliterator.tryAdvance(obj);
            }
            this.f1269a = null;
        }
        return zTryAdvance;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(Object obj) {
        if (this.f1269a == null) {
            return;
        }
        if (this.f1272d == null) {
            Spliterator spliterator = this.f1271c;
            if (spliterator == null) {
                Deque dequeM1043b = m1043b();
                while (true) {
                    InterfaceC1882F0 interfaceC1882F0 = (InterfaceC1882F0) AbstractC2041j1.m1042a(dequeM1043b);
                    if (interfaceC1882F0 != null) {
                        interfaceC1882F0.mo953d(obj);
                    } else {
                        this.f1269a = null;
                        return;
                    }
                }
            } else {
                ((InterfaceC1789b0) spliterator).forEachRemaining(obj);
            }
        } else {
            while (tryAdvance(obj)) {
            }
        }
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        forEachRemaining((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
        return tryAdvance((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        forEachRemaining((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
        return tryAdvance((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        forEachRemaining((Object) doubleConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
        return tryAdvance((Object) doubleConsumer);
    }
}
