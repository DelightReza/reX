package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import p017j$.util.C2128x;
import p017j$.util.C2130z;
import p017j$.util.function.BiConsumer$CC;
import p017j$.util.function.BiFunction$CC;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.U */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1955U implements ObjIntConsumer, BiConsumer, IntBinaryOperator, Supplier, ObjLongConsumer, LongBinaryOperator, ToLongFunction, IntFunction, LongFunction, Consumer, BinaryOperator {

    /* renamed from: a */
    public final /* synthetic */ int f1130a;

    public /* synthetic */ C1955U(int i) {
        this.f1130a = i;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public void m971v(Object obj) {
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        switch (this.f1130a) {
            case 1:
                break;
            case 6:
                break;
            case 10:
                break;
        }
        return BiConsumer$CC.$default$andThen(this, biConsumer);
    }

    public /* synthetic */ BiFunction andThen(Function function) {
        switch (this.f1130a) {
        }
        return BiFunction$CC.$default$andThen(this, function);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // java.util.function.LongFunction
    public Object apply(long j) {
        switch (this.f1130a) {
            case 12:
                return Long.valueOf(j);
            case 20:
                return AbstractC2106w1.m1093b0(j);
            case 22:
                return AbstractC2106w1.m1101l0(j);
            default:
                return AbstractC2106w1.m1102m0(j);
        }
    }

    @Override // java.util.function.IntBinaryOperator
    public int applyAsInt(int i, int i2) {
        switch (this.f1130a) {
            case 2:
                return i + i2;
            default:
                return Math.max(i, i2);
        }
    }

    @Override // java.util.function.LongBinaryOperator
    public long applyAsLong(long j, long j2) {
        switch (this.f1130a) {
            case 8:
                return Math.min(j, j2);
            case 16:
                return Math.max(j, j2);
            default:
                return j + j2;
        }
    }

    @Override // java.util.function.IntFunction
    public Object apply(int i) {
        switch (this.f1130a) {
            case 11:
                return new Long[i];
            case 19:
                return new Object[i];
            case 27:
                return new Object[i];
            case 28:
                return new Integer[i];
            default:
                return new Long[i];
        }
    }

    @Override // java.util.function.ToLongFunction
    public long applyAsLong(Object obj) {
        return ((Long) obj).longValue();
    }

    @Override // java.util.function.Supplier
    public Object get() {
        switch (this.f1130a) {
            case 4:
                return new long[2];
            default:
                return new long[2];
        }
    }

    @Override // java.util.function.ObjLongConsumer
    public void accept(Object obj, long j) {
        switch (this.f1130a) {
            case 7:
                ((C2130z) obj).accept(j);
                break;
            default:
                long[] jArr = (long[]) obj;
                jArr[0] = jArr[0] + 1;
                jArr[1] = jArr[1] + j;
                break;
        }
    }

    @Override // java.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        switch (this.f1130a) {
            case 1:
                ((C2128x) obj).m1120a((C2128x) obj2);
                break;
            case 6:
                long[] jArr = (long[]) obj;
                long[] jArr2 = (long[]) obj2;
                jArr[0] = jArr[0] + jArr2[0];
                jArr[1] = jArr[1] + jArr2[1];
                break;
            case 10:
                ((C2130z) obj).m1121a((C2130z) obj2);
                break;
            default:
                long[] jArr3 = (long[]) obj;
                long[] jArr4 = (long[]) obj2;
                jArr3[0] = jArr3[0] + jArr4[0];
                jArr3[1] = jArr3[1] + jArr4[1];
                break;
        }
    }

    @Override // java.util.function.ObjIntConsumer
    public void accept(Object obj, int i) {
        switch (this.f1130a) {
            case 0:
                ((C2128x) obj).accept(i);
                break;
            default:
                long[] jArr = (long[]) obj;
                jArr[0] = jArr[0] + 1;
                jArr[1] = jArr[1] + i;
                break;
        }
    }

    @Override // java.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        switch (this.f1130a) {
            case 21:
                return new C1922N0((InterfaceC1857A0) obj, (InterfaceC1857A0) obj2);
            case 22:
            case 24:
            default:
                return new C1941R0((InterfaceC1887G0) obj, (InterfaceC1887G0) obj2);
            case 23:
                return new C1927O0((InterfaceC1867C0) obj, (InterfaceC1867C0) obj2);
            case 25:
                return new C1932P0((InterfaceC1877E0) obj, (InterfaceC1877E0) obj2);
        }
    }
}
