package org.telegram.p023ui.Components;

import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class CornerPath extends Path {
    private static ArrayList recycled;
    private boolean isPathCreated;
    private int paddingX;
    private int paddingY;
    private final ArrayList rects;
    private float rectsUnionDiffDelta;
    protected boolean useCornerPathImplementation;

    public CornerPath() {
        this.isPathCreated = false;
        this.useCornerPathImplementation = true;
        this.rectsUnionDiffDelta = 0.0f;
        this.rects = new ArrayList(1);
    }

    public CornerPath(int i) {
        this.isPathCreated = false;
        this.useCornerPathImplementation = true;
        this.rectsUnionDiffDelta = 0.0f;
        this.rects = new ArrayList(i);
    }

    public void setPadding(int i, int i2) {
        this.paddingX = i;
        this.paddingY = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007e  */
    @Override // android.graphics.Path
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void addRect(android.graphics.RectF r10, android.graphics.Path.Direction r11) {
        /*
            r9 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 34
            if (r0 < r1) goto La1
            boolean r0 = r9.useCornerPathImplementation
            if (r0 != 0) goto Lc
            goto La1
        Lc:
            java.util.ArrayList r11 = r9.rects
            int r11 = r11.size()
            if (r11 <= 0) goto L29
            java.util.ArrayList r11 = r9.rects
            int r0 = r11.size()
            int r0 = r0 + (-1)
            java.lang.Object r11 = r11.get(r0)
            android.graphics.RectF r11 = (android.graphics.RectF) r11
            boolean r11 = r11.contains(r10)
            if (r11 == 0) goto L29
            return
        L29:
            java.util.ArrayList r11 = r9.rects
            int r11 = r11.size()
            r0 = 0
            if (r11 <= 0) goto L7e
            float r11 = r10.top
            java.util.ArrayList r1 = r9.rects
            int r2 = r1.size()
            int r2 = r2 + (-1)
            java.lang.Object r1 = r1.get(r2)
            android.graphics.RectF r1 = (android.graphics.RectF) r1
            float r1 = r1.top
            float r11 = r11 - r1
            float r11 = java.lang.Math.abs(r11)
            float r1 = r9.rectsUnionDiffDelta
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r11 > 0) goto L7e
            float r11 = r10.bottom
            java.util.ArrayList r1 = r9.rects
            int r2 = r1.size()
            int r2 = r2 + (-1)
            java.lang.Object r1 = r1.get(r2)
            android.graphics.RectF r1 = (android.graphics.RectF) r1
            float r1 = r1.bottom
            float r11 = r11 - r1
            float r11 = java.lang.Math.abs(r11)
            float r1 = r9.rectsUnionDiffDelta
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r11 > 0) goto L7e
            java.util.ArrayList r11 = r9.rects
            int r1 = r11.size()
            int r1 = r1 + (-1)
            java.lang.Object r11 = r11.get(r1)
            android.graphics.RectF r11 = (android.graphics.RectF) r11
            r11.union(r10)
            goto L9e
        L7e:
            java.util.ArrayList r11 = org.telegram.p023ui.Components.CornerPath.recycled
            if (r11 == 0) goto L91
            int r11 = r11.size()
            if (r11 <= 0) goto L91
            java.util.ArrayList r11 = org.telegram.p023ui.Components.CornerPath.recycled
            java.lang.Object r11 = r11.remove(r0)
            android.graphics.RectF r11 = (android.graphics.RectF) r11
            goto L96
        L91:
            android.graphics.RectF r11 = new android.graphics.RectF
            r11.<init>()
        L96:
            r11.set(r10)
            java.util.ArrayList r10 = r9.rects
            r10.add(r11)
        L9e:
            r9.isPathCreated = r0
            return
        La1:
            float r0 = r10.left
            int r1 = r9.paddingX
            float r2 = (float) r1
            float r4 = r0 - r2
            float r0 = r10.top
            int r2 = r9.paddingY
            float r3 = (float) r2
            float r5 = r0 - r3
            float r0 = r10.right
            float r1 = (float) r1
            float r6 = r0 + r1
            float r10 = r10.bottom
            float r0 = (float) r2
            float r7 = r10 + r0
            r3 = r9
            r8 = r11
            super.addRect(r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.CornerPath.addRect(android.graphics.RectF, android.graphics.Path$Direction):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007c  */
    @Override // android.graphics.Path
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void addRect(float r9, float r10, float r11, float r12, android.graphics.Path.Direction r13) {
        /*
            r8 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 34
            if (r0 < r1) goto L9f
            boolean r0 = r8.useCornerPathImplementation
            if (r0 != 0) goto Lc
            goto L9f
        Lc:
            java.util.ArrayList r13 = r8.rects
            int r13 = r13.size()
            if (r13 <= 0) goto L29
            java.util.ArrayList r13 = r8.rects
            int r0 = r13.size()
            int r0 = r0 + (-1)
            java.lang.Object r13 = r13.get(r0)
            android.graphics.RectF r13 = (android.graphics.RectF) r13
            boolean r13 = r13.contains(r9, r10, r11, r12)
            if (r13 == 0) goto L29
            return
        L29:
            java.util.ArrayList r13 = r8.rects
            int r13 = r13.size()
            r0 = 0
            if (r13 <= 0) goto L7c
            java.util.ArrayList r13 = r8.rects
            int r1 = r13.size()
            int r1 = r1 + (-1)
            java.lang.Object r13 = r13.get(r1)
            android.graphics.RectF r13 = (android.graphics.RectF) r13
            float r13 = r13.top
            float r13 = r10 - r13
            float r13 = java.lang.Math.abs(r13)
            float r1 = r8.rectsUnionDiffDelta
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 > 0) goto L7c
            java.util.ArrayList r13 = r8.rects
            int r1 = r13.size()
            int r1 = r1 + (-1)
            java.lang.Object r13 = r13.get(r1)
            android.graphics.RectF r13 = (android.graphics.RectF) r13
            float r13 = r13.bottom
            float r13 = r12 - r13
            float r13 = java.lang.Math.abs(r13)
            float r1 = r8.rectsUnionDiffDelta
            int r13 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            if (r13 > 0) goto L7c
            java.util.ArrayList r13 = r8.rects
            int r1 = r13.size()
            int r1 = r1 + (-1)
            java.lang.Object r13 = r13.get(r1)
            android.graphics.RectF r13 = (android.graphics.RectF) r13
            r13.union(r9, r10, r11, r12)
            goto L9c
        L7c:
            java.util.ArrayList r13 = org.telegram.p023ui.Components.CornerPath.recycled
            if (r13 == 0) goto L8f
            int r13 = r13.size()
            if (r13 <= 0) goto L8f
            java.util.ArrayList r13 = org.telegram.p023ui.Components.CornerPath.recycled
            java.lang.Object r13 = r13.remove(r0)
            android.graphics.RectF r13 = (android.graphics.RectF) r13
            goto L94
        L8f:
            android.graphics.RectF r13 = new android.graphics.RectF
            r13.<init>()
        L94:
            r13.set(r9, r10, r11, r12)
            java.util.ArrayList r9 = r8.rects
            r9.add(r13)
        L9c:
            r8.isPathCreated = r0
            return
        L9f:
            int r0 = r8.paddingX
            float r1 = (float) r0
            float r3 = r9 - r1
            int r9 = r8.paddingY
            float r1 = (float) r9
            float r4 = r10 - r1
            float r10 = (float) r0
            float r5 = r11 + r10
            float r9 = (float) r9
            float r6 = r12 + r9
            r2 = r8
            r7 = r13
            super.addRect(r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.CornerPath.addRect(float, float, float, float, android.graphics.Path$Direction):void");
    }

    @Override // android.graphics.Path
    public void reset() {
        super.reset();
        if (Build.VERSION.SDK_INT < 34 || !this.useCornerPathImplementation) {
            return;
        }
        resetRects();
    }

    @Override // android.graphics.Path
    public void rewind() {
        super.rewind();
        if (Build.VERSION.SDK_INT < 34 || !this.useCornerPathImplementation) {
            return;
        }
        resetRects();
    }

    private void resetRects() {
        if (recycled == null) {
            recycled = new ArrayList(this.rects.size());
        }
        recycled.addAll(this.rects);
        this.rects.clear();
        this.isPathCreated = false;
    }

    public void closeRects() {
        if (Build.VERSION.SDK_INT < 34 || !this.useCornerPathImplementation || this.isPathCreated) {
            return;
        }
        createClosedPathsFromRects(this.rects);
        this.isPathCreated = true;
    }

    public void setUseCornerPathImplementation(boolean z) {
        this.useCornerPathImplementation = z;
    }

    public void setRectsUnionDiffDelta(float f) {
        this.rectsUnionDiffDelta = f;
    }

    private void createClosedPathsFromRects(List list) {
        if (list.isEmpty()) {
            return;
        }
        boolean z = false;
        if (list.size() == 1) {
            super.addRect(((RectF) list.get(0)).left - this.paddingX, ((RectF) list.get(0)).top - this.paddingY, ((RectF) list.get(0)).right + this.paddingX, ((RectF) list.get(0)).bottom + this.paddingY, Path.Direction.CW);
            return;
        }
        RectF rectF = (RectF) list.get(0);
        int size = list.size() - 1;
        super.moveTo(rectF.left - this.paddingX, rectF.top - this.paddingY);
        for (int i = 1; i < list.size(); i++) {
            RectF rectF2 = (RectF) list.get(i);
            if (rectF2.width() != 0.0f) {
                float f = rectF.bottom;
                int i2 = this.paddingY;
                float f2 = f + i2;
                float f3 = rectF2.top;
                if (f2 >= f3 - i2) {
                    float f4 = rectF.left;
                    if (f4 <= rectF2.right) {
                        float f5 = rectF.right;
                        float f6 = rectF2.left;
                        if (f5 >= f6) {
                            if (f4 != f6) {
                                super.lineTo(f4 - this.paddingX, f3);
                                super.lineTo(rectF2.left - this.paddingX, rectF2.top);
                            }
                            rectF = rectF2;
                        }
                    }
                }
                size = i;
                z = true;
                break;
            }
        }
        super.lineTo(rectF.left - this.paddingX, rectF.bottom + this.paddingY);
        super.lineTo(rectF.right + this.paddingX, rectF.bottom + this.paddingY);
        for (int i3 = size - 1; i3 >= 0; i3--) {
            RectF rectF3 = (RectF) list.get(i3);
            if (rectF3.width() != 0.0f) {
                float f7 = rectF.right;
                if (f7 != rectF3.right) {
                    super.lineTo(f7 + this.paddingX, rectF.top);
                    super.lineTo(rectF3.right + this.paddingX, rectF.top);
                }
                rectF = rectF3;
            }
        }
        super.lineTo(rectF.right + this.paddingX, rectF.top - this.paddingY);
        super.close();
        if (z) {
            createClosedPathsFromRects(list.subList(size, list.size()));
        }
    }
}
