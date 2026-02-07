package com.sun.jna;

/* loaded from: classes4.dex */
public class FromNativeContext {
    private Class<?> type;

    FromNativeContext(Class<?> cls) {
        this.type = cls;
    }

    public Class<?> getTargetType() {
        return this.type;
    }
}
