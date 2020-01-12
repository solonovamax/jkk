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

package it.polpetta.config

object Resources {
    /**
     * Folder configuration
     */
    const val JKK_HOME_FOLDER: String = ".jkk"
    /**
     * Credentials file name
     */
    const val JKK_AUTH_FILE: String = "credentials"
    /**
     * Configuration file name
     */
    const val JKK_CONFIG_FILE: String = "config"
    /**
     * A list of supported Version Control Systems
     */
    enum class VCS {
        GIT,
        HG,
        SVN
    }

    /**
     * Fields for TOML entries
     */
    object TOML {
        object Auth {
            const val USERNAME : String = "username"
            const val PASSWORD : String = "password"
            const val URL : String = "url"
            const val ITEM : String = "auth"
        }
        object Token {
            const val TOKEN : String = "token"
            const val URL : String = "url"
            const val ITEM : String = "token_auth"
        }
        object Config {
            const val ITEM: String = "config"
            const val ASSOCIATION: String = "association"
            object Job {
                const val ITEM: String = "job"
                const val FOLDER: String = "folder"
                const val NAME: String = "name"
                const val FALLBACK: String = "fallback"
            }
            object Branch {
                const val ITEM: String = "branch"
                const val REMOTE: String = "remote"
                const val LOCAL: String = "local"
            }
        }
    }
}