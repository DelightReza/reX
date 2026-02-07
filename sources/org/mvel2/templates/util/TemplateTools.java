package org.mvel2.templates.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.res.TerminalNode;

/* loaded from: classes4.dex */
public class TemplateTools {
    public static Node getLastNode(Node node) {
        while (!(node.getNext() instanceof TerminalNode)) {
            node = node.getNext();
        }
        return node;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int captureToEOS(char[] r3, int r4) {
        /*
            int r0 = r3.length
        L1:
            if (r4 == r0) goto L21
            char r1 = r3[r4]
            r2 = 40
            if (r1 == r2) goto L1a
            r2 = 59
            if (r1 == r2) goto L21
            r2 = 91
            if (r1 == r2) goto L1a
            r2 = 123(0x7b, float:1.72E-43)
            if (r1 == r2) goto L1a
            r2 = 125(0x7d, float:1.75E-43)
            if (r1 == r2) goto L21
            goto L1e
        L1a:
            int r4 = org.mvel2.util.ParseTools.balancedCapture(r3, r4, r1)
        L1e:
            int r4 = r4 + 1
            goto L1
        L21:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.templates.util.TemplateTools.captureToEOS(char[], int):int");
    }

    public static String readInFile(String str) {
        return readInFile(new File(str));
    }

    public static String readInFile(File file) throws IOException {
        try {
            FileChannel channel = new FileInputStream(file).getChannel();
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(10);
            StringBuilder sb = new StringBuilder();
            while (true) {
                byteBufferAllocateDirect.rewind();
                int i = channel.read(byteBufferAllocateDirect);
                if (i != -1) {
                    byteBufferAllocateDirect.rewind();
                    while (i != 0) {
                        sb.append((char) byteBufferAllocateDirect.get());
                        i--;
                    }
                } else {
                    channel.close();
                    return sb.toString();
                }
            }
        } catch (FileNotFoundException unused) {
            throw new TemplateError("cannot include template '" + file.getName() + "': file not found.");
        } catch (IOException e) {
            throw new TemplateError("unknown I/O exception while including '" + file.getName() + "' (stacktrace nested)", e);
        }
    }

    public static String readStream(InputStream inputStream) throws IOException {
        try {
            byte[] bArr = new byte[10];
            StringBuilder sb = new StringBuilder();
            while (true) {
                int i = inputStream.read(bArr);
                if (i == -1) {
                    return sb.toString();
                }
                for (int i2 = 0; i2 < i; i2++) {
                    sb.append((char) bArr[i2]);
                }
            }
        } catch (IOException e) {
            throw new TemplateError("unknown I/O exception while including (stacktrace nested)", e);
        } catch (NullPointerException e2) {
            if (inputStream == null) {
                throw new TemplateError("null input stream", e2);
            }
            throw e2;
        }
    }
}
