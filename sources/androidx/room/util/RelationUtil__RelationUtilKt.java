package androidx.room.util;

import androidx.collection.LongSparseArray;
import androidx.room.RoomDatabase;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
abstract /* synthetic */ class RelationUtil__RelationUtilKt {
    public static final void recursiveFetchLongSparseArray(LongSparseArray map, boolean z, Function1 fetchBlock) {
        int i;
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(fetchBlock, "fetchBlock");
        LongSparseArray longSparseArray = new LongSparseArray(RoomDatabase.MAX_BIND_PARAMETER_CNT);
        int size = map.size();
        int i2 = 0;
        loop0: while (true) {
            i = 0;
            while (i2 < size) {
                if (z) {
                    longSparseArray.put(map.keyAt(i2), map.valueAt(i2));
                } else {
                    longSparseArray.put(map.keyAt(i2), null);
                }
                i2++;
                i++;
                if (i == 999) {
                    fetchBlock.invoke(longSparseArray);
                    if (!z) {
                        map.putAll(longSparseArray);
                    }
                    longSparseArray.clear();
                }
            }
            break loop0;
        }
        if (i > 0) {
            fetchBlock.invoke(longSparseArray);
            if (z) {
                return;
            }
            map.putAll(longSparseArray);
        }
    }
}
