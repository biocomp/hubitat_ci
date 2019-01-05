/**
 *  Thermosat/Dimmer Sync Helper
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
 */
definition(
        name: "Thermostat/Dimmer Sync Helper",
        namespace: "RMoRobert",
        author: "RMoRboert",
        description: "Syncs the setpoint of a thermostat and the dimmer level of a virtual switch (bidirectional) and the thermostat temperature to a virtual dimmer (unidirectional), intended for using the virtual devices with other platforms that do not natively support thermostat integration",
        category: "Convenience",
        iconUrl: "",
        iconX2Url: "",
        iconX3Url: ""
)

preferences {
    mainPage()
}

def mainPage() {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section("Choose thermostat and virtual dimmers to sync with") {
            input (name:"thermostat", type: "capability.thermostat", title: "Thermostat", required: true, multiple: false)
            input (name:"setpointDimmer", type: "capability.switchLevel", title: "Setpoint dimmer", required: true, multiple: false)
            input (name:"tempDimmer", type: "capability.switchLevel", title: "Temperature dimmer", required: true, multiple: false)
        }
        section("Logging", hideable: true, hidden: true) {
            input ("debugLogging", "bool", title: "Enable verbose/debug logging")
        }
    }
}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    unschedule()
    initialize()
}

def initialize() {
    log.debug "Initializing"
    subscribe(thermostat, "thermostatSetpoint", realSetpointHandler)
    subscribe(thermostat, "temperature", tempHandler)
    subscribe(setpointDimmer, "level", virtualSetpointHandler)
}

def realSetpointHandler(evt) {
    if (debugLogging) log.debug("Thermostat setpoint changed...")
    def newSetpoint = thermostat.currentValue("thermostatSetpoint")
    def currLevel = setpointDimmer.currentLevel
    if (currLevel > newSetpoint - 0.5 && currLevel < newSetpoint + 0.5) {
        log.debug "Virtual dimmer not changed because setpoint of ${newSetpoint} is already close to virtual dimmer level of ${currLevel}"
    }
    else {
        setpointDimmer.setLevel(newSetpoint)
        log.debug "Changed virtual setpoint dimmer level to ${newSetpoint} because thermostat setpoint changed"
    }
    if (debugLogging) log.debug("...done handling thermostat setpoint change.")
}

def tempHandler(evt){
    if (debugLogging) log.debug("Thermostat temperature changed...")
    def newTemp = thermostat.currentValue("temperature")
    newTemp = Math.round(newTemp)
    tempDimmer.setLevel(newTemp)
    log.debug "Changed virtual temperature dimmer level to ${newTemp} because thermostat temperature changed"
    if (debugLogging) log.debug("...done handling thermostat temperature change.")
}

def virtualSetpointHandler(evt) {
    if (debugLogging) log.debug("Virtual setpoint dimmer level changed...")
    def targetSetpoint = setpointDimmer.currentLevel
    def currSetpoint = thermostat.currentValue("thermostatSetpoint")
    def thermostatMode = thermostat.currentValue("thermostatMode")
    if (debugLogging) log.debug("Target setpoint = ${targetSetpoint}; current setpoint = ${currSetpoint}; thermostat mode = ${thermostatMode}")
    if (currSetpoint > targetSetpoint - 0.5 && currSetpoint < targetSetpoint + 0.5) {
        log.debug "Thermostat not changed because setpoint of ${currSetpoint} is already close to virtual dimmer target of ${targetSetpoint}"
    }
    else {
        if (thermostatMode == "cool") {
            thermostat.setCoolingSetpoint(targetSetpoint)
            log.debug "Set thermostat cooling setpoint to ${targetSetpoint} because virtual dimmer changed"
        }
        else if (thermostatMode == "heat") {
            thermostat.setHeatingSetpoint(targetSetpoint)
            log.debug "Set thermostat heating setpoint to ${targetSetpoint} because virtual dimmer changed"
        }
        else {
            log.debug "Thermostat not adjusted because not in heat or cool mode (mode = ${thermostatMode})"
        }
    }
    if (debugLogging) log.debug("...done handling virtual setpoint dimmer change.")
}