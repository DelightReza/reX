package p017j$.time.chrono;

import p017j$.time.C1715i;
import p017j$.time.Instant;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.temporal.InterfaceC1739m;

/* loaded from: classes2.dex */
public interface ChronoZonedDateTime<D extends InterfaceC1652b> extends InterfaceC1739m, Comparable<ChronoZonedDateTime<?>> {
    /* renamed from: C */
    ZoneId mo632C();

    /* renamed from: P */
    long mo633P();

    /* renamed from: a */
    InterfaceC1661k mo637a();

    /* renamed from: b */
    C1715i mo638b();

    /* renamed from: f */
    InterfaceC1652b mo639f();

    /* renamed from: g */
    ZoneOffset mo640g();

    /* renamed from: o */
    ChronoLocalDateTime mo641o();

    Instant toInstant();

    /* renamed from: w */
    ChronoZonedDateTime mo642w(ZoneId zoneId);
}
