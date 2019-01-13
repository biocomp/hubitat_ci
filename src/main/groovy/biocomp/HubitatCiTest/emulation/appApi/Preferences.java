package biocomp.hubitatCiTest.emulation.appApi;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.Map;

public interface Preferences extends Page {
    /**
     * Adds page of settings.*/
    default Object page(String name, String title, @DelegatesTo(Page.class) Closure makeContents) { return null; }
    default Object page(Map options, String name, String title, @DelegatesTo(Page.class) Closure makeContents) { return null; }

    /**
     * Adds page of settings.
     *
     * @param options. Valid options are:
    name (required) (String) - Identifier for this page.
    title (String) - The display title of this page.
    nextPage (String) - Used on multi-page preferences only. Should be the name of the page to navigate to next.
    install (Boolean) - Set to true to allow the user to install this appApi from this page. Defaults to false. Not
    necessary for single-page preferences.
    uninstall (Boolean) - Set to true to allow the user to uninstall from this page. Defaults to false. Not necessary
    for single-page preferences.
     */
    default Object page(Map options, @DelegatesTo(Page.class) Closure makeContents) {
        return null;
    }


    /**
     * Creates dynamic page. In this case, page's 'name' must point to a method.
     * @param options (see other page() method). But 'name' must be a name of method that creates dynamic page.
     * @return
     */
    default Object page(Map options) {
        return null;
    }

//    /**
//     * Adds section to single-page appApi (one parent page is assumed)*/
//    default Object section(String sectionTitle, @DelegatesTo(Section.class) Closure makeContents) {
//        return null;
//    }
//
//    default Object section(@DelegatesTo(Section.class) Closure makeContents) {
//        return null;
//    }
//
//    /**
//     * Adds section to a single-page appApi (one parent page is assumed)
//     *
//     * @param sectionTitle
//     * @param options. valid options are:
//     *
//     * hideable (Boolean) - Pass true to allow the section to be collapsed. Defaults to false.
//     * hidden (Boolean) - Pass true to specify the section is collapsed by default. Used in conjunction with hideable.
//     *      Defaults to false.
//     * mobileOnly (Boolean) - Pass true to suppress this section from the IDE simulator. Defaults to false.
//     */
//    default Object section(Map options, String sectionTitle, @DelegatesTo(Section.class) Closure makeContents) {
//        return null;
//    }
//
//    default Object section(Map options, @DelegatesTo(Section.class) Closure makeContents) {
//        return section(options, null, makeContents);
//    }
}