package org.mvel2.p020sh.command.file;

import org.mvel2.p020sh.Command;
import org.mvel2.p020sh.ShellSession;

/* loaded from: classes4.dex */
public class PrintWorkingDirectory implements Command {
    @Override // org.mvel2.p020sh.Command
    public Object execute(ShellSession shellSession, String[] strArr) {
        System.out.println(shellSession.getEnv().get("$CWD"));
        return null;
    }

    @Override // org.mvel2.p020sh.Command
    public String getDescription() {
        return "prints the current working directory";
    }

    @Override // org.mvel2.p020sh.Command
    public String getHelp() {
        return "no help yet.";
    }
}
