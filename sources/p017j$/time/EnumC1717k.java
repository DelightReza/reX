package p017j$.time;

import org.mvel2.asm.Opcodes;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.k */
/* loaded from: classes2.dex */
public final class EnumC1717k implements InterfaceC1740n, InterfaceC1741o {
    public static final EnumC1717k APRIL;
    public static final EnumC1717k AUGUST;
    public static final EnumC1717k DECEMBER;
    public static final EnumC1717k FEBRUARY;
    public static final EnumC1717k JANUARY;
    public static final EnumC1717k JULY;
    public static final EnumC1717k JUNE;
    public static final EnumC1717k MARCH;
    public static final EnumC1717k MAY;
    public static final EnumC1717k NOVEMBER;
    public static final EnumC1717k OCTOBER;
    public static final EnumC1717k SEPTEMBER;

    /* renamed from: a */
    public static final EnumC1717k[] f649a;

    /* renamed from: b */
    public static final /* synthetic */ EnumC1717k[] f650b;

    public static EnumC1717k valueOf(String str) {
        return (EnumC1717k) Enum.valueOf(EnumC1717k.class, str);
    }

    public static EnumC1717k[] values() {
        return (EnumC1717k[]) f650b.clone();
    }

    static {
        EnumC1717k enumC1717k = new EnumC1717k("JANUARY", 0);
        JANUARY = enumC1717k;
        EnumC1717k enumC1717k2 = new EnumC1717k("FEBRUARY", 1);
        FEBRUARY = enumC1717k2;
        EnumC1717k enumC1717k3 = new EnumC1717k("MARCH", 2);
        MARCH = enumC1717k3;
        EnumC1717k enumC1717k4 = new EnumC1717k("APRIL", 3);
        APRIL = enumC1717k4;
        EnumC1717k enumC1717k5 = new EnumC1717k("MAY", 4);
        MAY = enumC1717k5;
        EnumC1717k enumC1717k6 = new EnumC1717k("JUNE", 5);
        JUNE = enumC1717k6;
        EnumC1717k enumC1717k7 = new EnumC1717k("JULY", 6);
        JULY = enumC1717k7;
        EnumC1717k enumC1717k8 = new EnumC1717k("AUGUST", 7);
        AUGUST = enumC1717k8;
        EnumC1717k enumC1717k9 = new EnumC1717k("SEPTEMBER", 8);
        SEPTEMBER = enumC1717k9;
        EnumC1717k enumC1717k10 = new EnumC1717k("OCTOBER", 9);
        OCTOBER = enumC1717k10;
        EnumC1717k enumC1717k11 = new EnumC1717k("NOVEMBER", 10);
        NOVEMBER = enumC1717k11;
        EnumC1717k enumC1717k12 = new EnumC1717k("DECEMBER", 11);
        DECEMBER = enumC1717k12;
        f650b = new EnumC1717k[]{enumC1717k, enumC1717k2, enumC1717k3, enumC1717k4, enumC1717k5, enumC1717k6, enumC1717k7, enumC1717k8, enumC1717k9, enumC1717k10, enumC1717k11, enumC1717k12};
        f649a = values();
    }

    /* renamed from: T */
    public static EnumC1717k m786T(int i) {
        if (i < 1 || i > 12) {
            throw new C1640b("Invalid value for MonthOfYear: " + i);
        }
        return f649a[i - 1];
    }

    public final int getValue() {
        return ordinal() + 1;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.MONTH_OF_YEAR : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.MONTH_OF_YEAR) {
            return interfaceC1744r.mo805n();
        }
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.MONTH_OF_YEAR) {
            return getValue();
        }
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.MONTH_OF_YEAR) {
            return getValue();
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return interfaceC1744r.mo806t(this);
    }

    /* renamed from: R */
    public final int m788R(boolean z) {
        int i = AbstractC1716j.f648a[ordinal()];
        return i != 1 ? (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31 : z ? 29 : 28;
    }

    /* renamed from: S */
    public final int m789S() {
        int i = AbstractC1716j.f648a[ordinal()];
        if (i != 1) {
            return (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31;
        }
        return 29;
    }

    /* renamed from: Q */
    public final int m787Q(boolean z) {
        switch (AbstractC1716j.f648a[ordinal()]) {
            case 1:
                return 32;
            case 2:
                return (z ? 1 : 0) + 91;
            case 3:
                return (z ? 1 : 0) + Opcodes.DCMPG;
            case 4:
                return (z ? 1 : 0) + 244;
            case 5:
                return (z ? 1 : 0) + 305;
            case 6:
                return 1;
            case 7:
                return (z ? 1 : 0) + 60;
            case 8:
                return (z ? 1 : 0) + Opcodes.LSHL;
            case 9:
                return (z ? 1 : 0) + Opcodes.INVOKEVIRTUAL;
            case 10:
                return (z ? 1 : 0) + 213;
            case 11:
                return (z ? 1 : 0) + 274;
            default:
                return (z ? 1 : 0) + 335;
        }
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f691b) {
            return C1668r.f512c;
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.MONTHS;
        }
        return AbstractC1745s.m815c(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        if (!AbstractC1636a.m492K(interfaceC1739m).equals(C1668r.f512c)) {
            throw new C1640b("Adjustment only supported on ISO date-time");
        }
        return interfaceC1739m.mo556c(getValue(), EnumC1727a.MONTH_OF_YEAR);
    }
}
