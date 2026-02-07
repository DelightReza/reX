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
/* renamed from: j$.time.chrono.s */
/* loaded from: classes2.dex */
public final class EnumC1669s implements InterfaceC1662l {
    public static final EnumC1669s BCE;

    /* renamed from: CE */
    public static final EnumC1669s f513CE;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1669s[] f514a;

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

    public static EnumC1669s valueOf(String str) {
        return (EnumC1669s) Enum.valueOf(EnumC1669s.class, str);
    }

    public static EnumC1669s[] values() {
        return (EnumC1669s[]) f514a.clone();
    }

    static {
        EnumC1669s enumC1669s = new EnumC1669s("BCE", 0);
        BCE = enumC1669s;
        EnumC1669s enumC1669s2 = new EnumC1669s("CE", 1);
        f513CE = enumC1669s2;
        f514a = new EnumC1669s[]{enumC1669s, enumC1669s2};
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
