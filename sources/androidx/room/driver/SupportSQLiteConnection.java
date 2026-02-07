package androidx.room.driver;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.p001db.SupportSQLiteDatabase;
import java.io.IOException;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SupportSQLiteConnection implements SQLiteConnection {

    /* renamed from: db */
    private final SupportSQLiteDatabase f43db;

    public SupportSQLiteConnection(SupportSQLiteDatabase db) {
        Intrinsics.checkNotNullParameter(db, "db");
        this.f43db = db;
    }

    public final SupportSQLiteDatabase getDb() {
        return this.f43db;
    }

    @Override // androidx.sqlite.SQLiteConnection
    public SupportSQLiteStatement prepare(String sql) {
        Intrinsics.checkNotNullParameter(sql, "sql");
        return SupportSQLiteStatement.Companion.create(this.f43db, sql);
    }

    @Override // androidx.sqlite.SQLiteConnection, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f43db.close();
    }
}
