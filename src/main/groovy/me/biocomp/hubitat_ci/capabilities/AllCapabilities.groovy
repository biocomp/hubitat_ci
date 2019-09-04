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
    @CompileStatic
    enum AccelerationValue
    {
        active,
        inactive
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("acceleration", AccelerationValue) // Called 'ActivityState' in SmartThings
    ])
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
    @CompileStatic
    enum AlarmValue
    {
        strobe,
        both,
        off,
        siren
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("alarm", AlarmValue)
    ])

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
    @CompileStatic
    enum MuteValue
    {
        unmuted,
        muted
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("volume", Double)
    ])

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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("battery", Double, min: 0, max: 100) // 0-100% battery charge
    ])
}

// Deprecated in SmartThings
interface Beacon extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("presence", PresenceValue)
    ])
}

// SmartThings only: Bridge


interface Bulb extends Capability
{
    @CompileStatic
    enum SwitchValue
    {
        on,
        off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("switch", SwitchValue)
    ])

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

// Deprecated in both SmartThings and Hubitat
interface Button extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("button", Number),
            new CapabilityAttributeInfo("holdableButton", HoldableButtonValue),
            new CapabilityAttributeInfo("numberOfButtons", Number),
    ])
}

interface CarbonDioxideMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("carbonDioxide", Double)
    ])
}

interface CarbonMonoxideDetector extends Capability
{
    @CompileStatic
    enum CarbonMonoxideValue
    {
        detected, tested, clear
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("carbonMonoxide", CarbonMonoxideValue)
    ])
}

@CompileStatic
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
    @CompileStatic
    enum StatusValue
    {
        playing, stopped
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("soundEffects", Object), // JSON_OBJECT
            new CapabilityAttributeInfo("soundName", String),
            new CapabilityAttributeInfo("status", StatusValue),
    ])

    @CompileStatic
    abstract void playSound(Number soundNumber)

    @CompileStatic
    abstract void stop()
}

interface ColorControl extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
        new CapabilityAttributeInfo("rGB", String),
        new CapabilityAttributeInfo("color", String),
        new CapabilityAttributeInfo("colorName", String),
        new CapabilityAttributeInfo("hue", Double),
        new CapabilityAttributeInfo("saturation", Double),
    ])


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
    @CompileStatic
    enum ColorModeValue{
        CT, RGB
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("colorMode", ColorModeValue),
    ])
}

interface ColorTemperature extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("colorName", String),
            new CapabilityAttributeInfo("colorTemperature", Double)
    ])
}

@CompileStatic
interface Configuration extends Capability
{
    abstract void configure()
}

interface Consumable extends Capability
{
    @CompileStatic
    enum ConsumableStatusValue
    {
        missing, order, maintenance_required, good, replace
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
        new CapabilityAttributeInfo("consumableStatus", ConsumableStatusValue),
    ])

    @CompileStatic
    abstract void setConsumableStatus(ConsumableStatusValue status)
}

// SmartThings only: Color

interface ContactSensor extends Capability
{
    @CompileStatic
    enum ContactValue
    {
        closed, open
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("contact", ContactValue),
    ])

}

// SmartThings only: Demand Response Load Control
// SmartThings only: Dishwasher Mode
// SmartThings only: Dishwasher Operating State

interface DoorControl extends Capability
{
    @CompileStatic
    enum DoorValue
    {
        unknown, closed, open, closing, opening
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("door", DoorValue),
    ])

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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("doubleTapped", Number),
    ])
}

interface EnergyMeter extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("energy", Double), // in kWh
    ])
}

interface EstimatedTimeOfArrival extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("eta", Date),
    ])
}

// Execute

// SmartThings calls it Fan Speed
interface FanControl extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("speed", SpeedValue),
    ])


    @CompileStatic
    abstract void setSpeed(SpeedValue speed)
}

interface FilterStatus extends Capability
{
    @CompileStatic
    enum FilterStatusValue
    {
        normal, replace
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("filterStatus", FilterStatusValue),
    ])
}

interface GarageDoorControl extends Capability
{
    @CompileStatic
    enum DoorValue
    {
        unknown, open, closing, closed, opening
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("door", DoorValue),
    ])

    @CompileStatic
    abstract  void close()

    @CompileStatic
    abstract  void open()
}

// Geolocation

interface HealthCheck extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("checkInterval", Number),
    ])

    @CompileStatic
    abstract void ping()
}

interface HoldableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("held", Number),
    ])
}

interface IlluminanceMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("illuminance", Double),
    ])
}

interface ImageCapture extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("image", String),
    ])

    @CompileStatic
    abstract void take()
}

interface Indicator extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("indicatorStatus", IndicatorStatusValue),
    ])

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
    abstract void initialize()
}

interface Light extends Capability
{
    @CompileStatic
    enum SwitchValue { on, off }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("switch", SwitchValue)
    ])

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

interface LightEffects extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("effectName", String),
            new CapabilityAttributeInfo("lightEffects", Object) // JSON object
    ])

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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("mode", String), // DYNAMIC_ENUM with mode
    ])
}

// Lock only

interface Lock extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("lock", LockValue),
    ])

    @CompileStatic
    abstract void lock()

    @CompileStatic
    abstract void unlock()
}

interface LockCodes extends Capability
{
    @CompileStatic
    enum CodeChangedValue
    {
        added, changed, deleted, failed
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("codeChanged", CodeChangedValue),
            new CapabilityAttributeInfo("codeLength", Number),
            new CapabilityAttributeInfo("lockCodes", Object), // JSON object
            new CapabilityAttributeInfo("maxCodes", Number)
    ])

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
     * @param pinCodeLength required (NUMBER) - Maximum pin code lentgh for this lock
     */
    @CompileStatic
    abstract void setCodeLength(Number pinCodeLength)
}

interface MediaController extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
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

@CompileStatic
interface Momentary extends Capability
{
    abstract void push()
}

interface MotionSensor extends Capability
{
    @CompileStatic
    enum MotionValue
    {
        inactive, active
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            //        new CapabilityAttributeInfo("motion", MotionValue),
    ])
}

interface MusicPlayer extends Capability
{
    @CompileStatic
    enum MuteValue
    {
        unmuted, muted
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("level", Double),
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("status", String),
            new CapabilityAttributeInfo("trackData", Object), // JSON object
            new CapabilityAttributeInfo("trackDescription", String)
    ])

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
    @CompileStatic
    enum SwitchValue
    {
        on, off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("switch", SwitchValue),
    ])

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

interface PowerMeter extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("power", Double), // In Watts
    ])
}

/**
 * Gives the ability to determine the current power source of the device
 */
interface PowerSource extends Capability
{
    @CompileStatic
    enum PowerSourceValue
    {
        battery, dc, mains, unknown
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("powerSource", PowerSourceValue),
    ])
}

interface PresenceSensor extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("presense", PresenceValue),
    ])

}

// Rapid Cooling

interface PressureMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("pressure", Double),
    ])
}

interface PushableButton extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("numberOfButtons", Number),
            new CapabilityAttributeInfo("pushed", Number)
    ])
}

@CompileStatic
interface Refresh extends Capability
{
    abstract void refresh()
}

interface RelativeHumidityMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("humidity", Double),
    ])
}

interface RelaySwitch extends Capability
{
    @CompileStatic
    enum SwitchValue
    {
        on, off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("switch", SwitchValue),
    ])

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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("released", Number),
    ])
}

@CustomDeviceSelector(deviceSelector = 'samsungTV')
@CustomPrettyName(prettyName = 'Samsung TV')
interface SamsungTV extends Capability
{
    @CompileStatic
    enum MuteValue
    {
        muted, unknown, unmuted
    }

    @CompileStatic
    enum PictureModeValue
    {
        unknown, standard, movie, dynamic
    }

    @CompileStatic
    enum SoundModeValue
    {
        speech, movie, unknown, standard, music
    }

    @CompileStatic
    enum SwitchValue
    {
        on, off
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("messageButton", Object), // JSON object
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("pictureMode", PictureModeValue),
            new CapabilityAttributeInfo("soundMode", SoundModeValue),
            new CapabilityAttributeInfo("switch", SwitchValue),
            new CapabilityAttributeInfo("volume", Double)
    ])

    @CompileStatic
    abstract void mute()

    @CompileStatic
    abstract void off()

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void setPictureMode(PictureModeValue mode)

    @CompileStatic
    abstract void setSoundMode(SoundModeValue mode)

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
    @CompileStatic
    enum CodeChangedValue
    {
        added, changed, deleted, failed
    }

    @CompileStatic
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



    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("codeChanged", CodeChangedValue),
            new CapabilityAttributeInfo("codeLength", Number),
            new CapabilityAttributeInfo("lockCodes", Object), // JSON object
            new CapabilityAttributeInfo("maxCodes", Number),
            new CapabilityAttributeInfo("securityKeypad", SecurityKeypadValue),
    ])

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
     * @param pinCodeLength required (NUMBER) - Maximum pin code lentgh for this keypad
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
{

}

interface ShockSensor extends Capability
{
    @CompileStatic
    enum ShockValue
    {
        clear, detected
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("shock", ShockValue),
    ])
}

@CompileStatic
interface SignalStrength extends Capability
{
    /**
     *
     * @return 0 - 255 value
     */
    abstract Double lqi()

    /**
     * @return -200 - 0 value
     */
    abstract Double rssi()
}

interface SleepSensor extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("sleeping", SleepingValue),
    ])
}

interface SmokeDetector extends Capability
{
    @CompileStatic
    enum SmokeValue
    {
        clear, tested, detected
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("smoke", SmokeValue),
    ])
}

interface SoundPressureLevel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("soundPressureLevel", Double),
    ])
}

interface SoundSensor extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("sound", SoundValue),
    ])
}

interface SpeechRecognition extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("phraseSpoken", String),
    ])
}

@CompileStatic
interface SpeechSynthesis extends Capability
{
    abstract void speak(String text)
}

interface StepSensor extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("goal", Double),
            new CapabilityAttributeInfo("steps", Double)
    ])
}

interface Switch extends Capability
{
    @CompileStatic
    enum SwitchValue { on, off }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("switch", SwitchValue)
    ])

    @CompileStatic
    abstract void on()

    @CompileStatic
    abstract void off()
}

interface SwitchLevel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("level", Double, min: 0, max: 100)
    ])

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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
        new CapabilityAttributeInfo("channel",  Number),
        new CapabilityAttributeInfo("movieMode",  String),
        new CapabilityAttributeInfo("picture",  String),
        new CapabilityAttributeInfo("power",  String),
        new CapabilityAttributeInfo("sound",  String),
        new CapabilityAttributeInfo("volume",  Double)
    ])

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
    @CompileStatic
    enum TamperValue
    {
        clear, detected
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("tamper", TamperValue),
    ])
}

@CompileStatic
interface Telnet extends Capability
{
}

interface TemperatureMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            //        new CapabilityAttributeInfo("temperature", Double),
    ])
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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("supportedThermostatFanModes", "STRING", new ArrayList<ThermostatFanMode.ThermostatFanModeValue>().class),
            new CapabilityAttributeInfo("supportedThermostatModes", "STRING", new ArrayList<ThermostatMode.ThermostatModeValue>().class),
            new CapabilityAttributeInfo("temperature", Double)
    ])
}

@CompileStatic
interface ThermostatCoolingSetpoint extends Capability
{
    static def _internalAttributes = CapabilityAttributeInfo.makeList {
        attribute("coolingSetpoint", Number)
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
    @CompileStatic
    enum ThermostatFanModeValue
    {
        auto, circulate, on
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("thermostatFanMode", ThermostatFanModeValue),
    ])

    @CompileStatic
    abstract void fanAuto()

    @CompileStatic
    abstract void fanCirculate()

    @CompileStatic
    abstract void fanOn()

    @CompileStatic
    abstract void setThermostatFanMode(ThermostatFanModeValue fanMode)
}

interface ThermostatHeatingSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("heatingSetpoint", Number) // temperature in degrees
    ])

    /**
     *
     * @param temperature required (NUMBER) - Heating setpoint in degrees
     */
    @CompileStatic
    abstract void setHeatingSetpoint(Number temperature)
}

interface ThermostatMode extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("thermostatMode", ThermostatModeValue),
    ])

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
    abstract void setThermostatMode(ThermostatModeValue mode)
}

interface ThermostatOperatingState extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("thermostatOperatingState", ThermostatOperatingStateValue),
    ])
}

interface ThermostatSchedule extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("schedule", Object), // JSON object
    ])

    @CompileStatic
    abstract void setSchedule(def jsonObject)
}

interface ThermostatSetpoint extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("thermostatSetpoint", Number),
    ])
}

interface ThreeAxis extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("threeAxis", String.class), // Seems like string in "x,y,z" format
    ])
}

interface TimedSession extends Capability
{
    @CompileStatic
    enum SessionStatusValue
    {
        stopped, canceled, running, paused
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("sessionStatus", SessionStatusValue),
            new CapabilityAttributeInfo("timeRemaining", Number)
    ])

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
    @CompileStatic
    enum TouchValue
    {
        touched
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("touch", TouchValue),
    ])
}

// Tv Channel

interface UltravioletIndex extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("ultravioletIndex", Number, min: 0, max: 255)
    ])
}

interface Valve extends Capability
{
    @CompileStatic
    enum ValveValue
    {
        open, closed
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("valve", ValveValue),
    ])

    @CompileStatic
    abstract void open()

    @CompileStatic
    abstract void close()
}

// Video Stream
// Video Clips

interface VideoCamera extends Capability
{
    @CompileStatic
    enum CameraValue
    {
        on, off, restarting, unavailable
    }

    @CompileStatic
    enum MuteValue
    {
        unmuted, muted
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("camera", CameraValue),
            new CapabilityAttributeInfo("mute", MuteValue),
            new CapabilityAttributeInfo("settings", Object), // JSON object
            new CapabilityAttributeInfo("statusMessage", String),
    ])

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

interface VideoCapture extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("clip", Object),
    ])

    @CompileStatic
    abstract void capture(Date a, Date b, Date c)
}

interface VoltageMeasurement extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("voltage", Number), // In Volts
    ])
}

// Washer Mode
// Washer Operating State

interface WaterSensor extends Capability
{
    @CompileStatic
    enum WaterValue
    {
        wet, dry
    }

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("water", WaterValue),
    ])
}

interface WindowShade extends Capability
{
    @CompileStatic
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

    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("position", Number, min: 0, max: 100),
            new CapabilityAttributeInfo("windowShade", WindowShadeValue)
    ])

    @CompileStatic
    abstract void close()

    @CompileStatic
    abstract void open()

    @CompileStatic
    abstract void setPosition(Number position)
}

interface ZwMultichannel extends Capability
{
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("epEvent", String),
            new CapabilityAttributeInfo("epInfo", String),
    ])

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
    static Map<String, CapabilityAttributeInfo> _internalAttributes = CapabilityAttributeInfo.makeList([
            new CapabilityAttributeInfo("PH", Number)
    ])
}