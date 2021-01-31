package com.github.rsh3118.methodtracer.model

public abstract class Dependency(public val name: String) {
    companion object {

    }
}

public class Project(name: String): Dependency(name){}

public class SQSClassDependency(name: String) : Dependency(name) {}

public class DBClassDependency(name: String) : Dependency(name) {}

public class APIClassDependency(name: String) : Dependency(name) {}

public class ApplicationClassDependency(name: String) : Dependency(name) {}

public class ExternalServiceClassDependency(name: String) : Dependency(name) {}

public class ScheduledTaskClassDependency(name: String) : Dependency(name) {}

public class SQSMethodDependency(name: String) : Dependency(name) {}

public class DBMethodDependency(name: String) : Dependency(name) {}

public class APIMethodDependency(name: String) : Dependency(name) {}

public class ApplicationMethodDependency(name: String) : Dependency(name) {}

public class ExternalServiceMethodDependency(name: String) : Dependency(name) {}

public class ScheduledTaskMethodDependency(name: String) : Dependency(name) {}