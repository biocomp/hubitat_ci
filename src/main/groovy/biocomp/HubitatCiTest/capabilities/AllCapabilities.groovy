package biocomp.hubitatCiTest.capabilities

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

    abstract AccelerationValue getAcceleration() // Called 'ActivityState' in SmartThings
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

    abstract AlarmValue getAlarm()

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

interface Battery extends Capability
{
    /**
     * @return 0-100% of battery charge
     */
    abstract double getBattery()
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

    abstract PresenceValue getPresence()
}

// SmartThings only: Bridge


interface Bulb extends Capability
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

    abstract int getButton()
    abstract HoldableButtonValue getHoldableButton()
    abstract int getNumberOfButtons()
}

interface CarbonDioxideMeasurement extends Capability
{
    abstract double getCarbonDioxide()
}

interface CarbonMonoxideDetector extends Capability
{
    enum CarbonMonoxideValue
    {
        detected, tested, clear
    }

    abstract CarbonMonoxideValue getCarbonMonoxide()
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

    /**
     * @return  - JSON_OBJECT
     */
    abstract def getSoundEffects()
    abstract String getSoundName()
    abstract StatusValue getStatus()

    abstract void playSound(int soundNumber)
    abstract void stop()
}

interface ColorControl extends Capability
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

interface ColorMode extends Capability
{
    enum ColorModeValue{
        CT, RGB
    }

    abstract ColorModeValue getColorMode()
}

interface ColorTemperature extends Capability
{
    abstract String getColorName()
    abstract double getColorTemperature()
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

    abstract ConsumableStatusValue getConsumableStatus()
    abstract void setConsumableStatus(ConsumableStatusValue status)
}

// SmartThings only: Color

interface ContactSensor extends Capability
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

interface DoorControl extends Capability
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

interface DoubleTapableButton extends Capability
{
    abstract int getDoubleTapped()
}

interface EnergyMeter extends Capability
{
    /**
     * @return energy used ing kWh
     */
    abstract double getEnergy()
}

interface EstimatedTimeOfArrival extends Capability
{
    abstract Date getEta()
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

    abstract SpeedValue getSpeed()
    abstract void setSpeed(SpeedValue speed)
}

interface FilterStatus extends Capability
{
    enum FilterStatusValue
    {
        normal, replace
    }

    abstract FilterStatusValue getFilterStatus()
}

interface GarageDoorControl extends Capability
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

interface HealthCheck extends Capability
{
    abstract int getCheckInterval()

    abstract void ping()
}

interface HoldableButton extends Capability
{
    abstract int getHeld()
}

interface IlluminanceMeasurement extends Capability
{
    abstract double getIlluminance()
}

interface ImageCapture extends Capability
{
    abstract String getImage()
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

    abstract IndicatorStatusValue getIndicatorStatus()

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

    abstract SwitchValue getSwitch()

    abstract void on()
    abstract void off()
}

interface LightEffects extends Capability
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

interface LocationMode extends Capability
{
    /**
     * @return DYNAMIC_ENUM with mode
     */
    abstract String getMode()
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

    abstract LockValue getLock()

    abstract void lock()
    abstract void unlock()
}

interface LockCodes extends Capability
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

interface MediaController extends Capability
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

    abstract MotionValue getMotion()
}

interface MusicPlayer extends Capability
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

    abstract SwitchValue getSwitch()

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
    /**
     * @return power in Watts
     */
    abstract double getPower()
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

    abstract PowerSourceValue getPowerSource()
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

    abstract PresenceValue getPresence()
}

// Rapid Cooling

interface PressureMeasurement extends Capability
{
    abstract double getPressure()
}

interface PushableButton extends Capability
{
    abstract int numberOfButtons()
    abstract int pushed()
}

interface Refresh extends Capability
{
    abstract void referesh()
}

interface RelativeHumidityMeasurement extends Capability
{
    abstract double getHumidity()
}

interface RelaySwitch extends Capability
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

interface ReleasableButton extends Capability
{
    abstract int getReleased()
}

@CustomDeviceSelector(deviceSelector = 'samsungTV')
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

    /**
     * @return JSON
     */
    abstract def getMessageButton()
    abstract MuteValue getMute()
    abstract PictureModeValue getPictureMode()
    abstract SoundModeValue getSoundMode()
    abstract SwitchValue getSwitch()
    abstract double getVolume()


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

    abstract CodeChangedValue codeChanged()
    abstract int codeLength()

    /**
     * @return JSON_OBJECT
     */
    abstract def lockCodes()
    abstract int maxCodes()
    abstract SecurityKeypadValue securityKeypad()


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

    abstract ShockSensor getShock()
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

    abstract SleepingValue getSleeping()
}

interface SmokeDetector extends Capability
{
    enum SmokeValue
    {
        clear, tested, detected
    }

    abstract SmokeValue getSmoke()
}

interface SoundPressureLevel extends Capability
{
    abstract double getSoundPressureLevel()
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

    abstract SoundValue getSound()
}

interface SpeechRecognition extends Capability
{
    abstract String getPhraseSpoken()
}

interface SpeechSynthesis extends Capability
{
    abstract void speak(String text)
}

interface StepSensor extends Capability
{
    abstract double getGoal()
    abstract double getSteps()
}

interface Switch extends Capability
{
    enum SwitchValue { on, off }

    abstract SwitchValue getSwitch()

    abstract void on()
    abstract void off()
}

interface SwitchLevel extends Capability
{
    /**
     * @return 0-100
     */
    abstract double getLevel()

    /**
    * @param level required (NUMBER) - Level to set (0 to 100)
    * @param duration optional (NUMBER) - Transition duration in seconds
    */
    abstract void setLevel(double level, int duration)
}

@CustomDeviceSelector(deviceSelector = 'tv')
interface TV extends Capability
{
    abstract int getChannel()
    abstract String getMovieMode()
    abstract String getPicture()
    abstract String getPower()
    abstract String getSound()
    abstract double getVolume()

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

    abstract TamperAlert getTamper()
}

interface Telnet extends Capability
{
}

interface TemperatureMeasurement extends Capability
{
    abstract double getTemperature()
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
    abstract List<ThermostatFanModeValue> getSupportedThermostatFanModes()
    abstract List<ThermostatModeValue> getSupportedThermostatModes()
    abstract double getTemperature()
}

interface ThermostatCoolingSetpoint extends Capability
{
    /**
     *
     * @return temperature in degrees
     */
    abstract double getCoolingSetpoint()

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

    abstract ThermostatFanModeValue getThermostatFanMode()

    abstract void fanAuto()
    abstract void fanCirculate()
    abstract void fanOn()

    abstract void setThermostatFanMode(ThermostatFanModeValue fanMode)
}

interface ThermostatHeatingSetpoint extends Capability
{
    /**
     *
     * @return temperature in degrees
     */
    abstract double getHeatingSetpoint()

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

    abstract ThermostatModeValue getThermostatMode()

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

    abstract ThermostatOperatingStateValue getThermostatOperatingState()
}

interface ThermostatSchedule extends Capability
{
    /**
     * @return JSON
     */
    abstract def getSchedule()

    abstract void setSchedule(def jsonObject)
}

interface ThermostatSetpoint extends Capability
{
    abstract double getThermostatSetpoint()
}

interface ThreeAxis extends Capability
{
    abstract Tuple3<Integer, Integer, Integer> getThreeAxis()
}

interface TimedSession extends Capability
{
    enum SessionStatusValue
    {
        stopped, canceled, running, paused
    }

    abstract SessionStatusValue getSessionStatus()
    abstract double getTimeRemaining()

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

    abstract TouchValue getTouch()
}

// Tv Channel

interface UltravioletIndex extends Capability
{
    /**
     * @return 0 - 255
     */
    abstract double getUltravioletIndex()
}

interface Valve extends Capability
{
    enum ValveValue
    {
        open, closed
    }

    abstract ValveValue getValve()

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

    abstract CameraValue getCamera()
    abstract MuteValue getMute()

    /**
     * @return JSON object
     */
    abstract def getSettings()
    abstract String getStatusMessage()

    abstract void flip()
    abstract void mute()
    abstract void off()
    abstract void on()
    abstract void unmute()
}

interface VideoCapture extends Capability
{
    abstract def getClip()

    abstract void capture(Date a, Date b, Date c)
}

interface VoltageMeasurement extends Capability
{
    /**
     * @return voltage in Volts
     */
    abstract double getVolage()
}

// Washer Mode
// Washer Operating State

interface WaterSensor extends Capability
{
    enum WaterValue
    {
        wet, dry
    }

    abstract WaterValue getWater()
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

    abstract double getPosition()
    abstract WindowShadeValue getWindowShade()

    abstract void close()
    abstract void open()

    /**
     *
     * @param position 0 - 100
     * @return
     */
    abstract void setPosition(double position)
}

interface ZwMultichannel extends Capability
{
    abstract String getEpEvent()
    abstract String getEpInfo()

    abstract void enableEpEvents(String a)

    abstract void epCmd(double a, String b)
}

@CustomDeviceSelector(deviceSelector = 'pHMeasurement')
@CustomDriverDefinition(driverDefinition = 'pHMeasurement')
interface PhMeasurement extends Capability
{
    abstract double getPH()
}