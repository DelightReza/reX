package p017j$.util.stream;

import java.util.function.IntFunction;

/* renamed from: j$.util.stream.Q0 */
/* loaded from: classes2.dex */
public abstract class AbstractC1937Q0 extends AbstractC1897I0 implements InterfaceC1882F0 {
    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: g */
    public final /* synthetic */ Object[] mo958g(IntFunction intFunction) {
        return AbstractC2106w1.m1076I(this, intFunction);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: d */
    public final void mo953d(Object obj) {
        ((InterfaceC1882F0) this.f1023a).mo953d(obj);
        ((InterfaceC1882F0) this.f1024b).mo953d(obj);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: c */
    public final void mo952c(int i, Object obj) {
        InterfaceC1887G0 interfaceC1887G0 = this.f1023a;
        ((InterfaceC1882F0) interfaceC1887G0).mo952c(i, obj);
        ((InterfaceC1882F0) this.f1024b).mo952c(i + ((int) ((InterfaceC1882F0) interfaceC1887G0).count()), obj);
    }

    @Override // p017j$.util.stream.InterfaceC1882F0
    /* renamed from: b */
    public final Object mo951b() {
        long j = this.f1025c;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object objNewArray = newArray((int) j);
        mo952c(0, objNewArray);
        return objNewArray;
    }

    public final String toString() {
        long j = this.f1025c;
        return j < 32 ? String.format("%s[%s.%s]", getClass().getName(), this.f1023a, this.f1024b) : String.format("%s[size=%d]", getClass().getName(), Long.valueOf(j));
    }
}
