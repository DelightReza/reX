package p017j$.util.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.mvel2.DataTypes;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1821f0;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Objects;

/* renamed from: j$.util.stream.W2 */
/* loaded from: classes2.dex */
public class C1968W2 extends AbstractC1978Y2 implements LongConsumer {
    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: j */
    public final void mo994j(Object obj, int i, int i2, Object obj2) {
        long[] jArr = (long[]) obj;
        LongConsumer longConsumer = (LongConsumer) obj2;
        while (i < i2) {
            longConsumer.accept(jArr[i]);
            i++;
        }
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: k */
    public final int mo995k(Object obj) {
        return ((long[]) obj).length;
    }

    @Override // java.lang.Iterable, p017j$.lang.InterfaceC1637a
    public final void forEach(Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            mo953d((LongConsumer) consumer);
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(getClass(), "{0} calling SpinedBuffer.OfLong.forEach(Consumer)");
                throw null;
            }
            AbstractC1636a.m515k((C1963V2) spliterator(), consumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: r */
    public final Object[] mo996r() {
        return new long[8][];
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    public final Object newArray(int i) {
        return new long[i];
    }

    @Override // java.util.function.LongConsumer
    public void accept(long j) {
        m1006s();
        long[] jArr = (long[]) this.f1149e;
        int i = this.f1213b;
        this.f1213b = i + 1;
        jArr[i] = j;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        InterfaceC1784Y interfaceC1784YSpliterator = spliterator();
        Objects.requireNonNull(interfaceC1784YSpliterator);
        return new C1821f0(interfaceC1784YSpliterator);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
    /* renamed from: t, reason: merged with bridge method [inline-methods] */
    public InterfaceC1784Y spliterator() {
        return new C1963V2(this, 0, this.f1214c, 0, this.f1213b);
    }

    public final String toString() {
        long[] jArr = (long[]) mo951b();
        if (jArr.length < 200) {
            return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.f1214c), Arrays.toString(jArr));
        }
        return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(jArr.length), Integer.valueOf(this.f1214c), Arrays.toString(Arrays.copyOf(jArr, DataTypes.EMPTY)));
    }
}
