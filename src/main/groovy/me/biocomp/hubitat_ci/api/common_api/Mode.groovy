package me.biocomp.hubitat_ci.api.common_api

trait Mode {
    /**
     * @return unique internal identifier
     */
    abstract Long id

    abstract Long locationId

    /**
     * @return user-friendly mode name
     */
    abstract String name
}

