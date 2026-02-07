package androidx.camera.video;

import android.util.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import p017j$.util.DesugarCollections;

/* loaded from: classes3.dex */
public abstract class Quality {
    public static final Quality FHD;

    /* renamed from: HD */
    public static final Quality f11HD;
    public static final Quality HIGHEST;
    public static final Quality LOWEST;
    static final Quality NONE;
    private static final Set QUALITIES;
    private static final List QUALITIES_ORDER_BY_SIZE;

    /* renamed from: SD */
    public static final Quality f12SD;
    public static final Quality UHD;

    private Quality() {
    }

    static {
        ConstantQuality constantQualityM75of = ConstantQuality.m75of(4, 2002, "SD", DesugarCollections.unmodifiableList(Arrays.asList(new Size(720, 480), new Size(640, 480))));
        f12SD = constantQualityM75of;
        ConstantQuality constantQualityM75of2 = ConstantQuality.m75of(5, 2003, "HD", Collections.singletonList(new Size(1280, 720)));
        f11HD = constantQualityM75of2;
        ConstantQuality constantQualityM75of3 = ConstantQuality.m75of(6, 2004, "FHD", Collections.singletonList(new Size(1920, 1080)));
        FHD = constantQualityM75of3;
        ConstantQuality constantQualityM75of4 = ConstantQuality.m75of(8, 2005, "UHD", Collections.singletonList(new Size(3840, 2160)));
        UHD = constantQualityM75of4;
        List list = Collections.EMPTY_LIST;
        ConstantQuality constantQualityM75of5 = ConstantQuality.m75of(0, 2000, "LOWEST", list);
        LOWEST = constantQualityM75of5;
        ConstantQuality constantQualityM75of6 = ConstantQuality.m75of(1, 2001, "HIGHEST", list);
        HIGHEST = constantQualityM75of6;
        NONE = ConstantQuality.m75of(-1, -1, "NONE", list);
        QUALITIES = new HashSet(Arrays.asList(constantQualityM75of5, constantQualityM75of6, constantQualityM75of, constantQualityM75of2, constantQualityM75of3, constantQualityM75of4));
        QUALITIES_ORDER_BY_SIZE = Arrays.asList(constantQualityM75of4, constantQualityM75of3, constantQualityM75of2, constantQualityM75of);
    }

    static boolean containsQuality(Quality quality) {
        return QUALITIES.contains(quality);
    }

    public static List getSortedQualities() {
        return new ArrayList(QUALITIES_ORDER_BY_SIZE);
    }

    public static abstract class ConstantQuality extends Quality {
        abstract int getHighSpeedValue();

        public abstract String getName();

        public abstract List getTypicalSizes();

        abstract int getValue();

        public ConstantQuality() {
            super();
        }

        /* renamed from: of */
        static ConstantQuality m75of(int i, int i2, String str, List list) {
            return new AutoValue_Quality_ConstantQuality(i, i2, str, list);
        }

        public int getQualityValue(int i) {
            if (i == 1) {
                return getValue();
            }
            if (i == 2) {
                return getHighSpeedValue();
            }
            throw new AssertionError("Unknown quality source: " + i);
        }
    }
}
