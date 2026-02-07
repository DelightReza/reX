package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.DesugarArrays;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.J0 */
/* loaded from: classes2.dex */
public class C1902J0 implements InterfaceC1887G0 {

    /* renamed from: a */
    public final Object[] f1038a;

    /* renamed from: b */
    public int f1039b;

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1086S(this, j, j2, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: i */
    public final /* synthetic */ int mo959i() {
        return 0;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1887G0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public C1902J0(long j, IntFunction intFunction) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1038a = (Object[]) intFunction.apply((int) j);
        this.f1039b = 0;
    }

    public C1902J0(Object[] objArr) {
        this.f1038a = objArr;
        this.f1039b = objArr.length;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final Spliterator spliterator() {
        return DesugarArrays.m854a(this.f1038a, 0, this.f1039b);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: f */
    public final void mo957f(Object[] objArr, int i) {
        System.arraycopy(this.f1038a, 0, objArr, i, this.f1039b);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final Object[] mo958g(IntFunction intFunction) {
        Object[] objArr = this.f1038a;
        if (objArr.length == this.f1039b) {
            return objArr;
        }
        throw new IllegalStateException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return this.f1039b;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final void forEach(Consumer consumer) {
        for (int i = 0; i < this.f1039b; i++) {
            consumer.m971v(this.f1038a[i]);
        }
    }

    public String toString() {
        Object[] objArr = this.f1038a;
        return String.format("ArrayNode[%d][%s]", Integer.valueOf(objArr.length - this.f1039b), Arrays.toString(objArr));
    }
}
