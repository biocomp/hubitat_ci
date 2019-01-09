package biocomp.hubitatCiTest.emulation.commonApi;

import groovy.lang.Closure;

import java.util.Map;

interface BaseHttp
{
    default Object httpGet(String address, Closure handler) { return null; }
    default Object httpGet(Map options, Closure handler) { return null; }

    default void httpPost(Map options, Closure handler) {}
    default void httpPost(String address, String request, Closure handler) {}

    default void httpPostJson(Map options, Closure handler) {}
    default void httpPostJson(String uri, String body, Closure handler) {}
    default void httpPostJson(String uri, Map options, Closure handler) {}

    default void httpPutJson(String uri, String body, Closure closure) {}
    default void httpPutJson(String uri, Map body, Closure closure) {}
    default void httpPutJson(Map params, Closure closure) {}

    default void httpPut(Map uri, Closure handler) {}
    default void httpPut(String uri, String body, Closure handler) {}

    default void httpDelete(Map params, Closure closure) {}
}
