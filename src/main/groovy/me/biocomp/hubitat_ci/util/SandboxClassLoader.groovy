package me.biocomp.hubitat_ci.util

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
                return 'me.biocomp.hubitat_ci.api.common_api.HubAction'
                
            case 'hubitat.device.HubMultiAction':
                return 'me.biocomp.hubitat_ci.api.common_api.HubMultiAction'

            case 'hubitat.device.HubResponse':
                return 'me.biocomp.hubitat_ci.api.common_api.HubResponse'

            case 'hubitat.device.Protocol':
                return 'me.biocomp.hubitat_ci.api.Protocol'

            case ~/hubitat\.zwave\..*/:
                return name.replace('hubitat.zwave', 'me.biocomp.hubitat_ci.api.device_api.zwave')

            default:
                return name
        }
        //return name.replaceAll('''hubitat[\\.$]device[\\.$]''', "me.biocomp.hubitat_ci.api.")
    }
}

