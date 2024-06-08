

plugins {
    java
    application
}

project.version = "0.0.1"
project.group = "jayo.arb.learn_j"

val ROOT_PROJECT_NAME = project.rootProject.name
    .replace("-", "_")
val MODULE_NAME = "${project.group}.${ROOT_PROJECT_NAME}.${project.name}";

project.java {
    this.modularity.inferModulePath.set(true);
    this.sourceCompatibility = JavaVersion.VERSION_1_9;
    this.targetCompatibility = JavaVersion.VERSION_11;
}
project.application {
    this.mainClass.set(
        "${MODULE_NAME}.MainExe"
    );
    this.mainModule.set( MODULE_NAME )
}

project.dependencies {
    this.implementation( project(":lib") );
}