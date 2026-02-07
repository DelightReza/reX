package p017j$.util.concurrent;

import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.concurrent.k */
/* loaded from: classes2.dex */
public class C1802k implements Map.Entry {

    /* renamed from: a */
    public final int f837a;

    /* renamed from: b */
    public final Object f838b;

    /* renamed from: c */
    public volatile Object f839c;

    /* renamed from: d */
    public volatile C1802k f840d;

    public C1802k(int i, Object obj, Object obj2) {
        this.f837a = i;
        this.f838b = obj;
        this.f839c = obj2;
    }

    public C1802k(int i, Object obj, Object obj2, C1802k c1802k) {
        this(i, obj, obj2);
        this.f840d = c1802k;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.f838b;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.f839c;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return this.f838b.hashCode() ^ this.f839c.hashCode();
    }

    public final String toString() {
        return AbstractC1636a.m502U(this.f838b, this.f839c);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        Map.Entry entry;
        Object key;
        Object value;
        if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (value = entry.getValue()) == null) {
            return false;
        }
        Object obj2 = this.f838b;
        if (key != obj2 && !key.equals(obj2)) {
            return false;
        }
        Object obj3 = this.f839c;
        return value == obj3 || value.equals(obj3);
    }

    /* renamed from: a */
    public C1802k mo891a(int i, Object obj) {
        Object obj2;
        C1802k c1802k = this;
        do {
            if (c1802k.f837a == i && ((obj2 = c1802k.f838b) == obj || (obj2 != null && obj.equals(obj2)))) {
                return c1802k;
            }
            c1802k = c1802k.f840d;
        } while (c1802k != null);
        return null;
    }
}
