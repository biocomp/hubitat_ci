package biocomp.hubitatCiTest.capabilities

import java.lang.invoke.SwitchPoint

class Capabilities
{
    static String toDeviceSelector(String capabilityClassName)
    {
        return capabilityClassName.uncapitalize()
    }
}

trait AccelerationSensor
{
    enum AccelerationValue
    {
        active,
        inactive
    }

    abstract AccelerationValue getAcceleration() // Called 'ActivityState' in SmartThings
}

// Deprecated in SmartThings
trait Actuator
{
}

// Only in SmartThings: trait AirConditionerMode {}
// Only in SmartThings: trait AirQualitySensor {}

trait Alarm
{
    enum AlarmValue
    {
        strobe,
        both,
        off,
        siren
    }

    abstract AlarmValue getAlarm()

    abstract both()
    abstract off()
    abstract siren()
    abstract strobe()
}

// Only in SmartThings: trait AudioMute{}

trait AudioNotification
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

trait AudioVolume
{
    enum MuteValue
    {
        unmuted,
        muted
    }

    abstract MuteValue getMute()
    abstract double getVolume()

    abstract void mute()

    /**
    * @param volumeLevel required (NUMBER) - Volume level (0 to 100)
    */
    abstract void setVolume(double volumeLevel)

    abstract void unmute()
    abstract void volumeDown()
    abstract void volumeUp()
}

trait Battery
{
    /**
     * @return 0-100% of battery charge
     */
    abstract double getBattery()
}

// Deprecated in SmartThings
trait Beacon
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

    abstract PresenceValue getPresence()
}

// SmartThings only: Bridge


trait Bulb
{
    enum SwitchValue
    {
        on,
        off
    }

    abstract SwitchValue getSwitch()

    abstract void on()
    abstract void off()
}

// Deprecated in both SmartThings and Hubitat
trait Button
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

    abstract int getButton()
    abstract HoldableButtonValue getHoldableButton()
    abstract int getNumberOfButtons()
}

trait CarbonDioxideMeasurement
{
    abstract double getCarbonDioxide()
}

trait CarbonMonoxideDetector
{
    enum CarbonMonoxideValue
    {
        detected, tested, clear
    }

    abstract CarbonMonoxideValue getCarbonMonoxide()
}

trait ChangeLevel
{
    enum DirectionValue
    {}

    /**
     * @param direction required (ENUM) - Direction for level change request
     */
    abstract void startLevelChange(DirectionValue direction)
    abstract void stopLevelChange()
}

trait Chime
{
    enum StatusValue
    {
        playing, stopped
    }

    /**
     * @return  - JSON_OBJECT
     */
    abstract def getSoundEffects()
    abstract String getSoundName()
    abstract StatusValue getStatus()

    abstract void playSound(int soundNumber)
    abstract void stop()
}

trait ColorControl
{
    abstract String getRGB()
    abstract String getColor()
    abstract String getColorName()
    abstract double getHue()
    abstract double getSaturation()

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

trait ColorMode
{
    enum ColorModeValue{
        CT, RGB
    }

    abstract ColorModeValue getColorMode()
}

trait ColorTemperature
{
    abstract String getColorName()
    abstract double getColorTemperature()
}

trait Configuration
{
    abstract void configure()
}

trait Consumable
{
    enum ConsumableStatusValue
    {
        missing, order, maintenance_required, good, replace
    }

    abstract ConsumableStatusValue getConsumableStatus()
    abstract void setConsumableStatus(ConsumableStatusValue status)
}

// SmartThings only: Color

trait ContactSensor
{
    enum ContactValue
    {
        closed, open
    }

    abstract ContactValue getContact()
}

// SmartThings only: Demand Response Load Control
// SmartThings only: Dishwasher Mode
// SmartThings only: Dishwasher Operating State

trait DoorControl
{
    enum DoorValue
    {
        unknown, closed, open, closing, opening
    }

    abstract DoorValue getDoor()

    abstract void open()
    abstract void close()
}

// SmartThings only: Dryer Mode
// SmartThings only: Dryer Operating State
// Dust Sensor

trait DoubleTapableButton
{
    abstract int getDoubleTapped()
}

trait EnergyMeter
{
    /**
     * @return energy used ing kWh
     */
    abstract double getEnergy()
}

trait EstimatedTimeOfArrival
{
    abstract Date getEta()
}

// Execute

// SmartThings calls it Fan Speed
trait FanControl
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

    abstract SpeedValue getSpeed()
    abstract void setSpeed(SpeedValue speed)
}

trait FilterStatus
{
    enum FilterStatusValue
    {
        normal, replace
    }

    abstract FilterStatusValue getFilterStatus()
}

trait GarageDoorControl
{
    enum DoorValue
    {
        unknown, open, closing, closed, opening
    }

    abstract DoorValue getDoor()

    abstract  void close()
    abstract  void open()
}

// Geolocation

trait HealthCheck
{
    abstract int getCheckInterval()

    abstract void ping()
}

trait HoldableButton
{
    abstract int getHeld()
}

trait IlluminanceMeasurement
{
    abstract double getIlluminance()
}

trait ImageCapture
{
    abstract String getImage()
    abstract void take()
}

trait Indicator
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

    abstract IndicatorStatusValue getIndicatorStatus()

    abstract void indicatorNever()
    abstract void indicatorWhenOff()
    abstract void indicatorWhenOn()
}

// Infrared Level

trait Initialize
{
    abstract void initialize()
}

trait Light
{
    enum SwitchValue { on, off }

    abstract SwitchValue getSwitch()

    abstract void on()
    abstract void off()
}

trait LightEffects
{
    abstract String getEffectName()

    /**
     * @return JSON object
     */
    abstract def getLightEffects()

    /**
     * @param effectNumber required (NUMBER) - Effect number to enable
     */
    abstract void setEffect(int effectNumber)


    abstract void setNextEffect()
    abstract void setPreviousEffect()
}

trait LocationMode
{
    /**
     * @return DYNAMIC_ENUM with mode
     */
    abstract String getMode()
}

// Lock only

trait Lock
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

    abstract LockValue getLock()

    abstract void lock()
    abstract void unlock()
}

trait LockCodes
{
    enum CodeChangedValue
    {
        added, changed, deleted, failed
    }

    abstract CodeChangedValue getCodeChanged()
    abstract int getCodeLengt()

    /**
     * @return JSON object
     */
    abstract def getLockCodes()

    abstract int getMaxCodes()

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

trait MediaController
{
    /**
     * @return JSON object with activities
     */
    abstract def getActivities()
    abstract String getCurrentActivity()
}

// Media Input Source
// Media Playback Repeat
// Media Playback Shuffle
// Media Playback
// Media Presets
// Media Track Control

trait Momentary
{
    abstract void push()
}

trait MotionSensor
{
    enum MotionValue
    {
        inactive, active
    }

    abstract MotionValue getMotion()
}

trait MusicPlayer
{
    enum MuteValue
    {
        unmuted, muted
    }

    abstract double getLevel()
    abstract MuteValue getMute()
    abstract String getStatus()

    /**
     * @return JSON object
     */
    abstract def getTrackData()
    abstract String getTrackDescription()

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
trait Notification
{
    abstract void deviceNotification(String text)
}

// Odor Sensor

trait Outlet
{
    enum SwitchValue
    {
        on, off
    }

    abstract SwitchValue getSwitch()

    abstract void off()
    abstract void on()
}

// Oven Mode
// Oven Operating State
// Oven Setpoint
// pH Measurement

// Deprecated in SmartThings
trait Polling
{
    abstract void poll()
}

// Power Consumption Report

trait PowerMeter
{
    /**
     * @return power in Watts
     */
    abstract double getPower()
}

/**
 * Gives the ability to determine the current power source of the device
 */
trait PowerSource
{
    enum PowerSourceValue
    {
        battery, dc, mains, unknown
    }

    abstract PowerSourceValue getPowerSource()
}

trait PresenceSensor
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

    abstract PresenceValue getPresence()
}

// Rapid Cooling

trait PressureMeasurement
{
    abstract double getPressure()
}

trait PushableButton
{
    abstract int numberOfButtons()
    abstract int pushed()
}

trait Refresh
{
    abstract void referesh()
}

trait RelativeHumidityMeasurement
{
    abstract double getHumidity()
}

trait RelaySwitch
{
    enum SwitchValue
    {
        on, off
    }

    abstract SwitchValue getSwitch()

    abstract void on()
    abstract void off()
}

// Robot Cleaner Cleaning Mode
// Robot Cleaner Movement
// Robot Cleaner Turbo Mode

trait ReleasableButton
{

}

trait SamsungTV
{

}

trait SecurityKeypad
{}
