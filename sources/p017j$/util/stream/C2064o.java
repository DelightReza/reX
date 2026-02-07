package p017j$.util.stream;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.C1810s;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.util.stream.o */
/* loaded from: classes2.dex */
public final class C2064o extends AbstractC2006d2 {
    /* renamed from: I0 */
    public static C1907K0 m1058I0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        C2044k c2044k = new C2044k(1);
        C2044k c2044k2 = new C2044k(2);
        C2044k c2044k3 = new C2044k(3);
        Objects.requireNonNull(c2044k);
        Objects.requireNonNull(c2044k2);
        Objects.requireNonNull(c2044k3);
        return new C1907K0((Collection) new C1863B1(EnumC2001c3.REFERENCE, c2044k3, c2044k2, c2044k, 3).mo903i(abstractC2106w1, spliterator));
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        AbstractC1985a abstractC1985a = (AbstractC1985a) abstractC2106w1;
        if (EnumC1995b3.DISTINCT.m1030n(abstractC1985a.f1167m)) {
            return abstractC2106w1.mo1015d0(spliterator, false, intFunction);
        }
        if (EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m)) {
            return m1058I0(abstractC2106w1, spliterator);
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        C1810s c1810s = new C1810s(4, atomicBoolean, concurrentHashMap);
        Objects.requireNonNull(c1810s);
        new C1931P(c1810s, false).m990a(abstractC2106w1, spliterator);
        Collection collectionKeySet = concurrentHashMap.keySet();
        if (atomicBoolean.get()) {
            HashSet hashSet = new HashSet(collectionKeySet);
            hashSet.add(null);
            collectionKeySet = hashSet;
        }
        return new C1907K0(collectionKeySet);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: C0 */
    public final Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        if (EnumC1995b3.DISTINCT.m1030n(abstractC1985a.f1167m)) {
            return abstractC1985a.mo1019v0(spliterator);
        }
        if (EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m)) {
            return m1058I0(abstractC1985a, spliterator).spliterator();
        }
        return new C2048k3(abstractC1985a.mo1019v0(spliterator), new ConcurrentHashMap());
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        Objects.requireNonNull(interfaceC2062n2);
        if (EnumC1995b3.DISTINCT.m1030n(i)) {
            return interfaceC2062n2;
        }
        if (EnumC1995b3.SORTED.m1030n(i)) {
            return new C2054m(interfaceC2062n2);
        }
        return new C2059n(interfaceC2062n2);
    }
}
