metadata {
	definition (name: "Device with time", namespace: "loremipsum", author: "Lorem Ipsum") {
        capability "Refresh"
	}

	preferences {
	}
}

def installed () {
    updated()
}

def updated () {
}

def parse(msg) {
}

def refresh() {
}

/**
* This function is added to the script in order to test that SandboxClassLoader
* can replace the Date class with TimeKeeper.now() in the script.
*/
Date scriptNow() {
    return new Date()
}