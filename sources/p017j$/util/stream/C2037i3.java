package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.i3 */
/* loaded from: classes2.dex */
public final class C2037i3 extends AbstractC2043j3 implements Consumer {

    /* renamed from: b */
    public final Object[] f1263b;

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public C2037i3(int i) {
        this.f1263b = new Object[i];
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.f1275a;
        this.f1275a = i + 1;
        this.f1263b[i] = obj;
    }
}
