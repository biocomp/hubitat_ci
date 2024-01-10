definition(
    name: 'App with subscriptions to device events',
    namespace: 'loremipsum',
    author: 'Lorem Ipsum',
    description: "A simple example app to test event subscriptions.  It makes sure a dimmer does not go below a level of 5.",
    iconUrl: '',
    iconX2Url: '',
    iconX3Url: '')

def dimmers = [
        name:                'dimmers',
        type:                'capability.switchLevel',
        title:                'Dimmers to control',
        description:        'Select the dimmers to control.',
        multiple:            true,
        required:            true
    ]

preferences {
    page(name: 'mainPage', title: 'Preferences', install: true, uninstall: true) {
        section() {
            input dimmers
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
    subscribe(dimmers, 'level', levelHandler)
    subscribe(dimmers, 'switch.on', switchOnHandler)
}

def levelHandler(evt) {
    def triggeredDevice = dimmers.find { it.deviceId == evt.deviceId }
    def newLevel = evt.value.toInteger()

    if (newLevel < 5
        && newLevel > 0
        && triggeredDevice.currentValue('switch') == 'on') {
        triggeredDevice.setLevel(5)
    }
}

def switchOnHandler(evt) {
    def triggeredDevice = dimmers.find { it.deviceId == evt.deviceId }
    def currentLevel = triggeredDevice.currentValue('level')

    if (currentLevel < 5) {
        triggeredDevice.setLevel(5)
    }
}