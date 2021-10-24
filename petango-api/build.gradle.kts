plugins {
    id("java")
}

version = "0.0.1-SNAPSHOT"

java.sourceSets["main"].java {
    srcDir("build/generated")
}

repositories {
    mavenCentral()
}

val jaxws by configurations.creating

dependencies {
    jaxws("com.sun.xml.ws:jaxws-tools:2.3.5")
    implementation("jakarta.xml.ws:jakarta.xml.ws-api:2.3.3")
    implementation("com.sun.xml.ws:runtime:2.3.5")
}

task("wsimport-petango") {
    group = BasePlugin.BUILD_GROUP
    val sourcedestdir = file("$projectDir/build/generated")
    sourcedestdir.mkdirs()
    doLast {
        ant.withGroovyBuilder {
            "taskdef"(
                "name" to "wsimport",
                "classname" to "com.sun.tools.ws.ant.WsImport",
                "classpath" to jaxws.asPath
            )

            "wsimport"(
                "keep" to true,
                "Xnocompile" to true,
                "sourcedestdir" to sourcedestdir,
                "wsdl" to project.file("src/main/resources/META-INF/wsdl/petango.wsdl"),
                "package" to "com.adoptastray.petango"
            ) {
                "xjcarg"("value" to "-XautoNameResolution")
            }
        }
    }
}

tasks.named("compileJava") { dependsOn("wsimport-petango") }
