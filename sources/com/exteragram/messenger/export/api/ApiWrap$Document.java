package com.exteragram.messenger.export.api;

import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class ApiWrap$Document {
    public int duration;
    public ApiWrap$File file;
    public String mime;
    public String name;
    public String songPerformer;
    public String songTitle;
    public TLRPC.Document sticker;
    public String stickerEmoji;
    public ApiWrap$Image thumb;

    /* renamed from: id */
    public long f154id = 0;
    public int date = 0;
    public int width = 0;
    public int height = 0;
    public boolean isSticker = false;
    public boolean isAnimated = false;
    public boolean isVideoMessage = false;
    public boolean isVoiceMessage = false;
    public boolean isVideoFile = false;
    public boolean isAudioFile = false;
    public boolean spoilered = false;
}
