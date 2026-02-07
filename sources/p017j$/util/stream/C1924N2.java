package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.N2 */
/* loaded from: classes2.dex */
public final class C1924N2 extends AbstractC1864B2 {

    /* renamed from: c */
    public int[] f1073c;

    /* renamed from: d */
    public int f1074d;

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1073c = new int[(int) j];
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = 0;
        Arrays.sort(this.f1073c, 0, this.f1074d);
        long j = this.f1074d;
        InterfaceC2062n2 interfaceC2062n2 = this.f1255a;
        interfaceC2062n2.mo931h(j);
        if (!this.f970b) {
            while (i < this.f1074d) {
                interfaceC2062n2.accept(this.f1073c[i]);
                i++;
            }
        } else {
            while (i < this.f1074d && !interfaceC2062n2.mo932m()) {
                interfaceC2062n2.accept(this.f1073c[i]);
                i++;
            }
        }
        interfaceC2062n2.end();
        this.f1073c = null;
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        int[] iArr = this.f1073c;
        int i2 = this.f1074d;
        this.f1074d = i2 + 1;
        iArr[i2] = i;
    }
}
