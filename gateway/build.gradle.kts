plugins {
    `java-library`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly("com.inductiveautomation.ignitionsdk:ignition-common:${rootProject.extra["sdk_version"]}")
    compileOnly("com.inductiveautomation.ignitionsdk:gateway-api:${rootProject.extra["sdk_version"]}")
    compileOnly("com.inductiveautomation.ignitionsdk:driver-api:${rootProject.extra["sdk_version"]}")

    modlImplementation("com.digitalpetri.modbus:modbus-tcp:2.2.0") {
        exclude(group = "io.netty")
    }
}
