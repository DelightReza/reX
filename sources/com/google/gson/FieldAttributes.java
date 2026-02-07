package com.google.gson;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import p017j$.util.Objects;

/* loaded from: classes4.dex */
public final class FieldAttributes {
    private final Field field;

    public FieldAttributes(Field field) {
        Objects.requireNonNull(field);
        this.field = field;
    }

    public Class getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    public String getName() {
        return this.field.getName();
    }

    public Type getDeclaredType() {
        return this.field.getGenericType();
    }

    public String toString() {
        return this.field.toString();
    }
}
