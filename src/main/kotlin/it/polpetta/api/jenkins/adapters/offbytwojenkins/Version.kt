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

import com.offbytwo.jenkins.helper.JenkinsVersion
import it.polpetta.api.jenkins.Version

/**
 * Adapted class for the [JenkinsVersion] implementation
 */
class Version(version: String?) : Version, JenkinsVersion(version) {

    override fun isValid(): Boolean {
        return isGreaterThan("1.0")
    }
}