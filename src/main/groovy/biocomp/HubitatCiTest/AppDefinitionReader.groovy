package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.util.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class AppDefinitionReader implements
        AppExecutor
{
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "name", required: true)

        stringParameter(name: "name", required: true, canBeEmpty: false)
        stringParameter(name: "namespace", required: true, canBeEmpty: false)
        stringParameter(name: "author", required: true, canBeEmpty: true)
        stringParameter(name: "description", required: true, canBeEmpty: false)
        stringParameter(name: "iconUrl", required: true, canBeEmpty: true)
        stringParameter(name: "iconX2Url", required: true, canBeEmpty: true)
        stringParameter(name: "iconX3Url", required: true, canBeEmpty: true)
    }

    AppDefinitionReader(AppExecutor delegate, boolean validate) {
        this.delegate = delegate
        this.validate = validate
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
        if (validate) {
            assert definitionsMap: "Map passed into definition() can't be null"

            def assertPropertyIsSet = { String name, boolean notEvenEmpty ->
                assert definitionsMap["${name}"] != null: "definition() call did not provide mandatory property '${name}'. "
                if (notEvenEmpty) {
                    assert definitionsMap["${name}"]: "mandatory property '${name}' can't empty in definition() call"
                }
            }

            // Checking mandatory properties
            assert definitionsMap: "definitions should be provided"
            paramValidator.validate("definition(): ", definitionsMap, true)
        }
    }

    Map<String, Object> getDefinitions()
    {
        if (validate) {
            assert definitions: "definition() method was never called or failed. Either way, definition list is empty."
        }
        return definitions
    }

    @Delegate
    final private AppExecutor delegate
    final private boolean validate

    private Map<String, Object> definitions
}