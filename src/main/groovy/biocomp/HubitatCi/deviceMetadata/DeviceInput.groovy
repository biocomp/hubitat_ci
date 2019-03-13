package biocomp.hubitatCi.deviceMetadata

import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TypeChecked
@TupleConstructor
class DeviceInput {
    final Map unnamedOptions
    final Map options

    String readName()
    {
        return unnamedOptions.name != null ? unnamedOptions.name : options.name
    }

    String readType()
    {
        return unnamedOptions.type != null ? unnamedOptions.type: options.type
    }

    @Override
    String toString()
    {
        return "input(unnamed options: ${unnamedOptions}, options = ${options})"
    }
}
