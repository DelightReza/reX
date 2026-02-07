package org.mvel2;

/* loaded from: classes4.dex */
public interface ConversionHandler {
    boolean canConvertFrom(Class cls);

    Object convertFrom(Object obj);
}
