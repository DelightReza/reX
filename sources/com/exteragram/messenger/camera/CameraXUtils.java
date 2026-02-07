package com.exteragram.messenger.camera;

import android.util.Size;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.DynamicRange;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.Quality;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.Recorder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import org.telegram.messenger.SharedConfig;
import p017j$.util.Collection;
import p017j$.util.Optional;
import p017j$.util.function.BiFunction$CC;
import p017j$.util.function.Function$CC;
import p017j$.util.function.IntPredicate$CC;
import p017j$.util.function.Predicate$CC;
import p017j$.util.stream.Collectors;

/* loaded from: classes3.dex */
public abstract class CameraXUtils {
    private static final Map videoSizesCache = new HashMap();

    public static Map getAvailableVideoSizes(CameraSelector cameraSelector, ProcessCameraProvider processCameraProvider) {
        Map map = videoSizesCache;
        if (map.containsKey(cameraSelector)) {
            return (Map) map.get(cameraSelector);
        }
        Map map2 = (Map) Collection.EL.stream(cameraSelector.filter(processCameraProvider.getAvailableCameraInfos())).findFirst().map(new Function() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda5
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CameraXUtils.$r8$lambda$YgU9PrLwhel1TIHy4WCo8rzgSYA((CameraInfo) obj);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).orElse(new LinkedHashMap());
        map.put(cameraSelector, map2);
        return map2;
    }

    public static /* synthetic */ LinkedHashMap $r8$lambda$YgU9PrLwhel1TIHy4WCo8rzgSYA(final CameraInfo cameraInfo) {
        return (LinkedHashMap) Collection.EL.stream(Recorder.getVideoCapabilities(cameraInfo).getSupportedQualities(DynamicRange.UNSPECIFIED)).collect(Collectors.toMap(Function$CC.identity(), new Function() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda6
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CameraXUtils.$r8$lambda$UGR0Y59aaJfrqW6a34UQskGgjco(cameraInfo, (Quality) obj);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }, new BinaryOperator() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda7
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return CameraXUtils.$r8$lambda$CCx8CXLCG_nkv_ID17qO7WDKzZ8((Size) obj, (Size) obj2);
            }
        }, new Supplier() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return new LinkedHashMap();
            }
        }));
    }

    public static /* synthetic */ Size $r8$lambda$UGR0Y59aaJfrqW6a34UQskGgjco(CameraInfo cameraInfo, Quality quality) {
        return (Size) Optional.ofNullable(QualitySelector.getResolution(cameraInfo, quality)).orElse(new Size(0, 0));
    }

    public static /* synthetic */ Size $r8$lambda$CCx8CXLCG_nkv_ID17qO7WDKzZ8(Size size, Size size2) {
        throw new IllegalStateException(String.format("Duplicate key %s", size));
    }

    public static Quality getVideoQuality(ProcessCameraProvider processCameraProvider, CameraSelector cameraSelector) {
        Map availableVideoSizes = getAvailableVideoSizes(cameraSelector, processCameraProvider);
        if (availableVideoSizes.isEmpty()) {
            return Quality.f12SD;
        }
        final int suggestedResolution = getSuggestedResolution();
        final int iOrElse = Collection.EL.stream(availableVideoSizes.values()).mapToInt(new ToIntFunction() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Size) obj).getHeight();
            }
        }).filter(new IntPredicate() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda1
            public /* synthetic */ IntPredicate and(IntPredicate intPredicate) {
                return IntPredicate$CC.$default$and(this, intPredicate);
            }

            public /* synthetic */ IntPredicate negate() {
                return IntPredicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ IntPredicate m191or(IntPredicate intPredicate) {
                return IntPredicate$CC.$default$or(this, intPredicate);
            }

            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return CameraXUtils.$r8$lambda$v5T8IYrR6jKONW1q2y97jgGDfxY(suggestedResolution, i);
            }
        }).max().orElse(Collection.EL.stream(availableVideoSizes.values()).mapToInt(new ToIntFunction() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Size) obj).getHeight();
            }
        }).filter(new IntPredicate() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda2
            public /* synthetic */ IntPredicate and(IntPredicate intPredicate) {
                return IntPredicate$CC.$default$and(this, intPredicate);
            }

            public /* synthetic */ IntPredicate negate() {
                return IntPredicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ IntPredicate m192or(IntPredicate intPredicate) {
                return IntPredicate$CC.$default$or(this, intPredicate);
            }

            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return CameraXUtils.$r8$lambda$IL4BtZ8H34viRBsg_H8FBwjzn_U(i);
            }
        }).min().orElse(0));
        if (iOrElse == 0) {
            return Quality.LOWEST;
        }
        return (Quality) Collection.EL.stream(availableVideoSizes.entrySet()).filter(new Predicate() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda3
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m193or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CameraXUtils.m1684$r8$lambda$jjSLxN_hmapvoQNaEKBm8QpD3E(iOrElse, (Map.Entry) obj);
            }
        }).map(new Function() { // from class: com.exteragram.messenger.camera.CameraXUtils$$ExternalSyntheticLambda4
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Quality) ((Map.Entry) obj).getKey();
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).findFirst().orElse(Quality.HIGHEST);
    }

    public static /* synthetic */ boolean $r8$lambda$v5T8IYrR6jKONW1q2y97jgGDfxY(int i, int i2) {
        return i2 > 0 && i2 <= i;
    }

    public static /* synthetic */ boolean $r8$lambda$IL4BtZ8H34viRBsg_H8FBwjzn_U(int i) {
        return i > 0;
    }

    /* renamed from: $r8$lambda$jjSLxN-_hmapvoQNaEKBm8QpD3E, reason: not valid java name */
    public static /* synthetic */ boolean m1684$r8$lambda$jjSLxN_hmapvoQNaEKBm8QpD3E(int i, Map.Entry entry) {
        return ((Size) entry.getValue()).getHeight() == i;
    }

    private static int getSuggestedResolution() {
        int devicePerformanceClass = SharedConfig.getDevicePerformanceClass();
        if (devicePerformanceClass == 0) {
            return 720;
        }
        if (devicePerformanceClass != 1) {
            return devicePerformanceClass != 2 ? 720 : 2160;
        }
        return 1080;
    }
}
