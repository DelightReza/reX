package com.google.android.play.core.integrity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.play.core.integrity.model.C1185a;
import java.util.Locale;

/* loaded from: classes4.dex */
public class IntegrityServiceException extends ApiException {

    /* renamed from: a */
    private final Throwable f237a;

    IntegrityServiceException(int i, Throwable th) {
        super(new Status(i, String.format(Locale.ROOT, "Integrity API error (%d): %s.", Integer.valueOf(i), C1185a.m367a(i))));
        if (i == 0) {
            throw new IllegalArgumentException("ErrorCode should not be 0.");
        }
        this.f237a = th;
    }

    @Override // java.lang.Throwable
    public final synchronized Throwable getCause() {
        return this.f237a;
    }

    public int getErrorCode() {
        return super.getStatusCode();
    }
}
