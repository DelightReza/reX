package com.exteragram.messenger.nowplaying;

import androidx.collection.LruCache;
import com.exteragram.messenger.api.dto.NowPlayingDTO;
import com.exteragram.messenger.api.dto.NowPlayingInfoDTO;
import com.exteragram.messenger.api.p005db.DatabaseHelper;
import com.exteragram.messenger.utils.ChatUtils;
import com.exteragram.messenger.utils.network.ExteraHttpClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.internal.ArrayListSerializer;
import kotlinx.serialization.internal.PluginExceptionsKt;
import kotlinx.serialization.internal.SerializationConstructorMarker;
import kotlinx.serialization.json.Json;
import kotlinx.serialization.json.JsonBuilder;
import kotlinx.serialization.json.JsonKt;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import p017j$.util.function.Consumer$CC;

/* loaded from: classes3.dex */
public final class NowPlayingController {
    private static final OkHttpClient httpClient;
    private static final Json jsonParser;
    public static final NowPlayingController INSTANCE = new NowPlayingController();
    private static final Regex ARTISTS_SPLITTER = new Regex("(?i)\\s*(?:,|&|feat\\.?|ft\\.?)\\s*");
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default(null, 1, null).plus(Dispatchers.getMain()));
    private static final LruCache itunesCache = new LruCache(50);

    /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$fetchItunesTrack$1 */
    static final class C08561 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C08561(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= TLObject.FLAG_31;
            return NowPlayingController.this.fetchItunesTrack(null, null, this);
        }
    }

    /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$processSavedMusic$1 */
    static final class C08611 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        C08611(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= TLObject.FLAG_31;
            return NowPlayingController.this.processSavedMusic(null, this);
        }
    }

    private NowPlayingController() {
    }

    static {
        OkHttpClient.Builder builderNewBuilder = ExteraHttpClient.INSTANCE.getClient().newBuilder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        httpClient = builderNewBuilder.connectTimeout(5L, timeUnit).readTimeout(5L, timeUnit).writeTimeout(5L, timeUnit).build();
        jsonParser = JsonKt.Json$default(null, new Function1() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NowPlayingController.jsonParser$lambda$9((JsonBuilder) obj);
            }
        }, 1, null);
    }

    /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1 */
    static final class C08571 extends SuspendLambda implements Function2 {
        final /* synthetic */ BiConsumer $callback;
        final /* synthetic */ boolean $checkApi;
        final /* synthetic */ TLRPC.Document $savedMusic;
        final /* synthetic */ long $userId;
        long J$0;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C08571(boolean z, long j, TLRPC.Document document, BiConsumer biConsumer, Continuation continuation) {
            super(2, continuation);
            this.$checkApi = z;
            this.$userId = j;
            this.$savedMusic = document;
            this.$callback = biConsumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C08571 c08571 = new C08571(this.$checkApi, this.$userId, this.$savedMusic, this.$callback, continuation);
            c08571.L$0 = obj;
            return c08571;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C08571) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a2, code lost:
        
            if (kotlinx.coroutines.BuildersKt.withContext(r15, r6, r14) == r0) goto L29;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r15) throws java.lang.Throwable {
            /*
                r14 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r14.label
                r2 = 3
                r3 = 2
                r4 = 1
                r5 = 0
                if (r1 == 0) goto L2f
                if (r1 == r4) goto L25
                if (r1 == r3) goto L1f
                if (r1 != r2) goto L17
                kotlin.ResultKt.throwOnFailure(r15)
                goto La5
            L17:
                java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r15.<init>(r0)
                throw r15
            L1f:
                long r3 = r14.J$0
                kotlin.ResultKt.throwOnFailure(r15)
                goto L8a
            L25:
                long r6 = r14.J$0
                java.lang.Object r1 = r14.L$0
                kotlinx.coroutines.Deferred r1 = (kotlinx.coroutines.Deferred) r1
                kotlin.ResultKt.throwOnFailure(r15)
                goto L6c
            L2f:
                kotlin.ResultKt.throwOnFailure(r15)
                java.lang.Object r15 = r14.L$0
                r6 = r15
                kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
                long r12 = java.lang.System.currentTimeMillis()
                kotlinx.coroutines.CoroutineDispatcher r7 = kotlinx.coroutines.Dispatchers.getIO()
                com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$liveTrackDeferred$1 r9 = new com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$liveTrackDeferred$1
                boolean r15 = r14.$checkApi
                long r10 = r14.$userId
                r9.<init>(r15, r10, r5)
                r10 = 2
                r11 = 0
                r8 = 0
                kotlinx.coroutines.Deferred r15 = kotlinx.coroutines.BuildersKt.async$default(r6, r7, r8, r9, r10, r11)
                kotlinx.coroutines.CoroutineDispatcher r7 = kotlinx.coroutines.Dispatchers.getIO()
                com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$savedTrackDeferred$1 r9 = new com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$savedTrackDeferred$1
                org.telegram.tgnet.TLRPC$Document r1 = r14.$savedMusic
                r9.<init>(r1, r5)
                kotlinx.coroutines.Deferred r1 = kotlinx.coroutines.BuildersKt.async$default(r6, r7, r8, r9, r10, r11)
                r14.L$0 = r1
                r14.J$0 = r12
                r14.label = r4
                java.lang.Object r15 = r15.await(r14)
                if (r15 != r0) goto L6b
                goto La4
            L6b:
                r6 = r12
            L6c:
                com.exteragram.messenger.api.dto.NowPlayingDTO r15 = (com.exteragram.messenger.api.dto.NowPlayingDTO) r15
                if (r15 == 0) goto L7c
                boolean r8 = r15.isPlaying()
                if (r8 == 0) goto L7c
                kotlinx.coroutines.Job.DefaultImpls.cancel$default(r1, r5, r4, r5)
                r9 = r6
            L7a:
                r8 = r15
                goto L8e
            L7c:
                r14.L$0 = r5
                r14.J$0 = r6
                r14.label = r3
                java.lang.Object r15 = r1.await(r14)
                if (r15 != r0) goto L89
                goto La4
            L89:
                r3 = r6
            L8a:
                com.exteragram.messenger.api.dto.NowPlayingDTO r15 = (com.exteragram.messenger.api.dto.NowPlayingDTO) r15
                r9 = r3
                goto L7a
            L8e:
                kotlinx.coroutines.MainCoroutineDispatcher r15 = kotlinx.coroutines.Dispatchers.getMain()
                com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$1 r6 = new com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$1
                java.util.function.BiConsumer r7 = r14.$callback
                r11 = 0
                r6.<init>(r7, r8, r9, r11)
                r14.L$0 = r5
                r14.label = r2
                java.lang.Object r15 = kotlinx.coroutines.BuildersKt.withContext(r15, r6, r14)
                if (r15 != r0) goto La5
            La4:
                return r0
            La5:
                kotlin.Unit r15 = kotlin.Unit.INSTANCE
                return r15
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.C08571.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$getCurrentPlayingTrack$1$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ BiConsumer $callback;
            final /* synthetic */ NowPlayingDTO $finalTrack;
            final /* synthetic */ long $startTime;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(BiConsumer biConsumer, NowPlayingDTO nowPlayingDTO, long j, Continuation continuation) {
                super(2, continuation);
                this.$callback = biConsumer;
                this.$finalTrack = nowPlayingDTO;
                this.$startTime = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$callback, this.$finalTrack, this.$startTime, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$callback.accept(this.$finalTrack, Boxing.boxLong(System.currentTimeMillis() - this.$startTime));
                return Unit.INSTANCE;
            }
        }
    }

    public static final Job getCurrentPlayingTrack(long j, TLRPC.Document document, boolean z, BiConsumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C08571(z, j, document, callback, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object processSavedMusic(org.telegram.tgnet.TLRPC.Document r21, kotlin.coroutines.Continuation r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 400
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.processSavedMusic(org.telegram.tgnet.TLRPC$Document, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final Pair hasCommonArtist(List list, String str) {
        if (str == null || StringsKt.isBlank(str)) {
            return TuplesKt.m1122to(Boolean.FALSE, list);
        }
        boolean z = false;
        List listSplit = ARTISTS_SPLITTER.split(str, 0);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit, 10));
        Iterator it = listSplit.iterator();
        while (it.hasNext()) {
            arrayList.add(StringsKt.trim((String) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        List list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator it2 = list2.iterator();
            loop2: while (it2.hasNext()) {
                String lowerCase = ((String) it2.next()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                String strTranslitSafe = AndroidUtilities.translitSafe(lowerCase);
                Intrinsics.checkNotNullExpressionValue(strTranslitSafe, "translitSafe(...)");
                if (!arrayList2.isEmpty()) {
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        String lowerCase2 = ((String) obj2).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
                        String strTranslitSafe2 = AndroidUtilities.translitSafe(lowerCase2);
                        Intrinsics.checkNotNullExpressionValue(strTranslitSafe2, "translitSafe(...)");
                        if (Intrinsics.areEqual(lowerCase2, lowerCase) || Intrinsics.areEqual(lowerCase2, strTranslitSafe) || Intrinsics.areEqual(strTranslitSafe2, lowerCase)) {
                            z = true;
                            break loop2;
                        }
                    }
                }
            }
        }
        if (z) {
            list = arrayList2;
        }
        return TuplesKt.m1122to(Boolean.valueOf(z), list);
    }

    /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$getNowPlayingInfo$1 */
    static final class C08601 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer $callback;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C08601(Consumer consumer, Continuation continuation) {
            super(2, continuation);
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08601(this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C08601) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NowPlayingController nowPlayingController = NowPlayingController.INSTANCE;
                this.label = 1;
                obj = nowPlayingController.getNowPlayingInfoInternal(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$callback.m971v((NowPlayingInfoDTO) obj);
            return Unit.INSTANCE;
        }
    }

    public static final void getNowPlayingInfo(Consumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C08601(callback, null), 3, null);
    }

    public static /* synthetic */ void updateNowPlayingInfo$default(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Consumer consumer, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        updateNowPlayingInfo(nowPlayingInfoDTO, z, consumer);
    }

    /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$updateNowPlayingInfo$1 */
    static final class C08621 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $cache;
        final /* synthetic */ Consumer $callback;
        final /* synthetic */ NowPlayingInfoDTO $newNowPlaying;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C08621(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Consumer consumer, Continuation continuation) {
            super(2, continuation);
            this.$newNowPlaying = nowPlayingInfoDTO;
            this.$cache = z;
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08621(this.$newNowPlaying, this.$cache, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C08621) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NowPlayingController nowPlayingController = NowPlayingController.INSTANCE;
                NowPlayingInfoDTO nowPlayingInfoDTO = this.$newNowPlaying;
                boolean z = this.$cache;
                this.label = 1;
                obj = nowPlayingController.updateNowPlayingInfoInternal(nowPlayingInfoDTO, z, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$callback.m971v(Boxing.boxBoolean(((Boolean) obj).booleanValue()));
            return Unit.INSTANCE;
        }
    }

    public static final void updateNowPlayingInfo(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Consumer callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C08621(nowPlayingInfoDTO, z, callback, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object getNowPlayingInfoInternal(Continuation continuation) {
        return dbGetNowPlaying(ChatUtils.getInstance().getUserConfig().getClientUserId(), continuation);
    }

    /* renamed from: com.exteragram.messenger.nowplaying.NowPlayingController$updateNowPlayingInfoInternal$2 */
    static final class C08632 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $cache;
        final /* synthetic */ NowPlayingInfoDTO $newNowPlaying;
        Object L$0;
        boolean Z$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C08632(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$newNowPlaying = nowPlayingInfoDTO;
            this.$cache = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08632(this.$newNowPlaying, this.$cache, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C08632) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x00b1, code lost:
        
            if (r9 == r0) goto L30;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) throws java.lang.Throwable {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 2
                r3 = 0
                r4 = 1
                if (r1 == 0) goto L24
                if (r1 == r4) goto L1c
                if (r1 != r2) goto L14
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> Lc8
                goto Lb4
            L14:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L1c:
                java.lang.Object r1 = r8.L$0
                java.lang.String r1 = (java.lang.String) r1
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Exception -> Lc8
                goto L8e
            L24:
                kotlin.ResultKt.throwOnFailure(r9)
                com.exteragram.messenger.api.dto.NowPlayingInfoDTO r9 = r8.$newNowPlaying     // Catch: java.lang.Exception -> Lc8
                if (r9 == 0) goto L5f
                com.exteragram.messenger.api.model.NowPlayingServiceType r9 = r9.getServiceType()     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.api.model.NowPlayingServiceType r1 = com.exteragram.messenger.api.model.NowPlayingServiceType.NONE     // Catch: java.lang.Exception -> Lc8
                if (r9 != r1) goto L34
                goto L5f
            L34:
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc8
                r9.<init>()     // Catch: java.lang.Exception -> Lc8
                java.lang.String r1 = "set_now_playing "
                r9.append(r1)     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.api.dto.NowPlayingInfoDTO r1 = r8.$newNowPlaying     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.api.model.NowPlayingServiceType r1 = r1.getServiceType()     // Catch: java.lang.Exception -> Lc8
                java.lang.String r1 = r1.name()     // Catch: java.lang.Exception -> Lc8
                r9.append(r1)     // Catch: java.lang.Exception -> Lc8
                r1 = 32
                r9.append(r1)     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.api.dto.NowPlayingInfoDTO r1 = r8.$newNowPlaying     // Catch: java.lang.Exception -> Lc8
                java.lang.String r1 = r1.getUsername()     // Catch: java.lang.Exception -> Lc8
                r9.append(r1)     // Catch: java.lang.Exception -> Lc8
                java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> Lc8
                goto L61
            L5f:
                java.lang.String r9 = "clear_now_playing"
            L61:
                boolean r1 = r8.$cache     // Catch: java.lang.Exception -> Lc8
                r8.L$0 = r9     // Catch: java.lang.Exception -> Lc8
                r8.Z$0 = r1     // Catch: java.lang.Exception -> Lc8
                r8.label = r4     // Catch: java.lang.Exception -> Lc8
                kotlin.coroutines.SafeContinuation r5 = new kotlin.coroutines.SafeContinuation     // Catch: java.lang.Exception -> Lc8
                kotlin.coroutines.Continuation r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r8)     // Catch: java.lang.Exception -> Lc8
                r5.<init>(r6)     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.utils.ChatUtils r6 = com.exteragram.messenger.utils.ChatUtils.getInstance()     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.nowplaying.NowPlayingController$updateNowPlayingInfoInternal$2$message$1$1 r7 = new com.exteragram.messenger.nowplaying.NowPlayingController$updateNowPlayingInfoInternal$2$message$1$1     // Catch: java.lang.Exception -> Lc8
                r7.<init>()     // Catch: java.lang.Exception -> Lc8
                r6.sendBotRequest(r9, r1, r7)     // Catch: java.lang.Exception -> Lc8
                java.lang.Object r9 = r5.getOrThrow()     // Catch: java.lang.Exception -> Lc8
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch: java.lang.Exception -> Lc8
                if (r9 != r1) goto L8b
                kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r8)     // Catch: java.lang.Exception -> Lc8
            L8b:
                if (r9 != r0) goto L8e
                goto Lb3
            L8e:
                java.lang.String r9 = (java.lang.String) r9     // Catch: java.lang.Exception -> Lc8
                java.lang.String r1 = "ok"
                boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r1)     // Catch: java.lang.Exception -> Lc8
                if (r9 == 0) goto Lc3
                com.exteragram.messenger.nowplaying.NowPlayingController r9 = com.exteragram.messenger.nowplaying.NowPlayingController.INSTANCE     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.utils.ChatUtils r1 = com.exteragram.messenger.utils.ChatUtils.getInstance()     // Catch: java.lang.Exception -> Lc8
                org.telegram.messenger.UserConfig r1 = r1.getUserConfig()     // Catch: java.lang.Exception -> Lc8
                long r5 = r1.getClientUserId()     // Catch: java.lang.Exception -> Lc8
                com.exteragram.messenger.api.dto.NowPlayingInfoDTO r1 = r8.$newNowPlaying     // Catch: java.lang.Exception -> Lc8
                r7 = 0
                r8.L$0 = r7     // Catch: java.lang.Exception -> Lc8
                r8.label = r2     // Catch: java.lang.Exception -> Lc8
                java.lang.Object r9 = com.exteragram.messenger.nowplaying.NowPlayingController.access$dbUpdateNowPlaying(r9, r5, r1, r8)     // Catch: java.lang.Exception -> Lc8
                if (r9 != r0) goto Lb4
            Lb3:
                return r0
            Lb4:
                java.lang.Number r9 = (java.lang.Number) r9     // Catch: java.lang.Exception -> Lc8
                int r9 = r9.intValue()     // Catch: java.lang.Exception -> Lc8
                if (r9 <= 0) goto Lbd
                goto Lbe
            Lbd:
                r4 = 0
            Lbe:
                java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)     // Catch: java.lang.Exception -> Lc8
                return r9
            Lc3:
                java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch: java.lang.Exception -> Lc8
                return r9
            Lc8:
                java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.C08632.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object updateNowPlayingInfoInternal(NowPlayingInfoDTO nowPlayingInfoDTO, boolean z, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C08632(nowPlayingInfoDTO, z, null), continuation);
    }

    private final Object dbGetNowPlaying(long j, Continuation continuation) throws Throwable {
        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        DatabaseHelper.getNowPlaying(j, new Consumer() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$dbGetNowPlaying$2$1
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            @Override // java.util.function.Consumer
            /* renamed from: accept, reason: merged with bridge method [inline-methods] */
            public final void m971v(NowPlayingInfoDTO nowPlayingInfoDTO) {
                safeContinuation.resumeWith(Result.m2961constructorimpl(nowPlayingInfoDTO));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object dbUpdateNowPlaying(long j, NowPlayingInfoDTO nowPlayingInfoDTO, Continuation continuation) throws Throwable {
        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        DatabaseHelper.updateNowPlaying(j, nowPlayingInfoDTO, new Consumer() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$dbUpdateNowPlaying$2$1
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }

            @Override // java.util.function.Consumer
            /* renamed from: accept, reason: merged with bridge method [inline-methods] */
            public final void m971v(Integer updatedRows) {
                Intrinsics.checkNotNullParameter(updatedRows, "updatedRows");
                safeContinuation.resumeWith(Result.m2961constructorimpl(updatedRows));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    public static final class ItunesSearchResponse {
        private final int resultCount;
        private final List results;
        public static final Companion Companion = new Companion(null);
        private static final KSerializer[] $childSerializers = {null, new ArrayListSerializer(NowPlayingController$ItunesTrack$$serializer.INSTANCE)};

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ItunesSearchResponse)) {
                return false;
            }
            ItunesSearchResponse itunesSearchResponse = (ItunesSearchResponse) obj;
            return this.resultCount == itunesSearchResponse.resultCount && Intrinsics.areEqual(this.results, itunesSearchResponse.results);
        }

        public int hashCode() {
            return (this.resultCount * 31) + this.results.hashCode();
        }

        public String toString() {
            return "ItunesSearchResponse(resultCount=" + this.resultCount + ", results=" + this.results + ')';
        }

        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final KSerializer serializer() {
                return NowPlayingController$ItunesSearchResponse$$serializer.INSTANCE;
            }
        }

        public /* synthetic */ ItunesSearchResponse(int i, int i2, List list, SerializationConstructorMarker serializationConstructorMarker) {
            if (3 != (i & 3)) {
                PluginExceptionsKt.throwMissingFieldException(i, 3, NowPlayingController$ItunesSearchResponse$$serializer.INSTANCE.getDescriptor());
            }
            this.resultCount = i2;
            this.results = list;
        }

        public final List getResults() {
            return this.results;
        }
    }

    public static final class ItunesTrack {
        public static final Companion Companion = new Companion(null);
        private final String artistName;
        private final String artworkUrl100;
        private final String collectionName;
        private final String previewUrl;
        private final String trackName;
        private final Long trackTimeMillis;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ItunesTrack)) {
                return false;
            }
            ItunesTrack itunesTrack = (ItunesTrack) obj;
            return Intrinsics.areEqual(this.trackName, itunesTrack.trackName) && Intrinsics.areEqual(this.artistName, itunesTrack.artistName) && Intrinsics.areEqual(this.collectionName, itunesTrack.collectionName) && Intrinsics.areEqual(this.artworkUrl100, itunesTrack.artworkUrl100) && Intrinsics.areEqual(this.previewUrl, itunesTrack.previewUrl) && Intrinsics.areEqual(this.trackTimeMillis, itunesTrack.trackTimeMillis);
        }

        public int hashCode() {
            String str = this.trackName;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            String str2 = this.artistName;
            int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.collectionName;
            int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
            String str4 = this.artworkUrl100;
            int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
            String str5 = this.previewUrl;
            int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
            Long l = this.trackTimeMillis;
            return iHashCode5 + (l != null ? l.hashCode() : 0);
        }

        public String toString() {
            return "ItunesTrack(trackName=" + this.trackName + ", artistName=" + this.artistName + ", collectionName=" + this.collectionName + ", artworkUrl100=" + this.artworkUrl100 + ", previewUrl=" + this.previewUrl + ", trackTimeMillis=" + this.trackTimeMillis + ')';
        }

        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final KSerializer serializer() {
                return NowPlayingController$ItunesTrack$$serializer.INSTANCE;
            }
        }

        public /* synthetic */ ItunesTrack(int i, String str, String str2, String str3, String str4, String str5, Long l, SerializationConstructorMarker serializationConstructorMarker) {
            if ((i & 1) == 0) {
                this.trackName = null;
            } else {
                this.trackName = str;
            }
            if ((i & 2) == 0) {
                this.artistName = null;
            } else {
                this.artistName = str2;
            }
            if ((i & 4) == 0) {
                this.collectionName = null;
            } else {
                this.collectionName = str3;
            }
            if ((i & 8) == 0) {
                this.artworkUrl100 = null;
            } else {
                this.artworkUrl100 = str4;
            }
            if ((i & 16) == 0) {
                this.previewUrl = null;
            } else {
                this.previewUrl = str5;
            }
            if ((i & 32) == 0) {
                this.trackTimeMillis = null;
            } else {
                this.trackTimeMillis = l;
            }
        }

        public final String getTrackName() {
            return this.trackName;
        }

        public final String getArtistName() {
            return this.artistName;
        }

        public final String getCollectionName() {
            return this.collectionName;
        }

        public final String getArtworkUrl100() {
            return this.artworkUrl100;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit jsonParser$lambda$9(JsonBuilder Json) {
        Intrinsics.checkNotNullParameter(Json, "$this$Json");
        Json.setIgnoreUnknownKeys(true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b1 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object fetchItunesTrack(java.lang.String r9, java.lang.String r10, kotlin.coroutines.Continuation r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 262
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.nowplaying.NowPlayingController.fetchItunesTrack(java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object await(final Call call, Continuation continuation) {
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$await$2$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(Throwable th) {
                call.cancel();
            }
        });
        call.enqueue(new Callback() { // from class: com.exteragram.messenger.nowplaying.NowPlayingController$await$2$2
            @Override // okhttp3.Callback
            public void onResponse(Call call2, Response response) {
                Intrinsics.checkNotNullParameter(call2, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                cancellableContinuationImpl.resumeWith(Result.m2961constructorimpl(response));
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call2, IOException e) {
                Intrinsics.checkNotNullParameter(call2, "call");
                Intrinsics.checkNotNullParameter(e, "e");
                if (cancellableContinuationImpl.isCancelled()) {
                    return;
                }
                CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                Result.Companion companion = Result.Companion;
                cancellableContinuation.resumeWith(Result.m2961constructorimpl(ResultKt.createFailure(e)));
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }
}
