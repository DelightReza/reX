package com.stripe.android.net;

import com.stripe.android.exception.APIException;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.exception.CardException;
import com.stripe.android.exception.InvalidRequestException;
import com.stripe.android.exception.PermissionException;
import com.stripe.android.exception.RateLimitException;
import com.stripe.android.model.Token;
import com.stripe.android.net.ErrorParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class StripeApiHandler {
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = new StripeSSLSocketFactory();

    public static Token createToken(Map map, RequestOptions requestOptions) {
        return requestToken("POST", getApiUrl(), map, requestOptions);
    }

    static String createQuery(Map map) {
        StringBuilder sb = new StringBuilder();
        for (Parameter parameter : flattenParams(map)) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(urlEncodePair(parameter.key, parameter.value));
        }
        return sb.toString();
    }

    static Map getHeaders(RequestOptions requestOptions) {
        HashMap map = new HashMap();
        String apiVersion = requestOptions.getApiVersion();
        map.put("Accept-Charset", "UTF-8");
        map.put("Accept", "application/json");
        map.put("User-Agent", String.format("Stripe/v1 JavaBindings/%s", "3.5.0"));
        map.put("Authorization", String.format("Bearer %s", requestOptions.getPublishableApiKey()));
        String[] strArr = {"os.name", "os.version", "os.arch", "java.version", "java.vendor", "java.vm.version", "java.vm.vendor"};
        HashMap map2 = new HashMap();
        for (int i = 0; i < 7; i++) {
            String str = strArr[i];
            map2.put(str, System.getProperty(str));
        }
        map2.put("bindings.version", "3.5.0");
        map2.put("lang", "Java");
        map2.put("publisher", "Stripe");
        map.put("X-Stripe-Client-User-Agent", new JSONObject(map2).toString());
        if (apiVersion != null) {
            map.put("Stripe-Version", apiVersion);
        }
        if (requestOptions.getIdempotencyKey() != null) {
            map.put("Idempotency-Key", requestOptions.getIdempotencyKey());
        }
        return map;
    }

    static String getApiUrl() {
        return String.format("%s/v1/%s", "https://api.stripe.com", "tokens");
    }

    private static String formatURL(String str, String str2) {
        if (str2 == null || str2.isEmpty()) {
            return str;
        }
        return String.format("%s%s%s", str, str.contains("?") ? "&" : "?", str2);
    }

    private static HttpURLConnection createGetConnection(String str, String str2, RequestOptions requestOptions) throws ProtocolException {
        HttpURLConnection httpURLConnectionCreateStripeConnection = createStripeConnection(formatURL(str, str2), requestOptions);
        httpURLConnectionCreateStripeConnection.setRequestMethod("GET");
        return httpURLConnectionCreateStripeConnection;
    }

    private static HttpURLConnection createPostConnection(String str, String str2, RequestOptions requestOptions) throws Throwable {
        OutputStream outputStream;
        HttpURLConnection httpURLConnectionCreateStripeConnection = createStripeConnection(str, requestOptions);
        httpURLConnectionCreateStripeConnection.setDoOutput(true);
        httpURLConnectionCreateStripeConnection.setRequestMethod("POST");
        httpURLConnectionCreateStripeConnection.setRequestProperty("Content-Type", String.format("application/x-www-form-urlencoded;charset=%s", "UTF-8"));
        try {
            outputStream = httpURLConnectionCreateStripeConnection.getOutputStream();
        } catch (Throwable th) {
            th = th;
            outputStream = null;
        }
        try {
            outputStream.write(str2.getBytes("UTF-8"));
            outputStream.close();
            return httpURLConnectionCreateStripeConnection;
        } catch (Throwable th2) {
            th = th2;
            if (outputStream != null) {
                outputStream.close();
            }
            throw th;
        }
    }

    private static HttpURLConnection createStripeConnection(String str, RequestOptions requestOptions) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(80000);
        httpURLConnection.setUseCaches(false);
        for (Map.Entry entry : getHeaders(requestOptions).entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        if (httpURLConnection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(SSL_SOCKET_FACTORY);
        }
        return httpURLConnection;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0026 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.stripe.android.model.Token requestToken(java.lang.String r7, java.lang.String r8, java.util.Map r9, com.stripe.android.net.RequestOptions r10) throws com.stripe.android.exception.AuthenticationException {
        /*
            java.lang.String r0 = "-1"
            java.lang.String r1 = "networkaddress.cache.ttl"
            r2 = 0
            if (r10 != 0) goto L8
            return r2
        L8:
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            java.lang.String r4 = java.security.Security.getProperty(r1)     // Catch: java.lang.SecurityException -> L14
            java.lang.String r5 = "0"
            java.security.Security.setProperty(r1, r5)     // Catch: java.lang.SecurityException -> L15
            goto L17
        L14:
            r4 = r2
        L15:
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
        L17:
            java.lang.String r5 = r10.getPublishableApiKey()
            java.lang.String r5 = r5.trim()
            boolean r5 = r5.isEmpty()
            r6 = 0
            if (r5 != 0) goto L95
            com.stripe.android.net.StripeResponse r7 = getStripeResponse(r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            int r8 = r7.getResponseCode()     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            java.lang.String r9 = r7.getResponseBody()     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            java.util.Map r7 = r7.getResponseHeaders()     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            if (r7 != 0) goto L3a
            r7 = r2
            goto L42
        L3a:
            java.lang.String r10 = "Request-Id"
            java.lang.Object r7 = r7.get(r10)     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            java.util.List r7 = (java.util.List) r7     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
        L42:
            if (r7 == 0) goto L55
            int r10 = r7.size()     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            if (r10 <= 0) goto L55
            java.lang.Object r7 = r7.get(r6)     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            goto L56
        L51:
            r7 = move-exception
            goto L75
        L53:
            goto L85
        L55:
            r7 = r2
        L56:
            r10 = 200(0xc8, float:2.8E-43)
            if (r8 < r10) goto L5e
            r10 = 300(0x12c, float:4.2E-43)
            if (r8 < r10) goto L61
        L5e:
            handleAPIError(r9, r8, r7)     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
        L61:
            com.stripe.android.model.Token r7 = com.stripe.android.net.TokenParser.parseToken(r9)     // Catch: java.lang.Throwable -> L51 org.json.JSONException -> L53
            boolean r8 = r3.booleanValue()
            if (r8 == 0) goto L74
            if (r4 != 0) goto L71
            java.security.Security.setProperty(r1, r0)
            goto L74
        L71:
            java.security.Security.setProperty(r1, r4)
        L74:
            return r7
        L75:
            boolean r8 = r3.booleanValue()
            if (r8 == 0) goto L84
            if (r4 != 0) goto L81
            java.security.Security.setProperty(r1, r0)
            goto L84
        L81:
            java.security.Security.setProperty(r1, r4)
        L84:
            throw r7
        L85:
            boolean r7 = r3.booleanValue()
            if (r7 == 0) goto L94
            if (r4 != 0) goto L91
            java.security.Security.setProperty(r1, r0)
            goto L94
        L91:
            java.security.Security.setProperty(r1, r4)
        L94:
            return r2
        L95:
            com.stripe.android.exception.AuthenticationException r7 = new com.stripe.android.exception.AuthenticationException
            java.lang.String r8 = "No API key provided. (HINT: set your API key using 'Stripe.apiKey = <API-KEY>'. You can generate API keys from the Stripe web interface. See https://stripe.com/api for details or email support@stripe.com if you have questions."
            java.lang.Integer r9 = java.lang.Integer.valueOf(r6)
            r7.<init>(r8, r2, r9)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stripe.android.net.StripeApiHandler.requestToken(java.lang.String, java.lang.String, java.util.Map, com.stripe.android.net.RequestOptions):com.stripe.android.model.Token");
    }

    private static StripeResponse getStripeResponse(String str, String str2, Map map, RequestOptions requestOptions) throws InvalidRequestException {
        try {
            return makeURLConnectionRequest(str, str2, createQuery(map), requestOptions);
        } catch (UnsupportedEncodingException e) {
            throw new InvalidRequestException("Unable to encode parameters to UTF-8. Please contact support@stripe.com for assistance.", null, null, 0, e);
        }
    }

    private static List flattenParams(Map map) {
        return flattenParamsMap(map, null);
    }

    private static List flattenParamsList(List list, String str) {
        LinkedList linkedList = new LinkedList();
        Iterator it = list.iterator();
        String str2 = String.format("%s[]", str);
        if (list.isEmpty()) {
            linkedList.add(new Parameter(str, ""));
            return linkedList;
        }
        while (it.hasNext()) {
            linkedList.addAll(flattenParamsValue(it.next(), str2));
        }
        return linkedList;
    }

    private static List flattenParamsMap(Map map, String str) {
        LinkedList linkedList = new LinkedList();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                String str2 = (String) entry.getKey();
                Object value = entry.getValue();
                if (str != null) {
                    str2 = String.format("%s[%s]", str, str2);
                }
                linkedList.addAll(flattenParamsValue(value, str2));
            }
        }
        return linkedList;
    }

    private static List flattenParamsValue(Object obj, String str) throws InvalidRequestException {
        if (obj instanceof Map) {
            return flattenParamsMap((Map) obj, str);
        }
        if (obj instanceof List) {
            return flattenParamsList((List) obj, str);
        }
        if ("".equals(obj)) {
            throw new InvalidRequestException("You cannot set '" + str + "' to an empty string. We interpret empty strings as null in requests. You may set '" + str + "' to null to delete the property.", str, null, 0, null);
        }
        if (obj == null) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(new Parameter(str, ""));
            return linkedList;
        }
        LinkedList linkedList2 = new LinkedList();
        linkedList2.add(new Parameter(str, obj.toString()));
        return linkedList2;
    }

    private static void handleAPIError(String str, int i, String str2) throws CardException, JSONException, AuthenticationException, InvalidRequestException, APIException {
        ErrorParser.StripeError error = ErrorParser.parseError(str);
        if (i != 429) {
            switch (i) {
                case 400:
                    throw new InvalidRequestException(error.message, error.param, str2, Integer.valueOf(i), null);
                case 401:
                    throw new AuthenticationException(error.message, str2, Integer.valueOf(i));
                case 402:
                    throw new CardException(error.message, str2, error.code, error.param, error.decline_code, error.charge, Integer.valueOf(i), null);
                case 403:
                    throw new PermissionException(error.message, str2, Integer.valueOf(i));
                case 404:
                    throw new InvalidRequestException(error.message, error.param, str2, Integer.valueOf(i), null);
                default:
                    throw new APIException(error.message, str2, Integer.valueOf(i), null);
            }
        }
        throw new RateLimitException(error.message, error.param, str2, Integer.valueOf(i), null);
    }

    private static String urlEncodePair(String str, String str2) {
        return String.format("%s=%s", urlEncode(str), urlEncode(str2));
    }

    private static String urlEncode(String str) {
        if (str == null) {
            return null;
        }
        return URLEncoder.encode(str, "UTF-8");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0045 A[Catch: all -> 0x001f, IOException -> 0x0021, TryCatch #0 {IOException -> 0x0021, blocks: (B:3:0x0003, B:7:0x0011, B:9:0x0019, B:18:0x0030, B:22:0x003c, B:24:0x004d, B:23:0x0045, B:27:0x005a, B:28:0x0069, B:15:0x0023, B:17:0x002b), top: B:34:0x0003, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.stripe.android.net.StripeResponse makeURLConnectionRequest(java.lang.String r5, java.lang.String r6, java.lang.String r7, com.stripe.android.net.RequestOptions r8) {
        /*
            r0 = 0
            r1 = 1
            r2 = 0
            int r3 = r5.hashCode()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            r4 = 70454(0x11336, float:9.8727E-41)
            if (r3 == r4) goto L23
            r4 = 2461856(0x2590a0, float:3.449795E-39)
            if (r3 != r4) goto L5a
            java.lang.String r3 = "POST"
            boolean r3 = r5.equals(r3)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            if (r3 == 0) goto L5a
            java.net.HttpURLConnection r5 = createPostConnection(r6, r7, r8)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
        L1d:
            r2 = r5
            goto L30
        L1f:
            r5 = move-exception
            goto L85
        L21:
            r5 = move-exception
            goto L6a
        L23:
            java.lang.String r3 = "GET"
            boolean r3 = r5.equals(r3)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            if (r3 == 0) goto L5a
            java.net.HttpURLConnection r5 = createGetConnection(r6, r7, r8)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            goto L1d
        L30:
            int r5 = r2.getResponseCode()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 < r6) goto L45
            r6 = 300(0x12c, float:4.2E-43)
            if (r5 >= r6) goto L45
            java.io.InputStream r6 = r2.getInputStream()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            java.lang.String r6 = getResponseBody(r6)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            goto L4d
        L45:
            java.io.InputStream r6 = r2.getErrorStream()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            java.lang.String r6 = getResponseBody(r6)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
        L4d:
            java.util.Map r7 = r2.getHeaderFields()     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            com.stripe.android.net.StripeResponse r8 = new com.stripe.android.net.StripeResponse     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            r8.<init>(r5, r6, r7)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            r2.disconnect()
            return r8
        L5a:
            com.stripe.android.exception.APIConnectionException r6 = new com.stripe.android.exception.APIConnectionException     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            java.lang.String r7 = "Unrecognized HTTP method %s. This indicates a bug in the Stripe bindings. Please contact support@stripe.com for assistance."
            java.lang.Object[] r8 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            r8[r0] = r5     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            java.lang.String r5 = java.lang.String.format(r7, r8)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
            throw r6     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L21
        L6a:
            com.stripe.android.exception.APIConnectionException r6 = new com.stripe.android.exception.APIConnectionException     // Catch: java.lang.Throwable -> L1f
            java.lang.String r7 = "IOException during API request to Stripe (%s): %s Please check your internet connection and try again. If this problem persists, you should check Stripe's service status at https://twitter.com/stripestatus, or let us know at support@stripe.com."
            java.lang.String r8 = getApiUrl()     // Catch: java.lang.Throwable -> L1f
            java.lang.String r3 = r5.getMessage()     // Catch: java.lang.Throwable -> L1f
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L1f
            r4[r0] = r8     // Catch: java.lang.Throwable -> L1f
            r4[r1] = r3     // Catch: java.lang.Throwable -> L1f
            java.lang.String r7 = java.lang.String.format(r7, r4)     // Catch: java.lang.Throwable -> L1f
            r6.<init>(r7, r5)     // Catch: java.lang.Throwable -> L1f
            throw r6     // Catch: java.lang.Throwable -> L1f
        L85:
            if (r2 == 0) goto L8a
            r2.disconnect()
        L8a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.stripe.android.net.StripeApiHandler.makeURLConnectionRequest(java.lang.String, java.lang.String, java.lang.String, com.stripe.android.net.RequestOptions):com.stripe.android.net.StripeResponse");
    }

    private static String getResponseBody(InputStream inputStream) throws IOException {
        String next = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
        inputStream.close();
        return next;
    }

    private static final class Parameter {
        public final String key;
        public final String value;

        public Parameter(String str, String str2) {
            this.key = str;
            this.value = str2;
        }
    }
}
