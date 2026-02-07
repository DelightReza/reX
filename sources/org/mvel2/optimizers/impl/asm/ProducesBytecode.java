package org.mvel2.optimizers.impl.asm;

import org.mvel2.asm.MethodVisitor;
import org.mvel2.integration.VariableResolverFactory;

/* loaded from: classes4.dex */
public interface ProducesBytecode {
    void produceBytecodeGet(MethodVisitor methodVisitor, String str, VariableResolverFactory variableResolverFactory);

    void produceBytecodePut(MethodVisitor methodVisitor, String str, VariableResolverFactory variableResolverFactory);
}
