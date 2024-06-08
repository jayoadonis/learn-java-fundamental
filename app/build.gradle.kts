

plugins {
    java
    application
}

project.version = "0.0.1"
project.group = "jayo.arb.learn_j"

val ROOT_PROJECT_NAME = project.rootProject.name
    .replace("-", "_")

project.application {

    this.mainClass.set(
        "${project.group}.${ROOT_PROJECT_NAME}.${project.name}.MainExe"
    );
}

project.dependencies {
    this.implementation( project(":lib") );
}