package biocomp.hubitatCi

import spock.lang.Specification

class WeatherDisplayScriptTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/Devices/Weather-Display With External Forecast.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run()
    }
}

