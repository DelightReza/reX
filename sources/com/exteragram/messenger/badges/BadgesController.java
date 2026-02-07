package com.exteragram.messenger.badges;

import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.api.p005db.ExteraDatabase;
import com.exteragram.messenger.badges.source.ApiBadgeSource;
import com.exteragram.messenger.badges.source.BadgeSource;
import com.exteragram.messenger.badges.source.TelegramBadgeSource;
import com.exteragram.messenger.utils.ChatUtils;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public final class BadgesController {
    public static final BadgesController INSTANCE = new BadgesController();
    private static final ApiBadgeSource apiBadgeSource;
    private static final List badgeSources;
    private static final CoroutineScope scope;
    private static final TelegramBadgeSource telegramBadgeSource;

    public final boolean hasBadge() {
        return hasBadge$default(this, null, 1, null);
    }

    public final boolean isDeveloper() {
        return isDeveloper$default(this, null, 1, null);
    }

    private BadgesController() {
    }

    static {
        CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        scope = CoroutineScope;
        TelegramBadgeSource telegramBadgeSource2 = new TelegramBadgeSource();
        telegramBadgeSource = telegramBadgeSource2;
        ApiBadgeSource apiBadgeSource2 = new ApiBadgeSource(ExteraDatabase.Companion.getInstance().profileDao());
        apiBadgeSource = apiBadgeSource2;
        badgeSources = CollectionsKt.listOf((Object[]) new BadgeSource[]{apiBadgeSource2, telegramBadgeSource2});
        BuildersKt__Builders_commonKt.launch$default(CoroutineScope, null, null, new C07841(null), 3, null);
    }

    /* renamed from: com.exteragram.messenger.badges.BadgesController$1 */
    static final class C07841 extends SuspendLambda implements Function2 {
        int label;

        C07841(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C07841(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C07841) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ApiBadgeSource apiBadgeSource = BadgesController.apiBadgeSource;
                this.label = 1;
                if (apiBadgeSource.loadToCache(this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final BadgeDTO getBadge(TLObject tLObject) {
        Pair pairM1122to;
        try {
            if (tLObject instanceof TLRPC.User) {
                pairM1122to = TuplesKt.m1122to(Long.valueOf(((TLRPC.User) tLObject).f1734id), Boolean.TRUE);
            } else {
                if (!(tLObject instanceof TLRPC.Chat)) {
                    return null;
                }
                pairM1122to = TuplesKt.m1122to(Long.valueOf(((TLRPC.Chat) tLObject).f1571id), Boolean.FALSE);
            }
            long jLongValue = ((Number) pairM1122to.component1()).longValue();
            boolean zBooleanValue = ((Boolean) pairM1122to.component2()).booleanValue();
            Iterator it = badgeSources.iterator();
            while (it.hasNext()) {
                BadgeDTO badge = ((BadgeSource) it.next()).getBadge(jLongValue, zBooleanValue);
                if (badge != null) {
                    return badge;
                }
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        return null;
    }

    public static /* synthetic */ boolean hasBadge$default(BadgesController badgesController, TLObject tLObject, int i, Object obj) {
        if ((i & 1) != 0) {
            tLObject = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser();
        }
        return badgesController.hasBadge(tLObject);
    }

    public final boolean hasBadge(TLObject obj) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        return getBadge(obj) != null;
    }

    public final void invalidateCustomBadgesCache() {
        telegramBadgeSource.invalidateCustomBadgesCache();
    }

    public final boolean isTrusted(long j) {
        return telegramBadgeSource.isTrusted(j);
    }

    public final boolean isExtera(long j) {
        return apiBadgeSource.isDeveloper(j) || telegramBadgeSource.isExtera(j);
    }

    public final boolean isExtera(TLRPC.Chat chat) {
        if (chat != null) {
            return apiBadgeSource.isDeveloper(chat.f1571id) || telegramBadgeSource.isExtera(chat.f1571id);
        }
        return false;
    }

    public final BadgeDTO getDefaultBadge() {
        if (apiBadgeSource.isDeveloper(UserConfig.getInstance(UserConfig.selectedAccount).clientUserId)) {
            return TelegramBadgeSource.Companion.getDEV_BADGE();
        }
        return TelegramBadgeSource.Companion.getSUPPORTER_BADGE();
    }

    public final boolean canChangeBadge() {
        return apiBadgeSource.canChangeBadge(UserConfig.getInstance(UserConfig.selectedAccount).clientUserId) || isDeveloper$default(this, null, 1, null);
    }

    public final void updateBadge(final BadgeDTO badgeDTO, final Consumer callback) {
        String str;
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (badgeDTO != null) {
            String text = badgeDTO.getText();
            if (text == null || text.length() == 0) {
                str = "badge " + badgeDTO.getDocumentId();
            } else {
                str = "badge " + badgeDTO.getDocumentId() + ' ' + text;
            }
            ChatUtils.getInstance(UserConfig.selectedAccount).sendBotRequest(str, false, new Utilities.Callback() { // from class: com.exteragram.messenger.badges.BadgesController$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    BadgesController.updateBadge$lambda$1(callback, badgeDTO, (String) obj);
                }
            });
            return;
        }
        callback.m971v(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateBadge$lambda$1(Consumer consumer, BadgeDTO badgeDTO, String str) {
        if (Intrinsics.areEqual("ok", str)) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new BadgesController$updateBadge$1$1(badgeDTO, null), 3, null);
        }
        consumer.m971v(str);
    }

    public static /* synthetic */ boolean isDeveloper$default(BadgesController badgesController, TLRPC.User user, int i, Object obj) {
        if ((i & 1) != 0) {
            user = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser();
        }
        return badgesController.isDeveloper(user);
    }

    public final boolean isDeveloper(TLRPC.User user) {
        Intrinsics.checkNotNullParameter(user, "user");
        return apiBadgeSource.isDeveloper(user.f1734id) || telegramBadgeSource.isExteraDev(user.f1734id);
    }

    public final boolean isAyuModerator(TLRPC.User user) {
        return user != null && telegramBadgeSource.isAyuModerator(user.f1734id);
    }
}
