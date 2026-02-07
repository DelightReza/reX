package com.google.android.material.shape;

/* loaded from: classes4.dex */
public interface ShapeAppearance {
    ShapeAppearanceModel getDefaultShape();

    ShapeAppearanceModel getShapeForState(int[] iArr);

    boolean isStateful();

    ShapeAppearanceModel withCornerSize(float f);

    ShapeAppearanceModel withCornerSize(CornerSize cornerSize);
}
