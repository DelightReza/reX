package p017j$.util.stream;

import java.util.Arrays;

/* renamed from: j$.util.stream.F2 */
/* loaded from: classes2.dex */
public final class C1884F2 extends AbstractC1864B2 {

    /* renamed from: c */
    public C1958U2 f1004c;

    /* JADX WARN: Type inference failed for: r0v2, types: [j$.util.stream.U2, j$.util.stream.Y2] */
    /* JADX WARN: Type inference failed for: r0v5, types: [j$.util.stream.Y2] */
    /* JADX WARN: Type inference failed for: r0v6, types: [j$.util.stream.Y2] */
    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        ?? abstractC1978Y2;
        if (j >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        if (j <= 0) {
            abstractC1978Y2 = new AbstractC1978Y2();
        } else {
            abstractC1978Y2 = new C1958U2((int) j);
        }
        this.f1004c = abstractC1978Y2;
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    public final void end() {
        int[] iArr = (int[]) this.f1004c.mo951b();
        Arrays.sort(iArr);
        long length = iArr.length;
        InterfaceC2062n2 interfaceC2062n2 = this.f1255a;
        interfaceC2062n2.mo931h(length);
        int i = 0;
        if (!this.f970b) {
            int length2 = iArr.length;
            while (i < length2) {
                interfaceC2062n2.accept(iArr[i]);
                i++;
            }
        } else {
            int length3 = iArr.length;
            while (i < length3) {
                int i2 = iArr[i];
                if (interfaceC2062n2.mo932m()) {
                    break;
                }
                interfaceC2062n2.accept(i2);
                i++;
            }
        }
        interfaceC2062n2.end();
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        this.f1004c.accept(i);
    }
}
