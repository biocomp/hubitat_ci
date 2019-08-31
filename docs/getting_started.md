# Getting started
1. [Install Gradle](https://gradle.org/install/) build system.
2. Look at a [minimal project sample (for app)](https://github.com/biocomp/hubitat_ci_example/tree/master/minimal) as a basis, use your own script instead of  `appscript.groovy`.
3. run `gradle build` from the same folder where `build.gradle` and your script are.

This will build and run the test, which, in turn will load and validate your script.
