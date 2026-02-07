package org.telegram.p023ui.Stars;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.RenderEffect;
import android.graphics.RuntimeShader;
import android.view.View;
import com.google.zxing.common.detector.MathUtils;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.NotificationBadge;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.blur3.LiquidGlassEffect$$ExternalSyntheticApiModelOutline0;

/* loaded from: classes6.dex */
public class SuperRipple extends ISuperRipple {
    public final int MAX_COUNT;
    public final float[] centerX;
    public final float[] centerY;
    public int count;
    public float density;
    public RenderEffect effect;
    public final ArrayList effects;
    public int height;
    public final float[] intensity;
    public final RuntimeShader shader;

    /* renamed from: t */
    public final float[] f2067t;
    public int width;

    public static class Effect {
        public final ValueAnimator animator;

        /* renamed from: cx */
        public final float f2068cx;

        /* renamed from: cy */
        public final float f2069cy;
        public final float intensity;

        /* renamed from: t */
        public float f2070t;

        private Effect(float f, float f2, float f3, ValueAnimator valueAnimator) {
            this.f2068cx = f;
            this.f2069cy = f2;
            this.intensity = f3;
            this.animator = valueAnimator;
        }
    }

    public SuperRipple(View view) {
        super(view);
        this.effects = new ArrayList();
        this.MAX_COUNT = 7;
        this.f2067t = new float[7];
        this.centerX = new float[7];
        this.centerY = new float[7];
        this.intensity = new float[7];
        RuntimeShader runtimeShaderM1299m = LiquidGlassEffect$$ExternalSyntheticApiModelOutline0.m1299m(AndroidUtilities.readRes(C2369R.raw.superripple_effect));
        this.shader = runtimeShaderM1299m;
        setupSizeUniforms(true);
        this.effect = RenderEffect.createRuntimeShaderEffect(runtimeShaderM1299m, "img");
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setupSizeUniforms(boolean r11) {
        /*
            r10 = this;
            if (r11 != 0) goto L28
            int r11 = r10.width
            android.view.View r0 = r10.view
            int r0 = r0.getWidth()
            if (r11 != r0) goto L28
            int r11 = r10.height
            android.view.View r0 = r10.view
            int r0 = r0.getHeight()
            if (r11 != r0) goto L28
            float r11 = r10.density
            float r0 = org.telegram.messenger.AndroidUtilities.density
            float r11 = r11 - r0
            float r11 = java.lang.Math.abs(r11)
            r0 = 1008981770(0x3c23d70a, float:0.01)
            int r11 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r11 <= 0) goto L27
            goto L28
        L27:
            return
        L28:
            android.graphics.RuntimeShader r11 = r10.shader
            android.view.View r0 = r10.view
            int r0 = r0.getWidth()
            r10.width = r0
            float r0 = (float) r0
            android.view.View r1 = r10.view
            int r1 = r1.getHeight()
            r10.height = r1
            float r1 = (float) r1
            java.lang.String r2 = "size"
            r11.setFloatUniform(r2, r0, r1)
            android.graphics.RuntimeShader r11 = r10.shader
            float r0 = org.telegram.messenger.AndroidUtilities.density
            r10.density = r0
            java.lang.String r1 = "density"
            r11.setFloatUniform(r1, r0)
            android.view.View r11 = r10.view
            android.view.WindowInsets r11 = r11.getRootWindowInsets()
            r0 = 0
            if (r11 != 0) goto L57
            r1 = r0
            goto L5c
        L57:
            r1 = 0
            android.view.RoundedCorner r1 = r11.getRoundedCorner(r1)
        L5c:
            if (r11 != 0) goto L60
            r2 = r0
            goto L65
        L60:
            r2 = 1
            android.view.RoundedCorner r2 = r11.getRoundedCorner(r2)
        L65:
            if (r11 != 0) goto L69
            r3 = r0
            goto L6e
        L69:
            r3 = 3
            android.view.RoundedCorner r3 = r11.getRoundedCorner(r3)
        L6e:
            if (r11 != 0) goto L71
            goto L76
        L71:
            r0 = 2
            android.view.RoundedCorner r0 = r11.getRoundedCorner(r0)
        L76:
            android.graphics.RuntimeShader r4 = r10.shader
            r11 = 0
            if (r0 == 0) goto L8f
            android.view.View r5 = r10.view
            android.view.View r6 = r5.getRootView()
            if (r5 == r6) goto L88
            int r5 = org.telegram.messenger.AndroidUtilities.navigationBarHeight
            if (r5 <= 0) goto L88
            goto L8f
        L88:
            int r0 = r0.getRadius()
            float r0 = (float) r0
            r6 = r0
            goto L90
        L8f:
            r6 = 0
        L90:
            if (r2 != 0) goto L94
            r7 = 0
            goto L9a
        L94:
            int r0 = r2.getRadius()
            float r0 = (float) r0
            r7 = r0
        L9a:
            if (r3 == 0) goto Lb0
            android.view.View r0 = r10.view
            android.view.View r2 = r0.getRootView()
            if (r0 == r2) goto La9
            int r0 = org.telegram.messenger.AndroidUtilities.navigationBarHeight
            if (r0 <= 0) goto La9
            goto Lb0
        La9:
            int r0 = r3.getRadius()
            float r0 = (float) r0
            r8 = r0
            goto Lb1
        Lb0:
            r8 = 0
        Lb1:
            if (r1 != 0) goto Lb5
            r9 = 0
            goto Lbb
        Lb5:
            int r11 = r1.getRadius()
            float r11 = (float) r11
            r9 = r11
        Lbb:
            java.lang.String r5 = "radius"
            r4.setFloatUniform(r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Stars.SuperRipple.setupSizeUniforms(boolean):void");
    }

    @Override // org.telegram.p023ui.Stars.ISuperRipple
    public void animate(float f, float f2, float f3) {
        if (this.effects.size() >= 7) {
            return;
        }
        float fMax = (Math.max(Math.max(MathUtils.distance(0.0f, 0.0f, f, f2), MathUtils.distance(this.view.getWidth(), 0.0f, f, f2)), Math.max(MathUtils.distance(0.0f, this.view.getHeight(), f, f2), MathUtils.distance(this.view.getWidth(), this.view.getHeight(), f, f2))) * 2.0f) / (AndroidUtilities.density * 1200.0f);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, fMax);
        final Effect effect = new Effect(f, f2, f3, valueAnimatorOfFloat);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Stars.SuperRipple$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$animate$0(effect, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Stars.SuperRipple.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SuperRipple.this.effects.remove(effect);
                SuperRipple.this.updateProperties();
            }
        });
        valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT);
        valueAnimatorOfFloat.setDuration((long) (fMax * 1000.0f));
        this.effects.add(effect);
        updateProperties();
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animate$0(Effect effect, ValueAnimator valueAnimator) {
        effect.f2070t = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateProperties();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProperties() {
        boolean z = false;
        if (!this.effects.isEmpty()) {
            boolean z2 = true;
            boolean z3 = this.count != Math.min(7, this.effects.size());
            this.count = Math.min(7, this.effects.size());
            for (int i = 0; i < this.count; i++) {
                Effect effect = (Effect) this.effects.get(i);
                boolean z4 = z3 || Math.abs(this.f2067t[i] - effect.f2070t) > 0.001f;
                this.f2067t[i] = effect.f2070t;
                boolean z5 = z4 || Math.abs(this.centerX[i] - effect.f2068cx) > 0.001f;
                this.centerX[i] = effect.f2068cx;
                boolean z6 = z5 || Math.abs(this.centerY[i] - effect.f2069cy) > 0.001f;
                this.centerY[i] = effect.f2069cy;
                z3 = z6 || Math.abs(this.intensity[i] - effect.intensity) > 0.001f;
                this.intensity[i] = effect.intensity;
            }
            if (!z3 && this.width == this.view.getWidth() && this.height == this.view.getHeight() && Math.abs(this.density - AndroidUtilities.density) <= 0.01f) {
                z2 = false;
            }
            if (z2) {
                this.shader.setIntUniform(NotificationBadge.NewHtcHomeBadger.COUNT, this.count);
                this.shader.setFloatUniform("t", this.f2067t);
                this.shader.setFloatUniform("centerX", this.centerX);
                this.shader.setFloatUniform("centerY", this.centerY);
                this.shader.setFloatUniform("intensity", this.intensity);
                setupSizeUniforms(false);
                this.effect = RenderEffect.createRuntimeShaderEffect(this.shader, "img");
            }
            z = z2;
        }
        this.view.setRenderEffect(this.effects.isEmpty() ? null : this.effect);
        if (z) {
            this.view.invalidate();
        }
    }
}
