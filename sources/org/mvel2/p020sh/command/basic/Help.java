package org.mvel2.p020sh.command.basic;

import org.mvel2.p020sh.Command;
import org.mvel2.p020sh.ShellSession;
import org.mvel2.p020sh.text.TextUtil;

/* loaded from: classes4.dex */
public class Help implements Command {
    @Override // org.mvel2.p020sh.Command
    public Object execute(ShellSession shellSession, String[] strArr) {
        for (String str : shellSession.getCommands().keySet()) {
            System.out.println(str + TextUtil.pad(str.length(), 25) + "- " + shellSession.getCommands().get(str).getDescription());
        }
        return null;
    }

    @Override // org.mvel2.p020sh.Command
    public String getDescription() {
        return "displays help for available shell commands";
    }

    @Override // org.mvel2.p020sh.Command
    public String getHelp() {
        return "No help yet";
    }
}
