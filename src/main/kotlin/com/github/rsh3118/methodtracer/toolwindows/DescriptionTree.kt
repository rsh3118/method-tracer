package com.github.rsh3118.methodtracer.toolwindows

import com.github.rsh3118.methodtracer.model.Dependency
import com.github.rsh3118.methodtracer.model.Node
import com.intellij.ui.treeStructure.Tree
import java.util.LinkedList
import java.util.Queue
import javax.swing.tree.DefaultMutableTreeNode

class DescriptionTreeCreator {
    companion object {
        private fun createDescriptionTreeNode(node: Node<Dependency>): DescriptionTreeNode{
            val mtn = DescriptionTreeNode(node.value)
            for(item in node.directDownstreams){
                mtn.add(createDescriptionTreeNode(item))
            }
            return mtn
        }

        private fun createDescriptionTreeNode(node: Node<Dependency>, errorNodes: Set<Node<Dependency>>, compromisedNodes: Set<Node<Dependency>>): DescriptionTreeNode{
            var mtn = DescriptionTreeNode(node.value, Status.NONE)
            if(node in errorNodes){
                println("red: ${node}")
                mtn = DescriptionTreeNode(node.value, Status.ERROR)
            }else if (node in compromisedNodes){
                println("yellow: ${node}")
                mtn = DescriptionTreeNode(node.value, Status.COMPROMISED)
            }
            for(item in node.directDownstreams){
                mtn.add(createDescriptionTreeNode(item, errorNodes, compromisedNodes))
            }
            return mtn
        }

        private fun createDescriptionTreeNode(node: Node<Dependency>, nodesInPath: Set<Node<Dependency>>): DescriptionTreeNode{
            var mtn = DescriptionTreeNode(node.value, Status.NONE)
            if(node in nodesInPath){
                mtn = DescriptionTreeNode(node.value, Status.SELECTED)
            }
            for(item in node.directDownstreams){
                mtn.add(createDescriptionTreeNode(item, nodesInPath))
            }
            return mtn
        }

        private fun createMutableTreeNodeFromDependencySet(node: Node<Dependency>, nodeSet: Set<Node<Dependency>>): DefaultMutableTreeNode{
            val mtn = DefaultMutableTreeNode(node.value)
            for(item in node.directDownstreams){
                if(item in nodeSet){
                    mtn.add(createDescriptionTreeNode(item))
                }
            }
            return mtn
        }

        fun createTreeWithOnlySelectedDependenciesAndItsDownstreamDependencies(dependency: Node<Dependency>): Tree {
            return wrapInTree(createDescriptionTreeNode(dependency));
        }

        fun createProjectTreeWithTraceFromTopLevelDependenciesToSelectedDependencyHighlighted(dependency: Node<Dependency>): Tree {
            val nodesInPathFromDependencyToTopLevelDependencies: MutableSet<Node<Dependency>> = mutableSetOf()
            val topLevelDependencies: MutableSet<Node<Dependency>> = mutableSetOf()
            val queue: Queue<Node<Dependency>> = LinkedList()
            queue.add(dependency)
            var i = 0
            while(queue.isNotEmpty()){
                val current = queue.poll();
                nodesInPathFromDependencyToTopLevelDependencies.add(current)
                for(parent in current.directUpstreams){
                    queue.add(parent)
                }
                if(current.directUpstreams.size == 0){
                    topLevelDependencies.add(current)
                }
                i += 1
            }
            val titleNode = DefaultMutableTreeNode("Upstream Dependencies ${dependency.value.name}")
            for(topLevelDependency in topLevelDependencies){
                titleNode.add(createDescriptionTreeNode(topLevelDependency, nodesInPathFromDependencyToTopLevelDependencies))
            }

            return wrapInTree(titleNode);
        }

        fun createProjectTreeWithErrorTraceFromTopLevelDependenciesToSelectedDependency(dependency: Node<Dependency>): Tree {
            val nodesInPathFromDependencyToTopLevelDependencies: MutableSet<Node<Dependency>> = mutableSetOf()
            val topLevelDependencies: MutableSet<Node<Dependency>> = mutableSetOf()
            val queue: Queue<Node<Dependency>> = LinkedList()
            queue.add(dependency)
            var i = 0
            while(queue.isNotEmpty()){
                val current = queue.poll();
                nodesInPathFromDependencyToTopLevelDependencies.add(current)
                for(parent in current.directUpstreams){
                    queue.add(parent)
                }
                if(current.directUpstreams.size == 0){
                    topLevelDependencies.add(current)
                }
                i += 1
            }
            val titleNode = DefaultMutableTreeNode("Upstream Dependencies ${dependency.value.name}")
            for(topLevelDependency in topLevelDependencies){
                titleNode.add(createDescriptionTreeNode(topLevelDependency, setOf(dependency) ,nodesInPathFromDependencyToTopLevelDependencies))
            }

            return wrapInTree(titleNode);
        }

        private fun wrapInTree(dmtNode: DefaultMutableTreeNode): Tree {
            val tree = Tree(dmtNode);
            tree.cellRenderer = DescriptionRenderer()
            return tree
        }
    }
}

class DescriptionTreeNode: DefaultMutableTreeNode {

    constructor(dependency: Dependency): super(Description(dependency, Status.NONE))

    constructor(dependency: Dependency, status: Status): super(Description(dependency, status))
}

class Description(val dependency: Dependency, val status: Status)