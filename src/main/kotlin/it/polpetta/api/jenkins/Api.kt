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

package it.polpetta.api.jenkins

/**
 * Adapter interface used internally in order to avoid talking directly with the underlying Jenkins library. This
 * will save us time in case we'll need to change Jenkins library.
 *
 * This is the main entry point to talk with a remote Jenkins instance. Multiple functionalities and data can be
 * obtained from here.
 *
 * Note: at this time, this API is not versioned. Functionalities may change based on the Jenkins API version. In this
 * case, you will have to check it by yourself using the [Version] provided by [getVersion].
 */
interface Api {

    val jenkinsRootFolder: String
        get() = "/"

    /**
     * Retrieve and obtain the remote Jenkins version
     * @return the Jenkins server version
     * @see Version
     */
    fun getVersion(): Version

    /**
     * @return the Jenkins server URL
     */
    fun getEndpoint(): String

    /**
     * Getter method to obtain a Job - the unit that in Jenkins represents a set of builds under defined rules.
     * @param id the job id (usually its original name)
     * @param folder if the job is inside a folder, the folder name is necessary since multiple jobs with the same id
     * can exist in Jenkins, as long as they are in different locations.
     * @return a Job object
     * @see Job
     */
    fun getJob(id: String, folder: String? = null): Job?
}