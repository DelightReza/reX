package com.exteragram.messenger.badges.source;

import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.badges.CachedRemoteSet;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class TelegramBadgeSource implements BadgeSource {
    private volatile Map customUserBadgesCache;
    private final CachedRemoteSet moderatorsCache;
    private final CachedRemoteSet supporterCache;
    private final CachedRemoteSet supporterChannelsCache;
    public static final Companion Companion = new Companion(null);
    private static final Set OFFICIAL_CHANNELS_DEFAULT = SetsKt.setOf((Object[]) new Long[]{1233768168L, 1524581881L, 1571726392L, 1632728092L, 1172503281L, 1877362358L});
    private static final Set DEVS_DEFAULT = SetsKt.setOf((Object[]) new Long[]{963080346L, 1282540315L, 1374434073L, 168769611L, 1773117711L, 5330087923L, 666154369L, 139303278L, 668557709L});
    private static final Set TRUSTED_PLUGINS_DEFAULT = SetsKt.setOf((Object) 2562664432L);
    private static final Set EMPTY_LONG_SET = SetsKt.emptySet();
    private static final BadgeDTO DEV_BADGE = new BadgeDTO(5359407509327085568L, null);
    private static final BadgeDTO SUPPORTER_BADGE = new BadgeDTO(5391059537102927631L, null);
    private static final BadgeDTO DESIGNER_BADGE = new BadgeDTO(5393546778433846015L, null);
    private final CachedRemoteSet officialChannelsCache = new CachedRemoteSet("channels", OFFICIAL_CHANNELS_DEFAULT);
    private final CachedRemoteSet devsCache = new CachedRemoteSet("devs", DEVS_DEFAULT);
    private final CachedRemoteSet trustedPluginsCache = new CachedRemoteSet("trusted_plugins", TRUSTED_PLUGINS_DEFAULT);

    public TelegramBadgeSource() {
        Set set = EMPTY_LONG_SET;
        this.supporterCache = new CachedRemoteSet("supporters", set);
        this.supporterChannelsCache = new CachedRemoteSet("supporter_channels", set);
        this.moderatorsCache = new CachedRemoteSet("moders", set);
    }

    @Override // com.exteragram.messenger.badges.source.BadgeSource
    public BadgeDTO getBadge(long j, boolean z) {
        if (z) {
            BadgeDTO badgeDTO = (BadgeDTO) getCustomUserBadges().get(Long.valueOf(j));
            if (badgeDTO != null) {
                return badgeDTO;
            }
            if (this.devsCache.contains(j)) {
                return DEV_BADGE;
            }
            if (this.supporterCache.contains(j)) {
                return SUPPORTER_BADGE;
            }
            return null;
        }
        if (this.officialChannelsCache.contains(j)) {
            return DEV_BADGE;
        }
        if (this.supporterChannelsCache.contains(j)) {
            return SUPPORTER_BADGE;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void loadCustomBadges() throws java.lang.NumberFormatException {
        /*
            r15 = this;
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.String r1 = "custom_badges"
            java.lang.String r2 = ""
            java.lang.String r1 = com.exteragram.messenger.utils.network.RemoteUtils.getStringConfigValue(r1, r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto Lda
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            kotlin.text.Regex r2 = new kotlin.text.Regex
            java.lang.String r3 = ";"
            r2.<init>(r3)
            r3 = 0
            java.util.List r1 = r2.split(r1, r3)
            boolean r2 = r1.isEmpty()
            r4 = 1
            if (r2 != 0) goto L50
            int r2 = r1.size()
            java.util.ListIterator r2 = r1.listIterator(r2)
        L31:
            boolean r5 = r2.hasPrevious()
            if (r5 == 0) goto L50
            java.lang.Object r5 = r2.previous()
            java.lang.String r5 = (java.lang.String) r5
            int r5 = r5.length()
            if (r5 != 0) goto L44
            goto L31
        L44:
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            int r2 = r2.nextIndex()
            int r2 = r2 + r4
            java.util.List r1 = kotlin.collections.CollectionsKt.take(r1, r2)
            goto L54
        L50:
            java.util.List r1 = kotlin.collections.CollectionsKt.emptyList()
        L54:
            java.util.Collection r1 = (java.util.Collection) r1
            java.lang.String[] r2 = new java.lang.String[r3]
            java.lang.Object[] r1 = r1.toArray(r2)
            java.lang.String[] r1 = (java.lang.String[]) r1
            int r2 = r1.length
            r5 = 0
        L60:
            if (r5 >= r2) goto Lda
            r6 = r1[r5]
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto Ld7
            java.lang.CharSequence r6 = kotlin.text.StringsKt.trim(r6)
            java.lang.String r6 = r6.toString()
            kotlin.text.Regex r7 = new kotlin.text.Regex
            java.lang.String r8 = ":"
            r7.<init>(r8)
            r8 = 3
            java.util.List r6 = r7.split(r6, r8)
            java.util.Collection r6 = (java.util.Collection) r6
            java.lang.String[] r7 = new java.lang.String[r3]
            java.lang.Object[] r6 = r6.toArray(r7)
            java.lang.String[] r6 = (java.lang.String[]) r6
            int r7 = r6.length
            r9 = 2
            if (r7 < r9) goto Ld7
            r7 = r6[r3]     // Catch: java.lang.NumberFormatException -> Ld7
            long r10 = java.lang.Long.parseLong(r7)     // Catch: java.lang.NumberFormatException -> Ld7
            r7 = r6[r4]     // Catch: java.lang.NumberFormatException -> Ld7
            java.lang.String r12 = "DESIGNER"
            boolean r12 = kotlin.text.StringsKt.equals(r12, r7, r4)     // Catch: java.lang.NumberFormatException -> Ld7
            if (r12 == 0) goto L9f
            com.exteragram.messenger.api.dto.BadgeDTO r7 = com.exteragram.messenger.badges.source.TelegramBadgeSource.DESIGNER_BADGE     // Catch: java.lang.NumberFormatException -> Ld7
            goto Lc0
        L9f:
            java.lang.String r12 = "DEV"
            boolean r12 = kotlin.text.StringsKt.equals(r12, r7, r4)     // Catch: java.lang.NumberFormatException -> Ld7
            if (r12 == 0) goto Laa
            com.exteragram.messenger.api.dto.BadgeDTO r7 = com.exteragram.messenger.badges.source.TelegramBadgeSource.DEV_BADGE     // Catch: java.lang.NumberFormatException -> Ld7
            goto Lc0
        Laa:
            java.lang.String r12 = "SUPPORTER"
            boolean r12 = kotlin.text.StringsKt.equals(r12, r7, r4)     // Catch: java.lang.NumberFormatException -> Ld7
            if (r12 == 0) goto Lb5
            com.exteragram.messenger.api.dto.BadgeDTO r7 = com.exteragram.messenger.badges.source.TelegramBadgeSource.SUPPORTER_BADGE     // Catch: java.lang.NumberFormatException -> Ld7
            goto Lc0
        Lb5:
            com.exteragram.messenger.api.dto.BadgeDTO r12 = new com.exteragram.messenger.api.dto.BadgeDTO     // Catch: java.lang.NumberFormatException -> Ld7
            long r13 = java.lang.Long.parseLong(r7)     // Catch: java.lang.NumberFormatException -> Ld7
            r7 = 0
            r12.<init>(r13, r7)     // Catch: java.lang.NumberFormatException -> Ld7
            r7 = r12
        Lc0:
            int r12 = r6.length     // Catch: java.lang.NumberFormatException -> Ld7
            if (r12 != r8) goto Ld0
            r8 = r6[r9]     // Catch: java.lang.NumberFormatException -> Ld7
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch: java.lang.NumberFormatException -> Ld7
            if (r8 != 0) goto Ld0
            r6 = r6[r9]     // Catch: java.lang.NumberFormatException -> Ld7
            r7.setText(r6)     // Catch: java.lang.NumberFormatException -> Ld7
        Ld0:
            java.lang.Long r6 = java.lang.Long.valueOf(r10)     // Catch: java.lang.NumberFormatException -> Ld7
            r0.put(r6, r7)     // Catch: java.lang.NumberFormatException -> Ld7
        Ld7:
            int r5 = r5 + 1
            goto L60
        Lda:
            java.util.Map r0 = p017j$.util.DesugarCollections.unmodifiableMap(r0)
            r15.customUserBadgesCache = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.badges.source.TelegramBadgeSource.loadCustomBadges():void");
    }

    private final Map getCustomUserBadges() {
        Map map = this.customUserBadgesCache;
        if (map == null) {
            synchronized (this) {
                try {
                    map = this.customUserBadgesCache;
                    if (map == null) {
                        loadCustomBadges();
                        map = this.customUserBadgesCache;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        Intrinsics.checkNotNull(map);
        return map;
    }

    public final void invalidateCustomBadgesCache() {
        this.customUserBadgesCache = null;
    }

    public final boolean isExtera(long j) {
        return this.officialChannelsCache.contains(j);
    }

    public final boolean isTrusted(long j) {
        return this.trustedPluginsCache.contains(j);
    }

    public final boolean isExteraDev(long j) {
        return this.devsCache.contains(j);
    }

    public final boolean isAyuModerator(long j) {
        return this.moderatorsCache.contains(j);
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BadgeDTO getDEV_BADGE() {
            return TelegramBadgeSource.DEV_BADGE;
        }

        public final BadgeDTO getSUPPORTER_BADGE() {
            return TelegramBadgeSource.SUPPORTER_BADGE;
        }
    }
}
