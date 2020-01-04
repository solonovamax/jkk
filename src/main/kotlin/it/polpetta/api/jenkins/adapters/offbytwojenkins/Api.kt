/*
 * jkk
 * Copyright (C) 2019 - 2020 Davide Polonio <poloniodavide@gmail.com>
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

package it.polpetta.api.jenkins.adapters.offbytwojenkins

import com.offbytwo.jenkins.JenkinsServer
import it.polpetta.api.jenkins.Api
import java.net.URI

class Api(private val uri: URI, username: String?, password: String?) : Api {

    private val jenkinsServer = JenkinsServer(uri, username, password)

    override fun getVersion(): Version {
        return Version(jenkinsServer.version.literalVersion)
    }

    override fun getEndpoint(): String {
        return uri.toString()
    }

    override fun getJob(id: String, folder: String?): Job? {
        return if (folder != null && folder.isEmpty() || folder == jenkinsRootFolder) {
            Job(jenkinsServer.getJob(id))
        } else {
            // FIXME need to understand how FolderJob object works.
            TODO("Folder support has not been implemented yet")
        }
    }
}