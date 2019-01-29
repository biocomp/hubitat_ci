/*
 *  Weather-Display With External Forecast Driver
 *
 *  Copyright 2018 @Matthew (Scottma61)
 *
 *  Many people contributed to the creation of this driver.  Significant contributors include
 *  @Cobra who adapted it from @mattw01's work and I thank them for that!  A large 'Thank you' 
 *  to @bangali for his APIXU.COM base code that much of this was adapted from. Also from @bangali
 *  is the Sunrise-Sunset.org code used to calculate illuminance/lux.  I learned a lot
 *  from his work and incorporated a lot of that here.  @bangali also contributed the icon work from
 *  https://github.com/jebbett for new cooler 'Alternative' weather icons with icons courtesy
 *  of https://www.deviantart.com/vclouds/art/VClouds-Weather-Icons-179152045.
 *
 *  With all of that collaboration I have heavily modified/created new code myself @Matthew (Scottma61)
 *  with lots of help from the Hubitat community.  This driver is free to use.  I do not accept donations.
 *  Please feel free to contribute to those mentioned here if you like this work, as it would not have
 *  possible without them.
 *
 *  *** PLEASE NOTE: You should download and store these 'Alternative' icons on your own server and
 *  change the reference to that location in the driver. There is no assurance that those icon files will
 *  remain in my github repository.    ***
 *
 *  This driver is intended to pull data from data files on a web server created by Weather-Display software
 *  (http://www.weather-display.com).  It will also supplement forecast data from  your choice of
 *  WeatherUnderground (WU)(http://www.wunderground.com) or APIXU.com (XU), but not both simultaneouly. 
 *  You will need your API keys for each of those APIs to use the forecast from those sites, but it will work
 *  without either too.
 *
 *  The driver uses the Weather-Display data as the primary dataset.  There are a few options you can select
 *  from like using your forecast source for illuminance/solar radiation/lux if you do not have those sensors.
 *  You can also select to use a base set of condition icons from the forecast source, or an 'alternative'
 *  (fancier) set.  The base set will be from WeatherUnderground if you choose eith er 'None' or 'WeatherUnderground'
 *  as your forecast source, or from APIXU.com if you choose APIXU as your forecast source.  You may choose the
 *  fancier 'Alternative' icon set if you have a forecast source other than 'None'.
 *
 *  The driver exposes both metric and imperial measurements for you to select from.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Last Update 01/06/2019
 * { Left room below to document version changes...}
 *
 *  
 *  
 *  
 *  V2.1.4 - Bug fix for Dark Sky condition_code/text/icon values; Added alerts to myTile      - 01/06/2019
 *  V2.1.3 - Code cleanup/correction - no functionality changes                                - 01/05/2019
 *  V2.1.2 - Added DarkSky as an external forecast source                                      - 01/04/2019
 *  V2.1.1 - Added Apparent temp ('Feels Like') to myTile                                      - 12/31/2018
 *  V2.1.0 - Tweaked myTile attribute. Removed "isStateChange:true" from sendEvents            - 12/30/2018
 *  V2.0.9 - Added a variation of @arnb's myTile attribute                                     - 12/9/2018
 *  V2.0.8 - Declared attributes: alert, twilight_begin, twilight_end, weatherSummary          - 11/3/2018
 *  V2.0.7 - Changed sunrise-sunset.org api from https: to http:                               - 9/21/2018
 *  V2.0.6 - More cleanup of table lookups/translations.                                       - 9/03/2018
 *  V2.0.5 - Consolidated table lookups (transform.field), cleaned up forecast translations    - 8/20/2016
 *  V2.0.4 - Translated forecastIcon for APIXU to WU equivalent                                - 8/16/2018
 *  V2.0.3 - Added forecastIcon attribute for SharpTools.io, various code cleanups.            - 8/16/2018
 *  V2.0.2 - Added hemisphere selectors for correct Lon/Lat on station.  Removed '?raw=true'   - 8/12/2018
 *           suffix from alternative icon file location if not on 'github.com'.
 *  V2.0.1 - Code cleanup; Added 'Observation' times; Changed 'Update' time to 'Poll' time     - 8/11/2018
 *           corrected display of some options variables (Illuminance/UV/FeelsLike) when no forecast source selected.
 *  V2.0.0 - New version completely rebuilt 08/10/2018
 *
 */
import groovy.transform.Field

metadata {
    definition (name: "Weather-Display With External Forecast Driver", namespace: "Matthew", author: "Scottma61") {
        capability "Actuator"
        capability "Sensor"
        capability "Temperature Measurement"
        capability "Illuminance Measurement"
        capability "Relative Humidity Measurement"
        
        command "PollStationAndForecast"
        
// Presentation variables
// - Location
        attribute "city", "string"
        attribute "state", "string"
        attribute "country", "string"
        attribute "location", "string"
        attribute "latitude", "string"
        attribute "longitude", "string"
        attribute "tz_id", "string"
        attribute "last_poll_Station", "string"
        attribute "last_poll_Forecast", "string"
        attribute "last_observation_Station", "string"
        attribute "last_observation_Forecast", "string"
		attribute "myTile", "string"
        
// - Astronomy        
        attribute "localSunset", "string"  //  - required for SmartTiles dashboard
        attribute "localSunrise", "string"  //  - required for SmartTiles dashboard        
        attribute "moonAge", "string"
        attribute "moonPhase", "string"
        attribute "moonIllumination", "string"

// - Current Conditions
        attribute "alert", "string"
        attribute "cloud", "number"
        attribute "condition_text", "string"
        attribute "icon", "string"
        attribute "iconWithText", "string"
        attribute "icon_url", "string"
		attribute "weather", "string"  //  - required for SmartTiles dashboard
        attribute "weatherIcon", "string"  // same as 'condition_code' - required for SmartTiles dashboard
        attribute "forecastIcon", "string"  // same as condition_code - required for SharpTools.io
        attribute "dewpoint", "number"
		attribute "feelsLike", "string"  //  - required for SmartTiles dashboard
        attribute "lux", "string" 
        attribute "precip_today", "number"
        attribute "percentPrecip", "string"  // same as 'chanceOfRain' - required for SmartTiles dashboard
        attribute "chanceOfRain", "string"
        attribute "pressure", "string"
		attribute "temperature", "string"  // same as 'temp' - required for SmartTiles dashboard
        attribute "solarradiation", "string"
        attribute "UV", "number"
        attribute "vis", "string"
        attribute "weatherSummary", "string"
        attribute "wind", "string"
        attribute "wind_gust", "string"
        attribute "wind_degree", "string"
        attribute "wind_dir", "string"
        attribute "wind_string", "string"

// Forecast
        attribute "forecastHigh", "string"
        attribute "forecastLow", "string"
        attribute "rainTomorrow", "string"
        attribute "rainDayAfterTomorrow", "string"

// SunriseSunSet Sourced variables
        attribute "twilight_begin", "string"
        attribute "twilight_end", "string"
        attribute "sunsetTime", "string"
        attribute "sunriseTime", "string"
        
    }
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
            LOGINFO("extSource: ${extSource}")
            if(extSource.toInteger() != 1){
                input "apiKey", "text", required: true, title: "API Key"
                input "pollIntervalForecast", "enum", title: "External Source Poll Interval", required: true, defaultValue: "3 Hours", options: ["Manual Poll Only", "5 Minutes", "10 Minutes", "15 Minutes", "30 Minutes", "1 Hour", "3 Hours"]
                input "pollLocationForecast", "text", required: true, title: "ZIP Code or Location"
				LOGINFO("pollLocationForecast: ${pollLocationForecast}")                
                input "sourceImg", "bool", required: true, defaultValue: false, title: "Icons from: On = Standard - Off = Alternative"
                input "iconStore", "text", required: true, defaultValue: "https://github.com/Scottma61/WeatherIcons/blob/master/", title: "Alternative Icon Location:"
            }
            input "iconType", "bool", title: "Condition Icon: On = Current - Off = Forecast", required: true, defaultValue: false
	    	input "tempFormat", "enum", required: true, defaultValue: "Fahrenheit (°F)", title: "Display Unit - Temperature: Fahrenheit (°F) or Celsius (°C)",  options: ["Fahrenheit (°F)", "Celsius (°C)"]
            input "datetimeFormat", "enum", required: true, defaultValue: "m/d/yyyy 12 hour (am|pm)", title: "Display Unit - Date-Time Format",  options: [1:"m/d/yyyy 12 hour (am|pm)", 2:"m/d/yyyy 24 hour", 3:"mm/dd/yyyy 12 hour (am|pm)", 4:"mm/dd/yyyy 24 hour", 5:"d/m/yyyy 12 hour (am|pm)", 6:"d/m/yyyy 24 hour", 7:"dd/mm/yyyy 12 hour (am|pm)", 8:"dd/mm/yyyy 24 hour", 9:"yyyy/mm/dd 24 hour"]
            input "distanceFormat", "enum", required: true, defaultValue: "Miles (mph)", title: "Display Unit - Distance/Speed: Miles or Kilometres",  options: ["Miles (mph)", "Kilometers (kph)"]
            input "pressureFormat", "enum", required: true, defaultValue: "Inches", title: "Display Unit - Pressure: Inches or Millibar",  options: ["Inches", "Millibar"]
            input "rainFormat", "enum", required: true, defaultValue: "Inches", title: "Display Unit - Precipitation: Inches or Millimetres",  options: ["Inches", "Millimetres"]
            input "summaryType", "bool", title: "Full Weather Summary", required: true, defaultValue: false
            input "logSet", "bool", title: "Create extended Logging", required: true, defaultValue: false
            

            input "sourcefeelsLike", "bool", required: true, title: "Feelslike from Weather-Display?", defaultValue: true
		    input "sourceIllumination", "bool", required: true, title: "Illuminance from Weather-Display?", defaultValue: true
            input "sourceUV", "bool", required: true, title: "UV from Weather-Display?", defaultValue: true
            input "sourceastronomy", "enum", required: true, defaultValue: "Sunrise-Sunset.org", title: "Astronomy Source:", options: [1:"Sunrise-Sunset.org", 2:"Weather-Display", 3:"WeatherUnderground.com", 4:"APIXU.com", 5:"DarkSky.net"]
        }
    }
}

def updated() {
    unschedule()
    logCheck()
    state.driverVersion = "2.1.4"    // ************************* Update as required *************************************
	state.driverNameSpace = "Matthew"
    
    state.extSource = (settings?.extSource ?: 1).toInteger()
    state.pollIntervalStation = (settings?.pollIntervalStation ?: "3 Hours")
    state.stationLatHemi =  (settings?.stationLatHemi ?: "North")
    state.stationLongHemi =  (settings?.stationLongHemi ?: "West")
    state.pollLocationStation = (settings?.pollLocationStation ?: "http://")
    state.pollIntervalForecast = (settings?.pollIntervalForecast ?: "ZIP Code or Location")
    state.pollLocationForecast = (settings?.pollLocationForecast ?: "http://")
    state.datetimeFormat = (settings?.datetimeFormat ?: 1).toInteger()
    state.distanceFormat = (settings?.distanceFormat ?: "Miles (mph)")
    state.pressureFormat = (settings?.pressureFormat ?: "Inches")
    state.rainFormat = (settings?.rainFormat ?: "Inches")
    state.tempFormat = (settings?.tempFormat ?: "Fahrenheit (°F)")
	state.iconType = (settings?.iconType ?: false)
    state.logSet = (settings?.logSet ?: false)
    state.sourcefeelsLike = (settings?.sourcefeelsLike ?: false)
    state.sourceIllumination = (settings?.sourceIllumination ?: false)
    state.sourceImg = (settings?.sourceImg ?: false)
    state.sourceUV = (settings?.sourceUV ?: false)
    state.sourceastronomy = (settings?.sourceastronomy ?: 1).toInteger()
    state.summaryType = (settings?.summaryType ?: false)
    state.iconStore = (settings?.iconStore ?: "https://github.com/Scottma61/WeatherIcons/blob/master/")

    if(state.extSource > 1){
        pollIntervalForecast = (settings?.pollIntervalForecast ?: "3 Hours").replace(" ", "")
        if(pollIntervalForecast == "Manual Poll Only"){
            LOGINFO("MANUAL POLLING ONLY")
        } else {
            "runEvery${pollIntervalForecast}"(pollScheduleForecast)
        }
    }
    LOGINFO("pollIntervalForecast: $pollIntervalForecast")

    pollIntervalStation = (settings?.pollIntervalStation ?: "3 Hours").replace(" ", "")
    if(pollIntervalStation != pollIntervalForecast){
        if(pollIntervalStation == "Manual Poll Only"){
            LOGINFO("MANUAL POLLING ONLY")
        } else {
            "runEvery${pollIntervalStation}"(pollScheduleStation)
            LOGINFO("pollIntervalStation: $pollIntervalStation")
        }
    }
}

def pollScheduleForecast(){
    PollExternal()
}

def pollScheduleStation(){
    PollStationNow()
}
def parse(String description) {
}

def PollStationNow() {
//  *****  refresh Weather-Display data *****
    log.info "Weather-Display Driver - INFO: Weather-Display: Executing 'poll', location: ${location.name}"    
    wd = [:]
    paramsWD = [ uri: "${pollLocationStation}everything.php" ]
    try {
        httpGet(paramsWD)		{ resp -> wd << resp.data }
    } catch (e) { log.error "Weather-Display Driver - ERROR: http call failed for Weather-Display weather api: $e" }

    if(state.logSet == true){  
        LOGINFO("paramsWD: ${paramsWD}")
        LOGINFO("response data: ${wd}")
    } 
    if(state.logSet == false){ 
        LOGINFO("Further Weather-Display data logging disabled")
    }        
    if (!wd){
        log.warn "Weather-Display Driver - WARNING: No response from Weather-Display API"
	} else {
		tZ = TimeZone.getDefault()	
		stationUpTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX", location.timeZone), tZ)
	}
    //  *****  stage Weather-Display variables  *****
    if(state.extSource==1){
	    possAlert = null
        if(!alert){alert = "Weather alerts are not available from this source."}
    }
    setDateTimeFormats(state.datetimeFormat)
    isFahrenheit = (state.tempFormat == "Fahrenheit (°F)") ? true : false
    isDistanceMetric = (state.distanceFormat == "Kilometers (kph)") ? true : false
    isRainMetric = (state.rainFormat == "Millimetres") ? true : false
    isPressureMetric = (state.pressureFormat == "Millibar") ? true : false

	tz_id = TimeZone.getDefault().getID()
	stationObsTime = new Date().parse("HH:mm d/M/yyyy", wd.time.time_date, tZ)
	sotime = stationObsTime
	sutime = stationUpTime
	forecastObsTime = Date.parse("yyyy-MM-dd hh:mm:ss", "2000-01-01 0:00:01")
    fotime = forecastObsTime
	futime = stationUpTime
	last_poll_Station = sutime.format("${dateFormat}", tZ) + ", " + sutime.format("${timeFormat}", tZ)
	last_observation_Station = sotime.format("${dateFormat}", tZ) + ", " + sotime.format("${timeFormat}", tZ)
	last_poll_Forecast = futime.format("${dateFormat}", tZ) + ", " + futime.format("${timeFormat}", tZ)
	last_observation_Forecast = fotime.format("${dateFormat}", tZ) + ", " + fotime.format("${timeFormat}", tZ)
    sDate = sutime.format("yyyy-MM-dd", tZ)
    sTimeOnly = sutime.format("HH:mm", tZ)

    if(!cloud){
        if(!wd.everything.weather.solar.percentage){
            cloud = 0.1
		} else {
            if(wd.everything.weather.solar.percentage.toInteger() == 100){    
                    cloud = 0.1
            } else {
                cloud = 100 - wd.everything.weather.solar.percentage.toInteger()
            }
        }
    }
//    condition_code = 'unknown'
	switch(wd.everything.forecast.icon.code.toInteger()) {
    	case 0: condition_code = 'sunny'; break;    
		case 1: condition_code = 'clear'; break;        
    	case 2: condition_code = 'cloudy'; break;
    	case 3: condition_code = 'clear'; break;    
		case 4: condition_code = 'cloudy'; break;    
		case 6: condition_code = 'fog'; break;
		case 7: condition_code = 'hazy'; break;
		case 8: condition_code = 'rain'; break;        
		case 9: condition_code = 'clear'; break;    
		case 10: condition_code = 'hazy'; break;
		case 11: condition_code = 'fog'; break;    
		case 12: condition_code = 'rain'; break;        
		case 13: condition_code = 'mostlycloudy'; break;    
		case 14: condition_code = 'rain'; break;        
		case 15: condition_code = 'rain'; break;        
		case 16: condition_code = 'snow'; break;        
		case 17: condition_code = 'tstorms'; break;            
		case 18: condition_code = 'mostlycloudy'; break;
		case 19: condition_code = 'mostlycloudy'; break;
		case 20: condition_code = 'rain'; break;
		case 21: condition_code = 'rain'; break;    
		case 22: condition_code = 'rain'; break;
		case 23: condition_code = 'sleet'; break;
		case 24: condition_code = 'sleet'; break;
		case 25: condition_code = 'snow'; break;
		case 26: condition_code = 'snow'; break;
		case 27: condition_code = 'snow'; break;
		case 28: condition_code = 'sunny'; break;
		case 29: condition_code = 'tstorms'; break;
		case 30: condition_code = 'tstorms'; break;
		case 31: condition_code = 'tstorms'; break;
		case 32: condition_code = 'tstorms'; break;
		case 35: condition_code = 'rain'; break;
		default: condition_code = 'unknown'; break;
	}
	condition_text = getDSTextName(condition_code)
	
    if(state.stationLatHemi == "North"){
    	latitude = wd.station.latitude
    } else {
        latitude =  0 - wd.station.latitude.toDouble()
    }
    if(state.stationLongHemi == "East"){
        longitude = wd.station.longitude
    } else {
    	longitude =  0 - wd.station.longitude.toDouble()
    }
    if(state.extSource==1){
    	if(!weatherIcon){weatherIcon = condition_code}
    	if(!forecastIcon){forecastIcon = condition_code}        
        sunriseAndSunset = getSunriseAndSunset(latitude, longitude, sutime.format("yyyy-MM-dd", tZ))       
		twilight_begin = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_begin, tZ)
		noonTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.solar_noon, tZ)    
		twilight_end = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_end, tZ)
		if(state.sourceastronomy == 2) {
			localSunset = Date.parse("HH:mm", wd.everything.astronomy.sun.sunset_time.time).format("${timeFormat}", tZ)
			localSunrise = Date.parse("HH:mm", wd.everything.astronomy.sun.sunrise_time.time).format("${timeFormat}", tZ)
			sunsetTime = localSunset
			sunriseTime = localSunrise
		} else {
			sunriseTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunrise, tZ)			
			sunsetTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunset, tZ)
			localSunset = sunsetTime
			localSunrise = sunriseTime
		}
		TwB = twilight_begin
		TwE = twilight_end
		sR = sunriseTime
		sS = sunsetTime
		if (!sutime || !sunriseTime || !sunsetTime || !noonTime ||
			!twilight_begin || !twilight_end || !condition_code || !cloud || !tz_id) {
			log.debug "Weather-Display Driver - DEBUG: sutime: $sutime"
			log.debug "Weather-Display Driver - DEBUG: twilight_begin: $twilight_begin"
			log.debug "Weather-Display Driver - DEBUG: sunriseTime: $sunriseTime"
			log.debug "Weather-Display Driver - DEBUG: noonTime: $noonTime"
			log.debug "Weather-Display Driver - DEBUG: sunsetTime: $sunsetTime"
			log.debug "Weather-Display Driver - DEBUG: twilight_end: $twilight_end"
			log.debug "Weather-Display Driver - DEBUG: condition_code: $condition_code"
			log.debug "Weather-Display Driver - DEBUG: cloud: $cloud"
			log.debug "Weather-Display Driver - DEBUG: tz_id: $tz_id"
			lux = null
		} else {
			is_day = null
			lux = estimateLux(sotime, sunriseTime, sunsetTime, noonTime, twilight_begin, twilight_end, condition_code, cloud, tz_id)
			illuminance = lux
			if(state.sourceastronomy == 1) {
				localSunset = sunsetTime.format("${timeFormat}")
				localSunrise = sunriseTime.format("${timeFormat}")
			}
		}
	}
	
    switch((wd.everything.weather.wind.avg_speed.bft).toInteger()){
		case 0: wind_string_bft = "Calm"; break;
		case 1: wind_string_bft = "Light air"; break;
		case 2: wind_string_bft = "Light breeze"; break;
		case 3: wind_string_bft = "Gentle breeze"; break;
		case 4: wind_string_bft = "Moderate breeze"; break;
		case 5: wind_string_bft = "Fresh breeze"; break;
		case 6: wind_string_bft = "Strong breeze"; break;
		case 7: wind_string_bft = "High wind, moderate gale, near gale"; break;
		case 8: wind_string_bft = "Gale, fresh gale"; break;
		case 9: wind_string_bft = "Strong/severe gale"; break;
		case 10: wind_string_bft = "Storm, whole gale"; break;
		case 11: wind_string_bft = "Violent storm"; break;
		case 12: wind_string_bft = "Hurricane force"; break;
        default: wind_string_bft = "Calm"; break;
	}
    city = wd.station.name.split(/ /)[0]
    lstate = wd.station.name.split(/ /)[1]
    country = wd.station.name.split(/ /)[2]
    location = wd.station.name.split(/ /)[0] + ", " + wd.station.name.split(/ /)[1]
    local_time = Date.parse("HH:mm", wd.time.time).format("${timeFormat}")
    local_date = Date.parse("d/M/yyyy", wd.time.date).format("${dateFormat}")
    moonAge = wd.everything.astronomy.moon.moon_age.toDouble()
    if (moonAge >= 0 && moonAge < 4) {moonPhase = "New Moon"}
    if (moonAge >= 4 && moonAge < 7) {moonPhase = "Waxing Crescent"}
    if (moonAge >= 7 && moonAge < 10) {moonPhase = "First Quarter"}
    if (moonAge >= 10 && moonAge < 14) {moonPhase = "Waxing Gibbous"}
    if (moonAge >= 14 && moonAge < 18) {moonPhase = "Full Moon"}
    if (moonAge >= 18 && moonAge < 22) {moonPhase = "Waning Gibbous"}
    if (moonAge >= 22 && moonAge < 26) {moonPhase = "Last Quarter"}
    if (moonAge >= 26) {moonPhase = "Waxing Gibbous"}
    moonIllumination = wd.everything.astronomy.moon.moon_phase
    if(!wd.everything.weather.solar.irradiance.wm2){
        solarradiation = "This station does not send Solar Radiation data."
    } else {
        solarradiation = wd.everything.weather.solar.irradiance.wm2
    }
    sfeelsLike = (isFahrenheit ? wd.everything.weather.apparent_temperature.current.f : wd.everything.weather.apparent_temperature.current.c)
    if(state.sourceIllumination) {
        if (!wd.everything.weather.solar.irradiance.wm2){
            lux =  "This station does not send lux data."
            illuminance = "This station does not send illuminance data."
        } else {
            lux = wd.everything.weather.solar.irradiance.wm2
            illuminance = wd.everything.weather.solar.irradiance.wm2
        } 
    }
    
	if(state.extSource==1){
        condition_icon = '<img src=https://icons.wxug.com/i/c/a/' + condition_code + '.gif>'
        condition_iconWithText = '<img src=https://icons.wxug.com/i/c/a/' + condition_code + '.gif><br>' + condition_text
        condition_icon_url = 'https://icons.wxug.com/i/c/a/' + condition_code +'.gif'
        if(state.sourceIllumination) {
            if (!wd.everything.weather.solar.irradiance.wm2){
                lux = lux
                illuminance = illuminance
            } else {
                lux = wd.everything.weather.solar.irradiance.wm2
                illuminance = wd.everything.weather.solar.irradiance.wm2
            } 
        }
        if(!percentPrecip){percentPrecip = 'Not available from this source'}
        if(!chanceOfRain){chanceOfRain = 'Not available from this source'}
	}
	
	dewpoint = (isFahrenheit ? wd.everything.weather.dew_point.current.f : wd.everything.weather.dew_point.current.c)
    humidity = wd.everything.weather.humidity.current
	precip_today = (isRainMetric ? wd.everything.weather.rainfall.daily.mm : wd.everything.weather.rainfall.daily.in)
	pressure = (isPressureMetric ? wd.everything.weather.pressure.current.mb : wd.everything.weather.pressure.current.inhg)
	temperature = (isFahrenheit ? wd.everything.weather.temperature.current.f : wd.everything.weather.temperature.current.c)
    UV = wd.everything.weather.uv.uvi
	wind = (isDistanceMetric ? wd.everything.weather.wind.avg_speed.kmh : wd.everything.weather.wind.avg_speed.mph)
	wind_gust = (isDistanceMetric ? wd.everything.weather.wind.max_gust_speed.kmh : wd.everything.weather.wind.max_gust_speed.mph)
	wind_degree = wd.everything.weather.wind.direction.degrees
	wind_dir = wd.everything.weather.wind.direction.cardinal
    wind_bft = wd.everything.weather.wind.avg_speed.bft
    wind_string = "Wind: ${wind_string_bft} from the " + wd.everything.weather.wind.direction.cardinal + " at " + (isDistanceMetric ? wd.everything.weather.wind.avg_speed.kmh + " KPH" : wd.everything.weather.wind.avg_speed.mph + " MPH")

	//  ***** present Weather-Display variables  *****
    sendEvent(name: "last_poll_Station", value: last_poll_Station, displayed: true)    
	sendEvent(name: "last_observation_Station", value: last_observation_Station, displayed: true)    
	sendEvent(name: "city", value: city, displayed: true)
	sendEvent(name: "state", value: lstate, displayed: true)
	sendEvent(name: "country", value: country, displayed: true)
	sendEvent(name: "location", value: location, displayed: true)
    sendEvent(name: "latitude", value: latitude, displayed: true)
    sendEvent(name: "longitude", value:  longitude, displayed: true)
    sendEvent(name: "tz_id", value: tz_id, displayed: true)
    sendEvent(name: "cloud", value: cloud, unit: '%', displayed: true)
    sendEvent(name: "humidity", value: humidity, unit: '%', displayed: true)
	sendEvent(name: "precip_today", value: precip_today, unit: (isRainMetric ? 'MM' : 'IN'), displayed: true)
	sendEvent(name: "pressure", value: pressure, unit: (isDistanceMetric ? 'MB' : 'IN'), displayed: true)
	sendEvent(name: "temperature", value: temperature, unit: (isFahrenheit ? '°F' : '°C'), displayed: false)
    if(state.sourceUV){
    	sendEvent(name: "UV", value: UV, displayed: true)
    }
	sendEvent(name: "solarradiation", value: solarradiation, displayed: true)
	sendEvent(name: "wind", value: wind, unit: (isDistanceMetric ? 'KPH' : 'MPH'), displayed: false)
	sendEvent(name: "wind_gust", value: wind_gust, unit: (isDistanceMetric ? 'KPH' : 'MPH'), displayed: false)
	sendEvent(name: "wind_degree", value: wind_degree, displayed: false)
	sendEvent(name: "wind_dir", value: wind_dir, displayed: false)
    sendEvent(name: "wind_string", value: wind_string, displayed: true)
    sendEvent(name: "moonAge", value: moonAge, displayed: true)
    sendEvent(name: "moonPhase", value: moonPhase, displayed: true)
    sendEvent(name: "moonIllumination", value: moonIllumination, unit: '%', displayed: true)
	sendEvent(name: "dewpoint", value: dewpoint, unit: (isFahrenheit ? '°F' : '°C'), displayed: true)
    if(state.sourcefeelsLike==true){
	    sendEvent(name: "feelsLike", value: sfeelsLike, unit: (isFahrenheit ? '°F' : '°C'), displayed: true) // only needed for SmartTiles dashboard
    }
	if(state.iconType) {
		sendEvent(name: "condition_code", value: condition_code, displayed: true)
		sendEvent(name: "condition_text", value: condition_text, displayed: true)            
    	sendEvent(name: "icon", value: condition_icon, displayed: true)
    	sendEvent(name: "iconWithText", value: condition_iconWithText, displayed: true)    
    	sendEvent(name: "icon_url", value: condition_icon_url, displayed: false)    
	}	
	if(state.extSource==1){	
		sendEvent(name: "feelsLike", value: sfeelslike, unit: (isFahrenheit ? '°F' : '°C'), displayed: true) // only needed for SmartTiles dashboard
		sendEvent(name: "twilight_begin", value: TwB.format("${timeFormat}"), displayed: true)
		sendEvent(name: "twilight_end", value: TwE.format("${timeFormat}"), displayed: true)
		sendEvent(name: "sunriseTime", value: sunriseTime.format("${timeFormat}"), displayed: true)
		sendEvent(name: "sunsetTime", value: sunsetTime.format("${timeFormat}"), displayed: true)
		sendEvent(name: "localSunset", value: localSunset.format("${timeFormat}"), displayed: false) // only needed for SmartTiles dashboard
		sendEvent(name: "localSunrise", value: localSunrise.format("${timeFormat}"), displayed: false) // only needed for SmartTiles dashboard
		sendEvent(name: "illuminance", value: illuminance, unit: 'lux', displayed: true)
		sendEvent(name: "lux", value: lux, unit: 'lux', displayed: true)
		sendEvent(name: "last_poll_Forecast", value: last_poll_Forecast, displayed: true)    
	    sendEvent(name: "last_observation_Forecast", value: last_observation_Forecast, displayed: true)    
	}
// ***** Build Summary Display variables  *****    
    if(state.extSource==1){
        Summary_last_observation_time = (sotime > fotime ? "${sotime.format("${timeFormat}")}" : "${fotime.format("${timeFormat}")}")
        Summary_last_observation_date = (sotime > fotime ? "${sotime.format("${dateFormat}")}" : "${fotime.format("${dateFormat}")}")
        if(state.sourcefeelsLike==true){
            Summary_feelsLike = sfeelsLike + (isFahrenheit ? '°F.' : '°C.')
        } else {
            if(ffeelslike==null){
                Summary_feelsLike = sfeelsLike + (isFahrenheit ? '°F.' : '°C.')
            } else {
                Summary_feelsLike = ""
            }
        }
    	SummaryMessage(state.summaryType, city, lstate, Summary_last_observation_date, Summary_last_observation_time, condition_text, humidity+'%', temperature+(isFahrenheit ? '°F.' : '°C.'), "", Summary_feelsLike, wind_string, wind_gust+(isDistanceMetric ? " KPH" : " MPH"), wind_dir, "", "", alert)
    }
    return 
}

def PollExternal(){
    if(state.extSource >=2){PollStationNow()}
    if(state.extSource == 2){
// ***** refresh WeatherUnderground data  *****
        log.info "Weather-Display Driver - INFO: WeatherUnderground: Executing 'poll', location: ${location.name}"
        wu = [:]
        paramsWU = [ uri: "http://api.wunderground.com/api/${apiKey}/alerts/astronomy/conditions_v11/forecast/hourly/q/${pollLocationForecast}.json" ]
        try {
            httpGet(paramsWU)		{ resp -> wu << resp.data }
        } catch (e) { log.error "Weather-Display Driver - ERROR: http call failed for WeatherUnderground weather api: $e" }
        if(!wu){
            log.warn "Weather-Display Driver - WARNING: No response from WeatherUnderground API"
        } else {
            if(state.logSet == true){
                LOGINFO("paramsWU: ${paramsWU}")
                LOGINFO("response data: ${wu}")
            } 
            if(state.logSet == false){ 
                LOGINFO("Further WeatherUnderground data logging disabled")
            }
        }

//  *****  stage WeatherUnderground Variables  *****
		tZ = TimeZone.getDefault()	
		stationUpTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX", location.timeZone), tZ)
    	setDateTimeFormats(state.datetimeFormat)        
        isFahrenheit = (state.tempFormat == "Fahrenheit (°F)") ? true : false
        isDistanceMetric = (state.distanceFormat == "Kilometers (kph)") ? true : false
        isRainMetric = (state.rainFormat == "Millimetres") ? true : false
        isPressureMetric = (state.pressureFormat == "Millibar") ? true : false

		tZ = TimeZone.getTimeZone(wu.current_observation.local_tz_long)
        tz_id = wu.current_observation.local_tz_long
        sutime = stationUpTime
        forecastObsTime =  new Date().parse("EEE, dd MMM yyyy HH:mm:ss", wu.current_observation.observation_time_rfc822, tZ)    
        fotime = forecastObsTime
        futime = stationUpTime
        last_poll_Station = sutime.format("${dateFormat}", tZ) + ", " + sutime.format("${timeFormat}", tZ)
        last_observation_Station = sotime.format("${dateFormat}", tZ) + ", " + sotime.format("${timeFormat}", tZ)
        last_poll_Forecast = futime.format("${dateFormat}", tZ) + ", " + futime.format("${timeFormat}", tZ)
        last_observation_Forecast = fotime.format("${dateFormat}", tZ) + ", " + fotime.format("${timeFormat}", tZ)
        fDate = futime.format("yyyy-MM-dd", tZ)
        fTimeOnly = futime.format("HH:mm", tZ)

		if (!wu.hourly_forecast.sky[0]) {
            cloud = 0.1
        } else {
			cloud = (wu.hourly_forecast.sky[0].toInteger() == 0) ? 0.1 : wu.hourly_forecast.sky[0].toInteger()
        }
        
		possAlert = wu.alerts.description
        if (!possAlert){
            alert = "No current weather alerts for this area."
        } else {
            alert = wu.alerts.description.toString().replaceAll("[{}\\[\\]]", "")
        }
        if(state.sourceastronomy == 3) {
            localSunset = Date.parse("HH:mm", wu.sun_phase.sunset.hour + ":" + wu.sun_phase.sunset.minute).format("$timeFormat}")
            localSunrise = Date.parse("HH:mm", wu.sun_phase.sunrise.hour + ":" + wu.sun_phase.sunrise.minute).format("$timeFormat}")
        }
        if(state.sourcefeelsLike==false){
            ffeelsLike = (isFahrenheit ? wu.current_observation.feelslike_f : wu.current_observation.feelslike_c)
            fforecast_text = wu.forecast.simpleforecast.forecastday[0].conditions
        }    
        forecast_text = wu.forecast.simpleforecast.forecastday[0].conditions.capitalize()
        forecast_code = wu.forecast.simpleforecast.forecastday[0].icon
        vis = (isDistanceMetric ? wu.current_observation.visibility_km : wu.current_observation.visibility_mi)
        uv = wu.current_observation.UV
        percentPrecip = wu.forecast.simpleforecast.forecastday[0].pop
        chanceOfRain = wu.forecast.simpleforecast.forecastday[0].pop + '%'
		condition_code = (state.iconType ? wu.current_observation.icon : wu.forecast.simpleforecast.forecastday[0].icon)
		condition_text = (state.iconType ? wu.current_observation.weather.capitalize() : wu.forecast.simpleforecast.forecastday[0].conditions.capitalize())
		weather = condition_text
        weatherIcon = condition_code
        forecastIcon = condition_code
        forecastHigh =(isFahrenheit ? wu.forecast.simpleforecast.forecastday[0].high.fahrenheit : wu.forecast.simpleforecast.forecastday[0].high.celsius)
        forecastLow = (isFahrenheit ? wu.forecast.simpleforecast.forecastday[0].low.fahrenheit : wu.forecast.simpleforecast.forecastday[0].low.celsius)
        rainTomorrow = (isRainMetric ? wu.forecast.simpleforecast.forecastday[1].qpf_allday.mm : wu.forecast.simpleforecast.forecastday[1].qpf_allday.in)
        rainDayAfterTomorrow = (isRainMetric ? wu.forecast.simpleforecast.forecastday[2].qpf_allday.mm : wu.forecast.simpleforecast.forecastday[2].qpf_allday.in)
        sunriseAndSunset = getSunriseAndSunset(wu.current_observation.display_location.latitude.toDouble(), wu.current_observation.display_location.longitude.toDouble(), futime.format("yyyy-MM-dd", tZ))       
        sunriseTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunrise, tZ)
        sunsetTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunset, tZ)
        noonTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.solar_noon, tZ)    
        twilight_begin = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_begin, tZ)
        twilight_end = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_end, tZ)
        TwB = twilight_begin
        TwE = twilight_end
        sR = sunriseTime
        sS = sunsetTime
        if (!futime || !sunriseTime || !sunsetTime || !noonTime ||
            !twilight_begin || !twilight_end || !condition_code || !cloud || !tz_id) {
            log.debug "Weather-Display Driver - DEBUG: futime: $futime"
            log.debug "Weather-Display Driver - DEBUG: twilight_begin: $twilight_begin"
            log.debug "Weather-Display Driver - DEBUG: sunriseTime: $sunriseTime"
            log.debug "Weather-Display Driver - DEBUG: noonTime: $noonTime"
            log.debug "Weather-Display Driver - DEBUG: sunsetTime: $sunsetTime"
            log.debug "Weather-Display Driver - DEBUG: twilight_end: $twilight_end"
            log.debug "Weather-Display Driver - DEBUG: condition_code: $condition_code"
            log.debug "Weather-Display Driver - DEBUG: cloud: $cloud"
            log.debug "Weather-Display Driver - DEBUG: tz_id: $tz_id"
            lux = null
        } else {
            is_day = null
            lux = estimateLux(futime, sunriseTime, sunsetTime, noonTime, twilight_begin, twilight_end, condition_code, cloud, tz_id)
           if(state.sourceImg==false){
                imgName = getImgName(condition_text, is_day)
                condition_icon = '<img src=' + imgName + '>'
                condition_iconWithText = "<img src=" + imgName + "><br>" + condition_text
                condition_icon_url = imgName
                condition_icon_only = imgName.split("/")[-1].replaceFirst("\\?raw=true","")
                fimgName = getImgName(forecast_text, is_day)
                forecast_icon = '<img src=' + fimgName + '>'
                forecast_iconWithText = "<img src=" + fimgName + "><br>" + forecast_text
                forecast_icon_url = fimgName
                forecast_icon_only = fimgName.split("/")[-1].replaceFirst("\\?raw=true","")
            } else {
                condition_icon = '<img src=https://icons.wxug.com/i/c/a/' + condition_code + '.gif>'
                condition_iconWithText = '<img src=https://icons.wxug.com/i/c/a/' + condition_code + '.gif><br>' + condition_text
                condition_icon_url = 'https://icons.wxug.com/i/c/a/' + condition_code +'.gif'
                condition_icon_only = condition_code +'.gif'
                forecast_icon = '<img src=https://icons.wxug.com/i/c/a/' + wu.forecast.simpleforecast.forecastday[0].icon_url.split("/")[-1] + '>'
                forecast_iconWithText = '<img src=https://icons.wxug.com/i/c/a/' + wu.forecast.simpleforecast.forecastday[0].icon_url.split("/")[-1] + '><br>' + forecast_text
                forecast_icon_only = wu.forecast.simpleforecast.forecastday[0].icon_url.split("/")[-1]
                forecast_icon_url = 'https://icons.wxug.com/i/c/a/' + wu.forecast.simpleforecast.forecastday[0].icon_url.split("/")[-1]
            }
        }
        if(state.sourceIllumination==false) {
            illuminance = lux
            lux = lux
            sendEvent(name: "lux", value: lux, unit: 'lux', displayed: true)
            sendEvent(name: "illuminance", value: illuminance, unit: 'lux', displayed: true)
        }
    }
    if(state.extSource == 3){
//  *****  refresh APIXU.com data  *****
        log.info "Weather-Display Driver - INFO: APIXU.com: Executing 'poll', location: ${location.name}"
        xu = [:]
        paramsXU = [ uri: "https://api.apixu.com/v1/forecast.json?key=$apiKey&q=$pollLocationForecast&days=3" ]
        try {
            httpGet(paramsXU)		{ resp -> xu << resp.data }
        } catch (e) { log.error "Weather-Display Driver - ERROR: http call failed for APIXU weather api: $e" }
        if (!xu){
            log.warn "Weather-Display Driver - WARNING: No response from APIXU.com API"
        } else {
            if(state.logSet == true){  
                LOGINFO("paramsXU: ${paramsXU}")   
                LOGINFO("response data: ${xu}")
            } 
            if(state.logSet == false){ 
                LOGINFO("Further APIXU data logging disabled")
            }   
        }
//  *****  stage APIXU.COM Variables  *****        
		tZ = TimeZone.getTimeZone(xu.location.tz_id)
		stationUpTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX", location.timeZone), tZ)
        sutime = stationUpTime
        forecastObsTime =  new Date().parse("yyyy-MM-dd HH:mm", xu.current.last_updated, tZ)
        fotime = forecastObsTime
        futime = new Date().parse("yyyy-MM-dd HH:mm", xu.location.localtime, tZ)
        last_poll_Station = sutime.format("${dateFormat}", tZ) + ", " + sutime.format("${timeFormat}", tZ)
        last_observation_Station = sotime.format("${dateFormat}", tZ) + ", " + sotime.format("${timeFormat}", tZ)
        last_poll_Forecast = futime.format("${dateFormat}", tZ) + ", " + futime.format("${timeFormat}", tZ)
        last_observation_Forecast = fotime.format("${dateFormat}", tZ) + ", " + fotime.format("${timeFormat}", tZ)
        fDate = futime.format("yyyy-MM-dd", tZ)
        fTimeOnly = futime.format("HH:mm", tZ)
        tz_id = xu.location.tz_id
		possAlert = null
        alert = "Weather alerts are not available from this source."
        if(state.sourcefeelsLike==false){	
            ffeelsLike = (isFahrenheit ? xu.current.feelslike_f : xu.current.feelslike_c)
            fforecast_text = xu.forecast.forecastday[0].day.condition.text.capitalize()
        }
        last_poll_Station = sutime.format("${dateFormat}") + ", " + sutime.format("${timeFormat}")
        last_observation_Station = sotime.format("${dateFormat}") + ", " + sotime.format("${timeFormat}")
        last_poll_Forecast = futime.format("${dateFormat}") + ", " + futime.format("${timeFormat}")
        last_observation_Forecast = fotime.format("${dateFormat}") + ", " + fotime.format("${timeFormat}")
		
		condition_code = (state.iconType ? getWUCodeName(xu.current.condition.code, xu.current.is_day) : getWUCodeName(xu.forecast.forecastday[0].day.condition.code, xu.current.is_day))
		condition_text = getDSTextName(condition_code)
		weatherIcon = condition_code
		weather = condition_text
        forecastIcon = weatherIcon
        cloud = (xu.current.cloud == 0 ? 0.1 : xu.current.cloud)
        vis = (isDistanceMetric ? xu.current.vis_km : xu.current.vis_miles)
        uv = xu.forecast.forecastday[0].day.condition.uv
        percentPrecip = "Not Available"
        chanceOfRain = "Not Available"
		forecastHigh = (isFahrenheit ? xu.forecast.forecastday[0].day.maxtemp_f : xu.forecast.forecastday[0].day.maxtemp_c)
        forecastLow = (isFahrenheit ? xu.forecast.forecastday[0].day.mintemp_f : xu.forecast.forecastday[0].day.mintemp_c)
        rainTomorrow = (isRainMetric ? xu.forecast.forecastday[1].day.totalprecip_mm : xu.forecast.forecastday[1].day.totalprecip_in)
        rainDayAfterTomorrow = (isRainMetric ? xu.forecast.forecastday[2].day.totalprecip_mm : xu.forecast.forecastday[2].day.totalprecip_in)
        if(state.sourceastronomy == 4) {
            localSunset = Date.parse("hh:mm a", xu.forecast.forecastday[0].astro.sunset).format("$timeFormat}")
            localSunrise = Date.parse("hh:mm a", xu.forecast.forecastday[0].astro.sunrise).format("$timeFormat}")      
        }
        sunriseAndSunset = getSunriseAndSunset(xu.location.lat.toDouble(), xu.location.lon.toDouble(), fDate)
        sunriseTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunrise, tZ)
        sunsetTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunset, tZ)
        noonTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.solar_noon, tZ)    
        twilight_begin = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_begin, tZ)
        twilight_end = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_end, tZ)
        TwB = twilight_begin
        TwE = twilight_end
        sR = sunriseTime
        sS = sunsetTime
        if (!futime || !sunriseTime || !sunsetTime || !noonTime ||
            !twilight_begin || !twilight_end || !condition_code || !cloud || !tz_id) {
            log.debug "Weather-Display Driver - DEBUG: futime: $futime"
            log.debug "Weather-Display Driver - DEBUG: twilight_begin: $twilight_begin"
            log.debug "Weather-Display Driver - DEBUG: sunriseTime: $sunriseTime"
            log.debug "Weather-Display Driver - DEBUG: noonTime: $noonTime"
            log.debug "Weather-Display Driver - DEBUG: sunsetTime: $sunsetTime"
            log.debug "Weather-Display Driver - DEBUG: twilight_end: $twilight_end"
            log.debug "Weather-Display Driver - DEBUG: condition_code: $condition_code"
            log.debug "Weather-Display Driver - DEBUG: cloud: $cloud"
            log.debug "Weather-Display Driver - DEBUG: tz_id: $tz_id"
            lux = null
        } else {
            is_day = xu.current.is_day
            lux = estimateLux(futime, sunriseTime, sunsetTime, noonTime, twilight_begin, twilight_end, condition_code, cloud, tz_id)
            if(state.sourceImg==false){
                imgName = getImgName((state.iconType ? xu.current.condition.code : xu.forecast.forecastday[0].day.condition.code), is_day)
                condition_icon = '<img src=' + imgName + '>'
                condition_iconWithText = "<img src=" + imgName + "><br>" + condition_text
                condition_icon_url = imgName
                condition_icon_only = imgName.split("/")[-1].replaceFirst("\\?raw=true","")
                fimgName = getImgName(forecast_text, is_day)
                forecast_icon = '<img src=' + fimgName + '>'
                forecast_iconWithText = "<img src=" + fimgName + "><br>" + forecast_text
                forecast_icon_url = fimgName
                forecast_icon_only = fimgName.split("/")[-1].replaceFirst("\\?raw=true","")
            } else {
                condition_icon = (iconType ? '<img src=http:' + xu.current.condition.icon + '>' : '<img src=http:' + xu.forecast.forecastday[0].day.condition.icon + '>')
                condition_iconWithText = condition_icon + '<br>' + condition_text
                condition_icon_url = (iconType ? 'https:' + xu.current.condition.icon : 'https:' + xu.forecast.forecastday[0].day.condition.icon)
                condition_icon_only = (iconType ? xu.current.condition.icon.split("/")[-1] : xu.forecast.forecastday[0].day.condition.icon.split("/")[-1])
                forecast_icon = '<img src=http:' + xu.forecast.forecastday[0].day.condition.icon + '>'
                forecast_icon_only = xu.forecast.forecastday[0].day.condition.icon.split("/")[-1]
                forecast_icon_url = 'https:' + xu.forecast.forecastday[0].day.condition.icon
                forecast_code = xu.forecast.forecastday[0].day.condition.code
            }
            if(state.sourceIllumination==false) {
                illuminance = lux
                lux = lux
                sendEvent(name: "lux", value: lux, unit: 'lux', displayed: true)
                sendEvent(name: "illuminance", value: illuminance, unit: 'lux', displayed: true)
            }
        }
    }
    if(state.extSource == 4){
// ***** refresh DarkSky data  *****
        log.info "Weather-Display Driver - INFO: DarkSky: Executing 'poll', location: ${location.name}"
        ds = [:]
        paramsDS = [ uri: "https://api.darksky.net/forecast/${apiKey}/${pollLocationForecast}?units=us&exclude=minutely,hourly,flags" ]
        try {
            httpGet(paramsDS)		{ resp -> ds << resp.data }
        } catch (e) { log.error "Weather-Display Driver - ERROR: http call failed for DarkSky weather api: $e" }
        if (!ds){
            log.warn "Weather-Display Driver - WARNING: No response from DarkSky API"
        } else {
            if(state.logSet == true){
                LOGINFO("paramsDS: ${paramsDS}")
                LOGINFO("response data: ${ds}")
            } 
            if(state.logSet == false){ 
                LOGINFO("Further DarkSky data logging disabled")
            }
        }

//  *****  stage DarkSky Variables  *****
		tZ = TimeZone.getTimeZone(ds.timezone)
		stationUpTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX", location.timeZone), tZ)
    	setDateTimeFormats(state.datetimeFormat)        
        isFahrenheit = (state.tempFormat == "Fahrenheit (°F)") ? true : false
        isDistanceMetric = (state.distanceFormat == "Kilometers (kph)") ? true : false
        isRainMetric = (state.rainFormat == "Millimetres") ? true : false
        isPressureMetric = (state.pressureFormat == "Millibar") ? true : false
        tZ = TimeZone.getTimeZone(ds.timezone)
        tz_id = ds.timezone
        sutime = stationUpTime
        forecastObsTime =  new Date(ds.currently.time * 1000L)
        fotime = forecastObsTime
        futime = stationUpTime
        last_poll_Station = sutime.format("${dateFormat}", tZ) + ", " + sutime.format("${timeFormat}", tZ)
        last_observation_Station = sotime.format("${dateFormat}", tZ) + ", " + sotime.format("${timeFormat}", tZ)
        last_poll_Forecast = futime.format("${dateFormat}", tZ) + ", " + futime.format("${timeFormat}", tZ)
        last_observation_Forecast = fotime.format("${dateFormat}", tZ) + ", " + fotime.format("${timeFormat}", tZ)
        fDate = futime.format("yyyy-MM-dd", tZ)
        fTimeOnly = futime.format("HH:mm", tZ)
        if (!ds.currently.cloudCover) {
            cloud = 0.1
        } else {
			cloud = (ds.currently.cloudCover <= 0.01) ? 0.1 : Math.round(ds.currently.cloudCover * 1000) /10
        }
        possAlert = ds.alerts
        if (!ds.alerts){
            alert = "No current weather alerts for this area."
        } else {
            alert = ds.alerts.title.toString().replaceAll("[{}\\[\\]]", "")
        }
        if(state.sourceastronomy == 5) {
            localSunset = new Date(ds.daily.data[0].sunriseTime * 1000L)    
            localSunrise = new Date(ds.daily.data[0].sunsetTime * 1000L)
        }
        if(state.sourcefeelsLike==false){
            ffeelsLike = (isFahrenheit ? Math.round(ds.currently.apparentTemperature * 10) / 10 : Math.round((ds.currently.apparentTemperature * (9/5) + 32) * 10) / 10)
            fforecast_text = ds.daily.data[0].summary
        }    
        sunriseAndSunset = getSunriseAndSunset(ds.latitude.toDouble(), ds.longitude.toDouble(), futime.format("yyyy-MM-dd", tZ))       
        sunriseTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunrise, tZ)
        sunsetTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.sunset, tZ)
        noonTime = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.solar_noon, tZ)    
        twilight_begin = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_begin, tZ)
        twilight_end = new Date().parse("yyyy-MM-dd'T'HH:mm:ssXXX", sunriseAndSunset.results.civil_twilight_end, tZ)
        TwB = twilight_begin
        TwE = twilight_end
        sR = sunriseTime
        sS = sunsetTime
		if(is_day == null){
			if(sunriseTime == null || sunsetTime == null) {
				is_day = 1
			} else {
			 if(timeOfDayIsBetween(sunriseTime, sunsetTime, futime, tZ)) {
        		is_day = 1
			 } else {
				 is_day = 0
    			}
			}
		}
		switch(ds.daily.data[0].icon){
			case "clear-day": forecast_code =  (is_day ? "sunny" : "nt_clear"); break;
			case "clear-night": forecast_code =  (is_day ? "sunny" : "nt_clear"); break;
			case "rain": forecast_code = (is_day ? "rain" : "nt_rain"); break;
			case "wind": forecast_code = (is_day ? "breezy" : "nt_breezy"); break;
			case "snow": forecast_code = (is_day ? "snow" : "nt_snow"); break;
			case "sleet": forecast_code = (is_day ? "sleet" : "nt_sleet"); break;
			case "fog": forecast_code = (is_day ? "fog" : "nt_fog"); break;
			case "cloudy": forecast_code = (is_day ? "cloudy" : "nt_cloudy"); break;
			case "partly-cloudy-day": forecast_code = "partlycloudy"; break;
			case "partly-cloudy-night": forecast_code = "nt_partlycloudy"; break;
			default: forecast_code = "unknown"; break;
		}
		forecast_text = getDSTextName(forecast_code)
		vis = (isDistanceMetric ? Math.round(ds.currently.visibility * 1.60934 * 10) / 10 : Math.round(ds.currently.visibility * 10) / 10)
        uv = ds.currently.uvIndex
        percentPrecip = Math.round(ds.currently.precipProbability * 1000) /10
        chanceOfRain = percentPrecip.toString() + '%'
		switch((state.iconType ? ds.currently.icon : ds.daily.data[0].icon)) {
			case "clear-day": condition_code = (is_day ? "sunny" : "nt_clear"); break;
			case "clear-night": condition_code = (is_day ? "sunny" : "nt_clear"); break;
			case "rain": condition_code = (is_day ? "rain" : "nt_rain"); break;
			case "wind": condition_code = (is_day ? "breezy" : "nt_breezy"); break;
			case "snow": condition_code = (is_day ? "snow" : "nt_snow"); break;
			case "sleet": condition_code = (is_day ? "sleet" : "nt_sleet"); break;
			case "fog": condition_code = (is_day ? "fog" : "nt_fog"); break;
			case "cloudy": condition_code = (is_day ? "cloudy" : "nt_cloudy"); break;
			case "partly-cloudy-day": condition_code = (is_day ? "partlycloudy" : "nt_partlycloudy"); break;
			case "partly-cloudy-night": condition_code = (is_day ? "partlycloudy" : "nt_partlycloudy"); break;
			default: condition_code = "unknown"; break;
		}
		condition_text = getDSTextName(condition_code)
		weather = condition_text
		weatherIcon = condition_code
		forecastIcon = forecast_code
        forecastHigh = (isFahrenheit ? Math.round(ds.daily.data[0].temperatureHigh * 10) / 10 : Math.round((ds.daily.data[0].temperatureHigh * (9/5) + 32) * 10) / 10)
        forecastLow = (isFahrenheit ? Math.round(ds.daily.data[0].temperatureLow * 10) / 10 : Math.round((ds.daily.data[0].temperatureLow * (9/5) + 32) * 10) / 10)
		rainTomorrow = (!ds.daily.data[1].precipProbability ? 0 : Math.round(ds.daily.data[1].precipProbability * 1000) /10)
		rainDayAfterTomorrow = (!ds.daily.data[2].precipProbability ? 0 : Math.round(ds.daily.data[2].precipProbability * 1000) /10)
        if (!futime || !sunriseTime || !sunsetTime || !noonTime ||
            !twilight_begin || !twilight_end || !condition_code || !cloud || !tz_id) {
            log.debug "Weather-Display Driver - DEBUG: futime: $futime"
            log.debug "Weather-Display Driver - DEBUG: twilight_begin: $twilight_begin"
            log.debug "Weather-Display Driver - DEBUG: sunriseTime: $sunriseTime"
            log.debug "Weather-Display Driver - DEBUG: noonTime: $noonTime"
            log.debug "Weather-Display Driver - DEBUG: sunsetTime: $sunsetTime"
            log.debug "Weather-Display Driver - DEBUG: twilight_end: $twilight_end"
            log.debug "Weather-Display Driver - DEBUG: condition_code: $condition_code"
            log.debug "Weather-Display Driver - DEBUG: cloud: $cloud"
            log.debug "Weather-Display Driver - DEBUG: tz_id: $tz_id"
            lux = null
        } else {
            is_day = null
            lux = estimateLux(futime, sunriseTime, sunsetTime, noonTime, twilight_begin, twilight_end, condition_code, cloud, tz_id)
           if(state.sourceImg==false){
                imgName = getImgName(condition_code, is_day)
                condition_icon = '<img src=' + imgName + '>'
                condition_iconWithText = "<img src=" + imgName + "><br>" + condition_text
                condition_icon_url = imgName
                condition_icon_only = imgName.split("/")[-1].replaceFirst("\\?raw=true","")
                fimgName = getImgName(forecast_code, is_day)
                forecast_icon = '<img src=' + fimgName + '>'
                forecast_iconWithText = "<img src=" + fimgName + "><br>" + forecast_text
                forecast_icon_url = fimgName
                forecast_icon_only = fimgName.split("/")[-1].replaceFirst("\\?raw=true","")
            } else {
                condition_icon = '<img src=https://icons.wxug.com/i/c/a/' + condition_code + '.gif>'
                condition_iconWithText = '<img src=https://icons.wxug.com/i/c/a/' + condition_code + '.gif><br>' + condition_text
                condition_icon_url = 'https://icons.wxug.com/i/c/a/' + condition_code +'.gif'
                condition_icon_only = condition_code +'.gif'
                forecast_icon = '<img src=https://icons.wxug.com/i/c/a/' + forecast_code + '.gif>'
                forecast_iconWithText = '<img src=https://icons.wxug.com/i/c/a/' + forecast_code + '.gif><br>' + forecast_text
                forecast_icon_only = forecast_code
                forecast_icon_url = 'https://icons.wxug.com/i/c/a/' + forecast_code + '.gif'
            }
        }
        if(state.sourceIllumination==false) {
            illuminance = lux
            lux = lux
            sendEvent(name: "lux", value: lux, unit: 'lux', displayed: true)
            sendEvent(name: "illuminance", value: illuminance, unit: 'lux', displayed: true)
        }
    }	
// *****   present Forecast Variables
    sendEvent(name: "alert", value: "$alert", displayed: true)  
    sendEvent(name: "twilight_begin", value: TwB.format("${timeFormat}"), displayed: true)
    sendEvent(name: "twilight_end", value: TwE.format("${timeFormat}"), displayed: true)
    sendEvent(name: "sunriseTime", value: sR.format("${timeFormat}"), displayed: true)
    sendEvent(name: "sunsetTime", value: sS.format("${timeFormat}"), displayed: true)
    sendEvent(name: "localSunset", value: sS.format("${timeFormat}"), descriptionText: "Sunset today at is $localSunset", displayed: false) // only needed for SmartTiles dashboard
    sendEvent(name: "localSunrise", value: sR.format("${timeFormat}"), descriptionText: "Sunrise today is at $localSunrise", displayed: false) // only needed for SmartTiles dashboard
    sendEvent(name: "illuminance", value: illuminance, unit: 'lux', displayed: true)
    sendEvent(name: "lux", value: lux, unit: 'lux', displayed: true)    
    sendEvent(name: "last_poll_Forecast", value: futime.format("${dateFormat}") + ", " + futime.format("${timeFormat}"), displayed: true)
    sendEvent(name: "last_observation_Forecast", value: fotime.format("${dateFormat}") + ", " + fotime.format("${timeFormat}"), displayed: true)    
    sendEvent(name: "condition_text", value: condition_text, displayed: true)
    sendEvent(name: "icon", value: condition_icon, displayed: true)
    sendEvent(name: "iconWithText", value: condition_iconWithText, displayed: true)    
    sendEvent(name: "icon_url", value: condition_icon_url, displayed: false)
    sendEvent(name: "weather", value: weather, displayed: true)
    sendEvent(name: "weatherIcon", value: weatherIcon, displayed: false) // only needed for SmartTiles dashboard
    sendEvent(name: "forecastIcon", value: forecastIcon, displayed: false) // only needed for ShaptTools.io    
	sendEvent(name: "cloud", value: "${cloud}", unit: "%", displayed: true)
    sendEvent(name: "vis", value: vis, unit: (isDistanceMetric ? 'KM' : 'MILES'), displayed: true)
    if(state.sourceUV==false){
    	sendEvent(name: "UV", value: UV, displayed: true)
    }
    sendEvent(name: "percentPrecip", value: percentPrecip, displayed: true) // only needed for SmartTiles dashboard
    sendEvent(name: "chanceOfRain", value: chanceOfRain, displayed: false)
    sendEvent(name: "forecastHigh", value: forecastHigh, unit: (isFahrenheit ? '°F' : '°C'), displayed: true)
    sendEvent(name: "forecastLow", value: forecastLow, unit: (isFahrenheit ? '°F' : '°C'), displayed: true)
    sendEvent(name: "rainTomorrow", value: rainTomorrow, unit: (state.extSource == 4 ? '%' : (isRainMetric ? 'mm' : 'in')), displayed: true)
    sendEvent(name: "rainDayAfterTomorrow", value: rainDayAfterTomorrow, unit: (state.extSource == 4 ? '%' : (isRainMetric ? 'mm' : 'in')), displayed: true)
    if(state.sourcefeelsLike==false){
    	sendEvent(name: "feelsLike", value: ffeelsLike, unit: (isFahrenheit ? '°F' : '°C'), displayed: true) // only needed for SmartTiles dashboard
	} else {
		sendEvent(name: "feelsLike", value: sfeelsLike, unit: (isFahrenheit ? '°F' : '°C'), displayed: true) // only needed for SmartTiles dashboard
	}
// ***** Build Summry Display variables  *****    
    Summary_last_poll_time = (sutime > futime ? "${sutime.format("${timeFormat}")}" : "${futime.format("${timeFormat}")}")
    Summary_last_poll_date = (sutime > futime ? "${sutime.format("${dateFormat}")}" : "${futime.format("${dateFormat}")}")
    if(state.sourcefeelsLike==false){
        Summary_feelsLike =  ffeelsLike + (isFahrenheit ? '°F' : '°C')
    } else {
        Summary_feelsLike = sfeelsLike + (isFahrenheit ? '°F' : '°C')
    }                
    Summary_forecastTemp = " with a high of ${forecastHigh}" + (isFahrenheit ? '°F' : '°C') + " and a low of ${forecastLow}" + (isFahrenheit ? '°F. ' : '°C. ')
    if(state.extSource == 2){Summary_precip = "There is a ${percentPrecip}% chance of precipitation. "} 
    if(state.extSource == 3){Summary_precip = ""}
    Summary_vis = "Visibility is around ${vis}" + (isDistanceMetric ? " kilometers." : " miles. ")
    SummaryMessage(state.summaryType, city, lstate, Summary_last_poll_date, Summary_last_poll_time, condition_text, "${humidity}"+'%', "${temperature}"+(isFahrenheit ? '°F. ' : '°C. '), Summary_forecastTemp, Summary_feelsLike, "${wind_string}", "${wind_gust}"+(isDistanceMetric ? " KPH" : " MPH"), "${wind_dir}", Summary_precip, Summary_vis, alert)
	def mytext = "${city}" + ', ' + "${lstate}"
		mytext+='<br>' + '<span style = ' + (!possAlert ? '\"font-size:1em;\">' : '\"font-size:.65em;\">')  + "${condition_text}" + (!possAlert ? "" : " | ${alert}" ) + '</span>'
		mytext+='<br>' + "${temperature}" + (isFahrenheit ? '&deg;F ' : '&deg;C ') + '<span style = \"font-size:.5em;\">(Feels like ' + "${Summary_feelsLike}" + ')</span>  ' + "${humidity}" + '%<span style = \"font-size:.5em;\"> Humidity</span>'
		mytext+='<br>' + sR.format("${timeFormat}") + ' <img style="height:1.5em" src=' + "${condition_icon_url}" + '> ' + sS.format("${timeFormat}")
		mytext+='<br>' + '<span style = \"font-size:.65em;\">' + "${wind_dir} wind: " + (Math.round("${wind}".toDouble()) == 0 ? 'calm' : "${wind} " + (isDistanceMetric ? 'KPH' : 'MPH')) + ', gusts: ' + "${wind_gust}" + (isDistanceMetric ? " KPH" : " MPH") + '</span>'
	sendEvent(name: "myTile", value: mytext, displayed: true)

	return 
}

private SummaryMessage(SType, Scity, Sstate, Slast_poll_date, Slast_poll_time, Scondition_text, SHumidity, Stemp, SforecastTemp, SfeelsLike, Swind, Swind_gust, Swind_dir, Sprecip, Svis, Salert){   
	if(SType == true){
        sendEvent(name: "weatherSummary", value: "Weather summary for ${Scity}, ${Sstate} updated at ${Slast_poll_time} on ${Slast_poll_date}. "
		+ ((!Scondition_text) ? "" : "${Scondition_text}") + ((!SforecastTemp) ? "" : "${SforecastTemp}") + "Humidity is currently around ${SHumidity} and temperature is ${Stemp} " 
		+ "The temperature feels like it's ${SfeelsLike}. ${Swind}"
		+ ", with gusts up to ${Swind_gust}. " + ((!Sprecip) ? "" : "${Sprecip}") + ((!Svis) ? "" : "${Svis}") + ((!Salert) ? "" : " ${Salert}"), displayed: true
		)
    } else {
		sendEvent(name: "weatherSummary", value: ((!Scondition_text) ? "" : "${Scondition_text}")((!SforecastTemp) ? "" : "${SforecastTemp}")
		+ " Humidity: ${SHumidity}. Temperature: ${Stemp}. Wind: ${Swind}; Gust: ${Swind_gust}.", displayed: true
		)  
	}
	return
}
public PollStationAndForecast(){
    if(state.extSource == 1){PollStationNow()}
    if(state.extSource >= 2){PollExternal()}                 
}

private getSunriseAndSunset(latitude, longitude, forDate)	{
    LOGINFO("SunriseAndSunset: lat: $latitude, lon: $longitude, forDate: $forDate")
    log.info "Weather-Display Driver - INFO: Sunrise-Sunset.org: Executing 'poll', location: ${location.name}"
    params = [ uri: "http://api.sunrise-sunset.org/json?lat=$latitude&lng=$longitude&date=$forDate&formatted=0" ]
    sunRiseAndSet = [:]
    try {
        httpGet(params)		{ resp -> sunRiseAndSet = resp.data }
    } catch (e) { log.error "Weather-Display Driver - ERROR: http call failed for sunrise and sunset api: $e" }
    return sunRiseAndSet
}

private estimateLux(localTime, sunriseTime, sunsetTime, noonTime, twilight_begin, twilight_end, ccode, cloud, tz_id)     {
    LOGINFO("estimateLux: localTime: $localTime, sunriseTime: $sunriseTime, sunsetTime: $sunsetTime, noonTime: $noonTime, twilight_begin: $twilight_begin, twilight_end: $twilight_end")
    LOGINFO("estimateLux (cont): condition_code: $ccode, cloud: $cloud, tz_id: $tz_id")
    tZ = TimeZone.getDefault()

    lux = 0l
    aFCC = true
    l

    if (timeOfDayIsBetween(sunriseTime, noonTime, localTime, tZ))      {
    LOGINFO("between sunrise and noon")
        l = (((localTime.getTime() - sunriseTime.getTime()) * 10000f) / (noonTime.getTime() - sunriseTime.getTime()))
        lux = (l < 50f ? 50l : l.trunc(0) as long)
        if(state.extSource != 3){is_day = 1}
    }
    else if (timeOfDayIsBetween(noonTime, sunsetTime, localTime, tZ))      {
        LOGINFO("between noon and sunset")
        l = (((sunsetTime.getTime() - localTime.getTime()) * 10000f) / (sunsetTime.getTime() - noonTime.getTime()))
        lux = (l < 50f ? 50l : l.trunc(0) as long)
        if(state.extSource != 3){is_day = 1}
    }
    else if (timeOfDayIsBetween(twilight_begin, sunriseTime, localTime, tZ))      {
        LOGINFO("between sunrise and twilight")
        l = (((localTime.getTime() - twilight_begin.getTime()) * 50f) / (sunriseTime.getTime() - twilight_begin.getTime()))
        lux = (l < 10f ? 10l : l.trunc(0) as long)
        if(state.extSource != 3){is_day = 0}
    }
    else if (timeOfDayIsBetween(sunsetTime, twilight_end, localTime, tZ))      {
        LOGINFO("between sunset and twilight")
        l = (((twilight_end.getTime() - localTime.getTime()) * 50f) / (twilight_end.getTime() - sunsetTime.getTime()))
        lux = (l < 10f ? 10l : l.trunc(0) as long)
        if(state.extSource != 3){is_day = 0}
    }
    else if (!timeOfDayIsBetween(twilight_begin, twilight_end, localTime, tZ))      {
        LOGINFO("between non-twilight")
        lux = 5l
        aFCC = false
        if(state.extSource != 3){is_day = 0}
    }

    cC = ccode
    cCT = ''
    cCF
    if(aFCC){
		if(state.extSource == 1){
            cCF = ((100 - (cloud.toInteger() / 3d)) / 100).round(1)
            cCT = 'using cloud cover'
		}
    	if(state.extSource >= 2){
			if(state.extSource == 2){
				LUitem = LUTable.find{ it.wuphrase == ccode && it.day == 1 }
			} else {
				LUitem = LUTable.find{ it.xucode == ccode && it.day == 1 }            
			}
			if (LUitem && (ccode != "unknown"))    {
				cCF = (LUitem ? LUitem.luxpercent : 0)
				cCT = (LUitem ? LUitem.wuphrase : 'unknown')
			} else    {
				cCF = ((100 - (cloud.toInteger() / 3d)) / 100).round(1)
				cCT = 'using cloud cover'
			}
		}
    } else {
		cCF = 1.0
		cCT = 'night time now'
    }
    lux = (lux * cCF) as long
    LOGINFO("condition: $cC | condition text: $cCT | condition factor: $cCF | lux: $lux")
    return lux
}

private timeOfDayIsBetween(fromDate, toDate, checkDate, timeZone)     {
    return (!checkDate.before(fromDate) && !checkDate.after(toDate))
}

// define debug action ***********************************
def logCheck(){
    if(state.logSet == true){
        log.info "Weather-Display Driver - INFO:  All Logging Enabled"
    }
    else if(state.logSet == false){
        log.info "Weather-Display Driver - INFO:  Further Logging Disabled"
    }
}
def LOGDEBUG(txt){
    try {
    	if(state.logSet == true){ log.debug("Weather-Display Driver - DEBUG:  ${txt}") }
    } catch(ex) {
    	log.error("LOGDEBUG unable to output requested data!")
    }
}

def LOGINFO(txt){
    try {
    	if(state.logSet == true){log.info("Weather-Display Driver - INFO:  ${txt}") }
    } catch(ex) {
    	log.error("LOGINFO unable to output requested data!")
    }
}

public setDateTimeFormats(formatselector){
    switch(formatselector) {
        case 1: DTFormat = "M/d/yyyy h:mm a";   dateFormat = "M/d/yyyy";   timeFormat = "h:mm a"; break;
        case 2: DTFormat = "M/d/yyyy HH:mm";    dateFormat = "M/d/yyyy";   timeFormat = "HH:mm";  break;
    	case 3: DTFormat = "MM/dd/yyyy h:mm a"; dateFormat = "MM/dd/yyyy"; timeFormat = "h:mm a"; break;
    	case 4: DTFormat = "MM/dd/yyyy HH:mm";  dateFormat = "MM/dd/yyyy"; timeFormat = "HH:mm";  break;
		case 5: DTFormat = "d/M/yyyy h:mm a";   dateFormat = "d/M/yyyy";   timeFormat = "h:mm a"; break;
    	case 6: DTFormat = "d/M/yyyy HH:mm";    dateFormat = "d/M/yyyy";   timeFormat = "HH:mm";  break;
    	case 7: DTFormat = "dd/MM/yyyy h:mm a"; dateFormat = "dd/MM/yyyy"; timeFormat = "h:mm a"; break;
        case 8: DTFormat = "dd/MM/yyyy HH:mm";  dateFormat = "dd/MM/yyyy"; timeFormat = "HH:mm";  break;
    	case 9: DTFormat = "yyyy/MM/dd HH:mm";  dateFormat = "yyyy/MM/dd"; timeFormat = "HH:mm";  break;
    	default: DTFormat = "M/d/yyyy h:mm a";  dateFormat = "M/d/yyyy";   timeFormat = "h:mm a"; break;
	}
}

private getImgName(wCode, is_day){
    url = state.iconStore
    LOGINFO("wCode: " + wCode + "  is_day: " + is_day + " iconStore: " + state.iconState)
    LUitem = LUTable.find{ (state.extSource<=2 ? it.wuphrase : (state.extSource == 3 ? it.xucode : it.wucode)) == wCode && it.day == is_day }    
    LOGINFO("image url: " + url + (LUitem ? LUitem.img : 'na.png') + "?raw=true")
    return (url + (LUitem ? LUitem.img : 'na.png') + (((state.iconStore.toLowerCase().contains('://github.com/')) && (state.iconStore.toLowerCase().contains('/blob/master/'))) ? "?raw=true" : ""))    
}

private getWUCodeName(wCode, is_day){
    LOGINFO("wCode: " + wCode + "  is_day: " + is_day)
    LUitem = LUTable.find{ it.xucode == wCode && it.day == is_day }    
    LOGINFO("APIXU Code: " + wCode + "WU Code: " + (LUitem ? LUitem.wucode : 'unknown'))
    return (LUitem ? LUitem.wucode : 'unknown')   
}

private getDSCodeName(wCode, is_day){
    LOGINFO("wCode: " + wCode + "  is_day: " + is_day)
    LUitem = LUTable.find{ it.wucode == wCode && it.day == is_day }    
    LOGINFO("DarkSky Code: " + wCode + "DS Code: " + (LUitem ? LUitem.wucode : 'unknown'))
    return (LUitem ? LUitem.wucode : 'unknown')   
}

private getDSTextName(wCode){
    LOGINFO("wCode: " + wCode)
    LUitem = LUTable.find{ it.wucode == wCode }    
    LOGINFO("DarkSky Code: " + wCode + "DS Code: " + (LUitem ? LUitem.wucode : 'unknown'))
    return (LUitem ? LUitem.wuphrase : 'unknown')   
}

@Field final List    LUTable =     [
          [xucode: 1000, wuphrase: 'Clear', wucode: 'sunny', day: 1, img: '32.png', luxpercent: 1],   // DAY: Sunny - Clear
     [xucode: 1003, wuphrase: 'Partly Cloudy', wucode: 'partlycloudy', day: 1, img: '30.png', luxpercent: 0.8],   // DAY: Partly cloudy
     [xucode: 1003, wuphrase: 'Scattered Clouds', wucode: 'partlycloudy', day: 1, img: '30.png', luxpercent: 0.8],   // DAY: Partly cloudy - Scattered Clouds
     [xucode: 1006, wuphrase: 'Mostly Cloudy', wucode: 'cloudy', day: 1, img: '26.png', luxpercent: 0.6],   // DAY: Cloudy - Mostly Cloudy
     [xucode: 1009, wuphrase: 'Overcast', wucode: 'cloudy', day: 1, img: '28.png', luxpercent: 0.6],   // DAY: Overcast
     [xucode: 1030, wuphrase: 'Hazy', wucode: 'hazy', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Mist
     [xucode: 1063, wuphrase: 'Rain', wucode: 'rain', day: 1, img: '39.png', luxpercent: 0.5],   // DAY: Patchy rain possible - Rain
     [xucode: 1066, wuphrase: 'Light Thunderstorms and Snow', wucode: 'chancesnow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Patchy snow possible - Light Thunderstorms and Snow
     [xucode: 1069, wuphrase: 'Ice Pellets', wucode: 'sleet', day: 1, img: '17.png', luxpercent: 0.5],   // DAY: Patchy sleet possible - Ice Pellets
     [xucode: 1072, wuphrase: 'Light Freezing Drizzle', wucode: 'sleet', day: 1, img: '6.png', luxpercent: 0.3],   // DAY: Patchy freezing drizzle possible - Light Freezing Drizzle
     [xucode: 1087, wuphrase: 'Thunderstorm', wucode: 'tstorms', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Thundery outbreaks possible - Thunderstorm
     [xucode: 1114, wuphrase: 'Blowing Snow', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow
     [xucode: 1114, wuphrase: 'Heavy Blowing Snow', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Heavy Blowing Snow
     [xucode: 1114, wuphrase: 'Heavy Low Drifting Snow', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Heavy Low Drifting Snow
     [xucode: 1114, wuphrase: 'Heavy Snow Blowing Snow Mist', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Heavy Snow Blowing Snow Mist
     [xucode: 1114, wuphrase: 'Light Blowing Snow', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Light Blowing Snow
     [xucode: 1114, wuphrase: 'Light Low Drifting Snow', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Light Low Drifting Snow
     [xucode: 1114, wuphrase: 'Light Snow Blowing Snow Mist', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Light Snow Blowing Snow Mist
     [xucode: 1114, wuphrase: 'Low Drifting Snow', wucode: 'snow', day: 1, img: '15.png', luxpercent: 0.3],   // DAY: Blowing snow - Low Drifting Snow
     [xucode: 1117, wuphrase: 'Heavy Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Blizzard - Heavy Snow
     [xucode: 1135, wuphrase: 'Fog', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog
     [xucode: 1135, wuphrase: 'Fog Patches', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Fog Patches
     [xucode: 1135, wuphrase: 'Hazy', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Haze
     [xucode: 1135, wuphrase: 'Heavy Fog', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Heavy Fog
     [xucode: 1135, wuphrase: 'Heavy Fog Patches', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Heavy Fog Patches
     [xucode: 1135, wuphrase: 'Light Fog', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Light Fog
     [xucode: 1135, wuphrase: 'Light Fog Patches', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Light Fog Patches
     [xucode: 1135, wuphrase: 'Mist', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Mist
     [xucode: 1135, wuphrase: 'Partial Fog', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Partial Fog
     [xucode: 1135, wuphrase: 'Shallow Fog', wucode: 'fog', day: 1, img: '20.png', luxpercent: 0.2],   // DAY: Fog - Shallow Fog
     [xucode: 1147, wuphrase: 'Freezing Fog', wucode: 'fog', day: 1, img: '21.png', luxpercent: 0.2],   // DAY: Freezing fog
     [xucode: 1147, wuphrase: 'Heavy Freezing Fog', wucode: 'fog', day: 1, img: '21.png', luxpercent: 0.2],   // DAY: Freezing fog - Heavy Freezing Fog
     [xucode: 1147, wuphrase: 'Light Freezing Fog', wucode: 'fog', day: 1, img: '21.png', luxpercent: 0.2],   // DAY: Freezing fog - Light Freezing Fog
     [xucode: 1147, wuphrase: 'Patches of Fog', wucode: 'fog', day: 1, img: '21.png', luxpercent: 0.2],   // DAY: Freezing fog - Patches of Fog
     [xucode: 1150, wuphrase: 'Light Drizzle', wucode: 'rain', day: 1, img: '9.png', luxpercent: 0.5],   // DAY: Patchy light drizzle - Light Drizzle
     [xucode: 1153, wuphrase: 'Drizzle', wucode: 'rain', day: 1, img: '9.png', luxpercent: 0.5],   // DAY: Light drizzle - Drizzle
     [xucode: 1153, wuphrase: 'Light Drizzle', wucode: 'rain', day: 1, img: '9.png', luxpercent: 0.5],   // DAY: Light drizzle
     [xucode: 1153, wuphrase: 'Light Mist', wucode: 'rain', day: 1, img: '9.png', luxpercent: 0.5],   // DAY: Light drizzle - Light Mist
     [xucode: 1153, wuphrase: 'Light Rain Mist', wucode: 'rain', day: 1, img: '9.png', luxpercent: 0.5],   // DAY: Light drizzle - Light Rain Mist
     [xucode: 1153, wuphrase: 'Rain Mist', wucode: 'rain', day: 1, img: '9.png', luxpercent: 0.5],   // DAY: Light drizzle - Rain Mist
     [xucode: 1168, wuphrase: 'Freezing Drizzle', wucode: 'sleet', day: 1, img: '8.png', luxpercent: 0.3],   // DAY: Freezing drizzle
     [xucode: 1168, wuphrase: 'Heavy Freezing Drizzle', wucode: 'sleet', day: 1, img: '6.png', luxpercent: 0.3],   // DAY: Freezing drizzle - Heavy Freezing Drizzle
     [xucode: 1168, wuphrase: 'Light Freezing Drizzle', wucode: 'sleet', day: 1, img: '8.png', luxpercent: 0.3],   // DAY: Freezing drizzle - Light Freezing Drizzle
     [xucode: 1171, wuphrase: 'Heavy Freezing Drizzle', wucode: 'sleet', day: 1, img: '6.png', luxpercent: 0.3],   // DAY: Heavy freezing drizzle
     [xucode: 1180, wuphrase: 'Light Rain', wucode: 'rain', day: 1, img: '11.png', luxpercent: 0.5],   // DAY: Patchy light rain - Light Rain
     [xucode: 1183, wuphrase: 'Heavy Mist', wucode: 'rain', day: 1, img: '11.png', luxpercent: 0.5],   // DAY: Light rain - Heavy Mist
     [xucode: 1183, wuphrase: 'Heavy Rain Mist', wucode: 'rain', day: 1, img: '11.png', luxpercent: 0.5],   // DAY: Light rain - Heavy Rain Mist
     [xucode: 1183, wuphrase: 'Light Rain', wucode: 'rain', day: 1, img: '11.png', luxpercent: 0.5],   // DAY: Light rain
     [xucode: 1186, wuphrase: 'Rain', wucode: 'rain', day: 1, img: '39.png', luxpercent: 0.5],   // DAY: Moderate rain at times - Rain
     [xucode: 1189, wuphrase: 'Heavy Drizzle', wucode: 'rain', day: 1, img: '5.png', luxpercent: 0.5],   // DAY: Moderate rain - Heavy Drizzle
     [xucode: 1189, wuphrase: 'Rain', wucode: 'rain', day: 1, img: '5.png', luxpercent: 0.5],   // DAY: Moderate rain - Rain
     [xucode: 1192, wuphrase: 'Heavy Rain', wucode: 'rain', day: 1, img: '40.png', luxpercent: 0.5],   // DAY: Heavy rain at times - Heavy Rain
     [xucode: 1195, wuphrase: 'Heavy Rain', wucode: 'rain', day: 1, img: '40.png', luxpercent: 0.5],   // DAY: Heavy rain
     [xucode: 1198, wuphrase: 'Light Freezing Rain', wucode: 'sleet', day: 1, img: '6.png', luxpercent: 0.3],   // DAY: Light freezing rain
     [xucode: 1201, wuphrase: 'Heavy Freezing Rain', wucode: 'rain', day: 1, img: '6.png', luxpercent: 0.5],   // DAY: Moderate or heavy freezing rain - Heavy Freezing Rain
     [xucode: 1204, wuphrase: 'Hail', wucode: 'sleet', day: 1, img: '35.png', luxpercent: 0.5],   // DAY: Light sleet - Hail
     [xucode: 1204, wuphrase: 'Light Hail', wucode: 'sleet', day: 1, img: '35.png', luxpercent: 0.5],   // DAY: Light sleet - Light Hail
     [xucode: 1204, wuphrase: 'Light Ice Crystals', wucode: 'sleet', day: 1, img: '25.png', luxpercent: 0.5],   // DAY: Light sleet - Light Ice Crystals
     [xucode: 1204, wuphrase: 'Light Ice Pellets', wucode: 'sleet', day: 1, img: '35.png', luxpercent: 0.5],   // DAY: Light sleet - Light Ice Pellets
     [xucode: 1204, wuphrase: 'Light Snow Grains', wucode: 'sleet', day: 1, img: '35.png', luxpercent: 0.5],   // DAY: Light sleet - Light Snow Grains
     [xucode: 1204, wuphrase: 'Small Hail', wucode: 'sleet', day: 1, img: '35.png', luxpercent: 0.5],   // DAY: Light sleet - Small Hail
     [xucode: 1207, wuphrase: 'Heavy Ice Crystals', wucode: 'sleet', day: 1, img: '25.png', luxpercent: 0.5],   // DAY: Moderate or heavy sleet - Heavy Ice Crystals
     [xucode: 1210, wuphrase: 'Light Snow', wucode: 'snow', day: 1, img: '13.png', luxpercent: 0.3],   // DAY: Patchy light snow - Light Snow
     [xucode: 1213, wuphrase: 'Light Snow', wucode: 'snow', day: 1, img: '14.png', luxpercent: 0.3],   // DAY: Light snow
     [xucode: 1216, wuphrase: 'Snow', wucode: 'snow', day: 1, img: '7.png', luxpercent: 0.3],   // DAY: Patchy moderate snow - Snow
     [xucode: 1219, wuphrase: 'Snow', wucode: 'snow', day: 1, img: '7.png', luxpercent: 0.3],   // DAY: Moderate snow - Snow
     [xucode: 1222, wuphrase: 'Heavy Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Patchy heavy snow - Heavy Snow
     [xucode: 1225, wuphrase: 'Heavy Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Heavy snow
     [xucode: 1237, wuphrase: 'Ice Crystals', wucode: 'sleet', day: 1, img: '17.png', luxpercent: 0.5],   // DAY: Ice pellets - Ice Crystals
     [xucode: 1237, wuphrase: 'Ice Pellets', wucode: 'sleet', day: 1, img: '17.png', luxpercent: 0.5],   // DAY: Ice pellets
     [xucode: 1237, wuphrase: 'Snow Grains', wucode: 'sleet', day: 1, img: '17.png', luxpercent: 0.5],   // DAY: Ice pellets - Snow Grains
     [xucode: 1240, wuphrase: 'Light Rain Showers', wucode: 'rain', day: 1, img: '10.png', luxpercent: 0.5],   // DAY: Light rain shower - Light Rain Showers
     [xucode: 1243, wuphrase: 'Heavy Rain Showers', wucode: 'rain', day: 1, img: '12.png', luxpercent: 0.5],   // DAY: Moderate or heavy rain shower - Heavy Rain Showers
     [xucode: 1243, wuphrase: 'Rain Showers', wucode: 'rain', day: 1, img: '12.png', luxpercent: 0.5],   // DAY: Moderate or heavy rain shower - Rain Showers
     [xucode: 1246, wuphrase: 'Heavy Rain Showers', wucode: 'rain', day: 1, img: '12.png', luxpercent: 0.5],   // DAY: Torrential rain shower - Heavy Rain Showers
     [xucode: 1249, wuphrase: 'Light Thunderstorms with Hail', wucode: 'sleet', day: 1, img: '5.png', luxpercent: 0.5],   // DAY: Light sleet showers - Light Thunderstorms with Hail
     [xucode: 1252, wuphrase: 'Freezing Rain', wucode: 'sleet', day: 1, img: '18.png', luxpercent: 0.5],   // DAY: Moderate or heavy sleet showers - Freezing Rain
     [xucode: 1252, wuphrase: 'Heavy Small Hail Showers', wucode: 'sleet', day: 1, img: '18.png', luxpercent: 0.5],   // DAY: Moderate or heavy sleet showers - Heavy Small Hail Showers
     [xucode: 1252, wuphrase: 'Heavy Snow Grains', wucode: 'sleet', day: 1, img: '18.png', luxpercent: 0.5],   // DAY: Moderate or heavy sleet showers - Heavy Snow Grains
     [xucode: 1252, wuphrase: 'Ice Pellet Showers', wucode: 'sleet', day: 1, img: '18.png', luxpercent: 0.5],   // DAY: Moderate or heavy sleet showers - Ice Pellet Showers
     [xucode: 1252, wuphrase: 'Small Hail Showers', wucode: 'sleet', day: 1, img: '18.png', luxpercent: 0.5],   // DAY: Moderate or heavy sleet showers - Small Hail Showers
     [xucode: 1255, wuphrase: 'Light Snow Showers', wucode: 'snow', day: 1, img: '16.png', luxpercent: 0.3],   // DAY: Light snow showers
     [xucode: 1258, wuphrase: 'Heavy Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Heavy Snow
     [xucode: 1258, wuphrase: 'Heavy Snow Showers', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Heavy Snow Showers
     [xucode: 1258, wuphrase: 'Heavy Thunderstorms and Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Heavy Thunderstorms and Snow
     [xucode: 1258, wuphrase: 'Snow Blowing Snow Mist', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Snow Blowing Snow Mist
     [xucode: 1258, wuphrase: 'Snow Showers', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Snow Showers
     [xucode: 1258, wuphrase: 'Thunderstorms and Ice Pellets', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Thunderstorms and Ice Pellets
     [xucode: 1258, wuphrase: 'Thunderstorms and Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow showers - Thunderstorms and Snow
     [xucode: 1261, wuphrase: 'Light Hail Showers', wucode: 'snow', day: 1, img: '8.png', luxpercent: 0.3],   // DAY: Light showers of ice pellets - Light Hail Showers
     [xucode: 1261, wuphrase: 'Light Ice Pellet Showers', wucode: 'snow', day: 1, img: '8.png', luxpercent: 0.3],   // DAY: Light showers of ice pellets - Light Ice Pellet Showers
     [xucode: 1261, wuphrase: 'Light Small Hail Showers', wucode: 'snow', day: 1, img: '8.png', luxpercent: 0.3],   // DAY: Light showers of ice pellets - Light Small Hail Showers
     [xucode: 1261, wuphrase: 'Light Thunderstorms with Small Hail', wucode: 'snow', day: 1, img: '8.png', luxpercent: 0.3],   // DAY: Light showers of ice pellets - Light Thunderstorms with Small Hail
     [xucode: 1264, wuphrase: 'Hail Showers', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Hail Showers
     [xucode: 1264, wuphrase: 'Heavy Hail', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Hail
     [xucode: 1264, wuphrase: 'Heavy Hail Showers', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Hail Showers
     [xucode: 1264, wuphrase: 'Heavy Ice Crystals', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Ice Crystals
     [xucode: 1264, wuphrase: 'Heavy Ice Pellet Showers', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Ice Pellet Showers
     [xucode: 1264, wuphrase: 'Heavy Ice Pellets', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Ice Pellets
     [xucode: 1264, wuphrase: 'Heavy Thunderstorms and Ice Pellets', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Thunderstorms and Ice Pellets
     [xucode: 1264, wuphrase: 'Heavy Thunderstorms with Hail', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Thunderstorms with Hail
     [xucode: 1264, wuphrase: 'Heavy Thunderstorms with Small Hail', wucode: 'sleet', day: 1, img: '4.png', luxpercent: 0.5],   // DAY: Moderate or heavy showers of ice pellets - Heavy Thunderstorms with Small Hail
     [xucode: 1264, wuphrase: 'Thunderstorms with Small Hail', wucode: 'sleet', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Moderate or heavy showers of ice pellets - Thunderstorms with Small Hail
     [xucode: 1273, wuphrase: 'Light Thunderstorm', wucode: 'chancetstorms', day: 1, img: '37.png', luxpercent: 0.2],   // DAY: Patchy light rain with thunder - Light Thunderstorm
     [xucode: 1273, wuphrase: 'Light Thunderstorms and Rain', wucode: 'chancetstorms', day: 1, img: '37.png', luxpercent: 0.2],   // DAY: Patchy light rain with thunder - Light Thunderstorms and Rain
     [xucode: 1276, wuphrase: 'Heavy Thunderstorm', wucode: 'tstorms', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Moderate or heavy rain with thunder - Heavy Thunderstorm
     [xucode: 1276, wuphrase: 'Heavy Thunderstorms and Rain', wucode: 'tstorms', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Moderate or heavy rain with thunder - Heavy Thunderstorms and Rain
     [xucode: 1276, wuphrase: 'Thunderstorm', wucode: 'tstorms', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Moderate or heavy rain with thunder - Thunderstorm
     [xucode: 1276, wuphrase: 'Thunderstorms and Rain', wucode: 'tstorms', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Moderate or heavy rain with thunder - Thunderstorms and Rain
     [xucode: 1276, wuphrase: 'Thunderstorms with Hail', wucode: 'tstorms', day: 1, img: '3.png', luxpercent: 0.3],   // DAY: Moderate or heavy rain with thunder - Thunderstorms with Hail
     [xucode: 1279, wuphrase: 'Light Thunderstorms and Ice Pellets', wucode: 'chancesnow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Patchy light snow with thunder - Light Thunderstorms and Ice Pellets
     [xucode: 1279, wuphrase: 'Light Thunderstorms and Snow', wucode: 'chancesnow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Patchy light snow with thunder - Light Thunderstorms and Snow
     [xucode: 1282, wuphrase: 'Thunderstorms and Snow', wucode: 'snow', day: 1, img: '41.png', luxpercent: 0.3],   // DAY: Moderate or heavy snow with thunder - Thunderstorms and Snow
     [xucode: 1000, wuphrase: 'Breezy', wucode: 'breezy', day: 1, img: '22.png', luxpercent: 1],   // DAY: Breezy
     [xucode: 1000, wuphrase: 'Clear', wucode: 'nt_clear', day: 0, img: '31.png', luxpercent: 0],   // NIGHT: Clear
     [xucode: 1003, wuphrase: 'Partly Cloudy', wucode: 'nt_partlycloudy', day: 0, img: '29.png', luxpercent: 0],   // NIGHT: Partly cloudy
     [xucode: 1003, wuphrase: 'Scattered Clouds', wucode: 'nt_partlycloudy', day: 0, img: '29.png', luxpercent: 0],   // NIGHT: Partly cloudy - Scattered Clouds
     [xucode: 1006, wuphrase: 'Mostly Cloudy', wucode: 'nt_cloudy', day: 0, img: '26.png', luxpercent: 0],   // NIGHT: Cloudy - Mostly Cloudy
     [xucode: 1009, wuphrase: 'Overcast', wucode: 'nt_cloudy', day: 0, img: '27.png', luxpercent: 0],   // NIGHT: Overcast
     [xucode: 1030, wuphrase: 'Hazy', wucode: 'nt_hazy', day: 0, img: '21.png', luxpercent: 0],   // NIGHT: Mist
     [xucode: 1063, wuphrase: 'Rain', wucode: 'nt_rain', day: 0, img: '45.png', luxpercent: 0],   // NIGHT: Patchy rain possible - Rain
     [xucode: 1066, wuphrase: 'Light Thunderstorms and Snow', wucode: 'nt_chancesnow', day: 0, img: '46.png', luxpercent: 0],   // NIGHT: Patchy snow possible - Light Thunderstorms and Snow
     [xucode: 1069, wuphrase: 'Ice Pellets', wucode: 'nt_sleet', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Patchy sleet possible - Ice Pellets
     [xucode: 1072, wuphrase: 'Light Freezing Drizzle', wucode: 'nt_sleet', day: 0, img: '6.png', luxpercent: 0],   // NIGHT: Patchy freezing drizzle possible - Light Freezing Drizzle
     [xucode: 1087, wuphrase: 'Thunderstorm', wucode: 'nt_tstorms', day: 0, img: '38.png', luxpercent: 0],   // NIGHT: Thundery outbreaks possible - Thunderstorm
     [xucode: 1114, wuphrase: 'Blowing Snow', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow
     [xucode: 1114, wuphrase: 'Heavy Blowing Snow', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Heavy Blowing Snow
     [xucode: 1114, wuphrase: 'Heavy Low Drifting Snow', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Heavy Low Drifting Snow
     [xucode: 1114, wuphrase: 'Heavy Snow Blowing Snow Mist', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Heavy Snow Blowing Snow Mist
     [xucode: 1114, wuphrase: 'Light Blowing Snow', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Light Blowing Snow
     [xucode: 1114, wuphrase: 'Light Low Drifting Snow', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Light Low Drifting Snow
     [xucode: 1114, wuphrase: 'Light Snow Blowing Snow Mist', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Light Snow Blowing Snow Mist
     [xucode: 1114, wuphrase: 'Low Drifting Snow', wucode: 'nt_snow', day: 0, img: '14.png', luxpercent: 0],   // NIGHT: Blowing snow - Low Drifting Snow
     [xucode: 1117, wuphrase: 'Heavy Snow', wucode: 'nt_snow', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Blizzard - Heavy Snow
     [xucode: 1135, wuphrase: 'Fog', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog
     [xucode: 1135, wuphrase: 'Fog Patches', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Fog Patches
     [xucode: 1135, wuphrase: 'Hazy', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Haze
     [xucode: 1135, wuphrase: 'Heavy Fog', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Heavy Fog
     [xucode: 1135, wuphrase: 'Heavy Fog Patches', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Heavy Fog Patches
     [xucode: 1135, wuphrase: 'Light Fog', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Light Fog
     [xucode: 1135, wuphrase: 'Light Fog Patches', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Light Fog Patches
     [xucode: 1135, wuphrase: 'Mist', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Mist
     [xucode: 1135, wuphrase: 'Partial Fog', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Partial Fog
     [xucode: 1135, wuphrase: 'Shallow Fog', wucode: 'nt_fog', day: 0, img: '20.png', luxpercent: 0],   // NIGHT: Fog - Shallow Fog
     [xucode: 1147, wuphrase: 'Freezing Fog', wucode: 'nt_fog', day: 0, img: '21.png', luxpercent: 0],   // NIGHT: Freezing fog
     [xucode: 1147, wuphrase: 'Heavy Freezing Fog', wucode: 'nt_fog', day: 0, img: '21.png', luxpercent: 0],   // NIGHT: Freezing fog - Heavy Freezing Fog
     [xucode: 1147, wuphrase: 'Light Freezing Fog', wucode: 'nt_fog', day: 0, img: '21.png', luxpercent: 0],   // NIGHT: Freezing fog - Light Freezing Fog
     [xucode: 1147, wuphrase: 'Patches of Fog', wucode: 'nt_fog', day: 0, img: '21.png', luxpercent: 0],   // NIGHT: Freezing fog - Patches of Fog
     [xucode: 1150, wuphrase: 'Light Drizzle', wucode: 'nt_rain', day: 0, img: '9.png', luxpercent: 0],   // NIGHT: Patchy light drizzle - Light Drizzle
     [xucode: 1153, wuphrase: 'Drizzle', wucode: 'nt_rain', day: 0, img: '9.png', luxpercent: 0],   // NIGHT: Light drizzle - Drizzle
     [xucode: 1153, wuphrase: 'Light Drizzle', wucode: 'nt_rain', day: 0, img: '9.png', luxpercent: 0],   // NIGHT: Light drizzle
     [xucode: 1153, wuphrase: 'Light Mist', wucode: 'nt_rain', day: 0, img: '9.png', luxpercent: 0],   // NIGHT: Light drizzle - Light Mist
     [xucode: 1153, wuphrase: 'Light Rain Mist', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Light drizzle - Light Rain Mist
     [xucode: 1153, wuphrase: 'Rain Mist', wucode: 'nt_rain', day: 0, img: '9.png', luxpercent: 0],   // NIGHT: Light drizzle - Rain Mist
     [xucode: 1168, wuphrase: 'Freezing Drizzle', wucode: 'nt_sleet', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Freezing drizzle
     [xucode: 1168, wuphrase: 'Light Freezing Drizzle', wucode: 'nt_sleet', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Freezing drizzle - Light Freezing Drizzle
     [xucode: 1171, wuphrase: 'Heavy Freezing Drizzle', wucode: 'nt_sleet', day: 0, img: '6.png', luxpercent: 0],   // NIGHT: Heavy freezing drizzle
     [xucode: 1180, wuphrase: 'Light Rain', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Patchy light rain - Light Rain
     [xucode: 1183, wuphrase: 'Heavy Mist', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Light rain - Heavy Mist
     [xucode: 1183, wuphrase: 'Heavy Rain Mist', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Light rain - Heavy Rain Mist
     [xucode: 1183, wuphrase: 'Light Rain', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Light rain
     [xucode: 1186, wuphrase: 'Rain', wucode: 'nt_rain', day: 0, img: '9.png', luxpercent: 0],   // NIGHT: Moderate rain at times - Rain
     [xucode: 1189, wuphrase: 'Heavy Drizzle', wucode: 'nt_rain', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Moderate rain - Heavy Drizzle
     [xucode: 1189, wuphrase: 'Rain', wucode: 'nt_rain', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Moderate rain - Rain
     [xucode: 1192, wuphrase: 'Heavy Rain', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Heavy rain at times - Heavy Rain
     [xucode: 1195, wuphrase: 'Heavy Rain', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Heavy rain
     [xucode: 1198, wuphrase: 'Light Freezing Rain', wucode: 'nt_sleet', day: 0, img: '6.png', luxpercent: 0],   // NIGHT: Light freezing rain
     [xucode: 1201, wuphrase: 'Heavy Freezing Rain', wucode: 'nt_rain', day: 0, img: '6.png', luxpercent: 0],   // NIGHT: Moderate or heavy freezing rain - Heavy Freezing Rain
     [xucode: 1204, wuphrase: 'Hail', wucode: 'nt_sleet', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Light sleet - Hail
     [xucode: 1204, wuphrase: 'Light Hail', wucode: 'nt_sleet', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Light sleet - Light Hail
     [xucode: 1204, wuphrase: 'Light Ice Crystals', wucode: 'nt_sleet', day: 0, img: '25.png', luxpercent: 0],   // NIGHT: Light sleet - Light Ice Crystals
     [xucode: 1204, wuphrase: 'Light Ice Pellets', wucode: 'nt_sleet', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Light sleet - Light Ice Pellets
     [xucode: 1204, wuphrase: 'Light Snow Grains', wucode: 'nt_sleet', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Light sleet - Light Snow Grains
     [xucode: 1204, wuphrase: 'Small Hail', wucode: 'nt_sleet', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Light sleet - Small Hail
     [xucode: 1207, wuphrase: 'Heavy Ice Crystals', wucode: 'nt_sleet', day: 0, img: '25.png', luxpercent: 0],   // NIGHT: Moderate or heavy sleet - Heavy Ice Crystals
     [xucode: 1210, wuphrase: 'Light Snow', wucode: 'nt_snow', day: 0, img: '13.png', luxpercent: 0],   // NIGHT: Patchy light snow - Light Snow
     [xucode: 1213, wuphrase: 'Light Snow', wucode: 'nt_snow', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Light snow
     [xucode: 1216, wuphrase: 'Snow', wucode: 'nt_snow', day: 0, img: '46.png', luxpercent: 0],   // NIGHT: Patchy moderate snow - Snow
     [xucode: 1219, wuphrase: 'Snow', wucode: 'nt_snow', day: 0, img: '7.png', luxpercent: 0],   // NIGHT: Moderate snow - Snow
     [xucode: 1222, wuphrase: 'Heavy Snow', wucode: 'nt_snow', day: 0, img: '46.png', luxpercent: 0],   // NIGHT: Patchy heavy snow - Heavy Snow
     [xucode: 1225, wuphrase: 'Heavy Snow', wucode: 'snow', day: 0, img: '16.png', luxpercent: 0],   // NIGHT: Heavy snow
     [xucode: 1237, wuphrase: 'Ice Crystals', wucode: 'nt_sleet', day: 0, img: '16.png', luxpercent: 0],   // NIGHT: Ice pellets - Ice Crystals
     [xucode: 1237, wuphrase: 'Ice Pellets', wucode: 'nt_sleet', day: 0, img: '16.png', luxpercent: 0],   // NIGHT: Ice pellets
     [xucode: 1237, wuphrase: 'Snow Grains', wucode: 'nt_sleet', day: 0, img: '16.png', luxpercent: 0],   // NIGHT: Ice pellets - Snow Grains
     [xucode: 1240, wuphrase: 'Light Rain Showers', wucode: 'nt_rain', day: 0, img: '11.png', luxpercent: 0],   // NIGHT: Light rain shower - Light Rain Showers
     [xucode: 1243, wuphrase: 'Heavy Rain Showers', wucode: 'nt_rain', day: 0, img: '40.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain shower - Heavy Rain Showers
     [xucode: 1243, wuphrase: 'Rain Showers', wucode: 'nt_rain', day: 0, img: '40.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain shower - Rain Showers
     [xucode: 1246, wuphrase: 'Heavy Rain Showers', wucode: 'nt_rain', day: 0, img: '40.png', luxpercent: 0],   // NIGHT: Torrential rain shower - Heavy Rain Showers
     [xucode: 1249, wuphrase: 'Light Thunderstorms with Hail', wucode: 'nt_sleet', day: 0, img: '5.png', luxpercent: 0],   // NIGHT: Light sleet showers - Light Thunderstorms with Hail
     [xucode: 1252, wuphrase: 'Freezing Rain', wucode: 'nt_sleet', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy sleet showers - Freezing Rain
     [xucode: 1252, wuphrase: 'Heavy Small Hail Showers', wucode: 'nt_sleet', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy sleet showers - Heavy Small Hail Showers
     [xucode: 1252, wuphrase: 'Heavy Snow Grains', wucode: 'nt_sleet', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy sleet showers - Heavy Snow Grains
     [xucode: 1252, wuphrase: 'Ice Pellet Showers', wucode: 'nt_sleet', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy sleet showers - Ice Pellet Showers
     [xucode: 1252, wuphrase: 'Small Hail Showers', wucode: 'nt_sleet', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy sleet showers - Small Hail Showers
     [xucode: 1255, wuphrase: 'Light Snow Showers', wucode: 'nt_snow', day: 0, img: '16.png', luxpercent: 0],   // NIGHT: Light snow showers
     [xucode: 1258, wuphrase: 'Heavy Snow', wucode: 'nt_snow', day: 0, img: '42.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow showers - Heavy Snow
     [xucode: 1258, wuphrase: 'Heavy Snow Showers', wucode: 'nt_snow', day: 0, img: '42.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow showers - Heavy Snow Showers
     [xucode: 1258, wuphrase: 'Snow Blowing Snow Mist', wucode: 'nt_snow', day: 0, img: '41.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow showers - Snow Blowing Snow Mist
     [xucode: 1258, wuphrase: 'Snow Showers', wucode: 'nt_snow', day: 0, img: '41.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow showers - Snow Showers
     [xucode: 1261, wuphrase: 'Light Hail Showers', wucode: 'nt_snow', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Light showers of ice pellets - Light Hail Showers
     [xucode: 1261, wuphrase: 'Light Ice Pellet Showers', wucode: 'nt_snow', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Light showers of ice pellets - Light Ice Pellet Showers
     [xucode: 1261, wuphrase: 'Light Small Hail Showers', wucode: 'nt_snow', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Light showers of ice pellets - Light Small Hail Showers
     [xucode: 1261, wuphrase: 'Light Thunderstorms with Small Hail', wucode: 'nt_snow', day: 0, img: '8.png', luxpercent: 0],   // NIGHT: Light showers of ice pellets - Light Thunderstorms with Small Hail
     [xucode: 1264, wuphrase: 'Hail Showers', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Hail Showers
     [xucode: 1264, wuphrase: 'Heavy Hail', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Hail
     [xucode: 1264, wuphrase: 'Heavy Hail Showers', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Hail Showers
     [xucode: 1264, wuphrase: 'Heavy Ice Crystals', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Ice Crystals
     [xucode: 1264, wuphrase: 'Heavy Ice Pellet Showers', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Ice Pellet Showers
     [xucode: 1264, wuphrase: 'Heavy Ice Pellets', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Ice Pellets
     [xucode: 1264, wuphrase: 'Heavy Thunderstorms and Ice Pellets', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Thunderstorms and Ice Pellets
     [xucode: 1264, wuphrase: 'Heavy Thunderstorms with Hail', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Thunderstorms with Hail
     [xucode: 1264, wuphrase: 'Heavy Thunderstorms with Small Hail', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Heavy Thunderstorms with Small Hail
     [xucode: 1264, wuphrase: 'Thunderstorms with Small Hail', wucode: 'nt_sleet', day: 0, img: '3.png', luxpercent: 0],   // NIGHT: Moderate or heavy showers of ice pellets - Thunderstorms with Small Hail
     [xucode: 1273, wuphrase: 'Light Thunderstorm', wucode: 'nt_chancetstorms', day: 0, img: '47.png', luxpercent: 0],   // NIGHT: Patchy light rain with thunder - Light Thunderstorm
     [xucode: 1273, wuphrase: 'Light Thunderstorms and Rain', wucode: 'nt_chancetstorms', day: 0, img: '47.png', luxpercent: 0],   // NIGHT: Patchy light rain with thunder - Light Thunderstorms and Rain
     [xucode: 1276, wuphrase: 'Heavy Thunderstorm', wucode: 'nt_tstorms', day: 0, img: '38.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain with thunder - Heavy Thunderstorm
     [xucode: 1276, wuphrase: 'Heavy Thunderstorms and Rain', wucode: 'nt_tstorms', day: 0, img: '38.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain with thunder - Heavy Thunderstorms and Rain
     [xucode: 1276, wuphrase: 'Thunderstorm', wucode: 'nt_tstorms', day: 0, img: '38.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain with thunder - Thunderstorm
     [xucode: 1276, wuphrase: 'Thunderstorms and Rain', wucode: 'nt_tstorms', day: 0, img: '38.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain with thunder - Thunderstorms and Rain
     [xucode: 1276, wuphrase: 'Thunderstorms with Hail', wucode: 'nt_tstorms', day: 0, img: '38.png', luxpercent: 0],   // NIGHT: Moderate or heavy rain with thunder - Thunderstorms with Hail
     [xucode: 1279, wuphrase: 'Light Thunderstorms and Ice Pellets', wucode: 'nt_chancesnow', day: 0, img: '41.png', luxpercent: 0],   // NIGHT: Patchy light snow with thunder - Light Thunderstorms and Ice Pellets
     [xucode: 1279, wuphrase: 'Light Thunderstorms and Snow', wucode: 'nt_chancesnow', day: 0, img: '41.png', luxpercent: 0],   // NIGHT: Patchy light snow with thunder - Light Thunderstorms and Snow
     [xucode: 1282, wuphrase: 'Heavy Thunderstorms and Snow', wucode: 'nt_snow', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow with thunder - Heavy Thunderstorms and Snow
     [xucode: 1282, wuphrase: 'Thunderstorms and Ice Pellets', wucode: 'nt_snow', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow with thunder - Thunderstorms and Ice Pellets
     [xucode: 1282, wuphrase: 'Thunderstorms and Snow', wucode: 'nt_snow', day: 0, img: '18.png', luxpercent: 0],   // NIGHT: Moderate or heavy snow with thunder - Thunderstorms and Snow
     [xucode: 1000, wuphrase: 'Breezy', wucode: 'nt_breezy', day: 0, img: '23.png', luxpercent: 0],   // NIGHT: Breezy
]    
//******************************************************************************************
