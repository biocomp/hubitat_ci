package biocomp.hubitatCi.util

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
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

@TypeChecked
class AddValidationAfterEachMethodCompilationCustomizer extends CompilationCustomizer{
    /*
    def addMethods = myClass.getMethods("add")
        for(m in addMethods){
            def code = m.getCode().statements

            //statement
            //AstBuilder abc = new AstBuilder()
            Statement s1 = new ExpressionStatement(
                    new BinaryExpression(
                            new VariableExpression('timeOfInstantiation'),
                            Token.newSymbol(org.codehaus.groovy.syntax.Types.EQUAL,0,0),
                            new MethodCallExpression(
                                    new ClassExpression(new ClassNode(java.lang.System)),
                                    'currentTimeMillis',
                                    ArgumentListExpression.EMPTY_ARGUMENTS
                            )
                    )

            )
//            List<ASTNode> statement1 = abc.buildFromString('timeOfInstantiation = System.currentTimeMillis()')
//            List<ASTNode> statement1 = abc.buildFromCode {
//                timeOfInstantiation = System.currentTimeMillis()
//                for(c in code){
//                    c.expression
//                }
//            }

            code.add(0,s1)
            //m.setCode(statement1[0])
        }
     */

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
                if (it.name != "hubitatciValidateAfterMethodCall") {

                    def original = it
//                    if (it.code instanceof BlockStatement) {
//                        ((BlockStatement) it.code).statements.add(callValidator(it.name))
//                    } else {
//                        def newStatement = new BlockStatement()
//                        newStatement.statements.add(it.code)
//                        newStatement.statements.add(callValidator(it.name))
//                        it.code = newStatement
//                    }

                    def methodArgsExpression = new ArgumentListExpression();
                    original.parameters.each {
                        methodArgsExpression.addExpression(new VariableExpression(original.name))
                    }

                    originalMethods.add(new MethodNode(
                            original.name + "_original",
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
                new ConstantExpression(original.name + "_original"),
                methodArgsExpression)
    }

    private static ExpressionStatement callOriginalAndCaptureIntoVariable(
            MethodNode original, ArgumentListExpression methodArgsExpression)
    {
        new ExpressionStatement(new DeclarationExpression(new VariableExpression("result"),
                Token.newSymbol(Types.EQUAL, 0, 0),
                callOriginal(original, methodArgsExpression)))
    }

    private static Statement returnResult()
    {
        return new ReturnStatement(new VariableExpression("result"))
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
