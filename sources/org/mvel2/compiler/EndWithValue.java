package org.mvel2.compiler;

/* loaded from: classes4.dex */
public class EndWithValue extends RuntimeException {
    private Object value;

    public EndWithValue(Object obj) {
        this.value = obj;
    }

    public Object getValue() {
        return this.value;
    }
}
