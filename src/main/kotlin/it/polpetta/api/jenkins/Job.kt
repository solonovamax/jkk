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
 * Class that represents a Job unit in Jenkins. From a Job is possible to get the latest [Build], a list of them or
 * launch a new [Build] with custom parameters.
 */
interface Job {

    /**
     * Obtain the current Job name.
     *
     * Note that this name could differ from the original id.
     */
    fun getName(): String

    /**
     * Given a job name and the build id, obtain the build with all the relative details
     * @param id the build id
     * @return the build details
     * @see Build
     */
    fun getBuild(id: String): Build

    /**
     * Get all the builds stored in this Job
     * @return all the builds currently stored in Jenkins
     * @see Build
     */
    fun getBuilds(): Collection<Build>

    /**
     * @return the last build processed by this Job
     */
    fun getLastBuild(): Build

    /**
     * Order Jenkins to launch a new build. The build can be personalized, giving a custom title or description.
     * Additionally, a build can have parameters that can be defined. Parameters allow to change the behavoir of the
     * build itself e.g. customizing the name of a particular release or executing additional steps based on the kind of
     * deployment the user wants.
     * @param title custom title that will be displayed instead of the common Jenkins numbering sequence
     * @param description additional description that will be added at the build itself
     * @param parameters a collection of parameters to pass to a build. Note that parameters need to be specified on the
     * Jenkinsfile first, or the build just launched will end up with undefined behavior
     * @return the build just launched
     * @see Build
     */
    fun startBuild(title: String = "", description: String = "", parameters: Collection<Pair<String, String>>): Build
}