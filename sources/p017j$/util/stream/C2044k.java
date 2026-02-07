package p017j$.util.stream;

import java.util.LinkedHashSet;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import p017j$.util.C1761A;
import p017j$.util.C1762B;
import p017j$.util.C2127w;
import p017j$.util.Optional;
import p017j$.util.OptionalInt;
import p017j$.util.function.BiConsumer$CC;
import p017j$.util.function.Function$CC;
import p017j$.util.function.Predicate$CC;

/* renamed from: j$.util.stream.k */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2044k implements Function, Supplier, BiConsumer, DoubleBinaryOperator, ObjDoubleConsumer, DoubleFunction, ToDoubleFunction, IntFunction, Predicate, ToIntFunction, IntBinaryOperator {

    /* renamed from: a */
    public final /* synthetic */ int f1276a;

    public /* synthetic */ C2044k(int i) {
        this.f1276a = i;
    }

    public /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.f1276a) {
        }
        return Predicate$CC.$default$and(this, predicate);
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        switch (this.f1276a) {
        }
        return BiConsumer$CC.$default$andThen(this, biConsumer);
    }

    public /* synthetic */ Function andThen(Function function) {
        return Function$CC.$default$andThen(this, function);
    }

    @Override // java.util.function.DoubleFunction
    public Object apply(double d) {
        return Double.valueOf(d);
    }

    @Override // java.util.function.Function
    public Object apply(Object obj) {
        return ((StringBuilder) obj).toString();
    }

    @Override // java.util.function.DoubleBinaryOperator
    public double applyAsDouble(double d, double d2) {
        switch (this.f1276a) {
            case 5:
                return Math.min(d, d2);
            default:
                return Math.max(d, d2);
        }
    }

    @Override // java.util.function.IntBinaryOperator
    public int applyAsInt(int i, int i2) {
        return Math.min(i, i2);
    }

    public /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }

    public /* synthetic */ Predicate negate() {
        switch (this.f1276a) {
        }
        return Predicate$CC.$default$negate(this);
    }

    /* renamed from: or */
    public /* synthetic */ Predicate m1045or(Predicate predicate) {
        switch (this.f1276a) {
        }
        return Predicate$CC.$default$or(this, predicate);
    }

    @Override // java.util.function.Predicate
    public boolean test(Object obj) {
        switch (this.f1276a) {
            case 17:
                return ((C1761A) obj).f758a;
            case 18:
            case 20:
            default:
                return ((Optional) obj).isPresent();
            case 19:
                return ((OptionalInt) obj).f780a;
            case 21:
                return ((C1762B) obj).f761a;
        }
    }

    @Override // java.util.function.Supplier
    public Object get() {
        switch (this.f1276a) {
            case 1:
                return new LinkedHashSet();
            case 6:
                return new double[4];
            case 15:
                return new double[3];
            case 18:
                return new C1881F();
            case 20:
                return new C1886G();
            case 22:
                return new C1891H();
            default:
                return new C1896I();
        }
    }

    @Override // java.util.function.ToDoubleFunction
    public double applyAsDouble(Object obj) {
        return ((Double) obj).doubleValue();
    }

    @Override // java.util.function.ObjDoubleConsumer
    public void accept(Object obj, double d) {
        switch (this.f1276a) {
            case 7:
                double[] dArr = (double[]) obj;
                dArr[2] = dArr[2] + 1.0d;
                Collectors.m942a(dArr, d);
                dArr[3] = dArr[3] + d;
                break;
            case 8:
            default:
                double[] dArr2 = (double[]) obj;
                Collectors.m942a(dArr2, d);
                dArr2[2] = dArr2[2] + d;
                break;
            case 9:
                ((C2127w) obj).accept(d);
                break;
        }
    }

    @Override // java.util.function.ToIntFunction
    public int applyAsInt(Object obj) {
        return ((Integer) obj).intValue();
    }

    @Override // java.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        switch (this.f1276a) {
            case 2:
                ((LinkedHashSet) obj).add(obj2);
                break;
            case 3:
                ((LinkedHashSet) obj).addAll((LinkedHashSet) obj2);
                break;
            case 4:
                double[] dArr = (double[]) obj;
                double[] dArr2 = (double[]) obj2;
                Collectors.m942a(dArr, dArr2[0]);
                Collectors.m942a(dArr, dArr2[1]);
                dArr[2] = dArr[2] + dArr2[2];
                break;
            case 5:
            case 6:
            case 7:
            default:
                ((C2127w) obj).m1118a((C2127w) obj2);
                break;
            case 8:
                double[] dArr3 = (double[]) obj;
                double[] dArr4 = (double[]) obj2;
                Collectors.m942a(dArr3, dArr4[0]);
                Collectors.m942a(dArr3, dArr4[1]);
                dArr3[2] = dArr3[2] + dArr4[2];
                dArr3[3] = dArr3[3] + dArr4[3];
                break;
        }
    }

    @Override // java.util.function.IntFunction
    public Object apply(int i) {
        switch (this.f1276a) {
            case 13:
                return new Double[i];
            case 25:
                return new Object[i];
            case 26:
                return new Integer[i];
            default:
                return Integer.valueOf(i);
        }
    }
}
