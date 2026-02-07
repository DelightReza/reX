package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.G2 */
/* loaded from: classes2.dex */
public final class C1889G2 extends AbstractC1869C2 {

    /* renamed from: c */
    public C1968W2 f1010c;

    /* JADX WARN: Type inference failed for: r0v2, types: [j$.util.stream.W2, j$.util.stream.Y2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [j$.util.stream.Y2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [j$.util.stream.Y2] */
    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        ?? abstractC1978Y2;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        if (j <= 0) {
            abstractC1978Y2 = new AbstractC1978Y2();
        } else {
            abstractC1978Y2 = new C1968W2((int) j);
        }
        this.f1010c = abstractC1978Y2;
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        long[] jArr = (long[]) this.f1010c.mo951b();
        Arrays.sort(jArr);
        long length = jArr.length;
        InterfaceC2062n2 interfaceC2062n2 = this.f1262a;
        interfaceC2062n2.mo931h(length);
        int i = 0;
        if (!this.f976b) {
            int length2 = jArr.length;
            while (i < length2) {
                interfaceC2062n2.accept(jArr[i]);
                i++;
            }
        } else {
            int length3 = jArr.length;
            while (i < length3) {
                long j = jArr[i];
                if (interfaceC2062n2.mo932m()) {
                    break;
                }
                interfaceC2062n2.accept(j);
                i++;
            }
        }
        interfaceC2062n2.end();
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        this.f1010c.accept(j);
    }
}
