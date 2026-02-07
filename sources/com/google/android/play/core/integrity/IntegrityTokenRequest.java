package com.google.android.play.core.integrity;

import android.net.Network;

/* loaded from: classes4.dex */
public abstract class IntegrityTokenRequest {

    public static abstract class Builder {
        public abstract IntegrityTokenRequest build();

        public abstract Builder setCloudProjectNumber(long j);

        public abstract Builder setNonce(String str);
    }

    public static Builder builder() {
        return new C1173c();
    }

    /* renamed from: a */
    public abstract Network mo330a();

    public abstract Long cloudProjectNumber();

    public abstract String nonce();
}
