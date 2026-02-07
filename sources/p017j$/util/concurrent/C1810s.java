package p017j$.util.concurrent;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import p017j$.util.Spliterator;
import p017j$.util.function.BiConsumer$CC;
import p017j$.util.function.BiFunction$CC;
import p017j$.util.function.Consumer$CC;
import p017j$.util.stream.AbstractC1985a;
import p017j$.util.stream.AbstractC2085s0;
import p017j$.util.stream.AbstractC2106w1;
import p017j$.util.stream.C2048k3;
import p017j$.util.stream.C2065o0;
import p017j$.util.stream.C2070p0;
import p017j$.util.stream.C2095u0;
import p017j$.util.stream.EnumC1995b3;
import p017j$.util.stream.EnumC2001c3;
import p017j$.util.stream.EnumC2090t0;
import p017j$.util.stream.InterfaceC1910K3;

/* renamed from: j$.util.concurrent.s */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1810s implements BiConsumer, BiFunction, Consumer, Supplier, InterfaceC1910K3 {

    /* renamed from: a */
    public final /* synthetic */ int f863a;

    /* renamed from: b */
    public final Object f864b;

    /* renamed from: c */
    public final Object f865c;

    public /* synthetic */ C1810s(int i, Object obj, Object obj2) {
        this.f863a = i;
        this.f864b = obj;
        this.f865c = obj2;
    }

    public /* synthetic */ C1810s(BiFunction biFunction, Function function) {
        this.f863a = 2;
        this.f865c = biFunction;
        this.f864b = function;
    }

    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        switch (this.f863a) {
        }
        return BiConsumer$CC.$default$andThen(this, biConsumer);
    }

    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction$CC.$default$andThen(this, function);
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.f863a) {
            case 3:
                break;
            case 4:
                break;
            case 8:
                break;
        }
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // java.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((Function) this.f864b).apply(((BiFunction) this.f865c).apply(obj, obj2));
    }

    @Override // java.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        switch (this.f863a) {
            case 0:
                ConcurrentMap concurrentMap = (ConcurrentMap) this.f864b;
                BiFunction biFunction = (BiFunction) this.f865c;
                while (!concurrentMap.replace(obj, obj2, biFunction.apply(obj, obj2)) && (obj2 = concurrentMap.get(obj)) != null) {
                }
            default:
                BiConsumer biConsumer = (BiConsumer) this.f864b;
                BiConsumer biConsumer2 = (BiConsumer) this.f865c;
                biConsumer.accept(obj, obj2);
                biConsumer2.accept(obj, obj2);
                break;
        }
    }

    @Override // java.util.function.Supplier
    public Object get() {
        switch (this.f863a) {
            case 5:
                return new C2070p0((EnumC2090t0) this.f864b, (IntPredicate) this.f865c);
            default:
                return new C2065o0((EnumC2090t0) this.f864b, (Predicate) this.f865c);
        }
    }

    public C1810s(EnumC2001c3 enumC2001c3, EnumC2090t0 enumC2090t0, Supplier supplier) {
        this.f863a = 7;
        this.f864b = enumC2090t0;
        this.f865c = supplier;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: s */
    public int mo904s() {
        return EnumC1995b3.f1203u | EnumC1995b3.f1200r;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: f */
    public Object mo902f(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        AbstractC2085s0 abstractC2085s0 = (AbstractC2085s0) ((Supplier) this.f865c).get();
        abstractC1985a.mo1017t0(spliterator, abstractC2085s0);
        return Boolean.valueOf(abstractC2085s0.f1336b);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: i */
    public Object mo903i(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        return (Boolean) new C2095u0(this, (AbstractC1985a) abstractC2106w1, spliterator).invoke();
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public void m971v(Object obj) {
        switch (this.f863a) {
            case 3:
                Consumer consumer = (Consumer) this.f864b;
                Consumer consumer2 = (Consumer) this.f865c;
                consumer.m971v(obj);
                consumer2.m971v(obj);
                break;
            case 4:
                AtomicBoolean atomicBoolean = (AtomicBoolean) this.f864b;
                ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.f865c;
                if (obj != null) {
                    concurrentHashMap.putIfAbsent(obj, Boolean.TRUE);
                    break;
                } else {
                    atomicBoolean.set(true);
                    break;
                }
            case 8:
                ((BiConsumer) this.f864b).accept(this.f865c, obj);
                break;
            default:
                C2048k3 c2048k3 = (C2048k3) this.f864b;
                Consumer consumer3 = (Consumer) this.f865c;
                if (c2048k3.f1281b.putIfAbsent(obj != null ? obj : C2048k3.f1279d, Boolean.TRUE) == null) {
                    consumer3.m971v(obj);
                    break;
                }
                break;
        }
    }
}
