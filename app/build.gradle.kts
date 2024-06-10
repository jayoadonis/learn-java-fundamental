

plugins {
    java
    application
}

project.version = "0.0.1"
project.group = "jayo.arb.learn-j"


val CHAR_PKG_SPACE: String = "_"; //REM: use for normalized and denormalized function.
val PROJECT_GROUP: String = project.normalized_as_pkg( project.group.toString() );
val ROOT_PROJECT_NAME: String = project.normalized_as_pkg( project.rootProject.name );
val PROJECT_NAME: String = project.normalized_as_pkg( project.name );
val MODULE_NAME = "${PROJECT_GROUP}.${ROOT_PROJECT_NAME}_${PROJECT_NAME}";
val PROJECT_COMPOUND_NAME: String = project.denormalized_from_pkg("${ROOT_PROJECT_NAME}-${PROJECT_NAME}");

//REM: Use for specialized format.
private fun Project.normalized_as_pkg( value: String ): String {
    val REGEX_PKG_SPACE_COMPILE: Regex = Regex("[\\/ *+-]+");
    val REGEX_TRIM_COMPILE: Regex = Regex("(^[ ._]+)|([ ._]+$)");

    return value.replace( REGEX_PKG_SPACE_COMPILE, CHAR_PKG_SPACE )
        .replace( REGEX_TRIM_COMPILE, "" )
        .lowercase();
}

//REM: Use for specialized format.
private fun Project.denormalized_from_pkg( value: String ): String {
    return value.replace( Regex( CHAR_PKG_SPACE ), "-");
}

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

project.repositories {
    this.mavenCentral();
}
project.dependencies {
    this.implementation( project(":lib") );
}

project.tasks.withType<Tar>() {
    this.archiveBaseName.set( PROJECT_COMPOUND_NAME );
}
project.tasks.withType<Zip>() {
    this.archiveBaseName.set( PROJECT_COMPOUND_NAME );
}
project.tasks.withType<Jar>() {
    this.archiveBaseName.set( PROJECT_COMPOUND_NAME );
}