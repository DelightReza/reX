package com.yandex.mapkit.tiles;

import com.yandex.mapkit.TileId;
import com.yandex.mapkit.Version;
import java.util.Map;

/* loaded from: classes4.dex */
public interface UrlProvider {
    String formatUrl(TileId tileId, Version version, Map<String, String> map);
}
