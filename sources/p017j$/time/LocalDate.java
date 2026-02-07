package p017j$.time;

import de.robv.android.xposed.callbacks.XCallback;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.mvel2.asm.Opcodes;
import org.mvel2.asm.signature.SignatureVisitor;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.ChronoLocalDateTime;
import p017j$.time.chrono.EnumC1669s;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.chrono.InterfaceC1662l;
import p017j$.time.format.C1709w;
import p017j$.time.format.DateTimeFormatter;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1743q;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.time.zone.C1754b;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public final class LocalDate implements InterfaceC1739m, InterfaceC1741o, InterfaceC1652b, Serializable {

    /* renamed from: d */
    public static final LocalDate f436d = m566of(-999999999, 1, 1);

    /* renamed from: e */
    public static final LocalDate f437e = m566of(999999999, 12, 31);
    private static final long serialVersionUID = 2942565459149668126L;

    /* renamed from: a */
    public final int f438a;

    /* renamed from: b */
    public final short f439b;

    /* renamed from: c */
    public final short f440c;

    static {
        m566of(1970, 1, 1);
    }

    public static LocalDate now() {
        return m562a0(AbstractC1636a.m504W());
    }

    /* renamed from: a0 */
    public static LocalDate m562a0(C1639a c1639a) {
        Objects.requireNonNull(c1639a, "clock");
        long jCurrentTimeMillis = System.currentTimeMillis();
        Instant instant = Instant.f433c;
        long j = MediaDataController.MAX_STYLE_RUNS_COUNT;
        Instant instantM551Q = Instant.m551Q(AbstractC1636a.m499R(jCurrentTimeMillis, j), ((int) AbstractC1636a.m498Q(jCurrentTimeMillis, j)) * 1000000);
        ZoneId zoneId = c1639a.f466a;
        Objects.requireNonNull(instantM551Q, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return m563b0(AbstractC1636a.m499R(instantM551Q.f434a + zoneId.getRules().getOffset(instantM551Q).getTotalSeconds(), 86400));
    }

    /* renamed from: of */
    public static LocalDate m566of(int i, int i2, int i3) {
        EnumC1727a.YEAR.m800D(i);
        EnumC1727a.MONTH_OF_YEAR.m800D(i2);
        EnumC1727a.DAY_OF_MONTH.m800D(i3);
        return m560R(i, i2, i3);
    }

    /* renamed from: c0 */
    public static LocalDate m564c0(int i, int i2) {
        long j = i;
        EnumC1727a.YEAR.m800D(j);
        EnumC1727a.DAY_OF_YEAR.m800D(i2);
        boolean zMo655O = C1668r.f512c.mo655O(j);
        if (i2 == 366 && !zMo655O) {
            throw new C1640b("Invalid date 'DayOfYear 366' as '" + i + "' is not a leap year");
        }
        EnumC1717k enumC1717kM786T = EnumC1717k.m786T(((i2 - 1) / 31) + 1);
        if (i2 > (enumC1717kM786T.m788R(zMo655O) + enumC1717kM786T.m787Q(zMo655O)) - 1) {
            enumC1717kM786T = EnumC1717k.f649a[((((int) 1) + 12) + enumC1717kM786T.ordinal()) % 12];
        }
        return new LocalDate(i, enumC1717kM786T.getValue(), (i2 - enumC1717kM786T.m787Q(zMo655O)) + 1);
    }

    /* renamed from: b0 */
    public static LocalDate m563b0(long j) {
        long j2;
        EnumC1727a.EPOCH_DAY.m800D(j);
        long j3 = 719468 + j;
        if (j3 < 0) {
            long j4 = ((j + 719469) / 146097) - 1;
            j2 = j4 * 400;
            j3 += (-j4) * 146097;
        } else {
            j2 = 0;
        }
        long j5 = ((j3 * 400) + 591) / 146097;
        long j6 = j3 - ((j5 / 400) + (((j5 / 4) + (j5 * 365)) - (j5 / 100)));
        if (j6 < 0) {
            j5--;
            j6 = j3 - ((j5 / 400) + (((j5 / 4) + (365 * j5)) - (j5 / 100)));
        }
        int i = (int) j6;
        int i2 = ((i * 5) + 2) / Opcodes.IFEQ;
        int i3 = ((i2 + 2) % 12) + 1;
        int i4 = (i - (((i2 * 306) + 5) / 10)) + 1;
        long j7 = j5 + j2 + (i2 / 10);
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        return new LocalDate(enumC1727a.f671b.m820a(j7, enumC1727a), i3, i4);
    }

    /* renamed from: S */
    public static LocalDate m561S(InterfaceC1740n interfaceC1740n) {
        Objects.requireNonNull(interfaceC1740n, "temporal");
        LocalDate localDate = (LocalDate) interfaceC1740n.mo547t(AbstractC1745s.f695f);
        if (localDate != null) {
            return localDate;
        }
        throw new C1640b("Unable to obtain LocalDate from TemporalAccessor: " + interfaceC1740n + " of type " + interfaceC1740n.getClass().getName());
    }

    public static LocalDate parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        String string;
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        C1678e c1678e = new C1678e(0);
        dateTimeFormatter.getClass();
        Objects.requireNonNull(charSequence, "text");
        Objects.requireNonNull(c1678e, "query");
        try {
            return (LocalDate) dateTimeFormatter.m720b(charSequence).mo547t(c1678e);
        } catch (C1709w e) {
            throw e;
        } catch (RuntimeException e2) {
            if (charSequence.length() > 64) {
                string = charSequence.subSequence(0, 64).toString() + "...";
            } else {
                string = charSequence.toString();
            }
            C1709w c1709w = new C1709w("Text '" + string + "' could not be parsed: " + e2.getMessage(), e2);
            charSequence.toString();
            throw c1709w;
        }
    }

    /* renamed from: R */
    public static LocalDate m560R(int i, int i2, int i3) {
        int i4 = 28;
        if (i3 > 28) {
            if (i2 != 2) {
                i4 = (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31;
            } else if (C1668r.f512c.mo655O(i)) {
                i4 = 29;
            }
            if (i3 > i4) {
                if (i3 == 29) {
                    throw new C1640b("Invalid date 'February 29' as '" + i + "' is not a leap year");
                }
                throw new C1640b("Invalid date '" + EnumC1717k.m786T(i2).name() + " " + i3 + "'");
            }
        }
        return new LocalDate(i, i2, i3);
    }

    /* renamed from: h0 */
    public static LocalDate m565h0(int i, int i2, int i3) {
        if (i2 == 2) {
            i3 = Math.min(i3, C1668r.f512c.mo655O((long) i) ? 29 : 28);
        } else if (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) {
            i3 = Math.min(i3, 30);
        }
        return new LocalDate(i, i2, i3);
    }

    public LocalDate(int i, int i2, int i3) {
        this.f438a = i;
        this.f439b = (short) i2;
        this.f440c = (short) i3;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m519o(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo803j(this);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        if (!enumC1727a.isDateBased()) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        int i = AbstractC1679f.f532a[enumC1727a.ordinal()];
        if (i == 1) {
            return C1748v.m818f(1L, m579Y());
        }
        if (i == 2) {
            return C1748v.m818f(1L, mo571M());
        }
        if (i != 3) {
            return i != 4 ? enumC1727a.f671b : getYear() <= 0 ? C1748v.m818f(1L, 1000000000L) : C1748v.m818f(1L, 999999999L);
        }
        return C1748v.m818f(1L, (EnumC1717k.m786T(this.f439b) != EnumC1717k.FEBRUARY || mo589p()) ? 5L : 4L);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return m574T(interfaceC1744r);
        }
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r == EnumC1727a.EPOCH_DAY) {
                return mo567E();
            }
            if (interfaceC1744r == EnumC1727a.PROLEPTIC_MONTH) {
                return m577W();
            }
            return m574T(interfaceC1744r);
        }
        return interfaceC1744r.mo806t(this);
    }

    /* renamed from: T */
    public final int m574T(InterfaceC1744r interfaceC1744r) {
        switch (AbstractC1679f.f532a[((EnumC1727a) interfaceC1744r).ordinal()]) {
            case 1:
                return this.f440c;
            case 2:
                return m576V();
            case 3:
                return ((this.f440c - 1) / 7) + 1;
            case 4:
                int i = this.f438a;
                return i >= 1 ? i : 1 - i;
            case 5:
                return m575U().getValue();
            case 6:
                return ((this.f440c - 1) % 7) + 1;
            case 7:
                return ((m576V() - 1) % 7) + 1;
            case 8:
                throw new C1747u("Invalid field 'EpochDay' for get() method, use getLong() instead");
            case 9:
                return ((m576V() - 1) / 7) + 1;
            case 10:
                return this.f439b;
            case 11:
                throw new C1747u("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case 12:
                return this.f438a;
            case 13:
                return this.f438a >= 1 ? 1 : 0;
            default:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
    }

    /* renamed from: W */
    public final long m577W() {
        return ((this.f438a * 12) + this.f439b) - 1;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: a */
    public final InterfaceC1661k mo581a() {
        return C1668r.f512c;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: G */
    public final InterfaceC1662l mo569G() {
        return getYear() >= 1 ? EnumC1669s.f513CE : EnumC1669s.BCE;
    }

    public int getYear() {
        return this.f438a;
    }

    /* renamed from: V */
    public final int m576V() {
        return (EnumC1717k.m786T(this.f439b).m787Q(mo589p()) + this.f440c) - 1;
    }

    /* renamed from: U */
    public final DayOfWeek m575U() {
        return DayOfWeek.m541Q(((int) AbstractC1636a.m498Q(mo567E() + 3, 7)) + 1);
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: p */
    public final boolean mo589p() {
        return C1668r.f512c.mo655O(this.f438a);
    }

    /* renamed from: Y */
    public final int m579Y() {
        short s = this.f439b;
        return s != 2 ? (s == 4 || s == 6 || s == 9 || s == 11) ? 30 : 31 : mo589p() ? 29 : 28;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: M */
    public final int mo571M() {
        return mo589p() ? 366 : 365;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: j0, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final LocalDate mo591x(InterfaceC1741o interfaceC1741o) {
        if (interfaceC1741o instanceof LocalDate) {
            return (LocalDate) interfaceC1741o;
        }
        return (LocalDate) interfaceC1741o.mo546n(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: i0, reason: merged with bridge method [inline-methods] */
    public final LocalDate mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return (LocalDate) interfaceC1744r.mo807y(this, j);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        enumC1727a.m800D(j);
        switch (AbstractC1679f.f532a[enumC1727a.ordinal()]) {
            case 1:
                int i = (int) j;
                if (this.f440c != i) {
                    return m566of(this.f438a, this.f439b, i);
                }
                return this;
            case 2:
                int i2 = (int) j;
                if (m576V() != i2) {
                    return m564c0(this.f438a, i2);
                }
                return this;
            case 3:
                return m584f0(j - mo542D(EnumC1727a.ALIGNED_WEEK_OF_MONTH));
            case 4:
                if (this.f438a < 1) {
                    j = 1 - j;
                }
                return m588k0((int) j);
            case 5:
                return plusDays(j - m575U().getValue());
            case 6:
                return plusDays(j - mo542D(EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_MONTH));
            case 7:
                return plusDays(j - mo542D(EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_YEAR));
            case 8:
                return m563b0(j);
            case 9:
                return m584f0(j - mo542D(EnumC1727a.ALIGNED_WEEK_OF_YEAR));
            case 10:
                int i3 = (int) j;
                if (this.f439b != i3) {
                    EnumC1727a.MONTH_OF_YEAR.m800D(i3);
                    return m565h0(this.f438a, i3, this.f440c);
                }
                return this;
            case 11:
                return m583e0(j - m577W());
            case 12:
                return m588k0((int) j);
            case 13:
                if (mo542D(EnumC1727a.ERA) != j) {
                    return m588k0(1 - this.f438a);
                }
                return this;
            default:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
    }

    /* renamed from: k0 */
    public final LocalDate m588k0(int i) {
        if (this.f438a == i) {
            return this;
        }
        EnumC1727a.YEAR.m800D(i);
        return m565h0(i, this.f439b, this.f440c);
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: J */
    public final InterfaceC1652b mo570J(InterfaceC1743q interfaceC1743q) {
        if (AbstractC1641c.m644b(interfaceC1743q)) {
            Period period = (Period) interfaceC1743q;
            return m583e0((period.f449a * 12) + period.f450b).plusDays(period.f451c);
        }
        Objects.requireNonNull(interfaceC1743q, "amountToAdd");
        return (LocalDate) ((Period) interfaceC1743q).mo550i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d0, reason: merged with bridge method [inline-methods] */
    public final LocalDate mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return (LocalDate) interfaceC1746t.mo808i(this, j);
        }
        switch (AbstractC1679f.f533b[((EnumC1728b) interfaceC1746t).ordinal()]) {
            case 1:
                return plusDays(j);
            case 2:
                return m584f0(j);
            case 3:
                return m583e0(j);
            case 4:
                return m585g0(j);
            case 5:
                return m585g0(AbstractC1636a.m500S(j, 10));
            case 6:
                return m585g0(AbstractC1636a.m500S(j, 100));
            case 7:
                return m585g0(AbstractC1636a.m500S(j, MediaDataController.MAX_STYLE_RUNS_COUNT));
            case 8:
                EnumC1727a enumC1727a = EnumC1727a.ERA;
                return mo556c(AbstractC1636a.m494M(mo542D(enumC1727a), j), enumC1727a);
            default:
                throw new C1747u("Unsupported unit: " + interfaceC1746t);
        }
    }

    /* renamed from: g0 */
    public final LocalDate m585g0(long j) {
        if (j == 0) {
            return this;
        }
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        return m565h0(enumC1727a.f671b.m820a(this.f438a + j, enumC1727a), this.f439b, this.f440c);
    }

    /* renamed from: e0 */
    public final LocalDate m583e0(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (this.f438a * 12) + (this.f439b - 1) + j;
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        long j3 = 12;
        return m565h0(enumC1727a.f671b.m820a(AbstractC1636a.m499R(j2, j3), enumC1727a), ((int) AbstractC1636a.m498Q(j2, j3)) + 1, this.f440c);
    }

    /* renamed from: f0 */
    public final LocalDate m584f0(long j) {
        return plusDays(AbstractC1636a.m500S(j, 7));
    }

    public LocalDate plusDays(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = this.f440c + j;
        if (j2 > 0) {
            if (j2 <= 28) {
                return new LocalDate(this.f438a, this.f439b, (int) j2);
            }
            if (j2 <= 59) {
                long jM579Y = m579Y();
                if (j2 <= jM579Y) {
                    return new LocalDate(this.f438a, this.f439b, (int) j2);
                }
                short s = this.f439b;
                if (s < 12) {
                    return new LocalDate(this.f438a, s + 1, (int) (j2 - jM579Y));
                }
                EnumC1727a.YEAR.m800D(this.f438a + 1);
                return new LocalDate(this.f438a + 1, 1, (int) (j2 - jM579Y));
            }
        }
        return m563b0(AbstractC1636a.m494M(mo567E(), j));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: Z, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final LocalDate mo559y(long j, InterfaceC1746t interfaceC1746t) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, interfaceC1746t).mo557d(1L, interfaceC1746t) : mo557d(-j, interfaceC1746t);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        return c1678e == AbstractC1745s.f695f ? this : AbstractC1636a.m521q(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return AbstractC1636a.m505a(this, interfaceC1739m);
    }

    public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.m719a(this);
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: F */
    public final ChronoLocalDateTime mo568F(C1715i c1715i) {
        return LocalDateTime.m593T(this, c1715i);
    }

    public LocalDateTime atStartOfDay() {
        return LocalDateTime.m593T(this, C1715i.f642g);
    }

    public ZonedDateTime atStartOfDay(ZoneId zoneId) {
        C1754b c1754bM840e;
        Objects.requireNonNull(zoneId, "zone");
        LocalDateTime localDateTimeM593T = LocalDateTime.m593T(this, C1715i.f642g);
        if (!(zoneId instanceof ZoneOffset) && (c1754bM840e = zoneId.getRules().m840e(localDateTimeM593T)) != null && c1754bM840e.m847i()) {
            localDateTimeM593T = c1754bM840e.f738b.m599W(c1754bM840e.f740d.getTotalSeconds() - c1754bM840e.f739c.getTotalSeconds());
        }
        return ZonedDateTime.m630Q(localDateTimeM593T, zoneId, null);
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: E */
    public final long mo567E() {
        long j = this.f438a;
        long j2 = this.f439b;
        long j3 = 365 * j;
        long j4 = (((367 * j2) - 362) / 12) + (j >= 0 ? ((j + 399) / 400) + (((3 + j) / 4) - ((99 + j) / 100)) + j3 : j3 - ((j / (-400)) + ((j / (-4)) - (j / (-100))))) + (this.f440c - 1);
        if (j2 > 2) {
            j4 = !mo589p() ? j4 - 2 : j4 - 1;
        }
        return j4 - 719528;
    }

    @Override // java.lang.Comparable
    /* renamed from: N, reason: merged with bridge method [inline-methods] */
    public final int compareTo(InterfaceC1652b interfaceC1652b) {
        if (interfaceC1652b instanceof LocalDate) {
            return m573Q((LocalDate) interfaceC1652b);
        }
        return AbstractC1636a.m508d(this, interfaceC1652b);
    }

    /* renamed from: Q */
    public final int m573Q(LocalDate localDate) {
        int i = this.f438a - localDate.f438a;
        if (i != 0) {
            return i;
        }
        int i2 = this.f439b - localDate.f439b;
        return i2 == 0 ? this.f440c - localDate.f440c : i2;
    }

    /* renamed from: X */
    public final boolean m578X(InterfaceC1652b interfaceC1652b) {
        return interfaceC1652b instanceof LocalDate ? m573Q((LocalDate) interfaceC1652b) < 0 : mo567E() < interfaceC1652b.mo567E();
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LocalDate) && m573Q((LocalDate) obj) == 0;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    public final int hashCode() {
        int i = this.f438a;
        return (((i << 11) + (this.f439b << 6)) + this.f440c) ^ (i & (-2048));
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    public final String toString() {
        int i = this.f438a;
        short s = this.f439b;
        short s2 = this.f440c;
        int iAbs = Math.abs(i);
        StringBuilder sb = new StringBuilder(10);
        if (iAbs >= 1000) {
            if (i > 9999) {
                sb.append(SignatureVisitor.EXTENDS);
            }
            sb.append(i);
        } else if (i < 0) {
            sb.append(i + XCallback.PRIORITY_LOWEST);
            sb.deleteCharAt(1);
        } else {
            sb.append(i + XCallback.PRIORITY_HIGHEST);
            sb.deleteCharAt(0);
        }
        sb.append(s < 10 ? "-0" : "-");
        sb.append((int) s);
        sb.append(s2 < 10 ? "-0" : "-");
        sb.append((int) s2);
        return sb.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 3, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
