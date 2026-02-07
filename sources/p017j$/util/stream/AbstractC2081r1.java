package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.r1 */
/* loaded from: classes2.dex */
public abstract class AbstractC2081r1 extends CountedCompleter implements InterfaceC2062n2 {

    /* renamed from: a */
    public final Spliterator f1325a;

    /* renamed from: b */
    public final AbstractC2106w1 f1326b;

    /* renamed from: c */
    public final long f1327c;

    /* renamed from: d */
    public final long f1328d;

    /* renamed from: e */
    public final long f1329e;

    /* renamed from: f */
    public int f1330f;

    /* renamed from: g */
    public int f1331g;

    /* renamed from: a */
    public abstract AbstractC2081r1 mo1053a(Spliterator spliterator, long j, long j2);

    public /* synthetic */ void accept(double d) {
        AbstractC2106w1.m1107z();
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        AbstractC2106w1.m1074G();
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        AbstractC2106w1.m1075H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    public final /* synthetic */ void end() {
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final /* synthetic */ boolean mo932m() {
        return false;
    }

    public AbstractC2081r1(Spliterator spliterator, AbstractC2106w1 abstractC2106w1, int i) {
        this.f1325a = spliterator;
        this.f1326b = abstractC2106w1;
        this.f1327c = AbstractC2003d.m1033e(spliterator.estimateSize());
        this.f1328d = 0L;
        this.f1329e = i;
    }

    public AbstractC2081r1(AbstractC2081r1 abstractC2081r1, Spliterator spliterator, long j, long j2, int i) {
        super(abstractC2081r1);
        this.f1325a = spliterator;
        this.f1326b = abstractC2081r1.f1326b;
        this.f1327c = abstractC2081r1.f1327c;
        this.f1328d = j;
        this.f1329e = j2;
        if (j < 0 || j2 < 0 || (j + j2) - 1 >= i) {
            throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)));
        }
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.f1325a;
        AbstractC2081r1 abstractC2081r1Mo1053a = this;
        while (spliterator.estimateSize() > abstractC2081r1Mo1053a.f1327c && (spliteratorTrySplit = spliterator.trySplit()) != null) {
            abstractC2081r1Mo1053a.setPendingCount(1);
            long jEstimateSize = spliteratorTrySplit.estimateSize();
            AbstractC2081r1 abstractC2081r1 = abstractC2081r1Mo1053a;
            abstractC2081r1.mo1053a(spliteratorTrySplit, abstractC2081r1Mo1053a.f1328d, jEstimateSize).fork();
            abstractC2081r1Mo1053a = abstractC2081r1.mo1053a(spliterator, abstractC2081r1.f1328d + jEstimateSize, abstractC2081r1.f1329e - jEstimateSize);
        }
        AbstractC2081r1 abstractC2081r12 = abstractC2081r1Mo1053a;
        abstractC2081r12.f1326b.mo1017t0(spliterator, abstractC2081r12);
        abstractC2081r12.propagateCompletion();
    }

    @Override // p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        long j2 = this.f1329e;
        if (j > j2) {
            throw new IllegalStateException("size passed to Sink.begin exceeds array length");
        }
        int i = (int) this.f1328d;
        this.f1330f = i;
        this.f1331g = i + ((int) j2);
    }
}
