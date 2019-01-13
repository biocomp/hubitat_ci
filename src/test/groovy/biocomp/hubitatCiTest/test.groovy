package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.commonApi.Log
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
            def api = Mock(AppExecutor)
            def script = sandbox.run(api)
            script.getMetaClass().ventDevices = ["S1", "S2"]
            script.getMetaClass().numberOption = 123

        when:
            script.installed()

        then:
            _ * api.getLog() >> log
            1 * log.debug("initialize")
            1 * log.debug("ventDevices: " + ["S1", "S2"])
            1 * log.debug("numberOption: 123")
            1 * api.unschedule()
    }

    def "Update initializes again"() {
        given:
            def log = Mock(Log)
            def api = Mock(AppExecutor)
            def script = sandbox.run(api)
            script.getMetaClass().ventDevices = ["S1", "S2"]
            script.getMetaClass().numberOption = 123

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
            def script = sandbox.run(api)

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
    def sandbox = new HubitatAppSandbox(new File("Scripts/Lennox-iComfort-connect.groovy"))

    def "Basic validation"() {
        setup:
            def log = Mock(Log)
            AppExecutor api = Mock{
                _ * getLog() >> log
            }
            def script = sandbox.run(
                api,
                [:],
                EnumSet.of(ValidationFlags.DontRunScript, ValidationFlags.AllowWritingToSettings, ValidationFlags.AllowReadingNonInputSettings),
                { script ->
                    script.getMetaClass().loginCheck = { -> 1 }
                    script.getMetaClass().getThermostatList = { -> ["a"] }
                })

        expect:
            script.run()
    }

    @Unroll
    def "Uninstall button on 'prefLogIn' page is only shown when username and password are not null"(
            String userName, String password, boolean uninstalledShown)
    {
        setup:
            def log = Mock(Log)
            AppExecutor api = Mock {
                _ * getLog() >> log
            }

            def script = sandbox.run(
                api,
                [username: userName, password: password],
                EnumSet.of(ValidationFlags.AllowWritingToSettings, ValidationFlags.AllowReadingNonInputSettings),
                { script ->
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