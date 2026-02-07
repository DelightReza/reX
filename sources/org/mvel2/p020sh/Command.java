package org.mvel2.p020sh;

/* loaded from: classes4.dex */
public interface Command {
    Object execute(ShellSession shellSession, String[] strArr);

    String getDescription();

    String getHelp();
}
