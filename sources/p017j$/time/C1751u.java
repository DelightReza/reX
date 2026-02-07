package p017j$.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import p017j$.time.zone.C1758f;
import p017j$.time.zone.C1760h;
import p017j$.time.zone.ZoneRules;
import p017j$.util.Objects;

/* renamed from: j$.time.u */
/* loaded from: classes2.dex */
public final class C1751u extends ZoneId {

    /* renamed from: d */
    public static final /* synthetic */ int f718d = 0;
    private static final long serialVersionUID = 8386373296231747096L;

    /* renamed from: b */
    public final String f719b;

    /* renamed from: c */
    public final transient ZoneRules f720c;

    /* renamed from: U */
    public static C1751u m834U(String str, boolean z) {
        ZoneRules zoneRulesM850a;
        Objects.requireNonNull(str, "zoneId");
        int length = str.length();
        if (length >= 2) {
            for (int i = 0; i < length; i++) {
                char cCharAt = str.charAt(i);
                if ((cCharAt < 'a' || cCharAt > 'z') && ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt != '/' || i == 0) && ((cCharAt < '0' || cCharAt > '9' || i == 0) && ((cCharAt != '~' || i == 0) && ((cCharAt != '.' || i == 0) && ((cCharAt != '_' || i == 0) && ((cCharAt != '+' || i == 0) && (cCharAt != '-' || i == 0))))))))) {
                    throw new C1640b("Invalid ID for region-based ZoneId, invalid format: ".concat(str));
                }
            }
            try {
                zoneRulesM850a = C1760h.m850a(str);
            } catch (C1758f e) {
                if (z) {
                    throw e;
                }
                zoneRulesM850a = null;
            }
            return new C1751u(str, zoneRulesM850a);
        }
        throw new C1640b("Invalid ID for region-based ZoneId, invalid format: ".concat(str));
    }

    public C1751u(String str, ZoneRules zoneRules) {
        this.f719b = str;
        this.f720c = zoneRules;
    }

    @Override // p017j$.time.ZoneId
    public final String getId() {
        return this.f719b;
    }

    @Override // p017j$.time.ZoneId
    public final ZoneRules getRules() {
        ZoneRules zoneRules = this.f720c;
        return zoneRules != null ? zoneRules : C1760h.m850a(this.f719b);
    }

    private Object writeReplace() {
        return new C1722p((byte) 7, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override // p017j$.time.ZoneId
    /* renamed from: T */
    public final void mo623T(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(7);
        dataOutput.writeUTF(this.f719b);
    }
}
