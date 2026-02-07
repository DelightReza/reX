package com.google.gson.internal;

/* loaded from: classes.dex */
public abstract class GsonPreconditions {
    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }
}
