package org.telegram.messenger;

import android.content.SharedPreferences;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.exteragram.messenger.ExteraConfig;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes4.dex */
public class FileUploadOperation {
    private static final int initialRequestsBoostCount = 14;
    private static final int initialRequestsCount = 8;
    private static final int initialRequestsSlowNetworkCount = 1;
    private static final int maxUploadingKBytes = 2048;
    private static final int maxUploadingSlowNetworkKBytes = 32;
    private static final int minUploadChunkBoostSize = 512;
    private static final int minUploadChunkSize = 128;
    private static final int minUploadChunkSlowNetworkSize = 32;
    private long availableSize;
    public volatile boolean caughtPremiumFloodWait;
    private int currentAccount;
    private long currentFileId;
    private int currentPartNum;
    private int currentType;
    private int currentUploadRequetsCount;
    private FileUploadOperationDelegate delegate;
    private long estimatedSize;
    private String fileKey;
    private int fingerprint;
    private boolean forceSmallFile;
    private ArrayList<byte[]> freeRequestIvs;
    private boolean isBigFile;
    private boolean isEncrypted;
    private boolean isLastPart;

    /* renamed from: iv */
    private byte[] f1442iv;
    private byte[] ivChange;
    private byte[] key;
    protected long lastProgressUpdateTime;
    private int lastSavedPartNum;
    private int maxRequestsCount;
    private boolean nextPartFirst;
    private int operationGuid;
    private SharedPreferences preferences;
    private byte[] readBuffer;
    private long readBytesCount;
    private int requestNum;
    private int saveInfoTimes;
    private boolean slowNetwork;
    private boolean started;
    private int state;
    private RandomAccessFile stream;
    private long totalFileSize;
    private int totalPartsCount;
    private boolean uploadFirstPartLater;
    private int uploadStartTime;
    private long uploadedBytesCount;
    private String uploadingFilePath;
    private int uploadChunkSize = 65536;
    public final SparseIntArray requestTokens = new SparseIntArray();
    public final ArrayList<Integer> uiRequestTokens = new ArrayList<>();
    private SparseArray<UploadCachedResult> cachedResults = new SparseArray<>();
    private boolean[] recalculatedEstimatedSize = {false, false};

    public interface FileUploadOperationDelegate {
        void didChangedUploadProgress(FileUploadOperation fileUploadOperation, long j, long j2);

        void didFailedUploadingFile(FileUploadOperation fileUploadOperation);

        void didFinishUploadingFile(FileUploadOperation fileUploadOperation, TLRPC.InputFile inputFile, TLRPC.InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2);
    }

    private static class UploadCachedResult {
        private long bytesOffset;

        /* renamed from: iv */
        private byte[] f1443iv;

        private UploadCachedResult() {
        }
    }

    public FileUploadOperation(int i, String str, boolean z, long j, int i2) {
        this.currentAccount = i;
        this.uploadingFilePath = str;
        this.isEncrypted = z;
        this.estimatedSize = j;
        this.currentType = i2;
        this.uploadFirstPartLater = (j == 0 || z) ? false : true;
    }

    public long getTotalFileSize() {
        return this.totalFileSize;
    }

    public void setDelegate(FileUploadOperationDelegate fileUploadOperationDelegate) {
        this.delegate = fileUploadOperationDelegate;
    }

    public void start() {
        if (this.state != 0) {
            return;
        }
        this.state = 1;
        AutoDeleteMediaTask.lockFile(this.uploadingFilePath);
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$start$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0() throws Exception {
        this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
        this.slowNetwork = ApplicationLoader.isConnectionSlow();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d("start upload on slow network = " + this.slowNetwork);
        }
        int i = this.slowNetwork ? 1 : ExteraConfig.uploadSpeedBoost ? 14 : 8;
        for (int i2 = 0; i2 < i; i2++) {
            startUploadRequest();
        }
    }

    protected void onNetworkChanged(final boolean z) {
        if (this.state != 1) {
            return;
        }
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$onNetworkChanged$1(z);
            }
        });
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onNetworkChanged$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNetworkChanged$1(boolean z) throws Exception {
        if (this.slowNetwork != z) {
            this.slowNetwork = z;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1157d("network changed to slow = " + this.slowNetwork);
            }
            int i = 0;
            while (true) {
                if (i >= this.requestTokens.size()) {
                    break;
                }
                ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.requestTokens.valueAt(i), true);
                i++;
            }
            this.requestTokens.clear();
            cleanup();
            this.isLastPart = false;
            this.nextPartFirst = false;
            this.requestNum = 0;
            this.currentPartNum = 0;
            this.readBytesCount = 0L;
            this.uploadedBytesCount = 0L;
            this.saveInfoTimes = 0;
            this.key = null;
            this.f1442iv = null;
            this.ivChange = null;
            this.currentUploadRequetsCount = 0;
            this.lastSavedPartNum = 0;
            this.uploadFirstPartLater = false;
            this.cachedResults.clear();
            this.operationGuid++;
            int i2 = this.slowNetwork ? 1 : ExteraConfig.uploadSpeedBoost ? 14 : 8;
            for (int i3 = 0; i3 < i2; i3++) {
                startUploadRequest();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNetworkChanged$2() {
        this.uiRequestTokens.clear();
    }

    public void cancel() {
        if (this.state == 3) {
            return;
        }
        this.state = 2;
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$cancel$3();
            }
        });
        AutoDeleteMediaTask.unlockFile(this.uploadingFilePath);
        this.delegate.didFailedUploadingFile(this);
        cleanup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$3() {
        for (int i = 0; i < this.requestTokens.size(); i++) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.requestTokens.valueAt(i), true);
        }
    }

    private void cleanup() throws IOException {
        if (this.preferences == null) {
            this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
        }
        this.preferences.edit().remove(this.fileKey + "_time").remove(this.fileKey + "_size").remove(this.fileKey + "_uploaded").remove(this.fileKey + "_id").remove(this.fileKey + "_iv").remove(this.fileKey + "_key").remove(this.fileKey + "_ivc").apply();
        try {
            RandomAccessFile randomAccessFile = this.stream;
            if (randomAccessFile != null) {
                randomAccessFile.close();
                this.stream = null;
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        AutoDeleteMediaTask.unlockFile(this.uploadingFilePath);
    }

    protected void checkNewDataAvailable(final long j, final long j2, final Float f) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$checkNewDataAvailable$4(f, j2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$checkNewDataAvailable$4(java.lang.Float r7, long r8, long r10) throws java.lang.Exception {
        /*
            r6 = this;
            r0 = 0
            if (r7 == 0) goto L43
            long r2 = r6.estimatedSize
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 == 0) goto L43
            int r2 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r2 != 0) goto L43
            float r2 = r7.floatValue()
            r3 = 1061158912(0x3f400000, float:0.75)
            r4 = 0
            r5 = 1
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L23
            boolean[] r2 = r6.recalculatedEstimatedSize
            boolean r3 = r2[r4]
            if (r3 != 0) goto L23
            r2[r4] = r5
            r4 = 1
        L23:
            float r2 = r7.floatValue()
            r3 = 1064514355(0x3f733333, float:0.95)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L37
            boolean[] r2 = r6.recalculatedEstimatedSize
            boolean r3 = r2[r5]
            if (r3 != 0) goto L37
            r2[r5] = r5
            goto L38
        L37:
            r5 = r4
        L38:
            if (r5 == 0) goto L43
            float r2 = (float) r10
            float r7 = r7.floatValue()
            float r2 = r2 / r7
            long r2 = (long) r2
            r6.estimatedSize = r2
        L43:
            long r2 = r6.estimatedSize
            int r7 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r7 == 0) goto L5f
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 == 0) goto L5f
            r6.estimatedSize = r0
            r6.totalFileSize = r8
            r6.calcTotalPartsCount()
            boolean r7 = r6.uploadFirstPartLater
            if (r7 != 0) goto L5f
            boolean r7 = r6.started
            if (r7 == 0) goto L5f
            r6.storeFileUploadInfo()
        L5f:
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 <= 0) goto L64
            goto L65
        L64:
            r8 = r10
        L65:
            r6.availableSize = r8
            int r7 = r6.currentUploadRequetsCount
            int r8 = r6.maxRequestsCount
            if (r7 >= r8) goto L70
            r6.startUploadRequest()
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileUploadOperation.lambda$checkNewDataAvailable$4(java.lang.Float, long, long):void");
    }

    private void storeFileUploadInfo() {
        SharedPreferences.Editor editorEdit = this.preferences.edit();
        editorEdit.putInt(this.fileKey + "_time", this.uploadStartTime);
        editorEdit.putLong(this.fileKey + "_size", this.totalFileSize);
        editorEdit.putLong(this.fileKey + "_id", this.currentFileId);
        editorEdit.remove(this.fileKey + "_uploaded");
        if (this.isEncrypted) {
            editorEdit.putString(this.fileKey + "_iv", Utilities.bytesToHex(this.f1442iv));
            editorEdit.putString(this.fileKey + "_ivc", Utilities.bytesToHex(this.ivChange));
            editorEdit.putString(this.fileKey + "_key", Utilities.bytesToHex(this.key));
        }
        editorEdit.apply();
    }

    private void calcTotalPartsCount() {
        if (this.uploadFirstPartLater) {
            if (this.isBigFile) {
                long j = this.totalFileSize;
                int i = this.uploadChunkSize;
                this.totalPartsCount = ((int) ((((j - i) + i) - 1) / i)) + 1;
                return;
            } else {
                long j2 = this.totalFileSize - 1024;
                int i2 = this.uploadChunkSize;
                this.totalPartsCount = ((int) (((j2 + i2) - 1) / i2)) + 1;
                return;
            }
        }
        long j3 = this.totalFileSize;
        int i3 = this.uploadChunkSize;
        this.totalPartsCount = (int) (((j3 + i3) - 1) / i3);
    }

    public void setForceSmallFile() {
        this.forceSmallFile = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0305 A[Catch: Exception -> 0x0053, TryCatch #2 {Exception -> 0x0053, blocks: (B:5:0x0009, B:7:0x0016, B:13:0x004a, B:15:0x0050, B:19:0x005c, B:21:0x0060, B:23:0x0069, B:24:0x006b, B:26:0x0084, B:28:0x008d, B:29:0x0096, B:36:0x00a6, B:39:0x00c1, B:41:0x00c5, B:42:0x00c8, B:43:0x00ca, B:47:0x00d3, B:49:0x00e0, B:50:0x00ea, B:52:0x00ee, B:53:0x00f8, B:57:0x011a, B:59:0x0150, B:61:0x0154, B:63:0x015c, B:65:0x0162, B:67:0x01b8, B:70:0x01f0, B:73:0x0202, B:75:0x0205, B:77:0x0208, B:82:0x0218, B:84:0x021c, B:94:0x023e, B:97:0x024b, B:99:0x0258, B:101:0x0264, B:103:0x0268, B:105:0x026e, B:107:0x0279, B:110:0x0282, B:114:0x028f, B:115:0x0296, B:116:0x02ad, B:109:0x0280, B:134:0x0305, B:136:0x0309, B:137:0x0329, B:139:0x0335, B:141:0x0339, B:143:0x0341, B:144:0x0344, B:153:0x037b, B:155:0x0387, B:157:0x038b, B:159:0x03a0, B:158:0x0399, B:163:0x03ac, B:165:0x03b4, B:168:0x03c1, B:170:0x03c5, B:172:0x03d0, B:174:0x03e3, B:179:0x03f2, B:181:0x03f6, B:183:0x03fa, B:185:0x0400, B:187:0x040b, B:189:0x040f, B:191:0x0417, B:197:0x042a, B:201:0x0437, B:202:0x043e, B:204:0x046a, B:206:0x046e, B:208:0x0483, B:210:0x048a, B:213:0x04a0, B:215:0x04a4, B:217:0x04a8, B:218:0x04b7, B:209:0x0486, B:212:0x0490, B:193:0x041e, B:195:0x0422, B:196:0x0428, B:173:0x03da, B:175:0x03e6, B:152:0x0378, B:118:0x02b7, B:120:0x02c2, B:122:0x02de, B:124:0x02e6, B:128:0x02ef, B:129:0x02f5, B:88:0x0228, B:32:0x009d, B:18:0x0056, B:160:0x03a3, B:161:0x03aa, B:11:0x0044, B:8:0x0026, B:146:0x0348, B:149:0x0363), top: B:238:0x0009, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0387 A[Catch: Exception -> 0x0053, TryCatch #2 {Exception -> 0x0053, blocks: (B:5:0x0009, B:7:0x0016, B:13:0x004a, B:15:0x0050, B:19:0x005c, B:21:0x0060, B:23:0x0069, B:24:0x006b, B:26:0x0084, B:28:0x008d, B:29:0x0096, B:36:0x00a6, B:39:0x00c1, B:41:0x00c5, B:42:0x00c8, B:43:0x00ca, B:47:0x00d3, B:49:0x00e0, B:50:0x00ea, B:52:0x00ee, B:53:0x00f8, B:57:0x011a, B:59:0x0150, B:61:0x0154, B:63:0x015c, B:65:0x0162, B:67:0x01b8, B:70:0x01f0, B:73:0x0202, B:75:0x0205, B:77:0x0208, B:82:0x0218, B:84:0x021c, B:94:0x023e, B:97:0x024b, B:99:0x0258, B:101:0x0264, B:103:0x0268, B:105:0x026e, B:107:0x0279, B:110:0x0282, B:114:0x028f, B:115:0x0296, B:116:0x02ad, B:109:0x0280, B:134:0x0305, B:136:0x0309, B:137:0x0329, B:139:0x0335, B:141:0x0339, B:143:0x0341, B:144:0x0344, B:153:0x037b, B:155:0x0387, B:157:0x038b, B:159:0x03a0, B:158:0x0399, B:163:0x03ac, B:165:0x03b4, B:168:0x03c1, B:170:0x03c5, B:172:0x03d0, B:174:0x03e3, B:179:0x03f2, B:181:0x03f6, B:183:0x03fa, B:185:0x0400, B:187:0x040b, B:189:0x040f, B:191:0x0417, B:197:0x042a, B:201:0x0437, B:202:0x043e, B:204:0x046a, B:206:0x046e, B:208:0x0483, B:210:0x048a, B:213:0x04a0, B:215:0x04a4, B:217:0x04a8, B:218:0x04b7, B:209:0x0486, B:212:0x0490, B:193:0x041e, B:195:0x0422, B:196:0x0428, B:173:0x03da, B:175:0x03e6, B:152:0x0378, B:118:0x02b7, B:120:0x02c2, B:122:0x02de, B:124:0x02e6, B:128:0x02ef, B:129:0x02f5, B:88:0x0228, B:32:0x009d, B:18:0x0056, B:160:0x03a3, B:161:0x03aa, B:11:0x0044, B:8:0x0026, B:146:0x0348, B:149:0x0363), top: B:238:0x0009, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0348 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0216 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void startUploadRequest() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 1408
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileUploadOperation.startUploadRequest():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$6(int i, final int[] iArr, int i2, byte[] bArr, int i3, int i4, int i5, long j, TLObject tLObject, TLRPC.TL_error tL_error) throws Exception {
        long jMax;
        TLRPC.InputEncryptedFile tL_inputEncryptedFileUploaded;
        TLRPC.InputFile tL_inputFile;
        byte[] bArr2 = bArr;
        if (i != this.operationGuid) {
            return;
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.m1157d("debug_uploading:  response reqId " + iArr[0] + " time" + this.uploadingFilePath);
        }
        int currentNetworkType = tLObject != null ? tLObject.networkType : ApplicationLoader.getCurrentNetworkType();
        int i6 = this.currentType;
        if (i6 == 50331648) {
            StatsController.getInstance(this.currentAccount).incrementSentBytesCount(currentNetworkType, 3, i2);
        } else if (i6 == 33554432) {
            StatsController.getInstance(this.currentAccount).incrementSentBytesCount(currentNetworkType, 2, i2);
        } else if (i6 == 16777216) {
            StatsController.getInstance(this.currentAccount).incrementSentBytesCount(currentNetworkType, 4, i2);
        } else if (i6 == 67108864) {
            String str = this.uploadingFilePath;
            if (str != null && (str.toLowerCase().endsWith("mp3") || this.uploadingFilePath.toLowerCase().endsWith("m4a"))) {
                StatsController.getInstance(this.currentAccount).incrementSentBytesCount(currentNetworkType, 7, i2);
            } else {
                StatsController.getInstance(this.currentAccount).incrementSentBytesCount(currentNetworkType, 5, i2);
            }
        }
        if (bArr2 != null) {
            this.freeRequestIvs.add(bArr2);
        }
        this.requestTokens.delete(i3);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startUploadRequest$5(iArr);
            }
        });
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            if (this.state != 1) {
                return;
            }
            this.uploadedBytesCount += i4;
            long j2 = this.estimatedSize;
            if (j2 != 0) {
                jMax = Math.max(this.availableSize, j2);
            } else {
                jMax = this.totalFileSize;
            }
            this.delegate.didChangedUploadProgress(this, this.uploadedBytesCount, jMax);
            int i7 = this.currentUploadRequetsCount - 1;
            this.currentUploadRequetsCount = i7;
            if (this.isLastPart && i7 == 0 && this.state == 1) {
                this.state = 3;
                if (this.key == null) {
                    if (this.isBigFile) {
                        tL_inputFile = new TLRPC.TL_inputFileBig();
                    } else {
                        tL_inputFile = new TLRPC.TL_inputFile();
                        tL_inputFile.md5_checksum = "";
                    }
                    tL_inputFile.parts = this.currentPartNum;
                    tL_inputFile.f1590id = this.currentFileId;
                    String str2 = this.uploadingFilePath;
                    tL_inputFile.name = str2.substring(str2.lastIndexOf("/") + 1);
                    this.delegate.didFinishUploadingFile(this, tL_inputFile, null, null, null);
                    cleanup();
                } else {
                    if (this.isBigFile) {
                        tL_inputEncryptedFileUploaded = new TLRPC.TL_inputEncryptedFileBigUploaded();
                    } else {
                        tL_inputEncryptedFileUploaded = new TLRPC.TL_inputEncryptedFileUploaded();
                        tL_inputEncryptedFileUploaded.md5_checksum = "";
                    }
                    tL_inputEncryptedFileUploaded.parts = this.currentPartNum;
                    tL_inputEncryptedFileUploaded.f1589id = this.currentFileId;
                    tL_inputEncryptedFileUploaded.key_fingerprint = this.fingerprint;
                    this.delegate.didFinishUploadingFile(this, null, tL_inputEncryptedFileUploaded, this.key, this.f1442iv);
                    cleanup();
                }
                int i8 = this.currentType;
                if (i8 == 50331648) {
                    StatsController.getInstance(this.currentAccount).incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 3, 1);
                    return;
                }
                if (i8 == 33554432) {
                    StatsController.getInstance(this.currentAccount).incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 2, 1);
                    return;
                }
                if (i8 == 16777216) {
                    StatsController.getInstance(this.currentAccount).incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 4, 1);
                    return;
                }
                if (i8 == 67108864) {
                    String str3 = this.uploadingFilePath;
                    if (str3 != null && (str3.toLowerCase().endsWith("mp3") || this.uploadingFilePath.toLowerCase().endsWith("m4a"))) {
                        StatsController.getInstance(this.currentAccount).incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 7, 1);
                        return;
                    } else {
                        StatsController.getInstance(this.currentAccount).incrementSentItemsCount(ApplicationLoader.getCurrentNetworkType(), 5, 1);
                        return;
                    }
                }
                return;
            }
            if (i7 < this.maxRequestsCount) {
                if (this.estimatedSize == 0 && !this.uploadFirstPartLater && !this.nextPartFirst) {
                    if (this.saveInfoTimes >= 4) {
                        this.saveInfoTimes = 0;
                    }
                    int i9 = this.lastSavedPartNum;
                    if (i5 == i9) {
                        this.lastSavedPartNum = i9 + 1;
                        long j3 = j;
                        while (true) {
                            UploadCachedResult uploadCachedResult = this.cachedResults.get(this.lastSavedPartNum);
                            if (uploadCachedResult == null) {
                                break;
                            }
                            j3 = uploadCachedResult.bytesOffset;
                            bArr2 = uploadCachedResult.f1443iv;
                            this.cachedResults.remove(this.lastSavedPartNum);
                            this.lastSavedPartNum++;
                        }
                        boolean z = this.isBigFile;
                        if ((z && j3 % 1048576 == 0) || (!z && this.saveInfoTimes == 0)) {
                            SharedPreferences.Editor editorEdit = this.preferences.edit();
                            editorEdit.putLong(this.fileKey + "_uploaded", j3);
                            if (this.isEncrypted) {
                                editorEdit.putString(this.fileKey + "_ivc", Utilities.bytesToHex(bArr2));
                            }
                            editorEdit.apply();
                        }
                    } else {
                        UploadCachedResult uploadCachedResult2 = new UploadCachedResult();
                        uploadCachedResult2.bytesOffset = j;
                        if (bArr2 != null) {
                            uploadCachedResult2.f1443iv = new byte[32];
                            System.arraycopy(bArr2, 0, uploadCachedResult2.f1443iv, 0, 32);
                        }
                        this.cachedResults.put(i5, uploadCachedResult2);
                    }
                    this.saveInfoTimes++;
                }
                startUploadRequest();
                return;
            }
            return;
        }
        this.state = 4;
        this.delegate.didFailedUploadingFile(this);
        cleanup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$5(int[] iArr) {
        this.uiRequestTokens.remove(Integer.valueOf(iArr[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$8() {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$startUploadRequest$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$7() throws Exception {
        if (this.currentUploadRequetsCount < this.maxRequestsCount) {
            startUploadRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$9(int[] iArr) {
        this.uiRequestTokens.add(Integer.valueOf(iArr[0]));
    }
}
