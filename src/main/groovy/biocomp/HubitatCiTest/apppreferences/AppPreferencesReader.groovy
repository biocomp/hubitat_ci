package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.HubitatAppScript
import biocomp.hubitatCiTest.emulation.AppPreferences
import groovy.transform.Canonical
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class NoneState implements State
{
    @Override
    int getChildrenCount() {
        return preferencesList.size()
    }

    String toString() { return "outside preferences()" }

    @Override
    void addChild(State state)
    {
        assert state instanceof InPreferencesState
        preferencesList << (state as InPreferencesState).preferences
    }

    List<Preferences> preferencesList = []
}

@Canonical
class Transition
{
    Transition(Class from, Class to)
    {
        this.from = from.toString()
        this.to = to.toString()
    }

    Transition(State from, State to)
    {
        this.from = from.id
        this.to = to.id
    }

    String from
    String to
}

@CompileStatic // To avoid redirecting all internal calls to HubitatAppScript's invokeMethod()
class AppPreferencesReader extends AppPreferences
{
    private static Closure none() { return { def me, State oldState, def newState ->} }

    @CompileDynamic
    private static Closure finalValidation() {
        return {def me, State oldState, def newState -> oldState.finalValidation() }
    }

    private HashMap<Transition, Closure> statesAndTransitions_ = [
            (new Transition(NoneState, InPreferencesState)): none(),
            (new Transition(InPreferencesState, NoneState)): finalValidation(),
            (new Transition(InPreferencesState, InPageState)): none(),
            (new Transition(InPageState, InPreferencesState)): finalValidation(),
            (new Transition(InPreferencesState, InSectionState)): none(),
            (new Transition(InSectionState, InPreferencesState)): finalValidation(),
            (new Transition(InPageState, InSectionState)): none(),
            (new Transition(InSectionState, InPageState)): finalValidation(),
    ] as HashMap<Transition, Closure>

    private State state_ = new NoneState()
    private List stateStack_ = [state_]

    private State tryChangeStateTo(State newState)
    {
        def found = statesAndTransitions_.get(new Transition(state_, newState))
        assert found : "You can't go into ${newState} while you're in ${state_}"
        (found as Closure)(this, state_, newState) // Transition

        State oldState = state_
        state_ = newState
        return oldState
    }

    private State pushState(State newState)
    {
        State oldState = tryChangeStateTo(newState)
        stateStack_.push(newState)
        return oldState
    }

    private State popState()
    {
        State prevState = stateStack_.pop()
        tryChangeStateTo(stateStack_.getAt(0) as State)
        return prevState
    }

    private addChildStateAndCreateContents(State newChildState, Closure makeContents)
    {
        state_.addChild(newChildState)
        pushState(newChildState)

        assert makeContents : "Contents creation closure must be present for ${newChildState}"
        makeContents()

        popState()
    }

    Preferences producePreferences()
    {
        assert state_ instanceof NoneState
        assert (state_ as NoneState).preferencesList.size() == 1
        return (state_ as NoneState).preferencesList[0]
    }

    @Override
    def preferences(Map options, Closure makeContents = null)
    {
        addChildStateAndCreateContents(new InPreferencesState(), makeContents)
    }

    @Override
    def page(String name, String title, Closure makeContents)
    {
        addChildStateAndCreateContents(new InPageState(state_.childrenCount, name, null), makeContents)
    }

    @Override
    def page(Map options, Closure makeContents)
    {
        addChildStateAndCreateContents(new InPageState(state_.childrenCount, null, options), makeContents)
    }

    @Override
    def section(String sectionTitle, Closure makeContents)
    {
        addChildStateAndCreateContents(new InSectionState(state_.childrenCount, sectionTitle, null), makeContents)
    }

    @Override
    def section(String sectionTitle, Map options, Closure makeContents)
    {
        addChildStateAndCreateContents(new InSectionState(state_.childrenCount, sectionTitle, options), makeContents)
    }

    @Override
    def input(Map options, String name, String type)
    {
        InSectionState section_ = state_ as InSectionState
        assert section_ : "Can't add input() while in ${state_}"

        section_.addInput(options, name, type)
    }
}


