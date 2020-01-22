package me.biocomp.hubitat_ci.capabilities

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.Command

import java.lang.reflect.Method

@CompileStatic
class GeneratedCommand implements Command {
    GeneratedCommand(Method method) {
        name = method.name
        arguments = method.parameterTypes.collect{typeToString(it)}
    }

    @Override
    List<String> getArguments() {
        arguments
    }

    @Override
    String getName() {
        name
    }

    private static String typeToString(Class cl) {
        switch (cl) {
            case String:
                return "STRING"
            case Double:
            case Integer:
            case Number:
                return "NUMBER"
            case Object:
                return "JSON_OBJECT"
            default:
                if (cl.isEnum()) {
                    return "ENUM"
                }

                assert false: "Class ${cl} is unsupported command argument type"
        }
    }

    private final String name
    private final List<String> arguments
}
