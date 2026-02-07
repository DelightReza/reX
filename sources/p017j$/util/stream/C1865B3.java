package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.B3 */
/* loaded from: classes2.dex */
public final class C1865B3 extends AbstractC1875D3 implements Spliterator, Consumer {

    /* renamed from: f */
    public Object f971f;

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

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

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f971f = obj;
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        while (m945c() != EnumC1870C3.NO_MORE && this.f985a.tryAdvance(this)) {
            if (m944a(1L) == 1) {
                consumer.accept(this.f971f);
                this.f971f = null;
                return true;
            }
        }
        return false;
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        C2037i3 c2037i3 = null;
        while (true) {
            EnumC1870C3 enumC1870C3M945c = m945c();
            if (enumC1870C3M945c == EnumC1870C3.NO_MORE) {
                return;
            }
            EnumC1870C3 enumC1870C3 = EnumC1870C3.MAYBE_MORE;
            Spliterator spliterator = this.f985a;
            if (enumC1870C3M945c == enumC1870C3) {
                int i = this.f987c;
                if (c2037i3 == null) {
                    c2037i3 = new C2037i3(i);
                } else {
                    c2037i3.f1275a = 0;
                }
                long j = 0;
                while (spliterator.tryAdvance(c2037i3)) {
                    j++;
                    if (j >= i) {
                        break;
                    }
                }
                if (j == 0) {
                    return;
                }
                long jM944a = m944a(j);
                for (int i2 = 0; i2 < jM944a; i2++) {
                    consumer.accept(c2037i3.f1263b[i2]);
                }
            } else {
                spliterator.forEachRemaining(consumer);
                return;
            }
        }
    }

    @Override // p017j$.util.stream.AbstractC1875D3
    /* renamed from: b */
    public final Spliterator mo940b(Spliterator spliterator) {
        return new C1865B3(spliterator, this);
    }
}
