package biocomp.hubitatCiTest.emulation.commonApi;

import groovy.lang.Closure;

import java.util.Map;

interface BaseHttp
{
    abstract Object httpGet(String address, Closure handler)
    abstract Object httpGet(Map options, Closure handler)

    abstract void httpPost(Map options, Closure handler)
    abstract void httpPost(String address, String request, Closure handler)

    abstract void httpPostJson(Map options, Closure handler)
    abstract void httpPostJson(String uri, String body, Closure handler)
    abstract void httpPostJson(String uri, Map options, Closure handler)

    abstract void httpPutJson(String uri, String body, Closure closure)
    abstract void httpPutJson(String uri, Map body, Closure closure)
    abstract void httpPutJson(Map params, Closure closure)

    abstract void httpPut(Map uri, Closure handler)
    abstract void httpPut(String uri, String body, Closure handler)

    abstract void httpDelete(Map params, Closure closure)
}
