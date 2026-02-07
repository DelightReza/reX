package org.mvel2.p020sh.command.file;

import java.util.HashMap;
import java.util.Map;
import org.mvel2.p020sh.Command;
import org.mvel2.p020sh.CommandSet;

/* loaded from: classes4.dex */
public class FileCommandSet implements CommandSet {
    @Override // org.mvel2.p020sh.CommandSet
    public Map<String, Command> load() {
        HashMap map = new HashMap();
        map.put("ls", new DirList());
        map.put("cd", new ChangeWorkingDir());
        map.put("pwd", new PrintWorkingDirectory());
        return map;
    }
}
