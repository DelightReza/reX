package p017j$.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;

/* renamed from: j$.util.d */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1816d implements Comparator, Serializable {

    /* renamed from: a */
    public final /* synthetic */ int f878a;

    /* renamed from: b */
    public final /* synthetic */ Comparator f879b;

    /* renamed from: c */
    public final /* synthetic */ Object f880c;

    public /* synthetic */ C1816d(Comparator comparator, Object obj, int i) {
        this.f878a = i;
        this.f879b = comparator;
        this.f880c = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f878a) {
            case 0:
                Comparator comparator = this.f879b;
                Comparator comparator2 = (Comparator) this.f880c;
                int iCompare = comparator.compare(obj, obj2);
                return iCompare != 0 ? iCompare : comparator2.compare(obj, obj2);
            default:
                Comparator comparator3 = this.f879b;
                Function function = (Function) this.f880c;
                return comparator3.compare(function.apply(obj), function.apply(obj2));
        }
    }
}
