package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntFunction;
import p017j$.util.C1835i0;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.S0 */
/* loaded from: classes2.dex */
public class C1946S0 implements InterfaceC1857A0 {

    /* renamed from: a */
    public final double[] f1118a;

    /* renamed from: b */
    public int f1119b;

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1083P(this, j, j2);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* synthetic */ void forEach(Consumer consumer) {
        AbstractC2106w1.m1080M(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final /* synthetic */ Object[] mo958g(IntFunction intFunction) {
        return AbstractC2106w1.m1076I(this, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: i */
    public final /* synthetic */ int mo959i() {
        return 0;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final /* bridge */ /* synthetic */ InterfaceC1887G0 mo950a(int i) {
        mo950a(i);
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC1882F0, p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1882F0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final /* synthetic */ void mo957f(Object[] objArr, int i) {
        AbstractC2106w1.m1077J(this, (Double[]) objArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: c */
    public final void mo952c(int i, Object obj) {
        int i2 = this.f1119b;
        System.arraycopy(this.f1118a, 0, (double[]) obj, i, i2);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: d */
    public final void mo953d(Object obj) {
        DoubleConsumer doubleConsumer = (DoubleConsumer) obj;
        for (int i = 0; i < this.f1119b; i++) {
            doubleConsumer.accept(this.f1118a[i]);
        }
    }

    public C1946S0(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1118a = new double[(int) j];
        this.f1119b = 0;
    }

    public C1946S0(double[] dArr) {
        this.f1118a = dArr;
        this.f1119b = dArr.length;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        int i = this.f1119b;
        double[] dArr = this.f1118a;
        Spliterators.m859a(((double[]) Objects.requireNonNull(dArr)).length, 0, i);
        return new C1835i0(dArr, 0, i, 1040);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0, p017j$.util.stream.InterfaceC1887G0
    public final InterfaceC1789b0 spliterator() {
        int i = this.f1119b;
        double[] dArr = this.f1118a;
        Spliterators.m859a(((double[]) Objects.requireNonNull(dArr)).length, 0, i);
        return new C1835i0(dArr, 0, i, 1040);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: b */
    public final Object mo951b() {
        double[] dArr = this.f1118a;
        int length = dArr.length;
        int i = this.f1119b;
        return length == i ? dArr : Arrays.copyOf(dArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return this.f1119b;
    }

    public String toString() {
        double[] dArr = this.f1118a;
        return String.format("DoubleArrayNode[%d][%s]", Integer.valueOf(dArr.length - this.f1119b), Arrays.toString(dArr));
    }
}
