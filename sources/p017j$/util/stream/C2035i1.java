package p017j$.util.stream;

import java.util.Deque;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.i1 */
/* loaded from: classes2.dex */
public final class C2035i1 extends AbstractC2041j1 {
    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        InterfaceC1887G0 interfaceC1887G0M1042a;
        if (!m1044c()) {
            return false;
        }
        boolean zTryAdvance = this.f1272d.tryAdvance(consumer);
        if (!zTryAdvance) {
            if (this.f1271c == null && (interfaceC1887G0M1042a = AbstractC2041j1.m1042a(this.f1273e)) != null) {
                Spliterator spliterator = interfaceC1887G0M1042a.spliterator();
                this.f1272d = spliterator;
                return spliterator.tryAdvance(consumer);
            }
            this.f1269a = null;
        }
        return zTryAdvance;
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        if (this.f1269a == null) {
            return;
        }
        if (this.f1272d == null) {
            Spliterator spliterator = this.f1271c;
            if (spliterator == null) {
                Deque dequeM1043b = m1043b();
                while (true) {
                    InterfaceC1887G0 interfaceC1887G0M1042a = AbstractC2041j1.m1042a(dequeM1043b);
                    if (interfaceC1887G0M1042a != null) {
                        interfaceC1887G0M1042a.forEach(consumer);
                    } else {
                        this.f1269a = null;
                        return;
                    }
                }
            } else {
                spliterator.forEachRemaining(consumer);
            }
        } else {
            while (tryAdvance(consumer)) {
            }
        }
    }
}
