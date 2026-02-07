package p017j$.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.d0 */
/* loaded from: classes2.dex */
public final class C1817d0 implements Iterator, Consumer {

    /* renamed from: a */
    public boolean f881a = false;

    /* renamed from: b */
    public Object f882b;

    /* renamed from: c */
    public final /* synthetic */ Spliterator f883c;

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public C1817d0(Spliterator spliterator) {
        this.f883c = spliterator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f881a = true;
        this.f882b = obj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (!this.f881a) {
            this.f883c.tryAdvance(this);
        }
        return this.f881a;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!this.f881a && !hasNext()) {
            throw new NoSuchElementException();
        }
        this.f881a = false;
        return this.f882b;
    }
}
