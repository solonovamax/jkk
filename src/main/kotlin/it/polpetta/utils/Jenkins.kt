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

import com.cdancy.jenkins.rest.JenkinsClient
import com.uchuhimo.konf.Config
import it.polpetta.config.Auth
import java.io.FileNotFoundException

object Jenkins {
    private val config: Config? = try {
        Config().from.properties.file(pwd())
    } catch (e: FileNotFoundException) {
        null
    }
    val session: JenkinsClient?

    init {
        session = if (config != null) {
            JenkinsClient
                .builder()
                .credentials("${config[Auth.username]}:${config[Auth.password]}")
                .endPoint(config[Auth.url])
                .build()
        } else {
            null
        }
    }

    /**
     * Generates a Jenkins API client from the configuration passed on the fly.
     * @param url The remote Jenkins instance
     * @param username The username of the user who wants to login in the server, if necessary
     * @param password The username's password, if required by the server
     * @return A Jenkins API client
     */
    fun with(url: String = "http://localhost", username: String? = null, password: String? = null): JenkinsClient {
        var credentials: String? = null
        if (username != null && password != null) {
            credentials = "${username}:${password}"
        }

        val jenkinsBuilder = JenkinsClient
            .builder()
            .endPoint(url)

        if (credentials != null) {
            jenkinsBuilder.credentials(credentials)
        }

        return jenkinsBuilder.build()
    }
}