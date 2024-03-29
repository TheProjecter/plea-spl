// $Id: FigAbstraction.java 9933 2006-03-03 23:51:33Z tfmorris $
// Copyright (c) 1996-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
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
import java.beans.PropertyChangeEvent;

import org.tigris.gef.presentation.ArrowHeadTriangle;

/**
 * Fig representing a UML Abstraction.
 * <p>
 * Implementation was copied here from FigRealization and parent type changed
 * from FigEdgeModelElement to FigDependency to align better with UML spec and
 * to allow reuse for other abstractions such as Derivation, Refinement, or
 * Trace. FigRealization is just a shell for backward compatibility.
 * <p>
 * Graphical representation is a dashed line and a triangle arrow-head.
 * 
 * @author agauthie
 */
public class FigAbstraction extends FigDependency {

    private ArrowHeadTriangle endArrow;
    
    /*
     * Text group to contain name & stereotypes
     */
    private FigTextGroup middleGroup = new FigTextGroup();

    /**
     * The constructor.
     *
     */
    public FigAbstraction() {
        super();
        endArrow = new ArrowHeadTriangle();
        endArrow.setFillColor(Color.white);
        setDestArrowHead(endArrow);
    }

    /**
     * The constructor.
     *
     * @param edge the owning UML element
     */
    public FigAbstraction(Object edge) {
        this();
        setOwner(edge);
    }

    ////////////////////////////////////////////////////////////////
    // accessors



    /**
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#renderingChanged()
     */
    public void renderingChanged() {
        updateStereotypeText();
        super.renderingChanged();
        middleGroup.calcBounds();
    }
    
    /**
     * @see org.argouml.uml.diagram.ui.FigEdgeModelElement#modelChanged(java.beans.PropertyChangeEvent)
     */
    public void modelChanged(PropertyChangeEvent e) {
        super.modelChanged(e);
        middleGroup.calcBounds();
    }

    /**
     * The UID.
     */
    private static final long serialVersionUID = -5688833795126793130L;
} /* end class FigRealization */

