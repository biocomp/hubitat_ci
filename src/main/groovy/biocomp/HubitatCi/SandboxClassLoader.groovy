package biocomp.hubitatCi

/* Will replace framework classes with test implementations,
    * and validate for allowed classes and methods*/
class SandboxClassLoader extends ClassLoader {
    SandboxClassLoader(ClassLoader parent) {
        super(parent)
    }

    @Override
    Class<?> loadClass(String name, boolean resolve) {
        //println "Loading ${name}"
        super.loadClass(mapClassName(name), resolve)
    }

    private static String mapClassName(String name) {
        switch (name)
        {
            case 'hubitat.device.HubAction':
                return 'biocomp.hubitatCi.emulation.commonApi.HubAction'

            case 'hubitat.device.HubResponse':
                return 'biocomp.hubitatCi.emulation.commonApi.HubResponse'

            case 'hubitat.device.Protocol':
                return 'biocomp.hubitatCi.emulation.Protocol'

            case ~/hubitat\.zwave\..*/:
                println "Boom! matching ${name} as my zwave!: ${name.replace('hubitat.zwave', 'biocomp.hubitatCi.emulation.deviceApi.zwave')}"
                return name.replace('hubitat.zwave', 'biocomp.hubitatCi.emulation.deviceApi.zwave')

            default:
                return name
        }
        //return name.replaceAll('''hubitat[\\.$]device[\\.$]''', "biocomp.hubitatCi.emulation.")
    }
}

