package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.s0 */
/* loaded from: classes2.dex */
public abstract class AbstractC2085s0 implements InterfaceC2062n2 {

    /* renamed from: a */
    public boolean f1335a;

    /* renamed from: b */
    public boolean f1336b;

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final /* synthetic */ void mo931h(long j) {
    }

    public AbstractC2085s0(EnumC2090t0 enumC2090t0) {
        this.f1336b = !enumC2090t0.f1343b;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return this.f1335a;
    }
}
