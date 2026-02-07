package org.mvel2.p020sh.command.basic;

import java.util.HashMap;
import java.util.Map;
import org.mvel2.p020sh.Command;
import org.mvel2.p020sh.CommandSet;

/* loaded from: classes4.dex */
public class BasicCommandSet implements CommandSet {
    @Override // org.mvel2.p020sh.CommandSet
    public Map<String, Command> load() {
        HashMap map = new HashMap();
        map.put("set", new Set());
        map.put("push", new PushContext());
        map.put("help", new Help());
        map.put("showvars", new ShowVars());
        map.put("inspect", new ObjectInspector());
        map.put("exit", new Exit());
        return map;
    }
}
