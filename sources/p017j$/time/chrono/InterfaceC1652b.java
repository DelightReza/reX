package p017j$.time.chrono;

import p017j$.time.C1715i;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1743q;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;

/* renamed from: j$.time.chrono.b */
/* loaded from: classes2.dex */
public interface InterfaceC1652b extends InterfaceC1739m, InterfaceC1741o, Comparable {
    /* renamed from: E */
    long mo567E();

    /* renamed from: F */
    ChronoLocalDateTime mo568F(C1715i c1715i);

    /* renamed from: G */
    InterfaceC1662l mo569G();

    /* renamed from: J */
    InterfaceC1652b mo570J(InterfaceC1743q interfaceC1743q);

    /* renamed from: M */
    int mo571M();

    /* renamed from: N */
    int compareTo(InterfaceC1652b interfaceC1652b);

    /* renamed from: a */
    InterfaceC1661k mo581a();

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    InterfaceC1652b mo556c(long j, InterfaceC1744r interfaceC1744r);

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    InterfaceC1652b mo557d(long j, InterfaceC1746t interfaceC1746t);

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    boolean mo543e(InterfaceC1744r interfaceC1744r);

    boolean equals(Object obj);

    int hashCode();

    /* renamed from: p */
    boolean mo589p();

    /* renamed from: r */
    InterfaceC1652b mo559y(long j, InterfaceC1746t interfaceC1746t);

    String toString();

    /* renamed from: x */
    InterfaceC1652b mo591x(InterfaceC1741o interfaceC1741o);
}
