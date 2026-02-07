package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.A3 */
/* loaded from: classes2.dex */
public abstract class AbstractC1860A3 extends AbstractC1875D3 implements InterfaceC1789b0 {
    /* renamed from: d */
    public abstract void mo935d(Object obj);

    /* renamed from: e */
    public abstract AbstractC2031h3 mo936e(int i);

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return Spliterator.CC.$default$getComparator(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        while (m945c() != EnumC1870C3.NO_MORE && ((InterfaceC1789b0) this.f985a).tryAdvance(this)) {
            if (m944a(1L) == 1) {
                mo935d(obj);
                return true;
            }
        }
        return false;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
        AbstractC2031h3 abstractC2031h3Mo936e = null;
        while (true) {
            EnumC1870C3 enumC1870C3M945c = m945c();
            if (enumC1870C3M945c == EnumC1870C3.NO_MORE) {
                return;
            }
            EnumC1870C3 enumC1870C3 = EnumC1870C3.MAYBE_MORE;
            Spliterator spliterator = this.f985a;
            if (enumC1870C3M945c == enumC1870C3) {
                int i = this.f987c;
                if (abstractC2031h3Mo936e == null) {
                    abstractC2031h3Mo936e = mo936e(i);
                } else {
                    abstractC2031h3Mo936e.f1256b = 0;
                }
                long j = 0;
                while (((InterfaceC1789b0) spliterator).tryAdvance(abstractC2031h3Mo936e)) {
                    j++;
                    if (j >= i) {
                        break;
                    }
                }
                if (j == 0) {
                    return;
                } else {
                    abstractC2031h3Mo936e.mo1039a(obj, m944a(j));
                }
            } else {
                ((InterfaceC1789b0) spliterator).forEachRemaining(obj);
                return;
            }
        }
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        forEachRemaining((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
        return tryAdvance((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        forEachRemaining((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
        return tryAdvance((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        forEachRemaining((Object) doubleConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
        return tryAdvance((Object) doubleConsumer);
    }
}
