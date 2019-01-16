package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import groovy.transform.TypeChecked

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

    static Class constructDerivedClass(ClassLoader parentClassLoader, MetaClass scriptMetaClass)
    {
        def scriptClassName = scriptMetaClass.theClass.name
        def classBody = """
            class DerivedFrom${scriptClassName} extends ${scriptClassName}
            {
                DerivedFrom${scriptClassName}(def params, def request) 
                { 
                    this.params = params;
                    this.request = request;
                }

                final def params
                final def request
            }"""

        def loader = new AllowsAddingClassesClassLoader(parentClassLoader)
        loader.addClass(scriptMetaClass.theClass)

        return loader.parseClass(classBody)
    }

    MappingPath(HubitatAppScript script, String path, Map actions, EnumSet<ValidationFlags> flags, Class scriptDerivedClass)
    {
        this.actionNames = actions
        this.path = path

        this.actionNames.each
        {
            this.actions[it.key as String] = makeActionHandler(script, it.key as String, it.value as String, flags, scriptDerivedClass)
        }
    }

    //@TypeChecked(TypeCheckingMode.SKIP)
    private static Closure makeActionHandler(HubitatAppScript script, String actionWord, String functionName, EnumSet<ValidationFlags> flags, Class scriptDerivedClass)
    {
        assert supportedActions.contains(actionWord) : "Action '${actionWord}' is not supported. Supported actions are: ${supportedActions}"

        // Now need to run named closure that is adding dynamic pages
        def methodWithNoArgs = script.getMetaClass().pickMethod(functionName, [] as Class[])

        if (!flags.contains(ValidationFlags.DontValidateMappings)) {
            assert methodWithNoArgs: "${this}: action '${actionWord}' refers to method '${functionName}' which does not exist (method must have no args)."
        }

        return {
            def params, def request ->
                def scriptWithInjectedProps = scriptDerivedClass.newInstance(params, request)
                (scriptWithInjectedProps as HubitatAppScript).initialize(script)
                scriptWithInjectedProps.&"${functionName}"()
        }
    }

    final String path
    final Map actionNames
    final Map<String, Closure> actions = [:]
}
