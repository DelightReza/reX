package p017j$.com.android.tools.p018r8;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import org.mvel2.asm.signature.SignatureVisitor;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.AbstractC1641c;
import p017j$.time.C1639a;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.chrono.AbstractC1651a;
import p017j$.time.chrono.AbstractC1658h;
import p017j$.time.chrono.C1647F;
import p017j$.time.chrono.C1664n;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.C1671u;
import p017j$.time.chrono.C1676z;
import p017j$.time.chrono.ChronoLocalDateTime;
import p017j$.time.chrono.ChronoZonedDateTime;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.chrono.InterfaceC1662l;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.AbstractC1855s0;
import p017j$.util.C1761A;
import p017j$.util.C1762B;
import p017j$.util.C1763C;
import p017j$.util.C1767G;
import p017j$.util.C1771K;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC2129y;
import p017j$.util.Objects;
import p017j$.util.OptionalInt;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.C1802k;
import p017j$.util.concurrent.ConcurrentHashMap;
import p017j$.util.function.C1823b;
import p017j$.util.function.C1828g;
import sun.misc.Unsafe;

/* renamed from: j$.com.android.tools.r8.a */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC1636a {
    /* renamed from: L */
    public static /* synthetic */ int m493L(long j) {
        int i = (int) j;
        if (j == i) {
            return i;
        }
        throw new ArithmeticException();
    }

    /* renamed from: M */
    public static /* synthetic */ long m494M(long j, long j2) {
        long j3 = j + j2;
        if (((j2 ^ j) < 0) || ((j ^ j3) >= 0)) {
            return j3;
        }
        throw new ArithmeticException();
    }

    /* renamed from: N */
    public static /* synthetic */ List m495N(Object[] objArr) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add(Objects.requireNonNull(obj));
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* renamed from: O */
    public static /* synthetic */ Map.Entry m496O(Object obj, Object obj2) {
        return new AbstractMap.SimpleImmutableEntry(Objects.requireNonNull(obj), Objects.requireNonNull(obj2));
    }

    /* renamed from: P */
    public static /* synthetic */ boolean m497P(Unsafe unsafe, Object obj, long j, C1802k c1802k) {
        while (true) {
            Unsafe unsafe2 = unsafe;
            Object obj2 = obj;
            long j2 = j;
            C1802k c1802k2 = c1802k;
            if (unsafe2.compareAndSwapObject(obj2, j2, (Object) null, c1802k2)) {
                return true;
            }
            if (unsafe2.getObject(obj2, j2) != null) {
                return false;
            }
            unsafe = unsafe2;
            obj = obj2;
            j = j2;
            c1802k = c1802k2;
        }
    }

    /* renamed from: Q */
    public static /* synthetic */ long m498Q(long j, long j2) {
        long j3 = j % j2;
        if (j3 == 0) {
            return 0L;
        }
        return (((j ^ j2) >> 63) | 1) > 0 ? j3 : j3 + j2;
    }

    /* renamed from: R */
    public static /* synthetic */ long m499R(long j, long j2) {
        long j3 = j / j2;
        return (j - (j2 * j3) != 0 && (((j ^ j2) >> 63) | 1) < 0) ? j3 - 1 : j3;
    }

    /* renamed from: S */
    public static /* synthetic */ long m500S(long j, long j2) {
        int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(~j2) + Long.numberOfLeadingZeros(j2) + Long.numberOfLeadingZeros(~j) + Long.numberOfLeadingZeros(j);
        if (iNumberOfLeadingZeros > 65) {
            return j * j2;
        }
        if (iNumberOfLeadingZeros >= 64) {
            if ((j >= 0) | (j2 != Long.MIN_VALUE)) {
                long j3 = j * j2;
                if (j == 0 || j3 / j == j2) {
                    return j3;
                }
            }
        }
        throw new ArithmeticException();
    }

    /* renamed from: T */
    public static /* synthetic */ long m501T(long j, long j2) {
        long j3 = j - j2;
        if (((j2 ^ j) >= 0) || ((j ^ j3) >= 0)) {
            return j3;
        }
        throw new ArithmeticException();
    }

    /* renamed from: F */
    public static Optional m487F(p017j$.util.Optional optional) {
        if (optional == null) {
            return null;
        }
        if (optional.isPresent()) {
            return Optional.of(optional.get());
        }
        return Optional.empty();
    }

    /* renamed from: B */
    public static p017j$.util.Optional m483B(Optional optional) {
        if (optional == null) {
            return null;
        }
        if (optional.isPresent()) {
            return p017j$.util.Optional.m856of(optional.get());
        }
        return p017j$.util.Optional.empty();
    }

    /* renamed from: C */
    public static C1761A m484C(OptionalDouble optionalDouble) {
        if (optionalDouble == null) {
            return null;
        }
        if (!optionalDouble.isPresent()) {
            return C1761A.f757c;
        }
        return new C1761A(optionalDouble.getAsDouble());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [j$.util.function.b] */
    /* renamed from: b */
    public static C1823b m506b(final DoubleConsumer doubleConsumer, final DoubleConsumer doubleConsumer2) {
        Objects.requireNonNull(doubleConsumer2);
        return new DoubleConsumer() { // from class: j$.util.function.b
            public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer3) {
                return AbstractC1636a.m506b(this, doubleConsumer3);
            }

            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                doubleConsumer.accept(d);
                doubleConsumer2.accept(d);
            }
        };
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [j$.util.function.g] */
    /* renamed from: c */
    public static C1828g m507c(final LongConsumer longConsumer, final LongConsumer longConsumer2) {
        Objects.requireNonNull(longConsumer2);
        return new LongConsumer() { // from class: j$.util.function.g
            public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer3) {
                return AbstractC1636a.m507c(this, longConsumer3);
            }

            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                longConsumer.accept(j);
                longConsumer2.accept(j);
            }
        };
    }

    /* renamed from: E */
    public static C1762B m486E(OptionalLong optionalLong) {
        if (optionalLong == null) {
            return null;
        }
        if (!optionalLong.isPresent()) {
            return C1762B.f760c;
        }
        return new C1762B(optionalLong.getAsLong());
    }

    /* renamed from: A */
    public static String m482A(long j, String str, Locale locale) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(0, (int) j, 0, 0, 0, 0);
        return simpleDateFormat.format(calendar.getTime());
    }

    /* renamed from: D */
    public static OptionalInt m485D(java.util.OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        if (!optionalInt.isPresent()) {
            return OptionalInt.f779c;
        }
        return new OptionalInt(optionalInt.getAsInt());
    }

    /* renamed from: h */
    public static void m512h(ConcurrentMap concurrentMap, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        for (Map.Entry entry : concurrentMap.entrySet()) {
            try {
                biConsumer.accept(entry.getKey(), entry.getValue());
            } catch (IllegalStateException unused) {
            }
        }
    }

    /* renamed from: U */
    public static String m502U(Object obj, Object obj2) {
        String string;
        String string2;
        String str = "null";
        if (obj == null || (string = obj.toString()) == null) {
            string = "null";
        }
        int length = string.length();
        if (obj2 != null && (string2 = obj2.toString()) != null) {
            str = string2;
        }
        int length2 = str.length();
        char[] cArr = new char[length + length2 + 1];
        string.getChars(0, length, cArr, 0);
        cArr[length] = SignatureVisitor.INSTANCEOF;
        str.getChars(0, length2, cArr, length + 1);
        return new String(cArr);
    }

    /* renamed from: z */
    public static String m530z(long j, String str, Locale locale) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        calendar.set(2016, 1, (int) j, 0, 0, 0);
        return simpleDateFormat.format(calendar.getTime());
    }

    /* renamed from: J */
    public static void m491J(Iterator it, Consumer consumer) {
        if (it instanceof InterfaceC2129y) {
            ((InterfaceC2129y) it).forEachRemaining(consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    /* renamed from: G */
    public static OptionalDouble m488G(C1761A c1761a) {
        if (c1761a == null) {
            return null;
        }
        boolean z = c1761a.f758a;
        if (!z) {
            return OptionalDouble.empty();
        }
        if (z) {
            return OptionalDouble.of(c1761a.f759b);
        }
        throw new NoSuchElementException("No value present");
    }

    /* renamed from: H */
    public static java.util.OptionalInt m489H(OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        boolean z = optionalInt.f780a;
        if (!z) {
            return java.util.OptionalInt.empty();
        }
        if (z) {
            return java.util.OptionalInt.of(optionalInt.f781b);
        }
        throw new NoSuchElementException("No value present");
    }

    /* renamed from: I */
    public static OptionalLong m490I(C1762B c1762b) {
        if (c1762b == null) {
            return null;
        }
        boolean z = c1762b.f761a;
        if (!z) {
            return OptionalLong.empty();
        }
        if (z) {
            return OptionalLong.of(c1762b.f762b);
        }
        throw new NoSuchElementException("No value present");
    }

    /* renamed from: p */
    public static boolean m520p(InterfaceC1662l interfaceC1662l, InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.ERA : interfaceC1744r != null && interfaceC1744r.mo802i(interfaceC1662l);
    }

    /* renamed from: K */
    public static InterfaceC1661k m492K(InterfaceC1740n interfaceC1740n) {
        Objects.requireNonNull(interfaceC1740n, "temporal");
        return (InterfaceC1661k) Objects.requireNonNullElse((InterfaceC1661k) interfaceC1740n.mo547t(AbstractC1745s.f691b), C1668r.f512c);
    }

    /* renamed from: W */
    public static C1639a m504W() {
        return new C1639a(ZoneId.systemDefault());
    }

    /* renamed from: l */
    public static int m516l(ChronoZonedDateTime chronoZonedDateTime, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            int i = AbstractC1658h.f489a[((EnumC1727a) interfaceC1744r).ordinal()];
            if (i == 1) {
                throw new C1747u("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
            }
            if (i == 2) {
                return chronoZonedDateTime.mo640g().getTotalSeconds();
            }
            return chronoZonedDateTime.mo641o().mo544i(interfaceC1744r);
        }
        return AbstractC1745s.m813a(chronoZonedDateTime, interfaceC1744r);
    }

    /* renamed from: m */
    public static int m517m(InterfaceC1662l interfaceC1662l, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.ERA) {
            return interfaceC1662l.getValue();
        }
        return AbstractC1745s.m813a(interfaceC1662l, interfaceC1744r);
    }

    /* renamed from: n */
    public static long m518n(InterfaceC1662l interfaceC1662l, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.ERA) {
            return interfaceC1662l.getValue();
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return interfaceC1744r.mo806t(interfaceC1662l);
    }

    /* renamed from: V */
    public static InterfaceC1661k m503V(String str) {
        ConcurrentHashMap concurrentHashMap = AbstractC1651a.f478a;
        Objects.requireNonNull(str, "id");
        while (true) {
            ConcurrentHashMap concurrentHashMap2 = AbstractC1651a.f478a;
            InterfaceC1661k interfaceC1661k = (InterfaceC1661k) concurrentHashMap2.get(str);
            if (interfaceC1661k == null) {
                interfaceC1661k = (InterfaceC1661k) AbstractC1651a.f479b.get(str);
            }
            if (interfaceC1661k != null) {
                return interfaceC1661k;
            }
            if (concurrentHashMap2.get("ISO") != null) {
                Iterator it = ServiceLoader.load(InterfaceC1661k.class).iterator();
                while (it.hasNext()) {
                    InterfaceC1661k interfaceC1661k2 = (InterfaceC1661k) it.next();
                    if (str.equals(interfaceC1661k2.getId()) || str.equals(interfaceC1661k2.mo658l())) {
                        return interfaceC1661k2;
                    }
                }
                throw new C1640b("Unknown chronology: " + str);
            }
            C1664n c1664n = C1664n.f495l;
            c1664n.getClass();
            AbstractC1651a.m668k(c1664n, "Hijrah-umalqura");
            C1671u c1671u = C1671u.f516c;
            c1671u.getClass();
            AbstractC1651a.m668k(c1671u, "Japanese");
            C1676z c1676z = C1676z.f528c;
            c1676z.getClass();
            AbstractC1651a.m668k(c1676z, "Minguo");
            C1647F c1647f = C1647F.f473c;
            c1647f.getClass();
            AbstractC1651a.m668k(c1647f, "ThaiBuddhist");
            try {
                for (AbstractC1651a abstractC1651a : Arrays.asList(new AbstractC1651a[0])) {
                    if (!abstractC1651a.getId().equals("ISO")) {
                        AbstractC1651a.m668k(abstractC1651a, abstractC1651a.getId());
                    }
                }
                C1668r c1668r = C1668r.f512c;
                c1668r.getClass();
                AbstractC1651a.m668k(c1668r, "ISO");
            } catch (Throwable th) {
                throw new ServiceConfigurationError(th.getMessage(), th);
            }
        }
    }

    /* renamed from: t */
    public static Object m524t(InterfaceC1662l interfaceC1662l, C1678e c1678e) {
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.ERAS;
        }
        return AbstractC1745s.m815c(interfaceC1662l, c1678e);
    }

    /* renamed from: r */
    public static Object m522r(ChronoLocalDateTime chronoLocalDateTime, C1678e c1678e) {
        if (c1678e == AbstractC1745s.f690a || c1678e == AbstractC1745s.f694e || c1678e == AbstractC1745s.f693d) {
            return null;
        }
        if (c1678e == AbstractC1745s.f696g) {
            return chronoLocalDateTime.mo605b();
        }
        if (c1678e == AbstractC1745s.f691b) {
            return chronoLocalDateTime.mo603a();
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.NANOS;
        }
        return c1678e.m704g(chronoLocalDateTime);
    }

    /* renamed from: o */
    public static boolean m519o(InterfaceC1652b interfaceC1652b, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).isDateBased();
        }
        return interfaceC1744r != null && interfaceC1744r.mo802i(interfaceC1652b);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
    
        return r1;
     */
    /* renamed from: g */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object m511g(java.util.concurrent.ConcurrentMap r2, java.lang.Object r3, java.util.function.BiFunction r4) {
        /*
        L0:
            java.lang.Object r0 = r2.get(r3)
        L4:
            java.lang.Object r1 = r4.apply(r3, r0)
            if (r1 == 0) goto L1a
            if (r0 == 0) goto L13
            boolean r0 = r2.replace(r3, r0, r1)
            if (r0 == 0) goto L0
            goto L19
        L13:
            java.lang.Object r0 = r2.putIfAbsent(r3, r1)
            if (r0 != 0) goto L4
        L19:
            return r1
        L1a:
            if (r0 == 0) goto L22
            boolean r0 = r2.remove(r3, r0)
            if (r0 == 0) goto L0
        L22:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.com.android.tools.p018r8.AbstractC1636a.m511g(java.util.concurrent.ConcurrentMap, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    /* renamed from: u */
    public static long m525u(ChronoLocalDateTime chronoLocalDateTime, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return ((chronoLocalDateTime.mo606f().mo567E() * 86400) + chronoLocalDateTime.mo605b().m782d0()) - zoneOffset.getTotalSeconds();
    }

    /* renamed from: s */
    public static Object m523s(ChronoZonedDateTime chronoZonedDateTime, C1678e c1678e) {
        if (c1678e == AbstractC1745s.f694e || c1678e == AbstractC1745s.f690a) {
            return chronoZonedDateTime.mo632C();
        }
        if (c1678e == AbstractC1745s.f693d) {
            return chronoZonedDateTime.mo640g();
        }
        if (c1678e == AbstractC1745s.f696g) {
            return chronoZonedDateTime.mo638b();
        }
        if (c1678e == AbstractC1745s.f691b) {
            return chronoZonedDateTime.mo637a();
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.NANOS;
        }
        return c1678e.m704g(chronoZonedDateTime);
    }

    /* renamed from: e */
    public static int m509e(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
        int iMo572N = chronoLocalDateTime.mo606f().compareTo(chronoLocalDateTime2.mo606f());
        return (iMo572N == 0 && (iMo572N = chronoLocalDateTime.mo605b().compareTo(chronoLocalDateTime2.mo605b())) == 0) ? ((AbstractC1651a) chronoLocalDateTime.mo603a()).getId().compareTo(chronoLocalDateTime2.mo603a().getId()) : iMo572N;
    }

    /* renamed from: q */
    public static Object m521q(InterfaceC1652b interfaceC1652b, C1678e c1678e) {
        if (c1678e == AbstractC1745s.f690a || c1678e == AbstractC1745s.f694e || c1678e == AbstractC1745s.f693d || c1678e == AbstractC1745s.f696g) {
            return null;
        }
        if (c1678e == AbstractC1745s.f691b) {
            return interfaceC1652b.mo581a();
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.DAYS;
        }
        return c1678e.m704g(interfaceC1652b);
    }

    /* renamed from: a */
    public static InterfaceC1739m m505a(InterfaceC1652b interfaceC1652b, InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(interfaceC1652b.mo567E(), EnumC1727a.EPOCH_DAY);
    }

    /* renamed from: v */
    public static long m526v(ChronoZonedDateTime chronoZonedDateTime) {
        return ((chronoZonedDateTime.mo639f().mo567E() * 86400) + chronoZonedDateTime.mo638b().m782d0()) - chronoZonedDateTime.mo640g().getTotalSeconds();
    }

    /* renamed from: f */
    public static int m510f(ChronoZonedDateTime chronoZonedDateTime, ChronoZonedDateTime chronoZonedDateTime2) {
        int iCompare = Long.compare(chronoZonedDateTime.mo633P(), chronoZonedDateTime2.mo633P());
        return (iCompare == 0 && (iCompare = chronoZonedDateTime.mo638b().f647d - chronoZonedDateTime2.mo638b().f647d) == 0 && (iCompare = chronoZonedDateTime.mo641o().mo595H(chronoZonedDateTime2.mo641o())) == 0 && (iCompare = chronoZonedDateTime.mo632C().getId().compareTo(chronoZonedDateTime2.mo632C().getId())) == 0) ? ((AbstractC1651a) chronoZonedDateTime.mo637a()).getId().compareTo(chronoZonedDateTime2.mo637a().getId()) : iCompare;
    }

    /* renamed from: x */
    public static boolean m528x(Spliterator.OfInt ofInt, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            return ofInt.tryAdvance((IntConsumer) consumer);
        }
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(ofInt.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return ofInt.tryAdvance((IntConsumer) new C1767G(consumer, 0));
    }

    /* renamed from: j */
    public static void m514j(Spliterator.OfInt ofInt, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            ofInt.forEachRemaining((IntConsumer) consumer);
        } else {
            if (AbstractC1855s0.f959a) {
                AbstractC1855s0.m914a(ofInt.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            ofInt.forEachRemaining((IntConsumer) new C1767G(consumer, 0));
        }
    }

    /* renamed from: d */
    public static int m508d(InterfaceC1652b interfaceC1652b, InterfaceC1652b interfaceC1652b2) {
        int iCompare = Long.compare(interfaceC1652b.mo567E(), interfaceC1652b2.mo567E());
        if (iCompare != 0) {
            return iCompare;
        }
        return ((AbstractC1651a) interfaceC1652b.mo581a()).getId().compareTo(interfaceC1652b2.mo581a().getId());
    }

    /* renamed from: y */
    public static boolean m529y(InterfaceC1784Y interfaceC1784Y, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            return interfaceC1784Y.tryAdvance((LongConsumer) consumer);
        }
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(interfaceC1784Y.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return interfaceC1784Y.tryAdvance((LongConsumer) new C1771K(consumer, 0));
    }

    /* renamed from: k */
    public static void m515k(InterfaceC1784Y interfaceC1784Y, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            interfaceC1784Y.forEachRemaining((LongConsumer) consumer);
        } else {
            if (AbstractC1855s0.f959a) {
                AbstractC1855s0.m914a(interfaceC1784Y.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            interfaceC1784Y.forEachRemaining((LongConsumer) new C1771K(consumer, 0));
        }
    }

    /* renamed from: w */
    public static boolean m527w(InterfaceC1779T interfaceC1779T, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            return interfaceC1779T.tryAdvance((DoubleConsumer) consumer);
        }
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(interfaceC1779T.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        return interfaceC1779T.tryAdvance((DoubleConsumer) new C1763C(consumer, 0));
    }

    /* renamed from: i */
    public static void m513i(InterfaceC1779T interfaceC1779T, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            interfaceC1779T.forEachRemaining((DoubleConsumer) consumer);
        } else {
            if (AbstractC1855s0.f959a) {
                AbstractC1855s0.m914a(interfaceC1779T.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                throw null;
            }
            Objects.requireNonNull(consumer);
            interfaceC1779T.forEachRemaining((DoubleConsumer) new C1763C(consumer, 0));
        }
    }

    public Spliterator trySplit() {
        return null;
    }

    public boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        return false;
    }

    public void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
    }

    public long estimateSize() {
        return 0L;
    }

    public int characteristics() {
        return 16448;
    }
}
