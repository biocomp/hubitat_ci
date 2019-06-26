package biocomp.hubitatCi.api

/**
 * Interface for an object returned from input of type 'capability' or 'device'
 */
trait Device {
    abstract List<Attribute> getSupportedAttributes()
}