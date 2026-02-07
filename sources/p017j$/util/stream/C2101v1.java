package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.v1 */
/* loaded from: classes2.dex */
public class C2101v1 extends CountedCompleter {

    /* renamed from: a */
    public final InterfaceC1887G0 f1352a;

    /* renamed from: b */
    public final int f1353b;

    /* renamed from: c */
    public final /* synthetic */ int f1354c;

    /* renamed from: d */
    public final Object f1355d;

    public C2101v1(InterfaceC1887G0 interfaceC1887G0, Object obj, int i) {
        this.f1354c = i;
        this.f1352a = interfaceC1887G0;
        this.f1353b = 0;
        this.f1355d = obj;
    }

    public C2101v1(C2101v1 c2101v1, InterfaceC1887G0 interfaceC1887G0, int i, byte b) {
        super(c2101v1);
        this.f1352a = interfaceC1887G0;
        this.f1353b = i;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        C2101v1 c2101v1M1067a = this;
        while (c2101v1M1067a.f1352a.mo959i() != 0) {
            c2101v1M1067a.setPendingCount(c2101v1M1067a.f1352a.mo959i() - 1);
            int i = 0;
            int iCount = 0;
            while (i < c2101v1M1067a.f1352a.mo959i() - 1) {
                C2101v1 c2101v1M1067a2 = c2101v1M1067a.m1067a(i, c2101v1M1067a.f1353b + iCount);
                iCount = (int) (c2101v1M1067a2.f1352a.count() + iCount);
                c2101v1M1067a2.fork();
                i++;
            }
            c2101v1M1067a = c2101v1M1067a.m1067a(i, c2101v1M1067a.f1353b + iCount);
        }
        switch (c2101v1M1067a.f1354c) {
            case 0:
                ((InterfaceC1882F0) c2101v1M1067a.f1352a).mo952c(c2101v1M1067a.f1353b, c2101v1M1067a.f1355d);
                break;
            default:
                c2101v1M1067a.f1352a.mo957f((Object[]) c2101v1M1067a.f1355d, c2101v1M1067a.f1353b);
                break;
        }
        c2101v1M1067a.propagateCompletion();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C2101v1(C2101v1 c2101v1, InterfaceC1887G0 interfaceC1887G0, int i) {
        this(c2101v1, interfaceC1887G0, i, (byte) 0);
        this.f1354c = 1;
        this.f1355d = (Object[]) c2101v1.f1355d;
    }

    /* renamed from: a */
    public final C2101v1 m1067a(int i, int i2) {
        switch (this.f1354c) {
            case 0:
                return new C2101v1(this, ((InterfaceC1882F0) this.f1352a).mo950a(i), i2);
            default:
                return new C2101v1(this, this.f1352a.mo950a(i), i2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C2101v1(C2101v1 c2101v1, InterfaceC1882F0 interfaceC1882F0, int i) {
        this(c2101v1, interfaceC1882F0, i, (byte) 0);
        this.f1354c = 0;
        this.f1355d = c2101v1.f1355d;
    }
}
