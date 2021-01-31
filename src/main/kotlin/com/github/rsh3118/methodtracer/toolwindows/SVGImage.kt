package com.github.rsh3118.methodtracer.toolwindows

import org.apache.batik.anim.dom.SAXSVGDocumentFactory
import org.apache.batik.bridge.BridgeContext
import org.apache.batik.bridge.GVTBuilder
import org.apache.batik.bridge.UserAgentAdapter
import org.apache.batik.bridge.ViewBox
import org.apache.batik.gvt.GraphicsNode
import org.apache.batik.util.XMLResourceDescriptor
import org.w3c.dom.Element
import org.w3c.dom.svg.SVGDocument
import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.net.URL


/**
 * Immutable class to get the Image representation of a svg resource.
 */
class SVGImage {
    /**
     * Get the svg root node of the document.
     *
     * @return svg root node.
     */
    /** Root node of svg document  */
    val rootSvgNode: GraphicsNode

    /**
     * Get the svg document.
     * @return the svg document.
     */
    /** Loaded SVG document  */
    val svgDocument: SVGDocument

    /**
     * Load the svg resource from a URL into a document.
     * @param url location of svg resource.
     * @throws java.io.IOException when svg resource cannot be read.
     */
    constructor(url: URL) {
        val parser = XMLResourceDescriptor.getXMLParserClassName()
        val factory = SAXSVGDocumentFactory(parser)
        svgDocument = factory.createDocument(url.toString()) as SVGDocument
        rootSvgNode = getRootNode(svgDocument)
    }

    /**
     * Load the svg from a document.
     *
     * @param document svg resource
     */
    constructor(document: SVGDocument) {
        svgDocument = document
        rootSvgNode = getRootNode(svgDocument)
    }

    /**
     * Renders and returns the svg based image.
     *
     * @param width desired width
     * @param height desired height
     * @return image of the rendered svg.
     */
    fun getImage(width: Int, height: Int): Image {
        // Paint svg into image buffer
        val bufferedImage = BufferedImage(width,
                height, BufferedImage.TYPE_INT_ARGB)
        val g2d = bufferedImage.graphics as Graphics2D

        // For a smooth graphic with no jagged edges or rastorized look.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR)

        // Scale image to desired size
        val elt: Element = svgDocument.rootElement
        val userAgentAdapter = UserAgentAdapter()
        val bridgeContext = BridgeContext(userAgentAdapter)
        val usr2dev = ViewBox.getViewTransform(null, elt,
                width.toFloat(), height.toFloat(), bridgeContext)
        g2d.transform(usr2dev)
        rootSvgNode.paint(g2d)

        // Cleanup and return image
        g2d.dispose()
        return bufferedImage
    }

    companion object {
        /**
         * Get svg root from the given document.
         *
         * @param document svg resource
         */
        private fun getRootNode(document: SVGDocument): GraphicsNode {
            // Build the tree and get the document dimensions
            val userAgentAdapter = UserAgentAdapter()
            val bridgeContext = BridgeContext(userAgentAdapter)
            val builder = GVTBuilder()
            return builder.build(bridgeContext, document)
        }
    }
}