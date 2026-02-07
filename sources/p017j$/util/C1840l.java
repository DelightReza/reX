package p017j$.util;

import java.util.Set;

/* renamed from: j$.util.l */
/* loaded from: classes2.dex */
public final class C1840l extends C1832h implements Set, Set {
    private static final long serialVersionUID = 487447009682186044L;

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        boolean zEquals;
        if (this == obj) {
            return true;
        }
        synchronized (this.f917b) {
            zEquals = this.f916a.equals(obj);
        }
        return zEquals;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        int iHashCode;
        synchronized (this.f917b) {
            iHashCode = this.f916a.hashCode();
        }
        return iHashCode;
    }
}
