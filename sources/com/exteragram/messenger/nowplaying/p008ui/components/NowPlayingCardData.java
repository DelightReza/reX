package com.exteragram.messenger.nowplaying.p008ui.components;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.camera.camera2.internal.compat.params.AbstractC0161x440b9a8e;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;
import androidx.work.Constraints$ContentUriTrigger$$ExternalSyntheticBackport0;
import com.exteragram.messenger.api.dto.NowPlayingDTO;
import com.exteragram.messenger.nowplaying.p008ui.components.NowPlayingCardData;
import com.exteragram.messenger.utils.p011ui.CanvasUtils;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public final class NowPlayingCardData {
    public static final Companion Companion = new Companion(null);
    private final Integer accentColor;
    private final Integer backgroundColor;
    private final Bitmap coverBitmap;
    private final ImageLocation imageLocation;
    private final NowPlayingDTO nowPlayingDTO;
    private boolean useContainers;
    private long userEmoji;

    public interface Callback {
        void onDataLoaded(NowPlayingCardData nowPlayingCardData);
    }

    public static final void create(NowPlayingDTO nowPlayingDTO, TLRPC.Document document, Callback callback) {
        Companion.create(nowPlayingDTO, document, callback);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NowPlayingCardData)) {
            return false;
        }
        NowPlayingCardData nowPlayingCardData = (NowPlayingCardData) obj;
        return Intrinsics.areEqual(this.nowPlayingDTO, nowPlayingCardData.nowPlayingDTO) && Intrinsics.areEqual(this.backgroundColor, nowPlayingCardData.backgroundColor) && Intrinsics.areEqual(this.accentColor, nowPlayingCardData.accentColor) && Intrinsics.areEqual(this.coverBitmap, nowPlayingCardData.coverBitmap) && Intrinsics.areEqual(this.imageLocation, nowPlayingCardData.imageLocation) && this.userEmoji == nowPlayingCardData.userEmoji && this.useContainers == nowPlayingCardData.useContainers;
    }

    public int hashCode() {
        int iHashCode = this.nowPlayingDTO.hashCode() * 31;
        Integer num = this.backgroundColor;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.accentColor;
        int iHashCode3 = (iHashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Bitmap bitmap = this.coverBitmap;
        int iHashCode4 = (iHashCode3 + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        ImageLocation imageLocation = this.imageLocation;
        return ((((iHashCode4 + (imageLocation != null ? imageLocation.hashCode() : 0)) * 31) + AbstractC0161x440b9a8e.m38m(this.userEmoji)) * 31) + Constraints$ContentUriTrigger$$ExternalSyntheticBackport0.m175m(this.useContainers);
    }

    public String toString() {
        return "NowPlayingCardData(nowPlayingDTO=" + this.nowPlayingDTO + ", backgroundColor=" + this.backgroundColor + ", accentColor=" + this.accentColor + ", coverBitmap=" + this.coverBitmap + ", imageLocation=" + this.imageLocation + ", userEmoji=" + this.userEmoji + ", useContainers=" + this.useContainers + ')';
    }

    public NowPlayingCardData(NowPlayingDTO nowPlayingDTO, Integer num, Integer num2, Bitmap bitmap, ImageLocation imageLocation, long j, boolean z) {
        Intrinsics.checkNotNullParameter(nowPlayingDTO, "nowPlayingDTO");
        this.nowPlayingDTO = nowPlayingDTO;
        this.backgroundColor = num;
        this.accentColor = num2;
        this.coverBitmap = bitmap;
        this.imageLocation = imageLocation;
        this.userEmoji = j;
        this.useContainers = z;
    }

    public /* synthetic */ NowPlayingCardData(NowPlayingDTO nowPlayingDTO, Integer num, Integer num2, Bitmap bitmap, ImageLocation imageLocation, long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(nowPlayingDTO, num, num2, bitmap, (i & 16) != 0 ? null : imageLocation, (i & 32) != 0 ? -1L : j, (i & 64) != 0 ? false : z);
    }

    public final NowPlayingDTO getNowPlayingDTO() {
        return this.nowPlayingDTO;
    }

    public final Integer getBackgroundColor() {
        return this.backgroundColor;
    }

    public final Integer getAccentColor() {
        return this.accentColor;
    }

    public final Bitmap getCoverBitmap() {
        return this.coverBitmap;
    }

    public final ImageLocation getImageLocation() {
        return this.imageLocation;
    }

    public final long getUserEmoji() {
        return this.userEmoji;
    }

    public final void setUserEmoji(long j) {
        this.userEmoji = j;
    }

    public final boolean getUseContainers() {
        return this.useContainers;
    }

    public final void setUseContainers(boolean z) {
        this.useContainers = z;
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void create(final NowPlayingDTO nowPlayingDTO, TLRPC.Document document, final Callback callback) {
            String coverUrl;
            TLRPC.PhotoSize closestPhotoSizeWithSize;
            Intrinsics.checkNotNullParameter(nowPlayingDTO, "nowPlayingDTO");
            Intrinsics.checkNotNullParameter(callback, "callback");
            final ImageLocation forDocument = (!Intrinsics.areEqual(nowPlayingDTO.getPlatform(), "TELEGRAM") || document == null || (closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, MediaDataController.MAX_STYLE_RUNS_COUNT)) == null) ? null : ImageLocation.getForDocument(closestPhotoSizeWithSize, document);
            if (forDocument == null && (coverUrl = nowPlayingDTO.getCoverUrl()) != null && coverUrl.length() != 0) {
                forDocument = ImageLocation.getForPath(nowPlayingDTO.getCoverUrl());
            }
            if (forDocument == null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.nowplaying.ui.components.NowPlayingCardData$Companion$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        NowPlayingCardData.Companion.create$lambda$0(callback, nowPlayingDTO);
                    }
                });
            } else {
                final ImageReceiver imageReceiver = new ImageReceiver(null);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.nowplaying.ui.components.NowPlayingCardData$Companion$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        NowPlayingCardData.Companion.create$lambda$4(imageReceiver, forDocument, callback, nowPlayingDTO);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void create$lambda$0(Callback callback, NowPlayingDTO nowPlayingDTO) {
            callback.onDataLoaded(new NowPlayingCardData(nowPlayingDTO, null, null, null, null, 0L, false, 96, null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void create$lambda$4(ImageReceiver imageReceiver, final ImageLocation imageLocation, final Callback callback, final NowPlayingDTO nowPlayingDTO) {
            imageReceiver.onAttachedToWindow();
            imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: com.exteragram.messenger.nowplaying.ui.components.NowPlayingCardData$Companion$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
                public final void didSetImage(ImageReceiver imageReceiver2, boolean z, boolean z2, boolean z3) {
                    NowPlayingCardData.Companion.create$lambda$4$lambda$3(callback, nowPlayingDTO, imageLocation, imageReceiver2, z, z2, z3);
                }

                @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
                public /* synthetic */ void didSetImageBitmap(int i, String str, Drawable drawable) {
                    ImageReceiver.ImageReceiverDelegate.CC.$default$didSetImageBitmap(this, i, str, drawable);
                }

                @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
                public /* synthetic */ void onAnimationReady(ImageReceiver imageReceiver2) {
                    ImageReceiver.ImageReceiverDelegate.CC.$default$onAnimationReady(this, imageReceiver2);
                }
            });
            imageReceiver.setImage(imageLocation, null, null, null, null, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void create$lambda$4$lambda$3(final Callback callback, final NowPlayingDTO nowPlayingDTO, final ImageLocation imageLocation, final ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
            if (!z || z2) {
                return;
            }
            final Bitmap bitmap = imageReceiver.getBitmap();
            Utilities.themeQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.nowplaying.ui.components.NowPlayingCardData$Companion$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NowPlayingCardData.Companion.create$lambda$4$lambda$3$lambda$2(bitmap, callback, nowPlayingDTO, imageLocation, imageReceiver);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void create$lambda$4$lambda$3$lambda$2(final Bitmap bitmap, final Callback callback, final NowPlayingDTO nowPlayingDTO, final ImageLocation imageLocation, final ImageReceiver imageReceiver) {
            Pair pairExtractColors = NowPlayingCardData.Companion.extractColors(bitmap);
            final Integer num = (Integer) pairExtractColors.component1();
            final Integer num2 = (Integer) pairExtractColors.component2();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.nowplaying.ui.components.NowPlayingCardData$Companion$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    NowPlayingCardData.Companion.create$lambda$4$lambda$3$lambda$2$lambda$1(callback, nowPlayingDTO, num, num2, bitmap, imageLocation, imageReceiver);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void create$lambda$4$lambda$3$lambda$2$lambda$1(Callback callback, NowPlayingDTO nowPlayingDTO, Integer num, Integer num2, Bitmap bitmap, ImageLocation imageLocation, ImageReceiver imageReceiver) {
            callback.onDataLoaded(new NowPlayingCardData(nowPlayingDTO, num, num2, bitmap, imageLocation, 0L, false, 96, null));
            imageReceiver.onDetachedFromWindow();
        }

        private final Pair extractColors(Bitmap bitmap) {
            int iIntValue;
            if (bitmap == null) {
                return TuplesKt.m1122to(null, null);
            }
            Palette paletteGenerate = Palette.from(bitmap).generate();
            Intrinsics.checkNotNullExpressionValue(paletteGenerate, "generate(...)");
            Palette.Swatch darkVibrantSwatch = paletteGenerate.getDarkVibrantSwatch();
            if (darkVibrantSwatch != null) {
                iIntValue = darkVibrantSwatch.getRgb();
            } else {
                Palette.Swatch mutedSwatch = paletteGenerate.getMutedSwatch();
                if (mutedSwatch != null) {
                    iIntValue = mutedSwatch.getRgb();
                } else {
                    Palette.Swatch darkMutedSwatch = paletteGenerate.getDarkMutedSwatch();
                    Integer numValueOf = darkMutedSwatch != null ? Integer.valueOf(darkMutedSwatch.getRgb()) : null;
                    if (numValueOf != null) {
                        iIntValue = numValueOf.intValue();
                    } else {
                        Palette.Swatch dominantSwatch = paletteGenerate.getDominantSwatch();
                        Integer numValueOf2 = dominantSwatch != null ? Integer.valueOf(dominantSwatch.getRgb()) : null;
                        iIntValue = numValueOf2 != null ? numValueOf2.intValue() : AndroidUtilities.getDominantColor(bitmap);
                    }
                }
            }
            int iAdjustHsl$default = iIntValue;
            double dCalculateContrast = ColorUtils.calculateContrast(-1, iAdjustHsl$default);
            if (dCalculateContrast > 15.0d) {
                iAdjustHsl$default = CanvasUtils.adjustHsl$default(CanvasUtils.INSTANCE, iAdjustHsl$default, 2.0f, 0.0f, 4, null);
            } else if (dCalculateContrast < 10.0d) {
                iAdjustHsl$default = CanvasUtils.adjustHsl$default(CanvasUtils.INSTANCE, iAdjustHsl$default, 0.5f, 0.0f, 4, null);
            }
            if (ColorUtils.calculateContrast(-1, iAdjustHsl$default) < 3.0d) {
                iAdjustHsl$default = ColorUtils.blendARGB(iAdjustHsl$default, -16777216, 0.3f);
            }
            int i = iAdjustHsl$default;
            float[] fArr = new float[3];
            ColorUtils.colorToHSL(i, fArr);
            float f = fArr[2];
            return TuplesKt.m1122to(Integer.valueOf(i), Integer.valueOf(CanvasUtils.adjustHsl$default(CanvasUtils.INSTANCE, i, (0.0f > f || f > 0.25f) ? (0.25f > f || f > 0.5f) ? (0.5f > f || f > 0.75f) ? 0.5f : 1.0f : 1.5f : 2.0f, 0.0f, 4, null)));
        }
    }
}
