package androidx.room.util;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
final class ForeignKeyWithSequence implements Comparable {
    private final String from;

    /* renamed from: id */
    private final int f45id;
    private final int sequence;

    /* renamed from: to */
    private final String f46to;

    public ForeignKeyWithSequence(int i, int i2, String from, String to) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        this.f45id = i;
        this.sequence = i2;
        this.from = from;
        this.f46to = to;
    }

    public final int getId() {
        return this.f45id;
    }

    public final String getFrom() {
        return this.from;
    }

    public final String getTo() {
        return this.f46to;
    }

    @Override // java.lang.Comparable
    public int compareTo(ForeignKeyWithSequence other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int i = this.f45id - other.f45id;
        return i == 0 ? this.sequence - other.sequence : i;
    }
}
