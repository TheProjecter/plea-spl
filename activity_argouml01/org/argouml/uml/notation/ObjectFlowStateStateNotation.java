// $Id: ObjectFlowStateStateNotation.java 9919 2006-03-01 22:38:48Z mvw $
// Copyright (c) 2006 The Regents of the University of California. All
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

package org.argouml.uml.notation;

import org.argouml.model.Model;

/**
 * This abstract class forms the basis of all Notation providers
 * for the ClassifierInState state name text shown in an ObjectFlowState. 
 * Subclass this for all languages.
 * 
 * @author Michiel
 */
public abstract class ObjectFlowStateStateNotation extends ValueHandler {

    protected Object myObjectFlowState;
    
    /**
     * The constructor.
     * 
     * @param objectflowstate
     */
    public ObjectFlowStateStateNotation(Object objectflowstate) {
        if (!Model.getFacade().isAObjectFlowState(objectflowstate)) {
            throw new IllegalArgumentException("This is not a ObjectFlowState.");
        }
        myObjectFlowState = objectflowstate;
    }

}
