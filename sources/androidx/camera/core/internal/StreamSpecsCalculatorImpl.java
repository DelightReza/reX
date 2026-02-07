package androidx.camera.core.internal;

import android.graphics.Rect;
import android.util.Pair;
import android.util.Range;
import android.util.Size;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.AttachedSurfaceInfo;
import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.StreamSpec;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceStreamSpecQueryResult;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.CameraUseCaseAdapter;
import androidx.camera.core.streamsharing.StreamSharing;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes3.dex */
public final class StreamSpecsCalculatorImpl implements StreamSpecsCalculator {
    private CameraDeviceSurfaceManager cameraDeviceSurfaceManager;
    private final UseCaseConfigFactory useCaseConfigFactory;

    public StreamSpecsCalculatorImpl(UseCaseConfigFactory useCaseConfigFactory, CameraDeviceSurfaceManager cameraDeviceSurfaceManager) {
        Intrinsics.checkNotNullParameter(useCaseConfigFactory, "useCaseConfigFactory");
        this.useCaseConfigFactory = useCaseConfigFactory;
        this.cameraDeviceSurfaceManager = cameraDeviceSurfaceManager;
    }

    @Override // androidx.camera.core.internal.StreamSpecsCalculator
    public void setCameraDeviceSurfaceManager(CameraDeviceSurfaceManager cameraDeviceSurfaceManager) {
        Intrinsics.checkNotNullParameter(cameraDeviceSurfaceManager, "cameraDeviceSurfaceManager");
        this.cameraDeviceSurfaceManager = cameraDeviceSurfaceManager;
    }

    @Override // androidx.camera.core.internal.StreamSpecsCalculator
    public StreamSpecQueryResult calculateSuggestedStreamSpecs(int i, CameraInfoInternal cameraInfoInternal, List newUseCases, List attachedUseCases, CameraConfig cameraConfig, int i2, Range targetFrameRate, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(cameraInfoInternal, "cameraInfoInternal");
        Intrinsics.checkNotNullParameter(newUseCases, "newUseCases");
        Intrinsics.checkNotNullParameter(attachedUseCases, "attachedUseCases");
        Intrinsics.checkNotNullParameter(cameraConfig, "cameraConfig");
        Intrinsics.checkNotNullParameter(targetFrameRate, "targetFrameRate");
        Pair pairCalculateSuggestedStreamSpecsForAttachedUseCases = calculateSuggestedStreamSpecsForAttachedUseCases(i, cameraInfoInternal, attachedUseCases);
        Object second = pairCalculateSuggestedStreamSpecsForAttachedUseCases.second;
        Intrinsics.checkNotNullExpressionValue(second, "second");
        Map configs = CameraUseCaseAdapter.getConfigs(newUseCases, cameraConfig.getUseCaseConfigFactory(), this.useCaseConfigFactory, i2, targetFrameRate);
        Intrinsics.checkNotNullExpressionValue(configs, "getConfigs(...)");
        StreamSpecQueryResult streamSpecQueryResultCalculateSuggestedStreamSpecsForNewUseCases = calculateSuggestedStreamSpecsForNewUseCases(i, cameraInfoInternal, newUseCases, (Map) second, configs, z, z2);
        Object first = pairCalculateSuggestedStreamSpecsForAttachedUseCases.first;
        Intrinsics.checkNotNullExpressionValue(first, "first");
        return new StreamSpecQueryResult(MapsKt.plus((Map) first, streamSpecQueryResultCalculateSuggestedStreamSpecsForNewUseCases.getStreamSpecs()), streamSpecQueryResultCalculateSuggestedStreamSpecsForNewUseCases.getMaxSupportedFrameRate());
    }

    private final Pair calculateSuggestedStreamSpecsForAttachedUseCases(int i, CameraInfoInternal cameraInfoInternal, List list) {
        ArrayList arrayList = new ArrayList();
        String cameraId = cameraInfoInternal.getCameraId();
        Intrinsics.checkNotNullExpressionValue(cameraId, "getCameraId(...)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UseCase useCase = (UseCase) it.next();
            StreamSpec attachedStreamSpec = useCase.getAttachedStreamSpec();
            if (attachedStreamSpec == null) {
                throw new IllegalArgumentException("Attached stream spec cannot be null for already attached use cases.");
            }
            CameraDeviceSurfaceManager cameraDeviceSurfaceManager = this.cameraDeviceSurfaceManager;
            if (cameraDeviceSurfaceManager == null) {
                throw new IllegalStateException("Required value was null.");
            }
            int imageFormat = useCase.getImageFormat();
            Size attachedSurfaceResolution = useCase.getAttachedSurfaceResolution();
            if (attachedSurfaceResolution != null) {
                SurfaceConfig surfaceConfigTransformSurfaceConfig = cameraDeviceSurfaceManager.transformSurfaceConfig(i, cameraId, imageFormat, attachedSurfaceResolution, useCase.getCurrentConfig().getStreamUseCase());
                Intrinsics.checkNotNullExpressionValue(surfaceConfigTransformSurfaceConfig, "transformSurfaceConfig(...)");
                int imageFormat2 = useCase.getImageFormat();
                Size attachedSurfaceResolution2 = useCase.getAttachedSurfaceResolution();
                Intrinsics.checkNotNull(attachedSurfaceResolution2);
                DynamicRange dynamicRange = attachedStreamSpec.getDynamicRange();
                List captureTypes = StreamSharing.getCaptureTypes(useCase);
                Config implementationOptions = attachedStreamSpec.getImplementationOptions();
                int sessionType = useCase.getCurrentConfig().getSessionType(0);
                Range targetFrameRate = useCase.getCurrentConfig().getTargetFrameRate(StreamSpec.FRAME_RATE_RANGE_UNSPECIFIED);
                if (targetFrameRate != null) {
                    AttachedSurfaceInfo attachedSurfaceInfoCreate = AttachedSurfaceInfo.create(surfaceConfigTransformSurfaceConfig, imageFormat2, attachedSurfaceResolution2, dynamicRange, captureTypes, implementationOptions, sessionType, targetFrameRate, useCase.getCurrentConfig().isStrictFrameRateRequired());
                    Intrinsics.checkNotNullExpressionValue(attachedSurfaceInfoCreate, "create(...)");
                    arrayList.add(attachedSurfaceInfoCreate);
                    linkedHashMap2.put(attachedSurfaceInfoCreate, useCase);
                    linkedHashMap.put(useCase, attachedStreamSpec);
                } else {
                    throw new IllegalArgumentException("Required value was null.");
                }
            } else {
                throw new IllegalArgumentException("Attached surface resolution cannot be null for already attached use cases.");
            }
        }
        return new Pair(linkedHashMap, linkedHashMap2);
    }

    private final StreamSpecQueryResult calculateSuggestedStreamSpecsForNewUseCases(int i, CameraInfoInternal cameraInfoInternal, List list, Map map, Map map2, boolean z, boolean z2) {
        int iComponent3;
        Rect sensorRect;
        String cameraId = cameraInfoInternal.getCameraId();
        Intrinsics.checkNotNullExpressionValue(cameraId, "getCameraId(...)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (list.isEmpty()) {
            iComponent3 = ConnectionsManager.DEFAULT_DATACENTER_ID;
        } else {
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            LinkedHashMap linkedHashMap3 = new LinkedHashMap();
            try {
                sensorRect = cameraInfoInternal.getSensorRect();
            } catch (NullPointerException unused) {
                sensorRect = null;
            }
            SupportedOutputSizesSorter supportedOutputSizesSorter = new SupportedOutputSizesSorter(cameraInfoInternal, sensorRect != null ? TransformUtils.rectToSize(sensorRect) : null);
            Iterator it = list.iterator();
            while (true) {
                boolean z3 = false;
                while (it.hasNext()) {
                    UseCase useCase = (UseCase) it.next();
                    Object obj = map2.get(useCase);
                    if (obj == null) {
                        throw new IllegalArgumentException("Required value was null.");
                    }
                    CameraUseCaseAdapter.ConfigPair configPair = (CameraUseCaseAdapter.ConfigPair) obj;
                    UseCaseConfig useCaseConfigMergeConfigs = useCase.mergeConfigs(cameraInfoInternal, configPair.mExtendedConfig, configPair.mCameraConfig);
                    Intrinsics.checkNotNullExpressionValue(useCaseConfigMergeConfigs, "mergeConfigs(...)");
                    linkedHashMap2.put(useCaseConfigMergeConfigs, useCase);
                    List sortedSupportedOutputSizes = supportedOutputSizesSorter.getSortedSupportedOutputSizes(useCaseConfigMergeConfigs);
                    Intrinsics.checkNotNullExpressionValue(sortedSupportedOutputSizes, "getSortedSupportedOutputSizes(...)");
                    linkedHashMap3.put(useCaseConfigMergeConfigs, sortedSupportedOutputSizes);
                    if ((useCase instanceof Preview) || (useCase instanceof StreamSharing)) {
                        if (useCaseConfigMergeConfigs.getPreviewStabilizationMode() == 2) {
                            z3 = true;
                        }
                    }
                }
                CameraDeviceSurfaceManager cameraDeviceSurfaceManager = this.cameraDeviceSurfaceManager;
                if (cameraDeviceSurfaceManager == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                SurfaceStreamSpecQueryResult suggestedStreamSpecs = cameraDeviceSurfaceManager.getSuggestedStreamSpecs(i, cameraId, new ArrayList(map.keySet()), linkedHashMap3, z3, CameraUseCaseAdapter.hasVideoCapture(list), z, z2);
                Intrinsics.checkNotNullExpressionValue(suggestedStreamSpecs, "getSuggestedStreamSpecs(...)");
                Map mapComponent1 = suggestedStreamSpecs.component1();
                Map mapComponent2 = suggestedStreamSpecs.component2();
                iComponent3 = suggestedStreamSpecs.component3();
                for (Map.Entry entry : linkedHashMap2.entrySet()) {
                    Object value = entry.getValue();
                    Object obj2 = mapComponent1.get(entry.getKey());
                    if (obj2 != null) {
                        linkedHashMap.put(value, obj2);
                    } else {
                        throw new IllegalArgumentException("Required value was null.");
                    }
                }
                for (Map.Entry entry2 : mapComponent2.entrySet()) {
                    if (map.containsKey(entry2.getKey())) {
                        Object obj3 = map.get(entry2.getKey());
                        if (obj3 != null) {
                            linkedHashMap.put(obj3, entry2.getValue());
                        } else {
                            throw new IllegalArgumentException("Required value was null.");
                        }
                    }
                }
            }
        }
        return new StreamSpecQueryResult(linkedHashMap, iComponent3);
    }
}
