package p017j$.time;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.temporal.EnumC1727a;
import p017j$.util.Objects;

/* renamed from: j$.time.p */
/* loaded from: classes2.dex */
public final class C1722p implements Externalizable {
    private static final long serialVersionUID = -7683839454370182990L;

    /* renamed from: a */
    public byte f659a;

    /* renamed from: b */
    public Object f660b;

    public C1722p() {
    }

    public C1722p(byte b, Object obj) {
        this.f659a = b;
        this.f660b = obj;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) throws IOException {
        byte b = this.f659a;
        Object obj = this.f660b;
        objectOutput.writeByte(b);
        switch (b) {
            case 1:
                Duration duration = (Duration) obj;
                objectOutput.writeLong(duration.f431a);
                objectOutput.writeInt(duration.f432b);
                return;
            case 2:
                Instant instant = (Instant) obj;
                objectOutput.writeLong(instant.f434a);
                objectOutput.writeInt(instant.f435b);
                return;
            case 3:
                LocalDate localDate = (LocalDate) obj;
                objectOutput.writeInt(localDate.f438a);
                objectOutput.writeByte(localDate.f439b);
                objectOutput.writeByte(localDate.f440c);
                return;
            case 4:
                ((C1715i) obj).m785g0(objectOutput);
                return;
            case 5:
                LocalDateTime localDateTime = (LocalDateTime) obj;
                LocalDate localDate2 = localDateTime.f443a;
                objectOutput.writeInt(localDate2.f438a);
                objectOutput.writeByte(localDate2.f439b);
                objectOutput.writeByte(localDate2.f440c);
                localDateTime.f444b.m785g0(objectOutput);
                return;
            case 6:
                ZonedDateTime zonedDateTime = (ZonedDateTime) obj;
                LocalDateTime localDateTime2 = zonedDateTime.f462a;
                LocalDate localDate3 = localDateTime2.f443a;
                objectOutput.writeInt(localDate3.f438a);
                objectOutput.writeByte(localDate3.f439b);
                objectOutput.writeByte(localDate3.f440c);
                localDateTime2.f444b.m785g0(objectOutput);
                zonedDateTime.f463b.m629Z(objectOutput);
                zonedDateTime.f464c.mo623T(objectOutput);
                return;
            case 7:
                objectOutput.writeUTF(((C1751u) obj).f719b);
                return;
            case 8:
                ((ZoneOffset) obj).m629Z(objectOutput);
                return;
            case 9:
                C1721o c1721o = (C1721o) obj;
                c1721o.f657a.m785g0(objectOutput);
                c1721o.f658b.m629Z(objectOutput);
                return;
            case 10:
                OffsetDateTime offsetDateTime = (OffsetDateTime) obj;
                LocalDateTime localDateTime3 = offsetDateTime.f446a;
                LocalDate localDate4 = localDateTime3.f443a;
                objectOutput.writeInt(localDate4.f438a);
                objectOutput.writeByte(localDate4.f439b);
                objectOutput.writeByte(localDate4.f440c);
                localDateTime3.f444b.m785g0(objectOutput);
                offsetDateTime.f447b.m629Z(objectOutput);
                return;
            case 11:
                objectOutput.writeInt(((C1724r) obj).f664a);
                return;
            case 12:
                YearMonth yearMonth = (YearMonth) obj;
                objectOutput.writeInt(yearMonth.f453a);
                objectOutput.writeByte(yearMonth.f454b);
                return;
            case 13:
                C1719m c1719m = (C1719m) obj;
                objectOutput.writeByte(c1719m.f653a);
                objectOutput.writeByte(c1719m.f654b);
                return;
            case 14:
                Period period = (Period) obj;
                objectOutput.writeInt(period.f449a);
                objectOutput.writeInt(period.f450b);
                objectOutput.writeInt(period.f451c);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) {
        byte b = objectInput.readByte();
        this.f659a = b;
        this.f660b = m792a(b, objectInput);
    }

    /* renamed from: a */
    public static Object m792a(byte b, ObjectInput objectInput) throws IOException {
        switch (b) {
            case 1:
                Duration duration = Duration.f430c;
                long j = objectInput.readLong();
                long j2 = objectInput.readInt();
                return Duration.m548j(AbstractC1636a.m494M(j, AbstractC1636a.m499R(j2, 1000000000L)), (int) AbstractC1636a.m498Q(j2, 1000000000L));
            case 2:
                Instant instant = Instant.f433c;
                return Instant.m553S(objectInput.readLong(), objectInput.readInt());
            case 3:
                LocalDate localDate = LocalDate.f436d;
                return LocalDate.m566of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte());
            case 4:
                return C1715i.m773b0(objectInput);
            case 5:
                LocalDateTime localDateTime = LocalDateTime.f441c;
                LocalDate localDate2 = LocalDate.f436d;
                return LocalDateTime.m593T(LocalDate.m566of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), C1715i.m773b0(objectInput));
            case 6:
                LocalDateTime localDateTime2 = LocalDateTime.f441c;
                LocalDate localDate3 = LocalDate.f436d;
                LocalDateTime localDateTimeM593T = LocalDateTime.m593T(LocalDate.m566of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), C1715i.m773b0(objectInput));
                ZoneOffset zoneOffsetM628Y = ZoneOffset.m628Y(objectInput);
                ZoneId zoneId = (ZoneId) m792a(objectInput.readByte(), objectInput);
                Objects.requireNonNull(localDateTimeM593T, "localDateTime");
                Objects.requireNonNull(zoneOffsetM628Y, "offset");
                Objects.requireNonNull(zoneId, "zone");
                if (!(zoneId instanceof ZoneOffset) || zoneOffsetM628Y.equals(zoneId)) {
                    return new ZonedDateTime(localDateTimeM593T, zoneId, zoneOffsetM628Y);
                }
                throw new IllegalArgumentException("ZoneId must match ZoneOffset");
            case 7:
                int i = C1751u.f718d;
                return ZoneId.m619Q(objectInput.readUTF(), false);
            case 8:
                return ZoneOffset.m628Y(objectInput);
            case 9:
                int i2 = C1721o.f656c;
                return new C1721o(C1715i.m773b0(objectInput), ZoneOffset.m628Y(objectInput));
            case 10:
                int i3 = OffsetDateTime.f445c;
                LocalDate localDate4 = LocalDate.f436d;
                return new OffsetDateTime(LocalDateTime.m593T(LocalDate.m566of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), C1715i.m773b0(objectInput)), ZoneOffset.m628Y(objectInput));
            case 11:
                int i4 = C1724r.f663b;
                return C1724r.m793Q(objectInput.readInt());
            case 12:
                int i5 = YearMonth.f452c;
                return YearMonth.m612of(objectInput.readInt(), objectInput.readByte());
            case 13:
                int i6 = C1719m.f652c;
                byte b2 = objectInput.readByte();
                byte b3 = objectInput.readByte();
                EnumC1717k enumC1717kM786T = EnumC1717k.m786T(b2);
                Objects.requireNonNull(enumC1717kM786T, "month");
                EnumC1727a.DAY_OF_MONTH.m800D(b3);
                if (b3 <= enumC1717kM786T.m789S()) {
                    return new C1719m(enumC1717kM786T.getValue(), b3);
                }
                throw new C1640b("Illegal value for DayOfMonth field, value " + ((int) b3) + " is not valid for month " + enumC1717kM786T.name());
            case 14:
                Period period = Period.f448d;
                return Period.m611a(objectInput.readInt(), objectInput.readInt(), objectInput.readInt());
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
    }

    private Object readResolve() {
        return this.f660b;
    }
}
