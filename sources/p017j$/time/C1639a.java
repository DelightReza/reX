package p017j$.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.time.a */
/* loaded from: classes2.dex */
public final class C1639a extends AbstractC1636a implements Serializable {

    /* renamed from: b */
    public static final C1639a f465b;
    private static final long serialVersionUID = 6740630888130243051L;

    /* renamed from: a */
    public final ZoneId f466a;

    public C1639a(ZoneId zoneId) {
        this.f466a = zoneId;
    }

    static {
        System.currentTimeMillis();
        f465b = new C1639a(ZoneOffset.UTC);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C1639a) {
            return this.f466a.equals(((C1639a) obj).f466a);
        }
        return false;
    }

    public final int hashCode() {
        return this.f466a.hashCode() + 1;
    }

    public final String toString() {
        return "SystemClock[" + this.f466a + "]";
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
    }
}
