package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.t3 */
/* loaded from: classes2.dex */
public final class C2093t3 extends AbstractC2098u3 implements InterfaceC1784Y {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m529y(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC2108w3
    /* renamed from: a */
    public final Spliterator mo1063a(Spliterator spliterator, long j, long j2, long j3, long j4) {
        return new C2093t3((InterfaceC1784Y) spliterator, j, j2, j3, j4);
    }

    @Override // p017j$.util.stream.AbstractC2098u3
    /* renamed from: b */
    public final Object mo1064b() {
        return new C1872D0(1);
    }
}
