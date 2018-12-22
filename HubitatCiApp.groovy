package biocomp.hubitatci;

import org.gradle.api.Project
import org.gradle.api.Plugin

class HubitatCiPluginExtension {
     String scriptPath
}

public class HubitatCiAppPlugin implements Plugin<Project> {
    void apply(Project project) {
        // Add the 'HubitatCi' extension object
        def extension = project.extensions.create('hubitat_ci_app', HubitatCiPluginExtension)
        // Add a task that uses configuration from the extension object
        project.task('build_script') {
            doLast {
                println "Hello from ${extension.scriptPath}!"
            }
        }
    }
}