package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.P2 */
/* loaded from: classes2.dex */
public final class C1934P2 extends AbstractC1874D2 {

    /* renamed from: d */
    public Object[] f1091d;

    /* renamed from: e */
    public int f1092e;

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1091d = new Object[(int) j];
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = 0;
        Arrays.sort(this.f1091d, 0, this.f1092e, this.f983b);
        long j = this.f1092e;
        InterfaceC2062n2 interfaceC2062n2 = this.f1274a;
        interfaceC2062n2.mo931h(j);
        if (!this.f984c) {
            while (i < this.f1092e) {
                interfaceC2062n2.accept((InterfaceC2062n2) this.f1091d[i]);
                i++;
            }
        } else {
            while (i < this.f1092e && !interfaceC2062n2.mo932m()) {
                interfaceC2062n2.accept((InterfaceC2062n2) this.f1091d[i]);
                i++;
            }
        }
        interfaceC2062n2.end();
        this.f1091d = null;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Object[] objArr = this.f1091d;
        int i = this.f1092e;
        this.f1092e = i + 1;
        objArr[i] = obj;
    }
}
