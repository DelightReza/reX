package androidx.camera.core.impl.utils;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class MappingRedirectableLiveData extends MediatorLiveData {
    private final Object initialValue;
    private LiveData liveDataSource;
    private final Function mapFunction;

    public MappingRedirectableLiveData(Object obj, Function mapFunction) {
        Intrinsics.checkNotNullParameter(mapFunction, "mapFunction");
        this.initialValue = obj;
        this.mapFunction = mapFunction;
    }

    public final void redirectTo(final LiveData liveDataSource) {
        Intrinsics.checkNotNullParameter(liveDataSource, "liveDataSource");
        LiveData liveData = this.liveDataSource;
        if (liveData != null) {
            Intrinsics.checkNotNull(liveData);
            super.removeSource(liveData);
        }
        this.liveDataSource = liveDataSource;
        Threads.runOnMain(new Runnable() { // from class: androidx.camera.core.impl.utils.MappingRedirectableLiveData$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MappingRedirectableLiveData.redirectTo$lambda$2(this.f$0, liveDataSource);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void redirectTo$lambda$2(final MappingRedirectableLiveData mappingRedirectableLiveData, LiveData liveData) {
        final Function1 function1 = new Function1() { // from class: androidx.camera.core.impl.utils.MappingRedirectableLiveData$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MappingRedirectableLiveData.redirectTo$lambda$2$lambda$0(this.f$0, obj);
            }
        };
        super.addSource(liveData, new Observer() { // from class: androidx.camera.core.impl.utils.MappingRedirectableLiveData$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                function1.invoke(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit redirectTo$lambda$2$lambda$0(MappingRedirectableLiveData mappingRedirectableLiveData, Object obj) {
        mappingRedirectableLiveData.setValue(mappingRedirectableLiveData.mapFunction.apply(obj));
        return Unit.INSTANCE;
    }

    @Override // androidx.lifecycle.LiveData
    public Object getValue() {
        LiveData liveData = this.liveDataSource;
        if (liveData == null) {
            return this.initialValue;
        }
        return this.mapFunction.apply(liveData.getValue());
    }
}
