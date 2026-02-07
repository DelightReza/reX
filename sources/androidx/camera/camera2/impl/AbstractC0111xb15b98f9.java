package androidx.camera.camera2.impl;

import android.content.ContentProviderClient;
import android.content.res.TypedArray;
import android.drm.DrmManagerClient;
import android.media.MediaDrm;
import android.media.MediaMetadataRetriever;
import java.util.concurrent.ExecutorService;

/* renamed from: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$$ExternalSyntheticAutoCloseableDispatcher8 */
/* loaded from: classes.dex */
public abstract /* synthetic */ class AbstractC0111xb15b98f9 {
    /* renamed from: m */
    public static /* synthetic */ void m15m(Object obj) throws Exception {
        if (obj instanceof AutoCloseable) {
            ((AutoCloseable) obj).close();
            return;
        }
        if (obj instanceof ExecutorService) {
            AbstractC0112x6d9f4bf.m16m((ExecutorService) obj);
            return;
        }
        if (obj instanceof TypedArray) {
            ((TypedArray) obj).recycle();
            return;
        }
        if (obj instanceof MediaMetadataRetriever) {
            ((MediaMetadataRetriever) obj).release();
            return;
        }
        if (obj instanceof MediaDrm) {
            ((MediaDrm) obj).release();
            return;
        }
        if (obj instanceof DrmManagerClient) {
            ((DrmManagerClient) obj).release();
        } else if (obj instanceof ContentProviderClient) {
            ((ContentProviderClient) obj).release();
        } else {
            FeatureCombinationQueryImpl$$ExternalSyntheticThrowIAE10.m17m(obj);
        }
    }
}
