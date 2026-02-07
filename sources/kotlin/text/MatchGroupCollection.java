package kotlin.text;

import java.util.Collection;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes.dex */
public interface MatchGroupCollection extends Collection, KMappedMarker {
    MatchGroup get(int i);
}
