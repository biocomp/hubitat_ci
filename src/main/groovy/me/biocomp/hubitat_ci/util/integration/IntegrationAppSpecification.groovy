package me.biocomp.hubitat_ci.util.integration

import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.app.HubitatAppScript
import me.biocomp.hubitat_ci.util.integration.IntegrationAppExecutor
import me.biocomp.hubitat_ci.util.integration.IntegrationScheduler
import me.biocomp.hubitat_ci.util.integration.TimeKeeper
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator

import spock.lang.Specification

/**
 * The IntegrationAppSpecification is a Spock spec that sets up a mock Hub
 * environment for running integration tests on Hubitat app scripts.
 * It provisions the necessary objects and mocks to run the app script in a
 * controlled environment, and provides helper methods for setting up the
 * app settings and initializing the app script.
 * It lets the test writer focus on the app script logic, without having to
 * worry about the Hubitat_ci framework.
 * An example of use can be found in DimmerMinimumsIntegrationTest.groovy.
 */
abstract class IntegrationAppSpecification extends Specification {
    private HubitatAppSandbox sandbox
    protected HubitatAppScript appScript
    private scheduler = new IntegrationScheduler()

    protected installedApp = Mock(InstalledAppWrapper)

    protected log = Mock(Log)

    protected appState = [:]
    protected appAtomicState = [:]

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
     * Required options:
     *  - appScriptFilename: the filename of the app script to be tested.
     * Optional options:
     *  - validationFlags: a list of Flags to be used for validating the app script. Default is an empty list.
     *  - userSettingValues: a map of user settings to be used configuring the app. Default is an empty map.
     */
    protected void initializeEnvironment(Map options = [:]) {
        validateAndUpdateOptions(options)

        sandbox = new HubitatAppSandbox(new File(options.appScriptFilename))
        appScript = sandbox.run(api: appExecutor, validationFlags: options.validationFlags, userSettingValues: options.userSettingValues)
        appExecutor.setSubscribingScript(appScript)
    }

    private static void validateAndUpdateOptions(Map options) {
        options.putIfAbsent('validationFlags', [])
        options.putIfAbsent('userSettingValues', [])
        optionsValidator.validate("Validating IntegrationAppSpecification options", options, EnumSet.noneOf(Flags))
    }

    private static final NamedParametersValidator optionsValidator = NamedParametersValidator.make {
        objParameter("appScriptFilename", required(), mustNotBeNull(), { v -> new Tuple2("String", v instanceof String)} )
        objParameter("validationFlags", notRequired(), mustNotBeNull(), { v -> new Tuple2("List<Flags>", v as List<Flags>) })
        objParameter("userSettingValues", notRequired(), mustNotBeNull(), { v -> new Tuple2("Map<String, Object>", v as Map<String, Object>) })
    }

    /**
     * Spock will run this base setup BEFORE running the overridden setup() in the subclass specifications.
     */
    def setup() {
        TimeKeeper.removeAllListeners()     // Ensure a clean slate for each test.
    }

    /**
     * Spock will run this base cleanup AFTER running the overridden cleanup() in the subclass specifications.
     */
    def cleanup() {
        TimeKeeper.removeAllListeners()     // The IntegrationScheduler is a TimeKeeper listener, so we want to unregister it after each test is complete.
    }

}