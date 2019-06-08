package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.validation.NamedParametersValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator
import groovy.transform.TypeChecked

@TypeChecked
class App {
    final String name
    final String namespace
    final String title
    final Map options

    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("name", notRequired(), mustNotBeEmpty())
        stringParameter("appName", notRequired(), mustNotBeEmpty())
        stringParameter("namespace", notRequired(), mustNotBeEmpty())
        stringParameter("title", notRequired(), mustNotBeEmpty())
        boolParameter("multiple", notRequired())
    }

    App(Map options, String name, String namespace, String title, AppValidator validator)
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
