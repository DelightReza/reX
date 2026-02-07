package p017j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import p017j$.time.C1678e;

/* renamed from: j$.util.stream.l */
/* loaded from: classes2.dex */
public final class C2049l implements Collector {

    /* renamed from: a */
    public final Supplier f1283a;

    /* renamed from: b */
    public final BiConsumer f1284b;

    /* renamed from: c */
    public final BinaryOperator f1285c;

    /* renamed from: d */
    public final Function f1286d;

    /* renamed from: e */
    public final Set f1287e;

    public C2049l(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Function function, Set set) {
        this.f1283a = supplier;
        this.f1284b = biConsumer;
        this.f1285c = binaryOperator;
        this.f1286d = function;
        this.f1287e = set;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public C2049l(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Set set) {
        this(supplier, biConsumer, binaryOperator, new C1678e(22), set);
        Set set2 = Collectors.f978a;
    }

    @Override // p017j$.util.stream.Collector
    public final BiConsumer accumulator() {
        return this.f1284b;
    }

    @Override // p017j$.util.stream.Collector
    public final Supplier supplier() {
        return this.f1283a;
    }

    @Override // p017j$.util.stream.Collector
    public final BinaryOperator combiner() {
        return this.f1285c;
    }

    @Override // p017j$.util.stream.Collector
    public final Function finisher() {
        return this.f1286d;
    }

    @Override // p017j$.util.stream.Collector
    public final Set characteristics() {
        return this.f1287e;
    }
}
