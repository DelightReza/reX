package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.l3 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2053l3 implements InterfaceC2047k2 {

    /* renamed from: a */
    public final /* synthetic */ int f1289a;

    /* renamed from: b */
    public final /* synthetic */ DoubleConsumer f1290b;

    public /* synthetic */ C2053l3(DoubleConsumer doubleConsumer, int i) {
        this.f1289a = i;
        this.f1290b = doubleConsumer;
    }

    /* renamed from: a */
    private final /* synthetic */ void m1048a(long j) {
    }

    /* renamed from: b */
    private final /* synthetic */ void m1049b(long j) {
    }

    /* renamed from: c */
    private final /* synthetic */ void m1050c() {
    }

    /* renamed from: d */
    private final /* synthetic */ void m1051d() {
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        switch (this.f1289a) {
            case 0:
                this.f1290b.accept(d);
                break;
            default:
                ((C1948S2) this.f1290b).accept(d);
                break;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(int i) {
        switch (this.f1289a) {
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
        switch (this.f1289a) {
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
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        switch (this.f1289a) {
            case 0:
                mo934v((Double) obj);
                break;
            default:
                mo934v((Double) obj);
                break;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f1289a) {
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        switch (this.f1289a) {
        }
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
        int i = this.f1289a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final /* synthetic */ void mo931h(long j) {
        int i = this.f1289a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        switch (this.f1289a) {
        }
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        switch (this.f1289a) {
            case 0:
                AbstractC2106w1.m1068A(this, d);
                break;
            default:
                AbstractC2106w1.m1068A(this, d);
                break;
        }
    }
}
