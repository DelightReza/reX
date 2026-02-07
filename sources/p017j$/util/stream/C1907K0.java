package p017j$.util.stream;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.Collection;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.K0 */
/* loaded from: classes2.dex */
public final class C1907K0 implements InterfaceC1887G0 {

    /* renamed from: a */
    public final Collection f1048a;

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1086S(this, j, j2, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: i */
    public final /* synthetic */ int mo959i() {
        return 0;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1887G0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public C1907K0(Collection collection) {
        this.f1048a = collection;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        return Collection.EL.stream(this.f1048a).spliterator();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final void mo957f(Object[] objArr, int i) {
        Iterator it = this.f1048a.iterator();
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final Object[] mo958g(IntFunction intFunction) {
        java.util.Collection collection = this.f1048a;
        return collection.toArray((Object[]) intFunction.apply(collection.size()));
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return this.f1048a.size();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final void forEach(Consumer consumer) {
        Collection.EL.m852a(this.f1048a, consumer);
    }

    public final String toString() {
        return String.format("CollectionNode[%d][%s]", Integer.valueOf(this.f1048a.size()), this.f1048a);
    }
}
