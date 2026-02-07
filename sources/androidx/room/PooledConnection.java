package androidx.room;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface PooledConnection {
    Object usePrepared(String str, Function1 function1, Continuation continuation);
}
