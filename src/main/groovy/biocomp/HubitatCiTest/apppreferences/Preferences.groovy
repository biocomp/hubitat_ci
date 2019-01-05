package biocomp.hubitatCiTest.apppreferences

import groovy.transform.CompileStatic

@CompileStatic
class InPreferencesState implements State
{
    Preferences preferences = new Preferences()

    @Override
    void finalValidation()
    {
        assert childrenCount != 0 : "${this} contents can't be empty"
    }

    @Override
    int getChildrenCount() { return preferences.pages.size() + preferences.sections.size() }

    @Override
    String toString() { return "preferences()" }

    @Override
    void addChild(State child)
    {
        if (child instanceof InPageState)
        {
            preferences.pages << (child as InPageState).page
        }
        else if (child instanceof InSectionState)
        {
            preferences.sections << (child as InSectionState).section
        }
    }
}

class Preferences
{
    List<Page> pages = []
    List<Section> sections = []
}