package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.Validator
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.appApi.Mappings
import groovy.transform.TypeChecked

@TypeChecked
class AppMappingsReader implements AppExecutor {
    AppMappingsReader(AppExecutor delegate, HubitatAppScript script, Validator validator)
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
        makeContents()
    }

    @Override
    def path(String relativePath, Closure makeContents)
    {
        Map contents = makeContents() as Map

        if (!validator.hasFlag(Flags.DontValidateMappings)) {
            assert !mappings.containsKey(relativePath) : "path() must define unique paths, but '${relativePath}' was added twice."
        }

        mappings.put(relativePath, new MappingPath(script, relativePath, contents, validator, derivedScriptFactory));
    }

    final Map<String, MappingPath> mappings = [:]

    final private Validator validator

    final HubitatAppScript script
    final Closure<HubitatAppScript> derivedScriptFactory

    @Delegate
    final private AppExecutor delegate
}
