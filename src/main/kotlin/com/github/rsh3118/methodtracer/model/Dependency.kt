package com.github.rsh3118.methodtracer.model

import com.intellij.psi.PsiElement

public abstract class Dependency(public val name: String) {
    companion object {

    }

    override fun toString(): String {
        return this.name
    }
}

public class Project(name: String): Dependency(name){
    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class SQSClassDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class DBClassDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class APIClassDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class ApplicationClassDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class ExternalServiceClassDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class ScheduledTaskClassDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class SQSMethodDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class DBMethodDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class APIMethodDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class ApplicationMethodDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class ExternalServiceMethodDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}

public class ScheduledTaskMethodDependency(name: String) : Dependency(name) {

    constructor(name: String, psiClass: PsiElement){

    }

    override fun toString(): String {
        return "${this.javaClass.name}:${this.name}"
    }
}