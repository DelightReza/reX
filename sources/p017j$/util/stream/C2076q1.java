package p017j$.util.stream;

import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.q1 */
/* loaded from: classes2.dex */
public final class C2076q1 extends AbstractC2081r1 {

    /* renamed from: h */
    public final Object[] f1319h;

    public C2076q1(Spliterator spliterator, AbstractC2106w1 abstractC2106w1, Object[] objArr) {
        super(spliterator, abstractC2106w1, objArr.length);
        this.f1319h = objArr;
    }

    public C2076q1(C2076q1 c2076q1, Spliterator spliterator, long j, long j2) {
        super(c2076q1, spliterator, j, j2, c2076q1.f1319h.length);
        this.f1319h = c2076q1.f1319h;
    }

    @Override // p017j$.util.stream.AbstractC2081r1
    /* renamed from: a */
    public final AbstractC2081r1 mo1053a(Spliterator spliterator, long j, long j2) {
        return new C2076q1(this, spliterator, j, j2);
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        int i = this.f1330f;
        if (i >= this.f1331g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f1330f));
        }
        Object[] objArr = this.f1319h;
        this.f1330f = i + 1;
        objArr[i] = obj;
    }
}
