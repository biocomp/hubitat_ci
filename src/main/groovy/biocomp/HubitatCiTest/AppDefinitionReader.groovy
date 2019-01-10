package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import groovy.transform.TypeChecked

@TypeChecked
class AppDefinitionReader implements
        AppExecutor
{
    AppDefinitionReader(AppExecutor delegate) {
        this.delegate = delegate
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
        assert definitionsMap: "Map passed into definition() can't be null"

        def assertPropertyIsSet = { String name, boolean notEvenEmpty ->
            assert definitionsMap["${name}"] != null: "definition() call did not provide mandatory property '${name}'. "
            if (notEvenEmpty) {
                assert definitionsMap["${name}"]: "mandatory property '${name}' can't empty in definition() call"
            }
        }

        // Checking mandatory properties
        assert definitionsMap: "definitions should be provided"
        assertPropertyIsSet "name", true
        assertPropertyIsSet "namespace", false
        assertPropertyIsSet "author", false
        assertPropertyIsSet "description", true
        assertPropertyIsSet "iconUrl", false
        assertPropertyIsSet "iconX2Url", false
        assertPropertyIsSet "iconX3Url", false
    }

    Map<String, Object> getDefinitions()
    {
        assert definitions : "definitions() method was never called or failed. Either way, you can't get the definitions."

        return definitions
    }

    @Delegate
    final private AppExecutor delegate

    private Map<String, Object> definitions
}