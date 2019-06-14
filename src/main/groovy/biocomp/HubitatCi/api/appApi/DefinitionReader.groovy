package biocomp.hubitatCi.api.appApi

interface DefinitionReader {
    /**
     * Metainformation about the appApi
     *
     * @param definitionsMap
     * @param makeContents
     * @return not sure
     */
    abstract Object definition(Map definitionsMap, Closure makeContents)

    /**
     * Metainformation about the appApi
     *
     * @param definitionsMap
     * @return not sure
     */
    abstract Object definition(Map definitionsMap)
}
