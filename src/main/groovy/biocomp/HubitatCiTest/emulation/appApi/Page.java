package biocomp.hubitatCiTest.emulation.appApi;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.Map;

public interface Page {
    /**
     * Adds section to a page
     */
    default Object section(String sectionTitle, @DelegatesTo(Section.class) Closure makeContents) {
        return null;
    }

    default Object section(@DelegatesTo(Section.class) Closure makeContents) {
        return section((String)null, makeContents);
    }

    /**
     * Adds section to a page
     *
     * @param options. valid options are:
     *                 <p>
     *                 hideable (Boolean) - Pass true to allow the section to be collapsed. Defaults to false.
     *                 hidden (Boolean) - Pass true to specify the section is collapsed by default. Used in conjunction with hideable.
     *                 Defaults to false.
     *                 mobileOnly (Boolean) - Pass true to suppress this section from the IDE simulator. Defaults to false.
     */
    default Object section(Map options, String sectionTitle,
                           @DelegatesTo(Section.class) Closure makeContents) {
        return null;
    }

    default Object section(Map options,
                           @DelegatesTo(Section.class) Closure makeContents) {
        return section((Map)null, null, makeContents);
    }
}

