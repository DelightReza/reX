package p017j$.util.stream;

/* renamed from: j$.util.stream.I0 */
/* loaded from: classes2.dex */
public abstract class AbstractC1897I0 implements InterfaceC1887G0 {

    /* renamed from: a */
    public final InterfaceC1887G0 f1023a;

    /* renamed from: b */
    public final InterfaceC1887G0 f1024b;

    /* renamed from: c */
    public final long f1025c;

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: i */
    public final int mo959i() {
        return 2;
    }

    public AbstractC1897I0(InterfaceC1887G0 interfaceC1887G0, InterfaceC1887G0 interfaceC1887G02) {
        this.f1023a = interfaceC1887G0;
        this.f1024b = interfaceC1887G02;
        this.f1025c = interfaceC1887G02.count() + interfaceC1887G0.count();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public final InterfaceC1887G0 mo950a(int i) {
        if (i == 0) {
            return this.f1023a;
        }
        if (i == 1) {
            return this.f1024b;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    public final long count() {
        return this.f1025c;
    }

    @Override // p017j$.util.stream.InterfaceC1887G0
    /* renamed from: a */
    public /* bridge */ /* synthetic */ InterfaceC1882F0 mo950a(int i) {
        return (InterfaceC1882F0) mo950a(i);
    }
}
