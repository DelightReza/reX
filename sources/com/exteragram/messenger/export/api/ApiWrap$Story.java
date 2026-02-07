package com.exteragram.messenger.export.api;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ApiWrap$Story {
    public ArrayList caption;
    public ApiWrap$Media media;

    /* renamed from: id */
    public int f159id = 0;
    public int date = 0;
    public int expires = 0;
    public boolean pinned = false;

    public ApiWrap$File file() {
        return this.media.getFile();
    }

    public ApiWrap$Image thumb() {
        return this.media.getThumb();
    }
}
