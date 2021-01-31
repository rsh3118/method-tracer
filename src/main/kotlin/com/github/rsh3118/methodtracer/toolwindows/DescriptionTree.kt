package com.github.rsh3118.methodtracer.toolwindows

import com.github.rsh3118.methodtracer.model.Dependency
import com.github.rsh3118.methodtracer.model.Node
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode

class DescriptionTreeCreator {
    companion object {
        public fun createRootTreeNode(node: Node<Dependency>): DefaultMutableTreeNode{
            val root = createMutableTreeNode(node)
            return root
        }

        private fun createMutableTreeNode(node: Node<Dependency>): DefaultMutableTreeNode{
            val mtn = DefaultMutableTreeNode(node.value)
            for(item in node.directDownstreams){
                mtn.add(createMutableTreeNode(item))
            }
            return mtn
        }
    }
}