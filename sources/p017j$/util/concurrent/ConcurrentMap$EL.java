package p017j$.util.concurrent;

import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Objects;

/* renamed from: j$.util.concurrent.ConcurrentMap$-EL, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class ConcurrentMap$EL {
    public static /* synthetic */ Object compute(ConcurrentMap concurrentMap, Object obj, BiFunction biFunction) {
        return concurrentMap instanceof InterfaceC1811t ? ((InterfaceC1811t) concurrentMap).compute(obj, biFunction) : AbstractC1636a.m511g(concurrentMap, obj, biFunction);
    }

    public static Object getOrDefault(ConcurrentMap concurrentMap, Object obj, Object obj2) {
        if (concurrentMap instanceof InterfaceC1811t) {
            return ((InterfaceC1811t) concurrentMap).getOrDefault(obj, obj2);
        }
        Object obj3 = concurrentMap.get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    public static Object computeIfAbsent(ConcurrentMap concurrentMap, Object obj, Function function) {
        Object objApply;
        if (concurrentMap instanceof InterfaceC1811t) {
            return ((InterfaceC1811t) concurrentMap).computeIfAbsent(obj, function);
        }
        Objects.requireNonNull(function);
        Object obj2 = concurrentMap.get(obj);
        if (obj2 != null || (objApply = function.apply(obj)) == null) {
            return obj2;
        }
        Object objPutIfAbsent = concurrentMap.putIfAbsent(obj, objApply);
        return objPutIfAbsent == null ? objApply : objPutIfAbsent;
    }

    public static Object computeIfPresent(ConcurrentMap concurrentMap, Object obj, BiFunction biFunction) {
        Object objApply;
        if (concurrentMap instanceof InterfaceC1811t) {
            return ((InterfaceC1811t) concurrentMap).computeIfPresent(obj, biFunction);
        }
        Objects.requireNonNull(biFunction);
        while (true) {
            Object obj2 = concurrentMap.get(obj);
            if (obj2 == null) {
                return null;
            }
            objApply = biFunction.apply(obj, obj2);
            if (objApply == null) {
                if (concurrentMap.remove(obj, obj2)) {
                    break;
                }
            } else if (concurrentMap.replace(obj, obj2, objApply)) {
                break;
            }
        }
        return objApply;
    }
}
