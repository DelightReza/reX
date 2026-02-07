package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.C1810s;
import p017j$.util.concurrent.ConcurrentHashMap;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.k3 */
/* loaded from: classes2.dex */
public final class C2048k3 implements Spliterator, Consumer {

    /* renamed from: d */
    public static final Object f1279d = new Object();

    /* renamed from: a */
    public final Spliterator f1280a;

    /* renamed from: b */
    public final ConcurrentHashMap f1281b;

    /* renamed from: c */
    public Object f1282c;

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public C2048k3(Spliterator spliterator, ConcurrentHashMap concurrentHashMap) {
        this.f1280a = spliterator;
        this.f1281b = concurrentHashMap;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        this.f1282c = obj;
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        while (this.f1280a.tryAdvance(this)) {
            Object obj = this.f1282c;
            if (obj == null) {
                obj = f1279d;
            }
            if (this.f1281b.putIfAbsent(obj, Boolean.TRUE) == null) {
                consumer.m971v(this.f1282c);
                this.f1282c = null;
                return true;
            }
        }
        return false;
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        this.f1280a.forEachRemaining(new C1810s(9, this, consumer));
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.f1280a.trySplit();
        if (spliteratorTrySplit != null) {
            return new C2048k3(spliteratorTrySplit, this.f1281b);
        }
        return null;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f1280a.estimateSize();
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return (this.f1280a.characteristics() & (-16469)) | 1;
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        return this.f1280a.getComparator();
    }
}
