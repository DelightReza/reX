package p017j$.time;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.C1830g;
import p017j$.util.C1850q;
import p017j$.util.C1851q0;
import p017j$.util.Map;
import p017j$.util.Spliterator;
import p017j$.util.function.BiFunction$CC;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.Function$CC;
import p017j$.util.function.IntPredicate$CC;
import p017j$.util.function.Predicate$CC;
import p017j$.util.stream.AbstractC1985a;
import p017j$.util.stream.C1861B;
import p017j$.util.stream.C1866C;
import p017j$.util.stream.C1885F3;
import p017j$.util.stream.C2050l0;
import p017j$.util.stream.C2055m0;
import p017j$.util.stream.C2058m3;
import p017j$.util.stream.C2068o3;
import p017j$.util.stream.C2078q3;
import p017j$.util.stream.Collectors;
import p017j$.util.stream.EnumC1989a3;
import p017j$.util.stream.IntStream;
import p017j$.util.stream.InterfaceC1871D;
import p017j$.util.stream.InterfaceC2062n2;
import p017j$.util.stream.LongStream;
import p017j$.util.stream.Stream;

/* renamed from: j$.time.t */
/* loaded from: classes2.dex */
public final class C1726t implements InterfaceC1740n, Consumer, IntPredicate, Predicate, Supplier, BinaryOperator, DoubleFunction, Function, LongFunction, BooleanSupplier {

    /* renamed from: a */
    public final /* synthetic */ int f667a;

    /* renamed from: b */
    public Object f668b;

    public /* synthetic */ C1726t(int i) {
        this.f667a = i;
    }

    public /* synthetic */ C1726t(int i, Object obj) {
        this.f667a = i;
        this.f668b = obj;
    }

    public /* synthetic */ IntPredicate and(IntPredicate intPredicate) {
        return IntPredicate$CC.$default$and(this, intPredicate);
    }

    public /* synthetic */ Predicate and(Predicate predicate) {
        return Predicate$CC.$default$and(this, predicate);
    }

    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction$CC.$default$andThen(this, function);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f667a) {
            case 1:
                break;
            case 11:
                break;
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    /* renamed from: andThen, reason: collision with other method in class */
    public /* synthetic */ Function m2950andThen(Function function) {
        return Function$CC.$default$andThen(this, function);
    }

    public /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return false;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public /* synthetic */ C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    public /* synthetic */ IntPredicate negate() {
        return IntPredicate$CC.$default$negate(this);
    }

    /* renamed from: negate, reason: collision with other method in class */
    public /* synthetic */ Predicate m2951negate() {
        return Predicate$CC.$default$negate(this);
    }

    /* renamed from: or */
    public /* synthetic */ IntPredicate m797or(IntPredicate intPredicate) {
        return IntPredicate$CC.$default$or(this, intPredicate);
    }

    /* renamed from: or */
    public /* synthetic */ Predicate m798or(Predicate predicate) {
        return Predicate$CC.$default$or(this, predicate);
    }

    @Override // java.util.function.Predicate
    public boolean test(Object obj) {
        return !((Predicate) this.f668b).test(obj);
    }

    @Override // java.util.function.Function
    public Object apply(Object obj) {
        Object objApply = ((Function) this.f668b).apply(obj);
        if (objApply == null) {
            return null;
        }
        if (objApply instanceof Stream) {
            return Stream.Wrapper.convert((Stream) objApply);
        }
        if (objApply instanceof java.util.stream.Stream) {
            return Stream.VivifiedWrapper.convert((java.util.stream.Stream) objApply);
        }
        if (objApply instanceof IntStream) {
            return IntStream.Wrapper.convert((IntStream) objApply);
        }
        if (objApply instanceof java.util.stream.IntStream) {
            return IntStream.VivifiedWrapper.convert((java.util.stream.IntStream) objApply);
        }
        if (objApply instanceof InterfaceC1871D) {
            return C1866C.m941f((InterfaceC1871D) objApply);
        }
        if (objApply instanceof DoubleStream) {
            return C1861B.m937f((DoubleStream) objApply);
        }
        if (objApply instanceof LongStream) {
            return C2055m0.m1052f((LongStream) objApply);
        }
        if (objApply instanceof java.util.stream.LongStream) {
            return C2050l0.m1047f((java.util.stream.LongStream) objApply);
        }
        C1830g.m910a(objApply.getClass(), "java.util.stream.*Stream");
        throw null;
    }

    @Override // java.util.function.IntPredicate
    public boolean test(int i) {
        return !((IntPredicate) this.f668b).test(i);
    }

    @Override // java.util.function.DoubleFunction
    public Object apply(double d) {
        Object objApply = ((DoubleFunction) this.f668b).apply(d);
        if (objApply == null) {
            return null;
        }
        if (objApply instanceof InterfaceC1871D) {
            return C1866C.m941f((InterfaceC1871D) objApply);
        }
        if (objApply instanceof DoubleStream) {
            return C1861B.m937f((DoubleStream) objApply);
        }
        C1830g.m910a(objApply.getClass(), "java.util.stream.DoubleStream");
        throw null;
    }

    @Override // java.util.function.LongFunction
    public Object apply(long j) {
        Object objApply = ((LongFunction) this.f668b).apply(j);
        if (objApply == null) {
            return null;
        }
        if (objApply instanceof LongStream) {
            return C2055m0.m1052f((LongStream) objApply);
        }
        if (objApply instanceof java.util.stream.LongStream) {
            return C2050l0.m1047f((java.util.stream.LongStream) objApply);
        }
        C1830g.m910a(objApply.getClass(), "java.util.stream.LongStream");
        throw null;
    }

    @Override // java.util.function.BooleanSupplier
    public boolean getAsBoolean() {
        switch (this.f667a) {
            case 14:
                C2058m3 c2058m3 = (C2058m3) this.f668b;
                return c2058m3.f1229d.tryAdvance(c2058m3.f1230e);
            case 15:
                C2068o3 c2068o3 = (C2068o3) this.f668b;
                return c2068o3.f1229d.tryAdvance(c2068o3.f1230e);
            case 16:
                C2078q3 c2078q3 = (C2078q3) this.f668b;
                return c2078q3.f1229d.tryAdvance(c2078q3.f1230e);
            default:
                C1885F3 c1885f3 = (C1885F3) this.f668b;
                return c1885f3.f1229d.tryAdvance(c1885f3.f1230e);
        }
    }

    /* renamed from: s */
    public void m799s(EnumC1989a3 enumC1989a3) {
        ((EnumMap) ((Map) this.f668b)).put((EnumMap) enumC1989a3, (EnumC1989a3) 1);
    }

    @Override // java.util.function.Supplier
    public Object get() {
        switch (this.f667a) {
            case 4:
                return ((AbstractC1985a) this.f668b).m1011F0(0);
            case 5:
                return (Spliterator) this.f668b;
            default:
                CharSequence charSequence = (CharSequence) this.f668b;
                Set set = Collectors.f978a;
                return new C1851q0(charSequence);
        }
    }

    @Override // java.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        BinaryOperator binaryOperator = (BinaryOperator) this.f668b;
        Map map = (Map) obj;
        Set set = Collectors.f978a;
        for (Map.Entry entry : ((Map) obj2).entrySet()) {
            Map.EL.m855a(map, entry.getKey(), entry.getValue(), binaryOperator);
        }
        return map;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public long mo542D(InterfaceC1744r interfaceC1744r) {
        throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f690a) {
            return (ZoneId) this.f668b;
        }
        return AbstractC1745s.m815c(this, c1678e);
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public void m971v(Object obj) {
        switch (this.f667a) {
            case 1:
                ((Consumer) this.f668b).m971v(new C1850q((Map.Entry) obj));
                break;
            case 11:
                ((InterfaceC2062n2) this.f668b).m971v((InterfaceC2062n2) obj);
                break;
            default:
                ((List) this.f668b).add(obj);
                break;
        }
    }
}
