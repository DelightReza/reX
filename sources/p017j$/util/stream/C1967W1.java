package p017j$.util.stream;

/* renamed from: j$.util.stream.W1 */
/* loaded from: classes2.dex */
public final class C1967W1 extends AbstractC1972X1 {
    @Override // p017j$.util.stream.AbstractC1947S1, java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.f1138b);
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        this.f1138b += ((AbstractC1972X1) interfaceC1942R1).f1138b;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f1138b++;
    }
}
