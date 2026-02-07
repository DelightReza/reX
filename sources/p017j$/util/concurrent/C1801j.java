package p017j$.util.concurrent;

import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.concurrent.j */
/* loaded from: classes2.dex */
public final class C1801j implements Map.Entry {

    /* renamed from: a */
    public final Object f834a;

    /* renamed from: b */
    public Object f835b;

    /* renamed from: c */
    public final ConcurrentHashMap f836c;

    public C1801j(Object obj, Object obj2, ConcurrentHashMap concurrentHashMap) {
        this.f834a = obj;
        this.f835b = obj2;
        this.f836c = concurrentHashMap;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.f834a;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.f835b;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return this.f834a.hashCode() ^ this.f835b.hashCode();
    }

    public final String toString() {
        return AbstractC1636a.m502U(this.f834a, this.f835b);
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        Map.Entry entry;
        Object key;
        Object value;
        if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (value = entry.getValue()) == null) {
            return false;
        }
        Object obj2 = this.f834a;
        if (key != obj2 && !key.equals(obj2)) {
            return false;
        }
        Object obj3 = this.f835b;
        return value == obj3 || value.equals(obj3);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        obj.getClass();
        Object obj2 = this.f835b;
        this.f835b = obj;
        this.f836c.put(this.f834a, obj);
        return obj2;
    }
}
