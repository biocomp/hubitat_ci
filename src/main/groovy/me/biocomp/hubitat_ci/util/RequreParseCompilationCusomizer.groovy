package me.biocomp.hubitat_ci.util

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer

/**
 * Requires that script has parse() method (for Device implementations).
 */
@TypeChecked
class RequreParseCompilationCusomizer extends CompilationCustomizer{
    RequreParseCompilationCusomizer() {
        super(CompilePhase.CANONICALIZATION);
    }

    @Override
    @CompileStatic
    void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException
    {
        if (classNode.isScript())
        {
            def parseFound = false;
            List<MethodNode> originalMethods = []
            classNode.methods.each {
                // Not modifying validating method, and not touching static methods.
                // Static methods can't call non-static hubitatciValidateAfterMethodCall,
                // and can't touch anything that needs to be validated.
                if (it.name == "parse") {
                    parseFound = true;
//                    def original = it
//
//                    def methodArgsExpression = new ArgumentListExpression();
//                    original.parameters.each {
//                        methodArgsExpression.addExpression(new VariableExpression(((Parameter)it).name))
//                    }
//
//                    originalMethods.add(new MethodNode(
//                            original.name + "_original",
//                            original.modifiers,
//                            original.returnType,
//                            original.parameters,
//                            original.exceptions,
//                            original.code))
//
//                    // Add validating wrapper method
//                    it.code = generateWrapper(original, methodArgsExpression)
                }
            }

            assert parseFound, "Script does not have parse() method required for devices"
        }
    }
}
