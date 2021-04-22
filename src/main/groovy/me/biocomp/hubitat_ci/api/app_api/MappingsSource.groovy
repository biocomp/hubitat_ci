package me.biocomp.hubitat_ci.api.app_api

interface MappingsSource {
    abstract boolean _is_hubitat_ci_private()

    /**
     * Defines mappings of http methods to actual methods in the script.
     * @param makeContents
     * @return
     */
    abstract def mappings(@DelegatesTo(Mappings) Closure makeContents)
}
