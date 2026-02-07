package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.O2 */
/* loaded from: classes2.dex */
public final class C1929O2 extends AbstractC1869C2 {

    /* renamed from: c */
    public long[] f1081c;

    /* renamed from: d */
    public int f1082d;

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1081c = new long[(int) j];
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = 0;
        Arrays.sort(this.f1081c, 0, this.f1082d);
        long j = this.f1082d;
        InterfaceC2062n2 interfaceC2062n2 = this.f1262a;
        interfaceC2062n2.mo931h(j);
        if (!this.f976b) {
            while (i < this.f1082d) {
                interfaceC2062n2.accept(this.f1081c[i]);
                i++;
            }
        } else {
            while (i < this.f1082d && !interfaceC2062n2.mo932m()) {
                interfaceC2062n2.accept(this.f1081c[i]);
                i++;
            }
        }
        interfaceC2062n2.end();
        this.f1081c = null;
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        long[] jArr = this.f1081c;
        int i = this.f1082d;
        this.f1082d = i + 1;
        jArr[i] = j;
    }
}
