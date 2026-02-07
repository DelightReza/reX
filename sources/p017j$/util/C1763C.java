package p017j$.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.stream.InterfaceC2062n2;

/* renamed from: j$.util.C */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1763C implements DoubleConsumer {

    /* renamed from: a */
    public final /* synthetic */ int f765a;

    /* renamed from: b */
    public final /* synthetic */ Consumer f766b;

    public /* synthetic */ C1763C(Consumer consumer, int i) {
        this.f765a = i;
        this.f766b = consumer;
    }

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        switch (this.f765a) {
            case 0:
                this.f766b.accept(Double.valueOf(d));
                break;
            default:
                ((InterfaceC2062n2) this.f766b).accept(d);
                break;
        }
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        switch (this.f765a) {
        }
        return AbstractC1636a.m506b(this, doubleConsumer);
    }
}
