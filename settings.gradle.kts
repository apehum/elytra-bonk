pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots")
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

        mc("1.20.1", "forge")
        mc("1.21.1", "fabric", "neoforge")
        mc("1.21.11", "fabric", "neoforge")
        mc("26.1", "fabric", "neoforge")

        vcsVersion = "1.21.1-fabric"
    }
}

rootProject.name = "elytra-bonk"
