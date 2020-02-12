package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.AppValidator
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class AppTemplateScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/New App Template.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run()
    }

    def "Installation succeeds and logs stuff"() {
        given:
            def log = Mock(Log)
            AppExecutor api = Mock{ _ * getLog() >> log }
            def script = sandbox.run(api: api, customizeScriptBeforeRun: { script ->
                script.getMetaClass().ventDevices = ["S1", "S2"]
                script.getMetaClass().numberOption = 123
            })

        when:
            script.installed()

        then:
            1 * log.debug("initialize")
            1 * log.debug("ventDevices: " + ["S1", "S2"])
            1 * log.debug("numberOption: 123")
            1 * api.unschedule()
    }

    def "Update initializes again"() {
        given:
            def log = Mock(Log)
            def api = Mock(AppExecutor)
            def script = sandbox.run(api: api, customizeScriptBeforeRun: { script ->
                script.getMetaClass().ventDevices = ["S1", "S2"]
                script.getMetaClass().numberOption = 123
            })

        when:
            script.updated()

        then:
            _ * api.log >> log
            1 * log.debug("updated")
            1 * log.debug("initialize")
            1 * log.debug("ventDevices: " + ["S1", "S2"])
            1 * log.debug("numberOption: 123")
            1 * api.unschedule()
    }

    def "Uninstallation succeeds"() {
        given:
            def log = Mock(Log)
            def api = Mock(AppExecutor)
            def script = sandbox.run(api: api)

        when:
            script.uninstalled()

        then:
            _ * api.getLog() >> log
            1 * log.debug("uninstalled")
    }
}

class ThermostatDimerSyncHelperTest extends
        Specification
{
    def sandbox = new HubitatAppSandbox(new File("Scripts/ThermostatDimmerSyncHelper.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run()
    }

    def "Installation succeeds"() {
        setup:
            final def log = Mock(Log)
            final AppExecutor api = Mock { _ * getLog() >> log }

            final def thermostat = Mock(DeviceWrapper)
                    {
                        _*getLabel() >> "thermostat"
                        _*getSupportedAttributes() >> [
                                Mock(Attribute){ _*getName() >> "coolingSetpoint" },
                                Mock(Attribute){ _*getName() >> "heatingSetpoint" }
                        ]
                    }

            final def coolingDimmer = Mock(DeviceWrapper)
                    {
                        _*getLabel() >> "coolingDimmer"
                        _*getSupportedAttributes() >> [
                                Mock(Attribute){ _*getName() >> "level" }
                        ]
                    }

            final def heatingDimmer = Mock(DeviceWrapper)
                    {
                        _*getLabel() >> "heatingDimmer"
                        _*getSupportedAttributes() >> [
                                Mock(Attribute){ _*getName() >> "level" }
                        ]
                    }

            final def script = sandbox.run(
                    api: api, userSettingValues: [
                    thermostat: thermostat,
                    coolingDimmer: coolingDimmer,
                    heatingDimmer: heatingDimmer]
            )

        when:
            script.installed()

        then:
            1* log.debug("Initializing")
            1* api.subscribe(thermostat, "coolingSetpoint", _)
            1* api.subscribe(thermostat, "heatingSetpoint", _)
            1* api.subscribe(coolingDimmer, "level", _)
            1* api.subscribe(heatingDimmer, "level", _)
    }
}

class IComfortAppScriptTest extends
        Specification
{
    def sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/Hubitat_iComfort/App/Lennox-iComfort-connect.groovy"))

    def "Basic validation"() {
        setup:
            def log = Mock(Log)
            AppExecutor api = Mock{
                _ * getLog() >> log
            }

        expect:
            sandbox.run(
                api: api,
                validationFlags: [Flags.DontRunScript, Flags.AllowWritingToSettings, Flags.AllowReadingNonInputSettings, Flags.AllowTitleInPageCallingMethods],
                customizeScriptBeforeRun: { script ->
                    script.getMetaClass().loginCheck = { -> 1 }
                    script.getMetaClass().getThermostatList = { -> ["a"] }
                })
    }

    //@Unroll
    def "Uninstall button on 'prefLogIn' page is only shown when username and password are not null"(
            String userName, String password, boolean uninstalledShown)
    {
        setup:
            def log = Mock(Log)
            AppExecutor api = Mock {
                _ * getLog() >> log
            }

            def script = sandbox.run(
                api: api,
                userSettingValues: [username: userName, password: password],
                validationFlags: [Flags.AllowWritingToSettings, Flags.AllowReadingNonInputSettings, Flags.AllowTitleInPageCallingMethods],
                customizeScriptBeforeRun: { script ->
                    script.getMetaClass().loginCheck = { -> 1 }
                    script.getMetaClass().getThermostatList = { -> ["a"] }
                })

        expect:
            script.getProducedPreferences().dynamicPages[0].options.name == 'prefLogIn'
            script.getProducedPreferences().dynamicPages[0].options.uninstall == uninstalledShown

        where:
            userName | password || uninstalledShown
            null     | null     || false
            "u"      | null     || false
            null     | "p"      || false
            "u"      | "p"      || true
            ""       | ""       || true // Even for just empty strings shows the page
    }
}

class KonnectedConnectScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/konnected/hubitat/apps/konnected-connect.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run()
    }
}

// It's a device?
//
//class KonnectedServiceManagerScriptTest extends
//        Specification
//{
//    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/konnected/hubitat/apps/konnected-service-manager.groovy"))
//
//    def "Basic validation"() {
//        expect:
//            sandbox.run()
//    }
//}

class EcoNetThermostatScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/EcoNet/Hubitat/SmartApps/econet-thermostat-app.groovy"))

    def "Basic validation"() {
        setup:
            def appState = [:]
            AppExecutor api = Mock{
                _ * getState() >> appState
            }

        expect:
            sandbox.run(
                api: api,
                validationFlags: [Flags.AllowTitleInPageCallingMethods],
                userSettingValues: [username: "user", password: "pass"],
                customizeScriptBeforeRun: { script ->
                    script.getMetaClass().login = { -> true }
                    script.getMetaClass().gethvaclist = { -> ["waterheater!"] }
                })
    }
}

class EcoNetTanklessAppScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/EcoNet/Hubitat/SmartApps/econet-tankless-app.groovy"))

    def "Basic validation"() {
        setup:
            def appState = [:]
            AppExecutor api = Mock{
                _ * getState() >> appState
            }

        expect:
            sandbox.run(
                api: api,
                validationFlags: [Flags.AllowTitleInPageCallingMethods],
                userSettingValues: [username: "user", password: "pass"],
                customizeScriptBeforeRun: { script ->
                    script.getMetaClass().login = { -> true }
                    script.getMetaClass().getWaterHeaterList = { -> ["waterheater!"] }
                })
    }
}

class HomeRemoteScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/homeremote/hubitatapp"))

    def "Basic validation"() {
        setup:
            Log log = Mock()
            def appState = [:]
            AppExecutor api = Mock{
                _ * getLog() >> log
                _ * getState() >> appState
            }

        expect:
            sandbox.run(api: api, validationFlags: [Flags.AllowEmptyOptionValueStrings])
    }
}

class Tonesto7HomebridgeScriptTest extends Specification
{
    def sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/homebridge-hubitat-tonesto7/smartapps/tonesto7/homebridge-hubitat.src/homebridge-hubitat.groovy"))

    def "Basic validation"()
    {
        setup:
            InstalledAppWrapper app = Mock{
                _ * getName() >> "MyAppName"
            }

            def appState = [isInstalled: true]
            AppExecutor api = Mock{
                //_ * getLog() >> log
                _ * getState() >> appState
                _ * getApp() >> app
            }

        expect:
            sandbox.run(
                    api: api,
                    validator: new AppValidator([
                            Flags.AllowEmptyOptionValueStrings,
                            Flags.AllowNullListOptions,
                            Flags.AllowMissingOAuthPage,
                            Flags.AllowNullEnumInputOptions],
                            [],
                            ["execute"]))
    }
}

class InfluxDbLoggerTest extends Specification {
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("SubmodulesWithScripts/influxdb_logger/influxdb-logger.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run()
    }

    def "Calling major script methods doesn't cause issues"() {
        setup:
            // Create mock log
            def log = Mock(Log)
            def app = Mock(InstalledAppWrapper){
                _*getLabel() >> "My label"
            }

            def state = [:]
            // Make AppExecutor return the mock log
            AppExecutor api = Mock{
                _*getLog() >> log
                _*getState() >> state
                _*getApp() >> app
                _*getLocation() >> Mock(Location)
            }

            // Parse, construct script object, run validations
            def script = sandbox.run(
                    api: api,
                    userSettingValues: [
                            prefSoftPollingInterval: "10",
                            prefDatabaseUser: "MyUserName",
                            prefDatabasePass: "MyPassword"],
                    validationFlags: [Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe])

            script.installed()
            script.updated()

        expect:
            state.headers.Authorization != ""
    }
}