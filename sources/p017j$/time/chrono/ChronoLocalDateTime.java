package p017j$.time.chrono;

import p017j$.time.C1715i;
import p017j$.time.Instant;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;

/* loaded from: classes2.dex */
public interface ChronoLocalDateTime<D extends InterfaceC1652b> extends InterfaceC1739m, InterfaceC1741o, Comparable<ChronoLocalDateTime<?>> {
    /* renamed from: H */
    int compareTo(ChronoLocalDateTime chronoLocalDateTime);

    /* renamed from: a */
    InterfaceC1661k mo603a();

    /* renamed from: b */
    C1715i mo605b();

    /* renamed from: f */
    InterfaceC1652b mo606f();

    Instant toInstant(ZoneOffset zoneOffset);

    /* renamed from: z */
    ChronoZonedDateTime mo607z(ZoneId zoneId);
}
