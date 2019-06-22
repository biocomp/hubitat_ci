package biocomp.hubitatCi.capabilities


import spock.lang.Specification

class TestCapabilityInfo
{
    TestCapabilityInfo(String deviceSelectorName, String driverDefinition, String capabilityReadableName)
    {
        this.deviceSelectorName = deviceSelectorName
        this.driverDefinition = driverDefinition
        this.capabilityPrettyName = capabilityReadableName
    }

    TestCapabilityInfo(String deviceSelectorName, String driverDefinition)
    {
        this.deviceSelectorName = deviceSelectorName
        this.driverDefinition = driverDefinition
        this.capabilityPrettyName = driverDefinition
    }

    String deviceSelectorName
    String driverDefinition
    String capabilityPrettyName
}

class CapabilitiesTest extends Specification
{
    static final List<TestCapabilityInfo> expectedCapabilities = [
            new TestCapabilityInfo("accelerationSensor", "AccelerationSensor", "Acceleration Sensor"),
            new TestCapabilityInfo("actuator", "Actuator"),
            new TestCapabilityInfo("alarm", "Alarm"),
            new TestCapabilityInfo("audioNotification", "AudioNotification", "Audio Notification"),
            new TestCapabilityInfo("audioVolume", "AudioVolume", "Audio Volume"),
            new TestCapabilityInfo("battery", "Battery"),
            new TestCapabilityInfo("beacon", "Beacon"),
            new TestCapabilityInfo("bulb", "Bulb"),
            new TestCapabilityInfo("button", "Button"),
            new TestCapabilityInfo("carbonDioxideMeasurement", "CarbonDioxideMeasurement", "Carbon Dioxide Measurement"),
            new TestCapabilityInfo("carbonMonoxideDetector", "CarbonMonoxideDetector", "Carbon Monoxide Detector"),
            new TestCapabilityInfo("changeLevel", "ChangeLevel", "Change Level"),
            new TestCapabilityInfo("chime", "Chime"),
            new TestCapabilityInfo("colorControl", "ColorControl", "Color Control"),
            new TestCapabilityInfo("colorMode", "ColorMode", "Color Mode"),
            new TestCapabilityInfo("colorTemperature", "ColorTemperature", "Color Temperature"),
            new TestCapabilityInfo("configuration", "Configuration"),
            new TestCapabilityInfo("consumable", "Consumable"),
            new TestCapabilityInfo("contactSensor", "ContactSensor", "Contact Sensor"),
            new TestCapabilityInfo("doorControl", "DoorControl", "Door Control"),
            new TestCapabilityInfo("doubleTapableButton", "DoubleTapableButton", "Double Tapable Button"),
            new TestCapabilityInfo("energyMeter", "EnergyMeter", "Energy Meter"),
            new TestCapabilityInfo("estimatedTimeOfArrival", "EstimatedTimeOfArrival", "Estimated Time Of Arrival"),
            new TestCapabilityInfo("fanControl", "FanControl", "Fan Control"),
            new TestCapabilityInfo("filterStatus", "FilterStatus", "Filter Status"),
            new TestCapabilityInfo("garageDoorControl", "GarageDoorControl", "Garage Door Control"),
            new TestCapabilityInfo("healthCheck", "HealthCheck", "Health Check"),
            new TestCapabilityInfo("holdableButton", "HoldableButton", "Holdable Button"),
            new TestCapabilityInfo("illuminanceMeasurement", "IlluminanceMeasurement", "Illuminance Measurement"),
            new TestCapabilityInfo("imageCapture", "ImageCapture", "Image Capture"),
            new TestCapabilityInfo("indicator", "Indicator"),
            new TestCapabilityInfo("initialize", "Initialize"),
            new TestCapabilityInfo("light", "Light"),
            new TestCapabilityInfo("lightEffects", "LightEffects", "Light Effects"),
            new TestCapabilityInfo("locationMode", "LocationMode", "Location Mode"),
            new TestCapabilityInfo("lock", "Lock"),
            new TestCapabilityInfo("lockCodes", "LockCodes", "Lock Codes"),
            new TestCapabilityInfo("mediaController", "MediaController", "Media Controller"),
            new TestCapabilityInfo("momentary", "Momentary"),
            new TestCapabilityInfo("motionSensor", "MotionSensor", "Motion Sensor"),
            new TestCapabilityInfo("musicPlayer", "MusicPlayer", "Music Player"),
            new TestCapabilityInfo("notification", "Notification"),
            new TestCapabilityInfo("outlet", "Outlet"),
            new TestCapabilityInfo("polling", "Polling"),
            new TestCapabilityInfo("powerMeter", "PowerMeter", "Power Meter"),
            new TestCapabilityInfo("powerSource", "PowerSource", "Power Source"),
            new TestCapabilityInfo("presenceSensor", "PresenceSensor", "Presence Sensor"),
            new TestCapabilityInfo("pressureMeasurement", "PressureMeasurement", "Pressure Measurement"),
            new TestCapabilityInfo("pushableButton", "PushableButton", "Pushable Button"),
            new TestCapabilityInfo("refresh", "Refresh"),
            new TestCapabilityInfo("relativeHumidityMeasurement", "RelativeHumidityMeasurement", "Relative Humidity Measurement"),
            new TestCapabilityInfo("relaySwitch", "RelaySwitch", "Relay Switch"),
            new TestCapabilityInfo("releasableButton", "ReleasableButton", "Releasable Button"),
            new TestCapabilityInfo("samsungTV", "SamsungTV", "Samsung TV"),
            new TestCapabilityInfo("securityKeypad", "SecurityKeypad", "Security Keypad"),
            new TestCapabilityInfo("sensor", "Sensor"),
            new TestCapabilityInfo("shockSensor", "ShockSensor", "Shock Sensor"),
            new TestCapabilityInfo("signalStrength", "SignalStrength", "Signal Strength"),
            new TestCapabilityInfo("sleepSensor", "SleepSensor", "Sleep Sensor"),
            new TestCapabilityInfo("smokeDetector", "SmokeDetector", "Smoke Detector"),
            new TestCapabilityInfo("soundPressureLevel", "SoundPressureLevel", "Sound Pressure Level"),
            new TestCapabilityInfo("soundSensor", "SoundSensor", "Sound Sensor"),
            new TestCapabilityInfo("speechRecognition", "SpeechRecognition", "Speech Recognition"),
            new TestCapabilityInfo("speechSynthesis", "SpeechSynthesis", "Speech Synthesis"),
            new TestCapabilityInfo("stepSensor", "StepSensor", "Step Sensor"),
            new TestCapabilityInfo("switch", "Switch"),
            new TestCapabilityInfo("switchLevel", "SwitchLevel", "Switch Level"),
            new TestCapabilityInfo("tv", "TV"),
            new TestCapabilityInfo("tamperAlert", "TamperAlert", "Tamper Alert"),
            new TestCapabilityInfo("telnet", "Telnet"),
            new TestCapabilityInfo("temperatureMeasurement", "TemperatureMeasurement", "Temperature Measurement"),
            new TestCapabilityInfo("testCapability", "TestCapability", "Test Capability"),
            new TestCapabilityInfo("thermostat", "Thermostat"),
            new TestCapabilityInfo("thermostatCoolingSetpoint", "ThermostatCoolingSetpoint", "Thermostat Cooling Setpoint"),
            new TestCapabilityInfo("thermostatFanMode", "ThermostatFanMode", "Thermostat Fan Mode"),
            new TestCapabilityInfo("thermostatHeatingSetpoint", "ThermostatHeatingSetpoint", "Thermostat Heating Setpoint"),
            new TestCapabilityInfo("thermostatMode", "ThermostatMode", "Thermostat Mode"),
            new TestCapabilityInfo("thermostatOperatingState", "ThermostatOperatingState", "Thermostat Operating State"),
            new TestCapabilityInfo("thermostatSchedule", "ThermostatSchedule", "Thermostat Schedule"),
            new TestCapabilityInfo("thermostatSetpoint", "ThermostatSetpoint", "Thermostat Setpoint"),
            new TestCapabilityInfo("threeAxis", "ThreeAxis", "Three Axis"),
            new TestCapabilityInfo("timedSession", "TimedSession", "Timed Session"),
            new TestCapabilityInfo("tone", "Tone"),
            new TestCapabilityInfo("touchSensor", "TouchSensor", "Touch Sensor"),
            new TestCapabilityInfo("ultravioletIndex", "UltravioletIndex", "Ultraviolet Index"),
            new TestCapabilityInfo("valve", "Valve"),
            new TestCapabilityInfo("videoCamera", "VideoCamera", "Video Camera"),
            new TestCapabilityInfo("videoCapture", "VideoCapture", "Video Capture"),
            new TestCapabilityInfo("voltageMeasurement", "VoltageMeasurement", "Voltage Measurement"),
            new TestCapabilityInfo("waterSensor", "WaterSensor", "Water Sensor"),
            new TestCapabilityInfo("windowShade", "WindowShade", "Window Shade"),
            new TestCapabilityInfo("zwMultichannel", "ZwMultichannel", "Zw Multichannel"),
            new TestCapabilityInfo("pHMeasurement", "pHMeasurement", "Ph Measurement")
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

    def "All capability pretty names"()
    {
        expect:
            assert Capabilities.capabilitiesByPrettyName.keySet() as HashSet == expectedCapabilities.collect{it.capabilityPrettyName} as HashSet
    }
}

/**
 * Just testing instantiation of one of the more complicated capabilities
 */
class ThermostatCapabilityTest extends Specification
{
    def "foo"()
    {
        expect:
            ThermostatCoolingSetpoint._internalAttributes
            Thermostat._internalAttributes
            Capabilities.readAttributes(Thermostat.class).keySet() == ["supportedThermostatFanModes", "supportedThermostatModes", "temperature", "coolingSetpoint", "thermostatFanMode", "heatingSetpoint", "thermostatMode", "thermostatOperatingState", "schedule", "thermostatSetpoint"] as Set
    }
}

