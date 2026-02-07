package p017j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* renamed from: j$.util.stream.h */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2027h implements Collector {

    /* renamed from: a */
    public final /* synthetic */ Collector f1254a;

    public /* synthetic */ C2027h(Collector collector) {
        this.f1254a = collector;
    }

    @Override // p017j$.util.stream.Collector
    public final /* synthetic */ BiConsumer accumulator() {
        return this.f1254a.accumulator();
    }

    @Override // p017j$.util.stream.Collector
    public final /* synthetic */ Set characteristics() {
        return AbstractC2106w1.m1099j0(this.f1254a.characteristics());
    }

    @Override // p017j$.util.stream.Collector
    public final /* synthetic */ BinaryOperator combiner() {
        return this.f1254a.combiner();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Collector collector = this.f1254a;
        if (obj instanceof C2027h) {
            obj = ((C2027h) obj).f1254a;
        }
        return collector.equals(obj);
    }

    @Override // p017j$.util.stream.Collector
    public final /* synthetic */ Function finisher() {
        return this.f1254a.finisher();
    }

    public final /* synthetic */ int hashCode() {
        return this.f1254a.hashCode();
    }

    @Override // p017j$.util.stream.Collector
    public final /* synthetic */ Supplier supplier() {
        return this.f1254a.supplier();
    }
}
