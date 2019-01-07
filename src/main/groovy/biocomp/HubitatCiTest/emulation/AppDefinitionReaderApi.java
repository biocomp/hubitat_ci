package biocomp.hubitatCiTest.emulation;

import groovy.lang.Closure;

import java.util.Map;

public interface AppDefinitionReaderApi {
    /**
     * Metainformation about the app
     *
     * @param definitionsMap
     * @param makeContents
     * @return not sure
     */
    default Object definition(Map definitionsMap, Closure makeContents) {
        return null;
    }

    /**
     * Metainformation about the app
     *
     * @param definitionsMap
     * @return not sure
     */
    default Object definition(Map definitionsMap) {
        return definition(definitionsMap, null);
    }
}
