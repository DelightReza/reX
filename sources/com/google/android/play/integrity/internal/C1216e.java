package com.google.android.play.integrity.internal;

/* renamed from: com.google.android.play.integrity.internal.e */
/* loaded from: classes4.dex */
final class C1216e extends AbstractC1217f {

    /* renamed from: a */
    private final int f357a;

    /* renamed from: b */
    private final long f358b;

    C1216e(int i, long j) {
        this.f357a = i;
        this.f358b = j;
    }

    @Override // com.google.android.play.integrity.internal.AbstractC1217f
    /* renamed from: a */
    public final int mo412a() {
        return this.f357a;
    }

    @Override // com.google.android.play.integrity.internal.AbstractC1217f
    /* renamed from: b */
    public final long mo413b() {
        return this.f358b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractC1217f) {
            AbstractC1217f abstractC1217f = (AbstractC1217f) obj;
            if (this.f357a == abstractC1217f.mo412a() && this.f358b == abstractC1217f.mo413b()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.f357a ^ 1000003;
        long j = this.f358b;
        return (i * 1000003) ^ ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        return "EventRecord{eventType=" + this.f357a + ", eventTimestamp=" + this.f358b + "}";
    }
}
