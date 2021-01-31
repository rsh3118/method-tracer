package com.github.rsh3118.methodtracer.services

import com.intellij.openapi.project.Project
import com.github.rsh3118.methodtracer.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
