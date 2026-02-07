package com.exteragram.messenger.p003ai.network;

/* loaded from: classes3.dex */
public interface GenerationCallback {
    void onChunk(String str);

    void onError(int i, String str);

    void onResponse(String str);
}
