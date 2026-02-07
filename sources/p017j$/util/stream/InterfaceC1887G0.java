package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.G0 */
/* loaded from: classes2.dex */
public interface InterfaceC1887G0 {
    /* renamed from: a */
    InterfaceC1887G0 mo950a(int i);

    long count();

    /* renamed from: e */
    InterfaceC1887G0 mo956e(long j, long j2, IntFunction intFunction);

    /* renamed from: f */
    void mo957f(Object[] objArr, int i);

    void forEach(Consumer consumer);

    /* renamed from: g */
    Object[] mo958g(IntFunction intFunction);

    /* renamed from: i */
    int mo959i();

    Spliterator spliterator();
}
