package biocomp.hubitatCi.api.appApi

/**
 * Define http mappings to script's handlers.
 */
interface Mappings {
    /**
     * Adds mappings for the relativePath
     * @param relativePath
     * @param makeContents - closure needs to produce a Map with single 'actions' field that is also a Map.
     *      The following keys are valid:
     *      GET, PUT, POST, DELETE.
     *      Values are script method names to be triggered when these actions happen.
     *
     *      Example:
     *      // getFoo() and postFoo() are methods on our current script.
     *      path('/foo') { actions: [GET: "getFoo", POST: "postFoo"] }
     * @return
     */
    abstract def path(String relativePath, Closure makeContents)
}
