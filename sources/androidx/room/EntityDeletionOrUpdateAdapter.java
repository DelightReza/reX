package androidx.room;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class EntityDeletionOrUpdateAdapter extends SharedSQLiteStatement {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EntityDeletionOrUpdateAdapter(RoomDatabase database) {
        super(database);
        Intrinsics.checkNotNullParameter(database, "database");
    }
}
