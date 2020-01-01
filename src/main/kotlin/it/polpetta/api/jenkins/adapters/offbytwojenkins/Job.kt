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

class Job() : Job, JobWithDetails() {
    override fun getBuild(id: String): Build {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBuilds(): List<Build> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastBuild(): Build {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startBuild(title: String, description: String, parameters: Collection<Pair<String, String>>): Build {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}