package p017j$.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.util.TimeZone;
import org.mvel2.asm.Opcodes;
import p017j$.time.ZoneOffset;

/* renamed from: j$.time.zone.a */
/* loaded from: classes2.dex */
public final class C1753a implements Externalizable {
    private static final long serialVersionUID = -8885321777449118786L;

    /* renamed from: a */
    public byte f734a;

    /* renamed from: b */
    public Object f735b;

    public C1753a() {
    }

    public C1753a(byte b, Object obj) {
        this.f734a = b;
        this.f735b = obj;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) throws IOException {
        byte b = this.f734a;
        Object obj = this.f735b;
        objectOutput.writeByte(b);
        if (b != 1) {
            if (b == 2) {
                C1754b c1754b = (C1754b) obj;
                m845c(c1754b.f737a, objectOutput);
                m846d(c1754b.f739c, objectOutput);
                m846d(c1754b.f740d, objectOutput);
                return;
            }
            if (b == 3) {
                ((C1757e) obj).m849b(objectOutput);
                return;
            } else {
                if (b != 100) {
                    throw new InvalidClassException("Unknown serialized type");
                }
                objectOutput.writeUTF(((ZoneRules) obj).f732g.getID());
                return;
            }
        }
        ZoneRules zoneRules = (ZoneRules) obj;
        objectOutput.writeInt(zoneRules.f726a.length);
        for (long j : zoneRules.f726a) {
            m845c(j, objectOutput);
        }
        for (ZoneOffset zoneOffset : zoneRules.f727b) {
            m846d(zoneOffset, objectOutput);
        }
        objectOutput.writeInt(zoneRules.f728c.length);
        for (long j2 : zoneRules.f728c) {
            m845c(j2, objectOutput);
        }
        for (ZoneOffset zoneOffset2 : zoneRules.f730e) {
            m846d(zoneOffset2, objectOutput);
        }
        objectOutput.writeByte(zoneRules.f731f.length);
        for (C1757e c1757e : zoneRules.f731f) {
            c1757e.m849b(objectOutput);
        }
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) throws IOException {
        Object zoneRules;
        byte b = objectInput.readByte();
        this.f734a = b;
        if (b == 1) {
            long[] jArr = ZoneRules.f722i;
            int i = objectInput.readInt();
            long[] jArr2 = i == 0 ? jArr : new long[i];
            for (int i2 = 0; i2 < i; i2++) {
                jArr2[i2] = m843a(objectInput);
            }
            int i3 = i + 1;
            ZoneOffset[] zoneOffsetArr = new ZoneOffset[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                zoneOffsetArr[i4] = m844b(objectInput);
            }
            int i5 = objectInput.readInt();
            if (i5 != 0) {
                jArr = new long[i5];
            }
            long[] jArr3 = jArr;
            for (int i6 = 0; i6 < i5; i6++) {
                jArr3[i6] = m843a(objectInput);
            }
            int i7 = i5 + 1;
            ZoneOffset[] zoneOffsetArr2 = new ZoneOffset[i7];
            for (int i8 = 0; i8 < i7; i8++) {
                zoneOffsetArr2[i8] = m844b(objectInput);
            }
            int i9 = objectInput.readByte();
            C1757e[] c1757eArr = i9 == 0 ? ZoneRules.f723j : new C1757e[i9];
            for (int i10 = 0; i10 < i9; i10++) {
                c1757eArr[i10] = C1757e.m848a(objectInput);
            }
            zoneRules = new ZoneRules(jArr2, zoneOffsetArr, jArr3, zoneOffsetArr2, c1757eArr);
        } else if (b == 2) {
            int i11 = C1754b.f736e;
            long jM843a = m843a(objectInput);
            ZoneOffset zoneOffsetM844b = m844b(objectInput);
            ZoneOffset zoneOffsetM844b2 = m844b(objectInput);
            if (zoneOffsetM844b.equals(zoneOffsetM844b2)) {
                throw new IllegalArgumentException("Offsets must not be equal");
            }
            zoneRules = new C1754b(jM843a, zoneOffsetM844b, zoneOffsetM844b2);
        } else if (b == 3) {
            zoneRules = C1757e.m848a(objectInput);
        } else {
            if (b != 100) {
                throw new StreamCorruptedException("Unknown serialized type");
            }
            zoneRules = new ZoneRules(TimeZone.getTimeZone(objectInput.readUTF()));
        }
        this.f735b = zoneRules;
    }

    private Object readResolve() {
        return this.f735b;
    }

    /* renamed from: d */
    public static void m846d(ZoneOffset zoneOffset, DataOutput dataOutput) throws IOException {
        int totalSeconds = zoneOffset.getTotalSeconds();
        int i = totalSeconds % 900 == 0 ? totalSeconds / 900 : Opcodes.LAND;
        dataOutput.writeByte(i);
        if (i == 127) {
            dataOutput.writeInt(totalSeconds);
        }
    }

    /* renamed from: b */
    public static ZoneOffset m844b(DataInput dataInput) throws IOException {
        byte b = dataInput.readByte();
        return b == 127 ? ZoneOffset.m626W(dataInput.readInt()) : ZoneOffset.m626W(b * 900);
    }

    /* renamed from: c */
    public static void m845c(long j, DataOutput dataOutput) throws IOException {
        if (j >= -4575744000L && j < 10413792000L && j % 900 == 0) {
            int i = (int) ((j + 4575744000L) / 900);
            dataOutput.writeByte((i >>> 16) & 255);
            dataOutput.writeByte((i >>> 8) & 255);
            dataOutput.writeByte(i & 255);
            return;
        }
        dataOutput.writeByte(255);
        dataOutput.writeLong(j);
    }

    /* renamed from: a */
    public static long m843a(DataInput dataInput) {
        if ((dataInput.readByte() & 255) == 255) {
            return dataInput.readLong();
        }
        return ((((r0 << 16) + ((dataInput.readByte() & 255) << 8)) + (dataInput.readByte() & 255)) * 900) - 4575744000L;
    }
}
