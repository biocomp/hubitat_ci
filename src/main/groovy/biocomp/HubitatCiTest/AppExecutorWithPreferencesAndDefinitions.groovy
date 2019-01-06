package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.emulation.AppDynamicPage
import biocomp.hubitatCiTest.emulation.AppExecutorApi
import biocomp.hubitatCiTest.emulation.AppPreferences
import com.sun.istack.internal.NotNull
import groovy.transform.AutoImplement
import groovy.transform.CompileStatic
import groovy.transform.Generated
import groovy.transform.TypeChecked
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter
import org.codehaus.groovy.runtime.callsite.CallSite

@TypeChecked
@AutoImplement
class Aaaa implements AppExecutorApi{}

class Bbbb
{
    @Delegate
    AppExecutorApi api
}


//interface IBB
//{
//    void g()
//}
//
//interface IB extends IBB
//{
//    void e()
//}
//
//interface ICccc extends IA, IB
//{
//    void foo()
//}


interface IA
{
    void f()
}

class AA implements IA
{
    void f() {}
}

@AutoImplement
class Cccc extends AA{
    int method()
    {
        println "method!"
    }

    String name
    boolean val
}


@TypeChecked
@AutoImplement
@CompileStatic
class AppExecutorWithPreferencesAndDefinitions implements AppExecutorApi{
    AppExecutorWithPreferencesAndDefinitions(@NotNull HubitatAppScript parentScript, @NotNull AppExecutorApi implementationOfTheRest)
    {
        this.preferencesReader = new AppPreferencesReader(parentScript)
        this.definitionsReader = new AppDefinitionValidator()
        this.implementationOfTheRest = implementationOfTheRest
    }

    @Override
    Map dynamicPage(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppDynamicPage) Closure makeContents)
    {
        preferencesReader.dynamicPage(options, makeContents)
        return implementationOfTheRest.dynamicPage(options, makeContents)
    }

    @Override
    def preferences(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppPreferences) Closure makeContents) {
        return preferencesReader.preferences(null, makeContents)
        return implementationOfTheRest.preferences(makeContents)
    }

    @Override
    def preferences(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppPreferences) Closure makeContents)
    {
        preferencesReader.preferences(options, makeContents)
        return implementationOfTheRest.preferences(options, makeContents)
    }


    /**
     * The rest of the calls are still going to be provided by user.
     * This way, any generated mock for AppExecutorApi can be used here.
     * Even methods implemented by this class will still call user-provided one
     * after verification
     */
    @Delegate(excludes = ["invokeMethod", "getProperty", "setProperty", "asType", "getMetaClass", "setMetaClass"])
    private final AppExecutorApi implementationOfTheRest

    final AppPreferencesReader preferencesReader
    final AppDefinitionValidator definitionsReader
}
