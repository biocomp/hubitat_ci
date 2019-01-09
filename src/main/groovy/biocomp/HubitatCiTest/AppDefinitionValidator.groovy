package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import groovy.transform.TypeChecked

@TypeChecked
class AppDefinitionValidator implements AppExecutor {
    AppDefinitionValidator(AppExecutor delegate)
    {
       this.delegate = delegate
    }

    @Override
    def definition(Map definitionsMap, Closure makeContents)
    {
        assert definitionsMap : "Map passed into definition() can't be null"

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

        return delegate.definition(definitionsMap, makeContents)
    }

    @Delegate
    final private AppExecutor delegate
}