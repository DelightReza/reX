package p017j$.util.concurrent;

/* renamed from: j$.util.concurrent.u */
/* loaded from: classes2.dex */
public final class C1812u extends ThreadLocal {
    @Override // java.lang.ThreadLocal
    public final Object initialValue() {
        return new ThreadLocalRandom(0);
    }
}
