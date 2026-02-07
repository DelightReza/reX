package org.telegram.messenger.audioinfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import androidx.camera.camera2.impl.AbstractC0111xb15b98f9;
import java.io.File;
import org.mvel2.MVEL;
import org.telegram.messenger.FileLog;

/* loaded from: classes4.dex */
public class OtherAudioInfo extends AudioInfo {
    public boolean failed;

    /* renamed from: r */
    private final MediaMetadataRetriever f1480r;

    public OtherAudioInfo(File file) throws IllegalArgumentException {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        this.f1480r = mediaMetadataRetriever;
        try {
            mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
            this.brand = "OTHER";
            this.version = MVEL.VERSION_SUB;
            this.duration = getLong(9);
            this.title = getString(7);
            this.artist = getString(2);
            this.albumArtist = getString(13);
            this.album = getString(1);
            this.year = getShort(8);
            this.genre = getString(6);
            this.track = getShort(0);
            this.tracks = getShort(10);
            this.disc = getShort(14);
            this.composer = getString(4);
            byte[] embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
            if (embeddedPicture != null) {
                this.cover = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.length);
            }
            if (this.cover != null) {
                float fMax = Math.max(r5.getWidth(), this.cover.getHeight()) / 120.0f;
                if (fMax > 0.0f) {
                    this.smallCover = Bitmap.createScaledBitmap(this.cover, (int) (r0.getWidth() / fMax), (int) (this.cover.getHeight() / fMax), true);
                } else {
                    this.smallCover = this.cover;
                }
            }
        } catch (Exception e) {
            this.failed = true;
            FileLog.m1160e(e);
        }
        try {
            MediaMetadataRetriever mediaMetadataRetriever2 = this.f1480r;
            if (mediaMetadataRetriever2 != null) {
                AbstractC0111xb15b98f9.m15m(mediaMetadataRetriever2);
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
    }

    private String getString(int i) {
        try {
            return this.f1480r.extractMetadata(i);
        } catch (Exception unused) {
            return null;
        }
    }

    private short getShort(int i) {
        try {
            return Short.parseShort(this.f1480r.extractMetadata(i));
        } catch (Exception unused) {
            return (short) 0;
        }
    }

    private long getLong(int i) {
        try {
            return Long.parseLong(this.f1480r.extractMetadata(i));
        } catch (Exception unused) {
            return 0L;
        }
    }
}
