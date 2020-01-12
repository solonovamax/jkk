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
import it.polpetta.api.jenkins.Api
import it.polpetta.config.Auth
import it.polpetta.config.Resources
import java.io.FileNotFoundException
import java.net.URI
import java.nio.file.Path
import java.util.*

class JenkinsSession {
/**
 * Manage the current Jenkins session, retrieving URL, username and password at boot time from the local repository
 * configuration. It gives the possibility to return an [Api] session.
 * @see Api
 */
    val session: Api?
    init {
        var config: Config? = null
        val localConfig = Path.of(prjHome(), Resources.JKK_HOME_FOLDER, Resources.JKK_AUTH_FILE).toFile()
        val globalConfig = Path.of(home(), Resources.JKK_HOME_FOLDER, Resources.JKK_AUTH_FILE).toFile()
        if (localConfig.exists()) {
            config = retrieveConfig(localConfig.path, Resources.JKK_AUTH_FILE, setOf(Auth))
        } else if (globalConfig.exists()) {
            config = retrieveConfig(globalConfig.path, Resources.JKK_AUTH_FILE, setOf(Auth))
        }
        session = if (config != null) {
            config.validateRequired()
            it.polpetta.api.jenkins.adapters.offbytwojenkins.Api(
                URI(config[Auth.url]),
                config[Auth.username],
                config[Auth.password]
            )
        } else {
            null
        }
    }

    /**
     * Generates a Jenkins [Api] client from the configuration passed on the fly.
     * @param url The remote Jenkins instance
     * @param username The username of the user who wants to login in the server, if necessary
     * @param password The username's password, if required by the server
     * @return A Jenkins [Api] client
     * @see Api
     */
    fun with(url: String = "http://localhost", username: String? = null, password: String? = null): Api {
        return it.polpetta.api.jenkins.adapters.offbytwojenkins.Api(URI(url), username, password)
    }

    /**
     * Returns the current Jenkins session if present or calls a lambda that defines how to behave
     * otherwise
     * @param onNullSession lambda that defines how to behave when session is null
     * @return the current Jenkins Client or the result of the argument lambda if the latter is null
     */
    //Not totally convinced this is a good idea but it could help to improve readability
    inline fun retrieveSession(onNullSession: () -> Api? = { null }): Api? {
        return session ?: onNullSession()
    }
}