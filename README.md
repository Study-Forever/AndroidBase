1.在settings.gradle中添加依赖地址

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

2.在app的build.gradle中添加

implementation 'com.github.Study-Forever:AndroidBase:v1.0'
