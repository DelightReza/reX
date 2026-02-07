package p017j$.util.stream;

/* renamed from: j$.util.stream.O3 */
/* loaded from: classes2.dex */
public final class C1930O3 extends AbstractC2042j2 implements InterfaceC1979Y3 {

    /* renamed from: b */
    public long f1083b;

    /* renamed from: c */
    public boolean f1084c;

    /* renamed from: d */
    public final /* synthetic */ boolean f1085d;

    /* renamed from: e */
    public final /* synthetic */ C1925N3 f1086e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1930O3(C1925N3 c1925n3, InterfaceC2062n2 interfaceC2062n2, boolean z) {
        super(interfaceC2062n2);
        this.f1086e = c1925n3;
        this.f1085d = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0015  */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void accept(java.lang.Object r7) {
        /*
            r6 = this;
            boolean r0 = r6.f1084c
            if (r0 != 0) goto L15
            j$.util.stream.N3 r0 = r6.f1086e
            java.util.function.Predicate r0 = r0.f1076t
            boolean r0 = r0.test(r7)
            r1 = r0 ^ 1
            r6.f1084c = r1
            if (r0 != 0) goto L13
            goto L15
        L13:
            r0 = 0
            goto L16
        L15:
            r0 = 1
        L16:
            boolean r1 = r6.f1085d
            if (r1 == 0) goto L23
            if (r0 != 0) goto L23
            long r2 = r6.f1083b
            r4 = 1
            long r2 = r2 + r4
            r6.f1083b = r2
        L23:
            if (r1 != 0) goto L29
            if (r0 == 0) goto L28
            goto L29
        L28:
            return
        L29:
            j$.util.stream.n2 r0 = r6.f1274a
            r0.accept(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.stream.C1930O3.accept(java.lang.Object):void");
    }

    @Override // p017j$.util.stream.InterfaceC1979Y3
    /* renamed from: n */
    public final long mo989n() {
        return this.f1083b;
    }
}
