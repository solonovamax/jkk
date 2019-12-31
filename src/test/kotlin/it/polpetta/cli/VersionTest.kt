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

package it.polpetta.cli

import it.polpetta.config.VersionInfo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream


internal class VersionTest {
    // TODO We really need to use Guice in order to Inject our version of JenkinsCli (a simulator maybe?)
    private val version = Version()
    private val outContent = ByteArrayOutputStream()
    private val errContent = ByteArrayOutputStream()
    private val originalOut = System.out
    private val originalErr = System.err

    @BeforeEach
    fun setup_all() {
        System.setErr(PrintStream(errContent))
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun cleanup_all() {
        System.setErr(originalErr)
        System.setOut(originalOut)
    }

    @Tag("it")
    @Test
    fun `Version formatting should change based on logged in status`() {
        // FIXME ok so this test is a little dumb. It won't work consistently until Guice is added to the project, and
        //  we can use dependency injection to push stuff inside the classes we declare. As it is, the test will fail
        //  if you're currently logged in a Jenkins server.
        version.run()
        assertEquals("""
            Version: ${VersionInfo.NUMBER}
            Version name: ${VersionInfo.NAME}
            """.trimIndent(), outContent.toString().trimIndent())
    }
}