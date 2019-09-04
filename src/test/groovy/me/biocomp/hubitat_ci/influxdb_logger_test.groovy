package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import spock.lang.Specification

class influxdb_logger_test extends Specification {
    def "Basic test"()
    {
        expect:
            new HubitatAppSandbox(new File("SubmodulesWithScripts/SmartThings/smartapps/influxdb-logger/influxdb-logger.groovy")).run()
    }
}
