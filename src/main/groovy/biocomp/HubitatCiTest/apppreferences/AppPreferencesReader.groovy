package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.HubitatAppScript
import biocomp.hubitatCiTest.emulation.AppExecutorApi
import biocomp.hubitatCiTest.emulation.AppPreferences
import biocomp.hubitatCiTest.emulation.AppDynamicPage
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.TypeChecked

@TypeChecked
class AppPreferencesReader implements AppExecutorApi{
    AppPreferencesReader(HubitatAppScript parentScript, AppExecutorApi delegate)
    {
        this.parentScript = parentScript
        this.delegate = delegate
    }

    private Preferences preferences_ = null

    Preferences getProducedPreferences() { return preferences_ }

    @Override
    Map dynamicPage(Map options, @DelegatesTo(AppDynamicPage) Closure makeContents) {
        assert preferences_ // preferences() should have already been called and thus created
        preferences_.addDynamicPage(options, makeContents)
        delegate.dynamicPage(options, makeContents)
    }

    private void readPreferencesImpl(Map options, @DelegatesTo(AppPreferences) Closure makeContents)
    {
        preferences_ = Utility.runClosureAndValidate(new Preferences(parentScript, null), makeContents)
        preferences_.registerDynamicPages()
    }

    @Override
    def preferences(@DelegatesTo(AppPreferences) Closure makeContents) {
        readPreferencesImpl(null, makeContents)
        delegate.preferences(makeContents)
    }

    @Override
    def preferences(Map options, @DelegatesTo(AppPreferences) Closure makeContents)
    {
        readPreferencesImpl(options, makeContents)
        delegate.preferences(makeContents)
    }

    final List<String> dynamicPageGenerationMethods = []
    final HubitatAppScript parentScript;

    @Delegate
    final private AppExecutorApi delegate;
}