package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.HubitatAppScript
import biocomp.hubitatCiTest.emulation.AppPreferences
import biocomp.hubitatCiTest.emulation.AppDynamicPage
import biocomp.hubitatCiTest.emulation.AppPreferencesSource
import biocomp.hubitatCiTest.util.Utility
import com.sun.istack.internal.NotNull
import groovy.transform.TypeChecked

@TypeChecked
class AppPreferencesReader implements AppPreferencesSource{
    AppPreferencesReader(@NotNull HubitatAppScript parentScript)
    {
        this.parentScript = parentScript
    }

    private Preferences preferences_ = null

    Preferences getProducedPreferences() { return preferences_ }

    @Override
    Map dynamicPage(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY,
            value=AppDynamicPage) Closure makeContents) {
        assert preferences_ // preferences() should have already been called and thus created
        preferences_.addDynamicPage(options, makeContents)
    }

    @Override
    def preferences(@DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppPreferences) Closure makeContents) {
        preferences(null, makeContents)
    }

    @Override
    Preferences preferences(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppPreferences) Closure makeContents)
    {
        preferences_ = Utility.runClosureAndValidate(new Preferences(parentScript, options), makeContents)
    }

    final @NotNull HubitatAppScript parentScript;
}