pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9"
}

stonecutter {
    create(rootProject) {
        fun mc(
            mcVersion: String,
            vararg loaders: String,
        ) = loaders.forEach { version("$mcVersion-$it", mcVersion) }

        mc("1.21.1", "fabric", "neoforge")
        mc("1.21.11", "fabric", "neoforge")
        mc("26.1", "fabric", "neoforge")
    }
}

rootProject.name = "elytra-bonk"
