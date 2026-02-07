package org.telegram.p023ui.web;

import android.os.AsyncTask;
import androidx.annotation.Keep;
import java.io.File;
import org.telegram.messenger.Utilities;

@Keep
/* loaded from: classes6.dex */
public class HttpGetFileTask extends AsyncTask<String, Void, File> {
    private Utilities.Callback<File> doneCallback;
    private Exception exception;
    private File file;
    private long max_size = -1;
    private String overrideExt;
    private Utilities.Callback<Float> progressCallback;

    public HttpGetFileTask(Utilities.Callback<File> callback, Utilities.Callback<Float> callback2) {
        this.doneCallback = callback;
        this.progressCallback = callback2;
    }

    @Keep
    public HttpGetFileTask setOverrideExtension(String str) {
        this.overrideExt = str;
        return this;
    }

    @Keep
    public HttpGetFileTask setDestFile(File file) {
        this.file = file;
        return this;
    }

    @Keep
    public HttpGetFileTask setMaxSize(long j) {
        this.max_size = j;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ec, code lost:
    
        r18.file.delete();
     */
    /* JADX WARN: Removed duplicated region for block: B:140:0x00dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0126 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0085 A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a7 A[Catch: Exception -> 0x0041, TryCatch #13 {Exception -> 0x0041, blocks: (B:9:0x0013, B:11:0x0025, B:14:0x0046, B:18:0x0055, B:20:0x005e, B:24:0x0068, B:33:0x007f, B:35:0x0085, B:37:0x008f, B:41:0x0099, B:43:0x00a0, B:45:0x00a3, B:47:0x00a7, B:51:0x00b8, B:50:0x00ac, B:52:0x00c0, B:36:0x008a, B:19:0x005a), top: B:144:0x0013 }] */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.io.File doInBackground(java.lang.String... r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 399
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.web.HttpGetFileTask.doInBackground(java.lang.String[]):java.io.File");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doInBackground$0(float f) {
        this.progressCallback.run(Float.valueOf(f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doInBackground$1() {
        this.progressCallback.run(Float.valueOf(1.0f));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(File file) {
        Utilities.Callback<File> callback = this.doneCallback;
        if (callback != null) {
            if (this.exception == null) {
                callback.run(file);
            } else {
                callback.run(null);
            }
        }
    }
}
