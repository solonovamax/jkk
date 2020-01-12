package it.polpetta.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.uchuhimo.konf.Config
import it.polpetta.config.Resources
import it.polpetta.utils.*

class Config : CliktCommand(name = "config", help = "Update and manage the cli configuration") {
    private val tomlEntry: String by argument("toml_entry", "TOML entry to create, edit or remove")
    private val global: Boolean by option(help = "Apply the config at global level").flag("g", default = false)
    private val remove: Boolean by option(help = "Remove the current configuration instead of editing it").flag("rm", default = false)
    override fun run() {
        val workingDir: String = if (global) {
            home()
        } else {
            var prj = prjHome()
            if (prj == null) {
                prj = pwd()
            }
            prj
        }
        var config: Config? = retrieveConfig(workingDir, Resources.JKK_CONFIG_FILE, setOf(it.polpetta.config.Config))
        val entryAndValue = tomlEntry.split("=")
        if (config == null) {
            config = Config { addSpec(it.polpetta.config.Config) }
        }
        if (remove) {
            config.unset(entryAndValue[0])
        } else {
            config[entryAndValue[0]] = entryAndValue[1]
        }
        saveConfig(config, Resources.JKK_CONFIG_FILE, global)
    }
}