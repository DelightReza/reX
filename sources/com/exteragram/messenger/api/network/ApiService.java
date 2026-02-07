package com.exteragram.messenger.api.network;

import com.exteragram.messenger.api.dto.NowPlayingDTO;
import com.exteragram.messenger.api.dto.ProfileDTO;
import java.util.List;
import java.util.Map;
import kotlin.coroutines.Continuation;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes.dex */
public interface ApiService {
    @GET("profiles")
    Object getAllProfiles(Continuation<? super Response<List<ProfileDTO>>> continuation);

    @GET("profiles/{userId}/now-playing")
    Object getCurrentPlayingTrack(@Path("userId") long j, Continuation<? super Response<NowPlayingDTO>> continuation);

    @GET("exchange-rates/{currency}")
    Object getExchangeRates(@Path("currency") String str, Continuation<? super Response<Map<String, Double>>> continuation);

    @GET("profiles/updates")
    Object getUpdates(@Query("since") String str, Continuation<? super Response<List<ProfileDTO>>> continuation);
}
