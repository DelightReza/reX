package com.google.mlkit.p014nl.languageid.internal;

import android.content.Context;
import com.google.mlkit.p014nl.languageid.LanguageIdentificationOptions;

/* loaded from: classes.dex */
public interface LanguageIdentifierCreatorDelegate {
    LanguageIdentifierDelegate create(Context context, LanguageIdentificationOptions languageIdentificationOptions);

    int getPriority();
}
