package p017j$.util.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import org.mvel2.DataTypes;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1831g0;
import p017j$.util.InterfaceC1779T;
import p017j$.util.Objects;

/* renamed from: j$.util.stream.S2 */
/* loaded from: classes2.dex */
public class C1948S2 extends AbstractC1978Y2 implements DoubleConsumer {
    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: j */
    public final void mo994j(Object obj, int i, int i2, Object obj2) {
        double[] dArr = (double[]) obj;
        DoubleConsumer doubleConsumer = (DoubleConsumer) obj2;
        while (i < i2) {
            doubleConsumer.accept(dArr[i]);
            i++;
        }
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: k */
    public final int mo995k(Object obj) {
        return ((double[]) obj).length;
    }

    @Override // java.lang.Iterable, p017j$.lang.InterfaceC1637a
    public final void forEach(Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            mo953d((DoubleConsumer) consumer);
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(getClass(), "{0} calling SpinedBuffer.OfDouble.forEach(Consumer)");
                throw null;
            }
            AbstractC1636a.m513i((C1943R2) spliterator(), consumer);
        }
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    /* renamed from: r */
    public final Object[] mo996r() {
        return new double[8][];
    }

    @Override // p017j$.util.stream.AbstractC1978Y2
    public final Object newArray(int i) {
        return new double[i];
    }

    @Override // java.util.function.DoubleConsumer
    public void accept(double d) {
        m1006s();
        double[] dArr = (double[]) this.f1149e;
        int i = this.f1213b;
        this.f1213b = i + 1;
        dArr[i] = d;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        InterfaceC1779T interfaceC1779TSpliterator = spliterator();
        Objects.requireNonNull(interfaceC1779TSpliterator);
        return new C1831g0(interfaceC1779TSpliterator);
    }

    @Override // p017j$.util.stream.AbstractC1978Y2, java.lang.Iterable
    /* renamed from: t, reason: merged with bridge method [inline-methods] */
    public InterfaceC1779T spliterator() {
        return new C1943R2(this, 0, this.f1214c, 0, this.f1213b);
    }

    public final String toString() {
        double[] dArr = (double[]) mo951b();
        if (dArr.length < 200) {
            return String.format("%s[length=%d, chunks=%d]%s", getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.f1214c), Arrays.toString(dArr));
        }
        return String.format("%s[length=%d, chunks=%d]%s...", getClass().getSimpleName(), Integer.valueOf(dArr.length), Integer.valueOf(this.f1214c), Arrays.toString(Arrays.copyOf(dArr, DataTypes.EMPTY)));
    }
}
