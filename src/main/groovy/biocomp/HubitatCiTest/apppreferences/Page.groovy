package biocomp.hubitatCiTest.apppreferences

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

@CompileStatic
class InPageState implements State
{
    InPageState(int index, String title, Map options)
    {
        page = new Page(index, title, options)
    }

    Page page

    @Override
    void finalValidation()
    {
        assert childrenCount != 0 : "${this} can't be empty"
    }

    @Override
    int getChildrenCount() {
        return page.sections.size()
    }

    String toString() { return "page(${page.title ? page.title : ""}) #${page.index}, options = ${page.options}" }

    @Override
    void addChild(State state) {
        assert state instanceof InSectionState

        page.sections << (state as InSectionState).section
    }
}

@TupleConstructor
class Page {
    int index
    String title
    Map options

    List<Section> sections = []
}
