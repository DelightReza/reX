package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import java.util.Map;

/* loaded from: classes.dex */
public interface DataSource extends DataReader {

    /* loaded from: classes4.dex */
    public interface Factory {
        DataSource createDataSource();
    }

    void addTransferListener(TransferListener transferListener);

    void close();

    Map getResponseHeaders();

    Uri getUri();

    long open(DataSpec dataSpec);

    /* renamed from: com.google.android.exoplayer2.upstream.DataSource$-CC, reason: invalid class name */
    /* loaded from: classes4.dex */
    public abstract /* synthetic */ class CC {
    }
}
