package com.yandex.runtime.internal;

import java.io.File;

/* loaded from: classes4.dex */
class AvailableSpace {
    AvailableSpace() {
    }

    public static long getAvailableSpaceOnFilesystem(String str) {
        return new File(str).getUsableSpace();
    }
}
