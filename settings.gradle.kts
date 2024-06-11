
@file:Suppress("UnstableApiUsage")

import java.text.SimpleDateFormat;
import java.util.Date;


settings.rootProject.name = "learn-java-fundamental"
println(String.format("Gradle Version: %s, %s", settings.gradle.gradleVersion,
    settings.rootProject.name ));

settings.include( ":app");
settings.include( ":lib");

final lateinit var JAVA_PLUGIN_IDS: Set<String>;
final lateinit var PROJECT_GROUP: String;


pluginManagement {
    this.repositories {
        this.mavenCentral();
        this.gradlePluginPortal();
    }
    this.plugins {
        //REM: TODO-HERE;
    }
}

settings.dependencyResolutionManagement{
    this.repositories {
        this.mavenCentral();
    }
    this.versionCatalogs {
        this.register("learnJavaFundamental") {
            this.from( files( "./gradle/learnJavaFundamental.versions.toml" ) );
        }
        this.register("testLib") {
            this.from( files( "./gradle/test.versions.toml" ) );
        }
    }
}

settings.gradle.settingsEvaluated {
    JAVA_PLUGIN_IDS = setOf(
        "java",
        "java-application",
        "application",
        "java-library",
        "war",
        "kotlin",
        "groove"
    ).map{ it.lowercase() }.toSet();
    PROJECT_GROUP = "jayo.arb.learn-j";
}

settings.gradle.projectsEvaluated {

    this.allprojects {
        if( JAVA_PLUGIN_IDS.any { javaPluginId ->
                this.project.plugins.hasPlugin( javaPluginId )
        }) {
            val SOURCE_SETS_EXTENSION: SourceSetContainer =
                this.project.extensions.getByType<SourceSetContainer>();

            SOURCE_SETS_EXTENSION.apply {
                this.named( "main" ) {
                    this.java {
                        this.setSrcDirs( listOf( "src/main/" ) );
                        this.setExcludes( listOf( "src/main/resources/" ) );
                    }
                    this.resources {
                        this.setSrcDirs( listOf( "src/main/resources/" ) );
                        this.setExcludes( listOf( "src/main/" ) );
                    }
                }
                this.named( "test" ) {
                    this.java {
                        this.setSrcDirs( listOf( "src/test/" ) );
                        this.setExcludes( listOf( "src/test/resources/" ) );
                    }
                    this.resources {
                        this.setSrcDirs( listOf( "src/test/resources/" ) );
                        this.setExcludes( listOf( "src/test/" ) );
                    }
                }
            }

            this.project.tasks.withType<Jar>() {
                this.manifest.attributes["whoami"] = "jayo.arb (https://github/jayoadonis)";
                this.manifest.attributes["Author"] = this.manifest.attributes["Author"]
                    .takeIf{ it.toString().isNotBlank() }
                    ?: project.providers.gradleProperty("project.author")
                        .orNull?.takeIf{ it.isNotBlank() }
                    ?: "A. R. B. Jayo";
                this.manifest {
                    attributes["Name"] = attributes["Name"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: archiveFileName;
                    attributes["Application-Name"] = attributes["Application-Name"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: archiveBaseName;
                    attributes["Built-By"] = attributes["Built-By"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: (
                                project.providers.gradleProperty("project.group.name")
                                    .orNull?.takeIf { it.isNotBlank() }
                                ?: PROJECT_GROUP
                        ).split(".").reversed().joinToString(" ");
                    attributes["Build-Time"] = attributes["Build-Time"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: SimpleDateFormat("yyyy-MM-dd h:mm:ss-aXXX").format(Date());
                    attributes["Description"] = attributes["Description"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty( "project.global.description" )
                            .orNull?.takeIf{ it.isNotBlank() }
                        ?: "N/a"
                    attributes["Implementation-Vendor"] = attributes["Implementation-Vendor"]
                        .takeIf{ it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty("project.group.name")
                            .orNull?.takeIf { it.isNotBlank() }
                        ?: PROJECT_GROUP
                    attributes["Implementation-Title"] = attributes["Implementation-Title"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: archiveBaseName
                    attributes["Implementation-Version"] = attributes["Implementation-Version"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: project.version
                    attributes["Specification-Vendor"] = attributes["Specification-Vendor"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: "${
                            project.providers.gradleProperty("project.group.name")
                                .orNull.takeIf { !it.isNullOrBlank() }
                                ?: "jayo.arb.learn-j"
                        }, learn more, do more."
                    attributes["Specification-Title"] = attributes["Specification-Title"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: "${archiveBaseName.get()}, practice makes perfect"
                    attributes["Specification-Version"] = attributes["Specification-Version"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: "${project.version}, unending versions of triumph"
                }
            }
        }
    }
}