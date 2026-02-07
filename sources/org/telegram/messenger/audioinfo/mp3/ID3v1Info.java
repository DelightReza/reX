package org.telegram.messenger.audioinfo.mp3;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.telegram.messenger.audioinfo.AudioInfo;

/* loaded from: classes4.dex */
public class ID3v1Info extends AudioInfo {
    /* JADX WARN: Removed duplicated region for block: B:13:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isID3v1StartPosition(java.io.InputStream r2) throws java.io.IOException {
        /*
            r0 = 3
            r2.mark(r0)
            int r0 = r2.read()     // Catch: java.lang.Throwable -> L1e
            r1 = 84
            if (r0 != r1) goto L20
            int r0 = r2.read()     // Catch: java.lang.Throwable -> L1e
            r1 = 65
            if (r0 != r1) goto L20
            int r0 = r2.read()     // Catch: java.lang.Throwable -> L1e
            r1 = 71
            if (r0 != r1) goto L20
            r0 = 1
            goto L21
        L1e:
            r0 = move-exception
            goto L25
        L20:
            r0 = 0
        L21:
            r2.reset()
            return r0
        L25:
            r2.reset()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.audioinfo.mp3.ID3v1Info.isID3v1StartPosition(java.io.InputStream):boolean");
    }

    public ID3v1Info(InputStream inputStream) throws IOException {
        byte b;
        if (isID3v1StartPosition(inputStream)) {
            this.brand = "ID3";
            this.version = "1.0";
            byte[] bytes = readBytes(inputStream, 128);
            this.title = extractString(bytes, 3, 30);
            this.artist = extractString(bytes, 33, 30);
            this.album = extractString(bytes, 63, 30);
            try {
                this.year = Short.parseShort(extractString(bytes, 93, 4));
            } catch (NumberFormatException unused) {
                this.year = (short) 0;
            }
            this.comment = extractString(bytes, 97, 30);
            ID3v1Genre genre = ID3v1Genre.getGenre(bytes[127]);
            if (genre != null) {
                this.genre = genre.getDescription();
            }
            if (bytes[125] != 0 || (b = bytes[126]) == 0) {
                return;
            }
            this.version = "1.1";
            this.track = (short) (b & 255);
        }
    }

    byte[] readBytes(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 <= 0) {
                throw new EOFException();
            }
            i2 += i3;
        }
        return bArr;
    }

    String extractString(byte[] bArr, int i, int i2) {
        try {
            String str = new String(bArr, i, i2, "ISO-8859-1");
            int iIndexOf = str.indexOf(0);
            return iIndexOf < 0 ? str : str.substring(0, iIndexOf);
        } catch (Exception unused) {
            return "";
        }
    }
}
