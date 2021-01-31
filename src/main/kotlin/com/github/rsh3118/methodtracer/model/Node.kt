package com.github.rsh3118.methodtracer.model;

public class Node<T>(public val value: T) {
    // order doesn't matter for upstreams
    public var directUpstreams: MutableSet<Node<T>> = mutableSetOf()
    // order matters when displaying downstream dependencies because we want to show the order of actions
    public var directDownstreams: MutableList<Node<T>> = mutableListOf()

    fun addUpstreamDependency(node: Node<T>){
        directUpstreams.add(node)
        node.directDownstreams.add(node)
    }

    fun addDownstreamDependency(node: Node<T>){
        directDownstreams.add(node)
        node.directUpstreams.plus(node)
    }

    fun getTopLevelUpstreamDependencies(): Set<Node<T>>{
        val topLevels: Set<Node<T>> = emptySet()
        getTopLevelDependenciesHelper(topLevels, this)
        return topLevels
    }

    private fun getTopLevelDependenciesHelper(topLevels: Set<Node<T>>, node: Node<T>){
        for (upstreamDependency in node.directUpstreams){
            if(upstreamDependency.directUpstreams.isEmpty()){
                topLevels.plus(upstreamDependency)
            }else{
                getTopLevelDependenciesHelper(topLevels, upstreamDependency)
            }
        }
    }
}
