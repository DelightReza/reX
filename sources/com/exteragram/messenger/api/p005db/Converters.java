package com.exteragram.messenger.api.p005db;

import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.api.dto.NowPlayingInfoDTO;
import com.google.gson.Gson;

/* loaded from: classes.dex */
public final class Converters {
    private final Gson gson = new Gson();

    public final String fromBadgeDTO(BadgeDTO badgeDTO) {
        return this.gson.toJson(badgeDTO);
    }

    public final BadgeDTO toBadgeDTO(String str) {
        return (BadgeDTO) this.gson.fromJson(str, BadgeDTO.class);
    }

    public final String fromNowPlayingInfoDTO(NowPlayingInfoDTO nowPlayingInfoDTO) {
        return this.gson.toJson(nowPlayingInfoDTO);
    }

    public final NowPlayingInfoDTO toNowPlayingInfoDTO(String str) {
        return (NowPlayingInfoDTO) this.gson.fromJson(str, NowPlayingInfoDTO.class);
    }
}
