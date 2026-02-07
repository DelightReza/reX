package org.telegram.p023ui.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;
import org.mvel2.DataTypes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes6.dex */
public class SnowflakesEffect {
    private int color;
    private long lastAnimationTime;
    Bitmap particleBitmap;
    private Paint particlePaint;
    private Paint particleThinPaint;
    private int viewType;
    private Paint bitmapPaint = new Paint();
    private int colorKey = Theme.key_actionBarDefaultTitle;
    final float angleDiff = 1.0471976f;
    private final ArrayList particles = new ArrayList();
    private final ArrayList freeParticles = new ArrayList();

    private class Particle {
        float alpha;
        float currentTime;
        float lifeTime;
        int paintType;
        float scale;
        int type;
        float velocity;

        /* renamed from: vx */
        float f2000vx;

        /* renamed from: vy */
        float f2001vy;

        /* renamed from: x */
        float f2002x;

        /* renamed from: y */
        float f2003y;

        private Particle() {
        }

        public void draw(Canvas canvas) {
            if (this.type == 0) {
                int i = (int) (this.alpha * 255.0f);
                if (this.paintType == 0) {
                    SnowflakesEffect.this.particlePaint.setAlpha(i);
                } else {
                    SnowflakesEffect.this.particleThinPaint.setAlpha(i);
                }
                canvas.drawPoint(this.f2002x, this.f2003y, this.paintType == 0 ? SnowflakesEffect.this.particlePaint : SnowflakesEffect.this.particleThinPaint);
                return;
            }
            SnowflakesEffect snowflakesEffect = SnowflakesEffect.this;
            if (snowflakesEffect.particleBitmap == null) {
                snowflakesEffect.particleThinPaint.setAlpha(255);
                SnowflakesEffect.this.particleBitmap = Bitmap.createBitmap(AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(SnowflakesEffect.this.particleBitmap);
                float fDpf2 = AndroidUtilities.dpf2(2.0f) * 2.0f;
                float f = (-AndroidUtilities.dpf2(0.57f)) * 2.0f;
                float fDpf22 = 2.0f * AndroidUtilities.dpf2(1.55f);
                float f2 = -1.5707964f;
                int i2 = 0;
                while (i2 < 6) {
                    float fM1146dp = AndroidUtilities.m1146dp(8.0f);
                    float fM1146dp2 = AndroidUtilities.m1146dp(8.0f);
                    double d = f2;
                    float fCos = ((float) Math.cos(d)) * fDpf2;
                    float fSin = ((float) Math.sin(d)) * fDpf2;
                    float f3 = fCos * 0.66f;
                    canvas2.drawLine(fM1146dp, fM1146dp2, fCos + fM1146dp, fSin + fM1146dp2, SnowflakesEffect.this.particleThinPaint);
                    double d2 = (float) (d - 1.5707963267948966d);
                    double d3 = f;
                    double d4 = fDpf22;
                    float fCos2 = (float) ((Math.cos(d2) * d3) - (Math.sin(d2) * d4));
                    float f4 = fDpf22;
                    float fSin2 = (float) ((Math.sin(d2) * d3) + (Math.cos(d2) * d4));
                    float f5 = fM1146dp + f3;
                    float f6 = fM1146dp2 + (fSin * 0.66f);
                    canvas2.drawLine(f5, f6, fM1146dp + fCos2, fM1146dp2 + fSin2, SnowflakesEffect.this.particleThinPaint);
                    canvas2.drawLine(f5, f6, fM1146dp + ((float) (((-Math.cos(d2)) * d3) - (Math.sin(d2) * d4))), fM1146dp2 + ((float) (((-Math.sin(d2)) * d3) + (Math.cos(d2) * d4))), SnowflakesEffect.this.particleThinPaint);
                    f2 += 1.0471976f;
                    i2++;
                    fDpf2 = fDpf2;
                    fDpf22 = f4;
                }
            }
            SnowflakesEffect.this.bitmapPaint.setAlpha((int) (this.alpha * 255.0f));
            canvas.save();
            float f7 = this.scale;
            canvas.scale(f7, f7, this.f2002x, this.f2003y);
            SnowflakesEffect snowflakesEffect2 = SnowflakesEffect.this;
            canvas.drawBitmap(snowflakesEffect2.particleBitmap, this.f2002x, this.f2003y, snowflakesEffect2.bitmapPaint);
            canvas.restore();
        }
    }

    public SnowflakesEffect(int i) {
        this.viewType = i;
        Paint paint = new Paint(1);
        this.particlePaint = paint;
        paint.setStrokeWidth(AndroidUtilities.m1146dp(2.5f));
        Paint paint2 = this.particlePaint;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint2.setStrokeCap(cap);
        Paint paint3 = this.particlePaint;
        Paint.Style style = Paint.Style.STROKE;
        paint3.setStyle(style);
        Paint paint4 = new Paint(1);
        this.particleThinPaint = paint4;
        paint4.setStrokeWidth(AndroidUtilities.m1146dp(1.0f));
        this.particleThinPaint.setStrokeCap(cap);
        this.particleThinPaint.setStyle(style);
        updateColors();
        for (int i2 = 0; i2 < 20; i2++) {
            this.freeParticles.add(new Particle());
        }
    }

    public void setColorKey(int i) {
        this.colorKey = i;
        updateColors();
    }

    public void updateColors() {
        int color = Theme.getColor(this.colorKey) & (-1644826);
        if (this.color != color) {
            this.color = color;
            this.particlePaint.setColor(color);
            this.particleThinPaint.setColor(color);
        }
    }

    private void updateParticles(long j) {
        int size = this.particles.size();
        int i = 0;
        while (i < size) {
            Particle particle = (Particle) this.particles.get(i);
            float f = particle.currentTime;
            float f2 = particle.lifeTime;
            if (f >= f2) {
                if (this.freeParticles.size() < 40) {
                    this.freeParticles.add(particle);
                }
                this.particles.remove(i);
                i--;
                size--;
            } else {
                if (this.viewType == 0) {
                    if (f < 200.0f) {
                        particle.alpha = AndroidUtilities.accelerateInterpolator.getInterpolation(f / 200.0f);
                    } else {
                        particle.alpha = 1.0f - AndroidUtilities.decelerateInterpolator.getInterpolation((f - 200.0f) / (f2 - 200.0f));
                    }
                } else if (f < 200.0f) {
                    particle.alpha = AndroidUtilities.accelerateInterpolator.getInterpolation(f / 200.0f);
                } else if (f2 - f < 2000.0f) {
                    particle.alpha = AndroidUtilities.decelerateInterpolator.getInterpolation((f2 - f) / 2000.0f);
                }
                float f3 = particle.f2002x;
                float f4 = particle.f2000vx;
                float f5 = particle.velocity;
                float f6 = j;
                particle.f2002x = f3 + (((f4 * f5) * f6) / 500.0f);
                particle.f2003y += ((particle.f2001vy * f5) * f6) / 500.0f;
                particle.currentTime += f6;
            }
            i++;
        }
    }

    public void onDraw(View view, Canvas canvas) {
        Particle particle;
        if (view == null || canvas == null || !LiteMode.isEnabled(32)) {
            return;
        }
        int size = this.particles.size();
        for (int i = 0; i < size; i++) {
            ((Particle) this.particles.get(i)).draw(canvas);
        }
        int i2 = this.viewType;
        int i3 = i2 == 0 ? 100 : DataTypes.UNIT;
        int i4 = i2 == 0 ? 1 : 10;
        if (this.particles.size() < i3) {
            for (int i5 = 0; i5 < i4; i5++) {
                if (this.particles.size() < i3 && Utilities.random.nextFloat() > 0.7f) {
                    int i6 = AndroidUtilities.statusBarHeight;
                    float fNextFloat = Utilities.random.nextFloat() * view.getMeasuredWidth();
                    float fNextFloat2 = i6 + (Utilities.random.nextFloat() * ((view.getMeasuredHeight() - AndroidUtilities.m1146dp(20.0f)) - i6));
                    double dNextInt = (Utilities.random.nextInt(40) + 70) * 0.017453292519943295d;
                    float fCos = (float) Math.cos(dNextInt);
                    float fSin = (float) Math.sin(dNextInt);
                    if (!this.freeParticles.isEmpty()) {
                        particle = (Particle) this.freeParticles.get(0);
                        this.freeParticles.remove(0);
                    } else {
                        particle = new Particle();
                    }
                    particle.f2002x = fNextFloat;
                    particle.f2003y = fNextFloat2;
                    particle.f2000vx = fCos;
                    particle.f2001vy = fSin;
                    particle.alpha = 0.0f;
                    particle.currentTime = 0.0f;
                    particle.scale = Utilities.random.nextFloat() * 1.2f;
                    particle.type = 0;
                    particle.paintType = Utilities.random.nextInt(2);
                    if (this.viewType == 0) {
                        particle.lifeTime = Utilities.random.nextInt(100) + 2000;
                    } else {
                        particle.lifeTime = Utilities.random.nextInt(2000) + 3000;
                    }
                    particle.velocity = (Utilities.random.nextFloat() * 4.0f) + 20.0f;
                    this.particles.add(particle);
                }
            }
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        updateParticles(Math.min(17L, jCurrentTimeMillis - this.lastAnimationTime));
        this.lastAnimationTime = jCurrentTimeMillis;
        view.invalidate();
    }
}
