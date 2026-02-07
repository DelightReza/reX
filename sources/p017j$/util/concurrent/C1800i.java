package p017j$.util.concurrent;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.concurrent.i */
/* loaded from: classes2.dex */
public final class C1800i extends C1806o implements Spliterator {

    /* renamed from: i */
    public final /* synthetic */ int f832i;

    /* renamed from: j */
    public long f833j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1800i(C1802k[] c1802kArr, int i, int i2, int i3, long j, int i4) {
        super(c1802kArr, i, i2, i3);
        this.f832i = i4;
        this.f833j = j;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        switch (this.f832i) {
            case 0:
                return 4353;
            default:
                return 4352;
        }
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        switch (this.f832i) {
        }
        return Spliterator.CC.$default$getComparator(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        switch (this.f832i) {
        }
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        switch (this.f832i) {
        }
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        switch (this.f832i) {
            case 0:
                int i = this.f850f;
                int i2 = this.f851g;
                int i3 = (i + i2) >>> 1;
                if (i3 <= i) {
                    return null;
                }
                C1802k[] c1802kArr = this.f845a;
                this.f851g = i3;
                long j = this.f833j >>> 1;
                this.f833j = j;
                return new C1800i(c1802kArr, this.f852h, i3, i2, j, 0);
            default:
                int i4 = this.f850f;
                int i5 = this.f851g;
                int i6 = (i4 + i5) >>> 1;
                if (i6 <= i4) {
                    return null;
                }
                C1802k[] c1802kArr2 = this.f845a;
                this.f851g = i6;
                long j2 = this.f833j >>> 1;
                this.f833j = j2;
                return new C1800i(c1802kArr2, this.f852h, i6, i5, j2, 1);
        }
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        switch (this.f832i) {
            case 0:
                consumer.getClass();
                while (true) {
                    C1802k c1802kM892a = m892a();
                    if (c1802kM892a == null) {
                        break;
                    } else {
                        consumer.accept(c1802kM892a.f838b);
                    }
                }
            default:
                consumer.getClass();
                while (true) {
                    C1802k c1802kM892a2 = m892a();
                    if (c1802kM892a2 == null) {
                        break;
                    } else {
                        consumer.accept(c1802kM892a2.f839c);
                    }
                }
        }
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        switch (this.f832i) {
            case 0:
                consumer.getClass();
                C1802k c1802kM892a = m892a();
                if (c1802kM892a != null) {
                    consumer.accept(c1802kM892a.f838b);
                    break;
                }
                break;
            default:
                consumer.getClass();
                C1802k c1802kM892a2 = m892a();
                if (c1802kM892a2 != null) {
                    consumer.accept(c1802kM892a2.f839c);
                    break;
                }
                break;
        }
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        switch (this.f832i) {
        }
        return this.f833j;
    }
}
