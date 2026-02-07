package com.exteragram.messenger.export.output;

import android.util.Log;
import com.exteragram.messenger.export.output.AbstractWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.NativeByteBuffer;

/* loaded from: classes3.dex */
public class OutputFile {
    public final File _file;
    private boolean _inStats = false;
    private long _offset = 0;
    private final Stats _stats;

    public OutputFile(String str, Stats stats) throws IOException {
        File file = new File(str);
        this._file = file;
        try {
            if (file.getPath().contains("/")) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            this._stats = stats;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String PrepareRelativePath(String str, String str2) {
        String str3;
        if (!new File(str + "/" + str2).exists()) {
            return str2;
        }
        int iIndexOf = str2.indexOf(46);
        int i = 0;
        final String strSubstring = str2.substring(0, iIndexOf);
        final String strSubstring2 = iIndexOf >= 0 ? str2.substring(iIndexOf) : "";
        Utilities.CallbackReturn callbackReturn = new Utilities.CallbackReturn() { // from class: com.exteragram.messenger.export.output.OutputFile$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.CallbackReturn
            public final Object run(Object obj) {
                return OutputFile.$r8$lambda$ApE4zRWH4xloOxmAXEkNaQI5bA4(strSubstring, strSubstring2, (Integer) obj);
            }
        };
        do {
            i++;
            str3 = (String) callbackReturn.run(Integer.valueOf(i));
        } while (new File(str + str3).exists());
        return str3;
    }

    public static /* synthetic */ String $r8$lambda$ApE4zRWH4xloOxmAXEkNaQI5bA4(String str, String str2, Integer num) {
        return str + " (" + num + ")" + str2;
    }

    public long size() {
        return this._offset;
    }

    public boolean empty() {
        return this._offset == 0;
    }

    public AbstractWriter.Result writeBlock(String str) throws Throwable {
        AbstractWriter.Result resultWriteBlockAttempt = writeBlockAttempt(str);
        if (resultWriteBlockAttempt.isSuccess()) {
            return resultWriteBlockAttempt;
        }
        throw new IllegalStateException("result is not success for block: " + str);
    }

    public AbstractWriter.Result writeBlock(NativeByteBuffer nativeByteBuffer) throws IOException {
        AbstractWriter.Result resultWriteBlockAttempt = writeBlockAttempt(nativeByteBuffer);
        if (resultWriteBlockAttempt.isSuccess()) {
            return resultWriteBlockAttempt;
        }
        throw new IllegalStateException("result is not success for block: " + nativeByteBuffer);
    }

    public AbstractWriter.Result writeBlock(byte[] bArr) throws Throwable {
        AbstractWriter.Result resultWriteBlockAttempt = writeBlockAttempt(bArr);
        if (resultWriteBlockAttempt.isSuccess()) {
            return resultWriteBlockAttempt;
        }
        throw new IllegalStateException("result is not success for block: " + bArr);
    }

    public AbstractWriter.Result writeBlockAttempt(String str) throws Throwable {
        FileOutputStream fileOutputStream;
        Stats stats = this._stats;
        if (stats != null && !this._inStats) {
            this._inStats = true;
            stats.incrementFiles();
        }
        int length = str.length();
        if (length == 0) {
            Log.e("exteraGram", "size of block to write was zero!");
            return AbstractWriter.Result.Success();
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(this._file, true);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            fileOutputStream.write(str.getBytes());
            this._offset += length;
            Stats stats2 = this._stats;
            if (stats2 != null) {
                stats2.incrementBytes(length);
            }
            try {
                fileOutputStream.close();
                return AbstractWriter.Result.Success();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            FileLog.m1160e(e);
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    throw new RuntimeException(e4);
                }
            }
            return AbstractWriter.Result.Error();
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    throw new RuntimeException(e5);
                }
            }
            throw th;
        }
    }

    public AbstractWriter.Result writeBlockAttempt(byte[] bArr) throws Throwable {
        FileOutputStream fileOutputStream;
        Stats stats = this._stats;
        if (stats != null && !this._inStats) {
            this._inStats = true;
            stats.incrementFiles();
        }
        int length = bArr.length;
        if (length == 0) {
            Log.e("exteraGram", "size of block to write was zero!");
            return AbstractWriter.Result.Success();
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(this._file, true);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(bArr);
            this._offset += length;
            Stats stats2 = this._stats;
            if (stats2 != null) {
                stats2.incrementBytes(length);
            }
            try {
                fileOutputStream.close();
                return AbstractWriter.Result.Success();
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            FileLog.m1160e(e);
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    throw new RuntimeException(e4);
                }
            }
            return AbstractWriter.Result.Error();
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    throw new RuntimeException(e5);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0052 A[Catch: all -> 0x0056, Exception -> 0x0058, TryCatch #5 {Exception -> 0x0058, blocks: (B:20:0x003f, B:22:0x0052, B:27:0x005a), top: B:54:0x003f, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.exteragram.messenger.export.output.AbstractWriter.Result writeBlockAttempt(org.telegram.tgnet.NativeByteBuffer r9) throws java.io.IOException {
        /*
            r8 = this;
            com.exteragram.messenger.export.output.OutputFile$Stats r0 = r8._stats
            if (r0 == 0) goto Le
            boolean r1 = r8._inStats
            if (r1 != 0) goto Le
            r1 = 1
            r8._inStats = r1
            r0.incrementFiles()
        Le:
            java.nio.ByteBuffer r0 = r9.buffer
            int r0 = r0.limit()
            if (r0 != 0) goto L23
            java.lang.String r9 = "exteraGram"
            java.lang.String r0 = "size of block to write was zero!"
            android.util.Log.e(r9, r0)
            com.exteragram.messenger.export.output.AbstractWriter$Result r9 = com.exteragram.messenger.export.output.AbstractWriter.Result.Success()
            return r9
        L23:
            r1 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch: java.io.IOException -> L38
            java.io.File r3 = r8._file     // Catch: java.io.IOException -> L38
            java.lang.String r4 = "rws"
            r2.<init>(r3, r4)     // Catch: java.io.IOException -> L38
            long r3 = r2.length()     // Catch: java.io.IOException -> L36
            r2.seek(r3)     // Catch: java.io.IOException -> L36
            goto L3f
        L36:
            r1 = move-exception
            goto L3c
        L38:
            r2 = move-exception
            r7 = r2
            r2 = r1
            r1 = r7
        L3c:
            org.telegram.messenger.FileLog.m1160e(r1)
        L3f:
            java.nio.channels.FileChannel r1 = r2.getChannel()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            java.nio.ByteBuffer r9 = r9.buffer     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r1.write(r9)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            long r3 = r8._offset     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            long r5 = (long) r0     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            long r3 = r3 + r5
            r8._offset = r3     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            com.exteragram.messenger.export.output.OutputFile$Stats r9 = r8._stats     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            if (r9 == 0) goto L5a
            r9.incrementBytes(r0)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            goto L5a
        L56:
            r9 = move-exception
            goto L7e
        L58:
            r9 = move-exception
            goto L6c
        L5a:
            r1.close()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L58
            r2.close()     // Catch: java.io.IOException -> L65
            com.exteragram.messenger.export.output.AbstractWriter$Result r9 = com.exteragram.messenger.export.output.AbstractWriter.Result.Success()
            return r9
        L65:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r9)
            throw r0
        L6c:
            org.telegram.messenger.FileLog.m1160e(r9)     // Catch: java.lang.Throwable -> L56
            r2.close()     // Catch: java.io.IOException -> L77
            com.exteragram.messenger.export.output.AbstractWriter$Result r9 = com.exteragram.messenger.export.output.AbstractWriter.Result.Error()
            return r9
        L77:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r9)
            throw r0
        L7e:
            r2.close()     // Catch: java.io.IOException -> L82
            throw r9
        L82:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r0.<init>(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.export.output.OutputFile.writeBlockAttempt(org.telegram.tgnet.NativeByteBuffer):com.exteragram.messenger.export.output.AbstractWriter$Result");
    }

    public static class Stats {
        private final AtomicInteger _files = new AtomicInteger(0);
        private final AtomicLong _bytes = new AtomicLong(0);

        public void incrementFiles() {
            this._files.getAndIncrement();
        }

        public void incrementBytes(int i) {
            this._bytes.addAndGet(i);
        }

        public int filesCount() {
            return this._files.get();
        }

        public long bytesCount() {
            return this._bytes.get();
        }
    }
}
