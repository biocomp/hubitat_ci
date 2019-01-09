package biocomp.hubitatCiTest.emulation.commonApi;

import groovy.lang.MetaMethod;

import java.util.Map;

interface BaseAsyncHttp
{
    // GET

    default void asynchttpGet(MetaMethod handlerMethod) {}
    default void asynchttpGet(Map options) {}
    default void asynchttpGet(MetaMethod handlerMethod, Map options) {}
    default void asynchttpGet(String handlerMethod, Map options) {}

    /**
     * Send an http GET request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpGet(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpGet(String handlerMethod, Map options, Map data) {}


    // POST

    default void asynchttpPost(MetaMethod handlerMethod) {}
    default void asynchttpPost(Map options) {}
    default void asynchttpPost(MetaMethod handlerMethod, Map options) {}
    default void asynchttpPost(String handlerMethod, Map options) {}

    /**
     * Send an http POST request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpPost(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpPost(String handlerMethod, Map options, Map data) {}


    // PUT

    default void asynchttpPut(MetaMethod handlerMethod) {}
    default void asynchttpPut(Map options) {}
    default void asynchttpPut(MetaMethod handlerMethod, Map options) {}
    default void asynchttpPut(String handlerMethod, Map options) {}

    /**
     * Send an http PUT request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpPut(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpPut(String handlerMethod, Map options, Map data) {}


    // DELETE

    default void asynchttpDelete(MetaMethod handlerMethod) {}
    default void asynchttpDelete(Map options) {}
    default void asynchttpDelete(MetaMethod handlerMethod, Map options) {}
    default void asynchttpDelete(String handlerMethod, Map options) {}

    /**
     * Send an http DELETE request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpDelete(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpDelete(String handlerMethod, Map options, Map data) {}


    // PATCH

    default void asynchttpPatch(MetaMethod handlerMethod) {}
    default void asynchttpPatch(Map options) {}
    default void asynchttpPatch(MetaMethod handlerMethod, Map options) {}
    default void asynchttpPatch(String handlerMethod, Map options) {}

    /**
     * Send an http PATCH request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpPatch(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpPatch(String handlerMethod, Map options, Map data) {}


    // HEAD

    // Not present? default void asynchttpHead(MetaMethod handlerMethod) {}
    default void asynchttpHead(Map options) {}
    // Not present? default void asynchttpHead(MetaMethod handlerMethod, Map options) {}
    default void asynchttpHead(String handlerMethod, Map options) {}

    /**
     * Send an http DELETE request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    // Not present? default void asynchttpHead(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpHead(String handlerMethod, Map options, Map data) {}
}
