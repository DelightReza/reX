package org.mvel2.compiler;

import java.io.Serializable;

/* loaded from: classes4.dex */
public interface AccessorNode extends Accessor, Serializable {
    AccessorNode getNextNode();

    AccessorNode setNextNode(AccessorNode accessorNode);
}
