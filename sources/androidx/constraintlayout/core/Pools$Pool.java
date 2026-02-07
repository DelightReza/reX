package androidx.constraintlayout.core;

/* loaded from: classes3.dex */
interface Pools$Pool {
    Object acquire();

    boolean release(Object obj);

    void releaseAll(Object[] objArr, int i);
}
