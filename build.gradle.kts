/*
 * jkk
 * Copyright (C) 2019 - 2019 Davide Polonio <poloniodavide@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@file:Suppress("SuspiciousCollectionReassignment")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import kotlin.math.max
import java.util.Date

plugins {
    application
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "it.polpetta"
version = "0.1"

application {
    mainClass.set("it.polpetta.Cli")
    applicationDefaultJvmArgs += "--add-opens=java.base/java.lang=ALL-UNNAMED"
}

kotlin {
    target {
        compilations.configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
                apiVersion = "1.3"
                languageVersion = "1.3"
                freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
            }
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    kotlin("stdlib-jdk8")
    implementation("com.github.ajalt:clikt:2.3.0")
    implementation("com.uchuhimo:konf:0.22.1")
    implementation("dev.misfitlabs.kotlinguice4:kotlin-guice:1.4.1")
    implementation("com.offbytwo.jenkins:jenkins-client:0.3.8")
    implementation("org.slf4j:slf4j-nop:1.7.30")
    
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.assertj:assertj-core:3.15.0")
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
        
        failFast = false
        maxParallelForks = max(Runtime.getRuntime().availableProcessors() - 1, 1)
    }
    
    withType<ShadowJar>().configureEach {
        archiveClassifier.set("standalone")
        
        mergeServiceFiles()
        minimize {
            exclude { // exclude any modules you need to exclude from minimization here
                it.moduleName == "something"
            }
        }
    }
    
    withType<Jar>().configureEach {
        from(rootProject.file("LICENSE"))
        manifest {
            attributes(
                    "Main-Class" to "it.polpetta.Cli",
                    "Built-By" to System.getProperty("user.name"),
                    "Built-Date" to Date().toString(),
                    "Built-Jdk" to System.getProperty("java.version"),
                    "Implementation-Title" to "JKK: A git-like cli for Jenkins written in Kotlin",
                    "Implementation-Version" to project.version.toString(),
                    "Add-Opens" to "java.base/java.lang",
                      )
        }
    }
}
