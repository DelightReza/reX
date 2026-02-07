package com.google.mlkit.p014nl.languageid.internal;

import java.util.List;

/* loaded from: classes4.dex */
public interface LanguageIdentifierDelegate {
    List identifyPossibleLanguages(String str, float f);

    void init();

    void release();
}
