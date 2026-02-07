package p017j$.util;

import java.util.Set;

/* renamed from: j$.util.v */
/* loaded from: classes2.dex */
public class C2126v extends C1844n implements Set, Set {
    private static final long serialVersionUID = -9215047833775013803L;

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        return obj == this || this.f934a.equals(obj);
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.f934a.hashCode();
    }
}
