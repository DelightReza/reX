package p017j$.util;

import java.util.LinkedHashSet;
import java.util.SortedSet;

/* loaded from: classes2.dex */
public interface Set<E> extends Collection<E> {
    @Override // java.lang.Iterable, java.util.Set, p017j$.util.Set, p017j$.util.Collection
    Spliterator<E> spliterator();

    /* renamed from: j$.util.Set$-EL, reason: invalid class name */
    public final /* synthetic */ class EL {
        public static Spliterator spliterator(java.util.Set set) {
            if (set instanceof Set) {
                return ((Set) set).spliterator();
            }
            if (set instanceof LinkedHashSet) {
                return Spliterators.spliterator((LinkedHashSet) set, 17);
            }
            if (!(set instanceof SortedSet)) {
                return CC.$default$spliterator(set);
            }
            SortedSet sortedSet = (SortedSet) set;
            return new C1776P(sortedSet, sortedSet);
        }
    }

    /* renamed from: j$.util.Set$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static Spliterator $default$spliterator(java.util.Set set) {
            return Spliterators.spliterator(set, 1);
        }
    }
}
