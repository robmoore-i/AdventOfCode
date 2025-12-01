plugins {
    java
}

(1..12).forEach { n ->
    tasks.register<JavaExec>("runP${n}") {
        mainClass.set("aoc.P${n}")
        classpath = sourceSets.main.get().runtimeClasspath
        args(layout.projectDirectory.dir("puzzle_inputs"))
    }

    tasks.register<JavaExec>("runP${n}Test") {
        mainClass.set("aoc.P${n}")
        classpath = sourceSets.main.get().runtimeClasspath
        args(layout.projectDirectory.dir("puzzle_inputs"), "test")
    }
}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
