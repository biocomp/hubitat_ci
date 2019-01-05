package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.HubitatAppScript

class AppDefinitionValidator extends HubitatAppScript {
    @Override
    def definition(Map definitions)
    {
        assert definitions : "Map passed into definition() can't be null"

        def assertPropertyIsSet = { String name, boolean notEvenEmpty = false ->
            assert definitions."${name}" != null: "definition() call did not provide mandatory property '${name}'. "
            if (notEvenEmpty) {
                assert definitions."${name}": "mandatory property '${name}' can't empty in definition() call"
            }
        }

        // Checking mandatory properties
        assert definitions: "definitions should be provided"
        assertPropertyIsSet "name", true
        assertPropertyIsSet "namespace"
        assertPropertyIsSet "author"
        assertPropertyIsSet "description", true
        assertPropertyIsSet "iconUrl"
        assertPropertyIsSet "iconX2Url"
        assertPropertyIsSet "iconX3Url"
    }
}