package org.telegram.messenger;

import android.content.Context;
import android.content.DialogInterface;
import android.os.CancellationSignal;
import android.util.Base64;
import androidx.credentials.CreateCredentialResponse;
import androidx.credentials.CreatePublicKeyCredentialRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.GetPublicKeyCredentialOption;
import androidx.credentials.exceptions.CreateCredentialCancellationException;
import androidx.credentials.exceptions.CreateCredentialInterruptedException;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.credentials.exceptions.GetCredentialInterruptedException;
import androidx.credentials.exceptions.NoCredentialException;
import com.exteragram.messenger.plugins.PluginsConstants;
import java.util.Arrays;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.PasskeysController;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;

/* loaded from: classes4.dex */
public class PasskeysController {
    public static void create(final Context context, final int i, final Utilities.Callback2<TL_account.Passkey, String> callback2) {
        if (BuildVars.SUPPORTS_PASSKEYS) {
            final CredentialManager credentialManagerCreate = CredentialManager.CC.create(context);
            final AlertDialog alertDialog = new AlertDialog(context, 3);
            alertDialog.showDelayed(500L);
            ConnectionsManager.getInstance(i).sendRequestTyped(new TL_account.initPasskeyRegistration(), new BotForumHelper$$ExternalSyntheticLambda2(), new Utilities.Callback2() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda10
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    PasskeysController.m3929$r8$lambda$Ce_xykqs492psrIzSU1fkU68Kg(alertDialog, callback2, credentialManagerCreate, context, i, (TL_account.passkeyRegistrationOptions) obj, (TLRPC.TL_error) obj2);
                }
            });
        }
    }

    /* renamed from: $r8$lambda$Ce_xykqs4-92psrIzSU1fkU68Kg, reason: not valid java name */
    public static /* synthetic */ void m3929$r8$lambda$Ce_xykqs492psrIzSU1fkU68Kg(AlertDialog alertDialog, final Utilities.Callback2 callback2, CredentialManager credentialManager, final Context context, final int i, TL_account.passkeyRegistrationOptions passkeyregistrationoptions, TLRPC.TL_error tL_error) {
        alertDialog.dismiss();
        if (tL_error != null) {
            callback2.run(null, tL_error.text);
            return;
        }
        try {
            try {
                credentialManager.createCredential(context, new CreatePublicKeyCredentialRequest(new JSONObject(passkeyregistrationoptions.options.data).getJSONObject("publicKey").toString()), ktxCallback(new Utilities.Callback2() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda4
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) throws JSONException {
                        PasskeysController.$r8$lambda$3SW81hFzUnxoqfU8kn7BcldnVtk(callback2, context, i, (CreateCredentialResponse) obj, (Throwable) obj2);
                    }
                }));
            } catch (Exception e) {
                FileLog.m1160e(e);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback2.run(null, e.getMessage());
                    }
                });
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
            callback2.run(null, e2.getMessage());
        }
    }

    public static /* synthetic */ void $r8$lambda$3SW81hFzUnxoqfU8kn7BcldnVtk(final Utilities.Callback2 callback2, final Context context, final int i, CreateCredentialResponse createCredentialResponse, final Throwable th) throws JSONException {
        if ((th instanceof CreateCredentialCancellationException) || (th instanceof CreateCredentialInterruptedException)) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    callback2.run(null, "CANCELLED");
                }
            });
            return;
        }
        if (th != null) {
            FileLog.m1160e(th);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    callback2.run(null, th.getMessage());
                }
            });
            return;
        }
        final TL_account.registerPasskey registerpasskey = new TL_account.registerPasskey();
        try {
            JSONObject jSONObject = new JSONObject(createCredentialResponse.getData().getString("androidx.credentials.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON"));
            TL_account.inputPasskeyCredentialPublicKey inputpasskeycredentialpublickey = new TL_account.inputPasskeyCredentialPublicKey();
            registerpasskey.credential = inputpasskeycredentialpublickey;
            inputpasskeycredentialpublickey.f1750id = jSONObject.getString("id");
            registerpasskey.credential.raw_id = jSONObject.getString("rawId");
            JSONObject jSONObject2 = jSONObject.getJSONObject(PluginsConstants.RESPONSE);
            TL_account.inputPasskeyResponseRegister inputpasskeyresponseregister = new TL_account.inputPasskeyResponseRegister();
            TLRPC.TL_dataJSON tL_dataJSON = new TLRPC.TL_dataJSON();
            inputpasskeyresponseregister.client_data = tL_dataJSON;
            tL_dataJSON.data = new String(Base64.decode(jSONObject2.getString("clientDataJSON"), 8));
            inputpasskeyresponseregister.attestation_object = Base64.decode(jSONObject2.getString("attestationObject"), 8);
            FileLog.m1157d("AAGUID: " + bytesToHex(Arrays.copyOfRange(inputpasskeyresponseregister.attestation_object, 67, 83)));
            registerpasskey.credential.response = inputpasskeyresponseregister;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    PasskeysController.m3930$r8$lambda$ImXRxa2Rasj6ufgbRIHlwqUic(context, i, registerpasskey, callback2);
                }
            });
        } catch (Exception e) {
            FileLog.m1160e(e);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    callback2.run(null, e.getMessage());
                }
            });
        }
    }

    /* renamed from: $r8$lambda$I-mXRxa2Rasj6uf-gbRIHlwqUic, reason: not valid java name */
    public static /* synthetic */ void m3930$r8$lambda$ImXRxa2Rasj6ufgbRIHlwqUic(Context context, final int i, TL_account.registerPasskey registerpasskey, final Utilities.Callback2 callback2) {
        final AlertDialog alertDialog = new AlertDialog(context, 3);
        alertDialog.showDelayed(500L);
        final int iSendRequestTyped = ConnectionsManager.getInstance(i).sendRequestTyped(registerpasskey, new BotForumHelper$$ExternalSyntheticLambda2(), new Utilities.Callback2() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda11
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                PasskeysController.m3932$r8$lambda$Q0jsxlRXbfxYRY42cc9IJqBE(alertDialog, callback2, (TL_account.Passkey) obj, (TLRPC.TL_error) obj2);
            }
        });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                PasskeysController.$r8$lambda$HeiFEaTFmKJ9b_dOV8JVHbHRdKE(i, iSendRequestTyped, callback2, dialogInterface);
            }
        });
    }

    /* renamed from: $r8$lambda$Q0jsxlRXbfxYRY-42-cc9-IJqBE, reason: not valid java name */
    public static /* synthetic */ void m3932$r8$lambda$Q0jsxlRXbfxYRY42cc9IJqBE(AlertDialog alertDialog, Utilities.Callback2 callback2, TL_account.Passkey passkey, TLRPC.TL_error tL_error) {
        alertDialog.dismiss();
        if (tL_error != null) {
            callback2.run(null, tL_error.text);
        } else {
            callback2.run(passkey, null);
        }
    }

    public static /* synthetic */ void $r8$lambda$HeiFEaTFmKJ9b_dOV8JVHbHRdKE(int i, int i2, Utilities.Callback2 callback2, DialogInterface dialogInterface) {
        ConnectionsManager.getInstance(i).cancelRequest(i2, true);
        callback2.run(null, "CANCELLED");
    }

    public static Runnable login(final Context context, final int i, final boolean z, final Utilities.Callback3<Long, TLRPC.auth_Authorization, String> callback3) {
        if (!BuildVars.SUPPORTS_PASSKEYS) {
            return null;
        }
        final CredentialManager credentialManagerCreate = CredentialManager.CC.create(context);
        final boolean[] zArr = new boolean[1];
        TL_account.initPasskeyLogin initpasskeylogin = new TL_account.initPasskeyLogin();
        initpasskeylogin.api_id = BuildVars.EXTERA_APP_ID;
        initpasskeylogin.api_hash = BuildVars.EXTERA_APP_HASH;
        final int iSendRequestTyped = ConnectionsManager.getInstance(i).sendRequestTyped(initpasskeylogin, new BotForumHelper$$ExternalSyntheticLambda2(), new Utilities.Callback2() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                PasskeysController.m3933$r8$lambda$Rm5UaK6U4xxbxsvgOZGJr_6UmE(zArr, callback3, z, credentialManagerCreate, context, i, runnableArr, (TL_account.passkeyLoginOptions) obj, (TLRPC.TL_error) obj2);
            }
        }, 8);
        final Runnable[] runnableArr = {new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ConnectionsManager.getInstance(i).cancelRequest(iSendRequestTyped, true);
            }
        }};
        return new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PasskeysController.$r8$lambda$5n4wwV_fOePzb_daYbBtOzbOsz4(zArr, runnableArr);
            }
        };
    }

    /* renamed from: $r8$lambda$Rm5UaK6U4-xxbxsvgOZGJr_6UmE, reason: not valid java name */
    public static /* synthetic */ void m3933$r8$lambda$Rm5UaK6U4xxbxsvgOZGJr_6UmE(boolean[] zArr, Utilities.Callback3 callback3, boolean z, CredentialManager credentialManager, Context context, int i, Runnable[] runnableArr, TL_account.passkeyLoginOptions passkeyloginoptions, TLRPC.TL_error tL_error) {
        if (zArr[0]) {
            return;
        }
        if (tL_error != null) {
            callback3.run(0L, null, tL_error.text);
            return;
        }
        try {
            GetCredentialRequest getCredentialRequestBuild = new GetCredentialRequest.Builder().addCredentialOption(new GetPublicKeyCredentialOption(new JSONObject(passkeyloginoptions.options.data).getJSONObject("publicKey").toString())).setPreferImmediatelyAvailableCredentials(!z).build();
            try {
                final CancellationSignal cancellationSignal = new CancellationSignal();
                credentialManager.getCredentialAsync(context, getCredentialRequestBuild, cancellationSignal, context.getMainExecutor(), new C23651(callback3, context, i));
                runnableArr[0] = new Runnable() { // from class: org.telegram.messenger.PasskeysController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        cancellationSignal.cancel();
                    }
                };
            } catch (Exception e) {
                callback3.run(0L, null, e.getMessage());
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
            callback3.run(0L, null, e2.getMessage());
        }
    }

    /* renamed from: org.telegram.messenger.PasskeysController$1 */
    class C23651 implements CredentialManagerCallback {
        final /* synthetic */ Context val$context;
        final /* synthetic */ int val$currentAccount;
        final /* synthetic */ Utilities.Callback3 val$done;

        C23651(Utilities.Callback3 callback3, Context context, int i) {
            this.val$done = callback3;
            this.val$context = context;
            this.val$currentAccount = i;
        }

        @Override // androidx.credentials.CredentialManagerCallback
        public void onResult(GetCredentialResponse getCredentialResponse) throws JSONException, NumberFormatException {
            Credential credential = getCredentialResponse.getCredential();
            TL_account.finishPasskeyLogin finishpasskeylogin = new TL_account.finishPasskeyLogin();
            finishpasskeylogin.credential = new TL_account.inputPasskeyCredentialPublicKey();
            try {
                JSONObject jSONObject = new JSONObject(credential.getData().getString("androidx.credentials.BUNDLE_KEY_AUTHENTICATION_RESPONSE_JSON"));
                finishpasskeylogin.credential.f1750id = jSONObject.getString("id");
                finishpasskeylogin.credential.raw_id = jSONObject.getString("rawId");
                JSONObject jSONObject2 = jSONObject.getJSONObject(PluginsConstants.RESPONSE);
                TL_account.inputPasskeyResponseLogin inputpasskeyresponselogin = new TL_account.inputPasskeyResponseLogin();
                TLRPC.TL_dataJSON tL_dataJSON = new TLRPC.TL_dataJSON();
                inputpasskeyresponselogin.client_data = tL_dataJSON;
                tL_dataJSON.data = new String(Base64.decode(jSONObject2.getString("clientDataJSON"), 8));
                inputpasskeyresponselogin.authenticator_data = Base64.decode(jSONObject2.getString("authenticatorData"), 8);
                inputpasskeyresponselogin.signature = Base64.decode(jSONObject2.getString("signature"), 8);
                String str = new String(Base64.decode(jSONObject2.getString("userHandle"), 8));
                inputpasskeyresponselogin.user_handle = str;
                int i = Integer.parseInt(str.split(":")[0]);
                final long j = Long.parseLong(inputpasskeyresponselogin.user_handle.split(":")[1]);
                finishpasskeylogin.credential.response = inputpasskeyresponselogin;
                final AlertDialog alertDialog = new AlertDialog(this.val$context, 3);
                alertDialog.showDelayed(500L);
                if (i != ConnectionsManager.getInstance(this.val$currentAccount).getCurrentDatacenterId()) {
                    int currentDatacenterId = ConnectionsManager.getInstance(this.val$currentAccount).getCurrentDatacenterId();
                    long currentAuthKeyId = ConnectionsManager.getInstance(this.val$currentAccount).getCurrentAuthKeyId();
                    ConnectionsManager.getInstance(this.val$currentAccount).setDefaultDatacenterId(i);
                    finishpasskeylogin.flags = 1 | finishpasskeylogin.flags;
                    finishpasskeylogin.from_dc_id = currentDatacenterId;
                    finishpasskeylogin.from_auth_key_id = currentAuthKeyId;
                }
                ConnectionsManager connectionsManager = ConnectionsManager.getInstance(this.val$currentAccount);
                BotForumHelper$$ExternalSyntheticLambda2 botForumHelper$$ExternalSyntheticLambda2 = new BotForumHelper$$ExternalSyntheticLambda2();
                final Utilities.Callback3 callback3 = this.val$done;
                final int iSendRequestTyped = connectionsManager.sendRequestTyped(finishpasskeylogin, botForumHelper$$ExternalSyntheticLambda2, new Utilities.Callback2() { // from class: org.telegram.messenger.PasskeysController$1$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) {
                        PasskeysController.C23651.$r8$lambda$gx4vqzEbMkPbuY3kEBWRgHgji2Q(alertDialog, callback3, j, (TLRPC.auth_Authorization) obj, (TLRPC.TL_error) obj2);
                    }
                }, i, 72);
                final int i2 = this.val$currentAccount;
                final Utilities.Callback3 callback32 = this.val$done;
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.messenger.PasskeysController$1$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        PasskeysController.C23651.$r8$lambda$aD_TSB9_08HxxMQbIkVP1CwvnZ0(i2, iSendRequestTyped, callback32, j, dialogInterface);
                    }
                });
            } catch (Exception e) {
                FileLog.m1160e(e);
                this.val$done.run(0L, null, e.getMessage());
            }
        }

        public static /* synthetic */ void $r8$lambda$gx4vqzEbMkPbuY3kEBWRgHgji2Q(AlertDialog alertDialog, Utilities.Callback3 callback3, long j, TLRPC.auth_Authorization auth_authorization, TLRPC.TL_error tL_error) {
            alertDialog.dismiss();
            if (tL_error != null) {
                callback3.run(Long.valueOf(j), null, tL_error.text);
            } else {
                callback3.run(Long.valueOf(j), auth_authorization, null);
            }
        }

        public static /* synthetic */ void $r8$lambda$aD_TSB9_08HxxMQbIkVP1CwvnZ0(int i, int i2, Utilities.Callback3 callback3, long j, DialogInterface dialogInterface) {
            ConnectionsManager.getInstance(i).cancelRequest(i2, true);
            callback3.run(Long.valueOf(j), null, "CANCELLED");
        }

        @Override // androidx.credentials.CredentialManagerCallback
        public void onError(GetCredentialException getCredentialException) {
            if (getCredentialException instanceof NoCredentialException) {
                this.val$done.run(0L, null, "EMPTY");
                return;
            }
            if (getCredentialException instanceof GetCredentialCancellationException) {
                this.val$done.run(0L, null, "CANCELLED");
            } else if (getCredentialException instanceof GetCredentialInterruptedException) {
                this.val$done.run(0L, null, "CANCELLED");
            } else if (getCredentialException != null) {
                this.val$done.run(0L, null, getCredentialException.getMessage());
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$5n4wwV_fOePzb_daYbBtOzbOsz4(boolean[] zArr, Runnable[] runnableArr) {
        zArr[0] = true;
        Runnable runnable = runnableArr[0];
        if (runnable != null) {
            runnable.run();
        }
    }

    public static <T> Continuation<T> ktxCallback(Utilities.Callback2<T, Throwable> callback2) {
        return ktxCallback(EmptyCoroutineContext.INSTANCE, callback2);
    }

    public static <T> Continuation<T> ktxCallback(final CoroutineContext coroutineContext, final Utilities.Callback2<T, Throwable> callback2) {
        return new Continuation<T>() { // from class: org.telegram.messenger.PasskeysController.2
            @Override // kotlin.coroutines.Continuation
            public CoroutineContext getContext() {
                return coroutineContext;
            }

            @Override // kotlin.coroutines.Continuation
            public void resumeWith(Object obj) {
                if (obj instanceof Result.Failure) {
                    callback2.run(null, ((Result.Failure) obj).exception);
                } else {
                    callback2.run(obj, null);
                }
            }
        };
    }

    public static String bytesToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}
