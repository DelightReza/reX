package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.T0 */
/* loaded from: classes2.dex */
public final class C1951T0 extends C1946S0 implements InterfaceC2100v0 {
    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
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
        mo934v((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        AbstractC2106w1.m1068A(this, d);
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final /* bridge */ /* synthetic */ InterfaceC1887G0 build() {
        build();
        return this;
    }

    @Override // p017j$.util.stream.InterfaceC2100v0, p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1857A0 build() {
        int i = this.f1119b;
        double[] dArr = this.f1118a;
        if (i >= dArr.length) {
            return this;
        }
        throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.f1119b), Integer.valueOf(dArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        double[] dArr = this.f1118a;
        if (j != dArr.length) {
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(dArr.length)));
        }
        this.f1119b = 0;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        int i = this.f1119b;
        double[] dArr = this.f1118a;
        if (i < dArr.length) {
            this.f1119b = i + 1;
            dArr[i] = d;
            return;
        }
        throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(dArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = this.f1119b;
        double[] dArr = this.f1118a;
        if (i < dArr.length) {
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.f1119b), Integer.valueOf(dArr.length)));
        }
    }

    @Override // p017j$.util.stream.C1946S0
    public final String toString() {
        double[] dArr = this.f1118a;
        return String.format("DoubleFixedNodeBuilder[%d][%s]", Integer.valueOf(dArr.length - this.f1119b), Arrays.toString(dArr));
    }
}
