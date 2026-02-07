package p017j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* renamed from: j$.util.stream.i */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2033i implements Collector {

    /* renamed from: a */
    public final /* synthetic */ Collector f1261a;

    public /* synthetic */ C2033i(Collector collector) {
        this.f1261a = collector;
    }

    @Override // java.util.stream.Collector
    public final /* synthetic */ BiConsumer accumulator() {
        return this.f1261a.accumulator();
    }

    @Override // java.util.stream.Collector
    public final /* synthetic */ Set characteristics() {
        return AbstractC2106w1.m1099j0(this.f1261a.characteristics());
    }

    @Override // java.util.stream.Collector
    public final /* synthetic */ BinaryOperator combiner() {
        return this.f1261a.combiner();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Collector collector = this.f1261a;
        if (obj instanceof C2033i) {
            obj = ((C2033i) obj).f1261a;
        }
        return collector.equals(obj);
    }

    @Override // java.util.stream.Collector
    public final /* synthetic */ Function finisher() {
        return this.f1261a.finisher();
    }

    public final /* synthetic */ int hashCode() {
        return this.f1261a.hashCode();
    }

    @Override // java.util.stream.Collector
    public final /* synthetic */ Supplier supplier() {
        return this.f1261a.supplier();
    }
}
