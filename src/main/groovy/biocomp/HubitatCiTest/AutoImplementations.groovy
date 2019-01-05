package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.AppExecutorApi
import biocomp.hubitatCiTest.emulation.DeviceExecutorApi
import groovy.transform.AutoImplement

@AutoImplement
class AutoAppExecutor implements AppExecutorApi
{}

@AutoImplement
class AutoDeviceExecutor implements DeviceExecutorApi
{}
