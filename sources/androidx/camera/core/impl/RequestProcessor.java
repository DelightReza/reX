package androidx.camera.core.impl;

import java.util.List;

/* loaded from: classes3.dex */
public interface RequestProcessor {

    public interface Callback {
    }

    public interface Request {
    }

    void abortCaptures();

    int setRepeating(Request request, Callback callback);

    void stopRepeating();

    int submit(Request request, Callback callback);

    int submit(List list, Callback callback);
}
