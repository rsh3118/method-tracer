package com.github.rsh3118.methodtracer.toolwindows

import com.github.rsh3118.methodtracer.model.APIClassDependency
import com.github.rsh3118.methodtracer.model.APIMethodDependency
import com.github.rsh3118.methodtracer.model.ApplicationClassDependency
import com.github.rsh3118.methodtracer.model.ApplicationMethodDependency
import com.github.rsh3118.methodtracer.model.DBClassDependency
import com.github.rsh3118.methodtracer.model.DBMethodDependency
import com.github.rsh3118.methodtracer.model.Dependency
import com.github.rsh3118.methodtracer.model.ExternalServiceClassDependency
import com.github.rsh3118.methodtracer.model.ExternalServiceMethodDependency
import com.github.rsh3118.methodtracer.model.Project
import com.github.rsh3118.methodtracer.model.SQSClassDependency
import com.github.rsh3118.methodtracer.model.SQSMethodDependency
import com.github.rsh3118.methodtracer.model.ScheduledTaskClassDependency
import com.github.rsh3118.methodtracer.model.ScheduledTaskMethodDependency
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.ImageTranscoder
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import kotlin.reflect.KClass


class DescriptionRenderer : DefaultTreeCellRenderer {
    var eyeIcon: ImageIcon
    var functionIcon: ImageIcon
    var warningIcon: ImageIcon
    var failIcon: ImageIcon
    var sqsIcon: ImageIcon
    var cloudIcon: ImageIcon
    var dbIcon: ImageIcon
    var scheduledIcon: ImageIcon
    var classIcon: ImageIcon
    var apiIcon: ImageIcon
    var folderIcon: ImageIcon

    constructor(): super(){
        this.backgroundNonSelectionColor = Color(1f,1f,1f,0f )
    }


    override fun getPreferredSize(): Dimension? {
        val d = super.getPreferredSize()
        d.height = 50
        return d
    }


    override fun getTreeCellRendererComponent(tree: JTree, value: Any,
                                              sel: Boolean, expanded: Boolean, leaf: Boolean, row: Int,
                                              hasFocus: Boolean): Component {
        super.getTreeCellRendererComponent(tree, "", sel, expanded, leaf,
                row, hasFocus)
        val treeNode = value as DefaultMutableTreeNode
        if(treeNode.userObject is String){
            this.icon = folderIcon
            this.text = treeNode.userObject as String
            return this
        }
        if(treeNode.userObject !is Description){
            this.icon = functionIcon
            this.text = "unknown"
            return this
        }
        val description = value.userObject as Description
        val dependency = description.dependency
        val dimension = super.getPreferredSize()
        dimension.height = 30
        this.size = dimension
        this.text = dependency.name
        this.icon = setIcon(dependency)
        if(description.status == Status.SELECTED){
            if(sel){
                this.foreground = Color(128, 203, 196)
            }else{
                this.foreground = Color(130, 170, 255)
            }
        }
        if(description.status == Status.ERROR){
            if(sel){
                this.foreground = Color(247, 82, 110)
            }else{
                this.foreground = Color(247, 82, 110)
            }
        }
        if(description.status == Status.COMPROMISED){
            if(sel){
                this.foreground = Color(245, 166, 135)
            }else{
                this.foreground = Color(245, 166, 135)
            }
        }
        return this
    }

    private fun setIcon(dependency: Dependency): ImageIcon {
        if(dependency is SQSClassDependency){
            return sqsIcon
        }
        if(dependency is DBClassDependency){
            return dbIcon
        }
        if(dependency is APIClassDependency){
            return apiIcon
        }
        if(dependency is ApplicationClassDependency){
            return classIcon
        }
        if(dependency is ExternalServiceClassDependency){
            return cloudIcon
        }
        if(dependency is ScheduledTaskClassDependency){
            return scheduledIcon
        }
        if(dependency is SQSMethodDependency){
            return sqsIcon
        }
        if(dependency is DBMethodDependency){
            return dbIcon
        }
        if(dependency is APIMethodDependency){
            return apiIcon
        }
        if(dependency is ApplicationMethodDependency){
            return functionIcon
        }
        if(dependency is ExternalServiceMethodDependency){
            return cloudIcon
        }
        if(dependency is ScheduledTaskMethodDependency){
            return scheduledIcon
        }
        if(dependency is Project){
            return folderIcon
        }
        throw Exception();
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 967937360839244309L
    }

    init {

        eyeIcon = createSVGIcon("/icons/svg/eye.svg")
        functionIcon = createSVGIcon("/icons/svg/class.svg")
        warningIcon = createSVGIcon("/icons/svg/warning.svg")
        failIcon = createSVGIcon("/icons/svg/error.svg")
        sqsIcon = createSVGIcon("/icons/svg/calendar.svg")
        cloudIcon = createSVGIcon("/icons/svg/cloud.svg")
        dbIcon = ImageIcon(javaClass.getResource("/icons/database.png"))
        scheduledIcon = createSVGIcon("/icons/svg/scheduled.svg")
        classIcon = createSVGIcon("/icons/svg/class.svg")
        apiIcon = createSVGIcon("/icons/svg/api.svg")
        folderIcon = createSVGIcon("/icons/svg/folder.svg")
    }

    private fun createSVGIcon(url: String): ImageIcon {
        return ImageIcon(SVGImage(javaClass.getResource(url)).getImage(13,13))
    }
}