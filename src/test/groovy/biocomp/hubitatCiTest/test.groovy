package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.commonApi.Log
import spock.lang.Specification

class AppTemplateScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/New App Template.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.setupAndValidate()
    }

    def "Installation succeeds and logs stuff"() {
        given:
            def log = Mock(Log)
            def api = Mock(AppExecutor)
            def script = sandbox.setupAndValidate(api)
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
            def script = sandbox.setupAndValidate(api)
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
            def script = sandbox.setupAndValidate(api)

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
            sandbox.setupAndValidate()
    }
}