package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.os.Handler;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.NetworkTypeObserver;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import org.mvel2.DataTypes;
import org.mvel2.Operator;
import org.mvel2.asm.Opcodes;
import org.mvel2.asm.TypeReference;
import org.mvel2.asm.signature.SignatureVisitor;
import org.telegram.messenger.voip.VoIPService;

/* loaded from: classes.dex */
public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    private static DefaultBandwidthMeter singletonInstance;
    private volatile long bitrateEstimate;
    private final Clock clock;
    private final BandwidthMeter.EventListener.EventDispatcher eventDispatcher;
    private final ImmutableMap initialBitrateEstimates;
    private long lastReportedBitrateEstimate;
    private int networkType;
    private int networkTypeOverride;
    private boolean networkTypeOverrideSet;
    private final boolean resetOnNetworkTypeChange;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private final SlidingPercentile slidingPercentile;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;
    public static final ImmutableList DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI = ImmutableList.m440of((Object) 4400000L, (Object) 3200000L, (Object) 2300000L, (Object) 1600000L, (Object) 810000L);
    public static final ImmutableList DEFAULT_INITIAL_BITRATE_ESTIMATES_2G = ImmutableList.m440of((Object) 1400000L, (Object) 990000L, (Object) 730000L, (Object) 510000L, (Object) 230000L);
    public static final ImmutableList DEFAULT_INITIAL_BITRATE_ESTIMATES_3G = ImmutableList.m440of((Object) 2100000L, (Object) 1400000L, (Object) 1000000L, (Object) 890000L, (Object) 640000L);
    public static final ImmutableList DEFAULT_INITIAL_BITRATE_ESTIMATES_4G = ImmutableList.m440of((Object) 2600000L, (Object) 1700000L, (Object) 1300000L, (Object) 1000000L, (Object) 700000L);
    public static final ImmutableList DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA = ImmutableList.m440of((Object) 5700000L, (Object) 3700000L, (Object) 2300000L, (Object) 1700000L, (Object) 990000L);
    public static final ImmutableList DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_SA = ImmutableList.m440of((Object) 2800000L, (Object) 1800000L, (Object) 1400000L, (Object) 1100000L, (Object) 870000L);

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public /* synthetic */ long getTimeToFirstByteEstimateUs() {
        return BandwidthMeter.CC.$default$getTimeToFirstByteEstimateUs(this);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public TransferListener getTransferListener() {
        return this;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z) {
    }

    public static final class Builder {
        private Clock clock;
        private final Context context;
        private Map initialBitrateEstimates;
        private boolean resetOnNetworkTypeChange;
        private int slidingWindowMaxWeight;

        public Builder(Context context) {
            this.context = context == null ? null : context.getApplicationContext();
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Util.getCountryCode(context));
            this.slidingWindowMaxWeight = 2000;
            this.clock = Clock.DEFAULT;
            this.resetOnNetworkTypeChange = true;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.context, this.initialBitrateEstimates, this.slidingWindowMaxWeight, this.clock, this.resetOnNetworkTypeChange);
        }

        private static Map getInitialBitrateEstimatesForCountry(String str) {
            int[] initialBitrateCountryGroupAssignment = DefaultBandwidthMeter.getInitialBitrateCountryGroupAssignment(str);
            HashMap map = new HashMap(8);
            map.put(0, 1000000L);
            ImmutableList immutableList = DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI;
            map.put(2, (Long) immutableList.get(initialBitrateCountryGroupAssignment[0]));
            map.put(3, (Long) DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_2G.get(initialBitrateCountryGroupAssignment[1]));
            map.put(4, (Long) DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_3G.get(initialBitrateCountryGroupAssignment[2]));
            map.put(5, (Long) DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_4G.get(initialBitrateCountryGroupAssignment[3]));
            map.put(10, (Long) DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA.get(initialBitrateCountryGroupAssignment[4]));
            map.put(9, (Long) DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_SA.get(initialBitrateCountryGroupAssignment[5]));
            map.put(7, (Long) immutableList.get(initialBitrateCountryGroupAssignment[0]));
            return map;
        }
    }

    public static synchronized DefaultBandwidthMeter getSingletonInstance(Context context) {
        try {
            if (singletonInstance == null) {
                singletonInstance = new Builder(context).build();
            }
        } catch (Throwable th) {
            throw th;
        }
        return singletonInstance;
    }

    private DefaultBandwidthMeter(Context context, Map map, int i, Clock clock, boolean z) {
        this.initialBitrateEstimates = ImmutableMap.copyOf(map);
        this.eventDispatcher = new BandwidthMeter.EventListener.EventDispatcher();
        this.slidingPercentile = new SlidingPercentile(i);
        this.clock = clock;
        this.resetOnNetworkTypeChange = z;
        if (context != null) {
            NetworkTypeObserver networkTypeObserver = NetworkTypeObserver.getInstance(context);
            int networkType = networkTypeObserver.getNetworkType();
            this.networkType = networkType;
            this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(networkType);
            networkTypeObserver.register(new NetworkTypeObserver.Listener() { // from class: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.util.NetworkTypeObserver.Listener
                public final void onNetworkTypeChanged(int i2) throws Throwable {
                    this.f$0.onNetworkTypeChanged(i2);
                }
            });
            return;
        }
        this.networkType = 0;
        this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(0);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.eventDispatcher.addListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        this.eventDispatcher.removeListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z) {
        try {
            if (isTransferAtFullNetworkSpeed(dataSpec, z)) {
                if (this.streamCount == 0) {
                    this.sampleStartTimeMs = this.clock.elapsedRealtime();
                }
                this.streamCount++;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z, int i) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z)) {
            this.sampleBytesTransferred += i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0056 A[Catch: all -> 0x0089, TRY_ENTER, TryCatch #2 {all -> 0x0089, blocks: (B:3:0x0001, B:7:0x0009, B:11:0x0011, B:13:0x002e, B:23:0x0077, B:22:0x0056), top: B:38:0x0001 }] */
    @Override // com.google.android.exoplayer2.upstream.TransferListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void onTransferEnd(com.google.android.exoplayer2.upstream.DataSource r11, com.google.android.exoplayer2.upstream.DataSpec r12, boolean r13) throws java.lang.Throwable {
        /*
            r10 = this;
            monitor-enter(r10)
            boolean r11 = isTransferAtFullNetworkSpeed(r12, r13)     // Catch: java.lang.Throwable -> L89
            if (r11 != 0) goto L9
            monitor-exit(r10)
            return
        L9:
            int r11 = r10.streamCount     // Catch: java.lang.Throwable -> L89
            r12 = 1
            if (r11 <= 0) goto L10
            r11 = 1
            goto L11
        L10:
            r11 = 0
        L11:
            com.google.android.exoplayer2.util.Assertions.checkState(r11)     // Catch: java.lang.Throwable -> L89
            com.google.android.exoplayer2.util.Clock r11 = r10.clock     // Catch: java.lang.Throwable -> L89
            long r0 = r11.elapsedRealtime()     // Catch: java.lang.Throwable -> L89
            long r2 = r10.sampleStartTimeMs     // Catch: java.lang.Throwable -> L89
            long r2 = r0 - r2
            int r5 = (int) r2     // Catch: java.lang.Throwable -> L89
            long r2 = r10.totalElapsedTimeMs     // Catch: java.lang.Throwable -> L89
            long r6 = (long) r5     // Catch: java.lang.Throwable -> L89
            long r2 = r2 + r6
            r10.totalElapsedTimeMs = r2     // Catch: java.lang.Throwable -> L89
            long r2 = r10.totalBytesTransferred     // Catch: java.lang.Throwable -> L89
            long r6 = r10.sampleBytesTransferred     // Catch: java.lang.Throwable -> L89
            long r2 = r2 + r6
            r10.totalBytesTransferred = r2     // Catch: java.lang.Throwable -> L89
            if (r5 <= 0) goto L8c
            float r11 = (float) r6     // Catch: java.lang.Throwable -> L89
            r13 = 1174011904(0x45fa0000, float:8000.0)
            float r11 = r11 * r13
            float r13 = (float) r5     // Catch: java.lang.Throwable -> L89
            float r11 = r11 / r13
            com.google.android.exoplayer2.upstream.SlidingPercentile r13 = r10.slidingPercentile     // Catch: java.lang.Throwable -> L89
            double r2 = (double) r6     // Catch: java.lang.Throwable -> L89
            double r2 = java.lang.Math.sqrt(r2)     // Catch: java.lang.Throwable -> L89
            int r2 = (int) r2     // Catch: java.lang.Throwable -> L89
            r13.addSample(r2, r11)     // Catch: java.lang.Throwable -> L89
            long r2 = r10.totalElapsedTimeMs     // Catch: java.lang.Throwable -> L89
            r6 = 2000(0x7d0, double:9.88E-321)
            int r11 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r11 >= 0) goto L56
            long r2 = r10.totalBytesTransferred     // Catch: java.lang.Throwable -> L52
            r6 = 524288(0x80000, double:2.590327E-318)
            int r11 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r11 < 0) goto L77
            goto L56
        L52:
            r0 = move-exception
            r11 = r0
            r4 = r10
            goto L94
        L56:
            com.google.android.exoplayer2.upstream.SlidingPercentile r11 = r10.slidingPercentile     // Catch: java.lang.Throwable -> L89
            r13 = 1056964608(0x3f000000, float:0.5)
            float r11 = r11.getPercentile(r13)     // Catch: java.lang.Throwable -> L89
            long r2 = (long) r11     // Catch: java.lang.Throwable -> L89
            r10.bitrateEstimate = r2     // Catch: java.lang.Throwable -> L89
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L89
            r11.<init>()     // Catch: java.lang.Throwable -> L89
            java.lang.String r13 = "debug_loading: bandwidth meter (onTransferEnd), bitrate estimate = "
            r11.append(r13)     // Catch: java.lang.Throwable -> L89
            long r2 = r10.bitrateEstimate     // Catch: java.lang.Throwable -> L89
            r11.append(r2)     // Catch: java.lang.Throwable -> L89
            java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> L89
            org.telegram.messenger.FileLog.m1157d(r11)     // Catch: java.lang.Throwable -> L89
        L77:
            long r6 = r10.sampleBytesTransferred     // Catch: java.lang.Throwable -> L89
            long r8 = r10.bitrateEstimate     // Catch: java.lang.Throwable -> L89
            r4 = r10
            r4.maybeNotifyBandwidthSample(r5, r6, r8)     // Catch: java.lang.Throwable -> L86
            r4.sampleStartTimeMs = r0     // Catch: java.lang.Throwable -> L86
            r0 = 0
            r4.sampleBytesTransferred = r0     // Catch: java.lang.Throwable -> L86
            goto L8d
        L86:
            r0 = move-exception
        L87:
            r11 = r0
            goto L94
        L89:
            r0 = move-exception
            r4 = r10
            goto L87
        L8c:
            r4 = r10
        L8d:
            int r11 = r4.streamCount     // Catch: java.lang.Throwable -> L86
            int r11 = r11 - r12
            r4.streamCount = r11     // Catch: java.lang.Throwable -> L86
            monitor-exit(r10)
            return
        L94:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> L86
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.onTransferEnd(com.google.android.exoplayer2.upstream.DataSource, com.google.android.exoplayer2.upstream.DataSpec, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006f A[Catch: all -> 0x00a0, TRY_ENTER, TryCatch #0 {all -> 0x00a0, blocks: (B:3:0x0001, B:7:0x0021, B:17:0x0090, B:16:0x006f), top: B:30:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void onTransfer(long r12, long r14) {
        /*
            r11 = this;
            monitor-enter(r11)
            com.google.android.exoplayer2.util.Clock r0 = r11.clock     // Catch: java.lang.Throwable -> La0
            long r0 = r0.elapsedRealtime()     // Catch: java.lang.Throwable -> La0
            long r2 = r11.sampleStartTimeMs     // Catch: java.lang.Throwable -> La0
            long r2 = r0 - r2
            int r3 = (int) r2     // Catch: java.lang.Throwable -> La0
            long r4 = r11.totalElapsedTimeMs     // Catch: java.lang.Throwable -> La0
            long r2 = (long) r3     // Catch: java.lang.Throwable -> La0
            long r4 = r4 + r2
            r11.totalElapsedTimeMs = r4     // Catch: java.lang.Throwable -> La0
            long r2 = r11.totalBytesTransferred     // Catch: java.lang.Throwable -> La0
            long r2 = r2 + r12
            r11.totalBytesTransferred = r2     // Catch: java.lang.Throwable -> La0
            r2 = 0
            int r4 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r4 <= 0) goto La3
            int r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r4 <= 0) goto La3
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La0
            r4.<init>()     // Catch: java.lang.Throwable -> La0
            java.lang.String r5 = "debug_loading: bandwidth meter on transfer "
            r4.append(r5)     // Catch: java.lang.Throwable -> La0
            java.lang.String r5 = org.telegram.messenger.AndroidUtilities.formatFileSize(r12)     // Catch: java.lang.Throwable -> La0
            r4.append(r5)     // Catch: java.lang.Throwable -> La0
            java.lang.String r5 = " per "
            r4.append(r5)     // Catch: java.lang.Throwable -> La0
            r4.append(r14)     // Catch: java.lang.Throwable -> La0
            java.lang.String r5 = "ms"
            r4.append(r5)     // Catch: java.lang.Throwable -> La0
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> La0
            org.telegram.messenger.FileLog.m1157d(r4)     // Catch: java.lang.Throwable -> La0
            float r4 = (float) r12     // Catch: java.lang.Throwable -> La0
            r5 = 1174011904(0x45fa0000, float:8000.0)
            float r4 = r4 * r5
            float r5 = (float) r14     // Catch: java.lang.Throwable -> La0
            float r4 = r4 / r5
            com.google.android.exoplayer2.upstream.SlidingPercentile r5 = r11.slidingPercentile     // Catch: java.lang.Throwable -> La0
            double r6 = (double) r12     // Catch: java.lang.Throwable -> La0
            double r6 = java.lang.Math.sqrt(r6)     // Catch: java.lang.Throwable -> La0
            int r6 = (int) r6     // Catch: java.lang.Throwable -> La0
            r5.addSample(r6, r4)     // Catch: java.lang.Throwable -> La0
            long r4 = r11.totalElapsedTimeMs     // Catch: java.lang.Throwable -> La0
            r6 = 2000(0x7d0, double:9.88E-321)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L6f
            long r4 = r11.totalBytesTransferred     // Catch: java.lang.Throwable -> L6b
            r6 = 524288(0x80000, double:2.590327E-318)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 < 0) goto L90
            goto L6f
        L6b:
            r0 = move-exception
            r12 = r0
            r5 = r11
            goto La6
        L6f:
            com.google.android.exoplayer2.upstream.SlidingPercentile r4 = r11.slidingPercentile     // Catch: java.lang.Throwable -> La0
            r5 = 1056964608(0x3f000000, float:0.5)
            float r4 = r4.getPercentile(r5)     // Catch: java.lang.Throwable -> La0
            long r4 = (long) r4     // Catch: java.lang.Throwable -> La0
            r11.bitrateEstimate = r4     // Catch: java.lang.Throwable -> La0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La0
            r4.<init>()     // Catch: java.lang.Throwable -> La0
            java.lang.String r5 = "debug_loading: bandwidth meter (onTransfer), bitrate estimate = "
            r4.append(r5)     // Catch: java.lang.Throwable -> La0
            long r5 = r11.bitrateEstimate     // Catch: java.lang.Throwable -> La0
            r4.append(r5)     // Catch: java.lang.Throwable -> La0
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> La0
            org.telegram.messenger.FileLog.m1157d(r4)     // Catch: java.lang.Throwable -> La0
        L90:
            int r6 = (int) r14     // Catch: java.lang.Throwable -> La0
            long r9 = r11.bitrateEstimate     // Catch: java.lang.Throwable -> La0
            r5 = r11
            r7 = r12
            r5.maybeNotifyBandwidthSample(r6, r7, r9)     // Catch: java.lang.Throwable -> L9d
            r5.sampleStartTimeMs = r0     // Catch: java.lang.Throwable -> L9d
            r5.sampleBytesTransferred = r2     // Catch: java.lang.Throwable -> L9d
            goto La4
        L9d:
            r0 = move-exception
        L9e:
            r12 = r0
            goto La6
        La0:
            r0 = move-exception
            r5 = r11
            goto L9e
        La3:
            r5 = r11
        La4:
            monitor-exit(r11)
            return
        La6:
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L9d
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.onTransfer(long, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onNetworkTypeChanged(int i) throws Throwable {
        Throwable th;
        try {
            try {
                int i2 = this.networkType;
                if (i2 != 0) {
                    try {
                        if (!this.resetOnNetworkTypeChange) {
                            return;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        throw th;
                    }
                }
                if (this.networkTypeOverrideSet) {
                    i = this.networkTypeOverride;
                }
                if (i2 == i) {
                    return;
                }
                this.networkType = i;
                if (i == 1 || i == 0 || i == 8) {
                    return;
                }
                this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(i);
                long jElapsedRealtime = this.clock.elapsedRealtime();
                maybeNotifyBandwidthSample(this.streamCount > 0 ? (int) (jElapsedRealtime - this.sampleStartTimeMs) : 0, this.sampleBytesTransferred, this.bitrateEstimate);
                this.sampleStartTimeMs = jElapsedRealtime;
                this.sampleBytesTransferred = 0L;
                this.totalBytesTransferred = 0L;
                this.totalElapsedTimeMs = 0L;
                this.slidingPercentile.reset();
            } catch (Throwable th3) {
                th = th3;
                th = th;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            th = th;
            throw th;
        }
    }

    private void maybeNotifyBandwidthSample(int i, long j, long j2) {
        if (i == 0 && j == 0 && j2 == this.lastReportedBitrateEstimate) {
            return;
        }
        this.lastReportedBitrateEstimate = j2;
        this.eventDispatcher.bandwidthSample(i, j, j2);
    }

    private long getInitialBitrateEstimateForNetworkType(int i) {
        Long l = (Long) this.initialBitrateEstimates.get(Integer.valueOf(i));
        if (l == null) {
            l = (Long) this.initialBitrateEstimates.get(0);
        }
        if (l == null) {
            l = 1000000L;
        }
        return l.longValue();
    }

    private static boolean isTransferAtFullNetworkSpeed(DataSpec dataSpec, boolean z) {
        if (z) {
            return dataSpec == null || !dataSpec.isFlagSet(8);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static int[] getInitialBitrateCountryGroupAssignment(String str) {
        str.getClass();
        char c = 65535;
        switch (str.hashCode()) {
            case 2083:
                if (str.equals("AD")) {
                    c = 0;
                    break;
                }
                break;
            case 2084:
                if (str.equals("AE")) {
                    c = 1;
                    break;
                }
                break;
            case 2085:
                if (str.equals("AF")) {
                    c = 2;
                    break;
                }
                break;
            case 2086:
                if (str.equals("AG")) {
                    c = 3;
                    break;
                }
                break;
            case 2088:
                if (str.equals("AI")) {
                    c = 4;
                    break;
                }
                break;
            case 2091:
                if (str.equals("AL")) {
                    c = 5;
                    break;
                }
                break;
            case 2092:
                if (str.equals("AM")) {
                    c = 6;
                    break;
                }
                break;
            case 2094:
                if (str.equals("AO")) {
                    c = 7;
                    break;
                }
                break;
            case 2096:
                if (str.equals("AQ")) {
                    c = '\b';
                    break;
                }
                break;
            case 2098:
                if (str.equals("AS")) {
                    c = '\t';
                    break;
                }
                break;
            case 2099:
                if (str.equals("AT")) {
                    c = '\n';
                    break;
                }
                break;
            case 2100:
                if (str.equals("AU")) {
                    c = 11;
                    break;
                }
                break;
            case 2102:
                if (str.equals("AW")) {
                    c = '\f';
                    break;
                }
                break;
            case 2103:
                if (str.equals("AX")) {
                    c = '\r';
                    break;
                }
                break;
            case 2105:
                if (str.equals("AZ")) {
                    c = 14;
                    break;
                }
                break;
            case 2111:
                if (str.equals("BA")) {
                    c = 15;
                    break;
                }
                break;
            case 2112:
                if (str.equals("BB")) {
                    c = 16;
                    break;
                }
                break;
            case 2114:
                if (str.equals("BD")) {
                    c = 17;
                    break;
                }
                break;
            case 2115:
                if (str.equals("BE")) {
                    c = 18;
                    break;
                }
                break;
            case 2116:
                if (str.equals("BF")) {
                    c = 19;
                    break;
                }
                break;
            case 2117:
                if (str.equals("BG")) {
                    c = 20;
                    break;
                }
                break;
            case 2118:
                if (str.equals("BH")) {
                    c = 21;
                    break;
                }
                break;
            case 2119:
                if (str.equals("BI")) {
                    c = 22;
                    break;
                }
                break;
            case 2120:
                if (str.equals("BJ")) {
                    c = 23;
                    break;
                }
                break;
            case 2122:
                if (str.equals("BL")) {
                    c = 24;
                    break;
                }
                break;
            case 2123:
                if (str.equals("BM")) {
                    c = 25;
                    break;
                }
                break;
            case 2124:
                if (str.equals("BN")) {
                    c = 26;
                    break;
                }
                break;
            case 2125:
                if (str.equals("BO")) {
                    c = 27;
                    break;
                }
                break;
            case 2127:
                if (str.equals("BQ")) {
                    c = 28;
                    break;
                }
                break;
            case 2128:
                if (str.equals("BR")) {
                    c = 29;
                    break;
                }
                break;
            case 2129:
                if (str.equals("BS")) {
                    c = 30;
                    break;
                }
                break;
            case 2130:
                if (str.equals("BT")) {
                    c = 31;
                    break;
                }
                break;
            case 2133:
                if (str.equals("BW")) {
                    c = ' ';
                    break;
                }
                break;
            case 2135:
                if (str.equals("BY")) {
                    c = '!';
                    break;
                }
                break;
            case 2136:
                if (str.equals("BZ")) {
                    c = '\"';
                    break;
                }
                break;
            case 2142:
                if (str.equals("CA")) {
                    c = '#';
                    break;
                }
                break;
            case 2145:
                if (str.equals("CD")) {
                    c = '$';
                    break;
                }
                break;
            case 2147:
                if (str.equals("CF")) {
                    c = '%';
                    break;
                }
                break;
            case 2148:
                if (str.equals("CG")) {
                    c = '&';
                    break;
                }
                break;
            case 2149:
                if (str.equals("CH")) {
                    c = '\'';
                    break;
                }
                break;
            case 2150:
                if (str.equals("CI")) {
                    c = '(';
                    break;
                }
                break;
            case 2152:
                if (str.equals("CK")) {
                    c = ')';
                    break;
                }
                break;
            case 2153:
                if (str.equals("CL")) {
                    c = '*';
                    break;
                }
                break;
            case 2154:
                if (str.equals("CM")) {
                    c = SignatureVisitor.EXTENDS;
                    break;
                }
                break;
            case 2155:
                if (str.equals("CN")) {
                    c = ',';
                    break;
                }
                break;
            case 2156:
                if (str.equals("CO")) {
                    c = SignatureVisitor.SUPER;
                    break;
                }
                break;
            case 2159:
                if (str.equals("CR")) {
                    c = '.';
                    break;
                }
                break;
            case 2162:
                if (str.equals("CU")) {
                    c = '/';
                    break;
                }
                break;
            case 2163:
                if (str.equals("CV")) {
                    c = '0';
                    break;
                }
                break;
            case 2164:
                if (str.equals("CW")) {
                    c = '1';
                    break;
                }
                break;
            case 2165:
                if (str.equals("CX")) {
                    c = '2';
                    break;
                }
                break;
            case 2166:
                if (str.equals("CY")) {
                    c = '3';
                    break;
                }
                break;
            case 2167:
                if (str.equals("CZ")) {
                    c = '4';
                    break;
                }
                break;
            case 2177:
                if (str.equals("DE")) {
                    c = '5';
                    break;
                }
                break;
            case 2182:
                if (str.equals("DJ")) {
                    c = '6';
                    break;
                }
                break;
            case 2183:
                if (str.equals("DK")) {
                    c = '7';
                    break;
                }
                break;
            case 2185:
                if (str.equals("DM")) {
                    c = '8';
                    break;
                }
                break;
            case 2187:
                if (str.equals("DO")) {
                    c = '9';
                    break;
                }
                break;
            case 2198:
                if (str.equals("DZ")) {
                    c = ':';
                    break;
                }
                break;
            case 2206:
                if (str.equals("EC")) {
                    c = ';';
                    break;
                }
                break;
            case 2208:
                if (str.equals("EE")) {
                    c = '<';
                    break;
                }
                break;
            case 2210:
                if (str.equals("EG")) {
                    c = SignatureVisitor.INSTANCEOF;
                    break;
                }
                break;
            case 2221:
                if (str.equals("ER")) {
                    c = '>';
                    break;
                }
                break;
            case 2222:
                if (str.equals("ES")) {
                    c = '?';
                    break;
                }
                break;
            case 2223:
                if (str.equals("ET")) {
                    c = '@';
                    break;
                }
                break;
            case 2243:
                if (str.equals("FI")) {
                    c = 'A';
                    break;
                }
                break;
            case 2244:
                if (str.equals("FJ")) {
                    c = 'B';
                    break;
                }
                break;
            case 2247:
                if (str.equals("FM")) {
                    c = 'C';
                    break;
                }
                break;
            case 2249:
                if (str.equals("FO")) {
                    c = 'D';
                    break;
                }
                break;
            case 2252:
                if (str.equals("FR")) {
                    c = 'E';
                    break;
                }
                break;
            case 2266:
                if (str.equals("GA")) {
                    c = 'F';
                    break;
                }
                break;
            case 2267:
                if (str.equals("GB")) {
                    c = 'G';
                    break;
                }
                break;
            case 2269:
                if (str.equals("GD")) {
                    c = 'H';
                    break;
                }
                break;
            case 2270:
                if (str.equals("GE")) {
                    c = 'I';
                    break;
                }
                break;
            case 2271:
                if (str.equals("GF")) {
                    c = 'J';
                    break;
                }
                break;
            case 2272:
                if (str.equals("GG")) {
                    c = 'K';
                    break;
                }
                break;
            case 2273:
                if (str.equals("GH")) {
                    c = 'L';
                    break;
                }
                break;
            case 2274:
                if (str.equals("GI")) {
                    c = 'M';
                    break;
                }
                break;
            case 2277:
                if (str.equals("GL")) {
                    c = 'N';
                    break;
                }
                break;
            case 2278:
                if (str.equals("GM")) {
                    c = 'O';
                    break;
                }
                break;
            case 2279:
                if (str.equals("GN")) {
                    c = 'P';
                    break;
                }
                break;
            case 2281:
                if (str.equals("GP")) {
                    c = 'Q';
                    break;
                }
                break;
            case 2282:
                if (str.equals("GQ")) {
                    c = 'R';
                    break;
                }
                break;
            case 2283:
                if (str.equals("GR")) {
                    c = 'S';
                    break;
                }
                break;
            case 2285:
                if (str.equals("GT")) {
                    c = 'T';
                    break;
                }
                break;
            case 2286:
                if (str.equals("GU")) {
                    c = 'U';
                    break;
                }
                break;
            case 2288:
                if (str.equals("GW")) {
                    c = 'V';
                    break;
                }
                break;
            case 2290:
                if (str.equals("GY")) {
                    c = 'W';
                    break;
                }
                break;
            case 2307:
                if (str.equals("HK")) {
                    c = 'X';
                    break;
                }
                break;
            case 2310:
                if (str.equals("HN")) {
                    c = 'Y';
                    break;
                }
                break;
            case 2314:
                if (str.equals("HR")) {
                    c = 'Z';
                    break;
                }
                break;
            case 2316:
                if (str.equals("HT")) {
                    c = '[';
                    break;
                }
                break;
            case 2317:
                if (str.equals("HU")) {
                    c = '\\';
                    break;
                }
                break;
            case 2331:
                if (str.equals("ID")) {
                    c = ']';
                    break;
                }
                break;
            case 2332:
                if (str.equals("IE")) {
                    c = '^';
                    break;
                }
                break;
            case 2339:
                if (str.equals("IL")) {
                    c = '_';
                    break;
                }
                break;
            case 2340:
                if (str.equals("IM")) {
                    c = '`';
                    break;
                }
                break;
            case 2341:
                if (str.equals("IN")) {
                    c = 'a';
                    break;
                }
                break;
            case 2342:
                if (str.equals("IO")) {
                    c = 'b';
                    break;
                }
                break;
            case 2344:
                if (str.equals("IQ")) {
                    c = 'c';
                    break;
                }
                break;
            case 2345:
                if (str.equals("IR")) {
                    c = 'd';
                    break;
                }
                break;
            case 2346:
                if (str.equals("IS")) {
                    c = 'e';
                    break;
                }
                break;
            case 2347:
                if (str.equals("IT")) {
                    c = 'f';
                    break;
                }
                break;
            case 2363:
                if (str.equals("JE")) {
                    c = 'g';
                    break;
                }
                break;
            case 2371:
                if (str.equals("JM")) {
                    c = 'h';
                    break;
                }
                break;
            case 2373:
                if (str.equals("JO")) {
                    c = 'i';
                    break;
                }
                break;
            case 2374:
                if (str.equals("JP")) {
                    c = 'j';
                    break;
                }
                break;
            case 2394:
                if (str.equals("KE")) {
                    c = 'k';
                    break;
                }
                break;
            case 2396:
                if (str.equals("KG")) {
                    c = 'l';
                    break;
                }
                break;
            case 2397:
                if (str.equals("KH")) {
                    c = 'm';
                    break;
                }
                break;
            case 2398:
                if (str.equals("KI")) {
                    c = 'n';
                    break;
                }
                break;
            case 2402:
                if (str.equals("KM")) {
                    c = 'o';
                    break;
                }
                break;
            case 2403:
                if (str.equals("KN")) {
                    c = 'p';
                    break;
                }
                break;
            case 2407:
                if (str.equals("KR")) {
                    c = 'q';
                    break;
                }
                break;
            case 2412:
                if (str.equals("KW")) {
                    c = 'r';
                    break;
                }
                break;
            case 2414:
                if (str.equals("KY")) {
                    c = 's';
                    break;
                }
                break;
            case 2415:
                if (str.equals("KZ")) {
                    c = 't';
                    break;
                }
                break;
            case 2421:
                if (str.equals("LA")) {
                    c = 'u';
                    break;
                }
                break;
            case 2422:
                if (str.equals("LB")) {
                    c = 'v';
                    break;
                }
                break;
            case 2423:
                if (str.equals("LC")) {
                    c = 'w';
                    break;
                }
                break;
            case 2429:
                if (str.equals("LI")) {
                    c = 'x';
                    break;
                }
                break;
            case 2431:
                if (str.equals("LK")) {
                    c = 'y';
                    break;
                }
                break;
            case 2438:
                if (str.equals("LR")) {
                    c = 'z';
                    break;
                }
                break;
            case 2439:
                if (str.equals("LS")) {
                    c = '{';
                    break;
                }
                break;
            case 2440:
                if (str.equals("LT")) {
                    c = '|';
                    break;
                }
                break;
            case 2441:
                if (str.equals("LU")) {
                    c = '}';
                    break;
                }
                break;
            case 2442:
                if (str.equals("LV")) {
                    c = '~';
                    break;
                }
                break;
            case 2445:
                if (str.equals("LY")) {
                    c = 127;
                    break;
                }
                break;
            case 2452:
                if (str.equals("MA")) {
                    c = 128;
                    break;
                }
                break;
            case 2454:
                if (str.equals("MC")) {
                    c = 129;
                    break;
                }
                break;
            case 2455:
                if (str.equals("MD")) {
                    c = 130;
                    break;
                }
                break;
            case 2456:
                if (str.equals("ME")) {
                    c = 131;
                    break;
                }
                break;
            case 2457:
                if (str.equals("MF")) {
                    c = 132;
                    break;
                }
                break;
            case 2458:
                if (str.equals("MG")) {
                    c = 133;
                    break;
                }
                break;
            case 2459:
                if (str.equals("MH")) {
                    c = 134;
                    break;
                }
                break;
            case 2462:
                if (str.equals("MK")) {
                    c = 135;
                    break;
                }
                break;
            case 2463:
                if (str.equals("ML")) {
                    c = 136;
                    break;
                }
                break;
            case 2464:
                if (str.equals("MM")) {
                    c = 137;
                    break;
                }
                break;
            case 2465:
                if (str.equals("MN")) {
                    c = 138;
                    break;
                }
                break;
            case 2466:
                if (str.equals("MO")) {
                    c = 139;
                    break;
                }
                break;
            case 2467:
                if (str.equals("MP")) {
                    c = 140;
                    break;
                }
                break;
            case 2468:
                if (str.equals("MQ")) {
                    c = 141;
                    break;
                }
                break;
            case 2469:
                if (str.equals("MR")) {
                    c = 142;
                    break;
                }
                break;
            case 2470:
                if (str.equals("MS")) {
                    c = 143;
                    break;
                }
                break;
            case 2471:
                if (str.equals("MT")) {
                    c = 144;
                    break;
                }
                break;
            case 2472:
                if (str.equals("MU")) {
                    c = 145;
                    break;
                }
                break;
            case 2473:
                if (str.equals("MV")) {
                    c = 146;
                    break;
                }
                break;
            case 2474:
                if (str.equals("MW")) {
                    c = 147;
                    break;
                }
                break;
            case 2475:
                if (str.equals("MX")) {
                    c = 148;
                    break;
                }
                break;
            case 2476:
                if (str.equals("MY")) {
                    c = 149;
                    break;
                }
                break;
            case 2477:
                if (str.equals("MZ")) {
                    c = 150;
                    break;
                }
                break;
            case 2483:
                if (str.equals("NA")) {
                    c = 151;
                    break;
                }
                break;
            case 2485:
                if (str.equals("NC")) {
                    c = 152;
                    break;
                }
                break;
            case 2487:
                if (str.equals("NE")) {
                    c = 153;
                    break;
                }
                break;
            case 2489:
                if (str.equals("NG")) {
                    c = 154;
                    break;
                }
                break;
            case 2491:
                if (str.equals("NI")) {
                    c = 155;
                    break;
                }
                break;
            case 2494:
                if (str.equals("NL")) {
                    c = 156;
                    break;
                }
                break;
            case 2497:
                if (str.equals("NO")) {
                    c = 157;
                    break;
                }
                break;
            case 2498:
                if (str.equals("NP")) {
                    c = 158;
                    break;
                }
                break;
            case 2500:
                if (str.equals("NR")) {
                    c = 159;
                    break;
                }
                break;
            case 2503:
                if (str.equals("NU")) {
                    c = 160;
                    break;
                }
                break;
            case 2508:
                if (str.equals("NZ")) {
                    c = 161;
                    break;
                }
                break;
            case 2526:
                if (str.equals("OM")) {
                    c = 162;
                    break;
                }
                break;
            case 2545:
                if (str.equals("PA")) {
                    c = 163;
                    break;
                }
                break;
            case 2549:
                if (str.equals("PE")) {
                    c = 164;
                    break;
                }
                break;
            case 2550:
                if (str.equals("PF")) {
                    c = 165;
                    break;
                }
                break;
            case 2551:
                if (str.equals("PG")) {
                    c = 166;
                    break;
                }
                break;
            case 2552:
                if (str.equals("PH")) {
                    c = 167;
                    break;
                }
                break;
            case 2555:
                if (str.equals("PK")) {
                    c = 168;
                    break;
                }
                break;
            case 2556:
                if (str.equals("PL")) {
                    c = 169;
                    break;
                }
                break;
            case 2557:
                if (str.equals("PM")) {
                    c = 170;
                    break;
                }
                break;
            case 2562:
                if (str.equals("PR")) {
                    c = 171;
                    break;
                }
                break;
            case 2563:
                if (str.equals("PS")) {
                    c = 172;
                    break;
                }
                break;
            case 2564:
                if (str.equals("PT")) {
                    c = 173;
                    break;
                }
                break;
            case 2567:
                if (str.equals("PW")) {
                    c = 174;
                    break;
                }
                break;
            case 2569:
                if (str.equals("PY")) {
                    c = 175;
                    break;
                }
                break;
            case 2576:
                if (str.equals("QA")) {
                    c = 176;
                    break;
                }
                break;
            case 2611:
                if (str.equals("RE")) {
                    c = 177;
                    break;
                }
                break;
            case 2621:
                if (str.equals("RO")) {
                    c = 178;
                    break;
                }
                break;
            case 2625:
                if (str.equals("RS")) {
                    c = 179;
                    break;
                }
                break;
            case 2627:
                if (str.equals("RU")) {
                    c = 180;
                    break;
                }
                break;
            case 2629:
                if (str.equals("RW")) {
                    c = 181;
                    break;
                }
                break;
            case 2638:
                if (str.equals("SA")) {
                    c = 182;
                    break;
                }
                break;
            case 2639:
                if (str.equals("SB")) {
                    c = 183;
                    break;
                }
                break;
            case 2640:
                if (str.equals("SC")) {
                    c = 184;
                    break;
                }
                break;
            case 2641:
                if (str.equals("SD")) {
                    c = 185;
                    break;
                }
                break;
            case 2642:
                if (str.equals("SE")) {
                    c = 186;
                    break;
                }
                break;
            case 2644:
                if (str.equals("SG")) {
                    c = 187;
                    break;
                }
                break;
            case 2645:
                if (str.equals("SH")) {
                    c = 188;
                    break;
                }
                break;
            case 2646:
                if (str.equals("SI")) {
                    c = 189;
                    break;
                }
                break;
            case 2647:
                if (str.equals("SJ")) {
                    c = 190;
                    break;
                }
                break;
            case 2648:
                if (str.equals("SK")) {
                    c = 191;
                    break;
                }
                break;
            case 2649:
                if (str.equals("SL")) {
                    c = 192;
                    break;
                }
                break;
            case 2650:
                if (str.equals("SM")) {
                    c = 193;
                    break;
                }
                break;
            case 2651:
                if (str.equals("SN")) {
                    c = 194;
                    break;
                }
                break;
            case 2652:
                if (str.equals("SO")) {
                    c = 195;
                    break;
                }
                break;
            case 2655:
                if (str.equals("SR")) {
                    c = 196;
                    break;
                }
                break;
            case 2656:
                if (str.equals("SS")) {
                    c = 197;
                    break;
                }
                break;
            case 2657:
                if (str.equals("ST")) {
                    c = 198;
                    break;
                }
                break;
            case 2659:
                if (str.equals("SV")) {
                    c = 199;
                    break;
                }
                break;
            case 2661:
                if (str.equals("SX")) {
                    c = 200;
                    break;
                }
                break;
            case 2662:
                if (str.equals("SY")) {
                    c = 201;
                    break;
                }
                break;
            case 2663:
                if (str.equals("SZ")) {
                    c = 202;
                    break;
                }
                break;
            case 2671:
                if (str.equals("TC")) {
                    c = 203;
                    break;
                }
                break;
            case 2672:
                if (str.equals("TD")) {
                    c = 204;
                    break;
                }
                break;
            case 2675:
                if (str.equals("TG")) {
                    c = 205;
                    break;
                }
                break;
            case 2676:
                if (str.equals("TH")) {
                    c = 206;
                    break;
                }
                break;
            case 2678:
                if (str.equals("TJ")) {
                    c = 207;
                    break;
                }
                break;
            case 2679:
                if (str.equals("TK")) {
                    c = 208;
                    break;
                }
                break;
            case 2680:
                if (str.equals("TL")) {
                    c = 209;
                    break;
                }
                break;
            case 2681:
                if (str.equals("TM")) {
                    c = 210;
                    break;
                }
                break;
            case 2682:
                if (str.equals("TN")) {
                    c = 211;
                    break;
                }
                break;
            case 2683:
                if (str.equals("TO")) {
                    c = 212;
                    break;
                }
                break;
            case 2686:
                if (str.equals("TR")) {
                    c = 213;
                    break;
                }
                break;
            case 2688:
                if (str.equals("TT")) {
                    c = 214;
                    break;
                }
                break;
            case 2690:
                if (str.equals("TV")) {
                    c = 215;
                    break;
                }
                break;
            case 2691:
                if (str.equals("TW")) {
                    c = 216;
                    break;
                }
                break;
            case 2694:
                if (str.equals("TZ")) {
                    c = 217;
                    break;
                }
                break;
            case 2700:
                if (str.equals("UA")) {
                    c = 218;
                    break;
                }
                break;
            case 2706:
                if (str.equals("UG")) {
                    c = 219;
                    break;
                }
                break;
            case 2718:
                if (str.equals("US")) {
                    c = 220;
                    break;
                }
                break;
            case 2724:
                if (str.equals("UY")) {
                    c = 221;
                    break;
                }
                break;
            case 2725:
                if (str.equals("UZ")) {
                    c = 222;
                    break;
                }
                break;
            case 2731:
                if (str.equals("VA")) {
                    c = 223;
                    break;
                }
                break;
            case 2733:
                if (str.equals("VC")) {
                    c = 224;
                    break;
                }
                break;
            case 2735:
                if (str.equals("VE")) {
                    c = 225;
                    break;
                }
                break;
            case 2737:
                if (str.equals("VG")) {
                    c = 226;
                    break;
                }
                break;
            case 2739:
                if (str.equals("VI")) {
                    c = 227;
                    break;
                }
                break;
            case 2744:
                if (str.equals("VN")) {
                    c = 228;
                    break;
                }
                break;
            case 2751:
                if (str.equals("VU")) {
                    c = 229;
                    break;
                }
                break;
            case 2767:
                if (str.equals("WF")) {
                    c = 230;
                    break;
                }
                break;
            case 2780:
                if (str.equals("WS")) {
                    c = 231;
                    break;
                }
                break;
            case 2803:
                if (str.equals("XK")) {
                    c = 232;
                    break;
                }
                break;
            case 2828:
                if (str.equals("YE")) {
                    c = 233;
                    break;
                }
                break;
            case 2843:
                if (str.equals("YT")) {
                    c = 234;
                    break;
                }
                break;
            case 2855:
                if (str.equals("ZA")) {
                    c = 235;
                    break;
                }
                break;
            case 2867:
                if (str.equals("ZM")) {
                    c = 236;
                    break;
                }
                break;
            case 2877:
                if (str.equals("ZW")) {
                    c = 237;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case '1':
                return new int[]{2, 2, 0, 0, 2, 2};
            case 1:
                return new int[]{1, 4, 3, 4, 4, 2};
            case 2:
            case Opcodes.IF_ACMPNE /* 166 */:
                return new int[]{4, 3, 3, 3, 2, 2};
            case 3:
                return new int[]{2, 4, 3, 4, 2, 2};
            case 4:
            case 16:
            case 25:
            case 28:
            case '8':
            case TypeReference.NEW /* 68 */:
                return new int[]{0, 2, 0, 0, 2, 2};
            case 5:
                return new int[]{1, 1, 1, 3, 2, 2};
            case 6:
                return new int[]{2, 3, 2, 3, 2, 2};
            case 7:
                return new int[]{4, 4, 4, 3, 2, 2};
            case '\b':
            case '>':
            case Opcodes.NEWARRAY /* 188 */:
                return new int[]{4, 2, 2, 2, 2, 2};
            case '\t':
                return new int[]{2, 2, 3, 3, 2, 2};
            case '\n':
                return new int[]{1, 2, 1, 4, 1, 4};
            case 11:
                return new int[]{0, 2, 1, 1, 3, 0};
            case '\f':
            case Opcodes.CASTORE /* 85 */:
                return new int[]{1, 2, 4, 4, 2, 2};
            case '\r':
            case '2':
            case Opcodes.ISHL /* 120 */:
            case Opcodes.F2L /* 140 */:
            case Opcodes.D2L /* 143 */:
            case Opcodes.TABLESWITCH /* 170 */:
            case Opcodes.INSTANCEOF /* 193 */:
            case 223:
                return new int[]{0, 2, 2, 2, 2, 2};
            case 14:
            case 19:
            case ':':
                return new int[]{3, 3, 4, 4, 2, 2};
            case 15:
            case Opcodes.DUP2_X2 /* 94 */:
                return new int[]{1, 1, 1, 1, 2, 2};
            case 17:
            case 't':
                return new int[]{2, 1, 2, 2, 2, 2};
            case 18:
                return new int[]{0, 1, 4, 4, 3, 2};
            case 20:
            case '?':
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.ANEWARRAY /* 189 */:
                return new int[]{0, 0, 0, 0, 1, 2};
            case 21:
                return new int[]{1, 3, 1, 4, 4, 2};
            case 22:
            case Opcodes.DUP_X2 /* 91 */:
            case Opcodes.I2L /* 133 */:
            case Opcodes.IFEQ /* 153 */:
            case 204:
            case 225:
            case 233:
                return new int[]{4, 4, 4, 4, 2, 2};
            case 23:
                return new int[]{4, 4, 2, 3, 2, 2};
            case 24:
            case Opcodes.IINC /* 132 */:
            case Opcodes.DRETURN /* 175 */:
                return new int[]{1, 2, 2, 2, 2, 2};
            case 26:
                return new int[]{3, 2, 0, 1, 2, 2};
            case 27:
                return new int[]{1, 2, 3, 2, 2, 2};
            case 29:
                return new int[]{1, 1, 2, 1, 1, 0};
            case 30:
            case Opcodes.FNEG /* 118 */:
                return new int[]{3, 2, 1, 2, 2, 2};
            case 31:
            case 150:
            case 231:
                return new int[]{3, 1, 2, 1, 2, 2};
            case ' ':
                return new int[]{3, 2, 1, 0, 2, 2};
            case '!':
                return new int[]{1, 1, 2, 3, 2, 2};
            case '\"':
            case Operator.WHILE /* 41 */:
                return new int[]{2, 2, 2, 1, 2, 2};
            case Operator.PROJECTION /* 35 */:
                return new int[]{0, 2, 3, 3, 3, 3};
            case Operator.CONVERTABLE_TO /* 36 */:
            case 'o':
                return new int[]{4, 3, 3, 2, 2, 2};
            case Operator.END_OF_STMT /* 37 */:
            case Opcodes.INVOKESPECIAL /* 183 */:
                return new int[]{4, 2, 4, 2, 2, 2};
            case Operator.FOREACH /* 38 */:
            case 'L':
                return new int[]{3, 3, 3, 3, 2, 2};
            case Operator.f1408IF /* 39 */:
                return new int[]{0, 0, 0, 0, 0, 3};
            case Operator.ELSE /* 40 */:
            case '=':
                return new int[]{3, 4, 3, 3, 2, 2};
            case Operator.UNTIL /* 42 */:
                return new int[]{1, 1, 2, 1, 3, 2};
            case Operator.FOR /* 43 */:
                return new int[]{4, 3, 3, 4, 2, 2};
            case Operator.SWITCH /* 44 */:
                return new int[]{2, 0, 4, 3, 3, 1};
            case Operator.f1407DO /* 45 */:
                return new int[]{2, 3, 4, 2, 2, 2};
            case '.':
                return new int[]{2, 4, 4, 4, 2, 2};
            case '/':
            case 'n':
                return new int[]{4, 2, 4, 3, 2, 2};
            case '0':
                return new int[]{2, 3, 0, 1, 2, 2};
            case '3':
            case Opcodes.DUP_X1 /* 90 */:
            case Opcodes.IAND /* 126 */:
                return new int[]{1, 0, 0, 0, 0, 2};
            case '4':
                return new int[]{0, 0, 2, 0, 1, 2};
            case '5':
                return new int[]{0, 1, 3, 2, 2, 2};
            case '6':
            case 201:
            case 207:
                return new int[]{4, 3, 4, 4, 2, 2};
            case '7':
            case '<':
            case '\\':
            case Opcodes.IUSHR /* 124 */:
            case Opcodes.D2F /* 144 */:
                return new int[]{0, 0, 0, 0, 0, 2};
            case '9':
                return new int[]{3, 4, 4, 4, 4, 2};
            case ';':
                return new int[]{1, 3, 2, 1, 2, 2};
            case '@':
            case Opcodes.MONITORENTER /* 194 */:
                return new int[]{4, 4, 3, 2, 2, 2};
            case 'A':
                return new int[]{0, 0, 0, 2, 0, 2};
            case TypeReference.EXCEPTION_PARAMETER /* 66 */:
                return new int[]{3, 1, 2, 3, 2, 2};
            case TypeReference.INSTANCEOF /* 67 */:
                return new int[]{4, 2, 3, 0, 2, 2};
            case TypeReference.CONSTRUCTOR_REFERENCE /* 69 */:
                return new int[]{1, 1, 2, 1, 1, 2};
            case 'F':
            case 205:
                return new int[]{3, 4, 1, 0, 2, 2};
            case TypeReference.CAST /* 71 */:
                return new int[]{0, 1, 1, 2, 1, 2};
            case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
            case 'p':
            case 's':
            case Opcodes.DNEG /* 119 */:
            case DataTypes.EMPTY /* 200 */:
            case 224:
                return new int[]{1, 2, 0, 0, 2, 2};
            case TypeReference.METHOD_INVOCATION_TYPE_ARGUMENT /* 73 */:
                return new int[]{1, 0, 0, 2, 2, 2};
            case TypeReference.CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT /* 74 */:
            case 168:
            case Opcodes.CHECKCAST /* 192 */:
                return new int[]{3, 2, 3, 3, 2, 2};
            case TypeReference.METHOD_REFERENCE_TYPE_ARGUMENT /* 75 */:
                return new int[]{0, 2, 1, 0, 2, 2};
            case 'M':
            case 'g':
                return new int[]{1, 2, 0, 1, 2, 2};
            case 'N':
            case 208:
                return new int[]{2, 2, 2, 4, 2, 2};
            case Opcodes.IASTORE /* 79 */:
                return new int[]{4, 3, 2, 4, 2, 2};
            case 'P':
                return new int[]{4, 4, 4, 2, 2, 2};
            case Opcodes.FASTORE /* 81 */:
                return new int[]{3, 1, 1, 3, 2, 2};
            case Opcodes.DASTORE /* 82 */:
                return new int[]{4, 4, 3, 3, 2, 2};
            case Opcodes.BASTORE /* 84 */:
                return new int[]{2, 2, 2, 1, 1, 2};
            case Opcodes.SASTORE /* 86 */:
                return new int[]{4, 4, 2, 2, 2, 2};
            case Opcodes.POP /* 87 */:
                return new int[]{3, 0, 1, 1, 2, 2};
            case Opcodes.POP2 /* 88 */:
                return new int[]{0, 1, 1, 3, 2, 0};
            case Opcodes.DUP /* 89 */:
                return new int[]{3, 3, 2, 2, 2, 2};
            case Opcodes.DUP2_X1 /* 93 */:
                return new int[]{3, 1, 1, 2, 3, 2};
            case '_':
                return new int[]{1, 2, 2, 3, 4, 2};
            case '`':
                return new int[]{0, 2, 0, 1, 2, 2};
            case 'a':
                return new int[]{1, 1, 2, 1, 2, 1};
            case 'b':
            case 215:
            case 230:
                return new int[]{4, 2, 2, 4, 2, 2};
            case 'c':
            case Opcodes.ARRAYLENGTH /* 190 */:
                return new int[]{3, 2, 2, 2, 2, 2};
            case 'd':
                return new int[]{4, 2, 3, 3, 4, 2};
            case 'e':
                return new int[]{0, 0, 1, 0, 0, 2};
            case 'f':
                return new int[]{0, 0, 1, 1, 1, 2};
            case 'h':
                return new int[]{2, 4, 2, 1, 2, 2};
            case 'i':
                return new int[]{2, 0, 1, 1, 2, 2};
            case 'j':
                return new int[]{0, 3, 3, 3, 4, 4};
            case 'k':
                return new int[]{3, 2, 2, 1, 2, 2};
            case 'l':
            case Opcodes.F2D /* 141 */:
                return new int[]{2, 1, 1, 2, 2, 2};
            case 'm':
                return new int[]{1, 0, 4, 2, 2, 2};
            case 'q':
                return new int[]{0, 2, 2, 4, 4, 4};
            case 'r':
                return new int[]{1, 0, 1, 0, 0, 2};
            case Opcodes.LNEG /* 117 */:
                return new int[]{1, 2, 1, 3, 2, 2};
            case Opcodes.LSHL /* 121 */:
                return new int[]{3, 2, 3, 4, 4, 2};
            case Opcodes.ISHR /* 122 */:
                return new int[]{3, 4, 3, 4, 2, 2};
            case Opcodes.LSHR /* 123 */:
            case 219:
                return new int[]{3, 3, 3, 2, 2, 2};
            case Opcodes.LUSHR /* 125 */:
                return new int[]{1, 1, 4, 2, 0, 2};
            case Opcodes.LAND /* 127 */:
            case 212:
            case 237:
                return new int[]{3, 2, 4, 3, 2, 2};
            case 128:
                return new int[]{3, 3, 2, 1, 2, 2};
            case Opcodes.LOR /* 129 */:
                return new int[]{0, 2, 2, 0, 2, 2};
            case Opcodes.IXOR /* 130 */:
                return new int[]{1, 0, 0, 0, 2, 2};
            case Opcodes.LXOR /* 131 */:
                return new int[]{2, 0, 0, 1, 1, 2};
            case Opcodes.I2F /* 134 */:
                return new int[]{4, 2, 1, 3, 2, 2};
            case Opcodes.I2D /* 135 */:
                return new int[]{2, 0, 0, 1, 3, 2};
            case Opcodes.L2I /* 136 */:
            case 217:
                return new int[]{3, 4, 2, 2, 2, 2};
            case Opcodes.L2F /* 137 */:
                return new int[]{2, 2, 2, 3, 4, 2};
            case Opcodes.L2D /* 138 */:
                return new int[]{2, 0, 1, 2, 2, 2};
            case Opcodes.F2I /* 139 */:
                return new int[]{0, 2, 4, 4, 4, 2};
            case Opcodes.D2I /* 142 */:
                return new int[]{4, 2, 3, 4, 2, 2};
            case Opcodes.I2B /* 145 */:
            case Opcodes.INVOKEVIRTUAL /* 182 */:
                return new int[]{3, 1, 1, 2, 2, 2};
            case Opcodes.I2C /* 146 */:
                return new int[]{3, 4, 1, 3, 3, 2};
            case Opcodes.I2S /* 147 */:
                return new int[]{4, 2, 3, 3, 2, 2};
            case Opcodes.LCMP /* 148 */:
                return new int[]{3, 4, 4, 4, 2, 2};
            case Opcodes.FCMPL /* 149 */:
                return new int[]{1, 0, 4, 1, 2, 2};
            case Opcodes.DCMPL /* 151 */:
                return new int[]{3, 4, 3, 2, 2, 2};
            case Opcodes.DCMPG /* 152 */:
                return new int[]{3, 2, 3, 4, 2, 2};
            case Opcodes.IFNE /* 154 */:
                return new int[]{3, 4, 2, 1, 2, 2};
            case Opcodes.IFLT /* 155 */:
                return new int[]{2, 3, 4, 3, 2, 2};
            case Opcodes.IFGE /* 156 */:
                return new int[]{0, 2, 3, 3, 0, 4};
            case Opcodes.IFGT /* 157 */:
                return new int[]{0, 1, 2, 1, 1, 2};
            case Opcodes.IFLE /* 158 */:
                return new int[]{2, 1, 4, 3, 2, 2};
            case Opcodes.IF_ICMPEQ /* 159 */:
                return new int[]{4, 0, 3, 2, 2, 2};
            case Opcodes.IF_ICMPNE /* 160 */:
                return new int[]{4, 2, 2, 1, 2, 2};
            case Opcodes.IF_ICMPLT /* 161 */:
                return new int[]{1, 0, 2, 2, 4, 2};
            case Opcodes.IF_ICMPGE /* 162 */:
                return new int[]{2, 3, 1, 3, 4, 2};
            case Opcodes.IF_ICMPGT /* 163 */:
                return new int[]{2, 3, 3, 3, 2, 2};
            case Opcodes.IF_ICMPLE /* 164 */:
                return new int[]{1, 2, 4, 4, 3, 2};
            case Opcodes.IF_ACMPEQ /* 165 */:
            case Opcodes.IFNONNULL /* 199 */:
                return new int[]{2, 3, 3, 1, 2, 2};
            case Opcodes.GOTO /* 167 */:
                return new int[]{2, 1, 3, 2, 2, 0};
            case Opcodes.RET /* 169 */:
                return new int[]{2, 1, 2, 2, 4, 2};
            case Opcodes.LOOKUPSWITCH /* 171 */:
                return new int[]{2, 0, 2, 0, 2, 1};
            case Opcodes.IRETURN /* 172 */:
                return new int[]{3, 4, 1, 4, 2, 2};
            case Opcodes.LRETURN /* 173 */:
                return new int[]{1, 0, 0, 0, 1, 2};
            case Opcodes.FRETURN /* 174 */:
                return new int[]{2, 2, 4, 2, 2, 2};
            case Opcodes.ARETURN /* 176 */:
                return new int[]{1, 4, 4, 4, 4, 2};
            case Opcodes.RETURN /* 177 */:
                return new int[]{1, 2, 2, 3, 1, 2};
            case Opcodes.GETSTATIC /* 178 */:
                return new int[]{0, 0, 1, 2, 1, 2};
            case Opcodes.PUTSTATIC /* 179 */:
                return new int[]{2, 0, 0, 0, 2, 2};
            case Opcodes.GETFIELD /* 180 */:
                return new int[]{1, 0, 0, 0, 3, 3};
            case Opcodes.PUTFIELD /* 181 */:
                return new int[]{3, 3, 1, 0, 2, 2};
            case Opcodes.INVOKESTATIC /* 184 */:
                return new int[]{4, 3, 1, 1, 2, 2};
            case Opcodes.INVOKEINTERFACE /* 185 */:
                return new int[]{4, 3, 4, 2, 2, 2};
            case Opcodes.INVOKEDYNAMIC /* 186 */:
                return new int[]{0, 1, 1, 1, 0, 2};
            case Opcodes.NEW /* 187 */:
                return new int[]{2, 3, 3, 3, 3, 3};
            case Opcodes.ATHROW /* 191 */:
                return new int[]{1, 1, 1, 1, 3, 2};
            case Opcodes.MONITOREXIT /* 195 */:
                return new int[]{3, 2, 2, 4, 4, 2};
            case 196:
                return new int[]{2, 4, 3, 0, 2, 2};
            case Opcodes.MULTIANEWARRAY /* 197 */:
            case 210:
                return new int[]{4, 2, 2, 3, 2, 2};
            case Opcodes.IFNULL /* 198 */:
                return new int[]{2, 2, 1, 2, 2, 2};
            case 202:
                return new int[]{4, 4, 3, 4, 2, 2};
            case VoIPService.ID_INCOMING_CALL_PRENOTIFICATION /* 203 */:
                return new int[]{2, 2, 1, 3, 2, 2};
            case 206:
                return new int[]{0, 1, 2, 1, 2, 2};
            case 209:
                return new int[]{4, 2, 4, 4, 2, 2};
            case 211:
            case 221:
                return new int[]{2, 1, 1, 1, 2, 2};
            case 213:
                return new int[]{1, 0, 0, 1, 3, 2};
            case 214:
                return new int[]{1, 4, 0, 0, 2, 2};
            case 216:
                return new int[]{0, 2, 0, 0, 0, 0};
            case 218:
                return new int[]{0, 1, 1, 2, 4, 2};
            case 220:
                return new int[]{1, 1, 4, 1, 3, 1};
            case 222:
                return new int[]{2, 2, 3, 4, 3, 2};
            case 226:
                return new int[]{2, 2, 0, 1, 2, 2};
            case 227:
                return new int[]{0, 2, 1, 2, 2, 2};
            case 228:
                return new int[]{0, 0, 1, 2, 2, 1};
            case 229:
                return new int[]{4, 3, 3, 1, 2, 2};
            case 232:
                return new int[]{1, 2, 1, 1, 2, 2};
            case 234:
                return new int[]{2, 3, 3, 4, 2, 2};
            case 235:
                return new int[]{2, 3, 2, 1, 2, 2};
            case 236:
                return new int[]{4, 4, 4, 3, 3, 2};
            default:
                return new int[]{2, 2, 2, 2, 2, 2};
        }
    }
}
