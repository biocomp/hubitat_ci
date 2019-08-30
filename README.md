# Hubitat CI

This is a library for unit testing Hubitat scripts locally (and via **C**ontinuous **I**ntegration, thus Hubitat CI).

In short, it uses [GroovyShell](http://docs.groovy-lang.org/latest/html/api/groovy/lang/GroovyShell.html) to load the scripts, make objects out of them and let user test them.

## Getting started
1. [Install Gradle](https://gradle.org/install/) build system.
2. Look at a [minimal project sample](https://github.com/biocomp/hubitat_ci_example/tree/master/minimal) as a basis, use your own script instead of  `appscript.groovy`.
3. run `gradle build` from the same folder where `build.gradle` and your script are.

This will build and run the test, which, in turn will load and validate your script.
