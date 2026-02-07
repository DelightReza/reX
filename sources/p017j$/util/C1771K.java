package p017j$.util;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.stream.InterfaceC2062n2;

/* renamed from: j$.util.K */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1771K implements LongConsumer {

    /* renamed from: a */
    public final /* synthetic */ int f773a;

    /* renamed from: b */
    public final /* synthetic */ Consumer f774b;

    public /* synthetic */ C1771K(Consumer consumer, int i) {
        this.f773a = i;
        this.f774b = consumer;
    }

    @Override // java.util.function.LongConsumer
    public final void accept(long j) {
        switch (this.f773a) {
            case 0:
                this.f774b.accept(Long.valueOf(j));
                break;
            default:
                ((InterfaceC2062n2) this.f774b).accept(j);
                break;
        }
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        switch (this.f773a) {
        }
        return AbstractC1636a.m507c(this, longConsumer);
    }
}
