package kotlinx.coroutines;

import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public abstract class CompletionStateKt {
    public static final Object toState(Object obj) {
        Throwable thM2963exceptionOrNullimpl = Result.m2963exceptionOrNullimpl(obj);
        return thM2963exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM2963exceptionOrNullimpl, false, 2, null);
    }

    public static final Object toState(Object obj, CancellableContinuation cancellableContinuation) {
        Throwable thM2963exceptionOrNullimpl = Result.m2963exceptionOrNullimpl(obj);
        return thM2963exceptionOrNullimpl == null ? obj : new CompletedExceptionally(thM2963exceptionOrNullimpl, false, 2, null);
    }

    public static final Object recoverResult(Object obj, Continuation continuation) {
        if (obj instanceof CompletedExceptionally) {
            Result.Companion companion = Result.Companion;
            return Result.m2961constructorimpl(ResultKt.createFailure(((CompletedExceptionally) obj).cause));
        }
        return Result.m2961constructorimpl(obj);
    }
}
