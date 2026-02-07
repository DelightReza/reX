package com.exteragram.messenger.api.model;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class RegDateFlag {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ RegDateFlag[] $VALUES;
    public static final RegDateFlag EXACT = new RegDateFlag("EXACT", 0);
    public static final RegDateFlag INTERPOLATED = new RegDateFlag("INTERPOLATED", 1);

    /* renamed from: LT */
    public static final RegDateFlag f149LT = new RegDateFlag("LT", 2);

    /* renamed from: ET */
    public static final RegDateFlag f148ET = new RegDateFlag("ET", 3);

    private static final /* synthetic */ RegDateFlag[] $values() {
        return new RegDateFlag[]{EXACT, INTERPOLATED, f149LT, f148ET};
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    private RegDateFlag(String str, int i) {
    }

    static {
        RegDateFlag[] regDateFlagArr$values = $values();
        $VALUES = regDateFlagArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(regDateFlagArr$values);
    }

    public static RegDateFlag valueOf(String str) {
        return (RegDateFlag) Enum.valueOf(RegDateFlag.class, str);
    }

    public static RegDateFlag[] values() {
        return (RegDateFlag[]) $VALUES.clone();
    }
}
