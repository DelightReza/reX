package com.google.android.play.core.integrity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.play.core.integrity.model.C1186b;
import java.util.Locale;

/* loaded from: classes4.dex */
public class StandardIntegrityException extends ApiException {

    /* renamed from: a */
    private final Throwable f238a;

    StandardIntegrityException(int i, Throwable th) {
        super(new Status(i, String.format(Locale.ROOT, "Standard Integrity API error (%d): %s.", Integer.valueOf(i), C1186b.m368a(i))));
        if (i == 0) {
            throw new IllegalArgumentException("ErrorCode should not be 0.");
        }
        this.f238a = th;
    }

    @Override // java.lang.Throwable
    public final synchronized Throwable getCause() {
        return this.f238a;
    }

    public int getErrorCode() {
        return super.getStatusCode();
    }
}
