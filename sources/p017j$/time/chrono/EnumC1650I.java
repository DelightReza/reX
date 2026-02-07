package p017j$.time.chrono;

import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1678e;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1744r;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.chrono.I */
/* loaded from: classes2.dex */
public final class EnumC1650I implements InterfaceC1662l {

    /* renamed from: BE */
    public static final EnumC1650I f476BE;
    public static final EnumC1650I BEFORE_BE;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1650I[] f477a;

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final /* synthetic */ long mo542D(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m518n(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final /* synthetic */ boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m520p(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m517m(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final /* synthetic */ Object mo547t(C1678e c1678e) {
        return AbstractC1636a.m524t(this, c1678e);
    }

    public static EnumC1650I valueOf(String str) {
        return (EnumC1650I) Enum.valueOf(EnumC1650I.class, str);
    }

    public static EnumC1650I[] values() {
        return (EnumC1650I[]) f477a.clone();
    }

    static {
        EnumC1650I enumC1650I = new EnumC1650I("BEFORE_BE", 0);
        BEFORE_BE = enumC1650I;
        EnumC1650I enumC1650I2 = new EnumC1650I("BE", 1);
        f476BE = enumC1650I2;
        f477a = new EnumC1650I[]{enumC1650I, enumC1650I2};
    }

    @Override // p017j$.time.chrono.InterfaceC1662l
    public final int getValue() {
        return ordinal();
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(getValue(), EnumC1727a.ERA);
    }
}
