package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1779T;

/* renamed from: j$.util.stream.e1 */
/* loaded from: classes2.dex */
public final class C2011e1 extends AbstractC2029h1 implements InterfaceC1779T {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m527w(this, consumer);
    }
}
