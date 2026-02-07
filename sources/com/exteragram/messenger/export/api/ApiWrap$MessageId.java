package com.exteragram.messenger.export.api;

import p017j$.util.Objects;

/* loaded from: classes3.dex */
public class ApiWrap$MessageId {
    public String didAndMsgId = "";

    public int hashCode() {
        return this.didAndMsgId.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ApiWrap$MessageId) {
            return Objects.equals(((ApiWrap$MessageId) obj).didAndMsgId, this.didAndMsgId);
        }
        return false;
    }
}
