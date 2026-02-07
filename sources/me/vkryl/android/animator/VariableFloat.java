package me.vkryl.android.animator;

/* loaded from: classes.dex */
public class VariableFloat {
    private float from;
    private float now;

    /* renamed from: to */
    private float f1404to;

    public VariableFloat(float f) {
        set(f);
    }

    public void set(float f) {
        this.from = f;
        this.f1404to = f;
        this.now = f;
    }

    public float get() {
        return this.now;
    }

    public void setFrom(float f) {
        this.from = f;
    }

    public void setTo(float f) {
        this.f1404to = f;
    }

    public boolean differs(float f) {
        return this.f1404to != f;
    }

    public void finishAnimation(boolean z) {
        if (z) {
            float f = this.f1404to;
            this.now = f;
            this.from = f;
            return;
        }
        this.from = this.now;
    }

    public boolean applyAnimation(float f) {
        float f2 = this.from;
        float f3 = f2 + ((this.f1404to - f2) * f);
        if (this.now == f3) {
            return false;
        }
        this.now = f3;
        return true;
    }
}
