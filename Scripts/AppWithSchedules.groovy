definition(
    name: 'App the uses the scheduler',
    namespace: 'loremipsum',
    author: 'Lorem Ipsum',
    description: "A simple example app to test callback scheduling.",
    iconUrl: '',
    iconX2Url: '',
    iconX3Url: '')

def switches = [
        name:                'switches',
        type:                'capability.switch',
        title:                'Switches to control',
        description:        'Select the switches to control.',
        multiple:            true,
        required:            true
    ]

preferences {
    page(name: 'mainPage', title: 'Preferences', install: true, uninstall: true) {
        section() {
            input switches
        }
    }
}

def installed() {
    initialize()
}

def updated() {
    unsubscribe()
    initialize()
}

def initialize() {
    subscribe(switches, 'switch.on', switchOnHandler)
    subscribe(switches, 'switch.off', switchOffHandler)
}

/**
* I'm using a switch.on event to trigger the scheduling of a runInMillis callback.
*/
def switchOnHandler(evt) {
    runInMillis(50, 'runInMillisHandler')
}

/**
* I'm using a switch.off event to trigger the scheduling of a recurring callback.
*/
def switchOffHandler(evt) {
    // Schedule a callback for every 5 minutes
    schedule('0 0/5 * * * ?', 'runEvery5MinutesHandler')
}

def runInMillisHandler(evt) {
    log.debug "runInMillisHandler() called"
}

def runEvery5MinutesHandler(evt) {
    log.debug "runEvery5MinutesHandler() called"
}