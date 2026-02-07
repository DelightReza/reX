package org.mvel2.templates.util.p021io;

import java.io.IOException;
import java.io.OutputStream;
import org.mvel2.templates.util.TemplateOutputStream;

/* loaded from: classes4.dex */
public class StandardOutputStream implements TemplateOutputStream {
    private OutputStream outputStream;

    public String toString() {
        return null;
    }

    public StandardOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override // org.mvel2.templates.util.TemplateOutputStream
    public TemplateOutputStream append(CharSequence charSequence) throws IOException {
        for (int i = 0; i < charSequence.length(); i++) {
            try {
                this.outputStream.write(charSequence.charAt(i));
            } catch (IOException e) {
                throw new RuntimeException("failed to write to stream", e);
            }
        }
        return this;
    }

    @Override // org.mvel2.templates.util.TemplateOutputStream
    public TemplateOutputStream append(char[] cArr) throws IOException {
        try {
            for (char c : cArr) {
                this.outputStream.write(c);
            }
            return this;
        } catch (IOException e) {
            throw new RuntimeException("failed to write to stream", e);
        }
    }
}
