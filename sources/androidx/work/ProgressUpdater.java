package androidx.work;

import android.content.Context;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

/* loaded from: classes3.dex */
public interface ProgressUpdater {
    ListenableFuture updateProgress(Context context, UUID uuid, Data data);
}
