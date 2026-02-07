package androidx.work.impl.constraints;

import java.util.List;

/* loaded from: classes.dex */
public interface WorkConstraintsCallback {
    void onAllConstraintsMet(List list);

    void onAllConstraintsNotMet(List list);
}
