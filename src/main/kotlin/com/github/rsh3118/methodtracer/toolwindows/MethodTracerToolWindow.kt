package com.github.rsh3118.methodtracer.toolwindows;

import com.github.rsh3118.methodtracer.mocks.IEDAAction
import com.github.rsh3118.methodtracer.mocks.MockData
import com.github.rsh3118.methodtracer.model.Dependency
import com.intellij.ui.treeStructure.Tree
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode

public class MethodTracerToolWindow {
    private lateinit var content: JPanel
    private lateinit var treeTracer: JTree
    private lateinit var scrollPane: JScrollPane

    public fun getContent(): JPanel{
        return content
    }

    fun createUIComponents(){
        println("hello")
        val style = DefaultMutableTreeNode("Style")
        val color = DefaultMutableTreeNode("color")
        val font = DefaultMutableTreeNode("font")
        style.add(color)
        style.add(font)
        val red = DefaultMutableTreeNode("red")
        val blue = DefaultMutableTreeNode("blue")
        val black = DefaultMutableTreeNode("black")
        val green = DefaultMutableTreeNode("green")
        color.add(red)
        color.add(blue)
        color.add(black)
        color.add(green)
        val iedaActionMap = MockData.mockIEDAMethodDependencyGraph()
        println("keys")
        println(iedaActionMap)
        for(key in iedaActionMap.keys){
            println(key)
        }
        val dependency = iedaActionMap[IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API] ?: error("action doesn't exist in action map")
        treeTracer = Tree(DescriptionTreeCreator.createRootTreeNode(dependency))
        treeTracer.cellRenderer = DescriptionRenderer()
    }
}
