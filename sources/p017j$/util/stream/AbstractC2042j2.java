package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.Objects;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.j2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2042j2 implements InterfaceC2062n2 {

    /* renamed from: a */
    public final InterfaceC2062n2 f1274a;

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
        throw null;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public AbstractC2042j2(InterfaceC2062n2 interfaceC2062n2) {
        this.f1274a = (InterfaceC2062n2) Objects.requireNonNull(interfaceC2062n2);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        this.f1274a.mo931h(j);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public void end() {
        this.f1274a.end();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public boolean mo932m() {
        return this.f1274a.mo932m();
    }
}
