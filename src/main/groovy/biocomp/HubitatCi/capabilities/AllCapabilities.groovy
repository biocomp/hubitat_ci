package biocomp.hubitatCi.capabilities

Map<String, CapabilityAttributeInfo> makeAttributes(List<CapabilityAttributeInfo> attributes)
{
    return attributes.collectEntries { it -> [it.name, it]}
}

/**
 * Base capability traits, all capabilities derive from it.
 */
interface Capability
{

}

interface AccelerationSensor extends Capability
{
    enum AccelerationValue
    {
        active,
        inactive
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("acceleration", AccelerationValue) // Called 'ActivityState' in SmartThings
    ])
}

// Deprecated in SmartThings
interface Actuator extends Capability
{
}

// Only in SmartThings: interface AirConditionerMode {}
// Only in SmartThings: interface AirQualitySensor {}

interface Alarm extends Capability
{
    enum AlarmValue
    {
        strobe,
        both,
        off,
        siren
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("alarm", AlarmValue)
    ])

    abstract both()
    abstract off()
    abstract siren()
    abstract strobe()
}

// Only in SmartThings: interface AudioMute{}

interface AudioNotification extends Capability
{
    /**
     * @param text required (STRING) - Text to play
     * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
     */
    abstract void playText(String text, double volumeLevel)

    /**
    * @param text required (STRING) - Text to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
     */
    abstract playTextAndRestore(String text, double volumeLevel)

    /**
    * @param text required (STRING) - Text to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTextAndResume(String text, double volumeLevel)

    /**
    * @param trackUri required (STRING) - URI/URL of track to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTrack(String trackUri, double volumeLevel)

    /**
    * @param trackUri required (STRING) - URI/URL of track to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTrackAndRestore(String trackUri, double volumeLevel)

    /**
    * @param trackUri required (STRING) - URI/URL of track to play
    * @param volumeLevel optional (NUMBER) - Volume level (0 to 100)
    * */
    abstract playTrackAndResume(String trackUri, double volumeLevel)
}

// SmartThings only: Audio Track Data

interface AudioVolume extends Capability
{
    enum MuteValue
    {
        unmuted,
        muted
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("volume", double)
    ])

    abstract void mute()

    /**
    * @param volumeLevel required (NUMBER) - Volume level (0 to 100)
    */
    abstract void setVolume(double volumeLevel)

    abstract void unmute()
    abstract void volumeDown()
    abstract void volumeUp()
}

interface Battery extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("battery", double, min: 0, max: 100) // 0-100% battery charge
    ])
}

// Deprecated in SmartThings
interface Beacon extends Capability
{
    enum PresenceValue
    {
        not_present("not present"),
        present("present")

        PresenceValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("presence", PresenceValue)
    ])
}

// SmartThings only: Bridge


interface Bulb extends Capability
{
    enum SwitchValue
    {
        on,
        off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("switch", SwitchValue)
    ])

    abstract void on()
    abstract void off()
}

// Deprecated in both SmartThings and Hubitat
interface Button extends Capability
{
    enum HoldableButtonValue
    {
        true_("true"),
        false_("false")

        HoldableButtonValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("button", int),
            new CapabilityAttributeInfo("holdableButton", HoldableButtonValue),
            new CapabilityAttributeInfo("numberOfButtons", int),
    ])
}

interface CarbonDioxideMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("carbonDioxide", double)
    ])
}

interface CarbonMonoxideDetector extends Capability
{
    enum CarbonMonoxideValue
    {
        detected, tested, clear
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("carbonMonoxide", CarbonMonoxideValue)
    ])
}

interface ChangeLevel extends Capability
{
    enum DirectionValue
    {}

    /**
     * @param direction required (ENUM) - Direction for level change request
     */
    abstract void startLevelChange(DirectionValue direction)
    abstract void stopLevelChange()
}

interface Chime extends Capability
{
    enum StatusValue
    {
        playing, stopped
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("soundEffects", Object), // JSON_OBJECT
            new CapabilityAttributeInfo("soundName", String),
            new CapabilityAttributeInfo("status", StatusValue),
    ])

    abstract void playSound(int soundNumber)
    abstract void stop()
}

interface ColorControl extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
        new CapabilityAttributeInfo("rGB", String),
        new CapabilityAttributeInfo("color", String),
        new CapabilityAttributeInfo("colorName", String),
        new CapabilityAttributeInfo("hue", double),
        new CapabilityAttributeInfo("saturation", double),
    ])


    /**
     *
     * @param colormap required (COLOR_MAP) - Color map settings [hue*:(0 to 100), saturation*:(0 to 100), level:(0 to 100)]
     * @return
     */
    abstract void setColor(Map colorMap)

    /**
     * @param hue required (NUMBER) - Color Hue (0 to 100)
     */
    abstract void setHue(double hue)

    /**
     * @param saturation required (NUMBER) - Color Saturation (0 to 100)
     */
    abstract void setSaturation(double saturation)
}

interface ColorMode extends Capability
{
    enum ColorModeValue{
        CT, RGB
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("colorMode", ColorModeValue),
    ])
}

interface ColorTemperature extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("colorName", String),
            new CapabilityAttributeInfo("colorTemperature", double)
    ])
}

interface Configuration extends Capability
{
    abstract void configure()
}

interface Consumable extends Capability
{
    enum ConsumableStatusValue
    {
        missing, order, maintenance_required, good, replace
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
        new CapabilityAttributeInfo("consumableStatus", ConsumableStatusValue),
    ])

    abstract void setConsumableStatus(ConsumableStatusValue status)
}

// SmartThings only: Color

interface ContactSensor extends Capability
{
    enum ContactValue
    {
        closed, open
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("contact", ContactValue),
    ])

}

// SmartThings only: Demand Response Load Control
// SmartThings only: Dishwasher Mode
// SmartThings only: Dishwasher Operating State

interface DoorControl extends Capability
{
    enum DoorValue
    {
        unknown, closed, open, closing, opening
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("door", DoorValue),
    ])

    abstract void open()
    abstract void close()
}

// SmartThings only: Dryer Mode
// SmartThings only: Dryer Operating State
// Dust Sensor

interface DoubleTapableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("doubleTapped", int),
    ])
}

interface EnergyMeter extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("energy", double), // in kWh
    ])
}

interface EstimatedTimeOfArrival extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("eta", Date),
    ])
}

// Execute

// SmartThings calls it Fan Speed
interface FanControl extends Capability
{
    enum SpeedValue
    {
        low("low"),
        medium_low("medium-low"),
        medium("medium"),
        medium_high("medium-high"),
        high("high"),
        on("on"),
        off("off"),
        auto("autp")

        SpeedValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("speed", SpeedValue),
    ])


    abstract void setSpeed(SpeedValue speed)
}

interface FilterStatus extends Capability
{
    enum FilterStatusValue
    {
        normal, replace
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("filterStatus", FilterStatusValue),
    ])
}

interface GarageDoorControl extends Capability
{
    enum DoorValue
    {
        unknown, open, closing, closed, opening
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("door", DoorValue),
    ])

    abstract  void close()
    abstract  void open()
}

// Geolocation

interface HealthCheck extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("checkInterval", int),
    ])

    abstract void ping()
}

interface HoldableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("held", int),
    ])
}

interface IlluminanceMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("illuminance", double),
    ])
}

interface ImageCapture extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("image", String),
    ])

    abstract void take()
}

interface Indicator extends Capability
{
    enum IndicatorStatusValue
    {
        never("never"),
        when_on("when on"),
        when_off("when off")

        IndicatorStatusValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("indicatorStatus", IndicatorStatusValue),
    ])

    abstract void indicatorNever()
    abstract void indicatorWhenOff()
    abstract void indicatorWhenOn()
}

// Infrared Level

interface Initialize extends Capability
{
    abstract void initialize()
}

interface Light extends Capability
{
    enum SwitchValue { on, off }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("switch", SwitchValue)
    ])

    abstract void on()
    abstract void off()
}

interface LightEffects extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("effectName", String),
            new CapabilityAttributeInfo("lightEffects", Object) // JSON object
    ])

    /**
     * @param effectNumber required (NUMBER) - Effect number to enable
     */
    abstract void setEffect(int effectNumber)

    abstract void setNextEffect()
    abstract void setPreviousEffect()
}

interface LocationMode extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("mode", String), // DYNAMIC_ENUM with mode
    ])
}

// Lock only

interface Lock extends Capability
{
    enum LockValue
    {
        locked("locked"),
        unlocked_with_timeout("unlocked with timeout"),
        unlocked("unlocked"),
        unknown("unknown")

        LockValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("lock", LockValue),
    ])

    abstract void lock()
    abstract void unlock()
}

interface LockCodes extends Capability
{
    enum CodeChangedValue
    {
        added, changed, deleted, failed
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("codeChanged", CodeChangedValue),
            new CapabilityAttributeInfo("codeLength", int),
            new CapabilityAttributeInfo("lockCodes", Object), // JSON object
            new CapabilityAttributeInfo("maxCodes", int)
    ])

    /**
     * @param codePosition required (NUMBER) - Code position number to delete
     */
    abstract void deleteCode(codePosition)


    abstract void getCodes()

    /**
     * @param codePosition - required (NUMBER) - Code position number
     * @param pinCode - required (STRING) - Numeric PIN code
     * @param name - optional (STRING) - Name for this lock code
     */
    abstract void setCode(int codePosition, String pinCode, String name)

    /**
     * @param pinCodeLength required (NUMBER) - Maximum pin code lentgh for this lock
     */
    abstract void setCodeLength(int pinCodeLength)
}

interface MediaController extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("activities", Object), // JSON object with activities
            new CapabilityAttributeInfo("currentActivity", String)
    ])
}

// Media Input Source
// Media Playback Repeat
// Media Playback Shuffle
// Media Playback
// Media Presets
// Media Track Control

interface Momentary extends Capability
{
    abstract void push()
}

interface MotionSensor extends Capability
{
    enum MotionValue
    {
        inactive, active
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            //        new CapabilityAttributeInfo("motion", MotionValue),
    ])
}

interface MusicPlayer extends Capability
{
    enum MuteValue
    {
        unmuted, muted
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("level", double),
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("status", String),
            new CapabilityAttributeInfo("trackData", Object), // JSON object
            new CapabilityAttributeInfo("trackDescription", String)
    ])

    abstract void mute()
    abstract void nextTrack()
    abstract void pause()
    abstract void play()

    /**
     * @param text required (STRING) - Text to play
     */
    abstract void playText(String text)


    /**
     * @param trackUri (STRING) - URI/URL of track to play
     */
    abstract void playTrack(String trackUri)



    abstract void previousTrack()

    /**
     *
     * @param trackUri required (STRING) - URI/URL of track to restore
     */
    abstract void restoreTrack(trackUri)

    /**
     *
     * @param trackUri required (STRING) - URI/URL of track to play
     */
    abstract void resumeTrack(trackUri)


    /**
     * @param volumeLevel required (NUMBER) - Volume level (0 to 100)
     * @return
     */
    abstract void setLevel(double volumeLevel)


    /**
     * @param trackUri required (STRING) - URI/URL of track to set
     */
    abstract void setTrack(trackUri)

    abstract void stop()
    abstract void unmute()
}

/**
 * Allows for displaying notifications on devices that allow notifications to be displayed
 */
interface Notification extends Capability
{
    abstract void deviceNotification(String text)
}

// Odor Sensor

interface Outlet extends Capability
{
    enum SwitchValue
    {
        on, off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("switch", SwitchValue),
    ])

    abstract void off()
    abstract void on()
}

// Oven Mode
// Oven Operating State
// Oven Setpoint

// Deprecated in SmartThings
interface Polling extends Capability
{
    abstract void poll()
}

// Power Consumption Report

interface PowerMeter extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("power", double), // In Watts
    ])
}

/**
 * Gives the ability to determine the current power source of the device
 */
interface PowerSource extends Capability
{
    enum PowerSourceValue
    {
        battery, dc, mains, unknown
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("powerSource", PowerSourceValue),
    ])
}

interface PresenceSensor extends Capability
{
    enum PresenceValue
    {
        present("present"),
        not_present("not present")

        PresenceValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("presense", PresenceValue),
    ])

}

// Rapid Cooling

interface PressureMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("pressure", double),
    ])
}

interface PushableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("numberOfButtons", int),
            new CapabilityAttributeInfo("pushed", int)
    ])
}

interface Refresh extends Capability
{
    abstract void refresh()
}

interface RelativeHumidityMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("humidity", double),
    ])
}

interface RelaySwitch extends Capability
{
    enum SwitchValue
    {
        on, off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("switch", SwitchValue),
    ])

    abstract void on()
    abstract void off()
}

// Robot Cleaner Cleaning Mode
// Robot Cleaner Movement
// Robot Cleaner Turbo Mode

interface ReleasableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("released", int),
    ])
}

@CustomDeviceSelector(deviceSelector = 'samsungTV')
@CustomPrettyName(prettyName = 'Samsung TV')
interface SamsungTV extends Capability
{
    enum MuteValue
    {
        muted, unknown, unmuted
    }

    enum PictureModeValue
    {
        unknown, standard, movie, dynamic
    }

    enum SoundModeValue
    {
        speech, movie, unknown, standard, music
    }

    enum SwitchValue
    {
        on, off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("messageButton", Object), // JSON object
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("pictureMode", PictureModeValue),
            new CapabilityAttributeInfo("soundMode", SoundModeValue),
            new CapabilityAttributeInfo("switch", SwitchValue),
            new CapabilityAttributeInfo("volume", double)
    ])

    abstract void mute()
    abstract void off()
    abstract void on()
    abstract void setPictureMode(PictureModeValue mode)

    abstract void setSoundMode(SoundModeValue mode)

    abstract void setVolume(double volume)

    abstract void showMessage(String a, String b, String c, String d)

    abstract void unmute()
    abstract void volumeDown()
    abstract void volumeUp()
}

interface SecurityKeypad extends Capability
{
    enum CodeChangedValue
    {
        added, changed, deleted, failed
    }

    enum SecurityKeypadValue
    {
        disarmed("disarmed"),
        armed_home("armed home"),
        armed_away("armed away"),
        unknown("unknown")

        SecurityKeypadValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }



    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("codeChanged", CodeChangedValue),
            new CapabilityAttributeInfo("codeLength", int),
            new CapabilityAttributeInfo("lockCodes", Object), // JSON object
            new CapabilityAttributeInfo("maxCodes", int),
            new CapabilityAttributeInfo("securityKeypad", SecurityKeypadValue),
    ])

    abstract void armAway()
    abstract void armHome()

    /**
     *
     * @param codePosition required (NUMBER) - Code position number to delete
     * @return
     */
    abstract void deleteCode(int codePosition)

    abstract void disarm()

    abstract void getCodes()


    /**
     * @param codePosition required (NUMBER) - Code position number
     * @param pinCode required (STRING) - Numeric PIN code
     * @param name optional (STRING) - Name for this lock code
     */
    abstract void setCode(codePosition, pinCode, name)

    /**
     * @param pinCodeLength required (NUMBER) - Maximum pin code lentgh for this keypad
     */
    abstract void setCodeLength(pinCodeLength)


    abstract void setEntryDelay(int entranceDelayInSeconds)

    /**
     * @param exitDelay required (NUMBER) - Exit delay in seconds
     */
    abstract void setExitDelay(exitDelay)
}

interface Sensor extends Capability
{

}

interface ShockSensor extends Capability
{
    enum ShockValue
    {
        clear, detected
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("shock", ShockSensor),
    ])
}

interface SignalStrength extends Capability
{
    /**
     *
     * @return 0 - 255 value
     */
    abstract double lqi()

    /**
     * @return -200 - 0 value
     */
    abstract double rssi()
}

interface SleepSensor extends Capability
{
    enum SleepingValue
    {
        not_sleeping("not sleeping"),
        sleeping("sleeping")

        SleepingValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("sleeping", SleepingValue),
    ])
}

interface SmokeDetector extends Capability
{
    enum SmokeValue
    {
        clear, tested, detected
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("smoke", SmokeValue),
    ])
}

interface SoundPressureLevel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("soundPressureLevel", double),
    ])
}

interface SoundSensor extends Capability
{
    enum SoundValue
    {
        not_detected("not detected"),
        detected("detected")

        SoundValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("sound", SoundValue),
    ])
}

interface SpeechRecognition extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("phraseSpoken", String),
    ])
}

interface SpeechSynthesis extends Capability
{
    abstract void speak(String text)
}

interface StepSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("goal", double),
            new CapabilityAttributeInfo("steps", double)
    ])
}

interface Switch extends Capability
{
    enum SwitchValue { on, off }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("switch", SwitchValue)
    ])

    abstract void on()
    abstract void off()
}

interface SwitchLevel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("level", double, min: 0, max: 100)
    ])

    /**
    * @param level required (NUMBER) - Level to set (0 to 100)
    * @param duration optional (NUMBER) - Transition duration in seconds
    */
    abstract void setLevel(double level, int duration)
}

@CustomDeviceSelector(deviceSelector = 'tv')
@CustomPrettyName(prettyName = 'TV')
interface TV extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
        new CapabilityAttributeInfo("channel",  int),
        new CapabilityAttributeInfo("movieMode",  String),
        new CapabilityAttributeInfo("picture",  String),
        new CapabilityAttributeInfo("power",  String),
        new CapabilityAttributeInfo("sound",  String),
        new CapabilityAttributeInfo("volume",  double)
    ])

    abstract void channelDown()
    abstract void channelUp()
    abstract void volumeDown()
    abstract void volumeUp()
}

interface TamperAlert extends Capability
{
    enum TamperValue
    {
        clear, detected
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("tamper", TamperAlert),
    ])
}

interface Telnet extends Capability
{
}

interface TemperatureMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            //        new CapabilityAttributeInfo("temperature", double),
    ])
}

interface TestCapability extends Capability {}

interface Thermostat extends
        ThermostatCoolingSetpoint,
        ThermostatHeatingSetpoint,
        ThermostatFanMode,
        ThermostatMode,
        ThermostatOperatingState,
        ThermostatSchedule,
        ThermostatSetpoint
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("supportedThermostatFanModes", new ArrayList<ThermostatFanModeValue>().class),
            new CapabilityAttributeInfo("supportedThermostatModes", new ArrayList<ThermostatModeValue>().class),
            new CapabilityAttributeInfo("temperature", double)
    ])
}

interface ThermostatCoolingSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("coolingSetpoint", double),
    ])

    /**
     *
     * @param temperature required (NUMBER) - Cooling setpoint in degrees
     */
    abstract void setCoolingSetpoint(double temperature)
}

interface ThermostatFanMode extends Capability
{
    enum ThermostatFanModeValue
    {
        auto, circulate, on
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("thermostatFanMode", ThermostatFanModeValue),
    ])

    abstract void fanAuto()
    abstract void fanCirculate()
    abstract void fanOn()

    abstract void setThermostatFanMode(ThermostatFanModeValue fanMode)
}

interface ThermostatHeatingSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("heatingSetpoint", double) // temperature in degrees
    ])

    /**
     *
     * @param temperature required (NUMBER) - Heating setpoint in degrees
     */
    abstract void setHeatingSetpoint(double temperature)
}

interface ThermostatMode extends Capability
{
    enum ThermostatModeValue
    {
        auto("auto"),
        off("off"),
        heat("heat"),
        emergency_heat("emergency heat"),
        coo("cool")

        ThermostatModeValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("thermostatMode", ThermostatModeValue),
    ])

    abstract void auto()
    abstract void cool()
    abstract void emergencyHeat()
    abstract void heat()
    abstract void off()

    abstract void setThermostatMode(ThermostatModeValue mode)
}

interface ThermostatOperatingState extends Capability
{
    enum ThermostatOperatingStateValue
    {
        heating("heating"),
        pending_cool("pending cool"),
        pending_heat("pending_heat"),
        vent_economizer("vent economizer"),
        idle("idle"),
        cooling("cooling"),
        fan_only("fan only")

        ThermostatOperatingStateValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("thermostatOperatingState", ThermostatOperatingStateValue),
    ])
}

interface ThermostatSchedule extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("schedule", Object), // JSON object
    ])

    abstract void setSchedule(def jsonObject)
}

interface ThermostatSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("thermostatSetpoint", double),
    ])
}

interface ThreeAxis extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("threeAxis", new Tuple3<Integer, Integer, Integer>().class),
    ])
}

interface TimedSession extends Capability
{
    enum SessionStatusValue
    {
        stopped, canceled, running, paused
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("sessionStatus", SessionStatusValue),
            new CapabilityAttributeInfo("timeRemaining", double)
    ])

    abstract void cancel()
    abstract void pause()
    abstract void setTimeRemaining(double time) // Is it NUMBER though?

    abstract void start()
    abstract void stop()
}

interface Tone extends Capability
{
    abstract void beep()
}

interface TouchSensor extends Capability
{
    enum TouchValue
    {
        touched
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("touch", TouchValue),
    ])
}

// Tv Channel

interface UltravioletIndex extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("ultravioletIndex", double, min: 0, max: 255)
    ])
}

interface Valve extends Capability
{
    enum ValveValue
    {
        open, closed
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("valve", ValveValue),
    ])

    abstract void open()
    abstract void close()
}

// Video Stream
// Video Clips

interface VideoCamera extends Capability
{
    enum CameraValue
    {
        on, off, restarting, unavailable
    }

    enum MuteValue
    {
        unmuted, muted
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("camera", CameraValue),
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("settings", Object), // JSON object
            new CapabilityAttributeInfo("statusMessage", String),
    ])

    abstract void flip()
    abstract void mute()
    abstract void off()
    abstract void on()
    abstract void unmute()
}

interface VideoCapture extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("clip", Object),
    ])

    abstract void capture(Date a, Date b, Date c)
}

interface VoltageMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("voltage", double), // In Volts
    ])
}

// Washer Mode
// Washer Operating State

interface WaterSensor extends Capability
{
    enum WaterValue
    {
        wet, dry
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("water", WaterValue),
    ])
}

interface WindowShade extends Capability
{
    enum WindowShadeValue
    {
        opening("opening"),
        partially_open("partially open"),
        closed("closed"),
        open("open"),
        closing("closing"),
        unknown("unknown")

        WindowShadeValue(String val) {
            this.val = val
        }

        private final String val

        @Override
        String toString() {
            return val
        }
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("position", double, min: 0, max: 100),
            new CapabilityAttributeInfo("windowShade", WindowShadeValue)
    ])

    abstract void close()
    abstract void open()
    abstract void setPosition(double position)
}

interface ZwMultichannel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("epEvent", String),
            new CapabilityAttributeInfo("epInfo", String),
    ])

    abstract void enableEpEvents(String a)

    abstract void epCmd(double a, String b)
}

@CustomDeviceSelector(deviceSelector = 'pHMeasurement')
@CustomDriverDefinition(driverDefinition = 'pHMeasurement')
@CustomPrettyName(prettyName = 'Ph Measurement')
interface PhMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = makeAttributes([
            new CapabilityAttributeInfo("PH", double)
    ])
}