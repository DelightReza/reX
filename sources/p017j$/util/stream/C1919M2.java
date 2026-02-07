package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.M2 */
/* loaded from: classes2.dex */
public final class C1919M2 extends AbstractC1859A2 {

    /* renamed from: c */
    public double[] f1066c;

    /* renamed from: d */
    public int f1067d;

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        this.f1066c = new double[(int) j];
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int i = 0;
        Arrays.sort(this.f1066c, 0, this.f1067d);
        long j = this.f1067d;
        InterfaceC2062n2 interfaceC2062n2 = this.f1249a;
        interfaceC2062n2.mo931h(j);
        if (!this.f963b) {
            while (i < this.f1067d) {
                interfaceC2062n2.accept(this.f1066c[i]);
                i++;
            }
        } else {
            while (i < this.f1067d && !interfaceC2062n2.mo932m()) {
                interfaceC2062n2.accept(this.f1066c[i]);
                i++;
            }
        }
        interfaceC2062n2.end();
        this.f1066c = null;
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        double[] dArr = this.f1066c;
        int i = this.f1067d;
        this.f1067d = i + 1;
        dArr[i] = d;
    }
}
