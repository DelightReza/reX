package p017j$.util.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import org.mvel2.DataTypes;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1819e0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.U2 */
/* loaded from: classes2.dex */
public class C1958U2 extends AbstractC1978Y2 implements IntConsumer {
    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: j */
    public final void mo994j(Object obj, int i, int i2, Object obj2) {
        int[] iArr = (int[]) obj;
        IntConsumer intConsumer = (IntConsumer) obj2;
        while (i < i2) {
            intConsumer.accept(iArr[i]);
            i++;
        }
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: k */
    public final int mo995k(Object obj) {
        return ((int[]) obj).length;
    }

    @Override // java.lang.Iterable, p017j$.lang.InterfaceC1637a
    public final void forEach(Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            mo953d((IntConsumer) consumer);
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(getClass(), "{0} calling SpinedBuffer.OfInt.forEach(Consumer)");
                throw null;
            }
            AbstractC1636a.m514j((C1953T2) spliterator(), consumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: r */
    public final Object[] mo996r() {
        return new int[8][];
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    public final Object newArray(int i) {
        return new int[i];
    }

    @Override // java.util.function.IntConsumer
    public void accept(int i) {
        m1006s();
        int[] iArr = (int[]) this.f1149e;
        int i2 = this.f1213b;
        this.f1213b = i2 + 1;
        iArr[i2] = i;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        Spliterator.OfInt ofIntSpliterator = spliterator();
        Objects.requireNonNull(ofIntSpliterator);
        return new C1819e0(ofIntSpliterator);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
    /* renamed from: t, reason: merged with bridge method [inline-methods] */
    public Spliterator.OfInt spliterator() {
        return new C1953T2(this, 0, this.f1214c, 0, this.f1213b);
    }

    public final String toString() {
        int[] iArr = (int[]) mo951b();
        if (iArr.length < 200) {
            return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.f1214c), Arrays.toString(iArr));
        }
        return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(iArr.length), Integer.valueOf(this.f1214c), Arrays.toString(Arrays.copyOf(iArr, DataTypes.EMPTY)));
    }
}
