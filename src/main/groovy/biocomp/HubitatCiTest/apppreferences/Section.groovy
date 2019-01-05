package biocomp.hubitatCiTest.apppreferences

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

@CompileStatic
class InSectionState implements State
{
    InSectionState(int index, String title, Map options)
    {
        section = new Section(index, title, options)
    }

    Section section

    @Override
    void finalValidation()
    {
        assert childrenCount != 0 : "${this} can't be empty"
    }

    @Override
    int getChildrenCount() {
        return section.children.size()
    }

    def addInput(Map options, String name, String type)
    {
        section.children << new Input("input", options, name, type)
    }

    String toString() { return "section(${section.title ? section.title : ""}) #${section.index}, options = ${section.options}" }

    @Override
    void addChild(State state) {
        assert false : "addChild() shouldn't be called on Section - use other add* methods"
    }
}

@TupleConstructor
class Section {
    int index
    String title
    Map options

    List children = []
}
