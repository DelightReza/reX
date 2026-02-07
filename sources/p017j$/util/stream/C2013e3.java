package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.stream.e3 */
/* loaded from: classes2.dex */
public final class C2013e3 extends AbstractC2031h3 implements DoubleConsumer {

    /* renamed from: c */
    public final double[] f1236c;

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    public C2013e3(int i) {
        this.f1236c = new double[i];
    }

    @Override // p017j$.util.stream.AbstractC2031h3
    /* renamed from: a */
    public final void mo1039a(Object obj, long j) {
        DoubleConsumer doubleConsumer = (DoubleConsumer) obj;
        for (int i = 0; i < j; i++) {
            doubleConsumer.accept(this.f1236c[i]);
        }
    }

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        int i = this.f1256b;
        this.f1256b = i + 1;
        this.f1236c[i] = d;
    }
}
