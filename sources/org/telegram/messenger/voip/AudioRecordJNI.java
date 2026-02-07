package org.telegram.messenger.voip;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.text.TextUtils;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class AudioRecordJNI {
    private AcousticEchoCanceler aec;
    private AutomaticGainControl agc;
    private AudioRecord audioRecord;
    private ByteBuffer buffer;
    private int bufferSize;
    private long nativeInst;
    private boolean needResampling = false;

    /* renamed from: ns */
    private NoiseSuppressor f1502ns;
    private boolean running;
    private Thread thread;

    private native void nativeCallback(ByteBuffer byteBuffer);

    public AudioRecordJNI(long j) {
        this.nativeInst = j;
    }

    private int getBufferSize(int i, int i2) {
        return Math.max(AudioRecord.getMinBufferSize(i2, 16, 2), i);
    }

    public void init(int i, int i2, int i3, int i4) {
        if (this.audioRecord != null) {
            throw new IllegalStateException("already inited");
        }
        this.bufferSize = i4;
        boolean zTryInit = tryInit(7, 48000);
        boolean z = true;
        if (!zTryInit) {
            zTryInit = tryInit(1, 48000);
        }
        if (!zTryInit) {
            zTryInit = tryInit(7, 44100);
        }
        if (!zTryInit) {
            zTryInit = tryInit(1, 44100);
        }
        if (zTryInit) {
            try {
                if (AutomaticGainControl.isAvailable()) {
                    AutomaticGainControl automaticGainControlCreate = AutomaticGainControl.create(this.audioRecord.getAudioSessionId());
                    this.agc = automaticGainControlCreate;
                    if (automaticGainControlCreate != null) {
                        automaticGainControlCreate.setEnabled(false);
                    }
                } else {
                    VLog.m1223w("AutomaticGainControl is not available on this device :(");
                }
            } catch (Throwable th) {
                VLog.m1219e("error creating AutomaticGainControl", th);
            }
            try {
                if (NoiseSuppressor.isAvailable()) {
                    NoiseSuppressor noiseSuppressorCreate = NoiseSuppressor.create(this.audioRecord.getAudioSessionId());
                    this.f1502ns = noiseSuppressorCreate;
                    if (noiseSuppressorCreate != null) {
                        noiseSuppressorCreate.setEnabled(Instance.getGlobalServerConfig().useSystemNs && isGoodAudioEffect(this.f1502ns));
                    }
                } else {
                    VLog.m1223w("NoiseSuppressor is not available on this device :(");
                }
            } catch (Throwable th2) {
                VLog.m1219e("error creating NoiseSuppressor", th2);
            }
            try {
                if (AcousticEchoCanceler.isAvailable()) {
                    AcousticEchoCanceler acousticEchoCancelerCreate = AcousticEchoCanceler.create(this.audioRecord.getAudioSessionId());
                    this.aec = acousticEchoCancelerCreate;
                    if (acousticEchoCancelerCreate != null) {
                        if (!Instance.getGlobalServerConfig().useSystemAec || !isGoodAudioEffect(this.aec)) {
                            z = false;
                        }
                        acousticEchoCancelerCreate.setEnabled(z);
                    }
                } else {
                    VLog.m1223w("AcousticEchoCanceler is not available on this device");
                }
            } catch (Throwable th3) {
                VLog.m1219e("error creating AcousticEchoCanceler", th3);
            }
            this.buffer = ByteBuffer.allocateDirect(i4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean tryInit(int r9, int r10) {
        /*
            r8 = this;
            android.media.AudioRecord r0 = r8.audioRecord
            if (r0 == 0) goto L7
            r0.release()     // Catch: java.lang.Exception -> L7
        L7:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Trying to initialize AudioRecord with source="
            r0.append(r1)
            r0.append(r9)
            java.lang.String r1 = " and sample rate="
            r0.append(r1)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            org.telegram.messenger.voip.VLog.m1221i(r0)
            int r0 = r8.bufferSize
            r1 = 48000(0xbb80, float:6.7262E-41)
            int r7 = r8.getBufferSize(r0, r1)
            android.media.AudioRecord r2 = new android.media.AudioRecord     // Catch: java.lang.Exception -> L3c
            r5 = 16
            r6 = 2
            r3 = r9
            r4 = r10
            r2.<init>(r3, r4, r5, r6, r7)     // Catch: java.lang.Exception -> L39
            r8.audioRecord = r2     // Catch: java.lang.Exception -> L39
            goto L44
        L39:
            r0 = move-exception
        L3a:
            r9 = r0
            goto L3f
        L3c:
            r0 = move-exception
            r4 = r10
            goto L3a
        L3f:
            java.lang.String r10 = "AudioRecord init failed!"
            org.telegram.messenger.voip.VLog.m1219e(r10, r9)
        L44:
            r9 = 0
            r10 = 1
            if (r4 == r1) goto L4a
            r0 = 1
            goto L4b
        L4a:
            r0 = 0
        L4b:
            r8.needResampling = r0
            android.media.AudioRecord r0 = r8.audioRecord
            if (r0 == 0) goto L58
            int r0 = r0.getState()
            if (r0 != r10) goto L58
            r9 = 1
        L58:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.voip.AudioRecordJNI.tryInit(int, int):boolean");
    }

    public void stop() throws IllegalStateException {
        try {
            AudioRecord audioRecord = this.audioRecord;
            if (audioRecord != null) {
                audioRecord.stop();
            }
        } catch (Exception unused) {
        }
    }

    public void release() throws InterruptedException {
        this.running = false;
        Thread thread = this.thread;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                VLog.m1220e(e);
            }
            this.thread = null;
        }
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null) {
            audioRecord.release();
            this.audioRecord = null;
        }
        AutomaticGainControl automaticGainControl = this.agc;
        if (automaticGainControl != null) {
            automaticGainControl.release();
            this.agc = null;
        }
        NoiseSuppressor noiseSuppressor = this.f1502ns;
        if (noiseSuppressor != null) {
            noiseSuppressor.release();
            this.f1502ns = null;
        }
        AcousticEchoCanceler acousticEchoCanceler = this.aec;
        if (acousticEchoCanceler != null) {
            acousticEchoCanceler.release();
            this.aec = null;
        }
    }

    public boolean start() throws IllegalStateException {
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null && audioRecord.getState() == 1) {
            try {
                if (this.thread == null) {
                    AudioRecord audioRecord2 = this.audioRecord;
                    if (audioRecord2 == null) {
                        return false;
                    }
                    audioRecord2.startRecording();
                    startThread();
                } else {
                    this.audioRecord.startRecording();
                }
                return true;
            } catch (Exception e) {
                VLog.m1219e("Error initializing AudioRecord", e);
            }
        }
        return false;
    }

    private void startThread() {
        if (this.thread != null) {
            throw new IllegalStateException("thread already started");
        }
        this.running = true;
        final ByteBuffer byteBufferAllocateDirect = this.needResampling ? ByteBuffer.allocateDirect(1764) : null;
        Thread thread = new Thread(new Runnable() { // from class: org.telegram.messenger.voip.AudioRecordJNI$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException {
                this.f$0.lambda$startThread$0(byteBufferAllocateDirect);
            }
        });
        this.thread = thread;
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startThread$0(ByteBuffer byteBuffer) throws IllegalStateException {
        while (this.running) {
            try {
                if (!this.needResampling) {
                    this.audioRecord.read(this.buffer, 1920);
                } else {
                    this.audioRecord.read(byteBuffer, 1764);
                    Resampler.convert44to48(byteBuffer, this.buffer);
                }
            } catch (Exception e) {
                VLog.m1220e(e);
            }
            if (!this.running) {
                this.audioRecord.stop();
                break;
            }
            nativeCallback(this.buffer);
        }
        VLog.m1221i("audiorecord thread exits");
    }

    public int getEnabledEffectsMask() {
        AcousticEchoCanceler acousticEchoCanceler = this.aec;
        int i = (acousticEchoCanceler == null || !acousticEchoCanceler.getEnabled()) ? 0 : 1;
        NoiseSuppressor noiseSuppressor = this.f1502ns;
        return (noiseSuppressor == null || !noiseSuppressor.getEnabled()) ? i : i | 2;
    }

    private static Pattern makeNonEmptyRegex(String str) {
        String string = Instance.getGlobalServerConfig().getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return Pattern.compile(string);
        } catch (Exception e) {
            VLog.m1220e(e);
            return null;
        }
    }

    private static boolean isGoodAudioEffect(AudioEffect audioEffect) throws IllegalStateException {
        Pattern patternMakeNonEmptyRegex = makeNonEmptyRegex("adsp_good_impls");
        Pattern patternMakeNonEmptyRegex2 = makeNonEmptyRegex("adsp_good_names");
        AudioEffect.Descriptor descriptor = audioEffect.getDescriptor();
        VLog.m1217d(audioEffect.getClass().getSimpleName() + ": implementor=" + descriptor.implementor + ", name=" + descriptor.name);
        if (patternMakeNonEmptyRegex != null && patternMakeNonEmptyRegex.matcher(descriptor.implementor).find()) {
            return true;
        }
        if (patternMakeNonEmptyRegex2 != null && patternMakeNonEmptyRegex2.matcher(descriptor.name).find()) {
            return true;
        }
        if (audioEffect instanceof AcousticEchoCanceler) {
            Pattern patternMakeNonEmptyRegex3 = makeNonEmptyRegex("aaec_good_impls");
            Pattern patternMakeNonEmptyRegex4 = makeNonEmptyRegex("aaec_good_names");
            if (patternMakeNonEmptyRegex3 != null && patternMakeNonEmptyRegex3.matcher(descriptor.implementor).find()) {
                return true;
            }
            if (patternMakeNonEmptyRegex4 != null && patternMakeNonEmptyRegex4.matcher(descriptor.name).find()) {
                return true;
            }
        }
        if (!(audioEffect instanceof NoiseSuppressor)) {
            return false;
        }
        Pattern patternMakeNonEmptyRegex5 = makeNonEmptyRegex("ans_good_impls");
        Pattern patternMakeNonEmptyRegex6 = makeNonEmptyRegex("ans_good_names");
        if (patternMakeNonEmptyRegex5 == null || !patternMakeNonEmptyRegex5.matcher(descriptor.implementor).find()) {
            return patternMakeNonEmptyRegex6 != null && patternMakeNonEmptyRegex6.matcher(descriptor.name).find();
        }
        return true;
    }
}
