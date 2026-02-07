package androidx.work.impl.utils;

import android.os.Build;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.impl.Scheduler;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class EnqueueUtilsKt {
    public static final WorkSpec tryDelegateConstrainedWorkSpec(WorkSpec workSpec) throws Throwable {
        Intrinsics.checkNotNullParameter(workSpec, "workSpec");
        Constraints constraints = workSpec.constraints;
        String str = workSpec.workerClassName;
        if (Intrinsics.areEqual(str, ConstraintTrackingWorker.class.getName()) || !(constraints.requiresBatteryNotLow() || constraints.requiresStorageNotLow())) {
            return workSpec;
        }
        Data dataBuild = new Data.Builder().putAll(workSpec.input).putString("androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME", str).build();
        Intrinsics.checkNotNullExpressionValue(dataBuild, "Builder().putAll(workSpe…ame)\n            .build()");
        String name = ConstraintTrackingWorker.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        return WorkSpec.copy$default(workSpec, null, null, name, null, dataBuild, null, 0L, 0L, 0L, null, 0, null, 0L, 0L, 0L, 0L, false, null, 0, 0, 1048555, null);
    }

    public static final WorkSpec wrapInConstraintTrackingWorkerIfNeeded(List schedulers, WorkSpec workSpec) {
        Intrinsics.checkNotNullParameter(schedulers, "schedulers");
        Intrinsics.checkNotNullParameter(workSpec, "workSpec");
        int i = Build.VERSION.SDK_INT;
        if (23 > i || i >= 26) {
            return (i > 22 || !usesScheduler(schedulers, "androidx.work.impl.background.gcm.GcmScheduler")) ? workSpec : tryDelegateConstrainedWorkSpec(workSpec);
        }
        return tryDelegateConstrainedWorkSpec(workSpec);
    }

    private static final boolean usesScheduler(List list, String str) throws ClassNotFoundException {
        Class<?> cls;
        List list2;
        try {
            cls = Class.forName(str);
            list2 = list;
        } catch (ClassNotFoundException unused) {
        }
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            if (cls.isAssignableFrom(((Scheduler) it.next()).getClass())) {
                return true;
            }
        }
        return false;
    }
}
