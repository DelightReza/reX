package p017j$.time;

import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.format.C1706t;
import p017j$.time.format.C1707u;
import p017j$.time.format.EnumC1684E;
import p017j$.time.format.TextStyle;
import p017j$.time.zone.ZoneRules;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public abstract class ZoneId implements Serializable {

    /* renamed from: a */
    public static final Map f455a;
    private static final long serialVersionUID = 8352817235686L;

    /* renamed from: T */
    public abstract void mo623T(DataOutput dataOutput);

    public abstract String getId();

    public abstract ZoneRules getRules();

    static {
        Map.Entry[] entryArr = {AbstractC1636a.m496O("ACT", "Australia/Darwin"), AbstractC1636a.m496O("AET", "Australia/Sydney"), AbstractC1636a.m496O("AGT", "America/Argentina/Buenos_Aires"), AbstractC1636a.m496O("ART", "Africa/Cairo"), AbstractC1636a.m496O("AST", "America/Anchorage"), AbstractC1636a.m496O("BET", "America/Sao_Paulo"), AbstractC1636a.m496O("BST", "Asia/Dhaka"), AbstractC1636a.m496O("CAT", "Africa/Harare"), AbstractC1636a.m496O("CNT", "America/St_Johns"), AbstractC1636a.m496O("CST", "America/Chicago"), AbstractC1636a.m496O("CTT", "Asia/Shanghai"), AbstractC1636a.m496O("EAT", "Africa/Addis_Ababa"), AbstractC1636a.m496O("ECT", "Europe/Paris"), AbstractC1636a.m496O("IET", "America/Indiana/Indianapolis"), AbstractC1636a.m496O("IST", "Asia/Kolkata"), AbstractC1636a.m496O("JST", "Asia/Tokyo"), AbstractC1636a.m496O("MIT", "Pacific/Apia"), AbstractC1636a.m496O("NET", "Asia/Yerevan"), AbstractC1636a.m496O("NST", "Pacific/Auckland"), AbstractC1636a.m496O("PLT", "Asia/Karachi"), AbstractC1636a.m496O("PNT", "America/Phoenix"), AbstractC1636a.m496O("PRT", "America/Puerto_Rico"), AbstractC1636a.m496O("PST", "America/Los_Angeles"), AbstractC1636a.m496O("SST", "Pacific/Guadalcanal"), AbstractC1636a.m496O("VST", "Asia/Ho_Chi_Minh"), AbstractC1636a.m496O("EST", "-05:00"), AbstractC1636a.m496O("MST", "-07:00"), AbstractC1636a.m496O("HST", "-10:00")};
        HashMap map = new HashMap(28);
        for (int i = 0; i < 28; i++) {
            Map.Entry entry = entryArr[i];
            Object objRequireNonNull = Objects.requireNonNull(entry.getKey());
            if (map.put(objRequireNonNull, Objects.requireNonNull(entry.getValue())) != null) {
                throw new IllegalArgumentException("duplicate key: " + objRequireNonNull);
            }
        }
        f455a = Collections.unmodifiableMap(map);
    }

    public static ZoneId systemDefault() {
        String id = TimeZone.getDefault().getID();
        Map map = f455a;
        Objects.requireNonNull(id, "zoneId");
        Objects.requireNonNull(map, "aliasMap");
        return m622of((String) Objects.requireNonNullElse((String) map.get(id), id));
    }

    /* renamed from: of */
    public static ZoneId m622of(String str) {
        return m619Q(str, true);
    }

    /* renamed from: R */
    public static ZoneId m620R(String str, ZoneOffset zoneOffset) {
        Objects.requireNonNull(str, "prefix");
        Objects.requireNonNull(zoneOffset, "offset");
        if (str.isEmpty()) {
            return zoneOffset;
        }
        if (!str.equals("GMT") && !str.equals("UTC") && !str.equals("UT")) {
            throw new IllegalArgumentException("prefix should be GMT, UTC or UT, is: ".concat(str));
        }
        if (zoneOffset.getTotalSeconds() != 0) {
            str = str.concat(zoneOffset.f461c);
        }
        return new C1751u(str, zoneOffset.getRules());
    }

    /* renamed from: Q */
    public static ZoneId m619Q(String str, boolean z) {
        Objects.requireNonNull(str, "zoneId");
        if (str.length() <= 1 || str.startsWith("+") || str.startsWith("-")) {
            return ZoneOffset.m624U(str);
        }
        if (str.startsWith("UTC") || str.startsWith("GMT")) {
            return m621S(str, 3, z);
        }
        if (str.startsWith("UT")) {
            return m621S(str, 2, z);
        }
        return C1751u.m834U(str, z);
    }

    /* renamed from: S */
    public static ZoneId m621S(String str, int i, boolean z) {
        String strSubstring = str.substring(0, i);
        if (str.length() == i) {
            return m620R(strSubstring, ZoneOffset.UTC);
        }
        if (str.charAt(i) != '+' && str.charAt(i) != '-') {
            return C1751u.m834U(str, z);
        }
        try {
            ZoneOffset zoneOffsetM624U = ZoneOffset.m624U(str.substring(i));
            if (zoneOffsetM624U == ZoneOffset.UTC) {
                return m620R(strSubstring, zoneOffsetM624U);
            }
            return m620R(strSubstring, zoneOffsetM624U);
        } catch (C1640b e) {
            throw new C1640b("Invalid ID for offset-based ZoneId: ".concat(str), e);
        }
    }

    public ZoneId() {
        if (getClass() != ZoneOffset.class && getClass() != C1751u.class) {
            throw new AssertionError("Invalid subclass");
        }
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        C1707u c1707u = new C1707u();
        c1707u.m745c(new C1706t(textStyle, false));
        return c1707u.m759q(locale, EnumC1684E.SMART, null).m719a(new C1726t(0, this));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZoneId) {
            return getId().equals(((ZoneId) obj).getId());
        }
        return false;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public String toString() {
        return getId();
    }

    private Object writeReplace() {
        return new C1722p((byte) 7, this);
    }
}
