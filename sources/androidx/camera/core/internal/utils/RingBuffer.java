package androidx.camera.core.internal.utils;

/* loaded from: classes3.dex */
public interface RingBuffer {

    public interface OnRemoveCallback {
        void onRemove(Object obj);
    }

    Object dequeue();

    boolean isEmpty();
}
