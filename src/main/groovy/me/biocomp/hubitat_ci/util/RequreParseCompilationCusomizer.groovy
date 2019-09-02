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

    @CompileStatic
    private static String printSignature(MethodNode m)
    {
        return "${m.returnType.nameWithoutPackage} parse(${m.parameters.collect{"${it.type.nameWithoutPackage} ${it.name}"}.join(", ")})"
    }

    @Override
    @CompileStatic
    void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException
    {
        if (classNode.isScript())
        {
            def parseFound = false;
            def goodParseFound = false;
            List<String> signatures = []

            classNode.methods.each {
                // Not modifying validating method, and not touching static methods.
                // Static methods can't call non-static hubitatciValidateAfterMethodCall,
                // and can't touch anything that needs to be validated.
                if (it.name == "parse") {
                    parseFound = true;

                    signatures.add(printSignature(it))

                    if (it.parameters.size() == 1
                        && (it.parameters[0].type.name == Object.class.name || it.parameters[0].type.name == String.class.name))
                    {
                        goodParseFound = true
                    }
                }
            }

            assert parseFound, "Script does not have parse() method required for devices"
            assert goodParseFound, "None of parse() method signatures (${signatures}) matches any of expected ones: [Map parse(String), List<Map> parse(String), HubAction parse(String), List<HubAction> parse(String)"
        }
    }
}
