package p017j$.util.stream;

import java.util.function.IntFunction;

/* renamed from: j$.util.stream.Z0 */
/* loaded from: classes2.dex */
public abstract class AbstractC1981Z0 implements InterfaceC1887G0 {
    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return 0L;
    }

    /* renamed from: d */
    public final void m1009d(Object obj) {
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: e */
    public /* synthetic */ InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction) {
        return AbstractC2106w1.m1086S(this, j, j2, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: i */
    public final /* synthetic */ int mo959i() {
        return 0;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public InterfaceC1887G0 mo950a(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final Object[] mo958g(IntFunction intFunction) {
        return (Object[]) intFunction.apply(0);
    }

    /* renamed from: c */
    public final void m1008c(int i, Object obj) {
    }
}
