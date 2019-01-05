package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.emulation.AppSection
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

//@CompileStatic
//class InSectionState implements State
//{
//    InSectionState(int index, String title, Map options)
//    {
//        section = new Section(index, title, options)
//    }
//
//    Section section
//
//    @Override
//    void finalValidation()
//    {
//        assert childrenCount != 0 : "${this} can't be empty"
//    }
//
//    @Override
//    int getChildrenCount() {
//        return section.children.size()
//    }
//
//    def addInput(Map options, String name, String type)
//    {
//        section.children << new Input("input", options, name, type)
//    }
//
//    String toString() { return "section(${section.title ? section.title : ""}) #${section.index}, options = ${section.options}" }
//
//    @Override
//    void addChild(State state) {
//        assert false : "addChild() shouldn't be called on Section - use other add* methods"
//    }
//}

@TupleConstructor
@TypeChecked
class Section implements AppSection{
    int index
    String title
    Map options

    List children = []

    void validate()
    {
        assert children.size() != 0 : "Section ${this} must have at least some content"
    }

    @Override
    def input(Map options, String name, String type) {
        children << new Input(options, name, type)
    }
}
