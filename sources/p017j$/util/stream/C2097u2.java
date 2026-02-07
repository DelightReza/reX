package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.u2 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2097u2 implements IntFunction, Consumer {

    /* renamed from: a */
    public final /* synthetic */ int f1348a;

    /* renamed from: accept$j$$util$stream$StreamSpliterators$SliceSpliterator$OfRef$$ExternalSyntheticLambda0 */
    private final void m1065x10ce6cf0(Object obj) {
    }

    /* renamed from: accept$j$$util$stream$StreamSpliterators$SliceSpliterator$OfRef$$ExternalSyntheticLambda1 */
    private final void m1066x10ce6cf1(Object obj) {
    }

    @Override // java.util.function.Consumer
    public void accept(Object obj) {
        int i = this.f1348a;
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f1348a) {
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // java.util.function.IntFunction
    public Object apply(int i) {
        switch (this.f1348a) {
            case 0:
                return new Double[i];
            case 1:
            case 2:
            default:
                return new Double[i];
            case 3:
                return new Integer[i];
            case 4:
                return new Integer[i];
            case 5:
                return new Long[i];
            case 6:
                return new Long[i];
            case 7:
                return new Double[i];
        }
    }
}
