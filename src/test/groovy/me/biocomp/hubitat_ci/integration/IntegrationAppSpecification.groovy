package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.app.HubitatAppScript
import me.biocomp.hubitat_ci.util.integration.IntegrationAppExecutor
import me.biocomp.hubitat_ci.util.integration.IntegrationScheduler
import me.biocomp.hubitat_ci.util.integration.TimeKeeper

import spock.lang.Specification

abstract class IntegrationAppSpecification extends Specification {
    private HubitatAppSandbox sandbox
    protected HubitatAppScript appScript
    private scheduler = new IntegrationScheduler()

    protected installedApp = Mock(InstalledAppWrapper)

    protected log = Mock(Log)

    def appState = [:]
    def appAtomicState = [:]

    protected appExecutor = Spy(IntegrationAppExecutor, constructorArgs: [scheduler: scheduler]) {
        _*getLog() >> log
        _*getApp() >> installedApp
        _*getState() >> appState
        _*getAtomicState() >> appAtomicState
    }

    /**
     * Set the app settings that will be used to run the app script, and initialize all
     * the necessary objects.
     * This method must be called in the overridden setup() method of the subclass specifications.
     */
    protected void initializeEnvironment(String appScriptFilename, Map appSettings = [:]) {
        sandbox = new HubitatAppSandbox(new File(appScriptFilename))
        appScript = sandbox.run(api: appExecutor, userSettingValues: appSettings)
        appExecutor.setSubscribingScript(appScript)
    }

    /**
     * Spock will run this base setup BEFORE running the overridden setup() in the subclass specifications.
     */
    def setup() {
        TimeKeeper.removeAllListeners()
    }

    /**
     * Spock will run this base cleanup AFTER running the overridden cleanup() in the subclass specifications.
     */
    def cleanup() {
        TimeKeeper.removeAllListeners()
    }

}