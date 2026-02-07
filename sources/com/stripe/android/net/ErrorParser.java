package com.stripe.android.net;

import com.exteragram.messenger.plugins.PluginsConstants;
import com.stripe.android.util.StripeJsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
abstract class ErrorParser {
    static StripeError parseError(String str) throws JSONException {
        StripeError stripeError = new StripeError();
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject(PluginsConstants.ERROR);
            stripeError.charge = StripeJsonUtils.optString(jSONObject, "charge");
            stripeError.code = StripeJsonUtils.optString(jSONObject, "code");
            stripeError.decline_code = StripeJsonUtils.optString(jSONObject, "decline_code");
            stripeError.message = StripeJsonUtils.optString(jSONObject, "message");
            stripeError.param = StripeJsonUtils.optString(jSONObject, "param");
            stripeError.type = StripeJsonUtils.optString(jSONObject, PluginsConstants.Settings.TYPE);
            return stripeError;
        } catch (JSONException unused) {
            stripeError.message = "An improperly formatted error response was found.";
            return stripeError;
        }
    }

    static class StripeError {
        public String charge;
        public String code;
        public String decline_code;
        public String message;
        public String param;
        public String type;

        StripeError() {
        }
    }
}
