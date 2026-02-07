package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1835i0;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.R2 */
/* loaded from: classes2.dex */
public final class C1943R2 extends AbstractC1973X2 implements InterfaceC1779T {

    /* renamed from: g */
    public final /* synthetic */ C1948S2 f1106g;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m527w(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: a */
    public final void mo991a(int i, Object obj, Object obj2) {
        ((DoubleConsumer) obj2).accept(((double[]) obj)[i]);
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: b */
    public final InterfaceC1789b0 mo992b(Object obj, int i, int i2) {
        double[] dArr = (double[]) obj;
        int i3 = i2 + i;
        Spliterators.m859a(((double[]) Objects.requireNonNull(dArr)).length, i, i3);
        return new C1835i0(dArr, i, i3, 1040);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1943R2(C1948S2 c1948s2, int i, int i2, int i3, int i4) {
        super(c1948s2, i, i2, i3, i4);
        this.f1106g = c1948s2;
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: c */
    public final InterfaceC1789b0 mo993c(int i, int i2, int i3, int i4) {
        return new C1943R2(this.f1106g, i, i2, i3, i4);
    }
}
