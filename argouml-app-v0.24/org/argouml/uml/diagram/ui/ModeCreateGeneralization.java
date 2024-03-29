// $Id: ModeCreateGeneralization.java 11995 2007-02-07 21:37:31Z bobtarling $
// Copyright (c) 2005-2006 The Regents of the University of California. All
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

import org.argouml.model.Model;
import org.tigris.gef.presentation.Fig;

/**
 * A Mode to interpret user input while creating a generalization edge.
 * The generalization can connect any model element including those
 * represented by edges as well as nodes. The source and destination
 * must be of the same type.
 * TODO: It should be possible to delete this class when issue 4632 is fixed.
 */
public final class ModeCreateGeneralization extends ModeCreateGraphEdge {
    
    /*
     * Well-formedness rule UML 1.4.2 Spec section 4.5.3.20 [5]
     * A GeneralizableElement may only be a child of GeneralizableElement of
     * the same kind.
     * @see org.argouml.uml.diagram.ui.ModeCreateGraphEdge#isDestFigEdgeValid(org.tigris.gef.presentation.FigEdge)
     */
    protected final boolean isConnectionValid(
	    Fig source,
	    Fig dest) {
	return dest != null && source.getClass() == dest.getClass();
    }

    /**
     * Return the meta type of the element that this mode is designed to
     * create. In the case the dependency metatype.
     * @return the dependency meta type.
     */
    protected final Object getMetaType() {
	return Model.getMetaTypes().getGeneralization();
    }
}
