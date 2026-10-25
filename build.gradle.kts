import dev.apehum.elytrabonk.VersionResolver

plugins {
    id("dev.isxander.modstitch.base") version "0.8.4"
    id("me.modmuss50.mod-publish-plugin") version "2.0.0-beta.2"
    id("dev.kikugie.fletching-table.relocate") version "0.2.0-alpha.9"
}

val platform = stonecutter.current.project.substringAfter('-')

base {
    archivesName = "${property("archives_base_name")}-$platform"
}

group = property("maven_group")!!

modstitch {
    minecraftVersion = property("minecraft_version") as String

    loom {
        fabricLoaderVersion = property("deps.fabric_loader_version") as String

        configureLoom {
            runs.all {
                runDir = "../../run"
            }
        }
    }

    moddevgradle {
        neoForgeVersion = findProperty("deps.neoforge") as? String
        forgeVersion = findProperty("deps.forge") as? String

        defaultRuns(client = true, server = false)

        configureNeoForge {
            runs.all {
                gameDirectory = file("../../run")
            }
        }
    }

    metadata {
        modId = "elytrabonk"
        modVersion = "${property("mod_version")}+${stonecutter.current.version}"
        modName = "Elytra Bonk"
        modDescription = "Plays a bonk sound when you fly into a wall with an elytra."

        replacementProperties.apply {
            put("fabric_loader_version", property("deps.fabric_loader_version") as String)
            put("minecraft_version_dependency", property("minecraft_version_dependency") as String)
        }
    }

    mixin {
        addMixinsToModManifest = true

        configs.register("elytrabonk")
    }
}

stonecutter {
    replacements.string(current.parsed < "1.21.11") {
        replace("Identifier", "ResourceLocation")
    }
}

fletchingTable {
    relocate.configure(sourceSets.main) {
        matching("(*).json5") {
            into("\$1.json")
            with(Json5ToJson)
        }
    }
}

dependencies {
    if (modstitch.isModDevGradleLegacy) {
        val mixinExtras = "0.5.5"
        "annotationProcessor"("io.github.llamalad7:mixinextras-common:$mixinExtras")
        "compileOnly"("io.github.llamalad7:mixinextras-common:$mixinExtras")
        "implementation"("io.github.llamalad7:mixinextras-forge:$mixinExtras")
        "jarJar"("io.github.llamalad7:mixinextras-forge:$mixinExtras")
    }
}

val outputJarTask = provider { modstitch.finalJarTask.get() }

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${project.property("archives_base_name")}" }
        }
    }

    val copyToRoot =
        register<Copy>("copyToRoot") {
            dependsOn(outputJarTask)
            from(outputJarTask.map { it.archiveFile.get() })
            into(rootProject.layout.buildDirectory.dir("libs"))
        }

    build {
        dependsOn(copyToRoot)
    }
}

publishMods {
    changelog =
        rootProject.layout.projectDirectory
            .file("changelog.md")
            .asFile
            .readText()
    type = STABLE
    modLoaders.add(platform)

    val loaderDisplayName =
        when (platform) {
            "fabric" -> "Fabric"
            "neoforge" -> "NeoForge"
            "forge" -> "Forge"
            else -> throw IllegalStateException("Unsupported platform $platform")
        }

    displayName = "[$loaderDisplayName ${property("minecraft_version")}] Elytra Bonk ${property("mod_version")}"
    file = outputJarTask.flatMap { it.archiveFile }

    val modrinthToken =
        providers
            .gradleProperty("modrinth_token")
            .orElse(
                providers.environmentVariable("MODRINTH_TOKEN").orNull ?: "",
            ).orNull
            ?.takeIf { it.isNotBlank() }

    val curseforgeToken =
        providers
            .gradleProperty("curseforge_token")
            .orElse(
                providers.environmentVariable("CURSEFORGE_TOKEN").orNull ?: "",
            ).orNull
            ?.takeIf { it.isNotBlank() }

    val dryRunProperty =
        providers
            .gradleProperty("dry_run")
            .getOrElse("false")
            .toBoolean()

    dryRun = modrinthToken == null || curseforgeToken == null || dryRunProperty

    val versions =
        VersionResolver
            .getMinecraftVersionsInRange("release", property("minecraft_version_dependency") as String)
            .get()
            .map { it.id }
            .also { println("Minecraft versions: $it") }


    modrinth {
        projectId = "NnnM5ni4"
        accessToken = modrinthToken
        minecraftVersions.addAll(versions)
    }

    curseforge {
        projectId = "1564314"
        accessToken = curseforgeToken
        minecraftVersions.addAll(versions)
        changelogType = "markdown"
        client = true
    }
}
