package org.mvel2;

import java.util.Map;
import org.mvel2.compiler.AbstractParser;
import org.mvel2.util.ParseTools;

/* loaded from: classes4.dex */
public class MacroProcessor extends AbstractParser implements PreProcessor {
    private Map<String, Macro> macros;

    public MacroProcessor() {
    }

    public MacroProcessor(Map<String, Macro> map) {
        this.macros = map;
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x010e  */
    @Override // org.mvel2.PreProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public char[] parse(char[] r10) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.MacroProcessor.parse(char[]):char[]");
    }

    @Override // org.mvel2.PreProcessor
    public String parse(String str) {
        return new String(parse(str.toCharArray()));
    }

    public Map<String, Macro> getMacros() {
        return this.macros;
    }

    public void setMacros(Map<String, Macro> map) {
        this.macros = map;
    }

    public void captureToWhitespace() {
        while (true) {
            int i = this.cursor;
            if (i >= this.length || ParseTools.isWhitespace(this.expr[i])) {
                return;
            } else {
                this.cursor++;
            }
        }
    }
}
