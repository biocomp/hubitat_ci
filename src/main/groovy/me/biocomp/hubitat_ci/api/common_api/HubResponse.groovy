package me.biocomp.hubitat_ci.api.common_api

trait HubResponse {
    abstract java.lang.String getBody() // Original: public java.lang.String hubitat.device.HubResponse.getBody()
    abstract java.lang.Object getData() // Original: public java.lang.Object hubitat.device.HubResponse.getData()
    abstract java.lang.String getDescription() // Original: public java.lang.String hubitat.device.HubResponse.getDescription()
    abstract java.lang.String getError() // Original: public java.lang.String hubitat.device.HubResponse.getError()
    abstract java.util.Map getHeaders() // Original: public java.util.Map hubitat.device.HubResponse.getHeaders()
    abstract java.lang.String getHubId() // Original: public java.lang.String hubitat.device.HubResponse.getHubId()
    abstract java.lang.Object getJson() // Original: public java.lang.Object hubitat.device.HubResponse.getJson()
    abstract java.lang.Integer getStatus() // Original: public java.lang.Integer hubitat.device.HubResponse.getStatus()
    abstract groovy.util.slurpersupport.GPathResult getXml() // Original: public groovy.util.slurpersupport.GPathResult hubitat.device.HubResponse.getXml()
    abstract void setError(java.lang.String a) // Original: public void hubitat.device.HubResponse.setError(java.lang.String)
    abstract void setHubId(java.lang.String a) // Original: public void hubitat.device.HubResponse.setHubId(java.lang.String)
    abstract void setJson(java.lang.Object a) // Original: public void hubitat.device.HubResponse.setJson(java.lang.Object)
    abstract void setStatus(java.lang.Integer a) // Original: public void hubitat.device.HubResponse.setStatus(java.lang.Integer)
    abstract void setXml(groovy.util.slurpersupport.GPathResult a) // Original: public void hubitat.device.HubResponse.setXml(groovy.util.slurpersupport.GPathResult)77
}
