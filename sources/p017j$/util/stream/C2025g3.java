package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.stream.g3 */
/* loaded from: classes2.dex */
public final class C2025g3 extends AbstractC2031h3 implements LongConsumer {

    /* renamed from: c */
    public final long[] f1250c;

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    public C2025g3(int i) {
        this.f1250c = new long[i];
    }

    @Override // p017j$.util.stream.AbstractC2031h3
    /* renamed from: a */
    public final void mo1039a(Object obj, long j) {
        LongConsumer longConsumer = (LongConsumer) obj;
        for (int i = 0; i < j; i++) {
            longConsumer.accept(this.f1250c[i]);
        }
    }

    @Override // java.util.function.LongConsumer
    public final void accept(long j) {
        int i = this.f1256b;
        this.f1256b = i + 1;
        this.f1250c[i] = j;
    }
}
