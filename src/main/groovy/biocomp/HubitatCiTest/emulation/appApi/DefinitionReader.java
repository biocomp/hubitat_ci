package biocomp.hubitatCiTest.emulation.appApi;

import groovy.lang.Closure;

import java.util.Map;

public interface DefinitionReader {
    /**
     * Metainformation about the appApi
     *
     * @param definitionsMap
     * @param makeContents
     * @return not sure
     */
    default Object definition(Map definitionsMap, Closure makeContents) {
        return null;
    }

    /**
     * Metainformation about the appApi
     *
     * @param definitionsMap
     * @return not sure
     */
    default Object definition(Map definitionsMap) {
        return definition(definitionsMap, null);
    }
}
