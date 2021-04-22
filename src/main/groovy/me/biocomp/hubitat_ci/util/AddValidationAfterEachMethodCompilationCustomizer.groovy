package me.biocomp.hubitat_ci.util


import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types

/**
 * This customizer adds a call to hubitatciValidateAfterMethodCall()
 * at the end of each user's method.
 * This is to validate access to properties and settings that that method had performed.
 */
@TypeChecked
class AddValidationAfterEachMethodCompilationCustomizer extends CompilationCustomizer{
    AddValidationAfterEachMethodCompilationCustomizer() {
        super(CompilePhase.CANONICALIZATION);
    }

    @Override
    @CompileStatic
    void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
        if (classNode.isScript())
        {
            List<MethodNode> originalMethods = []
            classNode.methods.each {
                // Not modifying validating method, and not touching static methods.
                // Static methods can't call non-static hubitatciValidateAfterMethodCall,
                // and can't touch anything that needs to be validated.
                if (it.name != "hubitatciValidateAfterMethodCall" && !it.static) {
                    def original = it

                    def methodArgsExpression = new ArgumentListExpression();
                    original.parameters.each {
                        methodArgsExpression.addExpression(new VariableExpression(((Parameter)it).name))
                    }

                    originalMethods.add(new MethodNode(
                            original.name + "_original_overwritten_by_hubitat_ci_ValidatorBase",
                            original.modifiers,
                            original.returnType,
                            original.parameters,
                            original.exceptions,
                            original.code))

                    // Add validating wrapper method
                    it.code = generateWrapper(original, methodArgsExpression)
                }
            }

            classNode.methods.addAll(originalMethods)
        }
    }

    static private BlockStatement generateWrapper(MethodNode original, ArgumentListExpression methodArgsExpression)
    {
        def code = new BlockStatement()
        if (original.returnType != ClassHelper.VOID_TYPE) {
            code.addStatement(callOriginalAndCaptureIntoVariable(original, methodArgsExpression))
            code.addStatement(callValidator(original.name))
            code.addStatement(returnResult())
        }
        else
        {
            // Just call original, then validator
            code.addStatement(new ExpressionStatement(callOriginal(original, methodArgsExpression)))
            code.addStatement(callValidator(original.name))
        }

        return code
    }

    private static Expression callOriginal(MethodNode original, ArgumentListExpression methodArgsExpression)
    {
        new MethodCallExpression(
                new VariableExpression("this"),
                new ConstantExpression(original.name + "_original_overwritten_by_hubitat_ci_ValidatorBase"),
                methodArgsExpression)
    }

    private static ExpressionStatement callOriginalAndCaptureIntoVariable(
            MethodNode original, ArgumentListExpression methodArgsExpression)
    {
        new ExpressionStatement(new DeclarationExpression(new VariableExpression("result_created_by_hubitat_ci_ValidatorBase", original.returnType),
                Token.newSymbol(Types.EQUAL, 0, 0),
                callOriginal(original, methodArgsExpression)))
    }

    private static Statement returnResult()
    {
        return new ReturnStatement(new VariableExpression("result_created_by_hubitat_ci_ValidatorBase"))
    }

    private static Statement callValidator(String methodName) {
        new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression("this"),
                        new ConstantExpression("hubitatciValidateAfterMethodCall"),
                        new ArgumentListExpression(
                                new ConstantExpression(methodName)
                        )
                )
        )
    }
}
