package com.sun.jna;

import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class CallbackResultContext extends ToNativeContext {
    private Method method;

    CallbackResultContext(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return this.method;
    }
}
