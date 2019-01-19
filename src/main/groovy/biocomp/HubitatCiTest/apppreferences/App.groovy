package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.validation.NamedParametersValidator
import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.Validator
import groovy.transform.TypeChecked

@TypeChecked
class App {
    final String name
    final String namespace
    final String title
    final Map options

    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "name", canBeEmpty: false)
        stringParameter(name: "appName", canBeEmpty: false)
        stringParameter(name: "namespace", canBeEmpty: false)
        stringParameter(name: "title", canBeEmpty: false)
        boolParameter(name: "multiple")
    }

    App(Map options, String name, String namespace, String title, Validator validator)
    {
        this.name = name
        this.namespace = namespace
        this.title = title
        this.options = options

        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validator)
        }
    }

    @Override
    String toString()
    {
        return "app(name: '${name}', namespace: '${namespace}', title: '${title}', options: '${options}')"
    }
}
