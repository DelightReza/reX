package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.Q */
/* loaded from: classes2.dex */
public abstract class AbstractC1936Q implements InterfaceC1910K3, InterfaceC1915L3 {

    /* renamed from: a */
    public final boolean f1095a;

    public /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
        throw null;
    }

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

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    public AbstractC1936Q(boolean z) {
        this.f1095a = z;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: s */
    public final int mo904s() {
        if (this.f1095a) {
            return 0;
        }
        return EnumC1995b3.f1200r;
    }

    /* renamed from: a */
    public final void m990a(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        if (this.f1095a) {
            new C1945S(abstractC2106w1, spliterator, this).invoke();
        } else {
            new C1950T(abstractC2106w1, spliterator, abstractC2106w1.mo1018u0(this)).invoke();
        }
    }
}
