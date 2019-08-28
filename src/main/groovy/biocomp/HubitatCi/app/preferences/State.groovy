package me.biocomp.hubitat_ci.app.preferences

trait State
{
    String getId() { return metaClass.theClass.toString() }

    void finalValidation() {}

    @Override
    boolean equals(def otherState)
    {
        return id == (otherState as State).id
    }

    @Override
    int hashCode()
    {
        return id.hashCode()
    }

    abstract int getChildrenCount()
    abstract String toString()
    abstract void addChild(State state)
}