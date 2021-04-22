package me.biocomp.hubitat_ci.api.common_api

import groovy.transform.CompileStatic

trait BaseHttp
{
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    @CompileStatic
    abstract boolean _is_hubitat_ci_private()

    abstract Object httpGet(String address, Closure handler)
    abstract Object httpGet(Map options, Closure handler)

    /**
     * uri 	Either a URI or URL of of the endpoint to make a request from.
     * path 	Request path that is merged with the URI.
     * query 	Map of URL query parameters.
     * headers 	Map of HTTP headers.
     * contentType 	Forced response content type and request Accept header.
     * requestContentType 	Content type for the request, if it is different from the expected response content-type.
     * body 	Request body that will be encoded based on the given contentType.
     * @param options
     * @param handler
     */
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
