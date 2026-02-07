package androidx.work.impl.constraints.controllers;

import androidx.work.impl.constraints.ConstraintListener;
import androidx.work.impl.constraints.trackers.ConstraintTracker;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ConstraintController implements ConstraintListener {
    private OnConstraintUpdatedCallback callback;
    private Object currentValue;
    private final List matchingWorkSpecIds;
    private final List matchingWorkSpecs;
    private final ConstraintTracker tracker;

    public interface OnConstraintUpdatedCallback {
        void onConstraintMet(List list);

        void onConstraintNotMet(List list);
    }

    public abstract boolean hasConstraint(WorkSpec workSpec);

    public abstract boolean isConstrained(Object obj);

    public ConstraintController(ConstraintTracker tracker) {
        Intrinsics.checkNotNullParameter(tracker, "tracker");
        this.tracker = tracker;
        this.matchingWorkSpecs = new ArrayList();
        this.matchingWorkSpecIds = new ArrayList();
    }

    public final void setCallback(OnConstraintUpdatedCallback onConstraintUpdatedCallback) {
        if (this.callback != onConstraintUpdatedCallback) {
            this.callback = onConstraintUpdatedCallback;
            updateCallback(onConstraintUpdatedCallback, this.currentValue);
        }
    }

    public final void replace(Iterable workSpecs) {
        Intrinsics.checkNotNullParameter(workSpecs, "workSpecs");
        this.matchingWorkSpecs.clear();
        this.matchingWorkSpecIds.clear();
        List list = this.matchingWorkSpecs;
        for (Object obj : workSpecs) {
            if (hasConstraint((WorkSpec) obj)) {
                list.add(obj);
            }
        }
        List list2 = this.matchingWorkSpecs;
        List list3 = this.matchingWorkSpecIds;
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            list3.add(((WorkSpec) it.next()).f51id);
        }
        if (this.matchingWorkSpecs.isEmpty()) {
            this.tracker.removeListener(this);
        } else {
            this.tracker.addListener(this);
        }
        updateCallback(this.callback, this.currentValue);
    }

    public final void reset() {
        if (this.matchingWorkSpecs.isEmpty()) {
            return;
        }
        this.matchingWorkSpecs.clear();
        this.tracker.removeListener(this);
    }

    public final boolean isWorkSpecConstrained(String workSpecId) {
        Intrinsics.checkNotNullParameter(workSpecId, "workSpecId");
        Object obj = this.currentValue;
        return obj != null && isConstrained(obj) && this.matchingWorkSpecIds.contains(workSpecId);
    }

    private final void updateCallback(OnConstraintUpdatedCallback onConstraintUpdatedCallback, Object obj) {
        if (this.matchingWorkSpecs.isEmpty() || onConstraintUpdatedCallback == null) {
            return;
        }
        if (obj == null || isConstrained(obj)) {
            onConstraintUpdatedCallback.onConstraintNotMet(this.matchingWorkSpecs);
        } else {
            onConstraintUpdatedCallback.onConstraintMet(this.matchingWorkSpecs);
        }
    }

    @Override // androidx.work.impl.constraints.ConstraintListener
    public void onConstraintChanged(Object obj) {
        this.currentValue = obj;
        updateCallback(this.callback, obj);
    }
}
