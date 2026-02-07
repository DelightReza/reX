package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import p017j$.time.C1726t;
import p017j$.util.C1761A;
import p017j$.util.C2127w;
import p017j$.util.InterfaceC1766F;
import p017j$.util.InterfaceC1779T;

/* renamed from: j$.util.stream.D */
/* loaded from: classes2.dex */
public interface InterfaceC1871D extends BaseStream {
    /* renamed from: a */
    InterfaceC1871D mo918a(C1726t c1726t);

    C1761A average();

    /* renamed from: b */
    InterfaceC1871D mo919b();

    Stream boxed();

    /* renamed from: c */
    InterfaceC1871D mo920c();

    Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer);

    long count();

    /* renamed from: d */
    InterfaceC1871D mo921d();

    InterfaceC1871D distinct();

    /* renamed from: e */
    InterfaceC1871D mo922e();

    C1761A findAny();

    C1761A findFirst();

    void forEach(DoubleConsumer doubleConsumer);

    void forEachOrdered(DoubleConsumer doubleConsumer);

    InterfaceC1766F iterator();

    /* renamed from: l */
    boolean mo923l();

    InterfaceC1871D limit(long j);

    Stream mapToObj(DoubleFunction doubleFunction);

    C1761A max();

    C1761A min();

    @Override // p017j$.util.stream.BaseStream
    InterfaceC1871D parallel();

    InterfaceC1871D peek(DoubleConsumer doubleConsumer);

    /* renamed from: q */
    boolean mo924q();

    /* renamed from: r */
    LongStream mo926r();

    double reduce(double d, DoubleBinaryOperator doubleBinaryOperator);

    C1761A reduce(DoubleBinaryOperator doubleBinaryOperator);

    @Override // p017j$.util.stream.BaseStream
    InterfaceC1871D sequential();

    InterfaceC1871D skip(long j);

    InterfaceC1871D sorted();

    InterfaceC1779T spliterator();

    double sum();

    C2127w summaryStatistics();

    double[] toArray();

    /* renamed from: w */
    IntStream mo927w();

    /* renamed from: y */
    boolean mo928y();
}
