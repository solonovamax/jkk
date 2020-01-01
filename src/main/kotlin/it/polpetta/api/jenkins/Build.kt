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

import java.io.OutputStream
import java.net.URL

/**
 * A build in Jenkins identifies a particular moment in time when a Job has been performed. A build can be completed or
 * currently running.
 * When completed, a build has a defined output status, that is:
 *  * Success - the build successfully finished, and exited with an expected status.
 *  * Failed - the build exited with an unexpected status.
 *  * Aborted - the build was stopped by a timeout reached or by user intervention.
 *  * Unstable - the build completed successfully, but some check failed while the build ran: this can be due tests
 *  failing or coverage not being satisfied, for instance.
 */
interface Build {
    /**
     * An enumeration of all the possible build statues. Note that differently from what said on the class
     * documentation, [BUILDING] has been added in order to identify an ongoing build.
     */
    enum class Status {
        BUILDING,
        SUCCESS,
        FAILED,
        ABORTED,
        UNSTABLE
    }

    /**
     * @return the build id
     */
    fun getId(): String

    /**
     * @return the URL to the current build
     */
    fun getUrl(): String

    /**
     * @return the title of the build. Note if no custom title has been set, the Jenkins one is returned
     */
    fun getTitle(): String

    /**
     * @return the custom description set in the current build
     */
    fun getDescription(): String

    /**
     * @return the current log stream. Note that if the build is still going, the stream will be filled as long as new
     * output comes out
     */
    fun getLogStream(): OutputStream

    /**
     * @return the current build status
     */
    fun getStatus(): Status

    /**
     * @return the build length in milliseconds
     */
    fun getDuration(): Long

    /**
     * Perform a desired action after the build passed from the state [Status.BUILDING] to another one.
     * @return the result got from the callback function
     */
    fun <T>onComplete(action: (log: OutputStream, status: Status, id: String) -> T): T
}