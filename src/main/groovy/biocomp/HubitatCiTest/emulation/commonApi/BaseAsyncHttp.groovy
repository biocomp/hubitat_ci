package biocomp.hubitatCiTest.emulation.commonApi;

import groovy.lang.MetaMethod;

import java.util.Map;

interface BaseAsyncHttp
{
    // GET

    abstract void asynchttpGet(MetaMethod handlerMethod)
    abstract void asynchttpGet(Map options)
    abstract void asynchttpGet(MetaMethod handlerMethod, Map options)
    abstract void asynchttpGet(String handlerMethod, Map options)

    /**
     * Send an http GET request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    abstract void asynchttpGet(MetaMethod handlerMethod, Map options, Map data)
    abstract void asynchttpGet(String handlerMethod, Map options, Map data)


    // POST

    abstract void asynchttpPost(MetaMethod handlerMethod)
    abstract void asynchttpPost(Map options)
    abstract void asynchttpPost(MetaMethod handlerMethod, Map options)
    abstract void asynchttpPost(String handlerMethod, Map options)

    /**
     * Send an http POST request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    abstract void asynchttpPost(MetaMethod handlerMethod, Map options, Map data)
    abstract void asynchttpPost(String handlerMethod, Map options, Map data)


    // PUT

    abstract void asynchttpPut(MetaMethod handlerMethod)
    abstract void asynchttpPut(Map options)
    abstract void asynchttpPut(MetaMethod handlerMethod, Map options)
    abstract void asynchttpPut(String handlerMethod, Map options)

    /**
     * Send an http PUT request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    abstract void asynchttpPut(MetaMethod handlerMethod, Map options, Map data)
    abstract void asynchttpPut(String handlerMethod, Map options, Map data)


    // DELETE

    abstract void asynchttpDelete(MetaMethod handlerMethod)
    abstract void asynchttpDelete(Map options)
    abstract void asynchttpDelete(MetaMethod handlerMethod, Map options)
    abstract void asynchttpDelete(String handlerMethod, Map options)

    /**
     * Send an http DELETE request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    abstract void asynchttpDelete(MetaMethod handlerMethod, Map options, Map data)
    abstract void asynchttpDelete(String handlerMethod, Map options, Map data)


    // PATCH

    abstract void asynchttpPatch(MetaMethod handlerMethod)
    abstract void asynchttpPatch(Map options)
    abstract void asynchttpPatch(MetaMethod handlerMethod, Map options)
    abstract void asynchttpPatch(String handlerMethod, Map options)

    /**
     * Send an http PATCH request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    abstract void asynchttpPatch(MetaMethod handlerMethod, Map options, Map data)
    abstract void asynchttpPatch(String handlerMethod, Map options, Map data)


    // HEAD

    // Not present? abstract void asynchttpHead(MetaMethod handlerMethod)
    abstract void asynchttpHead(Map options)
    // Not present? abstract void asynchttpHead(MetaMethod handlerMethod, Map options)
    abstract void asynchttpHead(String handlerMethod, Map options)

    /**
     * Send an http DELETE request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    // Not present? abstract void asynchttpHead(MetaMethod handlerMethod, Map options, Map data)
    abstract void asynchttpHead(String handlerMethod, Map options, Map data)
}
