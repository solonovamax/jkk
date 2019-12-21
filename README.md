Jkk
===

The aim of this project is to create a git-like tool to integrate in the development workflow of an everyday developer.

We all now how frustrating can be leaving our beloved shell to check on a 2005-white-background Jenkins dashboard how
build is going on. Sometimes, one would just like to know if the build for the last commit passed or not. Jkk wants to
smooth this task by integrating the build information directly in the developer workflow.

# Building the project
The project is configured using Gradle. It comes with the "application" plugin that allows to easily generate a
ready-to-use jar, wrapped up by a shell script.

Use this command to generate a runnable cli:
```shell script
./gradlew installDist
```

You will find everything under `./build/install/jkk`, and the executable will be in under the `bin` directory.

# License
The software comes under a GPL 3 or later license. This means you can redistribute to source code using the same license
as long as you want, provided you keep it open source.  