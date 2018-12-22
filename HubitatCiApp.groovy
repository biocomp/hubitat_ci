package biocomp.hubitatci;

import org.gradle.api.Project
import org.gradle.api.Plugin

// class HubitatCiPluginExtension {
//     String message = 'Hello from HubitatCiPlugin'
// }

public class HubitatCiAppPlugin implements Plugin<Project> {
    void apply(Project project) {
        // Add the 'HubitatCi' extension object
        //def extension = project.extensions.create('HubitatCi', HubitatCiPluginExtension)
        // Add a task that uses configuration from the extension object
        project.task('hello') {
            doLast {
                println "Hello from HubitatCiPlugin!"
            }
        }
    }
}