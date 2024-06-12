@file:Suppress("UnstableApiUsage")

import org.gradle.internal.impldep.org.apache.commons.lang.mutable.Mutable
import java.text.SimpleDateFormat;
import java.util.Date;


settings.rootProject.name = "learn-java-fundamental"
println(
    String.format(
        "Gradle Version: %s, %s", settings.gradle.gradleVersion,
        settings.rootProject.name
    )
);

settings.include(":app");
settings.include(":lib");

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

settings.dependencyResolutionManagement {

    //REM: TODO-HERE[0x0]: Note: we can handle it at the 'settings.dependencyResolutionManagement'.
    this.repositories {
        this.mavenCentral();
    }


    this.versionCatalogs {
        this.register("learnJavaFundamental") {
            this.from(files("./gradle/learnJavaFundamental.versions.toml"));
        }
        this.register("testLib") {
            this.from(files("./gradle/test.versions.toml"));
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
    ).map { it.lowercase() }.toSet();
    PROJECT_GROUP = "jayo.arb.learn-j";
}

settings.gradle.beforeProject {


}

settings.gradle.projectsEvaluated {

    this.allprojects {

        if (JAVA_PLUGIN_IDS.any { javaPluginId ->
                this.project.plugins.hasPlugin(javaPluginId)
            }) {

            val PROJECT_COMPOUND_NAME =
                this.project.tasks.withType<Jar>().firstOrNull()!!.archiveBaseName;
            val SOURCE_SETS_EXTENSION: SourceSetContainer =
                this.project.extensions.getByType<SourceSetContainer>();
            val VERSION_CATALOGS_EXTENSION: VersionCatalogsExtension =
                this.project.extensions.getByType<VersionCatalogsExtension>();

            lateinit var PUBLISHING_EXTENSION: PublishingExtension;

            SOURCE_SETS_EXTENSION.apply {
                this.named("main") {
                    this.java {
                        this.setSrcDirs(listOf("src/main/"));
                        this.setExcludes(listOf("src/main/resources/"));
                    }
                    this.resources {
                        this.setSrcDirs(listOf("src/main/resources/"));
                        this.setExcludes(listOf("src/main/"));
                    }
                }
                this.named("test") {
                    this.java {
                        this.setSrcDirs(listOf("src/test/"));
                        this.setExcludes(listOf("src/test/resources/"));
                    }
                    this.resources {
                        this.setSrcDirs(listOf("src/test/resources/"));
                        this.setExcludes(listOf("src/test/"));
                    }
                }
            }

            project.dependencies {
                val TEST_API_LIB: VersionCatalog = VERSION_CATALOGS_EXTENSION.named( "testLib" );
                this.add( "testImplementation", TEST_API_LIB.findLibrary("junit-jupiter-api").get() );
                this.add( "testRuntimeOnly", TEST_API_LIB.findLibrary("junit-jupiter-engine").get() );
            }


            //REM: MavenPublishingPlugin
            if (this.project.plugins.hasPlugin(MavenPublishPlugin::class.java)) {
                PUBLISHING_EXTENSION =
                    this.project.extensions.getByType<PublishingExtension>();
                PUBLISHING_EXTENSION.apply {
                    this.publications {
                        if (this.findByName(PROJECT_COMPOUND_NAME.get()) == null) {
                            this.register<MavenPublication>(PROJECT_COMPOUND_NAME.get()) {
                                this.from(components["java"]);
                                this.groupId = this.groupId ?: PROJECT_GROUP;
                                this.artifactId = this.artifactId ?: PROJECT_COMPOUND_NAME.get();
                                this.version = project.version.toString();
                                this.pom {
                                    this.name = PROJECT_COMPOUND_NAME;
                                    this.description = "N/a";
                                    this.developers {
                                        this.developer {
                                            this.id = "jayoadonis";
                                            this.name = "A. R. B. Jayo";
                                            this.email = "jayo.adonisraphael@gmail.com";
                                            this.url = "jayo.arb"
                                        }
                                    }
                                    this.licenses {
                                        license {
                                            this.name = "N/a";
                                            this.url = "N/a";
                                        }
                                    }
                                    this.scm {
                                        this.url = "N/a";
                                        this.connection = "N/a";
                                        this.developerConnection = "N/a";
                                    }
                                }
                            }
                        }
                    }
                    this.repositories {
                        if (this.findByName("local") == null) {
                            this.maven {
                                this.name = "local";
                                this.url = uri("file://${project.rootProject.layout.buildDirectory.get()}/repo/");
                            }
                        } else {
                            //REM: TODO-HERE: how to configure this build tool/script when enabling built-in assertion
                            //REM: TODO-HERE: ~ This is a build tool level and did not work, but on the
                            //REM: TODO-HERE: ~ source code level it worked...

                            //REM: TODO-HERE: this is a build tool/script level
                            assert(
                                !(project.findProperty("gradle.is_ea.publishing")
                                    ?.toString()?.takeIf { it.isNotBlank() }?.toBoolean()
                                    ?: false
                                        )
                            ) { "'local' repositories " }
                        }
                    }
                }
            }


            //REM: Configuring JVM arguments with 'enabling assertions' (activating built-in assertion)
            //REM: ~ ideally activating it on both build tool and the source codes.
            //REM: ~ at this moment it only works on the source codes.
            project.tasks.configureEach {
                val isEaJava = project.findProperty("gradle.is_ea.java")?.toString()?.toBoolean() ?: false
                if (isEaJava) {
                    when (this) {
                        is JavaExec -> {
                            val IS_EA_EXEC_JAVA = project.findProperty("gradle.is_ea.java_exec")
                                ?.toString()?.toBoolean() ?: false;
                            if( IS_EA_EXEC_JAVA ) {
                                println("::: Enabling Built-in Assertions with ${this.name}")
                                this.jvmArgs = listOf("-ea")
                            }
                        }
                        is Test -> {
                            val IS_EA_TEST_JAVA = project.findProperty("gradle.is_ea.java_test")
                                ?.toString()?.toBoolean() ?: false;
                            if( IS_EA_TEST_JAVA ) {
                                println("::: Enabling Built-in Assertions with ${this.name}, ${project.name}")
                                this.jvmArgs = listOf("-ea")
                            } else {
                                //REM: TODO-HERE[0x1]: Why it did not work?
                                //REM: TODO-HERE[0x1]: ~ Why work only in the 'submodule or subproject' build script level
                                println("::: Disabling Built-in Assertions with ${this.name}, ${project.name}")
                                this.jvmArgs = listOf("-da")
                            }
                        }
                        is JavaCompile -> {
                            val IS_EA_COMPILE_TEST_JAVA = project.findProperty("gradle.is_ea.java_test_compile")
                                ?.toString()?.toBoolean() ?: false;
                            val IS_EA_COMPILE_JAVA = project.findProperty("gradle.is_ea.java_compile")
                                ?.toString()?.toBoolean() ?: false;
                            when( this.name ) {
                                "compileTestJava" -> {
                                    if( IS_EA_COMPILE_TEST_JAVA ) {
                                        println("::: Enabling Built-in Assertions with ${this.name} (forked)")
                                        this.options.isFork = true
                                        this.options.forkOptions.jvmArgs = listOf("-ea")
                                    }
                                }
                                "compileJava" -> {
                                    if( IS_EA_COMPILE_JAVA ) {
                                        println("::: Enabling Built-in Assertions with ${this.name} (forked)")
                                        this.options.isFork = true
                                        this.options.forkOptions.jvmArgs = listOf("-ea")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            project.tasks.register("printJvmArgs") {
                this.doLast {
                    println("JavaExec tasks JVM Args: ${tasks.withType<JavaExec>().map { it.jvmArgs }}")
                    println("JavaCompile (forked) tasks JVM Args: ${tasks.withType<JavaCompile>().map { it.options.forkOptions.jvmArgs }}")
                    println("JavaCompile tasks JVM Args: ${tasks.withType<JavaCompile>().map { it.options.compilerArgs }}")
                    println("Test tasks JVM Args: ${tasks.withType<Test>().map { it.jvmArgs }}")
                }
            }


            this.project.tasks.withType<Jar>() {
                this.manifest.attributes["whoami"] = "jayo.arb (https://github/jayoadonis)";
                this.manifest.attributes["Author"] = this.manifest.attributes["Author"]
                    .takeIf { it.toString().isNotBlank() }
                    ?: project.providers.gradleProperty("project.author")
                        .orNull?.takeIf { it.isNotBlank() }
                            ?: "A. R. B. Jayo";
                this.manifest {
                    attributes["Name"] = attributes["Name"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: archiveFileName;
                    attributes["Application-Name"] = attributes["Application-Name"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: archiveBaseName;
                    attributes["Built-By"] = attributes["Built-By"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: (
                                project.providers.gradleProperty("project.group.name")
                                    .orNull?.takeIf { it.isNotBlank() }
                                    ?: PROJECT_GROUP
                                ).split(".").reversed().joinToString(" ");
                    attributes["Build-Time"] = attributes["Build-Time"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: SimpleDateFormat("yyyy-MM-dd h:mm:ss-aXXX").format(Date());
                    attributes["Description"] = attributes["Description"]
                        .takeIf { it.toString().isNotBlank() }
                        ?: project.providers.gradleProperty("project.global.description")
                            .orNull?.takeIf { it.isNotBlank() }
                                ?: "N/a <default>"
                    attributes["Implementation-Vendor"] = attributes["Implementation-Vendor"]
                        .takeIf { it.toString().isNotBlank() }
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