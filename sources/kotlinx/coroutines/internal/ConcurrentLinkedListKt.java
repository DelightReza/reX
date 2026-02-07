package kotlinx.coroutines.internal;

import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public abstract class ConcurrentLinkedListKt {
    private static final Symbol CLOSED = new Symbol("CLOSED");

    public static final Object findSegmentInternal(Segment segment, long j, Function2 function2) {
        while (true) {
            if (segment.f1399id >= j && !segment.isRemoved()) {
                return SegmentOrClosed.m2991constructorimpl(segment);
            }
            Object nextOrClosed = segment.getNextOrClosed();
            if (nextOrClosed == CLOSED) {
                return SegmentOrClosed.m2991constructorimpl(CLOSED);
            }
            Segment segment2 = (Segment) ((ConcurrentLinkedListNode) nextOrClosed);
            if (segment2 == null) {
                segment2 = (Segment) function2.invoke(Long.valueOf(segment.f1399id + 1), segment);
                if (segment.trySetNext(segment2)) {
                    if (segment.isRemoved()) {
                        segment.remove();
                    }
                }
            }
            segment = segment2;
        }
    }
}
