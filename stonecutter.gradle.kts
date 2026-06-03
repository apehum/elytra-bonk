plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.15.5" apply false
}
stonecutter active "26.1-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "neoforge")
}

gradle.projectsEvaluated {
    subprojects.sortedBy { it.name }.zipWithNext { prev, curr ->
        curr.tasks.matching { it.name.startsWith("publish") }.configureEach {
            val taskName = name
            mustRunAfter(prev.tasks.matching { it.name == taskName })
        }
    }
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
    }
}
