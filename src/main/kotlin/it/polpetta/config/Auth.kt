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

import com.uchuhimo.konf.ConfigSpec

object Auth : ConfigSpec(Resources.TOML.Auth.ITEM) {
    val username by required<String>(Resources.TOML.Auth.USERNAME, "Jenkins username")
    val password by required<String>(Resources.TOML.Auth.PASSWORD, "Jenkins password")
    val url by required<String>(Resources.TOML.Auth.URL, "Jenkins server URL")
}

object TokenAuth : ConfigSpec(Resources.TOML.Token.ITEM) {
    val token by required<String>(Resources.TOML.Token.TOKEN, "Jenkins Token login")
    val url by Auth.required<String>(Resources.TOML.Token.URL, "Jenkins server URL")
}