package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.s3 */
/* loaded from: classes2.dex */
public final class C2088s3 extends AbstractC2098u3 implements Spliterator.OfInt {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m528x(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC2108w3
    /* renamed from: a */
    public final Spliterator mo1063a(Spliterator spliterator, long j, long j2, long j3, long j4) {
        return new C2088s3((Spliterator.OfInt) spliterator, j, j2, j3, j4);
    }

    @Override // p017j$.util.stream.AbstractC2098u3
    /* renamed from: b */
    public final Object mo1064b() {
        return new C1862B0(1);
    }
}
