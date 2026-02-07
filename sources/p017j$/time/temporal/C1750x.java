package p017j$.time.temporal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.time.DayOfWeek;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.temporal.x */
/* loaded from: classes2.dex */
public final class C1750x implements Serializable {

    /* renamed from: g */
    public static final ConcurrentHashMap f710g = new ConcurrentHashMap(4, 0.75f, 2);

    /* renamed from: h */
    public static final EnumC1735i f711h;
    private static final long serialVersionUID = -1177360819670808121L;

    /* renamed from: a */
    public final DayOfWeek f712a;

    /* renamed from: b */
    public final int f713b;

    /* renamed from: c */
    public final transient C1749w f714c;

    /* renamed from: d */
    public final transient C1749w f715d;

    /* renamed from: e */
    public final transient C1749w f716e;

    /* renamed from: f */
    public final transient C1749w f717f;

    static {
        new C1750x(DayOfWeek.MONDAY, 4);
        m833a(DayOfWeek.SUNDAY, 1);
        f711h = AbstractC1736j.f682d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    public static C1750x m833a(DayOfWeek dayOfWeek, int i) {
        String str = dayOfWeek.toString() + i;
        ConcurrentHashMap concurrentHashMap = f710g;
        C1750x c1750x = (C1750x) concurrentHashMap.get(str);
        if (c1750x != null) {
            return c1750x;
        }
        concurrentHashMap.putIfAbsent(str, new C1750x(dayOfWeek, i));
        return (C1750x) concurrentHashMap.get(str);
    }

    public C1750x(DayOfWeek dayOfWeek, int i) {
        EnumC1728b enumC1728b = EnumC1728b.DAYS;
        EnumC1728b enumC1728b2 = EnumC1728b.WEEKS;
        this.f714c = new C1749w("DayOfWeek", this, enumC1728b, enumC1728b2, C1749w.f701f);
        this.f715d = new C1749w("WeekOfMonth", this, enumC1728b2, EnumC1728b.MONTHS, C1749w.f702g);
        EnumC1735i enumC1735i = AbstractC1736j.f682d;
        this.f716e = new C1749w("WeekOfWeekBasedYear", this, enumC1728b2, enumC1735i, C1749w.f704i);
        this.f717f = new C1749w("WeekBasedYear", this, enumC1735i, EnumC1728b.FOREVER, EnumC1727a.YEAR.f671b);
        Objects.requireNonNull(dayOfWeek, "firstDayOfWeek");
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Minimal number of days is invalid");
        }
        this.f712a = dayOfWeek;
        this.f713b = i;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.f712a == null) {
            throw new InvalidObjectException("firstDayOfWeek is null");
        }
        int i = this.f713b;
        if (i < 1 || i > 7) {
            throw new InvalidObjectException("Minimal number of days is invalid");
        }
    }

    private Object readResolve() throws InvalidObjectException {
        try {
            return m833a(this.f712a, this.f713b);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectException("Invalid serialized WeekFields: " + e.getMessage());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1750x) && hashCode() == obj.hashCode();
    }

    public final int hashCode() {
        return (this.f712a.ordinal() * 7) + this.f713b;
    }

    public final String toString() {
        return "WeekFields[" + this.f712a + "," + this.f713b + "]";
    }
}
