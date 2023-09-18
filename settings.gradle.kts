pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() //추가
        maven(url = "https://jitpack.io") //추가
//        maven {
//            url = uri("https://jitpack.io")
//        }
    }
}



rootProject.name = "SoGaeTing"
include(":app")
