package me.biocomp.hubitat_ci.capabilities

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

import java.lang.annotation.Annotation
import java.lang.reflect.Method

@TypeChecked
class Capabilities
{
    @CompileStatic
    private static HashMap<String, CapabilityAttributeInfo> readAttributesFromInterface(Class interface_)
    {
        HashMap<String, CapabilityAttributeInfo> result = [:]

        try {
            ((Map<String, CapabilityAttributeInfo>) interface_.getField("_internalAttributes").get(null)).each {
                result.put(it.key, it.value)
            }
        }
        catch (NoSuchFieldException e)
        {
            // This is fine, not all capabilities have attributes
        }

        return result
    }

    @CompileStatic
    static HashMap<String, CapabilityAttributeInfo> readAttributes(Class capability)
    {
        HashMap<String, CapabilityAttributeInfo> result = [:]

        capability.interfaces.each{
            result.putAll(readAttributesFromInterface(it))
        }
        result.putAll(readAttributesFromInterface(capability))

        return result
    }

    @CompileStatic
    static List<Method> readMethods(Class capability)
    {
        return capability.methods as List
    }

    static String toDeviceSelector(String capabilityClassName)
    {
        return getCapabilityDeviceSelector(Class.forName(capabilityClassName))
    }

    private static Annotation getAnnotation(Class c, Class annotation)
    {
        return c.annotations.find{it.annotationType() == annotation}
    }

    static String getCapabilityDeviceSelector(Class c)
    {
        def val = getAnnotation(c, CustomDeviceSelector)
        
        if (val)
        {
            def annotation = val as CustomDeviceSelector
            return annotation.deviceSelector()
        }
        else
        {
            return c.simpleName.uncapitalize()
        }
    }

    static String getDriverDefinition(Class c)
    {
        def val = getAnnotation(c, CustomDriverDefinition)
        
        if (val)
        {
            def annotation = val as CustomDriverDefinition
            return annotation.driverDefinition()
        }
        else
        {
            return c.simpleName
        }
    }

    static String getCapabilityPrettyName(Class c)
    {
        def val = getAnnotation(c, CustomPrettyName)
        if (val)
        {
            def annotation = val as CustomPrettyName
            return annotation.prettyName()
        }
        else {
            return c.simpleName.split(/(?=[A-Z])/).join(' ')
        }
    }

    private static HashMap<String, Class> makeCapabilities()
    {
        HashMap<String, Class> capabilities = [:]

        [AccelerationSensor,
         Actuator,
         Alarm,
         AudioNotification,
         AudioVolume,
         Battery,
         Beacon,
         Bulb,
         Button,
         CarbonDioxideMeasurement,
         CarbonMonoxideDetector,
         ChangeLevel,
         Chime,
         ColorControl,
         ColorMode,
         ColorTemperature,
         Configuration,
         Consumable,
         ContactSensor,
         DoorControl,
         DoubleTapableButton,
         EnergyMeter,
         EstimatedTimeOfArrival,
         FanControl,
         FilterStatus,
         GarageDoorControl,
         HealthCheck,
         HoldableButton,
         IlluminanceMeasurement,
         ImageCapture,
         Indicator,
         Initialize,
         Light,
         LightEffects,
         LocationMode,
         Lock,
         LockCodes,
         MediaController,
         Momentary,
         MotionSensor,
         MusicPlayer,
         Notification,
         Outlet,
         Polling,
         PowerMeter,
         PowerSource,
         PresenceSensor,
         PressureMeasurement,
         PushableButton,
         Refresh,
         RelativeHumidityMeasurement,
         RelaySwitch,
         ReleasableButton,
         SamsungTV,
         SecurityKeypad,
         Sensor,
         ShockSensor,
         SignalStrength,
         SleepSensor,
         SmokeDetector,
         SoundPressureLevel,
         SoundSensor,
         SpeechRecognition,
         SpeechSynthesis,
         StepSensor,
         Switch,
         SwitchLevel,
         TV,
         TamperAlert,
         Telnet,
         TemperatureMeasurement,
         TestCapability,
         Thermostat,
         ThermostatCoolingSetpoint,
         ThermostatFanMode,
         ThermostatHeatingSetpoint,
         ThermostatMode,
         ThermostatOperatingState,
         ThermostatSchedule,
         ThermostatSetpoint,
         ThreeAxis,
         TimedSession,
         Tone,
         TouchSensor,
         UltravioletIndex,
         Valve,
         VideoCamera,
         VideoCapture,
         VoltageMeasurement,
         WaterSensor,
         WindowShade,
         ZwMultichannel,
         PhMeasurement
        ].each{
            assert !capabilities.put(getCapabilityDeviceSelector(it), it)
        }

        return capabilities
    }

    static final HashMap<String, Class> capabilitiesByDeviceSelector = makeCapabilities()
    static final HashMap<String, Class> capabilitiesByDriverDefinition = makeCapabilities().values().collectEntries{[(getDriverDefinition(it)): it ]} as HashMap<String, Class>
    static final HashMap<String, Class> capabilitiesByPrettyName = makeCapabilities().values().collectEntries{[(getCapabilityPrettyName(it)): it ]} as HashMap<String, Class>

    /**
     * Searches capability in capabilitiesByPrettyName and capabilitiesByDriverDefinition.
     * @param prettyNameOrDriverDefinition
     * @return Class of capability, if found. {@code null} if not.
     */
    static Class findCapabilityByName(String prettyNameOrDriverDefinition) {
        return capabilitiesByPrettyName.containsKey(prettyNameOrDriverDefinition) ? capabilitiesByPrettyName.get(prettyNameOrDriverDefinition) : Capabilities.capabilitiesByDriverDefinition.get(prettyNameOrDriverDefinition)
    }
}