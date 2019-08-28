package me.biocomp.hubitat_ci.api.app_api

interface DefinitionReader {
    /**
     * Metainformation about the app_api
     *
     * @param definitionsMap
     * @param makeContents
     * @return not sure
     */
    abstract Object definition(Map definitionsMap, Closure makeContents)

    /**
     * Metainformation about the app_api
     *
     * @param definitionsMap
     * @return not sure
     */
    abstract Object definition(Map definitionsMap)
}
