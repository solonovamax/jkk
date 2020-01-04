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

import com.offbytwo.jenkins.model.JobWithDetails
import it.polpetta.api.jenkins.Job

class Job(private val job: JobWithDetails) : Job {
    override fun getName(): String {
        return job.name
    }

    override fun getBuild(id: Int): Build {
        return Build(job.getBuildByNumber(id).details())
    }

    override fun getBuilds(): Sequence<Build> {
        return job.allBuilds.asSequence().mapNotNull {
            Build(it.details())
        }
    }

    override fun getLastBuild(): Build {
        return Build(job.lastBuild.details())
    }

    override fun startBuild(title: String, description: String, parameters: Collection<Pair<String, String>>): Build? {
        val launchedBuild = job.build(parameters.toMap())
        val queueItem = job.queueItem
        if (queueItem.url == launchedBuild.queueItemUrlPart) {
            val buildWithDetails = job.getBuildByNumber(queueItem.id.toInt()).details()
            buildWithDetails.updateDisplayNameAndDescription(title, description)
            return Build(buildWithDetails)
        }
        return null
    }
}