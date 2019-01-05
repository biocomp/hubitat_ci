package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.DeviceWrapper
import biocomp.hubitatCiTest.util.CapturingLog
import biocomp.hubitatCiTest.util.Log
import spock.lang.Specification

class AppTemplateScriptTest extends
        Specification
{
    def sandbox = new HubitatAppSandbox(new File("Scripts/New App Template.groovy"))

    def "Script sets mandatory properties"() {
        expect:
            sandbox.mandatoryConfigIsSet()
    }

    def "Script's preferences are correct"() {
        expect:
            sandbox.preferencesAreCorrect()
    }

    def "Installation succeeds and logs stuff"() {
        given:
            def api = Mock(emulation.AppExecutorApi)
            def log = Mock(Log)
            def script = sandbox.setupScript(api)
            script.metaClass.ventDevices = ["S1", "S2"]
            script.metaClass.numberOption = 123

        when:
            script.installed()

        then:
            _ * api.log >> log
            1 * log.debug("initialize")
            1 * log.debug("ventDevices: " + ["S1", "S2"])
            1 * log.debug("numberOption: 123")
            1 * api.unschedule()
            1 * api.unschedule()
    }

    def "Update initializes again"() {
        given:
            def api = Mock(emulation.AppExecutorApi)
            def log = Mock(Log)
            def script = sandbox.setupScript(api)
            script.metaClass.ventDevices = ["S1", "S2"]
            script.metaClass.numberOption = 123

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
            def api = Mock(emulation.AppExecutorApi)
            def log = Mock(Log)
            def script = sandbox.setupScript(api)

        when:
            script.uninstalled()

        then:
            _ * api.log >> log
            1 * log.debug("uninstalled")
    }
}

class ThermostatDimerSyncHelperTest extends
        Specification
{
    def sandbox = new HubitatAppSandbox(new File("Scripts/ThermostatDimmerSyncHelper.groovy"))

    def "Script sets mandatory properties"() {
        expect:
            sandbox.mandatoryConfigIsSet()
    }

    def "Script's properties are correct"() {
        expect:
            sandbox.preferencesAreCorrect()
    }
}