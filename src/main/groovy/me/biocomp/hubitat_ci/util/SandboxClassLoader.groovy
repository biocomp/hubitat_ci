package me.biocomp.hubitat_ci.util

import groovy.transform.CompileStatic

/* Will replace framework classes with test implementations,
    * and validate for allowed classes and methods*/
class SandboxClassLoader extends ClassLoader {
    SandboxClassLoader(ClassLoader parent) {
        super(parent)

        super.loadClass("java.util.Date", true)
    }

    @Override
    Class<?> loadClass(String name, boolean resolve) {
        super.loadClass(mapClassName(name), resolve)
    }

    @CompileStatic
    public static String mapClassName(String name, String basePackageName = "me.biocomp.hubitat_ci.api") {
        assert basePackageName != null : "Must be empty or non-empty string, but not null"
        basePackageName = (basePackageName.empty || basePackageName.endsWith('.')) ? basePackageName : basePackageName + "."
        name = name.replace("com.hubitat", "hubitat")

        switch (name)
        {
            case 'java.util.Date':
                return "me.biocomp.hubitat_ci.util.TimeKeeperDate"

            case 'hubitat.device.HubAction':
                return "${basePackageName}common_api.HubAction"

            case 'hubitat.device.HubMultiAction':
                return "${basePackageName}common_api.HubMultiAction"

            case 'hubitat.device.HubResponse':
                return "${basePackageName}common_api.HubResponse"

            case 'hubitat.device.Protocol':
                return "${basePackageName}Protocol"

            case 'hubitat.app.DeviceWrapper':
                return "${basePackageName}common_api.DeviceWrapper"

            case 'hubitat.app.EventSubscriptionWrapper':
                return "${basePackageName}common_api.EventSubscriptionWrapper"

            case 'hubitat.hub.domain.AppType':
                return "${basePackageName}common_api.AppType"

            case 'hubitat.hub.domain.State':
                return "${basePackageName}State"

            case 'hubitat.hub.domain.Capability':
                return "${basePackageName}Capability"

            case 'hubitat.hub.domain.Attribute':
                return "${basePackageName}Attribute"

            case 'hubitat.hub.domain.Command':
                return "${basePackageName}Command"

            case "hubitat.hub.domain.AppType":
                return "${basePackageName}AppType"

            case 'hubitat.hub.controller.interfaces.ChromeCast':
                return "${basePackageName}common_api.ChromeCast"

            case 'hubitat.app.DeviceWrapperList':
                return "${basePackageName}common_api.DeviceWrapperList"

            case 'hubitat.hub.domain.Event':
                return "${basePackageName}common_api.Event"

            case 'hubitat.helper.interfaces.EventStream':
                return "${basePackageName}common_api.EventStream"

            case 'hubitat.hub.domain.Hub':
                return "${basePackageName}common_api.Hub"

            case 'hubitat.hub.domain.Mode':
                return "${basePackageName}common_api.Mode"

            case 'hubitat.hub.domain.Location':
                return "${basePackageName}common_api.Location"

            case 'hubitat.hub.domain.Device':
                return "${basePackageName}domain.Device"

            case 'hubitat.app.InstalledAppWrapper':
                return "${basePackageName}common_api.InstalledAppWrapper"

            case 'hubitat.app.ChildDeviceWrapper':
                return "${basePackageName}common_api.ChildDeviceWrapper"

            case 'hubitat.hub.executor.BaseExecutor$Log':
            case 'hubitat.hub.executor.BaseExecutor.Log':
                return "${basePackageName}common_api.Log"

            case "hubitat.helper.InterfaceHelper":
                return "${basePackageName}common_api.InterfaceHelper"

            case 'hubitat.helper.interfaces.Mqtt':
                return "${basePackageName}common_api.Mqtt"

            case 'hubitat.helper.interfaces.RawSocket':
                return "${basePackageName}common_api.RawSocket"

            case 'hubitat.helper.interfaces.WebSocket':
                return "${basePackageName}common_api.WebSocket"

            case 'hubitat.hub.executor.DeviceExecutor':
                return "${basePackageName}device_api.DeviceExecutor"

            case 'hubitat.hub.executor.AppExecutor':
                return "${basePackageName}app_api.AppExecutor"

            case ~/hubitat\.zwave\..*/:
                return name.replace('hubitat.zwave', "${basePackageName}device_api.zwave")

            case ~/hubitat\.zigbee\..*/:
                return name.replace('hubitat.zigbee', "${basePackageName}device_api.zigbee")

            default:
                if (name.startsWith("hubitat")) {
                    return name.replace('hubitat.', "${basePackageName}")
                } else {
                    return name
                }
        }
        //return name.replaceAll('''hubitat[\\.$]device[\\.$]''', "me.biocomp.hubitat_ci.api.")
    }
}
