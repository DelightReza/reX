package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.E2 */
/* loaded from: classes2.dex */
public final class C1879E2 extends AbstractC1859A2 {

    /* renamed from: c */
    public C1948S2 f997c;

    /* JADX WARN: Type inference failed for: r0v2, types: [j$.util.stream.S2, j$.util.stream.Y2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [j$.util.stream.Y2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [j$.util.stream.Y2] */
    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        ?? abstractC1978Y2;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        if (j <= 0) {
            abstractC1978Y2 = new AbstractC1978Y2();
        } else {
            abstractC1978Y2 = new C1948S2((int) j);
        }
        this.f997c = abstractC1978Y2;
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        double[] dArr = (double[]) this.f997c.mo951b();
        Arrays.sort(dArr);
        long length = dArr.length;
        InterfaceC2062n2 interfaceC2062n2 = this.f1249a;
        interfaceC2062n2.mo931h(length);
        int i = 0;
        if (!this.f963b) {
            int length2 = dArr.length;
            while (i < length2) {
                interfaceC2062n2.accept(dArr[i]);
                i++;
            }
        } else {
            int length3 = dArr.length;
            while (i < length3) {
                double d = dArr[i];
                if (interfaceC2062n2.mo932m()) {
                    break;
                }
                interfaceC2062n2.accept(d);
                i++;
            }
        }
        interfaceC2062n2.end();
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f997c.accept(d);
    }
}
