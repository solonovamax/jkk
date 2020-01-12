package it.polpetta.config

import com.uchuhimo.konf.ConfigSpec

object Branch: ConfigSpec(Resources.TOML.Config.Branch.ITEM) {
    val local by required<String>(Resources.TOML.Config.Branch.LOCAL, "Local branch name")
    val remote by required<String>(Resources.TOML.Config.Branch.REMOTE, "Remote branch name")
}

object Job: ConfigSpec(Resources.TOML.Config.Job.ITEM, innerSpecs = setOf(Branch)) {
    val branches by required<Branch>(Resources.TOML.Config.Branch.ITEM, "List of possible branch association")
}

object Config: ConfigSpec(Resources.TOML.Config.ITEM) {
    object Association: ConfigSpec(Resources.TOML.Config.ASSOCIATION, innerSpecs = setOf(Job)) {
        val jobs by required<Job>(Resources.TOML.Config.Job.ITEM, "List of possible Job configuration")
    }
}