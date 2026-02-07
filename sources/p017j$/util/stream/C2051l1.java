package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.l1 */
/* loaded from: classes2.dex */
public final class C2051l1 extends C2046k1 implements InterfaceC2110x0 {
    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
        throw null;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo988u((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        AbstractC2106w1.m1072E(this, l);
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final /* bridge */ /* synthetic */ InterfaceC1887G0 build() {
        build();
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC2110x0, p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1877E0 build() {
        int i = this.f1278b;
        long[] jArr = this.f1277a;
        if (i >= jArr.length) {
            return this;
        }
        throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.f1278b), Integer.valueOf(jArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        long[] jArr = this.f1277a;
        if (j != jArr.length) {
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(jArr.length)));
        }
        this.f1278b = 0;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        int i = this.f1278b;
        long[] jArr = this.f1277a;
        if (i < jArr.length) {
            this.f1278b = i + 1;
            jArr[i] = j;
            return;
        }
        throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(jArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = this.f1278b;
        long[] jArr = this.f1277a;
        if (i < jArr.length) {
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.f1278b), Integer.valueOf(jArr.length)));
        }
    }

    @Override // p017j$.util.stream.C2046k1
    public final String toString() {
        long[] jArr = this.f1277a;
        return String.format("LongFixedNodeBuilder[%d][%s]", Integer.valueOf(jArr.length - this.f1278b), Arrays.toString(jArr));
    }
}
