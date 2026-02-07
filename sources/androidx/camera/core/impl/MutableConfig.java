package androidx.camera.core.impl;

import androidx.camera.core.impl.Config;

/* loaded from: classes3.dex */
public interface MutableConfig extends Config {
    void insertOption(Config.Option option, Config.OptionPriority optionPriority, Object obj);

    void insertOption(Config.Option option, Object obj);
}
