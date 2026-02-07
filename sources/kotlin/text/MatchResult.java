package kotlin.text;

import java.util.List;
import kotlin.ranges.IntRange;

/* loaded from: classes.dex */
public interface MatchResult {
    List getGroupValues();

    MatchGroupCollection getGroups();

    IntRange getRange();
}
