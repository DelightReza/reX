package org.telegram.messenger;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.telegram.messenger.time.FastDateFormat;
import org.telegram.p023ui.Components.AnimatedFileDrawable;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public class FileLog {
    private static volatile FileLog Instance = null;
    public static boolean databaseIsMalformed = false;
    private static long dumpedHeap = 0;
    private static HashSet<String> excludeRequests = null;
    private static ExclusionStrategy exclusionStrategy = null;
    private static Gson gson = null;
    private static boolean gsonDisabled = false;
    private static final String mtproto_tag = "MTProto";
    private static HashSet<String> privateFields = null;
    private static final String tag = "tmessages";
    private boolean initied;
    private OutputStreamWriter streamWriter = null;
    private FastDateFormat dateFormat = null;
    private FastDateFormat fileDateFormat = null;
    private DispatchQueue logQueue = null;
    private File currentFile = null;
    private File networkFile = null;
    private File tonlibFile = null;
    private OutputStreamWriter tlStreamWriter = null;
    private File tlRequestsFile = null;

    public static FileLog getInstance() {
        FileLog fileLog;
        FileLog fileLog2 = Instance;
        if (fileLog2 != null) {
            return fileLog2;
        }
        synchronized (FileLog.class) {
            try {
                fileLog = Instance;
                if (fileLog == null) {
                    fileLog = new FileLog();
                    Instance = fileLog;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return fileLog;
    }

    public FileLog() throws IOException {
        if (BuildVars.LOGS_ENABLED) {
            init();
        }
    }

    public static void dumpResponseAndRequest(final int i, TLObject tLObject, TLObject tLObject2, final TLRPC.TL_error tL_error, final long j, final long j2, final int i2) {
        if (BuildVars.DEBUG_PRIVATE_VERSION && BuildVars.LOGS_ENABLED && tLObject != null) {
            String simpleName = tLObject.getClass().getSimpleName();
            checkGson();
            if (excludeRequests.contains(simpleName) && tL_error == null) {
                return;
            }
            try {
                final String str = "req -> " + simpleName + " : " + gson.toJson(tLObject);
                String str2 = "null";
                if (tLObject2 != null) {
                    str2 = "res -> " + tLObject2.getClass().getSimpleName() + " : " + gson.toJson(tLObject2);
                } else if (tL_error != null) {
                    str2 = "err -> " + tL_error.getClass().getSimpleName() + " : " + gson.toJson(tL_error);
                }
                final String str3 = str2;
                final long jCurrentTimeMillis = System.currentTimeMillis();
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.m3218$r8$lambda$TbpBHKbdIrkiw9Ph1QNW6DiAvI(j, j2, i2, i, jCurrentTimeMillis, str, str3, tL_error);
                    }
                });
            } catch (Throwable th) {
                m1160e(th);
            }
        }
    }

    /* renamed from: $r8$lambda$TbpBHKbdIrkiw9Ph1QNW-6DiAvI, reason: not valid java name */
    public static /* synthetic */ void m3218$r8$lambda$TbpBHKbdIrkiw9Ph1QNW6DiAvI(long j, long j2, int i, int i2, long j3, String str, String str2, TLRPC.TL_error tL_error) throws IOException {
        try {
            String str3 = "requestMsgId=" + j + " requestingTime=" + (System.currentTimeMillis() - j2) + " request_token=" + i + " account=" + i2;
            getInstance().tlStreamWriter.write(getInstance().dateFormat.format(j3) + " " + str3);
            getInstance().tlStreamWriter.write("\n");
            getInstance().tlStreamWriter.write(str);
            getInstance().tlStreamWriter.write("\n");
            getInstance().tlStreamWriter.write(str2);
            getInstance().tlStreamWriter.write("\n\n");
            getInstance().tlStreamWriter.flush();
            if (tL_error != null) {
                Log.e(mtproto_tag, str3);
                Log.e(mtproto_tag, str);
                Log.e(mtproto_tag, str2);
                Log.e(mtproto_tag, " ");
                return;
            }
            Log.d(mtproto_tag, str3);
            Log.d(mtproto_tag, str);
            Log.d(mtproto_tag, str2);
            Log.d(mtproto_tag, " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dumpUnparsedMessage(TLObject tLObject, final long j, final int i) {
        if (BuildVars.DEBUG_PRIVATE_VERSION && BuildVars.LOGS_ENABLED && tLObject != null) {
            try {
                checkGson();
                getInstance().dateFormat.format(System.currentTimeMillis());
                StringBuilder sb = new StringBuilder();
                sb.append("receive message -> ");
                sb.append(tLObject.getClass().getSimpleName());
                sb.append(" : ");
                sb.append(gsonDisabled ? tLObject : gson.toJson(tLObject));
                final String string = sb.toString();
                final long jCurrentTimeMillis = System.currentTimeMillis();
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.m3221$r8$lambda$zqRYPgC_YdSVtSz1dAmPwy_uUw(jCurrentTimeMillis, j, i, string);
                    }
                });
            } catch (Throwable unused) {
            }
        }
    }

    /* renamed from: $r8$lambda$zqRYPgC_YdSVtSz1dAm-Pwy_uUw, reason: not valid java name */
    public static /* synthetic */ void m3221$r8$lambda$zqRYPgC_YdSVtSz1dAmPwy_uUw(long j, long j2, int i, String str) throws IOException {
        try {
            getInstance().tlStreamWriter.write(getInstance().dateFormat.format(j) + " msgId=" + j2 + " account=" + i);
            getInstance().tlStreamWriter.write("\n");
            getInstance().tlStreamWriter.write(str);
            getInstance().tlStreamWriter.write("\n\n");
            getInstance().tlStreamWriter.flush();
            Log.d(mtproto_tag, "msgId=" + j2 + " account=" + i);
            Log.d(mtproto_tag, str);
            Log.d(mtproto_tag, " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableGson(boolean z) {
        gsonDisabled = z;
    }

    private static void checkGson() {
        if (gson == null) {
            HashSet<String> hashSet = new HashSet<>();
            privateFields = hashSet;
            hashSet.add("message");
            privateFields.add("phone");
            privateFields.add("about");
            privateFields.add("status_text");
            privateFields.add("bytes");
            privateFields.add("secret");
            privateFields.add("stripped_thumb");
            privateFields.add("strippedBitmap");
            privateFields.add("networkType");
            privateFields.add("disableFree");
            privateFields.add("mContext");
            privateFields.add(PluginsConstants.MenuItemProperties.PRIORITY);
            privateFields.add("constructor");
            for (int i = 0; i < 32; i++) {
                privateFields.add("FLAG_" + i);
            }
            HashSet<String> hashSet2 = new HashSet<>();
            excludeRequests = hashSet2;
            hashSet2.add("TL_upload_getFile");
            excludeRequests.add("TL_upload_getWebFile");
            exclusionStrategy = new ExclusionStrategy() { // from class: org.telegram.messenger.FileLog.1
                @Override // com.google.gson.ExclusionStrategy
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    if (FileLog.privateFields.contains(fieldAttributes.getName())) {
                        return true;
                    }
                    return "message".equalsIgnoreCase(fieldAttributes.getName()) && String.class.equals(fieldAttributes.getDeclaredType());
                }

                @Override // com.google.gson.ExclusionStrategy
                public boolean shouldSkipClass(Class<?> cls) {
                    return cls.isInstance(DispatchQueue.class) || cls.isInstance(AnimatedFileDrawable.class) || cls.isInstance(ColorStateList.class) || cls.isInstance(Context.class);
                }
            };
            gson = new GsonBuilder().addSerializationExclusionStrategy(exclusionStrategy).registerTypeAdapter(byte[].class, new ByteArrayHexAdapter()).registerTypeAdapterFactory(RuntimeClassNameTypeAdapterFactory.m1189of(TLObject.class, "type_", exclusionStrategy)).registerTypeHierarchyAdapter(TLObject.class, new TLObjectDeserializer()).create();
        }
    }

    /* loaded from: classes4.dex */
    public static class ByteArrayHexAdapter extends TypeAdapter {
        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, byte[] bArr) {
            if (bArr == null) {
                jsonWriter.nullValue();
                return;
            }
            StringBuilder sb = new StringBuilder((bArr.length * 2) + 2);
            sb.append("0x");
            for (byte b : bArr) {
                sb.append(String.format("%02x", Integer.valueOf(b & 255)));
            }
            jsonWriter.value(sb.toString());
        }

        @Override // com.google.gson.TypeAdapter
        public byte[] read(JsonReader jsonReader) {
            String strNextString = jsonReader.nextString();
            int length = strNextString.length();
            byte[] bArr = new byte[length / 2];
            for (int i = 0; i < length; i += 2) {
                bArr[i / 2] = (byte) ((Character.digit(strNextString.charAt(i), 16) << 4) + Character.digit(strNextString.charAt(i + 1), 16));
            }
            return bArr;
        }
    }

    /* loaded from: classes4.dex */
    private static class TLObjectDeserializer implements JsonSerializer {
        private TLObjectDeserializer() {
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x0076 A[Catch: Exception -> 0x0043, IllegalAccessException -> 0x0074, TRY_LEAVE, TryCatch #0 {IllegalAccessException -> 0x0074, blocks: (B:16:0x0049, B:18:0x004f, B:20:0x005b, B:22:0x0063, B:24:0x006b, B:29:0x0076), top: B:35:0x0049, outer: #1 }] */
        @Override // com.google.gson.JsonSerializer
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public com.google.gson.JsonElement serialize(org.telegram.tgnet.TLObject r8, java.lang.reflect.Type r9, com.google.gson.JsonSerializationContext r10) throws java.lang.IllegalAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException {
            /*
                r7 = this;
                com.google.gson.JsonObject r9 = new com.google.gson.JsonObject
                r9.<init>()
                java.lang.Class r0 = r8.getClass()
                java.lang.String r0 = r0.getName()
                java.lang.String r1 = "org.telegram.tgnet."
                boolean r1 = r0.startsWith(r1)
                if (r1 == 0) goto L1b
                r1 = 19
                java.lang.String r0 = r0.substring(r1)
            L1b:
                java.lang.String r1 = "_"
                r9.addProperty(r1, r0)
                java.lang.Class r0 = r8.getClass()     // Catch: java.lang.Exception -> L43
                java.lang.reflect.Field[] r0 = r0.getFields()     // Catch: java.lang.Exception -> L43
                int r1 = r0.length     // Catch: java.lang.Exception -> L43
                r2 = 0
            L2a:
                if (r2 >= r1) goto L8b
                r3 = r0[r2]     // Catch: java.lang.Exception -> L43
                java.util.HashSet r4 = org.telegram.messenger.FileLog.m3222$$Nest$sfgetprivateFields()     // Catch: java.lang.Exception -> L43
                if (r4 == 0) goto L45
                java.util.HashSet r4 = org.telegram.messenger.FileLog.m3222$$Nest$sfgetprivateFields()     // Catch: java.lang.Exception -> L43
                java.lang.String r5 = r3.getName()     // Catch: java.lang.Exception -> L43
                boolean r4 = r4.contains(r5)     // Catch: java.lang.Exception -> L43
                if (r4 == 0) goto L45
                goto L85
            L43:
                r8 = move-exception
                goto L88
            L45:
                r4 = 1
                r3.setAccessible(r4)     // Catch: java.lang.Exception -> L43
                java.lang.Object r4 = r3.get(r8)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                if (r4 == 0) goto L76
                java.lang.Class r5 = r4.getClass()     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                java.lang.Class<org.telegram.messenger.DispatchQueue> r6 = org.telegram.messenger.DispatchQueue.class
                boolean r6 = r5.isInstance(r6)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                if (r6 != 0) goto L85
                java.lang.Class<org.telegram.ui.Components.AnimatedFileDrawable> r6 = org.telegram.p023ui.Components.AnimatedFileDrawable.class
                boolean r6 = r5.isInstance(r6)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                if (r6 != 0) goto L85
                java.lang.Class<android.content.res.ColorStateList> r6 = android.content.res.ColorStateList.class
                boolean r6 = r5.isInstance(r6)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                if (r6 != 0) goto L85
                java.lang.Class<android.content.Context> r6 = android.content.Context.class
                boolean r5 = r5.isInstance(r6)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                if (r5 == 0) goto L76
                goto L85
            L74:
                r3 = move-exception
                goto L82
            L76:
                com.google.gson.JsonElement r4 = r10.serialize(r4)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                java.lang.String r3 = r3.getName()     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                r9.add(r3, r4)     // Catch: java.lang.Exception -> L43 java.lang.IllegalAccessException -> L74
                goto L85
            L82:
                r3.printStackTrace()     // Catch: java.lang.Exception -> L43
            L85:
                int r2 = r2 + 1
                goto L2a
            L88:
                r8.printStackTrace()
            L8b:
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLog.TLObjectDeserializer.serialize(org.telegram.tgnet.TLObject, java.lang.reflect.Type, com.google.gson.JsonSerializationContext):com.google.gson.JsonElement");
        }
    }

    public void init() throws IOException {
        File logsDir;
        if (this.initied) {
            return;
        }
        Locale locale = Locale.US;
        this.dateFormat = FastDateFormat.getInstance("dd_MM_yyyy_HH_mm_ss.SSS", locale);
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("dd_MM_yyyy_HH_mm_ss", locale);
        this.fileDateFormat = fastDateFormat;
        String str = fastDateFormat.format(System.currentTimeMillis());
        try {
            logsDir = AndroidUtilities.getLogsDir();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (logsDir == null) {
            return;
        }
        this.currentFile = new File(logsDir, str + ".txt");
        this.tlRequestsFile = new File(logsDir, str + "_mtproto.txt");
        try {
            this.logQueue = new DispatchQueue("logQueue");
            this.currentFile.createNewFile();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(this.currentFile));
            this.streamWriter = outputStreamWriter;
            outputStreamWriter.write("-----start log " + str + "-----\n");
            this.streamWriter.flush();
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new FileOutputStream(this.tlRequestsFile));
            this.tlStreamWriter = outputStreamWriter2;
            outputStreamWriter2.write("-----start log " + str + "-----\n");
            this.tlStreamWriter.flush();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (BuildVars.DEBUG_VERSION) {
            new ANRDetector(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.dumpANR();
                }
            });
        }
        this.initied = true;
    }

    public static void ensureInitied() throws IOException {
        getInstance().init();
    }

    public static String getNetworkLogPath() {
        if (!BuildVars.LOGS_ENABLED) {
            return "";
        }
        try {
            File logsDir = AndroidUtilities.getLogsDir();
            if (logsDir == null) {
                return "";
            }
            getInstance().networkFile = new File(logsDir, getInstance().fileDateFormat.format(System.currentTimeMillis()) + "_net.txt");
            return getInstance().networkFile.getAbsolutePath();
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static String getTonlibLogPath() {
        if (!BuildVars.LOGS_ENABLED) {
            return "";
        }
        try {
            File logsDir = AndroidUtilities.getLogsDir();
            if (logsDir == null) {
                return "";
            }
            getInstance().tonlibFile = new File(logsDir, getInstance().dateFormat.format(System.currentTimeMillis()) + "_tonlib.txt");
            return getInstance().tonlibFile.getAbsolutePath();
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    /* renamed from: e */
    public static void m1159e(final String str, final Throwable th) {
        if (BuildVars.LOGS_ENABLED) {
            ensureInitied();
            Log.e(tag, str, th);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.$r8$lambda$0ztTDayoCBQbqNUyey4KiVh2OWE(str, th);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$0ztTDayoCBQbqNUyey4KiVh2OWE(String str, Throwable th) throws IOException {
        try {
            getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + str + "\n");
            getInstance().streamWriter.write(th.toString());
            StackTraceElement[] stackTrace = th.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: \tat " + stackTraceElement + "\n");
            }
            getInstance().streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: e */
    public static void m1158e(final String str) {
        if (BuildVars.LOGS_ENABLED) {
            ensureInitied();
            Log.e(tag, str);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.$r8$lambda$Fp_tthXxEDYM8ruhhhIZvuLpt48(str);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$Fp_tthXxEDYM8ruhhhIZvuLpt48(String str) throws IOException {
        try {
            getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + str + "\n");
            getInstance().streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: e */
    public static void m1160e(final Throwable th) {
        if (BuildVars.LOGS_ENABLED) {
            if (BuildVars.DEBUG_VERSION && th.getMessage() != null && th.getMessage().contains("disk image is malformed") && !databaseIsMalformed) {
                m1157d("copy malformed files");
                databaseIsMalformed = true;
                File file = new File(ApplicationLoader.getFilesDirFixed(), "malformed_database/");
                file.mkdirs();
                ArrayList<File> databaseFiles = MessagesStorage.getInstance(UserConfig.selectedAccount).getDatabaseFiles();
                for (int i = 0; i < databaseFiles.size(); i++) {
                    try {
                        AndroidUtilities.copyFile(databaseFiles.get(i), new File(file, databaseFiles.get(i).getName()));
                    } catch (IOException e) {
                        m1160e(e);
                    }
                }
            }
            ensureInitied();
            th.printStackTrace();
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.$r8$lambda$dl4NO88LqoO42afmmxifdeuAxfg(th);
                    }
                });
            } else {
                th.printStackTrace();
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$dl4NO88LqoO42afmmxifdeuAxfg(Throwable th) throws IOException {
        try {
            getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: " + th + "\n");
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: \tat " + stackTraceElement + "\n");
            }
            Throwable cause = th.getCause();
            if (cause != null) {
                getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: Caused by " + cause + "\n");
                for (StackTraceElement stackTraceElement2 : cause.getStackTrace()) {
                    getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: \tat " + stackTraceElement2 + "\n");
                }
            }
            getInstance().streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fatal(Throwable th) throws IOException {
        fatal(th, true);
    }

    public void dumpMemory(boolean z) {
        if (z || System.currentTimeMillis() - dumpedHeap >= 30000) {
            dumpedHeap = System.currentTimeMillis();
            try {
                Debug.dumpHprofData(new File(AndroidUtilities.getLogsDir(), getInstance().dateFormat.format(System.currentTimeMillis()) + "_heap.hprof").getAbsolutePath());
            } catch (Exception e) {
                m1160e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpANR() {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<Thread, StackTraceElement[]>> it = Thread.getAllStackTraces().entrySet().iterator();
        while (true) {
            if (it.hasNext()) {
                Map.Entry<Thread, StackTraceElement[]> next = it.next();
                Thread key = next.getKey();
                StackTraceElement[] value = next.getValue();
                sb.append("Thread: ");
                sb.append(key.getName());
                sb.append("\n");
                for (StackTraceElement stackTraceElement : value) {
                    sb.append("\tat ");
                    sb.append(stackTraceElement);
                    sb.append("\n");
                }
                sb.append("\n\n");
            } else {
                m1158e("ANR thread dump\n" + sb.toString());
                dumpMemory(false);
                return;
            }
        }
    }

    public static void fatal(final Throwable th, boolean z) throws IOException {
        if (BuildVars.LOGS_ENABLED) {
            if (th instanceof OutOfMemoryError) {
                getInstance().dumpMemory(false);
            }
            ensureInitied();
            th.printStackTrace();
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.m3217$r8$lambda$Cka7Jn8J8_XFnXWLJvFPiIg4A4(th);
                    }
                });
                return;
            }
            th.printStackTrace();
            if (BuildVars.DEBUG_PRIVATE_VERSION) {
                System.exit(2);
            }
        }
    }

    /* renamed from: $r8$lambda$Cka7Jn8J8_XF-nXWLJvFPiIg4A4, reason: not valid java name */
    public static /* synthetic */ void m3217$r8$lambda$Cka7Jn8J8_XFnXWLJvFPiIg4A4(Throwable th) throws IOException {
        try {
            getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " FATAL/tmessages: " + th + "\n");
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " FATAL/tmessages: \tat " + stackTraceElement + "\n");
            }
            Throwable cause = th.getCause();
            if (cause != null) {
                getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: Caused by " + cause + "\n");
                for (StackTraceElement stackTraceElement2 : cause.getStackTrace()) {
                    getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " E/tmessages: \tat " + stackTraceElement2 + "\n");
                }
            }
            getInstance().streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (BuildVars.DEBUG_PRIVATE_VERSION) {
            System.exit(2);
        }
    }

    /* renamed from: d */
    public static void m1157d(final String str) {
        if (BuildVars.LOGS_ENABLED) {
            ensureInitied();
            Log.d(tag, str);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.m3220$r8$lambda$bYZ1YvZDkuzSWHS46PDhtH0hTE(str);
                    }
                });
            }
        }
    }

    /* renamed from: $r8$lambda$bY-Z1YvZDkuzSWHS46PDhtH0hTE, reason: not valid java name */
    public static /* synthetic */ void m3220$r8$lambda$bYZ1YvZDkuzSWHS46PDhtH0hTE(String str) throws IOException {
        try {
            getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " D/tmessages: " + str + "\n");
            getInstance().streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            if (AndroidUtilities.isENOSPC(e)) {
                LaunchActivity.checkFreeDiscSpaceStatic(1);
            }
        }
    }

    /* renamed from: w */
    public static void m1161w(final String str) throws IOException {
        if (BuildVars.LOGS_ENABLED) {
            ensureInitied();
            Log.w(tag, str);
            if (getInstance().streamWriter != null) {
                getInstance().logQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLog$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        FileLog.m3219$r8$lambda$W1fml6687f73UJ9cxSD3wMPIB4(str);
                    }
                });
            }
        }
    }

    /* renamed from: $r8$lambda$W1fml6687f73UJ9c-xSD3wMPIB4, reason: not valid java name */
    public static /* synthetic */ void m3219$r8$lambda$W1fml6687f73UJ9cxSD3wMPIB4(String str) throws IOException {
        try {
            getInstance().streamWriter.write(getInstance().dateFormat.format(System.currentTimeMillis()) + " W/tmessages: " + str + "\n");
            getInstance().streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLogDirSize() {
        File externalFilesDir = ApplicationLoader.applicationContext.getExternalFilesDir(null);
        if (externalFilesDir == null) {
            return "N/A";
        }
        return AndroidUtilities.formatFileSize(Utilities.getDirSize(new File(externalFilesDir.getAbsolutePath() + "/logs").getAbsolutePath(), 5, false), true, true);
    }

    public static void cleanupLogs() {
        File[] fileArrListFiles;
        ensureInitied();
        File logsDir = AndroidUtilities.getLogsDir();
        if (logsDir == null || (fileArrListFiles = logsDir.listFiles()) == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            if (!BuildVars.LOGS_ENABLED || ((getInstance().currentFile == null || !file.getAbsolutePath().equals(getInstance().currentFile.getAbsolutePath())) && ((getInstance().networkFile == null || !file.getAbsolutePath().equals(getInstance().networkFile.getAbsolutePath())) && (getInstance().tonlibFile == null || !file.getAbsolutePath().equals(getInstance().tonlibFile.getAbsolutePath()))))) {
                file.delete();
            }
        }
    }

    /* loaded from: classes4.dex */
    public static class IgnoreSentException extends Exception {
        public IgnoreSentException(String str) {
            super(str);
        }
    }

    /* loaded from: classes4.dex */
    public class ANRDetector {
        private final long TIMEOUT_MS = 5000;
        private final Handler mainHandler = new Handler(Looper.getMainLooper());
        private boolean isUIThreadResponsive = true;

        public ANRDetector(final Runnable runnable) {
            new Thread(new Runnable() { // from class: org.telegram.messenger.FileLog$ANRDetector$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    this.f$0.lambda$new$1(runnable);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(Runnable runnable) throws InterruptedException {
            while (true) {
                this.isUIThreadResponsive = false;
                this.mainHandler.post(new Runnable() { // from class: org.telegram.messenger.FileLog$ANRDetector$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$0();
                    }
                });
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.isUIThreadResponsive) {
                    runnable.run();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            this.isUIThreadResponsive = true;
        }
    }
}
