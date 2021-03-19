package com.github.rsh3118.methodtracer.toolwindows;

import com.github.rsh3118.methodtracer.mocks.IEDAAction
import com.github.rsh3118.methodtracer.mocks.MockData
import com.github.rsh3118.methodtracer.toolwindows.DescriptionTreeCreator.Companion.createProjectTreeWithErrorTraceFromTopLevelDependenciesToSelectedDependency
import com.intellij.ui.treeStructure.Tree
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTree

class MethodTracerToolWindow {
    private lateinit var content: JPanel
    private lateinit var treeTracer: JTree
    private lateinit var scrollPane: JScrollPane

    fun getContent(): JPanel{
        return content
    }

    fun createUIComponents(){
        val iedaActionMap = MockData.mockIEDAMethodDependencyGraph()
        val dependency = iedaActionMap[IEDAAction.DB_METHOD_STORE_USER] ?: error("action doesn't exist in action map")
        treeTracer = createProjectTreeWithErrorTraceFromTopLevelDependenciesToSelectedDependency(dependency)
    }
}
