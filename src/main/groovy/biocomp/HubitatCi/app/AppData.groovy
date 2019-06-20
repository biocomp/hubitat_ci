package biocomp.hubitatCi.app

import biocomp.hubitatCi.app.preferences.Preferences
import groovy.transform.TypeChecked

/**
 * All the information collected about the app.
  */
@TypeChecked
class AppData {
    /**
     * Results of definition() call.
     */
    final Map<String, Object> definitions = [:]

    final Preferences preferences = new Preferences()
}
