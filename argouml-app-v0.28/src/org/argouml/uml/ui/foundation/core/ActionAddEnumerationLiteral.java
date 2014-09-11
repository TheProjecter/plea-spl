// $Id: ActionAddEnumerationLiteral.java 41 2010-04-03 20:04:12Z marcusvnac $
// Copyright (c) 1996-2007 The Regents of the University of California. All
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

package org.argouml.uml.ui.foundation.core;

import java.awt.event.ActionEvent;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.kernel.UmlModelMutator;
import org.argouml.model.Model;
import org.argouml.ui.targetmanager.TargetManager;
import org.tigris.gef.undo.UndoableAction;

/**
 * Action to add an operation to a classifier.
 */
@UmlModelMutator
public class ActionAddEnumerationLiteral extends UndoableAction {

    /**
     * Serial version generated by Eclipse for initial implementation.
     */
    private static final long serialVersionUID = -1206083856173080229L;

    /**
     * The constructor.
     */
    public ActionAddEnumerationLiteral() {
        super(Translator.localize("button.new-enumeration-literal"),
                ResourceLoaderWrapper
                        .lookupIcon("button.new-enumeration-literal"));
    }

    ////////////////////////////////////////////////////////////////
    // main methods

    /*
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae) {
        super.actionPerformed(ae);
        Object target =  TargetManager.getInstance().getModelTarget();

        Object enumeration;
        if (Model.getFacade().isAEnumeration(target)) {
            enumeration = target;
        } else if (Model.getFacade().isAEnumerationLiteral(target)) {
            enumeration = Model.getFacade().getEnumeration(target);
        } else {
            return;
        }

        Object oper =
            Model.getCoreFactory().buildEnumerationLiteral("anon",
                enumeration);
        TargetManager.getInstance().setTarget(oper);
    }
}
