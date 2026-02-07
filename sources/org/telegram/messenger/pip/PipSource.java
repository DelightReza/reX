package org.telegram.messenger.pip;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.ComponentCallbacks2;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.exoplayer2.Player;
import java.util.ArrayList;
import org.telegram.messenger.pip.activity.IPipActivity;
import org.telegram.messenger.pip.activity.IPipActivityActionListener;
import org.telegram.messenger.pip.source.IPipSourceDelegate;
import org.telegram.messenger.pip.source.PipSourceHandlerState2;
import org.telegram.messenger.pip.utils.PipPositionObserver;
import org.telegram.messenger.pip.utils.PipSourceParams;
import org.telegram.messenger.pip.utils.PipUtils;

/* loaded from: classes4.dex */
public class PipSource {
    private static int sourceIdCounter;
    private static final Rect tmpRect = new Rect();
    public View contentView;
    public final PipActivityController controller;
    public final int cornerRadius;
    public final IPipSourceDelegate delegate;
    private boolean isAvailable;
    public final boolean needMediaSession;
    public final PipSourceParams params;
    private final PipPositionObserver pipPositionObserver;
    public View placeholderView;
    Player player;
    public final int priority;
    private ArrayList remoteActions;
    public final int sourceId;
    public final PipSourceHandlerState2 state2;
    public final String tag;

    public void invalidateActions() {
    }

    private PipSource(PipActivityController pipActivityController, Builder builder) {
        int i = sourceIdCounter;
        sourceIdCounter = i + 1;
        this.sourceId = i;
        PipSourceParams pipSourceParams = new PipSourceParams();
        this.params = pipSourceParams;
        this.pipPositionObserver = new PipPositionObserver(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.telegram.messenger.pip.PipSource$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                this.f$0.invalidatePosition();
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append(builder.tagPrefix != null ? builder.tagPrefix : "pip-source");
        sb.append("-");
        sb.append(i);
        this.tag = sb.toString();
        this.delegate = builder.delegate;
        Builder.m4195$$Nest$fgetactionListener(builder);
        this.priority = builder.priority;
        this.cornerRadius = builder.cornerRadius;
        this.needMediaSession = builder.needMediaSession;
        this.controller = pipActivityController;
        pipSourceParams.setRatio(builder.width, builder.height);
        this.player = builder.player;
        this.placeholderView = builder.placeholderView;
        this.state2 = new PipSourceHandlerState2(this);
        setContentView(builder.contentView);
        checkAvailable(false);
        invalidateActions();
        pipActivityController.dispatchSourceRegister(this);
    }

    public void destroy() {
        this.pipPositionObserver.stop();
        this.controller.dispatchSourceUnregister(this);
    }

    public void setContentView(View view) {
        this.pipPositionObserver.start(view);
        this.contentView = view;
        if (view != null) {
            updateContentPosition(view);
        }
    }

    public void setPlaceholderView(View view) {
        this.placeholderView = view;
    }

    public void setContentRatio(int i, int i2) {
        if (this.params.setRatio(i, i2)) {
            checkAvailable(true);
            this.controller.dispatchSourceParamsChanged(this);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
        checkAvailable(true);
        this.controller.dispatchSourceParamsChanged(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateContentPosition(android.view.View r4) {
        /*
            r3 = this;
            org.telegram.messenger.pip.PipActivityController r0 = r3.controller
            android.app.Activity r0 = r0.activity
            boolean r0 = org.telegram.messenger.AndroidUtilities.isInPictureInPictureMode(r0)
            if (r0 == 0) goto Lb
            goto L52
        Lb:
            org.telegram.messenger.pip.PipActivityController r0 = r3.controller
            android.app.Activity r0 = r0.activity
            android.graphics.Rect r1 = org.telegram.messenger.pip.PipSource.tmpRect
            org.telegram.messenger.pip.utils.PipUtils.getPipSourceRectHintPosition(r0, r4, r1)
            org.telegram.messenger.pip.utils.PipSourceParams r0 = r3.params
            boolean r0 = r0.setPosition(r1)
            boolean r1 = r4 instanceof org.webrtc.TextureViewRenderer
            if (r1 == 0) goto L2c
            org.webrtc.TextureViewRenderer r4 = (org.webrtc.TextureViewRenderer) r4
            int r1 = r4.rotatedFrameWidth
            int r4 = r4.rotatedFrameHeight
            org.telegram.messenger.pip.utils.PipSourceParams r2 = r3.params
            boolean r4 = r2.setRatio(r1, r4)
        L2a:
            r0 = r0 | r4
            goto L47
        L2c:
            int r1 = r4.getWidth()
            if (r1 == 0) goto L47
            int r1 = r4.getHeight()
            if (r1 == 0) goto L47
            int r1 = r4.getWidth()
            int r4 = r4.getHeight()
            org.telegram.messenger.pip.utils.PipSourceParams r2 = r3.params
            boolean r4 = r2.setRatio(r1, r4)
            goto L2a
        L47:
            if (r0 == 0) goto L52
            r4 = 1
            r3.checkAvailable(r4)
            org.telegram.messenger.pip.PipActivityController r4 = r3.controller
            r4.dispatchSourceParamsChanged(r3)
        L52:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.pip.PipSource.updateContentPosition(android.view.View):void");
    }

    public void invalidatePosition() {
        View view = this.contentView;
        if (view != null) {
            updateContentPosition(view);
        }
    }

    public PictureInPictureParams buildPictureInPictureParams() {
        PictureInPictureParams.Builder builderBuild = this.params.build();
        builderBuild.setActions(this.remoteActions);
        if (Build.VERSION.SDK_INT >= 31) {
            builderBuild.setAutoEnterEnabled(PipUtils.useAutoEnterInPictureInPictureMode());
        }
        return builderBuild.build();
    }

    private void checkAvailable(boolean z) {
        boolean z2 = this.params.isValid() && this.delegate.pipIsAvailable();
        if (this.isAvailable != z2) {
            this.isAvailable = z2;
            if (z) {
                this.controller.dispatchSourceAvailabilityChanged(this);
            }
        }
    }

    public void invalidateAvailability() {
        checkAvailable(true);
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public static class Builder {
        private final Activity activity;
        private View contentView;
        private int cornerRadius;
        private final IPipSourceDelegate delegate;
        private int height;
        private View placeholderView;
        private Player player;
        private String tagPrefix;
        private int width;
        private int priority = 0;
        private boolean needMediaSession = false;

        /* renamed from: -$$Nest$fgetactionListener, reason: not valid java name */
        static /* bridge */ /* synthetic */ IPipActivityActionListener m4195$$Nest$fgetactionListener(Builder builder) {
            builder.getClass();
            return null;
        }

        public Builder(Activity activity, IPipSourceDelegate iPipSourceDelegate) {
            this.activity = activity;
            this.delegate = iPipSourceDelegate;
        }

        public Builder setTagPrefix(String str) {
            this.tagPrefix = str;
            return this;
        }

        public Builder setPriority(int i) {
            this.priority = i;
            return this;
        }

        public Builder setPlaceholderView(View view) {
            this.placeholderView = view;
            return this;
        }

        public Builder setCornerRadius(int i) {
            this.cornerRadius = i;
            return this;
        }

        public Builder setNeedMediaSession(boolean z) {
            this.needMediaSession = z;
            return this;
        }

        public Builder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        public Builder setContentRatio(int i, int i2) {
            this.width = i;
            this.height = i2;
            return this;
        }

        public Builder setPlayer(Player player) {
            this.player = player;
            return this;
        }

        public PipSource build() {
            ComponentCallbacks2 componentCallbacks2 = this.activity;
            if (componentCallbacks2 instanceof IPipActivity) {
                return new PipSource(((IPipActivity) componentCallbacks2).getPipController(), this);
            }
            return null;
        }
    }
}
