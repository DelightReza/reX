package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.p3 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2073p3 implements InterfaceC2057m2 {

    /* renamed from: a */
    public final /* synthetic */ int f1315a;

    /* renamed from: b */
    public final /* synthetic */ LongConsumer f1316b;

    public /* synthetic */ C2073p3(LongConsumer longConsumer, int i) {
        this.f1315a = i;
        this.f1316b = longConsumer;
    }

    /* renamed from: a */
    private final /* synthetic */ void m1059a(long j) {
    }

    /* renamed from: b */
    private final /* synthetic */ void m1060b(long j) {
    }

    /* renamed from: c */
    private final /* synthetic */ void m1061c() {
    }

    /* renamed from: d */
    private final /* synthetic */ void m1062d() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final /* synthetic */ void accept(double d) {
        switch (this.f1315a) {
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
        switch (this.f1315a) {
            case 0:
                AbstractC2106w1.m1074G();
                throw null;
            default:
                AbstractC2106w1.m1074G();
                throw null;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        switch (this.f1315a) {
            case 0:
                this.f1316b.accept(j);
                break;
            default:
                ((C1968W2) this.f1316b).accept(j);
                break;
        }
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        switch (this.f1315a) {
            case 0:
                mo988u((Long) obj);
                break;
            default:
                mo988u((Long) obj);
                break;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f1315a) {
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        switch (this.f1315a) {
        }
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
        int i = this.f1315a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final /* synthetic */ void mo931h(long j) {
        int i = this.f1315a;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        switch (this.f1315a) {
        }
        return false;
    }

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        switch (this.f1315a) {
            case 0:
                AbstractC2106w1.m1072E(this, l);
                break;
            default:
                AbstractC2106w1.m1072E(this, l);
                break;
        }
    }
}
