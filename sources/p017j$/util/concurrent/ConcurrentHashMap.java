package p017j$.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import org.mvel2.asm.signature.SignatureVisitor;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.sun.misc.C1638a;
import p017j$.util.Collection;
import p017j$.util.Spliterator;
import p017j$.util.stream.Stream;

/* loaded from: classes2.dex */
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable, InterfaceC1811t {

    /* renamed from: g */
    public static final int f802g = Runtime.getRuntime().availableProcessors();

    /* renamed from: h */
    public static final C1638a f803h;

    /* renamed from: i */
    public static final long f804i;

    /* renamed from: j */
    public static final long f805j;

    /* renamed from: k */
    public static final long f806k;

    /* renamed from: l */
    public static final long f807l;

    /* renamed from: m */
    public static final long f808m;

    /* renamed from: n */
    public static final int f809n;

    /* renamed from: o */
    public static final int f810o;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 7249069246763182397L;

    /* renamed from: a */
    public volatile transient C1802k[] f811a;

    /* renamed from: b */
    public volatile transient C1802k[] f812b;
    private volatile transient long baseCount;

    /* renamed from: c */
    public volatile transient C1794c[] f813c;
    private volatile transient int cellsBusy;

    /* renamed from: d */
    public transient KeySetView f814d;

    /* renamed from: e */
    public transient C1809r f815e;

    /* renamed from: f */
    public transient C1796e f816f;
    private volatile transient int sizeCtl;
    private volatile transient int transferIndex;

    /* renamed from: i */
    public static final int m871i(int i) {
        return (i ^ (i >>> 16)) & ConnectionsManager.DEFAULT_DATACENTER_ID;
    }

    static {
        Class cls = Integer.TYPE;
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("segments", C1804m[].class), new ObjectStreamField("segmentMask", cls), new ObjectStreamField("segmentShift", cls)};
        C1638a c1638a = C1638a.f426b;
        f803h = c1638a;
        f804i = c1638a.m538h(ConcurrentHashMap.class, "sizeCtl");
        f805j = c1638a.m538h(ConcurrentHashMap.class, "transferIndex");
        f806k = c1638a.m538h(ConcurrentHashMap.class, "baseCount");
        f807l = c1638a.m538h(ConcurrentHashMap.class, "cellsBusy");
        f808m = c1638a.m538h(C1794c.class, "value");
        f809n = c1638a.m532a(C1802k[].class);
        int iM533b = c1638a.m533b(C1802k[].class);
        if (((iM533b - 1) & iM533b) != 0) {
            throw new ExceptionInInitializerError("array index scale not a power of two");
        }
        f810o = 31 - Integer.numberOfLeadingZeros(iM533b);
    }

    /* renamed from: l */
    public static final int m873l(int i) {
        int iNumberOfLeadingZeros = (-1) >>> Integer.numberOfLeadingZeros(i - 1);
        if (iNumberOfLeadingZeros < 0) {
            return 1;
        }
        return iNumberOfLeadingZeros >= 1073741824 ? TLObject.FLAG_30 : iNumberOfLeadingZeros + 1;
    }

    /* renamed from: c */
    public static Class m869c(Object obj) {
        Type[] actualTypeArguments;
        if (!(obj instanceof Comparable)) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if (cls != String.class) {
            Type[] genericInterfaces = cls.getGenericInterfaces();
            if (genericInterfaces == null) {
                return null;
            }
            for (Type type : genericInterfaces) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (parameterizedType.getRawType() != Comparable.class || (actualTypeArguments = parameterizedType.getActualTypeArguments()) == null || actualTypeArguments.length != 1 || actualTypeArguments[0] != cls) {
                    }
                }
            }
            return null;
        }
        return cls;
    }

    /* renamed from: k */
    public static final C1802k m872k(C1802k[] c1802kArr, int i) {
        return (C1802k) f803h.m537f(c1802kArr, (i << f810o) + f809n);
    }

    /* renamed from: b */
    public static final boolean m868b(C1802k[] c1802kArr, int i, C1802k c1802k) {
        return AbstractC1636a.m497P(f803h.f427a, c1802kArr, (i << f810o) + f809n, c1802k);
    }

    /* renamed from: h */
    public static final void m870h(C1802k[] c1802kArr, int i, C1802k c1802k) {
        f803h.m540j(c1802kArr, (i << f810o) + f809n, c1802k);
    }

    public ConcurrentHashMap() {
    }

    public ConcurrentHashMap(int i) {
        this(i, 0.75f, 1);
    }

    public ConcurrentHashMap(Map<? extends K, ? extends V> map) {
        this.sizeCtl = 16;
        putAll(map);
    }

    public ConcurrentHashMap(int i, float f, int i2) {
        if (f <= 0.0f || i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        long j = (long) (((i < i2 ? i2 : i) / f) + 1.0d);
        this.sizeCtl = j >= 1073741824 ? TLObject.FLAG_30 : m873l((int) j);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        long jM880j = m880j();
        if (jM880j < 0) {
            return 0;
        }
        return jM880j > 2147483647L ? ConnectionsManager.DEFAULT_DATACENTER_ID : (int) jM880j;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean isEmpty() {
        return m880j() <= 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        int length;
        C1802k c1802kM872k;
        Object obj2;
        int iM871i = m871i(obj.hashCode());
        C1802k[] c1802kArr = this.f811a;
        if (c1802kArr == null || (length = c1802kArr.length) <= 0 || (c1802kM872k = m872k(c1802kArr, (length - 1) & iM871i)) == null) {
            return null;
        }
        int i = c1802kM872k.f837a;
        if (i == iM871i) {
            Object obj3 = c1802kM872k.f838b;
            if (obj3 == obj || (obj3 != null && obj.equals(obj3))) {
                return (V) c1802kM872k.f839c;
            }
        } else if (i < 0) {
            C1802k c1802kMo891a = c1802kM872k.mo891a(iM871i, obj);
            if (c1802kMo891a != null) {
                return (V) c1802kMo891a.f839c;
            }
            return null;
        }
        while (true) {
            c1802kM872k = c1802kM872k.f840d;
            if (c1802kM872k == null) {
                return null;
            }
            if (c1802kM872k.f837a == iM871i && ((obj2 = c1802kM872k.f838b) == obj || (obj2 != null && obj.equals(obj2)))) {
                break;
            }
        }
        return (V) c1802kM872k.f839c;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object obj) {
        obj.getClass();
        C1802k[] c1802kArr = this.f811a;
        if (c1802kArr != null) {
            C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
            while (true) {
                C1802k c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    break;
                }
                Object obj2 = c1802kM892a.f839c;
                if (obj2 == obj) {
                    return true;
                }
                if (obj2 != null && obj.equals(obj2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        return (V) m878f(k, v, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x006a, code lost:
    
        r7 = r6.f839c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x006c, code lost:
    
        if (r11 != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x006e, code lost:
    
        r6.f839c = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00a5, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object m878f(java.lang.Object r9, java.lang.Object r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 195
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.ConcurrentHashMap.m878f(java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        m883o(map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            m878f(entry.getKey(), entry.getValue(), false);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        return (V) m879g(obj, null, null);
    }

    /* renamed from: g */
    public final Object m879g(Object obj, Object obj2, Object obj3) {
        int length;
        int i;
        C1802k c1802kM872k;
        boolean z;
        Object obj4;
        C1808q c1808qM901b;
        Object obj5;
        int iM871i = m871i(obj.hashCode());
        C1802k[] c1802kArrM876d = this.f811a;
        while (true) {
            if (c1802kArrM876d == null || (length = c1802kArrM876d.length) == 0 || (c1802kM872k = m872k(c1802kArrM876d, (i = (length - 1) & iM871i))) == null) {
                break;
            }
            int i2 = c1802kM872k.f837a;
            if (i2 == -1) {
                c1802kArrM876d = m876d(c1802kArrM876d, c1802kM872k);
            } else {
                synchronized (c1802kM872k) {
                    try {
                        if (m872k(c1802kArrM876d, i) == c1802kM872k) {
                            z = true;
                            if (i2 >= 0) {
                                C1802k c1802k = null;
                                C1802k c1802k2 = c1802kM872k;
                                while (true) {
                                    if (c1802k2.f837a != iM871i || ((obj5 = c1802k2.f838b) != obj && (obj5 == null || !obj.equals(obj5)))) {
                                        C1802k c1802k3 = c1802k2.f840d;
                                        if (c1802k3 == null) {
                                            break;
                                        }
                                        c1802k = c1802k2;
                                        c1802k2 = c1802k3;
                                    }
                                }
                                obj4 = c1802k2.f839c;
                                if (obj3 != null && obj3 != obj4 && (obj4 == null || !obj3.equals(obj4))) {
                                    obj4 = null;
                                } else if (obj2 != null) {
                                    c1802k2.f839c = obj2;
                                } else if (c1802k != null) {
                                    c1802k.f840d = c1802k2.f840d;
                                } else {
                                    m870h(c1802kArrM876d, i, c1802k2.f840d);
                                }
                            } else if (c1802kM872k instanceof C1807p) {
                                C1807p c1807p = (C1807p) c1802kM872k;
                                C1808q c1808q = c1807p.f855e;
                                if (c1808q != null && (c1808qM901b = c1808q.m901b(iM871i, obj, null)) != null) {
                                    obj4 = c1808qM901b.f839c;
                                    if (obj3 == null || obj3 == obj4 || (obj4 != null && obj3.equals(obj4))) {
                                        if (obj2 != null) {
                                            c1808qM901b.f839c = obj2;
                                        } else if (c1807p.m900f(c1808qM901b)) {
                                            m870h(c1802kArrM876d, i, m874p(c1807p.f856f));
                                        }
                                    }
                                }
                                obj4 = null;
                            } else if (c1802kM872k instanceof C1803l) {
                                throw new IllegalStateException("Recursive update");
                            }
                        }
                        z = false;
                        obj4 = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z) {
                    if (obj4 != null) {
                        if (obj2 == null) {
                            m875a(-1L, -1);
                        }
                        return obj4;
                    }
                }
            }
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        C1802k c1802kM872k;
        C1802k c1802k;
        C1802k[] c1802kArrM876d = this.f811a;
        long j = 0;
        loop0: while (true) {
            int i = 0;
            while (c1802kArrM876d != null && i < c1802kArrM876d.length) {
                c1802kM872k = m872k(c1802kArrM876d, i);
                if (c1802kM872k == null) {
                    i++;
                } else {
                    int i2 = c1802kM872k.f837a;
                    if (i2 == -1) {
                        break;
                    }
                    synchronized (c1802kM872k) {
                        try {
                            if (m872k(c1802kArrM876d, i) == c1802kM872k) {
                                if (i2 >= 0) {
                                    c1802k = c1802kM872k;
                                } else {
                                    c1802k = c1802kM872k instanceof C1807p ? ((C1807p) c1802kM872k).f856f : null;
                                }
                                while (c1802k != null) {
                                    j--;
                                    c1802k = c1802k.f840d;
                                }
                                m870h(c1802kArrM876d, i, null);
                                i++;
                            }
                        } finally {
                        }
                    }
                }
            }
            c1802kArrM876d = m876d(c1802kArrM876d, c1802kM872k);
        }
        if (j != 0) {
            m875a(j, -1);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        KeySetView keySetView = this.f814d;
        if (keySetView != null) {
            return keySetView;
        }
        KeySetView keySetView2 = new KeySetView(this, null);
        this.f814d = keySetView2;
        return keySetView2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        C1809r c1809r = this.f815e;
        if (c1809r != null) {
            return c1809r;
        }
        C1809r c1809r2 = new C1809r(this);
        this.f815e = c1809r2;
        return c1809r2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        C1796e c1796e = this.f816f;
        if (c1796e != null) {
            return c1796e;
        }
        C1796e c1796e2 = new C1796e(this);
        this.f816f = c1796e2;
        return c1796e2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        C1802k[] c1802kArr = this.f811a;
        int iHashCode = 0;
        if (c1802kArr != null) {
            C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
            while (true) {
                C1802k c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    break;
                }
                iHashCode += c1802kM892a.f839c.hashCode() ^ c1802kM892a.f838b.hashCode();
            }
        }
        return iHashCode;
    }

    @Override // java.util.AbstractMap
    public final String toString() {
        C1802k[] c1802kArr = this.f811a;
        int length = c1802kArr == null ? 0 : c1802kArr.length;
        C1806o c1806o = new C1806o(c1802kArr, length, 0, length);
        StringBuilder sb = new StringBuilder("{");
        C1802k c1802kM892a = c1806o.m892a();
        if (c1802kM892a != null) {
            while (true) {
                Object obj = c1802kM892a.f838b;
                Object obj2 = c1802kM892a.f839c;
                if (obj == this) {
                    obj = "(this Map)";
                }
                sb.append(obj);
                sb.append(SignatureVisitor.INSTANCEOF);
                if (obj2 == this) {
                    obj2 = "(this Map)";
                }
                sb.append(obj2);
                c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    break;
                }
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        V value;
        V v;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        C1802k[] c1802kArr = this.f811a;
        int length = c1802kArr == null ? 0 : c1802kArr.length;
        C1806o c1806o = new C1806o(c1802kArr, length, 0, length);
        while (true) {
            C1802k c1802kM892a = c1806o.m892a();
            if (c1802kM892a != null) {
                Object obj2 = c1802kM892a.f839c;
                Object obj3 = map.get(c1802kM892a.f838b);
                if (obj3 == null || (obj3 != obj2 && !obj3.equals(obj2))) {
                    break;
                }
            } else {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    K key = entry.getKey();
                    if (key == null || (value = entry.getValue()) == null || (v = get(key)) == null || (value != v && !value.equals(v))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int i = 1;
        int i2 = 0;
        while (i < 16) {
            i2++;
            i <<= 1;
        }
        int i3 = 32 - i2;
        int i4 = i - 1;
        C1804m[] c1804mArr = new C1804m[16];
        for (int i5 = 0; i5 < 16; i5++) {
            c1804mArr[i5] = new C1804m();
        }
        ObjectOutputStream.PutField putFieldPutFields = objectOutputStream.putFields();
        putFieldPutFields.put("segments", c1804mArr);
        putFieldPutFields.put("segmentShift", i3);
        putFieldPutFields.put("segmentMask", i4);
        objectOutputStream.writeFields();
        C1802k[] c1802kArr = this.f811a;
        if (c1802kArr != null) {
            C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
            while (true) {
                C1802k c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    break;
                }
                objectOutputStream.writeObject(c1802kM892a.f838b);
                objectOutputStream.writeObject(c1802kM892a.f839c);
            }
        }
        objectOutputStream.writeObject(null);
        objectOutputStream.writeObject(null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        long j;
        long j2;
        Object obj;
        this.sizeCtl = -1;
        objectInputStream.defaultReadObject();
        long j3 = 0;
        long j4 = 0;
        C1802k c1802k = null;
        while (true) {
            Object object = objectInputStream.readObject();
            Object object2 = objectInputStream.readObject();
            j = 1;
            if (object == null || object2 == null) {
                break;
            }
            j4++;
            c1802k = new C1802k(m871i(object.hashCode()), object, object2, c1802k);
        }
        if (j4 == 0) {
            this.sizeCtl = 0;
            return;
        }
        long j5 = (long) ((j4 / 0.75f) + 1.0d);
        int iM873l = j5 >= 1073741824 ? TLObject.FLAG_30 : m873l((int) j5);
        C1802k[] c1802kArr = new C1802k[iM873l];
        int i = iM873l - 1;
        while (c1802k != null) {
            C1802k c1802k2 = c1802k.f840d;
            int i2 = c1802k.f837a;
            int i3 = i2 & i;
            C1802k c1802kM872k = m872k(c1802kArr, i3);
            boolean z = true;
            if (c1802kM872k == null) {
                j2 = j;
            } else {
                Object obj2 = c1802k.f838b;
                if (c1802kM872k.f837a < 0) {
                    if (((C1807p) c1802kM872k).m899e(i2, obj2, c1802k.f839c) == null) {
                        j3 += j;
                    }
                    j2 = j;
                } else {
                    j2 = j;
                    int i4 = 0;
                    for (C1802k c1802k3 = c1802kM872k; c1802k3 != null; c1802k3 = c1802k3.f840d) {
                        if (c1802k3.f837a == i2 && ((obj = c1802k3.f838b) == obj2 || (obj != null && obj2.equals(obj)))) {
                            z = false;
                            break;
                        }
                        i4++;
                    }
                    if (z && i4 >= 8) {
                        j3 += j2;
                        c1802k.f840d = c1802kM872k;
                        C1802k c1802k4 = c1802k;
                        C1808q c1808q = null;
                        C1808q c1808q2 = null;
                        while (c1802k4 != null) {
                            C1808q c1808q3 = new C1808q(c1802k4.f837a, c1802k4.f838b, c1802k4.f839c, null, null);
                            c1808q3.f861h = c1808q2;
                            if (c1808q2 == null) {
                                c1808q = c1808q3;
                            } else {
                                c1808q2.f840d = c1808q3;
                            }
                            c1802k4 = c1802k4.f840d;
                            c1808q2 = c1808q3;
                        }
                        m870h(c1802kArr, i3, new C1807p(c1808q));
                    }
                }
                z = false;
            }
            if (z) {
                j3 += j2;
                c1802k.f840d = c1802kM872k;
                m870h(c1802kArr, i3, c1802k);
            }
            c1802k = c1802k2;
            j = j2;
        }
        this.f811a = c1802kArr;
        this.sizeCtl = iM873l - (iM873l >>> 2);
        this.baseCount = j3;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public V putIfAbsent(K k, V v) {
        return (V) m878f(k, v, true);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public boolean remove(Object obj, Object obj2) {
        obj.getClass();
        return (obj2 == null || m879g(obj, null, obj2) == null) ? false : true;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        if (obj == null || obj2 == null || obj3 == null) {
            throw null;
        }
        return m879g(obj, obj3, obj2) != null;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public final Object replace(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            throw null;
        }
        return m879g(obj, obj2, null);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        V v = get(obj);
        return v == null ? obj2 : v;
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public final void forEach(BiConsumer biConsumer) {
        biConsumer.getClass();
        C1802k[] c1802kArr = this.f811a;
        if (c1802kArr == null) {
            return;
        }
        C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
        while (true) {
            C1802k c1802kM892a = c1806o.m892a();
            if (c1802kM892a == null) {
                return;
            } else {
                biConsumer.accept(c1802kM892a.f838b, c1802kM892a.f839c);
            }
        }
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public final void replaceAll(BiFunction biFunction) {
        biFunction.getClass();
        C1802k[] c1802kArr = this.f811a;
        if (c1802kArr == null) {
            return;
        }
        C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
        while (true) {
            C1802k c1802kM892a = c1806o.m892a();
            if (c1802kM892a == null) {
                return;
            }
            Object obj = c1802kM892a.f839c;
            Object obj2 = c1802kM892a.f838b;
            do {
                Object objApply = biFunction.apply(obj2, obj);
                objApply.getClass();
                if (m879g(obj2, objApply, obj) == null) {
                    obj = get(obj2);
                }
            } while (obj != null);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x008c, code lost:
    
        r5 = r5.f839c;
     */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object computeIfAbsent(java.lang.Object r12, java.util.function.Function r13) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.ConcurrentHashMap.computeIfAbsent(java.lang.Object, java.util.function.Function):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00aa, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object computeIfPresent(java.lang.Object r14, java.util.function.BiFunction r15) {
        /*
            r13 = this;
            r0 = 0
            if (r14 == 0) goto Lbd
            if (r15 == 0) goto Lbd
            int r1 = r14.hashCode()
            int r1 = m871i(r1)
            j$.util.concurrent.k[] r2 = r13.f811a
            r3 = 0
            r5 = r0
            r4 = 0
        L12:
            if (r2 == 0) goto Lb7
            int r6 = r2.length
            if (r6 != 0) goto L19
            goto Lb7
        L19:
            int r6 = r6 + (-1)
            r6 = r6 & r1
            j$.util.concurrent.k r7 = m872k(r2, r6)
            if (r7 != 0) goto L24
            goto Lae
        L24:
            int r8 = r7.f837a
            r9 = -1
            if (r8 != r9) goto L2e
            j$.util.concurrent.k[] r2 = r13.m876d(r2, r7)
            goto L12
        L2e:
            monitor-enter(r7)
            j$.util.concurrent.k r10 = m872k(r2, r6)     // Catch: java.lang.Throwable -> L4b
            if (r10 != r7) goto Lab
            if (r8 < 0) goto L70
            r4 = 1
            r10 = r0
            r8 = r7
        L3a:
            int r11 = r8.f837a     // Catch: java.lang.Throwable -> L4b
            if (r11 != r1) goto L65
            java.lang.Object r11 = r8.f838b     // Catch: java.lang.Throwable -> L4b
            if (r11 == r14) goto L4e
            if (r11 == 0) goto L65
            boolean r11 = r14.equals(r11)     // Catch: java.lang.Throwable -> L4b
            if (r11 == 0) goto L65
            goto L4e
        L4b:
            r14 = move-exception
            goto Lb5
        L4e:
            java.lang.Object r5 = r8.f839c     // Catch: java.lang.Throwable -> L4b
            java.lang.Object r5 = r15.apply(r14, r5)     // Catch: java.lang.Throwable -> L4b
            if (r5 == 0) goto L59
            r8.f839c = r5     // Catch: java.lang.Throwable -> L4b
            goto Lab
        L59:
            j$.util.concurrent.k r3 = r8.f840d     // Catch: java.lang.Throwable -> L4b
            if (r10 == 0) goto L60
            r10.f840d = r3     // Catch: java.lang.Throwable -> L4b
            goto L63
        L60:
            m870h(r2, r6, r3)     // Catch: java.lang.Throwable -> L4b
        L63:
            r3 = -1
            goto Lab
        L65:
            j$.util.concurrent.k r10 = r8.f840d     // Catch: java.lang.Throwable -> L4b
            if (r10 != 0) goto L6a
            goto Lab
        L6a:
            int r4 = r4 + 1
            r12 = r10
            r10 = r8
            r8 = r12
            goto L3a
        L70:
            boolean r8 = r7 instanceof p017j$.util.concurrent.C1807p     // Catch: java.lang.Throwable -> L4b
            if (r8 == 0) goto L9e
            r4 = r7
            j$.util.concurrent.p r4 = (p017j$.util.concurrent.C1807p) r4     // Catch: java.lang.Throwable -> L4b
            j$.util.concurrent.q r8 = r4.f855e     // Catch: java.lang.Throwable -> L4b
            if (r8 == 0) goto L9c
            j$.util.concurrent.q r8 = r8.m901b(r1, r14, r0)     // Catch: java.lang.Throwable -> L4b
            if (r8 == 0) goto L9c
            java.lang.Object r5 = r8.f839c     // Catch: java.lang.Throwable -> L4b
            java.lang.Object r5 = r15.apply(r14, r5)     // Catch: java.lang.Throwable -> L4b
            if (r5 == 0) goto L8c
            r8.f839c = r5     // Catch: java.lang.Throwable -> L4b
            goto L9c
        L8c:
            boolean r3 = r4.m900f(r8)     // Catch: java.lang.Throwable -> L4b
            if (r3 == 0) goto L9b
            j$.util.concurrent.q r3 = r4.f856f     // Catch: java.lang.Throwable -> L4b
            j$.util.concurrent.k r3 = m874p(r3)     // Catch: java.lang.Throwable -> L4b
            m870h(r2, r6, r3)     // Catch: java.lang.Throwable -> L4b
        L9b:
            r3 = -1
        L9c:
            r4 = 2
            goto Lab
        L9e:
            boolean r6 = r7 instanceof p017j$.util.concurrent.C1803l     // Catch: java.lang.Throwable -> L4b
            if (r6 != 0) goto La3
            goto Lab
        La3:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L4b
            java.lang.String r15 = "Recursive update"
            r14.<init>(r15)     // Catch: java.lang.Throwable -> L4b
            throw r14     // Catch: java.lang.Throwable -> L4b
        Lab:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4b
            if (r4 == 0) goto L12
        Lae:
            if (r3 == 0) goto Lb4
            long r14 = (long) r3
            r13.m875a(r14, r4)
        Lb4:
            return r5
        Lb5:
            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4b
            throw r14
        Lb7:
            j$.util.concurrent.k[] r2 = r13.m877e()
            goto L12
        Lbd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.ConcurrentHashMap.computeIfPresent(java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        C1802k c1802k;
        Object obj2;
        if (obj == null || biFunction == null) {
            throw null;
        }
        int iM871i = m871i(obj.hashCode());
        C1802k[] c1802kArrM877e = this.f811a;
        int i = 0;
        Object objApply = null;
        int i2 = 0;
        while (true) {
            if (c1802kArrM877e != null) {
                int length = c1802kArrM877e.length;
                if (length != 0) {
                    int i3 = (length - 1) & iM871i;
                    C1802k c1802kM872k = m872k(c1802kArrM877e, i3);
                    if (c1802kM872k == null) {
                        C1803l c1803l = new C1803l();
                        synchronized (c1803l) {
                            try {
                                if (m868b(c1802kArrM877e, i3, c1803l)) {
                                    try {
                                        objApply = biFunction.apply(obj, null);
                                        if (objApply != null) {
                                            c1802k = new C1802k(iM871i, obj, objApply);
                                            i2 = 1;
                                        } else {
                                            c1802k = null;
                                        }
                                        m870h(c1802kArrM877e, i3, c1802k);
                                        i = 1;
                                    } catch (Throwable th) {
                                        m870h(c1802kArrM877e, i3, null);
                                        throw th;
                                    }
                                }
                            } finally {
                            }
                        }
                        if (i != 0) {
                        }
                    } else {
                        int i4 = c1802kM872k.f837a;
                        if (i4 == -1) {
                            c1802kArrM877e = m876d(c1802kArrM877e, c1802kM872k);
                        } else {
                            synchronized (c1802kM872k) {
                                try {
                                    if (m872k(c1802kArrM877e, i3) == c1802kM872k) {
                                        if (i4 >= 0) {
                                            C1802k c1802k2 = null;
                                            C1802k c1802k3 = c1802kM872k;
                                            i = 1;
                                            while (true) {
                                                if (c1802k3.f837a != iM871i || ((obj2 = c1802k3.f838b) != obj && (obj2 == null || !obj.equals(obj2)))) {
                                                    C1802k c1802k4 = c1802k3.f840d;
                                                    if (c1802k4 == null) {
                                                        Object objApply2 = biFunction.apply(obj, null);
                                                        if (objApply2 == null) {
                                                            objApply = objApply2;
                                                        } else {
                                                            if (c1802k3.f840d != null) {
                                                                throw new IllegalStateException("Recursive update");
                                                            }
                                                            c1802k3.f840d = new C1802k(iM871i, obj, objApply2);
                                                            objApply = objApply2;
                                                            i2 = 1;
                                                        }
                                                    } else {
                                                        i++;
                                                        c1802k2 = c1802k3;
                                                        c1802k3 = c1802k4;
                                                    }
                                                }
                                            }
                                            Object objApply3 = biFunction.apply(obj, c1802k3.f839c);
                                            if (objApply3 != null) {
                                                c1802k3.f839c = objApply3;
                                                objApply = objApply3;
                                            } else {
                                                C1802k c1802k5 = c1802k3.f840d;
                                                if (c1802k2 != null) {
                                                    c1802k2.f840d = c1802k5;
                                                } else {
                                                    m870h(c1802kArrM877e, i3, c1802k5);
                                                }
                                                objApply = objApply3;
                                                i2 = -1;
                                            }
                                        } else if (c1802kM872k instanceof C1807p) {
                                            C1807p c1807p = (C1807p) c1802kM872k;
                                            C1808q c1808q = c1807p.f855e;
                                            C1808q c1808qM901b = c1808q != null ? c1808q.m901b(iM871i, obj, null) : null;
                                            Object objApply4 = biFunction.apply(obj, c1808qM901b == null ? null : c1808qM901b.f839c);
                                            if (objApply4 != null) {
                                                if (c1808qM901b != null) {
                                                    c1808qM901b.f839c = objApply4;
                                                } else {
                                                    c1807p.m899e(iM871i, obj, objApply4);
                                                    i2 = 1;
                                                }
                                            } else if (c1808qM901b != null) {
                                                if (c1807p.m900f(c1808qM901b)) {
                                                    m870h(c1802kArrM877e, i3, m874p(c1807p.f856f));
                                                }
                                                i2 = -1;
                                            }
                                            objApply = objApply4;
                                            i = 1;
                                        } else if (c1802kM872k instanceof C1803l) {
                                            throw new IllegalStateException("Recursive update");
                                        }
                                    }
                                } finally {
                                }
                            }
                            if (i != 0) {
                                if (i >= 8) {
                                    m882n(c1802kArrM877e, i3);
                                }
                            }
                        }
                    }
                }
            }
            c1802kArrM877e = m877e();
        }
        if (i2 != 0) {
            m875a(i2, i);
        }
        return objApply;
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x00dd, code lost:
    
        throw new java.lang.IllegalStateException("Recursive update");
     */
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, p017j$.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object merge(java.lang.Object r18, java.lang.Object r19, java.util.function.BiFunction r20) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.ConcurrentHashMap.merge(java.lang.Object, java.lang.Object, java.util.function.BiFunction):java.lang.Object");
    }

    public static <K> KeySetView<K, Boolean> newKeySet(int i) {
        return new KeySetView<>(new ConcurrentHashMap(i), Boolean.TRUE);
    }

    /* renamed from: e */
    public final C1802k[] m877e() {
        while (true) {
            C1802k[] c1802kArr = this.f811a;
            if (c1802kArr != null && c1802kArr.length != 0) {
                return c1802kArr;
            }
            int i = this.sizeCtl;
            if (i < 0) {
                Thread.yield();
            } else if (f803h.m534c(this, f804i, i, -1)) {
                try {
                    C1802k[] c1802kArr2 = this.f811a;
                    if (c1802kArr2 == null || c1802kArr2.length == 0) {
                        int i2 = i > 0 ? i : 16;
                        C1802k[] c1802kArr3 = new C1802k[i2];
                        this.f811a = c1802kArr3;
                        i = i2 - (i2 >>> 2);
                        c1802kArr2 = c1802kArr3;
                    }
                    this.sizeCtl = i;
                    return c1802kArr2;
                } catch (Throwable th) {
                    this.sizeCtl = i;
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:97:0x013f, code lost:
    
        if (r1.f813c != r6) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0141, code lost:
    
        r1.f813c = (p017j$.util.concurrent.C1794c[]) java.util.Arrays.copyOf(r6, r7 << 1);
     */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01aa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x00c1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00fc  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void m875a(long r25, int r27) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.ConcurrentHashMap.m875a(long, int):void");
    }

    /* renamed from: d */
    public final C1802k[] m876d(C1802k[] c1802kArr, C1802k c1802k) {
        int i;
        if (c1802k instanceof C1798g) {
            C1802k[] c1802kArr2 = ((C1798g) c1802k).f830e;
            int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(c1802kArr.length) | 32768;
            while (c1802kArr2 == this.f812b && this.f811a == c1802kArr && (i = this.sizeCtl) < 0 && (i >>> 16) == iNumberOfLeadingZeros && i != iNumberOfLeadingZeros + 1 && i != 65535 + iNumberOfLeadingZeros && this.transferIndex > 0) {
                if (f803h.m534c(this, f804i, i, i + 1)) {
                    m881m(c1802kArr, c1802kArr2);
                    break;
                }
            }
            return c1802kArr2;
        }
        return this.f811a;
    }

    /* renamed from: o */
    public final void m883o(int i) {
        int length;
        int iM873l = i >= 536870912 ? TLObject.FLAG_30 : m873l(i + (i >>> 1) + 1);
        while (true) {
            int i2 = this.sizeCtl;
            if (i2 < 0) {
                break;
            }
            C1802k[] c1802kArr = this.f811a;
            if (c1802kArr == null || (length = c1802kArr.length) == 0) {
                int i3 = i2 > iM873l ? i2 : iM873l;
                if (f803h.m534c(this, f804i, i2, -1)) {
                    try {
                        if (this.f811a == c1802kArr) {
                            this.f811a = new C1802k[i3];
                            i2 = i3 - (i3 >>> 2);
                        }
                    } finally {
                        this.sizeCtl = i2;
                    }
                } else {
                    continue;
                }
            } else if (iM873l <= i2 || length >= 1073741824) {
                break;
            } else if (c1802kArr == this.f811a) {
                if (f803h.m534c(this, f804i, i2, ((Integer.numberOfLeadingZeros(length) | 32768) << 16) + 2)) {
                    m881m(c1802kArr, null);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11, types: [j$.util.concurrent.k] */
    /* JADX WARN: Type inference failed for: r10v9, types: [j$.util.concurrent.k] */
    /* JADX WARN: Type inference failed for: r5v5, types: [j$.util.concurrent.k] */
    /* JADX WARN: Type inference failed for: r8v13, types: [j$.util.concurrent.k] */
    /* JADX WARN: Type inference failed for: r8v8, types: [j$.util.concurrent.k] */
    /* renamed from: m */
    public final void m881m(C1802k[] c1802kArr, C1802k[] c1802kArr2) {
        C1802k[] c1802kArr3;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        C1802k c1807p;
        C1802k c1807p2;
        C1808q c1802k;
        int i7;
        ConcurrentHashMap<K, V> concurrentHashMap = this;
        int length = c1802kArr.length;
        int i8 = f802g;
        int i9 = i8 > 1 ? (length >>> 3) / i8 : length;
        int i10 = i9 < 16 ? 16 : i9;
        if (c1802kArr2 == null) {
            try {
                C1802k[] c1802kArr4 = new C1802k[length << 1];
                concurrentHashMap.f812b = c1802kArr4;
                concurrentHashMap.transferIndex = length;
                c1802kArr3 = c1802kArr4;
            } catch (Throwable unused) {
                concurrentHashMap.sizeCtl = ConnectionsManager.DEFAULT_DATACENTER_ID;
                return;
            }
        } else {
            c1802kArr3 = c1802kArr2;
        }
        int length2 = c1802kArr3.length;
        C1798g c1798g = new C1798g(c1802kArr3);
        int i11 = 0;
        int i12 = 0;
        boolean zM868b = true;
        boolean z = false;
        while (true) {
            if (zM868b) {
                int i13 = i11 - 1;
                if (i13 >= i12 || z) {
                    i12 = i12;
                    i11 = i13;
                    zM868b = false;
                } else {
                    int i14 = concurrentHashMap.transferIndex;
                    if (i14 <= 0) {
                        i11 = -1;
                    } else {
                        C1638a c1638a = f803h;
                        int i15 = i12;
                        long j = f805j;
                        if (i14 > i10) {
                            i2 = i15;
                            i3 = i14 - i10;
                            i = i13;
                        } else {
                            i = i13;
                            i2 = i15;
                            i3 = 0;
                        }
                        boolean zM534c = c1638a.m534c(concurrentHashMap, j, i14, i3);
                        i12 = i3;
                        if (zM534c) {
                            i11 = i14 - 1;
                        } else {
                            i12 = i2;
                            i11 = i;
                        }
                    }
                    zM868b = false;
                }
            } else {
                int i16 = i12;
                C1808q c1802k2 = null;
                if (i11 < 0 || i11 >= length || (i6 = i11 + length) >= length2) {
                    i4 = length;
                    i5 = i10;
                    if (z) {
                        concurrentHashMap.f812b = null;
                        concurrentHashMap.f811a = c1802kArr3;
                        concurrentHashMap.sizeCtl = (i4 << 1) - (i4 >>> 1);
                        return;
                    }
                    int i17 = i11;
                    C1638a c1638a2 = f803h;
                    long j2 = f804i;
                    int i18 = concurrentHashMap.sizeCtl;
                    if (!c1638a2.m534c(concurrentHashMap, j2, i18, i18 - 1)) {
                        i11 = i17;
                    } else {
                        if (i18 - 2 != ((Integer.numberOfLeadingZeros(i4) | 32768) << 16)) {
                            return;
                        }
                        i11 = i4;
                        zM868b = true;
                        z = true;
                    }
                } else {
                    ?? M872k = m872k(c1802kArr, i11);
                    if (M872k == 0) {
                        zM868b = m868b(c1802kArr, i11, c1798g);
                        i4 = length;
                        i5 = i10;
                    } else {
                        int i19 = M872k.f837a;
                        if (i19 == -1) {
                            i4 = length;
                            i5 = i10;
                            zM868b = true;
                        } else {
                            synchronized (M872k) {
                                try {
                                    if (m872k(c1802kArr, i11) == M872k) {
                                        if (i19 >= 0) {
                                            int i20 = i19 & length;
                                            C1808q c1808q = M872k;
                                            for (C1808q c1808q2 = M872k.f840d; c1808q2 != null; c1808q2 = c1808q2.f840d) {
                                                int i21 = c1808q2.f837a & length;
                                                if (i21 != i20) {
                                                    c1808q = c1808q2;
                                                    i20 = i21;
                                                }
                                            }
                                            if (i20 == 0) {
                                                c1802k = null;
                                                c1802k2 = c1808q;
                                            } else {
                                                c1802k = c1808q;
                                            }
                                            C1802k c1802k3 = M872k;
                                            while (c1802k3 != c1808q) {
                                                int i22 = c1802k3.f837a;
                                                Object obj = c1802k3.f838b;
                                                int i23 = length;
                                                Object obj2 = c1802k3.f839c;
                                                if ((i22 & i23) == 0) {
                                                    i7 = i10;
                                                    c1802k2 = new C1802k(i22, obj, obj2, c1802k2);
                                                } else {
                                                    i7 = i10;
                                                    c1802k = new C1802k(i22, obj, obj2, c1802k);
                                                }
                                                c1802k3 = c1802k3.f840d;
                                                length = i23;
                                                i10 = i7;
                                            }
                                            i4 = length;
                                            i5 = i10;
                                            m870h(c1802kArr3, i11, c1802k2);
                                            m870h(c1802kArr3, i6, c1802k);
                                            m870h(c1802kArr, i11, c1798g);
                                        } else {
                                            i4 = length;
                                            i5 = i10;
                                            if (M872k instanceof C1807p) {
                                                C1807p c1807p3 = (C1807p) M872k;
                                                C1808q c1808q3 = null;
                                                C1808q c1808q4 = null;
                                                C1802k c1802k4 = c1807p3.f856f;
                                                int i24 = 0;
                                                int i25 = 0;
                                                C1808q c1808q5 = null;
                                                while (c1802k4 != null) {
                                                    C1807p c1807p4 = c1807p3;
                                                    int i26 = c1802k4.f837a;
                                                    C1808q c1808q6 = new C1808q(i26, c1802k4.f838b, c1802k4.f839c, null, null);
                                                    if ((i26 & i4) == 0) {
                                                        c1808q6.f861h = c1808q4;
                                                        if (c1808q4 == null) {
                                                            c1802k2 = c1808q6;
                                                        } else {
                                                            c1808q4.f840d = c1808q6;
                                                        }
                                                        i24++;
                                                        c1808q4 = c1808q6;
                                                    } else {
                                                        c1808q6.f861h = c1808q3;
                                                        if (c1808q3 == null) {
                                                            c1808q5 = c1808q6;
                                                        } else {
                                                            c1808q3.f840d = c1808q6;
                                                        }
                                                        i25++;
                                                        c1808q3 = c1808q6;
                                                    }
                                                    c1802k4 = c1802k4.f840d;
                                                    c1807p3 = c1807p4;
                                                }
                                                C1807p c1807p5 = c1807p3;
                                                if (i24 <= 6) {
                                                    c1807p = m874p(c1802k2);
                                                } else {
                                                    c1807p = i25 != 0 ? new C1807p(c1802k2) : c1807p5;
                                                }
                                                if (i25 <= 6) {
                                                    c1807p2 = m874p(c1808q5);
                                                } else {
                                                    c1807p2 = i24 != 0 ? new C1807p(c1808q5) : c1807p5;
                                                }
                                                m870h(c1802kArr3, i11, c1807p);
                                                m870h(c1802kArr3, i6, c1807p2);
                                                m870h(c1802kArr, i11, c1798g);
                                            }
                                        }
                                        zM868b = true;
                                    } else {
                                        i4 = length;
                                        i5 = i10;
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                }
                concurrentHashMap = this;
                i12 = i16;
                length = i4;
                i10 = i5;
            }
        }
    }

    /* renamed from: j */
    public final long m880j() {
        C1794c[] c1794cArr = this.f813c;
        long j = this.baseCount;
        if (c1794cArr != null) {
            for (C1794c c1794c : c1794cArr) {
                if (c1794c != null) {
                    j += c1794c.value;
                }
            }
        }
        return j;
    }

    /* renamed from: n */
    public final void m882n(C1802k[] c1802kArr, int i) {
        int length = c1802kArr.length;
        if (length < 64) {
            m883o(length << 1);
            return;
        }
        C1802k c1802kM872k = m872k(c1802kArr, i);
        if (c1802kM872k == null || c1802kM872k.f837a < 0) {
            return;
        }
        synchronized (c1802kM872k) {
            try {
                if (m872k(c1802kArr, i) == c1802kM872k) {
                    C1808q c1808q = null;
                    C1808q c1808q2 = null;
                    C1802k c1802k = c1802kM872k;
                    while (c1802k != null) {
                        C1808q c1808q3 = new C1808q(c1802k.f837a, c1802k.f838b, c1802k.f839c, null, null);
                        c1808q3.f861h = c1808q2;
                        if (c1808q2 == null) {
                            c1808q = c1808q3;
                        } else {
                            c1808q2.f840d = c1808q3;
                        }
                        c1802k = c1802k.f840d;
                        c1808q2 = c1808q3;
                    }
                    m870h(c1802kArr, i, new C1807p(c1808q));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2, types: [j$.util.concurrent.k] */
    /* renamed from: p */
    public static C1802k m874p(C1808q c1808q) {
        C1802k c1802k = null;
        C1802k c1802k2 = null;
        for (C1808q c1808q2 = c1808q; c1808q2 != null; c1808q2 = c1808q2.f840d) {
            C1802k c1802k3 = new C1802k(c1808q2.f837a, c1808q2.f838b, c1808q2.f839c);
            if (c1802k2 == null) {
                c1802k = c1802k3;
            } else {
                c1802k2.f840d = c1802k3;
            }
            c1802k2 = c1802k3;
        }
        return c1802k;
    }

    public static class KeySetView<K, V> extends AbstractC1793b implements Set<K>, Serializable, p017j$.util.Set<K> {
        private static final long serialVersionUID = 7249069246763182397L;

        /* renamed from: b */
        public final Object f817b;

        @Override // java.util.Collection, p017j$.util.Collection
        public final /* synthetic */ Stream parallelStream() {
            return Collection.CC.$default$parallelStream(this);
        }

        @Override // java.util.Collection
        public final /* synthetic */ java.util.stream.Stream parallelStream() {
            return Stream.Wrapper.convert(Collection.CC.$default$parallelStream(this));
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public final /* synthetic */ boolean removeIf(Predicate predicate) {
            return Collection.CC.$default$removeIf(this, predicate);
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set
        public final /* synthetic */ Spliterator spliterator() {
            return Spliterator.Wrapper.convert(spliterator());
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public final /* synthetic */ Stream stream() {
            return Collection.CC.$default$stream(this);
        }

        @Override // java.util.Collection
        public final /* synthetic */ java.util.stream.Stream stream() {
            return Stream.Wrapper.convert(Collection.CC.$default$stream(this));
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return toArray((Object[]) intFunction.apply(0));
        }

        public KeySetView(ConcurrentHashMap concurrentHashMap, Object obj) {
            super(concurrentHashMap);
            this.f817b = obj;
        }

        @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return this.f827a.containsKey(obj);
        }

        @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            return this.f827a.remove(obj) != null;
        }

        @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator iterator() {
            ConcurrentHashMap concurrentHashMap = this.f827a;
            C1802k[] c1802kArr = concurrentHashMap.f811a;
            int length = c1802kArr == null ? 0 : c1802kArr.length;
            return new C1799h(c1802kArr, length, length, concurrentHashMap, 0);
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean add(Object obj) {
            Object obj2 = this.f817b;
            if (obj2 != null) {
                return this.f827a.m878f(obj, obj2, true) == null;
            }
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean addAll(java.util.Collection collection) {
            Object obj = this.f817b;
            if (obj == null) {
                throw new UnsupportedOperationException();
            }
            Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (this.f827a.m878f(it.next(), obj, true) == null) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Collection, java.util.Set
        public final int hashCode() {
            Object it = iterator();
            int iHashCode = 0;
            while (((AbstractC1792a) it).hasNext()) {
                iHashCode += ((C1799h) it).next().hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean equals(Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            Set set = (Set) obj;
            if (set != this) {
                return containsAll(set) && set.containsAll(this);
            }
            return true;
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set, p017j$.util.Set, p017j$.util.Collection
        public final p017j$.util.Spliterator spliterator() {
            ConcurrentHashMap concurrentHashMap = this.f827a;
            long jM880j = concurrentHashMap.m880j();
            C1802k[] c1802kArr = concurrentHashMap.f811a;
            int length = c1802kArr == null ? 0 : c1802kArr.length;
            return new C1800i(c1802kArr, length, 0, length, jM880j < 0 ? 0L : jM880j, 0);
        }

        @Override // java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
        public final void forEach(Consumer consumer) {
            consumer.getClass();
            C1802k[] c1802kArr = this.f827a.f811a;
            if (c1802kArr == null) {
                return;
            }
            C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
            while (true) {
                C1802k c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    return;
                } else {
                    consumer.accept(c1802kM892a.f838b);
                }
            }
        }
    }
}
