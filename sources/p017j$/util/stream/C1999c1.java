package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.c1 */
/* loaded from: classes2.dex */
public final class C1999c1 extends C1993b1 implements InterfaceC2105w0 {
    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo975l((Integer) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        AbstractC2106w1.m1070C(this, num);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final /* bridge */ /* synthetic */ InterfaceC1887G0 build() {
        build();
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC2105w0, p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1867C0 build() {
        int i = this.f1184b;
        int[] iArr = this.f1183a;
        if (i >= iArr.length) {
            return this;
        }
        throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.f1184b), Integer.valueOf(iArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        int[] iArr = this.f1183a;
        if (j != iArr.length) {
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(iArr.length)));
        }
        this.f1184b = 0;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        int i2 = this.f1184b;
        int[] iArr = this.f1183a;
        if (i2 < iArr.length) {
            this.f1184b = i2 + 1;
            iArr[i2] = i;
            return;
        }
        throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(iArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = this.f1184b;
        int[] iArr = this.f1183a;
        if (i < iArr.length) {
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.f1184b), Integer.valueOf(iArr.length)));
        }
    }

    @Override // p017j$.util.stream.C1993b1
    public final String toString() {
        int[] iArr = this.f1183a;
        return String.format("IntFixedNodeBuilder[%d][%s]", Integer.valueOf(iArr.length - this.f1184b), Arrays.toString(iArr));
    }
}
