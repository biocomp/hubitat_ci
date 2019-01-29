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
            input (name:"coolingDimmer", type: "capability.switchLevel", title: "Cooling setpoint dimmer", required: true, multiple: false)
            input (name:"heatingDimmer", type: "capability.switchLevel", title: "Heating setpoint dimmer", required: true, multiple: false)
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
    subscribe(thermostat, "thermostatCoolingSetpoint", realCoolingSetpointHandler)
    subscribe(thermostat, "thermostatHeatingSetpoint", realHeatingSetpointHandler)

    subscribe(coolingDimmer, "level", virtualCoolongSetpointHandler)
    subscribe(heatingDimmer, "level", virtualHeatingSetpointHandler)
}

def realCoolingSetpointHandler(def evt)
{
    realSetpointHandler(coolingDimmer, "coolingSetpoint")
}

def realHeatingSetpointHandler(def evt)
{
    realSetpointHandler(heatingDimmer, "heatingSetpoint")
}

def logDebug(String s)
{
    if (debugLogging) {
        log.debug s
    }
}

def realSetpointHandler(def dimmer, String thermostatSetpointName) {
    logDebug("Thermostat ${thermostatSetpointName} changed...")

    def newSetpoint = thermostat.currentValue(thermostatSetpointName)
    def currLevel = dimmer.currentLevel
    if (currLevel > newSetpoint - 0.5 && currLevel < newSetpoint + 0.5) {
        logDebug "Virtual dimmer not changed because setpoint of ${newSetpoint} is already close to virtual dimmer level of ${currLevel}"
    }
    else {
        dimmer.setLevel(newSetpoint)
        logDebug "Changed virtual setpoint dimmer level to ${newSetpoint} because thermostat setpoint changed"
    }

    logDebug("...done handling thermostat setpoint change.")
}


def virtualCoolongSetpointHandler(def evt)
{
    virtualSetpointHandler(coolingDimmer, "coolingSetpoint", { def newValue -> thermostat.setCoolingSetpoint(newValue)})
}

def virtualHeatingSetpointHandler(def evt)
{
    virtualSetpointHandler(heatingDimmer, "heatingSetpoint", { def newValue -> thermostat.setHeatingSetpoint(newValue)})
}

def virtualSetpointHandler(def dimmer, String thermostatSetpointName, Closure thermostatSetter) {
    logDebug("Virtual setpoint dimmer level changed...")

    def targetSetpoint = dimmer.currentLevel
    def currSetpoint = thermostat.currentValue(thermostatSetpointName)

    logDebug("Target setpoint = ${targetSetpoint}; current setpoint = ${currSetpoint};")

    if (currSetpoint > targetSetpoint - 0.5 && currSetpoint < targetSetpoint + 0.5) {
        logDebug "Thermostat not changed because setpoint of ${currSetpoint} is already close to virtual dimmer target of ${targetSetpoint}"
    }
    else
    {
        logDebug "Set thermostat ${thermostatSetpointName} to ${targetSetpoint} because virtual dimmer changed"

        thermostatSetter(targetSetpoint)
    }

    logDebug("...done handling virtual setpoint dimmer change.")
}