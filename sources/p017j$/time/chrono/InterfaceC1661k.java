package p017j$.time.chrono;

import java.util.List;
import java.util.Map;
import p017j$.time.Instant;
import p017j$.time.LocalDateTime;
import p017j$.time.ZoneId;
import p017j$.time.format.EnumC1684E;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;

/* renamed from: j$.time.chrono.k */
/* loaded from: classes2.dex */
public interface InterfaceC1661k extends Comparable {
    /* renamed from: A */
    InterfaceC1652b mo651A(InterfaceC1740n interfaceC1740n);

    /* renamed from: B */
    ChronoLocalDateTime mo670B(LocalDateTime localDateTime);

    /* renamed from: I */
    InterfaceC1652b mo652I(int i, int i2, int i3);

    /* renamed from: K */
    InterfaceC1652b mo653K(Map map, EnumC1684E enumC1684E);

    /* renamed from: L */
    ChronoZonedDateTime mo654L(Instant instant, ZoneId zoneId);

    /* renamed from: O */
    boolean mo655O(long j);

    boolean equals(Object obj);

    String getId();

    /* renamed from: h */
    InterfaceC1652b mo656h(long j);

    int hashCode();

    /* renamed from: l */
    String mo658l();

    /* renamed from: m */
    InterfaceC1652b mo659m(int i, int i2);

    /* renamed from: q */
    C1748v mo660q(EnumC1727a enumC1727a);

    /* renamed from: s */
    List mo661s();

    String toString();

    /* renamed from: u */
    InterfaceC1662l mo662u(int i);

    /* renamed from: v */
    int mo663v(InterfaceC1662l interfaceC1662l, int i);
}
