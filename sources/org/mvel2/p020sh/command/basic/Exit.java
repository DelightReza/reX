package org.mvel2.p020sh.command.basic;

import org.mvel2.p020sh.Command;
import org.mvel2.p020sh.ShellSession;

/* loaded from: classes4.dex */
public class Exit implements Command {
    @Override // org.mvel2.p020sh.Command
    public Object execute(ShellSession shellSession, String[] strArr) {
        System.exit(0);
        return null;
    }

    @Override // org.mvel2.p020sh.Command
    public String getDescription() {
        return "exits the command shell";
    }

    @Override // org.mvel2.p020sh.Command
    public String getHelp() {
        return "No help yet.";
    }
}
