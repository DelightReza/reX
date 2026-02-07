package p017j$.time.zone;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.LocalDateTime;
import p017j$.time.ZoneOffset;

/* renamed from: j$.time.zone.b */
/* loaded from: classes2.dex */
public final class C1754b implements Comparable, Serializable {

    /* renamed from: e */
    public static final /* synthetic */ int f736e = 0;
    private static final long serialVersionUID = -6946044323557704546L;

    /* renamed from: a */
    public final long f737a;

    /* renamed from: b */
    public final LocalDateTime f738b;

    /* renamed from: c */
    public final ZoneOffset f739c;

    /* renamed from: d */
    public final ZoneOffset f740d;

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Long.compare(this.f737a, ((C1754b) obj).f737a);
    }

    public C1754b(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        localDateTime.getClass();
        this.f737a = AbstractC1636a.m525u(localDateTime, zoneOffset);
        this.f738b = localDateTime;
        this.f739c = zoneOffset;
        this.f740d = zoneOffset2;
    }

    public C1754b(long j, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.f737a = j;
        this.f738b = LocalDateTime.m594U(j, 0, zoneOffset);
        this.f739c = zoneOffset;
        this.f740d = zoneOffset2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1753a((byte) 2, this);
    }

    /* renamed from: i */
    public final boolean m847i() {
        return this.f740d.getTotalSeconds() > this.f739c.getTotalSeconds();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1754b) {
            C1754b c1754b = (C1754b) obj;
            if (this.f737a == c1754b.f737a && this.f739c.equals(c1754b.f739c) && this.f740d.equals(c1754b.f740d)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (this.f738b.hashCode() ^ this.f739c.f460b) ^ Integer.rotateLeft(this.f740d.f460b, 16);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Transition[");
        sb.append(m847i() ? "Gap" : "Overlap");
        sb.append(" at ");
        sb.append(this.f738b);
        sb.append(this.f739c);
        sb.append(" to ");
        sb.append(this.f740d);
        sb.append(']');
        return sb.toString();
    }
}
