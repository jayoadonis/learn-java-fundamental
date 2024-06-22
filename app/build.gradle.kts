

plugins {
    java
    application
    `maven-publish`
    signing
}

project.version = learnJavaFundamental.app.get()
    .version?.takeIf{ it.isNotBlank() }!!
//    ?: "<0.0.0-default>"
project.group = learnJavaFundamental.app.get()
    .group?.takeIf{ it.isNotBlank() }!!
//    ?: "<jayo.arb.default>"


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

project.tasks.test {
    this.useJUnitPlatform();
    //REM: TODO-HERE[0x0]: We need it to explicitly init-disable built-in assertions
    //REM: TODO-HERE[0x0]: ~ the reason is we want to passed it and handled it by the '/settings.gradle.kts' and
    //REM: TODO-HERE[0x0]: ~ its 'settings.gradle.projectsEvaluated{ ... }'.
    //REM: TODO-HERE[0x0]: ~ However I don't know why it is '-ea' by default and by whom?
    //REM: TODO-HERE[0x0]: ~ Maybe it is mandatory to be at '-ea', because, it is a TEST config.
    this.jvmArgs = listOf("-da");
}

project.tasks.withType<Tar>() {
    this.archiveBaseName.set( PROJECT_COMPOUND_NAME );
}
project.tasks.withType<Zip>() {
    this.archiveBaseName.set( PROJECT_COMPOUND_NAME );
}
project.tasks.withType<Jar>() {
    this.archiveBaseName.set( PROJECT_COMPOUND_NAME );
    this.manifest.attributes["Main-Class"] = project.application.mainClass
}