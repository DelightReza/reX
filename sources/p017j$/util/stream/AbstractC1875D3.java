package p017j$.util.stream;

import java.util.concurrent.atomic.AtomicLong;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.D3 */
/* loaded from: classes2.dex */
public abstract class AbstractC1875D3 {

    /* renamed from: a */
    public final Spliterator f985a;

    /* renamed from: b */
    public final boolean f986b;

    /* renamed from: c */
    public final int f987c;

    /* renamed from: d */
    public final long f988d;

    /* renamed from: e */
    public final AtomicLong f989e;

    /* renamed from: b */
    public abstract Spliterator mo940b(Spliterator spliterator);

    public AbstractC1875D3(Spliterator spliterator, long j, long j2) {
        this.f985a = spliterator;
        this.f986b = j2 < 0;
        this.f988d = j2 >= 0 ? j2 : 0L;
        this.f987c = 128;
        this.f989e = new AtomicLong(j2 >= 0 ? j + j2 : j);
    }

    public AbstractC1875D3(Spliterator spliterator, AbstractC1875D3 abstractC1875D3) {
        this.f985a = spliterator;
        this.f986b = abstractC1875D3.f986b;
        this.f989e = abstractC1875D3.f989e;
        this.f988d = abstractC1875D3.f988d;
        this.f987c = abstractC1875D3.f987c;
    }

    /* renamed from: a */
    public final long m944a(long j) {
        long j2;
        boolean z;
        long jMin;
        do {
            j2 = this.f989e.get();
            z = this.f986b;
            if (j2 != 0) {
                jMin = Math.min(j2, j);
                if (jMin <= 0) {
                    break;
                }
            } else {
                if (z) {
                    return j;
                }
                return 0L;
            }
        } while (!this.f989e.compareAndSet(j2, j2 - jMin));
        if (z) {
            return Math.max(j - jMin, 0L);
        }
        long j3 = this.f988d;
        return j2 > j3 ? Math.max(jMin - (j2 - j3), 0L) : jMin;
    }

    /* renamed from: c */
    public final EnumC1870C3 m945c() {
        if (this.f989e.get() > 0) {
            return EnumC1870C3.MAYBE_MORE;
        }
        return this.f986b ? EnumC1870C3.UNLIMITED : EnumC1870C3.NO_MORE;
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public final Spliterator m2952trySplit() {
        Spliterator spliteratorTrySplit;
        if (this.f989e.get() == 0 || (spliteratorTrySplit = this.f985a.trySplit()) == null) {
            return null;
        }
        return mo940b(spliteratorTrySplit);
    }

    public final long estimateSize() {
        return this.f985a.estimateSize();
    }

    public final int characteristics() {
        return this.f985a.characteristics() & (-16465);
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ InterfaceC1789b0 m2955trySplit() {
        return (InterfaceC1789b0) m2952trySplit();
    }

    public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
        return (Spliterator.OfInt) m2952trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ InterfaceC1784Y m2954trySplit() {
        return (InterfaceC1784Y) m2952trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ InterfaceC1779T m2953trySplit() {
        return (InterfaceC1779T) m2952trySplit();
    }
}
