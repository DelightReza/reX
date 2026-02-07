package androidx.work.impl.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public interface WorkTagDao {
    List getTagsForWorkSpecId(String str);

    void insert(WorkTag workTag);

    void insertTags(String str, Set set);

    /* loaded from: classes3.dex */
    public static final class DefaultImpls {
        public static void insertTags(WorkTagDao workTagDao, String id, Set tags) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(tags, "tags");
            Iterator it = tags.iterator();
            while (it.hasNext()) {
                workTagDao.insert(new WorkTag((String) it.next(), id));
            }
        }
    }
}
