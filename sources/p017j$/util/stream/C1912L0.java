package p017j$.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.LongFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.L0 */
/* loaded from: classes2.dex */
public final class C1912L0 extends C1917M0 {

    /* renamed from: k */
    public final /* synthetic */ int f1055k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1912L0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, LongFunction longFunction, BinaryOperator binaryOperator, int i) {
        super(abstractC2106w1, spliterator, longFunction, binaryOperator);
        this.f1055k = i;
    }

    @Override // p017j$.util.stream.C1917M0, p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        switch (this.f1055k) {
        }
        return new C1917M0(this, spliterator);
    }

    @Override // p017j$.util.stream.C1917M0, p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    public final /* bridge */ /* synthetic */ Object mo972a() {
        switch (this.f1055k) {
        }
        return mo972a();
    }
}
