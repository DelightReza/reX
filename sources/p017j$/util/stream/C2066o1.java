package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.Spliterator;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.o1 */
/* loaded from: classes2.dex */
public final class C2066o1 extends AbstractC2081r1 implements InterfaceC2052l2 {

    /* renamed from: h */
    public final int[] f1304h;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo975l((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        AbstractC2106w1.m1070C(this, num);
    }

    public C2066o1(Spliterator spliterator, AbstractC2106w1 abstractC2106w1, int[] iArr) {
        super(spliterator, abstractC2106w1, iArr.length);
        this.f1304h = iArr;
    }

    public C2066o1(C2066o1 c2066o1, Spliterator spliterator, long j, long j2) {
        super(c2066o1, spliterator, j, j2, c2066o1.f1304h.length);
        this.f1304h = c2066o1.f1304h;
    }

    @Override // p017j$.util.stream.AbstractC2081r1
    /* renamed from: a */
    public final AbstractC2081r1 mo1053a(Spliterator spliterator, long j, long j2) {
        return new C2066o1(this, spliterator, j, j2);
    }

    @Override // p017j$.util.stream.AbstractC2081r1, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        int i2 = this.f1330f;
        if (i2 >= this.f1331g) {
            throw new IndexOutOfBoundsException(Integer.toString(this.f1330f));
        }
        int[] iArr = this.f1304h;
        this.f1330f = i2 + 1;
        iArr[i2] = i;
    }
}
