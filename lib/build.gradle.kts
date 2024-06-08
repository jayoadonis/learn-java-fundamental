
plugins {
    java
    `java-library`
}

project.version = "0.0.1"
project.group = "arb.jayo.learn_j"

project.java {
    this.modularity.inferModulePath.set(true);
    this.sourceCompatibility = JavaVersion.VERSION_1_9;
    this.targetCompatibility = JavaVersion.VERSION_11;
}