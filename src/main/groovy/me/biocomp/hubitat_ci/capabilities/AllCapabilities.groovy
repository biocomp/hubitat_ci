package me.biocomp.hubitat_ci.capabilities

import groovy.transform.CompileStatic

/**
 * Base capability traits, all capabilities derive from it.
 */
@CompileStatic
interface Capability
{
}

interface AccelerationSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("acceleration", ["active", "inactive"]) // Called 'ActivityState' in SmartThing
    }
}

// Deprecated in SmartThings
@CompileStatic
interface Actuator extends Capability
{
}

// Only in SmartThings: interface AirConditionerMode {}
// Only in SmartThings: interface AirQualitySensor {}

interface Alarm extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("alarm", ["strobe", "both", "off", "siren"])
    }

    @CompileStatic
    abstract both()

    @CompileStatic
    abstract off()

    @CompileStatic
    abstract siren()

    @CompileStatic
    abstract strobe()
}

// Only in SmartThings: interface AudioMute{}

@CompileStatic
interface AudioNotification extends Capability
{
    /**
     * @param text required (STRING) - Text to play
     * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
     */
    abstract void playText(String text, Double volumeLevel)

    /**
    * @param text required (STRING) - Text to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
     */
    abstract playTextAndRestore(String text, Double volumeLevel)

    /**
    * @param text required (STRING) - Text to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTextAndResume(String text, Double volumeLevel)

    /**
    * @param trackUri required (STRING) - URI/URL of track to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTrack(String trackUri, Double volumeLevel)

    /**
    * @param trackUri required (STRING) - URI/URL of track to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTrackAndRestore(String trackUri, Double volumeLevel)

    /**
    * @param trackUri required (STRING) - URI/URL of track to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTrackAndResume(String trackUri, Double volumeLevel)
}

// SmartThings only: Audio Track Data

interface AudioVolume extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("mute", ["unmuted", "muted"])
        number("volume") // Double
    }

    @CompileStatic
    abstract void mute()

    /**
    * @param volumeLevel required (NUMBER) - Volume level (0 to 100)
    */
    @CompileStatic
    abstract void setVolume(Double volumeLevel)

    @CompileStatic
    abstract void unmute()

    @CompileStatic
    abstract void volumeDown()

    @CompileStatic
    abstract void volumeUp()
}

interface Battery extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("battery", min: 0, max: 100) // 0-100% battery charge
    }
}

// Deprecated in SmartThings
interface Beacon extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("presence", ["present", "not present"])
    }
}

// SmartThings only: Bridge


interface Bulb extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("switch", ["on", "off"])
    }

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

// Deprecated in both SmartThings and Hubitat
interface Button extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("button")
        enumAttribute("holdableButton", ["true", "false"])
        number("numberOfButtons")
    }
}

interface CarbonDioxideMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("carbonDioxide") // Double
    }
}

interface CarbonMonoxideDetector extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("carbonMonoxide", ["detected", "tested", "clear"])
    }
}

@CompileStatic
interface ChangeLevel extends Capability
{
    /**
     * @param direction required (ENUM) - Direction for level change request
     */
    abstract void startLevelChange(String direction)
    abstract void stopLevelChange()
}

interface Chime extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        jsonObject("soundEffects")
        stringAttribute("soundName")
        enumAttribute("status", ["playing", "stopped"])
    }

    @CompileStatic
    abstract void playSound(Number soundNumber)

    @CompileStatic
    abstract void stop()
}

interface ColorControl extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("rGB")
        stringAttribute("color")
        stringAttribute("colorName")
        number("hue") // Double
        number("saturation") // Double
    }


    /**
     *
     * @param colormap required (COLOR_MAP) - Color map settings [hue*:(0 to 100), saturation*:(0 to 100), level:(0 to 100)]
     * @return
     */
    @CompileStatic
    abstract void setColor(Map colorMap)

    /**
     * @param hue required (NUMBER) - Color Hue (0 to 100)
     */
    @CompileStatic
    abstract void setHue(Double hue)

    /**
     * @param saturation required (NUMBER) - Color Saturation (0 to 100)
     */
    @CompileStatic
    abstract void setSaturation(Double saturation)
}

interface ColorMode extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("colorMode", ["CT", "RGB"])
    }
}

interface ColorTemperature extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("colorName")
        number("colorTemperature") // Double
    }
}

@CompileStatic
interface Configuration extends Capability
{
    abstract void configure()
}

interface Consumable extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("consumableStatus", ["missing", "order", "maintenance_required", "good", "replace"])
    }

    @CompileStatic
    abstract void setConsumableStatus(String status)
}

// SmartThings only: Color

interface ContactSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("contact", ["closed", "open"])
    }
}

// SmartThings only: Demand Response Load Control
// SmartThings only: Dishwasher Mode
// SmartThings only: Dishwasher Operating State

interface DoorControl extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("door", ["unknown", "closed", "open", "closing", "opening"])
    }

    @CompileStatic
    abstract void open()

    @CompileStatic
    abstract void close()
}

// SmartThings only: Dryer Mode
// SmartThings only: Dryer Operating State
// Dust Sensor

interface DoubleTapableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("doubleTapped")
    }
}

interface EnergyMeter extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("energy") // Double, // in kW
    }
}

interface EstimatedTimeOfArrival extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        date("eta")
    }
}

// Execute

// SmartThings calls it Fan Speed
interface FanControl extends Capability
{


    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("speed", ["low", "medium-low", "medium", "medium-high", "high", "on", "off", "auto"])
    }


    @CompileStatic
    abstract void setSpeed(String speed)
}

interface FilterStatus extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("filterStatus", ["normal", "replace"])
    }
}

interface GarageDoorControl extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("door", ["unknown", "open", "closing", "closed", "opening"])
    }

    @CompileStatic
    abstract  void close()

    @CompileStatic
    abstract  void open()
}

// Geolocation

interface HealthCheck extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("checkInterval")
    }

    @CompileStatic
    abstract void ping()
}

interface HoldableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("held")
    }
}

interface IlluminanceMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("illuminance") // Double
    }
}

interface ImageCapture extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("image")
    }

    @CompileStatic
    abstract void take()
}

interface Indicator extends Capability
{

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("indicatorStatus", ["never", "when on", "when off"])
    }

    @CompileStatic
    abstract void indicatorNever()

    @CompileStatic
    abstract void indicatorWhenOff()

    @CompileStatic
    abstract void indicatorWhenOn()
}

// Infrared Level

@CompileStatic
interface Initialize extends Capability
{
    abstract static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("switch", ["on", "off"])
    }
}

interface Light extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("switch", ["on", "off"])
    }

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

interface LightEffects extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("effectName")
        jsonObject("lightEffects")
    }

    /**
     * @param effectNumber required (NUMBER) - Effect number to enable
     */
    @CompileStatic
    abstract void setEffect(Number effectNumber)

    @CompileStatic
    abstract void setNextEffect()

    @CompileStatic
    abstract void setPreviousEffect()
}

interface LocationMode extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("mode") // DYNAMIC_ENUM with mode
    }
}

// Lock only

interface Lock extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("lock", ["locked", "unlocked with timeout", "unlocked", "unknown"])
    }

    @CompileStatic
    abstract void lock()

    @CompileStatic
    abstract void unlock()
}

interface LockCodes extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("codeChanged", ["added", "changed", "deleted", "failed"])
        number("codeLength")
        jsonObject("lockCodes")
        number("maxCodes")
    }

    /**
     * @param codePosition required (NUMBER) - Code position number to delete
     */
    @CompileStatic
    abstract void deleteCode(codePosition)


    @CompileStatic
    abstract void getCodes()

    /**
     * @param codePosition - required (NUMBER) - Code position number
     * @param pinCode - required (STRING) - Numeric PIN code
     * @param name - optional (STRING) - Name for this lock code
     */
    @CompileStatic
    abstract void setCode(Number codePosition, String pinCode, String name)

    /**
     * @param pinCodeLength required (NUMBER) - Maximum pin code length for this lock
     */
    @CompileStatic
    abstract void setCodeLength(Number pinCodeLength)
}

interface MediaController extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        jsonObject("activities")
        stringAttribute("currentActivity")
    }
}

// Media Input Source
// Media Playback Repeat
// Media Playback Shuffle
// Media Playback
// Media Presets
// Media Track Control

@CompileStatic
interface Momentary extends Capability
{
    abstract void push()
}

interface MotionSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("motion", ["inactive", "active"])
    }
}

interface MusicPlayer extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("level") // Double
        enumAttribute("mute", ["unmuted", "muted"])
        stringAttribute("status")
        jsonObject("trackData")
        stringAttribute("trackDescription")
    }

    @CompileStatic
    abstract void mute()

    @CompileStatic
    abstract void nextTrack()

    @CompileStatic
    abstract void pause()

    @CompileStatic
    abstract void play()

    /**
     * @param text required (STRING) - Text to play
     */
    @CompileStatic
    abstract void playText(String text)


    /**
     * @param trackUri (STRING) - URI/URL of track to play
     */
    @CompileStatic
    abstract void playTrack(String trackUri)


    @CompileStatic
    abstract void previousTrack()

    /**
     *
     * @param trackUri required (STRING) - URI/URL of track to restore
     */
    @CompileStatic
    abstract void restoreTrack(trackUri)

    /**
     *
     * @param trackUri required (STRING) - URI/URL of track to play
     */
    @CompileStatic
    abstract void resumeTrack(trackUri)


    /**
     * @param volumeLevel required (NUMBER) - Volume level (0 to 100)
     * @return
     */
    @CompileStatic
    abstract void setLevel(Double volumeLevel)


    /**
     * @param trackUri required (STRING) - URI/URL of track to set
     */
    @CompileStatic
    abstract void setTrack(trackUri)

    @CompileStatic
    abstract void stop()

    @CompileStatic
    abstract void unmute()
}

/**
 * Allows for displaying notifications on devices that allow notifications to be displayed
 */
@CompileStatic
interface Notification extends Capability
{
    abstract void deviceNotification(String text)
}

// Odor Sensor

interface Outlet extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("switch", ["on", "off"])
    }

    @CompileStatic
    abstract void off()

    @CompileStatic
    abstract void on()
}

// Oven Mode
// Oven Operating State
// Oven Setpoint

// Deprecated in SmartThings
@CompileStatic
interface Polling extends Capability
{
    abstract void poll()
}

// Power Consumption Report

interface PowerMeter extends Capability, VoltageMeasurement
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("power") // Double // In Watt
    }
}

/**
 * Gives the ability to determine the current power source of the device
 */
interface PowerSource extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("powerSource", ["battery", "dc", "mains", "unknown"])
    }
}

interface PresenceSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("presence", ["present", "not present"])
    }

}

// Rapid Cooling

interface PressureMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("pressure") // Double
    }
}

interface PushableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("numberOfButtons")
        number("pushed")
    }
}

@CompileStatic
interface Refresh extends Capability
{
    abstract void refresh()
}

interface RelativeHumidityMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("humidity") // Double
    }
}

interface RelaySwitch extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("switch", ["on", "off"])
    }

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

// Robot Cleaner Cleaning Mode
// Robot Cleaner Movement
// Robot Cleaner Turbo Mode

interface ReleasableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("released")
    }
}

@CustomDeviceSelector(deviceSelector = 'samsungTV')
@CustomPrettyName(prettyName = 'Samsung TV')
interface SamsungTV extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList {
        jsonObject("messageButton")
        enumAttribute("mute", ["muted", "unknown", "unmuted"])
        enumAttribute("pictureMode", ["unknown", "standard", "movie", "dynamic"])
        enumAttribute("soundMode", ["speech", "movie", "unknown", "standard", "music"])
        enumAttribute("switch", ["on", "off"])
        number("volume") // Double
    }

    @CompileStatic
    abstract void mute()

    @CompileStatic
    abstract void off()

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void setPictureMode(String mode)

    @CompileStatic
    abstract void setSoundMode(String mode)

    @CompileStatic
    abstract void setVolume(Double volume)

    @CompileStatic
    abstract void showMessage(String a, String b, String c, String d)

    @CompileStatic
    abstract void unmute()

    @CompileStatic
    abstract void volumeDown()

    @CompileStatic
    abstract void volumeUp()
}

interface SecurityKeypad extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("codeChanged", ["added", "changed", "deleted", "failed"])
        number("codeLength")
        jsonObject("lockCodes")
        number("maxCodes")
        enumAttribute("securityKeypad", ["disarmed","armed home","armed away","unknown"])
    }

    @CompileStatic
    abstract void armAway()

    @CompileStatic
    abstract void armHome()

    /**
     *
     * @param codePosition required (NUMBER) - Code position number to delete
     * @return
     */
    @CompileStatic
    abstract void deleteCode(Number codePosition)

    @CompileStatic
    abstract void disarm()

    @CompileStatic
    abstract void getCodes()


    /**
     * @param codePosition required (NUMBER) - Code position number
     * @param pinCode required (STRING) - Numeric PIN code
     * @param name optional (STRING) - Name for this lock code
     */
    @CompileStatic
    abstract void setCode(codePosition, pinCode, name)

    /**
     * @param pinCodeLength required (NUMBER) - Maximum pin code length for this keypad
     */
    @CompileStatic
    abstract void setCodeLength(pinCodeLength)


    @CompileStatic
    abstract void setEntryDelay(Number entranceDelayInSeconds)

    /**
     * @param exitDelay required (NUMBER) - Exit delay in seconds
     */
    @CompileStatic
    abstract void setExitDelay(exitDelay)
}

@CompileStatic
interface Sensor extends Capability
{}

interface ShockSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("shock", ["clear", "detected"])
    }
}

@CompileStatic
interface SignalStrength extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("lqi", min: 0, max: 255) // Double
        number("rssi", min: -200, max: 0) // Double
    }
}

interface SleepSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("sleeping", ["not sleeping", "sleeping"])
    }
}

interface SmokeDetector extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("smoke", ["clear", "tested", "detected"])
    }
}

interface SoundPressureLevel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("soundPressureLevel") // Double
    }
}

interface SoundSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("sound", ["not detected", "detected"])
    }
}

interface SpeechRecognition extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("phraseSpoken")
    }
}

@CompileStatic
interface SpeechSynthesis extends Capability
{
    abstract void speak(String text)
}

interface StepSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("goal") // Double
        number("steps") // Double
    }
}

interface Switch extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("switch", ["on", "off"])
    }

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

interface SwitchLevel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("level", min: 0, max: 100) // Double
    }

    /**
    * @param level required (NUMBER) - Level to set (0 to 100)
    * @param duration optional (NUMBER) - Transition duration in seconds
    */
    @CompileStatic
    abstract void setLevel(Double level, Number duration)
}

@CustomDeviceSelector(deviceSelector = 'tv')
@CustomPrettyName(prettyName = 'TV')
interface TV extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("channel")
        stringAttribute("movieMode")
        stringAttribute("picture")
        stringAttribute("power")
        stringAttribute("sound")
        number("volume") // Double
    }

    @CompileStatic
    abstract void channelDown()

    @CompileStatic
    abstract void channelUp()

    @CompileStatic
    abstract void volumeDown()

    @CompileStatic
    abstract void volumeUp()
}

interface TamperAlert extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("tamper", ["clear", "detected"])
    }
}

@CompileStatic
interface Telnet extends Capability
{
}

interface TemperatureMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
            number("temperature") // Double
    }
}

@CompileStatic
interface TestCapability extends Capability {}

@CompileStatic
interface Thermostat extends
        ThermostatCoolingSetpoint,
        ThermostatHeatingSetpoint,
        ThermostatFanMode,
        ThermostatMode,
        ThermostatOperatingState,
        ThermostatSchedule,
        ThermostatSetpoint
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        jsonObject("supportedThermostatFanModes") // Array of modes
        jsonObject("supportedThermostatModes") // Array of modes
        number("temperature") // Double
    }
}

@CompileStatic
interface ThermostatCoolingSetpoint extends Capability
{
    static def _internalAttributes = CapabilityAttributeInfo.makeList {
        number("coolingSetpoint")
    }

    /**
     *
     * @param temperature required (NUMBER) - Cooling setpoint in degrees
     */
    @CompileStatic
    abstract void setCoolingSetpoint(Number temperature)
}

interface ThermostatFanMode extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("thermostatFanMode", ["auto", "circulate", "on"])
    }

    @CompileStatic
    abstract void fanAuto()

    @CompileStatic
    abstract void fanCirculate()

    @CompileStatic
    abstract void fanOn()

    @CompileStatic
    abstract void setThermostatFanMode(String fanMode)
}

interface ThermostatHeatingSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("heatingSetpoint") // temperature in degree
    }

    /**
     *
     * @param temperature required (NUMBER) - Heating setpoint in degrees
     */
    @CompileStatic
    abstract void setHeatingSetpoint(Number temperature)
}

interface ThermostatMode extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("thermostatMode", ["auto", "off", "heat", "emergency heat", "cool"])
    }

    @CompileStatic
    abstract void auto()

    @CompileStatic
    abstract void cool()

    @CompileStatic
    abstract void emergencyHeat()

    @CompileStatic
    abstract void heat()

    @CompileStatic
    abstract void off()

    @CompileStatic
    abstract void setThermostatMode(String mode)
}

interface ThermostatOperatingState extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("thermostatOperatingState", ["heating", "pending cool", "pending_heat", "vent economizer", "idle", "cooling", "fan only"])
    }
}

interface ThermostatSchedule extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        jsonObject("schedule")
    }

    @CompileStatic
    abstract void setSchedule(def jsonObject)
}

interface ThermostatSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("thermostatSetpoint")
    }
}

interface ThreeAxis extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("threeAxis") // Seems like string in "x,y,z" format
    }
}

interface TimedSession extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("sessionStatus", ["stopped", "canceled", "running", "paused"])
        number("timeRemaining")
    }

    @CompileStatic
    abstract void cancel()

    @CompileStatic
    abstract void pause()

    @CompileStatic
    abstract void setTimeRemaining(Number time) // Is it NUMBER though?

    @CompileStatic
    abstract void start()

    @CompileStatic
    abstract void stop()
}

@CompileStatic
interface Tone extends Capability
{
    abstract void beep()
}

interface TouchSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("touch", ["touched"])
    }
}

// Tv Channel

interface UltravioletIndex extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("ultravioletIndex", min: 0, max: 255)
    }
}

interface Valve extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("valve", ["open", "closed"])
    }

    @CompileStatic
    abstract void open()

    @CompileStatic
    abstract void close()
}

// Video Stream
// Video Clips

interface VideoCamera extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("camera", ["on", "off", "restarting", "unavailable"])
        enumAttribute("mute", ["unmuted", "muted"])
        jsonObject("settings")
        stringAttribute("statusMessage")
    }

    @CompileStatic
    abstract void flip()

    @CompileStatic
    abstract void mute()

    @CompileStatic
    abstract void off()

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void unmute()
}

//interface VideoCapture extends Capability
//{
//    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
//        attribute("clip", Object)
//    }
//
//    @CompileStatic
//    abstract void capture(Date a, Date b, Date c)
//}

interface VoltageMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("voltage") // In Volt
    }
}

// Washer Mode
// Washer Operating State

interface WaterSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        enumAttribute("water", ["wet", "dry"])
    }
}

interface WindowShade extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("position", min: 0, max: 100)
        enumAttribute("windowShade", [ "opening", "partially open", "closed", "open", "closing", "unknown"])
    }

    @CompileStatic
    abstract void close()

    @CompileStatic
    abstract void open()

    @CompileStatic
    abstract void setPosition(Number position)
}

interface ZwMultichannel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        stringAttribute("epEvent")
        stringAttribute("epInfo")
    }

    @CompileStatic
    abstract void enableEpEvents(String a)

    @CompileStatic
    abstract void epCmd(Number a, String b)
}

@CustomDeviceSelector(deviceSelector = 'pHMeasurement')
@CustomDriverDefinition(driverDefinition = 'pHMeasurement')
@CustomPrettyName(prettyName = 'Ph Measurement')
interface PhMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList{
        number("PH")
    }
}
