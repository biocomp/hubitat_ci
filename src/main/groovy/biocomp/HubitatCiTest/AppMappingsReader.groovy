package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.appApi.Mappings
import groovy.transform.TypeChecked

@TypeChecked
class AppMappingsReader implements AppExecutor {
    AppMappingsReader(AppExecutor delegate, HubitatAppScript script, EnumSet<ValidationFlags> validationFlags)
    {
        this.delegate = delegate
        this.validationFlags = validationFlags
        this.script = script
        this.derivedClass = MappingPath.constructDerivedClass(this.class.classLoader, script.metaClass)
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

        if (!validationFlags.contains(!ValidationFlags.DontValidateMappings)) {
            assert !mappings.containsKey(relativePath) : "path() must define unique paths, but '${relativePath}' was added twice."
        }

        mappings.put(relativePath, new MappingPath(script, relativePath, contents, validationFlags, derivedClass));
    }

    final Map<String, MappingPath> mappings = [:]

    final private EnumSet<ValidationFlags> validationFlags

    final HubitatAppScript script
    final Class derivedClass

    @Delegate
    final private AppExecutor delegate
}
