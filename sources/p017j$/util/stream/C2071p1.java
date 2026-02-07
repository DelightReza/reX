package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.p1 */
/* loaded from: classes2.dex */
public final class C2071p1 extends AbstractC2081r1 implements InterfaceC2057m2 {

    /* renamed from: h */
    public final long[] f1312h;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo988u((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        AbstractC2106w1.m1072E(this, l);
    }

    public C2071p1(Spliterator spliterator, AbstractC2106w1 abstractC2106w1, long[] jArr) {
        super(spliterator, abstractC2106w1, jArr.length);
        this.f1312h = jArr;
    }

    public C2071p1(C2071p1 c2071p1, Spliterator spliterator, long j, long j2) {
        super(c2071p1, spliterator, j, j2, c2071p1.f1312h.length);
        this.f1312h = c2071p1.f1312h;
    }

    @Override // p017j$.util.stream.AbstractC2081r1
    /* renamed from: a */
    public final AbstractC2081r1 mo1053a(Spliterator spliterator, long j, long j2) {
        return new C2071p1(this, spliterator, j, j2);
    }

    @Override // p017j$.util.stream.AbstractC2081r1, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        int i = this.f1330f;
        if (i >= this.f1331g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f1330f));
        }
        long[] jArr = this.f1312h;
        this.f1330f = i + 1;
        jArr[i] = j;
    }
}
