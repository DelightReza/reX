package kotlin.time;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* loaded from: classes.dex */
public abstract class Duration implements Comparable {
    public static final Companion Companion = new Companion(null);
    private static final long ZERO = m2973constructorimpl(0);
    private static final long INFINITE = DurationKt.durationOfMillis(4611686018427387903L);
    private static final long NEG_INFINITE = DurationKt.durationOfMillis(-4611686018427387903L);

    /* renamed from: getValue-impl, reason: not valid java name */
    private static final long m2977getValueimpl(long j) {
        return j >> 1;
    }

    /* renamed from: isInMillis-impl, reason: not valid java name */
    private static final boolean m2979isInMillisimpl(long j) {
        return (((int) j) & 1) == 1;
    }

    /* renamed from: isInNanos-impl, reason: not valid java name */
    private static final boolean m2980isInNanosimpl(long j) {
        return (((int) j) & 1) == 0;
    }

    /* renamed from: isPositive-impl, reason: not valid java name */
    public static final boolean m2982isPositiveimpl(long j) {
        return j > 0;
    }

    /* renamed from: getStorageUnit-impl, reason: not valid java name */
    private static final DurationUnit m2976getStorageUnitimpl(long j) {
        return m2980isInNanosimpl(j) ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2973constructorimpl(long j) {
        if (!DurationJvmKt.getDurationAssertionsEnabled()) {
            return j;
        }
        if (m2980isInNanosimpl(j)) {
            long jM2977getValueimpl = m2977getValueimpl(j);
            if (-4611686018426999999L <= jM2977getValueimpl && jM2977getValueimpl < 4611686018427000000L) {
                return j;
            }
            throw new AssertionError(m2977getValueimpl(j) + " ns is out of nanoseconds range");
        }
        long jM2977getValueimpl2 = m2977getValueimpl(j);
        if (-4611686018427387903L > jM2977getValueimpl2 || jM2977getValueimpl2 >= 4611686018427387904L) {
            throw new AssertionError(m2977getValueimpl(j) + " ms is out of milliseconds range");
        }
        long jM2977getValueimpl3 = m2977getValueimpl(j);
        if (-4611686018426L > jM2977getValueimpl3 || jM2977getValueimpl3 >= 4611686018427L) {
            return j;
        }
        throw new AssertionError(m2977getValueimpl(j) + " ms is denormalized");
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final long m2983plusLRDsOJo(long j, long j2) {
        if (m2981isInfiniteimpl(j)) {
            if (m2978isFiniteimpl(j2) || (j2 ^ j) >= 0) {
                return j;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        }
        if (m2981isInfiniteimpl(j2)) {
            return j2;
        }
        if ((((int) j) & 1) == (((int) j2) & 1)) {
            long jM2977getValueimpl = m2977getValueimpl(j) + m2977getValueimpl(j2);
            return m2980isInNanosimpl(j) ? DurationKt.durationOfNanosNormalized(jM2977getValueimpl) : DurationKt.durationOfMillisNormalized(jM2977getValueimpl);
        }
        if (m2979isInMillisimpl(j)) {
            return m2972addValuesMixedRangesUwyO8pc(j, m2977getValueimpl(j), m2977getValueimpl(j2));
        }
        return m2972addValuesMixedRangesUwyO8pc(j, m2977getValueimpl(j2), m2977getValueimpl(j));
    }

    /* renamed from: addValuesMixedRanges-UwyO8pc, reason: not valid java name */
    private static final long m2972addValuesMixedRangesUwyO8pc(long j, long j2, long j3) {
        long jNanosToMillis = DurationKt.nanosToMillis(j3);
        long j4 = j2 + jNanosToMillis;
        if (-4611686018426L > j4 || j4 >= 4611686018427L) {
            return DurationKt.durationOfMillis(RangesKt.coerceIn(j4, -4611686018427387903L, 4611686018427387903L));
        }
        return DurationKt.durationOfNanos(DurationKt.millisToNanos(j4) + (j3 - DurationKt.millisToNanos(jNanosToMillis)));
    }

    /* renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m2981isInfiniteimpl(long j) {
        return j == INFINITE || j == NEG_INFINITE;
    }

    /* renamed from: isFinite-impl, reason: not valid java name */
    public static final boolean m2978isFiniteimpl(long j) {
        return !m2981isInfiniteimpl(j);
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2984toLongimpl(long j, DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j == INFINITE) {
            return Long.MAX_VALUE;
        }
        if (j == NEG_INFINITE) {
            return Long.MIN_VALUE;
        }
        return DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(m2977getValueimpl(j), m2976getStorageUnitimpl(j), unit);
    }

    /* renamed from: getInWholeSeconds-impl, reason: not valid java name */
    public static final long m2975getInWholeSecondsimpl(long j) {
        return m2984toLongimpl(j, DurationUnit.SECONDS);
    }

    /* renamed from: getInWholeMilliseconds-impl, reason: not valid java name */
    public static final long m2974getInWholeMillisecondsimpl(long j) {
        return (m2979isInMillisimpl(j) && m2978isFiniteimpl(j)) ? m2977getValueimpl(j) : m2984toLongimpl(j, DurationUnit.MILLISECONDS);
    }
}
