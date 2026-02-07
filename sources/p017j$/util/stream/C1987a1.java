package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.a1 */
/* loaded from: classes2.dex */
public final class C1987a1 extends C1902J0 implements InterfaceC2115y0 {
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

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2115y0
    public final InterfaceC1887G0 build() {
        int i = this.f1039b;
        Object[] objArr = this.f1038a;
        if (i >= objArr.length) {
            return this;
        }
        throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.f1039b), Integer.valueOf(objArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        Object[] objArr = this.f1038a;
        if (j != objArr.length) {
            throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(objArr.length)));
        }
        this.f1039b = 0;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        int i = this.f1039b;
        Object[] objArr = this.f1038a;
        if (i < objArr.length) {
            this.f1039b = i + 1;
            objArr[i] = obj;
            return;
        }
        throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(objArr.length)));
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = this.f1039b;
        Object[] objArr = this.f1038a;
        if (i < objArr.length) {
            throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.f1039b), Integer.valueOf(objArr.length)));
        }
    }

    @Override // p017j$.util.stream.C1902J0
    public final String toString() {
        Object[] objArr = this.f1038a;
        return String.format("FixedNodeBuilder[%d][%s]", Integer.valueOf(objArr.length - this.f1039b), Arrays.toString(objArr));
    }
}
