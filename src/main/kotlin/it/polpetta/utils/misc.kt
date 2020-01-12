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

package it.polpetta.utils

import com.uchuhimo.konf.Config
import com.uchuhimo.konf.Spec
import com.uchuhimo.konf.source.toml
import com.uchuhimo.konf.source.toml.toToml
import it.polpetta.config.Flags
import it.polpetta.config.Resources
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.system.exitProcess

/**
 * Prints the desired data into stderr, and then append a new line character (e.g. '\n')
 * @see printErr
 */
fun printErrln(err: String) {
    System.err.println(err)
}

/**
 * @return Prints the desired data into stderr
 * @see printErrln
 */
fun printErr(err: String) {
    System.err.print(err)
}

/**
 * @return Get the current working directory of the application
 */
fun pwd(): String {
    return System.getProperty("user.dir")
}

/**
 * @return Get the current user home
 */
fun home(): String {
    return System.getProperty("user.home")
}

/**
 * @return Get the current project root directory
 */
fun prjHome(): String? {
    var current = File(pwd())
    while (!hasPathVCSHome(current.path) && current.parentFile != null) {
        current = current.parentFile
    }
    if (hasPathVCSHome(current.path)) {
        return current.path
    }
    return null
}

fun configPath(global: Boolean = false): String {
    return if (global) {
        Path.of(home(), Resources.JKK_HOME_FOLDER).toFile().path
    } else {
        Path.of(prjHome(), Resources.JKK_CONFIG_FILE).toFile().path
    }
}

/**
 * @param path the path where supported [VCS] will be searched
 * @return Whenever there is a [VCS] supported folder in the given path
 */
fun hasPathVCSHome(path: String): Boolean {
    return Resources.VCS.values().any { Path.of(path, it.toString().toLowerCase()).toFile().exists() }
}

/**
 * Retrieves a config file from a given path
 * @param path the path to the configuration file
 * @return a possible configuration or null if the configuration was not loaded
 */
fun retrieveConfig(path: String, resource: String, specs: Collection<Spec>): Config? {
    var config: Config? = null
    try {
        config = Config { specs.forEach { addSpec(it) } }.from.toml.file(
            Path.of(
                path,
                resource
            ).toFile().path
        )
    } catch (e: FileNotFoundException) {
    }
    return config
}

/**
 * Init the configuration folder in the selected path if it doesn't exist
 */
fun initConfigFolder(path: String) {
    val configFolder = File(path)
    if (!configFolder.exists())
    {
        if (!configFolder.mkdirs()) {
            printErrln("Impossible to create ${Resources.JKK_CONFIG_FILE} folder")
            exitProcess(1)
        }
    }
}

/**
 * Saves the configuration selected in a global or local fashion, overwriting the old one if already existing.
 * Finally, this method creates the config folder for Jkk if it doesn't exist yet.
 * @param config the config to save
 * @param resourceName the name of the file where the config will be written
 * @param global if true the configuration will be written in the user home config, otherwise it will be used the
 * project folder (if any VCS is detected) or the current path of the program execution
 * @see VCS
 */
fun saveConfig(config: Config, resourceName: String, global: Boolean = false) {
    initConfigFolder(configPath(global))
    val saveFile = Path.of(configPath(global), resourceName).toFile()
    if (saveFile.exists()) {
        if (!saveFile.delete()) {
            printErrln("Impossible to delete old ${resourceName} file")
            exitProcess(1)
        }
    } else {
        if (!saveFile.createNewFile()) {
            printErrln("Impossible to write login file ${resourceName}")
            exitProcess(1)
        }
    }
    config.toToml.toFile(saveFile)
}

/**
 * Throw the exception only if the application was started in debug mode, otherwise do nothing
 */
fun debugThrow(e: Exception) {
    if (Flags.debug.get()) {
        throw e
    }
}