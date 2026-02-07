package p017j$.util.concurrent;

/* renamed from: j$.util.concurrent.a */
/* loaded from: classes2.dex */
public abstract class AbstractC1792a extends C1806o {

    /* renamed from: i */
    public final ConcurrentHashMap f825i;

    /* renamed from: j */
    public C1802k f826j;

    public AbstractC1792a(C1802k[] c1802kArr, int i, int i2, ConcurrentHashMap concurrentHashMap) {
        super(c1802kArr, i, 0, i2);
        this.f825i = concurrentHashMap;
        m892a();
    }

    public final boolean hasNext() {
        return this.f846b != null;
    }

    public final boolean hasMoreElements() {
        return this.f846b != null;
    }

    public final void remove() {
        C1802k c1802k = this.f826j;
        if (c1802k == null) {
            throw new IllegalStateException();
        }
        this.f826j = null;
        this.f825i.m879g(c1802k.f838b, null, null);
    }
}
