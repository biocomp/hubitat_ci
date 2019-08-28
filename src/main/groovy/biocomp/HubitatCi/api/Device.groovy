package me.biocomp.hubitat_ci.api

/**
 * Interface for an object returned from input of type 'capability' or 'device'
 */
trait Device {
    abstract List<Attribute> getSupportedAttributes()
    abstract Class getCapability()
}