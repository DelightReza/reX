package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.n3 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2063n3 implements InterfaceC2052l2 {

    /* renamed from: a */
    public final /* synthetic */ int f1300a;

    /* renamed from: b */
    public final /* synthetic */ IntConsumer f1301b;

    public /* synthetic */ C2063n3(IntConsumer intConsumer, int i) {
        this.f1300a = i;
        this.f1301b = intConsumer;
    }

    /* renamed from: a */
    private final /* synthetic */ void m1054a(long j) {
    }

    /* renamed from: b */
    private final /* synthetic */ void m1055b(long j) {
    }

    /* renamed from: c */
    private final /* synthetic */ void m1056c() {
    }

    /* renamed from: d */
    private final /* synthetic */ void m1057d() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        switch (this.f1300a) {
            case 0:
                AbstractC2106w1.m1107z();
                throw null;
            default:
                AbstractC2106w1.m1107z();
                throw null;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        switch (this.f1300a) {
            case 0:
                this.f1301b.accept(i);
                break;
            default:
                ((C1958U2) this.f1301b).accept(i);
                break;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void accept(long j) {
        switch (this.f1300a) {
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
        switch (this.f1300a) {
            case 0:
                mo975l((Integer) obj);
                break;
            default:
                mo975l((Integer) obj);
                break;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f1300a) {
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        switch (this.f1300a) {
        }
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
        int i = this.f1300a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final /* synthetic */ void mo931h(long j) {
        int i = this.f1300a;
    }

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        switch (this.f1300a) {
            case 0:
                AbstractC2106w1.m1070C(this, num);
                break;
            default:
                AbstractC2106w1.m1070C(this, num);
                break;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        switch (this.f1300a) {
        }
        return false;
    }
}
