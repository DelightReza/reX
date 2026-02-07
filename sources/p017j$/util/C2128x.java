package p017j$.util;

import java.util.function.IntConsumer;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.x */
/* loaded from: classes2.dex */
public final class C2128x implements IntConsumer {
    private long count;
    private long sum;
    private int min = ConnectionsManager.DEFAULT_DATACENTER_ID;
    private int max = TLObject.FLAG_31;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.count++;
        this.sum += i;
        this.min = Math.min(this.min, i);
        this.max = Math.max(this.max, i);
    }

    /* renamed from: a */
    public final void m1120a(C2128x c2128x) {
        this.count += c2128x.count;
        this.sum += c2128x.sum;
        this.min = Math.min(this.min, c2128x.min);
        this.max = Math.max(this.max, c2128x.max);
    }

    public final String toString() {
        String simpleName = C2128x.class.getSimpleName();
        Long lValueOf = Long.valueOf(this.count);
        Long lValueOf2 = Long.valueOf(this.sum);
        Integer numValueOf = Integer.valueOf(this.min);
        long j = this.count;
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", simpleName, lValueOf, lValueOf2, numValueOf, Double.valueOf(j > 0 ? this.sum / j : 0.0d), Integer.valueOf(this.max));
    }
}
