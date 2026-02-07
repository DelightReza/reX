package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.E3 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1880E3 implements InterfaceC2062n2 {

    /* renamed from: a */
    public final /* synthetic */ int f998a;

    /* renamed from: b */
    public final /* synthetic */ Consumer f999b;

    public /* synthetic */ C1880E3(Consumer consumer, int i) {
        this.f998a = i;
        this.f999b = consumer;
    }

    /* renamed from: a */
    private final /* synthetic */ void m946a(long j) {
    }

    /* renamed from: b */
    private final /* synthetic */ void m947b(long j) {
    }

    /* renamed from: c */
    private final /* synthetic */ void m948c() {
    }

    /* renamed from: d */
    private final /* synthetic */ void m949d() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        switch (this.f998a) {
            case 0:
                AbstractC2106w1.m1107z();
                throw null;
            default:
                AbstractC2106w1.m1107z();
                throw null;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(int i) {
        switch (this.f998a) {
            case 0:
                AbstractC2106w1.m1074G();
                throw null;
            default:
                AbstractC2106w1.m1074G();
                throw null;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        switch (this.f998a) {
            case 0:
                AbstractC2106w1.m1075H();
                throw null;
            default:
                AbstractC2106w1.m1075H();
                throw null;
        }
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        switch (this.f998a) {
            case 0:
                ((C1983Z2) this.f999b).m971v(obj);
                break;
            default:
                this.f999b.m971v(obj);
                break;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f998a) {
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
        int i = this.f998a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final /* synthetic */ void mo931h(long j) {
        int i = this.f998a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        switch (this.f998a) {
        }
        return false;
    }
}
