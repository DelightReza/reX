package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1849p0;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.V2 */
/* loaded from: classes2.dex */
public final class C1963V2 extends AbstractC1973X2 implements InterfaceC1784Y {

    /* renamed from: g */
    public final /* synthetic */ C1968W2 f1133g;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m529y(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: a */
    public final void mo991a(int i, Object obj, Object obj2) {
        ((LongConsumer) obj2).accept(((long[]) obj)[i]);
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: b */
    public final InterfaceC1789b0 mo992b(Object obj, int i, int i2) {
        long[] jArr = (long[]) obj;
        int i3 = i2 + i;
        Spliterators.m859a(((long[]) Objects.requireNonNull(jArr)).length, i, i3);
        return new C1849p0(jArr, i, i3, 1040);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1963V2(C1968W2 c1968w2, int i, int i2, int i3, int i4) {
        super(c1968w2, i, i2, i3, i4);
        this.f1133g = c1968w2;
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: c */
    public final InterfaceC1789b0 mo993c(int i, int i2, int i3, int i4) {
        return new C1963V2(this.f1133g, i, i2, i3, i4);
    }
}
