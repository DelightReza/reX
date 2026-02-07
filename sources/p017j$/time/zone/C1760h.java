package p017j$.time.zone;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.zone.h */
/* loaded from: classes2.dex */
public final class C1760h {

    /* renamed from: b */
    public static final CopyOnWriteArrayList f753b;

    /* renamed from: c */
    public static final ConcurrentHashMap f754c;

    /* renamed from: d */
    public static volatile Set f755d;

    /* renamed from: a */
    public final Set f756a;

    static {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        f753b = copyOnWriteArrayList;
        f754c = new ConcurrentHashMap(512, 0.75f, 2);
        ArrayList arrayList = new ArrayList();
        AccessController.doPrivileged(new C1759g(arrayList));
        copyOnWriteArrayList.addAll(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    public static ZoneRules m850a(String str) {
        Objects.requireNonNull(str, "zoneId");
        ConcurrentHashMap concurrentHashMap = f754c;
        C1760h c1760h = (C1760h) concurrentHashMap.get(str);
        if (c1760h == null) {
            if (concurrentHashMap.isEmpty()) {
                throw new C1758f("No time-zone data files registered");
            }
            throw new C1758f("Unknown time-zone ID: " + str);
        }
        if (c1760h.f756a.contains(str)) {
            return new ZoneRules(TimeZone.getTimeZone(str));
        }
        throw new C1758f("Not a built-in time zone: " + str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: b */
    public static void m851b(C1760h c1760h) {
        Objects.requireNonNull(c1760h, "provider");
        synchronized (C1760h.class) {
            try {
                for (String str : c1760h.f756a) {
                    Objects.requireNonNull(str, "zoneId");
                    if (((C1760h) f754c.putIfAbsent(str, c1760h)) != null) {
                        throw new C1758f("Unable to register zone as one already registered with that ID: " + str + ", currently loading from provider: " + c1760h);
                    }
                }
                f755d = Collections.unmodifiableSet(new HashSet(f754c.keySet()));
            } catch (Throwable th) {
                throw th;
            }
        }
        f753b.add(c1760h);
    }

    public C1760h() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String str : TimeZone.getAvailableIDs()) {
            linkedHashSet.add(str);
        }
        this.f756a = Collections.unmodifiableSet(linkedHashSet);
    }
}
