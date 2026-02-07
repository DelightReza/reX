package p017j$.time.chrono;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1715i;
import p017j$.time.LocalDate;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.EnumC1727a;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.chrono.D */
/* loaded from: classes2.dex */
public final class C1645D implements Externalizable {
    private static final long serialVersionUID = -6103370247208168577L;

    /* renamed from: a */
    public byte f470a;

    /* renamed from: b */
    public Object f471b;

    public C1645D() {
    }

    public C1645D(byte b, Object obj) {
        this.f470a = b;
        this.f471b = obj;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) throws IOException {
        byte b = this.f470a;
        Object obj = this.f471b;
        objectOutput.writeByte(b);
        switch (b) {
            case 1:
                objectOutput.writeUTF(((AbstractC1651a) obj).getId());
                return;
            case 2:
                C1656f c1656f = (C1656f) obj;
                objectOutput.writeObject(c1656f.f482a);
                objectOutput.writeObject(c1656f.f483b);
                return;
            case 3:
                C1660j c1660j = (C1660j) obj;
                objectOutput.writeObject(c1660j.f491a);
                objectOutput.writeObject(c1660j.f492b);
                objectOutput.writeObject(c1660j.f493c);
                return;
            case 4:
                C1673w c1673w = (C1673w) obj;
                c1673w.getClass();
                objectOutput.writeInt(AbstractC1745s.m813a(c1673w, EnumC1727a.YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1673w, EnumC1727a.MONTH_OF_YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1673w, EnumC1727a.DAY_OF_MONTH));
                return;
            case 5:
                objectOutput.writeByte(((C1674x) obj).f524a);
                return;
            case 6:
                C1666p c1666p = (C1666p) obj;
                objectOutput.writeObject(c1666p.f506a);
                objectOutput.writeInt(AbstractC1745s.m813a(c1666p, EnumC1727a.YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1666p, EnumC1727a.MONTH_OF_YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1666p, EnumC1727a.DAY_OF_MONTH));
                return;
            case 7:
                C1643B c1643b = (C1643B) obj;
                c1643b.getClass();
                objectOutput.writeInt(AbstractC1745s.m813a(c1643b, EnumC1727a.YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1643b, EnumC1727a.MONTH_OF_YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1643b, EnumC1727a.DAY_OF_MONTH));
                return;
            case 8:
                C1649H c1649h = (C1649H) obj;
                c1649h.getClass();
                objectOutput.writeInt(AbstractC1745s.m813a(c1649h, EnumC1727a.YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1649h, EnumC1727a.MONTH_OF_YEAR));
                objectOutput.writeByte(AbstractC1745s.m813a(c1649h, EnumC1727a.DAY_OF_MONTH));
                return;
            case 9:
                C1657g c1657g = (C1657g) obj;
                objectOutput.writeUTF(c1657g.f485a.getId());
                objectOutput.writeInt(c1657g.f486b);
                objectOutput.writeInt(c1657g.f487c);
                objectOutput.writeInt(c1657g.f488d);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) throws IOException {
        Object objM503V;
        byte b = objectInput.readByte();
        this.f470a = b;
        switch (b) {
            case 1:
                ConcurrentHashMap concurrentHashMap = AbstractC1651a.f478a;
                objM503V = AbstractC1636a.m503V(objectInput.readUTF());
                break;
            case 2:
                objM503V = ((InterfaceC1652b) objectInput.readObject()).mo568F((C1715i) objectInput.readObject());
                break;
            case 3:
                objM503V = ((ChronoLocalDateTime) objectInput.readObject()).mo607z((ZoneOffset) objectInput.readObject()).mo642w((ZoneId) objectInput.readObject());
                break;
            case 4:
                LocalDate localDate = C1673w.f518d;
                int i = objectInput.readInt();
                byte b2 = objectInput.readByte();
                byte b3 = objectInput.readByte();
                C1671u.f516c.getClass();
                objM503V = new C1673w(LocalDate.m566of(i, b2, b3));
                break;
            case 5:
                C1674x c1674x = C1674x.f522d;
                objM503V = C1674x.m702m(objectInput.readByte());
                break;
            case 6:
                C1664n c1664n = (C1664n) objectInput.readObject();
                int i2 = objectInput.readInt();
                byte b4 = objectInput.readByte();
                byte b5 = objectInput.readByte();
                c1664n.getClass();
                objM503V = new C1666p(c1664n, i2, b4, b5);
                break;
            case 7:
                int i3 = objectInput.readInt();
                byte b6 = objectInput.readByte();
                byte b7 = objectInput.readByte();
                C1676z.f528c.getClass();
                objM503V = new C1643B(LocalDate.m566of(i3 + 1911, b6, b7));
                break;
            case 8:
                int i4 = objectInput.readInt();
                byte b8 = objectInput.readByte();
                byte b9 = objectInput.readByte();
                C1647F.f473c.getClass();
                objM503V = new C1649H(LocalDate.m566of(i4 - 543, b8, b9));
                break;
            case 9:
                int i5 = C1657g.f484e;
                objM503V = new C1657g(AbstractC1636a.m503V(objectInput.readUTF()), objectInput.readInt(), objectInput.readInt(), objectInput.readInt());
                break;
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
        this.f471b = objM503V;
    }

    private Object readResolve() {
        return this.f471b;
    }
}
