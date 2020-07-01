package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeChecked

@TypeChecked
@PackageScope
class AppDefinitionReader implements
        AppExecutor
{
    private static final NamedParametersValidator definitionOptionsValidator = NamedParametersValidator.make {
        stringParameter("name", required(), mustNotBeEmpty())
        stringParameter("namespace", required(), mustNotBeEmpty())
        stringParameter("author", required(), canBeEmpty())
        stringParameter("description", required(), mustNotBeEmpty())
        stringParameter("iconUrl", required(), canBeEmpty())
        stringParameter("iconX2Url", required(), canBeEmpty())
        stringParameter("iconX3Url", required(), canBeEmpty())
        stringParameter("category", notRequired(), mustNotBeEmpty())
        stringParameter("parent", notRequired(), mustNotBeEmpty())
        boolParameter("singleInstance", notRequired())
        boolParameter("oauth", notRequired())
    }

    AppDefinitionReader(AppExecutor delegate, EnumSet<Flags> validationFlags, Map<String, Object> definitions) {
        this.delegate = delegate
        this.validationFlags = validationFlags

        this.definitions = definitions

        assert definitions != null
        assert definitions.isEmpty(): "definitions map passed into AppDefinitionReader must be empty"
    }

    @Override
    def definition(Map definitionsMap, Closure makeContents) {
        assert makeContents == null: "Our test framework doesn't support closure version of definition() call - it's not public in the API and not documented"

        verifyDefinitionMap(definitionsMap)
        storeDefinitions(definitionsMap)
    }

    @Override
    def definition(Map definitionsMap) {
        verifyDefinitionMap(definitionsMap)
        storeDefinitions(definitionsMap)
    }

    @CompileStatic
    private void storeDefinitions(Map definitionsMap)
    {
        definitions.putAll(definitionsMap as Map<String, Object>)
    }

    @CompileStatic
    private void verifyDefinitionMap(Map definitionsMap) {
        if (!validationFlags.contains(Flags.DontValidateDefinition)) {
            assert definitions.isEmpty(): "definition() called more than once"
        }

        if (!validationFlags.contains(Flags.DontValidateDefinition)) {
            assert definitionsMap: "Map passed into definition() can't be null"

            def assertPropertyIsSet = { String name, boolean notEvenEmpty ->
                assert definitionsMap["${name}"] != null: "definition() call did not provide mandatory property '${name}'. "
                if (notEvenEmpty) {
                    assert definitionsMap["${name}"]: "mandatory property '${name}' can't be empty in definition() call"
                }
            }

            // Checking mandatory properties
            definitionOptionsValidator.validate("definition(): ", definitionsMap, validationFlags)
        }
    }

    @CompileStatic
    void validateAfterRun()
    {
        if (!validationFlags.contains(Flags.DontValidateDefinition)) {
            assert definitions.size() != 0: "definition() method was never called or failed. Either way, definition list is empty."
        }
    }

    @Delegate
    final private AppExecutor delegate
    final private EnumSet<Flags> validationFlags

    private Map<String, Object> definitions
}