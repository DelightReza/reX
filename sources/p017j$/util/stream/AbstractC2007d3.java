package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.d3 */
/* loaded from: classes2.dex */
public abstract class AbstractC2007d3 implements Spliterator {

    /* renamed from: a */
    public final boolean f1226a;

    /* renamed from: b */
    public final AbstractC1985a f1227b;

    /* renamed from: c */
    public Supplier f1228c;

    /* renamed from: d */
    public Spliterator f1229d;

    /* renamed from: e */
    public InterfaceC2062n2 f1230e;

    /* renamed from: f */
    public BooleanSupplier f1231f;

    /* renamed from: g */
    public long f1232g;

    /* renamed from: h */
    public AbstractC1997c f1233h;

    /* renamed from: i */
    public boolean f1234i;

    /* renamed from: d */
    public abstract void mo954d();

    /* renamed from: e */
    public abstract AbstractC2007d3 mo955e(Spliterator spliterator);

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public AbstractC2007d3(AbstractC1985a abstractC1985a, Supplier supplier, boolean z) {
        this.f1227b = abstractC1985a;
        this.f1228c = supplier;
        this.f1229d = null;
        this.f1226a = z;
    }

    public AbstractC2007d3(AbstractC1985a abstractC1985a, Spliterator spliterator, boolean z) {
        this.f1227b = abstractC1985a;
        this.f1228c = null;
        this.f1229d = spliterator;
        this.f1226a = z;
    }

    /* renamed from: c */
    public final void m1037c() {
        if (this.f1229d == null) {
            this.f1229d = (Spliterator) this.f1228c.get();
            this.f1228c = null;
        }
    }

    /* renamed from: a */
    public final boolean m1035a() {
        AbstractC1997c abstractC1997c = this.f1233h;
        if (abstractC1997c == null) {
            if (this.f1234i) {
                return false;
            }
            m1037c();
            mo954d();
            this.f1232g = 0L;
            this.f1230e.mo931h(this.f1229d.getExactSizeIfKnown());
            return m1036b();
        }
        long j = this.f1232g + 1;
        this.f1232g = j;
        boolean z = j < abstractC1997c.count();
        if (z) {
            return z;
        }
        this.f1232g = 0L;
        this.f1233h.clear();
        return m1036b();
    }

    @Override // p017j$.util.Spliterator
    public Spliterator trySplit() {
        if (!this.f1226a || this.f1233h != null || this.f1234i) {
            return null;
        }
        m1037c();
        Spliterator spliteratorTrySplit = this.f1229d.trySplit();
        if (spliteratorTrySplit == null) {
            return null;
        }
        return mo955e(spliteratorTrySplit);
    }

    /* renamed from: b */
    public final boolean m1036b() {
        while (this.f1233h.count() == 0) {
            if (this.f1230e.mo932m() || !this.f1231f.getAsBoolean()) {
                if (this.f1234i) {
                    return false;
                }
                this.f1230e.end();
                this.f1234i = true;
            }
        }
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        m1037c();
        return this.f1229d.estimateSize();
    }

    @Override // p017j$.util.Spliterator
    public final long getExactSizeIfKnown() {
        m1037c();
        if (EnumC1995b3.SIZED.m1030n(this.f1227b.f1167m)) {
            return this.f1229d.getExactSizeIfKnown();
        }
        return -1L;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        m1037c();
        int i = this.f1227b.f1167m;
        int i2 = i & ((~i) >> 1) & EnumC1995b3.f1192j & EnumC1995b3.f1188f;
        return (i2 & 64) != 0 ? (i2 & (-16449)) | (this.f1229d.characteristics() & 16448) : i2;
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        if (Spliterator.CC.$default$hasCharacteristics(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }

    public final String toString() {
        return String.format("%s[%s]", getClass().getName(), this.f1229d);
    }
}
