package biocomp.hubitatCiTest.emulation

trait Attribute
{
    /**
     * @return the data type of this Attribute. Possible types are “STRING”, “NUMBER”, “VECTOR3”, “ENUM”.
     */
    abstract String getDataType()

    abstract String getName()

    /**
     * @return list of possible values, if data type is "ENUM"
     */
    abstract List<String> getValues()
}