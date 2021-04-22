metadata {
    definition (name: "hubitat_api_exporter_driver.groovy - use together with hubitat_api_exporter_app.groovy", namespace: "me.biocomp", author: "biocomp") {
        capability "Switch" // Important to have some non-empty capability so that we can get commands and attributes from it.
    }
}

def parse(String s) {
    return [:]
}

def getExportedClasses() {
    return [this.class, zwave.class, zigbee.class]
}

def on() {
    sendEvent(name: "switch", value: "on", isStateChange: true)
}

def off() {
    sendEvent(name: "switch", value: "off", isStateChange: true)
}