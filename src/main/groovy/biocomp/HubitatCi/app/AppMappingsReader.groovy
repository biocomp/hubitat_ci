package biocomp.hubitatCi.app

import biocomp.hubitatCi.api.appApi.AppExecutor
import biocomp.hubitatCi.api.appApi.Mappings
import biocomp.hubitatCi.util.MappingPath
import biocomp.hubitatCi.validation.Flags
import groovy.transform.TypeChecked

@TypeChecked
class AppMappingsReader implements AppExecutor {
    AppMappingsReader(AppExecutor delegate, HubitatAppScript script, AppValidator validator)
    {
        this.delegate = delegate
        this.validator = validator
        this.script = script
        this.derivedScriptFactory = MappingPath.makeScriptWithInjectedPropsFactory()
    }

    @Override
    def mappings(@DelegatesTo(Mappings) Closure makeContents)
    {
        assert makeContents : "mappings() can't be called with null closure"

        currentMappings = mappings
        try {
            makeContents()
        }
        finally {
            currentMappings = null
        }

        // Store this for future manual calls by user,
        // but don't touch initially captured mappings during that call.
        makeContentsClosure = {
            def params, def request ->

                currentMappings = [:]

                try
                {
                    def scriptWithInjectedProps = derivedScriptFactory(script, params, request)
                    def makeContentsWithInjectedProps = makeContents.rehydrate(scriptWithInjectedProps, scriptWithInjectedProps, scriptWithInjectedProps)

                    makeContentsWithInjectedProps()
                }
                finally
                {
                    currentMappings = null
                }
        }
    }

    @Override
    def path(String relativePath, Closure makeContents)
    {
        Map contents = makeContents() as Map

        if (!validator.hasFlag(Flags.DontValidateMappings)) {
            assert !currentMappings.containsKey(relativePath) : "path() must define unique paths, but '${relativePath}' was added twice."
        }

        currentMappings.put(relativePath, new MappingPath(script, relativePath, contents, validator, derivedScriptFactory));
    }

    Closure getMappingClosure()
    {
        return makeContentsClosure
    }


    private Map<String, MappingPath> currentMappings

    Closure makeContentsClosure = null

    final Map<String, MappingPath> mappings = [:]

    final private AppValidator validator

    final HubitatAppScript script
    final Closure<HubitatAppScript> derivedScriptFactory

    @Delegate
    final private AppExecutor delegate
}
