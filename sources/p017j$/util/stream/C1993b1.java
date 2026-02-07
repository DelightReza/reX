package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.b1 */
/* loaded from: classes2.dex */
public class C1993b1 implements InterfaceC1867C0 {

    /* renamed from: a */
    public final int[] f1183a;

    /* renamed from: b */
    public int f1184b;

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1084Q(this, j, j2);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* synthetic */ void forEach(Consumer consumer) {
        AbstractC2106w1.m1081N(this, consumer);
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
        AbstractC2106w1.m1078K(this, (Integer[]) objArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: c */
    public final void mo952c(int i, Object obj) {
        int i2 = this.f1184b;
        System.arraycopy(this.f1183a, 0, (int[]) obj, i, i2);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: d */
    public final void mo953d(Object obj) {
        IntConsumer intConsumer = (IntConsumer) obj;
        for (int i = 0; i < this.f1184b; i++) {
            intConsumer.accept(this.f1183a[i]);
        }
    }

    public C1993b1(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1183a = new int[(int) j];
        this.f1184b = 0;
    }

    public C1993b1(int[] iArr) {
        this.f1183a = iArr;
        this.f1184b = iArr.length;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        return Spliterators.spliterator(this.f1183a, 0, this.f1184b, 1040);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0, p017j$.util.stream.InterfaceC1887G0
    public final InterfaceC1789b0 spliterator() {
        return Spliterators.spliterator(this.f1183a, 0, this.f1184b, 1040);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: b */
    public final Object mo951b() {
        int[] iArr = this.f1183a;
        int length = iArr.length;
        int i = this.f1184b;
        return length == i ? iArr : Arrays.copyOf(iArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return this.f1184b;
    }

    public String toString() {
        int[] iArr = this.f1183a;
        return String.format("IntArrayNode[%d][%s]", Integer.valueOf(iArr.length - this.f1184b), Arrays.toString(iArr));
    }
}
