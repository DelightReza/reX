package p017j$.sun.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import p017j$.util.concurrent.C1802k;
import p017j$.util.concurrent.C1807p;
import sun.misc.Unsafe;

/* renamed from: j$.sun.misc.a */
/* loaded from: classes2.dex */
public final class C1638a {

    /* renamed from: b */
    public static final C1638a f426b;

    /* renamed from: a */
    public final Unsafe f427a;

    static {
        Field fieldM531g = m531g();
        fieldM531g.setAccessible(true);
        try {
            f426b = new C1638a((Unsafe) fieldM531g.get(null));
        } catch (IllegalAccessException e) {
            throw new AssertionError("Couldn't get the Unsafe", e);
        }
    }

    public C1638a(Unsafe unsafe) {
        this.f427a = unsafe;
    }

    /* renamed from: g */
    public static Field m531g() {
        try {
            return Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) && Unsafe.class.isAssignableFrom(field.getType())) {
                    return field;
                }
            }
            throw new AssertionError("Couldn't find the Unsafe", e);
        }
    }

    /* renamed from: e */
    public final int m536e(C1807p c1807p, long j) {
        while (true) {
            int intVolatile = this.f427a.getIntVolatile(c1807p, j);
            C1807p c1807p2 = c1807p;
            long j2 = j;
            if (this.f427a.compareAndSwapInt(c1807p2, j2, intVolatile, intVolatile - 4)) {
                return intVolatile;
            }
            c1807p = c1807p2;
            j = j2;
        }
    }

    /* renamed from: i */
    public final long m539i(Field field) {
        return this.f427a.objectFieldOffset(field);
    }

    /* renamed from: h */
    public final long m538h(Class cls, String str) {
        try {
            return m539i(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Cannot find field:", e);
        }
    }

    /* renamed from: a */
    public final int m532a(Class cls) {
        return this.f427a.arrayBaseOffset(cls);
    }

    /* renamed from: b */
    public final int m533b(Class cls) {
        return this.f427a.arrayIndexScale(cls);
    }

    /* renamed from: f */
    public final Object m537f(Object obj, long j) {
        return this.f427a.getObjectVolatile(obj, j);
    }

    /* renamed from: j */
    public final void m540j(Object obj, long j, C1802k c1802k) {
        this.f427a.putObjectVolatile(obj, j, c1802k);
    }

    /* renamed from: c */
    public final boolean m534c(Object obj, long j, int i, int i2) {
        return this.f427a.compareAndSwapInt(obj, j, i, i2);
    }

    /* renamed from: d */
    public final boolean m535d(Object obj, long j, long j2, long j3) {
        return this.f427a.compareAndSwapLong(obj, j, j2, j3);
    }
}
