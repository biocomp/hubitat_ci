package biocomp.hubitatCi.deviceMetadata

import biocomp.hubitatCi.HubitatDeviceSandbox
import biocomp.hubitatCi.validation.Flags

class DevicePreferencesReaderTest extends spock.lang.Specification
{
    private static List<DeviceInput> readPreferences(String script)
    {
        return new HubitatDeviceSandbox(script).run(validationFlags: [Flags.DontValidateDefinition]).producedPreferences
    }

    def "Reading complex configuration with setting additional unrelated state should apparently work"()
    {
        setup:
            HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox("""
metadata {
    preferences() {
        datetimeFormat = 1
        sourceastronomy = 1
        extSource = 0
        is_day = 0
        sourceImg = true 
        iconStore = "https://github.com/Scottma61/WeatherIcons/blob/master/"
        section("Query Inputs"){
            input "extSource", "enum", title: "Select External Source", required:true, defaultValue: "None", options: [1:"None", 2:"WeatherUnderground", 3:"Apixu", 4:"DarkSky"]
            input "pollIntervalStation", "enum", title: "Station Poll Interval", required: true, defaultValue: "3 Hours", options: ["Manual Poll Only", "5 Minutes", "10 Minutes", "15 Minutes", "30 Minutes", "1 Hour", "3 Hours"]
            input "stationLatHemi", "enum", title: "Station Northern or Southern hemisphere?", required: true, defaultValue: "North", options: ["North", "South"]
            input "stationLongHemi", "enum", title: "Station East or West of GMT (London, UK)?", required: true, defaultValue: "West", options: ["West", "East"]
            input "pollLocationStation", "text", required: true, title: "Station Data File Location:", defaultValue: "http://"
            LOGINFO("extSource: \${extSource}")
            if(extSource.toInteger() != 1){
                input "apiKey", "text", required: true, title: "API Key"
                input "pollIntervalForecast", "enum", title: "External Source Poll Interval", required: true, defaultValue: "3 Hours", options: ["Manual Poll Only", "5 Minutes", "10 Minutes", "15 Minutes", "30 Minutes", "1 Hour", "3 Hours"]
                input "pollLocationForecast", "text", required: true, title: "ZIP Code or Location"
\t\t\t\tLOGINFO("pollLocationForecast: \${pollLocationForecast}")                
                input "sourceImg", "bool", required: true, defaultValue: false, title: "Icons from: On = Standard - Off = Alternative"
                input "iconStore", "text", required: true, defaultValue: "https://github.com/Scottma61/WeatherIcons/blob/master/", title: "Alternative Icon Location:"
            }
            input "iconType", "bool", title: "Condition Icon: On = Current - Off = Forecast", required: true, defaultValue: false
\t    \tinput "tempFormat", "enum", required: true, defaultValue: "Fahrenheit (°F)", title: "Display Unit - Temperature: Fahrenheit (°F) or Celsius (°C)",  options: ["Fahrenheit (°F)", "Celsius (°C)"]
            input "datetimeFormat", "enum", required: true, defaultValue: "m/d/yyyy 12 hour (am|pm)", title: "Display Unit - Date-Time Format",  options: [1:"m/d/yyyy 12 hour (am|pm)", 2:"m/d/yyyy 24 hour", 3:"mm/dd/yyyy 12 hour (am|pm)", 4:"mm/dd/yyyy 24 hour", 5:"d/m/yyyy 12 hour (am|pm)", 6:"d/m/yyyy 24 hour", 7:"dd/mm/yyyy 12 hour (am|pm)", 8:"dd/mm/yyyy 24 hour", 9:"yyyy/mm/dd 24 hour"]
            input "distanceFormat", "enum", required: true, defaultValue: "Miles (mph)", title: "Display Unit - Distance/Speed: Miles or Kilometres",  options: ["Miles (mph)", "Kilometers (kph)"]
            input "pressureFormat", "enum", required: true, defaultValue: "Inches", title: "Display Unit - Pressure: Inches or Millibar",  options: ["Inches", "Millibar"]
            input "rainFormat", "enum", required: true, defaultValue: "Inches", title: "Display Unit - Precipitation: Inches or Millimetres",  options: ["Inches", "Millimetres"]
            input "summaryType", "bool", title: "Full Weather Summary", required: true, defaultValue: false
            input "logSet", "bool", title: "Create extended Logging", required: true, defaultValue: false
            

            input "sourcefeelsLike", "bool", required: true, title: "Feelslike from Weather-Display?", defaultValue: true
\t\t    input "sourceIllumination", "bool", required: true, title: "Illuminance from Weather-Display?", defaultValue: true
            input "sourceUV", "bool", required: true, title: "UV from Weather-Display?", defaultValue: true
            input "sourceastronomy", "enum", required: true, defaultValue: "Sunrise-Sunset.org", title: "Astronomy Source:", options: [1:"Sunrise-Sunset.org", 2:"Weather-Display", 3:"WeatherUnderground.com", 4:"APIXU.com", 5:"DarkSky.net"]
        }
    }
}


def LOGINFO(txt){
    try {
        if(state.logSet == true){log.info("Weather-Display Driver - INFO:  \${txt}") }
    } catch(ex) {
        log.error("LOGINFO unable to output requested data!")
    }
}
""")

        expect:
            sandbox.run(validationFlags: [Flags.DontValidateDefinition])
    }

    def "preferences() with no inputs work"()
    {
        expect:
            readPreferences("""
metadata{
    preferences()
}
""").size() == 0
    }
}

