definition(
    name: 'App the uses the scheduler, and schedules recursive callbacks, up to 3 times.',
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
    state.callbackCount = 0
}

/**
* I'm using a switch.on event to trigger the scheduling of a runInMillis callback.
*/
def switchOnHandler(evt) {
    runInMillis(50, 'runInMillisHandler')
}

def runInMillisHandler(evt) {
    log.debug "runInMillisHandler() called"
    state.callbackCount++

    if (state.callbackCount < 3) {
        runInMillis(1000, 'runInMillisHandler')
    }
}
