package com.github.rsh3118.methodtracer.mocks

import com.github.rsh3118.methodtracer.model.APIClassDependency
import com.github.rsh3118.methodtracer.model.APIMethodDependency
import com.github.rsh3118.methodtracer.model.ApplicationClassDependency
import com.github.rsh3118.methodtracer.model.ApplicationMethodDependency
import com.github.rsh3118.methodtracer.model.DBClassDependency
import com.github.rsh3118.methodtracer.model.DBMethodDependency
import com.github.rsh3118.methodtracer.model.Dependency
import com.github.rsh3118.methodtracer.model.ExternalServiceClassDependency
import com.github.rsh3118.methodtracer.model.ExternalServiceMethodDependency
import com.github.rsh3118.methodtracer.model.Node
import com.github.rsh3118.methodtracer.model.Project
import com.github.rsh3118.methodtracer.model.SQSClassDependency
import com.github.rsh3118.methodtracer.model.SQSMethodDependency
import com.github.rsh3118.methodtracer.model.ScheduledTaskClassDependency
import com.github.rsh3118.methodtracer.model.ScheduledTaskMethodDependency
import java.util.EnumMap

public enum class IEDAAction {
    PROJECT_ID_EXTERNAL_DIRECTORY_API,
    API_METHOD_CREATE_USER,
    APPLICATION_METHOD_CREATE_USER,
    APPLICATION_METHOD_CREATE_OR_FIND_ATLASSIAN_ACCOUNT,
    EXTERNAL_SERVICE_METHOD_AA_CHECK_IF_ATLASSIAN_ACCOUNT_EXISTS,
    EXTERNAL_SERVICE_METHOD_AA_CREATE_ATLASSIAN_ACCOUNT,
    EXTERNAL_SERVICE_METHOD_DCS_METHOD_CHECK_USER_DOMAIN,
    APPLICATION_METHOD_CREATE_MANAGED_USER,
    EXTERNAL_SERVICE_METHOD_AA_UPDATE_ATLASSIAN_ACCOUNT,
    APPLICATION_METHOD_CREATE_UNMANAGED_USER,
    DB_METHOD_STORE_USER,
    APPLICATION_METHOD_ADD_USER_TO_DEFAULT_GROUP,
    APPLICATION_METHOD_ADD_USER_TO_GROUP,
    DB_METHOD_UPDATE_GROUP_MEMBERSHIPS,
    DB_METHOD_UPDATE_GROUP_LSM,
    API_METHOD_CREATE_DIRECTORY,
    EXTERNAL_SERVICE_METHOD_TCS_FIND_SITES_LINKED_TO_ORG,
    APPLICATION_METHOD_CREATE_DIRECTORY,
    DB_METHOD_STORE_DIRECTORY,
    DB_METHOD_STORE_DIRECTORY_SITE_MAPPINGS,
    APPLICATION_METHOD_CREATE_DEFAULT_GROUP,
    DB_METHOD_STORE_GROUP,
    API_METHOD_CREATE_GROUP,
    APPLICATION_METHOD_CREATE_GROUP,
    SQS_METHOD_ADD_SITE_EVENT_LISTENER,
    APPLICATION_METHOD_ADD_SITE_EVENT_LISTENER,
    DB_METHOD_STORE_DIRECTORY_UPDATE_EVENT,
    SCHEDULED_JOB_METHOD_UPDATE_GROUP,
    APPLICATION_METHOD_GET_STALE_GROUPS,
    DB_METHOD_FIND_GROUPS_WITH_LSM_HIGHER_THAN_DIRECTORY_LSM,
    APPLICATION_METHOD_PROCESS_GROUP_WITH_LOWEST_LSM,
    APPLICATION_METHOD_GET_SITE_IDS,
    DB_METHOD_GET_SITE_IDS_FOR_DIRECTORY,
    APPLICATION_METHOD_UPDATE_GROUP_IN_SITES,
    EXTERNAL_SERVICE_METHOD_IIGA_GET_GROUP_IN_SITE,
    APPLICATION_METHOD_FIND_GROUP_DIFFERENCES,
    EXTERNAL_SERVICE_METHOD_IIGA_PATCH_GROUP_IN_SITE,
    DB_METHOD_INCREMENT_GROUP_LSM,
    SCHEDULED_JOB_METHOD_UPDATE_DIRECTORY,
    APPLICATION_METHOD_GET_STALE_DIRECTORIES,
    DB_METHOD_GET_OLDEST_DIRECTORY_UPDATE_EVENT,
    DB_METHOD_UPDATE_LSM_FOR_ALL_GROUPS
}

class MockData{

    companion object {
        public fun mockIEDAMethodDependencyGraph(): Map<IEDAAction, Node<Dependency>>{
            // When changing the calls below make sure you dont double up adding downstream dependencies
            // if
            // a -> b -> c -> d
            // z -> b -> c -> d
            // we are setting it as follows
            // a -> b
            // b -> c
            // c -> d
            // z -> b
            // (we need not do b -> c -> d as it was already done previously)
            val dMap: Map<IEDAAction, Node<Dependency>> = createIEDAEventToDependencyMap()
            addIEDACreateUser(dMap)
            addIEDACreateDirectory(dMap)
            addIEDACreateGroup(dMap)
            addIEDAddSite(dMap)
            addIEDAPushGroupToSite(dMap)
            addIEDAUpdateDirectory(dMap)
            return dMap
        }

        private fun createIEDAEventToDependencyMap(): Map<IEDAAction, Node<Dependency>>{
            val dependencyMap: MutableMap<IEDAAction, Node<Dependency>> = mutableMapOf()
            val apiMethodPrefix = "API_METHOD"
            val dbMethodPrefix = "DB_METHOD"
            val appMethodPrefix = "APPLICATION_METHOD"
            val scheduledMethodPrefix = "SCHEDULED_JOB_METHOD"
            val serviceMethodPrefix = "EXTERNAL_SERVICE_METHOD"
            val sqsMethodPrefix = "SQS_METHOD"
            val apiClassPrefix = "API_CLASS"
            val dbClassPrefix = "DB_CLASS"
            val appClassPrefix = "APPLICATION_CLASS"
            val scheduledClassPrefix = "SCHEDULED_JOB_CLASS"
            val serviceClassPrefix = "EXTERNAL_SERVICE_CLASS"
            val sqsClassPrefix = "SQS_CLASS"
            val projectPrefix = "PROJECT"
            val prefixes = listOf<String>(
                    apiMethodPrefix,
                    dbMethodPrefix,
                    appMethodPrefix,
                    scheduledMethodPrefix,
                    serviceMethodPrefix,
                    sqsMethodPrefix,
                    apiClassPrefix,
                    dbClassPrefix,
                    appClassPrefix,
                    scheduledClassPrefix,
                    serviceClassPrefix,
                    sqsClassPrefix,
                    projectPrefix
            )

            for(action in enumValues<IEDAAction>()){
                var actionString = action.toString()
                var selectedPrefix: String? = null
                for(prefix in prefixes){
                    println("checking if ${actionString} prefixed with ${prefix}")
                    if(actionString.startsWith(prefix)){
                        actionString = actionString.replace(prefix + "_", "")
                        selectedPrefix = prefix
                    }
                    if(selectedPrefix != null){
                        break
                    }
                }
                if(selectedPrefix == null){
                    throw Error("could not find prefix for ${actionString}")
                }
                if(selectedPrefix == serviceClassPrefix || selectedPrefix == serviceMethodPrefix){
                    actionString.replaceFirst("_", ":")
                }
                val readableActionDescription = actionString.replace("_", " ").toLowerCase()
                when(selectedPrefix) {
                    apiMethodPrefix -> dependencyMap.put(action, Node(APIMethodDependency(readableActionDescription)))
                    dbMethodPrefix -> dependencyMap.put(action, Node(DBMethodDependency(readableActionDescription)))
                    appMethodPrefix -> dependencyMap.put(action, Node(ApplicationMethodDependency(readableActionDescription)))
                    scheduledMethodPrefix -> dependencyMap.put(action, Node(ScheduledTaskMethodDependency(readableActionDescription)))
                    serviceMethodPrefix -> dependencyMap.put(action, Node(ExternalServiceMethodDependency(readableActionDescription)))
                    sqsMethodPrefix -> dependencyMap.put(action, Node(SQSMethodDependency(readableActionDescription)))
                    apiClassPrefix -> dependencyMap.put(action, Node(APIClassDependency(readableActionDescription)))
                    dbClassPrefix -> dependencyMap.put(action, Node(DBClassDependency(readableActionDescription)))
                    appClassPrefix -> dependencyMap.put(action, Node(ApplicationClassDependency(readableActionDescription)))
                    scheduledClassPrefix -> dependencyMap.put(action, Node(ScheduledTaskClassDependency(readableActionDescription)))
                    serviceClassPrefix -> dependencyMap.put(action, Node(ExternalServiceClassDependency(readableActionDescription)))
                    sqsClassPrefix -> dependencyMap.put(action, Node(SQSClassDependency(readableActionDescription)))
                    projectPrefix -> dependencyMap.put(action, Node(Project(readableActionDescription)))
                }
            }
            return dependencyMap
        }

        private fun addDownstreamDependency(map: Map<IEDAAction, Node<Dependency>>, action: IEDAAction, downstreamAction: IEDAAction){
            map[downstreamAction]?.let { (map[action] ?: error("")).addDownstreamDependency(it) }
        }

        private fun addIEDACreateUser(dMap: Map<IEDAAction, Node<Dependency>>){
            addDownstreamDependency(
                    dMap,
                    IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API,
                    IEDAAction.API_METHOD_CREATE_USER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.API_METHOD_CREATE_USER,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER,
                    IEDAAction.APPLICATION_METHOD_CREATE_OR_FIND_ATLASSIAN_ACCOUNT
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_OR_FIND_ATLASSIAN_ACCOUNT,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_AA_CHECK_IF_ATLASSIAN_ACCOUNT_EXISTS
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_OR_FIND_ATLASSIAN_ACCOUNT,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_AA_CREATE_ATLASSIAN_ACCOUNT
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_DCS_METHOD_CHECK_USER_DOMAIN
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER,
                    IEDAAction.APPLICATION_METHOD_CREATE_MANAGED_USER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_MANAGED_USER,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_AA_UPDATE_ATLASSIAN_ACCOUNT
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER,
                    IEDAAction.APPLICATION_METHOD_CREATE_UNMANAGED_USER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER,
                    IEDAAction.DB_METHOD_STORE_USER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_USER,
                    IEDAAction.APPLICATION_METHOD_ADD_USER_TO_DEFAULT_GROUP
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_ADD_USER_TO_DEFAULT_GROUP,
                    IEDAAction.APPLICATION_METHOD_ADD_USER_TO_GROUP
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_ADD_USER_TO_GROUP,
                    IEDAAction.DB_METHOD_UPDATE_GROUP_MEMBERSHIPS
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_ADD_USER_TO_GROUP,
                    IEDAAction.DB_METHOD_UPDATE_GROUP_LSM
            )
        }

        private fun addIEDACreateDirectory(dMap: Map<IEDAAction, Node<Dependency>>){
            addDownstreamDependency(
                    dMap,
                    IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API,
                    IEDAAction.API_METHOD_CREATE_DIRECTORY
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.API_METHOD_CREATE_DIRECTORY,
                    IEDAAction.APPLICATION_METHOD_CREATE_DIRECTORY
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_DIRECTORY,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_TCS_FIND_SITES_LINKED_TO_ORG
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_DIRECTORY,
                    IEDAAction.DB_METHOD_STORE_DIRECTORY
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_DIRECTORY,
                    IEDAAction.DB_METHOD_STORE_DIRECTORY_SITE_MAPPINGS
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_DIRECTORY,
                    IEDAAction.APPLICATION_METHOD_CREATE_DEFAULT_GROUP
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_DEFAULT_GROUP,
                    IEDAAction.APPLICATION_METHOD_CREATE_GROUP
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_CREATE_GROUP,
                    IEDAAction.DB_METHOD_STORE_GROUP
            )
        }

        private fun addIEDACreateGroup(dMap: Map<IEDAAction, Node<Dependency>>){
            addDownstreamDependency(
                    dMap,
                    IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API,
                    IEDAAction.API_METHOD_CREATE_GROUP
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.API_METHOD_CREATE_GROUP,
                    IEDAAction.APPLICATION_METHOD_CREATE_GROUP
            )
        }

        private fun addIEDAddSite(dMap: Map<IEDAAction, Node<Dependency>>){
            addDownstreamDependency(
                    dMap,
                    IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API,
                    IEDAAction.SQS_METHOD_ADD_SITE_EVENT_LISTENER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.SQS_METHOD_ADD_SITE_EVENT_LISTENER,
                    IEDAAction.APPLICATION_METHOD_ADD_SITE_EVENT_LISTENER
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_ADD_SITE_EVENT_LISTENER,
                    IEDAAction.DB_METHOD_STORE_DIRECTORY_UPDATE_EVENT
            )
        }

        private fun addIEDAPushGroupToSite(dMap: Map<IEDAAction, Node<Dependency>>){
            addDownstreamDependency(
                    dMap,
                    IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API,
                    IEDAAction.SCHEDULED_JOB_METHOD_UPDATE_GROUP
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.SCHEDULED_JOB_METHOD_UPDATE_GROUP,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_GROUPS
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_GROUPS,
                    IEDAAction.DB_METHOD_FIND_GROUPS_WITH_LSM_HIGHER_THAN_DIRECTORY_LSM
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_GROUPS,
                    IEDAAction.APPLICATION_METHOD_PROCESS_GROUP_WITH_LOWEST_LSM
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_PROCESS_GROUP_WITH_LOWEST_LSM,
                    IEDAAction.APPLICATION_METHOD_GET_SITE_IDS
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_SITE_IDS,
                    IEDAAction.DB_METHOD_GET_SITE_IDS_FOR_DIRECTORY
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_PROCESS_GROUP_WITH_LOWEST_LSM,
                    IEDAAction.APPLICATION_METHOD_UPDATE_GROUP_IN_SITES
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_UPDATE_GROUP_IN_SITES,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_IIGA_GET_GROUP_IN_SITE
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_UPDATE_GROUP_IN_SITES,
                    IEDAAction.APPLICATION_METHOD_FIND_GROUP_DIFFERENCES
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_UPDATE_GROUP_IN_SITES,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_IIGA_PATCH_GROUP_IN_SITE
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_PROCESS_GROUP_WITH_LOWEST_LSM,
                    IEDAAction.DB_METHOD_INCREMENT_GROUP_LSM
            )
        }

        private fun addIEDAUpdateDirectory(dMap: Map<IEDAAction, Node<Dependency>>){
            addDownstreamDependency(
                    dMap,
                    IEDAAction.PROJECT_ID_EXTERNAL_DIRECTORY_API,
                    IEDAAction.SCHEDULED_JOB_METHOD_UPDATE_DIRECTORY
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.SCHEDULED_JOB_METHOD_UPDATE_DIRECTORY,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_DIRECTORIES
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_DIRECTORIES,
                    IEDAAction.DB_METHOD_GET_OLDEST_DIRECTORY_UPDATE_EVENT
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_DIRECTORIES,
                    IEDAAction.DB_METHOD_GET_OLDEST_DIRECTORY_UPDATE_EVENT
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_DIRECTORIES,
                    IEDAAction.EXTERNAL_SERVICE_METHOD_TCS_FIND_SITES_LINKED_TO_ORG
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_DIRECTORIES,
                    IEDAAction.DB_METHOD_STORE_DIRECTORY_SITE_MAPPINGS
            )
            addDownstreamDependency(
                    dMap,
                    IEDAAction.APPLICATION_METHOD_GET_STALE_DIRECTORIES,
                    IEDAAction.DB_METHOD_UPDATE_LSM_FOR_ALL_GROUPS
            )
        }
    }
}