package p017j$.time.zone;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1715i;
import p017j$.time.DayOfWeek;
import p017j$.time.EnumC1717k;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.LocalDateTime;
import p017j$.time.ZoneOffset;
import p017j$.time.chrono.C1668r;
import p017j$.time.temporal.C1742p;
import p017j$.time.temporal.EnumC1727a;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class ZoneRules implements Serializable {

    /* renamed from: i */
    public static final long[] f722i = new long[0];

    /* renamed from: j */
    public static final C1757e[] f723j = new C1757e[0];

    /* renamed from: k */
    public static final LocalDateTime[] f724k = new LocalDateTime[0];

    /* renamed from: l */
    public static final C1754b[] f725l = new C1754b[0];
    private static final long serialVersionUID = 3044319355680032515L;

    /* renamed from: a */
    public final long[] f726a;

    /* renamed from: b */
    public final ZoneOffset[] f727b;

    /* renamed from: c */
    public final long[] f728c;

    /* renamed from: d */
    public final LocalDateTime[] f729d;

    /* renamed from: e */
    public final ZoneOffset[] f730e;

    /* renamed from: f */
    public final C1757e[] f731f;

    /* renamed from: g */
    public final TimeZone f732g;

    /* renamed from: h */
    public final transient ConcurrentHashMap f733h = new ConcurrentHashMap();

    /* renamed from: a */
    public static Object m835a(LocalDateTime localDateTime, C1754b c1754b) {
        LocalDateTime localDateTime2 = c1754b.f738b;
        if (c1754b.m847i()) {
            if (localDateTime.m597S(localDateTime2)) {
                return c1754b.f739c;
            }
            if (!localDateTime.m597S(c1754b.f738b.m599W(c1754b.f740d.getTotalSeconds() - c1754b.f739c.getTotalSeconds()))) {
                return c1754b.f740d;
            }
        } else {
            if (!localDateTime.m597S(localDateTime2)) {
                return c1754b.f740d;
            }
            if (localDateTime.m597S(c1754b.f738b.m599W(c1754b.f740d.getTotalSeconds() - c1754b.f739c.getTotalSeconds()))) {
                return c1754b.f739c;
            }
        }
        return c1754b;
    }

    public ZoneRules(long[] jArr, ZoneOffset[] zoneOffsetArr, long[] jArr2, ZoneOffset[] zoneOffsetArr2, C1757e[] c1757eArr) {
        this.f726a = jArr;
        this.f727b = zoneOffsetArr;
        this.f728c = jArr2;
        this.f730e = zoneOffsetArr2;
        this.f731f = c1757eArr;
        if (jArr2.length == 0) {
            this.f729d = f724k;
        } else {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (i < jArr2.length) {
                int i2 = i + 1;
                C1754b c1754b = new C1754b(jArr2[i], zoneOffsetArr2[i], zoneOffsetArr2[i2]);
                if (c1754b.m847i()) {
                    arrayList.add(c1754b.f738b);
                    arrayList.add(c1754b.f738b.m599W(c1754b.f740d.getTotalSeconds() - c1754b.f739c.getTotalSeconds()));
                } else {
                    arrayList.add(c1754b.f738b.m599W(c1754b.f740d.getTotalSeconds() - c1754b.f739c.getTotalSeconds()));
                    arrayList.add(c1754b.f738b);
                }
                i = i2;
            }
            this.f729d = (LocalDateTime[]) arrayList.toArray(new LocalDateTime[arrayList.size()]);
        }
        this.f732g = null;
    }

    public ZoneRules(ZoneOffset zoneOffset) {
        ZoneOffset[] zoneOffsetArr = {zoneOffset};
        this.f727b = zoneOffsetArr;
        long[] jArr = f722i;
        this.f726a = jArr;
        this.f728c = jArr;
        this.f729d = f724k;
        this.f730e = zoneOffsetArr;
        this.f731f = f723j;
        this.f732g = null;
    }

    public ZoneRules(TimeZone timeZone) {
        ZoneOffset[] zoneOffsetArr = {m837h(timeZone.getRawOffset())};
        this.f727b = zoneOffsetArr;
        long[] jArr = f722i;
        this.f726a = jArr;
        this.f728c = jArr;
        this.f729d = f724k;
        this.f730e = zoneOffsetArr;
        this.f731f = f723j;
        this.f732g = timeZone;
    }

    /* renamed from: h */
    public static ZoneOffset m837h(int i) {
        return ZoneOffset.m626W(i / MediaDataController.MAX_STYLE_RUNS_COUNT);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1753a(this.f732g != null ? (byte) 100 : (byte) 1, this);
    }

    public ZoneOffset getOffset(Instant instant) {
        TimeZone timeZone = this.f732g;
        if (timeZone != null) {
            return m837h(timeZone.getOffset(instant.toEpochMilli()));
        }
        long[] jArr = this.f728c;
        if (jArr.length == 0) {
            return this.f727b[0];
        }
        long j = instant.f434a;
        if (this.f731f.length > 0 && j > jArr[jArr.length - 1]) {
            C1754b[] c1754bArrM838b = m838b(m836c(j, this.f730e[r8.length - 1]));
            C1754b c1754b = null;
            for (int i = 0; i < c1754bArrM838b.length; i++) {
                c1754b = c1754bArrM838b[i];
                if (j < c1754b.f737a) {
                    return c1754b.f739c;
                }
            }
            return c1754b.f740d;
        }
        int iBinarySearch = Arrays.binarySearch(jArr, j);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 2;
        }
        return this.f730e[iBinarySearch + 1];
    }

    /* renamed from: f */
    public final List m841f(LocalDateTime localDateTime) {
        Object objM839d = m839d(localDateTime);
        if (!(objM839d instanceof C1754b)) {
            return Collections.singletonList((ZoneOffset) objM839d);
        }
        C1754b c1754b = (C1754b) objM839d;
        return c1754b.m847i() ? Collections.EMPTY_LIST : AbstractC1636a.m495N(new Object[]{c1754b.f739c, c1754b.f740d});
    }

    /* renamed from: e */
    public final C1754b m840e(LocalDateTime localDateTime) {
        Object objM839d = m839d(localDateTime);
        if (objM839d instanceof C1754b) {
            return (C1754b) objM839d;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0065, code lost:
    
        if (r9.m596Q(r0) > 0) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0088, code lost:
    
        if (r9.f444b.m781c0() <= r0.f444b.m781c0()) goto L44;
     */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object m839d(p017j$.time.LocalDateTime r9) {
        /*
            Method dump skipped, instructions count: 269
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.zone.ZoneRules.m839d(j$.time.LocalDateTime):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: b */
    public final C1754b[] m838b(int i) {
        LocalDate localDateM560R;
        C1754b[] c1754bArr = f725l;
        Integer numValueOf = Integer.valueOf(i);
        C1754b[] c1754bArr2 = (C1754b[]) this.f733h.get(numValueOf);
        if (c1754bArr2 != null) {
            return c1754bArr2;
        }
        long j = 1;
        int i2 = 0;
        int i3 = 1;
        if (this.f732g != null) {
            if (i < 1800) {
                return c1754bArr;
            }
            LocalDateTime localDateTime = LocalDateTime.f441c;
            LocalDate localDateM566of = LocalDate.m566of(i - 1, 12, 31);
            EnumC1727a.HOUR_OF_DAY.m800D(0);
            long jM525u = AbstractC1636a.m525u(new LocalDateTime(localDateM566of, C1715i.f643h[0]), this.f727b[0]);
            long j2 = 1000;
            int offset = this.f732g.getOffset(jM525u * 1000);
            long j3 = 31968000 + jM525u;
            while (jM525u < j3) {
                long j4 = jM525u + 7776000;
                long j5 = j2;
                if (offset != this.f732g.getOffset(j4 * j5)) {
                    while (j4 - jM525u > j) {
                        long jM499R = AbstractC1636a.m499R(j4 + jM525u, 2L);
                        if (this.f732g.getOffset(jM499R * j5) == offset) {
                            jM525u = jM499R;
                        } else {
                            j4 = jM499R;
                        }
                        j = 1;
                    }
                    if (this.f732g.getOffset(jM525u * j5) == offset) {
                        jM525u = j4;
                    }
                    ZoneOffset zoneOffsetM837h = m837h(offset);
                    int offset2 = this.f732g.getOffset(jM525u * j5);
                    ZoneOffset zoneOffsetM837h2 = m837h(offset2);
                    if (m836c(jM525u, zoneOffsetM837h2) == i) {
                        c1754bArr = (C1754b[]) Arrays.copyOf(c1754bArr, c1754bArr.length + 1);
                        c1754bArr[c1754bArr.length - 1] = new C1754b(jM525u, zoneOffsetM837h, zoneOffsetM837h2);
                    }
                    offset = offset2;
                } else {
                    jM525u = j4;
                }
                j2 = j5;
                j = 1;
            }
            if (1916 <= i && i < 2100) {
                this.f733h.putIfAbsent(numValueOf, c1754bArr);
            }
            return c1754bArr;
        }
        C1757e[] c1757eArr = this.f731f;
        C1754b[] c1754bArr3 = new C1754b[c1757eArr.length];
        int i4 = 0;
        while (i4 < c1757eArr.length) {
            C1757e c1757e = c1757eArr[i4];
            byte b = c1757e.f744b;
            if (b < 0) {
                EnumC1717k enumC1717k = c1757e.f743a;
                long j6 = i;
                int iM788R = enumC1717k.m788R(C1668r.f512c.mo655O(j6)) + 1 + c1757e.f744b;
                LocalDate localDate = LocalDate.f436d;
                EnumC1727a.YEAR.m800D(j6);
                Objects.requireNonNull(enumC1717k, "month");
                EnumC1727a.DAY_OF_MONTH.m800D(iM788R);
                localDateM560R = LocalDate.m560R(i, enumC1717k.getValue(), iM788R);
                DayOfWeek dayOfWeek = c1757e.f745c;
                if (dayOfWeek != null) {
                    localDateM560R = localDateM560R.mo558j(new C1742p(dayOfWeek.getValue(), i3));
                }
            } else {
                EnumC1717k enumC1717k2 = c1757e.f743a;
                LocalDate localDate2 = LocalDate.f436d;
                EnumC1727a.YEAR.m800D(i);
                Objects.requireNonNull(enumC1717k2, "month");
                EnumC1727a.DAY_OF_MONTH.m800D(b);
                localDateM560R = LocalDate.m560R(i, enumC1717k2.getValue(), b);
                DayOfWeek dayOfWeek2 = c1757e.f745c;
                if (dayOfWeek2 != null) {
                    localDateM560R = localDateM560R.mo558j(new C1742p(dayOfWeek2.getValue(), i2));
                }
            }
            if (c1757e.f747e) {
                localDateM560R = localDateM560R.plusDays(1L);
            }
            LocalDateTime localDateTimeM593T = LocalDateTime.m593T(localDateM560R, c1757e.f746d);
            EnumC1756d enumC1756d = c1757e.f748f;
            ZoneOffset zoneOffset = c1757e.f749g;
            ZoneOffset zoneOffset2 = c1757e.f750h;
            enumC1756d.getClass();
            int i5 = AbstractC1755c.f741a[enumC1756d.ordinal()];
            if (i5 == 1) {
                localDateTimeM593T = localDateTimeM593T.m599W(zoneOffset2.getTotalSeconds() - ZoneOffset.UTC.getTotalSeconds());
            } else if (i5 == 2) {
                localDateTimeM593T = localDateTimeM593T.m599W(zoneOffset2.getTotalSeconds() - zoneOffset.getTotalSeconds());
            }
            c1754bArr3[i4] = new C1754b(localDateTimeM593T, c1757e.f750h, c1757e.f751i);
            i4++;
            i2 = 0;
        }
        if (i < 2100) {
            this.f733h.putIfAbsent(numValueOf, c1754bArr3);
        }
        return c1754bArr3;
    }

    /* renamed from: g */
    public final boolean m842g(Instant instant) {
        ZoneOffset zoneOffsetM837h;
        TimeZone timeZone = this.f732g;
        if (timeZone != null) {
            zoneOffsetM837h = m837h(timeZone.getRawOffset());
        } else if (this.f728c.length != 0) {
            int iBinarySearch = Arrays.binarySearch(this.f726a, instant.f434a);
            if (iBinarySearch < 0) {
                iBinarySearch = (-iBinarySearch) - 2;
            }
            zoneOffsetM837h = this.f727b[iBinarySearch + 1];
        } else {
            zoneOffsetM837h = this.f727b[0];
        }
        return !zoneOffsetM837h.equals(getOffset(instant));
    }

    /* renamed from: c */
    public static int m836c(long j, ZoneOffset zoneOffset) {
        return LocalDate.m563b0(AbstractC1636a.m499R(j + zoneOffset.getTotalSeconds(), 86400)).getYear();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneRules) {
            ZoneRules zoneRules = (ZoneRules) obj;
            if (Objects.equals(this.f732g, zoneRules.f732g) && Arrays.equals(this.f726a, zoneRules.f726a) && Arrays.equals(this.f727b, zoneRules.f727b) && Arrays.equals(this.f728c, zoneRules.f728c) && Arrays.equals(this.f730e, zoneRules.f730e) && Arrays.equals(this.f731f, zoneRules.f731f)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((Objects.hashCode(this.f732g) ^ Arrays.hashCode(this.f726a)) ^ Arrays.hashCode(this.f727b)) ^ Arrays.hashCode(this.f728c)) ^ Arrays.hashCode(this.f730e)) ^ Arrays.hashCode(this.f731f);
    }

    public final String toString() {
        TimeZone timeZone = this.f732g;
        if (timeZone != null) {
            return "ZoneRules[timeZone=" + timeZone.getID() + "]";
        }
        return "ZoneRules[currentStandardOffset=" + this.f727b[r0.length - 1] + "]";
    }
}
