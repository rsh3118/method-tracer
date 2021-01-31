package com.github.rsh3118.methodtracer.toolwindows

import com.github.rsh3118.methodtracer.services.MyProjectService
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.content.ContentFactory

class MethodTracerToolWindowFactory: com.intellij.openapi.wm.ToolWindowFactory {
    // Create the tool window content.
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val callGraphToolWindow = MethodTracerToolWindow()

        // register the call graph tool window as a project service, so it can be accessed by editor menu actions.
        val callGraphToolWindowProjectService =
                ServiceManager.getService(project, MyProjectService::class.java)

        // register the tool window content
        val content = ContentFactory.SERVICE.getInstance().createContent(callGraphToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}