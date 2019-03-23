package biocomp.hubitatCi


import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator

import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

class AllowsAddingClassesClassLoader extends GroovyClassLoader
{
    AllowsAddingClassesClassLoader(ClassLoader parent) { super(parent) }

    void addClass(Class c)
    {
        setClassCacheEntry(c);
    }
}

@TypeChecked
class MappingPath {
    static final HashSet<String> supportedActions = ["GET", "PUT", "POST", "DELETE"] as HashSet

    /**
     * To be able to call script's mapping callbacks
     * @return
     */
    @TypeChecked(TypeCheckingMode.SKIP)
    static Closure<HubitatAppScript> makeScriptWithInjectedPropsFactory()
    {
        return { HubitatAppScript script, def params, def request ->
            def scriptWithInjectedProps = script.getMetaClass().invokeConstructor()
            scriptWithInjectedProps.metaClass = script.metaClass

            scriptWithInjectedProps.initialize(script)
            scriptWithInjectedProps.installMappingInjectedProps(params, request)
            return scriptWithInjectedProps
        }
    }

    MappingPath(HubitatAppScript script, String path, Map actions, AppValidator validator, Closure<HubitatAppScript> derivedScriptFactory)
    {
        this.actionNames = actions
        this.path = path

        this.actionNames.each
        {
            this.actions[it.key as String] = makeActionHandler(script, it.key as String, it.value as String, validator, derivedScriptFactory)
        }
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private static Closure makeActionHandler(HubitatAppScript script, String actionWord, String functionName, AppValidator validator, Closure<HubitatAppScript> derivedScriptFactory)
    {
        assert supportedActions.contains(actionWord) : "Action '${actionWord}' is not supported. Supported actions are: ${supportedActions}"

        // Now need to run named closure that is adding dynamic pages
        def methodWithNoArgs = script.getMetaClass().pickMethod(functionName, [] as Class[])

        if (!validator.hasFlag(Flags.DontValidateMappings)) {
            assert methodWithNoArgs: "${this}: action '${actionWord}' refers to method '${functionName}' which does not exist (method must have no args)."
        }

        return {
            def params, def request ->
                def scriptWithInjectedProps = derivedScriptFactory(script, params, request)
                println "Calling scriptWithInjectedProps.\"${functionName}\"()"
                println "scriptWithInjectedProps.methods = ${scriptWithInjectedProps.metaClass.methods.collect{it.name}}"
                scriptWithInjectedProps."${functionName}"()
        }
    }

    final String path
    final Map actionNames
    final Map<String, Closure> actions = [:]
}
