package me.vkryl.android.animator;

/* loaded from: classes.dex */
public interface Animatable {
    boolean applyAnimation(float f);

    void applyChanges();

    void finishAnimation(boolean z);

    boolean hasChanges();

    void prepareChanges();

    /* renamed from: me.vkryl.android.animator.Animatable$-CC, reason: invalid class name */
    /* loaded from: classes4.dex */
    public abstract /* synthetic */ class CC {
        public static boolean $default$hasChanges(Animatable animatable) {
            return false;
        }

        public static void $default$prepareChanges(Animatable animatable) {
        }

        public static void $default$applyChanges(Animatable animatable) {
        }
    }
}
