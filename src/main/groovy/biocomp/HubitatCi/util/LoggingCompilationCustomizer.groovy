package me.biocomp.hubitat_ci.util

import groovy.inspect.swingui.AstNodeToScriptVisitor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer

/**
 * Customizer is used for AST debugging - it will dump source code from AST.
 * Just add it after other customizers.
 */
class LoggingCompilationCustomizer extends CompilationCustomizer
{
    final PrintStream out

    LoggingCompilationCustomizer(CompilePhase compilePhase = CompilePhase.INSTRUCTION_SELECTION, PrintStream out = System.out)
    {
        super(compilePhase)
        this.out = out
    }

    void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException
    {
        StringWriter writer = new StringWriter()
        new AstNodeToScriptVisitor(writer).visitClass(classNode)
        out.println writer
    }
}