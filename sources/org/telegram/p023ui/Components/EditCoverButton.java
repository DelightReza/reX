package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public class EditCoverButton extends View {
    private final Drawable arrowDrawable;
    private final PhotoViewerBlurDrawable blur;
    private final ButtonBounce bounce;
    private final RectF imageBounds;
    private ImageReceiver imageReceiver;
    private View.OnClickListener listener;
    private final Text text;

    public EditCoverButton(Context context, PhotoViewer photoViewer, CharSequence charSequence, boolean z) {
        super(context);
        this.bounce = new ButtonBounce(this);
        this.imageBounds = new RectF();
        ImageReceiver imageReceiver = new ImageReceiver(this);
        this.imageReceiver = imageReceiver;
        imageReceiver.setRoundRadius(AndroidUtilities.m1146dp(22.66f));
        this.text = new Text(charSequence, 14.0f, AndroidUtilities.bold());
        this.blur = new PhotoViewerBlurDrawable(photoViewer, photoViewer.blurManager, this).setApplyBounds(false);
        if (z) {
            Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.arrow_newchat).mutate();
            this.arrowDrawable = drawableMutate;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(-1711276033, PorterDuff.Mode.SRC_IN));
            return;
        }
        this.arrowDrawable = null;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.imageReceiver.onAttachedToWindow();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.imageReceiver.onDetachedFromWindow();
    }

    /* renamed from: setImage, reason: merged with bridge method [inline-methods] */
    public void lambda$setImage$0(Bitmap bitmap) {
        this.imageReceiver.setImageBitmap(bitmap);
        invalidate();
    }

    public void setImage(TLRPC.Photo photo, Object obj) {
        if (photo == null) {
            lambda$setImage$0((Bitmap) null);
            return;
        }
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.m1146dp(48.0f), false, null, true);
        this.imageReceiver.setImage(ImageLocation.getForPhoto(closestPhotoSizeWithSize, photo), "24_24", ImageLocation.getForPhoto(FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.m1146dp(24.0f), false, closestPhotoSizeWithSize, false), photo), "24_24", 0L, null, obj, 0);
    }

    public void setImage(final String str) {
        if (str == null) {
            lambda$setImage$0((Bitmap) null);
        } else {
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.EditCoverButton$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setImage$1(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImage$1(String str) {
        final Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str);
        Canvas canvas = new Canvas(Bitmap.createBitmap(AndroidUtilities.m1146dp(26.0f), AndroidUtilities.m1146dp(26.0f), Bitmap.Config.ARGB_8888));
        Paint paint = new Paint(3);
        canvas.translate(r0.getWidth() / 2.0f, r0.getHeight() / 2.0f);
        float fMax = Math.max(r0.getWidth() / bitmapDecodeFile.getWidth(), r0.getHeight() / bitmapDecodeFile.getHeight());
        canvas.scale(fMax, fMax);
        canvas.drawBitmap(bitmapDecodeFile, (-bitmapDecodeFile.getWidth()) / 2.0f, (-bitmapDecodeFile.getHeight()) / 2.0f, paint);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EditCoverButton$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setImage$0(bitmapDecodeFile);
            }
        });
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float scale = this.bounce.getScale(0.05f);
        canvas.save();
        canvas.scale(scale, scale, getWidth() / 2.0f, getHeight() / 2.0f);
        boolean zHasBitmapImage = this.imageReceiver.hasBitmapImage();
        int iCeil = ((int) Math.ceil(this.text.getCurrentWidth())) + AndroidUtilities.m1146dp(zHasBitmapImage ? 30.33f : 11.33f) + AndroidUtilities.m1146dp(19.0f);
        int iM1146dp = AndroidUtilities.m1146dp(24.0f);
        int width = (getWidth() - iCeil) / 2;
        int height = getHeight() / 2;
        int i = iM1146dp / 2;
        int i2 = iCeil + width;
        this.blur.setBounds(width, height - i, i2, i + height);
        this.blur.draw(canvas);
        if (zHasBitmapImage) {
            this.imageBounds.set(AndroidUtilities.m1146dp(0.66f) + width, height - (AndroidUtilities.m1146dp(22.66f) / 2), AndroidUtilities.m1146dp(23.32f) + width, (AndroidUtilities.m1146dp(22.66f) / 2) + height);
            this.imageReceiver.setImageCoords(this.imageBounds);
            this.imageReceiver.draw(canvas);
        }
        this.text.draw(canvas, width + r1, height, -1, 1.0f);
        this.arrowDrawable.setBounds(i2 - AndroidUtilities.m1146dp(17.0f), height - AndroidUtilities.m1146dp(6.0f), i2 - AndroidUtilities.m1146dp(5.0f), height + AndroidUtilities.m1146dp(6.0f));
        this.arrowDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zContains = this.blur.getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY());
        if (motionEvent.getAction() == 0) {
            this.bounce.setPressed(zContains);
        } else if (motionEvent.getAction() == 2) {
            if (!zContains) {
                this.bounce.setPressed(false);
            }
        } else if (motionEvent.getAction() == 3) {
            if (this.bounce.isPressed()) {
                this.bounce.setPressed(false);
                return true;
            }
        } else if (motionEvent.getAction() == 1 && this.bounce.isPressed()) {
            this.bounce.setPressed(false);
            View.OnClickListener onClickListener = this.listener;
            if (onClickListener != null) {
                onClickListener.onClick(this);
            }
            return true;
        }
        return this.bounce.isPressed();
    }
}
