package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.emulation.AppDynamicPage
import biocomp.hubitatCiTest.emulation.AppExecutorApi
import biocomp.hubitatCiTest.emulation.AppPreferences
import groovy.transform.TypeChecked
//
//@TypeChecked
//class AppExecutorWithPreferencesAndDefinitions implements AppExecutorApi{
//    AppExecutorWithPreferencesAndDefinitions(HubitatAppScript parentScript, AppExecutorApi delegate)
//    {
//        this.preferencesReader = new AppPreferencesReader(parentScript)
//        this.definitionsReader = new AppDefinitionValidator()
//        this.delegate = delegate
//    }
//
//    @Override
//    Map dynamicPage(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppDynamicPage) Closure makeContents)
//    {
//        preferencesReader.dynamicPage(options, makeContents)
//        return delegate.dynamicPage(options, makeContents)
//    }
//
//    @Override
//    def preferences(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppPreferences) Closure makeContents) {
//        preferencesReader.preferences(null, makeContents)
//        return delegate.preferences(null, makeContents)
//    }
//
//    @Override
//    def preferences(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppPreferences) Closure makeContents)
//    {
//        preferencesReader.preferences(options, makeContents)
//        return delegate.preferences(null, makeContents)
//    }
//
//    final AppPreferencesReader preferencesReader
//    final AppDefinitionValidator definitionsReader
//
//    @Delegate
//    final AppExecutorApi delegate
//}
