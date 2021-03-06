definition(
		name: "New App Template",
		namespace: "GvnCampbell",
		author: "Gavin Campbell",
		description: "Just some bare bones app_api template",
		iconUrl: "",
		iconX2Url: "",
		iconX3Url: "")

preferences {
	page(name: "pageConfig")
}

def pageConfig() {
	dynamicPage(name: "pageConfig", title: "tit", install: true, uninstall: true, refreshInterval: 0) {

		section("") {
			input(name: "ventDevices", type: "capability.switch", title: "Trigger Devices", multiple: true)
			input(name: "numberOption", type: "number", defaultValue: "10", range: "1..*", title: "t", description: "d",
					required: true)
		}

	}
}

def installed() {
	log.debug "installed"
	initialize()
}
def updated() {
	log.debug "updated"
	initialize()
}
def initialize() {
	log.debug "initialize"
	log.debug "ventDevices: " + ventDevices
	log.debug "numberOption: " + numberOption

	unschedule()
	//runEvery5Minutes(checkDevices)
}
def uninstalled() {
	log.debug "uninstalled"
}

def checkDevices() {
	log.debug "checkDevices"
}