package com.google.common.base;

/* loaded from: classes4.dex */
public abstract class MoreObjects {
    public static Object firstNonNull(Object obj, Object obj2) {
        if (obj != null) {
            return obj;
        }
        if (obj2 != null) {
            return obj2;
        }
        throw new NullPointerException("Both parameters are null");
    }
}
