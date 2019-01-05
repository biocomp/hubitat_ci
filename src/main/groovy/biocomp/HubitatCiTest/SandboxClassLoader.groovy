package biocomp.hubitatCiTest

/* Will replace framework classes with test implementations,
    * and validate for allowed classes and methods*/
class SandboxClassLoader extends ClassLoader {
    SandboxClassLoader(ClassLoader parent) {
        super(parent)
    }

    @Override
    Class<?> loadClass(String name, boolean resolve) {
        println "Loading ${name}"
        super.loadClass(mapClassName(name), resolve)
    }

    private static String mapClassName(String name) {
        return name.replaceAll('''physicalgraph[\\.$]device[\\.$]''', "biocomp.hubitatCiTest.emulation.")
    }
}

