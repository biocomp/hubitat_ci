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
        assert whatLeadsToState.containsKey(name)
        assert currentStateNames == whatLeadsToState[name]

        currentStates.add(state)
        currentStateNames.add(name)

        def stateNamesBefore = currentStateNames.clone()
        def statesBefore = currentStates.clone()

        callback()

        assert stateNamesBefore == currentStateNames : "Internal test error: ${stateNamesBefore} != ${currentStateNames} - after running closure, state names are not the same as they were before"
        assert statesBefore == currentStates : "Internal test error: ${statesBefore} != ${currentStates} - after running closure, states are not the same as they were before"

        currentStates.removeLast()
        currentStateNames.removeLast()

        return state
    }

    def getState(String name)
    {
        assert currentStateNames.last() == name
        assert whatLeadsToState[name] == currentStateNames.subList(0, currentStateNames.size() - 1)

        return currentStates.last()
    }

    Map<String, List<String>> whatLeadsToState
    List<String> currentStateNames = []
    List<Object> currentStates = []
}

