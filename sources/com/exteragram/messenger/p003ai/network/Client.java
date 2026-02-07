package com.exteragram.messenger.p003ai.network;

import android.text.TextUtils;
import android.util.Base64;
import com.exteragram.messenger.p003ai.AiConfig;
import com.exteragram.messenger.p003ai.AiController;
import com.exteragram.messenger.p003ai.data.Message;
import com.exteragram.messenger.p003ai.data.Role;
import com.exteragram.messenger.p003ai.data.Service;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.utils.network.ExteraHttpClient;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.SharedConfig;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class Client {
    private static final int STREAM_SYMBOLS_LIMIT;
    private final ConcurrentHashMap activeRequests;
    private final ArrayList conversationHistory;
    private final OkHttpClient httpClient;
    private final AtomicBoolean isGenerating;
    private final Role roleOverride;
    private final Service serviceOverride;

    static {
        STREAM_SYMBOLS_LIMIT = SharedConfig.getDevicePerformanceClass() >= 1 ? 10 : 20;
    }

    private Client(Builder builder) {
        this.conversationHistory = new ArrayList();
        this.isGenerating = new AtomicBoolean(false);
        this.activeRequests = new ConcurrentHashMap();
        this.serviceOverride = builder.serviceOverride;
        this.roleOverride = builder.roleOverride;
        OkHttpClient.Builder builderDns = ExteraHttpClient.INSTANCE.getClient().newBuilder().dns(ProxyDns.INSTANCE);
        TimeUnit timeUnit = TimeUnit.MINUTES;
        this.httpClient = builderDns.connectTimeout(1L, timeUnit).readTimeout(5L, timeUnit).build();
    }

    public static String getMimeType(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        if (lowerCase.endsWith(".png")) {
            return "image/png";
        }
        if (lowerCase.endsWith(".webp")) {
            return "image/webp";
        }
        if (lowerCase.endsWith(".heic") || lowerCase.endsWith(".heif")) {
            return "image/heic";
        }
        return "image/jpeg";
    }

    public static byte[] loadImageToByteArray(String str) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists() || !file.isFile() || file.length() == 0) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i != -1) {
                            byteArrayOutputStream.write(bArr, 0, i);
                        } else {
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            fileInputStream.close();
                            return byteArray;
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            FileLog.m1159e("Error loading image: " + str, e);
            return null;
        }
    }

    private Service getSelectedService() {
        Service service = this.serviceOverride;
        return service != null ? service : AiController.getInstance().getSelected();
    }

    public String getResponse(String str, GenerationCallback generationCallback) {
        return getResponse(str, false, false, null, generationCallback);
    }

    public String getResponse(final String str, final boolean z, final boolean z2, final String str2, final GenerationCallback generationCallback) {
        final String string = UUID.randomUUID().toString();
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.activeRequests.put(string, executorServiceNewSingleThreadExecutor);
        executorServiceNewSingleThreadExecutor.execute(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                this.f$0.lambda$getResponse$6(str2, str, z2, z, string, generationCallback);
            }
        });
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getResponse$6(String str, String str2, boolean z, boolean z2, final String str3, final GenerationCallback generationCallback) throws InterruptedException {
        final Exception exc;
        String mimeType;
        byte[] bArr;
        this.isGenerating.set(true);
        try {
            if (AiController.canSendImage(str)) {
                try {
                    byte[] bArrLoadImageToByteArray = loadImageToByteArray(str);
                    mimeType = getMimeType(str);
                    bArr = bArrLoadImageToByteArray;
                } catch (Exception e) {
                    exc = e;
                    FileLog.m1159e("AI Error: ", exc);
                    stopRequest(str3);
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            GenerationCallback generationCallback2 = generationCallback;
                            Exception exc2 = exc;
                            generationCallback2.onError(500, exc2.getMessage() != null ? exc2.getMessage() : "Unknown error");
                        }
                    });
                }
            } else {
                bArr = null;
                mimeType = null;
            }
        } catch (Exception e2) {
            e = e2;
        }
        try {
            Request requestCreateRequest = createRequest(getSelectedService(), str2, z, z2, bArr, mimeType);
            if (requestCreateRequest == null) {
                stopRequest(str3);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        generationCallback.onError(500, "Failed to create request body");
                    }
                });
                return;
            }
            final Response responseExecute = this.httpClient.newCall(requestCreateRequest).execute();
            try {
                if (!responseExecute.isSuccessful()) {
                    if (responseExecute.body() != null) {
                        try {
                            FileLog.m1158e("AI_ERROR_RESPONSE_BODY (" + responseExecute.code() + "): " + responseExecute.body().string());
                        } catch (IOException e3) {
                            FileLog.m1159e("AI_ERROR_READING_RESPONSE_BODY: ", e3);
                        }
                    }
                    stopRequest(str3);
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            GenerationCallback generationCallback2 = generationCallback;
                            Response response = responseExecute;
                            generationCallback2.onError(response.code(), response.message().toLowerCase(Locale.ROOT));
                        }
                    });
                    responseExecute.close();
                    return;
                }
                ResponseBody responseBodyBody = responseExecute.body();
                if (responseBodyBody == null) {
                    stopRequest(str3);
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            generationCallback.onError(204, "Response body is null");
                        }
                    });
                } else if (z) {
                    handleStreamResponse(responseBodyBody, str3, z2, generationCallback);
                } else {
                    final String responseContent = parseResponseContent(responseBodyBody.string(), false);
                    if (responseContent == null) {
                        stopRequest(str3);
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                generationCallback.onError(500, "Failed to parse response");
                            }
                        });
                    } else {
                        if (z2) {
                            this.conversationHistory.add(new Message("assistant", responseContent));
                            AiConfig.saveConversationHistory(this.conversationHistory);
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() throws InterruptedException {
                                this.f$0.lambda$getResponse$4(generationCallback, responseContent, str3);
                            }
                        });
                    }
                }
                responseExecute.close();
            } finally {
            }
        } catch (Exception e4) {
            e = e4;
            exc = e;
            FileLog.m1159e("AI Error: ", exc);
            stopRequest(str3);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    GenerationCallback generationCallback2 = generationCallback;
                    Exception exc2 = exc;
                    generationCallback2.onError(500, exc2.getMessage() != null ? exc2.getMessage() : "Unknown error");
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getResponse$4(GenerationCallback generationCallback, String str, String str2) throws InterruptedException {
        if (generationCallback != null) {
            generationCallback.onResponse(str);
            stopRequest(str2);
        }
    }

    private Request createRequest(Service service, String str, boolean z, boolean z2, byte[] bArr, String str2) throws JSONException {
        StringBuilder sb;
        String str3;
        String url = service.getUrl();
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.contains("generativelanguage.googleapis")) {
            url = "https://generativelanguage.googleapis.com/v1beta/openai/";
        }
        if (url.endsWith("/")) {
            sb = new StringBuilder();
            sb.append(url);
            str3 = "chat/completions";
        } else {
            sb = new StringBuilder();
            sb.append(url);
            str3 = "/chat/completions";
        }
        sb.append(str3);
        String string = sb.toString();
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            Role selectedRole = this.roleOverride;
            if (selectedRole == null) {
                selectedRole = this.serviceOverride == null ? AiController.getInstance().getSelectedRole() : null;
            }
            if (selectedRole != null && !TextUtils.isEmpty(selectedRole.getPrompt())) {
                jSONArray.put(new JSONObject().put("role", "system").put("content", selectedRole.getPrompt()));
            }
            if (z2) {
                this.conversationHistory.clear();
                this.conversationHistory.addAll(AiConfig.getConversationHistory());
                ArrayList arrayList = this.conversationHistory;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    jSONArray.put(createMessageObject((Message) obj));
                }
                Message message = new Message("user", str, bArr, str2);
                this.conversationHistory.add(message);
                jSONArray.put(createMessageObject(message));
            } else {
                jSONArray.put(createMessageObject(new Message("user", str, bArr, str2)));
            }
            jSONObject.put("model", service.getModel());
            jSONObject.put("messages", jSONArray);
            jSONObject.put("stream", z);
            jSONObject.put("temperature", 1.0d);
            jSONObject.put("max_tokens", 4096);
            FileLog.m1157d("AI_REQUEST_URL: " + string);
            FileLog.m1157d("AI_REQUEST_MODEL: " + service.getModel());
            FileLog.m1157d("AI_REQUEST_JSON: " + jSONObject.toString(2));
            return new Request.Builder().url(string).addHeader("Content-Type", "application/json").addHeader("Authorization", "Bearer " + service.getKey()).addHeader("User-Agent", TranslatorUtils.formatUserAgent()).addHeader("HTTP-Referer", "exteragram.app").addHeader("X-Title", "exteraGram").post(RequestBody.create(jSONObject.toString(), MediaType.parse("application/json"))).build();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    private JSONObject createMessageObject(Message message) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("role", message.role());
        if (message.getImageData() != null && !TextUtils.isEmpty(message.getMimeType())) {
            JSONArray jSONArray = new JSONArray();
            if (!TextUtils.isEmpty(message.content())) {
                jSONArray.put(new JSONObject().put(PluginsConstants.Settings.TYPE, "text").put("text", message.content()));
            }
            jSONArray.put(new JSONObject().put(PluginsConstants.Settings.TYPE, "image_url").put("image_url", new JSONObject().put("url", "data:" + message.getMimeType() + ";base64," + Base64.encodeToString(message.getImageData(), 2))));
            jSONObject.put("content", jSONArray);
            return jSONObject;
        }
        jSONObject.put("content", message.content());
        return jSONObject;
    }

    private void sendStreamChunk(final String str, final GenerationCallback generationCallback) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                Client.m1620$r8$lambda$JlF_AmYce5S235INYFhSLFbrY(generationCallback, str);
            }
        });
    }

    /* renamed from: $r8$lambda$JlF_A-mYce5S2-35INYFhSLFbrY, reason: not valid java name */
    public static /* synthetic */ void m1620$r8$lambda$JlF_AmYce5S235INYFhSLFbrY(GenerationCallback generationCallback, String str) {
        if (generationCallback != null) {
            generationCallback.onChunk(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0044, code lost:
    
        if (r2 <= 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0046, code lost:
    
        sendStreamChunk(r0.toString(), r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void handleStreamResponse(okhttp3.ResponseBody r6, final java.lang.String r7, boolean r8, final com.exteragram.messenger.p003ai.network.GenerationCallback r9) throws java.lang.InterruptedException, java.io.IOException {
        /*
            r5 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L73
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Exception -> L73
            java.io.InputStream r6 = r6.byteStream()     // Catch: java.lang.Exception -> L73
            r2.<init>(r6)     // Catch: java.lang.Exception -> L73
            r1.<init>(r2)     // Catch: java.lang.Exception -> L73
            r6 = 0
        L14:
            r2 = 0
        L15:
            java.lang.String r3 = r1.readLine()     // Catch: java.lang.Throwable -> L4e
            if (r3 == 0) goto L6f
            j$.util.concurrent.ConcurrentHashMap r4 = r5.activeRequests     // Catch: java.lang.Throwable -> L4e
            boolean r4 = r4.containsKey(r7)     // Catch: java.lang.Throwable -> L4e
            if (r4 != 0) goto L24
            goto L6f
        L24:
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L2b
            goto L15
        L2b:
            java.lang.String r4 = "data: "
            boolean r4 = r3.startsWith(r4)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L15
            r4 = 6
            java.lang.String r3 = r3.substring(r4)     // Catch: java.lang.Throwable -> L4e
            java.lang.String r3 = r3.trim()     // Catch: java.lang.Throwable -> L4e
            java.lang.String r4 = "[DONE]"
            boolean r4 = r3.equals(r4)     // Catch: java.lang.Throwable -> L4e
            if (r4 == 0) goto L50
            if (r2 <= 0) goto L6f
            java.lang.String r6 = r0.toString()     // Catch: java.lang.Throwable -> L4e
            r5.sendStreamChunk(r6, r9)     // Catch: java.lang.Throwable -> L4e
            goto L6f
        L4e:
            r6 = move-exception
            goto L75
        L50:
            r4 = 1
            java.lang.String r3 = r5.parseResponseContent(r3, r4)     // Catch: java.lang.Throwable -> L4e
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Throwable -> L4e
            if (r4 != 0) goto L15
            r0.append(r3)     // Catch: java.lang.Throwable -> L4e
            int r3 = r3.length()     // Catch: java.lang.Throwable -> L4e
            int r2 = r2 + r3
            int r3 = com.exteragram.messenger.p003ai.network.Client.STREAM_SYMBOLS_LIMIT     // Catch: java.lang.Throwable -> L4e
            if (r2 < r3) goto L15
            java.lang.String r2 = r0.toString()     // Catch: java.lang.Throwable -> L4e
            r5.sendStreamChunk(r2, r9)     // Catch: java.lang.Throwable -> L4e
            goto L14
        L6f:
            r1.close()     // Catch: java.lang.Exception -> L73
            goto L81
        L73:
            r6 = move-exception
            goto L7e
        L75:
            r1.close()     // Catch: java.lang.Throwable -> L79
            goto L7d
        L79:
            r1 = move-exception
            r6.addSuppressed(r1)     // Catch: java.lang.Exception -> L73
        L7d:
            throw r6     // Catch: java.lang.Exception -> L73
        L7e:
            org.telegram.messenger.FileLog.m1160e(r6)
        L81:
            java.lang.String r6 = r0.toString()
            java.lang.String r6 = com.exteragram.messenger.p003ai.network.Client$$ExternalSyntheticBackport0.m184m(r6)
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            if (r0 != 0) goto Lab
            if (r8 == 0) goto La2
            java.util.ArrayList r8 = r5.conversationHistory
            com.exteragram.messenger.ai.data.Message r0 = new com.exteragram.messenger.ai.data.Message
            java.lang.String r1 = "assistant"
            r0.<init>(r1, r6)
            r8.add(r0)
            java.util.ArrayList r8 = r5.conversationHistory
            com.exteragram.messenger.p003ai.AiConfig.saveConversationHistory(r8)
        La2:
            com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda8 r8 = new com.exteragram.messenger.ai.network.Client$$ExternalSyntheticLambda8
            r8.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r8)
            goto Lae
        Lab:
            r5.stopRequest(r7)
        Lae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.p003ai.network.Client.handleStreamResponse(okhttp3.ResponseBody, java.lang.String, boolean, com.exteragram.messenger.ai.network.GenerationCallback):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleStreamResponse$8(GenerationCallback generationCallback, String str, String str2) throws InterruptedException {
        if (generationCallback != null) {
            generationCallback.onResponse(str);
            stopRequest(str2);
        }
    }

    private String parseResponseContent(String str, boolean z) {
        try {
            JSONArray jSONArrayOptJSONArray = new JSONObject(str).optJSONArray("choices");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.getJSONObject(0).optJSONObject(z ? "delta" : "message");
                if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.has("content")) {
                    return jSONObjectOptJSONObject.optString("content", null);
                }
                return null;
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        return null;
    }

    public boolean isGenerating() {
        return this.isGenerating.get();
    }

    public void stopRequest(String str) throws InterruptedException {
        ExecutorService executorService = (ExecutorService) this.activeRequests.remove(str);
        if (executorService != null) {
            executorService.shutdownNow();
            try {
                executorService.awaitTermination(500L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        if (this.activeRequests.isEmpty()) {
            this.isGenerating.set(false);
        }
    }

    public static class Builder {
        private Role roleOverride;
        private Service serviceOverride;

        public Builder serviceOverride(Service service) {
            this.serviceOverride = service;
            return this;
        }

        public Builder roleOverride(Role role) {
            this.roleOverride = role;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
