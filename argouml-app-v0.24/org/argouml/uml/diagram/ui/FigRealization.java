// $Id: FigRealization.java 11773 2007-01-09 23:29:54Z bobtarling $
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


/**
 * Fig for a UML Realization.
 * <p>
 * Implementation has been moved to FigAbstraction for alignment
 * with UML spec and to allow reuse for other abstractions such
 * as Derivation, Refinement, or Trace.
 * TODO: Bob says - looks like this should be deprecated with FigAbstraction
 * used instead. Make sure any persistence impact is addressed first.
 */
public class FigRealization extends FigAbstraction {
    /**
     * The constructor.
     *
     */
    public FigRealization() {
        super();
    }

    /**
     * The constructor.
     *
     * @param edge the owning UML element
     */
    public FigRealization(Object edge) {
        super(edge);
    }
    
    /**
     * The UID.
     */
    private static final long serialVersionUID = -5688833795126793130L;
} /* end class FigRealization */

