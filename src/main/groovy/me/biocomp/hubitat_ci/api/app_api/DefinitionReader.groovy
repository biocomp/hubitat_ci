package me.biocomp.hubitat_ci.api.app_api

interface DefinitionReader {
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

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
