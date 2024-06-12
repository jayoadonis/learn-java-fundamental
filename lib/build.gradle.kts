

plugins {
    java
    `java-library`
    `maven-publish`
    signing
}


project.version = learnJavaFundamental.lib.get()
    .version?.takeIf{ it.isNotBlank() }!!;
project.group = learnJavaFundamental.lib.get()
    .group?.takeIf{ it.isNotBlank() }!!;

val CHAR_PKG_SPACE: String = "_"; //REM: use for normalized and denormalized function.
val PROJECT_GROUP: String = project.normalized_as_pkg( project.group.toString() );
val ROOT_PROJECT_NAME: String = project.normalized_as_pkg( project.rootProject.name );
val PROJECT_NAME: String = project.normalized_as_pkg( project.name );
val MODULE_NAME = "${PROJECT_GROUP}.${ROOT_PROJECT_NAME}_${PROJECT_NAME}";
val PROJECT_COMPOUND_NAME: String = project.denormalized_from_pkg("${ROOT_PROJECT_NAME}-${PROJECT_NAME}");

//REM: Use for specialized format.
private fun Project.normalized_as_pkg( value: String, pkgSpace: String = CHAR_PKG_SPACE ): String {
    val REGEX_PKG_SPACE_COMPILE: Regex = Regex("[\\/ *+-]+");
    val REGEX_TRIM_COMPILE: Regex = Regex("(^[ ._]+)|([ ._]+$)");

    return value.replace( REGEX_PKG_SPACE_COMPILE, pkgSpace )
        .replace( REGEX_TRIM_COMPILE, "" )
        .lowercase();
}

//REM: Use for specialized format.
private fun Project.denormalized_from_pkg( value: String, pkgSpace: String = CHAR_PKG_SPACE ): String {
    return value.replace( Regex( pkgSpace ), "-");
}

project.java {
    this.modularity.inferModulePath.set(true);
    this.sourceCompatibility = JavaVersion.VERSION_1_9;
    this.targetCompatibility = JavaVersion.VERSION_11;
}

project.repositories {
    this.mavenCentral();
}

project.dependencies {
    this.implementation("org.jetbrains:annotations:24.1.0");
//    this.testImplementation( testLib.junit.jupiter.api );
//    this.testRuntimeOnly( testLib.junit.jupiter.engine );
}

project.publishing {
    this.repositories {
        this.maven {
            this.name = "local";
        }
    }
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

}
