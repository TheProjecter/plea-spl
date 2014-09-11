// $Id: FigMessage.java 11752 2007-01-08 00:54:54Z tfmorris $
// Copyright (c) 1996-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.diagram.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Vector;

import org.argouml.model.Model;
import org.argouml.notation.NotationProviderFactory2;
//#if defined(COLLABORATIONDIAGRAM)
//@#$LPS-COLLABORATIONDIAGRAM:GranularityType:Import
import org.argouml.uml.diagram.collaboration.ui.FigAssociationRole;
//#endif
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;
import org.tigris.gef.presentation.FigText;

/** 
 * Class to display graphics for a UML Message 
 * above an AssociationRole in a collaborations diagram.
 *
 * @author agauthie
 */
public class FigMessage extends FigNodeModelElement {

    private static Vector arrowDirections = new Vector(4);

    private FigPoly figPoly;

    private static final int SOUTH = 0;
    private static final int EAST = 1;
    private static final int WEST = 2;
    private static final int NORTH = 3;

    /**
     * The current arrow direction set to constants above.
     */
    private int arrowDirection = 0;

    /**
     * The main constructor.
     */
    public FigMessage() {
        setShadowSize(0); // Issue 2714.
	getNameFig().setLineWidth(0);
	getNameFig().setReturnAction(FigText.END_EDITING);
	getNameFig().setFilled(false);
	Dimension nameMin = getNameFig().getMinimumSize();
	getNameFig().setBounds(10, 10, 90, nameMin.height);
        getBigPort().setBounds(10, 10, 90, nameMin.height);

	figPoly = new FigPoly(Color.black, Color.black);
	int[] xpoints = {75, 75, 77, 75, 73, 75};
	int[] ypoints = {33, 24, 24, 15, 24, 24};
	Polygon polygon = new Polygon(xpoints, ypoints, 6);
	figPoly.setPolygon(polygon);
	figPoly.setBounds(100, 10, 5, 18);

        arrowDirections.setSize(4);
        arrowDirections.setElementAt("North", NORTH);
        arrowDirections.setElementAt("South", SOUTH);
        arrowDirections.setElementAt("East", EAST);
        arrowDirections.setElementAt("West", WEST);

        getBigPort().setFilled(false);
        getBigPort().setLineWidth(0);
	// add Figs to the FigNode in back-to-front order
        addFig(getBigPort());
	addFig(getNameFig());
	addFig(figPoly);

	Rectangle r = getBounds();
	setBounds(r.x, r.y, r.width, r.height);
    }

    /**
     * The constructor that hooks the Fig into an existing UML element
     * @param gm ignored
     * @param lay the layer
     * @param node the UML element
     */
    public FigMessage(GraphModel gm, Layer lay, Object node) {
	this();
	setLayer(lay);
	setOwner(node);
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigNodeModelElement#getNotationProviderType()
     */
    protected int getNotationProviderType() {
        return NotationProviderFactory2.TYPE_MESSAGE;
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigNodeModelElement#updateListeners(
     * java.lang.Object, java.lang.Object)
     */
    protected void updateListeners(Object oldOwner, Object newOwner) {
        if (oldOwner != null) {
            removeElementListener(oldOwner);
        }
        if (newOwner != null) {
            addElementListener(newOwner, "remove");
        }
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigNodeModelElement#placeString()
     */
    public String placeString() { return "new Message"; }

    /*
     * @see java.lang.Object#clone()
     */
    public Object clone() {
	FigMessage figClone = (FigMessage) super.clone();
	Iterator it = figClone.getFigs().iterator();
	figClone.setNameFig((FigText) it.next());
	figClone.figPoly = (FigPoly) it.next();
	//figClone._polygon = (Polygon) _polygon.clone();
	return figClone;
    }

    /*
     * @see org.tigris.gef.presentation.Fig#setLineColor(java.awt.Color)
     */
    public void setLineColor(Color col) {
	figPoly.setLineColor(col);
	getNameFig().setLineColor(col);
    }

    /*
     * @see org.tigris.gef.presentation.Fig#getLineColor()
     */
    public Color getLineColor() {
        return figPoly.getLineColor();
    }

    /*
     * @see org.tigris.gef.presentation.Fig#setFillColor(java.awt.Color)
     */
    public void setFillColor(Color col) {
	//figPoly.setFillColor(col);
	getNameFig().setFillColor(col);
    }
    
    /*
     * @see org.tigris.gef.presentation.Fig#getFillColor()
     */
    public Color getFillColor() {
        return getNameFig().getFillColor();
    }

    /*
     * @see org.tigris.gef.presentation.Fig#setFilled(boolean)
     */
    public void setFilled(boolean f) {
    }

    /*
     * @see org.tigris.gef.presentation.Fig#getFilled()
     */
    public boolean getFilled() {
        return true;
    }

    /*
     * @see org.tigris.gef.presentation.Fig#setLineWidth(int)
     */
    public void setLineWidth(int w) { figPoly.setLineWidth(w); }

    /*
     * @see org.tigris.gef.presentation.Fig#getLineWidth()
     */
    public int getLineWidth() { return figPoly.getLineWidth(); }

    /**
     * @param direction for the arrow
     * FigMessage.SOUTH
     * FigMessage.EAST
     * FigMessage.WEST
     * FigMessage.NORTH
     */
    public void setArrow(int direction) {
	Rectangle bbox = getBounds();

	if (arrowDirection == direction) {
	    return;
	}

	arrowDirection = direction;
	switch (direction) {
        case SOUTH: {
            int[] xpoints = {75, 75, 77, 75, 73, 75};
            int[] ypoints = {15, 24, 24, 33, 24, 24};
            Polygon polygon = new Polygon(xpoints, ypoints, 6);
            figPoly.setPolygon(polygon);
            break;
        }
        case EAST: {
            int[] xpoints = {66, 75, 75, 84, 75, 75};
            int[] ypoints = {24, 24, 26, 24, 22, 24};
            Polygon polygon = new Polygon(xpoints, ypoints, 6);
            figPoly.setPolygon(polygon);
            break;
        }
        case WEST: {
            int[] xpoints = {84, 75, 75, 66, 75, 75};
            int[] ypoints = {24, 24, 26, 24, 22, 24};
            Polygon polygon = new Polygon(xpoints, ypoints, 6);
            figPoly.setPolygon(polygon);
            break;
        }
        default: { // north
            int[] xpoints = {75, 75, 77, 75, 73, 75};
            int[] ypoints = {33, 24, 24, 15, 24, 24};
            Polygon polygon = new Polygon(xpoints, ypoints, 6);
            figPoly.setPolygon(polygon);
            break;
        }
        }
	setBounds(bbox);
    }

    /**
     * @return the arrow direction
     */
    public int getArrow() { return arrowDirection; }

    /*
     * @see org.tigris.gef.presentation.Fig#getMinimumSize()
     */
    public Dimension getMinimumSize() {
	Dimension nameMin = getNameFig().getMinimumSize();
	Dimension figPolyMin = figPoly.getSize();

	int h = Math.max(figPolyMin.height, nameMin.height);
	int w = figPolyMin.width + nameMin.width;
	return new Dimension(w, h);
    }

    /*
     * Override setBounds to keep shapes looking right.
     * @see org.tigris.gef.presentation.Fig#setBounds(int, int, int, int)
     */
    protected void setBoundsImpl(int x, int y, int w, int h) {
        if (getNameFig() == null) {
            return;
        }

        Rectangle oldBounds = getBounds();

        Dimension nameMin = getNameFig().getMinimumSize();

        int ht = 0;

        if (nameMin.height > figPoly.getHeight())
            ht = (nameMin.height - figPoly.getHeight()) / 2;

        getNameFig().setBounds(x, y, w - figPoly.getWidth(), nameMin.height);
        getBigPort().setBounds(x, y, w - figPoly.getWidth(), nameMin.height);
        figPoly.setBounds(x + getNameFig().getWidth(), y + ht,
        		   figPoly.getWidth(), figPoly.getHeight());

        firePropChange("bounds", oldBounds, getBounds());
        calcBounds(); //_x = x; _y = y; _w = w; _h = h;
        updateEdges();
    }

    /**
     * Determines the direction of the message arrow. Deetermination of the
     * type of arrow happens in modelchanged.
     */
    protected void updateArrow() {
  	Object mes = getOwner(); // Message
	if (mes == null || getLayer() == null) return;
	Object sender = Model.getFacade().getSender(mes); // ClassifierRole
	Object receiver = Model.getFacade().getReceiver(mes); // ClassifierRole
	Fig senderPort = getLayer().presentationFor(sender);
	Fig receiverPort = getLayer().presentationFor(receiver);
	if (senderPort == null || receiverPort == null) return;
	int sx = senderPort.getX();
	int sy = senderPort.getY();
	int rx = receiverPort.getX();
	int ry = receiverPort.getY();
	if (sx < rx && Math.abs(sy - ry) <= Math.abs(sx - rx)) { // east
	    setArrow(EAST);
	} else if (sx > rx && Math.abs(sy - ry) <= Math.abs(sx - rx)) { // west
	    setArrow(WEST);
	} else if (sy < ry) { // south
	    setArrow(SOUTH);
	} else {
	    setArrow(NORTH);
	}
    }
	//#if defined(COGNITIVE)    
    //@#$LPS-COGNITIVE:GranularityType:Statement
    /**
     * Add the FigMessage to the Path Items of its FigAssociationRole.
     * 
     * @param lay the Layer
     */
    public void addPathItemToFigAssociationRole(Layer lay) {
	Object associationRole =
	    Model.getFacade().getCommunicationConnection(getOwner());
	if (associationRole != null && lay != null) {
	    FigAssociationRole figAssocRole =
		(FigAssociationRole) lay.presentationFor(associationRole);
	    if (figAssocRole != null) {
		figAssocRole.addMessage(this);
		lay.bringToFront(this);
	    }
	}
    }
        //#endif

    /*
     * @see org.tigris.gef.presentation.Fig#paint(Graphics)
     */
    public void paint(Graphics g) {
	updateArrow();
	super.paint(g);
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigNodeModelElement#renderingChanged()
     */
    public void renderingChanged() {
        super.renderingChanged();
        updateArrow();
    }

    /**
     * @return Returns the arrowDirections.
     */
    public static Vector getArrowDirections() {
        return arrowDirections;
    }
} /* end class FigMessage */