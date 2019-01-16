package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.App
import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.commonApi.Log
import groovy.transform.NotYetImplemented
import org.junit.Assert
import spock.lang.Specification
import spock.lang.Unroll

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
}

//class WeatherForecastScriptTest extends
//        Specification
//{
//    def sandbox = new HubitatAppSandbox(new File("Scripts/Weather-Display With External Forecast.groovy"))
//
//    def "Basic validation"() {
//        expect:
//            sandbox.run()
//    }
//}

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
                validationFlags: [ValidationFlags.DontRunScript, ValidationFlags.AllowWritingToSettings, ValidationFlags.AllowReadingNonInputSettings, ValidationFlags.AllowTitleInPageCallingMethods],
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
                validationFlags: [ValidationFlags.AllowWritingToSettings, ValidationFlags.AllowReadingNonInputSettings, ValidationFlags.AllowTitleInPageCallingMethods],
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
        setup
            def appState = [:]
            AppExecutor api = Mock{
                _ * getState() >> appState
            }

        expect:
            sandbox.run(api: api, validationFlags: [ValidationFlags.AllowTitleInPageCallingMethods])
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
                validationFlags: [ValidationFlags.AllowTitleInPageCallingMethods],
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
            sandbox.run(api: api, validationFlags: [ValidationFlags.AllowEmptyOptionValueStrings])
    }
}