package com.exteragram.messenger.badges.source;

import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.api.model.ProfileStatus;
import com.exteragram.messenger.api.p005db.ProfileDao;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.tgnet.TLObject;
import p017j$.util.concurrent.ConcurrentHashMap;
import p017j$.util.concurrent.ConcurrentMap$EL;
import p017j$.util.function.BiFunction$CC;

/* loaded from: classes.dex */
public final class ApiBadgeSource implements BadgeSource {
    private final ConcurrentHashMap cache;
    private final ProfileDao profileDao;

    /* renamed from: com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1 */
    static final class C07851 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        C07851(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= TLObject.FLAG_31;
            return ApiBadgeSource.this.loadToCache(this);
        }
    }

    public ApiBadgeSource(ProfileDao profileDao) {
        Intrinsics.checkNotNullParameter(profileDao, "profileDao");
        this.profileDao = profileDao;
        this.cache = new ConcurrentHashMap();
    }

    @Override // com.exteragram.messenger.badges.source.BadgeSource
    public BadgeDTO getBadge(long j, boolean z) {
        BadgeInfo badgeInfo = (BadgeInfo) this.cache.get(Long.valueOf(j));
        if (badgeInfo != null) {
            return badgeInfo.getBadge();
        }
        return null;
    }

    public final boolean isDeveloper(long j) {
        BadgeInfo badgeInfo = (BadgeInfo) this.cache.get(Long.valueOf(j));
        return (badgeInfo != null ? badgeInfo.getStatus() : null) == ProfileStatus.DEVELOPER;
    }

    public final boolean canChangeBadge(long j) {
        BadgeInfo badgeInfo = (BadgeInfo) this.cache.get(Long.valueOf(j));
        return (badgeInfo != null && badgeInfo.getCanChangeBadge()) || isDeveloper(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final BadgeInfo updateLocalBadge$lambda$1(Function2 function2, Object obj, Object obj2) {
        return (BadgeInfo) function2.invoke(obj, obj2);
    }

    public final Object updateLocalBadge(long j, final BadgeDTO badgeDTO, Continuation continuation) {
        ConcurrentHashMap concurrentHashMap = this.cache;
        Long lBoxLong = Boxing.boxLong(j);
        final Function2 function2 = new Function2() { // from class: com.exteragram.messenger.badges.source.ApiBadgeSource$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ApiBadgeSource.updateLocalBadge$lambda$0(badgeDTO, (Long) obj, (BadgeInfo) obj2);
            }
        };
        ConcurrentMap$EL.computeIfPresent(concurrentHashMap, lBoxLong, new BiFunction() { // from class: com.exteragram.messenger.badges.source.ApiBadgeSource$$ExternalSyntheticLambda1
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ApiBadgeSource.updateLocalBadge$lambda$1(function2, obj, obj2);
            }
        });
        Object objUpdateBadge = this.profileDao.updateBadge(j, badgeDTO, continuation);
        return objUpdateBadge == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objUpdateBadge : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final BadgeInfo updateLocalBadge$lambda$0(BadgeDTO badgeDTO, Long l, BadgeInfo info) {
        Intrinsics.checkNotNullParameter(l, "<unused var>");
        Intrinsics.checkNotNullParameter(info, "info");
        return BadgeInfo.copy$default(info, badgeDTO, null, false, 6, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object loadToCache(kotlin.coroutines.Continuation r10) throws java.lang.Throwable {
        /*
            r9 = this;
            boolean r0 = r10 instanceof com.exteragram.messenger.badges.source.ApiBadgeSource.C07851
            if (r0 == 0) goto L13
            r0 = r10
            com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1 r0 = (com.exteragram.messenger.badges.source.ApiBadgeSource.C07851) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1 r0 = new com.exteragram.messenger.badges.source.ApiBadgeSource$loadToCache$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r0 = r0.L$0
            com.exteragram.messenger.badges.source.ApiBadgeSource r0 = (com.exteragram.messenger.badges.source.ApiBadgeSource) r0
            kotlin.ResultKt.throwOnFailure(r10)
            goto L46
        L2d:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L35:
            kotlin.ResultKt.throwOnFailure(r10)
            com.exteragram.messenger.api.db.ProfileDao r10 = r9.profileDao
            r0.L$0 = r9
            r0.label = r3
            java.lang.Object r10 = r10.getAll(r0)
            if (r10 != r1) goto L45
            return r1
        L45:
            r0 = r9
        L46:
            java.util.List r10 = (java.util.List) r10
            java.util.Iterator r10 = r10.iterator()
        L4c:
            boolean r1 = r10.hasNext()
            if (r1 == 0) goto L7f
            java.lang.Object r1 = r10.next()
            com.exteragram.messenger.api.dto.ProfileDTO r1 = (com.exteragram.messenger.api.dto.ProfileDTO) r1
            j$.util.concurrent.ConcurrentHashMap r2 = r0.cache
            long r4 = r1.getId()
            java.lang.Long r4 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r4)
            com.exteragram.messenger.badges.source.BadgeInfo r5 = new com.exteragram.messenger.badges.source.BadgeInfo
            com.exteragram.messenger.api.dto.BadgeDTO r6 = r1.getBadge()
            com.exteragram.messenger.api.model.ProfileStatus r7 = r1.getStatus()
            java.lang.Boolean r1 = r1.getCanChangeBadge()
            java.lang.Boolean r8 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r8)
            r5.<init>(r6, r7, r1)
            r2.put(r4, r5)
            goto L4c
        L7f:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.badges.source.ApiBadgeSource.loadToCache(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
