package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.capabilities.Capabilities
import spock.lang.Specification
import groovy.transform.TupleConstructor

@TupleConstructor
class TestCapabilityInfo
{
    String deviceSelectorName
    String driverDefinition
}

class CapabilitiesTest extends Specification
{

    static final List<TestCapabilityInfo> expectedCapabilities = [
            new TestCapabilityInfo("accelerationSensor", "AccelerationSensor"),
            new TestCapabilityInfo("actuator", "Actuator"),
            new TestCapabilityInfo("alarm", "Alarm"),
            new TestCapabilityInfo("audioNotification", "AudioNotification"),
            new TestCapabilityInfo("audioVolume", "AudioVolume"),
            new TestCapabilityInfo("battery", "Battery"),
            new TestCapabilityInfo("beacon", "Beacon"),
            new TestCapabilityInfo("bulb", "Bulb"),
            new TestCapabilityInfo("button", "Button"),
            new TestCapabilityInfo("carbonDioxideMeasurement", "CarbonDioxideMeasurement"),
            new TestCapabilityInfo("carbonMonoxideDetector", "CarbonMonoxideDetector"),
            new TestCapabilityInfo("changeLevel", "ChangeLevel"),
            new TestCapabilityInfo("chime", "Chime"),
            new TestCapabilityInfo("colorControl", "ColorControl"),
            new TestCapabilityInfo("colorMode", "ColorMode"),
            new TestCapabilityInfo("colorTemperature", "ColorTemperature"),
            new TestCapabilityInfo("configuration", "Configuration"),
            new TestCapabilityInfo("consumable", "Consumable"),
            new TestCapabilityInfo("contactSensor", "ContactSensor"),
            new TestCapabilityInfo("doorControl", "DoorControl"),
            new TestCapabilityInfo("doubleTapableButton", "DoubleTapableButton"),
            new TestCapabilityInfo("energyMeter", "EnergyMeter"),
            new TestCapabilityInfo("estimatedTimeOfArrival", "EstimatedTimeOfArrival"),
            new TestCapabilityInfo("fanControl", "FanControl"),
            new TestCapabilityInfo("filterStatus", "FilterStatus"),
            new TestCapabilityInfo("garageDoorControl", "GarageDoorControl"),
            new TestCapabilityInfo("healthCheck", "HealthCheck"),
            new TestCapabilityInfo("holdableButton", "HoldableButton"),
            new TestCapabilityInfo("illuminanceMeasurement", "IlluminanceMeasurement"),
            new TestCapabilityInfo("imageCapture", "ImageCapture"),
            new TestCapabilityInfo("indicator", "Indicator"),
            new TestCapabilityInfo("initialize", "Initialize"),
            new TestCapabilityInfo("light", "Light"),
            new TestCapabilityInfo("lightEffects", "LightEffects"),
            new TestCapabilityInfo("locationMode", "LocationMode"),
            new TestCapabilityInfo("lock", "Lock"),
            new TestCapabilityInfo("lockCodes", "LockCodes"),
            new TestCapabilityInfo("mediaController", "MediaController"),
            new TestCapabilityInfo("momentary", "Momentary"),
            new TestCapabilityInfo("motionSensor", "MotionSensor"),
            new TestCapabilityInfo("musicPlayer", "MusicPlayer"),
            new TestCapabilityInfo("notification", "Notification"),
            new TestCapabilityInfo("outlet", "Outlet"),
            new TestCapabilityInfo("polling", "Polling"),
            new TestCapabilityInfo("powerMeter", "PowerMeter"),
            new TestCapabilityInfo("powerSource", "PowerSource"),
            new TestCapabilityInfo("presenceSensor", "PresenceSensor"),
            new TestCapabilityInfo("pressureMeasurement", "PressureMeasurement"),
            new TestCapabilityInfo("pushableButton", "PushableButton"),
            new TestCapabilityInfo("refresh", "Refresh"),
            new TestCapabilityInfo("relativeHumidityMeasurement", "RelativeHumidityMeasurement"),
            new TestCapabilityInfo("relaySwitch", "RelaySwitch"),
            new TestCapabilityInfo("releasableButton", "ReleasableButton"),
            new TestCapabilityInfo("samsungTV", "SamsungTV"),
            new TestCapabilityInfo("securityKeypad", "SecurityKeypad"),
            new TestCapabilityInfo("sensor", "Sensor"),
            new TestCapabilityInfo("shockSensor", "ShockSensor"),
            new TestCapabilityInfo("signalStrength", "SignalStrength"),
            new TestCapabilityInfo("sleepSensor", "SleepSensor"),
            new TestCapabilityInfo("smokeDetector", "SmokeDetector"),
            new TestCapabilityInfo("soundPressureLevel", "SoundPressureLevel"),
            new TestCapabilityInfo("soundSensor", "SoundSensor"),
            new TestCapabilityInfo("speechRecognition", "SpeechRecognition"),
            new TestCapabilityInfo("speechSynthesis", "SpeechSynthesis"),
            new TestCapabilityInfo("stepSensor", "StepSensor"),
            new TestCapabilityInfo("switch", "Switch"),
            new TestCapabilityInfo("switchLevel", "SwitchLevel"),
            new TestCapabilityInfo("tv", "TV"),
            new TestCapabilityInfo("tamperAlert", "TamperAlert"),
            new TestCapabilityInfo("telnet", "Telnet"),
            new TestCapabilityInfo("temperatureMeasurement", "TemperatureMeasurement"),
            new TestCapabilityInfo("testCapability", "TestCapability"),
            new TestCapabilityInfo("thermostat", "Thermostat"),
            new TestCapabilityInfo("thermostatCoolingSetpoint", "ThermostatCoolingSetpoint"),
            new TestCapabilityInfo("thermostatFanMode", "ThermostatFanMode"),
            new TestCapabilityInfo("thermostatHeatingSetpoint", "ThermostatHeatingSetpoint"),
            new TestCapabilityInfo("thermostatMode", "ThermostatMode"),
            new TestCapabilityInfo("thermostatOperatingState", "ThermostatOperatingState"),
            new TestCapabilityInfo("thermostatSchedule", "ThermostatSchedule"),
            new TestCapabilityInfo("thermostatSetpoint", "ThermostatSetpoint"),
            new TestCapabilityInfo("threeAxis", "ThreeAxis"),
            new TestCapabilityInfo("timedSession", "TimedSession"),
            new TestCapabilityInfo("tone", "Tone"),
            new TestCapabilityInfo("touchSensor", "TouchSensor"),
            new TestCapabilityInfo("ultravioletIndex", "UltravioletIndex"),
            new TestCapabilityInfo("valve", "Valve"),
            new TestCapabilityInfo("videoCamera", "VideoCamera"),
            new TestCapabilityInfo("videoCapture", "VideoCapture"),
            new TestCapabilityInfo("voltageMeasurement", "VoltageMeasurement"),
            new TestCapabilityInfo("waterSensor", "WaterSensor"),
            new TestCapabilityInfo("windowShade", "WindowShade"),
            new TestCapabilityInfo("zwMultichannel", "ZwMultichannel"),
            new TestCapabilityInfo("pHMeasurement", "pHMeasurement")
    ]

    def "All device selector names are valid"()
    {
        expect:
            assert Capabilities.capabilitiesByDeviceSelector.keySet() as HashSet == expectedCapabilities.collect{it.deviceSelectorName} as HashSet
    }

    def "All driver definition names are valid"()
    {
        expect:
            assert Capabilities.capabilitiesByDriverDefinition.keySet() as HashSet == expectedCapabilities.collect{it.driverDefinition} as HashSet
    }
}

