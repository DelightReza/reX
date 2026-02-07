package p017j$.util;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.util.function.IntConsumer$CC;
import p017j$.util.stream.InterfaceC2062n2;

/* renamed from: j$.util.G */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1767G implements IntConsumer {

    /* renamed from: a */
    public final /* synthetic */ int f769a;

    /* renamed from: b */
    public final /* synthetic */ Consumer f770b;

    public /* synthetic */ C1767G(Consumer consumer, int i) {
        this.f769a = i;
        this.f770b = consumer;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        switch (this.f769a) {
            case 0:
                this.f770b.accept(Integer.valueOf(i));
                break;
            default:
                ((InterfaceC2062n2) this.f770b).accept(i);
                break;
        }
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        switch (this.f769a) {
        }
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }
}
