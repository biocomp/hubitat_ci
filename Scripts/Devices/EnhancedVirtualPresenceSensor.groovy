/**
* A virtual presence sensor that also implements the Switch capability, and links their state.
**/
metadata {
    definition (name: "Enhanced Virtual Presence Sensor", namespace: "hubitat_ci", author: "hubitat_ci") {
        capability "Actuator"
        capability "Switch"
        capability "PresenceSensor"
        capability "Sensor"

        command "arrived"
        command "departed"
    }
}

def parse(String description) {
    def pair = description.split(":")
    createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def arrived() {
    on()
}


def departed() {
    off()
}

def on() {
    sendEvent(name: "switch", value: "on")
sendEvent(name: "presence", value: "present")
}

def off() {
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "presence", value: "not present")
}
