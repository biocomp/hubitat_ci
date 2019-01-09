package biocomp.hubitatCiTest.emulation.commonApi

/**
 * Real methods:
 *
 * Constructors:
 * public hubitat.device.HubMultiAction(java.util.List,hubitat.device.Protocol,java.lang.String),
 * public hubitat.device.HubMultiAction(java.util.List,hubitat.device.Protocol),
 * public hubitat.device.HubMultiAction(java.util.List),
 * public hubitat.device.HubMultiAction()
 *
 * Rest:
 public void hubitat.device.HubMultiAction.add(hubitat.device.HubAction),
 public void hubitat.device.HubMultiAction.add(hubitat.device.HubMultiAction),
 public void hubitat.device.HubMultiAction.add(java.lang.String),
 public void hubitat.device.HubMultiAction.add(java.util.List),

 public java.util.List hubitat.device.HubMultiAction.getActionList(),
 public groovy.lang.MetaClass hubitat.device.HubMultiAction.getMetaClass(),
 public java.lang.Object hubitat.device.HubMultiAction.getProperty(java.lang.String),
 public java.lang.Object hubitat.device.HubMultiAction.invokeMethod(java.lang.String,java.lang.Object),
 public void hubitat.device.HubMultiAction.setProperty(java.lang.String,java.lang.Object)

 public void hubitat.device.HubMultiAction.setActionList(java.util.List),
 public void hubitat.device.HubMultiAction.setMetaClass(groovy.lang.MetaClass),
 */

trait HubMultiAction
{
    abstract void add(HubAction action)
    abstract void add(HubMultiAction action)
    abstract void add(String action)
    abstract void add(List action)

    abstract List getActionList()
    abstract void setActionList(List)
}

