package p017j$.util.stream;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.j1 */
/* loaded from: classes2.dex */
public abstract class AbstractC2041j1 implements Spliterator {

    /* renamed from: a */
    public InterfaceC1887G0 f1269a;

    /* renamed from: b */
    public int f1270b;

    /* renamed from: c */
    public Spliterator f1271c;

    /* renamed from: d */
    public Spliterator f1272d;

    /* renamed from: e */
    public Deque f1273e;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 64;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return Spliterator.CC.$default$getComparator(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public AbstractC2041j1(InterfaceC1887G0 interfaceC1887G0) {
        this.f1269a = interfaceC1887G0;
    }

    /* renamed from: b */
    public final Deque m1043b() {
        ArrayDeque arrayDeque = new ArrayDeque(8);
        int iMo959i = this.f1269a.mo959i();
        while (true) {
            iMo959i--;
            if (iMo959i < this.f1270b) {
                return arrayDeque;
            }
            arrayDeque.addFirst(this.f1269a.mo950a(iMo959i));
        }
    }

    /* renamed from: a */
    public static InterfaceC1887G0 m1042a(Deque deque) {
        while (true) {
            ArrayDeque arrayDeque = (ArrayDeque) deque;
            InterfaceC1887G0 interfaceC1887G0 = (InterfaceC1887G0) arrayDeque.pollFirst();
            if (interfaceC1887G0 == null) {
                return null;
            }
            if (interfaceC1887G0.mo959i() != 0) {
                for (int iMo959i = interfaceC1887G0.mo959i() - 1; iMo959i >= 0; iMo959i--) {
                    arrayDeque.addFirst(interfaceC1887G0.mo950a(iMo959i));
                }
            } else if (interfaceC1887G0.count() > 0) {
                return interfaceC1887G0;
            }
        }
    }

    /* renamed from: c */
    public final boolean m1044c() {
        if (this.f1269a == null) {
            return false;
        }
        if (this.f1272d != null) {
            return true;
        }
        Spliterator spliterator = this.f1271c;
        if (spliterator == null) {
            Deque dequeM1043b = m1043b();
            this.f1273e = dequeM1043b;
            InterfaceC1887G0 interfaceC1887G0M1042a = m1042a(dequeM1043b);
            if (interfaceC1887G0M1042a != null) {
                this.f1272d = interfaceC1887G0M1042a.spliterator();
                return true;
            }
            this.f1269a = null;
            return false;
        }
        this.f1272d = spliterator;
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        InterfaceC1887G0 interfaceC1887G0 = this.f1269a;
        if (interfaceC1887G0 == null || this.f1272d != null) {
            return null;
        }
        Spliterator spliterator = this.f1271c;
        if (spliterator != null) {
            return spliterator.trySplit();
        }
        if (this.f1270b < interfaceC1887G0.mo959i() - 1) {
            InterfaceC1887G0 interfaceC1887G02 = this.f1269a;
            int i = this.f1270b;
            this.f1270b = i + 1;
            return interfaceC1887G02.mo950a(i).spliterator();
        }
        InterfaceC1887G0 interfaceC1887G0Mo950a = this.f1269a.mo950a(this.f1270b);
        this.f1269a = interfaceC1887G0Mo950a;
        if (interfaceC1887G0Mo950a.mo959i() == 0) {
            Spliterator spliterator2 = this.f1269a.spliterator();
            this.f1271c = spliterator2;
            return spliterator2.trySplit();
        }
        InterfaceC1887G0 interfaceC1887G03 = this.f1269a;
        this.f1270b = 1;
        return interfaceC1887G03.mo950a(0).spliterator();
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        long jCount = 0;
        if (this.f1269a == null) {
            return 0L;
        }
        Spliterator spliterator = this.f1271c;
        if (spliterator != null) {
            return spliterator.estimateSize();
        }
        for (int i = this.f1270b; i < this.f1269a.mo959i(); i++) {
            jCount += this.f1269a.mo950a(i).count();
        }
        return jCount;
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        return (InterfaceC1789b0) trySplit();
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
        return (Spliterator.OfInt) trySplit();
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1784Y trySplit() {
        return (InterfaceC1784Y) trySplit();
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1779T trySplit() {
        return (InterfaceC1779T) trySplit();
    }
}
