package biocomp.hubitatCi.util

import groovy.transform.TypeChecked

@TypeChecked
class ReaderState
{
    /**
     *
     * @param whatLeadsToState takes map in form of stateName->List of states that lead to this state.
     *      For example: [capability(): [metadata(), definition()]]
     */
    ReaderState(Map<String, List<String>> whatLeadsToState)
    {
        this.whatLeadsToState = whatLeadsToState
    }

    public <T> T withState(String name, T state, Closure callback)
    {
        assert whatLeadsToState.containsKey(name), "${name} is not supported"
        assert currentStateNames == whatLeadsToState[name], "${name} should be called inside these: ${whatLeadsToState[name]}, put it was called with this sequence: ${currentStateNames}"

        currentStates.add(state)
        currentStateNames.add(name)

        def stateNamesBefore = currentStateNames.clone()
        def statesBefore = currentStates.clone()

        if (callback) {
            callback()
        }

        assert stateNamesBefore == currentStateNames : "Internal test error: ${stateNamesBefore} != ${currentStateNames} - after running closure, state names are not the same as they were before"
        assert statesBefore == currentStates : "Internal test error: ${statesBefore} != ${currentStates} - after running closure, states are not the same as they were before"

        currentStates.removeLast()
        currentStateNames.removeLast()

        return state
    }

    private def tryGetCurrentState(String name) {
        if (currentStateNames.last() == name) {
            return currentStates.last()
        }

        return null
    }

    def getCurrentState(String name)
    {
        def state = tryGetCurrentState(name)
        assert state, "State ${name} is not a current state, you're calling it in incorrect place. Currently you're here: ${currentStateNames}."
        return state
    }

    def getOneOfParentStates(String parentStateName)
    {
        final def stateIndex = currentStateNames.indexOf(parentStateName)
        assert stateIndex != -1, "We're not within ${parentStateName}, so it can't be retieved. Currently you're here: ${currentStateNames}."
        return currentStates[stateIndex]
    }

    def getOneOfCurrentStates(String[] names)
    {
        def currentName = names.find{currentStateNames.last() == it}

        assert currentName, "Asking for one of ${names} is incorrect. Currently you're here: ${currentStateNames}."
        return currentStates.last()
    }

    Map<String, List<String>> whatLeadsToState
    List<String> currentStateNames = []
    List<Object> currentStates = []
}

