package biocomp.hubitatCi


import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator
import biocomp.hubitatCi.emulation.appApi.AppExecutor
import biocomp.hubitatCi.validation.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class AppDefinitionReader implements
        AppExecutor
{
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("name", required(), mustNotBeEmpty())
        stringParameter("namespace", required(), mustNotBeEmpty())
        stringParameter("author", required(), canBeEmpty())
        stringParameter("description", required(), mustNotBeEmpty())
        stringParameter("iconUrl", required(), canBeEmpty())
        stringParameter("iconX2Url", required(), canBeEmpty())
        stringParameter("iconX3Url", required(), canBeEmpty())
        stringParameter("category", notRequired(), mustNotBeEmpty())
        boolParameter("singleInstance", notRequired())
        boolParameter("oauth", notRequired())
    }

    AppDefinitionReader(AppExecutor delegate, AppValidator validator) {
        this.delegate = delegate
        this.validator = validator
    }

    @Override
    def definition(Map definitionsMap, Closure makeContents) {
        assert makeContents == null: "Our test framework doesn't support closure version of definition() call - it's not public in the API and not documented"
        verifyDefinitionMap(definitionsMap)

        definitions = definitionsMap as Map<String, Object>
    }

    @Override
    def definition(Map definitionsMap) {
        verifyDefinitionMap(definitionsMap)

        definitions = definitionsMap as Map<String, Object>
    }

    private void verifyDefinitionMap(Map definitionsMap) {
        if (!validator.hasFlag(Flags.DontValidateDefinition)) {
            assert definitionsMap: "Map passed into definition() can't be null"

            def assertPropertyIsSet = { String name, boolean notEvenEmpty ->
                assert definitionsMap["${name}"] != null: "definition() call did not provide mandatory property '${name}'. "
                if (notEvenEmpty) {
                    assert definitionsMap["${name}"]: "mandatory property '${name}' can't empty in definition() call"
                }
            }

            // Checking mandatory properties
            assert definitionsMap: "definitions should be provided"
            paramValidator.validate("definition(): ", definitionsMap, validator)
        }
    }

    Map<String, Object> getDefinitions()
    {
        if (!validator.hasFlag(Flags.DontValidateDefinition)) {
            assert definitions: "definition() method was never called or failed. Either way, definition list is empty."
        }
        return definitions
    }

    @Delegate
    final private AppExecutor delegate
    final private AppValidator validator

    private Map<String, Object> definitions
}