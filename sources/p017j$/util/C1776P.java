package p017j$.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

/* renamed from: j$.util.P */
/* loaded from: classes2.dex */
public final class C1776P extends C1847o0 {

    /* renamed from: f */
    public final /* synthetic */ SortedSet f782f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1776P(SortedSet sortedSet, Collection collection) {
        super(collection, 21);
        this.f782f = sortedSet;
    }

    @Override // p017j$.util.C1847o0, p017j$.util.Spliterator
    public final Comparator getComparator() {
        return this.f782f.comparator();
    }
}
