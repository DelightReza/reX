package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import p017j$.util.C1849p0;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.k1 */
/* loaded from: classes2.dex */
public class C2046k1 implements InterfaceC1877E0 {

    /* renamed from: a */
    public final long[] f1277a;

    /* renamed from: b */
    public int f1278b;

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1085R(this, j, j2);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final /* synthetic */ void forEach(Consumer consumer) {
        AbstractC2106w1.m1082O(this, consumer);
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
        AbstractC2106w1.m1079L(this, (Long[]) objArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: c */
    public final void mo952c(int i, Object obj) {
        int i2 = this.f1278b;
        System.arraycopy(this.f1277a, 0, (long[]) obj, i, i2);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: d */
    public final void mo953d(Object obj) {
        LongConsumer longConsumer = (LongConsumer) obj;
        for (int i = 0; i < this.f1278b; i++) {
            longConsumer.accept(this.f1277a[i]);
        }
    }

    public C2046k1(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1277a = new long[(int) j];
        this.f1278b = 0;
    }

    public C2046k1(long[] jArr) {
        this.f1277a = jArr;
        this.f1278b = jArr.length;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        int i = this.f1278b;
        long[] jArr = this.f1277a;
        Spliterators.m859a(((long[]) Objects.requireNonNull(jArr)).length, 0, i);
        return new C1849p0(jArr, 0, i, 1040);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0, p017j$.util.stream.InterfaceC1887G0
    public final InterfaceC1789b0 spliterator() {
        int i = this.f1278b;
        long[] jArr = this.f1277a;
        Spliterators.m859a(((long[]) Objects.requireNonNull(jArr)).length, 0, i);
        return new C1849p0(jArr, 0, i, 1040);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: b */
    public final Object mo951b() {
        long[] jArr = this.f1277a;
        int length = jArr.length;
        int i = this.f1278b;
        return length == i ? jArr : Arrays.copyOf(jArr, i);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return this.f1278b;
    }

    public String toString() {
        long[] jArr = this.f1277a;
        return String.format("LongArrayNode[%d][%s]", Integer.valueOf(jArr.length - this.f1278b), Arrays.toString(jArr));
    }
}
