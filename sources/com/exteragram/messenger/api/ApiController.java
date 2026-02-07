package com.exteragram.messenger.api;

import android.content.SharedPreferences;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.network.ApiClient;
import com.exteragram.messenger.api.network.ApiService;
import com.exteragram.messenger.utils.AppUtils;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.mvel2.Operator;
import org.mvel2.asm.TypeReference;
import org.telegram.messenger.FileLog;
import p017j$.time.Duration;
import retrofit2.Response;

/* loaded from: classes.dex */
public final class ApiController {
    private static long lastApiSyncTime;
    public static final ApiController INSTANCE = new ApiController();
    private static final CoroutineScope scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());

    public static final void sync() {
        sync$default(null, 1, null);
    }

    private ApiController() {
    }

    @DebugMetadata(m1124c = "com.exteragram.messenger.api.ApiController$getExchangeRates$1", m1125f = "ApiController.kt", m1126l = {Operator.CONVERTABLE_TO}, m1127m = "invokeSuspend")
    /* renamed from: com.exteragram.messenger.api.ApiController$getExchangeRates$1 */
    static final class C07731 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Consumer<Map<String, Double>> $callback;
        final /* synthetic */ String $currency;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07731(String str, Consumer<Map<String, Double>> consumer, Continuation<? super C07731> continuation) {
            super(2, continuation);
            this.$currency = str;
            this.$callback = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C07731(this.$currency, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07731) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ApiService apiService = ApiClient.INSTANCE.getApiService();
                    String str = this.$currency;
                    this.label = 1;
                    obj = apiService.getExchangeRates(str, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                Response response = (Response) obj;
                if (!response.isSuccessful() || response.body() == null) {
                    this.$callback.m971v(MapsKt.emptyMap());
                } else {
                    Object objBody = response.body();
                    Intrinsics.checkNotNull(objBody);
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Map.Entry entry : ((Map) objBody).entrySet()) {
                        if (((Number) entry.getValue()).doubleValue() > 0.0d) {
                            linkedHashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap.size()));
                    for (Object obj2 : linkedHashMap.entrySet()) {
                        String string = StringsKt.trim((String) ((Map.Entry) obj2).getKey()).toString();
                        Locale ROOT = Locale.ROOT;
                        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                        String lowerCase = string.toLowerCase(ROOT);
                        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                        linkedHashMap2.put(lowerCase, ((Map.Entry) obj2).getValue());
                    }
                    LinkedHashMap linkedHashMap3 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap2.size()));
                    for (Object obj3 : linkedHashMap2.entrySet()) {
                        linkedHashMap3.put(((Map.Entry) obj3).getKey(), Boxing.boxDouble(1.0d / ((Number) ((Map.Entry) obj3).getValue()).doubleValue()));
                    }
                    this.$callback.m971v(linkedHashMap3);
                }
            } catch (Throwable th) {
                FileLog.m1160e(th);
                this.$callback.m971v(MapsKt.emptyMap());
            }
            return Unit.INSTANCE;
        }
    }

    public static final void getExchangeRates(String currency, Consumer<Map<String, Double>> callback) {
        Intrinsics.checkNotNullParameter(currency, "currency");
        Intrinsics.checkNotNullParameter(callback, "callback");
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C07731(currency, callback, null), 3, null);
    }

    public static /* synthetic */ void sync$default(SharedPreferences sharedPreferences, int i, Object obj) {
        if ((i & 1) != 0) {
            sharedPreferences = ExteraConfig.preferences;
        }
        sync(sharedPreferences);
    }

    public static final void sync(SharedPreferences preferences) {
        Intrinsics.checkNotNullParameter(preferences, "preferences");
        if (System.currentTimeMillis() - lastApiSyncTime < Duration.ofMinutes(8L).toMillis()) {
            return;
        }
        lastApiSyncTime = System.currentTimeMillis();
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new C07741(preferences, null), 3, null);
    }

    @DebugMetadata(m1124c = "com.exteragram.messenger.api.ApiController$sync$1", m1125f = "ApiController.kt", m1126l = {TypeReference.EXCEPTION_PARAMETER, TypeReference.NEW}, m1127m = "invokeSuspend")
    /* renamed from: com.exteragram.messenger.api.ApiController$sync$1 */
    static final class C07741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SharedPreferences $preferences;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C07741(SharedPreferences sharedPreferences, Continuation<? super C07741> continuation) {
            super(2, continuation);
            this.$preferences = sharedPreferences;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C07741(this.$preferences, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C07741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0048, code lost:
        
            if (r8 == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 293
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.api.ApiController.C07741.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$2(List list, Integer num) {
            AppUtils.log("Deleted profiles: " + list);
        }
    }
}
