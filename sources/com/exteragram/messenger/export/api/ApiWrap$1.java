package com.exteragram.messenger.export.api;

import com.exteragram.messenger.export.api.ApiWrap$HistoryMessageMarkupButton;
import com.exteragram.messenger.export.api.ApiWrap$Reaction;

/* loaded from: classes3.dex */
abstract /* synthetic */ class ApiWrap$1 {

    /* renamed from: $SwitchMap$com$exteragram$messenger$export$api$ApiWrap$HistoryMessageMarkupButton$Type */
    static final /* synthetic */ int[] f152x5e9458ff;

    /* renamed from: $SwitchMap$com$exteragram$messenger$export$api$ApiWrap$Reaction$Type */
    static final /* synthetic */ int[] f153x894b9bc3;

    static {
        int[] iArr = new int[ApiWrap$Reaction.Type.values().length];
        f153x894b9bc3 = iArr;
        try {
            iArr[ApiWrap$Reaction.Type.Empty.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f153x894b9bc3[ApiWrap$Reaction.Type.Emoji.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f153x894b9bc3[ApiWrap$Reaction.Type.CustomEmoji.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f153x894b9bc3[ApiWrap$Reaction.Type.Paid.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        int[] iArr2 = new int[ApiWrap$HistoryMessageMarkupButton.Type.values().length];
        f152x5e9458ff = iArr2;
        try {
            iArr2[ApiWrap$HistoryMessageMarkupButton.Type.Default.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.Url.ordinal()] = 2;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.Callback.ordinal()] = 3;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.CallbackWithPassword.ordinal()] = 4;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.RequestPhone.ordinal()] = 5;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.RequestLocation.ordinal()] = 6;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.RequestPoll.ordinal()] = 7;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.RequestPeer.ordinal()] = 8;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.SwitchInline.ordinal()] = 9;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.SwitchInlineSame.ordinal()] = 10;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.Game.ordinal()] = 11;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.Buy.ordinal()] = 12;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.Auth.ordinal()] = 13;
        } catch (NoSuchFieldError unused17) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.UserProfile.ordinal()] = 14;
        } catch (NoSuchFieldError unused18) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.WebView.ordinal()] = 15;
        } catch (NoSuchFieldError unused19) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.SimpleWebView.ordinal()] = 16;
        } catch (NoSuchFieldError unused20) {
        }
        try {
            f152x5e9458ff[ApiWrap$HistoryMessageMarkupButton.Type.CopyText.ordinal()] = 17;
        } catch (NoSuchFieldError unused21) {
        }
    }
}
