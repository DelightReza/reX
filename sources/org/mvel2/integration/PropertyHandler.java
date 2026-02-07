package org.mvel2.integration;

/* loaded from: classes4.dex */
public interface PropertyHandler {
    Object getProperty(String str, Object obj, VariableResolverFactory variableResolverFactory);

    Object setProperty(String str, Object obj, VariableResolverFactory variableResolverFactory, Object obj2);
}
