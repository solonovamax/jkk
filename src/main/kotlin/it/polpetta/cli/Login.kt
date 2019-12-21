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

package it.polpetta.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.uchuhimo.konf.Config
import it.polpetta.config.Auth
import it.polpetta.utils.Jenkins
import it.polpetta.utils.printErrln
import kotlin.system.exitProcess

class Login : CliktCommand(help = "Login to the remote Jenkins instance") {
    private val url: String by argument("server url", "Jenkins server URL")
    private val username: String? by option("-u", "--username", help = "Jenkins username").prompt("Username")
    private val password: String? by option("-p", "--password", help = "Jenkins password/auth token").prompt("Password")

    override fun run() {
        val jenkins = Jenkins.with(url, username, password)
        val authConfig = Config()
        val jenkinsVersion = jenkins.api().systemApi().systemInfo().jenkinsVersion()

        if (jenkinsVersion.split(".")[0].toShort() > 0) {
            println("Login succeed, found Jenkins $jenkinsVersion")
            authConfig.addSpec(Auth)

            authConfig[Auth.username] = username.orEmpty()
            authConfig[Auth.password] = password.orEmpty()
            authConfig[Auth.url] = url

            // TODO write down the credentials in the current pwd(). Optional -> add the file to the gitignore if git
            //  repository is found
        } else {
            printErrln("Login failed")
            exitProcess(1)
        }
    }
}