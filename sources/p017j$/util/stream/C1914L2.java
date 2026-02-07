package p017j$.util.stream;

import java.util.ArrayList;
import p017j$.time.C1726t;
import p017j$.util.Collection;
import p017j$.util.List;
import p017j$.util.Objects;

/* renamed from: j$.util.stream.L2 */
/* loaded from: classes2.dex */
public final class C1914L2 extends AbstractC1874D2 {

    /* renamed from: d */
    public ArrayList f1059d;

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1059d = j >= 0 ? new ArrayList((int) j) : new ArrayList();
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        List.EL.sort(this.f1059d, this.f983b);
        long size = this.f1059d.size();
        InterfaceC2062n2 interfaceC2062n2 = this.f1274a;
        interfaceC2062n2.mo931h(size);
        if (!this.f984c) {
            ArrayList arrayList = this.f1059d;
            Objects.requireNonNull(interfaceC2062n2);
            Collection.EL.m852a(arrayList, new C1726t(11, interfaceC2062n2));
        } else {
            ArrayList arrayList2 = this.f1059d;
            int size2 = arrayList2.size();
            int i = 0;
            while (i < size2) {
                Object obj = arrayList2.get(i);
                i++;
                if (interfaceC2062n2.mo932m()) {
                    break;
                } else {
                    interfaceC2062n2.m971v((InterfaceC2062n2) obj);
                }
            }
        }
        interfaceC2062n2.end();
        this.f1059d = null;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        this.f1059d.add(obj);
    }
}
