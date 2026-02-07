package androidx.camera.core.impl.utils;

import androidx.arch.core.util.Function;

/* loaded from: classes3.dex */
public final class RedirectableLiveData extends MappingRedirectableLiveData {
    private final Object initialValue;

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object _init_$lambda$0(Object obj) {
        return obj;
    }

    public RedirectableLiveData(Object obj) {
        super(obj, new Function() { // from class: androidx.camera.core.impl.utils.RedirectableLiveData$$ExternalSyntheticLambda0
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj2) {
                return RedirectableLiveData._init_$lambda$0(obj2);
            }
        });
        this.initialValue = obj;
    }
}
