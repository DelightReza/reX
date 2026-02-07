package okhttp3.internal.http;

import okhttp3.Interceptor;
import okhttp3.internal.connection.Exchange;

/* loaded from: classes.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    private final boolean shouldIgnoreAndWaitForRealResponse(int i, Exchange exchange) {
        if (i == 100) {
            return true;
        }
        return 102 <= i && i < 200;
    }

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00e4 A[Catch: IOException -> 0x00b7, TryCatch #2 {IOException -> 0x00b7, blocks: (B:39:0x00a9, B:41:0x00b2, B:44:0x00ba, B:45:0x00de, B:47:0x00e4, B:49:0x00ed, B:50:0x00f0, B:51:0x0115, B:55:0x0120, B:57:0x013e, B:59:0x014c, B:66:0x0162, B:69:0x0171, B:70:0x0197, B:61:0x0157, B:56:0x0125), top: B:81:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0125 A[Catch: IOException -> 0x00b7, TryCatch #2 {IOException -> 0x00b7, blocks: (B:39:0x00a9, B:41:0x00b2, B:44:0x00ba, B:45:0x00de, B:47:0x00e4, B:49:0x00ed, B:50:0x00f0, B:51:0x0115, B:55:0x0120, B:57:0x013e, B:59:0x014c, B:66:0x0162, B:69:0x0171, B:70:0x0197, B:61:0x0157, B:56:0x0125), top: B:81:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0157 A[Catch: IOException -> 0x00b7, TryCatch #2 {IOException -> 0x00b7, blocks: (B:39:0x00a9, B:41:0x00b2, B:44:0x00ba, B:45:0x00de, B:47:0x00e4, B:49:0x00ed, B:50:0x00f0, B:51:0x0115, B:55:0x0120, B:57:0x013e, B:59:0x014c, B:66:0x0162, B:69:0x0171, B:70:0x0197, B:61:0x0157, B:56:0x0125), top: B:81:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00a9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v19, types: [boolean] */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32 */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public okhttp3.Response intercept(okhttp3.Interceptor.Chain r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 417
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.CallServerInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }
}
