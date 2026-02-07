package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.J */
/* loaded from: classes2.dex */
public abstract class AbstractC1901J implements InterfaceC1915L3 {

    /* renamed from: a */
    public boolean f1036a;

    /* renamed from: b */
    public Object f1037b;

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

    @Override // java.util.function.Consumer
    /* renamed from: accept, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void m971v(Object obj) {
        if (this.f1036a) {
            return;
        }
        this.f1036a = true;
        this.f1037b = obj;
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return this.f1036a;
    }
}
