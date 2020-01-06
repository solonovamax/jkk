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

import com.offbytwo.jenkins.helper.BuildConsoleStreamListener
import com.offbytwo.jenkins.model.BuildResult
import com.offbytwo.jenkins.model.BuildWithDetails
import it.polpetta.api.jenkins.Build
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.sendBlocking

class Build(private val build: BuildWithDetails) : Build {
    override fun getId(): Int {
        return build.number
    }

    override fun getUrl(): String {
        return build.url
    }

    override fun getTitle(): String {
        return build.displayName
    }

    override fun getDescription(): String {
        return build.description
    }

    /**
     * The method creates a runBlocking [CoroutineScope] which insides create a [ReceiveChannel] producer that
     * subscribes to the underlying [BuildWithDetails.streamConsoleOutput] listener, and that it returns a reference to
     * the receiver channel.
     * @see Build.getLogStream for the method specification
     */
    @UseExperimental(ExperimentalCoroutinesApi::class)
    override fun getLogStream(isFromStart: Boolean): ReceiveChannel<String> = runBlocking {
        produce<String> {
            if (isFromStart) {
                send(build.consoleOutputText)
            }
            withContext(Dispatchers.IO) {
                build.streamConsoleOutput(object : BuildConsoleStreamListener {
                    override fun onData(newLogChunk: String?) {
                        if (newLogChunk != null) {
                            sendBlocking(newLogChunk.orEmpty())
                        }
                    }

                    override fun finished() {
                        close()
                    }
                }, 100, 500)
            }
        }
    }

    override fun getStatus(): Build.Status {
        return when (build.result) {
            BuildResult.ABORTED -> Build.Status.ABORTED
            BuildResult.BUILDING -> Build.Status.BUILDING
            BuildResult.CANCELLED -> Build.Status.CANCELLED
            BuildResult.FAILURE -> Build.Status.FAILURE
            BuildResult.NOT_BUILT -> Build.Status.NOT_BUILT
            BuildResult.REBUILDING -> Build.Status.REBUILDING
            BuildResult.SUCCESS -> Build.Status.SUCCESS
            BuildResult.UNKNOWN -> Build.Status.UNKNOWN
            BuildResult.UNSTABLE -> Build.Status.UNSTABLE
            else -> Build.Status.UNKNOWN
        }
    }

    override fun getDuration(): Long {
        return build.duration
    }

    override suspend fun onComplete(action: (log: String, status: Build.Status, id: Int) -> Unit) {
        while (getStatus() == Build.Status.REBUILDING || getStatus() == Build.Status.BUILDING) {
            delay(100)
        }
        action(build.consoleOutputText, getStatus(), getId())
    }
}