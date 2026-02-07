package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;
import p017j$.util.Spliterators;

/* renamed from: j$.util.stream.T2 */
/* loaded from: classes2.dex */
public final class C1953T2 extends AbstractC1973X2 implements Spliterator.OfInt {

    /* renamed from: g */
    public final /* synthetic */ C1958U2 f1128g;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m528x(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: a */
    public final void mo991a(int i, Object obj, Object obj2) {
        ((IntConsumer) obj2).accept(((int[]) obj)[i]);
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: b */
    public final InterfaceC1789b0 mo992b(Object obj, int i, int i2) {
        return Spliterators.spliterator((int[]) obj, i, i2 + i, 1040);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1953T2(C1958U2 c1958u2, int i, int i2, int i3, int i4) {
        super(c1958u2, i, i2, i3, i4);
        this.f1128g = c1958u2;
    }

    @Override // p017j$.util.stream.AbstractC1973X2
    /* renamed from: c */
    public final InterfaceC1789b0 mo993c(int i, int i2, int i3, int i4) {
        return new C1953T2(this.f1128g, i, i2, i3, i4);
    }
}
