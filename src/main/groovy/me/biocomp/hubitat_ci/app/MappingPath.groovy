package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.validation.Flags
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

/**
 * Helper class to handle url mappings in App.
 * It constructs and injects new script class into existing script's methods
 * to enable extra hidden parameters like 'request' and 'params'.
 */
@TypeChecked
@PackageScope
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
                scriptWithInjectedProps."${functionName}"()
        }
    }

    final String path
    final Map actionNames
    final Map<String, Closure> actions = [:]
}
