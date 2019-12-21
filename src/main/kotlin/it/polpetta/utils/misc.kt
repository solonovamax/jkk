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

import it.polpetta.config.Flags
import java.lang.Exception

/**
 * Prints the desired data into stderr, and then append a new line character (e.g. '\n')
 * @see printErr
 */
fun printErrln(err: String) {
    System.err.println(err)
}

/**
 * Prints the desired data into stderr
 * @see printErrln
 */
fun printErr(err: String) {
    System.err.print(err)
}

/**
 * Get the current working directory of the application
 */
fun pwd(): String {
    return System.getProperty("user.dir")
}

/**
 * Throw the exception only if the application was started in debug mode, otherwise do nothing
 */
fun debugThrow(e: Exception) {
    if (Flags.debug.get()) {
        throw e
    }
}