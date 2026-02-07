package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

/* loaded from: classes4.dex */
abstract class HideViewOnScrollDelegate {
    abstract int getSize(View view, ViewGroup.MarginLayoutParams marginLayoutParams);

    abstract int getTargetTranslation();

    abstract int getViewEdge();

    abstract ViewPropertyAnimator getViewTranslationAnimator(View view, int i);

    abstract void setViewTranslation(View view, int i);

    HideViewOnScrollDelegate() {
    }
}
