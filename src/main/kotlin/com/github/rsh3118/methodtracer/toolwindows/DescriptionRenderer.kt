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
import java.awt.Component
import java.awt.Dimension
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import kotlin.reflect.KClass


class DescriptionRenderer : DefaultTreeCellRenderer() {
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
        print(treeNode.userObject)
        if(treeNode.userObject !is Dependency){
            icon = functionIcon
            text = "unknown"
            return this
        }
        val dependency = value.userObject as Dependency
        val dimension = super.getPreferredSize()
        dimension.height = 30
        size = dimension
        text = dependency.name
        setIcon(dependency)

        return this
    }

    private fun setIcon(dependency: Dependency){
        if(dependency is SQSClassDependency){
            icon = sqsIcon
        }
        if(dependency is DBClassDependency){
            icon = dbIcon
        }
        if(dependency is APIClassDependency){
            icon = apiIcon
        }
        if(dependency is ApplicationClassDependency){
            icon = classIcon
        }
        if(dependency is ExternalServiceClassDependency){
            icon = cloudIcon
        }
        if(dependency is ScheduledTaskClassDependency){
            icon = scheduledIcon
        }
        if(dependency is SQSMethodDependency){
            icon = sqsIcon
        }
        if(dependency is DBMethodDependency){
            icon = dbIcon
        }
        if(dependency is APIMethodDependency){
            icon = apiIcon
        }
        if(dependency is ApplicationMethodDependency){
            icon = functionIcon
        }
        if(dependency is ExternalServiceMethodDependency){
            icon = cloudIcon
        }
        if(dependency is ScheduledTaskMethodDependency){
            icon = scheduledIcon
        }
        if(dependency is Project){
            icon = folderIcon
        }
    }

    fun<T: Any> T.getClass(): KClass<T> {
        return javaClass.kotlin
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

//        val transcoder = MyTranscoder()
//
//        val impl = SVGDOMImplementation.getDOMImplementation();
//        val hints = TranscodingHints();
//        hints.put(ImageTranscoder.KEY_WIDTH, 300.00); // e.g. width=new Float(300)
//        hints.put(ImageTranscoder.KEY_HEIGHT, 300.00);// e.g. height=new Float(75)
//        hints.put(ImageTranscoder.KEY_DOM_IMPLEMENTATION, impl);
//        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI, SVGConstants.SVG_NAMESPACE_URI);
//        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT_NAMESPACE_URI,SVGConstants.SVG_NAMESPACE_URI);
//        hints.put(ImageTranscoder.KEY_DOCUMENT_ELEMENT, SVGConstants.SVG_SVG_TAG);
//        hints.put(ImageTranscoder.KEY_XML_PARSER_VALIDATING, false);
//
//        transcoder.setTranscodingHints(hints);
//        val ti = TranscoderInput("/icons/class.svg")
//        transcoder.transcode(ti, null);
//        val image = transcoder.image

    }

    private fun createSVGIcon(url: String): ImageIcon {
        return ImageIcon(SVGImage(javaClass.getResource(url)).getImage(13,13))
    }
}

internal class MyTranscoder : ImageTranscoder() {
    var image: BufferedImage? = null
        private set

    override fun createImage(w: Int, h: Int): BufferedImage {
        image = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        return image as BufferedImage
    }

    override fun writeImage(img: BufferedImage?, out: TranscoderOutput?) {}
}